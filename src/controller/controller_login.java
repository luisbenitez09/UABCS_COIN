package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.usuario;
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
        this.view.jButton1.addActionListener(this);
        this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        String email = this.view.jTextField1.getText();
        String pass = String.valueOf(this.view.jPasswordField1.getPassword());
        
        usuario user = usuario.findByLogin(email, pass);
        if (user != null) {
        JOptionPane.showMessageDialog(view, "Bienvenido " + user.getNombre());
        } else {
           JOptionPane.showMessageDialog(view, "Usuario y contraseña incorrectos "); 
        }
    }
    
}
