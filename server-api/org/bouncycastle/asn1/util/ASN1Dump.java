package org.bouncycastle.asn1.util;

import org.bouncycastle.asn1.ASN1BMPString;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1External;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1GraphicString;
import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1NumericString;
import org.bouncycastle.asn1.ASN1ObjectDescriptor;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1PrintableString;
import org.bouncycastle.asn1.ASN1RelativeOID;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1T61String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.ASN1UTF8String;
import org.bouncycastle.asn1.ASN1Util;
import org.bouncycastle.asn1.ASN1VideotexString;
import org.bouncycastle.asn1.ASN1VisibleString;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class ASN1Dump {
  private static final String TAB = "    ";
  
  private static final int SAMPLE_SIZE = 32;
  
  static void _dumpAsString(String paramString, boolean paramBoolean, ASN1Primitive paramASN1Primitive, StringBuilder paramStringBuilder) {
    String str = Strings.lineSeparator();
    paramStringBuilder.append(paramString);
    if (paramASN1Primitive instanceof org.bouncycastle.asn1.ASN1Null) {
      paramStringBuilder.append("NULL");
      paramStringBuilder.append(str);
    } else if (paramASN1Primitive instanceof ASN1Sequence) {
      if (paramASN1Primitive instanceof org.bouncycastle.asn1.BERSequence) {
        paramStringBuilder.append("BER Sequence");
      } else if (paramASN1Primitive instanceof org.bouncycastle.asn1.DERSequence) {
        paramStringBuilder.append("DER Sequence");
      } else {
        paramStringBuilder.append("Sequence");
      } 
      paramStringBuilder.append(str);
      ASN1Sequence aSN1Sequence = (ASN1Sequence)paramASN1Primitive;
      String str1 = paramString + "    ";
      byte b = 0;
      int i = aSN1Sequence.size();
      while (b < i) {
        _dumpAsString(str1, paramBoolean, aSN1Sequence.getObjectAt(b).toASN1Primitive(), paramStringBuilder);
        b++;
      } 
    } else if (paramASN1Primitive instanceof ASN1Set) {
      if (paramASN1Primitive instanceof org.bouncycastle.asn1.BERSet) {
        paramStringBuilder.append("BER Set");
      } else if (paramASN1Primitive instanceof org.bouncycastle.asn1.DERSet) {
        paramStringBuilder.append("DER Set");
      } else {
        paramStringBuilder.append("Set");
      } 
      paramStringBuilder.append(str);
      ASN1Set aSN1Set = (ASN1Set)paramASN1Primitive;
      String str1 = paramString + "    ";
      byte b = 0;
      int i = aSN1Set.size();
      while (b < i) {
        _dumpAsString(str1, paramBoolean, aSN1Set.getObjectAt(b).toASN1Primitive(), paramStringBuilder);
        b++;
      } 
    } else if (paramASN1Primitive instanceof ASN1TaggedObject) {
      if (paramASN1Primitive instanceof org.bouncycastle.asn1.BERTaggedObject) {
        paramStringBuilder.append("BER Tagged ");
      } else if (paramASN1Primitive instanceof org.bouncycastle.asn1.DERTaggedObject) {
        paramStringBuilder.append("DER Tagged ");
      } else {
        paramStringBuilder.append("Tagged ");
      } 
      ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)paramASN1Primitive;
      paramStringBuilder.append(ASN1Util.getTagText(aSN1TaggedObject));
      if (!aSN1TaggedObject.isExplicit())
        paramStringBuilder.append(" IMPLICIT"); 
      paramStringBuilder.append(str);
      String str1 = paramString + "    ";
      _dumpAsString(str1, paramBoolean, aSN1TaggedObject.getBaseObject().toASN1Primitive(), paramStringBuilder);
    } else if (paramASN1Primitive instanceof ASN1ObjectIdentifier) {
      paramStringBuilder.append("ObjectIdentifier(" + ((ASN1ObjectIdentifier)paramASN1Primitive).getId() + ")" + str);
    } else if (paramASN1Primitive instanceof ASN1RelativeOID) {
      paramStringBuilder.append("RelativeOID(" + ((ASN1RelativeOID)paramASN1Primitive).getId() + ")" + str);
    } else if (paramASN1Primitive instanceof ASN1Boolean) {
      paramStringBuilder.append("Boolean(" + ((ASN1Boolean)paramASN1Primitive).isTrue() + ")" + str);
    } else if (paramASN1Primitive instanceof ASN1Integer) {
      paramStringBuilder.append("Integer(" + ((ASN1Integer)paramASN1Primitive).getValue() + ")" + str);
    } else if (paramASN1Primitive instanceof ASN1OctetString) {
      ASN1OctetString aSN1OctetString = (ASN1OctetString)paramASN1Primitive;
      if (paramASN1Primitive instanceof org.bouncycastle.asn1.BEROctetString) {
        paramStringBuilder.append("BER Constructed Octet String[");
      } else {
        paramStringBuilder.append("DER Octet String[");
      } 
      paramStringBuilder.append(aSN1OctetString.getOctetsLength() + "]" + str);
      if (paramBoolean)
        dumpBinaryDataAsString(paramStringBuilder, paramString, aSN1OctetString.getOctets()); 
    } else if (paramASN1Primitive instanceof ASN1BitString) {
      ASN1BitString aSN1BitString = (ASN1BitString)paramASN1Primitive;
      if (aSN1BitString instanceof org.bouncycastle.asn1.DERBitString) {
        paramStringBuilder.append("DER Bit String[");
      } else if (aSN1BitString instanceof org.bouncycastle.asn1.DLBitString) {
        paramStringBuilder.append("DL Bit String[");
      } else {
        paramStringBuilder.append("BER Bit String[");
      } 
      paramStringBuilder.append(aSN1BitString.getBytesLength() + ", " + aSN1BitString.getPadBits() + "]" + str);
      if (paramBoolean)
        dumpBinaryDataAsString(paramStringBuilder, paramString, aSN1BitString.getBytes()); 
    } else if (paramASN1Primitive instanceof ASN1IA5String) {
      paramStringBuilder.append("IA5String(" + ((ASN1IA5String)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1UTF8String) {
      paramStringBuilder.append("UTF8String(" + ((ASN1UTF8String)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1NumericString) {
      paramStringBuilder.append("NumericString(" + ((ASN1NumericString)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1PrintableString) {
      paramStringBuilder.append("PrintableString(" + ((ASN1PrintableString)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1VisibleString) {
      paramStringBuilder.append("VisibleString(" + ((ASN1VisibleString)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1BMPString) {
      paramStringBuilder.append("BMPString(" + ((ASN1BMPString)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1T61String) {
      paramStringBuilder.append("T61String(" + ((ASN1T61String)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1GraphicString) {
      paramStringBuilder.append("GraphicString(" + ((ASN1GraphicString)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1VideotexString) {
      paramStringBuilder.append("VideotexString(" + ((ASN1VideotexString)paramASN1Primitive).getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1UTCTime) {
      paramStringBuilder.append("UTCTime(" + ((ASN1UTCTime)paramASN1Primitive).getTime() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1GeneralizedTime) {
      paramStringBuilder.append("GeneralizedTime(" + ((ASN1GeneralizedTime)paramASN1Primitive).getTime() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1Enumerated) {
      ASN1Enumerated aSN1Enumerated = (ASN1Enumerated)paramASN1Primitive;
      paramStringBuilder.append("DER Enumerated(" + aSN1Enumerated.getValue() + ")" + str);
    } else if (paramASN1Primitive instanceof ASN1ObjectDescriptor) {
      ASN1ObjectDescriptor aSN1ObjectDescriptor = (ASN1ObjectDescriptor)paramASN1Primitive;
      paramStringBuilder.append("ObjectDescriptor(" + aSN1ObjectDescriptor.getBaseGraphicString().getString() + ") " + str);
    } else if (paramASN1Primitive instanceof ASN1External) {
      ASN1External aSN1External = (ASN1External)paramASN1Primitive;
      paramStringBuilder.append("External " + str);
      String str1 = paramString + "    ";
      if (aSN1External.getDirectReference() != null)
        paramStringBuilder.append(str1 + "Direct Reference: " + aSN1External.getDirectReference().getId() + str); 
      if (aSN1External.getIndirectReference() != null)
        paramStringBuilder.append(str1 + "Indirect Reference: " + aSN1External.getIndirectReference().toString() + str); 
      if (aSN1External.getDataValueDescriptor() != null)
        _dumpAsString(str1, paramBoolean, aSN1External.getDataValueDescriptor(), paramStringBuilder); 
      paramStringBuilder.append(str1 + "Encoding: " + aSN1External.getEncoding() + str);
      _dumpAsString(str1, paramBoolean, aSN1External.getExternalContent(), paramStringBuilder);
    } else {
      paramStringBuilder.append(paramASN1Primitive.toString() + str);
    } 
  }
  
  public static String dumpAsString(Object paramObject) {
    return dumpAsString(paramObject, false);
  }
  
  public static String dumpAsString(Object paramObject, boolean paramBoolean) {
    ASN1Primitive aSN1Primitive;
    if (paramObject instanceof ASN1Primitive) {
      aSN1Primitive = (ASN1Primitive)paramObject;
    } else if (paramObject instanceof ASN1Encodable) {
      aSN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
    } else {
      return "unknown object type " + paramObject.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    _dumpAsString("", paramBoolean, aSN1Primitive, stringBuilder);
    return stringBuilder.toString();
  }
  
  private static void dumpBinaryDataAsString(StringBuilder paramStringBuilder, String paramString, byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte.length < 1)
      return; 
    String str = Strings.lineSeparator();
    paramString = paramString + "    ";
    for (byte b = 0; b < paramArrayOfbyte.length; b += 32) {
      int i = paramArrayOfbyte.length - b;
      int j = Math.min(i, 32);
      paramStringBuilder.append(paramString);
      paramStringBuilder.append(Hex.toHexString(paramArrayOfbyte, b, j));
      for (int k = j; k < 32; k++)
        paramStringBuilder.append("  "); 
      paramStringBuilder.append("    ");
      appendAscString(paramStringBuilder, paramArrayOfbyte, b, j);
      paramStringBuilder.append(str);
    } 
  }
  
  private static void appendAscString(StringBuilder paramStringBuilder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    for (int i = paramInt1; i != paramInt1 + paramInt2; i++) {
      if (paramArrayOfbyte[i] >= 32 && paramArrayOfbyte[i] <= 126)
        paramStringBuilder.append((char)paramArrayOfbyte[i]); 
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn\\util\ASN1Dump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */