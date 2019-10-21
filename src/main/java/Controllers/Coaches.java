package Controllers;

import Servers.Main;

import java.sql.PreparedStatement;

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
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Coaches SET coachID = ?, coachFirstName =?, coachLastName =?, teamID =? WHERE coachID =?");//prepared statement with the SQL code to update coach with the player ID equaling what is in the parameter
            ps.setInt(1,coachID);//prepared statement which links to the first?
            ps.setString(2,coachFirstName);
            ps.setString(3,coachLastName);
            ps.setInt(4,teamID);
            ps.execute();
            System.out.println("coach Updated");


        }catch (Exception exception){
            System.out.println("Database Error");
        }
    }
}
