package br.com.weather.model;

public class Usuario {
    private String email;
    private String senha;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(){

    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
