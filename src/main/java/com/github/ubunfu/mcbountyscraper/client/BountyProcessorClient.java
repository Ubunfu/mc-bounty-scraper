package com.github.ubunfu.mcbountyscraper.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bountyProcessor", url = "${client.bountyProcessor.url}")
public interface BountyProcessorClient {

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  Response postBounty(@RequestBody BountyProcessorRequest request);
}
