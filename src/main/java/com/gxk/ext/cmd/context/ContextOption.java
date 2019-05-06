package com.gxk.ext.cmd.context;

import com.gxk.ext.cmd.CmdOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*

help

use default
use beta

rm default

 */
@Data
@Slf4j
public class ContextOption implements CmdOption {

  private boolean help;

  private boolean use;
  private boolean rm;
  private boolean cp;

  private String source;
  private String context;

  @Override
  public void parseArgs(String... args) {
    if (args.length == 0 || args.length == 1) {
      help = true;
      return;
    }

    if (args.length == 2) {
      String arg = args[0];
      String ctx = args[1];
      switch (arg) {
        case "use":
          use = true;
          context = ctx;
          return;
        case "rm":
          rm = true;
          context = ctx;
          return;
        default:
          log.info("illegal args");
          help = true;
          return;
      }
    }

    if (args.length == 3) {
      String arg = args[0];
      String source = args[1];
      String target = args[2];

      switch (arg) {
        case "cp":
          this.cp = true;
          this.source = source;
          this.context = target;
          return;
        default:
          log.info("illegal args");
          help = true;
          return;
      }
    }

    help = true;
  }
}
