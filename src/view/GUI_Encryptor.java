package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import algorithm.DES;
import javax.swing.*;

/**
 * This class is the Encryptor interface implemented by JFrame.
 * It will be called in Demo main class by the initUI() function.
 *
 * @author Wang Hewei
 */

public class GUI_Encryptor extends JFrame {

    JPanel panel = new JPanel(null);

    void initUI(){
        this.setTitle("Encryptor - Hewei Wang");
        this.setVisible(true);
        this.setBounds(30, 30, 472, 700);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel.setBounds(0, 0, 500, 800);
        Color backGroundColor = new Color(176, 200, 255);
        panel.setBackground(backGroundColor);

        this.setLayout(null);


        // three textarea in this interface
        TextArea keyTextArea = new TextArea();
        keyTextArea.setBounds(30,65,410,100);

        TextArea plainTextArea = new TextArea();
        plainTextArea.setBounds(30,225,410,150);

        TextArea cipherTextArea = new TextArea();
        cipherTextArea.setBounds(30,438,410,150);


        // three label and the read file button in this interface
        JLabel keyLabel = new JLabel();
        keyLabel.setBounds(30,30,500, 30);
        keyLabel.setText("Key");

        JLabel plainTextLabel = new JLabel();
        plainTextLabel.setBounds(30,190,70, 30);
        plainTextLabel.setText("Plaintext");

        JButton readFileBtn = new JButton("read from txt file");
        readFileBtn.setBounds(317,190,126,34);
        readFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    File directory = fileChooser.getCurrentDirectory();
                    int option = fileChooser.showOpenDialog(panel);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        String fileName = fileChooser.getSelectedFile().getName();
                        File file = new File(directory, fileName);
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        plainTextArea.setText("");
                        String content = null;
                        while ((content = br.readLine()) != null) {
                            plainTextArea.append(content + "\n");
                        }
                        br.close();
                        fr.close();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JLabel cipherLabel = new JLabel();
        cipherLabel.setBounds(30,400,500, 30);
        cipherLabel.setText("Cipher");


        // encryption button and its listener
        JButton encryptionBtn = new JButton("Encryption");
        encryptionBtn.setBounds(150,595,170,50);
        encryptionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyTextArea.getText();
                String plainText = plainTextArea.getText();
                DES des = new DES(key);
                // transfer the plaintext into the byte array
                byte[] dataArray = plainText.getBytes(Charset.forName("UTF-8"));
                // call the function DESencrypt() to encrypt，encryption code is 1
                byte[] result = des.DESencrypt(dataArray, 1);
                // transfer the encoded byte to string as cipher
                String encodedText = Base64.encodeBase64String(result);
                cipherTextArea.setText(encodedText);
            }
        });


        panel.add(keyLabel);
        panel.add(plainTextLabel);
        panel.add(cipherLabel);
        panel.add(keyTextArea);
        panel.add(plainTextArea);
        panel.add(cipherTextArea);
        panel.add(encryptionBtn);
        panel.add(readFileBtn);
        this.add(panel);
        this.setVisible(true);
    }
}