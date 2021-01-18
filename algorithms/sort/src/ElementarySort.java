import java.util.Comparator;

public class ElementarySort {

    // have to compare n*(n - 1)/2 times exactly
    public static void selectionSort(Comparable [] arr) {
        int minIdx;
        int n = arr.length;
        for (int i = 0; i < n - 1; ++i) {
            minIdx = i;
            for (int j = i + 1; j < n; ++j) {
                if (less(arr[j], arr[minIdx])) {
                    minIdx = j;
                }
            }
            exch(arr, i, minIdx);
        }
    }

    // support comparator (customized sorting order)
    public static void selectionSort(Comparable [] arr, Comparator c) {
        int minIdx;
        int n = arr.length;
        for (int i = 0; i < n - 1; ++i) {
            minIdx = i;
            for (int j = i + 1; j < n; ++j) {
                if (less(c, arr[j], arr[minIdx])) {
                    minIdx = j;
                }
            }
            exch(arr, i, minIdx);
        }
    }

    // faster than selection sort especially when partially sorted
    public static void insertionSort(Comparable [] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            for (int j = i; j > 0; --j) {
                if (less(arr[j], arr[j - 1])) {
                    exch(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    // used by mergesort and quicksort for small sub-arrays
    public static void insertionSort(Comparable [] arr, int lo, int hi) {
        int n = hi - lo + 1;
        for (int i = lo + 1; i < hi; ++i) {
            for (int j = i; j > lo; --j) {
                if (less(arr[j], arr[j - 1])) {
                    exch(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    // support comparator (customized sorting order)
    public static void insertionSort(Comparable [] arr, Comparator c) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            for (int j = i; j > 0; --j) {
                if (less(c, arr[j], arr[j - 1])) {
                    exch(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    // used by mergesort and quicksort for small sub-arrays
    public static void insertionSort(Comparable [] arr, int lo, int hi, Comparator c) {
        int n = hi - lo + 1;
        for (int i = lo + 1; i < hi; ++i) {
            for (int j = i; j > lo; --j) {
                if (less(c, arr[j], arr[j - 1])) {
                    exch(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    // between [n*log(n), n^1.5] in practice, no math prove for this
    public static void shellSort(Comparable [] arr) {
        int h = 1;
        int n = arr.length;
        while (h < n/3) {
            h = 3*h + 1;
        }
        while (h > 0) {
            // do h-sort
            for (int i = h; i < n; ++i) {
                for (int j = i; j >= h; j = j - h) {
                    if (less(arr[j], arr[j - h])) {
                        exch(arr, j, j - h);
                    } else {
                        break;
                    }
                }
            }
            h /= 3;
        }
    }

    private static boolean less(Comparable u, Comparable v) {
        return u.compareTo(v) < 0;
    }

    private static boolean less(Comparator c, Comparable u, Comparable v) {
        return c.compare(u, v) < 0;
    }

    private static void exch(Comparable [] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String [] args) {
        Integer [] arr = {5, 3, 8, -546, 2, 1, 5, 2, 9, 34, 23, 86, -2, 323, 3, 8};
        //ElementarySort.selectionSort(arr);
        ElementarySort.insertionSort(arr, 0, arr.length - 1);
        //ElementarySort.shellSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();

        String [] names = {"Peter", "Cassie", "Bob"};
        double [] gpas = {8.8, 7.0, 5.0};
        double [] heights = {1.83, 1.67, 1.72};
        Student [] students = new Student[3];
        for (int i = 0; i < names.length; ++i) {
            students[i] = new Student();
            students[i].name = names[i];
            students[i].gpa = gpas[i];
            students[i].height = heights[i];
            students[i].print();
        }
        ElementarySort.insertionSort(students, Student.BY_HEIGHT);
        System.out.println("\nAfter sorting:\n");
        for (int i = 0; i < names.length; ++i) {
            students[i].print();
        }

    }
}
