package org.bouncycastle.pqc.jcajce.provider.mayo;

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
import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.pqc.jcajce.provider.util.BaseKeyFactorySpi;

public class MayoKeyFactorySpi extends BaseKeyFactorySpi {
  private static final Set<ASN1ObjectIdentifier> keyOids = new HashSet<>();
  
  public MayoKeyFactorySpi() {
    super(keyOids);
  }
  
  public MayoKeyFactorySpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    super(paramASN1ObjectIdentifier);
  }
  
  public final KeySpec engineGetKeySpec(Key paramKey, Class<?> paramClass) throws InvalidKeySpecException {
    if (paramKey instanceof BCMayoPrivateKey) {
      if (PKCS8EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new PKCS8EncodedKeySpec(paramKey.getEncoded()); 
    } else if (paramKey instanceof BCMayoPublicKey) {
      if (X509EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new X509EncodedKeySpec(paramKey.getEncoded()); 
    } else {
      throw new InvalidKeySpecException("Unsupported key type: " + paramKey.getClass() + ".");
    } 
    throw new InvalidKeySpecException("Unknown key specification: " + paramClass + ".");
  }
  
  public final Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
    if (paramKey instanceof BCMayoPrivateKey || paramKey instanceof BCMayoPublicKey)
      return paramKey; 
    throw new InvalidKeyException("Unsupported key type");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    return new BCMayoPrivateKey(paramPrivateKeyInfo);
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    return new BCMayoPublicKey(paramSubjectPublicKeyInfo);
  }
  
  static {
    keyOids.add(BCObjectIdentifiers.mayo1);
    keyOids.add(BCObjectIdentifiers.mayo2);
    keyOids.add(BCObjectIdentifiers.mayo3);
    keyOids.add(BCObjectIdentifiers.mayo5);
  }
  
  public static class Mayo1 extends MayoKeyFactorySpi {
    public Mayo1() {
      super(BCObjectIdentifiers.mayo1);
    }
  }
  
  public static class Mayo2 extends MayoKeyFactorySpi {
    public Mayo2() {
      super(BCObjectIdentifiers.mayo2);
    }
  }
  
  public static class Mayo3 extends MayoKeyFactorySpi {
    public Mayo3() {
      super(BCObjectIdentifiers.mayo3);
    }
  }
  
  public static class Mayo5 extends MayoKeyFactorySpi {
    public Mayo5() {
      super(BCObjectIdentifiers.mayo5);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\mayo\MayoKeyFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */