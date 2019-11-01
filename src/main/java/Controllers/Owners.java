package Controllers;

import Servers.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Owners {
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
