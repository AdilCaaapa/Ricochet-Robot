package com.ricochet.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Robot {
    Vector2 position;
    int row;
    int col;

    Image image;
    Token.Color color;

    public Robot(Vector2 position, int row, int col, Token.Color color) {
        this.position = position;
        this.row = row;
        this.col = col;
        this.color = color;

        switch (color){
            case red:
                image = new Image("redRobot.png");
                break;
            case green:
                image = new Image("greenRobot.png");
                break;
            case blue:
                image = new Image("blueRobot.png");
                break;
            case yellow:
                image = new Image("yellowRobot.png");
                break;
        }
    }
    public void setPosition(int row,int col){
        this.position = new Vector2(col * 40 + 20,row * 40 + 20);
        this.row = row;
        this.col = col;
    }

    public Token.Color getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void draw(GraphicsContext g){
        g.drawImage(image,position.x,position.y);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "position=" + position +
                ", row=" + row +
                ", col=" + col +
                ", image=" + image +
                ", color=" + color +
                '}';
    }
}
