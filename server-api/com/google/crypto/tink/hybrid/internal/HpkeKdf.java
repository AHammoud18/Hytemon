package com.google.crypto.tink.hybrid.internal;

import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

@Immutable
public interface HpkeKdf {
  byte[] labeledExtract(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, String paramString, byte[] paramArrayOfbyte3) throws GeneralSecurityException;
  
  byte[] labeledExpand(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, String paramString, byte[] paramArrayOfbyte3, int paramInt) throws GeneralSecurityException;
  
  byte[] extractAndExpand(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, String paramString1, byte[] paramArrayOfbyte3, String paramString2, byte[] paramArrayOfbyte4, int paramInt) throws GeneralSecurityException;
  
  byte[] getKdfId() throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeKdf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */