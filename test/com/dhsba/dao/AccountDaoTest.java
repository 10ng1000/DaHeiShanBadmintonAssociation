package com.dhsba.dao;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountDaoTest {
    AccountDao accountDao = new AccountDao();

    @Test
    void createAccount() throws SQLException {
        accountDao.createAccount("789", "789");
        accountDao.createAccount("678", "678");
        accountDao.createAccount("7890", "7890");
        accountDao.createAccount("6789", "6789");
    }

    @Test
    void getPassword() {
        assertEquals(Optional.empty(), accountDao.getPassword("666"));
        assertEquals(Optional.of("1234"), accountDao.getPassword("1234"));
    }

    @Test
    void changePassword() {
    }
}