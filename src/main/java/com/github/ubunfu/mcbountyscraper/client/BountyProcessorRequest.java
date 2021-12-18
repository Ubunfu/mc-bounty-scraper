package com.github.ubunfu.mcbountyscraper.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BountyProcessorRequest {

  private String achievement;
  private String player;
}
