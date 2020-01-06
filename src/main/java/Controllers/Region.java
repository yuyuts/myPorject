package Controllers;

import Servers.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Regions/")
public class Region {
    //APIS
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRegions(){
        System.out.println("Regions/list");
        JSONArray list = new JSONArray();
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT regionID, regionName FROM Region");
            ResultSet results = ps.executeQuery();
            while(results.next()){
                JSONObject item = new JSONObject();
                item.put("regionID",results.getInt(1));
                item.put("regionName",results.getString(2));
                list.add(item);
            }return list.toString();
        }catch (Exception exception){
            System.out.print("Database Error");
            return"{\"error\"Unable to list items,please see server console for more info.\"}";
        }
    }

    //Insert
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newRegion(
        @FormDataParam("regionID") Integer regionID, @FormDataParam("regionName") String regionName){
            try {
                if (regionID == null || regionName == null ) {
                    throw new Exception("One or more parameter were missing");
                }
                System.out.println("Regions/new regionID =" + regionID);
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Region (regionID, regionName) VALUES(?,?)");
                ps.setInt(1, regionID);
                ps.setString(2, regionName);
                ps.execute();
                return "{\"status\": \"OK\"}";
            } catch (Exception exception) {
                System.out.println("Database Error" + exception.getMessage());
                return "{\"error\"Unable to list items,please see server console for more info.\"}";
            }
        }
        //Update Player
    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateRegion(
            @FormDataParam("regionID") Integer regionID, @FormDataParam("regionName") String regionName){
        try {
            if(regionID == null|| regionName == null){
                throw new Exception("One or more parameters are missing - Unable to execute HTML request");
            }
            System.out.println("Region/update regionID ="+regionID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Region SET regionName = ? WHERE regionID = ?");
            ps.setString(1,regionName);
            ps.setInt(2, regionID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        }catch (Exception exception){
            System.out.println("Database Error:"+exception.getMessage());
            return "{\"error\"Unable to update\"}";

        }
    }






    //METHODS
    public static void insertRegion(int regionID, String regionName){ // METHOD TO CREATE A NEW REGION
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Region (regionID, regionName) VALUES (?,?)");//SQL Statement for creating a new region
            ps.setInt(1,regionID);//Prepared Statement which link to the first ?
            ps.setString(2,regionName);//Prepared Statement which links to the second ?
            ps.execute();//executes the prepared statement
            System.out.println("Successfully Created new Region");//SOUT which will clarify if it has worked successfully
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void updateRegion(int regionID, String regionName){//METHOD to update the region Name
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Regoion SET regionName = ? WHERE regionID = ?");//SQL STATEMENT to update region name with the region ID of ?
            ps.setString(1, regionName);//links to the first ?
            ps.setInt(2,regionID);//links to the second ?
            ps.execute();//executes prepared statements
            System.out.println("Successfully Updated Region");//SOUT which will output if the code worked sucessfully
        }catch(Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void deleteRegion(int regionID){//METHOD to delete region from table
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Region WHERE regionID =?");//SQL statement to delete region with the region id ?
            ps.setInt(1,regionID);//links to the ?
            ps.execute();//executes the prepared statement
            System.out.println("Successfully Deleted Region");//SOUT which will output if the code has worked successfully
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void listRegion(){//METHOD to list all the regions
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT regionID, regionNAME FROM Region ");//SQL Statement to list all the regions
            ResultSet results = ps.executeQuery();//RESULTS SET IMPORTED
            while (results.next()){//While loop
                int regionID = results.getInt(1);//Links to the first column in the table
                String regionName = results.getString(2);//links to the second column in the table
                System.out.print("Region ID:"+regionID+",");//OUTPUTS the region ID
                System.out.println("Region Name"+regionName+"\n");//OUTPUTS regionName
            }
        }catch(Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
}
