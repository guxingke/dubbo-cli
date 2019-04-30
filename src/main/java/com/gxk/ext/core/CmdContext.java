package com.gxk.ext.core;

import com.gxk.ext.config.SystemConfig;
import com.gxk.ext.config.UserConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmdContext {

  private SystemConfig systemConfig;
  private UserConfig userConfig;
  private String[] args;

  public static CmdContext mockContext() {
    CmdContext ctx = new CmdContext();
    ctx.setUserConfig(new UserConfig());
    ctx.setSystemConfig(new SystemConfig());
    ctx.setArgs(new String[0]);
    return ctx;
  }
}
