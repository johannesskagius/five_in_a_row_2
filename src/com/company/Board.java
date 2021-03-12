/**
 * author josk3261 Johannes Skagius
 */
package com.company;

import java.util.ArrayList;
import java.util.List;

public class Board implements Rules {
    private Score score;
    private int PLAYFIELDSIZE = 6;
    private boolean isGameOver = false;
    private int ROW_TO_WIN = 5;//PLAYFIELDSIZE - 1;/// 2;
    private Node[][] playField;
    private List<Coordinate> playedPositions = new ArrayList<> ();
    private boolean hasHumanWon;
    private boolean hasComputerWon;
    private ArrayList<Coordinate> possiblePlays = new ArrayList<> ();


    public Board (int playFieldSize) {
        this.PLAYFIELDSIZE = playFieldSize;
        playField = new Node[playFieldSize][playFieldSize];
        addPlayField ();
        score = new Score ( this );
    }

    public void addPlay (int x,int y,Node.Brick s) {
        playField[y][x].setStatus ( s );
        Coordinate cor = new Coordinate ( y,x );
        playedPositions.add ( cor );
        if(possiblePlays.contains ( cor ))
            possiblePlays.remove ( cor );
        //addFreeNeigbors ( cor );
        if(s.value.equals ( Node.Brick.HUMAN.value ))
            hasHumanWon = hasPlayerWon ( cor, s.value );
        else
            hasComputerWon = hasPlayerWon ( cor, s.value );
    }

    private void addFreeNeigbors (Coordinate cor) {
        int x = cor.getX ();
        int y = cor.getY ();
        neighbourStatus ( x+1, y ); // to the right
        neighbourStatus ( x-1, y ); // to the left
        neighbourStatus ( x, y + 1 ); //below;
        neighbourStatus ( x, y - 1 ); //above;
        neighbourStatus ( x + 1, y - 1 ); //top right;
        neighbourStatus ( x - 1, y - 1 ); //top left;
        neighbourStatus ( x + 1, y + 1 ); //down right
        neighbourStatus ( x - 1, y + 1 ); //down left
    }

    private void neighbourStatus(int x, int y) throws ArrayIndexOutOfBoundsException{
        final String NOTINUSE = Node.Brick.NOTPLAYED.value;
        try {
            if(playField[y][x].getStatus ().equals ( NOTINUSE )){
                Coordinate posAdd =new Coordinate ( y, x );
                if(!possiblePlays.contains ( posAdd )){
                    possiblePlays.add ( posAdd );
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    public boolean isHasHumanWon () {
        return hasHumanWon;
    }


    public boolean isHasComputerWon () {
        return hasComputerWon;
    }

    public void removePlay (int x,int y) {
        playField[y][x].setStatus ( Node.Brick.NOTPLAYED );
        Coordinate l = new Coordinate ( x,y );
        playedPositions.remove ( l );
    }

    protected ArrayList<Coordinate> getAllPossiblePlays () {
        final String NOTPLAYED = Node.Brick.NOTPLAYED.value;
        ArrayList<Coordinate> possiblePlays = new ArrayList<> ();
        Coordinate i;
        for (int y = 0; y < PLAYFIELDSIZE; y++) {
            for (int x = 0; x < PLAYFIELDSIZE; x++) {
                if (playField[y][x].getStatus ().equals ( NOTPLAYED )) {
                    possiblePlays.add ( new Coordinate ( y,x ) );
                }
            }
        }
        return possiblePlays;
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

    public ArrayList<Coordinate> getPossiblePlaysLimited2 () {
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
        String x = "";
        for (int i = 0; i < PLAYFIELDSIZE; i++) {
            if (i == 0) {
                x = "t|" + i + "||";
            } else
                x = i + "||";
            if (i == PLAYFIELDSIZE - 1) {
                x += "\n";
            }
        }
        int column = 1;
        int row = 0;
        String s = "";
        for (Node[] n : playField) {
            System.out.print ( row++ );
            for (int i = 0; i < PLAYFIELDSIZE; i++) {
                s += "|" + n[i].getStatus () + "|";
            }
            System.out.println ( s );
            s = "";
        }
    }

    public String getPositionValue (Coordinate position) {
        return playField[position.getX ()][position.getY ()].getStatus ();
    }

    public boolean isPositionOpen (Coordinate position) throws ArrayIndexOutOfBoundsException {
        return playField[position.getY ()][position.getX ()].getStatus ().equals ( Node.Brick.NOTPLAYED.value );
    }

    public int getROW_TO_WIN () {
        return ROW_TO_WIN;
    }

    public void setROW_TO_WIN (int ROW_TO_WIN) {
        this.ROW_TO_WIN = ROW_TO_WIN;
    }

    public int getPLAYFIELDSIZE () {
        return PLAYFIELDSIZE;
    }

    public void setPLAYFIELDSIZE (int PLAYFIELDSIZE) {
        this.PLAYFIELDSIZE = PLAYFIELDSIZE;
    }

    public Node[][] getPlayField () {
        return playField;
    }

    public void setPlayField (Node[][] playField) {
        this.playField = playField;
    }

    public Board getBoard () {
        return this;
    }

    public List<Coordinate> getPlayedPositions () {
        return playedPositions;
    }

    public void setPlayedPositions (List<Coordinate> playedPositions) {
        this.playedPositions = playedPositions;
    }

    public boolean isGameOver () {
        return isGameOver;
    }



    @Override  //Används inte längre
    public boolean hasPlayerWon (String player) {
        //Minimum positions to be played before anyone can win
        if (playedPositions.size () < ROW_TO_WIN * 2 - 1) return false;

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

        for (int i = START_COUNT; i <= ROW_TO_WIN; i++) {
            //Check all rows uppwards
            if (y - i >= START_COUNT) {
                //testWhichNodeAreWeLookingAt(playField[y-i][x]);
                if (playField[y - i][x].getStatus ().equals ( player ))
                    yPos++;
                else yPos = START_COUNT;
            }
            //Check all rows downWards
            if (y + i <= ROW_TO_WIN) {
                //testWhichNodeAreWeLookingAt(playField[y+i][x]);
                if (playField[y + i][x].getStatus ().equals ( player ))
                    yMin++;
                else yMin = START_COUNT;
            }

            if (x + i < ROW_TO_WIN) {
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
            if (x + i < ROW_TO_WIN && y - i >= 0) {
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
            if (x + i < ROW_TO_WIN && y + i < ROW_TO_WIN) {
                //testWhichNodeAreWeLookingAt(playField[y+i][x+i]);
                if (playField[y + i][x + i].getStatus ().equals ( player ))
                    rightDown++;
                else rightDown = START_COUNT;
            }
            //left down
            if (x - i >= 0 && y + i < ROW_TO_WIN) {
                if (playField[y + i][x - i].getStatus ().equals ( player ))
                    leftDown++;
                else leftDown = START_COUNT;
            }

            if (xPos >= ROW_TO_WIN || xMin >= ROW_TO_WIN || yPos >= ROW_TO_WIN || yMin >= ROW_TO_WIN || rightUp >= ROW_TO_WIN || rightDown >= ROW_TO_WIN || leftDown >= ROW_TO_WIN || leftUp >= ROW_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isATie () {
        for (int i = 0; i < PLAYFIELDSIZE; i++) {
            for (int j = 0; j < PLAYFIELDSIZE; j++) {
                if (playField[i][j].getStatus ().equalsIgnoreCase ( "-" )) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int score () {
        int human = score.getScore ( Node.Brick.HUMAN.value );
        int computer = score.getScore ( Node.Brick.COMPUTER.value );
        return computer - human;
    }
}
