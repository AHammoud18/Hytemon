/*     */ package com.google.crypto.tink.keyderivation.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Serialization;
/*     */ import com.google.crypto.tink.keyderivation.PrfBasedKeyDerivationKey;
/*     */ import com.google.crypto.tink.keyderivation.PrfBasedKeyDerivationParameters;
/*     */ import com.google.crypto.tink.prf.PrfKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.PrfBasedDeriverKey;
/*     */ import com.google.crypto.tink.proto.PrfBasedDeriverKeyFormat;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.MessageLite;
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
/*     */ public final class PrfBasedDeriverKeyManager
/*     */   implements KeyManager<Void>
/*     */ {
/*  77 */   private static final PrimitiveConstructor<PrfBasedKeyDerivationKey, KeyDeriver> PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(PrfBasedKeyDeriver::create, PrfBasedKeyDerivationKey.class, KeyDeriver.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static final PrfBasedKeyDerivationKey createNewKey(PrfBasedKeyDerivationParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  86 */     Key prfKey = MutableKeyCreationRegistry.globalInstance().createKey((Parameters)parameters.getPrfParameters(), null);
/*  87 */     if (!(prfKey instanceof PrfKey)) {
/*  88 */       throw new GeneralSecurityException("Failed to create PrfKey from parameters" + parameters
/*     */           
/*  90 */           .getPrfParameters() + ", instead got " + prfKey
/*     */           
/*  92 */           .getClass());
/*     */     }
/*  94 */     return PrfBasedKeyDerivationKey.create(parameters, (PrfKey)prfKey, idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  99 */   private static final KeyCreator<PrfBasedKeyDerivationParameters> KEY_CREATOR = PrfBasedDeriverKeyManager::createNewKey;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey";
/*     */ 
/*     */ 
/*     */   
/*     */   public Void getPrimitive(ByteString serializedKey) throws GeneralSecurityException {
/* 108 */     throw new GeneralSecurityException("Cannot use the KeyManager to get a primitive for KeyDerivation");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Void getPrimitive(MessageLite key) throws GeneralSecurityException {
/* 114 */     throw new GeneralSecurityException("Cannot use the KeyManager to get a primitive for KeyDerivation");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MessageLite newKey(ByteString serializedKeyFormat) throws GeneralSecurityException {
/* 121 */     KeyData keyData = newKeyData(serializedKeyFormat);
/*     */     try {
/* 123 */       return (MessageLite)PrfBasedDeriverKey.parseFrom(keyData
/* 124 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 125 */     } catch (InvalidProtocolBufferException e) {
/* 126 */       throw new GeneralSecurityException("Unexpectedly failed to parse key");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final MessageLite newKey(MessageLite keyFormat) throws GeneralSecurityException {
/* 132 */     return newKey(keyFormat.toByteString());
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean doesSupport(String typeUrl) {
/* 137 */     return typeUrl.equals(getKeyType());
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getKeyType() {
/* 142 */     return "type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVersion() {
/* 147 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType getOutputPrefixTypeFromSerializedKeyFormat(ByteString serializedKeyFormat) throws GeneralSecurityException {
/*     */     try {
/* 154 */       PrfBasedDeriverKeyFormat format = PrfBasedDeriverKeyFormat.parseFrom(serializedKeyFormat, 
/* 155 */           ExtensionRegistryLite.getEmptyRegistry());
/* 156 */       return format.getParams().getDerivedKeyTemplate().getOutputPrefixType();
/* 157 */     } catch (InvalidProtocolBufferException e) {
/* 158 */       throw new GeneralSecurityException("Unexpectedly failed to parse key format", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final KeyData newKeyData(ByteString serializedKeyFormat) throws GeneralSecurityException {
/* 165 */     OutputPrefixType outputPrefixType = getOutputPrefixTypeFromSerializedKeyFormat(serializedKeyFormat);
/*     */     
/* 167 */     ProtoParametersSerialization parametersSerialization = ProtoParametersSerialization.checkedCreate(
/* 168 */         KeyTemplate.newBuilder()
/* 169 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.PrfBasedDeriverKey")
/* 170 */         .setValue(serializedKeyFormat)
/* 171 */         .setOutputPrefixType(outputPrefixType)
/* 172 */         .build());
/*     */     
/* 174 */     Parameters parameters = MutableSerializationRegistry.globalInstance().parseParameters((Serialization)parametersSerialization);
/* 175 */     Integer idRequirement = null;
/* 176 */     if (!outputPrefixType.equals(OutputPrefixType.RAW))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       idRequirement = Integer.valueOf(123);
/*     */     }
/* 186 */     Key key = MutableKeyCreationRegistry.globalInstance().createKey(parameters, idRequirement);
/*     */ 
/*     */     
/* 189 */     ProtoKeySerialization keySerialization = (ProtoKeySerialization)MutableSerializationRegistry.globalInstance().serializeKey(key, ProtoKeySerialization.class, InsecureSecretKeyAccess.get());
/* 190 */     return KeyData.newBuilder()
/* 191 */       .setTypeUrl(keySerialization.getTypeUrl())
/* 192 */       .setValue(keySerialization.getValue())
/* 193 */       .setKeyMaterialType(keySerialization.getKeyMaterialType())
/* 194 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Class<Void> getPrimitiveClass() {
/* 199 */     return Void.class;
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 203 */     KeyManagerRegistry.globalInstance()
/* 204 */       .registerKeyManager(new PrfBasedDeriverKeyManager(), newKeyAllowed);
/* 205 */     MutableKeyCreationRegistry.globalInstance()
/* 206 */       .add(KEY_CREATOR, PrfBasedKeyDerivationParameters.class);
/* 207 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(PRIMITIVE_CONSTRUCTOR);
/*     */     
/* 209 */     PrfBasedKeyDerivationKeyProtoSerialization.register();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\internal\PrfBasedDeriverKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */