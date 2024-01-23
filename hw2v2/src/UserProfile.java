//--------------------------------------------------------------------------------------------
// UserProfile.java Author: KAAN YILMAZ ID: 22097937
// This class encapsulates the functionality required to manage user profiles,
// including their professional information, applied jobs, and the ability to upload a profile photo.
// Additionally, it provides methods to interact with job application-related data, such as applying
// for jobs and updating job end dates.
//--------------------------------------------------------------------------------------------

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Uploadable {
    void upload(String filePath);
}

public class UserProfile implements Uploadable {
    public abstract class test{
        private UserProfile userProfile;
        private JLabel profilePhotoLabel;
        private JLabel fullNameLabel;
        private JLabel usernameLabel;
        private JTextField usernameTextField;
        private JLabel skillsLabel;
    }

    private String username;
    private String fullName;
    private String bio;
    private String profilePhotoPath;
    private List<String> skills;
    private final List<String> appliedJobs;
    private final Map<String, JobDates> jobDates;  // Use a class to store both start and end dates

    public UserProfile(String username, String fullName, String skills) {
        this.username = username;
        this.fullName = fullName;
        this.skills = new ArrayList<>(List.of(skills.split(",\\s*")));
        this.appliedJobs = new ArrayList<>();
        this.jobDates = new HashMap<>();
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    public void setSkills(List<String> skills) {
        this.skills = new ArrayList<>(skills);
    }

    public void setUsername(String text) {
        this.username = text;
    }

    public void setFullName(String text) {
        this.fullName = text;
    }

    public void uploadProfilePhoto(String filePath) {
        try {
            Path sourcePath = Paths.get(filePath);
            String fileName = username + "_profile_photo_" + System.currentTimeMillis() + ".jpg";
            Path destinationPath = Paths.get("uploads", fileName);
            Files.createDirectories(destinationPath.getParent());
            Files.copy(sourcePath, destinationPath);
            this.profilePhotoPath = destinationPath.toString();
            System.out.println("Profile photo uploaded successfully. Path: " + this.profilePhotoPath);
        } catch (IOException e) {
            System.err.println("Error uploading profile photo: " + e.getMessage());
        }
    }

    public void applyForJob(String jobTitle, String startDate) {
        appliedJobs.add(jobTitle);
        jobDates.put(jobTitle, new JobDates(startDate, null));
    }

    public void setJobEndDate(String jobTitle, String endDate) {
        JobDates dates = jobDates.get(jobTitle);
        if (dates != null) {
            dates.setEndDate(endDate);
        }
    }

    public String getJobStartDate(String jobTitle) {
        JobDates dates = jobDates.get(jobTitle);
        return dates != null ? dates.getStartDate() : null;
    }

    public String getJobEndDate(String jobTitle) {
        JobDates dates = jobDates.get(jobTitle);
        return dates != null ? dates.getEndDate() : null;
    }

    @Override
    public void upload(String filePath) {
        System.out.println("Uploading: " + filePath);
    }

    // Class to store both start and end dates
    private static class JobDates {
        private String startDate;
        private String endDate;

        public JobDates(String startDate, String endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}
