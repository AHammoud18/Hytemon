package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;

public interface LMSPrivateKey extends LMSKey, PrivateKey {
  long getIndex();
  
  long getUsagesRemaining();
  
  LMSPrivateKey extractKeyShard(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\interfaces\LMSPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */