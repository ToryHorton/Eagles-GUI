import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Swing GUI application for the football team demo.
 */
public class MainGui {
    private final Team team;

    public MainGui(Team team) {
        this.team = team;
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame(team.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top: Team info
        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel(team.getName());
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        top.add(title, BorderLayout.NORTH);

        JTextArea desc = new JTextArea(team.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBackground(top.getBackground());
        top.add(desc, BorderLayout.CENTER);

        JPanel meta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        meta.add(new JLabel("Coach: " + team.getCoach()));
        meta.add(new JLabel("   Stadium: " + team.getStadium()));
        top.add(meta, BorderLayout.SOUTH);

        root.add(top, BorderLayout.NORTH);

        // Center: roster list and details
        JSplitPane split = new JSplitPane();

        DefaultListModel<Player> listModel = new DefaultListModel<>();
        for (Player p : team.listPlayers()) listModel.addElement(p);
        JList<Player> rosterList = new JList<>(listModel);
        rosterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rosterList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Player) setText(((Player) value).toString());
                return this;
            }
        });

        JScrollPane rosterScroll = new JScrollPane(rosterList);
        split.setLeftComponent(rosterScroll);

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane detailsScroll = new JScrollPane(details);
        split.setRightComponent(detailsScroll);
        split.setDividerLocation(300);

        root.add(split, BorderLayout.CENTER);

        // Bottom: search and stats
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchRow.add(new JLabel("Search:"));
        JTextField search = new JTextField(30);
        searchRow.add(search);
        bottom.add(searchRow, BorderLayout.NORTH);

        JTextArea stats = new JTextArea();
        stats.setEditable(false);
        StringBuilder sb = new StringBuilder();
        team.getStats().forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));
        stats.setText(sb.toString());
        bottom.add(new JScrollPane(stats), BorderLayout.CENTER);

        root.add(bottom, BorderLayout.SOUTH);

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

        frame.setContentPane(root);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }

    private String dumpPlayer(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number: ").append(p.getNumber()).append('\n');
        sb.append("Name:   ").append(p.getName()).append('\n');
        sb.append("Position:").append(p.getPosition()).append('\n');
        sb.append("College/University: ").append(p.getNationality()).append('\n');
        return sb.toString();
    }

    public static void main(String[] args) {
        Team team = sampleTeam();
        SwingUtilities.invokeLater(() -> new MainGui(team).createAndShowGui());
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
