package org.bouncycastle.jcajce.interfaces;

import java.security.PublicKey;

public interface MLDSAPublicKey extends PublicKey, MLDSAKey {
  byte[] getPublicData();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\interfaces\MLDSAPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */