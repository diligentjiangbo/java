import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SerializableDemo {
  public static void main(String[] args) throws Exception {
    //write();
    read();
  }
  public static void write() throws Exception {
    FileOutputStream fos = new FileOutputStream("c:\\serial.txt");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(new Person("hello", 1));
    oos.close();
    fos.close();
  }
  public static void read() throws Exception {
    FileInputStream fis = new FileInputStream("c:\\serial.txt");
    ObjectInputStream ois = new ObjectInputStream(fis);
    Person person = (Person) ois.readObject();
    System.out.println(person);
    ois.close();
    fis.close();
  }
}

class Person implements Serializable {
  private static final long serialVersionUID = 12345L;
  String name;
  int age;
  
  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }
  
  public String toString() {
    return "{name:" + this.name + ", age:" + this.age + "}";
  }
}