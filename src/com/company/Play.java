//  @author josk3261 Johannes Skagius
// Stockholms university
// Kurs: ALDA - algoritmer och datastrukturer

package com.company;

import java.util.List;

public class Play {
    private static final int ALPHA_START_VALUE = Integer.MIN_VALUE;
    private static final int BETA_START_VALUE = Integer.MAX_VALUE;
    private final Board board;
    private final int maxDepth;
    private final int alfa;
    private final int beta;
    private Coordinate computerMove;

    public Play (int maxDepth,int playFieldSize, int streakToWin) {
        this.maxDepth = maxDepth;
        alfa = ALPHA_START_VALUE;
        beta = BETA_START_VALUE;
        board = new Board ( playFieldSize, streakToWin );
    }

    public void printBoard () {
        board.printBoard ();
    }

    public boolean makeATurn (Coordinate move,Node.Brick player) {
        board.addPlay ( move.getY (),move.getX (),player );
        printBoard ();
        findComputerMove ();
        return board.isGameOver ();
    }

    public void findComputerMove () {
        computerMove ( 1,alfa,beta );
        if (computerMove == null) {
            findASpot ();
        }
        board.addPlay ( computerMove.getX (),computerMove.getY (),Node.Brick.COMPUTER );
    }

    private void findASpot () {
        final int FINDMIDDLE = 2;
        int x = board.getPLAYFIELDSIZE () / FINDMIDDLE;
        int y = board.getPLAYFIELDSIZE () / FINDMIDDLE;

        Coordinate z = new Coordinate ( y,x );
        if (board.getPositionValue ( z ).equals ( Node.Brick.NOTPLAYED.value )) {
            computerMove = z;
        }
    }

    public void addPosition (int x,int y,Node.Brick s) {
        board.addPlay ( x,y,s );
    }

    public int getScore () {
        return board.score ();
    }

    public Board getBoard () {
        return board;
    }

    /**
     *
     * @param depth
     * @param alfa
     * @param beta
     * @return
     */

    private int computerMove (int depth,int alfa,int beta) {
        if (board.isHasComputerWon ()) {
            return calcWinScore () / depth;
        }
        if (board.isHasHumanWon ()) {
            return -calcWinScore () / depth;
        }
        if (depth == maxDepth) {
            return board.score () / depth;
        }

        List<Coordinate> possiblePlays = board.getPossiblePlaysLimited ();
        //List<Coordinate> possiblePlays = board.getPossiblePlaysLimited2 ();
        Coordinate chosenPlay = null;
        for (Coordinate pos : possiblePlays) {
            board.addPlay ( pos.getX (),pos.getY (),Node.Brick.COMPUTER );
            //printBoard (Node.Brick.COMPUTER.value);
            int score = playerMove ( depth + 1,alfa,beta );
            if (score > alfa && score != 0) {
                alfa = score;
                chosenPlay = pos;
            }
            board.removePlay ( pos.getX (),pos.getY () );
            //printBoard (Node.Brick.NOTPLAYED.value );
            // Stop looping through moves if alfa is greater or equal to beta.
            if (alfa >= beta) {
                break;
            }
        }
        computerMove = chosenPlay;
        return alfa;
    }

    /**
     *
     * @param depth
     * @param alfa
     * @param beta
     * @return
     */
    private int playerMove (int depth,int alfa,int beta) {
        if (board.isHasComputerWon ()) {
            return calcWinScore () / depth;
        }
        if (board.isHasHumanWon ()) {
            return -calcWinScore () / depth;
        }
        if (depth == maxDepth) {
            return board.score () / depth;
        }
        Coordinate chosenPlay = null;
        List<Coordinate> possiblePlays = board.getPossiblePlaysLimited ();
        //List<Coordinate> possiblePlays = board.getPossiblePlaysLimited2 ();
        for (Coordinate pos : possiblePlays) {
            board.addPlay ( pos.getX (),pos.getY (),Node.Brick.HUMAN );
            //printBoard (Node.Brick.NOTPLAYED.value );
            int score = computerMove ( depth + 1,alfa,beta );
            if (score < beta) {
                beta = score;
                chosenPlay = pos;
            }
            board.removePlay ( pos.getX (),pos.getY () );
            //           printBoard (Node.Brick.NOTPLAYED.value );
            if (alfa >= beta) {
                break;
            }
        }
        computerMove = chosenPlay;
        return beta;
    }

    /**
     *
     * @return
     */
    private int calcWinScore () {
        int score = 1000;
        for (int i = 0; i < board.getStreakToWin (); i++) {
            score *= 10;
        }
        return score * board.getStreakToWin ();
    }
}
