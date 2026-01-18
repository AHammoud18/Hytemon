package org.bouncycastle.cert;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.DeltaCertificateDescriptor;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.util.Exceptions;

public class X509v3CertificateBuilder {
  private V3TBSCertificateGenerator tbsGen = new V3TBSCertificateGenerator();
  
  private ExtensionsGenerator extGenerator;
  
  public X509v3CertificateBuilder(X500Name paramX500Name1, BigInteger paramBigInteger, Date paramDate1, Date paramDate2, X500Name paramX500Name2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo) {
    this(paramX500Name1, paramBigInteger, new Time(paramDate1), new Time(paramDate2), paramX500Name2, paramSubjectPublicKeyInfo);
  }
  
  public X509v3CertificateBuilder(X500Name paramX500Name1, BigInteger paramBigInteger, Date paramDate1, Date paramDate2, Locale paramLocale, X500Name paramX500Name2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo) {
    this(paramX500Name1, paramBigInteger, new Time(paramDate1, paramLocale), new Time(paramDate2, paramLocale), paramX500Name2, paramSubjectPublicKeyInfo);
  }
  
  public X509v3CertificateBuilder(X500Name paramX500Name1, BigInteger paramBigInteger, Time paramTime1, Time paramTime2, X500Name paramX500Name2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo) {
    this.tbsGen.setSerialNumber(new ASN1Integer(paramBigInteger));
    this.tbsGen.setIssuer(paramX500Name1);
    this.tbsGen.setStartDate(paramTime1);
    this.tbsGen.setEndDate(paramTime2);
    this.tbsGen.setSubject(paramX500Name2);
    this.tbsGen.setSubjectPublicKeyInfo(paramSubjectPublicKeyInfo);
    this.extGenerator = new ExtensionsGenerator();
  }
  
  public X509v3CertificateBuilder(X509CertificateHolder paramX509CertificateHolder) {
    this.tbsGen.setSerialNumber(new ASN1Integer(paramX509CertificateHolder.getSerialNumber()));
    this.tbsGen.setIssuer(paramX509CertificateHolder.getIssuer());
    this.tbsGen.setStartDate(new Time(paramX509CertificateHolder.getNotBefore()));
    this.tbsGen.setEndDate(new Time(paramX509CertificateHolder.getNotAfter()));
    this.tbsGen.setSubject(paramX509CertificateHolder.getSubject());
    this.tbsGen.setSubjectPublicKeyInfo(paramX509CertificateHolder.getSubjectPublicKeyInfo());
    this.extGenerator = new ExtensionsGenerator();
    Extensions extensions = paramX509CertificateHolder.getExtensions();
    Enumeration<ASN1ObjectIdentifier> enumeration = extensions.oids();
    while (enumeration.hasMoreElements()) {
      ASN1ObjectIdentifier aSN1ObjectIdentifier = enumeration.nextElement();
      if (Extension.subjectAltPublicKeyInfo.equals((ASN1Primitive)aSN1ObjectIdentifier) || Extension.altSignatureAlgorithm.equals((ASN1Primitive)aSN1ObjectIdentifier) || Extension.altSignatureValue.equals((ASN1Primitive)aSN1ObjectIdentifier))
        continue; 
      this.extGenerator.addExtension(extensions.getExtension(aSN1ObjectIdentifier));
    } 
  }
  
  public boolean hasExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    return (doGetExtension(paramASN1ObjectIdentifier) != null);
  }
  
  public Extension getExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    return doGetExtension(paramASN1ObjectIdentifier);
  }
  
  private Extension doGetExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    if (this.extGenerator.isEmpty())
      return null; 
    Extensions extensions = this.extGenerator.generate();
    return extensions.getExtension(paramASN1ObjectIdentifier);
  }
  
  public X509v3CertificateBuilder setSubjectUniqueID(boolean[] paramArrayOfboolean) {
    this.tbsGen.setSubjectUniqueID(booleanToBitString(paramArrayOfboolean));
    return this;
  }
  
  public X509v3CertificateBuilder setIssuerUniqueID(boolean[] paramArrayOfboolean) {
    this.tbsGen.setIssuerUniqueID(booleanToBitString(paramArrayOfboolean));
    return this;
  }
  
  public X509v3CertificateBuilder addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable) throws CertIOException {
    try {
      this.extGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
    } catch (IOException iOException) {
      throw new CertIOException("cannot encode extension: " + iOException.getMessage(), iOException);
    } 
    return this;
  }
  
  public X509v3CertificateBuilder addExtension(Extension paramExtension) throws CertIOException {
    this.extGenerator.addExtension(paramExtension);
    return this;
  }
  
  public X509v3CertificateBuilder addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfbyte) throws CertIOException {
    this.extGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramArrayOfbyte);
    return this;
  }
  
  public X509v3CertificateBuilder replaceExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable) throws CertIOException {
    try {
      this.extGenerator = CertUtils.doReplaceExtension(this.extGenerator, new Extension(paramASN1ObjectIdentifier, paramBoolean, (ASN1OctetString)new DEROctetString(paramASN1Encodable)));
    } catch (IOException iOException) {
      throw new CertIOException("cannot encode extension: " + iOException.getMessage(), iOException);
    } 
    return this;
  }
  
  public X509v3CertificateBuilder replaceExtension(Extension paramExtension) throws CertIOException {
    this.extGenerator = CertUtils.doReplaceExtension(this.extGenerator, paramExtension);
    return this;
  }
  
  public X509v3CertificateBuilder replaceExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfbyte) throws CertIOException {
    this.extGenerator = CertUtils.doReplaceExtension(this.extGenerator, new Extension(paramASN1ObjectIdentifier, paramBoolean, paramArrayOfbyte));
    return this;
  }
  
  public X509v3CertificateBuilder removeExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    this.extGenerator = CertUtils.doRemoveExtension(this.extGenerator, paramASN1ObjectIdentifier);
    return this;
  }
  
  public X509v3CertificateBuilder copyAndAddExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, X509CertificateHolder paramX509CertificateHolder) {
    Certificate certificate = paramX509CertificateHolder.toASN1Structure();
    Extension extension = certificate.getTBSCertificate().getExtensions().getExtension(paramASN1ObjectIdentifier);
    if (extension == null)
      throw new NullPointerException("extension " + paramASN1ObjectIdentifier + " not present"); 
    this.extGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, extension.getExtnValue().getOctets());
    return this;
  }
  
  public X509CertificateHolder build(ContentSigner paramContentSigner) {
    AlgorithmIdentifier algorithmIdentifier = paramContentSigner.getAlgorithmIdentifier();
    this.tbsGen.setSignature(algorithmIdentifier);
    if (!this.extGenerator.isEmpty()) {
      Extension extension = this.extGenerator.getExtension(Extension.deltaCertificateDescriptor);
      if (extension != null) {
        DeltaCertificateDescriptor deltaCertificateDescriptor = DeltaCertificateTool.trimDeltaCertificateDescriptor(DeltaCertificateDescriptor.getInstance(extension.getParsedValue()), this.tbsGen.generateTBSCertificate(), this.extGenerator.generate());
        try {
          this.extGenerator.replaceExtension(Extension.deltaCertificateDescriptor, extension.isCritical(), (ASN1Encodable)deltaCertificateDescriptor);
        } catch (IOException iOException) {
          throw new IllegalStateException("unable to replace deltaCertificateDescriptor: " + iOException.getMessage());
        } 
      } 
      this.tbsGen.setExtensions(this.extGenerator.generate());
    } 
    try {
      TBSCertificate tBSCertificate = this.tbsGen.generateTBSCertificate();
      byte[] arrayOfByte = generateSig(paramContentSigner, (ASN1Object)tBSCertificate);
      return new X509CertificateHolder(generateStructure(tBSCertificate, algorithmIdentifier, arrayOfByte));
    } catch (IOException iOException) {
      throw Exceptions.illegalArgumentException("cannot produce certificate signature", iOException);
    } 
  }
  
  public X509CertificateHolder build(ContentSigner paramContentSigner1, boolean paramBoolean, ContentSigner paramContentSigner2) {
    AlgorithmIdentifier algorithmIdentifier1 = paramContentSigner1.getAlgorithmIdentifier();
    AlgorithmIdentifier algorithmIdentifier2 = paramContentSigner2.getAlgorithmIdentifier();
    try {
      this.extGenerator.addExtension(Extension.altSignatureAlgorithm, paramBoolean, (ASN1Encodable)algorithmIdentifier2);
    } catch (IOException iOException) {
      throw Exceptions.illegalStateException("cannot add altSignatureAlgorithm extension", iOException);
    } 
    Extension extension = this.extGenerator.getExtension(Extension.deltaCertificateDescriptor);
    if (extension != null) {
      this.tbsGen.setSignature(algorithmIdentifier1);
      try {
        ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
        extensionsGenerator.addExtensions(this.extGenerator.generate());
        extensionsGenerator.addExtension(Extension.altSignatureValue, false, (ASN1Encodable)DERNull.INSTANCE);
        DeltaCertificateDescriptor deltaCertificateDescriptor = DeltaCertificateTool.trimDeltaCertificateDescriptor(DeltaCertificateDescriptor.getInstance(extension.getParsedValue()), this.tbsGen.generateTBSCertificate(), extensionsGenerator.generate());
        this.extGenerator.replaceExtension(Extension.deltaCertificateDescriptor, extension.isCritical(), (ASN1Encodable)deltaCertificateDescriptor);
      } catch (IOException iOException) {
        throw new IllegalStateException("unable to replace deltaCertificateDescriptor: " + iOException.getMessage());
      } 
    } 
    this.tbsGen.setSignature(null);
    this.tbsGen.setExtensions(this.extGenerator.generate());
    try {
      byte[] arrayOfByte1 = generateSig(paramContentSigner2, (ASN1Object)this.tbsGen.generatePreTBSCertificate());
      this.extGenerator.addExtension(Extension.altSignatureValue, paramBoolean, (ASN1Encodable)new DERBitString(arrayOfByte1));
      this.tbsGen.setSignature(algorithmIdentifier1);
      this.tbsGen.setExtensions(this.extGenerator.generate());
      TBSCertificate tBSCertificate = this.tbsGen.generateTBSCertificate();
      byte[] arrayOfByte2 = generateSig(paramContentSigner1, (ASN1Object)tBSCertificate);
      return new X509CertificateHolder(generateStructure(tBSCertificate, algorithmIdentifier1, arrayOfByte2));
    } catch (IOException iOException) {
      throw Exceptions.illegalArgumentException("cannot produce certificate signature", iOException);
    } 
  }
  
  private static byte[] generateSig(ContentSigner paramContentSigner, ASN1Object paramASN1Object) throws IOException {
    OutputStream outputStream = paramContentSigner.getOutputStream();
    paramASN1Object.encodeTo(outputStream, "DER");
    outputStream.close();
    return paramContentSigner.getSignature();
  }
  
  private static Certificate generateStructure(TBSCertificate paramTBSCertificate, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte) {
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
    aSN1EncodableVector.add((ASN1Encodable)paramTBSCertificate);
    aSN1EncodableVector.add((ASN1Encodable)paramAlgorithmIdentifier);
    aSN1EncodableVector.add((ASN1Encodable)new DERBitString(paramArrayOfbyte));
    return Certificate.getInstance(new DERSequence(aSN1EncodableVector));
  }
  
  static DERBitString booleanToBitString(boolean[] paramArrayOfboolean) {
    byte[] arrayOfByte = new byte[(paramArrayOfboolean.length + 7) / 8];
    for (byte b = 0; b != paramArrayOfboolean.length; b++)
      arrayOfByte[b >>> 3] = (byte)(arrayOfByte[b >>> 3] | (paramArrayOfboolean[b] ? (byte)(128 >> (b & 0x7)) : 0)); 
    return new DERBitString(arrayOfByte, 8 - paramArrayOfboolean.length & 0x7);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\X509v3CertificateBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */