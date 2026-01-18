package org.bouncycastle.pqc.crypto.ntru;

class OWCPAKeyPair {
  public final byte[] publicKey;
  
  public final byte[] privateKey;
  
  public OWCPAKeyPair(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.publicKey = paramArrayOfbyte1;
    this.privateKey = paramArrayOfbyte2;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntru\OWCPAKeyPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */