/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L4;

/**
 *
 * @author Amanda
 */
public class Result {
    public String score;
    private String subject;
    
    public Result(){
    }
    
    public Result(String subject, String score){
        this.subject = subject;
        this.score = score;
    }
    
    public String getScore(){
        return this.score;
    }
     public String getSubject(){
        return this.subject;
    }
    
}
