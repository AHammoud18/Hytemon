package org.bouncycastle.cms.jcajce;

import java.security.Key;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.RecipientOperator;
import org.bouncycastle.operator.InputDecryptor;

public class JceKeyAgreeAuthEnvelopedRecipient extends JceKeyAgreeRecipient {
  public JceKeyAgreeAuthEnvelopedRecipient(PrivateKey paramPrivateKey) {
    super(paramPrivateKey);
  }
  
  public RecipientOperator getRecipientOperator(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, ASN1OctetString paramASN1OctetString, byte[] paramArrayOfbyte) throws CMSException {
    Key key = extractSecretKey(paramAlgorithmIdentifier1, paramAlgorithmIdentifier2, paramSubjectPublicKeyInfo, paramASN1OctetString, paramArrayOfbyte);
    Cipher cipher = this.contentHelper.createContentCipher(key, paramAlgorithmIdentifier2);
    return new RecipientOperator((InputDecryptor)new CMSInputAEADDecryptor(paramAlgorithmIdentifier2, cipher));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\jcajce\JceKeyAgreeAuthEnvelopedRecipient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */