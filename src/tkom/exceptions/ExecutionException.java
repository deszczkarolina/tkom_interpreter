package tkom.exceptions;

/**
 * Created by karolina on 25.05.18.
 */
public class ExecutionException extends Exception {

    private String msg;

    public ExecutionException(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
