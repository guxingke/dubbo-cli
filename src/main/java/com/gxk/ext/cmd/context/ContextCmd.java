package com.gxk.ext.cmd.context;

import com.gxk.ext.config.Context;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.error(String.format("%s is active, can not be rm", active));
        return;
      }
      if (!ctxs.contains(newCtx)) {
        log.error(String.format("unknown context %s, all contexts %s", newCtx, ctxs));
        return;
      }

      cfg.getContexts().remove(newCtx);
      cfg.save(ctx.getSystemConfig().getConfigPath());
      return;
    }

    if (option.isUse()) {
      if (active.equals(newCtx)) {
        log.error(String.format("%s is already active", active));
        return;
      }

      if (!ctxs.contains(newCtx)) {
        log.error(String.format("unknown context %s, all contexts %s", newCtx, ctxs));
        return;
      }

      cfg.setActive(newCtx);
      cfg.save(ctx.getSystemConfig().getConfigPath());
      return;
    }

    if (option.isCp()) {
      if (!ctxs.contains(option.getSource())) {
        log.error(String.format("unknown context %s, all contexts %s", option.getSource(), ctxs));
        return;
      }

      if (ctxs.contains(option.getContext())) {
        log.error(String.format("context %s already exists, all contexts %s", option.getContext(), ctxs));
        return;
      }

      Context sourceContext = cfg.getContexts().get(option.getSource());
      cfg.getContexts().put(option.getContext(), sourceContext);
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
        "context use <ctx>",
        "context rm <ctx>",
        "context cp <source> <target>"
    );

    msges.forEach(System.out::println);
  }
}
