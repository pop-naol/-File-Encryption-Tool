//Naol Sime-naolsim89@gmail.com

// This project is a Java-based application that allows users to encrypt and decrypt files securely using the AES (Advanced Encryption Standard) algorithm.
//  It provides a user-friendly GUI built with Java Swing, making it easy for users to select files, encrypt them, and decrypt them when needed.
//  The encrypted files are saved with a .enc extension, and decrypted files are saved with a .dec extension.

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class EncryptionTol {
    private static SecretKey secretKey;

    public static void main(String[] args) {
        // Generate a secret key (for demonstration purposes)
        try {
            secretKey = generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the GUI
        JFrame frame = new JFrame("File Encryption Tool");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // File selection
        JLabel fileLabel = new JLabel("Select File:");
        fileLabel.setBounds(10, 20, 80, 25);
        panel.add(fileLabel);

        JTextField fileText = new JTextField(20);
        fileText.setBounds(100, 20, 165, 25);
        panel.add(fileText);

        JButton browseButton = new JButton("Browse");
        browseButton.setBounds(270, 20, 80, 25);
        panel.add(browseButton);

        // Encryption/Decryption buttons
        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(10, 80, 80, 25);
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(180, 80, 80, 25);
        panel.add(decryptButton);

        // Action listeners
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    fileText.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = fileText.getText();
                if (!filePath.isEmpty()) {
                    try {
                        encryptFile(filePath, filePath + ".enc");
                        JOptionPane.showMessageDialog(null, "File Encrypted Successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a file.");
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = fileText.getText();
                if (!filePath.isEmpty() && filePath.endsWith(".enc")) {
                    try {
                        decryptFile(filePath, filePath.replace(".enc", ".dec"));
                        JOptionPane.showMessageDialog(null, "File Decrypted Successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an encrypted file.");
                }
            }
        });
    }

    // Generate a secret key for AES encryption
    private static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // 128-bit key
        return keyGen.generateKey();
    }

    // Encrypt a file
    private static void encryptFile(String inputFile, String outputFile) throws Exception {
        byte[] fileBytes = Files.readAllBytes(Paths.get(inputFile));
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(fileBytes);
        Files.write(Paths.get(outputFile), encryptedBytes);
    }

    // Decrypt a file
    private static void decryptFile(String inputFile, String outputFile) throws Exception {
        byte[] fileBytes = Files.readAllBytes(Paths.get(inputFile));
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(fileBytes);
        Files.write(Paths.get(outputFile), decryptedBytes);
    }
}