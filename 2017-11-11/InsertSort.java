public class InsertSort {
  public static void sort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      int temp = arr[i];
      int j = i;
      for (; j > 0 && temp < arr[j-1]; j--) {
          arr[j] = arr[j-1];
      }
      arr[j] = temp;
      
    }
  }
  
  public static void printArr(int[] arr) {
    StringBuilder sb = new StringBuilder("[");
    for (int i : arr) {
      sb.append(i).append(",");
    }
    String s = sb.substring(0, sb.length()-1);
    System.out.println(s + "]");
  }
  
  public static void main(String[] args) {
    int[] arr = {1, 2, 5, 8, 9, 0, 2, 3};
    printArr(arr);
    sort(arr);
    printArr(arr);
  }
}