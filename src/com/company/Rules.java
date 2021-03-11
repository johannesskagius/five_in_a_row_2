/**
 * author josk3261 Johannes Skagius
 */

package com.company;

public interface Rules {
    boolean hasPlayerWon (String player);
    boolean isATie();
    int score ();
}
