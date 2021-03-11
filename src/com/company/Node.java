package com.company;

public class Node {
    private Brick status;

    public Node (Brick s) {
        status = s;
    }

    public String getStatus () {
        return status.toString ();
    }

    public void setStatus (Brick status) {
        this.status = status;
    }

    @Override
    public String toString () {
        return "|" + status + "|";
    }

    public enum Brick {
        HUMAN ( "H" ),
        COMPUTER ( "C" ),
        NOTPLAYED ( "-" );
        String value;
        Brick (String s) {
            value = s;
        }
        @Override
        public String toString () {
            return value;
        }
    }
}
