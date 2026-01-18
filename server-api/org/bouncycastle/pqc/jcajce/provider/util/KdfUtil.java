package org.bouncycastle.pqc.jcajce.provider.util;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.macs.KMAC;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.spec.KEMKDFSpec;
import org.bouncycastle.util.Arrays;

public class KdfUtil {
  public static byte[] makeKeyBytes(KEMKDFSpec paramKEMKDFSpec, byte[] paramArrayOfbyte) {
    byte[] arrayOfByte;
    try {
      if (paramKEMKDFSpec == null) {
        arrayOfByte = new byte[paramArrayOfbyte.length];
        System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, arrayOfByte.length);
      } else {
        arrayOfByte = makeKeyBytes(paramKEMKDFSpec.getKdfAlgorithm(), paramArrayOfbyte, paramKEMKDFSpec.getOtherInfo(), paramKEMKDFSpec.getKeySize());
      } 
    } finally {
      Arrays.clear(paramArrayOfbyte);
    } 
    return arrayOfByte;
  }
  
  static byte[] makeKeyBytes(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) {
    byte[] arrayOfByte = new byte[(paramInt + 7) / 8];
    if (paramAlgorithmIdentifier == null) {
      System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, arrayOfByte.length);
    } else if (X9ObjectIdentifiers.id_kdf_kdf2.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      AlgorithmIdentifier algorithmIdentifier = AlgorithmIdentifier.getInstance(paramAlgorithmIdentifier.getParameters());
      KDF2BytesGenerator kDF2BytesGenerator = new KDF2BytesGenerator(getDigest(algorithmIdentifier.getAlgorithm()));
      kDF2BytesGenerator.init((DerivationParameters)new KDFParameters(paramArrayOfbyte1, paramArrayOfbyte2));
      kDF2BytesGenerator.generateBytes(arrayOfByte, 0, arrayOfByte.length);
    } else if (X9ObjectIdentifiers.id_kdf_kdf3.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      AlgorithmIdentifier algorithmIdentifier = AlgorithmIdentifier.getInstance(paramAlgorithmIdentifier.getParameters());
      ConcatenationKDFGenerator concatenationKDFGenerator = new ConcatenationKDFGenerator(getDigest(algorithmIdentifier.getAlgorithm()));
      concatenationKDFGenerator.init((DerivationParameters)new KDFParameters(paramArrayOfbyte1, paramArrayOfbyte2));
      concatenationKDFGenerator.generateBytes(arrayOfByte, 0, arrayOfByte.length);
    } else if (PKCSObjectIdentifiers.id_alg_hkdf_with_sha256.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      if (paramAlgorithmIdentifier.getParameters() == null) {
        HKDFBytesGenerator hKDFBytesGenerator = new HKDFBytesGenerator((Digest)new SHA256Digest());
        hKDFBytesGenerator.init((DerivationParameters)new HKDFParameters(paramArrayOfbyte1, null, paramArrayOfbyte2));
        hKDFBytesGenerator.generateBytes(arrayOfByte, 0, arrayOfByte.length);
      } else {
        throw new IllegalStateException("HDKF parameter support not added");
      } 
    } else if (PKCSObjectIdentifiers.id_alg_hkdf_with_sha384.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      if (paramAlgorithmIdentifier.getParameters() == null) {
        HKDFBytesGenerator hKDFBytesGenerator = new HKDFBytesGenerator((Digest)new SHA384Digest());
        hKDFBytesGenerator.init((DerivationParameters)new HKDFParameters(paramArrayOfbyte1, null, paramArrayOfbyte2));
        hKDFBytesGenerator.generateBytes(arrayOfByte, 0, arrayOfByte.length);
      } else {
        throw new IllegalStateException("HDKF parameter support not added");
      } 
    } else if (PKCSObjectIdentifiers.id_alg_hkdf_with_sha512.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      if (paramAlgorithmIdentifier.getParameters() == null) {
        HKDFBytesGenerator hKDFBytesGenerator = new HKDFBytesGenerator((Digest)new SHA512Digest());
        hKDFBytesGenerator.init((DerivationParameters)new HKDFParameters(paramArrayOfbyte1, null, paramArrayOfbyte2));
        hKDFBytesGenerator.generateBytes(arrayOfByte, 0, arrayOfByte.length);
      } else {
        throw new IllegalStateException("HDKF parameter support not added");
      } 
    } else if (NISTObjectIdentifiers.id_Kmac128.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      byte[] arrayOfByte1 = new byte[0];
      if (paramAlgorithmIdentifier.getParameters() != null)
        arrayOfByte1 = ASN1OctetString.getInstance(paramAlgorithmIdentifier.getParameters()).getOctets(); 
      KMAC kMAC = new KMAC(128, arrayOfByte1);
      kMAC.init((CipherParameters)new KeyParameter(paramArrayOfbyte1, 0, paramArrayOfbyte1.length));
      kMAC.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
      kMAC.doFinal(arrayOfByte, 0, arrayOfByte.length);
    } else if (NISTObjectIdentifiers.id_Kmac256.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      byte[] arrayOfByte1 = new byte[0];
      if (paramAlgorithmIdentifier.getParameters() != null)
        arrayOfByte1 = ASN1OctetString.getInstance(paramAlgorithmIdentifier.getParameters()).getOctets(); 
      KMAC kMAC = new KMAC(256, arrayOfByte1);
      kMAC.init((CipherParameters)new KeyParameter(paramArrayOfbyte1, 0, paramArrayOfbyte1.length));
      kMAC.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
      kMAC.doFinal(arrayOfByte, 0, arrayOfByte.length);
    } else if (NISTObjectIdentifiers.id_shake256.equals((ASN1Primitive)paramAlgorithmIdentifier.getAlgorithm())) {
      SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
      sHAKEDigest.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
      sHAKEDigest.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
      sHAKEDigest.doFinal(arrayOfByte, 0, arrayOfByte.length);
    } else {
      throw new IllegalArgumentException("Unrecognized KDF: " + paramAlgorithmIdentifier.getAlgorithm());
    } 
    return arrayOfByte;
  }
  
  static Digest getDigest(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    if (paramASN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha256))
      return (Digest)new SHA256Digest(); 
    if (paramASN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha512))
      return (Digest)new SHA512Digest(); 
    if (paramASN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_shake128))
      return (Digest)new SHAKEDigest(128); 
    if (paramASN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_shake256))
      return (Digest)new SHAKEDigest(256); 
    throw new IllegalArgumentException("unrecognized digest OID: " + paramASN1ObjectIdentifier);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provide\\util\KdfUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */