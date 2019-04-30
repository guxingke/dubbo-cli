package com.gxk.ext.cmd.context;

import com.gxk.ext.cmd.CmdOption;
import lombok.Data;

/*

help

use default
use beta

rm default

 */
@Data
public class ContextOption implements CmdOption {

  private boolean help;

  private boolean use;
  private boolean rm;

  private String context;

  @Override
  public void parseArgs(String... args) {
    if (args.length == 0 || args.length == 1) {
      help = true;
      return;
    }

    if (args.length > 2) {
      System.out.println("illegal args");
      help = true;
      return;
    }

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
      default:
        System.out.println("illegal args");
        help = true;
        return;
    }
  }
}
