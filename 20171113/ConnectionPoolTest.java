import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.sql.*;

public class ConnectionPoolTest {
  static ConnectionPool pool = new ConnectionPool(10);
  static CountDownLatch start = new CountDownLatch(1);
  static CountDownLatch end;
  
  public static void main(String[] args) throws Exception {
    int threadNum = 30;
    end = new CountDownLatch(threadNum);
    int count = 20;
    AtomicInteger got = new AtomicInteger();
    AtomicInteger notGot = new AtomicInteger();
    for (int i =0; i < threadNum; i++) {
      new Thread(new Task(count, got, notGot)).start();
    }
    start.countDown();
    long startTime = System.currentTimeMillis();
    end.await();
    long endTime = System.currentTimeMillis();
    System.out.println("invoke:" + threadNum * count);
    System.out.println("got:" + got.get());
    System.out.println("notGot:" + notGot.get());
    System.out.println("cost:" + (endTime - startTime));
  }
  
  static class Task implements Runnable {
    int count;
    AtomicInteger got;
    AtomicInteger notGot;
    
    Task(int count, AtomicInteger got, AtomicInteger notGot) {
      this.count = count;
      this.got = got;
      this.notGot = notGot;
    }
    
    public void run() {
      try {start.await();} catch (Exception e){}
      while (count > 0) {
        Connection conn = null;
        try {
          conn = pool.getConnection(1000);
          if (pool != null) {
            try {
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