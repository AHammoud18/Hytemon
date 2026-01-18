/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public abstract class ParametersParser<SerializationT extends Serialization>
/*     */ {
/*     */   private final Bytes objectIdentifier;
/*     */   private final Class<SerializationT> serializationClass;
/*     */   
/*     */   private ParametersParser(Bytes objectIdentifier, Class<SerializationT> serializationClass) {
/*  44 */     this.objectIdentifier = objectIdentifier;
/*  45 */     this.serializationClass = serializationClass;
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
/*     */   public final Bytes getObjectIdentifier() {
/*  68 */     return this.objectIdentifier;
/*     */   }
/*     */   
/*     */   public final Class<SerializationT> getSerializationClass() {
/*  72 */     return this.serializationClass;
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
/*     */   public static <SerializationT extends Serialization> ParametersParser<SerializationT> create(final ParametersParsingFunction<SerializationT> function, Bytes objectIdentifier, Class<SerializationT> serializationClass) {
/* 104 */     return new ParametersParser<SerializationT>(objectIdentifier, serializationClass)
/*     */       {
/*     */         public Parameters parseParameters(SerializationT serialization) throws GeneralSecurityException
/*     */         {
/* 108 */           return function.parseParameters(serialization);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public abstract Parameters parseParameters(SerializationT paramSerializationT) throws GeneralSecurityException;
/*     */   
/*     */   public static interface ParametersParsingFunction<SerializationT extends Serialization> {
/*     */     Parameters parseParameters(SerializationT param1SerializationT) throws GeneralSecurityException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\ParametersParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */