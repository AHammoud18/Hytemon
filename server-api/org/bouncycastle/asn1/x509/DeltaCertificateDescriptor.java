package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;

public class DeltaCertificateDescriptor extends ASN1Object {
  private final ASN1Integer serialNumber;
  
  private final AlgorithmIdentifier signature;
  
  private final X500Name issuer;
  
  private final Validity validity;
  
  private final X500Name subject;
  
  private final SubjectPublicKeyInfo subjectPublicKeyInfo;
  
  private final Extensions extensions;
  
  private final ASN1BitString signatureValue;
  
  public static DeltaCertificateDescriptor getInstance(Object paramObject) {
    return (paramObject instanceof DeltaCertificateDescriptor) ? (DeltaCertificateDescriptor)paramObject : ((paramObject != null) ? new DeltaCertificateDescriptor(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public static DeltaCertificateDescriptor fromExtensions(Extensions paramExtensions) {
    return getInstance(Extensions.getExtensionParsedValue(paramExtensions, Extension.deltaCertificateDescriptor));
  }
  
  private DeltaCertificateDescriptor(ASN1Sequence paramASN1Sequence) {
    ASN1Integer aSN1Integer = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    byte b = 1;
    ASN1Encodable aSN1Encodable = paramASN1Sequence.getObjectAt(b++);
    AlgorithmIdentifier algorithmIdentifier = null;
    X500Name x500Name1 = null;
    Validity validity = null;
    X500Name x500Name2 = null;
    while (aSN1Encodable instanceof ASN1TaggedObject) {
      ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Encodable);
      switch (aSN1TaggedObject.getTagNo()) {
        case 0:
          algorithmIdentifier = AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
          break;
        case 1:
          x500Name1 = X500Name.getInstance(aSN1TaggedObject, true);
          break;
        case 2:
          validity = Validity.getInstance(aSN1TaggedObject, true);
          break;
        case 3:
          x500Name2 = X500Name.getInstance(aSN1TaggedObject, true);
          break;
      } 
      aSN1Encodable = paramASN1Sequence.getObjectAt(b++);
    } 
    SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(aSN1Encodable);
    aSN1Encodable = paramASN1Sequence.getObjectAt(b);
    Extensions extensions = null;
    while (aSN1Encodable instanceof ASN1TaggedObject) {
      ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Encodable);
      switch (aSN1TaggedObject.getTagNo()) {
        case 4:
          extensions = Extensions.getInstance(aSN1TaggedObject, true);
          break;
      } 
      aSN1Encodable = paramASN1Sequence.getObjectAt(b++);
    } 
    ASN1BitString aSN1BitString = ASN1BitString.getInstance(aSN1Encodable);
    this.serialNumber = aSN1Integer;
    this.signature = algorithmIdentifier;
    this.issuer = x500Name1;
    this.validity = validity;
    this.subject = x500Name2;
    this.subjectPublicKeyInfo = subjectPublicKeyInfo;
    this.extensions = extensions;
    this.signatureValue = aSN1BitString;
  }
  
  public DeltaCertificateDescriptor(ASN1Integer paramASN1Integer, AlgorithmIdentifier paramAlgorithmIdentifier, X500Name paramX500Name1, Validity paramValidity, X500Name paramX500Name2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, Extensions paramExtensions, ASN1BitString paramASN1BitString) {
    if (paramASN1Integer == null)
      throw new NullPointerException("'serialNumber' cannot be null"); 
    if (paramSubjectPublicKeyInfo == null)
      throw new NullPointerException("'subjectPublicKeyInfo' cannot be null"); 
    if (paramASN1BitString == null)
      throw new NullPointerException("'signatureValue' cannot be null"); 
    this.serialNumber = paramASN1Integer;
    this.signature = paramAlgorithmIdentifier;
    this.issuer = paramX500Name1;
    this.validity = paramValidity;
    this.subject = paramX500Name2;
    this.subjectPublicKeyInfo = paramSubjectPublicKeyInfo;
    this.extensions = paramExtensions;
    this.signatureValue = paramASN1BitString;
  }
  
  public ASN1Integer getSerialNumber() {
    return this.serialNumber;
  }
  
  public AlgorithmIdentifier getSignature() {
    return this.signature;
  }
  
  public X500Name getIssuer() {
    return this.issuer;
  }
  
  public ASN1Sequence getValidity() {
    return (ASN1Sequence)this.validity.toASN1Primitive();
  }
  
  public Validity getValidityObject() {
    return this.validity;
  }
  
  public X500Name getSubject() {
    return this.subject;
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
    return this.subjectPublicKeyInfo;
  }
  
  public Extensions getExtensions() {
    return this.extensions;
  }
  
  public ASN1BitString getSignatureValue() {
    return this.signatureValue;
  }
  
  public DeltaCertificateDescriptor trimTo(TBSCertificate paramTBSCertificate, Extensions paramExtensions) {
    return trimDeltaCertificateDescriptor(this, paramTBSCertificate, paramExtensions);
  }
  
  private static DeltaCertificateDescriptor trimDeltaCertificateDescriptor(DeltaCertificateDescriptor paramDeltaCertificateDescriptor, TBSCertificate paramTBSCertificate, Extensions paramExtensions) {
    ASN1Integer aSN1Integer = paramDeltaCertificateDescriptor.getSerialNumber();
    AlgorithmIdentifier algorithmIdentifier = paramDeltaCertificateDescriptor.getSignature();
    if (algorithmIdentifier != null && algorithmIdentifier.equals(paramTBSCertificate.getSignature()))
      algorithmIdentifier = null; 
    X500Name x500Name1 = paramDeltaCertificateDescriptor.getIssuer();
    if (x500Name1 != null && x500Name1.equals(paramTBSCertificate.getIssuer()))
      x500Name1 = null; 
    Validity validity = paramDeltaCertificateDescriptor.getValidityObject();
    if (validity != null && validity.equals(paramTBSCertificate.getValidity()))
      validity = null; 
    X500Name x500Name2 = paramDeltaCertificateDescriptor.getSubject();
    if (x500Name2 != null && x500Name2.equals(paramTBSCertificate.getSubject()))
      x500Name2 = null; 
    SubjectPublicKeyInfo subjectPublicKeyInfo = paramDeltaCertificateDescriptor.getSubjectPublicKeyInfo();
    Extensions extensions = paramDeltaCertificateDescriptor.getExtensions();
    if (extensions != null) {
      ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
      Enumeration<ASN1ObjectIdentifier> enumeration = paramExtensions.oids();
      while (enumeration.hasMoreElements()) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = enumeration.nextElement();
        if (Extension.deltaCertificateDescriptor.equals((ASN1Primitive)aSN1ObjectIdentifier))
          continue; 
        Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
        if (extension != null && !extension.equals(paramExtensions.getExtension(aSN1ObjectIdentifier)))
          extensionsGenerator.addExtension(extension); 
      } 
      extensions = extensionsGenerator.isEmpty() ? null : extensionsGenerator.generate();
    } 
    ASN1BitString aSN1BitString = paramDeltaCertificateDescriptor.getSignatureValue();
    return new DeltaCertificateDescriptor(aSN1Integer, algorithmIdentifier, x500Name1, validity, x500Name2, subjectPublicKeyInfo, extensions, aSN1BitString);
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, int paramInt, boolean paramBoolean, ASN1Object paramASN1Object) {
    if (paramASN1Object != null)
      paramASN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(paramBoolean, paramInt, (ASN1Encodable)paramASN1Object)); 
  }
  
  public ASN1Primitive toASN1Primitive() {
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(8);
    aSN1EncodableVector.add((ASN1Encodable)this.serialNumber);
    addOptional(aSN1EncodableVector, 0, true, this.signature);
    addOptional(aSN1EncodableVector, 1, true, (ASN1Object)this.issuer);
    addOptional(aSN1EncodableVector, 2, true, this.validity);
    addOptional(aSN1EncodableVector, 3, true, (ASN1Object)this.subject);
    aSN1EncodableVector.add((ASN1Encodable)this.subjectPublicKeyInfo);
    addOptional(aSN1EncodableVector, 4, true, this.extensions);
    aSN1EncodableVector.add((ASN1Encodable)this.signatureValue);
    return (ASN1Primitive)new DERSequence(aSN1EncodableVector);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\DeltaCertificateDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */