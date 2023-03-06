
import static org.junit.jupiter.api.Assertions.assertEquals;

import code.guide.calc.Calculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

class AppTest {

    @Test
    void testCountMedian() {
        double result = Calculator.countMedian(List.of(1.0,2.0,3.0));
        double expected = 2.0;
        assertEquals(expected, result, 0.001);

        result = Calculator.countMedian(List.of(1.0,2.0,3.0,4.0));
        expected = 2.5;
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testCountModa() {
        double result = Calculator.countModa(List.of(1.0,2.0,3.0,4.0,5.0));
        double expected = 3.0;
        assertEquals(expected, result, 0.001);

        result = Calculator.countModa(List.of(1.0,2.0,3.0,4.0,5.0,6.0));
        expected = 3.5;
        assertEquals(expected, result, 0.001);

        result = Calculator.countModa(List.of(1.0,2.0,3.0,4.0,5.0,2.0));
        expected = 2.0;
        assertEquals(expected, result, 0.001);

        result = Calculator.countModa(List.of(1.0,2.0,3.0,5.0,3.0,2.0,3.0));
        expected = 3.0;
        assertEquals(expected, result, 0.001);
    }


}
