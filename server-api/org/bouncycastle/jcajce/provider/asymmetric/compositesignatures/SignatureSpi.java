package org.bouncycastle.jcajce.provider.asymmetric.compositesignatures;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.internal.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.CompositePublicKey;
import org.bouncycastle.jcajce.spec.CompositeSignatureSpec;
import org.bouncycastle.jcajce.spec.ContextParameterSpec;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.SpecUtil;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Exceptions;
import org.bouncycastle.util.encoders.Hex;

public class SignatureSpi extends SignatureSpi {
  private static final byte[] prefix = Hex.decode("436f6d706f73697465416c676f726974686d5369676e61747572657332303235");
  
  private static final Map<String, String> canonicalNames = new HashMap<>();
  
  private static final HashMap<ASN1ObjectIdentifier, byte[]> domainSeparators = (HashMap)new LinkedHashMap<>();
  
  private static final HashMap<ASN1ObjectIdentifier, AlgorithmParameterSpec> algorithmsParameterSpecs = new HashMap<>();
  
  private static final String ML_DSA_44 = "ML-DSA-44";
  
  private static final String ML_DSA_65 = "ML-DSA-65";
  
  private static final String ML_DSA_87 = "ML-DSA-87";
  
  private final SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
  
  private Key compositeKey;
  
  private final boolean isPrehash;
  
  private ASN1ObjectIdentifier algorithm;
  
  private String[] algs;
  
  private Signature[] componentSignatures;
  
  private byte[] domain;
  
  private Digest baseDigest;
  
  private JcaJceHelper helper = (JcaJceHelper)new BCJcaJceHelper();
  
  private Digest preHashDigest;
  
  private ContextParameterSpec contextSpec;
  
  private AlgorithmParameters engineParams = null;
  
  private boolean unprimed = true;
  
  SignatureSpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier, Digest paramDigest) {
    this(paramASN1ObjectIdentifier, paramDigest, false);
  }
  
  SignatureSpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier, Digest paramDigest, boolean paramBoolean) {
    this.algorithm = paramASN1ObjectIdentifier;
    this.isPrehash = paramBoolean;
    if (paramASN1ObjectIdentifier != null) {
      this.baseDigest = paramDigest;
      this.preHashDigest = paramBoolean ? new NullDigest(paramDigest.getDigestSize()) : paramDigest;
      this.domain = domainSeparators.get(paramASN1ObjectIdentifier);
      this.algs = CompositeIndex.getPairing(paramASN1ObjectIdentifier);
      this.componentSignatures = new Signature[this.algs.length];
    } 
  }
  
  protected void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException {
    if (!(paramPublicKey instanceof CompositePublicKey))
      throw new InvalidKeyException("public key is not composite"); 
    this.compositeKey = paramPublicKey;
    CompositePublicKey compositePublicKey = (CompositePublicKey)this.compositeKey;
    if (this.algorithm != null) {
      if (!compositePublicKey.getAlgorithmIdentifier().getAlgorithm().equals((ASN1Primitive)this.algorithm))
        throw new InvalidKeyException("provided composite public key cannot be used with the composite signature algorithm"); 
    } else {
      ASN1ObjectIdentifier aSN1ObjectIdentifier = SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()).getAlgorithm().getAlgorithm();
      this.algorithm = aSN1ObjectIdentifier;
      this.baseDigest = CompositeIndex.getDigest(aSN1ObjectIdentifier);
      this.preHashDigest = this.isPrehash ? new NullDigest(this.baseDigest.getDigestSize()) : this.baseDigest;
      this.domain = domainSeparators.get(aSN1ObjectIdentifier);
      this.algs = CompositeIndex.getPairing(aSN1ObjectIdentifier);
      this.componentSignatures = new Signature[this.algs.length];
    } 
    createComponentSignatures(compositePublicKey.getPublicKeys(), compositePublicKey.getProviders());
    sigInitVerify();
  }
  
  private void sigInitVerify() throws InvalidKeyException {
    CompositePublicKey compositePublicKey = (CompositePublicKey)this.compositeKey;
    for (byte b = 0; b < this.componentSignatures.length; b++)
      this.componentSignatures[b].initVerify(compositePublicKey.getPublicKeys().get(b)); 
    this.unprimed = true;
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException {
    if (!(paramPrivateKey instanceof CompositePrivateKey))
      throw new InvalidKeyException("Private key is not composite."); 
    this.compositeKey = paramPrivateKey;
    CompositePrivateKey compositePrivateKey = (CompositePrivateKey)paramPrivateKey;
    if (this.algorithm != null) {
      if (!compositePrivateKey.getAlgorithmIdentifier().getAlgorithm().equals((ASN1Primitive)this.algorithm))
        throw new InvalidKeyException("provided composite public key cannot be used with the composite signature algorithm"); 
    } else {
      ASN1ObjectIdentifier aSN1ObjectIdentifier = compositePrivateKey.getAlgorithmIdentifier().getAlgorithm();
      this.algorithm = aSN1ObjectIdentifier;
      this.baseDigest = CompositeIndex.getDigest(aSN1ObjectIdentifier);
      this.preHashDigest = this.isPrehash ? new NullDigest(this.baseDigest.getDigestSize()) : this.baseDigest;
      this.domain = domainSeparators.get(aSN1ObjectIdentifier);
      this.algs = CompositeIndex.getPairing(aSN1ObjectIdentifier);
      this.componentSignatures = new Signature[this.algs.length];
    } 
    createComponentSignatures(compositePrivateKey.getPrivateKeys(), compositePrivateKey.getProviders());
    sigInitSign();
  }
  
  private void createComponentSignatures(List paramList, List<Provider> paramList1) {
    try {
      if (paramList1 == null) {
        for (byte b = 0; b != this.componentSignatures.length; b++)
          this.componentSignatures[b] = getDefaultSignature(this.algs[b], paramList.get(b)); 
      } else {
        for (byte b = 0; b != this.componentSignatures.length; b++) {
          Provider provider = paramList1.get(b);
          if (provider == null) {
            this.componentSignatures[b] = getDefaultSignature(this.algs[b], paramList.get(b));
          } else {
            this.componentSignatures[b] = Signature.getInstance(this.algs[b], paramList1.get(b));
          } 
        } 
      } 
    } catch (GeneralSecurityException generalSecurityException) {
      throw Exceptions.illegalStateException(generalSecurityException.getMessage(), generalSecurityException);
    } 
  }
  
  private Signature getDefaultSignature(String paramString, Object paramObject) throws NoSuchAlgorithmException, NoSuchProviderException {
    return (paramObject instanceof org.bouncycastle.jcajce.interfaces.BCKey) ? this.helper.createSignature(paramString) : Signature.getInstance(paramString);
  }
  
  private void sigInitSign() throws InvalidKeyException {
    CompositePrivateKey compositePrivateKey = (CompositePrivateKey)this.compositeKey;
    for (byte b = 0; b < this.componentSignatures.length; b++)
      this.componentSignatures[b].initSign(compositePrivateKey.getPrivateKeys().get(b)); 
    this.unprimed = true;
  }
  
  private void baseSigInit() throws SignatureException {
    try {
      this.componentSignatures[0].setParameter((AlgorithmParameterSpec)new ContextParameterSpec(this.domain));
      AlgorithmParameterSpec algorithmParameterSpec = algorithmsParameterSpecs.get(this.algorithm);
      if (algorithmParameterSpec != null)
        this.componentSignatures[1].setParameter(algorithmParameterSpec); 
    } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
      throw new IllegalStateException("unable to set context on ML-DSA");
    } 
    this.unprimed = false;
  }
  
  protected void engineUpdate(byte paramByte) throws SignatureException {
    if (this.unprimed)
      baseSigInit(); 
    if (this.preHashDigest != null) {
      this.preHashDigest.update(paramByte);
    } else {
      for (byte b = 0; b < this.componentSignatures.length; b++) {
        Signature signature = this.componentSignatures[b];
        signature.update(paramByte);
      } 
    } 
  }
  
  protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
    if (this.unprimed)
      baseSigInit(); 
    if (this.preHashDigest != null) {
      this.preHashDigest.update(paramArrayOfbyte, paramInt1, paramInt2);
    } else {
      for (byte b = 0; b < this.componentSignatures.length; b++) {
        Signature signature = this.componentSignatures[b];
        signature.update(paramArrayOfbyte, paramInt1, paramInt2);
      } 
    } 
  }
  
  protected byte[] engineSign() throws SignatureException {
    byte[] arrayOfByte1 = new byte[32];
    this.random.nextBytes(arrayOfByte1);
    if (this.preHashDigest != null)
      processPreHashedMessage(null); 
    byte[] arrayOfByte2 = this.componentSignatures[0].sign();
    byte[] arrayOfByte3 = this.componentSignatures[1].sign();
    byte[] arrayOfByte4 = new byte[arrayOfByte2.length + arrayOfByte3.length];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, arrayOfByte2.length);
    System.arraycopy(arrayOfByte3, 0, arrayOfByte4, arrayOfByte2.length, arrayOfByte3.length);
    return arrayOfByte4;
  }
  
  private void processPreHashedMessage(byte[] paramArrayOfbyte) throws SignatureException {
    byte[] arrayOfByte = new byte[this.baseDigest.getDigestSize()];
    try {
      this.preHashDigest.doFinal(arrayOfByte, 0);
    } catch (IllegalStateException illegalStateException) {
      throw new SignatureException(illegalStateException.getMessage());
    } 
    for (byte b = 0; b < this.componentSignatures.length; b++) {
      Signature signature = this.componentSignatures[b];
      signature.update(prefix);
      signature.update(this.domain);
      if (this.contextSpec == null) {
        signature.update((byte)0);
      } else {
        byte[] arrayOfByte1 = this.contextSpec.getContext();
        signature.update((byte)arrayOfByte1.length);
        signature.update(arrayOfByte1);
      } 
      if (paramArrayOfbyte != null)
        signature.update(paramArrayOfbyte, 0, paramArrayOfbyte.length); 
      signature.update(arrayOfByte, 0, arrayOfByte.length);
    } 
  }
  
  public static byte[][] splitCompositeSignature(byte[] paramArrayOfbyte, int paramInt) {
    byte[] arrayOfByte1 = new byte[paramInt];
    byte[] arrayOfByte2 = new byte[paramArrayOfbyte.length - paramInt];
    System.arraycopy(paramArrayOfbyte, 0, arrayOfByte1, 0, paramInt);
    System.arraycopy(paramArrayOfbyte, paramInt, arrayOfByte2, 0, arrayOfByte2.length);
    return new byte[][] { arrayOfByte1, arrayOfByte2 };
  }
  
  protected boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException {
    char c = Character.MIN_VALUE;
    if (this.algs[0].indexOf("44") > 0) {
      c = 'ॴ';
    } else if (this.algs[0].indexOf("65") > 0) {
      c = '೭';
    } else if (this.algs[0].indexOf("87") > 0) {
      c = 'ሓ';
    } 
    byte[][] arrayOfByte = splitCompositeSignature(paramArrayOfbyte, c);
    if (this.preHashDigest != null)
      processPreHashedMessage(null); 
    boolean bool = false;
    for (byte b = 0; b < this.componentSignatures.length; b++) {
      if (!this.componentSignatures[b].verify(arrayOfByte[b]))
        bool = true; 
    } 
    return !bool;
  }
  
  protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    if (!this.unprimed)
      throw new InvalidAlgorithmParameterException("attempt to set parameter after update"); 
    if (paramAlgorithmParameterSpec instanceof ContextParameterSpec) {
      this.contextSpec = (ContextParameterSpec)paramAlgorithmParameterSpec;
      try {
        if (this.compositeKey instanceof PublicKey) {
          sigInitVerify();
        } else {
          sigInitSign();
        } 
      } catch (InvalidKeyException invalidKeyException) {
        throw new InvalidAlgorithmParameterException("keys invalid on reset: " + invalidKeyException.getMessage(), invalidKeyException);
      } 
    } else if (paramAlgorithmParameterSpec instanceof CompositeSignatureSpec) {
      CompositeSignatureSpec compositeSignatureSpec = (CompositeSignatureSpec)paramAlgorithmParameterSpec;
      if (compositeSignatureSpec.isPrehashMode()) {
        this.preHashDigest = new NullDigest(this.baseDigest.getDigestSize());
      } else {
        this.preHashDigest = this.baseDigest;
      } 
      AlgorithmParameterSpec algorithmParameterSpec = compositeSignatureSpec.getSecondarySpec();
      if (algorithmParameterSpec == null || algorithmParameterSpec instanceof ContextParameterSpec) {
        this.contextSpec = (ContextParameterSpec)compositeSignatureSpec.getSecondarySpec();
      } else {
        byte[] arrayOfByte = SpecUtil.getContextFrom(algorithmParameterSpec);
        if (arrayOfByte != null) {
          this.contextSpec = new ContextParameterSpec(arrayOfByte);
        } else {
          throw new InvalidAlgorithmParameterException("unknown parameterSpec passed to composite signature");
        } 
      } 
    } else {
      byte[] arrayOfByte = SpecUtil.getContextFrom(paramAlgorithmParameterSpec);
      if (arrayOfByte != null) {
        this.contextSpec = new ContextParameterSpec(arrayOfByte);
        try {
          if (this.compositeKey instanceof PublicKey) {
            sigInitVerify();
          } else {
            sigInitSign();
          } 
        } catch (InvalidKeyException invalidKeyException) {
          throw new InvalidAlgorithmParameterException("keys invalid on reset: " + invalidKeyException.getMessage(), invalidKeyException);
        } 
      } 
      throw new InvalidAlgorithmParameterException("unknown parameterSpec passed to composite signature");
    } 
  }
  
  private String getCanonicalName(String paramString) {
    String str = canonicalNames.get(paramString);
    return (str != null) ? str : paramString;
  }
  
  protected void engineSetParameter(String paramString, Object paramObject) throws InvalidParameterException {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  protected Object engineGetParameter(String paramString) throws InvalidParameterException {
    throw new UnsupportedOperationException("engineGetParameter unsupported");
  }
  
  protected final AlgorithmParameters engineGetParameters() {
    if (this.engineParams == null && this.contextSpec != null)
      try {
        this.engineParams = this.helper.createAlgorithmParameters("CONTEXT");
        this.engineParams.init((AlgorithmParameterSpec)this.contextSpec);
      } catch (Exception exception) {
        throw Exceptions.illegalStateException(exception.toString(), exception);
      }  
    return this.engineParams;
  }
  
  static {
    canonicalNames.put("MLDSA44", "ML-DSA-44");
    canonicalNames.put("MLDSA65", "ML-DSA-65");
    canonicalNames.put("MLDSA87", "ML-DSA-87");
    canonicalNames.put(NISTObjectIdentifiers.id_ml_dsa_44.getId(), "ML-DSA-44");
    canonicalNames.put(NISTObjectIdentifiers.id_ml_dsa_65.getId(), "ML-DSA-65");
    canonicalNames.put(NISTObjectIdentifiers.id_ml_dsa_87.getId(), "ML-DSA-87");
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PSS_SHA256, Hex.decode("434f4d505349472d4d4c44534134342d525341323034382d5053532d534841323536"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PKCS15_SHA256, Hex.decode("434f4d505349472d4d4c44534134342d525341323034382d504b435331352d534841323536"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA44_Ed25519_SHA512, Hex.decode("434f4d505349472d4d4c44534134342d456432353531392d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA44_ECDSA_P256_SHA256, Hex.decode("434f4d505349472d4d4c44534134342d45434453412d503235362d534841323536"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PSS_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d525341333037322d5053532d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PKCS15_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d525341333037322d504b435331352d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PSS_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d525341343039362d5053532d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PKCS15_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d525341343039362d504b435331352d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P256_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d45434453412d503235362d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P384_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d45434453412d503338342d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_brainpoolP256r1_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d45434453412d42503235362d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA65_Ed25519_SHA512, Hex.decode("434f4d505349472d4d4c44534136352d456432353531392d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_brainpoolP384r1_SHA512, Hex.decode("434f4d505349472d4d4c44534138372d45434453412d42503338342d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA87_Ed448_SHAKE256, Hex.decode("434f4d505349472d4d4c44534138372d45643434382d5348414b45323536"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA87_RSA3072_PSS_SHA512, Hex.decode("434f4d505349472d4d4c44534138372d525341333037322d5053532d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA87_RSA4096_PSS_SHA512, Hex.decode("434f4d505349472d4d4c44534138372d525341343039362d5053532d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P384_SHA512, Hex.decode("434f4d505349472d4d4c44534138372d45434453412d503338342d534841353132"));
    domainSeparators.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P521_SHA512, Hex.decode("434f4d505349472d4d4c44534138372d45434453412d503532312d534841353132"));
    algorithmsParameterSpecs.put(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PSS_SHA256, new PSSParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), 32, 1));
    algorithmsParameterSpecs.put(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PSS_SHA512, new PSSParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), 32, 1));
    algorithmsParameterSpecs.put(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PSS_SHA512, new PSSParameterSpec("SHA-384", "MGF1", new MGF1ParameterSpec("SHA-384"), 48, 1));
    algorithmsParameterSpecs.put(IANAObjectIdentifiers.id_MLDSA87_RSA4096_PSS_SHA512, new PSSParameterSpec("SHA-384", "MGF1", new MGF1ParameterSpec("SHA-384"), 48, 1));
    algorithmsParameterSpecs.put(IANAObjectIdentifiers.id_MLDSA87_RSA3072_PSS_SHA512, new PSSParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), 32, 1));
  }
  
  public static final class COMPOSITE extends SignatureSpi {
    public COMPOSITE() {
      super(null, null, false);
    }
  }
  
  private static final class ErasableOutputStream extends ByteArrayOutputStream {
    public byte[] getBuf() {
      return this.buf;
    }
  }
  
  public static final class MLDSA44_ECDSA_P256_SHA256 extends SignatureSpi {
    public MLDSA44_ECDSA_P256_SHA256() {
      super(IANAObjectIdentifiers.id_MLDSA44_ECDSA_P256_SHA256, (Digest)new SHA256Digest());
    }
  }
  
  public static final class MLDSA44_ECDSA_P256_SHA256_PREHASH extends SignatureSpi {
    public MLDSA44_ECDSA_P256_SHA256_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA44_ECDSA_P256_SHA256, (Digest)new SHA256Digest(), true);
    }
  }
  
  public static final class MLDSA44_Ed25519_SHA512 extends SignatureSpi {
    public MLDSA44_Ed25519_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA44_Ed25519_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA44_Ed25519_SHA512_PREHASH extends SignatureSpi {
    public MLDSA44_Ed25519_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA44_Ed25519_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA44_RSA2048_PKCS15_SHA256 extends SignatureSpi {
    public MLDSA44_RSA2048_PKCS15_SHA256() {
      super(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PKCS15_SHA256, (Digest)new SHA256Digest());
    }
  }
  
  public static final class MLDSA44_RSA2048_PKCS15_SHA256_PREHASH extends SignatureSpi {
    public MLDSA44_RSA2048_PKCS15_SHA256_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PKCS15_SHA256, (Digest)new SHA256Digest(), true);
    }
  }
  
  public static final class MLDSA44_RSA2048_PSS_SHA256 extends SignatureSpi {
    public MLDSA44_RSA2048_PSS_SHA256() {
      super(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PSS_SHA256, (Digest)new SHA256Digest());
    }
  }
  
  public static final class MLDSA44_RSA2048_PSS_SHA256_PREHASH extends SignatureSpi {
    public MLDSA44_RSA2048_PSS_SHA256_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PSS_SHA256, (Digest)new SHA256Digest(), true);
    }
  }
  
  public static final class MLDSA65_ECDSA_P256_SHA512 extends SignatureSpi {
    public MLDSA65_ECDSA_P256_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P256_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_ECDSA_P256_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_ECDSA_P256_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P256_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA65_ECDSA_P384_SHA512 extends SignatureSpi {
    public MLDSA65_ECDSA_P384_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P384_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_ECDSA_P384_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_ECDSA_P384_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P384_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA65_ECDSA_brainpoolP256r1_SHA512 extends SignatureSpi {
    public MLDSA65_ECDSA_brainpoolP256r1_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_brainpoolP256r1_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_ECDSA_brainpoolP256r1_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_ECDSA_brainpoolP256r1_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_ECDSA_brainpoolP256r1_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA65_Ed25519_SHA512 extends SignatureSpi {
    public MLDSA65_Ed25519_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_Ed25519_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_Ed25519_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_Ed25519_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_Ed25519_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA65_RSA3072_PKCS15_SHA512 extends SignatureSpi {
    public MLDSA65_RSA3072_PKCS15_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PKCS15_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_RSA3072_PKCS15_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_RSA3072_PKCS15_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PKCS15_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA65_RSA3072_PSS_SHA512 extends SignatureSpi {
    public MLDSA65_RSA3072_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PSS_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_RSA3072_PSS_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_RSA3072_PSS_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PSS_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA65_RSA4096_PKCS15_SHA512 extends SignatureSpi {
    public MLDSA65_RSA4096_PKCS15_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PKCS15_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_RSA4096_PKCS15_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_RSA4096_PKCS15_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PKCS15_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA65_RSA4096_PSS_SHA512 extends SignatureSpi {
    public MLDSA65_RSA4096_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PSS_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA65_RSA4096_PSS_SHA512_PREHASH extends SignatureSpi {
    public MLDSA65_RSA4096_PSS_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PSS_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA87_ECDSA_P384_SHA512 extends SignatureSpi {
    public MLDSA87_ECDSA_P384_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P384_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA87_ECDSA_P384_SHA512_PREHASH extends SignatureSpi {
    public MLDSA87_ECDSA_P384_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P384_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA87_ECDSA_P521_SHA512 extends SignatureSpi {
    public MLDSA87_ECDSA_P521_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P521_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA87_ECDSA_P521_SHA512_PREHASH extends SignatureSpi {
    public MLDSA87_ECDSA_P521_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P521_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA87_ECDSA_brainpoolP384r1_SHA512 extends SignatureSpi {
    public MLDSA87_ECDSA_brainpoolP384r1_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_brainpoolP384r1_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA87_ECDSA_brainpoolP384r1_SHA512_PREHASH extends SignatureSpi {
    public MLDSA87_ECDSA_brainpoolP384r1_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA87_ECDSA_brainpoolP384r1_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA87_Ed448_SHAKE256 extends SignatureSpi {
    public MLDSA87_Ed448_SHAKE256() {
      super(IANAObjectIdentifiers.id_MLDSA87_Ed448_SHAKE256, (Digest)new SHAKEDigest(256));
    }
  }
  
  public static final class MLDSA87_Ed448_SHAKE256_PREHASH extends SignatureSpi {
    public MLDSA87_Ed448_SHAKE256_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA87_Ed448_SHAKE256, (Digest)new SHAKEDigest(256), true);
    }
  }
  
  public static final class MLDSA87_RSA3072_PSS_SHA512 extends SignatureSpi {
    public MLDSA87_RSA3072_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_RSA3072_PSS_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA87_RSA3072_PSS_SHA512_PREHASH extends SignatureSpi {
    public MLDSA87_RSA3072_PSS_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA87_RSA3072_PSS_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  public static final class MLDSA87_RSA4096_PSS_SHA512 extends SignatureSpi {
    public MLDSA87_RSA4096_PSS_SHA512() {
      super(IANAObjectIdentifiers.id_MLDSA87_RSA4096_PSS_SHA512, (Digest)new SHA512Digest());
    }
  }
  
  public static final class MLDSA87_RSA4096_PSS_SHA512_PREHASH extends SignatureSpi {
    public MLDSA87_RSA4096_PSS_SHA512_PREHASH() {
      super(IANAObjectIdentifiers.id_MLDSA87_RSA4096_PSS_SHA512, (Digest)new SHA512Digest(), true);
    }
  }
  
  private static class NullDigest implements Digest {
    private final int expectedSize;
    
    private final OpenByteArrayOutputStream bOut = new OpenByteArrayOutputStream();
    
    NullDigest(int param1Int) {
      this.expectedSize = param1Int;
    }
    
    public String getAlgorithmName() {
      return "NULL";
    }
    
    public int getDigestSize() {
      return this.bOut.size();
    }
    
    public void update(byte param1Byte) {
      this.bOut.write(param1Byte);
    }
    
    public void update(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
      this.bOut.write(param1ArrayOfbyte, param1Int1, param1Int2);
    }
    
    public int doFinal(byte[] param1ArrayOfbyte, int param1Int) {
      int i = this.bOut.size();
      if (i != this.expectedSize)
        throw new IllegalStateException("provided pre-hash digest is the wrong length"); 
      this.bOut.copy(param1ArrayOfbyte, param1Int);
      reset();
      return i;
    }
    
    public void reset() {
      this.bOut.reset();
    }
    
    private static class OpenByteArrayOutputStream extends ByteArrayOutputStream {
      private OpenByteArrayOutputStream() {}
      
      public void reset() {
        super.reset();
        Arrays.clear(this.buf);
      }
      
      void copy(byte[] param2ArrayOfbyte, int param2Int) {
        System.arraycopy(this.buf, 0, param2ArrayOfbyte, param2Int, size());
      }
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\compositesignatures\SignatureSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */