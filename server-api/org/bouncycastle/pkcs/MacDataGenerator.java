package org.bouncycastle.pkcs;

import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.util.Strings;

class MacDataGenerator {
  private PKCS12MacCalculatorBuilder builder;
  
  MacDataGenerator(PKCS12MacCalculatorBuilder paramPKCS12MacCalculatorBuilder) {
    this.builder = paramPKCS12MacCalculatorBuilder;
  }
  
  public MacData build(char[] paramArrayOfchar, byte[] paramArrayOfbyte) throws PKCSException {
    MacCalculator macCalculator;
    byte[] arrayOfByte;
    int i;
    try {
      macCalculator = this.builder.build(paramArrayOfchar);
      OutputStream outputStream = macCalculator.getOutputStream();
      outputStream.write(paramArrayOfbyte);
      outputStream.close();
    } catch (Exception exception) {
      throw new PKCSException("unable to process data: " + exception.getMessage(), exception);
    } 
    AlgorithmIdentifier algorithmIdentifier = macCalculator.getAlgorithmIdentifier();
    DigestInfo digestInfo = new DigestInfo(this.builder.getDigestAlgorithmIdentifier(), macCalculator.getMac());
    if (PKCSObjectIdentifiers.id_PBMAC1.equals((ASN1Primitive)digestInfo.getAlgorithmId().getAlgorithm())) {
      arrayOfByte = Strings.toUTF8ByteArray("NOT USED".toCharArray());
      i = 1;
    } else {
      PKCS12PBEParams pKCS12PBEParams = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
      arrayOfByte = pKCS12PBEParams.getIV();
      i = pKCS12PBEParams.getIterations().intValue();
    } 
    return new MacData(digestInfo, arrayOfByte, i);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pkcs\MacDataGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */