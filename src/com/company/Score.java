/**
 * author josk3261 Johannes Skagius
 */

package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Score {
    private Board b;
    private int boardRowSize;
    private int inARowToWin;
    private Node[][] playField;

    public Score (Board b) {
        this.b = b;
        this.playField = b.getPlayField ();
        boardRowSize = b.getPLAYFIELDSIZE ();
        inARowToWin = b.getROW_TO_WIN ();
    }

    public int getScore (String player) {
        int score = 1;
        int count;

        for (int y = 0; y < boardRowSize; y++) {
            for (int x = 0; x < boardRowSize; x++) {
                int nextInRow = x;
                count = 1;
                //Checks the row to the right
                while (playField[y][nextInRow].getStatus ().equals ( player )) {
                    score *= count;
                    nextInRow++;
                    if (count > 1) {
                        boolean before = false;
                        boolean after = false;
                        //check if the node to the left of start is free if it's add extra score!
                        if (x > 0 && playField[y][x - 1].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            before = true;
                        }
                        if (nextInRow < boardRowSize && playField[y][nextInRow].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            after = true;
                        }
                        if (before && after && count == 3) {
                            score = 10000;
                        }
                    }
                    count++;
                    if (!(nextInRow < boardRowSize)) break;
                }
                int nextInColumn = y;
                count = 1;
                //Check straight down
                while (playField[nextInColumn][x].getStatus ().equals ( player )) {
                    score *= count;
                    nextInColumn++;
                    if (count > 1) {
                        boolean before = false;
                        boolean after = false;
                        //check if the node to above of start is free if it's add extra score!
                        if (y > 0 && playField[y - 1][x].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            before = true;
                        }
                        //Check the node below
                        if (nextInColumn < boardRowSize && playField[nextInColumn][x].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            after = true;
                        }
                        if (before && after && count == 3) {
                            score = 10000;
                        }
                    }
                    count++;
                    if (!(nextInColumn < boardRowSize)) break;
                }
                count = 1;

                int nextPositionInRow = y;
                int nextPositionInColumn = x;
                //Checks the node down to the right
                while (playField[nextPositionInRow][nextPositionInColumn].getStatus ().equals ( player )) {
                    score *= count;
                    nextPositionInRow++;
                    nextPositionInColumn++;
                    if (count > 1) {
                        boolean before = false;
                        boolean after = false;
                        //check if the node to above of start is free if it's add extra score!
                        if (y > 0 && x < boardRowSize && playField[y - 1][x + 1].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            before = true;
                        }
                        //Check the down to the right after streak
                        if (nextPositionInRow < boardRowSize && nextPositionInColumn < boardRowSize && playField[nextPositionInRow][nextPositionInColumn].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            after = true;
                        }
                        if (before && after && count == 3) {
                            score = 10000;
                        }
                    }
                    count++;
                    if (!(nextPositionInColumn < boardRowSize) || !(nextPositionInRow < boardRowSize))
                        break;
                }
                count = 1;

                int behindInRow = y;
                int behindInColumn = x;
                //Check down to left
                while (playField[behindInRow][behindInColumn].getStatus ().equals ( player )) {
                    score *= count;
                    behindInColumn--;
                    behindInRow++;
                    if (count > 1) {
                        boolean before = false;
                        boolean after = false;
                        //check if the node to above of start is free if it's add extra score!
                        if (y < boardRowSize && x > 0 && playField[y + 1][x - 1].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            before = true;
                        }
                        //Check the node below
                        if (behindInRow < boardRowSize && behindInColumn > 0 && playField[behindInRow][behindInColumn].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                            score += 50 * count;
                            after = true;
                        }
                        if (before && after && count == 3) {
                            score = 10000;
                        }
                    }
                    count++;

                    if (!(behindInColumn > 0) || !(behindInRow < boardRowSize))
                        break;
                }
            }
        }
        return score;
    }

    /*private int calculateScore(List<Coordinate> inARow, Coordinate before, Coordinate after) {
        int score = 0;
        boolean isBeforeStartEmpty;
        boolean isAfterEndEmpty;
        try {
            isBeforeStartEmpty = b.isPositionOpen ( before );
        } catch (ArrayIndexOutOfBoundsException e) {
            isBeforeStartEmpty = false;
        }
        try {
            isAfterEndEmpty = b.isPositionOpen ( after );
        } catch (ArrayIndexOutOfBoundsException e) {
            isAfterEndEmpty = false;
        }

        if (isBeforeStartEmpty || isAfterEndEmpty) {
            score += calculateListScore(inARow);
            if (isBeforeStartEmpty && isAfterEndEmpty) {
                score += 10;
            }
        }
        return score;
    }

    private int diagonalLeftScore (Node.Brick player) {
        int score = 0;
        List<Coordinate> inARow = new ArrayList<> ();
        for (int yTemp = 0; yTemp <= boardRowSize - inARowToWin; yTemp++) {
            int x = 0;
            int y = yTemp;
            while (x < boardRowSize && y < boardRowSize) {
                // If coordinate value is player add it to inARow
                Coordinate coordinate = new Coordinate ( y, x );
                if (b.getPositionValue ( coordinate ).equals ( player.value )) {
                    inARow.add(coordinate);
                    // When encounter something else and if coordinate has empty neighbour, calculate the score and add it to the total score
                } else if (inARow.size () >= 2) {
                    Coordinate before = new Coordinate ( inARow.get (0).getX() - 1, inARow.get (0).getY() - 1 );
                    Coordinate after = new Coordinate ( inARow.get ( inARow.size ()-1 ).getX() + 1, inARow.get(inARow.size ()-1).getY() + 1 );
                    score += calculateScore(inARow, before, after);
                    inARow.clear(); // Empty list to store upcoming in a row sequence
                }
                // Has to check again in last step
                if (x == boardRowSize - 1 || y == boardRowSize - 1) {
                    if (!inARow.isEmpty()) {
                        // score += calculateScore(inARow, inARow.getFirst().getX() - 1, inARow.getFirst().getY() - 1, inARow.getLast().getX() + 1, inARow.getLast().getY() + 1);
                    }
                    inARow.clear(); // Empty list to store upcoming in a row sequence
                }
                x++;
                y++;
            }
        }
        return score;
    }

    private int calculateListScore(List<Coordinate> inARow) {
        int inARowScore = 1;
        for (Coordinate coord : inARow) {
            inARowScore *= 10;
        }
        return inARowScore * inARow.size();
    }

    private int diagonalRightScore (Node.Brick player) {
        int score = 0;
        List<Coordinate> inARow = new ArrayList<> ();
        // Score for tilt right '/'
        for (int yTemp = inARowToWin - 1; yTemp < boardRowSize; yTemp++) {
            int x = 0;
            int y = yTemp;
            while (x < boardRowSize && y >= 0) {
                Coordinate coordinate = new Coordinate ( x, y );
                // If coordinate value is player add it to inARow
                if (b.getPositionValue (coordinate).equals ( player.value )) {
                    inARow.add(new Coordinate(x, y));
                    // When encounter something else and if coordinate has empty neighbour, calculate the score and add it to the total score
                } else if (!inARow.isEmpty()) {
                    //Get the coordinate before
                    Coordinate before = new Coordinate ( inARow.get (0).getX() - 1,- 1,inARow.get ( 0 ).getX () );
                    //Get the coordinate after
                    Coordinate after = new Coordinate (inARow.get (inARow.size ()-1).getX() + 1, inARow.get (inARow.size ()-1).getY() - 1 );
                    score += calculateScore ( inARow,before,after );
                    inARow.clear(); // Empty list to store upcoming in a row sequence
                }
                // Has to check again in last step
                if (y == 0 || x == boardRowSize - 1) {
                    if (!inARow.isEmpty()) {
                    //    score += calculateScore(inARow, inARow.getFirst().getX() - 1, inARow.getFirst().getY() + 1, inARow.getLast().getX() + 1, inARow.getLast().getY() - 1);
                    }
                    inARow.clear(); // Empty list to store upcoming in a row sequence
                }
                x++;
                y--;
            }
        }
        return score;
    }

    private int verticalScore (Node.Brick player) {
        int score = 0;
        List<Coordinate> inARow = new LinkedList<> ();
        for (int y = 0; y < boardRowSize; y++) {
            for (int x = 0; x < boardRowSize; x++) {
                if (playField[y][x].getStatus ().equals ( player.value )) {
                    inARow.add ( new Coordinate ( y,x ) );
                } else if (inARow.size () >= 2) {
                    //Get the coordinate before
                    Coordinate before = new Coordinate ( inARow.get ( 0 ).getY ()- 1,inARow.get ( 0 ).getX () );
                    //Get the coordinate after
                    Coordinate after = new Coordinate ( inARow.get ( inARow.size () - 1 ).getY ()+1,inARow.get ( inARow.size () - 1 ).getX () );
                    score += calculateScore ( inARow,before,after );
                    inARow.clear ();
                }
            }
            inARow.clear ();
        }
        return score;
    }

    private int horizontalScore (Node.Brick player) {
        int score = 0;
        List<Coordinate> inARow = new LinkedList<> ();
        for (int y = 0; y < boardRowSize; y++) {
            for (int x = 0; x < boardRowSize; x++) {
                if (playField[x][y].getStatus ().equals ( player.value )) {
                    inARow.add ( new Coordinate ( x,y ) );
                } else if (inARow.size () >= 2) {
                    //Get the coordinate before
                    Coordinate before = new Coordinate ( inARow.get ( 0 ).getX (),inARow.get ( 0 ).getY ()- 1 );
                    //Get the coordinate after
                    Coordinate after = new Coordinate ( inARow.get ( inARow.size () - 1 ).getY (),inARow.get ( inARow.size () - 1 ).getX () + 1 );
                    score += calculateScore ( inARow,before,after );
                    inARow.clear ();
                }
            }
            inARow.clear ();
        }
        return score;
    }*/
}
