package com.google.crypto.tink.hybrid.internal;

import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

@Immutable
public interface HpkeKem {
  HpkeKemEncapOutput encapsulate(byte[] paramArrayOfbyte) throws GeneralSecurityException;
  
  byte[] decapsulate(byte[] paramArrayOfbyte, HpkeKemPrivateKey paramHpkeKemPrivateKey) throws GeneralSecurityException;
  
  HpkeKemEncapOutput authEncapsulate(byte[] paramArrayOfbyte, HpkeKemPrivateKey paramHpkeKemPrivateKey) throws GeneralSecurityException;
  
  byte[] authDecapsulate(byte[] paramArrayOfbyte1, HpkeKemPrivateKey paramHpkeKemPrivateKey, byte[] paramArrayOfbyte2) throws GeneralSecurityException;
  
  byte[] getKemId() throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeKem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */