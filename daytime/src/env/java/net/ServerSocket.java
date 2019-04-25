/* $Id: ServerSocket.java 838 2010-11-24 08:16:23Z cartho $ */

package env.java.net;

/* ServerSocket stub. */

import gov.nasa.jpf.vm.Verify;
import java.io.IOException;

public class ServerSocket {
  
  public ServerSocket(int port) throws IOException {
  }

  public Socket accept() throws IOException {
      if (Verify.getBoolean()) { // possible failure
        throw new java.net.ConnectException("Connection refused");
      }
    return new Socket();
  }

  public void close() {
  }
}
