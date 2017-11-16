import java.util.*;
import java.util.concurrent.*;
import java.lang.reflect.*;
import java.sql.Connection;

public class ConnectionPool {
  private LinkedList<Connection> pool = new LinkedList<Connection>();
  
  public ConnectionPool(int num) {
    if (num > 0) {
      for (int i = 0; i < num; i++) {
        pool.addLast(createConnection());
      }
    }
  }
  
  public Connection getConnection(long mill) throws InterruptedException {
    if (mill < 0) {
      synchronized (pool) {
        while (pool.isEmpty()) {
          pool.wait();
        }
        return pool.removeFirst();
      }
    } else {
      synchronized (pool) {
        long remain = mill;
        while (pool.isEmpty() && remain > 0) {
          long startTime = System.currentTimeMillis();
          pool.wait(remain);
          long endTime = System.currentTimeMillis();
          remain = remain - (endTime - startTime);
        }
        Connection conn = null;
        if (!pool.isEmpty()) {
          conn = pool.removeFirst();
        }
        return conn;
      }
    }
  }
  
  public void releaseConnection(Connection conn) {
    if (conn != null) {
      synchronized (pool) {
        pool.addLast(conn);
        pool.notifyAll();
      }
    }
  }
  
  public Connection createConnection() {
     class ConnectionHandler implements InvocationHandler {
       public Object invoke(Object proxy, Method method, Object[] args) {
         if (method.getName().equals("commit")) {
           try {
            TimeUnit.MILLISECONDS.sleep(100);
           } catch (Exception e) {}
         }
         return null;
       }
     }
    Connection conn = (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class<?>[]{Connection.class}, new ConnectionHandler());
    return conn;
  }
  
  
}