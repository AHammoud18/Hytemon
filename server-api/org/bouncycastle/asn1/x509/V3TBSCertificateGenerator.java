package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;

public class V3TBSCertificateGenerator {
  private static final DERTaggedObject VERSION = new DERTaggedObject(true, 0, (ASN1Encodable)new ASN1Integer(2L));
  
  ASN1Integer serialNumber;
  
  AlgorithmIdentifier signature;
  
  X500Name issuer;
  
  Validity validity;
  
  Time startDate;
  
  Time endDate;
  
  X500Name subject;
  
  SubjectPublicKeyInfo subjectPublicKeyInfo;
  
  Extensions extensions;
  
  private boolean altNamePresentAndCritical;
  
  private DERBitString issuerUniqueID;
  
  private DERBitString subjectUniqueID;
  
  public void setSerialNumber(ASN1Integer paramASN1Integer) {
    this.serialNumber = paramASN1Integer;
  }
  
  public void setSignature(AlgorithmIdentifier paramAlgorithmIdentifier) {
    this.signature = paramAlgorithmIdentifier;
  }
  
  public void setIssuer(X509Name paramX509Name) {
    this.issuer = X500Name.getInstance(paramX509Name);
  }
  
  public void setIssuer(X500Name paramX500Name) {
    this.issuer = paramX500Name;
  }
  
  public void setValidity(Validity paramValidity) {
    this.validity = paramValidity;
    this.startDate = null;
    this.endDate = null;
  }
  
  public void setStartDate(Time paramTime) {
    this.validity = null;
    this.startDate = paramTime;
  }
  
  public void setStartDate(ASN1UTCTime paramASN1UTCTime) {
    setStartDate(new Time((ASN1Primitive)paramASN1UTCTime));
  }
  
  public void setEndDate(Time paramTime) {
    this.validity = null;
    this.endDate = paramTime;
  }
  
  public void setEndDate(ASN1UTCTime paramASN1UTCTime) {
    setEndDate(new Time((ASN1Primitive)paramASN1UTCTime));
  }
  
  public void setSubject(X509Name paramX509Name) {
    this.subject = X500Name.getInstance(paramX509Name.toASN1Primitive());
  }
  
  public void setSubject(X500Name paramX500Name) {
    this.subject = paramX500Name;
  }
  
  public void setIssuerUniqueID(DERBitString paramDERBitString) {
    this.issuerUniqueID = paramDERBitString;
  }
  
  public void setSubjectUniqueID(DERBitString paramDERBitString) {
    this.subjectUniqueID = paramDERBitString;
  }
  
  public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) {
    this.subjectPublicKeyInfo = paramSubjectPublicKeyInfo;
  }
  
  public void setExtensions(X509Extensions paramX509Extensions) {
    setExtensions(Extensions.getInstance(paramX509Extensions));
  }
  
  public void setExtensions(Extensions paramExtensions) {
    this.extensions = paramExtensions;
    if (paramExtensions != null) {
      Extension extension = paramExtensions.getExtension(Extension.subjectAlternativeName);
      if (extension != null && extension.isCritical())
        this.altNamePresentAndCritical = true; 
    } 
  }
  
  public ASN1Sequence generatePreTBSCertificate() {
    if (this.signature != null)
      throw new IllegalStateException("signature field should not be set in PreTBSCertificate"); 
    if (this.serialNumber == null || this.issuer == null || (this.validity == null && (this.startDate == null || this.endDate == null)) || (this.subject == null && !this.altNamePresentAndCritical) || this.subjectPublicKeyInfo == null)
      throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator"); 
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(9);
    aSN1EncodableVector.add((ASN1Encodable)VERSION);
    aSN1EncodableVector.add((ASN1Encodable)this.serialNumber);
    aSN1EncodableVector.add((ASN1Encodable)this.issuer);
    aSN1EncodableVector.add((this.validity != null) ? (ASN1Encodable)this.validity : (ASN1Encodable)new Validity(this.startDate, this.endDate));
    aSN1EncodableVector.add((this.subject != null) ? (ASN1Encodable)this.subject : (ASN1Encodable)X500Name.getInstance(new DERSequence()));
    aSN1EncodableVector.add((ASN1Encodable)this.subjectPublicKeyInfo);
    if (this.issuerUniqueID != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 1, (ASN1Encodable)this.issuerUniqueID)); 
    if (this.subjectUniqueID != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 2, (ASN1Encodable)this.subjectUniqueID)); 
    if (this.extensions != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(true, 3, (ASN1Encodable)this.extensions)); 
    return (ASN1Sequence)new DERSequence(aSN1EncodableVector);
  }
  
  public TBSCertificate generateTBSCertificate() {
    if (this.serialNumber == null || this.signature == null || this.issuer == null || (this.validity == null && (this.startDate == null || this.endDate == null)) || (this.subject == null && !this.altNamePresentAndCritical) || this.subjectPublicKeyInfo == null)
      throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator"); 
    return new TBSCertificate(new ASN1Integer(2L), this.serialNumber, this.signature, this.issuer, (this.validity != null) ? this.validity : new Validity(this.startDate, this.endDate), (this.subject != null) ? this.subject : X500Name.getInstance(new DERSequence()), this.subjectPublicKeyInfo, (ASN1BitString)this.issuerUniqueID, (ASN1BitString)this.subjectUniqueID, this.extensions);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\V3TBSCertificateGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */