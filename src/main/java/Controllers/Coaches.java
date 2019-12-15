package Controllers;

import Servers.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.processing.Generated;
import javax.validation.constraints.Positive;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Coaches/")
public class Coaches {
    //APIS
    //GETTING ALL
    @GET//GET REQUEST
    @Path("list") //declaring its a list
    @Produces(MediaType.APPLICATION_JSON)//using json
    public String listCoach(){
        System.out.println("Coaches/list");//API PATH
        JSONArray list = new JSONArray();//creating new JSON array
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT coachID,coachIGN,coachFirstName, coachLastName,teamID FROM COACHES");//SELECT SQL STATEMENT
            JSONObject item = new JSONObject();
            ResultSet results= ps.executeQuery();//PREPARED STATEMENTS
            while(results.next()){//while loop, stops when there are no more coaches
                item.put("coachID",results.getInt(1));//gets the coachID
                item.put("CoachIGN",results.getString(2));//gets the coachIGN
                item.put("coachFirstName",results.getString(3));//gets the coachFirstName
                item.put("coachLastName",results.getString(4));//gets the coachLastName
                item.put("teamID",results.getInt(5));//gets the teamID
                list.add(item);
            }
            return list.toString();
        }catch (Exception exception){
            System.out.println("Database Error:"); //ERROR MESSAGE
            return"{\"error\":\"Unable to list items,please see server console for more info.\"}";
        }
    }
//get
 /*
    @GET
    @Path("get/{coachID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("coachID") Integer coachID){
        if(coachID ==null){
            throw new Exception("Coach ID is missing from the HTTP requests URl.");
        }
        System.out.println("users/get/"+ coachID);
        JSONObject item = new JSONObject();
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT coachIGN, coachFirstName, coachLastName, teamID FROM COACHES WHERE coachID = ?");
            ps.setInt(1, coachID);
            ResultSet results = ps.executeQuery();
            if(results.next()){
                item.put("id",coachID);
                item.put("coachIGN", results.getString(2));

            }
            return item.toString();
        } catch (Exception exception){
            System.out.println("Database Error:"+ exception.getMessage());
            return"{\"error\":\"Unable to list items, please see server console for more info .\"}";
        }
        }
 */

    //insertCoach
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertCoach(
            @FormDataParam("coachID")Integer coachID, @FormDataParam("coachIGN") String coachIGN, @FormDataParam("coachFirstName") String coachFirstName, @FormDataParam("coachLastName") String coachLastName, @FormDataParam("teamID") Integer teamID, @CookieParam("token")String token) {
        try {
            if (coachID == null ||coachIGN == null || coachFirstName == null || coachLastName == null || teamID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP Request");
            }
            System.out.println("Coaches/new coachID =" + coachID);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Coaches(coachID,coachIGN, coachFirstName, coachLastName, teamID) VALUES (?,?,?,?,?)");
            ps.setInt(1, coachID);
            ps.setString(2, coachIGN);
            ps.setString(3, coachFirstName);
            ps.setString(4, coachLastName);
            ps.setInt(5,teamID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database Error");
            return "{\"error\":\"Unable to create new item, please see server console for more info.\"}";
        }
    }

    //Update Coach
    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateCoach(
            @FormDataParam("coachID") Integer coachID,@FormDataParam("coachIGN") String coachIGN,@FormDataParam("coachFirstName") String coachFirstName,@FormDataParam("coachLastName") String coachLastName, @FormDataParam("teamID") Integer teamID,@CookieParam("token")String token){
        try{
            if(coachID == null || coachIGN == null || coachFirstName == null || coachLastName == null || teamID == null){
                throw new Exception("One or more form data parameters are missing from the HTTP request.");
            }
            System.out.println("Coaches/update id= "+ coachID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Coaches SET coachID = ?,coachIGN =?,coachFirstName =?, coachLastName =?, teamID = ?");
            ps.setInt(1,coachID);
            ps.setString(2,coachIGN);
            ps.setString(3,coachFirstName);
            ps.setString(4, coachLastName);
            ps.setInt(5,teamID);
            ps.execute();
            return "{\"status\":\"OK\"}";
        }catch (Exception exception){
            System.out.println("Database Error");
            return "{\"error\":\"Unable to update item, please see the console for more info.\"}";
        }
    }





    //METHODS

    public static void insertCoach(int coachID, String coachFirstName, String coachLastName, int teamID){ //calls for these parameters
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Coaches(coachID, coachFirstName, coachLastName,teamID) VALUES (?,?,?,?)"); //prepared statement with the SQL CODE to insertCoach
            ps.setInt(1, coachID);//links to the first ? saying to set the coachID as whatever is on the first  element on the parameter
            ps.setString(2, coachFirstName);//links to the second ? to set the coachFistName as what is set on the second element in the parameter
            ps.setString(3, coachLastName);//links to the third ? to set the coachLastName as what is set on the third element in the parameter
            ps.setInt(4, teamID);//link to the fourth ? to set the teamID as what is set on the fourth element in the parameter

            ps.execute();//executes the prepared statements

            System.out.println("Coach Inserted"); //SOUT testing to confirm that the insertCoach method works

        } catch (Exception exception) {
            System.out.println("Database Error"); //SOUT message outputted to say that there was a problem with executing the prepared statements
        }


    }




    public static void deleteCoach(int coachID) {//calls for the parameter of coachID for delete coach
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Coaches WHERE coachID = ?");//prepared statement with the SQL Code to delete coach with the PlayerID equaling what was set on the parameter
            ps.setInt(1, coachID);// prepared statement which links to the ? to what the coachID is set in the parameter
            ps.execute();//prepared statement is executed
            System.out.println("coach deleted");//SOUT testing to confirm that the deleteCoach method was successful
        } catch (Exception exception) {
            System.out.println("Database Error");//SOUT message outputted to say that there was a problem with executing the preparedStatement
        }
    }


    public static void updateCoach(int coachID, String coachFirstName, String coachLastName, int teamID){//calls for the parameters to update coach
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Coaches SET coachFirstName =?, coachLastName =?, teamID =? WHERE coachID =?");//prepared statement with the SQL code to update coach with the player ID equaling what is in the parameter
            ps.setString(1,coachFirstName);//prepared statement which links to the first "?"
            ps.setString(2,coachLastName);//prepared statement which links to the second "?"
            ps.setInt(3,teamID);//prepared statement which link to the third "?"
            ps.setInt(4,coachID);//prepared statement which link to the fourth "?"
            ps.execute();//executes the prepared statement
            System.out.println("coach Updated");//SOUT to verify that the query has worked


        }catch (Exception exception){
            System.out.println("Database Error");//SOUT to show there was an error with the database
        }
    }


    public static void listCoach(int coachID){//lists coach method with coachID as parameter
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT coachFirstName, coachLastName,teamID FROM Coaches WHERE coachID =?");//SQL STATEMENT listing all the values in coaches table where the coach ID = the value given in the parameter
            ResultSet results = ps.executeQuery(); //imported resultSet and makes results = the execute prepared statement query
            while(results.next()){//while loop to list the values
                String coachFirstName = results.getString(1);//gets the coaches First Name
                String coachLastName = results.getString(2);//gets the coaches second Name
                int teamID = results.getInt(3);// gets the coaches teamID
                System.out.println("ID:" + coachID+ ",");//Outputs the coachID
                System.out.println("FirstName:" + coachFirstName +",");//outputs the coaches first name
                System.out.println("LastName:" + coachLastName + ",");//outputs the coaches second name
                System.out.println("TeamID:"+ teamID +"\n");//outputs the coaches teamID
            }
        }catch(Exception exception){
            System.out.println("Database Error"); //SOUT to show if there is a database error
        }
    }
}