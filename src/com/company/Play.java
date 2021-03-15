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
     * This method evaluates and simulates the play in multiple scenarios ahead by using Alpha-beta prunig
     *
     * {@link #maxDepth} describes how many moves ahead.
     * {@link #alfa} describes the best possible score for the maximizing player
     * {@link #beta} describes the best possible score for the minimizing  player
     *<p>
     * The method starts by checking if a player has won or if the{@link #maxDepth} is reached. If any is true the method calls {@link Board#score()} which will evaluate if it looks like
     * or if a player has won.
     *</p>
     *
     * Min max algorithm
     *
     *
     * @param depth
     * @param alfa
     * @param beta
     * @return
     */

    /**
     * Den här metoden simulerar spelets gång i flera steg (depth) för att slutligen få fram det bästa draget för den maximerande spelaren (COMPUTER_PLAYER).
     * Metoden använder sig av den så kallade min-max strategin tillsammans med optimeringen alpha-beta pruning.
     * <p>
     * Metoden börjar med att hämta alla möjliga drag för en given situation. Varje drag prövas, en åt gången, och för varje drag anropar minmax rekursivt och nästa spelares drag simuleras.
     * Spelet spelas antingen tills maxdjupet är nått eller om någon av spelarna vinner. Vid någon av dessa händelser returneras ett värde som baseras på hur nära en vinst den maximerande spelaren är.
     * <p>
     * Den maximerande spelaren försöker hitta det drag med högst värdet, sparar detta i variabel alpha och returnerar värdet till den minimerande spelaren.
     * Returnvärdet används av den minimerande spelaren som i sin tur letar efter det minsta värdet, sparar detta i variabeln beta och returnerar värdet till den maximerande spelaren.
     * Och så upprepas det om och om igen i X antal gånger (maxDepth eller vinst).
     * <p>
     * Om alpha skulle bli större eller lika med beta under genomgången av möjliga drag bryts loopen (alpha-beta pruning).
     * Inga fler drag behöver utvärderas då föräldrarnoden inte kommer att väljas ändå. Ett bättre syskondrag till föräldern finns redan.
     * <p>
     * Eftersom metoden bara är en simulation för att räkna ut det bästa draget återställs de prövade dragen när de fått ett värde.
     * Det bästa draget som hittats av metoden sparas i den publika globala variabeln computerMove som sedan kan användas för att göra det faktiska draget.
     *
     * @param player spelaren som ska simuleras. Ska vara någon av konstanterna COMPUTER_PLAYER eller HUMAN_PLAYER
     * @param depth  vilket djup man befinner sig på. Måste vara 0 eller större.
     * @param alpha  alphavärdet som sätts av den maximerande spelaren och som används vid alpha-beta pruning. Måste vara minimum integer vid djup 0
     * @param beta   betavärdet som sätts av den minimerande spelaren och som används vid alpha-beta pruning. Måste vara maximul integer vid djup 0
     * @return alphavärdet om player är den maximerande spelaren (COMPUTER_PLAYER) eller betavärdet om player är den minimerande spelaren (HUMAN_PLAYER)
     * @throws IllegalArgumentException om djupet är mindre än 0 eller större än maxDepth
     * @throws IllegalArgumentException om spelaren inte är COMPUTER_PLAYER och inte är HUMAN_PLAYER
     * @throws IllegalArgumentException om depth är 0 och (alpha inte är ALPHA_START_VALUE eler beta inte är BETA_START_VALUE)
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
