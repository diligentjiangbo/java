import java.util.List;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;

public class DynamicProxyDemo {
  
  //真实继承接口的类
  class Coder implements Worker {
    public void doWork() {
      System.out.println("coding......");
    }
    public void play() {
      System.out.println("playing......");
    }
  }
  
  //代理类
  class WorkerProxy implements InvocationHandler {
    private Worker worker;
    public WorkerProxy(Worker worker) {
      this.worker = worker;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      //过滤，只执行名字叫doWork的方法
      if (!method.getName().equals("doWork")) {
        return null;
      }
      System.out.println("I'm proxy");
      return method.invoke(worker, args);
    }
  }
  
  
  public static void main(String[] args) {
    DynamicProxyDemo proxyDemo = new DynamicProxyDemo();
    Worker coder = proxyDemo.new Coder();
    InvocationHandler invocationHandler = proxyDemo.new WorkerProxy(coder);
    Worker workerProxy = (Worker)Proxy.newProxyInstance(DynamicProxyDemo.class.getClassLoader(), 
                                                  new Class<?>[]{Worker.class}, 
                                                  invocationHandler);
    workerProxy.doWork();
    workerProxy.play();
  }
  
}

interface Worker {
  void doWork();
  void play();
}

