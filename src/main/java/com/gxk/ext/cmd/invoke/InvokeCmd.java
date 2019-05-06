package com.gxk.ext.cmd.invoke;

import com.gxk.ext.config.Alias;
import com.gxk.ext.config.Context;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.core.CmdContext;
import com.gxk.ext.core.CmdHandler;
import com.gxk.ext.telnet.TelnetClient;
import com.gxk.ext.telnet.TelnetClientFactory;
import com.gxk.ext.util.Utils;
import java.util.Arrays;
import java.util.List;

public class InvokeCmd implements CmdHandler {

  @Override
  public void apply(CmdContext ctx) {
    InvokeOption option = new InvokeOption();
    option.parseArgs(ctx.getArgs());

    if (option.isTestCall()) {
      doTestCall(ctx, option.getCmd());
      return;
    }

    if (option.isHelp()) {
      doPrintHelpMessage();
      return;
    }

    if (option.isSet()) {
      doSet(ctx, option);
      return;
    }

    if (option.isLs()) {
      doLs(ctx);
      return;
    }

    if (option.isRm()) {
      doRm(ctx, option);
      return;
    }

    if (option.isAliasCall()) {
      doAliasCall(ctx, option);
      return;
    }
  }

  private void doAliasCall(CmdContext ctx, InvokeOption option) {
    UserConfig cfg = ctx.getUserConfig();
    Context context = cfg.getContexts().get(cfg.getActive());
    Alias alias = context.getAliases().get(option.getAlias());
    if (alias == null) {
      System.err.println("alias not found, available alias : " + context.getAliases().keySet());
      return;
    }
    String tpl = alias.getTpl();
    String args = alias.getArgs();
    if (option.getAliasArgs() != null) {
      args = option.getAliasArgs();
    }

    String[] argArr = args.split(",");

    String cmd = Utils.format(tpl, argArr);

    doTestCall(ctx, cmd);
  }

  private void doRm(CmdContext ctx, InvokeOption option) {
    UserConfig config = ctx.getUserConfig();
    String alias = option.getAlias();

    Context context = config.getContexts().get(config.getActive());
    context.getAliases().remove(alias);
    config.save(ctx.getSystemConfig().getConfigPath());
  }

  private void doLs(CmdContext ctx) {
    UserConfig cfg = ctx.getUserConfig();
    Context context = cfg.getContexts().get(cfg.getActive());

    context.getAliases().forEach((key, val) -> {
      System.out.println("ALIAS:  " + key);
      System.out.println("  TPL:  " + val.getTpl());
      System.out.println(" ARGS:  " + val.getArgs());
      System.out.println();
    });

  }

  private void doSet(CmdContext ctx, InvokeOption option) {
    UserConfig config = ctx.getUserConfig();

    String alias = option.getAlias();
    String tpl = option.getAliasTpl();
    String args = option.getAliasArgs();

    Context context = config.getContexts().get(config.getActive());

    context.getAliases().put(alias, new Alias(tpl, args));

    config.save(ctx.getSystemConfig().getConfigPath());
  }

  private void doPrintHelpMessage() {
    List<String> lines = Arrays.asList(
        "dubbo telnet invoke wrapper",
        "",
        "USAGE:",
        "- test call",
        "test <service>.<method>(<args>)",
        "",
        "- alias call",
        "ls                                 # list all alias",
        "<alias> <arg1> <arg2>              # e.g f1 \"test\" true",
        "set <alias> <tpl> <default args>   # e.g set f1 test.HelloService.hello(\"{}\", {}) test,true",
        "rm <alias>                         # e.g rm f1"
    );

    lines.forEach(System.out::println);
  }

  private void doTestCall(CmdContext ctx, String cmd) {
    TelnetClient client = TelnetClientFactory
        .createClient(Utils.parseTelnetConfig(ctx.getUserConfig()));

    String cmd2 = String.format("invoke %s", cmd);
    String ret = client.exec(cmd2);

    int elapsed = ret.indexOf("elapsed");
    // not found
    if (elapsed < 0) {
      System.out.println(ret);
      return;
    }
    // sub last \n
    String subStr = ret.substring(0, elapsed - 1);
    System.out.println(subStr);
  }
}
