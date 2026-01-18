package org.bouncycastle.jcajce.provider.asymmetric.compositesignatures;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.internal.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.internal.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.internal.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.CompositePublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Exceptions;

public class KeyFactorySpi extends BaseKeyFactorySpi implements AsymmetricKeyInfoConverter {
  private static final AlgorithmIdentifier mlDsa44 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_ml_dsa_44);
  
  private static final AlgorithmIdentifier mlDsa65 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_ml_dsa_65);
  
  private static final AlgorithmIdentifier mlDsa87 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_ml_dsa_87);
  
  private static final AlgorithmIdentifier falcon512Identifier = new AlgorithmIdentifier(BCObjectIdentifiers.falcon_512);
  
  private static final AlgorithmIdentifier ed25519 = new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519);
  
  private static final AlgorithmIdentifier ecDsaP256 = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, (ASN1Encodable)new X962Parameters(SECObjectIdentifiers.secp256r1));
  
  private static final AlgorithmIdentifier ecDsaBrainpoolP256r1 = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, (ASN1Encodable)new X962Parameters(TeleTrusTObjectIdentifiers.brainpoolP256r1));
  
  private static final AlgorithmIdentifier rsa = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption);
  
  private static final AlgorithmIdentifier ed448 = new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed448);
  
  private static final AlgorithmIdentifier ecDsaP384 = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, (ASN1Encodable)new X962Parameters(SECObjectIdentifiers.secp384r1));
  
  private static final AlgorithmIdentifier ecDsaP521 = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, (ASN1Encodable)new X962Parameters(SECObjectIdentifiers.secp521r1));
  
  private static final AlgorithmIdentifier ecDsaBrainpoolP384r1 = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, (ASN1Encodable)new X962Parameters(TeleTrusTObjectIdentifiers.brainpoolP384r1));
  
  private static Map<ASN1ObjectIdentifier, AlgorithmIdentifier[]> pairings = (Map)new HashMap<>();
  
  private static Map<ASN1ObjectIdentifier, int[]> componentKeySizes = (Map)new HashMap<>();
  
  private JcaJceHelper helper;
  
  public KeyFactorySpi() {
    this(null);
  }
  
  public KeyFactorySpi(JcaJceHelper paramJcaJceHelper) {
    this.helper = paramJcaJceHelper;
  }
  
  protected Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
    if (this.helper == null)
      this.helper = (JcaJceHelper)new BCJcaJceHelper(); 
    try {
      if (paramKey instanceof PrivateKey)
        return generatePrivate(PrivateKeyInfo.getInstance(paramKey.getEncoded())); 
      if (paramKey instanceof PublicKey)
        return generatePublic(SubjectPublicKeyInfo.getInstance(paramKey.getEncoded())); 
    } catch (IOException iOException) {
      throw new InvalidKeyException("Key could not be parsed: " + iOException.getMessage());
    } 
    throw new InvalidKeyException("Key not recognized");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    if (this.helper == null)
      this.helper = (JcaJceHelper)new BCJcaJceHelper(); 
    ASN1ObjectIdentifier aSN1ObjectIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
    if (MiscObjectIdentifiers.id_alg_composite.equals((ASN1Primitive)aSN1ObjectIdentifier) || MiscObjectIdentifiers.id_composite_key.equals((ASN1Primitive)aSN1ObjectIdentifier)) {
      ASN1Sequence aSN1Sequence = DERSequence.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      PrivateKey[] arrayOfPrivateKey = new PrivateKey[aSN1Sequence.size()];
      for (byte b = 0; b != aSN1Sequence.size(); b++) {
        ASN1Sequence aSN1Sequence1 = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(b));
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(aSN1Sequence1);
        try {
          arrayOfPrivateKey[b] = this.helper.createKeyFactory(privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm().getId()).generatePrivate(new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded()));
        } catch (Exception exception) {
          throw new IOException("cannot decode generic composite: " + exception.getMessage(), exception);
        } 
      } 
      return (PrivateKey)new CompositePrivateKey(arrayOfPrivateKey);
    } 
    try {
      byte[] arrayOfByte;
      List<KeyFactory> list = getKeyFactoriesFromIdentifier(aSN1ObjectIdentifier);
      ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
      try {
        arrayOfByte = DEROctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      } catch (Exception exception) {
        arrayOfByte = paramPrivateKeyInfo.getPrivateKey().getOctets();
      } 
      aSN1EncodableVector.add((ASN1Encodable)new DEROctetString(Arrays.copyOfRange(arrayOfByte, 0, 32)));
      String str = ((KeyFactory)list.get(1)).getAlgorithm();
      if (str.equals("Ed25519")) {
        aSN1EncodableVector.add((ASN1Encodable)new DEROctetString(Arrays.concatenate(new byte[] { 4, 32 }, Arrays.copyOfRange(arrayOfByte, 32, arrayOfByte.length))));
      } else if (str.equals("Ed448")) {
        aSN1EncodableVector.add((ASN1Encodable)new DEROctetString(Arrays.concatenate(new byte[] { 4, 57 }, Arrays.copyOfRange(arrayOfByte, 32, arrayOfByte.length))));
      } else {
        aSN1EncodableVector.add((ASN1Encodable)new DEROctetString(Arrays.copyOfRange(arrayOfByte, 32, arrayOfByte.length)));
      } 
      DERSequence dERSequence = new DERSequence(aSN1EncodableVector);
      PrivateKey[] arrayOfPrivateKey = new PrivateKey[dERSequence.size()];
      AlgorithmIdentifier[] arrayOfAlgorithmIdentifier = pairings.get(aSN1ObjectIdentifier);
      for (byte b = 0; b < dERSequence.size(); b++) {
        if (dERSequence.getObjectAt(b) instanceof org.bouncycastle.asn1.ASN1OctetString) {
          aSN1EncodableVector = new ASN1EncodableVector(3);
          aSN1EncodableVector.add((ASN1Encodable)paramPrivateKeyInfo.getVersion());
          aSN1EncodableVector.add((ASN1Encodable)arrayOfAlgorithmIdentifier[b]);
          aSN1EncodableVector.add(dERSequence.getObjectAt(b));
          PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(PrivateKeyInfo.getInstance(new DERSequence(aSN1EncodableVector)).getEncoded());
          arrayOfPrivateKey[b] = ((KeyFactory)list.get(b)).generatePrivate(pKCS8EncodedKeySpec);
        } else {
          ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(dERSequence.getObjectAt(b));
          PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(PrivateKeyInfo.getInstance(aSN1Sequence).getEncoded());
          arrayOfPrivateKey[b] = ((KeyFactory)list.get(b)).generatePrivate(pKCS8EncodedKeySpec);
        } 
      } 
      return (PrivateKey)new CompositePrivateKey(aSN1ObjectIdentifier, arrayOfPrivateKey);
    } catch (GeneralSecurityException generalSecurityException) {
      throw Exceptions.ioException(generalSecurityException.getMessage(), generalSecurityException);
    } 
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    if (this.helper == null)
      this.helper = (JcaJceHelper)new BCJcaJceHelper(); 
    ASN1ObjectIdentifier aSN1ObjectIdentifier = paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm();
    ASN1Sequence aSN1Sequence = null;
    byte[][] arrayOfByte = new byte[2][];
    try {
      aSN1Sequence = DERSequence.getInstance(paramSubjectPublicKeyInfo.getPublicKeyData().getBytes());
    } catch (Exception exception) {
      arrayOfByte = split(aSN1ObjectIdentifier, paramSubjectPublicKeyInfo.getPublicKeyData());
    } 
    if (MiscObjectIdentifiers.id_alg_composite.equals((ASN1Primitive)aSN1ObjectIdentifier) || MiscObjectIdentifiers.id_composite_key.equals((ASN1Primitive)aSN1ObjectIdentifier)) {
      ASN1Sequence aSN1Sequence1 = ASN1Sequence.getInstance(paramSubjectPublicKeyInfo.getPublicKeyData().getBytes());
      PublicKey[] arrayOfPublicKey = new PublicKey[aSN1Sequence1.size()];
      for (byte b = 0; b != aSN1Sequence1.size(); b++) {
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(aSN1Sequence1.getObjectAt(b));
        try {
          arrayOfPublicKey[b] = this.helper.createKeyFactory(subjectPublicKeyInfo.getAlgorithm().getAlgorithm().getId()).generatePublic(new X509EncodedKeySpec(subjectPublicKeyInfo.getEncoded()));
        } catch (Exception exception) {
          throw new IOException("cannot decode generic composite: " + exception.getMessage(), exception);
        } 
      } 
      return (PublicKey)new CompositePublicKey(arrayOfPublicKey);
    } 
    try {
      int i = (aSN1Sequence == null) ? arrayOfByte.length : aSN1Sequence.size();
      List<KeyFactory> list = getKeyFactoriesFromIdentifier(aSN1ObjectIdentifier);
      ASN1BitString[] arrayOfASN1BitString = new ASN1BitString[i];
      for (byte b1 = 0; b1 < i; b1++) {
        if (aSN1Sequence != null) {
          if (aSN1Sequence.getObjectAt(b1) instanceof DEROctetString) {
            arrayOfASN1BitString[b1] = (ASN1BitString)new DERBitString(((DEROctetString)aSN1Sequence.getObjectAt(b1)).getOctets());
          } else {
            arrayOfASN1BitString[b1] = (ASN1BitString)aSN1Sequence.getObjectAt(b1);
          } 
        } else {
          arrayOfASN1BitString[b1] = (ASN1BitString)new DERBitString(arrayOfByte[b1]);
        } 
      } 
      X509EncodedKeySpec[] arrayOfX509EncodedKeySpec = getKeysSpecs(aSN1ObjectIdentifier, arrayOfASN1BitString);
      PublicKey[] arrayOfPublicKey = new PublicKey[i];
      for (byte b2 = 0; b2 < i; b2++)
        arrayOfPublicKey[b2] = ((KeyFactory)list.get(b2)).generatePublic(arrayOfX509EncodedKeySpec[b2]); 
      return (PublicKey)new CompositePublicKey(aSN1ObjectIdentifier, arrayOfPublicKey);
    } catch (GeneralSecurityException generalSecurityException) {
      throw Exceptions.ioException(generalSecurityException.getMessage(), generalSecurityException);
    } 
  }
  
  byte[][] split(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1BitString paramASN1BitString) {
    int[] arrayOfInt = componentKeySizes.get(paramASN1ObjectIdentifier);
    byte[] arrayOfByte = paramASN1BitString.getOctets();
    byte[][] arrayOfByte1 = { new byte[arrayOfInt[0]], new byte[arrayOfByte.length - arrayOfInt[0]] };
    System.arraycopy(arrayOfByte, 0, arrayOfByte1[0], 0, arrayOfInt[0]);
    System.arraycopy(arrayOfByte, arrayOfInt[0], arrayOfByte1[1], 0, (arrayOfByte1[1]).length);
    return arrayOfByte1;
  }
  
  private List<KeyFactory> getKeyFactoriesFromIdentifier(ASN1ObjectIdentifier paramASN1ObjectIdentifier) throws NoSuchAlgorithmException, NoSuchProviderException {
    ArrayList<KeyFactory> arrayList = new ArrayList();
    ArrayList arrayList1 = new ArrayList();
    String[] arrayOfString = CompositeIndex.getPairing(paramASN1ObjectIdentifier);
    if (arrayOfString == null)
      throw new NoSuchAlgorithmException("Cannot create KeyFactories. Unsupported algorithm identifier."); 
    arrayList.add(this.helper.createKeyFactory(CompositeIndex.getBaseName(arrayOfString[0])));
    arrayList.add(this.helper.createKeyFactory(CompositeIndex.getBaseName(arrayOfString[1])));
    return Collections.unmodifiableList(arrayList);
  }
  
  private X509EncodedKeySpec[] getKeysSpecs(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1BitString[] paramArrayOfASN1BitString) throws IOException {
    X509EncodedKeySpec[] arrayOfX509EncodedKeySpec = new X509EncodedKeySpec[paramArrayOfASN1BitString.length];
    SubjectPublicKeyInfo[] arrayOfSubjectPublicKeyInfo = new SubjectPublicKeyInfo[paramArrayOfASN1BitString.length];
    AlgorithmIdentifier[] arrayOfAlgorithmIdentifier = pairings.get(paramASN1ObjectIdentifier);
    if (arrayOfAlgorithmIdentifier == null)
      throw new IOException("Cannot create key specs. Unsupported algorithm identifier."); 
    arrayOfSubjectPublicKeyInfo[0] = new SubjectPublicKeyInfo(arrayOfAlgorithmIdentifier[0], paramArrayOfASN1BitString[0]);
    arrayOfSubjectPublicKeyInfo[1] = new SubjectPublicKeyInfo(arrayOfAlgorithmIdentifier[1], paramArrayOfASN1BitString[1]);
    arrayOfX509EncodedKeySpec[0] = new X509EncodedKeySpec(arrayOfSubjectPublicKeyInfo[0].getEncoded());
    arrayOfX509EncodedKeySpec[1] = new X509EncodedKeySpec(arrayOfSubjectPublicKeyInfo[1].getEncoded());
    return arrayOfX509EncodedKeySpec;
  }
  
  static {
    pairings.put(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PSS_SHA256, new AlgorithmIdentifier[] { mlDsa44, rsa });
    pairings.put(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PKCS15_SHA256, new AlgorithmIdentifier[] { mlDsa44, rsa });
    pairings.put(IANAObjectIdentifiers.id_MLDSA44_Ed25519_SHA512, new AlgorithmIdentifier[] { mlDsa44, ed25519 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA44_ECDSA_P256_SHA256, new AlgorithmIdentifier[] { mlDsa44, ecDsaP256 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PSS_SHA512, new AlgorithmIdentifier[] { mlDsa65, rsa });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PKCS15_SHA512, new AlgorithmIdentifier[] { mlDsa65, rsa });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PSS_SHA512, new AlgorithmIdentifier[] { mlDsa65, rsa });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PKCS15_SHA512, new AlgorithmIdentifier[] { mlDsa65, rsa });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P256_SHA512, new AlgorithmIdentifier[] { mlDsa65, ecDsaP256 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P384_SHA512, new AlgorithmIdentifier[] { mlDsa65, ecDsaP384 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_brainpoolP256r1_SHA512, new AlgorithmIdentifier[] { mlDsa65, ecDsaBrainpoolP256r1 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA65_Ed25519_SHA512, new AlgorithmIdentifier[] { mlDsa65, ed25519 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P384_SHA512, new AlgorithmIdentifier[] { mlDsa87, ecDsaP384 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_brainpoolP384r1_SHA512, new AlgorithmIdentifier[] { mlDsa87, ecDsaBrainpoolP384r1 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA87_Ed448_SHAKE256, new AlgorithmIdentifier[] { mlDsa87, ed448 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA87_RSA4096_PSS_SHA512, new AlgorithmIdentifier[] { mlDsa87, rsa });
    pairings.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P521_SHA512, new AlgorithmIdentifier[] { mlDsa87, ecDsaP521 });
    pairings.put(IANAObjectIdentifiers.id_MLDSA87_RSA3072_PSS_SHA512, new AlgorithmIdentifier[] { mlDsa87, rsa });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PSS_SHA256, new int[] { 1312, 268 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA44_RSA2048_PKCS15_SHA256, new int[] { 1312, 284 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA44_Ed25519_SHA512, new int[] { 1312, 32 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA44_ECDSA_P256_SHA256, new int[] { 1312, 76 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PSS_SHA512, new int[] { 1952, 256 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_RSA3072_PKCS15_SHA512, new int[] { 1952, 256 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PSS_SHA512, new int[] { 1952, 542 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_RSA4096_PKCS15_SHA512, new int[] { 1952, 542 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P256_SHA512, new int[] { 1952, 76 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_P384_SHA512, new int[] { 1952, 87 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_ECDSA_brainpoolP256r1_SHA512, new int[] { 1952, 76 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA65_Ed25519_SHA512, new int[] { 1952, 32 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P384_SHA512, new int[] { 2592, 87 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_brainpoolP384r1_SHA512, new int[] { 2592, 87 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA87_Ed448_SHAKE256, new int[] { 2592, 57 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA87_RSA4096_PSS_SHA512, new int[] { 2592, 542 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA87_RSA3072_PSS_SHA512, new int[] { 2592, 256 });
    componentKeySizes.put(IANAObjectIdentifiers.id_MLDSA87_ECDSA_P521_SHA512, new int[] { 2592, 93 });
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\compositesignatures\KeyFactorySpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */