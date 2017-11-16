public class ShallowCloneDemo {
  class Person implements Cloneable {
    String name;
    Wallet wallet;
    public Person(String name, Wallet wallet) {
      this.name = name;
      this.wallet = wallet;
    }
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj instanceof Person) {
        if (name.equals(((Person)obj).name) && wallet.equals(((Person)obj).wallet)) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }
  
  class Wallet implements Cloneable {
    int money;
    public Wallet(int money) {
      this.money = money;
    }
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj instanceof Wallet) {
        if (money == ((Wallet)obj).money)
          return true;
        else 
          return false;
      } else {
        return false;
      }
    }
  }
  
  public static void main(String[] args) throws Exception {
    ShallowCloneDemo ShallowCloneDemo = new ShallowCloneDemo();
    Wallet wallet = ShallowCloneDemo.new Wallet(10);
    Person person = ShallowCloneDemo.new Person("hello", wallet);
    Person personClone = (Person)person.clone();
    System.out.println(person == personClone);//expect false
    System.out.println(person.equals(personClone));//expect true
    System.out.println(person.wallet == personClone.wallet);//expect false
    System.out.println(person.wallet.equals(personClone.wallet));//expect true
  }

}
