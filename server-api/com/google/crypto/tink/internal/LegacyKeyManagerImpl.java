/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.PrivateKey;
/*     */ import com.google.crypto.tink.PrivateKeyManager;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import com.google.protobuf.Parser;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ public class LegacyKeyManagerImpl<P>
/*     */   implements KeyManager<P>
/*     */ {
/*     */   final String typeUrl;
/*     */   final Class<P> primitiveClass;
/*     */   final KeyData.KeyMaterialType keyMaterialType;
/*     */   final Parser<? extends MessageLite> protobufKeyParser;
/*     */   
/*     */   public static <P> KeyManager<P> create(String typeUrl, Class<P> primitiveClass, KeyData.KeyMaterialType keyMaterialType, Parser<? extends MessageLite> protobufKeyParser) {
/*  62 */     return new LegacyKeyManagerImpl<>(typeUrl, primitiveClass, keyMaterialType, protobufKeyParser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LegacyKeyManagerImpl(String typeUrl, Class<P> primitiveClass, KeyData.KeyMaterialType keyMaterialType, Parser<? extends MessageLite> protobufKeyParser) {
/*  70 */     this.protobufKeyParser = protobufKeyParser;
/*  71 */     this.typeUrl = typeUrl;
/*  72 */     this.primitiveClass = primitiveClass;
/*  73 */     this.keyMaterialType = keyMaterialType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public P getPrimitive(ByteString serializedKey) throws GeneralSecurityException {
/*  79 */     ProtoKeySerialization serialization = ProtoKeySerialization.create(this.typeUrl, serializedKey, this.keyMaterialType, OutputPrefixType.RAW, null);
/*     */ 
/*     */ 
/*     */     
/*  83 */     Key key = MutableSerializationRegistry.globalInstance().parseKey(serialization, InsecureSecretKeyAccess.get());
/*  84 */     return MutablePrimitiveRegistry.globalInstance().getPrimitive(key, this.primitiveClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public final P getPrimitive(MessageLite key) throws GeneralSecurityException {
/*  89 */     return getPrimitive(key.toByteString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final MessageLite newKey(ByteString serializedKeyFormat) throws GeneralSecurityException {
/*  95 */     KeyData keyData = newKeyData(serializedKeyFormat);
/*     */     try {
/*  97 */       return (MessageLite)this.protobufKeyParser.parseFrom(keyData
/*  98 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/*  99 */     } catch (InvalidProtocolBufferException e) {
/* 100 */       throw new GeneralSecurityException("Unexpectedly failed to parse key");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final MessageLite newKey(MessageLite keyFormat) throws GeneralSecurityException {
/* 106 */     return newKey(keyFormat.toByteString());
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean doesSupport(String typeUrl) {
/* 111 */     return typeUrl.equals(getKeyType());
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getKeyType() {
/* 116 */     return this.typeUrl;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVersion() {
/* 121 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final KeyData newKeyData(ByteString serializedKeyFormat) throws GeneralSecurityException {
/* 127 */     ProtoParametersSerialization parametersSerialization = ProtoParametersSerialization.checkedCreate(
/* 128 */         KeyTemplate.newBuilder()
/* 129 */         .setTypeUrl(this.typeUrl)
/* 130 */         .setValue(serializedKeyFormat)
/* 131 */         .setOutputPrefixType(OutputPrefixType.RAW)
/* 132 */         .build());
/*     */     
/* 134 */     Parameters parameters = MutableSerializationRegistry.globalInstance().parseParameters(parametersSerialization);
/*     */ 
/*     */     
/* 137 */     Key key = MutableKeyCreationRegistry.globalInstance().createKey(parameters, null);
/*     */ 
/*     */     
/* 140 */     ProtoKeySerialization keySerialization = MutableSerializationRegistry.globalInstance().<Key, ProtoKeySerialization>serializeKey(key, ProtoKeySerialization.class, InsecureSecretKeyAccess.get());
/* 141 */     return KeyData.newBuilder()
/* 142 */       .setTypeUrl(keySerialization.getTypeUrl())
/* 143 */       .setValue(keySerialization.getValue())
/* 144 */       .setKeyMaterialType(keySerialization.getKeyMaterialType())
/* 145 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Class<P> getPrimitiveClass() {
/* 150 */     return this.primitiveClass;
/*     */   }
/*     */   
/*     */   private static class LegacyPrivateKeyManagerImpl<P>
/*     */     extends LegacyKeyManagerImpl<P>
/*     */     implements PrivateKeyManager<P> {
/*     */     protected LegacyPrivateKeyManagerImpl(String typeUrl, Class<P> primitiveClass, Parser<? extends MessageLite> protobufKeyParser) {
/* 157 */       super(typeUrl, primitiveClass, KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, protobufKeyParser);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyData getPublicKeyData(ByteString serializedKey) throws GeneralSecurityException {
/* 163 */       ProtoKeySerialization serialization = ProtoKeySerialization.create(this.typeUrl, serializedKey, this.keyMaterialType, OutputPrefixType.RAW, null);
/*     */ 
/*     */ 
/*     */       
/* 167 */       Key key = MutableSerializationRegistry.globalInstance().parseKey(serialization, InsecureSecretKeyAccess.get());
/* 168 */       if (!(key instanceof PrivateKey)) {
/* 169 */         throw new GeneralSecurityException("Key not private key");
/*     */       }
/* 171 */       Key publicKey = ((PrivateKey)key).getPublicKey();
/*     */ 
/*     */       
/* 174 */       ProtoKeySerialization publicKeySerialization = MutableSerializationRegistry.globalInstance().<Key, ProtoKeySerialization>serializeKey(publicKey, ProtoKeySerialization.class, InsecureSecretKeyAccess.get());
/* 175 */       return KeyData.newBuilder()
/* 176 */         .setTypeUrl(publicKeySerialization.getTypeUrl())
/* 177 */         .setValue(publicKeySerialization.getValue())
/* 178 */         .setKeyMaterialType(publicKeySerialization.getKeyMaterialType())
/* 179 */         .build();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static <P> PrivateKeyManager<P> createPrivateKeyManager(String typeUrl, Class<P> primitiveClass, Parser<? extends MessageLite> protobufKeyParser) {
/* 185 */     return new LegacyPrivateKeyManagerImpl<>(typeUrl, primitiveClass, protobufKeyParser);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\LegacyKeyManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */