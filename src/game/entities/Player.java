package game.entities;

import game.GameController;
import game.draw.Drawer;
import game.draw.Sprite;
import game.event.handler.inputs.Collisions;
import game.event.handler.inputs.KeyReader;
import util.Clock;
import util.Math;

import java.util.ArrayList;

public class Player extends Entity {

    private KeyReader key = KeyReader.getInstance();
    private Clock clock = Clock.getInstance();
    private double lives = 3;
    private double xSpeed = 0, ySpeed = 0;
    private double xMaxSpeed = 8, yMaxSpeed = 8;
    private double xAcc = 1, yAcc = 2;
    private double xPoss = 200, yPoss = 200;
    private double playerWidth = 120, playerHeight = 80;
    private double fire_rate = 500;
    private double damage = 1;
    private long lastTime = 0;
    private String state = "Moving"; // Moving / Dead / Dashing
    private Sprite sprite;
    private ArrayList<Sprite> movementAnimations = new ArrayList<>();
    private double animationTimer = 200;
    private double lastAnimationTime = 0;
    private int currentSprite = 0;

    public Player(){
        sprite = loadImages();
        Drawer.getInstance().addDrawAtEnd(this);
        GameController.getInstance().addEntity(this);
    }

    @Override
    public void update(){
        move();

        if (key.shoot == 1 && canShoot()){
            shoot();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Sprite draw() {
        long time = clock.getTime();
        if (time - lastAnimationTime > animationTimer){
            sprite = movementAnimations.get(currentSprite);
            lastAnimationTime = time;
            currentSprite = (currentSprite + 1) % movementAnimations.size();
        }
        sprite.move(xPoss, yPoss);
        return sprite;
    }

    private Sprite loadImages(){
        movementAnimations.add(sprite = new Sprite(xPoss, yPoss, playerWidth, playerHeight,
                "file:res/img/entities/griffin/Griffin.png"));
        //movementAnimations.add(sprite = new Sprite(xPoss, yPoss, playerWidth, playerHeight,
        //        "file:res/img/entities/dragon/Grifo"));
        //movementAnimations.add(sprite = new Sprite(xPoss, yPoss, playerWidth, playerHeight,
        //        "file:res/img/entities/dragon/Grifo"));
        return movementAnimations.get(0);

    }

    private void hit(){
        lives--;
        if(lives <= 0){
            dead();
        }
    }

    private void heal(){

    }

    private void shoot(){
        var yDirection = key.arrow_down - key.arrow_up;
        FireBall fireBall = new FireBall(xPoss, yPoss, 40, 45,1, yDirection);
        Collisions.getInstance().addPlayerBullets(fireBall);
    }

    private void dead(){

    }

    private void move(){
        var xMove = key.right - key.left;
        var yMove = key.up - key.down;

        if (xMove == 0){
            xSpeed =  Math.approach(xSpeed, 0, 0.1);
        }

        if (yMove == 0){
            ySpeed =  Math.approach(ySpeed, 0, 0.1);
        }

        xSpeed = Math.clamp(xSpeed += xAcc * xMove, -xMaxSpeed, xMaxSpeed);
        ySpeed = Math.clamp(ySpeed -= yAcc * yMove, -yMaxSpeed, yMaxSpeed);
        xPoss += xSpeed;
        yPoss += ySpeed;
    }

    private Boolean canShoot(){
        Boolean result = false;
        long time = clock.getTime();
        if (time - lastTime > fire_rate){
            result = true;
            lastTime = time;
        }
        return result;

    }

    public double getxPoss() {
        return xPoss;
    }

    public void setxPoss(double xPoss) {
        this.xPoss = xPoss;
    }

    public double getyPoss() {
        return yPoss;
    }

    public void setyPoss(double yPoss) {
        this.yPoss = yPoss;
    }
}
