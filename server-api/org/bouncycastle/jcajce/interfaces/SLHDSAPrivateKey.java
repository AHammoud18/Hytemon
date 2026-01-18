package org.bouncycastle.jcajce.interfaces;

import java.security.PrivateKey;

public interface SLHDSAPrivateKey extends PrivateKey, SLHDSAKey {
  SLHDSAPublicKey getPublicKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\interfaces\SLHDSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */