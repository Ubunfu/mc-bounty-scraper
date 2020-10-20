package com.github.ubunfu.mcbountyscraper;

import com.github.ubunfu.mcbountyscraper.config.LogScrapingTailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class McBountyScraperApplication implements CommandLineRunner {

	@Autowired
	private LogScrapingTailer tailer;

	@Value("${tailer.enabled}")
	private boolean tailerEnabled;

	public static void main(String[] args) {
		SpringApplication.run(McBountyScraperApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (tailerEnabled)
			tailer.run();
	}
}
