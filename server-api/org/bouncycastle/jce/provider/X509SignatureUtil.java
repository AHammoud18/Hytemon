package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PSSParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.internal.asn1.oiw.OIWObjectIdentifiers;

class X509SignatureUtil {
  static byte[] getExtensionValue(Extensions paramExtensions, String paramString) {
    if (paramString != null) {
      ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.tryFromID(paramString);
      if (aSN1ObjectIdentifier != null) {
        ASN1OctetString aSN1OctetString = Extensions.getExtensionValue(paramExtensions, aSN1ObjectIdentifier);
        if (null != aSN1OctetString)
          try {
            return aSN1OctetString.getEncoded();
          } catch (Exception exception) {
            throw new IllegalStateException("error parsing " + exception.toString());
          }  
      } 
    } 
    return null;
  }
  
  private static boolean isAbsentOrEmptyParameters(ASN1Encodable paramASN1Encodable) {
    return (paramASN1Encodable == null || DERNull.INSTANCE.equals(paramASN1Encodable));
  }
  
  static void setSignatureParameters(Signature paramSignature, ASN1Encodable paramASN1Encodable) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
    if (!isAbsentOrEmptyParameters(paramASN1Encodable)) {
      String str = paramSignature.getAlgorithm();
      AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(str, paramSignature.getProvider());
      try {
        algorithmParameters.init(paramASN1Encodable.toASN1Primitive().getEncoded());
      } catch (IOException iOException) {
        throw new SignatureException("IOException decoding parameters: " + iOException.getMessage());
      } 
      if (str.endsWith("MGF1"))
        try {
          paramSignature.setParameter(algorithmParameters.getParameterSpec((Class)PSSParameterSpec.class));
        } catch (GeneralSecurityException generalSecurityException) {
          throw new SignatureException("Exception extracting parameters: " + generalSecurityException.getMessage());
        }  
    } 
  }
  
  static String getSignatureName(AlgorithmIdentifier paramAlgorithmIdentifier) {
    ASN1ObjectIdentifier aSN1ObjectIdentifier = paramAlgorithmIdentifier.getAlgorithm();
    ASN1Encodable aSN1Encodable = paramAlgorithmIdentifier.getParameters();
    if (!isAbsentOrEmptyParameters(aSN1Encodable)) {
      if (PKCSObjectIdentifiers.id_RSASSA_PSS.equals((ASN1Primitive)aSN1ObjectIdentifier)) {
        RSASSAPSSparams rSASSAPSSparams = RSASSAPSSparams.getInstance(aSN1Encodable);
        return getDigestAlgName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()) + "withRSAandMGF1";
      } 
      if (X9ObjectIdentifiers.ecdsa_with_SHA2.equals((ASN1Primitive)aSN1ObjectIdentifier)) {
        AlgorithmIdentifier algorithmIdentifier = AlgorithmIdentifier.getInstance(aSN1Encodable);
        return getDigestAlgName(algorithmIdentifier.getAlgorithm()) + "withECDSA";
      } 
    } 
    return aSN1ObjectIdentifier.getId();
  }
  
  private static String getDigestAlgName(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    return PKCSObjectIdentifiers.md5.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "MD5" : (OIWObjectIdentifiers.idSHA1.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "SHA1" : (NISTObjectIdentifiers.id_sha224.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "SHA224" : (NISTObjectIdentifiers.id_sha256.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "SHA256" : (NISTObjectIdentifiers.id_sha384.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "SHA384" : (NISTObjectIdentifiers.id_sha512.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "SHA512" : (TeleTrusTObjectIdentifiers.ripemd128.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "RIPEMD128" : (TeleTrusTObjectIdentifiers.ripemd160.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "RIPEMD160" : (TeleTrusTObjectIdentifiers.ripemd256.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "RIPEMD256" : (CryptoProObjectIdentifiers.gostR3411.equals((ASN1Primitive)paramASN1ObjectIdentifier) ? "GOST3411" : paramASN1ObjectIdentifier.getId())))))))));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\provider\X509SignatureUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */