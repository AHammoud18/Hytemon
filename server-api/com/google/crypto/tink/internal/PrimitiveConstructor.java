/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
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
/*     */ 
/*     */ 
/*     */ public abstract class PrimitiveConstructor<KeyT extends Key, PrimitiveT>
/*     */ {
/*     */   private final Class<KeyT> keyClass;
/*     */   private final Class<PrimitiveT> primitiveClass;
/*     */   
/*     */   private PrimitiveConstructor(Class<KeyT> keyClass, Class<PrimitiveT> primitiveClass) {
/*  48 */     this.keyClass = keyClass;
/*  49 */     this.primitiveClass = primitiveClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<KeyT> getKeyClass() {
/*  56 */     return this.keyClass;
/*     */   }
/*     */   
/*     */   public Class<PrimitiveT> getPrimitiveClass() {
/*  60 */     return this.primitiveClass;
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
/*     */ 
/*     */   
/*     */   public static <KeyT extends Key, PrimitiveT> PrimitiveConstructor<KeyT, PrimitiveT> create(final PrimitiveConstructionFunction<KeyT, PrimitiveT> function, Class<KeyT> keyClass, Class<PrimitiveT> primitiveClass) {
/*  97 */     return new PrimitiveConstructor<KeyT, PrimitiveT>(keyClass, primitiveClass)
/*     */       {
/*     */         public PrimitiveT constructPrimitive(KeyT key) throws GeneralSecurityException {
/* 100 */           return function.constructPrimitive(key);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public abstract PrimitiveT constructPrimitive(KeyT paramKeyT) throws GeneralSecurityException;
/*     */   
/*     */   public static interface PrimitiveConstructionFunction<KeyT extends Key, PrimitiveT> {
/*     */     PrimitiveT constructPrimitive(KeyT param1KeyT) throws GeneralSecurityException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\PrimitiveConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */