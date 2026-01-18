package org.bouncycastle.util.io.pem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.util.encoders.Base64;

public class PemReader extends BufferedReader {
  public static final String LAX_PEM_PARSING_SYSTEM_PROPERTY_NAME = "org.bouncycastle.pemreader.lax";
  
  private static final String BEGIN = "-----BEGIN ";
  
  private static final String END = "-----END ";
  
  private static final Logger LOG = Logger.getLogger(PemReader.class.getName());
  
  public PemReader(Reader paramReader) {
    super(paramReader);
  }
  
  public PemObject readPemObject() throws IOException {
    String str;
    for (str = readLine(); str != null && !str.startsWith("-----BEGIN "); str = readLine());
    if (str != null) {
      str = str.substring("-----BEGIN ".length()).trim();
      int i = str.indexOf('-');
      if (i > 0 && str.endsWith("-----") && str.length() - i == 5) {
        String str1 = str.substring(0, i);
        return loadObject(str1);
      } 
    } 
    return null;
  }
  
  private PemObject loadObject(String paramString) throws IOException {
    String str2 = "-----END " + paramString + "-----";
    StringBuffer stringBuffer = new StringBuffer();
    ArrayList<PemHeader> arrayList = new ArrayList();
    String str1;
    while ((str1 = readLine()) != null) {
      int i = str1.indexOf(':');
      if (i >= 0) {
        String str3 = str1.substring(0, i);
        String str4 = str1.substring(i + 1).trim();
        arrayList.add(new PemHeader(str3, str4));
        continue;
      } 
      if (System.getProperty("org.bouncycastle.pemreader.lax", "false").equalsIgnoreCase("true")) {
        String str = str1.trim();
        if (!str.equals(str1) && LOG.isLoggable(Level.WARNING))
          LOG.log(Level.WARNING, "PEM object contains whitespaces on -----END line", new Exception("trace")); 
        str1 = str;
      } 
      if (str1.indexOf(str2) == 0)
        break; 
      stringBuffer.append(str1.trim());
    } 
    if (str1 == null)
      throw new IOException(str2 + " not found"); 
    return new PemObject(paramString, arrayList, Base64.decode(stringBuffer.toString()));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastl\\util\io\pem\PemReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */