import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Swing GUI application for the football team demo.
 * Enhanced with Philadelphia Eagles team colors and styling.
 */
public class MainGui {
    private final Team team;
    
    // Philadelphia Eagles Official Colors
    private static final Color MIDNIGHT_GREEN = new Color(0, 76, 84);      // Primary
    private static final Color SILVER = new Color(165, 172, 175);          // Secondary
    private static final Color BLACK = new Color(0, 0, 0);                 // Accent
    private static final Color WHITE = new Color(255, 255, 255);           // Text
    private static final Color DARK_GREEN = new Color(0, 50, 56);          // Darker shade
    private static final Color LIGHT_GREEN = new Color(0, 95, 106);        // Lighter shade
    private static final Color CHARCOAL = new Color(32, 32, 32);           // Dark background

    public MainGui(Team team) {
        this.team = team;
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame(team.getName() + " - Roster Manager");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(CHARCOAL);
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top: Team info with Eagles styling
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBackground(MIDNIGHT_GREEN);
        top.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(SILVER, 3),
            new EmptyBorder(15, 15, 15, 15)
        ));

        // Title with larger font
        JLabel title = new JLabel("🦅 " + team.getName());
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(WHITE);
        top.add(title, BorderLayout.NORTH);

        // Description
        JTextArea desc = new JTextArea(team.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBackground(MIDNIGHT_GREEN);
        desc.setForeground(WHITE);
        desc.setFont(new Font("Arial", Font.PLAIN, 13));
        desc.setBorder(new EmptyBorder(10, 0, 10, 0));
        top.add(desc, BorderLayout.CENTER);

        // Meta information panel
        JPanel meta = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        meta.setBackground(DARK_GREEN);
        meta.setBorder(new EmptyBorder(8, 10, 8, 10));
        
        JLabel coachLabel = new JLabel("🏈 Coach: " + team.getCoach());
        coachLabel.setForeground(WHITE);
        coachLabel.setFont(new Font("Arial", Font.BOLD, 13));
        meta.add(coachLabel);
        
        JLabel stadiumLabel = new JLabel("🏟️ Stadium: " + team.getStadium());
        stadiumLabel.setForeground(WHITE);
        stadiumLabel.setFont(new Font("Arial", Font.BOLD, 13));
        meta.add(stadiumLabel);
        
        top.add(meta, BorderLayout.SOUTH);

        root.add(top, BorderLayout.NORTH);

        // Center: roster list and details
        JSplitPane split = new JSplitPane();
        split.setBackground(CHARCOAL);
        split.setDividerSize(8);

        // Left panel - Roster List
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(CHARCOAL);
        leftPanel.setBorder(new EmptyBorder(5, 0, 0, 5));
        
        // Top section with title and dropdown
        JPanel leftTopPanel = new JPanel(new BorderLayout(10, 0));
        leftTopPanel.setBackground(CHARCOAL);
        leftTopPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        
        JLabel rosterTitle = new JLabel("ROSTER");
        rosterTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rosterTitle.setForeground(SILVER);
        leftTopPanel.add(rosterTitle, BorderLayout.WEST);
        
        // Dropdown to switch between Players, Coaches, and Staff
        String[] categories = {"Players", "Coaches", "Staff"};
        JComboBox<String> categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setBackground(MIDNIGHT_GREEN);
        categoryDropdown.setForeground(WHITE);
        categoryDropdown.setFont(new Font("Arial", Font.PLAIN, 13));
        categoryDropdown.setBorder(new LineBorder(SILVER, 1));
        leftTopPanel.add(categoryDropdown, BorderLayout.EAST);
        
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);

        DefaultListModel<Object> playerListModel = new DefaultListModel<>();
        for (Player p : team.listPlayers()) playerListModel.addElement(p);
        
        DefaultListModel<Object> coachListModel = new DefaultListModel<>();
        for (Coach c : team.listCoaches()) coachListModel.addElement(c);
        
        DefaultListModel<Object> staffListModel = new DefaultListModel<>();
        for (Staff s : team.listStaff()) staffListModel.addElement(s);
        
        JList<Object> rosterList = new JList<>(playerListModel);
        rosterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rosterList.setBackground(DARK_GREEN);
        rosterList.setForeground(WHITE);
        rosterList.setSelectionBackground(LIGHT_GREEN);
        rosterList.setSelectionForeground(WHITE);
        rosterList.setFont(new Font("Arial", Font.PLAIN, 13));
        rosterList.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        rosterList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Player player) {
                    setText(player.toString());
                    setBorder(new EmptyBorder(8, 10, 8, 10));
                    if (isSelected) {
                        setBackground(LIGHT_GREEN);
                        setForeground(WHITE);
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setBackground(DARK_GREEN);
                        setForeground(WHITE);
                    }
                } else if (value instanceof Coach) {
                    setText(((Coach) value).toString());
                    setBorder(new EmptyBorder(8, 10, 8, 10));
                    if (isSelected) {
                        setBackground(LIGHT_GREEN);
                        setForeground(WHITE);
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setBackground(DARK_GREEN);
                        setForeground(WHITE);
                    }
                } else if (value instanceof Staff) {
                    setText(((Staff) value).toString());
                    setBorder(new EmptyBorder(8, 10, 8, 10));
                    if (isSelected) {
                        setBackground(LIGHT_GREEN);
                        setForeground(WHITE);
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setBackground(DARK_GREEN);
                        setForeground(WHITE);
                    }
                }
                return this;
            }
        });

        JScrollPane rosterScroll = new JScrollPane(rosterList);
        rosterScroll.setBorder(new LineBorder(MIDNIGHT_GREEN, 2));
        rosterScroll.getViewport().setBackground(DARK_GREEN);
        leftPanel.add(rosterScroll, BorderLayout.CENTER);
        
        split.setLeftComponent(leftPanel);

        // Right panel - Details (Player, Coach, or Staff)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(CHARCOAL);
        rightPanel.setBorder(new EmptyBorder(5, 5, 0, 0));
        
        JLabel detailsTitle = new JLabel("DETAILS");
        detailsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        detailsTitle.setForeground(SILVER);
        detailsTitle.setBorder(new EmptyBorder(5, 10, 10, 10));
        rightPanel.add(detailsTitle, BorderLayout.NORTH);

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setFont(new Font("Arial", Font.PLAIN, 25));
        details.setBackground(DARK_GREEN);
        details.setForeground(WHITE);
        details.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane detailsScroll = new JScrollPane(details);
        detailsScroll.setBorder(new LineBorder(MIDNIGHT_GREEN, 2));
        detailsScroll.getViewport().setBackground(DARK_GREEN);
        rightPanel.add(detailsScroll, BorderLayout.CENTER);
        
        split.setRightComponent(rightPanel);
        split.setDividerLocation(425);

        root.add(split, BorderLayout.CENTER);

        // Bottom: search and stats
        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.setBackground(CHARCOAL);
        bottom.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(MIDNIGHT_GREEN);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(SILVER, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel searchLabel = new JLabel("🔍 SEARCH ROSTER:");
        searchLabel.setForeground(WHITE);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 13));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        
        JTextField search = new JTextField(30);
        search.setFont(new Font("Arial", Font.PLAIN, 14));
        search.setBackground(WHITE);
        search.setForeground(BLACK);
        search.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(SILVER, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        searchPanel.add(search, BorderLayout.CENTER);
        
        bottom.add(searchPanel, BorderLayout.NORTH);

        // Stats panel
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(DARK_GREEN);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MIDNIGHT_GREEN, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel statsTitle = new JLabel("📊 TEAM STATISTICS");
        statsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        statsTitle.setForeground(SILVER);
        statsTitle.setBorder(new EmptyBorder(0, 0, 8, 0));
        statsPanel.add(statsTitle, BorderLayout.NORTH);

        JTextArea stats = new JTextArea();
        stats.setEditable(false);
        stats.setBackground(DARK_GREEN);
        stats.setForeground(WHITE);
        stats.setFont(new Font("Arial", Font.PLAIN, 13));
        StringBuilder sb = new StringBuilder();
        team.getStats().forEach((k, v) -> sb.append("  • ").append(k).append(": ").append(v).append("\n"));
        stats.setText(sb.toString());
        stats.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JScrollPane statsScroll = new JScrollPane(stats);
        statsScroll.setBorder(null);
        statsScroll.setPreferredSize(new Dimension(0, 100));
        statsScroll.getViewport().setBackground(DARK_GREEN);
        statsPanel.add(statsScroll, BorderLayout.CENTER);

        bottom.add(statsPanel, BorderLayout.CENTER);

        root.add(bottom, BorderLayout.SOUTH);

        // List selection -> details
        rosterList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Object selected = rosterList.getSelectedValue();
                if (selected instanceof Player) {
                    details.setText(dumpPlayer((Player) selected));
                } else if (selected instanceof Coach) {
                    details.setText(dumpCoach((Coach) selected));
                } else if (selected instanceof Staff) {
                    details.setText(dumpStaff((Staff) selected));
                } else {
                    details.setText("");
                }
            }
        });
        
        // Dropdown listener to switch between categories
        categoryDropdown.addActionListener(e -> {
            String selected = (String) categoryDropdown.getSelectedItem();
            rosterList.clearSelection();
            
            if ("Players".equals(selected)) {
                playerListModel.clear();
                rosterList.setModel(playerListModel);
                for (Player p : team.listPlayers()) {
                    playerListModel.addElement(p);
                }
            } else if ("Coaches".equals(selected)) {
                coachListModel.clear();
                rosterList.setModel(coachListModel);
                for (Coach c : team.listCoaches()) {
                    coachListModel.addElement(c);
                }
            } else if ("Staff".equals(selected)) {
                staffListModel.clear();
                rosterList.setModel(staffListModel);
                for (Staff s : team.listStaff()) {
                    staffListModel.addElement(s);
                }
            }
            details.setText("");
        });

        // Search filter
        search.getDocument().addDocumentListener(new DocumentListener() {
            private void filter() {
                String q = search.getText().trim().toLowerCase(Locale.ROOT);
                Object currentModel = rosterList.getModel();
                
                if (currentModel == playerListModel) {
                    playerListModel.clear();
                    for (Player p : team.listPlayers()) {
                        if (q.isEmpty() || p.getName().toLowerCase(Locale.ROOT).contains(q)
                                || Integer.toString(p.getNumber()).equals(q)) {
                            playerListModel.addElement(p);
                        }
                    }
                } else if (currentModel == coachListModel) {
                    coachListModel.clear();
                    for (Coach c : team.listCoaches()) {
                        if (q.isEmpty() || c.getName().toLowerCase(Locale.ROOT).contains(q)
                                || c.getPosition().toLowerCase(Locale.ROOT).contains(q)) {
                            coachListModel.addElement(c);
                        }
                    }
                } else if (currentModel == staffListModel) {
                    staffListModel.clear();
                    for (Staff s : team.listStaff()) {
                        if (q.isEmpty() || s.getName().toLowerCase(Locale.ROOT).contains(q)
                                || s.getRole().toLowerCase(Locale.ROOT).contains(q)) {
                            staffListModel.addElement(s);
                        }
                    }
                }
            }

            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
        });

        frame.setContentPane(root);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                JOptionPane.showMessageDialog(null,
                    "Thanks for using the Philadelphia Eagles Roster Manager!\n\n" +
                    "Go Birds! 🦅",
                    "Goodbye - Eagles Roster Manager",
                    JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
        
        // Set custom icon color for title bar (if supported)
        frame.setIconImage(createEaglesIcon());
        
        frame.setVisible(true);
    }

    private String dumpPlayer(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("  PLAYER INFORMATION\n");
        sb.append("═══════════════════════════════════════\n\n");
        sb.append("  Number:     #").append(p.getNumber()).append('\n');
        sb.append("  Name:       ").append(p.getName()).append('\n');
        sb.append("  Position:   ").append(p.getPosition()).append('\n');
        sb.append("  College:    ").append(p.getNationality()).append('\n');
        sb.append("\n═══════════════════════════════════════\n");
        return sb.toString();
    }
    
    private String dumpCoach(Coach c) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("  COACH INFORMATION\n");
        sb.append("═══════════════════════════════════════\n\n");
        sb.append("  Name:       ").append(c.getName()).append('\n');
        sb.append("  Position:   ").append(c.getPosition()).append('\n');
        sb.append("  Specialty:  ").append(c.getSpecialty()).append('\n');
        sb.append("\n═══════════════════════════════════════\n");
        return sb.toString();
    }
    
    private String dumpStaff(Staff s) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("  STAFF INFORMATION\n");
        sb.append("═══════════════════════════════════════\n\n");
        sb.append("  Name:       ").append(s.getName()).append('\n');
        sb.append("  Department: ").append(s.getDepartment()).append('\n');
        sb.append("  Role:       ").append(s.getRole()).append('\n');
        sb.append("\n═══════════════════════════════════════\n");
        return sb.toString();
    }
    
    private Image createEaglesIcon() {
        // Create a simple Eagles-colored icon
        int size = 64;
        Image img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw circle background
        g2d.setColor(MIDNIGHT_GREEN);
        g2d.fillOval(0, 0, size, size);
        
        // Draw border
        g2d.setColor(SILVER);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(2, 2, size - 4, size - 4);
        
        g2d.dispose();
        return img;
    }

    public static void main(String[] args) {
        Team team = sampleTeam();
        
        // Show welcome pop-up and get user info
        JPanel userPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        userPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nameField = new JTextField(15);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField emailField = new JTextField(15);
        
        JLabel teamFanLabel = new JLabel("Favorite Team:");
        teamFanLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField teamFanField = new JTextField(15);
        
        userPanel.add(nameLabel);
        userPanel.add(nameField);
        userPanel.add(emailLabel);
        userPanel.add(emailField);
        userPanel.add(teamFanLabel);
        userPanel.add(teamFanField);
        
        int result = JOptionPane.showConfirmDialog(null, userPanel,
            "Welcome to the Philadelphia Eagles Roster Manager!",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String teamFan = teamFanField.getText().trim();
            
            // Save user info to CSV
            try {
                UserInfoCSVHandler.saveUserInfo(name, email, teamFan, "userinfo.csv");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving user info: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGui(team).createAndShowGui();
            }
        });
    }

    private static Team sampleTeam() {
        Team t = new Team("Philadelphia Eagles", "Nick Sirianni", "Lincoln Financial Field",
                "Based in Philadelphia, Pennsylvania, the Philadelphia Eagles are a professional football team that plays in the National Football League's (NFL) East division of the National Football Conference (NFC). Explore part of the roster below!");

        t.addPlayer(new Player(14, "Sam Howell", "Quarterback", "North Carolina"));
        t.addPlayer(new Player(1, "Jalen Hurts", "Quarterback", "Oklahoma"));
        t.addPlayer(new Player(16, "Tanner McKee", "Quarterback", "Stanford"));
        t.addPlayer(new Player(26, "Saquon Barkley", "Running Back", "Penn State"));
        t.addPlayer(new Player(29, "AJ Dillon", "Running Back", "Boston College"));
        t.addPlayer(new Player(37, "Tank Bigsby", "Running Back", "Auburn"));
        t.addPlayer(new Player(28, "Will Shipley", "Running Back", "Clemson"));
        t.addPlayer(new Player(11, "A.J. Brown", "Wide Receiver", "Ole Miss"));
        t.addPlayer(new Player(80, "Darius Cooper", "Wide Receiver", "Tarleton State"));
        t.addPlayer(new Player(18, "Britain Covey", "Wide Receiver", "Utah"));
        t.addPlayer(new Player(2, "Jahan Dotson", "Wide Receiver", "Penn State"));
        t.addPlayer(new Player(6, "DeVonta Smith", "Wide Receiver", "Alabama"));
        t.addPlayer(new Player(81, "Grant Calcaterra", "Tight End", "SMU"));
        t.addPlayer(new Player(83, "Kylen Granson", "Tight End", "SMU"));
        t.addPlayer(new Player(88, "Dallas Goedert", "Tight End", "South Dakota State"));
        t.addPlayer(new Player(84, "E.J. Jenkins", "Tight End", "Georgia Tech"));
        t.addPlayer(new Player(36, "Cameron Latu", "Tight End", "Alabama"));
        t.addPlayer(new Player(51, "Cam Jurgens", "Center", "Nebraska"));
        t.addPlayer(new Player(66, "Drew Kendall", "Center", "Boston College"));
        t.addPlayer(new Player(64, "Brett Toth", "Center", "Army"));
        t.addPlayer(new Player(69, "Landon Dickerson", "Guard", "Alabama"));
        t.addPlayer(new Player(56, "Tyler Steen", "Guard", "Alabama"));
        t.addPlayer(new Player(74, "Fred Johnson", "Offensive Tackle", "Florida"));
        t.addPlayer(new Player(65, "Lane Johnson", "Offensive Tackle", "Oklahoma"));
        t.addPlayer(new Player(68, "Jordan Mailata", "Offensive Tackle", "N/a"));
        t.addPlayer(new Player(79, "Matt Pryor", "Offensive Tackle", "TCU"));
        t.addPlayer(new Player(73, "Cameron Williams", "Offensive Tackle", "Texas"));

        t.addPlayer(new Player(55, "Brandon Graham", "Defensive End", "Michigan"));
        t.addPlayer(new Player(98, "Jalen Carter", "Defensive Tackle", "Georgia"));
        t.addPlayer(new Player(90, "Jordan Davis", "Defensive Tackle", "Georgia"));
        t.addPlayer(new Player(96, "Gabe Hall", "Defensive Tackle", "Baylor"));
        t.addPlayer(new Player(97, "Moro Ojomo", "Defensive Tackle", "Texas"));
        t.addPlayer(new Player(95, "Ty Robinson", "Defensive Tackle", "Nebraska"));
        t.addPlayer(new Player(93, "Jacob Sykes", "Defensive Tackle", "UCLA"));
        t.addPlayer(new Player(94, "Byron Young", "Defensive Tackle", "Alabama"));
        t.addPlayer(new Player(53, "Zack Baun", "Linebacker", "Wisconsin"));
        t.addPlayer(new Player(30, "Jihaad Campbell", "Linebacker", "Alabama"));
        t.addPlayer(new Player(17, "Nakobe Dean", "Linebacker", "Georgia"));
        t.addPlayer(new Player(58, "Jalyx Hunt", "Linebacker", "Houston Christian"));
        t.addPlayer(new Player(42, "Smael Mondon Jr.", "Linebacker", "Georgia"));
        t.addPlayer(new Player(50, "Jaelan Phillips", "Linebacker", "Miami"));
        t.addPlayer(new Player(3, "Nolan Smith Jr.", "Linebacker", "Georgia"));
        t.addPlayer(new Player(54, "Jeremiah Trotter Jr.", "Linebacker", "Clemson"));
        t.addPlayer(new Player(23, "Jakorian Bennett", "Cornerback", "Maryland"));
        t.addPlayer(new Player(35, "Michael Carter II", "Cornerback", "Duke"));
        t.addPlayer(new Player(46, "Tariq Castro-Fields", "Cornerback", "Penn State"));
        t.addPlayer(new Player(33, "Cooper DeJean", "Cornerback", "Iowa"));
        t.addPlayer(new Player(8, "Adoree' Jackson", "Cornerback", "USC"));
        t.addPlayer(new Player(49, "Brandon Johnson", "Cornerback", "Oregon"));
        t.addPlayer(new Player(22, "Mac McWilliams", "Cornerback", "UCF"));
        t.addPlayer(new Player(27, "Quinyon Mitchell", "Cornerback", "Toledo"));
        t.addPlayer(new Player(7, "Kelee Ringo", "Cornerback", "Georgia"));
        t.addPlayer(new Player(32, "Reed Blankenship", "Safety", "Middle Tennessee"));
        t.addPlayer(new Player(21, "Sydney Brown", "Safety", "Illinois"));
        t.addPlayer(new Player(39, "Marcus Epps", "Safety", "Wyoming"));
        t.addPlayer(new Player(31, "Andre' Sam", "Safety", "LSU"));

        t.addPlayer(new Player(4, "Jake Elliot", "Placekicker", "Memphis"));
        t.addPlayer(new Player(10, "Bradden Mann", "Punter", "Texas A&M"));

        // Add coaches
        t.addCoach(new Coach("Nick Sirianni", "Head Coach", "Offense"));
        t.addCoach(new Coach("Joe Barry", "Defensive Coordinator", "Defense"));
        t.addCoach(new Coach("Shane Steichen", "Offensive Coordinator", "Offense"));
        t.addCoach(new Coach("Tim Hauck", "Linebacker Coach", "Defense"));
        t.addCoach(new Coach("DeShawn Jackson", "Wide Receiver Coach", "Offense"));
        t.addCoach(new Coach("Juan Castillo", "Defensive Line Coach", "Defense"));
        
        // Add staff
        t.addStaff(new Staff("Catherine Raîche", "Medical", "Team Physician"));
        t.addStaff(new Staff("Tommy Specht", "Operations", "Strength & Conditioning Coach"));
        t.addStaff(new Staff("Ryan Patrizio", "Communications", "Director of Communications"));
        t.addStaff(new Staff("James Sexton", "Medical", "Head Athletic Trainer"));
        t.addStaff(new Staff("Brett Drake", "Scouting", "Scout Director"));

        t.setStat("Wins", 649);
        t.setStat("Draws", 27);
        t.setStat("Losses", 645);
        t.setStat("NFL Championships", 5);

        return t;
    }
}