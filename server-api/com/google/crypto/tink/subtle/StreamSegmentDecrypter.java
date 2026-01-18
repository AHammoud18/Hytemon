package com.google.crypto.tink.subtle;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;

public interface StreamSegmentDecrypter {
  void init(ByteBuffer paramByteBuffer, byte[] paramArrayOfbyte) throws GeneralSecurityException;
  
  void decryptSegment(ByteBuffer paramByteBuffer1, int paramInt, boolean paramBoolean, ByteBuffer paramByteBuffer2) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\StreamSegmentDecrypter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */