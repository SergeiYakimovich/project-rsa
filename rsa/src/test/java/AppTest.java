import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.NumberUtils;

class AppTest {

    @Test
    void testSortNumberByDigits() {
//        Integer result = NumberUtils.sortNumberByDigits(132496758);
//        Integer expected = 987654321;
//        assertThat(expected).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource({
            "132496758, 987654321",
            "123, 321",
            "4713, 7431"
    })
    void testSortNumberByDigits_1(int number, int expected) {
//        Integer result = NumberUtils.sortNumberByDigits(number);
//        assertThat(expected).isEqualTo(result);
    }

}
