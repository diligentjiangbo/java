import java.lang.reflect.*;

interface Milk {
  void suck();
}

class GrainMilk implements Milk {
  public void suck() {
    System.out.println("suck milk");
  }
}

class MyHandler implements InvocationHandler {
  private Object target;
  MyHandler(Object target) {
    this.target = target;
  }
  public Object invoke(Object obj, Method method, Object[] args) throws Exception {
    System.out.println("before");
    Object result = method.invoke(target, args);
    System.out.println("after");
    return result;
  }
}

public class ProxyDemo {
  public static void main(String[] args) throws Exception {
    Milk milk = new GrainMilk();
    MyHandler handler = new MyHandler(milk);
    Milk proxyMilk = (Milk)Proxy.newProxyInstance(Milk.class.getClassLoader(), new Class<?>[]{Milk.class}, handler);
    proxyMilk.suck();
  }
}