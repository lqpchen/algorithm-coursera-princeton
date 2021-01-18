/*
Java applies merge sort for objects
 */

import java.util.Comparator;

public class MergeSort {

    private static Comparable [] aux;

    private static boolean less(Comparable u, Comparable v) {
        return u.compareTo(v) < 0;
    }

    private static boolean less(Comparator c, Comparable u, Comparable v) {
        return c.compare(u, v) < 0;
    }

    private static boolean le(Comparable u, Comparable v) {
        return u.compareTo(v) <= 0;
    }

    private static boolean le(Comparator c, Comparable u, Comparable v) {
        return c.compare(u, v) <= 0;
    }

    private static void exch(Comparable [] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean isSorted(Comparable [] arr, int start, int end) {
        for (int i = start; i < end; ++i) {
            if (less(arr[i + 1], arr[i])) {
                return false;
            }
        }
        return true;
    }

    public static void sort(Comparable [] arr) {
        aux = new Comparable[arr.length]; // auxiliary array for copy and merge
        int lo = 0, hi = arr.length - 1;
        subSort(arr, aux, lo, hi);
    }

    // allows for comparator
    public static void sort(Comparable [] arr, Comparator c) {
        aux = new Comparable[arr.length]; // auxiliary array for copy and merge
        int lo = 0, hi = arr.length - 1;
        subSort(arr, aux, lo, hi, c);
    }

    private static void subSort(Comparable [] arr, Comparable [] aux, int lo, int hi) {
        /*if (lo >= hi) {
            return;
        }*/
        if (lo + 7 > hi) { // can apply insertion sort for small sub-array
            ElementarySort.insertionSort(arr, lo, hi);
            return;
        }

        int mid = (lo + hi)/2;
        subSort(arr, aux, lo, mid);
        subSort(arr, aux, mid + 1, hi);
        if (less(arr[mid], arr[mid + 1])) {
            return;
        }
        merge(arr, aux, lo, mid, hi);
    }

    // allows for comparator
    private static void subSort(Comparable [] arr, Comparable [] aux, int lo, int hi, Comparator c) {
        /*if (lo >= hi) {
            return;
        }*/
        if (lo + 7 > hi) { // can apply insertion sort for small sub-array
            ElementarySort.insertionSort(arr, lo, hi, c);
            return;
        }

        int mid = (lo + hi)/2;
        subSort(arr, aux, lo, mid, c);
        subSort(arr, aux, mid + 1, hi, c);
        if (less(c, arr[mid], arr[mid + 1])) {
            return;
        }
        merge(arr, aux, lo, mid, hi, c);
    }

    // merge sort bottom up version, without recursion
    public static void sortBU(Comparable [] arr) {
        aux = new Comparable[arr.length]; // auxiliary array for copy and merge
        int n = arr.length;
        for (int sz = 2; sz < 2*n; sz*=2) {
            for (int lo = 0; lo < n; lo += sz) {
                merge(arr, aux, lo, (lo*2 + sz - 1)/2, Math.min(n - 1, lo + sz -1));
            }
        }
    }

    private static void merge(Comparable [] arr, Comparable [] aux, int lo, int mid, int hi) {
        assert isSorted(arr, lo, mid);
        assert isSorted(arr, mid + 1, hi);

        // copy the original array to auxiliary array
        for (int i = lo; i < hi + 1; ++i) {
            aux[i] = arr[i];
        }

        // copy the elem back in sorted order
        int fi = lo, se = mid + 1;
        for (int i = lo; i < hi + 1; ++i) {
            if (fi > mid) {
                arr[i] = aux[se++];
            } else if (se > hi) {
                arr[i] = aux[fi++];
            } else if (le(aux[fi], aux[se])) { // make the sort stable
                arr[i] = aux[fi++];
            } else if (less(aux[se], aux[fi])) {
                arr[i] = aux[se++];
            }
        }

        assert isSorted(arr, lo, hi);
    }

    // allows for comparator
    private static void merge(Comparable [] arr, Comparable [] aux, int lo, int mid, int hi, Comparator c) {
        //assert isSorted(arr, lo, mid);
        //assert isSorted(arr, mid + 1, hi);

        // copy the original array to auxiliary array
        for (int i = lo; i < hi + 1; ++i) {
            aux[i] = arr[i];
        }

        // copy the elem back in sorted order
        int fi = lo, se = mid + 1;
        for (int i = lo; i < hi + 1; ++i) {
            if (fi > mid) {
                arr[i] = aux[se++];
            } else if (se > hi) {
                arr[i] = aux[fi++];
            } else if (le(c, aux[fi], aux[se])) { // make the sort stable
                arr[i] = aux[fi++];
            } else if (less(c, aux[se], aux[fi])) {
                arr[i] = aux[se++];
            }
        }

        //assert isSorted(arr, lo, hi);
    }

    public static void main(String [] args) {
        Integer [] arr = {999, 5, 3, 8, -546, 2, 1, 5, 2, 9, 34, 23, 86, -2, 323, 3, 8};
        //Integer [] arr = {999, 5, 3, 8, -546};
        MergeSort.sort(arr);
        //MergeSort.sortBU(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
