package org.bouncycastle.crypto.ec;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.math.ec.ECPoint;

public interface ECDecryptor {
  void init(CipherParameters paramCipherParameters);
  
  ECPoint decrypt(ECPair paramECPair);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\ec\ECDecryptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */