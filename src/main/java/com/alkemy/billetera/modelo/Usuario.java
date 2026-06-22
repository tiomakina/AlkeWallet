package com.alkemy.billetera.modelo;

/**
 * Clase que representa a un usuario registrado en la billetera digital.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String contrasena;

    /**
     * Constructor completo del usuario.
     * @param id         identificador único
     * @param nombre     nombre completo
     * @param correo     correo electrónico
     * @param contrasena contraseña de acceso
     */
    public Usuario(int id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', correo='" + correo + "'}";
    }
}
