package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;

public interface XMSSPrivateKey extends XMSSKey, PrivateKey {
  long getIndex();
  
  long getUsagesRemaining();
  
  XMSSPrivateKey extractKeyShard(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\interfaces\XMSSPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */