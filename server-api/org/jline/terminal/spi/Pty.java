package org.jline.terminal.spi;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;

public interface Pty extends Closeable {
  InputStream getMasterInput() throws IOException;
  
  OutputStream getMasterOutput() throws IOException;
  
  InputStream getSlaveInput() throws IOException;
  
  OutputStream getSlaveOutput() throws IOException;
  
  Attributes getAttr() throws IOException;
  
  void setAttr(Attributes paramAttributes) throws IOException;
  
  Size getSize() throws IOException;
  
  void setSize(Size paramSize) throws IOException;
  
  SystemStream getSystemStream();
  
  TerminalProvider getProvider();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\spi\Pty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */