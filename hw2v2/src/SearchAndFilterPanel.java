//--------------------------------------------------------------------------------------------
// SearchAndFilterPanel.java Author: KAAN YILMAZ ID: 22097937
// this class integrates the search functionality into the LinkedIn Platform's interface,
// allowing users to find specific user profiles or job advertisements based on provided search queries.
//--------------------------------------------------------------------------------------------
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SearchAndFilterPanel extends JPanel {
    private final JTable searchResultsTable;
    private final List<UserProfile> userProfiles;
    private List<JobAdvertPanel.JobAdvert> jobAdverts;
    private final JTextField searchTextField;

    public SearchAndFilterPanel(List<UserProfile> userProfiles, List<JobAdvertPanel.JobAdvert> jobAdverts) {
        this.userProfiles = userProfiles;
        this.jobAdverts = jobAdverts;

        setLayout(new BorderLayout());

        // Create a table model with columns: Name, Username, Skills
        String[] columnNames = {"Name", "Username", "Skills"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        searchResultsTable = new JTable(tableModel);

        // Enable sorting for the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        searchResultsTable.setRowSorter(sorter);

        JScrollPane searchScrollPane = new JScrollPane(searchResultsTable);

        searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(200, 30));

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Perform search when Enter key is pressed
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    performSearch(searchTextField.getText());
                }
            }
        });

        JButton searchUsersButton = new JButton("");
        searchUsersButton.setBackground(Color.white);
        searchUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(searchTextField.getText());
            }
        });
        ImageIcon searchIcon = new ImageIcon("search_icon.png");
        Image scaledImage = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        searchUsersButton.setIcon(new ImageIcon(scaledImage));

        JPanel searchUsersPanel = new JPanel();
        searchUsersPanel.add(searchTextField);
        searchUsersPanel.add(searchUsersButton);

        add(searchUsersPanel, BorderLayout.NORTH);
        add(searchScrollPane, BorderLayout.CENTER);
    }

    private void performSearch(String searchTerm) {
        DefaultTableModel tableModel = (DefaultTableModel) searchResultsTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (UserProfile userProfile : userProfiles) {
            // Check if the search term is found in the full name or skills (case-insensitive and partial matching)
            if (userProfile.getFullName().toLowerCase().contains(searchTerm.toLowerCase())
                    || userProfile.getSkills().stream().anyMatch(skill -> skill.toLowerCase().contains(searchTerm.toLowerCase()))) {
                // Add a row to the table
                Object[] rowData = {userProfile.getFullName(), userProfile.getUsername(), String.join(", ", userProfile.getSkills())};
                tableModel.addRow(rowData);
            }
        }
    }
}
