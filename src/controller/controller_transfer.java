/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import model.usuario;
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
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
