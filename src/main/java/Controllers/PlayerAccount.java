package Controllers;

import Servers.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerAccount {
    public static void insertPlayerAccount(int playerAccountID, String playerUsername, String playerPassword){//METHOD TO CREATE A NEW PLAYER ACCOUNT
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO playerAccount (playerAccountID, playerUsername, playerPassword) VALUES (?,?,?)");//SQL Statement which creates a new player account
            ps.setInt(1,playerAccountID);//Prepared statement which links to the first ?
            ps.setString(2,playerUsername);//Prepared statement which links to the second ?
            ps.setString(3,playerPassword);//Prepared statement which links to the third ?
            ps.execute();//executes the prepared statements
            System.out.println("Successfully added player Account");//SOUT which will be outputted to clarify that the code has worked
        }catch (Exception exception){
            System.out.print("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void updatePlayerAccount(int playerAccountID, String playerUsername, String playerPassword){//METHOD TO UPDATE PLAYER ACCOUNT
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE playerAccount SET playerUsername = ?, playerPassword =? WHERE playerAccountID =? ");//UPDATE NEW PLAYER ACCOUNT SQL
            ps.setString(1,playerUsername);//Prepared Statement which links to the first ?
            ps.setString(2,playerPassword);//Prepared Statement which links to the second ?
            ps.setInt(3,playerAccountID);//Prepared Statement which links to the third ?
            ps.execute();//executes the prepared statement
            System.out.println("Successfully Updated Player Account");//SOUT which will be outputted to clarify that the code works
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work, will be outputted
        }
    }
    public static void deletePlayerAccount(int playerAccountID){//METHOD TO DELETE A PLAYER ACCOUNT
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM playerAccount WHERE playerAccountID = ?");//SQL TO DELETE A PLAYER ACCOUNT WITH THE PLAYER ACCOUNT ID =?
            ps.setInt(1,playerAccountID);//links to the ?
            ps.execute();//executes the prepared statement
            System.out.println("Successfully Deleted Player");//SOUT which will be outputted to clarify that the code works
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void listPlayerAccount(){//METHOD TO LIST A PLAYER ACCOUNT
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT playerAccountID, playerUsername, playerPassword FROM playerAccount ");//SQL STATEMENT TO LIST ALL PLAYER ACCOUNTS
            ResultSet results = ps.executeQuery();//import ResultSET
            while(results.next()){//while it can get the next result set
                int playerAccountID = results.getInt(1);//links to the first column in the table
                String playerUsername = results.getString(2);//links to the second column in the table
                String playerPassword = results.getString(3);//links to the third column in the table
                System.out.println("Player Account ID:"+ playerAccountID+",");//OUTPUTS the player Account ID
                System.out.println("Players Username:"+ playerUsername+",");//OUTPUTS player Username
                System.out.println("Player Password:"+ playerPassword+"\n");//OUT PUTS player password
            }

        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }

}
