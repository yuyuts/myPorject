package Controllers;

import Servers.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Owners/")
public class Owners {
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listOwner(){
        System.out.println("Owners/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ownerID, ownerFirst, ownerSecond, ownerIGN FROM Owners ");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("coachID",results.getInt(1));
                item.put("ownerFirst",results.getString(2));
                item.put("ownerSecond",results.getString(3));
                item.put("OwnerIGN", results.getString(4));
                list.add(item);
            }
            return list.toString();
        } catch (
                Exception exception) {
            System.out.println("Database Error:" + exception.getMessage());
            return "{\"error\":\"Unable to list items,please see server console for more info.\"}";
        }
    }
    @GET
    @Path("get/{ownerID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOwner(@PathParam("ownerID") Integer ownerID){
        try{
            if(ownerID == null){
                throw new Exception("ownerID is missing in the HTTP requests URL");
            }
            System.out.println("Owners/get/"+ownerID);
            JSONObject item = new JSONObject();
            PreparedStatement ps = Main.db.prepareStatement("SELECT ownerFirst, ownerSecond, ownerIGN FROM Owners WHERE ownerID =?");
            ps.setInt(1,ownerID);
            ResultSet results = ps.executeQuery();
            if(results.next()){
                item.put("ownerID",ownerID);
                item.put("ownerFirst",results.getString(1));
                item.put("ownerSecond",results.getString(2));
                item.put("ownerIGN",results.getString(3));
            }
            return item.toString();
        }catch (Exception exception){
            System.out.println("Database ERROR:"+exception.getMessage());
            return "{\"error\":\"Unable to list items,please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newOwner(
            @FormDataParam("ownerID") Integer ownerID,@FormDataParam("ownerFirst") String ownerFirst, @FormDataParam("ownerSecond") String ownerSecond, @FormDataParam("ownerIGN") String ownerIGN){
        try{
            if(ownerID == null|| ownerFirst == null || ownerSecond == null || ownerIGN == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request");
            }
            System.out.println("Owners/new ownerID="+ownerID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Owner(OwnerID, ownerFirst, ownerSecond, ownerIGN)VALUES (?,?,?,?)");
            ps.setInt(1,ownerID);
            ps.setString(2,ownerFirst);
            ps.setString(3,ownerSecond);
            ps.setString(4,ownerIGN);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch (Exception exception){
            System.out.println("Database Error:"+ exception.getMessage());
            return "{\"error\":\"Unable to create new Item, please see server console for more info\"}";
        }

    }
    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateCoach(
            @FormDataParam("ownerID") Integer ownerID,@FormDataParam("ownerFirst") String ownerFirst,@FormDataParam("ownerSecond") String ownerSecond, @FormDataParam("ownerIGN") String ownerIGN){
        try{
            if(ownerID == null|| ownerFirst == null || ownerSecond == null || ownerIGN == null ){
                throw new Exception("one or more of the data parameters are missing in the HTTP request.");
            }
            System.out.println("Owners/update ownerID =" + ownerID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Owners SET ownerFirst =?, ownerSecond = ?, ownerIGN =? WHERE ownerID = ?");
            ps.setString(1,ownerFirst);
            ps.setString(2,ownerSecond);
            ps.setString(3,ownerIGN);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch (Exception exception){
            System.out.println("Database Error: "+ exception.getMessage());
            return "{\"error\":\"unable to update owner. Check console for more info\"}";

        }
    }
    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String removeCoach(@FormDataParam("ownerID") Integer ownerID){
        try{
            if(ownerID == null){
                throw new Exception("one or more form data parameters are missing in the HTTP Request");
            }
            System.out.println("Owners/delete ownerID = " +ownerID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Owners WHERE ownerID = ?");
            ps.setInt(1,ownerID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch(Exception exception){
            System.out.println("Database Error:"+exception.getMessage());
            return "{\"error\":\"unable to delete owner. Check console for more info\"}";
        }
    }





    public static void insertOwner(int ownerID, String ownerFirst, String ownerSecond, String ownerIGN){//METHOD TO CREATE OWNER
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Owners (ownerID, ownerFirst,ownerSecond, ownerIGN) VALUES(?,?,?,?)");//SQL Statement to create a new owner
            ps.setInt(1,ownerID);//prepared statement which links to the first ?
            ps.setString(2,ownerFirst);//prepared statement which links to the second ?
            ps.setString(3,ownerSecond);//prepared statement which links to the third ?
            ps.setString(4,ownerIGN);//prepared statement which links to the fourth ?
            ps.execute();//executes the prepared statements
            System.out.println("Successfully Updated");//SOUT to test if it was successful
        }catch(Exception exception){
            System.out.println("Database Error");//SOUT which will print if the try doesnt work
        }
    }

    public static void updateOwner(int ownerID, String ownerFirst, String ownerSecond, String ownerIGN){//METHOD TO UPDATE AN  OWNER
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Owners SET ownerFirst =?, ownerSecond = ?, ownerIGN =? WHERE  ownerID =?");//SQL STATEMENT TO UPDATE OWNER IF ownerID =?
            ps.setString(1,ownerFirst);//Prepared statement which links to the first?
            ps.setString(2,ownerSecond);//Prepared statement which links to the second ?
            ps.setString(3,ownerIGN);//Prepared Statement which links to the third ?
            ps.setInt(4,ownerID);//Prepared statement which links to the fourth  ?
            ps.execute();//executes the prepared statements
            System.out.println("Successfully Updated");//SOUT to test if it was successful
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which will print if the try doesnt work
        }
    }

    public static void deleteOwner(int ownerID){//METHOD TO DELETE OWNER
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Owners WHERE ownerID = ?");//SQL CODE TO DELETE OWNER WITH OWNER ID =?
            ps.setInt(1,ownerID);//prepared statement which links to the first ?
            ps.execute();//executes prepared statement
            System.out.println("Successfully Deleted");//SOUT to test if it was successful

        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which will output if the try doesnt work
        }
    }

    public static void listOwners(){//METHOD to list owners
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT String ownerFirst, String ownerSecond, String ownerIGN FROM Owners ");//SQL statement which will list all the owners in the Owners table
            ResultSet results = ps.executeQuery();//impporting the ResultSet
            while(results.next()){//while loop while the results can go next
                String ownerFirst =results.getString(1);//links to the first column in the table
                String ownerSecond = results.getString(2);//links to the second column in the table
                String ownerIGN = results.getString(3);//links to the third column in the table
                System.out.println("Owners Name:"+ownerFirst +" "+ownerSecond+",");//Outputs the first and second name
                System.out.println("Owners IGN:"+ownerIGN+"\n");//Outputs the owner IGN

            }
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which will out put if the try doesnt work
        }
    }
}
