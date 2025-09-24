package com.example.cardatabase;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 구성 관련 클래스임을 선언
public class OpenApiConfig {

    @Bean // 이 메서드가 반환하는 객체를 스프링 컨테이너에 등록해라
    public OpenAPI carDtabaseOpenApi() { // 굳이 따지면 메서드
        return new OpenAPI()             // 새로운 OpenAPI 객체를 만들어서 반환해줌
                .info(new Info()
                        .title("Car REST API")
                        .description("My car Stock")
                        .version("1.0"));
    }
}
