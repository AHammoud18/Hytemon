package org.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;

public interface ECPrivateKey extends ECKey, PrivateKey {
  BigInteger getD();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\interfaces\ECPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */