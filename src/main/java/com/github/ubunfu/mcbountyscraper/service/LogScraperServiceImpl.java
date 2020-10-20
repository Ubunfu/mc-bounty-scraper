package com.github.ubunfu.mcbountyscraper.service;

import com.github.ubunfu.mcbountyscraper.client.BountyProcessorClient;
import com.github.ubunfu.mcbountyscraper.client.BountyProcessorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogScraperServiceImpl implements LogScraperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogScraperServiceImpl.class);
    private static final String MATCHER_ACHIEVEMENT = "has made the advancement";
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String RIGHT_SQUARE_BRACKET = "]";

    private BountyProcessorClient bountyProcessorClient;

    @Autowired
    public LogScraperServiceImpl(BountyProcessorClient bountyProcessorClient) {
        this.bountyProcessorClient = bountyProcessorClient;
    }

    @Override
    public void handleLog(String log) {
        if (log.contains(MATCHER_ACHIEVEMENT)) {
            bountyProcessorClient.postBounty(buildBoundyProcessorRequest(log));
        }
    }

    private BountyProcessorRequest buildBoundyProcessorRequest(String message) {
        message = stripServerInfo(message);
        String playerName = parsePlayerName(message);
        String achievement = parseAchievement(message);
        return new BountyProcessorRequest(achievement, playerName);
    }

    private String parseAchievement(String message) {
        return message.substring(
                message.lastIndexOf(LEFT_SQUARE_BRACKET) + 1,
                message.lastIndexOf(RIGHT_SQUARE_BRACKET)
        );
    }

    private String parsePlayerName(String message) {
        return message.substring(0, message.indexOf(MATCHER_ACHIEVEMENT) - 1);
    }

    private String stripServerInfo(String message) {
        return message.substring(33);
    }
}
