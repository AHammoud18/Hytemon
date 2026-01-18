package org.bouncycastle.crypto.agreement.ecjpake;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Exceptions;

public class ECJPAKEParticipant {
  public static final int STATE_INITIALIZED = 0;
  
  public static final int STATE_ROUND_1_CREATED = 10;
  
  public static final int STATE_ROUND_1_VALIDATED = 20;
  
  public static final int STATE_ROUND_2_CREATED = 30;
  
  public static final int STATE_ROUND_2_VALIDATED = 40;
  
  public static final int STATE_KEY_CALCULATED = 50;
  
  public static final int STATE_ROUND_3_CREATED = 60;
  
  public static final int STATE_ROUND_3_VALIDATED = 70;
  
  private final String participantId;
  
  private char[] password;
  
  private final Digest digest;
  
  private final SecureRandom random;
  
  private String partnerParticipantId;
  
  private ECCurve.AbstractFp ecCurve;
  
  private BigInteger q;
  
  private BigInteger h;
  
  private BigInteger n;
  
  private ECPoint g;
  
  private BigInteger x1;
  
  private BigInteger x2;
  
  private ECPoint gx1;
  
  private ECPoint gx2;
  
  private ECPoint gx3;
  
  private ECPoint gx4;
  
  private ECPoint b;
  
  private int state;
  
  public ECJPAKEParticipant(String paramString, char[] paramArrayOfchar) {
    this(paramString, paramArrayOfchar, ECJPAKECurves.NIST_P256);
  }
  
  public ECJPAKEParticipant(String paramString, char[] paramArrayOfchar, ECJPAKECurve paramECJPAKECurve) {
    this(paramString, paramArrayOfchar, paramECJPAKECurve, (Digest)SHA256Digest.newInstance(), CryptoServicesRegistrar.getSecureRandom());
  }
  
  public ECJPAKEParticipant(String paramString, char[] paramArrayOfchar, ECJPAKECurve paramECJPAKECurve, Digest paramDigest, SecureRandom paramSecureRandom) {
    ECJPAKEUtil.validateNotNull(paramString, "participantId");
    ECJPAKEUtil.validateNotNull(paramArrayOfchar, "password");
    ECJPAKEUtil.validateNotNull(paramECJPAKECurve, "curve params");
    ECJPAKEUtil.validateNotNull(paramDigest, "digest");
    ECJPAKEUtil.validateNotNull(paramSecureRandom, "random");
    if (paramArrayOfchar.length == 0)
      throw new IllegalArgumentException("Password must not be empty."); 
    this.participantId = paramString;
    this.password = Arrays.copyOf(paramArrayOfchar, paramArrayOfchar.length);
    this.ecCurve = paramECJPAKECurve.getCurve();
    this.g = paramECJPAKECurve.getG();
    this.h = paramECJPAKECurve.getH();
    this.n = paramECJPAKECurve.getN();
    this.q = paramECJPAKECurve.getQ();
    this.digest = paramDigest;
    this.random = paramSecureRandom;
    this.state = 0;
  }
  
  public int getState() {
    return this.state;
  }
  
  public ECJPAKERound1Payload createRound1PayloadToSend() {
    if (this.state >= 10)
      throw new IllegalStateException("Round1 payload already created for " + this.participantId); 
    this.x1 = ECJPAKEUtil.generateX1(this.n, this.random);
    this.x2 = ECJPAKEUtil.generateX1(this.n, this.random);
    this.gx1 = ECJPAKEUtil.calculateGx(this.g, this.x1);
    this.gx2 = ECJPAKEUtil.calculateGx(this.g, this.x2);
    ECSchnorrZKP eCSchnorrZKP1 = ECJPAKEUtil.calculateZeroKnowledgeProof(this.g, this.n, this.x1, this.gx1, this.digest, this.participantId, this.random);
    ECSchnorrZKP eCSchnorrZKP2 = ECJPAKEUtil.calculateZeroKnowledgeProof(this.g, this.n, this.x2, this.gx2, this.digest, this.participantId, this.random);
    this.state = 10;
    return new ECJPAKERound1Payload(this.participantId, this.gx1, this.gx2, eCSchnorrZKP1, eCSchnorrZKP2);
  }
  
  public void validateRound1PayloadReceived(ECJPAKERound1Payload paramECJPAKERound1Payload) throws CryptoException {
    if (this.state >= 20)
      throw new IllegalStateException("Validation already attempted for round1 payload for" + this.participantId); 
    this.partnerParticipantId = paramECJPAKERound1Payload.getParticipantId();
    this.gx3 = paramECJPAKERound1Payload.getGx1();
    this.gx4 = paramECJPAKERound1Payload.getGx2();
    ECSchnorrZKP eCSchnorrZKP1 = paramECJPAKERound1Payload.getKnowledgeProofForX1();
    ECSchnorrZKP eCSchnorrZKP2 = paramECJPAKERound1Payload.getKnowledgeProofForX2();
    ECJPAKEUtil.validateParticipantIdsDiffer(this.participantId, paramECJPAKERound1Payload.getParticipantId());
    ECJPAKEUtil.validateZeroKnowledgeProof(this.g, this.gx3, eCSchnorrZKP1, this.q, this.n, (ECCurve)this.ecCurve, this.h, paramECJPAKERound1Payload.getParticipantId(), this.digest);
    ECJPAKEUtil.validateZeroKnowledgeProof(this.g, this.gx4, eCSchnorrZKP2, this.q, this.n, (ECCurve)this.ecCurve, this.h, paramECJPAKERound1Payload.getParticipantId(), this.digest);
    this.state = 20;
  }
  
  public ECJPAKERound2Payload createRound2PayloadToSend() {
    if (this.state >= 30)
      throw new IllegalStateException("Round2 payload already created for " + this.participantId); 
    if (this.state < 20)
      throw new IllegalStateException("Round1 payload must be validated prior to creating Round2 payload for " + this.participantId); 
    ECPoint eCPoint1 = ECJPAKEUtil.calculateGA(this.gx1, this.gx3, this.gx4);
    BigInteger bigInteger1 = calculateS();
    BigInteger bigInteger2 = ECJPAKEUtil.calculateX2s(this.n, this.x2, bigInteger1);
    ECPoint eCPoint2 = ECJPAKEUtil.calculateA(eCPoint1, bigInteger2);
    ECSchnorrZKP eCSchnorrZKP = ECJPAKEUtil.calculateZeroKnowledgeProof(eCPoint1, this.n, bigInteger2, eCPoint2, this.digest, this.participantId, this.random);
    this.state = 30;
    return new ECJPAKERound2Payload(this.participantId, eCPoint2, eCSchnorrZKP);
  }
  
  public void validateRound2PayloadReceived(ECJPAKERound2Payload paramECJPAKERound2Payload) throws CryptoException {
    if (this.state >= 40)
      throw new IllegalStateException("Validation already attempted for round2 payload for" + this.participantId); 
    if (this.state < 20)
      throw new IllegalStateException("Round1 payload must be validated prior to validating Round2 payload for " + this.participantId); 
    ECPoint eCPoint = ECJPAKEUtil.calculateGA(this.gx3, this.gx1, this.gx2);
    this.b = paramECJPAKERound2Payload.getA();
    ECSchnorrZKP eCSchnorrZKP = paramECJPAKERound2Payload.getKnowledgeProofForX2s();
    ECJPAKEUtil.validateParticipantIdsDiffer(this.participantId, paramECJPAKERound2Payload.getParticipantId());
    ECJPAKEUtil.validateParticipantIdsEqual(this.partnerParticipantId, paramECJPAKERound2Payload.getParticipantId());
    ECJPAKEUtil.validateZeroKnowledgeProof(eCPoint, this.b, eCSchnorrZKP, this.q, this.n, (ECCurve)this.ecCurve, this.h, paramECJPAKERound2Payload.getParticipantId(), this.digest);
    this.state = 40;
  }
  
  public BigInteger calculateKeyingMaterial() {
    if (this.state >= 50)
      throw new IllegalStateException("Key already calculated for " + this.participantId); 
    if (this.state < 40)
      throw new IllegalStateException("Round2 payload must be validated prior to creating key for " + this.participantId); 
    BigInteger bigInteger1 = calculateS();
    Arrays.fill(this.password, false);
    this.password = null;
    BigInteger bigInteger2 = ECJPAKEUtil.calculateKeyingMaterial(this.n, this.gx4, this.x2, bigInteger1, this.b);
    this.x1 = null;
    this.x2 = null;
    this.b = null;
    this.state = 50;
    return bigInteger2;
  }
  
  public ECJPAKERound3Payload createRound3PayloadToSend(BigInteger paramBigInteger) {
    if (this.state >= 60)
      throw new IllegalStateException("Round3 payload already created for " + this.participantId); 
    if (this.state < 50)
      throw new IllegalStateException("Keying material must be calculated prior to creating Round3 payload for " + this.participantId); 
    BigInteger bigInteger = ECJPAKEUtil.calculateMacTag(this.participantId, this.partnerParticipantId, this.gx1, this.gx2, this.gx3, this.gx4, paramBigInteger, this.digest);
    this.state = 60;
    return new ECJPAKERound3Payload(this.participantId, bigInteger);
  }
  
  public void validateRound3PayloadReceived(ECJPAKERound3Payload paramECJPAKERound3Payload, BigInteger paramBigInteger) throws CryptoException {
    if (this.state >= 70)
      throw new IllegalStateException("Validation already attempted for round3 payload for" + this.participantId); 
    if (this.state < 50)
      throw new IllegalStateException("Keying material must be calculated validated prior to validating Round3 payload for " + this.participantId); 
    ECJPAKEUtil.validateParticipantIdsDiffer(this.participantId, paramECJPAKERound3Payload.getParticipantId());
    ECJPAKEUtil.validateParticipantIdsEqual(this.partnerParticipantId, paramECJPAKERound3Payload.getParticipantId());
    ECJPAKEUtil.validateMacTag(this.participantId, this.partnerParticipantId, this.gx1, this.gx2, this.gx3, this.gx4, paramBigInteger, this.digest, paramECJPAKERound3Payload.getMacTag());
    this.gx1 = null;
    this.gx2 = null;
    this.gx3 = null;
    this.gx4 = null;
    this.state = 70;
  }
  
  private BigInteger calculateS() {
    try {
      return ECJPAKEUtil.calculateS(this.n, this.password);
    } catch (CryptoException cryptoException) {
      throw Exceptions.illegalStateException(cryptoException.getMessage(), cryptoException);
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECJPAKEParticipant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */