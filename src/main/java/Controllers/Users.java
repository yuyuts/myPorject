package Controllers;

import Servers.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Users{
    public static void insertUsers(int userID, String userName, String userPassword, String userEmail){//MMEHOD TO CREATE A NEW USER
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO USERS(userID, userName, userPassword, userEmail)VALUES (?,?,?,?)");//SQL STATEMENT TO CREATE A NEW USER
            ps.setInt(1,userID);//Pepared statement which links to the first ?
            ps.setString(2,userName);//prepared statement which links to the second ? d
            ps.setString(3,userPassword);//prepared statement which links to the third ?
            ps.setString(4, userEmail);//prepared statement which links to the fourth ?
            ps.execute();//executes the prepared statement
            System.out.println("Successfully  Inserted ");//SOUT OUTPUTS to show that it has been successfully created
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
    public static void updateUsers(int userID, String userName,String userPassword, String userEmail){//METHOD TO UPDATE A USER
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET userName = ?,userPassword =?, userEmail = ? WHERE userID = ?");//SQL statement which updates USER
            ps.setString(1, userName);//Prepared statement which links to the first ?
            ps.setString(2,userPassword);//Prepared Statement which links to the second ?
            ps.setString(3,userEmail);//prepared statement which links to the the third ?
            ps.setInt(4,userID);//prepared statement which links to the fourth ?
            ps.execute();//Executes the prepared statements
            System.out.println("Successfully Updated");//SOUT OUTPUTS to show that user has been successfully deleted
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }

    public static void deleteUser(int userID){//METHOD TO DELETE USER
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE userID = ?");//SQL STATEMENT TO DELTE USER
            ps.setInt(1,userID);//links to the ?
            ps.execute();//executes the prepared statements
            System.out.println("Successfully deleted");//SOUT OUTPUTS TO SHOW THAT THE USER HAS BEEN SUCCESSFULLY OUTPUTTED
        }catch (Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }

    public static void listUsers(){//METHOD TO LIST USERS
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT userID,userName,userPassword,userEmail FROM Users");//SQL statement TO LIST ALL PLAYERS
            ResultSet results = ps.executeQuery();//IMPORT RESULT SET
            while(results.next()){//While loop
                int userID = results.getInt(1);//userID = what ever is on the first column
                String userName = results.getString(2);//username = what ever is on the second column
                String userPassword = results.getString(3);//userPassword = what ever is on the third column
                String userEmail = results.getString(4);//userEmail = what ever is on the fourth ?
                System.out.println("User ID :"+ userID+",");//OUTPUTS USERID
                System.out.println("User Name:"+ userName +",");///OUTPUTS USERNAME
                System.out.println("User Password:"+ userPassword+",");//OUTPUTS USER PASSWORD
                System.out.println("User Email"+ userEmail+"\n");//OUTPUTS USER EMAIL


            }

        } catch(Exception exception){
            System.out.println("Database Error");//SOUT which if the code doesnt work will be outputted
        }
    }
}