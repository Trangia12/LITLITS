package litskitchenware;

import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author MERYLL MEDINA
 */
public class Menu extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    public Menu() {
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

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        NewTransactionButt = new javax.swing.JButton();
        ViewTransactionHistoryButt = new javax.swing.JButton();
        ViewAccountListButt = new javax.swing.JButton();
        LogoutButt = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ViewStocksButt = new javax.swing.JButton();

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(0, 0, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("LIT'S");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LIT'S");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 90, -1));

        jLabel4.setFont(new java.awt.Font("Vivaldi", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Kitchenware");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 200, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/441477846_1158283951980106_2400353697483071379_n.jpg"))); // NOI18N
        jLabel5.setText("jLabel5");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 460, 440));

        NewTransactionButt.setBackground(new java.awt.Color(0, 0, 0));
        NewTransactionButt.setText("New Transaction");
        NewTransactionButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewTransactionButtActionPerformed(evt);
            }
        });
        jPanel3.add(NewTransactionButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 190, 40));

        ViewTransactionHistoryButt.setBackground(new java.awt.Color(0, 0, 0));
        ViewTransactionHistoryButt.setText("View Transaction History");
        ViewTransactionHistoryButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewTransactionHistoryButtActionPerformed(evt);
            }
        });
        jPanel3.add(ViewTransactionHistoryButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 190, 40));

        ViewAccountListButt.setBackground(new java.awt.Color(0, 0, 0));
        ViewAccountListButt.setText("View Account List");
        ViewAccountListButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewAccountListButtActionPerformed(evt);
            }
        });
        jPanel3.add(ViewAccountListButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 190, 40));

        LogoutButt.setBackground(new java.awt.Color(0, 0, 0));
        LogoutButt.setText("Logout");
        LogoutButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtActionPerformed(evt);
            }
        });
        jPanel3.add(LogoutButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 190, 40));

        jLabel2.setText("jLabel2");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, -1, -1));

        ViewStocksButt.setBackground(new java.awt.Color(0, 0, 0));
        ViewStocksButt.setText("View Stocks");
        ViewStocksButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewStocksButtActionPerformed(evt);
            }
        });
        jPanel3.add(ViewStocksButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 190, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtActionPerformed
        // TODO add your handling code here:
         int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to Log Out?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
       
    }
    }//GEN-LAST:event_LogoutButtActionPerformed

    private void NewTransactionButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewTransactionButtActionPerformed
        // TODO add your handling code here:
         int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to open New Transaction?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
        NewTransaction NewTransaction = new NewTransaction();
        NewTransaction.setVisible(true);
        dispose();
    }
    }//GEN-LAST:event_NewTransactionButtActionPerformed

    private void ViewTransactionHistoryButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewTransactionHistoryButtActionPerformed
        // TODO add your handling code here:
         int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to open View Transaction History?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
        InventoryReport InventoryReport = new InventoryReport();
        InventoryReport.setVisible(true);
        dispose();
    }
    }//GEN-LAST:event_ViewTransactionHistoryButtActionPerformed

    private void ViewStocksButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewStocksButtActionPerformed
        // TODO add your handling code here:
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to open View Stocks?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
        ViewStocks ViewStocks = new ViewStocks();
        ViewStocks.setVisible(true);
        dispose();
    }
    }//GEN-LAST:event_ViewStocksButtActionPerformed

    private void ViewAccountListButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewAccountListButtActionPerformed
        // TODO add your handling code here:
         int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to open View Stocks?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
        ViewStocks ViewStocks = new ViewStocks();
        ViewStocks.setVisible(true);
        dispose();
    }
    }//GEN-LAST:event_ViewAccountListButtActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogoutButt;
    private javax.swing.JButton NewTransactionButt;
    private javax.swing.JButton ViewAccountListButt;
    private javax.swing.JButton ViewStocksButt;
    private javax.swing.JButton ViewTransactionHistoryButt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
