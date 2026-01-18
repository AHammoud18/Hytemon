package org.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1ParsingException;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;

public class ExtensionsGenerator {
  private Hashtable extensions = new Hashtable<>();
  
  private Vector extOrdering = new Vector();
  
  private static final Set dupsAllowed;
  
  public void reset() {
    this.extensions = new Hashtable<>();
    this.extOrdering = new Vector();
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable) throws IOException {
    Extension extension = (Extension)this.extensions.get(paramASN1ObjectIdentifier);
    if (extension != null) {
      implAddExtensionDup(extension, paramBoolean, paramASN1Encodable.toASN1Primitive().getEncoded("DER"));
    } else {
      implAddExtension(new Extension(paramASN1ObjectIdentifier, paramBoolean, (ASN1OctetString)new DEROctetString(paramASN1Encodable)));
    } 
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfbyte) {
    Extension extension = (Extension)this.extensions.get(paramASN1ObjectIdentifier);
    if (extension != null) {
      implAddExtensionDup(extension, paramBoolean, paramArrayOfbyte);
    } else {
      implAddExtension(new Extension(paramASN1ObjectIdentifier, paramBoolean, paramArrayOfbyte));
    } 
  }
  
  public void addExtension(Extension paramExtension) {
    if (hasExtension(paramExtension.getExtnId()))
      throw new IllegalArgumentException("extension " + paramExtension.getExtnId() + " already added"); 
    implAddExtension(paramExtension);
  }
  
  public void addExtension(Extensions paramExtensions) {
    addExtensions(paramExtensions);
  }
  
  public void addExtensions(Extensions paramExtensions) {
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = paramExtensions.getExtensionOIDs();
    for (byte b = 0; b != arrayOfASN1ObjectIdentifier.length; b++) {
      ASN1ObjectIdentifier aSN1ObjectIdentifier = arrayOfASN1ObjectIdentifier[b];
      Extension extension = paramExtensions.getExtension(aSN1ObjectIdentifier);
      addExtension(ASN1ObjectIdentifier.getInstance(aSN1ObjectIdentifier), extension.isCritical(), extension.getExtnValue().getOctets());
    } 
  }
  
  public void replaceExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable) throws IOException {
    replaceExtension(new Extension(paramASN1ObjectIdentifier, paramBoolean, (ASN1OctetString)new DEROctetString(paramASN1Encodable)));
  }
  
  public void replaceExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfbyte) {
    replaceExtension(new Extension(paramASN1ObjectIdentifier, paramBoolean, paramArrayOfbyte));
  }
  
  public void replaceExtension(Extension paramExtension) {
    if (!hasExtension(paramExtension.getExtnId()))
      throw new IllegalArgumentException("extension " + paramExtension.getExtnId() + " not present"); 
    this.extensions.put(paramExtension.getExtnId(), paramExtension);
  }
  
  public void removeExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    if (!hasExtension(paramASN1ObjectIdentifier))
      throw new IllegalArgumentException("extension " + paramASN1ObjectIdentifier + " not present"); 
    this.extOrdering.removeElement(paramASN1ObjectIdentifier);
    this.extensions.remove(paramASN1ObjectIdentifier);
  }
  
  public boolean hasExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    return this.extensions.containsKey(paramASN1ObjectIdentifier);
  }
  
  public Extension getExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier) {
    return (Extension)this.extensions.get(paramASN1ObjectIdentifier);
  }
  
  public boolean isEmpty() {
    return this.extOrdering.isEmpty();
  }
  
  public Extensions generate() {
    Extension[] arrayOfExtension = new Extension[this.extOrdering.size()];
    for (byte b = 0; b != this.extOrdering.size(); b++)
      arrayOfExtension[b] = (Extension)this.extensions.get(this.extOrdering.elementAt(b)); 
    return new Extensions(arrayOfExtension);
  }
  
  private void implAddExtension(Extension paramExtension) {
    this.extOrdering.addElement(paramExtension.getExtnId());
    this.extensions.put(paramExtension.getExtnId(), paramExtension);
  }
  
  private void implAddExtensionDup(Extension paramExtension, boolean paramBoolean, byte[] paramArrayOfbyte) {
    ASN1ObjectIdentifier aSN1ObjectIdentifier = paramExtension.getExtnId();
    if (!dupsAllowed.contains(aSN1ObjectIdentifier))
      throw new IllegalArgumentException("extension " + aSN1ObjectIdentifier + " already added"); 
    ASN1Sequence aSN1Sequence1 = ASN1Sequence.getInstance(DEROctetString.getInstance(paramExtension.getExtnValue()).getOctets());
    ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(paramArrayOfbyte);
    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(aSN1Sequence1.size() + aSN1Sequence2.size());
    Enumeration<ASN1Encodable> enumeration = aSN1Sequence1.getObjects();
    while (enumeration.hasMoreElements())
      aSN1EncodableVector.add(enumeration.nextElement()); 
    enumeration = aSN1Sequence2.getObjects();
    while (enumeration.hasMoreElements())
      aSN1EncodableVector.add(enumeration.nextElement()); 
    try {
      this.extensions.put(aSN1ObjectIdentifier, new Extension(aSN1ObjectIdentifier, paramBoolean, (ASN1OctetString)new DEROctetString((ASN1Encodable)new DERSequence(aSN1EncodableVector))));
    } catch (IOException iOException) {
      throw new ASN1ParsingException(iOException.getMessage(), iOException);
    } 
  }
  
  static {
    HashSet<ASN1ObjectIdentifier> hashSet = new HashSet();
    hashSet.add(Extension.subjectAlternativeName);
    hashSet.add(Extension.issuerAlternativeName);
    hashSet.add(Extension.subjectDirectoryAttributes);
    hashSet.add(Extension.certificateIssuer);
    dupsAllowed = Collections.unmodifiableSet(hashSet);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\asn1\x509\ExtensionsGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */