package com.games.tictactoe;

import java.util.ArrayList;
import java.util.List;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.*;

/**
 * <h1> TicTacToeApp</h1>
 *  <p> A simple JavaFx game Tic Tac Toe, with the goal of completing 4 X/O in a row on a 5x5 board </p>
 *  <p> The game is two players only (no option to play against the computer). Player has an option to enter their names. </p>
 *  <p> The elements are set onto a grid pane. The visuals are done in css. </p>
 * @author  Lucie Saad
 * @version 1.0
 * */



public class TicTacToe extends Application {

    private boolean playable = true;

    private boolean turnX = true;
    private Tile[][] board = new Tile[5][5];
    private List<Combo> combos = new ArrayList<>();
    Label enterName;
    Label welcome;
    Label playerX, playerO;
    String name1;
    String name2;

/**
 * start() method starts running the application
 * contains all winning combinations and the child elements of the scene (incl. the playing board)
 * @param primaryStage is the primary stage containing the scene (myScene)
 * **/

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TicTacToeApp");

        //GridPane for the root node
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);

        Scene myScene = new Scene(gridPane, 1000, 500);
        primaryStage.setScene(myScene);
        myScene.getStylesheets().add("stylesheet.css");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile();

                gridPane.getChildren().add(tile);

                board[j][i] = tile;
                gridPane.setConstraints(tile, j, i);


            }
        }

        // horizontal
        for (int y = 0; y <= 4; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y], board[3][y]));
            combos.add(new Combo(board[1][y], board[2][y], board[3][y], board[4][y]));
        }

        // vertical
        for (int x = 0; x <= 4; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2], board[x][3]));
            combos.add(new Combo(board[x][1], board[x][2], board[x][3], board[x][4]));
        }

        // diagonal combinations
        combos.add(new Combo(board[0][0], board[1][1], board[2][2], board[3][3]));
        combos.add(new Combo(board[4][4], board[3][3], board[2][2], board[1][1]));
        combos.add(new Combo(board[3][0], board[2][1], board[1][2], board[0][3]));
        combos.add(new Combo(board[0][1], board[1][2], board[2][3], board[3][4]));
        combos.add(new Combo(board[0][4], board[1][3], board[2][2], board[3][1]));
        combos.add(new Combo(board[4][0], board[3][1], board[2][2], board[1][3]));
        combos.add(new Combo(board[4][1], board[3][2], board[2][3], board[1][4]));
        combos.add(new Combo(board[1][0], board[2][1], board[3][2], board[4][3]));

        //welcome label
        welcome = new Label("Welcome to the Tic Tac Toe App!");
        welcome.setMinWidth(500);
        gridPane.setConstraints(welcome, 5, 0);
        welcome.setAlignment(Pos.CENTER);


        //enter-name label
        enterName = new Label("Enter your names: ");
        enterName.setId("winner");
        enterName.setMinWidth(500);
        gridPane.setConstraints(enterName, 5, 1);
        enterName.setAlignment(Pos.CENTER);

        //create text field for player X
        playerX = new Label ("Player X: ");
        TextField txtFieldX = new TextField("Enter Player X");
        txtFieldX.getStyleClass().add("txtField");
        txtFieldX.setMaxWidth(400);
        gridPane.setConstraints(txtFieldX, 5, 2);
        txtFieldX.setOnAction((ae) -> name1 = txtFieldX.getText());


        // text field for player O
        playerO = new Label ("Player O: ");
        TextField txtFieldO = new TextField("Enter Player O");
        txtFieldO.getStyleClass().add("txtField");
        txtFieldO.setMaxWidth(400);
        gridPane.setConstraints(txtFieldO, 5, 3);
        txtFieldO.setOnAction((ae) -> name2 = txtFieldO.getText());

        //create button
        Button btnRestart = new Button("Start new game");
        gridPane.setConstraints(btnRestart,5, 4);
        btnRestart.setId("btnStart");
        btnRestart.setAlignment(Pos.CENTER);

        VBox myBox = new VBox(welcome, enterName, playerX, txtFieldX, playerO, txtFieldO, btnRestart);
        myBox.setAlignment(Pos.CENTER);
        myBox.setId("box");
        gridPane.setConstraints(myBox, 5, 0, 5, 5);

        //add vbox with all children to the scene
        gridPane.getChildren().addAll(myBox);

        //sets the action on the button click
        btnRestart.setOnAction( (ae) -> startNewGame());


        primaryStage.show();
    }

    /**
     * checkstate() method uses a for-each loop to get the winning combination, makes changes to it and ends the game
     **/
    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                changeColor(combo);
                getWinnerX(combo);
                break;
            }
        }
    }

    /**
     * changeColor() method is used to set he background color on the winning tiles (by setting id on the winning tiles and assigning a color to them
     * through css
     * @param combo represents the winning combination
     * **/

    //combo = winning combination, as stated and set in the checkState() method
    private void changeColor(Combo combo) {
        for (int r=0; r <=3; r++) {
        combo.tiles[r].setId("winBackground");
        }
    }

    /**
     * getwinner() method is used to determine if the winner had Xs or Os, which is then later used to write the winner's correct name (in the start() method)
     * @param combo represents the winning combination
     * **/
     private void getWinnerX (Combo combo) {
        if (combo.tiles[0].getValue().contains("X")){
            enterName.setText("The winner is: " +
                    name1);
        }
        else {
            enterName.setText("The winner is: " +
                    name2);
        }
    }

    /**
     * startNewGame() method is used to set the game to the start and is set as the onbutton action in the start() method**/
    private void startNewGame() {
        playable = true;
        turnX = true;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[j][i].text.setText("");
                board[j][i].tileFree = true;
                board[j][i].setId("noBackground");
            }
        }
    }

    public class Combo {
        public Tile[] tiles;

        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        /**
         * isComplete() method is used to determine if the tile is empty and if not, checks if any of the tile combinations contain the same values (Xs or Os)
         * checks for the winning combination of 4 Xs or Os
         * @return true if there is a winning combination on the board**/

        // checks if the tile is empty and if not, checks if any of the tile combinations contain the same values (Xs or Os)
        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue())
                    && tiles[0].getValue().equals(tiles[3].getValue());
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        private boolean tileFree = true;

        /**
         * Tile used to create the game board and connect the text(Xs and Os) with it**/

        public Tile() {
            Rectangle border = new Rectangle(100, 100);
            border.setLayoutX(50);
            border.setLayoutY(50);
            border.setId("border");
            border.setFill(null);
            border.setStroke(Color.WHITE);

            text.setFont(Font.font(72));

            //setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            /**
             * event set on a mouse click with lambda **/


            setOnMouseClicked(event -> {
                if (!playable || !tileFree)
                    return;

                if (turnX) {
                    drawX();
                    turnX = false;
                }
                else {
                    drawO();
                    turnX = true;
                }
                tileFree=false;
                checkState();
            });
        }

        /**
         * getValue() method is used to get the value of the text
         * @return String text (X or O)**/
        public String getValue() {
            return text.getText();
        }

        /**
         * method() drawX is used to draw X**/

        private void drawX() {
            text.setText("X");
            text.setId("xValue");
        }

        /**
         * drawO() method is used to draw O**/
        private void drawO() {
            text.setText("O");
            text.setId("oValue");
        }
    }
/**
 * main method
 * @param args Unused**/
    public static void main(String[] args) {
        launch(args);
    }
}