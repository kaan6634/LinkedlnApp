//--------------------------------------------------------------------------------------------
// JobAndCareerPanel.java Author: KAAN YILMAZ ID: 22097937
// The JobAndCareerPanel class is a graphical user interface component that displays a table
// of jobs the user has applied for, showing job titles, start dates, and end dates.
// It allows users to view and manage their applied jobs in a visually organized manner.
//--------------------------------------------------------------------------------------------

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JobAndCareerPanel extends JPanel {
    private final UserProfile userProfile;
    private final JTable appliedJobsTable;
    private JScrollPane appliedJobsScrollPane;

    public JobAndCareerPanel(UserProfile userProfile) {
        this.userProfile = userProfile;

        setLayout(new BorderLayout());

        // Create a table model with columns: Job Title, Start Date, End Date
        String[] columnNames = {"Job Title", "Start Date", "End Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        appliedJobsTable = new JTable(tableModel);

        appliedJobsScrollPane = new JScrollPane(appliedJobsTable);

        JPanel appliedJobsPanel = new JPanel(new BorderLayout());

        appliedJobsPanel.add(new JLabel("Applied Jobs: "), BorderLayout.NORTH);
        appliedJobsPanel.add(appliedJobsScrollPane, BorderLayout.CENTER);

        add(appliedJobsPanel, BorderLayout.CENTER);
    }

    // Method to update the applied jobs in the table with the current date
    public void updateAppliedJobs(String selectedJobTitle) {
        // Check if the user has already applied for a job
        if (hasAppliedJob()) {
            // Get the index of the last applied job
            int lastIndex = appliedJobsTable.getRowCount() - 1;

            // Update the end date of the last applied job with the current date
            DefaultTableModel tableModel = (DefaultTableModel) appliedJobsTable.getModel();
            tableModel.setValueAt(getCurrentDate(), lastIndex, 2);
        }

        // Add the selected job title, start date, and "Working" status to the table
        DefaultTableModel tableModel = (DefaultTableModel) appliedJobsTable.getModel();
        Object[] rowData = {selectedJobTitle, getCurrentDate(), "Working"};
        tableModel.addRow(rowData);

        // Add the selected job title to the user's applied jobs
        userProfile.applyForJob(selectedJobTitle, getCurrentDate());
    }

    // Helper method to get the current date as a formatted string
    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    // Method to check if the user has applied for a job
    public boolean hasAppliedJob() {
        return appliedJobsTable.getRowCount() > 0;
    }
}
