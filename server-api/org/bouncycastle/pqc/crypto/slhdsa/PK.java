package org.bouncycastle.pqc.crypto.slhdsa;

class PK {
  final byte[] seed;
  
  final byte[] root;
  
  PK(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.seed = paramArrayOfbyte1;
    this.root = paramArrayOfbyte2;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\PK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */