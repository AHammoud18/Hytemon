package com.google.crypto.tink.jwt;

import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Immutable
public interface JwtPublicKeySignInternal {
  String signAndEncodeWithKid(RawJwt paramRawJwt, Optional<String> paramOptional) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtPublicKeySignInternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */