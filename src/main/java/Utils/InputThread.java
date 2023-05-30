package Utils;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputThread extends Thread {
    private final Scanner scanner;
    private final BlockingQueue<String> userInputQueue;
    private volatile boolean running;

    public InputThread() {
        this.scanner = new Scanner(System.in);
        this.userInputQueue = new LinkedBlockingQueue<>();
        this.running = true;
    }

    @Override
    public void run() {
        while (running && !isInterrupted()) {
            String input = scanner.nextLine();
            if (input != null && !input.isEmpty()) {
                try {
                    userInputQueue.put(input);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        scanner.close();
    }

    public String getUserInput() {
        try {
            return userInputQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void stopThread() {
        running = false;
        interrupt();
    }
}
