package org.bouncycastle.crypto.agreement.ecjpake;

import java.math.BigInteger;

public class ECJPAKERound3Payload {
  private final String participantId;
  
  private final BigInteger macTag;
  
  public ECJPAKERound3Payload(String paramString, BigInteger paramBigInteger) {
    this.participantId = paramString;
    this.macTag = paramBigInteger;
  }
  
  public String getParticipantId() {
    return this.participantId;
  }
  
  public BigInteger getMacTag() {
    return this.macTag;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECJPAKERound3Payload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */