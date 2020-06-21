package ladder.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StepRandomStrategyTest {
    @ParameterizedTest
    @MethodSource("countOfUserAndResult")
    @DisplayName("random 값이 항상 true를 반환 시, true가 연속되어 나타날 수 없다.")
    void step_exception(List<Boolean> expectedList) {
        StepStrategy stepStrategy = new StepRandomStrategy(new RandomReturnTrue());

        expectedList.forEach(expected -> assertThat(stepStrategy.nextStep()).isEqualTo(expected));
    }

    static Stream<Arguments> countOfUserAndResult() {
        return Stream.of(
                arguments(Arrays.asList(true)),
                arguments(Arrays.asList(true, false)),
                arguments(Arrays.asList(true, false, true)),
                arguments(Arrays.asList(true, false, true, false)));
    }

    private static class RandomReturnTrue extends Random {
        @Override
        public boolean nextBoolean() {
            return true;
        }
    }
}