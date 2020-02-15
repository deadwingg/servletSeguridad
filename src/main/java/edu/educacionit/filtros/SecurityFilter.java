package edu.educacionit.filtros;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        String authorization = null;
        
        File f = new File("c:\\var\\log\\ver.txt");
        HttpServletRequest req = (HttpServletRequest)sr;
        
        Enumeration<String> headers = req.getHeaderNames();
        
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            String value = req.getHeader(key);
            String output = String.join(" ", 
                    key, " : ", value, System.lineSeparator());
            
            org.apache.commons.io.FileUtils.write(f, output, "UTF-8", true);
            
            if (key.equals("authorization")) {
                authorization = value;
            }
        }
        
        org.apache.commons.io.FileUtils.write(f, System.lineSeparator(), "UTF-8", true);
        
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String value = req.getParameter(key);
            String output = String.join("=", key, value);

            org.apache.commons.io.FileUtils.write(f, output, "UTF-8", true);
            org.apache.commons.io.FileUtils.write(f, System.lineSeparator(), "UTF-8", true);
        }
        
        org.apache.commons.io.FileUtils.write(f, System.lineSeparator(), "UTF-8", true);
        org.apache.commons.io.FileUtils.write(f, System.lineSeparator(), "UTF-8", true);
        org.apache.commons.io.FileUtils.write(f, System.lineSeparator(), "UTF-8", true);
        
        if (authorization == null) {
            HttpServletResponse res = (HttpServletResponse)sr1;
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        fc.doFilter(sr, sr1);
    }
}
