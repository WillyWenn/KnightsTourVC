package ktour;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Label;




public class KnightsTourVC {
    private App app;
    private AnchorPane anchorPane;
    private Button startButton; //starts the program
    private Button stepButton; //button to get next step
    private Label rowLabel; //text
    private Label colLabel; //text
    private TextField rowTextField; //an area where the user can input text
    private TextField colTextField; 
    private Canvas canvas; //a place to drwa graphics to
    private GraphicsContext gc; //use a GC, to draw to things

    public KnightsTourVC(App app) {
        this.app = app;
        anchorPane = new AnchorPane();

        createGui();
        attachListeners();
    }

    private void attachListeners() {
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleButtonClicks(event);
            }
        });
        stepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleButtonClicks(event);
            }
        });
    }

    /*
     * @param x x-coordinate
     * @param y y-coordinate
     * @param size size of the square in pixels
     * @param stroke size in pixels of the outline
     * @param color the color of the square
     */
    private void drawSingleSquare(int x, int y, int size, int stroke, Paint color)  {
        //first draw a black rectangle
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, size, size);

        //draw a slightly smaller, colored rectangle
        gc.setFill(color);
        gc.fillRect(x+stroke, y+stroke, size - (stroke*2), size - (stroke*2));
    }

    public void drawMoves(ArrayList<Location> locs, int xoffset, int yoffset) {
        for(Location current: locs) {
            drawSingleSquare((current.getCol()*50) + xoffset, (current.getRow()*50) + yoffset, 50, 2, Color.BLUE);
        }
    }

    //draws the chessboard
    public void draw(Location current, ArrayList<Location> locs, int board[][]) {
        int xoffset = 20;
        int yoffset = 50;
        //make a double for loop to make a chess board out of these squares
        for(int row=0; row < App.NUMROWS; row++) {
            for(int col=0; col < App.NUMCOLS; col++) {
                //
                if(app.getCurrentLoc() != null && app.getCurrentLoc().equals(row, col)) {
                    drawSingleSquare((col*50) + xoffset, (row*50) + yoffset, 50, 2, Color.CORNFLOWERBLUE);
                }
                //
                else {
                    drawSingleSquare((col*50) + xoffset, (row*50) + yoffset, 50, 2, Color.BISQUE);

                }
            }
        }
        drawMoves(locs, xoffset, yoffset);
        
    }

    private void handleButtonClicks(ActionEvent actionEvent) {
        if(actionEvent.getSource() == startButton) {
            String buttonText = startButton.getText();
            
            //if the tour is ot currently running
            if(buttonText.equals("Start") || buttonText.equals("Continue")) {
                startButton.setText("Pause");

                //if the knights tour hasn't started yet, begin a brand new tour
                if(app.getCurrentLoc() == null) {
                    try {
                        //this can crash
                        int row = Integer.parseInt(rowTextField.getText());
                        int col = Integer.parseInt(colTextField.getText());

                        Location loc = new Location(row, col);
                        app.setCurrentLoc(loc);
                    } 
                    catch(Exception e) {
                        rowTextField.setText("");
                        colTextField.setText("");
                        startButton.setText("Start");
                    }   
                }
            }
            else {
                startButton.setText("Continue");
            }
        }
    }

    private void createGui() {
        //create the GUI element
        startButton = new Button("Start");
        startButton.setPrefWidth(100);
        //anchor the element to any of the four sides
        AnchorPane.setTopAnchor(startButton, 170.0);
        AnchorPane.setRightAnchor(startButton, 140.0);
        //add the element to the pane
        anchorPane.getChildren().add(startButton);

        //create the GUI element
        stepButton = new Button("Step");
        stepButton.setPrefWidth(100);
        //anchor the element to any of the four sides
        AnchorPane.setTopAnchor(stepButton, 210.0);
        AnchorPane.setRightAnchor(stepButton, 140.0);
        //add the element to the pane
        anchorPane.getChildren().add(stepButton);

        //create the GUI element
        rowLabel = new Label("row");
        rowLabel.setPrefWidth(100);
        //anchor the element to any of the four sides
        AnchorPane.setTopAnchor(rowLabel, 100.0);
        AnchorPane.setRightAnchor(rowLabel, 200.0);
        //add the element to the pane
        anchorPane.getChildren().add(rowLabel);

        //create the GUI element
        colLabel = new Label("column");
        colLabel.setPrefWidth(100);
        //anchor the element to any of the four sides
        AnchorPane.setTopAnchor(colLabel, 130.0);
        AnchorPane.setRightAnchor(colLabel, 200.0);
        //add the element to the pane
        anchorPane.getChildren().add(colLabel);
        
        rowTextField = new TextField();
        rowTextField.setPrefWidth(50);
        AnchorPane.setTopAnchor(rowTextField, 97.0);
        AnchorPane.setRightAnchor(rowTextField, 135.0);
        anchorPane.getChildren().add(rowTextField);

        colTextField = new TextField();
        colTextField.setPrefWidth(50);
        AnchorPane.setTopAnchor(colTextField, 127.0);
        AnchorPane.setRightAnchor(colTextField, 135.0);
        anchorPane.getChildren().add(colTextField);



        canvas = new Canvas(600, 500); //a canvas that is 600x500
        gc = canvas.getGraphicsContext2D(); //draw 2d things on the canvas
        gc.setFill(Color.RED); //choose red as the color
        gc.fillRect(0, 0, 600, 500); //fill the entire area 600x500, with a red rectangle
        AnchorPane.setLeftAnchor(canvas, 50.0);
        AnchorPane.setTopAnchor(canvas, 100.0);
        anchorPane.getChildren().add(canvas); //add the canvas to the pane


    }

    public AnchorPane getPane() {
        return anchorPane;
    }
}
