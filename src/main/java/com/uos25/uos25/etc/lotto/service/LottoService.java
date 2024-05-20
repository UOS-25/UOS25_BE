package com.uos25.uos25.etc.lotto.service;

import com.uos25.uos25.common.error.etc.LottoNotFoundException;
import com.uos25.uos25.etc.lotto.dto.LottoDTO.LottoBuyResponse;
import com.uos25.uos25.etc.lotto.dto.LottoDTO.LottoCheckResponse;
import com.uos25.uos25.etc.lotto.dto.LottoDTO.LottoResultResponse;
import com.uos25.uos25.etc.lotto.entity.Lotto;
import com.uos25.uos25.etc.lotto.entity.Rank;
import com.uos25.uos25.etc.lotto.repository.LottoRepository;
import com.uos25.uos25.funds.service.FundsService;
import com.uos25.uos25.store.service.StoreService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LottoService {
    private final FundsService fundsService;
    private final LottoRepository lottoRepository;
    private final StoreService storeService;

    public void assignLottoInfo(Long storeId, String winningNumber, int price) {
        Lotto lotto = lottoRepository.findByStoreId(storeId).orElse(null);

        if (lotto == null) {
            lottoRepository.save(
                    Lotto.builder()
                            .price(price)
                            .winningNumbers(winningNumber)
                            .store(storeService.getStoreById(storeId))
                            .build()
            );
            return;
        }

        lotto.update(price, winningNumber);
        lottoRepository.save(lotto);
    }

    public LottoBuyResponse buyLotto(Long storeId, int lottoAmount) {
        Lotto lotto = lottoRepository.findByStoreId(storeId).orElseThrow(LottoNotFoundException::new);

        fundsService.sales(lottoAmount * lotto.getPrice(), storeId);

        List<LottoResultResponse> results = new ArrayList<>();

        for (int i = 0; i < lottoAmount; i++) {
            String numbers = RandomNumberGenerator.generateNumbers();
            LottoResultResponse response = LottoResultResponse.builder()
                    .numbers(numbers)
                    .result(checkLotto(storeId, numbers))
                    .build();

            results.add(response);
        }

        return new LottoBuyResponse(results);

    }

    public LottoCheckResponse checkLotto(Long storeId, String numbers) {
        Lotto lotto = lottoRepository.findByStoreId(storeId).orElseThrow(LottoNotFoundException::new);
        String winningNumbers = lotto.getWinningNumbers();
        int count = 0;

        for (int i = 0; i < 6; i++) {
            if (numbers.charAt(i) == winningNumbers.charAt(i)) {
                count += 1;
            }
        }

        return LottoCheckResponse.builder()
                .count(count)
                .rank(Rank.from(count).name())
                .build();
    }
}
