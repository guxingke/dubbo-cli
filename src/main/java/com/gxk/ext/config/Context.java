package com.gxk.ext.config;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Context {

  private String active;
  private Map<String, Env> envs;

  private Map<String, Alias> aliases;

  public Map<String, Alias> getAliases() {
    if (aliases == null) {
      aliases = new LinkedHashMap<>();
    }
    return aliases;
  }
}
