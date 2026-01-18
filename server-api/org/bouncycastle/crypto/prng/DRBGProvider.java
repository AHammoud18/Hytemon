package org.bouncycastle.crypto.prng;

import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;

interface DRBGProvider {
  String getAlgorithm();
  
  SP80090DRBG get(EntropySource paramEntropySource);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\prng\DRBGProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */