package org.bouncycastle.asn1;

import java.io.IOException;

public abstract class ASN1Null extends ASN1Primitive {
  static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1Null.class, 5) {
      ASN1Primitive fromImplicitPrimitive(DEROctetString param1DEROctetString) {
        ASN1Null.checkContentsLength(param1DEROctetString.getOctetsLength());
        return ASN1Null.createPrimitive();
      }
    };
  
  public static ASN1Null getInstance(Object paramObject) {
    if (paramObject instanceof ASN1Null)
      return (ASN1Null)paramObject; 
    if (paramObject != null)
      try {
        return (ASN1Null)TYPE.fromByteArray((byte[])paramObject);
      } catch (IOException iOException) {
        throw new IllegalArgumentException("failed to construct NULL from byte[]: " + iOException.getMessage());
      }  
    return null;
  }
  
  public static ASN1Null getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return (ASN1Null)TYPE.getContextTagged(paramASN1TaggedObject, paramBoolean);
  }
  
  public static ASN1Null getTagged(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return (ASN1Null)TYPE.getTagged(paramASN1TaggedObject, paramBoolean);
  }
  
  public int hashCode() {
    return -1;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive) {
    return !!(paramASN1Primitive instanceof ASN1Null);
  }
  
  public String toString() {
    return "NULL";
  }
  
  static void checkContentsLength(int paramInt) {
    if (0 != paramInt)
      throw new IllegalStateException("malformed NULL encoding encountered"); 
  }
  
  static ASN1Null createPrimitive() {
    return DERNull.INSTANCE;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\ASN1Null.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */