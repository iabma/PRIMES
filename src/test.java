// Radix sort Java implementation

import java.util.Arrays;

class test {
    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    static void countSort(int arr[], int n, int exp) {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[1000];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 1000]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 1000; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 1000] - 1] = arr[i];
            count[(arr[i] / exp) % 1000]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to curent digit
        System.out.println();
        for (i = 0; i < n; i++) {
            arr[i] = output[i];
            System.out.print(output[i] + " ");
        }
    }

    // The main function to that sorts arr[] of size n using
    // Radix Sort
    static void radixsort(int arr[], int n) {// Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; 1000000000 / exp > 0; exp *= 1000) {
            countSort(arr, n, exp);
            System.out.println(exp + " " + 1000000000 / exp);
        }
    }

    // A utility function to print an array
    static void print(int arr[], int n) {
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }


    /*Driver function to check for above function*/
    public static void main(String[] args) {
        int arr[] = {345678916, 342368905, 467231450, 398765468, 602345671};
        int n = arr.length;
        radixsort(arr, n);
        print(arr, n);
    }
}
/* This code is contributed by Devesh Agrawal */
