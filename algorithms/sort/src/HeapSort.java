/*
in place and guarantees worst case O(n*log(n)), but not stable
 */
public class HeapSort {
    private static void sort(Comparable [] arr) {
        int n = arr.length;

        // construct a heap in place, O(n)
        for (int p = n/2 - 1; p >= 0; --p) {
            sink(arr, p, n);
        }

        // heap sort, O(n*log(n))
        for (int i = n - 1; i >= 0; --i) {
            exch(arr, 0, i);
            sink(arr, 0, i);
        }
    }

    private static void sink(Comparable [] arr, int p, int sz) {
        int n = sz;

        while (2*p + 1 < n) {
            p = 2*p + 1;
            if (p + 1 < n && less(arr[p], arr[p + 1])) {
                ++p;
            }
            if (less(arr[(p - 1)/2], arr[p])) {
                exch(arr, (p - 1)/2, p);
            }
        }
    }

    private static boolean less(Comparable u, Comparable v) {
        return u.compareTo(v) < 0;
    }

    private static void exch(Comparable [] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String [] args) {
        Integer [] arr = {9, 999, 5, 3, 8, -546, 2, 1, 5, 2, 9, 34, 23, 86, -2, 323, 3, 8};
        //Integer [] arr = {999, 5, 3, 8, -546, 2};
        HeapSort.sort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
