package com.gxk.ext.cmd.env;

import com.gxk.ext.config.Context;
import com.gxk.ext.config.Env;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import com.gxk.ext.logger.Log;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EnvCmd implements CmdHandler {
  private static final Log log = new Log();

  @Override
  public void apply(CmdContext ctx) {
    EnvOption option = new EnvOption();
    option.parseArgs(ctx.getArgs());

    if (option.isParseError()) {
      log.error(option.getMsg());
      return;
    }

    if (option.isHelp()) {
      printHelpMsg(ctx);
      return;
    }

    UserConfig cfg = ctx.getUserConfig();
    Context context = cfg.getContexts().get(cfg.getActive());

    String active = context.getActive();
    Set<String> envs = context.getEnvs().keySet();

    String newEnv = option.getEnv();
    if (option.isRm()) {
      if (active.equals(newEnv)) {
        log.error(String.format("%s is active, can not be rm", active));
        return;
      }
      if (!envs.contains(newEnv)) {
        log.error(String.format("unknown env %s, all envs %s", newEnv, envs));
        return;
      }

      context.getEnvs().remove(active);
      cfg.save(ctx.getSystemConfig().getConfigPath());
      return;
    }

    if (option.isUse()) {
      if (active.equals(newEnv)) {
        log.error(String.format("%s is already active", active));
        return;
      }

      if (!envs.contains(newEnv)) {
        log.error(String.format("unknown env %s, all envs %s", newEnv, envs));
        return;
      }

      context.setActive(newEnv);
      cfg.save(ctx.getSystemConfig().getConfigPath());
      return;
    }

    if (option.isSet()) {
      Env env = new Env(option.getHost(), option.getPort(), option.getLink(), option.getCharset(),
          option.getTransport());
      context.getEnvs().put(option.getEnv(), env);
      cfg.save(ctx.getSystemConfig().getConfigPath());
      return;
    }
  }

  private void printHelpMsg(CmdContext ctx) {
    UserConfig cfg = ctx.getUserConfig();
    Context context = cfg.getContexts().get(cfg.getActive());
    String active = context.getActive();
    Set<String> envs = context.getEnvs().keySet();

    List<String> msges = Arrays.asList(
        "Active Env: " + active,
        "Env Options: " + envs,
        "",
        "USEAGE: ",
        "env use xxxx",
        "env rm xxxx",
        "env set xxxx --host=127.0.0.1 --port=20880 --link=telnet --charset=UTF-8 --transport=cnet"
    );

    msges.forEach(log::info);
  }
}
