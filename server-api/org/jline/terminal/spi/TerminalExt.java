package org.jline.terminal.spi;

import org.jline.terminal.Terminal;

public interface TerminalExt extends Terminal {
  TerminalProvider getProvider();
  
  SystemStream getSystemStream();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\spi\TerminalExt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */