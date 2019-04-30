package com.gxk.ext.telnet.cnet;

import com.gxk.ext.constants.Const;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.net.telnet.TelnetClient;

public class Telnet implements com.gxk.ext.telnet.TelnetClient {

  private final String host;
  private final int port;

  private final String charset;

  public Telnet(String host, int port, String charset) {
    this.host = host;
    this.port = port;
    this.charset = charset;
  }

  public String exec(String cmd) {
    TelnetClient tc = new TelnetClient();

    try {
      tc.connect(host, port);
    } catch (IOException e) {
      e.printStackTrace();
    }

    InputStream is = tc.getInputStream();

    byte[] bytes = new byte[0];
    try {
      OutputStream os = tc.getOutputStream();
      bytes = (cmd + "\r\n").getBytes(charset);
      os.write(bytes);
      os.flush();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    StringBuilder sb = new StringBuilder();
    boolean end = false;
    try {
      byte[] buff = new byte[1024];

      int retRead = 0;

      do {
        retRead = is.read(buff);
        if (retRead > 0) {
          String str = new String(buff, 0, retRead, charset);
          if (str.endsWith("dubbo>")) {
            end = true;
          }
          if (!end) {
            sb.append(str);
          } else {
            // rm \ndubbo>
            str = str.substring(0, str.length() - 7);
            sb.append(str);
          }
        }
      }
      while (!end);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      tc.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String ret = sb.toString();
    if (charset.equals(Const.CHARSET_UTF8)) {
      return ret;
    }

    String newRet = new String(ret.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    return newRet;
  }
}
