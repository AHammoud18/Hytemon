package org.bouncycastle.crypto.agreement.ecjpake;

import org.bouncycastle.math.ec.ECPoint;

public class ECJPAKERound1Payload {
  private final String participantId;
  
  private final ECPoint gx1;
  
  private final ECPoint gx2;
  
  private final ECSchnorrZKP knowledgeProofForX1;
  
  private final ECSchnorrZKP knowledgeProofForX2;
  
  public ECJPAKERound1Payload(String paramString, ECPoint paramECPoint1, ECPoint paramECPoint2, ECSchnorrZKP paramECSchnorrZKP1, ECSchnorrZKP paramECSchnorrZKP2) {
    ECJPAKEUtil.validateNotNull(paramString, "participantId");
    ECJPAKEUtil.validateNotNull(paramECPoint1, "gx1");
    ECJPAKEUtil.validateNotNull(paramECPoint2, "gx2");
    ECJPAKEUtil.validateNotNull(paramECSchnorrZKP1, "knowledgeProofForX1");
    ECJPAKEUtil.validateNotNull(paramECSchnorrZKP2, "knowledgeProofForX2");
    this.participantId = paramString;
    this.gx1 = paramECPoint1;
    this.gx2 = paramECPoint2;
    this.knowledgeProofForX1 = paramECSchnorrZKP1;
    this.knowledgeProofForX2 = paramECSchnorrZKP2;
  }
  
  public String getParticipantId() {
    return this.participantId;
  }
  
  public ECPoint getGx1() {
    return this.gx1;
  }
  
  public ECPoint getGx2() {
    return this.gx2;
  }
  
  public ECSchnorrZKP getKnowledgeProofForX1() {
    return this.knowledgeProofForX1;
  }
  
  public ECSchnorrZKP getKnowledgeProofForX2() {
    return this.knowledgeProofForX2;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECJPAKERound1Payload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */