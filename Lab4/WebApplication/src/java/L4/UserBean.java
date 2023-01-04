/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserBean {
    private String username;
    private String password;
    private Boolean userExists;
    private Integer userId;
    
    //Constructor
    public UserBean(){
    }
    
    public UserBean(String username, String password){
        this.username = username;
        this.password = password;
        this.userExists = userExists();
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    public Boolean getUserExists(){
        return this.userExists;
    }
    
    private Boolean userExists(){
        
        Statement stmt = null;
        Connection conn = null;
    
        try{
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            
            DataSource ds = (DataSource)envContext.lookup("jdbc/derby");
            conn = ds.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                Integer id = rs.getInt("id");
                //if(this.username.equals(rs.getString("username")) && this.password.equals(rs.getString("password"))){
                if(this.username.equals(rs.getString("username")) && this.password.equals(rs.getString("password"))){
                    this.userId = id;
                    rs.close();
                    return true;
                }
            }
            return false;
        }catch(Exception e){
        
        } finally {
            try {
		stmt.close();
		conn.close();
            } catch (Exception e) {}
        }
        return false;
    }
    public List<String> getAllQuizzes(){
        List<String> quizzes = new ArrayList<String>();
        try{
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            
            DataSource ds = (DataSource)envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from QUIZZES");
            while(rs.next()){
                quizzes.add(rs.getString("SUBJECT"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return quizzes;
    }
    public ArrayList<Result> userResults(){
        ArrayList<Result> userResults = new ArrayList<>();
        //String[]userResults = new String[10]; 
        
        //List<String> userResults = new ArrayList<String>();
        Statement stmt = null;
        Connection conn = null;
        
        try{
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            
            DataSource ds = (DataSource)envContext.lookup("jdbc/derby");
            conn = ds.getConnection();
            stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM RESULTS WHERE USER_ID IN (" + this.userId + ")");
            ResultSet rs = stmt.executeQuery("SELECT * FROM RESULTS INNER JOIN Quizzes ON Results.quiz_id=Quizzes.id WHERE user_id IN (" + this.userId + ")");
            //SELECT * FROM RESULTS INNER JOIN Quizzes ON Results.quiz_id=Quizzes.id WHERE user_id IN (1);
            while(rs.next()){
              
                String score = Integer.toString(rs.getInt("score"));
                String subject = rs.getString("Subject");
                Result r = new Result(subject, score);
                
                //Integer empId = rs.getInt("id");
                userResults.add(r);
                //userResults.add(userId);
                //userResults.add(quizId);
                //userResults.add(score);
                
                //userResults.add(Integer.toString(rs.getInt("id")));
                //userResults.add(Integer.toString(rs.getInt("user_id")));
                //userResults.add(Integer.toString(rs.getInt("quiz_id")));
                //userResults.add(Integer.toString(rs.getInt("score")));
                //userResults.add(rs.getString("score"));   
            }
            
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
		stmt.close();
		conn.close();
            } catch (Exception e) {}
        }
         
        return userResults;
    }
    
}
