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
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.Properties;

public class TBSCertificate extends ASN1Object {
  ASN1Sequence seq;
  
  ASN1Integer version;
  
  ASN1Integer serialNumber;
  
  AlgorithmIdentifier signature;
  
  X500Name issuer;
  
  Validity validity;
  
  X500Name subject;
  
  SubjectPublicKeyInfo subjectPublicKeyInfo;
  
  ASN1BitString issuerUniqueId;
  
  ASN1BitString subjectUniqueId;
  
  Extensions extensions;
  
  public static TBSCertificate getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public static TBSCertificate getInstance(Object paramObject) {
    return (paramObject instanceof TBSCertificate) ? (TBSCertificate)paramObject : ((paramObject != null) ? new TBSCertificate(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  private TBSCertificate(ASN1Sequence paramASN1Sequence) {
    byte b = 0;
    this.seq = paramASN1Sequence;
    if (paramASN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) {
      this.version = ASN1Integer.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0), true);
    } else {
      b = -1;
      this.version = new ASN1Integer(0L);
    } 
    boolean bool1 = false;
    boolean bool2 = false;
    if (this.version.hasValue(0)) {
      bool1 = true;
    } else if (this.version.hasValue(1)) {
      bool2 = true;
    } else if (!this.version.hasValue(2)) {
      throw new IllegalArgumentException("version number not recognised");
    } 
    this.serialNumber = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(b + 1));
    this.signature = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(b + 2));
    this.issuer = X500Name.getInstance(paramASN1Sequence.getObjectAt(b + 3));
    this.validity = Validity.getInstance(paramASN1Sequence.getObjectAt(b + 4));
    this.subject = X500Name.getInstance(paramASN1Sequence.getObjectAt(b + 5));
    this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(paramASN1Sequence.getObjectAt(b + 6));
    int i = paramASN1Sequence.size() - b + 6 - 1;
    if (i != 0 && bool1)
      throw new IllegalArgumentException("version 1 certificate contains extra data"); 
    while (i > 0) {
      ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(b + 6 + i);
      switch (aSN1TaggedObject.getTagNo()) {
        case 1:
          this.issuerUniqueId = ASN1BitString.getInstance(aSN1TaggedObject, false);
          break;
        case 2:
          this.subjectUniqueId = ASN1BitString.getInstance(aSN1TaggedObject, false);
          break;
        case 3:
          if (bool2)
            throw new IllegalArgumentException("version 2 certificate cannot contain extensions"); 
          this.extensions = Extensions.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
          break;
        default:
          throw new IllegalArgumentException("Unknown tag encountered in structure: " + aSN1TaggedObject.getTagNo());
      } 
      i--;
    } 
  }
  
  public TBSCertificate(ASN1Integer paramASN1Integer1, ASN1Integer paramASN1Integer2, AlgorithmIdentifier paramAlgorithmIdentifier, X500Name paramX500Name1, Validity paramValidity, X500Name paramX500Name2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, ASN1BitString paramASN1BitString1, ASN1BitString paramASN1BitString2, Extensions paramExtensions) {
    if (paramASN1Integer2 == null)
      throw new NullPointerException("'serialNumber' cannot be null"); 
    if (paramAlgorithmIdentifier == null)
      throw new NullPointerException("'signature' cannot be null"); 
    if (paramX500Name1 == null)
      throw new NullPointerException("'issuer' cannot be null"); 
    if (paramValidity == null)
      throw new NullPointerException("'validity' cannot be null"); 
    if (paramX500Name2 == null)
      throw new NullPointerException("'subject' cannot be null"); 
    if (paramSubjectPublicKeyInfo == null)
      throw new NullPointerException("'subjectPublicKeyInfo' cannot be null"); 
    this.version = (paramASN1Integer1 != null) ? paramASN1Integer1 : new ASN1Integer(0L);
    this.serialNumber = paramASN1Integer2;
    this.signature = paramAlgorithmIdentifier;
    this.issuer = paramX500Name1;
    this.validity = paramValidity;
    this.subject = paramX500Name2;
    this.subjectPublicKeyInfo = paramSubjectPublicKeyInfo;
    this.issuerUniqueId = paramASN1BitString1;
    this.subjectUniqueId = paramASN1BitString2;
    this.extensions = paramExtensions;
    this.seq = null;
  }
  
  public int getVersionNumber() {
    return this.version.intValueExact() + 1;
  }
  
  public ASN1Integer getVersion() {
    return this.version;
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
  
  public Validity getValidity() {
    return this.validity;
  }
  
  public Time getStartDate() {
    return this.validity.getNotBefore();
  }
  
  public Time getEndDate() {
    return this.validity.getNotAfter();
  }
  
  public X500Name getSubject() {
    return this.subject;
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
    return this.subjectPublicKeyInfo;
  }
  
  public ASN1BitString getIssuerUniqueId() {
    return this.issuerUniqueId;
  }
  
  public ASN1BitString getSubjectUniqueId() {
    return this.subjectUniqueId;
  }
  
  public Extensions getExtensions() {
    return this.extensions;
  }
  
  public ASN1Primitive toASN1Primitive() {
    if (this.seq != null)
      if (Properties.getPropertyValue("org.bouncycastle.x509.allow_non-der_tbscert") != null) {
        if (Properties.isOverrideSet("org.bouncycastle.x509.allow_non-der_tbscert"))
          return (ASN1Primitive)this.seq; 
      } else {
        return (ASN1Primitive)this.seq;
      }  
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(10);
    if (!this.version.hasValue(0))
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(true, 0, (ASN1Encodable)this.version)); 
    aSN1EncodableVector.add((ASN1Encodable)this.serialNumber);
    aSN1EncodableVector.add((ASN1Encodable)this.signature);
    aSN1EncodableVector.add((ASN1Encodable)this.issuer);
    aSN1EncodableVector.add((ASN1Encodable)this.validity);
    aSN1EncodableVector.add((ASN1Encodable)this.subject);
    aSN1EncodableVector.add((ASN1Encodable)this.subjectPublicKeyInfo);
    if (this.issuerUniqueId != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 1, (ASN1Encodable)this.issuerUniqueId)); 
    if (this.subjectUniqueId != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 2, (ASN1Encodable)this.subjectUniqueId)); 
    if (this.extensions != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(true, 3, (ASN1Encodable)this.extensions)); 
    return (ASN1Primitive)new DERSequence(aSN1EncodableVector);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\TBSCertificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */