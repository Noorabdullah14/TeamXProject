
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

public class GuiPlayfairCipher extends javax.swing.JFrame {
    
       private int length = 0;
//creates a matrix for Playfair cipher   
    private String[][] table;
//main() method to test Playfair method  
    
    public GuiPlayfairCipher() {
        initComponents();
        matrixLabel.setText("");
        encLabel.setText("");
        decLabel.setText("");
        matrixLabel2.setText("");
        encLabel2.setText("");
        decLabel2.setText("");
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);


        encButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                    String key = keyFiled.getText().toString();
                    if (key.isEmpty()) {
                        alert("Enter key");
                        return;
                    }
                 key= parseString(key);
                 table = cipherTable(key);
                 
                  String plainText = plainFiled.getText().toString();
                    if (plainText.isEmpty()) {
                        alert("Enter plain text");
                        return;
                    }
                  plainText= parseString(plainText);
                  //encodes and then decodes the encoded message  
                  String output = cipher(plainText);
                  String decodedOutput = decode(output);
                  matrixLabel2.setText("Playfair Cipher Key Matrix:");
                  keyTable(table,matrixLabel);
                  encLabel2.setText("Encrypted Message: ");
                  encLabel.setText(output);
                  decLabel2.setText("Decrypted Message: ");
                  decLabel.setText(decodedOutput);
             
            }
        });
        
    }
    
    //parses an input string to remove numbers, punctuation,  
//replaces any J's with I's and makes string all caps  

    private String parseString(String st) {
        String parse = st;
//converts all the letters in upper case  
        parse = parse.toUpperCase();
//the string to be substituted by space for each match (A to Z)  
        parse = parse.replaceAll("[^A-Z]", "");
//replace the letter J by I  
        parse = parse.replace("J", "I");
        return parse;
    }
//creates the cipher table based on some input string (already parsed)  

    private String[][] cipherTable(String key) {
//creates a matrix of 5*5     
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
//fill string array with empty string  
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                playfairTable[i][j] = "";
            }
        }
        for (int k = 0; k < keyString.length(); k++) {
            boolean repeat = false;
            boolean used = false;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playfairTable[i][j].equals("" + keyString.charAt(k))) {
                        repeat = true;
                    } else if (playfairTable[i][j].equals("") && !repeat && !used) {
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }
//cipher: takes input (all upper-case), encodes it, and returns the output  

    private String cipher(String in) {
        length = (int) in.length() / 2 + in.length() % 2;
//insert x between double-letter digraphs & redefines "length"  

        for (int i = 0; i < (length - 1); i++) {
            if (in.charAt(2 * i) == in.charAt(2 * i + 1)) {
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = (int) in.length() / 2 + in.length() % 2;
            }
        }
//------------makes plaintext of even length--------------  
//creates an array of digraphs  
        String[] digraph = new String[length];
//loop iterates over the plaintext  
        for (int j = 0; j < length; j++) {
//checks the plaintext is of even length or not   
            if (j == (length - 1) && in.length() / 2 == (length - 1)) //if not addends X at the end of the plaintext    
            {
                in = in + "X";
            }
            digraph[j] = in.charAt(2 * j) + "" + in.charAt(2 * j + 1);
        }
//encodes the digraphs and returns the output  
        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for (int k = 0; k < length; k++) {
            out = out + encDigraphs[k];
        }
        return out;
    }
//---------------encryption logic-----------------  
//encodes the digraph input with the cipher's specifications  

    private String[] encodeDigraph(String di[]) {
        String[] encipher = new String[length];
        for (int i = 0; i < length; i++) {
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
//executes if the letters of digraph appear in the same row  
//in such case shift columns to right  
            if (r1 == r2) {
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            } //executes if the letters of digraph appear in the same column  
            //in such case shift rows down  
            else if (c1 == c2) {
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            } //executes if the letters of digraph appear in the different row and different column  
            //in such case swap the first column with the second column  
            else {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
//performs the table look-up and puts those values into the encoded array  
            encipher[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return encipher;
    }
//-----------------------decryption logic---------------------  
// decodes the output given from the cipher and decode methods (opp. of encoding process)  

    private String decode(String out) {
        String decoded = "";
        for (int i = 0; i < out.length() / 2; i++) {
            char a = out.charAt(2 * i);
            char b = out.charAt(2 * i + 1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if (r1 == r2) {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            } else if (c1 == c2) {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            } else {
//swapping logic      
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded = decoded + table[r1][c1] + table[r2][c2];
        }
//returns the decoded message  
        return decoded;
    }
// returns a point containing the row and column of the letter  

    private Point getPoint(char c) {
        Point pt = new Point(0, 0);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (c == table[i][j].charAt(0)) {
                    pt = new Point(i, j);
                }
            }
        }
        return pt;
    }
//function prints the key-table in matrix form for playfair cipher  

    private void keyTable(String[][] printTable,JLabel matrixLabel) {
matrixLabel.setText("<html><body>");
//loop iterates for rows  
        for (int i = 0; i < 5; i++) {
//loop iterates for column    
            for (int j = 0; j < 5; j++) {
//prints the key-table in matrix form 
matrixLabel.setText(matrixLabel.getText()+printTable[i][j] + " ");
            }
           matrixLabel.setText(matrixLabel.getText()+"<br>");
        }
      matrixLabel.setText(matrixLabel.getText()+"<br></body></html>");
    }
    
    private void alert(String message) {
        JOptionPane.showMessageDialog(rootPane, message, "Playfair Cipher", JOptionPane.INFORMATION_MESSAGE);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel00 = new javax.swing.JLabel();
        keyFiled = new javax.swing.JTextField();
        encButton = new javax.swing.JButton();
        label_2 = new javax.swing.JLabel();
        label_3 = new javax.swing.JLabel();
        plainFiled = new javax.swing.JTextField();
        encLabel2 = new javax.swing.JLabel();
        encLabel = new javax.swing.JLabel();
        matrixLabel2 = new javax.swing.JLabel();
        matrixLabel = new javax.swing.JLabel();
        decLabel2 = new javax.swing.JLabel();
        decLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel00.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel00.setForeground(new java.awt.Color(51, 153, 255));
        jLabel00.setText("Playfair Cipher");

        keyFiled.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        keyFiled.setForeground(new java.awt.Color(51, 153, 255));
        keyFiled.setHorizontalAlignment(javax.swing.JTextField.CENTER);

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
        label_2.setText("Enter the key for playfair cipher: ");

        label_3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        label_3.setForeground(new java.awt.Color(0, 51, 51));
        label_3.setText("Enter the plaintext to be encipher: ");

        plainFiled.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        plainFiled.setForeground(new java.awt.Color(51, 153, 255));
        plainFiled.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        encLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        encLabel2.setText("Encrypted Message: ");

        encLabel.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        encLabel.setForeground(new java.awt.Color(51, 153, 255));
        encLabel.setText("encrypted");

        matrixLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        matrixLabel2.setText("Playfair Cipher Key Matrix:");

        matrixLabel.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        matrixLabel.setForeground(new java.awt.Color(51, 153, 255));
        matrixLabel.setText("matrix");

        decLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        decLabel2.setText("Decrypted Message: ");

        decLabel.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        decLabel.setForeground(new java.awt.Color(51, 153, 255));
        decLabel.setText("decrypt");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(298, Short.MAX_VALUE)
                .addComponent(jLabel00)
                .addGap(321, 321, 321))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_3)
                    .addComponent(label_2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(keyFiled)
                            .addComponent(plainFiled))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(encButton, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(matrixLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decLabel2)
                    .addComponent(encLabel2)
                    .addComponent(matrixLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(309, 309, 309)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(decLabel)
                            .addComponent(encLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel00)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_2)
                    .addComponent(keyFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_3)
                    .addComponent(plainFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(encButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(matrixLabel2)
                .addGap(6, 6, 6)
                .addComponent(matrixLabel)
                .addGap(34, 34, 34)
                .addComponent(encLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(encLabel)
                .addGap(34, 34, 34)
                .addComponent(decLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decLabel)
                .addContainerGap(174, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_encButtonActionPerformed
    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiPlayfairCipher().setVisible(true);
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
    private javax.swing.JTextField keyFiled;
    private javax.swing.JLabel label_2;
    private javax.swing.JLabel label_3;
    private javax.swing.JLabel matrixLabel;
    private javax.swing.JLabel matrixLabel2;
    private javax.swing.JTextField plainFiled;
    // End of variables declaration//GEN-END:variables
}
