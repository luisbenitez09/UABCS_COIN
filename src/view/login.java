/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author luisangel
 */
public class login extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        user_login = new javax.swing.JTextField();
        login_btn = new javax.swing.JButton();
        pass_login = new javax.swing.JPasswordField();
        forgotPass = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBounds(new java.awt.Rectangle(10, 10, 1280, 750));
        getContentPane().setLayout(null);

        user_login.setBackground(new java.awt.Color(6, 121, 205));
        user_login.setFont(new java.awt.Font("Lato", 0, 20)); // NOI18N
        user_login.setForeground(new java.awt.Color(255, 255, 255));
        user_login.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        user_login.setNextFocusableComponent(jLabel1);
        user_login.setOpaque(true);
        getContentPane().add(user_login);
        user_login.setBounds(880, 270, 230, 30);

        login_btn.setBackground(new java.awt.Color(255, 255, 255));
        login_btn.setFont(new java.awt.Font("Lato", 0, 12)); // NOI18N
        login_btn.setBorderPainted(false);
        getContentPane().add(login_btn);
        login_btn.setBounds(1080, 500, 170, 60);

        pass_login.setBackground(new java.awt.Color(6, 121, 205));
        pass_login.setFont(new java.awt.Font("Lato", 0, 20)); // NOI18N
        pass_login.setForeground(new java.awt.Color(255, 255, 255));
        pass_login.setBorder(null);
        getContentPane().add(pass_login);
        pass_login.setBounds(870, 376, 240, 30);

        forgotPass.setBorderPainted(false);
        getContentPane().add(forgotPass);
        forgotPass.setBounds(860, 520, 190, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/login_uabcs_coin.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1280, 750);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton forgotPass;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JButton login_btn;
    public javax.swing.JPasswordField pass_login;
    public javax.swing.JTextField user_login;
    // End of variables declaration//GEN-END:variables
}
