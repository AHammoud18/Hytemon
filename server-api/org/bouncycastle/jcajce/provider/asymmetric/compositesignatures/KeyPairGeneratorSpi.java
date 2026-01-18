package org.bouncycastle.jcajce.provider.asymmetric.compositesignatures;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyPairGeneratorSpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.internal.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.CompositePublicKey;

public class KeyPairGeneratorSpi extends KeyPairGeneratorSpi {
  private final ASN1ObjectIdentifier algorithm;
  
  private final KeyPairGenerator[] generators;
  
  private SecureRandom secureRandom;
  
  private boolean parametersInitialized = false;
  
  KeyPairGeneratorSpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    this.algorithm = paramASN1ObjectIdentifier;
    String[] arrayOfString = CompositeIndex.getPairing(paramASN1ObjectIdentifier);
    AlgorithmParameterSpec[] arrayOfAlgorithmParameterSpec = CompositeIndex.getKeyPairSpecs(paramASN1ObjectIdentifier);
    this.generators = new KeyPairGenerator[arrayOfString.length];
    for (byte b = 0; b != arrayOfString.length; b++) {
      try {
        this.generators[b] = KeyPairGenerator.getInstance(CompositeIndex.getBaseName(arrayOfString[b]), "BC");
        AlgorithmParameterSpec algorithmParameterSpec = arrayOfAlgorithmParameterSpec[b];
        if (algorithmParameterSpec != null)
          this.generators[b].initialize(algorithmParameterSpec); 
      } catch (Exception exception) {
        throw new IllegalStateException("unable to create base generator: " + exception.getMessage());
      } 
    } 
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom) {
    throw new IllegalArgumentException("use AlgorithmParameterSpec");
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
    if (paramAlgorithmParameterSpec != null)
      throw new IllegalArgumentException("Use initialize only for custom SecureRandom. AlgorithmParameterSpec must be null because it is determined by algorithm name."); 
    AlgorithmParameterSpec[] arrayOfAlgorithmParameterSpec = CompositeIndex.getKeyPairSpecs(this.algorithm);
    for (byte b = 0; b != arrayOfAlgorithmParameterSpec.length; b++) {
      AlgorithmParameterSpec algorithmParameterSpec = arrayOfAlgorithmParameterSpec[b];
      if (algorithmParameterSpec != null)
        this.generators[b].initialize(algorithmParameterSpec, paramSecureRandom); 
    } 
  }
  
  public KeyPair generateKeyPair() {
    return getCompositeKeyPair();
  }
  
  private KeyPair getCompositeKeyPair() {
    PublicKey[] arrayOfPublicKey = new PublicKey[this.generators.length];
    PrivateKey[] arrayOfPrivateKey = new PrivateKey[this.generators.length];
    for (byte b = 0; b < this.generators.length; b++) {
      KeyPair keyPair = this.generators[b].generateKeyPair();
      arrayOfPublicKey[b] = keyPair.getPublic();
      arrayOfPrivateKey[b] = keyPair.getPrivate();
    } 
    CompositePublicKey compositePublicKey = new CompositePublicKey(this.algorithm, arrayOfPublicKey);
    CompositePrivateKey compositePrivateKey = new CompositePrivateKey(this.algorithm, arrayOfPrivateKey);
    return new KeyPair((PublicKey)compositePublicKey, (PrivateKey)compositePrivateKey);
  }
  
  public static final class MLDSA44_ECDSA_P256_SHA256 extends KeyPairGeneratorSpi {
    public MLDSA44_ECDSA_P256_SHA256() {
      super(IANAObjectIdentifiers.id_MLDSA44_ECDSA_P256_SHA256);
    }
  }
  
  public static final class MLDSA44_Ed25519_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA44_Ed25519_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA44_Ed25519_SHA512);
    }
  }
  
  public static final class MLDSA44_RSA2048_PKCS15_SHA256 extends KeyPairGeneratorSpi {
    public MLDSA44_RSA2048_PKCS15_SHA256() {
      super(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PKCS15_SHA256);
    }
  }
  
  public static final class MLDSA44_RSA2048_PSS_SHA256 extends KeyPairGeneratorSpi {
    public MLDSA44_RSA2048_PSS_SHA256() {
      super(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PSS_SHA256);
    }
  }
  
  public static final class MLDSA65_ECDSA_P256_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_ECDSA_P256_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P256_SHA512);
    }
  }
  
  public static final class MLDSA65_ECDSA_P384_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_ECDSA_P384_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P384_SHA512);
    }
  }
  
  public static final class MLDSA65_ECDSA_brainpoolP256r1_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_ECDSA_brainpoolP256r1_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_brainpoolP256r1_SHA512);
    }
  }
  
  public static final class MLDSA65_Ed25519_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_Ed25519_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_Ed25519_SHA512);
    }
  }
  
  public static final class MLDSA65_RSA3072_PKCS15_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_RSA3072_PKCS15_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PKCS15_SHA512);
    }
  }
  
  public static final class MLDSA65_RSA3072_PSS_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_RSA3072_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PSS_SHA512);
    }
  }
  
  public static final class MLDSA65_RSA4096_PKCS15_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_RSA4096_PKCS15_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PKCS15_SHA512);
    }
  }
  
  public static final class MLDSA65_RSA4096_PSS_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA65_RSA4096_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PSS_SHA512);
    }
  }
  
  public static final class MLDSA87_ECDSA_P384_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA87_ECDSA_P384_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P384_SHA512);
    }
  }
  
  public static final class MLDSA87_ECDSA_P521_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA87_ECDSA_P521_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P521_SHA512);
    }
  }
  
  public static final class MLDSA87_ECDSA_brainpoolP384r1_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA87_ECDSA_brainpoolP384r1_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_brainpoolP384r1_SHA512);
    }
  }
  
  public static final class MLDSA87_Ed448_SHAKE256 extends KeyPairGeneratorSpi {
    public MLDSA87_Ed448_SHAKE256() {
      super(IANAObjectIdentifiers.id_MLDSA87_Ed448_SHAKE256);
    }
  }
  
  public static final class MLDSA87_RSA3072_PSS_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA87_RSA3072_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_RSA3072_PSS_SHA512);
    }
  }
  
  public static final class MLDSA87_RSA4096_PSS_SHA512 extends KeyPairGeneratorSpi {
    public MLDSA87_RSA4096_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_RSA4096_PSS_SHA512);
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\compositesignatures\KeyPairGeneratorSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */