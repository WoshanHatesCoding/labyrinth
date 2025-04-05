package labirithm;
import java.sql.*;
public class DB {


    private static Connection con;
    private static  Statement stmt;

    /**
     * Connects to the MySQL database using the provided connection parameters.
     * This method should be called before performing any database operations.
     */
    private static void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/labirithm", "java", "java");
            stmt= con.createStatement();
        }
        catch(Exception e){ System.out.println(e);}
    }


    public static void newResult(String name,int points, long time){
        connect();
        try{
            stmt.execute(String.format("INSERT INTO `scores`  VALUES ('%s',%d,UTC_TIMESTAMP(),%d)",name,points,time));
        }catch(Exception e){ System.out.println(e);}
    }


    public static ResultSet getLeaderBoard(){
        connect();
        try{
            return stmt.executeQuery("SELECT * FROM `scores` ORDER BY `userScore` DESC LIMIT 10;");

        }catch(Exception e){ System.out.println(e);}
        return null;
    }
}