package lotto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lotto.model.Lotto;
import lotto.model.Result;
import lotto.model.User;
import lotto.model.wonTheLotto;
import lotto.utils.Converter;
import lotto.utils.randomNumbersLotto;
import lotto.view.InputView;
import lotto.view.outputView;

public class Controller {

    private final Converter<String, Integer> converterNumber;
    private final Converter<String, List<Integer>> seperateComma;


    public Controller(Converter<String, Integer> converterNumber, Converter<String, List<Integer>> seperateComma) {
        this.converterNumber = converterNumber;
        this.seperateComma = seperateComma;
    }

    public void tempInputOutputController() {
        outputView outputview = new outputView();
        try{
            int purchase = purchaseLotto();
            User user = new User(buyLottos(purchase/1000));
            Lotto inputWinningNumber = outputWinningStatistics();
            int bonusNumber = getBonus();
            wonTheLotto wonTheLotto = new wonTheLotto(inputWinningNumber,bonusNumber);
            Result result = new Result(user.confirmResult(wonTheLotto));
            outputResult(result);

        }catch (IllegalArgumentException e){
            outputview.printError(e.getMessage());
        }
    }

    private int purchaseLotto() {
        InputView inputView = new InputView();
        String inputPurchase = inputView.howMuchBuyLotto();
        int purchase = converterNumber.convert(inputPurchase);
        // 추후에 이 값이 유효한지 확인해보는 과정 넣기
        return purchase;
    }

    private List<Lotto> buyLottos(int count) {
        randomNumbersLotto randomNumbersLotto = new randomNumbersLotto();
        outputView outputview = new outputView();

        List<Lotto> lottos = IntStream.range(0, count)
                .mapToObj(i -> new Lotto(randomNumbersLotto.makeRandomNumLottos())).collect(
                        Collectors.toList());
        outputview.printLotto(lottos);
        return lottos;
    }

    private Lotto outputWinningStatistics() {
        InputView inputView = new InputView();
        String userInputLotto = inputView.inputWinningNumber();
        return new Lotto(seperateComma.convert(userInputLotto));
    }

    private int getBonus() {
        InputView inputView = new InputView();
        String inputBonus = inputView.inputBonusNumber();
        return converterNumber.convert(inputBonus);
    }

    private void outputResult(Result result) {
        outputView outputview = new outputView();
        outputview.outputWinningStatistics(result);
        outputview.outputReturn(result.rateOfReturn());
    }
}
