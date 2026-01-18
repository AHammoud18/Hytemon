/*    */ package com.google.crypto.tink.internal;
/*    */ 
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
/*    */ public final class MutableMonitoringRegistry
/*    */ {
/* 25 */   private static final MutableMonitoringRegistry GLOBAL_INSTANCE = new MutableMonitoringRegistry();
/*    */ 
/*    */   
/*    */   public static MutableMonitoringRegistry globalInstance() {
/* 29 */     return GLOBAL_INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   private static class DoNothingClient
/*    */     implements MonitoringClient
/*    */   {
/*    */     private DoNothingClient() {}
/*    */     
/*    */     public MonitoringClient.Logger createLogger(KeysetHandleInterface keysetInfo, MonitoringAnnotations annotations, String primitive, String api) {
/* 39 */       return MonitoringUtil.DO_NOTHING_LOGGER;
/*    */     } }
/*    */   
/* 42 */   private static final DoNothingClient DO_NOTHING_CLIENT = new DoNothingClient();
/*    */   
/* 44 */   private final AtomicReference<MonitoringClient> monitoringClient = new AtomicReference<>();
/*    */   
/*    */   public synchronized void clear() {
/* 47 */     this.monitoringClient.set(null);
/*    */   }
/*    */   
/*    */   public synchronized void registerMonitoringClient(MonitoringClient client) {
/* 51 */     if (this.monitoringClient.get() != null) {
/* 52 */       throw new IllegalStateException("a monitoring client has already been registered");
/*    */     }
/* 54 */     this.monitoringClient.set(client);
/*    */   }
/*    */   
/*    */   public MonitoringClient getMonitoringClient() {
/* 58 */     MonitoringClient client = this.monitoringClient.get();
/* 59 */     if (client == null) {
/* 60 */       return DO_NOTHING_CLIENT;
/*    */     }
/* 62 */     return client;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MutableMonitoringRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */