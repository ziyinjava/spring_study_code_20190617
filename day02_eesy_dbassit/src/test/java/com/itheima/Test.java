package com.itheima;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.domain.Account;

import java.util.List;

/**
 * 测试
 */
public class Test {

    public static void main(String[] args) {

//        Account account = new Account();
//        account.setName("ccc");
//        account.setMoney(1234f);
        IAccountDao accountDao = new AccountDaoImpl();
//        accountDao.save(account);
//        Account account = accountDao.findById(1);
//        System.out.println(account);
        List<Account> accounts = accountDao.findAll();
        System.out.println(accounts);
    }
}

