package org.bouncycastle.tsp;

import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.tsp.MessageImprint;
import org.bouncycastle.asn1.tsp.TimeStampReq;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;

public class TimeStampRequestGenerator {
  private static final DefaultDigestAlgorithmIdentifierFinder DEFAULT_DIGEST_ALG_FINDER = new DefaultDigestAlgorithmIdentifierFinder();
  
  private final ExtensionsGenerator extGenerator = new ExtensionsGenerator();
  
  private final DigestAlgorithmIdentifierFinder digestAlgFinder;
  
  private ASN1ObjectIdentifier reqPolicy;
  
  private ASN1Boolean certReq;
  
  public TimeStampRequestGenerator() {
    this((DigestAlgorithmIdentifierFinder)DEFAULT_DIGEST_ALG_FINDER);
  }
  
  public TimeStampRequestGenerator(DigestAlgorithmIdentifierFinder paramDigestAlgorithmIdentifierFinder) {
    if (paramDigestAlgorithmIdentifierFinder == null)
      throw new NullPointerException("'digestAlgFinder' cannot be null"); 
    this.digestAlgFinder = paramDigestAlgorithmIdentifierFinder;
  }
  
  public void setReqPolicy(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    this.reqPolicy = paramASN1ObjectIdentifier;
  }
  
  public void setReqPolicy(String paramString) {
    setReqPolicy(new ASN1ObjectIdentifier(paramString));
  }
  
  public void setCertReq(ASN1Boolean paramASN1Boolean) {
    this.certReq = paramASN1Boolean;
  }
  
  public void setCertReq(boolean paramBoolean) {
    setCertReq(ASN1Boolean.getInstance(paramBoolean));
  }
  
  public void addExtension(String paramString, boolean paramBoolean, ASN1Encodable paramASN1Encodable) throws IOException {
    addExtension(new ASN1ObjectIdentifier(paramString), paramBoolean, paramASN1Encodable);
  }
  
  public void addExtension(String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) {
    addExtension(new ASN1ObjectIdentifier(paramString), paramBoolean, paramArrayOfbyte);
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable) throws TSPIOException {
    TSPUtil.addExtension(this.extGenerator, paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfbyte) {
    this.extGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramArrayOfbyte);
  }
  
  public TimeStampRequest generate(String paramString, byte[] paramArrayOfbyte) {
    return generate(paramString, paramArrayOfbyte, (BigInteger)null);
  }
  
  public TimeStampRequest generate(String paramString, byte[] paramArrayOfbyte, BigInteger paramBigInteger) {
    if (paramString == null)
      throw new NullPointerException("'digestAlgorithmOID' cannot be null"); 
    return generate(new ASN1ObjectIdentifier(paramString), paramArrayOfbyte, paramBigInteger);
  }
  
  public TimeStampRequest generate(ASN1ObjectIdentifier paramASN1ObjectIdentifier, byte[] paramArrayOfbyte) {
    return generate(paramASN1ObjectIdentifier, paramArrayOfbyte, (BigInteger)null);
  }
  
  public TimeStampRequest generate(ASN1ObjectIdentifier paramASN1ObjectIdentifier, byte[] paramArrayOfbyte, BigInteger paramBigInteger) {
    return generate(this.digestAlgFinder.find(paramASN1ObjectIdentifier), paramArrayOfbyte, paramBigInteger);
  }
  
  public TimeStampRequest generate(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte) {
    return generate(paramAlgorithmIdentifier, paramArrayOfbyte, (BigInteger)null);
  }
  
  public TimeStampRequest generate(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfbyte, BigInteger paramBigInteger) {
    if (paramAlgorithmIdentifier == null)
      throw new NullPointerException("'digestAlgorithmID' cannot be null"); 
    MessageImprint messageImprint = new MessageImprint(paramAlgorithmIdentifier, paramArrayOfbyte);
    ASN1Integer aSN1Integer = (paramBigInteger == null) ? null : new ASN1Integer(paramBigInteger);
    Extensions extensions = this.extGenerator.isEmpty() ? null : this.extGenerator.generate();
    return new TimeStampRequest(new TimeStampReq(messageImprint, this.reqPolicy, aSN1Integer, this.certReq, extensions));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\tsp\TimeStampRequestGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */