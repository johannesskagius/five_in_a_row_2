//  author josk3261 Johannes Skagius
// Stockholms university
// Kurs: ALDA - algoritmer och datastrukturer

package com.company;

public class Score {
    private Board b;
    private int boardRowSize;
    private int inARowToWin;
    private Node[][] playField;

    public Score (Board b) {
        this.b = b;
        this.playField = b.getPlayField ();
        boardRowSize = b.getPLAYFIELDSIZE ();
        inARowToWin = b.getStreakToWin ();
    }

    protected int calcWinScore () {
        int score = 1000;
        for (int i = 0; i < inARowToWin; i++) {
            score *= 10;
        }
        return score * inARowToWin;
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
                        if (before && after && count == inARowToWin-1) {
                            score += 10000;
                        }else if(before && after && count == inARowToWin-2){
                            score += 3000;
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
                        if (before && after && count == inARowToWin-1) {
                            score += 10000;
                        }else if(before && after && count == inARowToWin-2){
                            score += 3000;
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
                        if (before && after && count == inARowToWin-1) {
                            score += 10000;
                        }else if(before && after && count == inARowToWin-2){
                            score += 3000;
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
                        if (before && after && count == inARowToWin-1) {
                            score += 10000;
                        }else if(before && after && count == inARowToWin-2){
                            score += 3000;
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
}
