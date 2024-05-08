package com.uos25.uos25.auth.login.service;

import com.uos25.uos25.Store.entity.Store;
import com.uos25.uos25.Store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {

    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        Store store = storeRepository.findByCode(code)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(store.getCode())
                .password(store.getPassword())
                .roles(store.getRole().getKey())
                .build();
    }

}
