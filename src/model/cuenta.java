/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DB.connectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.transferencia;
 
/**
 *
 * @author luisangel
 */
public class cuenta {
    
    private transferencia view;
    
    
    
    private int id;
    private int balance = 0;
    private int cuentaB = 0;
    private int cuentaP = 0;
    private int cuentaG = 0;
    private int numCuenta;
    private int tipo;
    private float saldo;
    private Date fechaInicio;
    private Date fechaFin;
    private String [] saldos;
    private String [] fechas;
    private static final String TABLE = "cuentas";
    private static final String TABLE2 = "transacciones";
    
    
    
    
    
    
    
    
    public void inicio (int id) {
        this.id = id;
        
        Connection conn = null;
        
        try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT SUM(" + TABLE +".saldo) as balance, tipoCuenta FROM " + TABLE + " WHERE userCuenta = ? GROUP BY tipoCuenta";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, id);
            
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                
                if (rs.getInt("tipoCuenta") == 1) {
                    cuentaB = rs.getInt("balance");
                } 
                if (rs.getInt("tipoCuenta") == 2) {
                    cuentaG = rs.getInt("balance");
                    
                }
                if (rs.getInt("tipoCuenta") == 3) {
                    cuentaP = rs.getInt("balance");
                }
                
                balance = cuentaB + cuentaG + cuentaP;
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String[] tipos () {
        Connection conn = null;
        
        String[] tipos = new String[3];
        try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT nombreTipo FROM tiposCuenta";
            PreparedStatement  pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            
            int  iter = 0;
            while (rs.next()) {
                tipos[iter] = rs.getString("nombreTipo");
                iter++;
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipos;
    }
    
    public void addCuenta (int id, String tipo, int money) {
        this.id = id;
        int tipoC = 0;
        Connection conn = null;
        if (tipo.equals("Normal")) {
            tipoC = 1;
        } 
        if (tipo.equals("Gold")) {
            tipoC = 2;
        } if (tipo.equals("Premium")) {
            tipoC = 3;
        }
        
        try {
            
            conn = connectionManager.getConnection();
            String query = "INSERT INTO cuentas(userCuenta, tipoCuenta, saldo) VALUES (?, ?, ?)";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, id);
            pstm.setInt(2, tipoC);
            pstm.setInt(3, money);
            int row = pstm.executeUpdate();
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String[] cuentas (int id) throws ClassNotFoundException, SQLException {
        this.id = id;
        
        Connection conn = null;
        String[] cuentas = null;
        
        try {
            conn = connectionManager.getConnection();
            String query = "SELECT cuentaId FROM " + TABLE + " WHERE userCuenta = ?";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            
            int  iter = 0;
            while (rs.next()) {
                iter++;
            }
            cuentas = new String[iter];
            
            rs = pstm.executeQuery();
            
            iter = 0;
            while (rs.next()) {
                cuentas[iter] = rs.getString("cuentaId");
                iter++;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(cuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return cuentas;
    }

    public int dineroCuenta(int numCuenta) {
        
        Connection conn = null;
        int dinero = 0;
        try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT saldo FROM " + TABLE +" WHERE cuentaId = ?";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, numCuenta);
            
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()){
                dinero = rs.getInt("saldo");
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dinero;
    }
    
    public void transferir (int cuentaOrigen, int cuentaDestino, int saldo) {
       
        if (validarCuenta(cuentaDestino)) {
        
        
            Connection conn = null;
            float cantidad = (float)saldo;
            try {

                conn = connectionManager.getConnection();
                String query = "INSERT INTO " + TABLE2 + "(cuentaTransferencia,cuentaDeposito,saldoTransferencia) VALUES (?,?,?);";
                PreparedStatement  pstm = conn.prepareStatement(query);

                pstm.setInt(1, cuentaOrigen);
                pstm.setInt(2, cuentaDestino);
                pstm.setFloat(3, cantidad);
                System.out.println(pstm);
                int row = pstm.executeUpdate();

                JOptionPane.showMessageDialog(view,"Transferencia realizada exitosamente");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view,ex.toString());
                Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(view,"Cuenta inexistente");
        }
    }
    
    private boolean validarCuenta(int cuentaDestino) {
        boolean valida = false;
        
        Connection conn = null;
        
        try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE + " WHERE cuentaId = ?";
            PreparedStatement  pstm = conn.prepareStatement(query);
            
            pstm.setInt(1, cuentaDestino);
            
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                valida = true;
            }
            
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(view,"Cuenta ingresada no existe");
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,"Cuenta ingresada no existe");
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valida;
    }
    
    public int imgCard (int numCuenta) {
        int numCard = 0;
        
        Connection conn = null;
        
        try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT tipoCuenta FROM " + TABLE +" WHERE cuentaId = ?";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, numCuenta);
            
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()){
                numCard = rs.getInt("tipoCuenta");
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numCard;
    }
    
    public String ultiMov (int numCuenta) {
        String ultimo = "";
        
        Connection conn = null;
        
        try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT fechaTranfer FROM " + TABLE2 + " WHERE cuentaTransferencia = ? or cuentaDeposito = ? ORDER by fechaTranfer DESC LIMIT 1";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, numCuenta);
            pstm.setInt(2, numCuenta);
            
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()){
                ultimo = rs.getString("fechaTranfer");
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ultimo;
    }
    
    public String [] [] movimientos (int numCuenta) {
        String [] [] movi = null;
        Connection conn = null;
        
        System.out.println("Atributos:");
        System.out.println(this.fechaInicio.after(fechaFin));
        if (this.fechaInicio.before(fechaFin)) {
            try {
            
            conn = connectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE2 + " WHERE cuentaDeposito = ? and fechaTranfer between date(?) " +
                    "and date(?) OR cuentaTransferencia = ? and fechaTranfer between date(?) and date(?)";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, numCuenta);
            pstm.setDate(2, this.fechaInicio);
            pstm.setDate(3, this.fechaFin);
            pstm.setInt(4, numCuenta);
            pstm.setDate(5, this.fechaInicio);
            pstm.setDate(6, this.fechaFin);
            System.out.println(pstm);
            ResultSet rs = pstm.executeQuery();
            
            int contador = 0;
            while (rs.next()) {
                contador++;
            }
            movi = new String [contador] [4];
            
            rs = pstm.executeQuery();
            
            int  iter = 0;
            while (rs.next()) {
                movi[iter][0] = rs.getString("cuentaTransferencia");
                movi[iter][1] = rs.getString("cuentaDeposito");
                movi[iter][2] = rs.getString("saldoTransferencia");
                movi[iter][3] = rs.getString("fechaTranfer");
                iter++;
            }
            
            
            
            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(view,"Fechas invalidas");
        }
        
        
        return movi;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCuentaB() {
        return cuentaB;
    }
    public void setCuentaB(int cuentaB) {
        this.cuentaB = cuentaB;
    }

    public int getCuentaP() {
        return cuentaP;
    }
    public void setCuentaP(int cuentaP) {
        this.cuentaP = cuentaP;
    }

    public int getCuentaG() {
        return cuentaG;
    }
    public void setCuentaG(int cuentaG) {
        this.cuentaG = cuentaG;
    }

    public int getNumCuenta() {
        return numCuenta;
    }

    public int getTipo() {
        return tipo;
    }

    public float getSaldo() {
        return saldo;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(java.util.Date fechaInicio) {
        this.fechaInicio = new Date(fechaInicio.getTime());
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(java.util.Date fechaFin) {
        this.fechaFin = new Date(fechaFin.getTime());
    }

    public String[] getSaldos() {
        return saldos;
    }

    public String[] getFechas() {
        return fechas;
    }
    
    
}
