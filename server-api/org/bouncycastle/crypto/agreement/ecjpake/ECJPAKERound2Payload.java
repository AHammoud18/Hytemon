package org.bouncycastle.crypto.agreement.ecjpake;

import org.bouncycastle.math.ec.ECPoint;

public class ECJPAKERound2Payload {
  private final String participantId;
  
  private final ECPoint a;
  
  private final ECSchnorrZKP knowledgeProofForX2s;
  
  public ECJPAKERound2Payload(String paramString, ECPoint paramECPoint, ECSchnorrZKP paramECSchnorrZKP) {
    ECJPAKEUtil.validateNotNull(paramString, "participantId");
    ECJPAKEUtil.validateNotNull(paramECPoint, "a");
    ECJPAKEUtil.validateNotNull(paramECSchnorrZKP, "knowledgeProofForX2s");
    this.participantId = paramString;
    this.a = paramECPoint;
    this.knowledgeProofForX2s = paramECSchnorrZKP;
  }
  
  public String getParticipantId() {
    return this.participantId;
  }
  
  public ECPoint getA() {
    return this.a;
  }
  
  public ECSchnorrZKP getKnowledgeProofForX2s() {
    return this.knowledgeProofForX2s;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECJPAKERound2Payload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */