package com.gxk.ext.cmd.context;

import com.gxk.ext.config.UserConfig;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ContextCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    ContextOption option = new ContextOption();
    option.parseArgs(ctx.getArgs());

    if (option.isHelp()) {
      printHelpMsg(ctx);
      return;
    }

    UserConfig cfg = ctx.getUserConfig();
    String active = cfg.getActive();
    Set<String> ctxs = cfg.getContexts().keySet();

    String newCtx = option.getContext();
    if (option.isRm()) {
      if (active.equals(newCtx)) {
        System.err.println(String.format("%s is active, can not be rm", active));
        return;
      }
      if (!ctxs.contains(newCtx)) {
        System.err.println(String.format("unknown context %s, all contexts %s", newCtx, ctxs));
        return;
      }

      cfg.getContexts().remove(newCtx);
      cfg.save(ctx.getSystemConfig().getConfigPath());
      return;
    }

    if (option.isUse()) {
      if (active.equals(newCtx)) {
        System.err.println(String.format("%s is already active", active));
        return;
      }

      if (!ctxs.contains(newCtx)) {
        System.err.println(String.format("unknown context %s, all contexts %s", newCtx, ctxs));
        return;
      }

      cfg.setActive(newCtx);
      cfg.save(ctx.getSystemConfig().getConfigPath());
      return;
    }
  }

  private void printHelpMsg(CmdContext ctx) {
    UserConfig cfg = ctx.getUserConfig();
    String active = cfg.getActive();
    Set<String> ctxs = cfg.getContexts().keySet();

    List<String> msges = Arrays.asList(
        "Active Context: " + active,
        "Context Options: " + ctxs,
        "",
        "USEAGE: ",
        "context use xxxx",
        "context rm xxxx"
    );

    msges.forEach(System.out::println);
  }
}
