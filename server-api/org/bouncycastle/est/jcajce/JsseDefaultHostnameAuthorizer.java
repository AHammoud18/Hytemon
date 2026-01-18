package org.bouncycastle.est.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.est.ESTException;
import org.bouncycastle.util.IPAddress;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class JsseDefaultHostnameAuthorizer implements JsseHostnameAuthorizer {
  private static Logger LOG = Logger.getLogger(JsseDefaultHostnameAuthorizer.class.getName());
  
  private final Set<String> knownSuffixes;
  
  public JsseDefaultHostnameAuthorizer(Set<String> paramSet) {
    this.knownSuffixes = paramSet;
  }
  
  public boolean verified(String paramString, SSLSession paramSSLSession) throws IOException {
    try {
      CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
      X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(paramSSLSession.getPeerCertificates()[0].getEncoded()));
      return verify(paramString, x509Certificate);
    } catch (Exception exception) {
      if (exception instanceof ESTException)
        throw (ESTException)exception; 
      throw new ESTException(exception.getMessage(), exception);
    } 
  }
  
  public boolean verify(String paramString, X509Certificate paramX509Certificate) throws IOException {
    if (paramString == null)
      throw new NullPointerException("'name' cannot be null"); 
    boolean bool1 = false;
    boolean bool = IPAddress.isValidIPv4(paramString);
    boolean bool2 = (!bool && IPAddress.isValidIPv6(paramString)) ? true : false;
    boolean bool3 = (bool || bool2) ? true : false;
    try {
      Collection<List<?>> collection = paramX509Certificate.getSubjectAlternativeNames();
      if (collection != null) {
        InetAddress inetAddress = null;
        for (List<Integer> list : collection) {
          int j = ((Integer)list.get(0)).intValue();
          switch (j) {
            case 2:
              if (!bool3 && isValidNameMatch(paramString, (String)list.get(1), this.knownSuffixes))
                return true; 
              bool1 = true;
              continue;
            case 7:
              if (bool3) {
                String str = (String)list.get(1);
                if (paramString.equalsIgnoreCase(str))
                  return true; 
                if (bool2 && IPAddress.isValidIPv6(str))
                  try {
                    if (inetAddress == null)
                      inetAddress = InetAddress.getByName(paramString); 
                    if (inetAddress.equals(InetAddress.getByName(str)))
                      return true; 
                  } catch (UnknownHostException unknownHostException) {} 
              } 
              continue;
          } 
          if (LOG.isLoggable(Level.INFO)) {
            String str;
            if (list.get(1) instanceof byte[]) {
              str = Hex.toHexString((byte[])list.get(1));
            } else {
              str = list.get(1).toString();
            } 
            LOG.log(Level.INFO, "ignoring type " + j + " value = " + str);
          } 
        } 
      } 
    } catch (Exception exception) {
      throw new ESTException(exception.getMessage(), exception);
    } 
    if (bool3 || bool1)
      return false; 
    X500Principal x500Principal = paramX509Certificate.getSubjectX500Principal();
    if (x500Principal == null)
      return false; 
    RDN[] arrayOfRDN = X500Name.getInstance(x500Principal.getEncoded()).getRDNs();
    for (int i = arrayOfRDN.length - 1; i >= 0; i--) {
      AttributeTypeAndValue[] arrayOfAttributeTypeAndValue = arrayOfRDN[i].getTypesAndValues();
      for (byte b = 0; b != arrayOfAttributeTypeAndValue.length; b++) {
        AttributeTypeAndValue attributeTypeAndValue = arrayOfAttributeTypeAndValue[b];
        if (BCStyle.CN.equals((ASN1Primitive)attributeTypeAndValue.getType())) {
          ASN1Primitive aSN1Primitive = attributeTypeAndValue.getValue().toASN1Primitive();
          return (aSN1Primitive instanceof ASN1String && isValidNameMatch(paramString, ((ASN1String)aSN1Primitive).getString(), this.knownSuffixes));
        } 
      } 
    } 
    return false;
  }
  
  public static boolean isValidNameMatch(String paramString1, String paramString2, Set<String> paramSet) throws IOException {
    if (paramString2.contains("*")) {
      int i = paramString2.indexOf('*');
      if (i == paramString2.lastIndexOf("*")) {
        if (paramString2.contains("..") || paramString2.charAt(paramString2.length() - 1) == '*')
          return false; 
        int j = paramString2.indexOf('.', i);
        if (paramSet != null && paramSet.contains(Strings.toLowerCase(paramString2.substring(j))))
          throw new IOException("Wildcard `" + paramString2 + "` matches known public suffix."); 
        String str1 = Strings.toLowerCase(paramString2.substring(i + 1));
        String str2 = Strings.toLowerCase(paramString1);
        if (str2.equals(str1))
          return false; 
        if (str1.length() > str2.length())
          return false; 
        if (i > 0)
          return (str2.startsWith(paramString2.substring(0, i)) && str2.endsWith(str1)) ? ((str2.substring(i, str2.length() - str1.length()).indexOf('.') < 0)) : false; 
        String str3 = str2.substring(0, str2.length() - str1.length());
        return (str3.indexOf('.') > 0) ? false : str2.endsWith(str1);
      } 
      return false;
    } 
    return paramString1.equalsIgnoreCase(paramString2);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\est\jcajce\JsseDefaultHostnameAuthorizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */