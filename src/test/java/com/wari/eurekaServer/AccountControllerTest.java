package com.wari.eurekaServer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    // 스프링 MVC 컨트롤러에 HTTP 요청을 보내는 MVC 클라이언트 모의 객체를 주입받는다.
    @Autowired
    private MockMvc mvc;

    // AccountController 가 의존하는 AccountService 에 대한 모의 객체를 생성한다.
    @MockBean
    private AccountService accountService;

    @Test
    public void getUserAccountShouldReturnAccounts() throws Exception {
        String content = "[{\"username\": \"user\", \"accountNumber\": \"123456789\"}]";

        // 모의 객체인 AccountService 의 getUserAccounts 가 특정 값을 반화하도록 스텁한다.
        given(this.accountService.getUserAccounts()).willReturn(
                Collections.singletonList(new Account("user", "123456789")));

        // 마지막으로 MockMvc 클라이언트를 활용해서 실제 AccountController 가 반환하는 값과 테스트해서
        // 예상한 값이 일치하는지 판정한다.
        this.mvc.perform(get("/v1/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(content));
    }
}
