package org.bouncycastle.jcajce.provider.asymmetric.mldsa;

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
import org.bouncycastle.jcajce.spec.MLDSAPrivateKeySpec;
import org.bouncycastle.jcajce.spec.MLDSAPublicKeySpec;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.provider.util.BaseKeyFactorySpi;
import org.bouncycastle.util.Arrays;

public class MLDSAKeyFactorySpi extends BaseKeyFactorySpi {
  private static final Set<ASN1ObjectIdentifier> pureKeyOids = new HashSet<>();
  
  private static final Set<ASN1ObjectIdentifier> hashKeyOids = new HashSet<>();
  
  private final boolean isHashOnly = false;
  
  public MLDSAKeyFactorySpi(Set<ASN1ObjectIdentifier> paramSet) {
    super(paramSet);
  }
  
  public MLDSAKeyFactorySpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    super(paramASN1ObjectIdentifier);
  }
  
  public final KeySpec engineGetKeySpec(Key paramKey, Class<?> paramClass) throws InvalidKeySpecException {
    if (paramKey instanceof BCMLDSAPrivateKey) {
      if (PKCS8EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new PKCS8EncodedKeySpec(paramKey.getEncoded()); 
      if (MLDSAPrivateKeySpec.class.isAssignableFrom(paramClass)) {
        BCMLDSAPrivateKey bCMLDSAPrivateKey = (BCMLDSAPrivateKey)paramKey;
        byte[] arrayOfByte = bCMLDSAPrivateKey.getSeed();
        return (KeySpec)((arrayOfByte != null) ? new MLDSAPrivateKeySpec(bCMLDSAPrivateKey.getParameterSpec(), arrayOfByte) : new MLDSAPrivateKeySpec(bCMLDSAPrivateKey.getParameterSpec(), bCMLDSAPrivateKey.getPrivateData(), bCMLDSAPrivateKey.getPublicKey().getPublicData()));
      } 
      if (MLDSAPublicKeySpec.class.isAssignableFrom(paramClass)) {
        BCMLDSAPrivateKey bCMLDSAPrivateKey = (BCMLDSAPrivateKey)paramKey;
        return (KeySpec)new MLDSAPublicKeySpec(bCMLDSAPrivateKey.getParameterSpec(), bCMLDSAPrivateKey.getPublicKey().getPublicData());
      } 
    } else if (paramKey instanceof BCMLDSAPublicKey) {
      if (X509EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new X509EncodedKeySpec(paramKey.getEncoded()); 
      if (MLDSAPublicKeySpec.class.isAssignableFrom(paramClass)) {
        BCMLDSAPublicKey bCMLDSAPublicKey = (BCMLDSAPublicKey)paramKey;
        return (KeySpec)new MLDSAPublicKeySpec(bCMLDSAPublicKey.getParameterSpec(), bCMLDSAPublicKey.getPublicData());
      } 
    } else {
      throw new InvalidKeySpecException("unsupported key type: " + paramKey.getClass() + ".");
    } 
    throw new InvalidKeySpecException("unknown key specification: " + paramClass + ".");
  }
  
  public final Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
    if (paramKey instanceof BCMLDSAPrivateKey || paramKey instanceof BCMLDSAPublicKey)
      return paramKey; 
    throw new InvalidKeyException("unsupported key type");
  }
  
  public PrivateKey engineGeneratePrivate(KeySpec paramKeySpec) throws InvalidKeySpecException {
    if (paramKeySpec instanceof MLDSAPrivateKeySpec) {
      MLDSAPrivateKeyParameters mLDSAPrivateKeyParameters;
      MLDSAPrivateKeySpec mLDSAPrivateKeySpec = (MLDSAPrivateKeySpec)paramKeySpec;
      MLDSAParameters mLDSAParameters = Utils.getParameters(mLDSAPrivateKeySpec.getParameterSpec().getName());
      if (mLDSAPrivateKeySpec.isSeed()) {
        mLDSAPrivateKeyParameters = new MLDSAPrivateKeyParameters(mLDSAParameters, mLDSAPrivateKeySpec.getSeed());
      } else {
        mLDSAPrivateKeyParameters = new MLDSAPrivateKeyParameters(mLDSAParameters, mLDSAPrivateKeySpec.getPrivateData(), null);
        byte[] arrayOfByte = mLDSAPrivateKeySpec.getPublicData();
        if (arrayOfByte != null && !Arrays.constantTimeAreEqual(arrayOfByte, mLDSAPrivateKeyParameters.getPublicKey()))
          throw new InvalidKeySpecException("public key data does not match private key data"); 
      } 
      return (PrivateKey)new BCMLDSAPrivateKey(mLDSAPrivateKeyParameters);
    } 
    return super.engineGeneratePrivate(paramKeySpec);
  }
  
  public PublicKey engineGeneratePublic(KeySpec paramKeySpec) throws InvalidKeySpecException {
    if (paramKeySpec instanceof MLDSAPublicKeySpec) {
      MLDSAPublicKeySpec mLDSAPublicKeySpec = (MLDSAPublicKeySpec)paramKeySpec;
      return (PublicKey)new BCMLDSAPublicKey(new MLDSAPublicKeyParameters(Utils.getParameters(mLDSAPublicKeySpec.getParameterSpec().getName()), mLDSAPublicKeySpec.getPublicData()));
    } 
    return super.engineGeneratePublic(paramKeySpec);
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    BCMLDSAPrivateKey bCMLDSAPrivateKey = new BCMLDSAPrivateKey(paramPrivateKeyInfo);
    if (!this.isHashOnly || bCMLDSAPrivateKey.getAlgorithm().indexOf("WITH") > 0)
      return (PrivateKey)bCMLDSAPrivateKey; 
    MLDSAPrivateKeyParameters mLDSAPrivateKeyParameters1 = bCMLDSAPrivateKey.getKeyParams();
    MLDSAParameters mLDSAParameters = null;
    if (mLDSAPrivateKeyParameters1.getParameters().equals(MLDSAParameters.ml_dsa_44)) {
      mLDSAParameters = MLDSAParameters.ml_dsa_44_with_sha512;
    } else if (mLDSAPrivateKeyParameters1.getParameters().equals(MLDSAParameters.ml_dsa_65)) {
      mLDSAParameters = MLDSAParameters.ml_dsa_65_with_sha512;
    } else if (mLDSAPrivateKeyParameters1.getParameters().equals(MLDSAParameters.ml_dsa_87)) {
      mLDSAParameters = MLDSAParameters.ml_dsa_87_with_sha512;
    } else {
      throw new IllegalStateException("unknown ML-DSA parameters");
    } 
    MLDSAPrivateKeyParameters mLDSAPrivateKeyParameters2 = new MLDSAPrivateKeyParameters(mLDSAParameters, mLDSAPrivateKeyParameters1.getRho(), mLDSAPrivateKeyParameters1.getK(), mLDSAPrivateKeyParameters1.getTr(), mLDSAPrivateKeyParameters1.getS1(), mLDSAPrivateKeyParameters1.getS2(), mLDSAPrivateKeyParameters1.getT0(), mLDSAPrivateKeyParameters1.getT1(), mLDSAPrivateKeyParameters1.getSeed());
    return (PrivateKey)new BCMLDSAPrivateKey(mLDSAPrivateKeyParameters2);
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    return (PublicKey)new BCMLDSAPublicKey(paramSubjectPublicKeyInfo);
  }
  
  static {
    pureKeyOids.add(NISTObjectIdentifiers.id_ml_dsa_44);
    pureKeyOids.add(NISTObjectIdentifiers.id_ml_dsa_65);
    pureKeyOids.add(NISTObjectIdentifiers.id_ml_dsa_87);
    hashKeyOids.add(NISTObjectIdentifiers.id_ml_dsa_44);
    hashKeyOids.add(NISTObjectIdentifiers.id_ml_dsa_65);
    hashKeyOids.add(NISTObjectIdentifiers.id_ml_dsa_87);
    hashKeyOids.add(NISTObjectIdentifiers.id_hash_ml_dsa_44_with_sha512);
    hashKeyOids.add(NISTObjectIdentifiers.id_hash_ml_dsa_65_with_sha512);
    hashKeyOids.add(NISTObjectIdentifiers.id_hash_ml_dsa_87_with_sha512);
  }
  
  public static class Hash extends MLDSAKeyFactorySpi {
    public Hash() {
      super(MLDSAKeyFactorySpi.hashKeyOids);
    }
  }
  
  public static class HashMLDSA44 extends MLDSAKeyFactorySpi {
    public HashMLDSA44() {
      super(NISTObjectIdentifiers.id_hash_ml_dsa_44_with_sha512);
    }
  }
  
  public static class HashMLDSA65 extends MLDSAKeyFactorySpi {
    public HashMLDSA65() {
      super(NISTObjectIdentifiers.id_hash_ml_dsa_65_with_sha512);
    }
  }
  
  public static class HashMLDSA87 extends MLDSAKeyFactorySpi {
    public HashMLDSA87() {
      super(NISTObjectIdentifiers.id_hash_ml_dsa_87_with_sha512);
    }
  }
  
  public static class MLDSA44 extends MLDSAKeyFactorySpi {
    public MLDSA44() {
      super(NISTObjectIdentifiers.id_ml_dsa_44);
    }
  }
  
  public static class MLDSA65 extends MLDSAKeyFactorySpi {
    public MLDSA65() {
      super(NISTObjectIdentifiers.id_ml_dsa_65);
    }
  }
  
  public static class MLDSA87 extends MLDSAKeyFactorySpi {
    public MLDSA87() {
      super(NISTObjectIdentifiers.id_ml_dsa_87);
    }
  }
  
  public static class Pure extends MLDSAKeyFactorySpi {
    public Pure() {
      super(MLDSAKeyFactorySpi.pureKeyOids);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\mldsa\MLDSAKeyFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */