package com.nimbusds.jose;

import com.nimbusds.jose.util.Base64URL;
import java.security.Signature;

public interface CompletableJWSObjectSigning {
  Signature getInitializedSignature();
  
  Base64URL complete() throws JOSEException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\CompletableJWSObjectSigning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */