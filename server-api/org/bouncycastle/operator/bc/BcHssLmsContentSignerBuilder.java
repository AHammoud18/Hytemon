package org.bouncycastle.operator.bc;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.pqc.crypto.lms.HSSSigner;
import org.bouncycastle.pqc.crypto.lms.LMSSigner;

public class BcHssLmsContentSignerBuilder extends BcContentSignerBuilder {
  private static final AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_alg_hss_lms_hashsig);
  
  public BcHssLmsContentSignerBuilder() {
    super(sigAlgId, null);
  }
  
  protected Signer createSigner(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2) throws OperatorCreationException {
    return new HssSigner();
  }
  
  static class HssSigner implements Signer {
    private MessageSigner signer;
    
    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();
    
    public void init(boolean param1Boolean, CipherParameters param1CipherParameters) {
      if (param1CipherParameters instanceof org.bouncycastle.pqc.crypto.lms.HSSPublicKeyParameters || param1CipherParameters instanceof org.bouncycastle.pqc.crypto.lms.HSSPrivateKeyParameters) {
        this.signer = (MessageSigner)new HSSSigner();
      } else if (param1CipherParameters instanceof org.bouncycastle.pqc.crypto.lms.LMSPublicKeyParameters || param1CipherParameters instanceof org.bouncycastle.pqc.crypto.lms.LMSPrivateKeyParameters) {
        this.signer = (MessageSigner)new LMSSigner();
      } else {
        throw new IllegalArgumentException("Incorrect Key Parameters");
      } 
      this.signer.init(param1Boolean, param1CipherParameters);
    }
    
    public void update(byte param1Byte) {
      this.stream.write(param1Byte);
    }
    
    public void update(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
      this.stream.write(param1ArrayOfbyte, param1Int1, param1Int2);
    }
    
    public byte[] generateSignature() throws CryptoException, DataLengthException {
      byte[] arrayOfByte = this.stream.toByteArray();
      this.stream.reset();
      return this.signer.generateSignature(arrayOfByte);
    }
    
    public boolean verifySignature(byte[] param1ArrayOfbyte) {
      byte[] arrayOfByte = this.stream.toByteArray();
      this.stream.reset();
      return this.signer.verifySignature(arrayOfByte, param1ArrayOfbyte);
    }
    
    public void reset() {
      this.stream.reset();
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\operator\bc\BcHssLmsContentSignerBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */