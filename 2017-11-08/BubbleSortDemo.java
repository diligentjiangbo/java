public class BubbleSortDemo {
  public static void sort(int[] arr) {
    if (arr == null || arr.length == 0)
      return;
    for (int i = 0; i < arr.length -1 ; i++) {
      for (int j = arr.length - 1; j > i; j--) {
        if (arr[j] < arr[j-1]) {
          int temp = arr[j];
          arr[j] = arr[j-1];
          arr[j-1] = temp;
        }
      }
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
    int[] arr = new int[]{5,7,2,9,1,8,2};
    printArr(arr);
    sort(arr);
    printArr(arr);
  }
}