package org.bouncycastle.jcajce.provider.asymmetric.mldsa;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseDeterministicOrRandomSignature;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;
import org.bouncycastle.pqc.crypto.mldsa.HashMLDSASigner;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;

public class HashSignatureSpi extends BaseDeterministicOrRandomSignature {
  private HashMLDSASigner signer;
  
  private MLDSAParameters parameters;
  
  protected HashSignatureSpi(HashMLDSASigner paramHashMLDSASigner) {
    super("HashMLDSA");
    this.signer = paramHashMLDSASigner;
    this.parameters = null;
  }
  
  protected HashSignatureSpi(HashMLDSASigner paramHashMLDSASigner, MLDSAParameters paramMLDSAParameters) {
    super(MLDSAParameterSpec.fromName(paramMLDSAParameters.getName()).getName());
    this.signer = paramHashMLDSASigner;
    this.parameters = paramMLDSAParameters;
  }
  
  protected void verifyInit(PublicKey paramPublicKey) throws InvalidKeyException {
    if (paramPublicKey instanceof BCMLDSAPublicKey) {
      BCMLDSAPublicKey bCMLDSAPublicKey = (BCMLDSAPublicKey)paramPublicKey;
      this.keyParams = (AsymmetricKeyParameter)bCMLDSAPublicKey.getKeyParams();
      if (this.parameters != null) {
        String str = MLDSAParameterSpec.fromName(this.parameters.getName()).getName();
        if (!str.equals(bCMLDSAPublicKey.getAlgorithm()))
          throw new InvalidKeyException("signature configured for " + str); 
      } 
    } else {
      throw new InvalidKeyException("unknown public key passed to ML-DSA");
    } 
  }
  
  protected void signInit(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
    this.appRandom = paramSecureRandom;
    if (paramPrivateKey instanceof BCMLDSAPrivateKey) {
      BCMLDSAPrivateKey bCMLDSAPrivateKey = (BCMLDSAPrivateKey)paramPrivateKey;
      this.keyParams = (AsymmetricKeyParameter)bCMLDSAPrivateKey.getKeyParams();
      if (this.parameters != null) {
        String str = MLDSAParameterSpec.fromName(this.parameters.getName()).getName();
        if (!str.equals(bCMLDSAPrivateKey.getAlgorithm()))
          throw new InvalidKeyException("signature configured for " + str); 
      } 
    } else {
      throw new InvalidKeyException("unknown private key passed to ML-DSA");
    } 
  }
  
  protected void updateEngine(byte paramByte) throws SignatureException {
    this.signer.update(paramByte);
  }
  
  protected void updateEngine(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
    this.signer.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  protected byte[] engineSign() throws SignatureException {
    try {
      return this.signer.generateSignature();
    } catch (Exception exception) {
      throw new SignatureException(exception.toString());
    } 
  }
  
  protected boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException {
    return this.signer.verifySignature(paramArrayOfbyte);
  }
  
  protected void reInitialize(boolean paramBoolean, CipherParameters paramCipherParameters) {
    this.signer.init(paramBoolean, paramCipherParameters);
  }
  
  public static class MLDSA extends HashSignatureSpi {
    public MLDSA() {
      super(new HashMLDSASigner());
    }
  }
  
  public static class MLDSA44 extends HashSignatureSpi {
    public MLDSA44() {
      super(new HashMLDSASigner(), MLDSAParameters.ml_dsa_44_with_sha512);
    }
  }
  
  public static class MLDSA65 extends HashSignatureSpi {
    public MLDSA65() {
      super(new HashMLDSASigner(), MLDSAParameters.ml_dsa_65_with_sha512);
    }
  }
  
  public static class MLDSA87 extends HashSignatureSpi {
    public MLDSA87() throws NoSuchAlgorithmException {
      super(new HashMLDSASigner(), MLDSAParameters.ml_dsa_87_with_sha512);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mldsa\HashSignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */