package com.gxk.ext.telnet;

import com.gxk.ext.telnet.cnet.Telnet;

public class TelnetClientFactory {

  public static TelnetClient createClient(TelnetConfig config) {
    if (config.getTransport().equals("cnet")) {
      return new Telnet(config.getHost(), config.getPort(), config.getCharset());
    } else {
      return new com.gxk.ext.telnet.netty.TelnetClient(config.getHost(), config.getPort());
    }
  }
}
