import java.util.Arrays;
import java.util.Random;

public class Main {

    /**
     * Programm start.
     * creates random int array and does heapsort.
     *
     * @param args shell arguments
     */
    public static void main(String[] args) {
        int[] ints = createRandomArr(10);
        heapsort(ints);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * sorts given array of integers (heapsort).
     *
     * @param arr Given array of integers
     */
    public static void heapsort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            siftDown(arr, arr.length, i);
        }
        for (int r = arr.length - 1; r > 0; r--) {
            swap(arr, 0, r);
            siftDown(arr, r, 0);
        }
    }

    /**
     * Swaps two elements in array.
     *
     * @param arr Given array
     * @param i   index of first element
     * @param j   index of second element
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Does "sift down" in order to restore properties of max heap.
     *
     * @param arr Given array of int
     * @param len Size of subarray, that we perceive as a max heap
     * @param i   index of element, that should be "sifted down"
     */
    public static void siftDown(int[] arr, int len, int i) {
        while (2 * i + 1 < len) {
            int indexOfMax = 2 * i + 1;

            if (2 * i + 2 < len && arr[2 * i + 2] > arr[2 * i + 1]) {
                indexOfMax = 2 * i + 2;
            }
            if (arr[i] >= arr[indexOfMax]) {
                break;
            }
            swap(arr, i, indexOfMax);
            i = indexOfMax;
        }
    }

    /**
     * Generates an array of given length. Elements are random integers from [-100;100]
     *
     * @param len length of the creating array
     * @return array of random integers
     */
    public static int[] createRandomArr(int len) {
        int[] arr = new int[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            arr[i] = r.nextInt(201) - 100; // generating number between -100 and 100
        }
        return arr;
    }
}