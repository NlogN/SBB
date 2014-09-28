package ru.sbb.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 24.08.14
 */
public class TrainNotFoundException extends Exception {
    private int trainNumber;

    public TrainNotFoundException(String text) {
        super(text);
    }

    public TrainNotFoundException(String text, int trainNumber) {
        super(text);
        this.trainNumber=trainNumber;
    }

    public int getTrainNumber() {
        return trainNumber;
    }
}
