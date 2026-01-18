package com.google.crypto.tink;

import java.security.GeneralSecurityException;

public interface KmsClient {
  boolean doesSupport(String paramString);
  
  KmsClient withCredentials(String paramString) throws GeneralSecurityException;
  
  KmsClient withDefaultCredentials() throws GeneralSecurityException;
  
  Aead getAead(String paramString) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KmsClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */