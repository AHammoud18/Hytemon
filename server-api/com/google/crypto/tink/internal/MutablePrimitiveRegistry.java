/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MutablePrimitiveRegistry
/*    */ {
/* 34 */   private static MutablePrimitiveRegistry globalInstance = new MutablePrimitiveRegistry();
/*    */ 
/*    */   
/*    */   public static MutablePrimitiveRegistry globalInstance() {
/* 38 */     return globalInstance;
/*    */   }
/*    */   
/*    */   public static void resetGlobalInstanceTestOnly() {
/* 42 */     globalInstance = new MutablePrimitiveRegistry();
/*    */   }
/*    */   
/* 45 */   private final AtomicReference<PrimitiveRegistry> registry = new AtomicReference<>(
/* 46 */       PrimitiveRegistry.builder().build());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized <KeyT extends com.google.crypto.tink.Key, PrimitiveT> void registerPrimitiveConstructor(PrimitiveConstructor<KeyT, PrimitiveT> constructor) throws GeneralSecurityException {
/* 63 */     PrimitiveRegistry newRegistry = PrimitiveRegistry.builder(this.registry.get()).<KeyT, PrimitiveT>registerPrimitiveConstructor(constructor).build();
/* 64 */     this.registry.set(newRegistry);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized <InputPrimitiveT, WrapperPrimitiveT> void registerPrimitiveWrapper(PrimitiveWrapper<InputPrimitiveT, WrapperPrimitiveT> wrapper) throws GeneralSecurityException {
/* 71 */     PrimitiveRegistry newRegistry = PrimitiveRegistry.builder(this.registry.get()).<InputPrimitiveT, WrapperPrimitiveT>registerPrimitiveWrapper(wrapper).build();
/* 72 */     this.registry.set(newRegistry);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <KeyT extends com.google.crypto.tink.Key, PrimitiveT> PrimitiveT getPrimitive(KeyT key, Class<PrimitiveT> primitiveClass) throws GeneralSecurityException {
/* 85 */     return ((PrimitiveRegistry)this.registry.get()).getPrimitive(key, primitiveClass);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <WrapperPrimitiveT> WrapperPrimitiveT wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, Class<WrapperPrimitiveT> wrapperClassObject) throws GeneralSecurityException {
/* 93 */     return ((PrimitiveRegistry)this.registry.get()).wrap(keysetHandle, annotations, wrapperClassObject);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MutablePrimitiveRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */