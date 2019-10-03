package Servers;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static Connection db = null;


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
                System.out.println(Nationality);
                System.out.println(playerID);
                System.out.println(firstName);
                System.out.println(playerIGN);
                System.out.println(teamName);

            }

        } catch (Exception exception){
            System.out.println("Database error:" + exception.getMessage());
        }
    }
    public static void main(String[] args) {

        openDatabse("LeagueDatabase.db");

        ResourceConfig config = new ResourceConfig();
        config.packages("Controllers");
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
