public class Demo {
  public static void main(String[] args) {
    class Task implements Runnable {
      public void run() {
        for (int i = 0; i < 1000; i++) {
          System.out.println(Thread.currentThread().getName() + "---" + i);
        }
      }
    };
    
    DefaultThreadPool<Task> pool = new DefaultThreadPool<Task>(9);
    pool.execute(new Task());
    pool.execute(new Task());
    
  }
}