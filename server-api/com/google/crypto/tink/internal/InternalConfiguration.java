/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InternalConfiguration
/*    */   extends Configuration
/*    */ {
/*    */   public abstract <P> P wrap(KeysetHandleInterface paramKeysetHandleInterface, MonitoringAnnotations paramMonitoringAnnotations, Class<P> paramClass) throws GeneralSecurityException;
/*    */   
/*    */   public static InternalConfiguration createFromPrimitiveRegistry(PrimitiveRegistry registry) {
/* 39 */     return new InternalConfigurationImpl(registry);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static class InternalConfigurationImpl
/*    */     extends InternalConfiguration
/*    */   {
/*    */     private final PrimitiveRegistry registry;
/*    */ 
/*    */ 
/*    */     
/*    */     private InternalConfigurationImpl(PrimitiveRegistry registry) {
/* 52 */       this.registry = registry;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public <P> P wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, Class<P> clazz) throws GeneralSecurityException {
/* 59 */       return this.registry.wrap(keysetHandle, annotations, clazz);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\InternalConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */