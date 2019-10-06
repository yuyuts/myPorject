package Controllers;

import Servers.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Players {
    @GET // GET  method for API since its listing
    @Path("list") //This is the path command
    @Produces(MediaType.APPLICATION_JSON)//we are using JSON

    public String listPlayers() {
        System.out.println("players/list"); // Additional Print Statements for debugging purposes
        JSONArray list = new JSONArray(); //SIMPLE JSON QUERY - A new JSON Object is created
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT playerID, playerIGN,firstName, Nationality,teamName FROM Players");//sql Code
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("playerID", results.getInt(1));
                item.put("playerIGN", results.getString(2));
                item.put("firstName", results.getString(3));
                item.put("Nationality", results.getString(4));
                item.put("teamName", results.getString(5));
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error:" + exception.getMessage());
            return "{\"error\":\"unable to list items, please see server console for more info.\"}";
        }
    }
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPlayers(){
        @FormDataParam("playerID") Integer playerID, @FormDataParam("playerIGN") String playerIGN, @FormDataParam("firstName") String firstName, @FormDataParam("Nationality") String Nationality, @FormDataParam("teamName") String teamName)
        {
            try{
                if (playerID == null||playerIGN == null ||firstName == null|| Nationality == null|| teamName == null ){
                    throw new Exception("One or more data parameters are missing in the HTTP request");

                }
                System.out.println("player/new playerID ="+ playerID);
                PreparedStatement ps == Main.db.prepareStatement("INSERT INTO  Players(playerID, playerIGN, firstName, Nationality, teamName )VALUES (?,?,?,?,?)");
                ps.setInt(1,playerID);
                ps.setString(2,playerIGN);
                ps.setString(3,firstName);
                ps.setString(4,Nationality);
                ps.setString(5,teamName);
                ps.execute();
                return"{\"status\":\"OK\"}";
            } catch (Exception exception){
                System.out.println("Database error:"+exception.getMessage());
                return "{\"error\":\"Unable to create new item, please see server console for more info.\"}";
            }

        }


    }
}


