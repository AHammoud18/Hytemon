package org.bouncycastle.tsp;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.asn1.cmp.PKIFreeText;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.tsp.TimeStampResp;

public class TimeStampResponse {
  private final TimeStampResp resp;
  
  private final TimeStampToken timeStampToken;
  
  private static TimeStampResp parseTimeStampResp(byte[] paramArrayOfbyte) throws IOException, TSPException {
    try {
      return TimeStampResp.getInstance(paramArrayOfbyte);
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new TSPException("malformed timestamp response: " + illegalArgumentException, illegalArgumentException);
    } catch (ClassCastException classCastException) {
      throw new TSPException("malformed timestamp response: " + classCastException, classCastException);
    } 
  }
  
  private static TimeStampResp parseTimeStampResp(InputStream paramInputStream) throws IOException, TSPException {
    try {
      return TimeStampResp.getInstance((new ASN1InputStream(paramInputStream)).readObject());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new TSPException("malformed timestamp response: " + illegalArgumentException, illegalArgumentException);
    } catch (ClassCastException classCastException) {
      throw new TSPException("malformed timestamp response: " + classCastException, classCastException);
    } 
  }
  
  public TimeStampResponse(TimeStampResp paramTimeStampResp) throws TSPException, IOException {
    this.resp = paramTimeStampResp;
    ContentInfo contentInfo = paramTimeStampResp.getTimeStampToken();
    this.timeStampToken = (contentInfo == null) ? null : new TimeStampToken(contentInfo);
  }
  
  public TimeStampResponse(byte[] paramArrayOfbyte) throws TSPException, IOException {
    this(parseTimeStampResp(paramArrayOfbyte));
  }
  
  public TimeStampResponse(InputStream paramInputStream) throws TSPException, IOException {
    this(parseTimeStampResp(paramInputStream));
  }
  
  TimeStampResponse(DLSequence paramDLSequence) throws TSPException, IOException {
    try {
      this.resp = TimeStampResp.getInstance(paramDLSequence);
      this.timeStampToken = new TimeStampToken(ContentInfo.getInstance(paramDLSequence.getObjectAt(1)));
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new TSPException("malformed timestamp response: " + illegalArgumentException, illegalArgumentException);
    } catch (ClassCastException classCastException) {
      throw new TSPException("malformed timestamp response: " + classCastException, classCastException);
    } 
  }
  
  public int getStatus() {
    return this.resp.getStatus().getStatusObject().intValueExact();
  }
  
  public String getStatusString() {
    if (this.resp.getStatus().getStatusString() == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    PKIFreeText pKIFreeText = this.resp.getStatus().getStatusString();
    for (byte b = 0; b != pKIFreeText.size(); b++)
      stringBuilder.append(pKIFreeText.getStringAtUTF8(b).getString()); 
    return stringBuilder.toString();
  }
  
  public PKIFailureInfo getFailInfo() {
    return (this.resp.getStatus().getFailInfo() != null) ? new PKIFailureInfo(this.resp.getStatus().getFailInfo()) : null;
  }
  
  public TimeStampToken getTimeStampToken() {
    return this.timeStampToken;
  }
  
  public void validate(TimeStampRequest paramTimeStampRequest) throws TSPException {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getTimeStampToken : ()Lorg/bouncycastle/tsp/TimeStampToken;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull -> 206
    //   9: aload_2
    //   10: invokevirtual getTimeStampInfo : ()Lorg/bouncycastle/tsp/TimeStampTokenInfo;
    //   13: astore_3
    //   14: aload_1
    //   15: invokevirtual getNonce : ()Ljava/math/BigInteger;
    //   18: ifnull -> 45
    //   21: aload_1
    //   22: invokevirtual getNonce : ()Ljava/math/BigInteger;
    //   25: aload_3
    //   26: invokevirtual getNonce : ()Ljava/math/BigInteger;
    //   29: invokevirtual equals : (Ljava/lang/Object;)Z
    //   32: ifne -> 45
    //   35: new org/bouncycastle/tsp/TSPValidationException
    //   38: dup
    //   39: ldc 'response contains wrong nonce value.'
    //   41: invokespecial <init> : (Ljava/lang/String;)V
    //   44: athrow
    //   45: aload_0
    //   46: invokevirtual getStatus : ()I
    //   49: ifeq -> 70
    //   52: aload_0
    //   53: invokevirtual getStatus : ()I
    //   56: iconst_1
    //   57: if_icmpeq -> 70
    //   60: new org/bouncycastle/tsp/TSPValidationException
    //   63: dup
    //   64: ldc 'time stamp token found in failed request.'
    //   66: invokespecial <init> : (Ljava/lang/String;)V
    //   69: athrow
    //   70: aload_3
    //   71: invokevirtual getMessageImprintAlgOID : ()Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;
    //   74: aload_1
    //   75: invokevirtual getMessageImprintAlgOID : ()Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;
    //   78: invokevirtual equals : (Lorg/bouncycastle/asn1/ASN1Primitive;)Z
    //   81: ifne -> 94
    //   84: new org/bouncycastle/tsp/TSPValidationException
    //   87: dup
    //   88: ldc 'response for different message imprint algorithm.'
    //   90: invokespecial <init> : (Ljava/lang/String;)V
    //   93: athrow
    //   94: aload_1
    //   95: invokevirtual getMessageImprintDigest : ()[B
    //   98: aload_3
    //   99: invokevirtual getMessageImprintDigest : ()[B
    //   102: invokestatic constantTimeAreEqual : ([B[B)Z
    //   105: ifne -> 118
    //   108: new org/bouncycastle/tsp/TSPValidationException
    //   111: dup
    //   112: ldc 'response for different message imprint digest.'
    //   114: invokespecial <init> : (Ljava/lang/String;)V
    //   117: athrow
    //   118: aload_2
    //   119: invokevirtual getSignedAttributes : ()Lorg/bouncycastle/asn1/cms/AttributeTable;
    //   122: getstatic org/bouncycastle/asn1/pkcs/PKCSObjectIdentifiers.id_aa_signingCertificate : Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;
    //   125: invokevirtual get : (Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;)Lorg/bouncycastle/asn1/cms/Attribute;
    //   128: astore #4
    //   130: aload_2
    //   131: invokevirtual getSignedAttributes : ()Lorg/bouncycastle/asn1/cms/AttributeTable;
    //   134: getstatic org/bouncycastle/asn1/pkcs/PKCSObjectIdentifiers.id_aa_signingCertificateV2 : Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;
    //   137: invokevirtual get : (Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;)Lorg/bouncycastle/asn1/cms/Attribute;
    //   140: astore #5
    //   142: aload #4
    //   144: ifnonnull -> 162
    //   147: aload #5
    //   149: ifnonnull -> 162
    //   152: new org/bouncycastle/tsp/TSPValidationException
    //   155: dup
    //   156: ldc 'no signing certificate attribute present.'
    //   158: invokespecial <init> : (Ljava/lang/String;)V
    //   161: athrow
    //   162: aload #4
    //   164: ifnull -> 172
    //   167: aload #5
    //   169: ifnull -> 172
    //   172: aload_1
    //   173: invokevirtual getReqPolicy : ()Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;
    //   176: ifnull -> 203
    //   179: aload_1
    //   180: invokevirtual getReqPolicy : ()Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;
    //   183: aload_3
    //   184: invokevirtual getPolicy : ()Lorg/bouncycastle/asn1/ASN1ObjectIdentifier;
    //   187: invokevirtual equals : (Lorg/bouncycastle/asn1/ASN1Primitive;)Z
    //   190: ifne -> 203
    //   193: new org/bouncycastle/tsp/TSPValidationException
    //   196: dup
    //   197: ldc 'TSA policy wrong for request.'
    //   199: invokespecial <init> : (Ljava/lang/String;)V
    //   202: athrow
    //   203: goto -> 231
    //   206: aload_0
    //   207: invokevirtual getStatus : ()I
    //   210: ifeq -> 221
    //   213: aload_0
    //   214: invokevirtual getStatus : ()I
    //   217: iconst_1
    //   218: if_icmpne -> 231
    //   221: new org/bouncycastle/tsp/TSPValidationException
    //   224: dup
    //   225: ldc 'no time stamp token found and one expected.'
    //   227: invokespecial <init> : (Ljava/lang/String;)V
    //   230: athrow
    //   231: return
  }
  
  public byte[] getEncoded() throws IOException {
    return this.resp.getEncoded();
  }
  
  public byte[] getEncoded(String paramString) throws IOException {
    DLSequence dLSequence;
    TimeStampResp timeStampResp = this.resp;
    if ("DL".equals(paramString))
      dLSequence = (this.timeStampToken == null) ? new DLSequence((ASN1Encodable)this.resp.getStatus()) : new DLSequence((ASN1Encodable)this.resp.getStatus(), (ASN1Encodable)this.timeStampToken.toCMSSignedData().toASN1Structure()); 
    return dLSequence.getEncoded(paramString);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\tsp\TimeStampResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */