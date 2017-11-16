public class SelectSort {
  public static void sort(int[] arr) {
    if (arr == null || arr.length == 0)
      return;
    for (int i = 0; i < arr.length - 1; i++) {
      int k = i;
      for (int j = k + 1; j < arr.length; j++) {
        if (arr[j] < arr[k])
          k = j;
      }
      if (k != i) {
        int temp = arr[k];
        arr[k] = arr[i];
        arr[i] = temp;
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
    int[] arr = new int[]{1, 8, 0, 2, 9, 5};
    printArr(arr);
    sort(arr);
    printArr(arr);
  }
}