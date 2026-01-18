/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public abstract class KeyParser<SerializationT extends Serialization>
/*     */ {
/*     */   private final Bytes objectIdentifier;
/*     */   private final Class<SerializationT> serializationClass;
/*     */   
/*     */   private KeyParser(Bytes objectIdentifier, Class<SerializationT> serializationClass) {
/*  47 */     this.objectIdentifier = objectIdentifier;
/*  48 */     this.serializationClass = serializationClass;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Bytes getObjectIdentifier() {
/*  70 */     return this.objectIdentifier;
/*     */   }
/*     */   
/*     */   public final Class<SerializationT> getSerializationClass() {
/*  74 */     return this.serializationClass;
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
/*     */   public static <SerializationT extends Serialization> KeyParser<SerializationT> create(final KeyParsingFunction<SerializationT> function, Bytes objectIdentifier, Class<SerializationT> serializationClass) {
/* 109 */     return new KeyParser<SerializationT>(objectIdentifier, serializationClass)
/*     */       {
/*     */         public Key parseKey(SerializationT serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException
/*     */         {
/* 113 */           return function.parseKey(serialization, access);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public abstract Key parseKey(SerializationT paramSerializationT, @Nullable SecretKeyAccess paramSecretKeyAccess) throws GeneralSecurityException;
/*     */   
/*     */   public static interface KeyParsingFunction<SerializationT extends Serialization> {
/*     */     Key parseKey(SerializationT param1SerializationT, @Nullable SecretKeyAccess param1SecretKeyAccess) throws GeneralSecurityException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\KeyParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */