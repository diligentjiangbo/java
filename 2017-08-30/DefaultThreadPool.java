import java.util.*;
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
  //默认工作线程个数
  private static final int DEFAULT_WORKER_NUM = 10;
  //最大工作线程个数
  private static final int MAX_WORKER_NUM = 20;
  //最小工作线程个数
  private static final int MIN_WORKER_NUM = 5;
  //存储工作任务的列表
  private LinkedList<Job> jobList = new LinkedList<Job>();
  //存储工作线程的列表
  private List<Worker> workerList = Collections.synchronizedList(new ArrayList<Worker>());
  //线程个数
  private int workerNum = DEFAULT_WORKER_NUM;
  //线程sequence
  private static int sequence = 0;
  
  public DefaultThreadPool(int num) {
    workerNum = num > MAX_WORKER_NUM ? MAX_WORKER_NUM : num < MIN_WORKER_NUM ? MIN_WORKER_NUM : num;
    init(workerNum);
  }
  
  //初始化工作线程
  public void init(int threadNum) {
    for (int i = 0; i < threadNum; i++) {
      Worker worker = new Worker();
      new Thread(worker, "Worker---" + sequence++).start();
      workerList.add(worker);
    }
  }
  
  //实现执行任务方法
  public void execute(Job job) {
    synchronized (jobList) {
      jobList.add(job);
      jobList.notify();
    }
  }
  
  //实现关闭线程池方法
  public void shutdown() {
    for (Worker worker : workerList) {
      worker.shutdown();
    }
  }
  
  class Worker implements Runnable {
    private volatile boolean stop = false;
    public void run() {
      while (!stop) {
        Job job = null;
        synchronized (jobList) {
          
          while (jobList.isEmpty()) {
            try {
              jobList.wait();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              return;
            }
            
          }
          job = jobList.removeFirst();
        }
        
        if (job != null) {
          job.run();
        }
      }
    }
    
    public void shutdown() {
      stop = true;
    }
    
  }
  
}