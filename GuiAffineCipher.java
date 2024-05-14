
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.BitSet;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GuiAffineCipher extends javax.swing.JFrame {

    private int length = 0;
//creates a matrix for Playfair cipher   
    private String[][] table;
//main() method to test Playfair method  

    public GuiAffineCipher() {
        initComponents();
        encLabel.setText("");
        decLabel.setText("");
        encLabel2.setText("");
        decLabel2.setText("");
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);

        encButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String plainText = plainFiled.getText().toString();
                    if (plainText.isEmpty()) {
                        alert("Enter plain text");
                        return;
                    }

                    String key1_ = keyFiled1.getText().toString();
                    if (key1_.isEmpty()) {
                        alert("Enter key 1");
                        return;
                    }
                    int key1 = Integer.parseInt(key1_);
                    if (GCD(key1, 26) != 1) {
                        alert("Key 1 (a) should be coprime with 26");
                        return;
                    }

                    String key2_ = keyFiled2.getText().toString();
                    if (key2_.isEmpty()) {
                        alert("Enter key 2");
                        return;
                    }
                    int key2 = Integer.parseInt(key2_);
                    String encrypted = encryption(plainText, key1, key2);
                    String decrypted = decryption(encrypted, key1, key2);
                    encLabel2.setText("Encrypted Message: ");
                    encLabel.setText(encrypted);
                    decLabel2.setText("Decrypted Message: ");
                    decLabel.setText(decrypted);

                } catch (NumberFormatException ex) {
                    alert("Enter key as number");

                }
            }
        });

    }

    public static String encryption(String plaintext, int key1, int key2) {
        plaintext = plaintext.toUpperCase();
        String encrypted = "";
        int a = key1;
        int b = key2;
        for (int i = 0; i < plaintext.length(); i++) {
            encrypted = encrypted + (char) ((((a * plaintext.charAt(i)) + b) % 26) + 65);
        }
        return encrypted;
    }

    public static String decryption(String encrypted, int key1, int key2) {
        String decrypted = "";
        int a = key1;
        int b = key2;
        int a_inv = 0;
        int flag = 0;
        for (int i = 0; i < 26; i++) {
            flag = (a * i) % 26;
            if (flag == 1) {
                a_inv = i;
            }
        }
        for (int i = 0; i < encrypted.length(); i++) {
            decrypted = decrypted + (char) (((a_inv * ((encrypted.charAt(i) - b)) % 26)) + 65);
        }
        return decrypted;
    }

    public static int GCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return GCD(b, a % b);
    }

    private void alert(String message) {
        JOptionPane.showMessageDialog(rootPane, message, "Playfair Cipher", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel00 = new javax.swing.JLabel();
        keyFiled1 = new javax.swing.JTextField();
        encButton = new javax.swing.JButton();
        label_2 = new javax.swing.JLabel();
        label_3 = new javax.swing.JLabel();
        plainFiled = new javax.swing.JTextField();
        encLabel2 = new javax.swing.JLabel();
        encLabel = new javax.swing.JLabel();
        decLabel2 = new javax.swing.JLabel();
        decLabel = new javax.swing.JLabel();
        keyFiled2 = new javax.swing.JTextField();
        label_4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel00.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel00.setForeground(new java.awt.Color(51, 153, 255));
        jLabel00.setText("Affine Cipher");

        keyFiled1.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        keyFiled1.setForeground(new java.awt.Color(51, 153, 255));
        keyFiled1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        encButton.setBackground(new java.awt.Color(51, 153, 255));
        encButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        encButton.setForeground(new java.awt.Color(255, 255, 255));
        encButton.setText("Encrypt");
        encButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encButtonActionPerformed(evt);
            }
        });

        label_2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        label_2.setForeground(new java.awt.Color(0, 51, 51));
        label_2.setText("Enter key 1 (a) :");

        label_3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        label_3.setForeground(new java.awt.Color(0, 51, 51));
        label_3.setText("Enter the plaintext :");

        plainFiled.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        plainFiled.setForeground(new java.awt.Color(51, 153, 255));
        plainFiled.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        encLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        encLabel2.setText("Encrypted Message: ");

        encLabel.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        encLabel.setForeground(new java.awt.Color(51, 153, 255));
        encLabel.setText("encrypted");

        decLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        decLabel2.setText("Decrypted Message: ");

        decLabel.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        decLabel.setForeground(new java.awt.Color(51, 153, 255));
        decLabel.setText("decrypt");

        keyFiled2.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        keyFiled2.setForeground(new java.awt.Color(51, 153, 255));
        keyFiled2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        label_4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        label_4.setForeground(new java.awt.Color(0, 51, 51));
        label_4.setText("Enter key 2 (b) :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(321, Short.MAX_VALUE)
                .addComponent(jLabel00)
                .addGap(321, 321, 321))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_3)
                    .addComponent(label_2)
                    .addComponent(label_4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(keyFiled2, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addComponent(plainFiled, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(keyFiled1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(encButton, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(decLabel2)
                            .addComponent(encLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(309, 309, 309)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(decLabel)
                                    .addComponent(encLabel))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel00)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_3)
                    .addComponent(plainFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_2)
                    .addComponent(keyFiled1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_4)
                    .addComponent(keyFiled2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(encButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(encLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(encLabel)
                .addGap(34, 34, 34)
                .addComponent(decLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decLabel)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_encButtonActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiAffineCipher().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel decLabel;
    private javax.swing.JLabel decLabel2;
    private javax.swing.JButton encButton;
    private javax.swing.JLabel encLabel;
    private javax.swing.JLabel encLabel2;
    private javax.swing.JLabel jLabel00;
    private javax.swing.JTextField keyFiled1;
    private javax.swing.JTextField keyFiled2;
    private javax.swing.JLabel label_2;
    private javax.swing.JLabel label_3;
    private javax.swing.JLabel label_4;
    private javax.swing.JTextField plainFiled;
    // End of variables declaration//GEN-END:variables
}
