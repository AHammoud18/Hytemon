package org.bouncycastle.cms.jcajce;

import java.security.Key;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.RecipientOperator;
import org.bouncycastle.operator.InputDecryptor;

public class JcePasswordAuthEnvelopedRecipient extends JcePasswordRecipient {
  public JcePasswordAuthEnvelopedRecipient(char[] paramArrayOfchar) {
    super(paramArrayOfchar);
  }
  
  public RecipientOperator getRecipientOperator(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws CMSException {
    Key key = extractSecretKey(paramAlgorithmIdentifier1, paramAlgorithmIdentifier2, paramArrayOfbyte1, paramArrayOfbyte2);
    Cipher cipher = this.helper.createContentCipher(key, paramAlgorithmIdentifier2);
    return new RecipientOperator((InputDecryptor)new CMSInputAEADDecryptor(paramAlgorithmIdentifier2, cipher));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\jcajce\JcePasswordAuthEnvelopedRecipient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */