package ktour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static final int NUMROWS = 5;
    public static final int NUMCOLS = 5;
    private Location currentLoc = null;
    private AnimationTimer animationTimer;

    //data structures
    HashMap<Location, ArrayList<Location>> exhaustedList; //Maps a location with the visited locations
    Stack<Location> stack; //my history of moves
    int board[][]; //the number board that shows each move in sequence

    @Override
    public void start(Stage stage) throws IOException {
        KnightsTourVC knightsTourVC = new KnightsTourVC(this);
        scene = new Scene(knightsTourVC.getPane(), 1024, 768);
        stage.setTitle("Knight's Tour");
        stage.setScene(scene);

        //initialize data structures
        exhaustedList = new HashMap<>();
        stack = new Stack<>();
        board = new int [NUMROWS][NUMCOLS];

        addToExhausted(new Location(0, 0), new Location(1, 0), true);
        addToExhausted(new Location(0, 0), new Location(1, 3), true);
        addToExhausted(new Location(0, 0), new Location(2, 3), true);

        addToExhausted(new Location(1, 0), new Location(1, 3), true);
        addToExhausted(new Location(1, 0), new Location(1, 4), true);
        deleteFromExhausted(new Location(0,0));
        System.out.println(getPossibleMoves(new Location(2, 2)));

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                knightsTourVC.draw(currentLoc, getPossibleMoves(currentLoc), board);
            }
        }; 
        animationTimer.start();
        knightsTourVC.draw(currentLoc, getPossibleMoves(currentLoc), board);


        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    public Location getCurrentLoc() {
        return currentLoc;
    }

    public void setCurrentLoc(Location currentLoc) {
        this.currentLoc = currentLoc;
    }

    public void deleteFromExhausted(Location from) {
        //System.out.println("deleteFromExhausted");
        //System.out.println("-----------");
        //System.out.println("does location exist in exhausted: " + exhaustedList.containsKey(from));
        //System.out.println("deleting " + from);
        exhaustedList.remove(from);
        //System.out.println("does location exist in exhausted: " + exhaustedList.containsKey(from));
        //System.out.println();
    }

    public void addToExhausted(Location from, Location to, boolean debug) {
        //System.out.println("addToExhausted");
        //System.out.println("-----------");
        //if Location from is not in the exhausted list
        if(!exhaustedList.containsKey(from)) {
            //System.out.println("Location " + from + " not in exhausted.");

            //add it to the list
            exhaustedList.put(from, new ArrayList<Location>());
            //add the path to the list
            exhaustedList.get(from).add(to);

            //System.out.println("added to exhausted list: " + exhaustedList.get(from));

        }
        else { //the location is already in exhausted
            //System.out.println(from + " is already in exhausted list");
            exhaustedList.get(from).add(to); //add the to location in the list
            //System.out.println("exhausted list is now " + exhaustedList.get(from));
        }
        System.out.println();
    }    

    //returns an ArrayList of all the possible moves
    //removing the moves that are out of bounds AND
    //those moves cannot be made because of the board[][] already
    //has a number on it
    public ArrayList<Location> getPossibleMoves(Location loc) {
        ArrayList<Location> moves = new ArrayList<>();

        if(loc == null) {
            return moves;
        }
    
        //[2][2] can move to ([1][0], [0][1]), ([3][0], [4][1]), ([0][3], [1][4]), ([3][4], [4][3])\
        Location up1 = new Location(loc.getRow()-2, loc.getCol()-1);
        Location up2 = new Location(loc.getRow()-2, loc.getCol()+1);
        Location right1 = new Location(loc.getRow()-1, loc.getCol()+2);
        Location right2 = new Location(loc.getRow()+1, loc.getCol()+2);
        Location down1 = new Location(loc.getRow()+2, loc.getCol()-1);
        Location down2 = new Location(loc.getRow()+2, loc.getCol()+1);
        Location left1 = new Location(loc.getRow()-1, loc.getCol()-2); 
        Location left2 = new Location(loc.getRow()+1, loc.getCol()-2);
        

        


        if(isValid(up1) && board[up1.getRow()][up1.getCol()] == 0) {
            moves.add(up1);
        }
        if(isValid(up2) && board[up2.getRow()][up2.getCol()] == 0) {
            moves.add(up2);
        }
        if(isValid(right1) && board[right1.getRow()][right1.getCol()] == 0) {
            moves.add(right1);
        }
        if(isValid(right2) && board[right2.getRow()][right2.getCol()] == 0) {
            moves.add(right2);
        }
        if(isValid(down1) && board[down1.getRow()][down1.getCol()] == 0) {
            moves.add(down1);
        }
        if(isValid(down2) && board[down2.getRow()][down2.getCol()] == 0) {
            moves.add(down2);
        }
        if(isValid(left1) && board[left1.getRow()][left1.getCol()] == 0) {
            moves.add(left1);
        }
        if(isValid(left2) && board[left2.getRow()][left2.getCol()] == 0) {
            moves.add(left2);
        }

        System.out.println(moves);
        return moves;
        
    }

    public boolean isValid(Location loc) {
        return loc.getRow() >= 0 && loc.getRow() < NUMROWS &&
            loc.getCol() >= 0 && loc.getCol() < NUMCOLS;
    }
    
}
    