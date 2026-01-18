package org.bouncycastle.jcajce.provider.asymmetric.mldsa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.jcajce.MLDSAProxyPrivateKey;
import org.bouncycastle.jcajce.interfaces.MLDSAPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseDeterministicOrRandomSignature;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPublicKeyParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSASigner;
import org.bouncycastle.pqc.crypto.util.PublicKeyFactory;

public class SignatureSpi extends BaseDeterministicOrRandomSignature {
  protected MLDSASigner signer;
  
  protected MLDSAParameters parameters;
  
  protected SignatureSpi(MLDSASigner paramMLDSASigner) {
    super("MLDSA");
    this.signer = paramMLDSASigner;
    this.parameters = null;
  }
  
  protected SignatureSpi(MLDSASigner paramMLDSASigner, MLDSAParameters paramMLDSAParameters) {
    super(MLDSAParameterSpec.fromName(paramMLDSAParameters.getName()).getName());
    this.signer = paramMLDSASigner;
    this.parameters = paramMLDSAParameters;
  }
  
  protected void verifyInit(PublicKey paramPublicKey) throws InvalidKeyException {
    BCMLDSAPublicKey bCMLDSAPublicKey;
    if (paramPublicKey instanceof BCMLDSAPublicKey) {
      BCMLDSAPublicKey bCMLDSAPublicKey1 = (BCMLDSAPublicKey)paramPublicKey;
      this.keyParams = (AsymmetricKeyParameter)bCMLDSAPublicKey1.getKeyParams();
    } else {
      try {
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded());
        this.keyParams = PublicKeyFactory.createKey(subjectPublicKeyInfo);
        bCMLDSAPublicKey = new BCMLDSAPublicKey((MLDSAPublicKeyParameters)this.keyParams);
      } catch (Exception exception) {
        throw new InvalidKeyException("unknown public key passed to ML-DSA");
      } 
    } 
    if (this.parameters != null) {
      String str = MLDSAParameterSpec.fromName(this.parameters.getName()).getName();
      if (!str.equals(bCMLDSAPublicKey.getAlgorithm()))
        throw new InvalidKeyException("signature configured for " + str); 
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
    } else if (paramPrivateKey instanceof MLDSAProxyPrivateKey && this instanceof MLDSACalcMu) {
      MLDSAProxyPrivateKey mLDSAProxyPrivateKey = (MLDSAProxyPrivateKey)paramPrivateKey;
      MLDSAPublicKey mLDSAPublicKey = mLDSAProxyPrivateKey.getPublicKey();
      try {
        this.keyParams = PublicKeyFactory.createKey(mLDSAPublicKey.getEncoded());
      } catch (IOException iOException) {
        throw new InvalidKeyException(iOException.getMessage());
      } 
      if (this.parameters != null) {
        String str = MLDSAParameterSpec.fromName(this.parameters.getName()).getName();
        if (!str.equals(mLDSAPublicKey.getAlgorithm()))
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
  
  public static class MLDSA extends SignatureSpi {
    public MLDSA() {
      super(new MLDSASigner());
    }
  }
  
  public static class MLDSA44 extends SignatureSpi {
    public MLDSA44() {
      super(new MLDSASigner(), MLDSAParameters.ml_dsa_44);
    }
  }
  
  public static class MLDSA65 extends SignatureSpi {
    public MLDSA65() {
      super(new MLDSASigner(), MLDSAParameters.ml_dsa_65);
    }
  }
  
  public static class MLDSA87 extends SignatureSpi {
    public MLDSA87() throws NoSuchAlgorithmException {
      super(new MLDSASigner(), MLDSAParameters.ml_dsa_87);
    }
  }
  
  public static class MLDSACalcMu extends SignatureSpi {
    public MLDSACalcMu() {
      super(new MLDSASigner());
    }
    
    protected byte[] engineSign() throws SignatureException {
      try {
        return this.signer.generateMu();
      } catch (Exception exception) {
        throw new SignatureException(exception.toString());
      } 
    }
    
    protected boolean engineVerify(byte[] param1ArrayOfbyte) throws SignatureException {
      return this.signer.verifyMu(param1ArrayOfbyte);
    }
  }
  
  public static class MLDSAExtMu extends SignatureSpi {
    private ByteArrayOutputStream bOut = new ByteArrayOutputStream(64);
    
    public MLDSAExtMu() {
      super(new MLDSASigner());
    }
    
    protected void updateEngine(byte param1Byte) throws SignatureException {
      this.bOut.write(param1Byte);
    }
    
    protected void updateEngine(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SignatureException {
      this.bOut.write(param1ArrayOfbyte, param1Int1, param1Int2);
    }
    
    protected byte[] engineSign() throws SignatureException {
      try {
        byte[] arrayOfByte = this.bOut.toByteArray();
        this.bOut.reset();
        return this.signer.generateMuSignature(arrayOfByte);
      } catch (DataLengthException dataLengthException) {
        throw new SignatureException(dataLengthException.getMessage());
      } catch (Exception exception) {
        throw new SignatureException(exception.toString());
      } 
    }
    
    protected boolean engineVerify(byte[] param1ArrayOfbyte) throws SignatureException {
      byte[] arrayOfByte = this.bOut.toByteArray();
      this.bOut.reset();
      try {
        return this.signer.verifyMuSignature(arrayOfByte, param1ArrayOfbyte);
      } catch (DataLengthException dataLengthException) {
        throw new SignatureException(dataLengthException.getMessage());
      } 
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mldsa\SignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */