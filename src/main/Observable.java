package main;

/**
 * Created by anderson on 02/04/17.
 */
public interface Observable {

    void attachObserver(Observer observer);
    void detachObserver(Observer observer);
    void notifyObservers();

}
