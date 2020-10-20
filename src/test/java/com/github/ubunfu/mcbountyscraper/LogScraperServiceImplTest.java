package com.github.ubunfu.mcbountyscraper;

import com.github.ubunfu.mcbountyscraper.client.BountyProcessorClient;
import com.github.ubunfu.mcbountyscraper.client.BountyProcessorRequest;
import com.github.ubunfu.mcbountyscraper.service.LogScraperService;
import com.github.ubunfu.mcbountyscraper.service.LogScraperServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogScraperServiceImplTest {

    private static final String PLAYER = "PLAYER_001";
    private static final String ACHIEVEMENT = "Who's the Pillager Now?";
    private static final String MESSAGE_ACHIEVEMENT = "[01:27:02] [Server thread/INFO]: " + PLAYER + " has made the advancement [" + ACHIEVEMENT + "]";
    private static final String MESSAGE_NO_ACHIEVEMENT = "This message has no bounty";

    private LogScraperService logScraperService;

    @Mock
    private BountyProcessorClient bountyProcessorClient;

    @BeforeEach
    void setUp() {
        logScraperService = new LogScraperServiceImpl(bountyProcessorClient);
    }

    @Test
    void callsBountyProcessorForAchievementEarned() {
        logScraperService.handleLog(MESSAGE_ACHIEVEMENT);
        verify(bountyProcessorClient).postBounty(new BountyProcessorRequest(ACHIEVEMENT, PLAYER));
    }

    @Test
    void doesNotCallBountyProcessorIfNotAchievementEarned() {
        logScraperService.handleLog(MESSAGE_NO_ACHIEVEMENT);
        verify(bountyProcessorClient, never()).postBounty(any());
    }
}
