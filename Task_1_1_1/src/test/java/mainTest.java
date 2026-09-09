import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class mainTest {

    @Test
    void testHeapSortRandomArray() {
        for (int i = 0; i < 1000; i++) {
            int[] input = main.create_random_arr(i);

            int[] expected = input.clone();
            Arrays.sort(expected);  // trusted sort function

            main.heapsort(input);   // homemade sort function

            assertArrayEquals(expected, input);
        }
    }
}