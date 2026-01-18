package org.bouncycastle.jcajce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

public interface XDHPublicKey extends XDHKey, PublicKey {
  BigInteger getU();
  
  byte[] getUEncoding();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\interfaces\XDHPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */