package org.bouncycastle.pqc.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.asn1.CMCEPrivateKey;
import org.bouncycastle.pqc.asn1.FalconPrivateKey;
import org.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.bouncycastle.pqc.asn1.SPHINCS256KeyParams;
import org.bouncycastle.pqc.asn1.SPHINCSPLUSPrivateKey;
import org.bouncycastle.pqc.asn1.SPHINCSPLUSPublicKey;
import org.bouncycastle.pqc.asn1.XMSSKeyParams;
import org.bouncycastle.pqc.asn1.XMSSMTKeyParams;
import org.bouncycastle.pqc.asn1.XMSSMTPrivateKey;
import org.bouncycastle.pqc.asn1.XMSSPrivateKey;
import org.bouncycastle.pqc.crypto.bike.BIKEParameters;
import org.bouncycastle.pqc.crypto.bike.BIKEPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.cmce.CMCEParameters;
import org.bouncycastle.pqc.crypto.cmce.CMCEPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumParameters;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumPublicKeyParameters;
import org.bouncycastle.pqc.crypto.falcon.FalconParameters;
import org.bouncycastle.pqc.crypto.falcon.FalconPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.frodo.FrodoParameters;
import org.bouncycastle.pqc.crypto.frodo.FrodoPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.lms.HSSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPublicKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.pqc.crypto.newhope.NHPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.ntru.NTRUParameters;
import org.bouncycastle.pqc.crypto.ntru.NTRUPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.ntruprime.NTRULPRimeParameters;
import org.bouncycastle.pqc.crypto.ntruprime.NTRULPRimePrivateKeyParameters;
import org.bouncycastle.pqc.crypto.ntruprime.SNTRUPrimeParameters;
import org.bouncycastle.pqc.crypto.ntruprime.SNTRUPrimePrivateKeyParameters;
import org.bouncycastle.pqc.crypto.picnic.PicnicParameters;
import org.bouncycastle.pqc.crypto.picnic.PicnicPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.rainbow.RainbowParameters;
import org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.saber.SABERParameters;
import org.bouncycastle.pqc.crypto.saber.SABERPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.snova.SnovaParameters;
import org.bouncycastle.pqc.crypto.snova.SnovaPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusParameters;
import org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xmss.BDS;
import org.bouncycastle.pqc.crypto.xmss.BDSStateMap;
import org.bouncycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSUtil;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class PrivateKeyFactory {
  public static AsymmetricKeyParameter createKey(byte[] paramArrayOfbyte) throws IOException {
    if (paramArrayOfbyte == null)
      throw new IllegalArgumentException("privateKeyInfoData array null"); 
    if (paramArrayOfbyte.length == 0)
      throw new IllegalArgumentException("privateKeyInfoData array empty"); 
    return createKey(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(paramArrayOfbyte)));
  }
  
  public static AsymmetricKeyParameter createKey(InputStream paramInputStream) throws IOException {
    return createKey(PrivateKeyInfo.getInstance((new ASN1InputStream(paramInputStream)).readObject()));
  }
  
  public static AsymmetricKeyParameter createKey(PrivateKeyInfo paramPrivateKeyInfo) throws IOException {
    if (paramPrivateKeyInfo == null)
      throw new IllegalArgumentException("keyInfo array null"); 
    AlgorithmIdentifier algorithmIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm();
    ASN1ObjectIdentifier aSN1ObjectIdentifier = algorithmIdentifier.getAlgorithm();
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)PQCObjectIdentifiers.sphincs256))
      return (AsymmetricKeyParameter)new SPHINCSPrivateKeyParameters(ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets(), Utils.sphincs256LookupTreeAlgName(SPHINCS256KeyParams.getInstance(algorithmIdentifier.getParameters()))); 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)PQCObjectIdentifiers.newHope))
      return (AsymmetricKeyParameter)new NHPrivateKeyParameters(convert(ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets())); 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)PKCSObjectIdentifiers.id_alg_hss_lms_hashsig)) {
      ASN1OctetString aSN1OctetString = parseOctetString(paramPrivateKeyInfo.getPrivateKey(), 64);
      byte[] arrayOfByte = aSN1OctetString.getOctets();
      ASN1BitString aSN1BitString = paramPrivateKeyInfo.getPublicKeyData();
      if (aSN1BitString != null) {
        byte[] arrayOfByte1 = aSN1BitString.getOctets();
        return (AsymmetricKeyParameter)HSSPrivateKeyParameters.getInstance(Arrays.copyOfRange(arrayOfByte, 4, arrayOfByte.length), arrayOfByte1);
      } 
      return (AsymmetricKeyParameter)HSSPrivateKeyParameters.getInstance(Arrays.copyOfRange(arrayOfByte, 4, arrayOfByte.length));
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.sphincsPlus) || aSN1ObjectIdentifier.on(BCObjectIdentifiers.sphincsPlus_interop)) {
      SPHINCSPlusParameters sPHINCSPlusParameters = Utils.sphincsPlusParamsLookup(aSN1ObjectIdentifier);
      ASN1Encodable aSN1Encodable = paramPrivateKeyInfo.parsePrivateKey();
      if (aSN1Encodable instanceof ASN1Sequence) {
        SPHINCSPLUSPrivateKey sPHINCSPLUSPrivateKey = SPHINCSPLUSPrivateKey.getInstance(aSN1Encodable);
        SPHINCSPLUSPublicKey sPHINCSPLUSPublicKey = sPHINCSPLUSPrivateKey.getPublicKey();
        return (AsymmetricKeyParameter)new SPHINCSPlusPrivateKeyParameters(sPHINCSPlusParameters, sPHINCSPLUSPrivateKey.getSkseed(), sPHINCSPLUSPrivateKey.getSkprf(), sPHINCSPLUSPublicKey.getPkseed(), sPHINCSPLUSPublicKey.getPkroot());
      } 
      return (AsymmetricKeyParameter)new SPHINCSPlusPrivateKeyParameters(sPHINCSPlusParameters, ASN1OctetString.getInstance(aSN1Encodable).getOctets());
    } 
    if (Utils.slhdsaParams.containsKey(aSN1ObjectIdentifier)) {
      SLHDSAParameters sLHDSAParameters = Utils.slhdsaParamsLookup(aSN1ObjectIdentifier);
      ASN1OctetString aSN1OctetString = parseOctetString(paramPrivateKeyInfo.getPrivateKey(), sLHDSAParameters.getN() * 4);
      return (AsymmetricKeyParameter)new SLHDSAPrivateKeyParameters(sLHDSAParameters, aSN1OctetString.getOctets());
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.picnic)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      PicnicParameters picnicParameters = Utils.picnicParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new PicnicPrivateKeyParameters(picnicParameters, arrayOfByte);
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_mceliece)) {
      CMCEPrivateKey cMCEPrivateKey = CMCEPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      CMCEParameters cMCEParameters = Utils.mcElieceParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new CMCEPrivateKeyParameters(cMCEParameters, cMCEPrivateKey.getDelta(), cMCEPrivateKey.getC(), cMCEPrivateKey.getG(), cMCEPrivateKey.getAlpha(), cMCEPrivateKey.getS());
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_frodo)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      FrodoParameters frodoParameters = Utils.frodoParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new FrodoPrivateKeyParameters(frodoParameters, arrayOfByte);
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_saber)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      SABERParameters sABERParameters = Utils.saberParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new SABERPrivateKeyParameters(sABERParameters, arrayOfByte);
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_ntru)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      NTRUParameters nTRUParameters = Utils.ntruParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new NTRUPrivateKeyParameters(nTRUParameters, arrayOfByte);
    } 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_alg_ml_kem_512) || aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_alg_ml_kem_768) || aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_alg_ml_kem_1024)) {
      ASN1Primitive aSN1Primitive = parsePrimitiveString(paramPrivateKeyInfo.getPrivateKey(), 64);
      MLKEMParameters mLKEMParameters = Utils.mlkemParamsLookup(aSN1ObjectIdentifier);
      MLKEMPublicKeyParameters mLKEMPublicKeyParameters = null;
      if (paramPrivateKeyInfo.getPublicKeyData() != null)
        mLKEMPublicKeyParameters = PublicKeyFactory.MLKEMConverter.getPublicKeyParams(mLKEMParameters, paramPrivateKeyInfo.getPublicKeyData()); 
      if (aSN1Primitive instanceof ASN1OctetString)
        return (AsymmetricKeyParameter)new MLKEMPrivateKeyParameters(mLKEMParameters, ((ASN1OctetString)aSN1Primitive).getOctets(), mLKEMPublicKeyParameters); 
      if (aSN1Primitive instanceof ASN1Sequence) {
        ASN1Sequence aSN1Sequence = (ASN1Sequence)aSN1Primitive;
        byte[] arrayOfByte1 = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets();
        byte[] arrayOfByte2 = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets();
        MLKEMPrivateKeyParameters mLKEMPrivateKeyParameters = new MLKEMPrivateKeyParameters(mLKEMParameters, arrayOfByte1, mLKEMPublicKeyParameters);
        if (!Arrays.constantTimeAreEqual(mLKEMPrivateKeyParameters.getEncoded(), arrayOfByte2))
          throw new IllegalArgumentException("inconsistent " + mLKEMParameters.getName() + " private key"); 
        return (AsymmetricKeyParameter)mLKEMPrivateKeyParameters;
      } 
      throw new IllegalArgumentException("invalid " + mLKEMParameters.getName() + " private key");
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_ntrulprime)) {
      ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      NTRULPRimeParameters nTRULPRimeParameters = Utils.ntrulprimeParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new NTRULPRimePrivateKeyParameters(nTRULPRimeParameters, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets(), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets(), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(2)).getOctets(), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(3)).getOctets());
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_sntruprime)) {
      ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      SNTRUPrimeParameters sNTRUPrimeParameters = Utils.sntruprimeParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new SNTRUPrimePrivateKeyParameters(sNTRUPrimeParameters, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets(), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets(), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(2)).getOctets(), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(3)).getOctets(), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(4)).getOctets());
    } 
    if (Utils.mldsaParams.containsKey(aSN1ObjectIdentifier)) {
      ASN1Primitive aSN1Primitive = parsePrimitiveString(paramPrivateKeyInfo.getPrivateKey(), 32);
      MLDSAParameters mLDSAParameters = Utils.mldsaParamsLookup(aSN1ObjectIdentifier);
      MLDSAPublicKeyParameters mLDSAPublicKeyParameters = null;
      if (paramPrivateKeyInfo.getPublicKeyData() != null)
        mLDSAPublicKeyParameters = PublicKeyFactory.MLDSAConverter.getPublicKeyParams(mLDSAParameters, paramPrivateKeyInfo.getPublicKeyData()); 
      if (aSN1Primitive instanceof ASN1OctetString)
        return (AsymmetricKeyParameter)new MLDSAPrivateKeyParameters(mLDSAParameters, ((ASN1OctetString)aSN1Primitive).getOctets(), mLDSAPublicKeyParameters); 
      if (aSN1Primitive instanceof ASN1Sequence) {
        ASN1Sequence aSN1Sequence = (ASN1Sequence)aSN1Primitive;
        byte[] arrayOfByte1 = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets();
        byte[] arrayOfByte2 = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets();
        MLDSAPrivateKeyParameters mLDSAPrivateKeyParameters = new MLDSAPrivateKeyParameters(mLDSAParameters, arrayOfByte1, mLDSAPublicKeyParameters);
        if (!Arrays.constantTimeAreEqual(mLDSAPrivateKeyParameters.getEncoded(), arrayOfByte2))
          throw new IllegalArgumentException("inconsistent " + mLDSAParameters.getName() + " private key"); 
        return (AsymmetricKeyParameter)mLDSAPrivateKeyParameters;
      } 
      throw new IllegalArgumentException("invalid " + mLDSAParameters.getName() + " private key");
    } 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)BCObjectIdentifiers.dilithium2) || aSN1ObjectIdentifier.equals((ASN1Primitive)BCObjectIdentifiers.dilithium3) || aSN1ObjectIdentifier.equals((ASN1Primitive)BCObjectIdentifiers.dilithium5)) {
      ASN1Encodable aSN1Encodable = paramPrivateKeyInfo.parsePrivateKey();
      DilithiumParameters dilithiumParameters = Utils.dilithiumParamsLookup(aSN1ObjectIdentifier);
      if (aSN1Encodable instanceof ASN1Sequence) {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(aSN1Encodable);
        int i = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).intValueExact();
        if (i != 0)
          throw new IOException("unknown private key version: " + i); 
        if (paramPrivateKeyInfo.getPublicKeyData() != null) {
          DilithiumPublicKeyParameters dilithiumPublicKeyParameters = PublicKeyFactory.DilithiumConverter.getPublicKeyParams(dilithiumParameters, paramPrivateKeyInfo.getPublicKeyData());
          return (AsymmetricKeyParameter)new DilithiumPrivateKeyParameters(dilithiumParameters, ASN1BitString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(2)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(3)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(4)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(5)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(6)).getOctets(), dilithiumPublicKeyParameters.getT1());
        } 
        return (AsymmetricKeyParameter)new DilithiumPrivateKeyParameters(dilithiumParameters, ASN1BitString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(2)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(3)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(4)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(5)).getOctets(), ASN1BitString.getInstance(aSN1Sequence.getObjectAt(6)).getOctets(), null);
      } 
      if (aSN1Encodable instanceof org.bouncycastle.asn1.DEROctetString) {
        byte[] arrayOfByte = ASN1OctetString.getInstance(aSN1Encodable).getOctets();
        if (paramPrivateKeyInfo.getPublicKeyData() != null) {
          DilithiumPublicKeyParameters dilithiumPublicKeyParameters = PublicKeyFactory.DilithiumConverter.getPublicKeyParams(dilithiumParameters, paramPrivateKeyInfo.getPublicKeyData());
          return (AsymmetricKeyParameter)new DilithiumPrivateKeyParameters(dilithiumParameters, arrayOfByte, dilithiumPublicKeyParameters);
        } 
        return (AsymmetricKeyParameter)new DilithiumPrivateKeyParameters(dilithiumParameters, arrayOfByte, null);
      } 
      throw new IOException("not supported");
    } 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)BCObjectIdentifiers.falcon_512) || aSN1ObjectIdentifier.equals((ASN1Primitive)BCObjectIdentifiers.falcon_1024)) {
      FalconPrivateKey falconPrivateKey = FalconPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      FalconParameters falconParameters = Utils.falconParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new FalconPrivateKeyParameters(falconParameters, falconPrivateKey.getf(), falconPrivateKey.getG(), falconPrivateKey.getF(), falconPrivateKey.getPublicKey().getH());
    } 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)BCObjectIdentifiers.old_falcon_512) || aSN1ObjectIdentifier.equals((ASN1Primitive)BCObjectIdentifiers.old_falcon_1024)) {
      FalconPrivateKey falconPrivateKey = FalconPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      FalconParameters falconParameters = Utils.falconParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new FalconPrivateKeyParameters(falconParameters, falconPrivateKey.getf(), falconPrivateKey.getG(), falconPrivateKey.getF(), falconPrivateKey.getPublicKey().getH());
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_bike)) {
      byte[] arrayOfByte1 = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      BIKEParameters bIKEParameters = Utils.bikeParamsLookup(aSN1ObjectIdentifier);
      byte[] arrayOfByte2 = Arrays.copyOfRange(arrayOfByte1, 0, bIKEParameters.getRByte());
      byte[] arrayOfByte3 = Arrays.copyOfRange(arrayOfByte1, bIKEParameters.getRByte(), 2 * bIKEParameters.getRByte());
      byte[] arrayOfByte4 = Arrays.copyOfRange(arrayOfByte1, 2 * bIKEParameters.getRByte(), arrayOfByte1.length);
      return (AsymmetricKeyParameter)new BIKEPrivateKeyParameters(bIKEParameters, arrayOfByte2, arrayOfByte3, arrayOfByte4);
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.pqc_kem_hqc)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      HQCParameters hQCParameters = Utils.hqcParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new HQCPrivateKeyParameters(hQCParameters, arrayOfByte);
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.rainbow)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      RainbowParameters rainbowParameters = Utils.rainbowParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new RainbowPrivateKeyParameters(rainbowParameters, arrayOfByte);
    } 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)PQCObjectIdentifiers.xmss)) {
      XMSSKeyParams xMSSKeyParams = XMSSKeyParams.getInstance(algorithmIdentifier.getParameters());
      ASN1ObjectIdentifier aSN1ObjectIdentifier1 = xMSSKeyParams.getTreeDigest().getAlgorithm();
      XMSSPrivateKey xMSSPrivateKey = XMSSPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      try {
        XMSSPrivateKeyParameters.Builder builder = (new XMSSPrivateKeyParameters.Builder(new XMSSParameters(xMSSKeyParams.getHeight(), Utils.getDigest(aSN1ObjectIdentifier1)))).withIndex(xMSSPrivateKey.getIndex()).withSecretKeySeed(xMSSPrivateKey.getSecretKeySeed()).withSecretKeyPRF(xMSSPrivateKey.getSecretKeyPRF()).withPublicSeed(xMSSPrivateKey.getPublicSeed()).withRoot(xMSSPrivateKey.getRoot());
        if (xMSSPrivateKey.getVersion() != 0)
          builder.withMaxIndex(xMSSPrivateKey.getMaxIndex()); 
        if (xMSSPrivateKey.getBdsState() != null) {
          BDS bDS = (BDS)XMSSUtil.deserialize(xMSSPrivateKey.getBdsState(), BDS.class);
          builder.withBDSState(bDS.withWOTSDigest(aSN1ObjectIdentifier1));
        } 
        return (AsymmetricKeyParameter)builder.build();
      } catch (ClassNotFoundException classNotFoundException) {
        throw new IOException("ClassNotFoundException processing BDS state: " + classNotFoundException.getMessage());
      } 
    } 
    if (aSN1ObjectIdentifier.equals((ASN1Primitive)PQCObjectIdentifiers.xmss_mt)) {
      XMSSMTKeyParams xMSSMTKeyParams = XMSSMTKeyParams.getInstance(algorithmIdentifier.getParameters());
      ASN1ObjectIdentifier aSN1ObjectIdentifier1 = xMSSMTKeyParams.getTreeDigest().getAlgorithm();
      try {
        XMSSMTPrivateKey xMSSMTPrivateKey = XMSSMTPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey());
        XMSSMTPrivateKeyParameters.Builder builder = (new XMSSMTPrivateKeyParameters.Builder(new XMSSMTParameters(xMSSMTKeyParams.getHeight(), xMSSMTKeyParams.getLayers(), Utils.getDigest(aSN1ObjectIdentifier1)))).withIndex(xMSSMTPrivateKey.getIndex()).withSecretKeySeed(xMSSMTPrivateKey.getSecretKeySeed()).withSecretKeyPRF(xMSSMTPrivateKey.getSecretKeyPRF()).withPublicSeed(xMSSMTPrivateKey.getPublicSeed()).withRoot(xMSSMTPrivateKey.getRoot());
        if (xMSSMTPrivateKey.getVersion() != 0)
          builder.withMaxIndex(xMSSMTPrivateKey.getMaxIndex()); 
        if (xMSSMTPrivateKey.getBdsState() != null) {
          BDSStateMap bDSStateMap = (BDSStateMap)XMSSUtil.deserialize(xMSSMTPrivateKey.getBdsState(), BDSStateMap.class);
          builder.withBDSState(bDSStateMap.withWOTSDigest(aSN1ObjectIdentifier1));
        } 
        return (AsymmetricKeyParameter)builder.build();
      } catch (ClassNotFoundException classNotFoundException) {
        throw new IOException("ClassNotFoundException processing BDS state: " + classNotFoundException.getMessage());
      } 
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.mayo)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      MayoParameters mayoParameters = Utils.mayoParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new MayoPrivateKeyParameters(mayoParameters, arrayOfByte);
    } 
    if (aSN1ObjectIdentifier.on(BCObjectIdentifiers.snova)) {
      byte[] arrayOfByte = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
      SnovaParameters snovaParameters = Utils.snovaParamsLookup(aSN1ObjectIdentifier);
      return (AsymmetricKeyParameter)new SnovaPrivateKeyParameters(snovaParameters, arrayOfByte);
    } 
    throw new RuntimeException("algorithm identifier in private key not recognised");
  }
  
  private static ASN1OctetString parseOctetString(ASN1OctetString paramASN1OctetString, int paramInt) throws IOException {
    byte[] arrayOfByte = paramASN1OctetString.getOctets();
    if (arrayOfByte.length == paramInt)
      return paramASN1OctetString; 
    ASN1OctetString aSN1OctetString = Utils.parseOctetData(arrayOfByte);
    return (aSN1OctetString != null) ? ASN1OctetString.getInstance(aSN1OctetString) : paramASN1OctetString;
  }
  
  private static ASN1Primitive parsePrimitiveString(ASN1OctetString paramASN1OctetString, int paramInt) throws IOException {
    byte[] arrayOfByte = paramASN1OctetString.getOctets();
    if (arrayOfByte.length == paramInt)
      return (ASN1Primitive)paramASN1OctetString; 
    ASN1Primitive aSN1Primitive = Utils.parseData(arrayOfByte);
    return (ASN1Primitive)((aSN1Primitive instanceof ASN1OctetString) ? ASN1OctetString.getInstance(aSN1Primitive) : ((aSN1Primitive instanceof ASN1Sequence) ? ASN1Sequence.getInstance(aSN1Primitive) : paramASN1OctetString));
  }
  
  private static short[] convert(byte[] paramArrayOfbyte) {
    short[] arrayOfShort = new short[paramArrayOfbyte.length / 2];
    for (byte b = 0; b != arrayOfShort.length; b++)
      arrayOfShort[b] = Pack.littleEndianToShort(paramArrayOfbyte, b * 2); 
    return arrayOfShort;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypt\\util\PrivateKeyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */