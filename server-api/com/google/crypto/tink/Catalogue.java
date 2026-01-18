package com.google.crypto.tink;

import com.google.crypto.tink.internal.PrimitiveWrapper;
import java.security.GeneralSecurityException;

@Deprecated
public interface Catalogue<P> {
  KeyManager<P> getKeyManager(String paramString1, String paramString2, int paramInt) throws GeneralSecurityException;
  
  PrimitiveWrapper<?, P> getPrimitiveWrapper() throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\Catalogue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */