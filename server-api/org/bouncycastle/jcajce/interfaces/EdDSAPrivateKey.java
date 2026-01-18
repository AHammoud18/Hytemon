package org.bouncycastle.jcajce.interfaces;

import java.security.PrivateKey;

public interface EdDSAPrivateKey extends EdDSAKey, PrivateKey {
  EdDSAPublicKey getPublicKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\interfaces\EdDSAPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */