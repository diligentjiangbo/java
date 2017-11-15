public class QuickSortDemo {
  public static void sort(int[] arr, int left, int right) {
    if (left > right) return;
    int temp = arr[left];
    int i = left;
    int j = right;
    while (i != j) {
      while (i < j && arr[j] >= temp)
        j--;
      while (i < j && arr[i] <= temp)
        i++;
      if (i < j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
      }
    }
    arr[left] = arr[i];
    arr[i] = temp;
    sort(arr, left, i - 1);
    sort(arr, i + 1, right);
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
    int[] arr = new int[]{1, 5, 7, 9, 2, 8};
    printArr(arr);
    sort(arr, 0, arr.length-1);
    printArr(arr);
  }
}