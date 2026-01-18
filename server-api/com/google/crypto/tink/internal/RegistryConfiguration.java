/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.errorprone.annotations.DoNotCall;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ public final class RegistryConfiguration
/*    */   extends InternalConfiguration
/*    */ {
/*    */   public static RegistryConfiguration get() {
/* 32 */     return CONFIG;
/*    */   }
/*    */   
/* 35 */   private static final RegistryConfiguration CONFIG = new RegistryConfiguration();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <P> P wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, Class<P> clazz) throws GeneralSecurityException {
/* 43 */     return MutablePrimitiveRegistry.globalInstance().wrap(keysetHandle, annotations, clazz);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @DoNotCall
/*    */   public static InternalConfiguration createFromPrimitiveRegistry(PrimitiveRegistry registry) {
/* 55 */     throw new UnsupportedOperationException("Cannot create RegistryConfiguration from a PrimitiveRegistry");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\RegistryConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */