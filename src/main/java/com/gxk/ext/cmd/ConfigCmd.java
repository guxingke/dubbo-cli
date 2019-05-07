package com.gxk.ext.cmd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;

public class ConfigCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    UserConfig config = ctx.getUserConfig();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(config);
    log.info(json);
  }
}
