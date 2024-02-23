package cn.wangqiqi.barkserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "cn.wangqiqi.barkserver.mapper")
public class BarkServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarkServerApplication.class, args);
    }

}
