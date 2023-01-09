/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L4;
import java.util.Random;
/**
 *
 * @author Amanda
 */
public class Game {
    String cookie;
    //Session socket;
    int numberOfGuesses;
    boolean guessHigher;
    boolean win;
    private int correctNumber;

    
    public Game(){
        //this.socket = playerSocket;
        //this.cookie = cookie.toString();
        this.numberOfGuesses = 0;
        this.guessHigher = false;
        this.win = false;
        Random rand = new Random();
        this.correctNumber = rand.nextInt(100); //Get Random number between 0-100
    }

    public void newGuess (Integer guess){
        //System.out.println("Correct:" + this.correctNumber);
        //update number of guesses if a guess was made
        if(guess != null){
            this.numberOfGuesses++;
            //System.out.println(numberOfGuesses);
            //check if its a win
            if (guess == this.correctNumber) {
                this.win = true;
            }//if it's a higher number
            else if (guess < this.correctNumber) {
                this.guessHigher = true;
            }//else it's a lower number
            else {
                this.guessHigher = false;
            }
        }
        //View.response(this);
    }
    public Boolean getGuessHigher(){
        return this.guessHigher;
    }
    public Boolean getWin(){
        return this.win;
    }
    
    public int getCorrectNumber(){
        return this.correctNumber;
    }
}