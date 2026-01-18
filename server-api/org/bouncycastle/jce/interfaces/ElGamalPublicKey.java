package org.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;

public interface ElGamalPublicKey extends ElGamalKey, DHPublicKey {
  BigInteger getY();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\interfaces\ElGamalPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */