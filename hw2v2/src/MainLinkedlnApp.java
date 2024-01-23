//--------------------------------------------------------------------------------------------
// MainLinkedlnApp.java Author: KAAN YILMAZ ID: 22097937
// The MainLinkedlnApp class serves as the main entry point for the LinkedIn Platform application.
// It initializes and displays the login screen upon application startup. After a successful login,
// it opens the main window, which consists of various tabs for user profiles, job and career information,
// search and filtering functionality, and job advertisements.Write the purpose of this class
//--------------------------------------------------------------------------------------------

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MainLinkedlnApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and display the login screen
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }

    // Method to open the main window after successful login
    public static void openMainWindow() {
        JFrame frame = new JFrame("LinkedIn Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sample user profiles
        UserProfile userProfile1 = new UserProfile("User1", "User1", "java, C++");
        UserProfile userProfile2 = new UserProfile("User2", "User2", "java");
        UserProfile userProfile3 = new UserProfile("User3", "User3", "C++, REACT");
        UserProfile userProfile4 = new UserProfile("User4", "User4", "JAVA, C++");
        UserProfile userProfile5 = new UserProfile("User5", "User5", "java, C++");

        UserProfilePanel userProfilePanel = new UserProfilePanel(userProfile1);
        JobAndCareerPanel jobAndCareerPanel = new JobAndCareerPanel(userProfile1);

        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfile1);
        userProfiles.add(userProfile2);
        userProfiles.add(userProfile3);
        userProfiles.add(userProfile4);
        userProfiles.add(userProfile5);
        // Sample job adverts
        List<JobAdvertPanel.JobAdvert> jobAdverts = new ArrayList<>();
        jobAdverts.add(new JobAdvertPanel.JobAdvert("Software Engineer", "Join our team as a software engineer.", "Tech Company", "City A"));
        jobAdverts.add(new JobAdvertPanel.JobAdvert("Data Analyst", "Exciting opportunity for a data analyst.", "Data Company", "City B"));

        SearchAndFilterPanel searchAndFilterPanel = new SearchAndFilterPanel(userProfiles, jobAdverts);
        JobAdvertPanel jobAdvertPanel = new JobAdvertPanel(jobAdverts, jobAndCareerPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("User Profile", userProfilePanel);
        tabbedPane.addTab("Job and Career", jobAndCareerPanel);
        tabbedPane.addTab("Search and Filter", searchAndFilterPanel);
        tabbedPane.addTab("Job Adverts", jobAdvertPanel);

        frame.getContentPane().add(tabbedPane);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
