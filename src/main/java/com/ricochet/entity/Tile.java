package com.ricochet.entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    Wall[] walls;
    Vector2 position;
    Vector2 size;
    Vector2 add;
    List<Tile> neighbors;

    int row,col;

    Tile parent = null;

    Image image;
    Image hintImage;

    Image[] wallImages;
    boolean hint = false;

    public Tile(Vector2 position, int row, int col,int sizes)
    {
        neighbors = new ArrayList<>();
        this.row = row;
        this.col = col;
        this.size = new Vector2(sizes,sizes);
        this.position = position;
        this.walls = new Wall[4];
        wallImages = new Image[4];

        wallImages[0] = new Image("leftWall.png");
        wallImages[1] = new Image("rightWall.png");
        wallImages[2] = new Image("upWall.png");
        wallImages[3] = new Image("downWall.png");


        //left
        walls[0] = new Wall(new Vector2(position.x,position.y),wallImages[0]);
        // right
        walls[1] = new Wall( new Vector2(position.x + size.x,position.y),wallImages[1]);

        // up
        walls[2] = new Wall( new Vector2(position.x,position.y),wallImages[2]);

        // down
        walls[3] = new Wall( new Vector2(position.x ,position.y + size.y),wallImages[3]);


        image = new Image("tile.png");
        hintImage = new Image("tileHint.png");
    }


    public void open_wall_left()
    {
        walls[0].open_wall();
    }
    public void open_wall_right()
    {
        walls[1].open_wall();
    }
    public void open_wall_up()
    {
        walls[2].open_wall();
    }
    public void open_wall_down()
    {
        walls[3].open_wall();
    }

    void addNeighbour(Tile tile)
    {
        neighbors.add(tile);
    }

    public void draw(GraphicsContext g){
        if(isHint()){
            g.drawImage(hintImage,position.x,position.y);
        }else {
            g.drawImage(image,position.x,position.y);
        }
    }
    public void drawWall(GraphicsContext g){
        for(int i = 0 ; i < walls.length;i++){
            walls[i].draw(g);
        }
    }


    public boolean collide(double x,double y){
        if (x > position.x && x < position.x + size.x && y > position.y && y < position.y + size.y){
            return true;
        }
        return false;
    }

    public boolean isHint() {
        return hint;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
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

    public Wall[] getWalls() {
        return walls;
    }

    public void setWalls(Wall[] walls) {
        this.walls = walls;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Vector2 getAdd() {
        return add;
    }

    public void setAdd(Vector2 add) {
        this.add = add;
    }

    public List<Tile> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Tile> neighbors) {
        this.neighbors = neighbors;
    }

    public Tile getParent() {
        return parent;
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    }
}
