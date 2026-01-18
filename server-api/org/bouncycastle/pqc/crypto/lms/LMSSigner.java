package org.bouncycastle.pqc.crypto.lms;

import java.io.IOException;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.crypto.MessageSigner;

public class LMSSigner implements MessageSigner {
  private LMSPrivateKeyParameters privKey;
  
  private LMSPublicKeyParameters pubKey;
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    if (paramBoolean) {
      if (paramCipherParameters instanceof HSSPrivateKeyParameters) {
        HSSPrivateKeyParameters hSSPrivateKeyParameters = (HSSPrivateKeyParameters)paramCipherParameters;
        if (hSSPrivateKeyParameters.getL() == 1) {
          this.privKey = hSSPrivateKeyParameters.getRootKey();
        } else {
          throw new IllegalArgumentException("only a single level HSS key can be used with LMS");
        } 
      } else {
        this.privKey = (LMSPrivateKeyParameters)paramCipherParameters;
      } 
    } else if (paramCipherParameters instanceof HSSPublicKeyParameters) {
      HSSPublicKeyParameters hSSPublicKeyParameters = (HSSPublicKeyParameters)paramCipherParameters;
      if (hSSPublicKeyParameters.getL() == 1) {
        this.pubKey = hSSPublicKeyParameters.getLMSPublicKey();
      } else {
        throw new IllegalArgumentException("only a single level HSS key can be used with LMS");
      } 
    } else {
      this.pubKey = (LMSPublicKeyParameters)paramCipherParameters;
    } 
  }
  
  public byte[] generateSignature(byte[] paramArrayOfbyte) {
    try {
      return LMS.generateSign(this.privKey, paramArrayOfbyte).getEncoded();
    } catch (IOException iOException) {
      throw new IllegalStateException("unable to encode signature: " + iOException.getMessage());
    } 
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    try {
      return LMS.verifySignature(this.pubKey, LMSSignature.getInstance(paramArrayOfbyte2), paramArrayOfbyte1);
    } catch (IOException iOException) {
      throw new IllegalStateException("unable to decode signature: " + iOException.getMessage());
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\lms\LMSSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */