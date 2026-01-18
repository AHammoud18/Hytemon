package org.bouncycastle.pqc.crypto.slhdsa;

class SIG_FORS {
  final byte[][] authPath;
  
  final byte[] sk;
  
  SIG_FORS(byte[] paramArrayOfbyte, byte[][] paramArrayOfbyte1) {
    this.authPath = paramArrayOfbyte1;
    this.sk = paramArrayOfbyte;
  }
  
  byte[] getSK() {
    return this.sk;
  }
  
  public byte[][] getAuthPath() {
    return this.authPath;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SIG_FORS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */