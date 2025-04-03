package web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import web.model.User;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;




@Configuration
@ComponentScan(value = "web")
public class AppConfig {

   @Bean
   public EntityManagerFactory createEntityManagerFactory() {
      return Persistence.createEntityManagerFactory("spring_hiber");
   }


}
