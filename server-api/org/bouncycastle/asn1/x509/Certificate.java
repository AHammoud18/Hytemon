package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;

public class Certificate extends ASN1Object {
  ASN1Sequence seq;
  
  TBSCertificate tbsCert;
  
  AlgorithmIdentifier sigAlgId;
  
  ASN1BitString sig;
  
  public static Certificate getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public static Certificate getInstance(Object paramObject) {
    return (paramObject instanceof Certificate) ? (Certificate)paramObject : ((paramObject != null) ? new Certificate(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  private Certificate(ASN1Sequence paramASN1Sequence) {
    this.seq = paramASN1Sequence;
    if (paramASN1Sequence.size() == 3) {
      this.tbsCert = TBSCertificate.getInstance(paramASN1Sequence.getObjectAt(0));
      this.sigAlgId = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
      this.sig = ASN1BitString.getInstance(paramASN1Sequence.getObjectAt(2));
    } else {
      throw new IllegalArgumentException("sequence wrong size for a certificate");
    } 
  }
  
  public Certificate(TBSCertificate paramTBSCertificate, AlgorithmIdentifier paramAlgorithmIdentifier, ASN1BitString paramASN1BitString) {
    if (paramTBSCertificate == null)
      throw new NullPointerException("'tbsCertificate' cannot be null"); 
    if (paramAlgorithmIdentifier == null)
      throw new NullPointerException("'signatureAlgorithm' cannot be null"); 
    if (paramASN1BitString == null)
      throw new NullPointerException("'signature' cannot be null"); 
    this.tbsCert = paramTBSCertificate;
    this.sigAlgId = paramAlgorithmIdentifier;
    this.sig = paramASN1BitString;
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
    aSN1EncodableVector.add((ASN1Encodable)paramTBSCertificate);
    aSN1EncodableVector.add((ASN1Encodable)paramAlgorithmIdentifier);
    aSN1EncodableVector.add((ASN1Encodable)paramASN1BitString);
    this.seq = (ASN1Sequence)new DERSequence(aSN1EncodableVector);
  }
  
  public TBSCertificate getTBSCertificate() {
    return this.tbsCert;
  }
  
  public ASN1Integer getVersion() {
    return this.tbsCert.getVersion();
  }
  
  public int getVersionNumber() {
    return this.tbsCert.getVersionNumber();
  }
  
  public ASN1Integer getSerialNumber() {
    return this.tbsCert.getSerialNumber();
  }
  
  public X500Name getIssuer() {
    return this.tbsCert.getIssuer();
  }
  
  public Validity getValidity() {
    return this.tbsCert.getValidity();
  }
  
  public Time getStartDate() {
    return this.tbsCert.getStartDate();
  }
  
  public Time getEndDate() {
    return this.tbsCert.getEndDate();
  }
  
  public X500Name getSubject() {
    return this.tbsCert.getSubject();
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
    return this.tbsCert.getSubjectPublicKeyInfo();
  }
  
  public ASN1BitString getIssuerUniqueID() {
    return this.tbsCert.getIssuerUniqueId();
  }
  
  public ASN1BitString getSubjectUniqueID() {
    return this.tbsCert.getSubjectUniqueId();
  }
  
  public Extensions getExtensions() {
    return this.tbsCert.getExtensions();
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm() {
    return this.sigAlgId;
  }
  
  public ASN1BitString getSignature() {
    return this.sig;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)this.seq;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */