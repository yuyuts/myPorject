package Controllers;

import Servers.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Coaches {
    public static void insertCoach(int coachID, String coachFirstName, String coachLastName, int teamID) { //calls for these parameters
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
