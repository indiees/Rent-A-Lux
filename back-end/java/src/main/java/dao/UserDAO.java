package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static User checkLogin(String email, String password) {
        //Returns 2 if employee, 3 if admin, 0 if none (in future 1 will be customer)
        try {
            // Here you prepare your sql statement
            String sql = "SELECT  `email`, `password` FROM rent-a-lux.Users WHERE `email` = '" + email + "';";
            System.out.println(sql);
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                if (Utils.passwordIsValid(password, result.getString("password"))){
                    return getPersonByEmail(result.getString("email"));
                }
                else{
                    System.out.println("Wrong password");
                }
            }
            else{
                System.out.println("no result");
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static User getPersonByEmail(String email) {
        List<User> users = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM rent-a-lux.Users WHERE email = '" + email + "';";
            System.out.println(sql);
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            if(result.next()) {
                // 2) Add it to the list we have prepared
                users.add(new User());
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!users.isEmpty()) {
            return users.get(0);
        }
        // If we are here, something bad happened
        return null;
    }
}
