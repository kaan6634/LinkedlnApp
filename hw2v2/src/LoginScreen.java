//--------------------------------------------------------------------------------------------
// LoginScreen.java Author: KAAN YILMAZ ID: 22097937
// The LoginScreen class is a graphical user interface (GUI) for user authentication.
// It provides a window with fields for entering a username and password,
// along with a checkbox to show or hide the entered password. The user can log in by pressing
// the "Login" button, which triggers authentication logic.
//--------------------------------------------------------------------------------------------

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.nio.charset.StandardCharsets;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;

    public LoginScreen() {
        setTitle("Login");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        addEnterKeyListener(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        addEnterKeyListener(passwordField);

        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordField.setEchoChar((char) 0); // Show password
                } else {
                    passwordField.setEchoChar('*'); // Hide password
                }
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Placeholder for spacing
        panel.add(showPasswordCheckbox);
        panel.add(new JLabel()); // Placeholder for spacing
        panel.add(loginButton);

        add(panel);
    }

    private void addEnterKeyListener(JComponent component) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
    }

    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String hashedPassword = hashPassword(new String(password));
        // For demonstration purposes print the hashed password.
        System.out.println("Hashed Password: " + hashedPassword);
        
        if (authenticate(username, hashedPassword)) {
            MainLinkedlnApp.openMainWindow();
            dispose();
        } else {
            JOptionPane.showMessageDialog(LoginScreen.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean authenticate(String username, String hashedPassword) {
        String storedHashedPassword = hashPassword("qw123");
        return "kaan".equals(username) && storedHashedPassword.equals(hashedPassword);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
