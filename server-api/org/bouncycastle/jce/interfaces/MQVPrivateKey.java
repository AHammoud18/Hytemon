package org.bouncycastle.jce.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface MQVPrivateKey extends PrivateKey {
  PrivateKey getStaticPrivateKey();
  
  PrivateKey getEphemeralPrivateKey();
  
  PublicKey getEphemeralPublicKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\interfaces\MQVPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */