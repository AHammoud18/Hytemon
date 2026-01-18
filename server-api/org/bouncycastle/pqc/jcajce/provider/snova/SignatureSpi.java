package org.bouncycastle.pqc.jcajce.provider.snova;

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
import org.bouncycastle.pqc.crypto.snova.SnovaParameters;
import org.bouncycastle.pqc.crypto.snova.SnovaPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.snova.SnovaSigner;
import org.bouncycastle.util.Strings;

public class SignatureSpi extends Signature {
  private final ByteArrayOutputStream bOut;
  
  private final SnovaSigner signer;
  
  private SecureRandom random;
  
  private final SnovaParameters parameters;
  
  protected SignatureSpi(SnovaSigner paramSnovaSigner) {
    super("Snova");
    this.bOut = new ByteArrayOutputStream();
    this.signer = paramSnovaSigner;
    this.parameters = null;
  }
  
  protected SignatureSpi(SnovaSigner paramSnovaSigner, SnovaParameters paramSnovaParameters) {
    super(Strings.toUpperCase(paramSnovaParameters.getName()));
    this.parameters = paramSnovaParameters;
    this.bOut = new ByteArrayOutputStream();
    this.signer = paramSnovaSigner;
  }
  
  protected void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException {
    if (!(paramPublicKey instanceof BCSnovaPublicKey))
      try {
        paramPublicKey = new BCSnovaPublicKey(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
      } catch (Exception exception) {
        throw new InvalidKeyException("unknown public key passed to Snova: " + exception.getMessage());
      }  
    BCSnovaPublicKey bCSnovaPublicKey = (BCSnovaPublicKey)paramPublicKey;
    if (this.parameters != null) {
      String str = Strings.toUpperCase(this.parameters.getName());
      if (!str.equals(bCSnovaPublicKey.getAlgorithm()))
        throw new InvalidKeyException("signature configured for " + str); 
    } 
    this.signer.init(false, (CipherParameters)bCSnovaPublicKey.getKeyParams());
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
    this.random = paramSecureRandom;
    engineInitSign(paramPrivateKey);
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException {
    if (paramPrivateKey instanceof BCSnovaPrivateKey) {
      BCSnovaPrivateKey bCSnovaPrivateKey = (BCSnovaPrivateKey)paramPrivateKey;
      SnovaPrivateKeyParameters snovaPrivateKeyParameters = bCSnovaPrivateKey.getKeyParams();
      if (this.parameters != null) {
        String str = Strings.toUpperCase(this.parameters.getName());
        if (!str.equals(bCSnovaPrivateKey.getAlgorithm()))
          throw new InvalidKeyException("signature configured for " + str); 
      } 
      if (this.random != null) {
        this.signer.init(true, (CipherParameters)new ParametersWithRandom((CipherParameters)snovaPrivateKeyParameters, this.random));
      } else {
        this.signer.init(true, (CipherParameters)snovaPrivateKeyParameters);
      } 
    } else {
      throw new InvalidKeyException("unknown private key passed to Snova");
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
      super(new SnovaSigner());
    }
  }
  
  public static class SNOVA_24_5_4_ESK extends SignatureSpi {
    public SNOVA_24_5_4_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_4_ESK);
    }
  }
  
  public static class SNOVA_24_5_4_SHAKE_ESK extends SignatureSpi {
    public SNOVA_24_5_4_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_4_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_24_5_4_SHAKE_SSK extends SignatureSpi {
    public SNOVA_24_5_4_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_4_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_24_5_4_SSK extends SignatureSpi {
    public SNOVA_24_5_4_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_4_SSK);
    }
  }
  
  public static class SNOVA_24_5_5_ESK extends SignatureSpi {
    public SNOVA_24_5_5_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_5_ESK);
    }
  }
  
  public static class SNOVA_24_5_5_SHAKE_ESK extends SignatureSpi {
    public SNOVA_24_5_5_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_5_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_24_5_5_SHAKE_SSK extends SignatureSpi {
    public SNOVA_24_5_5_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_5_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_24_5_5_SSK extends SignatureSpi {
    public SNOVA_24_5_5_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_24_5_5_SSK);
    }
  }
  
  public static class SNOVA_25_8_3_ESK extends SignatureSpi {
    public SNOVA_25_8_3_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_25_8_3_ESK);
    }
  }
  
  public static class SNOVA_25_8_3_SHAKE_ESK extends SignatureSpi {
    public SNOVA_25_8_3_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_25_8_3_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_25_8_3_SHAKE_SSK extends SignatureSpi {
    public SNOVA_25_8_3_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_25_8_3_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_25_8_3_SSK extends SignatureSpi {
    public SNOVA_25_8_3_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_25_8_3_SSK);
    }
  }
  
  public static class SNOVA_29_6_5_ESK extends SignatureSpi {
    public SNOVA_29_6_5_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_29_6_5_ESK);
    }
  }
  
  public static class SNOVA_29_6_5_SHAKE_ESK extends SignatureSpi {
    public SNOVA_29_6_5_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_29_6_5_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_29_6_5_SHAKE_SSK extends SignatureSpi {
    public SNOVA_29_6_5_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_29_6_5_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_29_6_5_SSK extends SignatureSpi {
    public SNOVA_29_6_5_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_29_6_5_SSK);
    }
  }
  
  public static class SNOVA_37_17_2_ESK extends SignatureSpi {
    public SNOVA_37_17_2_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_17_2_ESK);
    }
  }
  
  public static class SNOVA_37_17_2_SHAKE_ESK extends SignatureSpi {
    public SNOVA_37_17_2_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_17_2_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_37_17_2_SHAKE_SSK extends SignatureSpi {
    public SNOVA_37_17_2_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_17_2_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_37_17_2_SSK extends SignatureSpi {
    public SNOVA_37_17_2_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_17_2_SSK);
    }
  }
  
  public static class SNOVA_37_8_4_ESK extends SignatureSpi {
    public SNOVA_37_8_4_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_8_4_ESK);
    }
  }
  
  public static class SNOVA_37_8_4_SHAKE_ESK extends SignatureSpi {
    public SNOVA_37_8_4_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_8_4_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_37_8_4_SHAKE_SSK extends SignatureSpi {
    public SNOVA_37_8_4_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_8_4_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_37_8_4_SSK extends SignatureSpi {
    public SNOVA_37_8_4_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_37_8_4_SSK);
    }
  }
  
  public static class SNOVA_49_11_3_ESK extends SignatureSpi {
    public SNOVA_49_11_3_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_49_11_3_ESK);
    }
  }
  
  public static class SNOVA_49_11_3_SHAKE_ESK extends SignatureSpi {
    public SNOVA_49_11_3_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_49_11_3_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_49_11_3_SHAKE_SSK extends SignatureSpi {
    public SNOVA_49_11_3_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_49_11_3_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_49_11_3_SSK extends SignatureSpi {
    public SNOVA_49_11_3_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_49_11_3_SSK);
    }
  }
  
  public static class SNOVA_56_25_2_ESK extends SignatureSpi {
    public SNOVA_56_25_2_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_56_25_2_ESK);
    }
  }
  
  public static class SNOVA_56_25_2_SHAKE_ESK extends SignatureSpi {
    public SNOVA_56_25_2_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_56_25_2_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_56_25_2_SHAKE_SSK extends SignatureSpi {
    public SNOVA_56_25_2_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_56_25_2_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_56_25_2_SSK extends SignatureSpi {
    public SNOVA_56_25_2_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_56_25_2_SSK);
    }
  }
  
  public static class SNOVA_60_10_4_ESK extends SignatureSpi {
    public SNOVA_60_10_4_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_60_10_4_ESK);
    }
  }
  
  public static class SNOVA_60_10_4_SHAKE_ESK extends SignatureSpi {
    public SNOVA_60_10_4_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_60_10_4_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_60_10_4_SHAKE_SSK extends SignatureSpi {
    public SNOVA_60_10_4_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_60_10_4_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_60_10_4_SSK extends SignatureSpi {
    public SNOVA_60_10_4_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_60_10_4_SSK);
    }
  }
  
  public static class SNOVA_66_15_3_ESK extends SignatureSpi {
    public SNOVA_66_15_3_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_66_15_3_ESK);
    }
  }
  
  public static class SNOVA_66_15_3_SHAKE_ESK extends SignatureSpi {
    public SNOVA_66_15_3_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_66_15_3_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_66_15_3_SHAKE_SSK extends SignatureSpi {
    public SNOVA_66_15_3_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_66_15_3_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_66_15_3_SSK extends SignatureSpi {
    public SNOVA_66_15_3_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_66_15_3_SSK);
    }
  }
  
  public static class SNOVA_75_33_2_ESK extends SignatureSpi {
    public SNOVA_75_33_2_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_75_33_2_ESK);
    }
  }
  
  public static class SNOVA_75_33_2_SHAKE_ESK extends SignatureSpi {
    public SNOVA_75_33_2_SHAKE_ESK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_75_33_2_SHAKE_ESK);
    }
  }
  
  public static class SNOVA_75_33_2_SHAKE_SSK extends SignatureSpi {
    public SNOVA_75_33_2_SHAKE_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_75_33_2_SHAKE_SSK);
    }
  }
  
  public static class SNOVA_75_33_2_SSK extends SignatureSpi {
    public SNOVA_75_33_2_SSK() {
      super(new SnovaSigner(), SnovaParameters.SNOVA_75_33_2_SSK);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\snova\SignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */