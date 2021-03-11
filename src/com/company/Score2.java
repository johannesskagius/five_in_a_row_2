package com.company;

public class Score2 {
    private Board b;
    private int boardRowSize;
    private int inARowToWin;
    private Node[][] playField;

    public Score2 (Board b) {
        this.b = b;
        this.playField = b.getPlayField ();
        boardRowSize = b.getPLAYFIELDSIZE ();
        inARowToWin = b.getROW_TO_WIN ();
    }

    public int getScore(){
        int humanScore = calcScore(Node.Brick.HUMAN );

        int computerScore = calcScore( Node.Brick.COMPUTER );
        return computerScore - humanScore;
    }

    private int calcScore (Node.Brick player) {

        return 0;
    }

}
