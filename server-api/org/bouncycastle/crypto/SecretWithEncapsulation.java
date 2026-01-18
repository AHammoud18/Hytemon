package org.bouncycastle.crypto;

import javax.security.auth.Destroyable;

public interface SecretWithEncapsulation extends Destroyable {
  byte[] getSecret();
  
  byte[] getEncapsulation();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\SecretWithEncapsulation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */