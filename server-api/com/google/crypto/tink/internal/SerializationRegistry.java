/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ public final class SerializationRegistry
/*     */ {
/*     */   private final Map<SerializerIndex, KeySerializer<?, ?>> keySerializerMap;
/*     */   private final Map<ParserIndex, KeyParser<?>> keyParserMap;
/*     */   private final Map<SerializerIndex, ParametersSerializer<?, ?>> parametersSerializerMap;
/*     */   private final Map<ParserIndex, ParametersParser<?>> parametersParserMap;
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private final Map<SerializationRegistry.SerializerIndex, KeySerializer<?, ?>> keySerializerMap;
/*     */     private final Map<SerializationRegistry.ParserIndex, KeyParser<?>> keyParserMap;
/*     */     private final Map<SerializationRegistry.SerializerIndex, ParametersSerializer<?, ?>> parametersSerializerMap;
/*     */     private final Map<SerializationRegistry.ParserIndex, ParametersParser<?>> parametersParserMap;
/*     */     
/*     */     public Builder() {
/*  48 */       this.keySerializerMap = new HashMap<>();
/*  49 */       this.keyParserMap = new HashMap<>();
/*  50 */       this.parametersSerializerMap = new HashMap<>();
/*  51 */       this.parametersParserMap = new HashMap<>();
/*     */     }
/*     */     
/*     */     public Builder(SerializationRegistry registry) {
/*  55 */       this.keySerializerMap = new HashMap<>(registry.keySerializerMap);
/*  56 */       this.keyParserMap = new HashMap<>(registry.keyParserMap);
/*  57 */       this.parametersSerializerMap = new HashMap<>(registry.parametersSerializerMap);
/*  58 */       this.parametersParserMap = new HashMap<>(registry.parametersParserMap);
/*     */     }
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
/*     */     @CanIgnoreReturnValue
/*     */     public <KeyT extends Key, SerializationT extends Serialization> Builder registerKeySerializer(KeySerializer<KeyT, SerializationT> serializer) throws GeneralSecurityException {
/*  73 */       SerializationRegistry.SerializerIndex index = new SerializationRegistry.SerializerIndex(serializer.getKeyClass(), serializer.getSerializationClass());
/*  74 */       if (this.keySerializerMap.containsKey(index)) {
/*  75 */         KeySerializer<?, ?> existingSerializer = this.keySerializerMap.get(index);
/*  76 */         if (!existingSerializer.equals(serializer) || !serializer.equals(existingSerializer)) {
/*  77 */           throw new GeneralSecurityException("Attempt to register non-equal serializer for already existing object of type: " + index);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/*  82 */         this.keySerializerMap.put(index, serializer);
/*     */       } 
/*  84 */       return this;
/*     */     }
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
/*     */     @CanIgnoreReturnValue
/*     */     public <SerializationT extends Serialization> Builder registerKeyParser(KeyParser<SerializationT> parser) throws GeneralSecurityException {
/*  99 */       SerializationRegistry.ParserIndex index = new SerializationRegistry.ParserIndex(parser.getSerializationClass(), parser.getObjectIdentifier());
/* 100 */       if (this.keyParserMap.containsKey(index)) {
/* 101 */         KeyParser<?> existingParser = this.keyParserMap.get(index);
/* 102 */         if (!existingParser.equals(parser) || !parser.equals(existingParser)) {
/* 103 */           throw new GeneralSecurityException("Attempt to register non-equal parser for already existing object of type: " + index);
/*     */         }
/*     */       } else {
/*     */         
/* 107 */         this.keyParserMap.put(index, parser);
/*     */       } 
/* 109 */       return this;
/*     */     }
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
/*     */     @CanIgnoreReturnValue
/*     */     public <ParametersT extends Parameters, SerializationT extends Serialization> Builder registerParametersSerializer(ParametersSerializer<ParametersT, SerializationT> serializer) throws GeneralSecurityException {
/* 126 */       SerializationRegistry.SerializerIndex index = new SerializationRegistry.SerializerIndex(serializer.getParametersClass(), serializer.getSerializationClass());
/* 127 */       if (this.parametersSerializerMap.containsKey(index)) {
/* 128 */         ParametersSerializer<?, ?> existingSerializer = this.parametersSerializerMap.get(index);
/* 129 */         if (!existingSerializer.equals(serializer) || !serializer.equals(existingSerializer)) {
/* 130 */           throw new GeneralSecurityException("Attempt to register non-equal serializer for already existing object of type: " + index);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 135 */         this.parametersSerializerMap.put(index, serializer);
/*     */       } 
/* 137 */       return this;
/*     */     }
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
/*     */     @CanIgnoreReturnValue
/*     */     public <SerializationT extends Serialization> Builder registerParametersParser(ParametersParser<SerializationT> parser) throws GeneralSecurityException {
/* 152 */       SerializationRegistry.ParserIndex index = new SerializationRegistry.ParserIndex(parser.getSerializationClass(), parser.getObjectIdentifier());
/* 153 */       if (this.parametersParserMap.containsKey(index)) {
/* 154 */         ParametersParser<?> existingParser = this.parametersParserMap.get(index);
/* 155 */         if (!existingParser.equals(parser) || !parser.equals(existingParser)) {
/* 156 */           throw new GeneralSecurityException("Attempt to register non-equal parser for already existing object of type: " + index);
/*     */         }
/*     */       } else {
/*     */         
/* 160 */         this.parametersParserMap.put(index, parser);
/*     */       } 
/* 162 */       return this;
/*     */     }
/*     */     
/*     */     public SerializationRegistry build() {
/* 166 */       return new SerializationRegistry(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private SerializationRegistry(Builder builder) {
/* 171 */     this.keySerializerMap = new HashMap<>(builder.keySerializerMap);
/* 172 */     this.keyParserMap = new HashMap<>(builder.keyParserMap);
/* 173 */     this.parametersSerializerMap = new HashMap<>(builder.parametersSerializerMap);
/* 174 */     this.parametersParserMap = new HashMap<>(builder.parametersParserMap);
/*     */   }
/*     */   
/*     */   private static class SerializerIndex
/*     */   {
/*     */     private final Class<?> keyClass;
/*     */     private final Class<? extends Serialization> keySerializationClass;
/*     */     
/*     */     private SerializerIndex(Class<?> keyClass, Class<? extends Serialization> keySerializationClass) {
/* 183 */       this.keyClass = keyClass;
/* 184 */       this.keySerializationClass = keySerializationClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 189 */       if (!(o instanceof SerializerIndex)) {
/* 190 */         return false;
/*     */       }
/* 192 */       SerializerIndex other = (SerializerIndex)o;
/* 193 */       return (other.keyClass.equals(this.keyClass) && other.keySerializationClass
/* 194 */         .equals(this.keySerializationClass));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 199 */       return Objects.hash(new Object[] { this.keyClass, this.keySerializationClass });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 204 */       return this.keyClass.getSimpleName() + " with serialization type: " + this.keySerializationClass
/*     */         
/* 206 */         .getSimpleName();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ParserIndex
/*     */   {
/*     */     private final Class<? extends Serialization> keySerializationClass;
/*     */     private final Bytes serializationIdentifier;
/*     */     
/*     */     private ParserIndex(Class<? extends Serialization> keySerializationClass, Bytes serializationIdentifier) {
/* 216 */       this.keySerializationClass = keySerializationClass;
/* 217 */       this.serializationIdentifier = serializationIdentifier;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 222 */       if (!(o instanceof ParserIndex)) {
/* 223 */         return false;
/*     */       }
/* 225 */       ParserIndex other = (ParserIndex)o;
/* 226 */       return (other.keySerializationClass.equals(this.keySerializationClass) && other.serializationIdentifier
/* 227 */         .equals(this.serializationIdentifier));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 232 */       return Objects.hash(new Object[] { this.keySerializationClass, this.serializationIdentifier });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 237 */       return this.keySerializationClass.getSimpleName() + ", object identifier: " + this.serializationIdentifier;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <SerializationT extends Serialization> boolean hasParserForKey(SerializationT serializedKey) {
/* 247 */     ParserIndex index = new ParserIndex(serializedKey.getClass(), serializedKey.getObjectIdentifier());
/* 248 */     return this.keyParserMap.containsKey(index);
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
/*     */   public <SerializationT extends Serialization> Key parseKey(SerializationT serializedKey, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 262 */     ParserIndex index = new ParserIndex(serializedKey.getClass(), serializedKey.getObjectIdentifier());
/*     */     
/* 264 */     if (!this.keyParserMap.containsKey(index)) {
/* 265 */       throw new GeneralSecurityException("No Key Parser for requested key type " + index + " available");
/*     */     }
/*     */ 
/*     */     
/* 269 */     KeyParser<SerializationT> parser = (KeyParser<SerializationT>)this.keyParserMap.get(index);
/* 270 */     return parser.parseKey(serializedKey, access);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyT extends Key, SerializationT extends Serialization> boolean hasSerializerForKey(KeyT key, Class<SerializationT> serializationClass) {
/* 276 */     SerializerIndex index = new SerializerIndex(key.getClass(), serializationClass);
/* 277 */     return this.keySerializerMap.containsKey(index);
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
/* 289 */     SerializerIndex index = new SerializerIndex(key.getClass(), serializationClass);
/* 290 */     if (!this.keySerializerMap.containsKey(index)) {
/* 291 */       throw new GeneralSecurityException("No Key serializer for " + index + " available");
/*     */     }
/*     */ 
/*     */     
/* 295 */     KeySerializer<KeyT, SerializationT> serializer = (KeySerializer<KeyT, SerializationT>)this.keySerializerMap.get(index);
/* 296 */     return serializer.serializeKey(key, access);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <SerializationT extends Serialization> boolean hasParserForParameters(SerializationT serializedParameters) {
/* 304 */     ParserIndex index = new ParserIndex(serializedParameters.getClass(), serializedParameters.getObjectIdentifier());
/* 305 */     return this.parametersParserMap.containsKey(index);
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
/*     */   public <SerializationT extends Serialization> Parameters parseParameters(SerializationT serializedParameters) throws GeneralSecurityException {
/* 319 */     ParserIndex index = new ParserIndex(serializedParameters.getClass(), serializedParameters.getObjectIdentifier());
/*     */     
/* 321 */     if (!this.parametersParserMap.containsKey(index)) {
/* 322 */       throw new GeneralSecurityException("No Parameters Parser for requested key type " + index + " available");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 327 */     ParametersParser<SerializationT> parser = (ParametersParser<SerializationT>)this.parametersParserMap.get(index);
/* 328 */     return parser.parseParameters(serializedParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <ParametersT extends Parameters, SerializationT extends Serialization> boolean hasSerializerForParameters(ParametersT parameters, Class<SerializationT> serializationClass) {
/* 335 */     SerializerIndex index = new SerializerIndex(parameters.getClass(), serializationClass);
/* 336 */     return this.parametersSerializerMap.containsKey(index);
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
/* 349 */     SerializerIndex index = new SerializerIndex(parameters.getClass(), serializationClass);
/* 350 */     if (!this.parametersSerializerMap.containsKey(index)) {
/* 351 */       throw new GeneralSecurityException("No Key Format serializer for " + index + " available");
/*     */     }
/*     */ 
/*     */     
/* 355 */     ParametersSerializer<ParametersT, SerializationT> serializer = (ParametersSerializer<ParametersT, SerializationT>)this.parametersSerializerMap.get(index);
/* 356 */     return serializer.serializeParameters(parameters);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\SerializationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */