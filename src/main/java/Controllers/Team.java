package Controllers;
import Servers.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Team{
    public static void insertTeam(int teamID, String teamName,String Earnings, String teamBio, int teamAccountID){//METHOD to create a new team
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Teams(teamID, teamName, Earnings, teamBio, teamAccountID) VALUES(?,?,?,?,?)");//SQL STATEMENT TO CREATE A NEW TEAM
            ps.setInt(1,teamID);// prepared statement lonks to the first ?
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

    public static void updateTeam(int teamID, String teamName, String Earnings, String teamBio, int teamAccountID){ //METHOD to update a team
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE TEAMS SET teamName = ?, Earnings =?, teamBio =?, teamAccountID =? WHERE teamID = ?");//SQL UPDATES TEAM WITH THE TEAMID = ?
            ps.setString(1,teamName);//prepared statement which links to the first ?
            ps.setString(2,Earnings);//prepared statement which links to the second ?
            ps.setString(3,teamBio);//prepared statement which links to the third ?
            ps.setInt(4,teamAccountID);//prepared statement which link to the fourth ?
            ps.setInt(5,teamID);;//prepared statement which links to the fifith ?
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
            PreparedStatement ps = Main.db.prepareStatement("SELECT teamID, teamName, Earnings, teambio FROM Teams");//SQL Statement to SELECT all the collumns from TEAMS
            ResultSet results = ps.executeQuery();//IMPORT RESULT SET
            while(results.next()) {//while loop
                int teamID = results.getInt(1);//sets the teamID to equal what ever is on the first column
                String teamName = results.getString(2);//sets the teamName to equal to what ever is on the second column
                String Earnings = results.getString(3);//sets the Earnings to equal to what ever is on the third column
                String teamBio = results.getString(4);//sets the teamBio to equal to what ever is on the fourth column
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




