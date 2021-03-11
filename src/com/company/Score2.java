package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Score2 {
    private Board b;
    private int boardRowSize;
    private int inARowToWin;
    private Node[][] playField;
    private List<Coordinate> playedPos = new ArrayList<> ();

    public Score2 (Board b) {
        this.b = b;
        this.playField = b.getPlayField ();
        boardRowSize = b.getPLAYFIELDSIZE ();
        inARowToWin = b.getROW_TO_WIN ();
        playedPos = b.getPlayedPositions ();
    }

    public int getScore () {
        //getVerticalScore
        int human = getVerticalScore(Node.Brick.HUMAN.value) +
                horizontalScore(Node.Brick.HUMAN.value ) +
                diagonalScore(Node.Brick.HUMAN.value);

        int computer = getVerticalScore ( Node.Brick.COMPUTER.value )+
                horizontalScore ( Node.Brick.COMPUTER.value )+
                diagonalScore ( Node.Brick.COMPUTER.value );
        return computer-human;
    }

    private int getVerticalScore (String player) {
        int score = 0;
        List<Coordinate> inARow = new LinkedList<> ();
        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardRowSize; j++) {
                if (playField[j][i].getStatus ().equals ( player )) {
                    inARow.add ( new Coordinate ( j,i ) );
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

    private int diagonalScore (String player) {
        int rightTopLeftBottom = rightTopToLeftBottomAndRight ( player ) + rightTopToLeftBottomAndLeft ( player );
        int leftTopRightBottom = leftTopToRightBottomAndLeft ( player ) + leftTopToRightBottomAndRight ( player );
        return rightTopLeftBottom + leftTopRightBottom;
    }
    private int rightTopToLeftBottomAndRight (String player) {
        int x = 0;
        int y = 0;
        int score = 0;
        int count = 0;
        List<Coordinate> inARow = new LinkedList<> ();

        for (int i = 1; i <= boardRowSize- 1; i++) {   //Yvärdet börjar ifrån 0 och går neråt på kartan
            x = boardRowSize - 1;
            for (int j = boardRowSize; j > 0; j--) { //Xvärdet börjar ifrån storleken på kartan - i
                if (playField[y][x].getStatus ().equals ( player )) {
                    inARow.add ( new Coordinate ( y,x ) );
                } else if (inARow.size () >= 2) {
                    //Get the coordinate before
                    Coordinate before = new Coordinate ( inARow.get ( 0 ).getY () - 1,inARow.get ( 0 ).getX () + 1 );
                    //Get the coordinate after
                    Coordinate after = new Coordinate ( inARow.get ( inARow.size () - 1 ).getY () + 1,inARow.get ( inARow.size () - 1 ).getX () );
                    score += calculateScore ( inARow,before,after );
                    inARow.clear ();

                }
            }
            if (count < b.getROW_TO_WIN ()) {
                break;
            }
            y = i;
            count = 0;
        }
        return score;
    }

    private int rightTopToLeftBottomAndLeft (String player) {
        int x = boardRowSize - 1;
        int y = 0;
        int score = 0;
        int count = 0;
        List<Coordinate> inARow = new LinkedList<> ();

        for (int i = 1; i <= boardRowSize - 1; i++) {   //Yvärdet börjar ifrån 0 och går neråt på kartan
            x = boardRowSize - i;
            for (int j = boardRowSize; j > 0; j--) { //Xvärdet börjar ifrån storleken på kartan - i
                if (x != -1) {
                    if (playField[y][x].getStatus ().equals ( player )) {
                        inARow.add ( new Coordinate ( y,x ) );
                    } else if (inARow.size () >= 2) {
                        //Get the coordinate before
                        Coordinate before = new Coordinate ( inARow.get ( 0 ).getY () - 1,inARow.get ( 0 ).getX () + 1 );
                        //Get the coordinate after
                        Coordinate after = new Coordinate ( inARow.get ( inARow.size () - 1 ).getY () + 1,inARow.get ( inARow.size () - 1 ).getX ()-1 );
                        score += calculateScore ( inARow,before,after );
                        inARow.clear ();
                    }
                    //playField[y][x].setStatus ( Node.Brick.THISNODE );
                    y++;
                    x--;
                    count++;
                }
            }
            inARow.clear ();
            y = 0;
            if (count < b.getROW_TO_WIN ()) {
                break;
            }
            count = 0;
        }
        return score;
    }

    private int leftTopToRightBottomAndLeft (String player) {
        int x = boardRowSize - 1;
        int y = 0;
        int score = 0;
        int count = 0;
        int topScore = 0;
        List<Coordinate> inARow = new LinkedList<> ();

        for (int i = 0; i <= boardRowSize - 1; i++) {   //Yvärdet börjar ifrån 0 och går neråt på kartan
            x = i;  //TODO KOLLA OM DENNA BEHÖVS
            for (int j = 0; j < boardRowSize; j++) { //Xvärdet börjar ifrån storleken på kartan - i
                if (x < boardRowSize) {
                    if (playField[y][x].getStatus ().equals ( player )) {
                        inARow.add ( new Coordinate ( y,x ) );
                    } else if (inARow.size () >= 2) {
                        //Get the coordinate before
                        Coordinate before = new Coordinate ( inARow.get ( 0 ).getY () - 1,inARow.get ( 0 ).getX () - 1 );
                        //Get the coordinate after
                        Coordinate after = new Coordinate ( inARow.get ( inARow.size () - 1 ).getY () + 1,inARow.get ( inARow.size () - 1 ).getX () + 1 );
                        score += calculateScore ( inARow,before,after );
                        inARow.clear ();
                    }
                    y++;
                    x++; //TODO KOLLA OM DENNA BEHÖVS
                    count++;
                } else break;
            }
            inARow.clear ();
            y = 0;
            if (count < b.getROW_TO_WIN ()) {
                break;
            }
            count = 0;
        }
        return score;
    }

    private int leftTopToRightBottomAndRight (String player) {
        int x = 0;
        int y = 0;
        int score = 0;
        int count = 0;
        List<Coordinate> inARow = new LinkedList<> ();

        for (int i = 0; i <= boardRowSize - 1; i++) {   //Yvärdet börjar ifrån 0 och går neråt på kartan
            y = i;  //TODO KOLLA OM DENNA BEHÖVS
            for (int j = 0; j < boardRowSize; j++) { //Xvärdet börjar ifrån storleken på kartan - i
                if (y < boardRowSize) {
                    if (playField[y][x].getStatus ().equals ( player )) {
                        inARow.add ( new Coordinate ( y,x ) );
                    } else if (inARow.size () >= 2) {
                        //Get the coordinate before
                        Coordinate before = new Coordinate ( inARow.get ( 0 ).getY () - 1,inARow.get ( 0 ).getX () - 1 );
                        //Get the coordinate after
                        Coordinate after = new Coordinate ( inARow.get ( inARow.size () - 1 ).getY () + 1,inARow.get ( inARow.size () - 1 ).getX () + 1 );
                        score += calculateScore ( inARow,before,after );
                        inARow.clear ();
                    }
                    y++;
                    x++; //TODO KOLLA OM DENNA BEHÖVS
                    count++;
                } else break;
            }
            inARow.clear ();
            x = 0;
            if (count < b.getROW_TO_WIN ()) {
                break;
            }
            count = 0;
        }
        return score;
    }


    private int horizontalScore (String player) {
        int score = 0;
        List<Coordinate> inARow = new LinkedList<> ();
        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardRowSize; j++) {
                if (playField[j][i].getStatus ().equals ( player )) {
                    inARow.add ( new Coordinate ( j,i ) );
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

    private int calculateScore (List<Coordinate> inARow,Coordinate beforeStart,Coordinate afterEnd) {
        int score = 0;
        boolean isBeforeStartEmpty;
        boolean isAfterEndEmpty;
        try {
            isBeforeStartEmpty = checkNode ( beforeStart );
        } catch (ArrayIndexOutOfBoundsException e) {
            isBeforeStartEmpty = false;
        }
        try {
            isAfterEndEmpty = checkNode ( afterEnd );
        } catch (ArrayIndexOutOfBoundsException e) {
            isAfterEndEmpty = false;
        }
        if (isBeforeStartEmpty || isAfterEndEmpty) {
            score += calculateListScore ( inARow );
            if (isBeforeStartEmpty && isAfterEndEmpty) {
                score += 10;
            }
            if (inARow.size () >= b.getROW_TO_WIN () - 1) {
                score += 1000000;
            }
        }
        return score;
    }

    private int calculateListScore (List<Coordinate> inARow) {
        int score = 1;
        for (Coordinate c : inARow)
            score += 100;
        return score;
    }

    private boolean checkNode (Coordinate coordinate) throws ArrayIndexOutOfBoundsException {
        return playField[coordinate.getY ()][coordinate.getX ()].getStatus ().equals ( Node.Brick.NOTPLAYED.value );
    }
}
