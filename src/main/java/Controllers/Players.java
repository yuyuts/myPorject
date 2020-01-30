package Controllers;

import Servers.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("players/")
public class Players {
    @GET// GET  method for API since its listing
    @Path("list") //This is the path command
    @Produces(MediaType.APPLICATION_JSON)//we are using JSON
    public String listPlayers() {
        System.out.println("players/list"); // Additional Print Statements for debugging purposes
        JSONArray list = new JSONArray(); //A new json array is constructed with a series of json objects with values from sql query.
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT playerID, playerIGN,firstName, Nationality,playerBio FROM Player");//sql Code
            JSONObject item = new JSONObject();//new json object
            ResultSet results = ps.executeQuery();
            while (results.next()) {//while loop till there are no more teams
                item.put("playerID", results.getInt(1));//links to playerID in sql statement
                item.put("playerIGN", results.getString(2));//links to playerIGN in sql statement
                item.put("firstName", results.getString(3));//links to firstName in sql statement
                item.put("Nationality", results.getString(4));//links to nationality in sql statement
                item.put("playerBio",results.getString(5));//links to player bio in sql statement
                list.add(item);
            }return list.toString();//returns list
        } catch (Exception exception) { //if there is an error
            System.out.println("Database error:" + exception.getMessage());
            return "{\"error\":\"unable to list items, please see server console for more info.\"}";//returns error message
        }
    }

    @GET// this API selects and lists a player depending on the playerID is inputted
    @Path("get/{playerID}")//path
    @Produces(MediaType.APPLICATION_JSON)//json
    public String getPlayer(@PathParam("playerID") Integer playerID){//path param is used for playerID because the value is directed straight to the end of the HTTP request
        try{
            if(playerID == null){//if the plyaerID doesnt exist
                throw new Exception("playerID is missing in the HTTP request");//error message
            }
            System.out.println("Players/get/"+ playerID);//SOUT for debugging purposes
            JSONObject item = new JSONObject();//new JSON object is constructed with values from the sql query.
            PreparedStatement ps = Main.db.prepareStatement("SELECT playerIGN,firstName, Nationality,playerBio FROM Player WHERE playerID =?");//select sql statement
            ps.setInt(1,playerID);// the playerID value
            ResultSet results = ps.executeQuery();
            if(results.next()){//if statment not a while since its only one item
                item.put("playerID", playerID);//links to the playerID in the sql statement
                item.put("playerIGN",results.getString(1));//links to the playerIGn in the sql statement
                item.put("firstName",results.getString(2));//links to the firstName in the sql statement
                item.put("Nationality",results.getString(3));//links to the Nationality in the sql statment
                item.put("playerBio", results.getString(4));//links to playerBio in the SQL Stamtent
            }
            return item.toString();//returns item
        }catch(Exception exception){//goes to this if an error occurs
            System.out.println("Database Error:"+exception.getMessage());
            return "{\"error\":\"unable to list items, please see server console for more info.\"}";//returns error message
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newOwners(
            @FormDataParam("playerID") Integer playerID, @FormDataParam("playerIGN") String playerIGN, @FormDataParam("firstName") String firstName, @FormDataParam("Nationality") String Nationality,@FormDataParam("playerBio") String playerBio, @FormDataParam("regionID") Integer regionID, @FormDataParam("teamID") Integer teamID){
        try{
            if(playerID == null||playerIGN == null || firstName == null || Nationality == null || playerBio == null|| regionID == null|| teamID == null){
                throw new Exception("One or more of the parameters are missing in the HTTP Request");
            }
            System.out.println("Owners/new:" + playerID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Owners(playerID, playerIGN, firstName, Nationality, playerBio,regionID,teamID) VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1,playerID);
            ps.setString(2,playerIGN);
            ps.setString(3, firstName);
            ps.setString(4, Nationality);
            ps.setString(5,playerBio);
            ps.setInt(6,regionID);
            ps.setInt(7,teamID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch(Exception exception){
            System.out.println("Database Error:"+ exception.getMessage());
            return "{\"error\":\"unable to add player please check console for more information\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updatePlayer(
            @FormDataParam("playerID") Integer playerID, @FormDataParam("playerIGN") String playerIGN, @FormDataParam("firstName") String firstName, @FormDataParam("Nationality") String Nationality,@FormDataParam("playerBio") String playerBio, @FormDataParam("regionID") Integer regionID, @FormDataParam("teamID") Integer teamID){
        try {
            if(playerID == null||playerIGN == null || firstName == null || Nationality == null || playerBio == null|| regionID == null|| teamID == null){
                throw new Exception("One or more form of data parameters are missing int he HTML request");
            }
            System.out.println("Players/update playerID="+playerID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Owners SET playerIGN =?, firstName = ?, Nationality =?, playerBio = ?, regionID = ?, teamID = ? WHERE playerID =?");
            ps.setString(1, playerIGN);
            ps.setString(2,firstName);
            ps.setString(3, Nationality);
            ps.setString(4,playerBio);
            ps.setInt(5,regionID);
            ps.setInt(6,teamID);
            ps.setInt(7,playerID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception){
            System.out.println("Database Error" + exception.getMessage());
            return "{\"error\":\"Unable to updatePlayer. Check console for more information\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String removePlayer(
            @FormDataParam("playerID") Integer playerID){
        try{
            if(playerID == null){
                throw new Exception("one or more form data parameters are missing the HTTP request");
            }
            System.out.println("Players/delete playerID = ?" + playerID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM OWNERS WHERE playerID = ?");
            ps.setInt(1,playerID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch (Exception exception){
            System.out.println("Database Error:" + exception.getMessage());
            return "{\"error\":\"Unable to delete players. Check Console for more information\"}";
        }
    }



    public static void insertPlayer(int playerID, String playerIGN, String firstName, String Nationality,int teamID) {//calls for these parameters
        try {
            PreparedStatement ps = Main.db.prepareStatement(
                    "Insert INTO Players(playerID, playerIGN, firstName, Nationality,teamID)VALUES (?.?.?.?,?)");//sql code to insert values into the fields
            ps.setInt(1, playerID);//prepared statement 1 is the value going to the first "?" therefore the player ID
            ps.setString(2, playerIGN);//prepared statement 2 is the value going to the second "?" therefore the playerIGN
            ps.setString(3, firstName);//prepared statement 3 is the value going to the third "?" therefore the firstName
            ps.setString(4, Nationality);//prepared statement 4 is the value going to the fourth "?" therefore Nationality
            ps.setInt(5,teamID);//prepared statement 5 is the value going into the fight "?" therefore teamID

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





