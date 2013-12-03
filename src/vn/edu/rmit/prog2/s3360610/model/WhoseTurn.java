/**
 *
 * @author nhanb
 */
package vn.edu.rmit.prog2.s3360610.model;

import java.util.Observable;
import java.util.Observer;

public class WhoseTurn extends Observable {

    // Decides whether it is the player's turn
    private boolean players = false;

    public void switchTurn() {
        players = !players;
        setChanged();
        notifyObservers();
    }

    public void setPlayersTurn(boolean t) {
        players = t;
        setChanged();
        notifyObservers();

    }

    public boolean isPlayers() {
        return players;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
