package org.bouncycastle.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.cms.KEMRecipientInfo;
import org.bouncycastle.asn1.cms.RecipientIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Arrays;

public class KEMRecipientInformation extends RecipientInformation {
  private KEMRecipientInfo info;
  
  KEMRecipientInformation(KEMRecipientInfo paramKEMRecipientInfo, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable) {
    super(paramKEMRecipientInfo.getKem(), paramAlgorithmIdentifier, paramCMSSecureReadable);
    this.info = paramKEMRecipientInfo;
    RecipientIdentifier recipientIdentifier = paramKEMRecipientInfo.getRecipientIdentifier();
    if (recipientIdentifier.isTagged()) {
      ASN1OctetString aSN1OctetString = ASN1OctetString.getInstance(recipientIdentifier.getId());
      this.rid = new KEMRecipientId(aSN1OctetString.getOctets());
    } else {
      IssuerAndSerialNumber issuerAndSerialNumber = IssuerAndSerialNumber.getInstance(recipientIdentifier.getId());
      this.rid = new KEMRecipientId(issuerAndSerialNumber.getName(), issuerAndSerialNumber.getSerialNumber().getValue());
    } 
  }
  
  public AlgorithmIdentifier getKdfAlgorithm() {
    return this.info.getKdf();
  }
  
  public byte[] getUkm() {
    return Arrays.clone(this.info.getUkm());
  }
  
  public byte[] getEncapsulation() {
    return Arrays.clone(this.info.getKemct().getOctets());
  }
  
  protected RecipientOperator getRecipientOperator(Recipient paramRecipient) throws CMSException {
    return ((KEMRecipient)paramRecipient).getRecipientOperator(new AlgorithmIdentifier(this.keyEncAlg.getAlgorithm(), (ASN1Encodable)this.info), this.messageAlgorithm, this.info.getEncryptedKey().getOctets());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\KEMRecipientInformation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */