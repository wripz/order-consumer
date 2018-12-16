package cn.vpclub.order.consumer;

import cn.vpclub.spring.boot.cors.autoconfigure.CorsConfiguration;
import cn.vpclub.spring.boot.cors.autoconfigure.CorsProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import cn.vpclub.spring.boot.swagger.autoconfigure.EnableSBCSwagger;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("cn.vpclub.order.consumer.mapper*")
@SpringBootApplication
@EnableConfigurationProperties({CorsProperties.class})
@EnableSBCSwagger
public class ConsumerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
                ConsumerApplication.class);
        application.run(args);
    }

    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        return new CorsConfiguration();
    }

}
