package org.bouncycastle.asn1.mod;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface ModObjectIdentifiers {
  public static final ASN1ObjectIdentifier id_mod = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.0");
  
  public static final ASN1ObjectIdentifier id_mod_algorithmInformation_02 = id_mod.branch("58");
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\mod\ModObjectIdentifiers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */