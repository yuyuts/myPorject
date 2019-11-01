package Controllers;

import Servers.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.annotation.Target;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Players {
    @GET// GET  method for API since its listing
    @Path("list") //This is the path command
    @Produces(MediaType.APPLICATION_JSON)//we are using JSON
    public String listPlayers() {
        System.out.println("players/list"); // Additional Print Statements for debugging purposes
        JSONArray list = new JSONArray(); //SIMPLE JSON QUERY - A new JSON Object is created
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT playerID, playerIGN,firstName, Nationality,teamID,teamBio   FROM Players");//sql Code
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("playerID", results.getInt(1));
                item.put("playerIGN", results.getString(2));
                item.put("firstName", results.getString(3));
                item.put("Nationality", results.getString(4));
                item.put("teamID", results.getString(5));
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error:" + exception.getMessage());
            return "{\"error\":\"unable to list items, please see server console for more info.\"}";
        }
    }

    public static void insertPlayer(int playerID, String playerIGN, String firstName, String Nationality) {//calls for these parameters
        try {
            PreparedStatement ps = Main.db.prepareStatement(
                    "Insert INTO Players(playerID, playerIGN, firstName, Nationality)VALUES (?.?.?.?)");//sql code to insert values into the fields
            ps.setInt(1, playerID);//prepared statement 1 is the value going to the first "?" therefore the player ID
            ps.setString(2, playerIGN);//prepared statement 2 is the value going to the second "?" therefore the playerIGN
            ps.setString(3, firstName);//prepared statement 3 is the value going to the third "?" therefore the firstName
            ps.setString(4, Nationality);//prepared statement 4 is the value going to the fourth "?" therefore Nationality

            ps.execute();//executes the prepared statements
            System.out.println("inserted");

        } catch (Exception exception) { //if the parameters dont work or an database error
            System.out.println("Database Error:");//the database error message
        }
    }
    public static void deletePlayer(int playerID){//delete player method
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Players WHERE playerID = ?");//SQL Statement saying to delete everything with the playerID
            ps.setInt(1,playerID);//The playerID to remove
            ps.execute();//executes
            System.out.println("Success:");//testing if the code works


        }catch (Exception exception){
            System.out.println("Database Error:");//if there is no playerID with that value
        }
    }

    public static void listPlayer(){
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT playerID, playerIGN, firstName, Nationality,teamID FROM Players WHERE playerID = ?");
            ResultSet results = ps.executeQuery();
            while(results.next()){
                int playerID = results.getInt(1);
                String playerIGN = results.getString(2);
                String firstName = results.getString(3);
                String Nationality = results.getString(4);
                int teamID = results.getInt(6);
                System.out.println("ID:"+playerID +",,");
                System.out.println("firstName:"+firstName+",");
                System.out.println("Nationality"+Nationality+",");
                System.out.println("teamID"+teamID+"\n");

            }

        }catch (Exception exception){
            System.out.println("Database error");
        }
    }

    public static void updatePlayers(int playerID, String playerIGN, String Nationality,int teamID, int regionID, int playerAccountID, int playerStatisticsID){ //update Player
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Players SET playerID = ?, playerIGN =?, Nationality = ?, teamID=?, regionID =?, playerAccountID = ?. playerStatisticsID =? WHERE playerID = ?");
            ps.setString(1, playerIGN);
            ps.setString(2,Nationality);
            ps.setInt(3,teamID);
            ps.setInt(4,regionID);
            ps.setInt(5,playerAccountID);
            ps.setInt(6,playerStatisticsID);
            ps.setInt(7,playerID);

            ps.execute();
            System.out.println("Successfully Updated");

        }catch (Exception exception){
            System.out.println("Database Error:");
        }
    }







}





