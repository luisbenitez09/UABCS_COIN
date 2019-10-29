package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    private int tipoTransferencia = 1;

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
        
        this.view.tipoT.removeAllItems();
        this.view.tipoT.addItem("Cuenta Nueva");
        this.view.tipoT.addItem("Mis Cuentas");
        this.view.tipoT.addItem("Cuenta Frecuente");
        this.view.tipoT.addActionListener(this);
        this.view.cFrecuentes.setVisible(false);
        
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
            String cuentaDeposito = "";
            if (this.tipoTransferencia == 1) {
                cuentaDeposito = this.view.cuentaDeposito.getText();
                if (this.view.addFrecuente.isSelected()) {
                    cuenta c = new cuenta();
                    int status = c.addFrecuente(Integer.parseInt(cuentaDeposito), Integer.parseInt(cuentaOrigen) );
                    
                    if (status == 1 ) {
                        JOptionPane.showMessageDialog(view, "Cuenta agregada a tus cuentas frecuentes. ");
                    } else if (status == -1) {
                        JOptionPane.showMessageDialog(view, "La cuenta ya esta en tu lista de cuentas frecuentes.");
                    } else {
                         JOptionPane.showMessageDialog(view, "Ocurrio un error en cuetnas frecuentes. Intenta agregarla más tarde");
                    }
                }
            } else {
                cuentaDeposito = this.view.cFrecuentes.getSelectedItem().toString();
            }
            
            String saldoTransfer = this.view.monto.getText();
            int status = model.transferir(Integer.parseInt(cuentaOrigen),Integer.parseInt(cuentaDeposito),Integer.parseInt(saldoTransfer));
            
            if (status == 1) {
                JOptionPane.showMessageDialog(view, "Transferencia realizada exitosamente.");
            } else if (status == -1) {
                JOptionPane.showMessageDialog(view, "No puedes hacer un deposito a la misma cuenta.");
            } else if (status == -2) {
                JOptionPane.showMessageDialog(view, "Tú saldo es insuficiente, por favor, intente con otra cantidad.");
            } else if (status == -3) {
                JOptionPane.showMessageDialog(view, "No puedes exeder tu limite de transferencia diaria");
            } else if (status == -4) {
                JOptionPane.showMessageDialog(view, "No puedes exceder el limite de tu cuenta");
            } else {
                JOptionPane.showMessageDialog(view, "Ocurrio un error.");
            }
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
            
        } else if (e.getSource() == this.view.tipoT) {
            String tipoTrans = this.view.tipoT.getSelectedItem().toString();
            if (tipoTrans.equals("Cuenta Nueva")) {
                
                
                this.view.cuentaDeposito.setVisible(true);
                this.view.cFrecuentes.setVisible(false);
                this.view.addFrecuente.setVisible(true);
                this.tipoTransferencia = 1;
            } else if (tipoTrans.equals("Mis Cuentas")) {
                
                
                this.view.cuentaDeposito.setVisible(false);
                this.view.cFrecuentes.setVisible(true);
                this.view.addFrecuente.setVisible(false);
                this.view.cFrecuentes.removeAllItems();
                cuenta m = new cuenta();
                int cuentaOrigen = Integer.parseInt(this.view.numCuenta.getSelectedItem().toString());
                String[] cuentas = m.cuentas(this.user.getId());
                for (int i = 0; i < cuentas.length; i++) {
                    this.view.cFrecuentes.addItem(cuentas[i]);
                }
                this.tipoTransferencia = 2;
            } else {
                
                this.view.cuentaDeposito.setVisible(false);
                this.view.cFrecuentes.setVisible(true);
                this.view.addFrecuente.setVisible(false);
                cuenta m = new cuenta();
                int cuentaOrigen = Integer.parseInt(this.view.numCuenta.getSelectedItem().toString());
                this.view.cFrecuentes.removeAllItems();
                String[] cuentas = m.cuentasFrec(cuentaOrigen);
                for (int i = 0; i < cuentas.length; i++) {
                    this.view.cFrecuentes.addItem(cuentas[i]);
                }
                this.tipoTransferencia = 2;
            }
            
        }
    }
    
}
