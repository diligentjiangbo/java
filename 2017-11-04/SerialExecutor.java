import java.util.*;
import java.util.concurrent.*;

public class SerialExecutor implements Executor {
  
  private Queue<Runnable> taskQueue = new ArrayDeque<Runnable>();
  private Executor executor;
  private Runnable active;
  
  public SerialExecutor(Executor executor) {
    this.executor = executor;
  }
  
  public void execute(Runnable r) {
    taskQueue.offer(new Runnable(){
      public void run() {
        try {r.run();} finally {next();}
      }
    });
    if (active == null) next();
  }
  
  public void next() {
    if ((active = taskQueue.poll()) != null)
      executor.execute(active);
  }
  
  public static void main(String[] args) {
    Executor executor = new SerialExecutor(Executors.newCachedThreadPool());
    class MyTask implements Runnable {
      private int i;
      public MyTask(int i ) {this.i = i;}
      public void run() {
        try {
          TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {}
        System.out.println("-=-=-=-=-==" + i);
      }
    }
    for (int i = 0; i < 10; i++) {
      executor.execute(new MyTask(i));
    }
    
  }
  
  
}