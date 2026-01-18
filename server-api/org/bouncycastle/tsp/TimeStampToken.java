package org.bouncycastle.tsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.tsp.TSTInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Store;

public class TimeStampToken {
  CMSSignedData tsToken;
  
  SignerInformation tsaSignerInfo;
  
  TimeStampTokenInfo tstInfo;
  
  ESSCertIDv2 certID;
  
  public TimeStampToken(ContentInfo paramContentInfo) throws TSPException, IOException {
    this(getSignedData(paramContentInfo));
  }
  
  private static CMSSignedData getSignedData(ContentInfo paramContentInfo) throws TSPException {
    try {
      return new CMSSignedData(paramContentInfo);
    } catch (CMSException cMSException) {
      throw new TSPException("TSP parsing error: " + cMSException.getMessage(), cMSException.getCause());
    } 
  }
  
  public TimeStampToken(CMSSignedData paramCMSSignedData) throws TSPException, IOException {
    this.tsToken = paramCMSSignedData;
    if (!this.tsToken.getSignedContentTypeOID().equals(PKCSObjectIdentifiers.id_ct_TSTInfo.getId()))
      throw new TSPValidationException("ContentInfo object not for a time stamp."); 
    Collection<SignerInformation> collection = this.tsToken.getSignerInfos().getSigners();
    if (collection.size() != 1)
      throw new IllegalArgumentException("Time-stamp token signed by " + collection.size() + " signers, but it must contain just the TSA signature."); 
    this.tsaSignerInfo = collection.iterator().next();
    try {
      CMSTypedData cMSTypedData = this.tsToken.getSignedContent();
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      cMSTypedData.write(byteArrayOutputStream);
      this.tstInfo = new TimeStampTokenInfo(TSTInfo.getInstance(byteArrayOutputStream.toByteArray()));
      Attribute attribute = this.tsaSignerInfo.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
      if (attribute != null) {
        SigningCertificate signingCertificate = SigningCertificate.getInstance(attribute.getAttrValues().getObjectAt(0));
        this.certID = ESSCertIDv2.from(ESSCertID.getInstance(signingCertificate.getCerts()[0]));
      } else {
        attribute = this.tsaSignerInfo.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
        if (attribute == null)
          throw new TSPValidationException("no signing certificate attribute found, time stamp invalid."); 
        SigningCertificateV2 signingCertificateV2 = SigningCertificateV2.getInstance(attribute.getAttrValues().getObjectAt(0));
        this.certID = ESSCertIDv2.getInstance(signingCertificateV2.getCerts()[0]);
      } 
    } catch (CMSException cMSException) {
      throw new TSPException(cMSException.getMessage(), cMSException.getUnderlyingException());
    } 
  }
  
  public TimeStampTokenInfo getTimeStampInfo() {
    return this.tstInfo;
  }
  
  public SignerId getSID() {
    return this.tsaSignerInfo.getSID();
  }
  
  public AttributeTable getSignedAttributes() {
    return this.tsaSignerInfo.getSignedAttributes();
  }
  
  public AttributeTable getUnsignedAttributes() {
    return this.tsaSignerInfo.getUnsignedAttributes();
  }
  
  public Store<X509CertificateHolder> getCertificates() {
    return this.tsToken.getCertificates();
  }
  
  public Store<X509CRLHolder> getCRLs() {
    return this.tsToken.getCRLs();
  }
  
  public Store<X509AttributeCertificateHolder> getAttributeCertificates() {
    return this.tsToken.getAttributeCertificates();
  }
  
  public void validate(SignerInformationVerifier paramSignerInformationVerifier) throws TSPException, TSPValidationException {
    if (!paramSignerInformationVerifier.hasAssociatedCertificate())
      throw new IllegalArgumentException("verifier provider needs an associated certificate"); 
    try {
      X509CertificateHolder x509CertificateHolder = paramSignerInformationVerifier.getAssociatedCertificate();
      DigestCalculator digestCalculator = paramSignerInformationVerifier.getDigestCalculator(this.certID.getHashAlgorithm());
      OutputStream outputStream = digestCalculator.getOutputStream();
      outputStream.write(x509CertificateHolder.getEncoded());
      outputStream.close();
      if (!Arrays.constantTimeAreEqual(this.certID.getCertHashObject().getOctets(), digestCalculator.getDigest()))
        throw new TSPValidationException("certificate hash does not match certID hash."); 
      IssuerSerial issuerSerial = this.certID.getIssuerSerial();
      if (issuerSerial != null) {
        Certificate certificate = x509CertificateHolder.toASN1Structure();
        if (!issuerSerial.getSerial().equals((ASN1Primitive)certificate.getSerialNumber()))
          throw new TSPValidationException("certificate serial number does not match certID for signature."); 
        GeneralName[] arrayOfGeneralName = issuerSerial.getIssuer().getNames();
        boolean bool = false;
        for (byte b = 0; b != arrayOfGeneralName.length; b++) {
          if (arrayOfGeneralName[b].getTagNo() == 4 && X500Name.getInstance(arrayOfGeneralName[b].getName()).equals(certificate.getIssuer())) {
            bool = true;
            break;
          } 
        } 
        if (!bool)
          throw new TSPValidationException("certificate name does not match certID for signature. "); 
      } 
      TSPUtil.validateCertificate(x509CertificateHolder);
      if (!x509CertificateHolder.isValidOn(this.tstInfo.getGenTime()))
        throw new TSPValidationException("certificate not valid when time stamp created."); 
      if (!this.tsaSignerInfo.verify(paramSignerInformationVerifier))
        throw new TSPValidationException("signature not created by certificate."); 
    } catch (CMSException cMSException) {
      if (cMSException.getUnderlyingException() != null)
        throw new TSPException(cMSException.getMessage(), cMSException.getUnderlyingException()); 
      throw new TSPException("CMS exception: " + cMSException, cMSException);
    } catch (IOException iOException) {
      throw new TSPException("problem processing certificate: " + iOException, iOException);
    } catch (OperatorCreationException operatorCreationException) {
      throw new TSPException("unable to create digest: " + operatorCreationException.getMessage(), operatorCreationException);
    } 
  }
  
  public boolean isSignatureValid(SignerInformationVerifier paramSignerInformationVerifier) throws TSPException {
    try {
      return this.tsaSignerInfo.verify(paramSignerInformationVerifier);
    } catch (CMSException cMSException) {
      if (cMSException.getUnderlyingException() != null)
        throw new TSPException(cMSException.getMessage(), cMSException.getUnderlyingException()); 
      throw new TSPException("CMS exception: " + cMSException, cMSException);
    } 
  }
  
  public CMSSignedData toCMSSignedData() {
    return this.tsToken;
  }
  
  public byte[] getEncoded() throws IOException {
    return this.tsToken.getEncoded("DL");
  }
  
  public byte[] getEncoded(String paramString) throws IOException {
    return this.tsToken.getEncoded(paramString);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\tsp\TimeStampToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */