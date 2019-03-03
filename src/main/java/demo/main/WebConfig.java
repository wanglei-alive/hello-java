package demo.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ImportResource("classpath:spring-config.xml")
@Configuration
@EnableWebMvc
@Import(value = {WebMvcConfiguration.class})
public class WebConfig {

}
