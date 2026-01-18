package org.bouncycastle.crypto;

public interface Committer {
  Commitment commit(byte[] paramArrayOfbyte);
  
  boolean isRevealed(Commitment paramCommitment, byte[] paramArrayOfbyte);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\Committer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */