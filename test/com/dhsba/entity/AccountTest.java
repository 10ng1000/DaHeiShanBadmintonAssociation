package com.dhsba.entity;

import com.dhsba.dao.AccountDao;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    Account account = new Account("123", "123", false);
    AccountDao accountDao = new AccountDao();

    @Test
    void changePassWord() {
        account.changePassWord("000");
        assertEquals(Optional.of("000"), accountDao.getPassword("123"));
        account.changePassWord("123");
    }

    @Test
    void login() {
        assertEquals(true, Account.login("1234", "1234"));
    }
}