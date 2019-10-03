package Controllers;

import Servers.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Players {
    @GET // GET  method for API since its listing
    @Path("list") //This is the path command
    @Produces(MediaType.APPLICATION_JSON)//we are using JSON

    public String listPlayers() {
        System.out.println("players/list"); // Additional Print Statements for debugging purposes
        JSONArray list= new JSONArray(); //SIMPLE JSON QUERY - A new JSON Object is created
        try{
            PreparedStatement ps= Main.db.prepareStatement("SELECT playerID, playerIGN,firstName, Nationality,teamName FROM Players");
            ResultSet results = ps.executeQuery();
            while(results.next()){
                JSONObject item = new JSONObject();
                item.put("playerID",results.getInt(1));
                item.put("playerIGN",results.getString(2));
                item.put("firstName",results.getString(3));
                item.put("Nationality",results.getString(4));
                item.put("teamName",results.getString(5));
            }
            return list.toString();

        } catch (Exception exception){
            System.out.println("Database error:" + exception.getMessage());
            return"{\"error\":\"unable to list items, please see server console for more info.\"}";
        }
    }
    }


