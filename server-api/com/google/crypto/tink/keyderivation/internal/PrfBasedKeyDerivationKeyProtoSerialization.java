/*     */ package com.google.crypto.tink.keyderivation.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.TinkProtoParametersFormat;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Serialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.keyderivation.PrfBasedKeyDerivationKey;
/*     */ import com.google.crypto.tink.keyderivation.PrfBasedKeyDerivationParameters;
/*     */ import com.google.crypto.tink.prf.PrfKey;
/*     */ import com.google.crypto.tink.prf.PrfParameters;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.PrfBasedDeriverKey;
/*     */ import com.google.crypto.tink.proto.PrfBasedDeriverKeyFormat;
/*     */ import com.google.crypto.tink.proto.PrfBasedDeriverParams;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PrfBasedKeyDerivationKeyProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey";
/*  57 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final ParametersSerializer<PrfBasedKeyDerivationParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(PrfBasedKeyDerivationKeyProtoSerialization::serializeParameters, PrfBasedKeyDerivationParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(PrfBasedKeyDerivationKeyProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final KeySerializer<PrfBasedKeyDerivationKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(PrfBasedKeyDerivationKeyProtoSerialization::serializeKey, PrfBasedKeyDerivationKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(PrfBasedKeyDerivationKeyProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PrfBasedKeyDerivationParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     PrfBasedDeriverKeyFormat format;
/*  88 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey")) {
/*  89 */       throw new IllegalArgumentException("Wrong type URL in call to PrfBasedKeyDerivationKeyProtoSerialization.parseParameters: " + serialization
/*     */           
/*  91 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  96 */       format = PrfBasedDeriverKeyFormat.parseFrom(serialization
/*  97 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/*  98 */     } catch (InvalidProtocolBufferException e) {
/*  99 */       throw new GeneralSecurityException("Parsing PrfBasedDeriverKeyFormat failed: ", e);
/*     */     } 
/*     */ 
/*     */     
/* 103 */     Parameters derivedKeyParameters = TinkProtoParametersFormat.parse(format.getParams().getDerivedKeyTemplate().toByteArray());
/*     */     
/* 105 */     Parameters prfParameters = TinkProtoParametersFormat.parse(format.getPrfKeyTemplate().toByteArray());
/* 106 */     if (!(prfParameters instanceof PrfParameters)) {
/* 107 */       throw new GeneralSecurityException("Non-PRF parameters stored in the field prf_key_template");
/*     */     }
/*     */     
/* 110 */     if (serialization.getKeyTemplate().getOutputPrefixType() != format
/* 111 */       .getParams().getDerivedKeyTemplate().getOutputPrefixType()) {
/* 112 */       throw new GeneralSecurityException("Output-Prefix mismatch in parameters while parsing " + format);
/*     */     }
/*     */ 
/*     */     
/* 116 */     return PrfBasedKeyDerivationParameters.builder()
/* 117 */       .setPrfParameters((PrfParameters)prfParameters)
/* 118 */       .setDerivedKeyParameters(derivedKeyParameters)
/* 119 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(PrfBasedKeyDerivationParameters parameters) throws GeneralSecurityException {
/*     */     try {
/* 126 */       byte[] serializedPrfParameters = TinkProtoParametersFormat.serialize((Parameters)parameters.getPrfParameters());
/*     */       
/* 128 */       KeyTemplate prfKeyTemplate = KeyTemplate.parseFrom(serializedPrfParameters, ExtensionRegistryLite.getEmptyRegistry());
/*     */       
/* 130 */       byte[] serializedDerivedKeyParameters = TinkProtoParametersFormat.serialize(parameters.getDerivedKeyParameters());
/*     */       
/* 132 */       KeyTemplate derivedKeyTemplate = KeyTemplate.parseFrom(serializedDerivedKeyParameters, 
/* 133 */           ExtensionRegistryLite.getEmptyRegistry());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       PrfBasedDeriverKeyFormat format = PrfBasedDeriverKeyFormat.newBuilder().setPrfKeyTemplate(prfKeyTemplate).setParams(PrfBasedDeriverParams.newBuilder().setDerivedKeyTemplate(derivedKeyTemplate)).build();
/* 140 */       return ProtoParametersSerialization.create(
/* 141 */           KeyTemplate.newBuilder()
/* 142 */           .setTypeUrl("type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey")
/* 143 */           .setValue(format.toByteString())
/* 144 */           .setOutputPrefixType(derivedKeyTemplate.getOutputPrefixType())
/* 145 */           .build());
/* 146 */     } catch (InvalidProtocolBufferException e) {
/* 147 */       throw new GeneralSecurityException("Serializing PrfBasedKeyDerivationParameters failed: ", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static ProtoKeySerialization serializeKey(PrfBasedKeyDerivationKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 157 */     ProtoKeySerialization prfKeySerialization = (ProtoKeySerialization)MutableSerializationRegistry.globalInstance().serializeKey((Key)key.getPrfKey(), ProtoKeySerialization.class, access);
/*     */ 
/*     */     
/* 160 */     ProtoParametersSerialization derivedKeyParametersSerialization = (ProtoParametersSerialization)MutableSerializationRegistry.globalInstance().serializeParameters(key
/* 161 */         .getParameters().getDerivedKeyParameters(), ProtoParametersSerialization.class);
/* 162 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey", 
/*     */         
/* 164 */         PrfBasedDeriverKey.newBuilder()
/* 165 */         .setPrfKey(
/* 166 */           KeyData.newBuilder()
/* 167 */           .setValue(prfKeySerialization.getValue())
/* 168 */           .setTypeUrl(prfKeySerialization.getTypeUrl())
/* 169 */           .setKeyMaterialType(prfKeySerialization.getKeyMaterialType()))
/* 170 */         .setParams(
/* 171 */           PrfBasedDeriverParams.newBuilder()
/* 172 */           .setDerivedKeyTemplate(derivedKeyParametersSerialization.getKeyTemplate()))
/* 173 */         .build()
/* 174 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, derivedKeyParametersSerialization
/*     */         
/* 176 */         .getKeyTemplate().getOutputPrefixType(), key
/* 177 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static PrfBasedKeyDerivationKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 185 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey")) {
/* 186 */       throw new IllegalArgumentException("Wrong type URL in call to PrfBasedKeyDerivationKey.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 191 */       PrfBasedDeriverKey protoKey = PrfBasedDeriverKey.parseFrom(serialization
/* 192 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/*     */       
/* 194 */       ProtoKeySerialization prfKeySerialization = ProtoKeySerialization.create(protoKey
/* 195 */           .getPrfKey().getTypeUrl(), protoKey
/* 196 */           .getPrfKey().getValue(), protoKey
/* 197 */           .getPrfKey().getKeyMaterialType(), OutputPrefixType.RAW, null);
/*     */ 
/*     */ 
/*     */       
/* 201 */       Key prfKeyUncast = MutableSerializationRegistry.globalInstance().parseKey((Serialization)prfKeySerialization, access);
/* 202 */       if (!(prfKeyUncast instanceof PrfKey)) {
/* 203 */         throw new GeneralSecurityException("Non-PRF key stored in the field prf_key");
/*     */       }
/* 205 */       PrfKey prfKey = (PrfKey)prfKeyUncast;
/*     */       
/* 207 */       ProtoParametersSerialization derivedKeyParametersSerialization = ProtoParametersSerialization.checkedCreate(protoKey.getParams().getDerivedKeyTemplate());
/*     */ 
/*     */       
/* 210 */       Parameters derivedKeyParameters = MutableSerializationRegistry.globalInstance().parseParameters((Serialization)derivedKeyParametersSerialization);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 215 */       PrfBasedKeyDerivationParameters parameters = PrfBasedKeyDerivationParameters.builder().setDerivedKeyParameters(derivedKeyParameters).setPrfParameters(prfKey.getParameters()).build();
/*     */       
/* 217 */       if (serialization.getOutputPrefixType() != derivedKeyParametersSerialization
/* 218 */         .getKeyTemplate().getOutputPrefixType()) {
/* 219 */         throw new GeneralSecurityException("Output-Prefix mismatch in parameters while parsing PrfBasedKeyDerivationKey with parameters " + parameters);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       return PrfBasedKeyDerivationKey.create(parameters, prfKey, serialization
/* 226 */           .getIdRequirementOrNull());
/* 227 */     } catch (InvalidProtocolBufferException e) {
/*     */       
/* 229 */       throw new GeneralSecurityException("Parsing HmacKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 234 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 239 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 240 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 241 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 242 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\internal\PrfBasedKeyDerivationKeyProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */