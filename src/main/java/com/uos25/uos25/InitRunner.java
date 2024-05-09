package com.uos25.uos25;

import com.uos25.uos25.funds.entity.Funds;
import com.uos25.uos25.funds.repository.FundsRepository;
import com.uos25.uos25.store.entity.Role;
import com.uos25.uos25.store.entity.Store;
import com.uos25.uos25.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitRunner implements ApplicationRunner {
    private final FundsRepository fundsRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Store store = initStore();
        Funds funds = initFunds(store);

        storeRepository.save(store);
        fundsRepository.save(funds);
    }

    public Store initStore() {
        Store store = Store.builder()
                .code("qwer")
                .location("서울")
                .role(Role.USER)
                .build();
        store.passwordEncode(passwordEncoder);

        return store;
    }

    public Funds initFunds(Store store) {
        return Funds.builder()
                .headPayment(100)
                .maintenanceExpense(100)
                .personalExpense(100)
//                .sales(20000)
                .store(store)
                .build();
    }
}
