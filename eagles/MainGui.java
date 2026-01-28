import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private static final Color MIDNIGHT_GREEN = new Color(0, 76, 84);
    private static final Color SILVER = new Color(165, 172, 175);
    private static final Color BLACK = new Color(0, 0, 0);
    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color DARK_GREEN = new Color(0, 50, 56);
    private static final Color LIGHT_GREEN = new Color(0, 95, 106);
    private static final Color CHARCOAL = new Color(32, 32, 32);

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
        JLabel title = new JLabel(team.getName());
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
        
        JLabel coachLabel = new JLabel("Coach: " + team.getCoach());
        coachLabel.setForeground(WHITE);
        coachLabel.setFont(new Font("Arial", Font.BOLD, 13));
        meta.add(coachLabel);
        
        JLabel stadiumLabel = new JLabel("Stadium: " + team.getStadium());
        stadiumLabel.setForeground(WHITE);
        stadiumLabel.setFont(new Font("Arial", Font.BOLD, 13));
        meta.add(stadiumLabel);
        
        top.add(meta, BorderLayout.SOUTH);

        root.add(top, BorderLayout.NORTH);

        // Create tabbed pane for Players, Staff, and Statistics
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(CHARCOAL);
        tabbedPane.setForeground(WHITE);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Players Tab
        JPanel playersPanel = createPlayersPanel();
        tabbedPane.addTab("PLAYERS", playersPanel);

        // Staff Tab
        JPanel staffPanel = createStaffPanel();
        tabbedPane.addTab("STAFF", staffPanel);

        // Statistics Tab
        JPanel statsPanel = createStatsTabPanel();
        tabbedPane.addTab("STATISTICS", statsPanel);

        root.add(tabbedPane, BorderLayout.CENTER);

        frame.setContentPane(root);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        
        frame.setVisible(true);
    }

    private JPanel createPlayersPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(CHARCOAL);

        // Center: roster list and details
        JSplitPane split = new JSplitPane();
        split.setBackground(CHARCOAL);
        split.setDividerSize(8);

        // Left panel - Roster List
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(CHARCOAL);
        leftPanel.setBorder(new EmptyBorder(5, 0, 0, 5));
        
        JLabel rosterTitle = new JLabel("ROSTER");
        rosterTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rosterTitle.setForeground(SILVER);
        rosterTitle.setBorder(new EmptyBorder(5, 10, 10, 10));
        leftPanel.add(rosterTitle, BorderLayout.NORTH);

        DefaultListModel<Player> listModel = new DefaultListModel<>();
        for (Player p : team.listPlayers()) listModel.addElement(p);
        
        JList<Player> rosterList = new JList<>(listModel);
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
                if (value instanceof Player) {
                    setText(((Player) value).toString());
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

        // Right panel - Player Details
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(CHARCOAL);
        rightPanel.setBorder(new EmptyBorder(5, 5, 0, 0));
        
        JLabel detailsTitle = new JLabel("PLAYER DETAILS");
        detailsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        detailsTitle.setForeground(SILVER);
        detailsTitle.setBorder(new EmptyBorder(5, 10, 10, 10));
        rightPanel.add(detailsTitle, BorderLayout.NORTH);

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setFont(new Font("Monospaced", Font.PLAIN, 13));
        details.setBackground(DARK_GREEN);
        details.setForeground(WHITE);
        details.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane detailsScroll = new JScrollPane(details);
        detailsScroll.setBorder(new LineBorder(MIDNIGHT_GREEN, 2));
        detailsScroll.getViewport().setBackground(DARK_GREEN);
        rightPanel.add(detailsScroll, BorderLayout.CENTER);
        
        split.setRightComponent(rightPanel);
        split.setDividerLocation(350);

        mainPanel.add(split, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(MIDNIGHT_GREEN);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(SILVER, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel searchLabel = new JLabel("SEARCH ROSTER:");
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
        
        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        // List selection -> details
        rosterList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Player p = rosterList.getSelectedValue();
                if (p != null) {
                    details.setText(dumpPlayer(p));
                } else {
                    details.setText("");
                }
            }
        });

        // Search filter
        search.getDocument().addDocumentListener(new DocumentListener() {
            private void filter() {
                String q = search.getText().trim().toLowerCase(Locale.ROOT);
                listModel.clear();
                for (Player p : team.listPlayers()) {
                    if (q.isEmpty() || p.getName().toLowerCase(Locale.ROOT).contains(q)
                            || Integer.toString(p.getNumber()).equals(q)) {
                        listModel.addElement(p);
                    }
                }
            }

            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
        });

        return mainPanel;
    }

    private JPanel createStaffPanel() {
        JPanel staffPanel = new JPanel(new BorderLayout(10, 10));
        staffPanel.setBackground(CHARCOAL);
        staffPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel staffTitle = new JLabel("TEAM STAFF & MANAGEMENT");
        staffTitle.setFont(new Font("Arial", Font.BOLD, 20));
        staffTitle.setForeground(SILVER);
        staffTitle.setBorder(new EmptyBorder(10, 10, 20, 10));
        staffPanel.add(staffTitle, BorderLayout.NORTH);

        // Staff information panel
        JPanel staffInfo = new JPanel();
        staffInfo.setLayout(new BoxLayout(staffInfo, BoxLayout.Y_AXIS));
        staffInfo.setBackground(DARK_GREEN);
        staffInfo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MIDNIGHT_GREEN, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Head Coach
        addStaffMember(staffInfo, "HEAD COACH", team.getCoach(), 
            "Leading the team since 2021. Responsible for game strategy, player development, and overall team performance.");

        // Add spacing
        staffInfo.add(Box.createVerticalStrut(20));

        // General Manager
        addStaffMember(staffInfo, "GENERAL MANAGER", "Howie Roseman",
            "Executive Vice President and General Manager. Oversees all football operations, draft strategy, and player acquisitions.");

        staffInfo.add(Box.createVerticalStrut(20));

        // Offensive Coordinator
        addStaffMember(staffInfo, "OFFENSIVE COORDINATOR", "Kellen Moore",
            "Designs and calls offensive plays. Works with quarterbacks and coordinates the offensive game plan.");

        staffInfo.add(Box.createVerticalStrut(20));

        // Defensive Coordinator
        addStaffMember(staffInfo, "DEFENSIVE COORDINATOR", "Vic Fangio",
            "Manages defensive strategy and schemes. Coordinates with defensive position coaches.");

        staffInfo.add(Box.createVerticalStrut(20));

        // Owner
        addStaffMember(staffInfo, "OWNER", "Jeffrey Lurie",
            "Team owner since 1994. Oversees all business operations and maintains the team's championship culture.");

        JScrollPane scrollPane = new JScrollPane(staffInfo);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_GREEN);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        staffPanel.add(scrollPane, BorderLayout.CENTER);

        return staffPanel;
    }

    private void addStaffMember(JPanel panel, String title, String name, String description) {
        JPanel memberPanel = new JPanel(new BorderLayout(10, 5));
        memberPanel.setBackground(DARK_GREEN);
        memberPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(SILVER);
        memberPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(LIGHT_GREEN);
        nameLabel.setBorder(new EmptyBorder(5, 0, 5, 0));

        JTextArea descArea = new JTextArea(description);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(DARK_GREEN);
        descArea.setForeground(WHITE);
        descArea.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel infoPanel = new JPanel(new BorderLayout(5, 5));
        infoPanel.setBackground(DARK_GREEN);
        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(descArea, BorderLayout.CENTER);

        memberPanel.add(infoPanel, BorderLayout.CENTER);

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(MIDNIGHT_GREEN);
        separator.setBackground(MIDNIGHT_GREEN);
        memberPanel.add(separator, BorderLayout.SOUTH);

        panel.add(memberPanel);
    }

    private JPanel createStatsTabPanel() {
        JPanel statsTabPanel = new JPanel(new BorderLayout(10, 10));
        statsTabPanel.setBackground(CHARCOAL);
        statsTabPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel statsTitle = new JLabel("TEAM STATISTICS & ACHIEVEMENTS");
        statsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        statsTitle.setForeground(SILVER);
        statsTitle.setBorder(new EmptyBorder(10, 10, 20, 10));
        statsTabPanel.add(statsTitle, BorderLayout.NORTH);

        // Create a panel for all stats cards
        JPanel statsCardsPanel = new JPanel();
        statsCardsPanel.setLayout(new BoxLayout(statsCardsPanel, BoxLayout.Y_AXIS));
        statsCardsPanel.setBackground(CHARCOAL);

        // Overall Record Card
        JPanel recordCard = createStatCard("OVERALL RECORD", 
            String.format("Wins: %s | Draws: %s | Losses: %s", 
                team.getStat("Wins"), 
                team.getStat("Draws"), 
                team.getStat("Losses")));
        statsCardsPanel.add(recordCard);
        statsCardsPanel.add(Box.createVerticalStrut(15));

        // Championships Card
        JPanel championshipCard = createStatCard("CHAMPIONSHIPS", 
            String.format("NFL Championships: %s", team.getStat("NFL Championships")));
        statsCardsPanel.add(championshipCard);
        statsCardsPanel.add(Box.createVerticalStrut(15));

        // Win Percentage Card
        int wins = (Integer) team.getStat("Wins");
        int draws = (Integer) team.getStat("Draws");
        int losses = (Integer) team.getStat("Losses");
        int totalGames = wins + draws + losses;
        double winPct = (wins / (double) totalGames) * 100;
        
        JPanel winPctCard = createStatCard("WIN PERCENTAGE", 
            String.format("%.1f%% (%d total games)", winPct, totalGames));
        statsCardsPanel.add(winPctCard);
        statsCardsPanel.add(Box.createVerticalStrut(15));

        // Roster Size Card
        JPanel rosterCard = createStatCard("ROSTER SIZE", 
            String.format("%d Players", team.listPlayers().size()));
        statsCardsPanel.add(rosterCard);
        statsCardsPanel.add(Box.createVerticalStrut(15));

        // Stadium Info Card
        JPanel stadiumCard = createStatCard("HOME STADIUM", 
            String.format("%s - %s", team.getStadium(), "Philadelphia, PA"));
        statsCardsPanel.add(stadiumCard);

        JScrollPane scrollPane = new JScrollPane(statsCardsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CHARCOAL);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        statsTabPanel.add(scrollPane, BorderLayout.CENTER);

        return statsTabPanel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(DARK_GREEN);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MIDNIGHT_GREEN, 3),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(SILVER);
        card.add(titleLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(LIGHT_GREEN);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createStatsPanel() {
        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.setBackground(CHARCOAL);
        bottom.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Stats panel
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(DARK_GREEN);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MIDNIGHT_GREEN, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel statsTitle = new JLabel("TEAM STATISTICS");
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
        team.getStats().forEach((k, v) -> sb.append("  ").append(k).append(": ").append(v).append("\n"));
        stats.setText(sb.toString());
        stats.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JScrollPane statsScroll = new JScrollPane(stats);
        statsScroll.setBorder(null);
        statsScroll.setPreferredSize(new Dimension(0, 100));
        statsScroll.getViewport().setBackground(DARK_GREEN);
        statsPanel.add(statsScroll, BorderLayout.CENTER);

        bottom.add(statsPanel, BorderLayout.CENTER);
        return bottom;
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

    public static void main(String[] args) {
        Team team = sampleTeam();
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

        t.setStat("Wins", 649);
        t.setStat("Draws", 27);
        t.setStat("Losses", 645);
        t.setStat("NFL Championships", 5);

        return t;
    }
}