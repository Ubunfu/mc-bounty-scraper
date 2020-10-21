package com.github.ubunfu.mcbountyscraper.config;

import com.github.ubunfu.mcbountyscraper.service.LogScraperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogScrapingTailerListenerAdapterTest {

    private static final String TEST_EVENT = "test log message";

    private LogScrapingTailerListenerAdapter listenerAdapter;

    @Mock
    private LogScraperService logScraperService;

    @Mock
    private TailerProperties tailerProperties;

    @BeforeEach
    void setUp() {
        listenerAdapter = new LogScrapingTailerListenerAdapter(logScraperService, tailerProperties);
    }

    @Test
    void listenerCallsService() {
        listenerAdapter.handle(TEST_EVENT);
        verify(logScraperService).handleLog(TEST_EVENT);
    }

    @Test
    void missingLogFileThrowsRuntimeExceptionCausedByFileNotFoundException() {
        Throwable exception = assertThrows(RuntimeException.class, () -> listenerAdapter.fileNotFound());
        assertThat(exception, isA(RuntimeException.class));
        assertThat(exception.getCause(), isA(FileNotFoundException.class));
    }
}
