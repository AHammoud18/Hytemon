/*    */ package com.hypixel.hytale;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.logger.backend.HytaleFileHandler;
/*    */ import com.hypixel.hytale.logger.backend.HytaleLoggerBackend;
/*    */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*    */ import com.hypixel.hytale.server.core.Options;
/*    */ import io.sentry.Sentry;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class LateMain {
/*    */   public static void lateMain(String[] args) {
/*    */     try {
/* 17 */       if (Options.parse(args))
/*    */         return; 
/* 19 */       HytaleLogger.init();
/*    */ 
/*    */       
/* 22 */       HytaleFileHandler.INSTANCE.enable();
/*    */ 
/*    */       
/* 25 */       HytaleLogger.replaceStd();
/*    */       
/* 27 */       HytaleLoggerBackend.LOG_LEVEL_LOADER = (name -> {
/*    */           for (Map.Entry<String, Level> e : (Iterable<Map.Entry<String, Level>>)Options.getOptionSet().valuesOf(Options.LOG_LEVELS)) {
/*    */             if (name.equals(e.getKey())) {
/*    */               return e.getValue();
/*    */             }
/*    */           } 
/*    */           
/*    */           HytaleServer hytaleServer = HytaleServer.get();
/*    */           
/*    */           if (hytaleServer != null) {
/*    */             HytaleServerConfig config = hytaleServer.getConfig();
/*    */             
/*    */             if (config != null) {
/*    */               Level configLevel = (Level)config.getLogLevels().get(name);
/*    */               
/*    */               if (configLevel != null) {
/*    */                 return configLevel;
/*    */               }
/*    */             } 
/*    */           } 
/*    */           
/*    */           return Options.getOptionSet().has(Options.SHUTDOWN_AFTER_VALIDATE) ? Level.WARNING : null;
/*    */         });
/* 50 */       if (Options.getOptionSet().has(Options.SHUTDOWN_AFTER_VALIDATE)) {
/* 51 */         HytaleLoggerBackend.reloadLogLevels();
/*    */       }
/*    */       
/* 54 */       new HytaleServer();
/* 55 */     } catch (Throwable t) {
/* 56 */       if (!SkipSentryException.hasSkipSentry(t)) Sentry.captureException(t); 
/* 57 */       t.printStackTrace();
/* 58 */       throw new RuntimeException("Failed to create HytaleServer", t);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\LateMain.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */