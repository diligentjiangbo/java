import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;

public class ConnectionPool {
  private LinkedList<Connection> poolList = new LinkedList<Connection>();
  
  public ConnectionPool(int size) {
    if (size > 0) {
      for (int i = 0; i < size; i++) {
        poolList.addLast(createConnection());
      }
    }
  }
  
  public Connection getConnection(long mill) throws InterruptedException {
    synchronized (poolList) {
      if (mill <= 0) {
        while (poolList.isEmpty()) {
          poolList.wait();
        }
        return poolList.removeFirst();
      } else {
        long future = System.currentTimeMillis() + mill;
        long remaing = mill;
        while (poolList.isEmpty() && remaing > 0) {
          poolList.wait();
          remaing = System.currentTimeMillis() - future;
        }
        Connection conn = null;
        if (!poolList.isEmpty()) {
          conn = poolList.removeFirst();
        }
        return conn;
      }
    }
  }
  
  public void releaseConnection(Connection conn) {
    if (conn != null) {
      synchronized (poolList) {
        poolList.addLast(conn);
        poolList.notifyAll();
      }
    }
  }
  
  private Connection createConnection() {
    class ConnHandler implements InvocationHandler {
      public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.getName().equals("commit")) {
          try {TimeUnit.MILLISECONDS.sleep(100);} catch (Exception e){}
        }
        return proxy;
      }
    }
    
    Connection conn = (Connection)Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(), new Class<?>[]{Connection.class}, new ConnHandler());
    return conn;
  }
  
}