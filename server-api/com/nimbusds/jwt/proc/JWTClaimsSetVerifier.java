package com.nimbusds.jwt.proc;

import com.nimbusds.jwt.JWTClaimsSet;

public interface JWTClaimsSetVerifier<C extends com.nimbusds.jose.proc.SecurityContext> {
  void verify(JWTClaimsSet paramJWTClaimsSet, C paramC) throws BadJWTException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jwt\proc\JWTClaimsSetVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */