import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CarQueue {

    private final Queue<Integer> queue;
    private final Random random;

    public CarQueue() {
        queue = new LinkedList<>();
        random = new Random();

        // Initialize the queue with random values
        for (int i = 0; i < 7; i++) {
            queue.add(random.nextInt(4));
        }
    }

    public void addToQueue() {
        Runnable addRandomTask = () -> {
            while (true) {
                synchronized (queue) {
                    queue.add(random.nextInt(4));
                }
                try {
                    Thread.sleep(200); // Sleep for 200ms before adding another element
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupt status
                    break; // Exit the loop if interrupted
                }
            }
        };

        Thread thread = new Thread(addRandomTask);
        thread.setDaemon(true); // Mark the thread as a daemon to exit when the program stops
        thread.start();
    }

    public int deleteQueue() {
        synchronized (queue) {
            if (!queue.isEmpty()) {
                return queue.remove();
            } else {
                throw new IllegalStateException("Queue is empty. Cannot remove an element.");
            }
        }
    }
}
