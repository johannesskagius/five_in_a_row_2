package com.company;

import java.util.Scanner;

public class Scan {
    private final Scanner input = new Scanner ( System.in );

    public int getInt () throws NumberFormatException {
        return Integer.parseInt ( input.nextLine () );
    }
}
