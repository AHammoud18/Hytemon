package org.bouncycastle.its.operator;

public interface ETSIDataDecryptor {
  byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3);
  
  byte[] getKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\its\operator\ETSIDataDecryptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */