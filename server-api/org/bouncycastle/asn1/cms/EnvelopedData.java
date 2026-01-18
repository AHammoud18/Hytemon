package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class EnvelopedData extends ASN1Object {
  private ASN1Integer version;
  
  private OriginatorInfo originatorInfo;
  
  private ASN1Set recipientInfos;
  
  private EncryptedContentInfo encryptedContentInfo;
  
  private ASN1Set unprotectedAttrs;
  
  public EnvelopedData(OriginatorInfo paramOriginatorInfo, ASN1Set paramASN1Set1, EncryptedContentInfo paramEncryptedContentInfo, ASN1Set paramASN1Set2) {
    this.version = new ASN1Integer(calculateVersion(paramOriginatorInfo, paramASN1Set1, paramASN1Set2));
    this.originatorInfo = paramOriginatorInfo;
    this.recipientInfos = paramASN1Set1;
    this.encryptedContentInfo = paramEncryptedContentInfo;
    this.unprotectedAttrs = paramASN1Set2;
  }
  
  public EnvelopedData(OriginatorInfo paramOriginatorInfo, ASN1Set paramASN1Set, EncryptedContentInfo paramEncryptedContentInfo, Attributes paramAttributes) {
    this.version = new ASN1Integer(calculateVersion(paramOriginatorInfo, paramASN1Set, ASN1Set.getInstance(paramAttributes)));
    this.originatorInfo = paramOriginatorInfo;
    this.recipientInfos = paramASN1Set;
    this.encryptedContentInfo = paramEncryptedContentInfo;
    this.unprotectedAttrs = ASN1Set.getInstance(paramAttributes);
  }
  
  private EnvelopedData(ASN1Sequence paramASN1Sequence) {
    byte b = 0;
    this.version = (ASN1Integer)paramASN1Sequence.getObjectAt(b++);
    ASN1Encodable aSN1Encodable = paramASN1Sequence.getObjectAt(b++);
    if (aSN1Encodable instanceof ASN1TaggedObject) {
      this.originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)aSN1Encodable, false);
      aSN1Encodable = paramASN1Sequence.getObjectAt(b++);
    } 
    this.recipientInfos = ASN1Set.getInstance(aSN1Encodable);
    this.encryptedContentInfo = EncryptedContentInfo.getInstance(paramASN1Sequence.getObjectAt(b++));
    if (paramASN1Sequence.size() > b)
      this.unprotectedAttrs = ASN1Set.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(b), false); 
  }
  
  public static EnvelopedData getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public static EnvelopedData getInstance(Object paramObject) {
    return (paramObject instanceof EnvelopedData) ? (EnvelopedData)paramObject : ((paramObject != null) ? new EnvelopedData(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public ASN1Integer getVersion() {
    return this.version;
  }
  
  public OriginatorInfo getOriginatorInfo() {
    return this.originatorInfo;
  }
  
  public ASN1Set getRecipientInfos() {
    return this.recipientInfos;
  }
  
  public EncryptedContentInfo getEncryptedContentInfo() {
    return this.encryptedContentInfo;
  }
  
  public ASN1Set getUnprotectedAttrs() {
    return this.unprotectedAttrs;
  }
  
  public ASN1Primitive toASN1Primitive() {
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(5);
    aSN1EncodableVector.add((ASN1Encodable)this.version);
    if (this.originatorInfo != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 0, (ASN1Encodable)this.originatorInfo)); 
    aSN1EncodableVector.add((ASN1Encodable)this.recipientInfos);
    aSN1EncodableVector.add((ASN1Encodable)this.encryptedContentInfo);
    if (this.unprotectedAttrs != null)
      aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 1, (ASN1Encodable)this.unprotectedAttrs)); 
    return (ASN1Primitive)new BERSequence(aSN1EncodableVector);
  }
  
  public static int calculateVersion(OriginatorInfo paramOriginatorInfo, ASN1Set paramASN1Set1, ASN1Set paramASN1Set2) {
    if (paramOriginatorInfo != null) {
      ASN1Set aSN1Set1 = paramOriginatorInfo.getCRLs();
      if (aSN1Set1 != null) {
        byte b1 = 0;
        int j = aSN1Set1.size();
        while (b1 < j) {
          ASN1Encodable aSN1Encodable = aSN1Set1.getObjectAt(b1);
          if (aSN1Encodable instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)aSN1Encodable;
            if (aSN1TaggedObject.hasContextTag(1))
              return 4; 
          } 
          b1++;
        } 
      } 
      ASN1Set aSN1Set2 = paramOriginatorInfo.getCertificates();
      if (aSN1Set2 != null) {
        boolean bool1 = false;
        byte b1 = 0;
        int j = aSN1Set2.size();
        while (b1 < j) {
          ASN1Encodable aSN1Encodable = aSN1Set2.getObjectAt(b1);
          if (aSN1Encodable instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)aSN1Encodable;
            if (aSN1TaggedObject.hasContextTag(3))
              return 4; 
            bool1 = (bool1 || aSN1TaggedObject.hasContextTag(2)) ? true : false;
          } 
          b1++;
        } 
        if (bool1)
          return 3; 
      } 
    } 
    boolean bool = true;
    byte b = 0;
    int i = paramASN1Set1.size();
    while (b < i) {
      RecipientInfo recipientInfo = RecipientInfo.getInstance(paramASN1Set1.getObjectAt(b));
      if (recipientInfo.isPasswordOrOther())
        return 3; 
      bool = (bool && recipientInfo.isKeyTransV0()) ? true : false;
      b++;
    } 
    return (paramOriginatorInfo == null && paramASN1Set2 == null && bool) ? 0 : 2;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\cms\EnvelopedData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */