package com.github.ubunfu.mcbountyscraper.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "tailer.file.name=testLogFile.log",
        "tailer.delay.seconds=10"
})
public class TailerConfigTest {

    @Autowired
    private LogScrapingTailer tailer;

    @Autowired
    private TailerProperties properties;

    @Test
    void testTailerIsConfiguredFromProperties() {
        assertThat(tailer.getFile().getPath(), equalTo(properties.getLogFile()));
        assertThat(tailer.getDelay(), equalTo(properties.getReadDelayMillis()));
    }
}
