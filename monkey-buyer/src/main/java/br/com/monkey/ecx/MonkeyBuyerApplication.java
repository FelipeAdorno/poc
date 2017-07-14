package br.com.monkey.ecx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.cache.ElastiCacheAutoConfiguration;

import java.util.Locale;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ElastiCacheAutoConfiguration.class})
public class MonkeyBuyerApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		SpringApplication.run(MonkeyBuyerApplication.class, args);
	}
}
