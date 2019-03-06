package com.wari.eurekaServer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// 자동 설정 테스트 슬라이스 컨텍스트에 RestTemplate 을 등록한다.
@RunWith(SpringRunner.class)
@RestClientTest({ UserService.class })
@AutoConfigureWebClient(registerRestTemplate = true)
public class UserServiceTests {

    @Value("${user-service.host:user-service}")
    private String serviceHost;

    @Autowired
    private UserService userService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void getAuthenticatedUserShouldReturnUser() {
        // RestTemplate 가 특정 URL 로 요청을 보낸 후 그 URL 로부터 받아오는 반환값을 스텁한다.
        // new ClassPathResource("user.json") -> user.json
        // new ClassPathResource("user.json", getClass()) -> 현재 클래스 파일 패키지/user.json
        this.server.expect(
                requestTo(String.format("http://%s/uaa/v1/me", serviceHost))).andRespond(
                withSuccess(new ClassPathResource("user.json", getClass()),
                        MediaType.APPLICATION_JSON)); // <2>

        User user = userService.getAuthenticatedUser();

        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getFirstName()).isEqualTo("Jack");
        assertThat(user.getLastName()).isEqualTo("Frost");
        assertThat(user.getCreatedAt()).isEqualTo(12345);
        assertThat(user.getLastModified()).isEqualTo(12346);
        assertThat(user.getId()).isEqualTo(0L);
    }
}
