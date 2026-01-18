package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Properties;

public class ASN1RelativeOID extends ASN1Primitive {
  static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1RelativeOID.class, 13) {
      ASN1Primitive fromImplicitPrimitive(DEROctetString param1DEROctetString) {
        return ASN1RelativeOID.createPrimitive(param1DEROctetString.getOctets(), false);
      }
    };
  
  private static final int MAX_CONTENTS_LENGTH = 4096;
  
  private static final int MAX_IDENTIFIER_LENGTH = 16383;
  
  private static final long LONG_LIMIT = 72057594037927808L;
  
  private static final ConcurrentMap<ASN1ObjectIdentifier.OidHandle, ASN1RelativeOID> pool = new ConcurrentHashMap<>();
  
  private final byte[] contents;
  
  private String identifier;
  
  public static ASN1RelativeOID fromContents(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      throw new NullPointerException("'contents' cannot be null"); 
    return createPrimitive(paramArrayOfbyte, true);
  }
  
  public static ASN1RelativeOID getInstance(Object paramObject) {
    if (paramObject == null || paramObject instanceof ASN1RelativeOID)
      return (ASN1RelativeOID)paramObject; 
    if (paramObject instanceof ASN1Encodable) {
      ASN1Primitive aSN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
      if (aSN1Primitive instanceof ASN1RelativeOID)
        return (ASN1RelativeOID)aSN1Primitive; 
    } else if (paramObject instanceof byte[]) {
      byte[] arrayOfByte = (byte[])paramObject;
      try {
        return (ASN1RelativeOID)TYPE.fromByteArray(arrayOfByte);
      } catch (IOException iOException) {
        throw new IllegalArgumentException("failed to construct relative OID from byte[]: " + iOException.getMessage());
      } 
    } 
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1RelativeOID getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return (ASN1RelativeOID)TYPE.getContextTagged(paramASN1TaggedObject, paramBoolean);
  }
  
  public static ASN1RelativeOID getTagged(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean) {
    return (ASN1RelativeOID)TYPE.getTagged(paramASN1TaggedObject, paramBoolean);
  }
  
  public static ASN1RelativeOID tryFromID(String paramString) {
    if (paramString == null)
      throw new NullPointerException("'identifier' cannot be null"); 
    if (paramString.length() <= 16383 && isValidIdentifier(paramString, 0)) {
      byte[] arrayOfByte = parseIdentifier(paramString);
      if (arrayOfByte.length <= 4096)
        return new ASN1RelativeOID(arrayOfByte, paramString); 
    } 
    return null;
  }
  
  public ASN1RelativeOID(String paramString) {
    checkIdentifier(paramString);
    byte[] arrayOfByte = parseIdentifier(paramString);
    checkContentsLength(arrayOfByte.length);
    this.contents = arrayOfByte;
    this.identifier = paramString;
  }
  
  private ASN1RelativeOID(byte[] paramArrayOfbyte, String paramString) {
    this.contents = paramArrayOfbyte;
    this.identifier = paramString;
  }
  
  public ASN1RelativeOID branch(String paramString) {
    byte[] arrayOfByte;
    checkIdentifier(paramString);
    if (paramString.length() <= 2) {
      checkContentsLength(this.contents.length + 1);
      int i = paramString.charAt(0) - 48;
      if (paramString.length() == 2) {
        i *= 10;
        i += paramString.charAt(1) - 48;
      } 
      arrayOfByte = Arrays.append(this.contents, (byte)i);
    } else {
      byte[] arrayOfByte1 = parseIdentifier(paramString);
      checkContentsLength(this.contents.length + arrayOfByte1.length);
      arrayOfByte = Arrays.concatenate(this.contents, arrayOfByte1);
    } 
    String str1 = getId();
    String str2 = str1 + "." + paramString;
    return new ASN1RelativeOID(arrayOfByte, str2);
  }
  
  public synchronized String getId() {
    if (this.identifier == null)
      this.identifier = parseContents(this.contents); 
    return this.identifier;
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.contents);
  }
  
  public String toString() {
    return getId();
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive) {
    if (this == paramASN1Primitive)
      return true; 
    if (!(paramASN1Primitive instanceof ASN1RelativeOID))
      return false; 
    ASN1RelativeOID aSN1RelativeOID = (ASN1RelativeOID)paramASN1Primitive;
    return Arrays.areEqual(this.contents, aSN1RelativeOID.contents);
  }
  
  int encodedLength(boolean paramBoolean) {
    return ASN1OutputStream.getLengthOfEncodingDL(paramBoolean, this.contents.length);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream, boolean paramBoolean) throws IOException {
    paramASN1OutputStream.writeEncodingDL(paramBoolean, 13, this.contents);
  }
  
  boolean encodeConstructed() {
    return false;
  }
  
  static void checkContentsLength(int paramInt) {
    if (paramInt > 4096)
      throw new IllegalArgumentException("exceeded relative OID contents length limit"); 
  }
  
  static void checkIdentifier(String paramString) {
    if (paramString == null)
      throw new NullPointerException("'identifier' cannot be null"); 
    if (paramString.length() > 16383)
      throw new IllegalArgumentException("exceeded relative OID contents length limit"); 
    if (!isValidIdentifier(paramString, 0))
      throw new IllegalArgumentException("string " + paramString + " not a valid relative OID"); 
  }
  
  static ASN1RelativeOID createPrimitive(byte[] paramArrayOfbyte, boolean paramBoolean) {
    checkContentsLength(paramArrayOfbyte.length);
    ASN1ObjectIdentifier.OidHandle oidHandle = new ASN1ObjectIdentifier.OidHandle(paramArrayOfbyte);
    ASN1RelativeOID aSN1RelativeOID = pool.get(oidHandle);
    if (aSN1RelativeOID != null)
      return aSN1RelativeOID; 
    if (!isValidContents(paramArrayOfbyte))
      throw new IllegalArgumentException("invalid relative OID contents"); 
    return new ASN1RelativeOID(paramBoolean ? Arrays.clone(paramArrayOfbyte) : paramArrayOfbyte, null);
  }
  
  static boolean isValidContents(byte[] paramArrayOfbyte) {
    if (Properties.isOverrideSet("org.bouncycastle.asn1.allow_wrong_oid_enc"))
      return true; 
    if (paramArrayOfbyte.length < 1)
      return false; 
    boolean bool = true;
    for (byte b = 0; b < paramArrayOfbyte.length; b++) {
      if (bool && (paramArrayOfbyte[b] & 0xFF) == 128)
        return false; 
      bool = ((paramArrayOfbyte[b] & 0x80) == 0) ? true : false;
    } 
    return bool;
  }
  
  static boolean isValidIdentifier(String paramString, int paramInt) {
    byte b = 0;
    int i = paramString.length();
    while (--i >= paramInt) {
      char c = paramString.charAt(i);
      if (c == '.') {
        if (0 == b || (b > 1 && paramString.charAt(i + 1) == '0'))
          return false; 
        b = 0;
        continue;
      } 
      if ('0' <= c && c <= '9') {
        b++;
        continue;
      } 
      return false;
    } 
    return !(0 == b || (b > 1 && paramString.charAt(i + 1) == '0'));
  }
  
  static String parseContents(byte[] paramArrayOfbyte) {
    StringBuilder stringBuilder = new StringBuilder();
    long l = 0L;
    BigInteger bigInteger = null;
    boolean bool = true;
    for (byte b = 0; b != paramArrayOfbyte.length; b++) {
      int i = paramArrayOfbyte[b] & 0xFF;
      if (l <= 72057594037927808L) {
        l += (i & 0x7F);
        if ((i & 0x80) == 0) {
          if (bool) {
            bool = false;
          } else {
            stringBuilder.append('.');
          } 
          stringBuilder.append(l);
          l = 0L;
        } else {
          l <<= 7L;
        } 
      } else {
        if (bigInteger == null)
          bigInteger = BigInteger.valueOf(l); 
        bigInteger = bigInteger.or(BigInteger.valueOf((i & 0x7F)));
        if ((i & 0x80) == 0) {
          if (bool) {
            bool = false;
          } else {
            stringBuilder.append('.');
          } 
          stringBuilder.append(bigInteger);
          bigInteger = null;
          l = 0L;
        } else {
          bigInteger = bigInteger.shiftLeft(7);
        } 
      } 
    } 
    return stringBuilder.toString();
  }
  
  static byte[] parseIdentifier(String paramString) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    OIDTokenizer oIDTokenizer = new OIDTokenizer(paramString);
    while (oIDTokenizer.hasMoreTokens()) {
      String str = oIDTokenizer.nextToken();
      if (str.length() <= 18) {
        writeField(byteArrayOutputStream, Long.parseLong(str));
        continue;
      } 
      writeField(byteArrayOutputStream, new BigInteger(str));
    } 
    return byteArrayOutputStream.toByteArray();
  }
  
  static void writeField(ByteArrayOutputStream paramByteArrayOutputStream, long paramLong) {
    byte[] arrayOfByte = new byte[9];
    byte b = 8;
    arrayOfByte[b] = (byte)((int)paramLong & 0x7F);
    while (paramLong >= 128L) {
      paramLong >>= 7L;
      arrayOfByte[--b] = (byte)((int)paramLong | 0x80);
    } 
    paramByteArrayOutputStream.write(arrayOfByte, b, 9 - b);
  }
  
  static void writeField(ByteArrayOutputStream paramByteArrayOutputStream, BigInteger paramBigInteger) {
    int i = (paramBigInteger.bitLength() + 6) / 7;
    if (i == 0) {
      paramByteArrayOutputStream.write(0);
    } else {
      BigInteger bigInteger = paramBigInteger;
      byte[] arrayOfByte = new byte[i];
      for (int j = i - 1; j >= 0; j--) {
        arrayOfByte[j] = (byte)(bigInteger.intValue() | 0x80);
        bigInteger = bigInteger.shiftRight(7);
      } 
      arrayOfByte[i - 1] = (byte)(arrayOfByte[i - 1] & Byte.MAX_VALUE);
      paramByteArrayOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\ASN1RelativeOID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */