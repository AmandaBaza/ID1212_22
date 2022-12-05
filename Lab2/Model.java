import java.net.Socket;
import java.util.Random;

public class Model {
    String cookie;
    Socket socket;
    int numberOfGuesses;
    boolean guessHigher;
    boolean win;
    private int correctNumber;

    Model(Socket playerSocket, Integer cookie){
        this.socket = playerSocket;
        this.cookie = cookie.toString();
        this.numberOfGuesses = 0;
        this.guessHigher = false;
        this.win = false;
        System.out.println("NEW MODEL!!");
        Random rand = new Random();
        this.correctNumber = rand.nextInt(100); //Get Random number between 0-100
    }

    public void newGuess (Integer guess){
        System.out.println("Correct:" + this.correctNumber);
        //update number of guesses if a guess was made
        if(guess != null){
            this.numberOfGuesses++;
            System.out.println(numberOfGuesses);
            //check if its a win
            if (guess == this.correctNumber) {
                this.win = true;
            }//else if it's a higher number
            else if (guess < this.correctNumber) {
                this.guessHigher = true;
            }//else guessHigher = false;
            else {
                this.guessHigher = false;
            }
        }
        View.response(this);
    }
}
