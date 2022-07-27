import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExampleCuntDownLatch {
    public static void main(String[] args) throws InterruptedException {
        // данный объект передаётся всем потокам
        CountDownLatch countDownLatch = new CountDownLatch(3); // нужно отсчитать 3 раза для открытия завёртыша
        // создание трёх потоков
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // передача задания для потоков
        for (int i = 0; i < 3; i++)
            executorService.submit(new Processor(i, countDownLatch));
        executorService.shutdown();

        // отсчёт
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            countDownLatch.countDown();
        }
    }
}
class Processor implements Runnable {
    private int id;
    private CountDownLatch countDownLatch;

    public Processor(int id, CountDownLatch countDownLatch) {
        this.id = id;
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(3000); // торможение каждого потока на 3 секунды
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            countDownLatch.await(); // ожидание открытия завёртыша
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread with id " + id + " proceeded.");
    }
}
