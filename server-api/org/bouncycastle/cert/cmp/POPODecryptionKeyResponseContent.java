package org.bouncycastle.cert.cmp;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.cmp.PKIBody;
import org.bouncycastle.asn1.cmp.POPODecKeyRespContent;

public class POPODecryptionKeyResponseContent {
  private final POPODecKeyRespContent respContent;
  
  POPODecryptionKeyResponseContent(POPODecKeyRespContent paramPOPODecKeyRespContent) {
    this.respContent = paramPOPODecKeyRespContent;
  }
  
  public byte[][] getResponses() {
    ASN1Integer[] arrayOfASN1Integer = this.respContent.toASN1IntegerArray();
    byte[][] arrayOfByte = new byte[arrayOfASN1Integer.length][];
    for (byte b = 0; b != arrayOfASN1Integer.length; b++)
      arrayOfByte[b] = arrayOfASN1Integer[b].getValue().toByteArray(); 
    return arrayOfByte;
  }
  
  public static POPODecryptionKeyResponseContent fromPKIBody(PKIBody paramPKIBody) {
    if (paramPKIBody.getType() != 6)
      throw new IllegalArgumentException("content of PKIBody wrong type: " + paramPKIBody.getType()); 
    return new POPODecryptionKeyResponseContent(POPODecKeyRespContent.getInstance(paramPKIBody.getContent()));
  }
  
  public POPODecKeyRespContent toASN1Structure() {
    return this.respContent;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\cmp\POPODecryptionKeyResponseContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */