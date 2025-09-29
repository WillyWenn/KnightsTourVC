package ktour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                knightsTourVC.draw();
            }
        }; 
        animationTimer.start();
        knightsTourVC.draw();

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

    

}