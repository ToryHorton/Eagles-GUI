import java.util.*;

/**
 * Team model holding basic info and a roster stored in a Dictionary.
 */
public class Team {
    private final String name;
    private final String coach;
    private final String stadium;
    private final String description;
    private final Dictionary<Integer, Player> roster;
    private final Map<String, Integer> seasonStats;

    public Team(String name, String coach, String stadium, String description) {
        this.name = name;
        this.coach = coach;
        this.stadium = stadium;
        this.description = description;
        this.roster = new Dictionary<>();
        this.seasonStats = new LinkedHashMap<>();
    }

    public void addPlayer(Player p) { roster.put(p.getNumber(), p); }
    public Player getPlayer(int number) { return roster.get(number); }
    public Collection<Player> listPlayers() { return roster.values(); }
    public void setStat(String key, int value) { seasonStats.put(key, value); }
    public Map<String, Integer> getStats() { return Collections.unmodifiableMap(seasonStats); }

    public String getName() { return name; }
    public String getCoach() { return coach; }
    public String getStadium() { return stadium; }
    public String getDescription() { return description; }

    public void printSummary() {
        System.out.println("Team: " + name);
        System.out.println("Coach: " + coach);
        System.out.println("Stadium: " + stadium);
        System.out.println("Description: " + description);
        System.out.println("Season Stats:");
        seasonStats.forEach((k, v) -> System.out.printf("  %s: %d%n", k, v));
        System.out.println();
    }

    public void printRoster() {
        System.out.println("Roster:");
        for (Player p : listPlayers()) {
            System.out.println("  " + p.toString());
        }
    }
}
