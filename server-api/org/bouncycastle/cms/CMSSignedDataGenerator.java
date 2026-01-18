package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;

public class CMSSignedDataGenerator extends CMSSignedGenerator {
  private boolean isDefiniteLength = false;
  
  public CMSSignedDataGenerator() {}
  
  public CMSSignedDataGenerator(DigestAlgorithmIdentifierFinder paramDigestAlgorithmIdentifierFinder) {
    super(paramDigestAlgorithmIdentifierFinder);
  }
  
  public void setDefiniteLengthEncoding(boolean paramBoolean) {
    this.isDefiniteLength = paramBoolean;
  }
  
  public CMSSignedData generate(CMSTypedData paramCMSTypedData) throws CMSException {
    return generate(paramCMSTypedData, false);
  }
  
  public CMSSignedData generate(CMSTypedData paramCMSTypedData, boolean paramBoolean) throws CMSException {
    BEROctetString bEROctetString;
    LinkedHashSet<AlgorithmIdentifier> linkedHashSet = new LinkedHashSet();
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
    this.digests.clear();
    for (SignerInformation signerInformation : this._signers) {
      CMSUtils.addDigestAlgs(linkedHashSet, signerInformation, this.digestAlgIdFinder);
      aSN1EncodableVector.add((ASN1Encodable)signerInformation.toASN1Structure());
    } 
    ASN1ObjectIdentifier aSN1ObjectIdentifier = paramCMSTypedData.getContentType();
    DEROctetString dEROctetString = null;
    if (paramCMSTypedData.getContent() != null)
      if (paramBoolean) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        writeContentViaSignerGens(paramCMSTypedData, byteArrayOutputStream);
        if (this.isDefiniteLength) {
          dEROctetString = new DEROctetString(byteArrayOutputStream.toByteArray());
        } else {
          bEROctetString = new BEROctetString(byteArrayOutputStream.toByteArray());
        } 
      } else {
        writeContentViaSignerGens(paramCMSTypedData, (OutputStream)null);
      }  
    for (SignerInfoGenerator signerInfoGenerator : this.signerGens) {
      SignerInfo signerInfo = generateSignerInfo(signerInfoGenerator, aSN1ObjectIdentifier);
      linkedHashSet.add(signerInfo.getDigestAlgorithm());
      aSN1EncodableVector.add((ASN1Encodable)signerInfo);
    } 
    ASN1Set aSN1Set1 = createSetFromList(this.certs, this.isDefiniteLength);
    ASN1Set aSN1Set2 = createSetFromList(this.crls, this.isDefiniteLength);
    ContentInfo contentInfo1 = new ContentInfo(aSN1ObjectIdentifier, (ASN1Encodable)bEROctetString);
    SignedData signedData = new SignedData(CMSUtils.convertToDlSet(linkedHashSet), contentInfo1, aSN1Set1, aSN1Set2, (ASN1Set)new DERSet(aSN1EncodableVector));
    ContentInfo contentInfo2 = new ContentInfo(CMSObjectIdentifiers.signedData, (ASN1Encodable)signedData);
    return new CMSSignedData(paramCMSTypedData, contentInfo2);
  }
  
  public SignerInformationStore generateCounterSigners(SignerInformation paramSignerInformation) throws CMSException {
    this.digests.clear();
    CMSProcessableByteArray cMSProcessableByteArray = new CMSProcessableByteArray(null, paramSignerInformation.getSignature());
    ArrayList<SignerInformation> arrayList = new ArrayList();
    for (SignerInformation signerInformation : this._signers) {
      SignerInfo signerInfo = signerInformation.toASN1Structure();
      arrayList.add(new SignerInformation(signerInfo, null, cMSProcessableByteArray, null));
    } 
    writeContentViaSignerGens(cMSProcessableByteArray, (OutputStream)null);
    for (SignerInfoGenerator signerInfoGenerator : this.signerGens) {
      SignerInfo signerInfo = generateSignerInfo(signerInfoGenerator, (ASN1ObjectIdentifier)null);
      arrayList.add(new SignerInformation(signerInfo, null, cMSProcessableByteArray, null));
    } 
    return new SignerInformationStore(arrayList);
  }
  
  private SignerInfo generateSignerInfo(SignerInfoGenerator paramSignerInfoGenerator, ASN1ObjectIdentifier paramASN1ObjectIdentifier) throws CMSException {
    SignerInfo signerInfo = paramSignerInfoGenerator.generate(paramASN1ObjectIdentifier);
    byte[] arrayOfByte = paramSignerInfoGenerator.getCalculatedDigest();
    if (arrayOfByte != null)
      this.digests.put(signerInfo.getDigestAlgorithm().getAlgorithm().getId(), arrayOfByte); 
    return signerInfo;
  }
  
  private void writeContentViaSignerGens(CMSTypedData paramCMSTypedData, OutputStream paramOutputStream) throws CMSException {
    OutputStream outputStream = CMSUtils.attachSignersToOutputStream(this.signerGens, paramOutputStream);
    outputStream = CMSUtils.getSafeOutputStream(outputStream);
    try {
      paramCMSTypedData.write(outputStream);
      outputStream.close();
    } catch (IOException iOException) {
      throw new CMSException("data processing exception: " + iOException.getMessage(), iOException);
    } 
  }
  
  private static ASN1Set createSetFromList(List paramList, boolean paramBoolean) {
    return (paramList.size() < 1) ? null : (paramBoolean ? CMSUtils.createDlSetFromList(paramList) : CMSUtils.createBerSetFromList(paramList));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\CMSSignedDataGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */