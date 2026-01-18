package org.bouncycastle.asn1.cmc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class TaggedCertificationRequest extends ASN1Object {
  private final BodyPartID bodyPartID;
  
  private final CertificationRequest certificationRequest;
  
  public TaggedCertificationRequest(BodyPartID paramBodyPartID, CertificationRequest paramCertificationRequest) {
    this.bodyPartID = paramBodyPartID;
    this.certificationRequest = paramCertificationRequest;
  }
  
  private TaggedCertificationRequest(ASN1Sequence paramASN1Sequence) {
    if (paramASN1Sequence.size() != 2)
      throw new IllegalArgumentException("incorrect sequence size"); 
    this.bodyPartID = BodyPartID.getInstance(paramASN1Sequence.getObjectAt(0));
    this.certificationRequest = CertificationRequest.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static TaggedCertificationRequest getInstance(Object paramObject) {
    return (paramObject instanceof TaggedCertificationRequest) ? (TaggedCertificationRequest)paramObject : ((paramObject != null) ? new TaggedCertificationRequest(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public static TaggedCertificationRequest getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public BodyPartID getBodyPartID() {
    return this.bodyPartID;
  }
  
  public CertificationRequest getCertificationRequest() {
    return this.certificationRequest;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERSequence((ASN1Encodable)this.bodyPartID, (ASN1Encodable)this.certificationRequest);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\cmc\TaggedCertificationRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */