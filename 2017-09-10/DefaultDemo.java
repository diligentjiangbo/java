public class DefaultDemo {
  public static void main(String[] args) {
    class GoneImpl implements Gone, Love {
      public void with() {
        System.out.println("I love you");
      }
      public void wind() {
        System.out.println("nice to meet you");
      }
    }
    Gone gone = new GoneImpl();
    gone.with();
    gone.wind();
  }
}

interface Gone {
  void with();
  default void wind() {
    System.out.println("Gone with the wind");
  }
}

interface Love {
  void wind();
}