public class SofDemo {
  int length = 0;
  void method() {
    length = length + 1;
    method();
  }
  public static void main(String[] args) {
    SofDemo sofDemo = new SofDemo();
    try {
      sofDemo.method();
    } catch (Throwable e) {
      System.out.println("stack length = " + sofDemo.length);
      System.out.println(e);
    }
  }
}