package org.bouncycastle.operator;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Integers;

public class DefaultKemEncapsulationLengthProvider implements KemEncapsulationLengthProvider {
  private static Map<ASN1ObjectIdentifier, Integer> kemEncapsulationLengths = new HashMap<>();
  
  public int getEncapsulationLength(AlgorithmIdentifier paramAlgorithmIdentifier) {
    return ((Integer)kemEncapsulationLengths.get(paramAlgorithmIdentifier.getAlgorithm())).intValue();
  }
  
  static {
    kemEncapsulationLengths.put(NISTObjectIdentifiers.id_alg_ml_kem_512, Integers.valueOf(768));
    kemEncapsulationLengths.put(NISTObjectIdentifiers.id_alg_ml_kem_768, Integers.valueOf(1088));
    kemEncapsulationLengths.put(NISTObjectIdentifiers.id_alg_ml_kem_1024, Integers.valueOf(1568));
    kemEncapsulationLengths.put(BCObjectIdentifiers.ntruhps2048509, Integers.valueOf(699));
    kemEncapsulationLengths.put(BCObjectIdentifiers.ntruhps2048677, Integers.valueOf(930));
    kemEncapsulationLengths.put(BCObjectIdentifiers.ntruhps4096821, Integers.valueOf(1230));
    kemEncapsulationLengths.put(BCObjectIdentifiers.ntruhps40961229, Integers.valueOf(1842));
    kemEncapsulationLengths.put(BCObjectIdentifiers.ntruhrss701, Integers.valueOf(1138));
    kemEncapsulationLengths.put(BCObjectIdentifiers.ntruhrss1373, Integers.valueOf(2401));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\operator\DefaultKemEncapsulationLengthProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */