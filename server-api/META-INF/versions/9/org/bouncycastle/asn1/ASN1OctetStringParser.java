package META-INF.versions.9.org.bouncycastle.asn1;

import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.InMemoryRepresentable;

public interface ASN1OctetStringParser extends ASN1Encodable, InMemoryRepresentable {
  InputStream getOctetStream();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\asn1\ASN1OctetStringParser.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */