package com.google.crypto.tink.daead.subtle;

import com.google.crypto.tink.DeterministicAead;
import java.security.GeneralSecurityException;

public interface DeterministicAeads extends DeterministicAead {
  byte[] encryptDeterministicallyWithAssociatedDatas(byte[] paramArrayOfbyte, byte[]... paramVarArgs) throws GeneralSecurityException;
  
  byte[] decryptDeterministicallyWithAssociatedDatas(byte[] paramArrayOfbyte, byte[]... paramVarArgs) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\subtle\DeterministicAeads.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */