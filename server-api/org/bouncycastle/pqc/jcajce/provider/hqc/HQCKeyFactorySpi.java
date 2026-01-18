package org.bouncycastle.pqc.jcajce.provider.hqc;

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
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.pqc.jcajce.provider.util.BaseKeyFactorySpi;

public class HQCKeyFactorySpi extends BaseKeyFactorySpi {
  private static final Set<ASN1ObjectIdentifier> keyOids = new HashSet<>();
  
  public HQCKeyFactorySpi() {
    super(keyOids);
  }
  
  public HQCKeyFactorySpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    super(paramASN1ObjectIdentifier);
  }
  
  public PrivateKey engineGeneratePrivate(KeySpec paramKeySpec) throws InvalidKeySpecException {
    if (paramKeySpec instanceof PKCS8EncodedKeySpec) {
      byte[] arrayOfByte = ((PKCS8EncodedKeySpec)paramKeySpec).getEncoded();
      try {
        return generatePrivate(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(arrayOfByte)));
      } catch (Exception exception) {
        throw new InvalidKeySpecException(exception.toString());
      } 
    } 
    throw new InvalidKeySpecException("Unsupported key specification: " + paramKeySpec.getClass() + ".");
  }
  
  public PublicKey engineGeneratePublic(KeySpec paramKeySpec) throws InvalidKeySpecException {
    if (paramKeySpec instanceof X509EncodedKeySpec) {
      byte[] arrayOfByte = ((X509EncodedKeySpec)paramKeySpec).getEncoded();
      try {
        return generatePublic(SubjectPublicKeyInfo.getInstance(arrayOfByte));
      } catch (Exception exception) {
        throw new InvalidKeySpecException(exception.toString());
      } 
    } 
    throw new InvalidKeySpecException("Unknown key specification: " + paramKeySpec + ".");
  }
  
  public final KeySpec engineGetKeySpec(Key paramKey, Class<?> paramClass) throws InvalidKeySpecException {
    if (paramKey instanceof BCHQCPrivateKey) {
      if (PKCS8EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new PKCS8EncodedKeySpec(paramKey.getEncoded()); 
    } else if (paramKey instanceof BCHQCPublicKey) {
      if (X509EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new X509EncodedKeySpec(paramKey.getEncoded()); 
    } else {
      throw new InvalidKeySpecException("Unsupported key type: " + paramKey.getClass() + ".");
    } 
    throw new InvalidKeySpecException("Unknown key specification: " + paramClass + ".");
  }
  
  public final Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
    if (paramKey instanceof BCHQCPrivateKey || paramKey instanceof BCHQCPublicKey)
      return paramKey; 
    throw new InvalidKeyException("Unsupported key type");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    return new BCHQCPrivateKey(paramPrivateKeyInfo);
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    return new BCHQCPublicKey(paramSubjectPublicKeyInfo);
  }
  
  static {
    keyOids.add(BCObjectIdentifiers.hqc128);
    keyOids.add(BCObjectIdentifiers.hqc192);
    keyOids.add(BCObjectIdentifiers.hqc256);
  }
  
  public static class HQC128 extends HQCKeyFactorySpi {
    public HQC128() {
      super(BCObjectIdentifiers.hqc128);
    }
  }
  
  public static class HQC192 extends HQCKeyFactorySpi {
    public HQC192() {
      super(BCObjectIdentifiers.hqc192);
    }
  }
  
  public static class HQC256 extends HQCKeyFactorySpi {
    public HQC256() {
      super(BCObjectIdentifiers.hqc256);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\hqc\HQCKeyFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */