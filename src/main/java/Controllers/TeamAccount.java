package Controllers;

import Servers.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeamAccount {
    public static void insertTeamAccount(int teamAccountID, String teamUsername, String teamPassword){//METHOD TO CREATE A TEAM ACCOUNT
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO teamAccount (teamAccountID, teamUsername, teamPassword) VALUES(?,?,?)");//sql statement to create a new player account
            ps.setInt(1,teamAccountID);//Prepared Statement which links to the first ?
            ps.setString(2,teamUsername);//Prepared Statement which links to the second ?
            ps.setString(3,teamPassword);//prepared statement which link to the third ?
            ps.execute();//executes the statement
            System.out.println("Inserted Team Account");//SOUT OUTPUTS TO show that team account  has successfully been created
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void updateTeamAccount(int teamAccountID, String teamUsername, String teamPassword){//MEHOD TO UPDATE A NEW PLAYER ACCOUNT
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE teamAccount SET teamUsername =?, teamPassword =? WHERE teamAccountID = ?");//SQL STATEEMENT OT UPDATE PLAYER ACCOUND THAT HAS THE PLAYER ID OF ?
            ps.setString(1,teamUsername);//prepared statement to link to the first ?
            ps.setString(2,teamPassword);//prepared statement to link to the second ?
            ps.setInt(3,teamAccountID);//Prepared statement which link to the third ?
            ps.execute();//EXCCUTES THE PREPARED STATEMENT
            System.out.println("Successfully Updated the Team Account");//outputs to show that the team account has been successfully updated
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void deleteTeamAccount(int teamAccountID){//method to deltte a team Account
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM teamAccount WHERE teamAccountID = ?");//SQL STATEMENT WHICH DELETES A TEAM ACOCOUNT WITH THE TEAMACCOUND ID OF ?
            ps.setInt(1,teamAccountID);//links to the ?
            ps.execute();//executes the prepared statement
            System.out.println("Successfully Deleted teamAccount");//Outputs to show that the
        }catch(Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void listTeamAccount(){//METHOD TO LIST TEAM ACCOUNTS
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT teamAccountID, teamUsername, teamPassword FROM teamAccount ");//SQL STATEMENT TO LIST ALL THE TEAM ACCOUNTS
            ResultSet results = ps.executeQuery();//IMPORT RESULTS SET
            while (results.next()){// while loop
                int teamAccountID = results.getInt(1);// teamAccount ID = what ever is on the first column
                String teamUsername = results.getString(2);//team Username = to what ever is on the second column
                String teamPassword = results.getString(3);//team password = to what ever is on the third column
                System.out.println("Team Account ID:"+teamAccountID+",");//OUTPUTS THE TEAM ACCOUNT ID
                System.out.println("Team Username:"+teamUsername+",");//OUTPUTS THE TEAM USERNAME
                System.out.println("Team Password:"+teamPassword+"\n");//OUTPUTS THE TEAM PASSWORD
            }
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
}
