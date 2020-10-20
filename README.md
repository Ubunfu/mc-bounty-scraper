# mc-bounty-scraper
This app scrapes minecraft server logs for messages indicating that a player earned a "bounty".  Bounties are things 
that merit the awarding of some currency to the player's wallet.  When such a message is found, `mc-bounty-scraper` 
it POSTs to a REST endoint for a `mc-bounty-processor` service.  `mc-bounty-scraper` will provide the player's username
and the name of the bounty that was earned.

## Recognized Bounties
* All Vanilla Minecraft achievements ("advancements")