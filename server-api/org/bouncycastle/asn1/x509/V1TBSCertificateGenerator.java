package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;

public class V1TBSCertificateGenerator {
  DERTaggedObject version = new DERTaggedObject(true, 0, (ASN1Encodable)new ASN1Integer(0L));
  
  ASN1Integer serialNumber;
  
  AlgorithmIdentifier signature;
  
  X500Name issuer;
  
  Validity validity;
  
  Time startDate;
  
  Time endDate;
  
  X500Name subject;
  
  SubjectPublicKeyInfo subjectPublicKeyInfo;
  
  public void setSerialNumber(ASN1Integer paramASN1Integer) {
    this.serialNumber = paramASN1Integer;
  }
  
  public void setSignature(AlgorithmIdentifier paramAlgorithmIdentifier) {
    this.signature = paramAlgorithmIdentifier;
  }
  
  public void setIssuer(X509Name paramX509Name) {
    this.issuer = X500Name.getInstance(paramX509Name.toASN1Primitive());
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
  
  public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) {
    this.subjectPublicKeyInfo = paramSubjectPublicKeyInfo;
  }
  
  public TBSCertificate generateTBSCertificate() {
    if (this.serialNumber == null || this.signature == null || this.issuer == null || (this.validity == null && (this.startDate == null || this.endDate == null)) || this.subject == null || this.subjectPublicKeyInfo == null)
      throw new IllegalStateException("not all mandatory fields set in V1 TBScertificate generator"); 
    return new TBSCertificate(new ASN1Integer(0L), this.serialNumber, this.signature, this.issuer, (this.validity != null) ? this.validity : new Validity(this.startDate, this.endDate), this.subject, this.subjectPublicKeyInfo, null, null, null);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\V1TBSCertificateGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */