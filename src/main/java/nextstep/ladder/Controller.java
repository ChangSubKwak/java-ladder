package nextstep.ladder;

import nextstep.ladder.domain.IndexedName;
import nextstep.ladder.domain.Ladder;
import nextstep.ladder.domain.Spoke;
import nextstep.ladder.view.InputView;
import nextstep.ladder.view.ResultView;

import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Controller {

    private static final Random RANDOM = new Random();
    private static final String ALL = "all";
    private final InputView inputView = new InputView();
    private final ResultView resultView = new ResultView(new OutputStreamWriter(System.out));

    public void play() {
        List<IndexedName> players = IndexedName.wrap(inputView.requestPlayers());
        List<IndexedName> goals = IndexedName.wrap(inputView.requestGoal());
        int ladderHeight = inputView.requestHeight();

        Ladder ladder = Ladder.of(createSpokes(ladderHeight, players.size()), goals);

        resultView.printLadder(ladder);

        String name;
        do {
            name = inputView.requestPlayerName();
            resultView.printResult(getMoveResult(ladder, name));
        } while (!name.equals(ALL));
    }

    private Stream<Spoke> createSpokes(int ladderHeight, int width) {
        return IntStream.range(0, ladderHeight)
                .mapToObj(__ -> Spoke.fromCount(width - 1, RANDOM::nextBoolean));
    }

    private Map<String, String> getMoveResult(Ladder ladder, String name) {
        if (name.equals(ALL)) {
            return ladder.moveForAll();
        }
        Map<String, String> result = new HashMap<>();
        result.put(name, ladder.moveFor(name));
        return result;
    }
}
