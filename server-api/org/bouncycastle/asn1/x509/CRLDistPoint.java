package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Strings;

public class CRLDistPoint extends ASN1Object {
  ASN1Sequence seq = null;
  
  public static CRLDistPoint getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public static CRLDistPoint getInstance(Object paramObject) {
    return (paramObject instanceof CRLDistPoint) ? (CRLDistPoint)paramObject : ((paramObject != null) ? new CRLDistPoint(ASN1Sequence.getInstance(paramObject)) : null);
  }
  
  public static CRLDistPoint fromExtensions(Extensions paramExtensions) {
    return getInstance(Extensions.getExtensionParsedValue(paramExtensions, Extension.cRLDistributionPoints));
  }
  
  private CRLDistPoint(ASN1Sequence paramASN1Sequence) {
    this.seq = paramASN1Sequence;
  }
  
  public CRLDistPoint(DistributionPoint[] paramArrayOfDistributionPoint) {
    this.seq = (ASN1Sequence)new DERSequence((ASN1Encodable[])paramArrayOfDistributionPoint);
  }
  
  public DistributionPoint[] getDistributionPoints() {
    DistributionPoint[] arrayOfDistributionPoint = new DistributionPoint[this.seq.size()];
    for (byte b = 0; b != this.seq.size(); b++)
      arrayOfDistributionPoint[b] = DistributionPoint.getInstance(this.seq.getObjectAt(b)); 
    return arrayOfDistributionPoint;
  }
  
  public ASN1Primitive toASN1Primitive() {
    return (ASN1Primitive)this.seq;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    stringBuilder.append("CRLDistPoint:");
    stringBuilder.append(str);
    DistributionPoint[] arrayOfDistributionPoint = getDistributionPoints();
    for (byte b = 0; b != arrayOfDistributionPoint.length; b++) {
      stringBuilder.append("    ");
      stringBuilder.append(arrayOfDistributionPoint[b]);
      stringBuilder.append(str);
    } 
    return stringBuilder.toString();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\CRLDistPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */