package com.xuan.service;

import com.xuan.entity.Account;

public interface AccountService {
    public Account findByUsername(String username);
}
