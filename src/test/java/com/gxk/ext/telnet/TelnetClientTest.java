package com.gxk.ext.telnet;

import static org.junit.Assert.*;

import com.gxk.ext.telnet.cnet.Telnet;
import com.gxk.ext.telnet.netty.TelnetClient;
import java.io.IOException;
import org.junit.Test;

public class TelnetClientTest {

  @Test
  public void testCnet() throws InterruptedException, IOException {
    Telnet client = new Telnet("10.1.2.51", 19528, "GBK");
    String ls = client.exec("ls");
    System.out.println(ls);
    assertNotNull(ls);
  }

  @Test
  public void testNetty() throws InterruptedException, IOException {
//    TelnetClient client = new TelnetClient("10.1.2.51", 19528);
    TelnetClient client = new TelnetClient("127.0.0.1", 20880);
    String ls = client.exec("ls");
    System.out.println(ls);
    assertNotNull(ls);
  }
}