package com.gxk.ext.cmd;

import com.gxk.ext.config.Context;
import com.gxk.ext.config.Env;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;

public class StatusCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    UserConfig cfg = ctx.getUserConfig();
    String activeContext = cfg.getActive();
    Context context = cfg.getContexts().get(cfg.getActive());

    String activeEnv = context.getActive();
    Env env = context.getEnvs().get(context.getActive());

    log.info("Active Context: " + activeContext);
    log.info("Active Env: " + activeEnv);
    log.info("Env Detail: " + env);
  }
}
