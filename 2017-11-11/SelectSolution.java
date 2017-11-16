public class SelectSolution {
  //从数组中选出最大的k个数
  public static int[] select(int[] arr, int k) {
    if (k > arr.length)
      return arr;
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
      result[i] = arr[i];
    }
    quickSort(result, 0, k-1);
    printArr(result);
    for (int i = k; i < arr.length; i++) {
      if (arr[i] <= result[0])
        continue;
      int index = -1;
      int j = 1;
      for (; j < k; j++) {
        if (arr[i] <= result[j])
          break;
        index = j;
        result[j-1] = result[j];
      }
      if (index != -1)
        result[index] = arr[i];
    }
    return result;
  }
  
  //快速排序
  public static void quickSort(int[] arr, int left, int right) {
    if (left > right) {
      return;
    }
    int temp = arr[left];
    int i = left;
    int j = right;
    
    while (i!= j) {
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
    quickSort(arr, left, i - 1);
    quickSort(arr, i + 1, right);
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
    int[] arr = {1, 2, 5, 4, 8, 9, 1, 8, 0, 123, 88};
    printArr(arr);
    //quickSort(arr, 0, arr.length-1);
    int[] result = select(arr, 6);
    printArr(result);
    
  }
}