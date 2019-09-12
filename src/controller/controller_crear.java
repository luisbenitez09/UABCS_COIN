package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import model.usuario;
import view.crearCuenta;

/**
 *
 * @author luisangel
 */
public class controller_crear implements ActionListener{

    private crearCuenta view;
    private usuario user;
    
    public controller_crear(crearCuenta view, usuario user) {
        this.user = user;
        this.view = view;
    }
    
    public void init() {
        this.view.setVisible(true);
        this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }

    
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        
    }
    
}
