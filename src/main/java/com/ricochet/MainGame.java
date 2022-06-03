package com.ricochet;

import com.ricochet.entity.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGame {

    Tile[][] tiles;
    List<Tile> tileList;

    int rows = 16;
    int cols = 16;

    Robot[] robots;
    Token token;

    Robot selectedRobot = null;
    Tile selectedTile = null;


    GraphicsContext g;

    int tokenCount = 0;
    int[] moveCount;

    MainGame(){
        tiles = new Tile[rows][cols];
        tileList = new ArrayList<>();

        moveCount = new int[]{0,0,0,0};

        Vector2 pos= new Vector2(20,20);
        int size = 40;
        for(int row = 0 ; row < rows;row++){
            for(int col = 0; col < cols;col++){
                Tile t = new Tile(new Vector2(pos.x,pos.y),row,col,size);
                tiles[row][col] = t;
                tileList.add(t);
                pos.x += size;
            }
            pos.x = 20;
            pos.y += size;
        }

        for(int row = 0 ; row < rows;row++){
            for(int col = 0; col < cols;col++) {
                if(row == 0){
                    tiles[row][col].open_wall_up();
                }
                if(col == 0){
                    tiles[row][col].open_wall_left();
                }
                if(row == rows-1){
                    tiles[row][col].open_wall_down();
                }
                if(col == cols-1){
                    tiles[row][col].open_wall_right();
                }
            }
        }

        wallSetup();
        Vector2 tokenRowCol = getTokenRandomRowCol();
        token = new Token(tokenRowCol.y,tokenRowCol.x, Token.Color.blue);


        robots = new Robot[4];
        robots[0] = new Robot(new Vector2(0,0),0,0, Token.Color.blue);
        robots[1] = new Robot(new Vector2(0,0),0,0, Token.Color.red);
        robots[2] = new Robot(new Vector2(0,0),0,0, Token.Color.green);
        robots[3] = new Robot(new Vector2(0,0),0,0, Token.Color.yellow);
        for(int i = 0 ;i < 4;i++){
            Vector2 rowCol = getRandomRowCol();
            robots[i].setPosition(rowCol.y,rowCol.x);
        }

    }

    public void onMouseCLick(MouseEvent event){
        for(Tile tile :tileList){
            if(tile.collide(event.getX(),event.getY())){
                for(Robot robot : robots){
                    if(robot.getRow() == tile.getRow() && robot.getCol() == tile.getCol()){
                        selectedRobot = robot;
                    }
                }
                selectedTile = tile;
            }
        }

        if( selectedTile != null && selectedTile.isHint()){
            moveRobot();
        }
        else if(selectedRobot != null){
            showPath();
        }
    }

    void moveRobot(){
        if(selectedTile.getCol() < selectedRobot.getCol()){
            int col = selectedRobot.getCol();
            int row = selectedRobot.getRow();
            while (col > 0){
                col--;
                if(!tiles[row][col].isHint()){
                    break;
                }
                selectedRobot.setPosition(row,col);
            }
            moveCount[tokenCount]++;
        }
        else if(selectedTile.getCol() > selectedRobot.getCol()){
            int col = selectedRobot.getCol();
            int row = selectedRobot.getRow();
            while (col < cols - 1){
                col++;
                if(!tiles[row][col].isHint()){
                    break;
                }
                selectedRobot.setPosition(row,col);
            }
            moveCount[tokenCount]++;
        }
        else if(selectedTile.getRow() < selectedRobot.getRow()){
            int col = selectedRobot.getCol();
            int row = selectedRobot.getRow();
            while (row > 0){
                row--;
                if(!tiles[row][col].isHint()){
                    break;
                }
                selectedRobot.setPosition(row,col);
            }
            moveCount[tokenCount]++;
        }
        else if(selectedTile.getRow() > selectedRobot.getRow()){
            int col = selectedRobot.getCol();
            int row = selectedRobot.getRow();
            while (row < rows-1){
                row++;
                if(!tiles[row][col].isHint()){
                    break;
                }
                selectedRobot.setPosition(row,col);
            }
            moveCount[tokenCount]++;
        }

        if(selectedRobot.getRow() == token.getRow() && selectedRobot.getCol() == token.getCol()){
            if(selectedRobot.getColor() == token.getColor()){
                Vector2 tokenRowCol = getTokenRandomRowCol();

                tokenCount++;
                if(tokenCount > 3){
                    tokenCount = 0;
                }

                switch (tokenCount){
                    case 0:
                        token = new Token(tokenRowCol.y,tokenRowCol.x, Token.Color.blue);
                        break;
                    case 1:
                        token = new Token(tokenRowCol.y,tokenRowCol.x, Token.Color.green);
                        break;
                    case 2:
                        token = new Token(tokenRowCol.y,tokenRowCol.x, Token.Color.red);
                        break;
                    case 3:
                        token = new Token(tokenRowCol.y,tokenRowCol.x, Token.Color.yellow);
                        break;
                }
            }
        }

        selectedTile = null;
        selectedRobot = null;
        clearHint();
        draw(g);
    }

    void showPath(){

        clearHint();

        tiles[selectedRobot.getRow()][selectedRobot.getCol()].setHint(true);

        Wall[] walls = tiles[selectedRobot.getRow()][selectedRobot.getCol()].getWalls();

        int origin_col = selectedRobot.getCol();
        int origin_row = selectedRobot.getRow();

        if(!walls[0].isOpen()){
            int row = origin_row;
            int col = origin_col;
            while (col > 0 && !tiles[row][col].getWalls()[0].isOpen()){
                col--;
                if(isRobotPos(row,col)){
                    break;
                }
                tiles[row][col].setHint(true);
            }
        }
        if(!walls[1].isOpen()){
            int row = origin_row;
            int col = origin_col;
            while (col < cols && !tiles[row][col].getWalls()[1].isOpen()){
                col++;
                if(isRobotPos(row,col)){
                    break;
                }
                tiles[row][col].setHint(true);
            }
        }
        if(!walls[2].isOpen()){
            int row = origin_row;
            int col = origin_col;
            while (row > 0 && !tiles[row][col].getWalls()[2].isOpen()){
                row--;
                if(isRobotPos(row,col)){
                    break;
                }
                tiles[row][col].setHint(true);
            }
        }
        if(!walls[3].isOpen()){
            int row = origin_row;
            int col = origin_col;
            while (row < rows && !tiles[row][col].getWalls()[3].isOpen() ){
                row++;
                if(isRobotPos(row,col)){
                    break;
                }
                tiles[row][col].setHint(true);
            }
        }

        draw(g);
    }

    void clearHint(){
        for(Tile t : tileList){
            t.setHint(false);
        }
    }

    boolean isRobotPos(int row,int col){
        for(Robot robot : robots){
            if(robot.getRow() == row && robot.getCol() == col){
                return true;
            }
        }
        return false;
    }

    Vector2 getTokenRandomRowCol(){
        Random random = new Random();
        int row = 0;
        int col = 0;

        boolean exist = true;


        while (exist){
            row = random.nextInt(16);
            col = random.nextInt(16);
            int count = 0;

            for(Wall wall: tiles[row][col].getWalls()){
                if(wall.isOpen()){
                    count++;
                }
            }

            if(count == 2){
                for(Wall wall: tiles[row][col].getWalls()){
                }
                exist = false;
            }

            if(token != null && token.getRow() == row && token.getCol() == col){
                exist = true;
            }
        }
        return new Vector2(col,row);
    }

    Vector2 getRandomRowCol(){
        Random random = new Random();
        int row = 0;
        int col = 0;

        boolean exist = true;

        while (exist){
            exist = false;
            for(Robot robot : robots){
                if(robot.getRow() == row && robot.getCol() == col){
                    row = random.nextInt(16);
                    col = random.nextInt(16);
                    exist = true;
                }
            }

            if(row == 7 || row == 8 || col == 7 || col == 8){
                exist = true;
            }

            if( token.getRow() == row && token.getCol() == col){
                exist = true;
            }
        }

        return new Vector2(col,row);
    }

    private void wallSetup(){
        tiles[0][4].open_wall_right();tiles[0][5].open_wall_left();
        tiles[0][9].open_wall_right(); tiles[0][10].open_wall_left();

        tiles[1][6].open_wall_right(); tiles[1][6].open_wall_down();

        tiles[1][13].open_wall_up(); tiles[1][13].open_wall_right();

        tiles[2][1].open_wall_up(); tiles[2][1].open_wall_left();
        tiles[2][9].open_wall_up(); tiles[2][9].open_wall_left();

        tiles[5][0].open_wall_down();tiles[6][0].open_wall_up();
        tiles[5][6].open_wall_up();tiles[5][6].open_wall_right();

        tiles[5][15].open_wall_up();tiles[4][15].open_wall_down();

        tiles[6][2].open_wall_left();tiles[6][2].open_wall_down();
        tiles[6][10].open_wall_right();tiles[6][10].open_wall_down();
        tiles[6][14].open_wall_left();tiles[6][14].open_wall_down();

        tiles[7][7].open_wall_left();tiles[7][7].open_wall_up();
        tiles[7][7].open_wall_right();tiles[7][7].open_wall_down();
        tiles[7][8].open_wall_right();tiles[7][8].open_wall_up();

        tiles[8][5].open_wall_left();tiles[8][5].open_wall_down();
        tiles[8][7].open_wall_left();tiles[8][7].open_wall_down();
        tiles[8][8].open_wall_right();tiles[8][8].open_wall_down();
        tiles[8][8].open_wall_left();tiles[8][8].open_wall_up();

        tiles[9][2].open_wall_left();tiles[9][2].open_wall_up();
        tiles[9][12].open_wall_left();tiles[9][12].open_wall_up();

        tiles[10][10].open_wall_right();tiles[10][10].open_wall_up();
        tiles[10][10].open_wall_right();tiles[10][10].open_wall_up();
        tiles[10][15].open_wall_up();tiles[9][15].open_wall_down();

        tiles[11][0].open_wall_down(); tiles[12][0].open_wall_up();

        tiles[12][14].open_wall_right();tiles[12][14].open_wall_up();
        tiles[13][4].open_wall_right();tiles[13][4].open_wall_up();

        tiles[14][1].open_wall_right();tiles[14][1].open_wall_down();
        tiles[14][11].open_wall_left();tiles[14][11].open_wall_down();

        tiles[15][5].open_wall_right(); tiles[15][6].open_wall_left();
        tiles[15][14].open_wall_left();tiles[15][13].open_wall_right();



        for(int row = 0 ; row < rows;row++){
            for(int col = 0; col < cols;col++) {

                if(row == 0 || col == 0 || row == rows-1 || col == cols-1){
                    continue;
                }

                if(tiles[row][col].getWalls()[0].isOpen()){
                    tiles[row][col-1].getWalls()[1].setOpen(true);
                }
                if(tiles[row][col].getWalls()[1].isOpen()){
                    tiles[row][col+1].getWalls()[0].setOpen(true);
                }
                if(tiles[row][col].getWalls()[2].isOpen()){
                    tiles[row-1][col].getWalls()[3].setOpen(true);
                }
                if(tiles[row][col].getWalls()[3].isOpen()){
                    tiles[row+1][col].getWalls()[2].setOpen(true);
                }
            }
        }

    }

    void draw(GraphicsContext g){
        g.clearRect(0,0,1280,720);

        this.g = g;
        for(Tile t: tileList){
            t.draw(g);
        }

        token.draw(g);

        for(Robot robot : robots){
            robot.draw(g);
        }


        for(Tile t: tileList){
            t.drawWall(g);
        }

        g.fillText("Token blue:  " + moveCount[0],800,40);
        g.fillText("Token green:  " + moveCount[1],800,40*2);
        g.fillText("Token red:  " + moveCount[2],800,40*3);
        g.fillText("Token yellow:  " + moveCount[3],800,40 *4);

    }

}
