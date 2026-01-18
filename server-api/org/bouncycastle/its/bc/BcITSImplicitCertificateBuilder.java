package org.bouncycastle.its.bc;

import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.ITSImplicitCertificateBuilder;
import org.bouncycastle.oer.its.ieee1609dot2.ToBeSignedCertificate;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;

public class BcITSImplicitCertificateBuilder extends ITSImplicitCertificateBuilder {
  public BcITSImplicitCertificateBuilder(ITSCertificate paramITSCertificate, ToBeSignedCertificate.Builder paramBuilder) {
    super(paramITSCertificate, (DigestCalculatorProvider)new BcDigestCalculatorProvider(), paramBuilder);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\its\bc\BcITSImplicitCertificateBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */