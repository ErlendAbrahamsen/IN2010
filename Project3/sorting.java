import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

class sorting {

  static int[] selectionSort(int[] arr) {
    /*
    Selection-sort / utplukkingssortering.
    Velger første element,
    looper gjennom etterfølgende elementer helt til et mindre er funnet.
    Bytter om plassen på de. Gjenta prosess.
    */

    int n = arr.length;

    for (int i = 0; i < n-1; i++) {
      for (int j = i+1; j < n; j++) {
        if (arr[j] < arr[i]) {
          int temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;

        }
      }
    }

    return arr;
  }

  static void quickSort(int[] arr, int l, int r) {
    /*
    Kvikksortering.
    Velger siste element i arr som pivot.
    Leter fra venstre mot høyre til et element er større enn pivoten.
    Leter fra høyre mot venstre til et element er mindre enn pivoten.
    Bytter plass på disse elementene.
    Reapeat gjennom rekursjon.
    */

    if (l >= r) {
      return;
    }

    int pivot = arr[r];         //Velger siste element i partisjon som pivot

    int i = l; int j = r;
    while (i < j) {
      while (i < j && arr[i] <= pivot) {
        i++;
      }
      while(j > i && arr[j] >= pivot ) {
        j--;
      }

      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }

    //Siste byttet element fra venstre som ny pivot
    int temp = arr[i];
    arr[i] = pivot;
    arr[r] = temp;

    //Rekursjon
    quickSort(arr, l, i-1);
    quickSort(arr, i+1, r);
  }

  static void mergeSort(int[] arr, int l, int r) {
    /*
    merge/flette sorting. Bruker sub-metoden mergeTogether.
    */

    if (l >= r) { //Ferdig
      return;
    }

    int m = (l+r)/2;

    mergeSort(arr, l, m);
    mergeSort(arr, m+1, r);
    mergeTogether(arr, l, m, r);
  }

  static void mergeTogether(int[] arr, int l, int m, int r) {
    /*
    Sub-metode i merge. Fletter sammen sortert S1, S2 inn i arr.
    */

    int n1 = m - l + 1; int n2 = r - m;
    int[] S1 = new int[n1];
    int[] S2 = new int[n2];

    //Kopiere til to halvdeler S1, S2.
    for (int i = 0; i < n1; i++) { S1[i] = arr[l+i]; }
    for (int i = 0; i < n2; i++) { S2[i] = arr[m+i+1]; }

    int i = 0; int j = 0; int k = l;
    while (i < n1 && j < n2) {
      if (S1[i] <= S2[j]) {
        arr[k] = S1[i];
        i++;
      }

      else {
        arr[k] = S2[j];
        j++;
      }
      k++;
    }

    //Resten av enten S1 eller S2 dersom n1 != n2.
    while (i < n1) {
      arr[k] = S1[i];
      i++;
      k++;
    }

    while (j < n2) {
      arr[k] = S2[j];
      j++;
      k++;
    }
  }

    static List<long[]> timeTesting(String method, String input, int startSize, int stopSize) {
      /*
      Rerturnerer array av tider for sorterings algoritmene.
      method: Velg mellom "selectionSort", "quickSort" eller "mergeSort".
      input: Velg mellom "random", "unique", "sorted", "decreasing".
      startSize, stopSize: start array lengde og slutt array lengde

      random er random med flere like elementer.
      sorted, decreasing er også random verdier med flere like elementer, bare sortert.
      unique er random uten flere like elementer.
      */

      //Legge test arrays i liste tests.
      List<int[]> tests = new ArrayList<>();
      List<Long> sizes = new ArrayList<>();  //Liste over input sizes til senere
      for (int size = startSize; size <= stopSize; size *= 5) {
        int[] arr = new int[size];
        sizes.add((long) size);

        for (int i = 0; i < size; i++) {
          if (input == "random" || input == "sorted" || input == "decreasing") {
            arr[i] = new Random().nextInt(size/2); //Random repeat
           }

          else {                              //Unique.
            arr[i] = new Random().nextInt();  //Verdi området er så stort at
            }                                 //sjansen for like verdier -> 0.
          }

       if (input == "sorted") {  //Sorterer random arr fra første if.
         Arrays.sort(arr);
       }

       else if (input == "decreasing") {      //Decreasing
         Arrays.sort(arr);
         int n = arr.length - 1;
         for (int j = 0; j <= n/2; j++) {     //Reverse sorted arr.
           int temp = arr[j];
           arr[j] = arr[n-j];
           arr[n-j] = temp;
         }
       }

      tests.add(arr);
    }

    //Legge tid i times og javaTimes.
    long[] times = new long[tests.size()];
    long[] javaTimes = new long[tests.size()];

    for (int i = 0; i < tests.size(); i++) {

      if (method == "selectionSort") {
        int[] arr_i = tests.get(i);

        long t0 = System.currentTimeMillis();
        selectionSort(arr_i);
        long t1 = System.currentTimeMillis();
        long t = t1 - t0;
        times[i] = t;
      }

      if (method == "quickSort") {
        int[] arr_i = tests.get(i);
        int n = arr_i.length - 1;

        long t0 = System.currentTimeMillis();
        quickSort(arr_i, 0, n);
        long t1 = System.currentTimeMillis();
        long t = t1 - t0;
        times[i] = t;
      }

      if (method == "mergeSort") {
        int[] arr_i = tests.get(i);
        int n = arr_i.length - 1;

        long t0 = System.currentTimeMillis();
        mergeSort(arr_i, 0, n);
        long t1 = System.currentTimeMillis();
        long t = t1 - t0;
        times[i] = t;
      }

      //Sortering med java util.
      int[] arr_i = tests.get(i);

      long t0_ = System.currentTimeMillis();
      Arrays.sort(arr_i);
      long t1_ = System.currentTimeMillis();
      javaTimes[i] = t1_ - t0_;
    }

    //Returnere times, javaTimes, sizes.
    List<long[]> returnTimes = new ArrayList<>();
    returnTimes.add(times);
    returnTimes.add(javaTimes);
    returnTimes.add(sizes.stream().mapToLong(l -> l).toArray());

    return returnTimes;
  }


  static void printTable(List<long[]> returnTimes) {
    /*
    Printer tabell av test tider.
    */

    long[] times = returnTimes.get(0);
    long[] javaTimes = returnTimes.get(1);
    long[] sizes = returnTimes.get(2);

    System.out.println("size   time (ms)   java time (ms)");
    for (int i = 0; i < times.length; i++) {
      System.out.printf("%6d %6d %6d \n", sizes[i], times[i], javaTimes[i]);
    }
  }


  static List<int[]> testArrs() {
    /*
    Returnerer array med random numbers 0-9 med size 10,
    array med verdier 0,..,9 ,
    array med verdier 9,...0 .
    */

    List<int[]> arrs = new ArrayList<>();

    //Testing arrays
    int n = 10;                   //Size
    int[] random = new int[n];    //0-9 random
    int[] zeroNine = new int[n];  //0-9
    int[] nineZero = new int[n];  //9-0
    for (int i = 0; i < n; i++) {
      random[i] = new Random().nextInt(n);
      zeroNine[i] = i;
      nineZero[i] = (n - 1) - i;
    }

    arrs.add(random); arrs.add(zeroNine); arrs.add(nineZero);

    return arrs;
  }



  static void printArrs(int[] a1, int[] a2, int[] a3) {
    /*
    Printer arrays.
    */

    System.out.println(Arrays.toString(a1));
    System.out.println(Arrays.toString(a2));
    System.out.println(Arrays.toString(a3));
    System.out.println();

  }

  public static void main(String[] args) {
    //* Time tables *//

    String[] methods = {"quickSort", "mergeSort", "selectionSort"};
    int startSize = 5000; int stopSize = 125000;

    for (String method: methods) {
      System.out.printf("%s() random repeat input time table: \n", method);
      List<long[]> returnTimes1 = timeTesting(method, "random", startSize, stopSize);
      printTable(returnTimes1); System.out.printf("\n");

      System.out.printf("%s() random unique input time table: \n", method);
      List<long[]> returnTimes11 = timeTesting(method, "unique", startSize, stopSize);
      printTable(returnTimes11); System.out.printf("\n");
    }
  }
}
