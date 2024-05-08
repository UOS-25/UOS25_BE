package com.uos25.uos25.Store.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    
    USER("USER");

    private final String key;
}
