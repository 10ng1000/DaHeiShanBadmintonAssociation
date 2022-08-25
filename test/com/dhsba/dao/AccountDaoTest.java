package com.dhsba.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {
    AccountDao accountDao = new AccountDao();

    @Test
    void createAccount() {
        accountDao.createAccount("1234", "1234");
        accountDao.createAccount("12345", "12345");
    }

    @Test
    void getAccount() {
    }

    @Test
    void changePassword() {
    }
}