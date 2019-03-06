package com.wari.eurekaServer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber("098765432");

    // 애플리케이션 컨텍스트에서 AccountRepository 를 주입받는다.
    @Autowired
    private AccountRepository accountRepository;

    // 리포지토리를 사용하지 않고 영속성(persistence)을 관리할 수 있는 TestEntityManager 를 주입받는다.
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findUserAccountShouldReturnAccounts() throws Exception {
        // Account 엔티티를 인메모리 데이터베이스에 영속화한다.
        this.entityManager.persist(new Account("jack", ACCOUNT_NUMBER));
        // 영혹화된 Account 엔티티를 조회한다.
        List<Account> account = this.accountRepository.findAccountsByUsername("jack");
        assertThat(account).size().isEqualTo(1);
        Account actual = account.get(0);
        assertThat(actual.getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
        assertThat(actual.getUsername()).isEqualTo("jack");
    }

    @Test
    public void findAccountShouldReturnAccount() throws Exception {
        this.entityManager.persist(new Account("jill", ACCOUNT_NUMBER));
        Account account = this.accountRepository.findAccountByAccountNumber(ACCOUNT_NUMBER);
        assertThat(account).isNotNull();
        assertThat(account.getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
    }

    @Test
    public void findAccountShouldReturnNull() throws Exception {
        this.entityManager.persist(new Account("jack", ACCOUNT_NUMBER));
        Account account = this.accountRepository.findAccountByAccountNumber(new AccountNumber("000000000"));
        assertThat(account).isNull();
    }
}
