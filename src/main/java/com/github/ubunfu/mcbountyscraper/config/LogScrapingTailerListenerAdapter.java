package com.github.ubunfu.mcbountyscraper.config;

import com.github.ubunfu.mcbountyscraper.service.LogScraperService;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

import static java.lang.String.format;

@Component
public class LogScrapingTailerListenerAdapter extends TailerListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogScrapingTailerListenerAdapter.class);

    private static final String ERROR_FILE_NOT_FOUND = "Unable to locate file: %s";

    private LogScraperService logScraperService;
    private TailerProperties tailerProperties;

    @Autowired
    public LogScrapingTailerListenerAdapter(
            LogScraperService logScraperService,
            TailerProperties tailerProperties) {
        this.logScraperService = logScraperService;
        this.tailerProperties = tailerProperties;
    }

    @Override
    public void handle(String line) {
        LOGGER.info(format("Read event: %s", line));
        logScraperService.handleLog(line);
    }

    @Override
    public void fileNotFound() {
        try {
            LOGGER.error(format(ERROR_FILE_NOT_FOUND, tailerProperties.getLogFile()));
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(format(ERROR_FILE_NOT_FOUND, tailerProperties.getLogFile()), e);
        }
    }
}
