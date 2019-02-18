package com.wari.eurekaServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/*
    @PropertySource 애노테이션은 일종의 단축키로, 앞의 예제에 사용된 property-placeholder 와 비슷하게
    properties 파일에서 Properties 파일에서 PropertySource 를 설정하는 역할을 한다.
*/
@Configuration
@PropertySource("test.properties")
public class Application {

    private final Log log = LogFactory.getLog(getClass());

    public static void main(String[] args) {
//        new ClassPathXmlApplicationContext("test.xml");
        new AnnotationConfigApplicationContext(Application.class);
    }

    /*public void setConfigurationProjectName(String pn) {
        LogFactory.getLog(getClass())
                .info("the configuration project name is " + pn);
    }*/

    /*
        PropertySourcesPlaceholderConfigurer 는 BeanFactoryPostProcessor 의 구현체이고
        스프링 빈 생성 수명 주기에서 초기에 호출되어야 하므로 static 빈으로 등록해야한다.
        스프링 XML 빈 설정 방식을 사용하면 이런 미묘한 차이를 알아챌 수 없다.
     */
    @Bean
    static PropertySourcesPlaceholderConfigurer pspc() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /*
        @Value 를 클래스 필드에 사용할 수 있다.
        하지만 이렇게 클래스 필드에 직접 사용하면 테스트할 때 불편하다.
     */
    @Value("${configuration.projectName}")
    private String fieldValue;

    /*
        @Value 를 생성자의 파라미터에 사용할 수 있다.
    */
    @Autowired
    Application(@Value("${configuration.projectName}") String pn) {
        log.info("Application constructor: " + pn);
    }

    /*
        @Value 를 세터 메소드의 파라미터에 사용할 수 있다.
     */
    @Value("${configuration.projectName}")
    void setProjectName(String projectName) {
        log.info("setProjectName: " + projectName);
    }

    /*
        스프링 Environment 객체를 주입받아서 속성 값을 직접 가져올 수 있다.
    */
    @Autowired
    void setEnvironment(Environment env) {
        log.info("setEnvironment: " + env.getProperty("configuration.projectName"));
    }

    /*
        @Value 를 @Bean 이 붙은 메소드의 파라미터에 사용할 수 있다.
     */
    @Bean
    InitializingBean both(Environment env, @Value("${configuration.projectName}") String projectName) {
        return () -> {
            log.info("@Bean with both dependencies (projectName): " + projectName);
            log.info("@Bean with both dependencies (env): " + env.getProperty("configuration.projectName"));
        };
    }

    @PostConstruct
    void afterPropertiesSet() throws Throwable {
        log.info("fieldValue: " + this.fieldValue);
    }
}
