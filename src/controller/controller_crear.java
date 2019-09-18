package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.usuario;
import view.crearCuenta;
import model.cuenta;
import view.inicio;
import view.login;
import view.movimientos;
import view.transferencia;

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
        
        this.view.close.addActionListener(this);
        this.view.inicio.addActionListener(this);
        this.view.BTNcrear.addActionListener(this);
        
        this.view.name.setText(this.user.getNombre() + " " +this.user.getApellido());
        
        this.view.tiposCuentas.removeAllItems();
        cuenta model = new cuenta();
        String[] tipos = model.tipos();
        
        for (int i = 0; i < tipos.length; i++) {
            this.view.tiposCuentas.addItem(tipos[i]);
            
        }
        this.view.tiposCuentas.addActionListener(this);
        
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.view.close){
            user = null;
            controller_login login = new controller_login(new login());
            login.init();
            this.view.dispose();
        } else if (e.getSource() == this.view.inicio){
            controller_inicio inicio = new controller_inicio(new inicio(),this.user);
            inicio.init();
            this.view.dispose();
        } else if (e.getSource() == this.view.BTNcrear){
            String tipo = this.view.tiposCuentas.getSelectedItem().toString();
            cuenta model = new cuenta();
            model.addCuenta(this.user.getId(), tipo,Integer.parseInt(this.view.monto.getText()));
            JOptionPane.showMessageDialog(view, "Cuenta creada");
            controller_inicio inicio = new controller_inicio(new inicio(),this.user);
            inicio.init();
            this.view.dispose();
        } 
    }
    
}
