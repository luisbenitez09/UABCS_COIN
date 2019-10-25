package model;

import DB.connectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Types;
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
    private Date fechaNacimiento;

     
    
    public static usuario findByLogin(String email, String pass) {
        usuario Usuario = null;
        Connection conn = null;
        
        try {
            
            conn = connectionManager.getConnection();
            String query = "{CALL FIND_BY_LOGIN(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setString(1, email);
            cb.setString(2, pass);
            cb.execute();
            
            boolean aux = cb.getBoolean(6);
            
            if (aux) {
                Usuario = new usuario();
                Usuario.setNombre(cb.getString(3));
                Usuario.setApellido(cb.getString(4));
                Usuario.setEmail(cb.getString(7));
                Usuario.setId(cb.getInt(5));
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return Usuario;
    }
    
    public int createSP() {
        int created = 0;
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            String query = "{CALL ADD_USER(?, ?, ?, ?, ?, ?)}";
            CallableStatement cb = conn.prepareCall(query);
            cb.setString(1, this.nombre);
            cb.setString(2, this.apellido);
            cb.setString(3, this.email);
            cb.setString(4, this.password);
            cb.setDate(5, this.getFechaNacimiento());
            cb.registerOutParameter(6, Types.INTEGER);
            cb.execute();
            created = cb.getInt(6);
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return created;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(java.util.Date fechaNacimiento) {
        
        this.fechaNacimiento = new Date(fechaNacimiento.getTime());
    }
    
}
