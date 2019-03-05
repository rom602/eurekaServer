package com.wari.eurekaServer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class UserTests {

    private User user;

    // Jackson에서 제공하는 AsserJ 기반의 json 테스터를 주입받는다.
    @Autowired
    private JacksonTester<User> json;

    @Before
    public void setUp() throws Exception {
        User user = new User("user", "Jack", "Frost", "jfrost@example.com");
        user.setId(0L);
        user.setCreatedAt(12345L);
        user.setLastModified(12345L);
        this.user = user;
    }

    @Test
    public void deserializeJson() throws Exception {
        String content = "{\n" +
                "  \"username\": \"user\",\n" +
                "  \"firstName\": \"Jack\",\n" +
                "  \"lastName\": \"Frost\",\n" +
                "  \"email\": \"jfrost@example.com\",\n" +
                "  \"createdAt\": 12345,\n" +
                "  \"lastModified\": 123456,\n" +
                "  \"id\": 0\n" +
                "}";
        /*assertThat(this.json.parse(content))
                .isEqualTo(new User("user", "Jack", "Frost", "jfrost@example.com"));*/
        assertThat(this.json.parseObject(content).getUsername())
                .isEqualTo("user");
    }

    @Test
    public void serializeJson() throws Exception {
        // User 객체를 JSON으로 변환하고, user.json 파일과 비교한다.
        /*assertThat(this.json.write(user)).isEqualTo("user.json");
        assertThat(this.json.write(user)).isEqualToJson("user.json");*/
        assertThat(this.json.write(user)).hasJsonPathStringValue("@.username");

        // 실제 JSON 결과가 예상한 속성값과 일치하는지 판정한다.
        assertJsonPropertyEquals("@.username", "user");
        assertJsonPropertyEquals("@.firstName", "Jack");
        assertJsonPropertyEquals("@.lastName", "Frost");
        assertJsonPropertyEquals("@.email", "jfrost@example.com");
    }

    private void assertJsonPropertyEquals(String key, String value) throws IOException {
        assertThat(this.json.write(user)).extractingJsonPathStringValue(key)
                .isEqualTo(value);
    }
}
