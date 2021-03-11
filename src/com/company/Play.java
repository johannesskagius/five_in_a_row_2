package com.company;

import java.util.List;

public class Play {
    private static final int ALPHA_START_VALUE = Integer.MIN_VALUE;
    private static final int BETA_START_VALUE = Integer.MAX_VALUE;
    private Board board;
    private int maxDepth;
    private Coordinate computerMove;
    private int alfa;
    private int beta;


    public Play (int maxDepth,int playFieldSize) {
        this.maxDepth = maxDepth;
        board = new Board ( playFieldSize );
        alfa = ALPHA_START_VALUE;
        beta = BETA_START_VALUE;
    }

    public void printBoard () {
        board.printBoard ();
    }

    public boolean makeATurn (Coordinate move,Node.Brick player) {
        board.addPlay ( move.getX (),move.getY (),player );
        findComputerMove ();
        board.printBoard ();
        return board.isGameOver ();
    }


    //TODO change back to private
    protected void findComputerMove () {
        computerMove ( 1,alfa,beta );
        board.addPlay ( computerMove.getX (),computerMove.getY (),Node.Brick.COMPUTER );
    }

    public void addPosition (int x,int y,Node.Brick s) {
        board.addPlay ( x,y,s );
    }

    public int getScore () {
        return board.score ();
    }

    public Board getBoard(){
        return board;
    }

    private int computerMove (int depth,int alfa,int beta) {
        if (board.hasPlayerWon ( Node.Brick.HUMAN.value )) {
            return calcWinScore () / depth;
        }
        if (board.hasPlayerWon ( Node.Brick.COMPUTER.value )) {
            return -calcWinScore () / depth;
        }
        if (depth == maxDepth) {
            return board.score () / depth;
        }
        List<Coordinate> possiblePlays = board.possiblePlaysLimited ();
        Coordinate chosenPlay = null;
        for (Coordinate pos : possiblePlays) {
            board.addPlay ( pos.getX (),pos.getY (),Node.Brick.COMPUTER );
            int score = playerMove ( depth + 1,alfa,beta );
            printBoard ();
            board.removePlay ( pos.getX (),pos.getY () );
            if (score < beta) {
                beta = score;
                chosenPlay = pos;
            }
            if (beta >= alfa) {
                break;
            }
        }
        computerMove = chosenPlay;
        return alfa;
    }

    private int playerMove (int depth,int alfa,int beta) {
        if (board.hasPlayerWon ( Node.Brick.COMPUTER.value )) {
            return calcWinScore () / depth;
        }
        if (board.hasPlayerWon ( Node.Brick.HUMAN.value )) {
            return -calcWinScore () / depth;
        }
        if (depth == maxDepth) {
            return board.score () / depth;
        }
        Coordinate chosenPlay = null;
        List<Coordinate> possiblePlays = board.possiblePlaysLimited ();
        for (Coordinate pos : possiblePlays) {
            board.addPlay ( pos.getX (), pos.getY (),Node.Brick.HUMAN );
            printBoard ();
            int score = computerMove ( depth + 1,alfa,beta );
            board.removePlay ( pos.getX (),pos.getY () );

            if (score < alfa) {
                alfa = score;
                chosenPlay = pos;
            }
            if (beta >= alfa) {
                break;
            }
        }
        computerMove = chosenPlay;
        return alfa;
    }

    private int calcWinScore () {
        int score = 10000;
        for (int i = 0; i < board.getROW_TO_WIN (); i++) {
            score *= 10;
        }
        return score * board.getROW_TO_WIN ();
    }
}