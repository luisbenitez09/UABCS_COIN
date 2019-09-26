
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import model.cuenta;
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
        cuenta model = new cuenta();
        model.inicio(this.user.getId());
        this.view.balance.setText(Integer.toString(model.getBalance()));
        
        this.view.basicMoney.setText(Integer.toString(model.getCuentaB()));
        this.view.goldMoney.setText(Integer.toString(model.getCuentaG()));
        this.view.premiumMoney.setText(Integer.toString(model.getCuentaP()));
        
        
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
            try {
                transfer.init();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(controller_inicio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(controller_inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.view.dispose();
        } else if (e.getSource() == this.view.BTNmovimientos){
            controller_movimientos movimientos = new controller_movimientos(new movimientos(),user);
            try {
                movimientos.init();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(controller_inicio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(controller_inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.view.dispose();
        }
    }
    
}
