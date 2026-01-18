package org.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;

public interface CMSProcessable {
  void write(OutputStream paramOutputStream) throws IOException, CMSException;
  
  Object getContent();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\CMSProcessable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */