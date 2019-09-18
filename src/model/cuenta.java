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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisangel
 */
public class cuenta {
    
    private int id;
    private int balance = 0;
    private int cuentaB = 0;
    private int cuentaP = 0;
    private int cuentaG = 0;
    private static final String TABLE = "cuentas";
    
    
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
                    System.out.println(cuentaG);
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
    
    
    
}
