/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.concurrent.atomic.AtomicReference;
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
/*     */ public final class MutableSerializationRegistry
/*     */ {
/*     */   private static MutableSerializationRegistry createGlobalInstance() throws GeneralSecurityException {
/*  41 */     MutableSerializationRegistry registry = new MutableSerializationRegistry();
/*  42 */     registry.registerKeySerializer(
/*  43 */         KeySerializer.create(LegacyProtoKey::getSerialization, (Class)LegacyProtoKey.class, (Class)ProtoKeySerialization.class));
/*     */     
/*  45 */     return registry;
/*     */   }
/*     */ 
/*     */   
/*  49 */   private static final MutableSerializationRegistry GLOBAL_INSTANCE = TinkBugException.<MutableSerializationRegistry>exceptionIsBug(MutableSerializationRegistry::createGlobalInstance);
/*     */   
/*     */   public static MutableSerializationRegistry globalInstance() {
/*  52 */     return GLOBAL_INSTANCE;
/*     */   }
/*     */   
/*  55 */   private final AtomicReference<SerializationRegistry> registry = new AtomicReference<>((new SerializationRegistry.Builder())
/*  56 */       .build());
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
/*     */   public synchronized <KeyT extends Key, SerializationT extends Serialization> void registerKeySerializer(KeySerializer<KeyT, SerializationT> serializer) throws GeneralSecurityException {
/*  72 */     SerializationRegistry newRegistry = (new SerializationRegistry.Builder(this.registry.get())).<KeyT, SerializationT>registerKeySerializer(serializer).build();
/*  73 */     this.registry.set(newRegistry);
/*     */   }
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
/*     */   public synchronized <SerializationT extends Serialization> void registerKeyParser(KeyParser<SerializationT> parser) throws GeneralSecurityException {
/*  87 */     SerializationRegistry newRegistry = (new SerializationRegistry.Builder(this.registry.get())).<SerializationT>registerKeyParser(parser).build();
/*  88 */     this.registry.set(newRegistry);
/*     */   }
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
/*     */   public synchronized <ParametersT extends Parameters, SerializationT extends Serialization> void registerParametersSerializer(ParametersSerializer<ParametersT, SerializationT> serializer) throws GeneralSecurityException {
/* 106 */     SerializationRegistry newRegistry = (new SerializationRegistry.Builder(this.registry.get())).<ParametersT, SerializationT>registerParametersSerializer(serializer).build();
/* 107 */     this.registry.set(newRegistry);
/*     */   }
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
/*     */   public synchronized <SerializationT extends Serialization> void registerParametersParser(ParametersParser<SerializationT> parser) throws GeneralSecurityException {
/* 121 */     SerializationRegistry newRegistry = (new SerializationRegistry.Builder(this.registry.get())).<SerializationT>registerParametersParser(parser).build();
/* 122 */     this.registry.set(newRegistry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <SerializationT extends Serialization> boolean hasParserForKey(SerializationT serializedKey) {
/* 128 */     return ((SerializationRegistry)this.registry.get()).hasParserForKey(serializedKey);
/*     */   }
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
/*     */   public <SerializationT extends Serialization> Key parseKey(SerializationT serializedKey, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 141 */     return ((SerializationRegistry)this.registry.get()).parseKey(serializedKey, access);
/*     */   }
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
/*     */   public Key parseKeyWithLegacyFallback(ProtoKeySerialization protoKeySerialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 157 */     if (!hasParserForKey(protoKeySerialization)) {
/* 158 */       return new LegacyProtoKey(protoKeySerialization, access);
/*     */     }
/* 160 */     return parseKey(protoKeySerialization, access);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyT extends Key, SerializationT extends Serialization> boolean hasSerializerForKey(KeyT key, Class<SerializationT> serializationClass) {
/* 167 */     return ((SerializationRegistry)this.registry.get()).hasSerializerForKey(key, serializationClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyT extends Key, SerializationT extends Serialization> SerializationT serializeKey(KeyT key, Class<SerializationT> serializationClass, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 179 */     return ((SerializationRegistry)this.registry.get()).serializeKey(key, serializationClass, access);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <SerializationT extends Serialization> boolean hasParserForParameters(SerializationT serializedParameters) {
/* 185 */     return ((SerializationRegistry)this.registry.get()).hasParserForParameters(serializedParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <SerializationT extends Serialization> Parameters parseParameters(SerializationT serializedParameters) throws GeneralSecurityException {
/* 197 */     return ((SerializationRegistry)this.registry.get()).parseParameters(serializedParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Parameters parseParametersWithLegacyFallback(ProtoParametersSerialization protoParametersSerialization) throws GeneralSecurityException {
/* 209 */     if (!hasParserForParameters(protoParametersSerialization)) {
/* 210 */       return new LegacyProtoParameters(protoParametersSerialization);
/*     */     }
/* 212 */     return parseParameters(protoParametersSerialization);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <ParametersT extends Parameters, SerializationT extends Serialization> boolean hasSerializerForParameters(ParametersT parameters, Class<SerializationT> serializationClass) {
/* 220 */     return ((SerializationRegistry)this.registry.get()).hasSerializerForParameters(parameters, serializationClass);
/*     */   }
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
/*     */   public <ParametersT extends Parameters, SerializationT extends Serialization> SerializationT serializeParameters(ParametersT parameters, Class<SerializationT> serializationClass) throws GeneralSecurityException {
/* 233 */     return ((SerializationRegistry)this.registry.get()).serializeParameters(parameters, serializationClass);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MutableSerializationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */