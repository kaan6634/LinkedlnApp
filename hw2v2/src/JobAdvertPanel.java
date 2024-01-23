//--------------------------------------------------------------------------------------------
// JobAdvertPanel.java Author: KAAN YILMAZ ID: 22097937
// Manages the display and interaction with a panel that presents job advertisements.
//--------------------------------------------------------------------------------------------

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JobAdvertPanel extends JPanel {
    private JTable jobAdvertsTable;
    private List<JobAdvert> jobAdverts;
    private JTextField searchJobAdvertsTextField;
    private JobAndCareerPanel jobAndCareerPanel;
    public int appliedJobCounter = 0;

    // Inner class JobAdvert
    public static class JobAdvert {
        private final String title;
        private final String description;
        private final String company;
        private final String location;

        public JobAdvert(String title, String description, String company, String location) {
            this.title = title;
            this.description = description;
            this.company = company;
            this.location = location;
        }

        // Getter methods for new fields
        public String getCompany() {
            return company;
        }

        public String getLocation() {
            return location;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

    public JobAdvertPanel(List<JobAdvert> jobAdverts, JobAndCareerPanel jobAndCareerPanel) {
        this.jobAdverts = jobAdverts;
        this.jobAndCareerPanel = jobAndCareerPanel;

        setLayout(new BorderLayout());

        // Create a table model with columns: Title, Description, Company, Location
        String[] columnNames = {"Title", "Description", "Company", "Location"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        jobAdvertsTable = new JTable(tableModel);

        // Enable sorting for the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        jobAdvertsTable.setRowSorter(sorter);

        JScrollPane jobAdvertsScrollPane = new JScrollPane(jobAdvertsTable);

        searchJobAdvertsTextField = new JTextField();
        searchJobAdvertsTextField.setPreferredSize(new Dimension(200, 30));

        searchJobAdvertsTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performJobAdvertSearch(searchJobAdvertsTextField.getText());
            }
        });

        JButton postJobButton = new JButton("Post Job");
        postJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postJob();
            }
        });

        JButton searchJobAdvertsButton = new JButton("Search Job Adverts");
        searchJobAdvertsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performJobAdvertSearch(searchJobAdvertsTextField.getText());
            }
        });

        JButton applyJobButton = new JButton("Apply for Job");
        applyJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyForSelectedJob();
            }
        });

        JPanel postJobPanel = new JPanel();
        postJobPanel.add(postJobButton);

        add(postJobPanel, BorderLayout.SOUTH);
        JPanel searchJobAdvertsPanel = new JPanel();
        searchJobAdvertsPanel.add(new JLabel("Search Job Adverts: "));
        searchJobAdvertsPanel.add(searchJobAdvertsTextField);
        searchJobAdvertsPanel.add(searchJobAdvertsButton);
        searchJobAdvertsPanel.add(applyJobButton); 

        add(searchJobAdvertsPanel, BorderLayout.NORTH);
        add(jobAdvertsScrollPane, BorderLayout.CENTER);
    }

    private void performJobAdvertSearch(String searchTerm) {
        DefaultTableModel tableModel = (DefaultTableModel) jobAdvertsTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (JobAdvert jobAdvert : jobAdverts) {
            // Check if the search term is found in the title, description, company, or location (case-insensitive and partial matching)
            if (jobAdvert.getTitle().toLowerCase().contains(searchTerm.toLowerCase())
                    || jobAdvert.getDescription().toLowerCase().contains(searchTerm.toLowerCase())
                    || jobAdvert.getCompany().toLowerCase().contains(searchTerm.toLowerCase())
                    || jobAdvert.getLocation().toLowerCase().contains(searchTerm.toLowerCase())) {
                // Add a row to the table
                Object[] rowData = {jobAdvert.getTitle(), jobAdvert.getDescription(), jobAdvert.getCompany(), jobAdvert.getLocation()};
                tableModel.addRow(rowData);
            }
        }
    }

    private void applyForSelectedJob() {
        int selectedRow = jobAdvertsTable.getSelectedRow();
        if (selectedRow != -1) {
            String selectedJobTitle = (String) jobAdvertsTable.getValueAt(selectedRow, 0);

            // Check if the user already has an applied job
            if (jobAndCareerPanel.hasAppliedJob()) {
                // Ask for confirmation to cancel the old job
                int confirmation = JOptionPane.showConfirmDialog(
                        JobAdvertPanel.this,
                        "You already have an applied job. Applying for a new job will cancel the old one. Continue?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmation == JOptionPane.NO_OPTION) {
                    return; // User canceled the operation
                }
            }

            jobAndCareerPanel.updateAppliedJobs(selectedJobTitle);  // Update applied jobs for the user
            JOptionPane.showMessageDialog(JobAdvertPanel.this, "You've applied for the job: " + selectedJobTitle);
            appliedJobCounter++;
        } else {
            JOptionPane.showMessageDialog(JobAdvertPanel.this, "Please select a job to apply.");
        }
    }

    private void postJob() {
        // Implement the logic to post a new job
        String title = JOptionPane.showInputDialog("Enter Job Title:");
        String description = JOptionPane.showInputDialog("Enter Job Description:");
        String companyName = JOptionPane.showInputDialog("Enter Your Company Name:");
        String jobLocation = JOptionPane.showInputDialog("Enter Job Location");

        JobAdvert newJobAdvert = new JobAdvert(title, description, companyName, jobLocation);
        jobAdverts.add(newJobAdvert);

        // Refresh the table to display the new job
        performJobAdvertSearch("");  // You may need to adjust this depending on your search logic
    }
}
