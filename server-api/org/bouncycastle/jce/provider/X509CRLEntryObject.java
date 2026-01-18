package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.TBSCertList;
import org.bouncycastle.util.Strings;

public class X509CRLEntryObject extends X509CRLEntry {
  private TBSCertList.CRLEntry c;
  
  private X500Name certificateIssuer;
  
  private int hashValue;
  
  private boolean isHashValueSet;
  
  public X509CRLEntryObject(TBSCertList.CRLEntry paramCRLEntry) {
    this.c = paramCRLEntry;
    this.certificateIssuer = null;
  }
  
  public X509CRLEntryObject(TBSCertList.CRLEntry paramCRLEntry, boolean paramBoolean, X500Name paramX500Name) {
    this.c = paramCRLEntry;
    this.certificateIssuer = loadCertificateIssuer(paramBoolean, paramX500Name);
  }
  
  public boolean hasUnsupportedCriticalExtension() {
    Extensions extensions = this.c.getExtensions();
    return (extensions != null && extensions.hasAnyCriticalExtensions());
  }
  
  private X500Name loadCertificateIssuer(boolean paramBoolean, X500Name paramX500Name) {
    if (!paramBoolean)
      return null; 
    ASN1OctetString aSN1OctetString = Extensions.getExtensionValue(this.c.getExtensions(), Extension.certificateIssuer);
    if (aSN1OctetString == null)
      return paramX500Name; 
    try {
      GeneralName[] arrayOfGeneralName = GeneralNames.getInstance(aSN1OctetString.getOctets()).getNames();
      for (byte b = 0; b < arrayOfGeneralName.length; b++) {
        if (arrayOfGeneralName[b].getTagNo() == 4)
          return X500Name.getInstance(arrayOfGeneralName[b].getName()); 
      } 
      return null;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public X500Principal getCertificateIssuer() {
    if (this.certificateIssuer == null)
      return null; 
    try {
      return new X500Principal(this.certificateIssuer.getEncoded());
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  private Set getExtensionOIDs(boolean paramBoolean) {
    Extensions extensions = this.c.getExtensions();
    if (extensions != null) {
      HashSet<String> hashSet = new HashSet();
      Enumeration<ASN1ObjectIdentifier> enumeration = extensions.oids();
      while (enumeration.hasMoreElements()) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = enumeration.nextElement();
        Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
        if (paramBoolean == extension.isCritical())
          hashSet.add(aSN1ObjectIdentifier.getId()); 
      } 
      return hashSet;
    } 
    return null;
  }
  
  public Set getCriticalExtensionOIDs() {
    return getExtensionOIDs(true);
  }
  
  public Set getNonCriticalExtensionOIDs() {
    return getExtensionOIDs(false);
  }
  
  public byte[] getExtensionValue(String paramString) {
    return X509SignatureUtil.getExtensionValue(this.c.getExtensions(), paramString);
  }
  
  public int hashCode() {
    if (!this.isHashValueSet) {
      this.hashValue = super.hashCode();
      this.isHashValueSet = true;
    } 
    return this.hashValue;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof X509CRLEntryObject) {
      X509CRLEntryObject x509CRLEntryObject = (X509CRLEntryObject)paramObject;
      return this.c.equals(x509CRLEntryObject.c);
    } 
    return super.equals(this);
  }
  
  public byte[] getEncoded() throws CRLException {
    try {
      return this.c.getEncoded("DER");
    } catch (IOException iOException) {
      throw new CRLException(iOException.toString());
    } 
  }
  
  public BigInteger getSerialNumber() {
    return this.c.getUserCertificate().getValue();
  }
  
  public Date getRevocationDate() {
    return this.c.getRevocationDate().getDate();
  }
  
  public boolean hasExtensions() {
    return (this.c.getExtensions() != null);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    stringBuilder.append("      userCertificate: ").append(getSerialNumber()).append(str);
    stringBuilder.append("       revocationDate: ").append(getRevocationDate()).append(str);
    stringBuilder.append("       certificateIssuer: ").append(getCertificateIssuer()).append(str);
    Extensions extensions = this.c.getExtensions();
    if (extensions != null) {
      Enumeration<ASN1ObjectIdentifier> enumeration = extensions.oids();
      if (enumeration.hasMoreElements()) {
        stringBuilder.append("   crlEntryExtensions:").append(str);
        while (enumeration.hasMoreElements()) {
          ASN1ObjectIdentifier aSN1ObjectIdentifier = enumeration.nextElement();
          Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
          if (extension.getExtnValue() != null) {
            byte[] arrayOfByte = extension.getExtnValue().getOctets();
            ASN1InputStream aSN1InputStream = new ASN1InputStream(arrayOfByte);
            stringBuilder.append("                       critical(").append(extension.isCritical()).append(") ");
            try {
              if (aSN1ObjectIdentifier.equals((ASN1Primitive)Extension.reasonCode)) {
                stringBuilder.append(CRLReason.getInstance(ASN1Enumerated.getInstance(aSN1InputStream.readObject()))).append(str);
                continue;
              } 
              if (aSN1ObjectIdentifier.equals((ASN1Primitive)Extension.certificateIssuer)) {
                stringBuilder.append("Certificate issuer: ").append(GeneralNames.getInstance(aSN1InputStream.readObject())).append(str);
                continue;
              } 
              stringBuilder.append(aSN1ObjectIdentifier.getId());
              stringBuilder.append(" value = ").append(ASN1Dump.dumpAsString(aSN1InputStream.readObject())).append(str);
            } catch (Exception exception) {
              stringBuilder.append(aSN1ObjectIdentifier.getId());
              stringBuilder.append(" value = ").append("*****").append(str);
            } 
            continue;
          } 
          stringBuilder.append(str);
        } 
      } 
    } 
    return stringBuilder.toString();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\provider\X509CRLEntryObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */