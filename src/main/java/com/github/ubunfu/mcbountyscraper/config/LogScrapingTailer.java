package com.github.ubunfu.mcbountyscraper.config;

import java.io.File;
import org.apache.commons.io.input.Tailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogScrapingTailer extends Tailer {

  @Autowired
  public LogScrapingTailer(
      TailerProperties tailerProperties, LogScrapingTailerListenerAdapter tailerListenerAdapter) {
    super(
        new File(tailerProperties.getLogFile()),
        tailerListenerAdapter,
        tailerProperties.getReadDelayMillis(),
        tailerProperties.isReadFromEnd(),
        tailerProperties.isCloseFileBetweenChunks());
  }
}
