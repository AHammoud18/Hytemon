package org.bouncycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.cmp.Challenge;
import org.bouncycastle.asn1.cmp.PKIHeader;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.Recipient;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.Arrays;

public class ChallengeContent {
  private final Challenge challenge;
  
  private final DigestCalculator owfCalc;
  
  ChallengeContent(Challenge paramChallenge, DigestCalculator paramDigestCalculator) {
    this.challenge = paramChallenge;
    this.owfCalc = paramDigestCalculator;
  }
  
  public byte[] extractChallenge(PKIHeader paramPKIHeader, Recipient paramRecipient) throws CMPException {
    try {
      CMSEnvelopedData cMSEnvelopedData = new CMSEnvelopedData(new ContentInfo(PKCSObjectIdentifiers.envelopedData, (ASN1Encodable)this.challenge.getEncryptedRand()));
      Collection<RecipientInformation> collection = cMSEnvelopedData.getRecipientInfos().getRecipients();
      RecipientInformation recipientInformation = collection.iterator().next();
      byte[] arrayOfByte = recipientInformation.getContent(paramRecipient);
      Challenge.Rand rand = Challenge.Rand.getInstance(arrayOfByte);
      if (!Arrays.constantTimeAreEqual(rand.getSender().getEncoded(), paramPKIHeader.getSender().getEncoded()))
        throw new CMPChallengeFailedException("incorrect sender found"); 
      OutputStream outputStream = this.owfCalc.getOutputStream();
      outputStream.write(rand.getInt().getEncoded());
      outputStream.close();
      if (!Arrays.constantTimeAreEqual(this.challenge.getWitness(), this.owfCalc.getDigest()))
        throw new CMPChallengeFailedException("corrupted challenge found"); 
      return rand.getInt().getValue().toByteArray();
    } catch (CMSException cMSException) {
      throw new CMPException(cMSException.getMessage(), cMSException);
    } catch (IOException iOException) {
      throw new CMPException(iOException.getMessage(), iOException);
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\cmp\ChallengeContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */