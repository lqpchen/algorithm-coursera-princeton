/*
Java applies quick sort for primitive types
 */

import java.util.Random;

public class QuickSort {

    private final static Random randInt = new Random();

    // for holding the resulting dual index from 3-way partitioning
    private static int lt;
    private static int gt;


    // Java sort for primitive types
    public static void sort(Comparable [] arr) {
        shuffle(arr, 0, arr.length - 1);
        quickSort3w(arr, 0, arr.length - 1);
        ElementarySort.insertionSort(arr); //
    }

    private static boolean less(Comparable u, Comparable v) {
        return u.compareTo(v) < 0;
    }

    private static void exch(Comparable [] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void quickSort(Comparable [] arr, int lo, int hi) {

        // base case
        if (hi <= lo) return;

        // base case, small sub arrays
        if (hi - lo <= 9) {
            // ElementarySort.insertionSort(arr, lo, hi); // can be delayed
            return; // delay insertion sort until the last one-pass insertion sort
        }

        // partition
        int m = partition(arr, lo, hi);

        // recursion
        quickSort(arr, lo, m - 1);
        quickSort(arr, m + 1, hi);
    }

    private static int partition(Comparable [] arr, int lo, int hi) {
        int piv = lo; // can take median of three samples as the pivot element
        int i = lo, j = hi + 1;
        while (true) {
            while (less(arr[++i], arr[piv])) {
                if (i >= hi) {
                    break;
                }
            }
            while (less(arr[piv], arr[--j])) {
                if (j < i) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(arr, i, j);
        }
        exch(arr, piv, j);
        return j;
    }

    // applies three way partitioning
    // reduces to near O(n) for a broad class of applications in practice
    private static void quickSort3w(Comparable [] arr, int lo, int hi) {
        // base case
        if (hi <= lo) return;

        // base case, small sub arrays
        if (hi - lo <= 9) {
            // ElementarySort.insertionSort(arr, lo, hi); // can be delayed
            return; // delay insertion sort until the last one-pass insertion sort
        }

        // three-way partitioning
        int lt = lo, gt = hi, i = lo + 1;
        while (i <= gt) {
            if (less(arr[i], arr[lt])) {
                exch(arr, i++, lt++);
            }
            else if (less(arr[lt], arr[i])) {
                exch(arr, i, gt--);
            }
            else {
                ++i;
            }
        }

        // recursion
        quickSort3w(arr, lo, lt - 1);
        quickSort3w(arr, gt + 1, hi);
    }

    // return the k-th smallest item, k = 0, 1, 2, ...
    private static Comparable select(Comparable [] arr, int k) {
        int m = k, lo = 0, hi = arr.length - 1;
        while (lo < hi) {
            m = partition(arr, lo, hi);
            if (m < k) {
                lo = m + 1;
            } else if (m > k) {
                hi = m - 1;
            } else {
                return arr[m];
            }
        }
        return arr[m];
    }

    private static void shuffle(Comparable [] arr, int lo, int hi) {
        int n = hi - lo + 1;
        int randIdx;
        for (int i = 0; i < n - 1; ++i) {
            randIdx = randInt.nextInt(n - i);
            exch(arr, lo + randIdx, hi - i);
        }
    }

    public static void main(String [] args) {
        Integer [] arr = {9, 999, 5, 3, 8, -546, 2, 1, 5, 2, 9, 34, 23, 86, -2, 323, 3, 8};
        //Integer [] arr = {999, 5, 3, 8, -546, 2};
        QuickSort.sort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(QuickSort.select(arr, 5));
    }
}
