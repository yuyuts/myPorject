import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static Connection db = null;

    public static void main(String[] args) {
        openDatabse("LeagueDatabase.db");


        closeDatabase();
    }

    private static void openDatabse(String dbFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database Connection Successfully Established");


        } catch (Exception exception) {
            System.out.println("Database Connection Error" + exception.getMessage());
        }

    }

    private static void closeDatabase() {
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error:" + exception.getMessage());
        }

    }

    public static void listPlayers(){
        try{
            PreparedStatement ps= Main.db.prepareStatement("SELECT playerID, playerIGN,firstName, Nationality,teamName FROM Players");
            ResultSet results = ps.executeQuery();
            while(results.next()){
                int playerID = results.getInt(1);
                String playerIGN = results.getString(2);
                String firstName = results.getString(3);
                String Nationality = results.getString(4);
                String teamName = results.getString(5);

            }

        } catch (Exception exception){
            System.out.println("Database error:" + exception.getMessage());
        }
    }
    public static void
}
