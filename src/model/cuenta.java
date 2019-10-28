/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DB.connectionManager;
import java.sql.CallableStatement;
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
            String query = "{CALL INICIO_CUENTA(?, ?, ?, ?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setInt(1, id);
            cb.execute();
                
            cuentaB = cb.getInt(2);
            cuentaG = cb.getInt(3);
            cuentaP = cb.getInt(4);
            balance = cuentaB + cuentaG + cuentaP;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String[] tipos () {
        
        String[] tipos = new String[3];
        tipos[0] = "Normal";
        tipos[1] = "Gold";
        tipos[2] = "Premium";
        
        return tipos;
    }
    
    public int addCuenta (int id, String tipo, int money) {
        int created = 0;
        this.id = id;
        int tipoC = 0;
        Connection conn = null;
        if (tipo.equals("Normal")) {
            tipoC = 1;
        } if (tipo.equals("Gold")) {
            tipoC = 2;
        } if (tipo.equals("Premium")) {
            tipoC = 3;
        }
        
        try {
            conn = connectionManager.getConnection();
            String query = "{CALL ADD_CUENTA(?, ?, ?, ?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setInt(1, id);
            cb.setInt(2, tipoC);
            cb.setInt(3, money);
            cb.execute();
            created = cb.getInt(4);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return created;
    }
    
    // ------------------ STRING -----------------
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
   
    public String[] cuentasFrec (int cuentaM)  {
        this.id = id;
        
        Connection conn = null;
        String[] cuentas = null;
        
        try {
            conn = connectionManager.getConnection();
            String query = "SELECT cuentaRecurrente FROM cuentasFrecuentes WHERE cuentaMaster = ?";
            PreparedStatement  pstm = conn.prepareStatement(query);
            pstm.setInt(1, cuentaM);
            ResultSet rs = pstm.executeQuery();
            
            int  iter = 0;
            while (rs.next()) {
                iter++;
            }
            cuentas = new String[iter];
            
            rs = pstm.executeQuery();
            
            iter = 0;
            while (rs.next()) {
                cuentas[iter] = rs.getString("cuentaRecurrente");
                iter++;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(cuenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return cuentas;
    }

    public int dineroCuenta(int numCuenta) {
        
        Connection conn = null;
        int dinero = 0;
        try {
            conn = connectionManager.getConnection();
            String query = "{CALL DINERO_CUENTA(?, ?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setInt(1, numCuenta);
            cb.execute();
            dinero = cb.getInt(2);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dinero;
    }
    
    public int transferir (int cuentaOrigen, int cuentaDestino, int saldo) {
       int status = 0;
        if (validarCuenta(cuentaDestino)) {
        
            Connection conn = null;
            float cantidad = (float)saldo;
            try {
                conn = connectionManager.getConnection();
                String query = "{CALL TRANSFERENCIA(?,?,?,?)}";
                CallableStatement  cb = conn.prepareCall(query);

                cb.setInt(1, cuentaOrigen);
                cb.setInt(2, cuentaDestino);
                cb.setFloat(3, cantidad);
                
                cb.execute();

                status = cb.getInt(4);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view,ex.toString());
                Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(view,"Cuenta inexistente");
        }
        return status;
    }
    
    private boolean validarCuenta(int cuentaDestino) {
        
        boolean valida = false;
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            String query = "{CALL VALIDAR_CUENTA(?, ?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setInt(1, cuentaDestino);
            cb.execute();
            
            if (cb.getBoolean(2)) {
                valida = true;
            } else {
                JOptionPane.showMessageDialog(view,"Cuenta ingresada no existe");
            }
            
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(view,"Ocurrio un error");
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,"Ocurrio un error");
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valida;
    }
    
    public int imgCard (int numCuenta) {
        int numCard = 0;
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            String query = "{CALL IMG_CARD(?, ?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setInt(1, numCuenta);
            cb.execute();
            
            numCard = cb.getInt(2);
            
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
            String query = "{CALL ULTIMO_MOV(?, ?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setInt(1, numCuenta);
            cb.setInt(2, numCuenta);
            cb.execute();
            
            ultimo = cb.getString(2);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ultimo;
    }
    
    //---------------------STRING ----------------------
    public String [] [] movimientos (int numCuenta) {
        String [] [] movi = null;
        Connection conn = null;
        
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
    
    public int addFrecuente (int numCuenta, int cuentaPrincipal) {
        int status = 0;
        
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            String query = "{CALL ADD_FRECUENTE(?,?,?)}";
            CallableStatement  cb = conn.prepareCall(query);
            cb.setInt(1, numCuenta);
            cb.setInt(2, cuentaPrincipal);
            cb.execute();
            
            status = cb.getInt(3);
            
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(view,"Ocurrio un error");
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,"Ocurrio un error");
            Logger.getLogger(usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
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
