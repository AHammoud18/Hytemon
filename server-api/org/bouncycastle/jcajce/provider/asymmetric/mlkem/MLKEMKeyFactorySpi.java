package org.bouncycastle.jcajce.provider.asymmetric.mlkem;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.Set;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.spec.MLKEMPrivateKeySpec;
import org.bouncycastle.jcajce.spec.MLKEMPublicKeySpec;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.provider.util.BaseKeyFactorySpi;
import org.bouncycastle.util.Arrays;

public class MLKEMKeyFactorySpi extends BaseKeyFactorySpi {
  private static final Set<ASN1ObjectIdentifier> keyOids = new HashSet<>();
  
  public MLKEMKeyFactorySpi() {
    super(keyOids);
  }
  
  public MLKEMKeyFactorySpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    super(paramASN1ObjectIdentifier);
  }
  
  public final KeySpec engineGetKeySpec(Key paramKey, Class<?> paramClass) throws InvalidKeySpecException {
    if (paramKey instanceof BCMLKEMPrivateKey) {
      if (PKCS8EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new PKCS8EncodedKeySpec(paramKey.getEncoded()); 
      if (MLKEMPrivateKeySpec.class.isAssignableFrom(paramClass)) {
        BCMLKEMPrivateKey bCMLKEMPrivateKey = (BCMLKEMPrivateKey)paramKey;
        byte[] arrayOfByte = bCMLKEMPrivateKey.getSeed();
        return (KeySpec)((arrayOfByte != null) ? new MLKEMPrivateKeySpec(bCMLKEMPrivateKey.getParameterSpec(), arrayOfByte) : new MLKEMPrivateKeySpec(bCMLKEMPrivateKey.getParameterSpec(), bCMLKEMPrivateKey.getPrivateData(), bCMLKEMPrivateKey.getPublicKey().getPublicData()));
      } 
      if (MLKEMPublicKeySpec.class.isAssignableFrom(paramClass)) {
        BCMLKEMPrivateKey bCMLKEMPrivateKey = (BCMLKEMPrivateKey)paramKey;
        return (KeySpec)new MLKEMPublicKeySpec(bCMLKEMPrivateKey.getParameterSpec(), bCMLKEMPrivateKey.getPublicKey().getPublicData());
      } 
    } else if (paramKey instanceof BCMLKEMPublicKey) {
      if (X509EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new X509EncodedKeySpec(paramKey.getEncoded()); 
      if (MLKEMPublicKeySpec.class.isAssignableFrom(paramClass)) {
        BCMLKEMPublicKey bCMLKEMPublicKey = (BCMLKEMPublicKey)paramKey;
        return (KeySpec)new MLKEMPublicKeySpec(bCMLKEMPublicKey.getParameterSpec(), bCMLKEMPublicKey.getPublicData());
      } 
    } else {
      throw new InvalidKeySpecException("Unsupported key type: " + paramKey.getClass() + ".");
    } 
    throw new InvalidKeySpecException("unknown key specification: " + paramClass + ".");
  }
  
  public final Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
    if (paramKey instanceof BCMLKEMPrivateKey || paramKey instanceof BCMLKEMPublicKey)
      return paramKey; 
    throw new InvalidKeyException("unsupported key type");
  }
  
  public PrivateKey engineGeneratePrivate(KeySpec paramKeySpec) throws InvalidKeySpecException {
    if (paramKeySpec instanceof MLKEMPrivateKeySpec) {
      MLKEMPrivateKeyParameters mLKEMPrivateKeyParameters;
      MLKEMPrivateKeySpec mLKEMPrivateKeySpec = (MLKEMPrivateKeySpec)paramKeySpec;
      MLKEMParameters mLKEMParameters = Utils.getParameters(mLKEMPrivateKeySpec.getParameterSpec().getName());
      if (mLKEMPrivateKeySpec.isSeed()) {
        mLKEMPrivateKeyParameters = new MLKEMPrivateKeyParameters(mLKEMParameters, mLKEMPrivateKeySpec.getSeed());
      } else {
        mLKEMPrivateKeyParameters = new MLKEMPrivateKeyParameters(mLKEMParameters, mLKEMPrivateKeySpec.getPrivateData());
        byte[] arrayOfByte = mLKEMPrivateKeySpec.getPublicData();
        if (arrayOfByte != null && !Arrays.constantTimeAreEqual(arrayOfByte, mLKEMPrivateKeyParameters.getPublicKey()))
          throw new InvalidKeySpecException("public key data does not match private key data"); 
      } 
      return (PrivateKey)new BCMLKEMPrivateKey(mLKEMPrivateKeyParameters);
    } 
    return super.engineGeneratePrivate(paramKeySpec);
  }
  
  public PublicKey engineGeneratePublic(KeySpec paramKeySpec) throws InvalidKeySpecException {
    if (paramKeySpec instanceof MLKEMPublicKeySpec) {
      MLKEMPublicKeySpec mLKEMPublicKeySpec = (MLKEMPublicKeySpec)paramKeySpec;
      return (PublicKey)new BCMLKEMPublicKey(new MLKEMPublicKeyParameters(Utils.getParameters(mLKEMPublicKeySpec.getParameterSpec().getName()), mLKEMPublicKeySpec.getPublicData()));
    } 
    return super.engineGeneratePublic(paramKeySpec);
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    return (PrivateKey)new BCMLKEMPrivateKey(paramPrivateKeyInfo);
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    return (PublicKey)new BCMLKEMPublicKey(paramSubjectPublicKeyInfo);
  }
  
  static {
    keyOids.add(NISTObjectIdentifiers.id_alg_ml_kem_512);
    keyOids.add(NISTObjectIdentifiers.id_alg_ml_kem_768);
    keyOids.add(NISTObjectIdentifiers.id_alg_ml_kem_1024);
  }
  
  public static class MLKEM1024 extends MLKEMKeyFactorySpi {
    public MLKEM1024() {
      super(NISTObjectIdentifiers.id_alg_ml_kem_1024);
    }
  }
  
  public static class MLKEM512 extends MLKEMKeyFactorySpi {
    public MLKEM512() {
      super(NISTObjectIdentifiers.id_alg_ml_kem_512);
    }
  }
  
  public static class MLKEM768 extends MLKEMKeyFactorySpi {
    public MLKEM768() {
      super(NISTObjectIdentifiers.id_alg_ml_kem_768);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mlkem\MLKEMKeyFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */