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
        btn_singup = new javax.swing.JButton();
        forgotPass = new javax.swing.JButton();
        fecha_singup = new com.toedter.calendar.JDateChooser();
        name_singup = new javax.swing.JTextField();
        lname_singup = new javax.swing.JTextField();
        mail_singup = new javax.swing.JTextField();
        pass_singup = new javax.swing.JPasswordField();
        pass2_singup = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBounds(new java.awt.Rectangle(10, 10, 1066, 625));
        setMinimumSize(new java.awt.Dimension(1066, 625));
        getContentPane().setLayout(null);

        user_login.setBackground(new java.awt.Color(31, 52, 159));
        user_login.setFont(new java.awt.Font("Lato", 0, 20)); // NOI18N
        user_login.setForeground(new java.awt.Color(255, 255, 255));
        user_login.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        user_login.setNextFocusableComponent(jLabel1);
        user_login.setOpaque(true);
        getContentPane().add(user_login);
        user_login.setBounds(720, 220, 210, 30);

        login_btn.setBackground(new java.awt.Color(255, 255, 255));
        login_btn.setFont(new java.awt.Font("Lato", 0, 12)); // NOI18N
        login_btn.setBorderPainted(false);
        getContentPane().add(login_btn);
        login_btn.setBounds(910, 420, 130, 40);

        pass_login.setBackground(new java.awt.Color(31, 52, 159));
        pass_login.setFont(new java.awt.Font("Lato", 0, 20)); // NOI18N
        pass_login.setForeground(new java.awt.Color(255, 255, 255));
        pass_login.setBorder(null);
        getContentPane().add(pass_login);
        pass_login.setBounds(720, 310, 210, 30);

        btn_singup.setBorderPainted(false);
        getContentPane().add(btn_singup);
        btn_singup.setBounds(340, 530, 140, 50);

        forgotPass.setBorderPainted(false);
        getContentPane().add(forgotPass);
        forgotPass.setBounds(720, 430, 160, 20);
        getContentPane().add(fecha_singup);
        fecha_singup.setBounds(40, 460, 250, 26);

        name_singup.setBorder(null);
        getContentPane().add(name_singup);
        name_singup.setBounds(50, 190, 240, 30);

        lname_singup.setBorder(null);
        getContentPane().add(lname_singup);
        lname_singup.setBounds(50, 260, 230, 20);

        mail_singup.setBorder(null);
        getContentPane().add(mail_singup);
        mail_singup.setBounds(50, 320, 240, 20);

        pass_singup.setBorder(null);
        getContentPane().add(pass_singup);
        pass_singup.setBounds(50, 380, 240, 30);

        pass2_singup.setBorder(null);
        pass2_singup.setBounds(new java.awt.Rectangle(330, 384, 240, 25));
        getContentPane().add(pass2_singup);
        pass2_singup.setBounds(330, 384, 240, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/IMG/login.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1066, 625);

        pack();
        setLocationRelativeTo(null);
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
    public javax.swing.JButton btn_singup;
    public com.toedter.calendar.JDateChooser fecha_singup;
    private javax.swing.JButton forgotPass;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JTextField lname_singup;
    public javax.swing.JButton login_btn;
    public javax.swing.JTextField mail_singup;
    public javax.swing.JTextField name_singup;
    public javax.swing.JPasswordField pass2_singup;
    public javax.swing.JPasswordField pass_login;
    public javax.swing.JPasswordField pass_singup;
    public javax.swing.JTextField user_login;
    // End of variables declaration//GEN-END:variables
}
