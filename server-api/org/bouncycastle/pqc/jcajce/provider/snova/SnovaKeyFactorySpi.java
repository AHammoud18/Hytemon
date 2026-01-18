package org.bouncycastle.pqc.jcajce.provider.snova;

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

public class SnovaKeyFactorySpi extends BaseKeyFactorySpi {
  private static final Set<ASN1ObjectIdentifier> keyOids = new HashSet<>();
  
  public SnovaKeyFactorySpi() {
    super(keyOids);
  }
  
  public SnovaKeyFactorySpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    super(paramASN1ObjectIdentifier);
  }
  
  public final KeySpec engineGetKeySpec(Key paramKey, Class<?> paramClass) throws InvalidKeySpecException {
    if (paramKey instanceof BCSnovaPrivateKey) {
      if (PKCS8EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new PKCS8EncodedKeySpec(paramKey.getEncoded()); 
    } else if (paramKey instanceof BCSnovaPublicKey) {
      if (X509EncodedKeySpec.class.isAssignableFrom(paramClass))
        return new X509EncodedKeySpec(paramKey.getEncoded()); 
    } else {
      throw new InvalidKeySpecException("Unsupported key type: " + paramKey.getClass() + ".");
    } 
    throw new InvalidKeySpecException("Unknown key specification: " + paramClass + ".");
  }
  
  public final Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
    if (paramKey instanceof BCSnovaPrivateKey || paramKey instanceof BCSnovaPublicKey)
      return paramKey; 
    throw new InvalidKeyException("Unsupported key type");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    return new BCSnovaPrivateKey(paramPrivateKeyInfo);
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    return new BCSnovaPublicKey(paramSubjectPublicKeyInfo);
  }
  
  static {
    keyOids.add(BCObjectIdentifiers.snova_24_5_4_ssk);
    keyOids.add(BCObjectIdentifiers.snova_24_5_4_esk);
    keyOids.add(BCObjectIdentifiers.snova_24_5_4_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_24_5_4_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_24_5_5_ssk);
    keyOids.add(BCObjectIdentifiers.snova_24_5_5_esk);
    keyOids.add(BCObjectIdentifiers.snova_24_5_5_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_24_5_5_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_25_8_3_ssk);
    keyOids.add(BCObjectIdentifiers.snova_25_8_3_esk);
    keyOids.add(BCObjectIdentifiers.snova_25_8_3_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_25_8_3_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_29_6_5_ssk);
    keyOids.add(BCObjectIdentifiers.snova_29_6_5_esk);
    keyOids.add(BCObjectIdentifiers.snova_29_6_5_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_29_6_5_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_37_8_4_ssk);
    keyOids.add(BCObjectIdentifiers.snova_37_8_4_esk);
    keyOids.add(BCObjectIdentifiers.snova_37_8_4_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_37_8_4_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_37_17_2_ssk);
    keyOids.add(BCObjectIdentifiers.snova_37_17_2_esk);
    keyOids.add(BCObjectIdentifiers.snova_37_17_2_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_37_17_2_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_49_11_3_ssk);
    keyOids.add(BCObjectIdentifiers.snova_49_11_3_esk);
    keyOids.add(BCObjectIdentifiers.snova_49_11_3_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_49_11_3_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_56_25_2_ssk);
    keyOids.add(BCObjectIdentifiers.snova_56_25_2_esk);
    keyOids.add(BCObjectIdentifiers.snova_56_25_2_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_56_25_2_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_60_10_4_ssk);
    keyOids.add(BCObjectIdentifiers.snova_60_10_4_esk);
    keyOids.add(BCObjectIdentifiers.snova_60_10_4_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_60_10_4_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_66_15_3_ssk);
    keyOids.add(BCObjectIdentifiers.snova_66_15_3_esk);
    keyOids.add(BCObjectIdentifiers.snova_66_15_3_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_66_15_3_shake_esk);
    keyOids.add(BCObjectIdentifiers.snova_75_33_2_ssk);
    keyOids.add(BCObjectIdentifiers.snova_75_33_2_esk);
    keyOids.add(BCObjectIdentifiers.snova_75_33_2_shake_ssk);
    keyOids.add(BCObjectIdentifiers.snova_75_33_2_shake_esk);
  }
  
  public static class SNOVA_24_5_4_ESK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_4_ESK() {
      super(BCObjectIdentifiers.snova_24_5_4_esk);
    }
  }
  
  public static class SNOVA_24_5_4_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_4_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_24_5_4_shake_esk);
    }
  }
  
  public static class SNOVA_24_5_4_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_4_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_24_5_4_shake_ssk);
    }
  }
  
  public static class SNOVA_24_5_4_SSK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_4_SSK() {
      super(BCObjectIdentifiers.snova_24_5_4_ssk);
    }
  }
  
  public static class SNOVA_24_5_5_ESK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_5_ESK() {
      super(BCObjectIdentifiers.snova_24_5_5_esk);
    }
  }
  
  public static class SNOVA_24_5_5_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_5_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_24_5_5_shake_esk);
    }
  }
  
  public static class SNOVA_24_5_5_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_5_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_24_5_5_shake_ssk);
    }
  }
  
  public static class SNOVA_24_5_5_SSK extends SnovaKeyFactorySpi {
    public SNOVA_24_5_5_SSK() {
      super(BCObjectIdentifiers.snova_24_5_5_ssk);
    }
  }
  
  public static class SNOVA_25_8_3_ESK extends SnovaKeyFactorySpi {
    public SNOVA_25_8_3_ESK() {
      super(BCObjectIdentifiers.snova_25_8_3_esk);
    }
  }
  
  public static class SNOVA_25_8_3_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_25_8_3_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_25_8_3_shake_esk);
    }
  }
  
  public static class SNOVA_25_8_3_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_25_8_3_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_25_8_3_shake_ssk);
    }
  }
  
  public static class SNOVA_25_8_3_SSK extends SnovaKeyFactorySpi {
    public SNOVA_25_8_3_SSK() {
      super(BCObjectIdentifiers.snova_25_8_3_ssk);
    }
  }
  
  public static class SNOVA_29_6_5_ESK extends SnovaKeyFactorySpi {
    public SNOVA_29_6_5_ESK() {
      super(BCObjectIdentifiers.snova_29_6_5_esk);
    }
  }
  
  public static class SNOVA_29_6_5_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_29_6_5_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_29_6_5_shake_esk);
    }
  }
  
  public static class SNOVA_29_6_5_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_29_6_5_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_29_6_5_shake_ssk);
    }
  }
  
  public static class SNOVA_29_6_5_SSK extends SnovaKeyFactorySpi {
    public SNOVA_29_6_5_SSK() {
      super(BCObjectIdentifiers.snova_29_6_5_ssk);
    }
  }
  
  public static class SNOVA_37_17_2_ESK extends SnovaKeyFactorySpi {
    public SNOVA_37_17_2_ESK() {
      super(BCObjectIdentifiers.snova_37_17_2_esk);
    }
  }
  
  public static class SNOVA_37_17_2_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_37_17_2_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_37_17_2_shake_esk);
    }
  }
  
  public static class SNOVA_37_17_2_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_37_17_2_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_37_17_2_shake_ssk);
    }
  }
  
  public static class SNOVA_37_17_2_SSK extends SnovaKeyFactorySpi {
    public SNOVA_37_17_2_SSK() {
      super(BCObjectIdentifiers.snova_37_17_2_ssk);
    }
  }
  
  public static class SNOVA_37_8_4_ESK extends SnovaKeyFactorySpi {
    public SNOVA_37_8_4_ESK() {
      super(BCObjectIdentifiers.snova_37_8_4_esk);
    }
  }
  
  public static class SNOVA_37_8_4_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_37_8_4_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_37_8_4_shake_esk);
    }
  }
  
  public static class SNOVA_37_8_4_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_37_8_4_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_37_8_4_shake_ssk);
    }
  }
  
  public static class SNOVA_37_8_4_SSK extends SnovaKeyFactorySpi {
    public SNOVA_37_8_4_SSK() {
      super(BCObjectIdentifiers.snova_37_8_4_ssk);
    }
  }
  
  public static class SNOVA_49_11_3_ESK extends SnovaKeyFactorySpi {
    public SNOVA_49_11_3_ESK() {
      super(BCObjectIdentifiers.snova_49_11_3_esk);
    }
  }
  
  public static class SNOVA_49_11_3_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_49_11_3_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_49_11_3_shake_esk);
    }
  }
  
  public static class SNOVA_49_11_3_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_49_11_3_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_49_11_3_shake_ssk);
    }
  }
  
  public static class SNOVA_49_11_3_SSK extends SnovaKeyFactorySpi {
    public SNOVA_49_11_3_SSK() {
      super(BCObjectIdentifiers.snova_49_11_3_ssk);
    }
  }
  
  public static class SNOVA_56_25_2_ESK extends SnovaKeyFactorySpi {
    public SNOVA_56_25_2_ESK() {
      super(BCObjectIdentifiers.snova_56_25_2_esk);
    }
  }
  
  public static class SNOVA_56_25_2_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_56_25_2_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_56_25_2_shake_esk);
    }
  }
  
  public static class SNOVA_56_25_2_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_56_25_2_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_56_25_2_shake_ssk);
    }
  }
  
  public static class SNOVA_56_25_2_SSK extends SnovaKeyFactorySpi {
    public SNOVA_56_25_2_SSK() {
      super(BCObjectIdentifiers.snova_56_25_2_ssk);
    }
  }
  
  public static class SNOVA_60_10_4_ESK extends SnovaKeyFactorySpi {
    public SNOVA_60_10_4_ESK() {
      super(BCObjectIdentifiers.snova_60_10_4_esk);
    }
  }
  
  public static class SNOVA_60_10_4_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_60_10_4_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_60_10_4_shake_esk);
    }
  }
  
  public static class SNOVA_60_10_4_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_60_10_4_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_60_10_4_shake_ssk);
    }
  }
  
  public static class SNOVA_60_10_4_SSK extends SnovaKeyFactorySpi {
    public SNOVA_60_10_4_SSK() {
      super(BCObjectIdentifiers.snova_60_10_4_ssk);
    }
  }
  
  public static class SNOVA_66_15_3_ESK extends SnovaKeyFactorySpi {
    public SNOVA_66_15_3_ESK() {
      super(BCObjectIdentifiers.snova_66_15_3_esk);
    }
  }
  
  public static class SNOVA_66_15_3_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_66_15_3_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_66_15_3_shake_esk);
    }
  }
  
  public static class SNOVA_66_15_3_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_66_15_3_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_66_15_3_shake_ssk);
    }
  }
  
  public static class SNOVA_66_15_3_SSK extends SnovaKeyFactorySpi {
    public SNOVA_66_15_3_SSK() {
      super(BCObjectIdentifiers.snova_66_15_3_ssk);
    }
  }
  
  public static class SNOVA_75_33_2_ESK extends SnovaKeyFactorySpi {
    public SNOVA_75_33_2_ESK() {
      super(BCObjectIdentifiers.snova_75_33_2_esk);
    }
  }
  
  public static class SNOVA_75_33_2_SHAKE_ESK extends SnovaKeyFactorySpi {
    public SNOVA_75_33_2_SHAKE_ESK() {
      super(BCObjectIdentifiers.snova_75_33_2_shake_esk);
    }
  }
  
  public static class SNOVA_75_33_2_SHAKE_SSK extends SnovaKeyFactorySpi {
    public SNOVA_75_33_2_SHAKE_SSK() {
      super(BCObjectIdentifiers.snova_75_33_2_shake_ssk);
    }
  }
  
  public static class SNOVA_75_33_2_SSK extends SnovaKeyFactorySpi {
    public SNOVA_75_33_2_SSK() {
      super(BCObjectIdentifiers.snova_75_33_2_ssk);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provider\snova\SnovaKeyFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */