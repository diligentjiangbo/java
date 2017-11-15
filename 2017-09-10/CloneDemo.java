import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class CloneDemo implements Serializable {
  class Person implements Cloneable, Serializable {
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
  
  class Wallet implements Cloneable,Serializable {
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
  
  public static <T> T DeepClone(T t) throws Exception {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(t);
    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bis);
    T retT = (T)ois.readObject();
    ois.close();
    bis.close();
    oos.close();
    bos.close();
    return retT;
  }
  
  public static void main(String[] args) throws Exception {
    CloneDemo CloneDemo = new CloneDemo();
    Wallet wallet = CloneDemo.new Wallet(10);
    Person person = CloneDemo.new Person("hello", wallet);
    
    //浅克隆，对象引用没有进行克隆
    Person personClone = (Person)person.clone();
    System.out.println(person == personClone);//false
    System.out.println(person.equals(personClone));//true
    System.out.println(person.wallet == personClone.wallet);//true
    System.out.println(person.wallet.equals(personClone.wallet));//true
    
    //深克隆，对象引用进行了克隆
    Person deepPersonClone = (Person)DeepClone(person);
    System.out.println(person == deepPersonClone);//false
    System.out.println(person.equals(deepPersonClone));//true
    System.out.println(person.wallet == deepPersonClone.wallet);//false
    System.out.println(person.wallet.equals(deepPersonClone.wallet));//true
  }

}
