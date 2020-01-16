package Controllers;
import Servers.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("team/")
public class Team {
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listPlayers() {
        System.out.println("team/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT teamID, teamName, Earnings, teamBio, standingPo, coachID,ownerID,regionID, image  FROM Teams");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("teamID", results.getInt(1));
                item.put("teamName", results.getString(2));
                item.put("Earnings", results.getString(3));
                item.put("teamBio", results.getString(4));
                item.put("standingPo", results.getString(5));
                item.put("coachID", results.getInt(6));
                item.put("ownerID", results.getInt(7));
                item.put("regionID", results.getInt(8));
                item.put("Image",results.getString(9));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
            return "{\"error\":\"Unable to list items, please see server console for more info .\"}";
        }
    }

    /*
    @GET
    @Path("get/{regionID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRegion(@PathParam("regionID") Integer regionID){
        try{
            if(regionID == null){
                throw new Exception("The regionID is missing in the HTTP request URl");
            }
            System.out.println("regionID/get"+regionID);
            JSONObject item = new JSONObject();
            PreparedStatement ps = Main.db.prepareStatement("SELECT teamID, teamName, Earnings, teamBio, StandingPo coachID, ownerID FROM Teams where regionID = ?");
            ps.setInt(1,regionID);
            ResultSet result = ps.executeQuery();
            while (result.next()){
                item.put("regionID", regionID);
                item.put("teamID", result.getInt(1));
                item.put("teamName", result.getString(2));
                item.put("Earnings",result.getString(3));
                item.put("teamBio",result.getString(4));
                item.put("standingPO",result.getString(5));
                item.put("coachID",result.getInt(6));
                item.put("ownerID",result.getInt(7));
            }
            return item.toString();
        }catch (Exception exception){
            System.out.println("Database Error:"+exception.getMessage());
            return"{\"error\":\"Unable to list items, please see server console for more info .\"}";
        }
    }
*/
    @GET
    @Path("get/{teamID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTeam(@PathParam("teamID") Integer teamID) {
        try {
            if (teamID == null) {
                throw new Exception("team ID is missing in HTTP requests URL");
            }
            System.out.println("teamID/get" + teamID);
            JSONObject item = new JSONObject();
            PreparedStatement ps = Main.db.prepareStatement("SELECT teamName, Earnings, teamBio, standingPo, coachID,ownerID,regionID, image FROM Teams where  teamID  =?");
            ps.setInt(1, teamID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("teamID", teamID);
                item.put("teamName", results.getString(1));
                item.put("Earnings", results.getString(2));
                item.put("teamBio", results.getString(3));
                    item.put("StandingPo", results.getString(4));
                item.put("coachID", results.getInt(5));
                item.put("ownerID", results.getInt(6));
                item.put("regionID", results.getInt(7));
                item.put("Image",results.getString(8));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
            return "{\"error\":\"Unable to list items, please see server console for more info .\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTeam(
            @FormDataParam("teamID") Integer teamID, @FormDataParam("teamName") String teamName, @FormDataParam("Earnings") String Earnings, @FormDataParam("teamBio") String teamBio, @FormDataParam("standingPo") String standingPo, @FormDataParam("coachID") Integer coachID, @FormDataParam("ownerID") Integer ownerID, @FormDataParam("regionID") Integer regionID,@FormDataParam("image") String image, @CookieParam("token") String token) {
        if (!Users.validToken(token)) {
            return "{\"error\":\"you dont appear to be logged in\"}";
        }
        try {
            if (teamID == null || teamName == null || Earnings == null || teamBio == null || Earnings == null || standingPo == null || ownerID == null || coachID == null || regionID == null) {
                throw new Exception("One or more of the parameters are missing in the HTTP request");
            }
            System.out.println("team/new=" + teamID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Teams (teamID, teamName, Earnings, teamBio, standingPo ownerID, coachID, regionID, Image) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, teamID);
            ps.setString(2, teamName);
            ps.setString(3, Earnings);
            ps.setString(4, teamBio);
            ps.setString(5, standingPo);
            ps.setInt(6, ownerID);
            ps.setInt(7, coachID);
            ps.setInt(8, regionID);
            ps.setString(9,image);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
            return "{\"error\":\"Unable to create new item, check console for more info.\"}";

        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateTeam(@FormDataParam("teamID") Integer teamID, @FormDataParam("teamName") String teamName, @FormDataParam("Earnings") String Earnings, @FormDataParam("teamBio") String teamBio, @FormDataParam("standingPo") String standingPo, @FormDataParam("coachID") Integer coachID, @FormDataParam("ownerID") Integer ownerID, @FormDataParam("regionID") Integer regionID,@FormDataParam("image") String image, @CookieParam("token") String token) {
        try {
            if (teamID == null || teamName == null || Earnings == null || teamBio == null  || standingPo == null || ownerID == null || coachID == null || regionID == null) {
                throw new Exception("One or more data paramerters are missing in the HTTP request");
            }
            System.out.print("team/update teamID:" + teamID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Teams SET teamName =? teamEarnings = ?, teamBio =?, Earnings =?, standingPo =?,ownerID = ?, coachID = ?, regionID =? WHERE teamID = ?");
            ps.setString(1, teamName);
            ps.setString(2, teamBio);
            ps.setString(3, Earnings);
            ps.setString(4, standingPo);
            ps.setInt(5, ownerID);
            ps.setInt(6, coachID);
            ps.setInt(7, regionID);
            ps.setInt(8, teamID);
            ps.setString(9,image);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
            return "{\"error\":\"Unable to update item, please see server console for more info.\"}";

        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String removeTeam(@FormDataParam("teamID") Integer teamID) {
        try {
            if (teamID == null) {
                throw new Exception("one data parameters are missing in the HTTP request");
            }
            System.out.println("team/delete id=" + teamID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Teams WHERE teamID = ?");
            ps.setInt(1, teamID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
            return "{\"error\":\"Unable to delete items, please see server console for more info\"}";
        }
    }
}








    /*
    public static void insertTeam(int teamID, String teamName,String Earnings, String teamBio, int teamAccountID){//METHOD to create a new team
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Teams(teamID, teamName, Earnings, teamBio, teamAccountID) VALUES(?,?,?,?,?)");//SQL STATEMENT TO CREATE A NEW TEAM
            ps.setInt(1,teamID);// prepared statement looks to the first ?
            ps.setString(2,teamName);//prepared statement which links to the second ?
            ps.setString(3,Earnings);//prepared statement which links to the third ?
            ps.setString(4,teamBio);//prepared statement which links to the fourth ?
            ps.setInt(5,teamAccountID);//pepraed statement which links to the fifth ?
            ps.execute();//executes the prepared statements
            System.out.println("Successfully inserted");//SOUT will output to show that it has successfully created a new team
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }


    public static void updateTeam(int teamID, String teamName, String Earnings, String teamBio, int teamAccountID,int standingPo){ //METHOD to update a team
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE TEAMS SET teamName = ?, Earnings =?, teamBio =?, teamAccountID =?, stadningPo = ?WHERE teamID = ?");//SQL UPDATES TEAM WITH THE TEAMID = ?
            ps.setString(1,teamName);//prepared statement which links to the first ?
            ps.setString(2,Earnings);//prepared statement which links to the second ?
            ps.setString(3,teamBio);//prepared statement which links to the third ?
            ps.setInt(4,teamAccountID);//prepared statement which link to the fourth ?
            ps.setInt(5,teamID);;//prepared statement which links to the fifith ?
            ps.setInt(6,standingPo);//prepared statement which links to the sixth ?
            ps.execute();//executes the prepared statements
            System.out.println("Successfully updated");//SOUT will output to show that is has successfully updated the team
        }catch(Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }


    public static void deleteTeam(int teamID){//METHOD TO DELETE TEAM
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM TEAMS WHERE teamID =?");//SQL Statement which deletes a team with the team ID of
            ps.setInt(1,teamID);//links to the teamID ?
            ps.execute();//Executes the prepared statement
            System.out.print("Successfully deleted");// SOUT will output to show that it has successfully deleted the team
        }catch(Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }

    public static void listTeams(){//METHOD TO LIST THE TEAMS
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT teamID, teamName, Earnings, teambio, standingPo FROM Teams");//SQL Statement to SELECT all the collumns from TEAMS
            ResultSet results = ps.executeQuery();//IMPORT RESULT SET
            while(results.next()) {//while loop
                int teamID = results.getInt(1);//sets the teamID to equal what ever is on the first column
                String teamName = results.getString(2);//sets the teamName to equal to what ever is on the second column
                String Earnings = results.getString(3);//sets the Earnings to equal to what ever is on the third column
                String teamBio = results.getString(4);//sets the teamBio to equal to what ever is on the fourth column
                int standingPo = results.getInt(5);;//sets the standingPo to equal to what ever is on the fifth column
                System.out.println("teamID:" + teamID + ",");//OUTPUTS THE TEAMID
                System.out.println("Team Name:" + teamID + ",");//OUTPUTS THE TEAMNAME
                System.out.println("Earnings:" + Earnings + ",");//OUTPUTS THE EARNINGS
                System.out.println("Team Bio:" + teamBio + "\n");//OUTPUTS THE TEAm BIO
            }


        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted

            }


        }

    }


*/

