import java.util.Arrays;
import java.util.Random;

public class main {

    /**
     * Programm start.
     * creates random int array and does heapsort
     *
     * @param args shell arguments
     */
    public static void main(String[] args) {
        int[] ints = create_random_arr(2);
        heapsort(ints);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * sorts given array of integers (heapsort)
     *
     * @param arr Given array of integers
     */
    public static void heapsort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            sift_down(arr, arr.length, i);
        }
        for (int r = arr.length - 1; r > 0; r--) {
            swap(arr, 0, r);
            sift_down(arr, r, 0);
        }
    }

    /**
     * Swaps two elements in array
     *
     * @param arr Given array
     * @param i index of first element
     * @param j index of second element
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Does "sift down" in order to restore properties of max heap
     *
     * @param arr Given array of int
     * @param len Size of subarray, that we perceive as a max heap
     * @param i index of element, that should be "sifted down"
     */
    public static void sift_down(int[] arr,int len, int i) {
        while (2 * i + 1 < len) {
            int index_of_max = 2 * i + 1;

            if (2 * i + 2 < len && arr[2 * i + 2] > arr[2 * i + 1]) {
                index_of_max = 2 * i + 2;
            }
            if (arr[i] >= arr[index_of_max]) {
                break;
            }
            swap(arr, i, index_of_max);
            i = index_of_max;
        }
    }

    /**
     * Generates an array of given length. Elements are random integers from [-100;100]
     *
     * @param len length of the creating array
     * @return array of random integers
     */
    public static int[] create_random_arr(int len) {
        int[] arr = new int[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            arr[i] = r.nextInt(201) - 100; // generating number between -100 and 100
        }
        return arr;
    }

    /**
     * Generates an array of given length. Elements are random integers from [left_border;right_border]
     *
     * @param len length of the creating array
     * @param left_bound bottom border for random numbers
     * @param right_bound upper border for random numbers
     * @return array of random integers
     */
    public static int[] create_random_arr(int len, int left_bound, int right_bound) {
        if (left_bound > right_bound) {
            System.out.printf("In function 'create_random_arr' left_bound : %d > %d : right_bound. Aborting\n", left_bound, right_bound);
            System.exit(1);
        }
        int[] arr = new int[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            arr[i] = r.nextInt(right_bound - left_bound + 1) + left_bound; // generating number: left_bound <= number <= right_bound
        }
        return arr;
    }
}