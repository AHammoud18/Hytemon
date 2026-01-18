package org.bouncycastle.cert;

import java.io.IOException;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.DeltaCertificateDescriptor;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.asn1.x509.Validity;

public class DeltaCertificateTool {
  public static Extension makeDeltaCertificateExtension(boolean paramBoolean, Certificate paramCertificate) throws IOException {
    DeltaCertificateDescriptor deltaCertificateDescriptor = new DeltaCertificateDescriptor(paramCertificate.getSerialNumber(), paramCertificate.getSignatureAlgorithm(), paramCertificate.getIssuer(), paramCertificate.getValidity(), paramCertificate.getSubject(), paramCertificate.getSubjectPublicKeyInfo(), paramCertificate.getExtensions(), paramCertificate.getSignature());
    DEROctetString dEROctetString = new DEROctetString(deltaCertificateDescriptor.getEncoded("DER"));
    return new Extension(Extension.deltaCertificateDescriptor, paramBoolean, (ASN1OctetString)dEROctetString);
  }
  
  public static Extension makeDeltaCertificateExtension(boolean paramBoolean, X509CertificateHolder paramX509CertificateHolder) throws IOException {
    return makeDeltaCertificateExtension(paramBoolean, paramX509CertificateHolder.toASN1Structure());
  }
  
  public static Certificate extractDeltaCertificate(TBSCertificate paramTBSCertificate) {
    Extensions extensions1 = paramTBSCertificate.getExtensions();
    Extension extension = extensions1.getExtension(Extension.deltaCertificateDescriptor);
    if (extension == null)
      throw new IllegalStateException("no deltaCertificateDescriptor present"); 
    DeltaCertificateDescriptor deltaCertificateDescriptor = DeltaCertificateDescriptor.getInstance(extension.getParsedValue());
    ASN1Integer aSN1Integer1 = paramTBSCertificate.getVersion();
    ASN1Integer aSN1Integer2 = deltaCertificateDescriptor.getSerialNumber();
    AlgorithmIdentifier algorithmIdentifier = deltaCertificateDescriptor.getSignature();
    if (algorithmIdentifier == null)
      algorithmIdentifier = paramTBSCertificate.getSignature(); 
    X500Name x500Name1 = deltaCertificateDescriptor.getIssuer();
    if (x500Name1 == null)
      x500Name1 = paramTBSCertificate.getIssuer(); 
    Validity validity = deltaCertificateDescriptor.getValidityObject();
    if (validity == null)
      validity = paramTBSCertificate.getValidity(); 
    X500Name x500Name2 = deltaCertificateDescriptor.getSubject();
    if (x500Name2 == null)
      x500Name2 = paramTBSCertificate.getSubject(); 
    SubjectPublicKeyInfo subjectPublicKeyInfo = deltaCertificateDescriptor.getSubjectPublicKeyInfo();
    Extensions extensions2 = extractDeltaExtensions(deltaCertificateDescriptor.getExtensions(), extensions1);
    TBSCertificate tBSCertificate = new TBSCertificate(aSN1Integer1, aSN1Integer2, algorithmIdentifier, x500Name1, validity, x500Name2, subjectPublicKeyInfo, null, null, extensions2);
    return new Certificate(tBSCertificate, algorithmIdentifier, deltaCertificateDescriptor.getSignatureValue());
  }
  
  public static X509CertificateHolder extractDeltaCertificate(X509CertificateHolder paramX509CertificateHolder) {
    return new X509CertificateHolder(extractDeltaCertificate(paramX509CertificateHolder.getTBSCertificate()));
  }
  
  public static DeltaCertificateDescriptor trimDeltaCertificateDescriptor(DeltaCertificateDescriptor paramDeltaCertificateDescriptor, TBSCertificate paramTBSCertificate, Extensions paramExtensions) {
    return paramDeltaCertificateDescriptor.trimTo(paramTBSCertificate, paramExtensions);
  }
  
  private static Extensions extractDeltaExtensions(Extensions paramExtensions1, Extensions paramExtensions2) {
    ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
    Enumeration<ASN1ObjectIdentifier> enumeration = paramExtensions2.oids();
    while (enumeration.hasMoreElements()) {
      ASN1ObjectIdentifier aSN1ObjectIdentifier = enumeration.nextElement();
      if (!Extension.deltaCertificateDescriptor.equals((ASN1Primitive)aSN1ObjectIdentifier))
        extensionsGenerator.addExtension(paramExtensions2.getExtension(aSN1ObjectIdentifier)); 
    } 
    if (paramExtensions1 != null) {
      Enumeration<ASN1ObjectIdentifier> enumeration1 = paramExtensions1.oids();
      while (enumeration1.hasMoreElements()) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = enumeration1.nextElement();
        extensionsGenerator.replaceExtension(paramExtensions1.getExtension(aSN1ObjectIdentifier));
      } 
    } 
    return extensionsGenerator.isEmpty() ? null : extensionsGenerator.generate();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cert\DeltaCertificateTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */