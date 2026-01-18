package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1Util;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.util.Strings;

public class DistributionPointName extends ASN1Object implements ASN1Choice {
  public static final int FULL_NAME = 0;
  
  public static final int NAME_RELATIVE_TO_CRL_ISSUER = 1;
  
  private final ASN1Encodable name;
  
  private final int type;
  
  public static DistributionPointName getInstance(Object paramObject) {
    if (paramObject == null || paramObject instanceof DistributionPointName)
      return (DistributionPointName)paramObject; 
    if (paramObject instanceof ASN1TaggedObject)
      return new DistributionPointName((ASN1TaggedObject)paramObject); 
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public static DistributionPointName getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return getInstance(ASN1Util.getInstanceChoiceBaseObject(paramASN1TaggedObject, paramBoolean, "DistributionPointName"));
  }
  
  public static DistributionPointName getTagged(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return getInstance(ASN1Util.getTaggedChoiceBaseObject(paramASN1TaggedObject, paramBoolean, "DistributionPointName"));
  }
  
  public DistributionPointName(int paramInt, ASN1Encodable paramASN1Encodable) {
    this.type = paramInt;
    this.name = paramASN1Encodable;
  }
  
  public DistributionPointName(GeneralNames paramGeneralNames) {
    this(0, (ASN1Encodable)paramGeneralNames);
  }
  
  public int getType() {
    return this.type;
  }
  
  public ASN1Encodable getName() {
    return this.name;
  }
  
  public DistributionPointName(ASN1TaggedObject paramASN1TaggedObject) {
    this.type = paramASN1TaggedObject.getTagNo();
    if (paramASN1TaggedObject.hasContextTag(0)) {
      this.name = (ASN1Encodable)GeneralNames.getInstance(paramASN1TaggedObject, false);
    } else if (paramASN1TaggedObject.hasContextTag(1)) {
      this.name = (ASN1Encodable)ASN1Set.getInstance(paramASN1TaggedObject, false);
    } else {
      throw new IllegalArgumentException("unknown tag: " + ASN1Util.getTagText(paramASN1TaggedObject));
    } 
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)new DERTaggedObject(false, this.type, this.name);
  }
  
  public String toString() {
    String str = Strings.lineSeparator();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("DistributionPointName: [");
    stringBuilder.append(str);
    if (this.type == 0) {
      appendObject(stringBuilder, str, "fullName", this.name.toString());
    } else {
      appendObject(stringBuilder, str, "nameRelativeToCRLIssuer", this.name.toString());
    } 
    stringBuilder.append("]");
    stringBuilder.append(str);
    return stringBuilder.toString();
  }
  
  private void appendObject(StringBuilder paramStringBuilder, String paramString1, String paramString2, String paramString3) {
    String str = "    ";
    paramStringBuilder.append(str);
    paramStringBuilder.append(paramString2);
    paramStringBuilder.append(":");
    paramStringBuilder.append(paramString1);
    paramStringBuilder.append(str);
    paramStringBuilder.append(str);
    paramStringBuilder.append(paramString3);
    paramStringBuilder.append(paramString1);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\DistributionPointName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */