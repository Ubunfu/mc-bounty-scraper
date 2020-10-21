# mc-bounty-scraper
This app scrapes minecraft server logs for messages indicating that a player earned a "bounty".  Bounties are things 
that merit the awarding of some currency to the player's wallet.  When such a message is found, `mc-bounty-scraper` 
it POSTs to a REST endoint for a `mc-bounty-processor` service.  `mc-bounty-scraper` will provide the player's username
and the name of the bounty that was earned.

## Recognized Bounties
* All Vanilla Minecraft achievements ("advancements")

## Configuration
The following configs must be provided as environment variables to the container:

| Parameter                  | Description                                                                                     | Default                            | Required? |
|----------------------------|-------------------------------------------------------------------------------------------------|------------------------------------|-----------|
| TAILER_LOGFILE             | The path to the primary Minecraft server log file, relative to the root of the attached volume. | server.log                         | Yes       |
| TAILER_READDELAYMILLIS     | How frequently the app checks the server log for updates, in milliseconds.                      | 1000                               | Yes       |
| CLIENT_BOUNTYPROCESSOR_URL | Full URL of the bounty processor app.                                                           | https://api-gateway.aws.com/bounty | Yes       | 