import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
  static ConnectionPool pool = new ConnectionPool(10);
  static CountDownLatch start = new CountDownLatch(1);
  static CountDownLatch end;
  
  public static void main(String[] args) throws Exception {
    int threadNums = 20;
    end = new CountDownLatch(threadNums);
    int count = 50;
    AtomicInteger got = new AtomicInteger();
    AtomicInteger notGot = new AtomicInteger();
    for (int i = 0; i < threadNums; i++) {
      new Thread(new Task(count, got, notGot)).start();
    }
    start.countDown();
    long startTime = System.currentTimeMillis();
    end.await();
    long endTime = System.currentTimeMillis();
    System.out.println("invoke:" + threadNums * count);
    System.out.println("got:" + got.get());
    System.out.println("notGot:" + notGot.get());
    System.out.println("cost:" + (endTime-startTime) + "ms");
  }
  
  static class Task implements Runnable {
    int count;
    AtomicInteger got;
    AtomicInteger notGot;
    
    public Task(int count, AtomicInteger got, AtomicInteger notGot) {
      this.count = count;
      this.got = got;
      this.notGot = notGot;
    }
    
    public void run() {
      while (count > 0) {
        try {start.await();} catch (Exception e) {}
        try {
          Connection conn = pool.getConnection(1000);
          if (conn != null) {
            try {
              conn.createStatement();
              conn.commit();
            } finally {
              pool.releaseConnection(conn);
              got.getAndIncrement();
            }
          } else {
            notGot.getAndIncrement();
          }
        } catch (Exception e) {
          
        } finally {
          count--;
        }
      }
      end.countDown();
    }
  }
}