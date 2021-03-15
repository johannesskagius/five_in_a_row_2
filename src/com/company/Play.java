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

    public Play (int maxDepth,int playFieldSize,int streakToWin) {
        this.maxDepth = maxDepth;
        alfa = ALPHA_START_VALUE;
        beta = BETA_START_VALUE;
        board = new Board ( playFieldSize,streakToWin );
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
     * This method evaluates and simulates the play in multiple scenarios ahead by using Alpha-beta prunig
     * <p>
     * {@link #maxDepth} describes how many moves ahead.
     * {@link #alfa} describes the best possible score for the maximizing player
     * {@link #beta} describes the best possible score for the minimizing  player
     * <p>
     * The method starts by checking if a player has won
     * If a player has won is true the method calls {@link Score#calcWinScore()} which evaluates if it looks like any of the players are in a better position.
     * If nobody has won the method checks if the {@link #maxDepth, maxdepth} equals depth is reached. If it has the method evaluates the board by calling {@link Board#score()}
     * <p>
     * If maxDepth hasn't been reached a limited List of plays is collected by {@link Board#getPossiblePlaysLimited()}, the method then adds the first play (pos) in the
     * enhanced for loop below.
     * to the board and then calling the {@link #humanMove(int,int,int)} recursively, which will repeat the procedure above until a player has won or maxdepth is reached.
     * <p>
     * if @param alfa gets bigger or equal to beta than the algorithm breaks the loop meaning that the parents sibling has better option and therefore
     * these scenarios won't by played.
     * <p>
     * This is only simulated which means we will have to delete the move
     * </p>
     *
     * @param depth is the current depth which we are at. Depth is how many moves ahead the algorithm is simulating
     * @param alfa  is set by the maximizing player, the computer.
     * @param beta  is set by the minimizing player, the human.
     * @return alfa value for the computer or beta for human player.
     * @see <a href="https://en.wikipedia.org/wiki/Alpha–beta_pruning"> for more general information about the Minmax algorithm with
     */
    int computerMove (int depth,int alfa,int beta) {
        if (board.isHasComputerWon ()) return board.getWinningScore () / depth;
        if (board.isHasHumanWon ()) return -board.getWinningScore () / depth;
        if (depth == maxDepth) return board.score () / depth;

        List<Coordinate> possiblePlays = board.getPossiblePlaysLimited ();
        Coordinate chosenPlay = null;
        for (Coordinate pos : possiblePlays) {
            board.addPlay ( pos.getX (),pos.getY (),Node.Brick.COMPUTER );
            int score = humanMove ( depth + 1,alfa,beta );
            if (score > alfa && score != 0) {
                alfa = score;
                chosenPlay = pos;
            }
            board.removePlay ( pos.getX (),pos.getY () );
            if (alfa >= beta) {
                break;
            }
        }
        computerMove = chosenPlay;
        return alfa;
    }

    /**
     * This method evaluates and simulates the play in multiple scenarios ahead by using Alpha-beta pruning
     * <p>
     * for more information please read the javadoc for {@link #computerMove(int,int,int)}
     * this method repeats that method but simulating the humansMove instead of the AI move.
     *
     * @param depth is the current depth which we are at. Depth is how many moves ahead the algorithm is simulating
     * @param alfa  is set by the maximizing player, the computer.
     * @param beta  is set by the minimizing player, the human.
     * @return alfa value for the computer or beta for human player.
     * @see <a href="https://en.wikipedia.org/wiki/Alpha–beta_pruning"> for more general information about the Minmax algorithm with
     */
    int humanMove (int depth,int alfa,int beta) {
        if (board.isHasComputerWon ()) return board.getWinningScore () / depth;
        if (board.isHasHumanWon ()) return -board.getWinningScore () / depth;
        if (depth == maxDepth) return board.score () / depth;
        Coordinate chosenPlay = null;
        List<Coordinate> possiblePlays = board.getPossiblePlaysLimited ();
        for (Coordinate pos : possiblePlays) {
            board.addPlay ( pos.getX (),pos.getY (),Node.Brick.HUMAN );
            int score = computerMove ( depth + 1,alfa,beta );
            if (score < beta) {
                beta = score;
                chosenPlay = pos;
            }
            board.removePlay ( pos.getX (),pos.getY () );
            if (alfa >= beta) {
                break;
            }
        }
        computerMove = chosenPlay;
        return beta;
    }
}
