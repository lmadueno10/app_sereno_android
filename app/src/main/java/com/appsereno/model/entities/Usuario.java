package com.appsereno.model.entities;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("id")
    private int id;
    @SerializedName("usuario")
    private String usuario;
    @SerializedName("nombres_apellidos")
    private String nombresApellidos;
    @SerializedName("profile")
    private int profile;

    public Usuario(int id, String usuario, String nombresApellidos, int profile) {
        this.id = id;
        this.usuario = usuario;
        this.nombresApellidos = nombresApellidos;
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombresApellidos() {
        return nombresApellidos;
    }

    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", nombresApellidos='" + nombresApellidos + '\'' +
                ", profile=" + profile +
                '}';
    }
}
