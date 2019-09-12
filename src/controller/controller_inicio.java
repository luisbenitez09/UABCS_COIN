
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
public class controller_inicio implements ActionListener{
    
    private inicio view;
    private usuario user;

    public controller_inicio(inicio view, usuario user) {
        this.view = view;
        this.user = user;
    }
    
    public void init(){
        this.view.setVisible(true);
        this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.view.close.addActionListener(this);
        this.view.BTNcrearCuenta.addActionListener(this);
        this.view.BTNmovimientos.addActionListener(this);
        this.view.BTNtransferencia.addActionListener(this);
        
        this.view.name.setText(this.user.getNombre() + " " +this.user.getApellido());
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.BTNcrearCuenta) {
            controller_crear crtCrear = new controller_crear(new crearCuenta(), user);
            crtCrear.init();
            this.view.dispose();
        } else if(e.getSource() == this.view.close){
            user = null;
            controller_login login = new controller_login(new login());
            login.init();
            this.view.dispose();
        } else if (e.getSource() == this.view.BTNtransferencia){
            controller_transfer transfer = new controller_transfer(new transferencia(),user);
            transfer.init();
            this.view.dispose();
        } else if (e.getSource() == this.view.BTNmovimientos){
            controller_movimientos movimientos = new controller_movimientos(new movimientos(),user);
            movimientos.init();
            this.view.dispose();
        }
    }
    
}
