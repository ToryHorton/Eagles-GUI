import java.util.*;

/**
 * Team model that holds players and team information.
 */
public class Team {
    private final String name;
    private final String coach;
    private final String stadium;
    private final String description;
    private final Map<Integer, Player> players;
    private final Map<String, Object> stats;

    public Team(String name, String coach, String stadium, String description) {
        this.name = name;
        this.coach = coach;
        this.stadium = stadium;
        this.description = description;
        this.players = new HashMap<>();
        this.stats = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getCoach() {
        return coach;
    }

    public String getStadium() {
        return stadium;
    }

    public String getDescription() {
        return description;
    }

    public void addPlayer(Player player) {
        players.put(player.getNumber(), player);
    }

    public Player getPlayer(int number) {
        return players.get(number);
    }

    public List<Player> listPlayers() {
        List<Player> list = new ArrayList<>(players.values());
        list.sort(Comparator.comparingInt(Player::getNumber));
        return list;
    }

    public void setStat(String key, Object value) {
        stats.put(key, value);
    }

    public Object getStat(String key) {
        return stats.get(key);
    }

    public Map<String, Object> getStats() {
        return new LinkedHashMap<>(stats);
    }

    public void printSummary() {
        System.out.println("Team: " + name);
        System.out.println("Coach: " + coach);
        System.out.println("Stadium: " + stadium);
        System.out.println("Description: " + description);
        System.out.println("Players: " + players.size());
        System.out.println("Stats:");
        stats.forEach((k, v) -> System.out.println("  " + k + ": " + v));
    }

    public void printRoster() {
        System.out.println("Roster for " + name + ":");
        for (Player p : listPlayers()) {
            System.out.println("  " + p);
        }
    }

    @Override
    public String toString() {
        return name + " (" + players.size() + " players)";
    }
}
