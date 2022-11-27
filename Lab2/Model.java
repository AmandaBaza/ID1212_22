import java.net.Socket;
import java.util.Random;

public class Model {
    private Socket socket;
    int numberOfGuesses;
    boolean guessHigher;
    boolean win;
    private int correctNumber;
    Random rand = new Random();

    Model(Socket playerSocket){
        this.socket = playerSocket;
        this.numberOfGuesses = 0;
        this.guessHigher = false;
        this.win = false;
        this.correctNumber = rand.nextInt(100); //Get Random number between 0-100
    }

    public void newGuess (int guess){
        //update number of guesses
        this.numberOfGuesses++;
        //check if its a win
        if (guess == this.correctNumber){
            this.win = true;
        }//else if it's a higher number
        else if (guess < this.correctNumber){
            this.guessHigher = true;
        }//else guessHigher = false;
        else{
            this.guessHigher = false;
        }
        //set cookie?

        //call View -TODO swap new socket() with the players sockets
        View.response(this, new Socket());
    }

}
