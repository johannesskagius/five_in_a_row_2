package com.company;

import java.util.ArrayList;
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

    public int getScore (String player) {
        //getVerticalScore
        int vertical = getVerticalScore(player);
        //getHorizontalScore
        //getDiagonalScore
        return 0;
    }

    private int getVerticalScore (String player) {
        playedPos.get ( 0 );

    }
}
