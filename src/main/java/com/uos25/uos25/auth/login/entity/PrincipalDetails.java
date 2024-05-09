package com.uos25.uos25.auth.login.entity;

import com.uos25.uos25.store.entity.Store;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class PrincipalDetails implements UserDetails {
    private Store store;
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetails(Store store) {
        this.store = store;
    }

    // OAuth 로그인
    public PrincipalDetails(Store store, Map<String, Object> attributes) {
        this.store = store;
        this.attributes = attributes;
    }

    // UserDetails //
    @Override // 해당 User의 권한을 리턴하는곳.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> store.getRole().getKey());
        return collect;
    }

    @Override
    public String getPassword() {
        return store.getPassword();
    }

    @Override
    public String getUsername() {
        return store.getCode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // ex) 1년동안 로그인 안하면 휴먼계정
        return true;
    }

    public long getId() {
        return store.getId();
    }
}
