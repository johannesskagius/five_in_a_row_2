package com.company;

public class Main {
    private final int PLAYFIELDSIZE = 6;
    private final int MAX_DEPTH = 5;
    private final Play play = new Play ( MAX_DEPTH,PLAYFIELDSIZE );
    private boolean isEnded = false;

    public static void main (String[] args) {
        Main m = new Main ();
        //  m.testScore ();
        // m.testScore2 ();
        //m.testScore3 ();
        // test for 4*4
        //m.testScore4 ();
        //m.testScore5 ();
        //m.testScore6 ();    //Working test the T fungerar för 4*4
        /**
         * Tests for 6*6
         */
        //m.testScore7 ();
        //m.testScore8 ();
        //m.testScore9 ();
        //m.testScoreAIChoice10 ();
        //m.testScoreAIChoice11 ();
        //m.testComAi1 ();
        m.play ();
    }


    private void play () {
        Scan s = new Scan ();
        while (true) {
            play.printBoard ();
            int x = getChoice ( s,"X: " );
            int y = getChoice ( s,"Y: " );
            Coordinate move = new Coordinate ( x,y );
            long start = System.currentTimeMillis ();
            isEnded = play.makeATurn ( move,Node.Brick.HUMAN );
            System.out.println ( (System.currentTimeMillis () - start) + "ms" );
            // play.printBoard ();
        }
    }

    private int getChoice (Scan s,String s2) {
        int choice;
        do {
            System.out.print ( s2 );
            choice = s.getInt ();
        } while (!(0 <= choice && choice < PLAYFIELDSIZE));
        return choice;
    }

    private void testScore () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );

        //play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 1,2,Node.Brick.COMPUTER );
        play.addPosition ( 0,2,Node.Brick.COMPUTER );
        b.addPlay ( 0,1,Node.Brick.COMPUTER );
        play.printBoard ();
        boolean x = b.hasPlayerWon ( Node.Brick.HUMAN.value );
//        System.out.println (x +" score: " +b.score ());
//        play.findComputerMove ();
//        int i = play.getScore ();
//        System.out.println (i);
//        play.printBoard ();
    }

    private void testScore2 () {
        Board b = play.getBoard ();
        int x = 1;
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        //play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 2,4,Node.Brick.HUMAN );
        // play.addPosition ( 1, 1, Node.Brick.COMPUTER  );
        play.addPosition ( 1,1,Node.Brick.COMPUTER );
        play.addPosition ( 2,1,Node.Brick.COMPUTER );
        play.addPosition ( 3,1,Node.Brick.COMPUTER );
        b.printBoard ();
        int i = b.score ();
        System.out.println ( i );
    }

    private void testScore3 () {
        Board b = play.getBoard ();
        int x = 1;
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        play.addPosition ( 2,4,Node.Brick.HUMAN );
        play.addPosition ( 1,1,Node.Brick.COMPUTER );
        play.addPosition ( 3,1,Node.Brick.COMPUTER );

        //play.addPosition ( 3, 4, Node.Brick.COMPUTER  );
        play.addPosition ( 2,1,Node.Brick.COMPUTER );

        b.printBoard ();
        System.out.println ( b.score () );
    }

    private void testScore4 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 1,1,Node.Brick.COMPUTER );
        play.addPosition ( 3,1,Node.Brick.COMPUTER );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        b.printBoard ();
        long start = System.currentTimeMillis ();
        play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.HUMAN.value ) );
    }


    /**
     * Chooses win before save
     */
    private void testScore5 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 1,2,Node.Brick.COMPUTER );
        play.addPosition ( 1,3,Node.Brick.COMPUTER );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        //b.printBoard ();
        //long start = System.currentTimeMillis ();
        play.findComputerMove ();
        //System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.COMPUTER.value ) );
    }

    /**
     * For playfield 4*4 with MAXDEPTH of 3 with 3 in a row to win
     */
    private void testScore6 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 1,1,Node.Brick.COMPUTER );
        play.addPosition ( 3,1,Node.Brick.COMPUTER );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        //b.printBoard ();
        long start = System.currentTimeMillis ();
        play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.COMPUTER.value ) );
    }

    /**
     * for 6*6
     */
    private void testScore7 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        play.addPosition ( 2,4,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 1,1,Node.Brick.COMPUTER );
        play.addPosition ( 2,1,Node.Brick.COMPUTER );
        play.addPosition ( 3,1,Node.Brick.COMPUTER );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        long start = System.currentTimeMillis ();
        // play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.COMPUTER.value ) );
    }

    /**
     * for 6*6
     * equal playing field gives the same score!
     */
    private void testScore8 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        play.addPosition ( 2,4,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 4,2,Node.Brick.COMPUTER );
        play.addPosition ( 4,3,Node.Brick.COMPUTER );
        play.addPosition ( 4,4,Node.Brick.COMPUTER );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        long start = System.currentTimeMillis ();
        // play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.COMPUTER.value ) );
    }

    /**
     * for 6*6
     * equal playing field gives the same score!
     */
    private void testScore9 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,1,Node.Brick.HUMAN );
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        play.addPosition ( 2,4,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 4,2,Node.Brick.COMPUTER );
        play.addPosition ( 4,3,Node.Brick.COMPUTER );
        play.addPosition ( 4,4,Node.Brick.COMPUTER );
        //play.addPosition ( 4, 1, Node.Brick.COMPUTER  );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        long start = System.currentTimeMillis ();
        // play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.HUMAN.value ) );
    }

    /**
     * for 6*6
     * equal playing field gives the same score!
     */
    private void testScoreAIChoice10 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        play.addPosition ( 2,4,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 4,2,Node.Brick.COMPUTER );
        play.addPosition ( 4,3,Node.Brick.COMPUTER );
        play.addPosition ( 4,4,Node.Brick.COMPUTER );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        long start = System.currentTimeMillis ();
        play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.COMPUTER.value ) );
    }

    private void testScoreAIChoice11 () {
        Board b = play.getBoard ();
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 4,2,Node.Brick.COMPUTER );
        play.addPosition ( 4,3,Node.Brick.COMPUTER );
        b.hasPlayerWon ( Node.Brick.HUMAN.value );
        long start = System.currentTimeMillis ();
        play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        b.printBoard ();
        System.out.println ( b.score () + " " + b.hasPlayerWon ( Node.Brick.COMPUTER.value ) );
    }

    private void testComAi1 () {
        Board b = play.getBoard ();
        int x = 1;
        play.addPosition ( 2,2,Node.Brick.HUMAN );
        play.addPosition ( 2,3,Node.Brick.HUMAN );
        // play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 1,1,Node.Brick.COMPUTER );
        play.addPosition ( 3,1,Node.Brick.COMPUTER );
        b.printBoard ();
        long start = System.currentTimeMillis ();
        play.findComputerMove ();
        System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        int i = b.score ();
        System.out.println ( i );
    }
}
