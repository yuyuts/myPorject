import org.sqlite.SQLiteConfig;

import java.sql.DriverManager;

public class Main {
    public static Connection db = null;
    private static void openDatabse(String dbFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database Connection Sucessfully Established");


        } catch (Exception exception) {
            System.out.println("Database Connection Error" + exception.getMessage());
        }

    }
    private

    }
}
