package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import model.usuario;
import view.crearCuenta;
import view.inicio;
import view.login;
import view.movimientos;
import view.transferencia;

/**
 *
 * @author luisangel
 */
public class controller_transfer implements ActionListener{
    
    private transferencia view;
    private usuario user;

    public controller_transfer(transferencia view, usuario user) {
        this.view = view;
        this.user = user;
    }
    
    public void init(){
        this.view.setVisible(true);
        this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.view.close.addActionListener(this);
        this.view.inicio.addActionListener(this);
        this.view.crearCuenta.addActionListener(this);
        this.view.movimientos.addActionListener(this);
        this.view.BTNtransferir.addActionListener(this);
        this.view.nombre.setText(this.user.getNombre() + " " +this.user.getApellido());
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.close) {
            user = null;
            controller_login login = new controller_login(new login());
            login.init();
            this.view.dispose();
        } else if ( e.getSource() == this.view.inicio) {
            controller_inicio inicio = new controller_inicio(new inicio(),this.user);
            inicio.init();
            this.view.dispose();
        } else if (e.getSource() == this.view.crearCuenta){
            controller_crear crear = new controller_crear(new crearCuenta(), this.user);
            crear.init();
            this.view.dispose();
        } else if (e.getSource() == this.view.movimientos) {
            controller_movimientos movim = new controller_movimientos(new movimientos(), this.user);
            movim.init();
            this.view.dispose();
        } else if (e.getSource() == this.view.BTNtransferir) {
            
        }
    }
    
}
