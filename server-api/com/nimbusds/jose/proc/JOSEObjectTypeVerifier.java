package com.nimbusds.jose.proc;

import com.nimbusds.jose.JOSEObjectType;

public interface JOSEObjectTypeVerifier<C extends SecurityContext> {
  void verify(JOSEObjectType paramJOSEObjectType, C paramC) throws BadJOSEException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\proc\JOSEObjectTypeVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */