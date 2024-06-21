package litskitchenware;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerNumberModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author MERYLL MEDINA
 */
public class NewTransaction extends javax.swing.JFrame {

    
    /**
     * Creates new form NewTransaction
     */
    public NewTransaction() {
        initComponents();
        populateProductComboBox();
        configureSpinner();
        setupEditableComboBox();
        startDateTimeUpdater();
        
        
        
         Payment.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent evt) {
            numericValidation(evt);
        }
    });
    }
    
    
    private void numericValidation(KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!Character.isDigit(c)) {
        evt.consume(); 
    }
    }
    
    private double totalPrice = 0.0;
    
    private void startDateTimeUpdater() {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    Timer timer = new Timer(1000, e -> {
        LocalDateTime now = LocalDateTime.now();
        DatePrint.setText(dateFormatter.format(now));
        TimePrint.setText(timeFormatter.format(now));
    });
    timer.start();
}

     
      public void generateReceipt(String[] items, double totalPrice, double payment, double change) {
        BufferedImage receiptImage = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = receiptImage.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 300, 400);
        g2d.setColor(Color.BLACK);

        int y = 20;
        g2d.drawString("Receipt", 100, y);
        y += 20;
        g2d.drawString("--------------------", 100, y);
        y += 20;
        for (String item : items) {
            g2d.drawString(item, 100, y);
            y += 20;
        }
        g2d.drawString("--------------------", 100, y);
        y += 20;
        g2d.drawString("Total: $" + totalPrice, 100, y);
        y += 20;
        g2d.drawString("Payment: $" + payment, 100, y);
        y += 20;
        g2d.drawString("Change: $" + change, 100, y);

        g2d.dispose();

        try {
            File output = new File("receipt.png");
            ImageIO.write(receiptImage, "png", output);
            System.out.println("Receipt saved to: " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     

     public Connection getConnection() {
    Connection con;
    try {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lits", "root", "");
        return con;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return null;
    }
}
     
     private void populateProductComboBox() {
    Connection con = getConnection();
    String sql = "SELECT PName FROM stock"; 
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Clear existing items in combo box
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) ProductComboBox.getModel();
        model.removeAllElements();

        // Populate combo box with product names
        while (rs.next()) {
            String productName = rs.getString("PName");
            model.addElement(productName);
        }

        ps.close();
        rs.close();
        con.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching products from database.");
    }
}

     
    private void setupEditableComboBox() {
    ProductComboBox.setEditable(true);
    JTextField editor = (JTextField) ProductComboBox.getEditor().getEditorComponent();

    editor.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            String input = editor.getText().toLowerCase();
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) ProductComboBox.getModel();
            model.removeAllElements();

            Connection con = getConnection();
            String sql = "SELECT PName FROM stock WHERE LOWER(PName) LIKE ?";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, "%" + input + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String productName = rs.getString("PName");
                    model.addElement(productName);
                }

                ps.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching products from database.");
            }

            // Show the dropdown
            if (model.getSize() > 0) {
                ProductComboBox.setPopupVisible(true);
            } else {
                ProductComboBox.setPopupVisible(false);
            }
        }
    });
}


     
     public double getProductCost(String productName) {
        Connection con = getConnection();
        String sql = "SELECT Cost FROM stock WHERE PName  = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, productName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double cost = rs.getDouble("Cost");
                ps.close();
                rs.close();
                con.close();
                return cost;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching product cost from database.");
        }
        return 0.0;
    }
     
     public int getAvailableStock(String productName) {
    Connection con = getConnection();
    String sql = "SELECT Quantity FROM stock WHERE PName = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, productName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int quantity = rs.getInt("Quantity");
            ps.close();
            rs.close();
            con.close();
            return quantity;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching product quantity from database.");
    }
    return 0;
}
     
     public void deductStock(String productName, int quantity) {
    Connection con = getConnection();
    String sql = "UPDATE stock SET Quantity = Quantity - ? WHERE PName = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, quantity);
        ps.setString(2, productName);
        ps.executeUpdate();
        ps.close();
        con.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating product quantity in database.");
    }
}
     
     private void generateReceiptImage(String receiptContent, double totalPrice, double payment, double change, String imagePath) {
    // Create a BufferedImage to hold the receipt
    BufferedImage receiptImage = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = receiptImage.createGraphics();

    // Draw receipt contents
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 300, 400);
    g2d.setColor(Color.BLACK);

    int y = 20;
    g2d.drawString("Welcome to Lit's Kitchen", 100, y);
    y += 20;
    g2d.drawString("--------------------", 100, y);
    y += 20;
    // Split receipt content into lines and draw
    String[] lines = receiptContent.split("\n");
    for (String line : lines) {
        g2d.drawString(line, 100, y);
        y += 20;
    }
    g2d.drawString("--------------------", 100, y);
    y += 20;
    g2d.drawString("Thank You", 100, y);
    y += 20;
    g2d.drawString("Please come Again", 100, y);
    y += 20;
    g2d.drawString("Contact us: 09876543212", 100, y);

    g2d.dispose();

    // Save the receipt image to the specified file path
    try {
        File output = new File(imagePath);
        ImageIO.write(receiptImage, "png", output);
        System.out.println("Receipt saved to: " + output.getAbsolutePath());
    } catch (IOException e) {
        e.printStackTrace();
    }
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
        DatePrint = new javax.swing.JLabel();
        TimePrint = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        AddButt = new javax.swing.JButton();
        NumberofItemButt = new javax.swing.JSpinner();
        ProductComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableItems = new javax.swing.JTable();
        ContinueButt = new javax.swing.JButton();
        TotalPrice = new javax.swing.JLabel();
        Payment = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 153, 51));
        jPanel4.setForeground(new java.awt.Color(255, 153, 51));

        DatePrint.setText("0");

        TimePrint.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(470, Short.MAX_VALUE)
                .addComponent(TimePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(DatePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DatePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TimePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 110));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51), 3), "Purchase Items", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14)))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AddButt.setBackground(new java.awt.Color(255, 153, 51));
        AddButt.setText("Add to Cart");
        AddButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtActionPerformed(evt);
            }
        });
        jPanel2.add(AddButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, -1));
        jPanel2.add(NumberofItemButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 200, -1));

        ProductComboBox.setEditable(true);
        ProductComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));
        ProductComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductComboBoxActionPerformed(evt);
            }
        });
        jPanel2.add(ProductComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 200, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setText("SELECT PRODUCT");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("NUMBER OF ITEMS");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 270, 210));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 51), 4, true), "List of Items", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        TableItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Number of Items", "Price ", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableItems);
        if (TableItems.getColumnModel().getColumnCount() > 0) {
            TableItems.getColumnModel().getColumn(0).setResizable(false);
            TableItems.getColumnModel().getColumn(1).setResizable(false);
            TableItems.getColumnModel().getColumn(2).setResizable(false);
            TableItems.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 520, 330));

        ContinueButt.setBackground(new java.awt.Color(255, 153, 51));
        ContinueButt.setText("Checkout");
        ContinueButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinueButtActionPerformed(evt);
            }
        });
        jPanel1.add(ContinueButt, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 360, -1, -1));

        TotalPrice.setText("Total:");
        jPanel1.add(TotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 360, 110, 40));

        Payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentActionPerformed(evt);
            }
        });
        jPanel1.add(Payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 120, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 920, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ProductComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductComboBoxActionPerformed

    private void AddButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtActionPerformed
      String productName = (String) ProductComboBox.getSelectedItem();
    double cost = getProductCost(productName);
    int numOfItems = (int) NumberofItemButt.getValue();

    if (numOfItems <= 0) {
        JOptionPane.showMessageDialog(this, "Please choose how many items.");
        return;
    }

    int availableStock = getAvailableStock(productName);

    if (numOfItems <= availableStock) {
        DefaultTableModel model = (DefaultTableModel) TableItems.getModel();
        boolean itemExists = false;

        // Check if the product already exists in the table
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(productName)) {
                int existingNumOfItems = (int) model.getValueAt(i, 1);
                int newNumOfItems = existingNumOfItems + numOfItems;
                double totalItemPrice = cost * newNumOfItems;

                model.setValueAt(newNumOfItems, i, 1);
                model.setValueAt(totalItemPrice, i, 3);
                
                // Update total price
                totalPrice += cost * numOfItems;
                TotalPrice.setText(String.format("Total: %.2f", totalPrice));
                
                itemExists = true;
                break;
            }
        }

        // If the product does not exist in the table, add a new row
        if (!itemExists) {
            double totalItemPrice = cost * numOfItems;
            model.addRow(new Object[]{productName, numOfItems, cost, totalItemPrice});
            
            // Update total price
            totalPrice += totalItemPrice;
            TotalPrice.setText(String.format("Total: %.2f", totalPrice));
        }

        // Reset number of items spinner
        NumberofItemButt.setValue(0);
    } else {
        JOptionPane.showMessageDialog(this, "Insufficient stock available.");
    }
    }//GEN-LAST:event_AddButtActionPerformed

    private void ContinueButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueButtActionPerformed
      double payment = 0.0;
    try {
        payment = Double.parseDouble(Payment.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid payment amount.");
        return;
    }

    // Check if payment is sufficient
    if (payment < totalPrice) {
        JOptionPane.showMessageDialog(this, "Insufficient payment amount.");
        return;
    }
    
    // Calculate change
    double change = payment - totalPrice;

    // Prepare receipt content
    StringBuilder receiptContent = new StringBuilder();
    DefaultTableModel model = (DefaultTableModel) TableItems.getModel();
    receiptContent.append("Receipt\n");
    receiptContent.append("--------------------\n");
    for (int i = 0; i < model.getRowCount(); i++) {
        String productName = model.getValueAt(i, 0).toString();
        int quantity = Integer.parseInt(model.getValueAt(i, 1).toString());
        double itemPrice = Double.parseDouble(model.getValueAt(i, 2).toString());
        receiptContent.append(productName).append(" x").append(quantity).append(" ₱").append(itemPrice).append("\n");
    }
    receiptContent.append("--------------------\n");
    receiptContent.append("Total: ₱").append(totalPrice).append("\n");
    receiptContent.append("Payment: ₱").append(payment).append("\n");
    receiptContent.append("Change: ₱").append(change).append("\n");

    
    int printOption = JOptionPane.showConfirmDialog(this, "Do you want to print the receipt?", "Print Receipt", JOptionPane.YES_NO_OPTION);
    
    // Specify where to save the receipt image (adjust path as needed)
    String imagePath = "C:\\Users\\Abren Trangia\\OneDrive\\Desktop\\receipt.png";

    if (printOption == JOptionPane.YES_OPTION) {
        // Generate receipt image
        generateReceiptImage(receiptContent.toString(), totalPrice, payment, change, imagePath);
    }
    
    
    // Display receipt content in a dialog
// Deduct stock from database (assuming you already have this logic)
    // for (int i = 0; i < model.getRowCount(); i++) {
    //     String productName = model.getValueAt(i, 0).toString();
    //     int quantity = Integer.parseInt(model.getValueAt(i, 1).toString());
    //     deductStock(productName, quantity);
    // }
    saveToDailySales(model);
    Payment.setText(""); // Clear payment field
    clearTable((DefaultTableModel) TableItems.getModel()); // Clear table
    TotalPrice.setText("Total:");

    // Reset total price
    totalPrice = 0.0;
    }//GEN-LAST:event_ContinueButtActionPerformed

    private void PaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PaymentActionPerformed

    /**
     * @param args the command line arguments
     */
    
     
     
   private void configureSpinner() {
    SpinnerNumberModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    NumberofItemButt.setModel(model);
}

   private void clearTable(DefaultTableModel model) {
    while (model.getRowCount() > 0) {
        model.removeRow(0);
    }
}
   
   private void saveToDailySales(DefaultTableModel model) {
    Connection con = getConnection();
    String insertSQL = "INSERT INTO dailysales (Code, PName, Amount, Quantity, Date) VALUES (?, ?, ?, ?, ?)";
    
    try {
        PreparedStatement ps = con.prepareStatement(insertSQL);

        for (int i = 0; i < model.getRowCount(); i++) {
            String productName = model.getValueAt(i, 0).toString();
            int quantity = Integer.parseInt(model.getValueAt(i, 1).toString());
            double itemPrice = Double.parseDouble(model.getValueAt(i, 3).toString());
            
            // Retrieve code (assuming it's available in stock table)
            String code = getCode(productName);

            // Set values to insert
            ps.setString(1, code);
            ps.setString(2, productName);
            ps.setDouble(3, itemPrice);
            ps.setInt(4, quantity);
            
            // Set current date
            LocalDateTime now = LocalDateTime.now();
            ps.setString(5, DatePrint.getText()); // Use DatePrint JLabel for date

            // Execute insert statement
            ps.executeUpdate();
        }

        ps.close();
        con.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving transaction to database.");
    }
}



private String getCode(String productName) {
    Connection con = getConnection();
    String sql = "SELECT Code FROM stock WHERE PName = ?";
    String code = ""; // Default code value
    
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, productName);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            code = rs.getString("Code");
        } else {
            JOptionPane.showMessageDialog(this, "Product code not found for " + productName);
        }
        
        rs.close();
        ps.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching product code from database.");
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    return code; // Return the retrieved code or default (empty string) if not found or error occurred
}

    
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
            java.util.logging.Logger.getLogger(NewTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewTransaction().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButt;
    private javax.swing.JButton ContinueButt;
    private javax.swing.JLabel DatePrint;
    private javax.swing.JSpinner NumberofItemButt;
    private javax.swing.JTextField Payment;
    private javax.swing.JComboBox<String> ProductComboBox;
    private javax.swing.JTable TableItems;
    private javax.swing.JLabel TimePrint;
    private javax.swing.JLabel TotalPrice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
