
package model;
import DB.connectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
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
    private static final String TABLE = "users";
     
    
    public static usuario findByLogin(String email, String pass) {
        usuario Usuario = null;
        Connection conn = null;
        
        try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE + " WHERE email = ? AND pass = sha1(?)";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setString(1, email);
            pstm.setString(2, pass);
            
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                Usuario = new usuario();
                Usuario.setNombre(rs.getString("fisrt_name"));
                Usuario.setApellido(rs.getString("last_name"));
                Usuario.setEmail(rs.getString("email"));
                Usuario.setId(rs.getInt("userId"));
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
    
    public boolean create() {
        boolean created = false;
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            String query = "INSERT INTO " + TABLE + "(fisrt_name, last_name, email, pass, cumple) VALUES (?,?,?,?,?)";
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, this.nombre);
            pstm.setString(2, this.apellido);
            pstm.setString(3, this.email);
            pstm.setString(4, this.password);
            pstm.setDate(5, this.getFechaNacimiento());
            
            int row = pstm.executeUpdate();
            created = row == 1;
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
