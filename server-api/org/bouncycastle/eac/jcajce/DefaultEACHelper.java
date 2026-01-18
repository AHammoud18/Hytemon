package org.bouncycastle.eac.jcajce;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

class DefaultEACHelper implements EACHelper {
  public KeyFactory createKeyFactory(String paramString) throws NoSuchAlgorithmException {
    return KeyFactory.getInstance(paramString);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\eac\jcajce\DefaultEACHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */