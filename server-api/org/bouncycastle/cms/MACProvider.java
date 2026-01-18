package org.bouncycastle.cms;

import java.io.IOException;

interface MACProvider {
  byte[] getMAC();
  
  void init() throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\MACProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */