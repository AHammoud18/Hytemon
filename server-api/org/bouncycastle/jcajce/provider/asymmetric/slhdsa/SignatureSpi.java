package org.bouncycastle.jcajce.provider.asymmetric.slhdsa;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseDeterministicOrRandomSignature;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSASigner;

public class SignatureSpi extends BaseDeterministicOrRandomSignature {
  private final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
  
  private final SLHDSASigner signer;
  
  protected SignatureSpi(SLHDSASigner paramSLHDSASigner) {
    super("SLH-DSA");
    this.signer = paramSLHDSASigner;
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
    this.bOut.write(paramByte);
  }
  
  protected void updateEngine(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
    this.bOut.write(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  protected byte[] engineSign() throws SignatureException {
    AsymmetricKeyParameter asymmetricKeyParameter = this.keyParams;
    if (!(asymmetricKeyParameter instanceof org.bouncycastle.pqc.crypto.slhdsa.SLHDSAPrivateKeyParameters))
      throw new SignatureException("engine initialized for verification"); 
    try {
      byte[] arrayOfByte = this.signer.generateSignature(this.bOut.toByteArray());
      return arrayOfByte;
    } catch (Exception exception) {
      throw new SignatureException(exception.toString());
    } finally {
      this.isInitState = true;
      this.bOut.reset();
    } 
  }
  
  protected boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException {
    AsymmetricKeyParameter asymmetricKeyParameter = this.keyParams;
    if (!(asymmetricKeyParameter instanceof org.bouncycastle.pqc.crypto.slhdsa.SLHDSAPublicKeyParameters))
      throw new SignatureException("engine initialized for signing"); 
    try {
      return this.signer.verifySignature(this.bOut.toByteArray(), paramArrayOfbyte);
    } finally {
      this.isInitState = true;
      this.bOut.reset();
    } 
  }
  
  protected void reInitialize(boolean paramBoolean, CipherParameters paramCipherParameters) {
    this.signer.init(paramBoolean, paramCipherParameters);
    this.bOut.reset();
  }
  
  public static class Direct extends SignatureSpi {
    public Direct() {
      super(new SLHDSASigner());
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\slhdsa\SignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */