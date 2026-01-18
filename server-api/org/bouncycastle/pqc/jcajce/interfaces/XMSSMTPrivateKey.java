package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;

public interface XMSSMTPrivateKey extends XMSSMTKey, PrivateKey {
  long getIndex();
  
  long getUsagesRemaining();
  
  XMSSMTPrivateKey extractKeyShard(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\interfaces\XMSSMTPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */