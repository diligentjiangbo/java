import java.util.*;
import java.util.concurrent.*;

public class CyclicBarrierDemo {
  public static void main(String[] args) {
    CyclicBarrier barrier = new CyclicBarrier(3);
    class Task implements Runnable {
      private String name;
      private CyclicBarrier barrier;
      Task(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
      }
      public void run() {
        try {
          TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5000));
          System.out.println(this.name + " ok");
          barrier.await();
          System.out.println(this.name + " hello world");
        } catch (Exception e) {
          
        }
      }
    }
    for (char i = 'A'; i <= 'C'; i++) {
      new Thread(new Task(String.valueOf(i), barrier)).start();
    }
  }
}