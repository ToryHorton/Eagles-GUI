import java.util.*;

/**
 * Interactive CLI demo that shows how to use Dictionary to store team info.
 */
public class Main {
    public static void main(String[] args) {
        Team team = sampleTeam();

        Scanner sc = new Scanner(System.in);
        System.out.println("Football Team Info - interactive demo");
        System.out.println("Commands: info | list | show <number> | search <name> | exit");

        while (true) {
            System.out.print("cmd> ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+", 2);
            String cmd = parts[0].toLowerCase(Locale.ROOT);

            try {
                switch (cmd) {
                    case "info":
                        team.printSummary();
                        break;
                    case "list":
                        team.printRoster();
                        break;
                    case "show":
                        if (parts.length < 2) {
                            System.out.println("Usage: show <number>");
                            break;
                        }
                        int num = Integer.parseInt(parts[1].trim());
                        Player p = team.getPlayer(num);
                        if (p == null) System.out.println("No player with number " + num);
                        else System.out.println(p);
                        break;
                    case "search":
                        if (parts.length < 2) {
                            System.out.println("Usage: search <name>");
                            break;
                        }
                        String q = parts[1].toLowerCase(Locale.ROOT);
                        boolean found = false;
                        for (Player pl : team.listPlayers()) {
                            if (pl.getName().toLowerCase(Locale.ROOT).contains(q)) {
                                System.out.println(pl);
                                found = true;
                            }
                        }
                        if (!found) System.out.println("No matching players.");
                        break;
                    case "exit":
                        System.out.println("Bye");
                        sc.close();
                        return;
                    default:
                        System.out.println("Unknown command: " + cmd);
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private static Team sampleTeam() {
        Team t = new Team("The Eagles", "Alex Morgan", "Riverside Stadium",
                "A competitive club with a focus on developing local talent.");

        t.addPlayer(new Player(1, "Oliver Banks", "Goalkeeper", "England"));
        t.addPlayer(new Player(2, "Marco Silva", "Defender", "Portugal"));
        t.addPlayer(new Player(7, "Ethan Perez", "Midfielder", "Spain"));
        t.addPlayer(new Player(9, "Liam Carter", "Forward", "England"));

        t.setStat("Wins", 12);
        t.setStat("Draws", 5);
        t.setStat("Losses", 3);
        t.setStat("Goals Scored", 38);

        return t;
    }
}
