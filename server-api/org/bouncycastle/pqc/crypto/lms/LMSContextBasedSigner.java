package org.bouncycastle.pqc.crypto.lms;

public interface LMSContextBasedSigner {
  LMSContext generateLMSContext();
  
  byte[] generateSignature(LMSContext paramLMSContext);
  
  long getUsagesRemaining();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\lms\LMSContextBasedSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */