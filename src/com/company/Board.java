//  author josk3261 Johannes Skagius
// Stockholms university
// Kurs: ALDA - algoritmer och datastrukturer
package com.company;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int PLAYFIELDSIZE;// = 6;
    private boolean isGameOver = false;
    private int streakToWin;// = 5;//PLAYFIELDSIZE - 1;/// 2;
    private Node[][] playField;
    private List<Coordinate> playedPositions = new ArrayList<> ();
    private boolean hasHumanWon;
    private boolean hasComputerWon;
    private ArrayList<Coordinate> possiblePlays = new ArrayList<> ();


    public Board (int playFieldSize, int streakToWin) {
        this.streakToWin = streakToWin;
        this.PLAYFIELDSIZE = playFieldSize;
        playField = new Node[playFieldSize][playFieldSize];
        addPlayField ();
    }

    public void addPlay (int x,int y,Node.Brick s) {
        playField[y][x].setStatus ( s );
        Coordinate cor = new Coordinate ( y,x );
        playedPositions.add ( cor );
        if(possiblePlays.contains ( cor ))
            possiblePlays.remove ( cor );
        if(s.value.equals ( Node.Brick.HUMAN.value ))
            hasHumanWon = hasPlayerWon ( cor, s.value );
        else
            hasComputerWon = hasPlayerWon ( cor, s.value );
    }

    public boolean isHasHumanWon () {
        return hasHumanWon;
    }

    public boolean isHasComputerWon () {
        return hasComputerWon;
    }

    public void removePlay (int x,int y) {
        playField[y][x].setStatus ( Node.Brick.NOTPLAYED );
        Coordinate l = new Coordinate ( y,x );
        playedPositions.remove ( l );
    }

    protected ArrayList<Coordinate> getPossiblePlaysLimited () {
        final String NOTPLAYED = Node.Brick.NOTPLAYED.value;
        ArrayList<Coordinate> possiblePlays = new ArrayList<> ();
        Coordinate i;
        for (int y = 0; y < PLAYFIELDSIZE; y++) {
            for (int x = 0; x < PLAYFIELDSIZE; x++) {
                if (playField[y][x].getStatus ().equals ( NOTPLAYED )) {
                    i = new Coordinate ( y,x );
                    if (!possiblePlays.contains ( i ) && hasNeighbor ( y,x ))
                        possiblePlays.add ( i );
                }
            }
        }
        return possiblePlays;
    }

    private boolean hasNeighbor (int y,int x) {
        return checkPosition ( y + 1,x ) ||
                checkPosition ( y - 1,x ) ||
                checkPosition ( y,x + 1 ) ||
                checkPosition ( y,x + 1 ) ||
                checkPosition ( y,x - 1 ) ||
                checkPosition ( y,x - 1 ) ||
                checkPosition ( y - 1,x + 1 ) ||
                checkPosition ( y - 1,x + 1 ) ||
                checkPosition ( y - 1,x - 1 ) ||
                checkPosition ( y - 1,x - 1 ) ||
                checkPosition ( y + 1,x + 1 ) ||
                checkPosition ( y + 1,x - 1 );
    }

    private boolean checkPosition (int y,int x) {
        try {
            if (playField[y][x].getStatus ().equals ( Node.Brick.NOTPLAYED.value )) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private void addPlayField () {
        for (int i = 0; i < PLAYFIELDSIZE; i++) {
            for (int j = 0; j < PLAYFIELDSIZE; j++) {
                playField[i][j] = new Node ( Node.Brick.NOTPLAYED );
            }
        }
    }

    public void printBoard () {
        int row = 0;
        StringBuilder s = new StringBuilder ();
        for (Node[] n : playField) {
            System.out.print ( row++ );
            for (int i = 0; i < PLAYFIELDSIZE; i++) {
                s.append ( "|" ).append ( n[i].getStatus () ).append ( "|" );
            }
            System.out.println ( s );
            s = new StringBuilder ();
        }
    }

    public String getPositionValue (Coordinate position) {
        return playField[position.getX ()][position.getY ()].getStatus ();
    }

    public boolean isPositionOpen (Coordinate position) throws ArrayIndexOutOfBoundsException {
        return playField[position.getY ()][position.getX ()].getStatus ().equals ( Node.Brick.NOTPLAYED.value );
    }

    public int getStreakToWin () {
        return streakToWin;
    }


    public int getPLAYFIELDSIZE () {
        return PLAYFIELDSIZE;
    }


    public Node[][] getPlayField () {
        return playField;
    }

    public boolean isGameOver () {
        return isGameOver;
    }

    //Only for testing
    public boolean hasPlayerWon (String player) {
        //Minimum positions to be played before anyone can win
        if (playedPositions.size () < streakToWin * 2 - 1) return false;

        boolean playerWon = false;
        for (Coordinate x : playedPositions) {
            if (playField[x.getY ()][x.getX ()].getStatus ().equals ( player )) {
                playerWon = hasPlayerWon ( x,player );
                if (playerWon) break;
            }
        }
        if (playerWon)
            isGameOver = playerWon;
        return playerWon;
    }

    private boolean hasPlayerWon (Coordinate position,String player) {
        final int START_COUNT = 0;
        int y = position.getY ();
        int x = position.getX ();

        int xPos = START_COUNT;
        int xMin = START_COUNT;
        int yPos = START_COUNT;
        int yMin = START_COUNT;
        int rightUp = START_COUNT;
        int leftUp = START_COUNT;
        int rightDown = START_COUNT;
        int leftDown = START_COUNT;

        for (int i = START_COUNT; i <= streakToWin; i++) {
            //Check all rows uppwards
            if (y - i >= START_COUNT) {
                //testWhichNodeAreWeLookingAt(playField[y-i][x]);
                if (playField[y - i][x].getStatus ().equals ( player ))
                    yPos++;
                else yPos = START_COUNT;
            }
            //Check all rows downWards
            if (y + i <= streakToWin) {
                //testWhichNodeAreWeLookingAt(playField[y+i][x]);
                if (playField[y + i][x].getStatus ().equals ( player ))
                    yMin++;
                else yMin = START_COUNT;
            }

            if (x + i < streakToWin) {
                //Check all kolumns rightwards
                //testWhichNodeAreWeLookingAt ( playField[y][x + i] );
                if (playField[y][x + i].getStatus ().equals ( player ))
                    xPos++;
                else xPos = START_COUNT;
            }

            if (x - i >= 0) {
                //Check all kolumns left wards
                int s = x - i;
                //testWhichNodeAreWeLookingAt(playField[y][s]);
                if (playField[y][s].getStatus ().equals ( player ))
                    xMin++;
                else xMin = START_COUNT;
            }
            //Right upwards
            if (x + i < streakToWin && y - i >= 0) {
                //testWhichNodeAreWeLookingAt(playField[y-i][x+i]);
                if (playField[y - i][x + i].getStatus ().equals ( player ))
                    rightUp++;
                else rightUp = START_COUNT;
            }
            //Left upwards
            if (x - i >= 0 && y - i >= 0) {
                // testWhichNodeAreWeLookingAt(playField[y-i][x-i]);
                if (playField[y - i][x - i].getStatus ().equals ( player )) leftUp++;
                else leftUp = START_COUNT;
            }
            //RightDown
            if (x + i < streakToWin && y + i < streakToWin) {
                //testWhichNodeAreWeLookingAt(playField[y+i][x+i]);
                if (playField[y + i][x + i].getStatus ().equals ( player ))
                    rightDown++;
                else rightDown = START_COUNT;
            }
            //left down
            if (x - i >= 0 && y + i < streakToWin) {
                if (playField[y + i][x - i].getStatus ().equals ( player ))
                    leftDown++;
                else leftDown = START_COUNT;
            }

            if (xPos >= streakToWin || xMin >= streakToWin || yPos >= streakToWin || yMin >= streakToWin || rightUp >= streakToWin || rightDown >= streakToWin || leftDown >= streakToWin || leftUp >= streakToWin) {
                return true;
            }
        }
        return false;
    }

    public int score () {
        Score score = new Score ( this);
        int human = score.getScore ( Node.Brick.HUMAN.value );
        int computer = score.getScore ( Node.Brick.COMPUTER.value );
        return computer - human;
    }
}
