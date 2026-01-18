package org.bouncycastle.jcajce.interfaces;

import java.security.PublicKey;

public interface EdDSAPublicKey extends EdDSAKey, PublicKey {
  byte[] getPointEncoding();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\interfaces\EdDSAPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */