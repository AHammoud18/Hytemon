package com.google.crypto.tink.jwt;

import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Immutable
public interface JwtPublicKeyVerifyInternal {
  VerifiedJwt verifyAndDecodeWithKid(String paramString, JwtValidator paramJwtValidator, Optional<String> paramOptional) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtPublicKeyVerifyInternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */