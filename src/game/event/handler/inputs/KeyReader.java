package game.event.handler.inputs;

import javafx.scene.Scene;

/**
 * Class that gets the events of certain keys of the keyboard
 * @author José Acuña
 */
public class KeyReader {

    private Scene scene;  // Scene where the keyboard is activated
    private static KeyReader instance;
    public int right;
    public int left;
    public int up;
    public int down;
    public int arrow_right;
    public int arrow_left;
    public int arrow_up;
    public int arrow_down;
    public int shoot;
    public int space;
    public int shift;


    private KeyReader(){

    }

    public static KeyReader getInstance(){
        if (instance == null) {
            instance = new KeyReader();
        }
        return instance;
    }

    /**
     * Method that change the state of key variables to 1 when pressed
     */
    private void keyPressed() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case D: right = 1; break;
                case A: left = 1; break;
                case W: up = 1; break;
                case S: down = 1; break;
                case L: arrow_right = 1; break;
                case I: arrow_up = 1; break;
                case K: arrow_down = 1; break;
                case E: shoot = 1; break;
                case SPACE: space = 1; break;
                case SHIFT: shift = 1; break;
            }
        });
    }


    /**
     * Method that change the state of key variables to 0 when released
     */
    private void keyReleased() {
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case D: right = 0; break;
                case A: left = 0; break;
                case W: up = 0; break;
                case S: down = 0; break;
                case L: arrow_right = 0; break;
                case I: arrow_up = 0; break;
                case K: arrow_down = 0; break;
                case E: shoot = 0; break;
                case SPACE: space = 0; break;
                case SHIFT: shift = 0; break;
            }
        });
    }

    /**
     * Sets a Scene as focused so the key inputs can be readed
     * @param scene is the Scene to focus
     */
    public void setScene(Scene scene) {
        this.scene = scene;
        keyPressed();
        keyReleased();
    }
}