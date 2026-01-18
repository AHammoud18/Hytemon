package org.bouncycastle.asn1;

import java.io.InputStream;

public interface ASN1OctetStringParser extends ASN1Encodable, InMemoryRepresentable {
  InputStream getOctetStream();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\ASN1OctetStringParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */