import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testHeapSortRandomArray() {
        for (int i = 0; i < 1000; i++) {
            int[] input = Main.createRandomArr(i);

            int[] expected = input.clone();
            Arrays.sort(expected);  // trusted sort function

            Main.heapsort(input);   // homemade sort function

            assertArrayEquals(expected, input);
        }
    }

    @Test
    void testHeapSortNullArray() {
        int[] input = null;
        Main.heapsort(input);
        assertArrayEquals(null, input);
    }
}