package org.bouncycastle.jcajce.interfaces;

import java.security.PrivateKey;

public interface MLDSAPrivateKey extends PrivateKey, MLDSAKey {
  MLDSAPublicKey getPublicKey();
  
  byte[] getPrivateData();
  
  byte[] getSeed();
  
  MLDSAPrivateKey getPrivateKey(boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\interfaces\MLDSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */