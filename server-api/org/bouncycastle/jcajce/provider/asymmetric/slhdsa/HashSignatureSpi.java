package org.bouncycastle.jcajce.provider.asymmetric.slhdsa;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseDeterministicOrRandomSignature;
import org.bouncycastle.pqc.crypto.slhdsa.HashSLHDSASigner;

public class HashSignatureSpi extends BaseDeterministicOrRandomSignature {
  private final HashSLHDSASigner signer;
  
  protected HashSignatureSpi(HashSLHDSASigner paramHashSLHDSASigner) {
    super("HASH-SLH-DSA");
    this.signer = paramHashSLHDSASigner;
  }
  
  protected void verifyInit(PublicKey paramPublicKey) throws InvalidKeyException {
    if (paramPublicKey instanceof BCSLHDSAPublicKey) {
      BCSLHDSAPublicKey bCSLHDSAPublicKey = (BCSLHDSAPublicKey)paramPublicKey;
      this.keyParams = (AsymmetricKeyParameter)bCSLHDSAPublicKey.getKeyParams();
    } else {
      throw new InvalidKeyException("unknown public key passed to SLH-DSA");
    } 
  }
  
  protected void signInit(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
    this.appRandom = paramSecureRandom;
    if (paramPrivateKey instanceof BCSLHDSAPrivateKey) {
      BCSLHDSAPrivateKey bCSLHDSAPrivateKey = (BCSLHDSAPrivateKey)paramPrivateKey;
      this.keyParams = (AsymmetricKeyParameter)bCSLHDSAPrivateKey.getKeyParams();
    } else {
      throw new InvalidKeyException("unknown private key passed to SLH-DSA");
    } 
  }
  
  protected void updateEngine(byte paramByte) throws SignatureException {
    this.signer.update(paramByte);
  }
  
  protected void updateEngine(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
    this.signer.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  protected byte[] engineSign() throws SignatureException {
    AsymmetricKeyParameter asymmetricKeyParameter = this.keyParams;
    if (!(asymmetricKeyParameter instanceof org.bouncycastle.pqc.crypto.slhdsa.SLHDSAPrivateKeyParameters))
      throw new SignatureException("engine initialized for verification"); 
    try {
      byte[] arrayOfByte = this.signer.generateSignature();
      return arrayOfByte;
    } catch (Exception exception) {
      throw new SignatureException(exception.toString());
    } finally {
      this.isInitState = true;
    } 
  }
  
  protected boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException {
    AsymmetricKeyParameter asymmetricKeyParameter = this.keyParams;
    if (!(asymmetricKeyParameter instanceof org.bouncycastle.pqc.crypto.slhdsa.SLHDSAPublicKeyParameters))
      throw new SignatureException("engine initialized for signing"); 
    try {
      return this.signer.verifySignature(paramArrayOfbyte);
    } finally {
      this.isInitState = true;
    } 
  }
  
  protected void reInitialize(boolean paramBoolean, CipherParameters paramCipherParameters) {
    this.signer.init(paramBoolean, paramCipherParameters);
  }
  
  public static class Direct extends HashSignatureSpi {
    public Direct() {
      super(new HashSLHDSASigner());
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\slhdsa\HashSignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */