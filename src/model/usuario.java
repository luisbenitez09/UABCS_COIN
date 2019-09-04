
package model;
import DB.connectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            System.out.println(pstm);
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                Usuario = new usuario();
                Usuario.setNombre(rs.getString("fisrt_name"));
                Usuario.setApellido(rs.getString("last_name"));
                Usuario.setEmail(rs.getString("email"));
                Usuario.setId(rs.getInt("userId"));
            }
            
            /*if (email.equals(emailDB) && pass.equals(passDB)){
                usuario = new usuario();
                usuario.setNombre("Luis");
                usuario.setApellido("Benitez");
                usuario.setEmail("benitez2909");
            }*/
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Usuario;
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
