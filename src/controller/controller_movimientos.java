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
import java.sql.Date;
import javax.swing.JLabel;

/**
 *
 * @author luisangel
 */
public class controller_movimientos implements ActionListener {
    
    private movimientos view;
    private usuario user;
    private Date fechaInicio;
    private Date fechaFin;

    public controller_movimientos(movimientos view, usuario user) {
        this.view = view;
        this.user = user;
    }
    
    public void init() throws ClassNotFoundException, SQLException{
        this.view.setVisible(true);
        this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.view.close.addActionListener(this);
        this.view.inicio.addActionListener(this);
        this.view.crearCuenta.addActionListener(this);
        this.view.BTNtransferencia.addActionListener(this);
        this.view.name.setText(this.user.getNombre() + " " +this.user.getApellido());
        
        
        cuenta model = new cuenta();
        
        this.view.numCuenta.removeAllItems();
        String[] cuentas = model.cuentas(this.user.getId());
        for (int i = 0; i < cuentas.length; i++) {
            this.view.numCuenta.addItem(cuentas[i]);
        }
        this.view.numCuenta.addActionListener(this);
        
        String numCuenta = this.view.numCuenta.getSelectedItem().toString();
        
        int saldo = model.dineroCuenta(Integer.parseInt(numCuenta));
        
        this.view.balance.setText(saldo+"");
        this.view.balance2.setText(saldo+"");
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
            
        this.view.consulta.addActionListener(this);
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
        } else if (e.getSource() == this.view.BTNtransferencia) {
            controller_transfer transfer = new controller_transfer(new transferencia(), this.user);
            try {
                transfer.init();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(controller_movimientos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(controller_movimientos.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.view.dispose();
        } else if (e.getSource() == this.view.numCuenta) {
            cuenta model = new cuenta();
            String numCuenta = this.view.numCuenta.getSelectedItem().toString();
        
            int saldo = model.dineroCuenta(Integer.parseInt(numCuenta));

            this.view.balance.setText(saldo+"");
            this.view.balance2.setText(saldo+"");
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
            } else if (e.getSource() == this.view.consulta) {
                System.out.println("Originales:");
                System.out.println(this.view.fechaInicio.getDate());
                System.out.println(this.view.fechaFin.getDate());
                
                this.view.jTextArea1.setText("");
                this.view.jTextArea2.setText("");
                
                String numCuenta = this.view.numCuenta.getSelectedItem().toString();
                
                cuenta model = new cuenta();
                
                model.setFechaInicio(this.view.fechaInicio.getDate());
                model.setFechaFin(this.view.fechaFin.getDate());
                
                String[][] datos = model.movimientos(Integer.parseInt(numCuenta));
                
                String[] depo = new String[datos.length];
                String[] transfer = new String[datos.length];
                String[] saldos = new String[datos.length];
                String[] fechas = new String[datos.length];
                
                for (int i =0; i < datos.length;i++) {
                    transfer[i] = datos[i][0];
                    depo[i] = datos[i][1];
                    saldos[i] = datos[i][2];
                    fechas[i] = datos[i][3];
                }
                
                
                for (int i = 0; i < datos.length; i++) {
                    if (depo[i].equals(numCuenta)) {
                        this.view.jTextArea1.setText(this.view.jTextArea1.getText() + fechas[i] + "                 " + saldos[i] + "\n\n");
                        this.view.jTextArea2.setText(this.view.jTextArea2.getText() + " --- " + "\n\n");
                    } else {
                        this.view.jTextArea1.setText(this.view.jTextArea1.getText() + fechas[i] + "                 " + " --- " + "\n\n");
                        this.view.jTextArea2.setText(this.view.jTextArea2.getText() + saldos[i] + "\n\n");
                    }
                    
                    
                    
                    
                }
                
                        
            }
        
    }

    
    
}
