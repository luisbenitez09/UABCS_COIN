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
public class controller_transfer implements ActionListener{
    
    private transferencia view;
    private usuario user;

    public controller_transfer(transferencia view, usuario user) {
        this.view = view;
        this.user = user;
    }
    
    public void init() throws ClassNotFoundException, SQLException{
        this.view.setVisible(true);
        this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.view.close.addActionListener(this);
        this.view.inicio.addActionListener(this);
        this.view.crearCuenta.addActionListener(this);
        this.view.movimientos.addActionListener(this);
        this.view.BTNtransferir.addActionListener(this);
        this.view.nombre.setText(this.user.getNombre() + " " +this.user.getApellido());
        
        cuenta model = new cuenta();
        
        this.view.numCuenta.removeAllItems();
        String[] cuentas = model.cuentas(this.user.getId());
        for (int i = 0; i < cuentas.length; i++) {
            this.view.numCuenta.addItem(cuentas[i]);
        }
        this.view.numCuenta.addActionListener(this);
        
        String numCuenta = this.view.numCuenta.getSelectedItem().toString();
        
        int saldo = model.dineroCuenta(Integer.parseInt(numCuenta));
        
        this.view.balanceCuenta.setText(saldo+"");
        this.view.balanceCuenta2.setText(saldo+"");
        this.view.terminacionCuenta.setText(numCuenta);
        this.view.ultimoMov.setText(model.ultiMov(Integer.parseInt(numCuenta)));
        int tipoCuenta = model.imgCard(Integer.parseInt(numCuenta));
            
            switch(tipoCuenta) {
                case 1:
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/basicCard.png")));
                    break;
                case 2:
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/goldCard.png")));
                    break;
                case 3:
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/premiumCard.png")));
                    break;
                default: 
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/newCard.png")));
                    break;
            }
            
            
        
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
            try {
                movim.init();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(controller_transfer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(controller_transfer.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.view.dispose();
        } else if (e.getSource() == this.view.BTNtransferir) {
            cuenta model = new cuenta();
            
            String cuentaOrigen = this.view.numCuenta.getSelectedItem().toString();
            String cuentaDeposito = this.view.cuentaDeposito.getText();
            String saldoTransfer = this.view.monto.getText();
            
            model.transferir(Integer.parseInt(cuentaOrigen),Integer.parseInt(cuentaDeposito),Integer.parseInt(saldoTransfer));
            this.view.cuentaDeposito.setText("");
            this.view.monto.setText("");
            
            
            String numCuenta = this.view.numCuenta.getSelectedItem().toString();
        
            int saldo = model.dineroCuenta(Integer.parseInt(numCuenta));

            this.view.balanceCuenta.setText(saldo+"");
            this.view.balanceCuenta2.setText(saldo+"");
            this.view.terminacionCuenta.setText(numCuenta);
            this.view.ultimoMov.setText("");
            
            
        } else if (e.getSource() == this.view.numCuenta) {
            cuenta model = new cuenta();
            String numCuenta = this.view.numCuenta.getSelectedItem().toString();
        
            int saldo = model.dineroCuenta(Integer.parseInt(numCuenta));

            this.view.balanceCuenta.setText(saldo+"");
            this.view.balanceCuenta2.setText(saldo+"");
            this.view.terminacionCuenta.setText(numCuenta);
            int tipoCuenta = model.imgCard(Integer.parseInt(numCuenta));
            this.view.ultimoMov.setText(model.ultiMov(Integer.parseInt(numCuenta)));
            
            switch(tipoCuenta) {
                case 1:
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/basicCard.png")));
                    break;
                case 2:
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/goldCard.png")));
                    break;
                case 3:
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/premiumCard.png")));
                    break;
                default: 
                    this.view.card.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/newCard.png")));
                    break;
            }
            
        }
    }
    
}
