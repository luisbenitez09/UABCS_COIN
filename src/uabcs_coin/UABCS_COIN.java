/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uabcs_coin;

import controller.controller_login;
import view.login;

/**
 *
 * @author luisangel
 */
public class UABCS_COIN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        login vista = new login();
        
        controller_login crtl = new controller_login(vista);
        crtl.init();
    }
    
}
