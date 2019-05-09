package com.gxk.ext.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Env {

  private String host;
  private int port;
  private String link;
  private String charset;
  // support cnet (commons net provider), netty (netty provider)
  private String transport = "cnet";
}
