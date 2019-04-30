package com.gxk.ext.util;

import com.gxk.ext.config.Context;
import com.gxk.ext.config.Env;
import com.gxk.ext.config.UserConfig;
import com.gxk.ext.telnet.TelnetConfig;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.slf4j.helpers.MessageFormatter;

public abstract class Utils {

  public static boolean rm(Path path, boolean recursive) {
    if (!path.toFile().exists()) {
      return false;
    }
    if (!recursive) {
      if (path.toFile().isDirectory()) {
        return false;
      } else {
        path.toFile().delete();
        return true;
      }
    }

    try {
      Files.walkFileTree(path, new FileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
            throws IOException {
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          file.toFile().delete();
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
          return FileVisitResult.TERMINATE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          dir.toFile().delete();
          return FileVisitResult.CONTINUE;
        }
      });
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public static TelnetConfig parseTelnetConfig(UserConfig userConfig) {
    Context context = userConfig.getContexts().get(userConfig.getActive());
    Env env = context.getEnvs().get(context.getActive());

    return TelnetConfig.builder()
        .host(env.getHost())
        .port(env.getPort())
        .charset(env.getCharset())
        .impl("cnet")
        .build();
  }

  public static String format(String tpl, Object... args) {
    return MessageFormatter.arrayFormat(tpl, args).getMessage();
  }
}
