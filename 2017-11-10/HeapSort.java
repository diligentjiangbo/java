public class HeapSort {
  public static void sort(int[] arr) {
    for (int i = arr.length/2; i >= 0; i--) {
      heapInit(arr, i, arr.length);
    }
    printArr(arr);
    
    for (int i = arr.length - 1; i > 0; i--) {
      int temp = arr[i];
      arr[i] = arr[0];
      arr[0] = temp;
      
      heapInit(arr, 0, i);
    }
  }
  
  public static void heapInit(int[] arr, int parent, int length) {
    int child = parent * 2 + 1;
    while (child < length) {
      if ((child + 1) < length && arr[child] < arr[child+1])
        child = child + 1;
      
      if (arr[parent] > arr[child])
        break;
      
      int temp = arr[parent];
      arr[parent] = arr[child];
      arr[child] = temp;
      
      parent = child;
      child = child * 2 + 1;
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
    int[] arr = new int[]{1, 5, 2, 6, 8, 9};
    printArr(arr);
    sort(arr);
    printArr(arr);
  }
}