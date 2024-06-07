package ddareunging.ddareunging_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DdareungingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdareungingServerApplication.class, args);
	}

}
