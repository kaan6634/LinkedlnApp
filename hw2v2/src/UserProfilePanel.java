//--------------------------------------------------------------------------------------------
// UserProfilePanel.java Author: KAAN YILMAZ ID: 22097937
// This class encapsulates the graphical representation and interaction with the user's profile.
// It provides an intuitive and user-friendly interface for viewing and modifying key aspects
// of the user's professional information on the LinkedIn Platform.
//--------------------------------------------------------------------------------------------

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class UserProfilePanel extends JPanel {
    private UserProfile userProfile;
    private JLabel profilePhotoLabel;
    private JLabel fullNameLabel;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel skillsLabel;
    private JTextField skillsTextField;
    private JLabel editFullNameLabel;
    private JTextField editFullNameTextField;
    private JButton uploadPhotoButton;
    private JButton editProfileButton;
    private JButton saveProfileButton;

    public UserProfilePanel(UserProfile userProfile) {
        this.userProfile = userProfile;

        // Set BoxLayout along Y-axis for the panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create a JLabel for displaying the profile photo in a round shape
        profilePhotoLabel = new JLabel();
        updateProfilePhoto();
        profilePhotoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fullNameLabel = new JLabel("Full Name: " + userProfile.getFullName());
        fullNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels and TextFields for username and skills
        usernameLabel = new JLabel("Username: ");
        usernameTextField = new JTextField(userProfile.getUsername());
        usernameTextField.setEditable(false);

        skillsLabel = new JLabel("Skills: ");
        skillsTextField = new JTextField(String.join(", ", userProfile.getSkills()));
        skillsTextField.setEditable(false);

        // New components for editing the full name
        editFullNameLabel = new JLabel("Full Name: ");
        editFullNameTextField = new JTextField(userProfile.getFullName());
        editFullNameTextField.setEditable(false);

        // Button to upload a photo
        uploadPhotoButton = new JButton("Upload Photo");
        uploadPhotoButton.setVisible(false);
        uploadPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser for photo selection
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(UserProfilePanel.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    userProfile.uploadProfilePhoto(selectedFilePath);
                    updateProfilePhoto(); // Update the displayed photo
                }
            }
        });

        // Button to edit profile
        editProfileButton = new JButton("Edit Profile");
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEditProfile();
            }
        });

        // Button to save changes
        saveProfileButton = new JButton("Save Changes");
        saveProfileButton.setVisible(false);
        saveProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveChanges();
            }
        });
        // Add components to the panel
        add(profilePhotoLabel);
        add(editFullNameLabel);
        add(editFullNameTextField);
        add(usernameLabel);
        add(usernameTextField);
        add(skillsLabel);
        add(skillsTextField);
        add(uploadPhotoButton);
        add(editProfileButton);
        add(saveProfileButton);

        // Add KeyListener to editFullNameTextField to detect Enter key and trigger save
        editFullNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSaveChanges();
                }
            }
        });
    }

    // Method to update the displayed profile photo
    private void updateProfilePhoto() {
        String photoPath = userProfile.getProfilePhotoPath();

        // If the user has a custom profile photo, load and display it
        if (photoPath != null) {
            ImageIcon imageIcon = new ImageIcon(photoPath);
            Image originalImage = imageIcon.getImage();
            ImageIcon circularIcon = getCircularImage(originalImage, 250);
            int targetWidth = 250;
            int targetHeight = 250;

            Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

            profilePhotoLabel.setIcon(circularIcon);
        } else {
            // If there's no custom profile photo, display a default photo
            ImageIcon defaultIcon = new ImageIcon("default.png");
            ImageIcon circularIcon = getCircularImage(defaultIcon.getImage(), 250);
            profilePhotoLabel.setIcon(circularIcon);
        }
    }

    // Method to create a circular image
    private ImageIcon getCircularImage(Image image, int diameter) {
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = circularImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Ellipse2D.Double ellipse = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2.setClip(ellipse);

        g2.drawImage(image, 0, 0, diameter, diameter, null);

        g2.dispose();

        // Create a new ImageIcon from the circular image
        return new ImageIcon(circularImage);
    }

    // Method to handle the profile editing
    private void handleEditProfile() {
        fullNameLabel.setVisible(false);
        usernameLabel.setVisible(true);
        usernameTextField.setEditable(true);
        skillsLabel.setVisible(true);
        skillsTextField.setEditable(true);
        editFullNameLabel.setVisible(true);
        editFullNameTextField.setEditable(true);
        uploadPhotoButton.setVisible(true);
        editProfileButton.setVisible(false);
        saveProfileButton.setVisible(true);
    }

    // Method to save changes
    private void handleSaveChanges() {
        fullNameLabel.setText(editFullNameTextField.getText());
        fullNameLabel.setVisible(true);
        usernameLabel.setVisible(false);
        usernameTextField.setEditable(false);
        skillsLabel.setVisible(false);
        skillsTextField.setEditable(false);
        editFullNameLabel.setVisible(false);
        editFullNameTextField.setEditable(false);
        uploadPhotoButton.setVisible(false);
        editProfileButton.setVisible(true);
        saveProfileButton.setVisible(false);

        // Save changes to the user profile
        userProfile.setUsername(usernameTextField.getText());
        userProfile.setSkills(List.of(skillsTextField.getText().split(",\\s*")));
        userProfile.setFullName(editFullNameTextField.getText());
    }
}