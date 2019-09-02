/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author luisangel
 */
public class usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String fechaNacimiento;
     
    
    public static usuario findByLogin(String email, String pass) {
        String emailDB = "benitez2909";
        String passDB = "2909";
        
        usuario usuario = null;
        
        if (email.equals(emailDB) && pass.equals(passDB)){
            usuario = new usuario();
            usuario.setNombre("Luis");
            usuario.setApellido("Benitez");
            usuario.setEmail("benitez2909");
        }
        return usuario;
    }
    

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
}
