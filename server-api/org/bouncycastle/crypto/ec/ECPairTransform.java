package org.bouncycastle.crypto.ec;

import org.bouncycastle.crypto.CipherParameters;

public interface ECPairTransform {
  void init(CipherParameters paramCipherParameters);
  
  ECPair transform(ECPair paramECPair);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\ec\ECPairTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */