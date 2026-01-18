package org.bouncycastle.pqc.crypto.slhdsa;

class SK {
  final byte[] seed;
  
  final byte[] prf;
  
  SK(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.seed = paramArrayOfbyte1;
    this.prf = paramArrayOfbyte2;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */