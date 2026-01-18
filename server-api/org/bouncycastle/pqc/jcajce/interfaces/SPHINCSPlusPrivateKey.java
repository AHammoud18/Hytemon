package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;

public interface SPHINCSPlusPrivateKey extends PrivateKey, SPHINCSPlusKey {
  SPHINCSPlusPublicKey getPublicKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\interfaces\SPHINCSPlusPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */