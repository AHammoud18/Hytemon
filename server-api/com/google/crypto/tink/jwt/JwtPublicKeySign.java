package com.google.crypto.tink.jwt;

import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

@Immutable
public interface JwtPublicKeySign {
  String signAndEncode(RawJwt paramRawJwt) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtPublicKeySign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */