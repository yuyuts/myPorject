import org.sqlite.SQLiteConfig;

public class Main {
    public static Connection db = null;
    private static void openDatabse(String dbFile)
    {
        try{
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig = new SQLiteConfig();
            config.enforceForeignKeys(true);



        }

    }
}
