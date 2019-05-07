package com.gxk.ext.config;

import com.gxk.ext.logger.Log;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import lombok.Data;

@Data
public class UserConfig {

  private static final Log log = new Log();

  private String active = "default";
  private Map<String, Context> contexts;

  public static UserConfig load(Path configPath) {
    Toml toml = new Toml().read(configPath.toFile());
    return toml.to(UserConfig.class);
  }

  public void save(Path configPath) {
    try {
      TomlWriter writer = new TomlWriter();
      writer.write(this, configPath.toFile());
    } catch (IOException e) {
      log.error("cmd err", e);
    }
  }
}
