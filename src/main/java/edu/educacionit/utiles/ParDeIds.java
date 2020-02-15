package edu.educacionit.utiles;

public class ParDeIds {
    String curso;
    String usuario;

    public ParDeIds(){}

    public ParDeIds(String curso, String usuario) {
        this.curso = curso;
        this.usuario = usuario;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "ParDeIds{" +
                "curso='" + curso + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}