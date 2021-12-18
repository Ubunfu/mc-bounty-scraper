package com.github.ubunfu.mcbountyscraper.service;

import com.github.ubunfu.mcbountyscraper.client.BountyProcessorClient;
import com.github.ubunfu.mcbountyscraper.client.BountyProcessorRequest;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogScraperServiceImpl implements LogScraperService {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogScraperServiceImpl.class);
  private static final String MATCHER_ACHIEVEMENT = "has made the advancement";
  private static final String MATCHER_CHALLENGE = "has completed the challenge";
  private static final String LEFT_SQUARE_BRACKET = "[";
  private static final String RIGHT_SQUARE_BRACKET = "]";
  private static final String SPACE = " ";

  private final BountyProcessorClient bountyProcessorClient;

  @Autowired
  public LogScraperServiceImpl(BountyProcessorClient bountyProcessorClient) {
    this.bountyProcessorClient = bountyProcessorClient;
  }

  @Override
  public void handleLog(String log) {
    if (log.contains(MATCHER_ACHIEVEMENT) || log.contains(MATCHER_CHALLENGE)) {
      try {
        Response response = bountyProcessorClient.postBounty(buildBountyProcessorRequest(log));
        LOGGER.info("Discord response: {}", response.status());
      } catch (RuntimeException e) {
        LOGGER.error("Error invoking Bounty Processor: {}, caused by: {}", e, e.getCause());
      }
    }
  }

  private BountyProcessorRequest buildBountyProcessorRequest(String message) {
    message = stripServerInfo(message);
    String playerName = parsePlayerName(message);
    String achievement = parseAchievement(message);
    return new BountyProcessorRequest(achievement, playerName);
  }

  private String parseAchievement(String message) {
    return message.substring(
        message.lastIndexOf(LEFT_SQUARE_BRACKET) + 1, message.lastIndexOf(RIGHT_SQUARE_BRACKET));
  }

  private String parsePlayerName(String message) {
    return message.split(SPACE)[0];
  }

  private String stripServerInfo(String message) {
    return message.substring(33);
  }
}
