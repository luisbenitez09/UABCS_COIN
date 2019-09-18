package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.usuario;
import view.inicio;
import view.login;

/**
 *
 * @author luisangel
 */
public class controller_login implements ActionListener{

    private login view;

    public controller_login(login view) {
        this.view = view;
        
    }
    
    public void init() {
        this.view.setVisible(true);
        this.view.login_btn.addActionListener(this);
        this.view.btn_singup.addActionListener(this);
        this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.login_btn) {
            String email = this.view.user_login.getText();
            String pass = String.valueOf(this.view.pass_login.getPassword());


            usuario user = usuario.findByLogin(email, pass);
            if (user != null) {
            controller_inicio crtInicio = new controller_inicio(new inicio(), user);
            crtInicio.init();
            this.view.dispose();
            } else {
               JOptionPane.showMessageDialog(view, "Usuario y contraseña incorrectos "); 
            }
        } else if (e.getSource() == this.view.btn_singup){
            String password = String.valueOf(this.view.pass_singup.getPassword());
            String passwordRepeat = String.valueOf(this.view.pass2_singup.getPassword());
            
            if (!password.equals(passwordRepeat)) {
                JOptionPane.showMessageDialog(view,"Error: Las contraseñas no coinciden");
            } else {
                usuario u = new usuario();
                u.setNombre(this.view.name_singup.getText());
                u.setApellido(this.view.lname_singup.getText());
                u.setEmail(this.view.mail_singup.getText());
                u.setPassword(password);
                u.setFechaNacimiento(this.view.fecha_singup.getDate());
                if (u.create()) {
                    JOptionPane.showMessageDialog(view,"Datos guardados correctamente");
                    
                } else {
                    JOptionPane.showMessageDialog(view,"Ocurrio un error con tu información");
                    
                }
                this.view.name_singup.setText("");
                this.view.lname_singup.setText("");
                this.view.pass_singup.setText("");
                this.view.pass2_singup.setText("");
                this.view.mail_singup.setText("");
                this.view.fecha_singup.setDate(null);
                
            }
            
            
        }
    }
    
}
