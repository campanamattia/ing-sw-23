package Utils;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputThread extends Thread{
    private final Scanner scanner;
    private final BlockingQueue<String> userInputQueue;

    public InputThread() {
        this.scanner = new Scanner(System.in);
        this.userInputQueue = new LinkedBlockingQueue<>();
    }

    public void run() {
        while (!isInterrupted()) {
            String input = scanner.nextLine();
            if (input != null && !input.isEmpty()) {
                try {
                    userInputQueue.put(input);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public String getUserInput() {
        try {
            return userInputQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
