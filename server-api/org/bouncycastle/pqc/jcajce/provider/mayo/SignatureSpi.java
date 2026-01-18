package org.bouncycastle.pqc.jcajce.provider.mayo;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.mayo.MayoParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoSigner;
import org.bouncycastle.util.Strings;

public class SignatureSpi extends Signature {
  private final ByteArrayOutputStream bOut;
  
  private final MayoSigner signer;
  
  private SecureRandom random;
  
  private final MayoParameters parameters;
  
  protected SignatureSpi(MayoSigner paramMayoSigner) {
    super("Mayo");
    this.bOut = new ByteArrayOutputStream();
    this.signer = paramMayoSigner;
    this.parameters = null;
  }
  
  protected SignatureSpi(MayoSigner paramMayoSigner, MayoParameters paramMayoParameters) {
    super(Strings.toUpperCase(paramMayoParameters.getName()));
    this.parameters = paramMayoParameters;
    this.bOut = new ByteArrayOutputStream();
    this.signer = paramMayoSigner;
  }
  
  protected void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException {
    if (!(paramPublicKey instanceof BCMayoPublicKey))
      try {
        paramPublicKey = new BCMayoPublicKey(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
      } catch (Exception exception) {
        throw new InvalidKeyException("unknown public key passed to Mayo: " + exception.getMessage());
      }  
    BCMayoPublicKey bCMayoPublicKey = (BCMayoPublicKey)paramPublicKey;
    if (this.parameters != null) {
      String str = Strings.toUpperCase(this.parameters.getName());
      if (!str.equals(bCMayoPublicKey.getAlgorithm()))
        throw new InvalidKeyException("signature configured for " + str); 
    } 
    this.signer.init(false, (CipherParameters)bCMayoPublicKey.getKeyParams());
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
    this.random = paramSecureRandom;
    engineInitSign(paramPrivateKey);
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException {
    if (paramPrivateKey instanceof BCMayoPrivateKey) {
      BCMayoPrivateKey bCMayoPrivateKey = (BCMayoPrivateKey)paramPrivateKey;
      MayoPrivateKeyParameters mayoPrivateKeyParameters = bCMayoPrivateKey.getKeyParams();
      if (this.parameters != null) {
        String str = Strings.toUpperCase(this.parameters.getName());
        if (!str.equals(bCMayoPrivateKey.getAlgorithm()))
          throw new InvalidKeyException("signature configured for " + str); 
      } 
      if (this.random != null) {
        this.signer.init(true, (CipherParameters)new ParametersWithRandom((CipherParameters)mayoPrivateKeyParameters, this.random));
      } else {
        this.signer.init(true, (CipherParameters)mayoPrivateKeyParameters);
      } 
    } else {
      throw new InvalidKeyException("unknown private key passed to Mayo");
    } 
  }
  
  protected void engineUpdate(byte paramByte) throws SignatureException {
    this.bOut.write(paramByte);
  }
  
  protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
    this.bOut.write(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  protected byte[] engineSign() throws SignatureException {
    try {
      byte[] arrayOfByte = this.bOut.toByteArray();
      this.bOut.reset();
      return this.signer.generateSignature(arrayOfByte);
    } catch (Exception exception) {
      throw new SignatureException(exception.toString());
    } 
  }
  
  protected boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException {
    byte[] arrayOfByte = this.bOut.toByteArray();
    this.bOut.reset();
    return this.signer.verifySignature(arrayOfByte, paramArrayOfbyte);
  }
  
  protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  protected void engineSetParameter(String paramString, Object paramObject) {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  protected Object engineGetParameter(String paramString) {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  public static class Base extends SignatureSpi {
    public Base() {
      super(new MayoSigner());
    }
  }
  
  public static class Mayo1 extends SignatureSpi {
    public Mayo1() {
      super(new MayoSigner(), MayoParameters.mayo1);
    }
  }
  
  public static class Mayo2 extends SignatureSpi {
    public Mayo2() {
      super(new MayoSigner(), MayoParameters.mayo2);
    }
  }
  
  public static class Mayo3 extends SignatureSpi {
    public Mayo3() {
      super(new MayoSigner(), MayoParameters.mayo3);
    }
  }
  
  public static class Mayo5 extends SignatureSpi {
    public Mayo5() {
      super(new MayoSigner(), MayoParameters.mayo5);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\mayo\SignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */