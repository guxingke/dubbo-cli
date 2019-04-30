package com.gxk.ext.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmdRegistry {

  private Map<String, CmdHandler> cmds = new HashMap<>();

  public final void reg(String name, CmdHandler handler) {
    cmds.putIfAbsent(name, handler);
  }

  public final void reg(List<String> names, CmdHandler handler) {
    names.forEach(it -> reg(it, handler));
  }

  public final CmdHandler get(String name) {
    return cmds.get(name);
  }
}
