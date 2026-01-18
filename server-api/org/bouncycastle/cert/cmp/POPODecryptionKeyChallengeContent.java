package org.bouncycastle.cert.cmp;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cmp.Challenge;
import org.bouncycastle.asn1.cmp.PKIBody;
import org.bouncycastle.asn1.cmp.POPODecKeyChallContent;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;

public class POPODecryptionKeyChallengeContent {
  private final ASN1Sequence content;
  
  private final DigestCalculatorProvider owfCalcProvider;
  
  POPODecryptionKeyChallengeContent(POPODecKeyChallContent paramPOPODecKeyChallContent, DigestCalculatorProvider paramDigestCalculatorProvider) {
    this.content = ASN1Sequence.getInstance(paramPOPODecKeyChallContent.toASN1Primitive());
    this.owfCalcProvider = paramDigestCalculatorProvider;
  }
  
  public ChallengeContent[] toChallengeArray() throws CMPException {
    ChallengeContent[] arrayOfChallengeContent = new ChallengeContent[this.content.size()];
    DigestCalculator digestCalculator = null;
    for (byte b = 0; b != arrayOfChallengeContent.length; b++) {
      Challenge challenge = Challenge.getInstance(this.content.getObjectAt(b));
      if (challenge.getOwf() != null)
        try {
          digestCalculator = this.owfCalcProvider.get(challenge.getOwf());
        } catch (OperatorCreationException operatorCreationException) {
          throw new CMPException(operatorCreationException.getMessage(), operatorCreationException);
        }  
      arrayOfChallengeContent[b] = new ChallengeContent(Challenge.getInstance(this.content.getObjectAt(b)), digestCalculator);
    } 
    return arrayOfChallengeContent;
  }
  
  public static POPODecryptionKeyChallengeContent fromPKIBody(PKIBody paramPKIBody, DigestCalculatorProvider paramDigestCalculatorProvider) {
    if (paramPKIBody.getType() != 5)
      throw new IllegalArgumentException("content of PKIBody wrong type: " + paramPKIBody.getType()); 
    return new POPODecryptionKeyChallengeContent(POPODecKeyChallContent.getInstance(paramPKIBody.getContent()), paramDigestCalculatorProvider);
  }
  
  public POPODecKeyChallContent toASN1Structure() {
    return POPODecKeyChallContent.getInstance(this.content);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\cmp\POPODecryptionKeyChallengeContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */