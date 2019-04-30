package com.gxk.ext.telnet;

import java.util.HashMap;
import java.util.Map;

public abstract class TelnetClientHolder {

  private static Map<String, TelnetClient> clients = new HashMap<>();

  public static void putIfPresent(String name, TelnetClient client) {
    clients.putIfAbsent(name, client);
  }

  public static TelnetClient getDefaultClient() {
    return getClient("cnet");
  }

  public static TelnetClient getClient(String name) {
    return clients.get(name);
  }
}
