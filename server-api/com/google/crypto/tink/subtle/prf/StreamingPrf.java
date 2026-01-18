package com.google.crypto.tink.subtle.prf;

import com.google.errorprone.annotations.Immutable;
import java.io.InputStream;

@Immutable
public interface StreamingPrf {
  InputStream computePrf(byte[] paramArrayOfbyte);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\prf\StreamingPrf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */