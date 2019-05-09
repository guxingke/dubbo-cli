package com.gxk.ext.telnet;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TelnetConfig {

  private String host;
  private int port;
  private String charset;
  private String transport;
}
