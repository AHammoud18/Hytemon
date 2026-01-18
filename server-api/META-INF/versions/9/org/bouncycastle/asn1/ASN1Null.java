package META-INF.versions.9.org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UniversalType;
import org.bouncycastle.asn1.DERNull;

public abstract class ASN1Null extends ASN1Primitive {
  static final ASN1UniversalType TYPE = (ASN1UniversalType)new Object(org.bouncycastle.asn1.ASN1Null.class, 5);
  
  public static org.bouncycastle.asn1.ASN1Null getInstance(Object paramObject) {
    if (paramObject instanceof org.bouncycastle.asn1.ASN1Null)
      return (org.bouncycastle.asn1.ASN1Null)paramObject; 
    if (paramObject != null)
      try {
        return (org.bouncycastle.asn1.ASN1Null)TYPE.fromByteArray((byte[])paramObject);
      } catch (IOException iOException) {
        throw new IllegalArgumentException("failed to construct NULL from byte[]: " + iOException.getMessage());
      }  
    return null;
  }
  
  public static org.bouncycastle.asn1.ASN1Null getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return (org.bouncycastle.asn1.ASN1Null)TYPE.getContextTagged(paramASN1TaggedObject, paramBoolean);
  }
  
  public static org.bouncycastle.asn1.ASN1Null getTagged(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return (org.bouncycastle.asn1.ASN1Null)TYPE.getTagged(paramASN1TaggedObject, paramBoolean);
  }
  
  public int hashCode() {
    return -1;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive) {
    return !!(paramASN1Primitive instanceof org.bouncycastle.asn1.ASN1Null);
  }
  
  public String toString() {
    return "NULL";
  }
  
  static void checkContentsLength(int paramInt) {
    if (0 != paramInt)
      throw new IllegalStateException("malformed NULL encoding encountered"); 
  }
  
  static org.bouncycastle.asn1.ASN1Null createPrimitive() {
    return (org.bouncycastle.asn1.ASN1Null)DERNull.INSTANCE;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\asn1\ASN1Null.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */