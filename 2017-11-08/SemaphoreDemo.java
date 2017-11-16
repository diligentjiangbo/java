import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class SemaphoreDemo {
  private Lock lock = new ReentrantLock();
  private Condition condition = lock.newCondition();
  private int num;
  public SemaphoreDemo(int num) {
    this.num = num;
  }
  
  public void acquire() throws InterruptedException {
    lock.lock();
    try {
      while (num <= 0) {
        condition.await();
      }
      num--;
    } finally {
      lock.unlock();
    }
    
  }
  
  public void release() throws InterruptedException {
    lock.lock();
    try {
      num++;
      condition.signal();
    } finally {
      lock.unlock();
    }
  }
  
  public static void main(String[] args) {
    SemaphoreDemo semaphoreDemo = new SemaphoreDemo(1);
    class Task implements Runnable {
      private SemaphoreDemo semaphoreDemo;
      public Task(SemaphoreDemo semaphoreDemo) {
        this.semaphoreDemo = semaphoreDemo;
      }
      public void run() {
        try {
          semaphoreDemo.acquire();
          System.out.println(Thread.currentThread().getName() + "acquire");
          TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
          
        } finally {
          try {
            semaphoreDemo.release();
            System.out.println(Thread.currentThread().getName() + "release");
          } catch (Exception e) {
            
          }
        }
      }
    }
    
    Runnable task1 = new Task(semaphoreDemo);
    Runnable task2 = new Task(semaphoreDemo);
    Runnable task3 = new Task(semaphoreDemo);
    new Thread(task1).start();
    new Thread(task2).start();
    new Thread(task3).start();
  }
}