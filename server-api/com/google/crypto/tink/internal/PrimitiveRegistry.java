/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrimitiveRegistry
/*     */ {
/*     */   private final Map<PrimitiveConstructorIndex, PrimitiveConstructor<?, ?>> primitiveConstructorMap;
/*     */   private final Map<Class<?>, PrimitiveWrapper<?, ?>> primitiveWrapperMap;
/*     */   private final boolean reparseLegacyKeys;
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private final Map<PrimitiveRegistry.PrimitiveConstructorIndex, PrimitiveConstructor<?, ?>> primitiveConstructorMap;
/*     */     private final Map<Class<?>, PrimitiveWrapper<?, ?>> primitiveWrapperMap;
/*     */     private boolean reparseLegacyKeys = false;
/*     */     
/*     */     private Builder() {
/*  45 */       this.primitiveConstructorMap = new HashMap<>();
/*  46 */       this.primitiveWrapperMap = new HashMap<>();
/*     */     }
/*     */     
/*     */     private Builder(PrimitiveRegistry registry) {
/*  50 */       this.primitiveConstructorMap = new HashMap<>(registry.primitiveConstructorMap);
/*  51 */       this.primitiveWrapperMap = new HashMap<>(registry.primitiveWrapperMap);
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
/*     */     public <KeyT extends Key, PrimitiveT> Builder registerPrimitiveConstructor(PrimitiveConstructor<KeyT, PrimitiveT> primitiveConstructor) throws GeneralSecurityException {
/*  66 */       if (primitiveConstructor == null) {
/*  67 */         throw new NullPointerException("primitive constructor must be non-null");
/*     */       }
/*     */ 
/*     */       
/*  71 */       PrimitiveRegistry.PrimitiveConstructorIndex index = new PrimitiveRegistry.PrimitiveConstructorIndex(primitiveConstructor.getKeyClass(), primitiveConstructor.getPrimitiveClass());
/*  72 */       if (this.primitiveConstructorMap.containsKey(index)) {
/*  73 */         PrimitiveConstructor<?, ?> existingConstructor = this.primitiveConstructorMap.get(index);
/*  74 */         if (!existingConstructor.equals(primitiveConstructor) || 
/*  75 */           !primitiveConstructor.equals(existingConstructor)) {
/*  76 */           throw new GeneralSecurityException("Attempt to register non-equal PrimitiveConstructor object for already existing object of type: " + index);
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/*  82 */         this.primitiveConstructorMap.put(index, primitiveConstructor);
/*     */       } 
/*  84 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public <InputPrimitiveT, WrapperPrimitiveT> Builder registerPrimitiveWrapper(PrimitiveWrapper<InputPrimitiveT, WrapperPrimitiveT> wrapper) throws GeneralSecurityException {
/*  91 */       if (wrapper == null) {
/*  92 */         throw new NullPointerException("wrapper must be non-null");
/*     */       }
/*  94 */       Class<WrapperPrimitiveT> wrapperClassObject = wrapper.getPrimitiveClass();
/*  95 */       if (this.primitiveWrapperMap.containsKey(wrapperClassObject)) {
/*     */         
/*  97 */         PrimitiveWrapper<?, ?> existingPrimitiveWrapper = this.primitiveWrapperMap.get(wrapperClassObject);
/*  98 */         if (!existingPrimitiveWrapper.equals(wrapper) || 
/*  99 */           !wrapper.equals(existingPrimitiveWrapper)) {
/* 100 */           throw new GeneralSecurityException("Attempt to register non-equal PrimitiveWrapper object or input class object for already existing object of type" + wrapperClassObject);
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 106 */         this.primitiveWrapperMap.put(wrapperClassObject, wrapper);
/*     */       } 
/* 108 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder allowReparsingLegacyKeys() {
/* 113 */       this.reparseLegacyKeys = true;
/* 114 */       return this;
/*     */     }
/*     */     
/*     */     public PrimitiveRegistry build() {
/* 118 */       return new PrimitiveRegistry(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 123 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static Builder builder(PrimitiveRegistry registry) {
/* 127 */     return new Builder(registry);
/*     */   }
/*     */   
/*     */   private PrimitiveRegistry(Builder builder) {
/* 131 */     this.primitiveConstructorMap = new HashMap<>(builder.primitiveConstructorMap);
/* 132 */     this.primitiveWrapperMap = new HashMap<>(builder.primitiveWrapperMap);
/* 133 */     this.reparseLegacyKeys = builder.reparseLegacyKeys;
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
/*     */   public <KeyT extends Key, PrimitiveT> PrimitiveT getPrimitive(KeyT key, Class<PrimitiveT> primitiveClass) throws GeneralSecurityException {
/* 145 */     if (this.reparseLegacyKeys && key instanceof LegacyProtoKey) {
/*     */ 
/*     */       
/* 148 */       Key reparsedKey = MutableSerializationRegistry.globalInstance().parseKey(((LegacyProtoKey)key)
/* 149 */           .getSerialization(InsecureSecretKeyAccess.get()), 
/* 150 */           InsecureSecretKeyAccess.get());
/* 151 */       return getPrimitiveWithoutReparsing(reparsedKey, primitiveClass);
/*     */     } 
/* 153 */     return getPrimitiveWithoutReparsing(key, primitiveClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private <KeyT extends Key, PrimitiveT> PrimitiveT getPrimitiveWithoutReparsing(KeyT key, Class<PrimitiveT> primitiveClass) throws GeneralSecurityException {
/* 158 */     PrimitiveConstructorIndex index = new PrimitiveConstructorIndex(key.getClass(), primitiveClass);
/* 159 */     if (!this.primitiveConstructorMap.containsKey(index)) {
/* 160 */       throw new GeneralSecurityException("No PrimitiveConstructor for " + index + " available, see https://developers.google.com/tink/faq/registration_errors");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     PrimitiveConstructor<KeyT, PrimitiveT> primitiveConstructor = (PrimitiveConstructor<KeyT, PrimitiveT>)this.primitiveConstructorMap.get(index);
/* 168 */     return primitiveConstructor.constructPrimitive(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <InnerPrimitiveT, WrappedPrimitiveT> WrappedPrimitiveT wrapWithPrimitiveWrapper(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper<InnerPrimitiveT, WrappedPrimitiveT> wrapper) throws GeneralSecurityException {
/* 176 */     return wrapper.wrap(keysetHandle, annotations, entry -> getPrimitive(entry.getKey(), wrapper.getInputPrimitiveClass()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <WrappedPrimitiveT> WrappedPrimitiveT wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, Class<WrappedPrimitiveT> wrapperClassObject) throws GeneralSecurityException {
/* 187 */     if (!this.primitiveWrapperMap.containsKey(wrapperClassObject)) {
/* 188 */       throw new GeneralSecurityException("No wrapper found for " + wrapperClassObject);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 193 */     PrimitiveWrapper<?, WrappedPrimitiveT> wrapper = (PrimitiveWrapper<?, WrappedPrimitiveT>)this.primitiveWrapperMap.get(wrapperClassObject);
/* 194 */     return wrapWithPrimitiveWrapper(keysetHandle, annotations, wrapper);
/*     */   }
/*     */   
/*     */   private static final class PrimitiveConstructorIndex {
/*     */     private final Class<?> keyClass;
/*     */     private final Class<?> primitiveClass;
/*     */     
/*     */     private PrimitiveConstructorIndex(Class<?> keyClass, Class<?> primitiveClass) {
/* 202 */       this.keyClass = keyClass;
/* 203 */       this.primitiveClass = primitiveClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 208 */       if (!(o instanceof PrimitiveConstructorIndex)) {
/* 209 */         return false;
/*     */       }
/* 211 */       PrimitiveConstructorIndex other = (PrimitiveConstructorIndex)o;
/* 212 */       return (other.keyClass.equals(this.keyClass) && other.primitiveClass.equals(this.primitiveClass));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 217 */       return Objects.hash(new Object[] { this.keyClass, this.primitiveClass });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 222 */       return this.keyClass.getSimpleName() + " with primitive type: " + this.primitiveClass.getSimpleName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\PrimitiveRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */