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

    public int getScore(){
        int humanScore = horizontalScore(Node.Brick.HUMAN ) +
                verticalScore(Node.Brick.HUMAN ) +
                diagonalRightScore(Node.Brick.HUMAN ) +
                diagonalLeftScore(Node.Brick.HUMAN );

        int computerScore = horizontalScore( Node.Brick.COMPUTER ) +
                verticalScore(Node.Brick.COMPUTER) +
                diagonalRightScore(Node.Brick.COMPUTER) +
                diagonalLeftScore(Node.Brick.COMPUTER);
        return computerScore - humanScore;
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

    private int calculateScore(List<Coordinate> inARow, Coordinate before, Coordinate after) {
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
    }
}
