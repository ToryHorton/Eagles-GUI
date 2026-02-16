import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserInfoCSVHandler {
    
    /**
     * Save user information to a CSV file
     */
    public static void saveUserInfo(String name, String email, String favoriteTeam, String filename) throws IOException {
        boolean fileExists = Files.exists(Paths.get(filename));
        
        try (FileWriter fw = new FileWriter(filename, true);
             PrintWriter writer = new PrintWriter(fw)) {
            
            // Write header if file doesn't exist
            if (!fileExists) {
                writer.println("Name,Email,Favorite Team,Login Date/Time");
            }
            
            // Write user data with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String line = String.format("%s,%s,%s,%s",
                escapeCSV(name),
                escapeCSV(email),
                escapeCSV(favoriteTeam),
                timestamp
            );
            writer.println(line);
        }
    }
    
    /**
     * Escape special characters in CSV fields
     */
    private static String escapeCSV(String value) {
        if (value == null || value.isEmpty()) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}