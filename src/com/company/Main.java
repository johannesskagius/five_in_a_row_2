package com.company;

public class Main {
    private Play play = new Play ( 2, 5 );
    private boolean isEnded = false;

    public static void main (String[] args) {
        Main m = new Main ();
      //  m.testScore ();
        m.testScore2 ();
        //m.play ();
    }

    private void play () {
        Scan s = new Scan ();
        while (!isEnded) {
            play.printBoard ();
            System.out.print ( "X: " );
            int x = s.getInt ();
            System.out.print ( "Y: " );
            int y = s.getInt ();
            Coordinate move = new Coordinate ( x,y );
            long start = System.currentTimeMillis ();
            isEnded = play.makeATurn ( move,Node.Brick.HUMAN );
            System.out.println ( (System.currentTimeMillis () - start) + "ms" );
        }
    }

    private void testScore(){
        Board b = play.getBoard ();
        play.addPosition ( 2, 2, Node.Brick.HUMAN );
        play.addPosition ( 2, 3, Node.Brick.HUMAN );
        //play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 2, 4, Node.Brick.HUMAN );
        play.addPosition ( 1, 2, Node.Brick.COMPUTER  );
        play.addPosition ( 0, 2, Node.Brick.COMPUTER  );
        b.addPlay ( 0, 1, Node.Brick.COMPUTER  );
        play.printBoard ();
        boolean x = b.hasPlayerWon ( Node.Brick.HUMAN.value );
        System.out.println (x);
        play.findComputerMove ();
        int i = play.getScore ();
        System.out.println (i);
        play.printBoard ();
    }

    private void testScore2 () {
        Board b = play.getBoard ();
        int x = 1;
        play.addPosition ( 2, 2, Node.Brick.HUMAN );
        play.addPosition ( 2, 3, Node.Brick.HUMAN );
        //play.addPosition ( 2, 1, Node.Brick.HUMAN );
        play.addPosition ( 2, 4, Node.Brick.HUMAN );
       // play.addPosition ( 1, 1, Node.Brick.COMPUTER  );
        play.addPosition ( 1, 1, Node.Brick.COMPUTER  );
        play.addPosition ( 2, 1, Node.Brick.COMPUTER  );
        play.addPosition ( 3, 1, Node.Brick.COMPUTER  );
        b.printBoard ();
        int i = b.score ();
        System.out.println (i);
    }
}
