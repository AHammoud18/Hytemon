package org.bouncycastle.operator.bc;

import java.io.IOException;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pqc.crypto.util.PublicKeyFactory;

public class BcHssLmsContentVerifierProviderBuilder extends BcContentVerifierProviderBuilder {
  protected Signer createSigner(AlgorithmIdentifier paramAlgorithmIdentifier) throws OperatorCreationException {
    return new BcHssLmsContentSignerBuilder.HssSigner();
  }
  
  protected AsymmetricKeyParameter extractKeyParameters(SubjectPublicKeyInfo paramSubjectPublicKeyInfo) throws IOException {
    return PublicKeyFactory.createKey(paramSubjectPublicKeyInfo);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\operator\bc\BcHssLmsContentVerifierProviderBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */