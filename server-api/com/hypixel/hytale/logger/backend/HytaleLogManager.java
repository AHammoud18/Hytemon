/*    */ package com.hypixel.hytale.logger.backend;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogManager;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.logging.Logger;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class HytaleLogManager extends LogManager {
/*    */   public static HytaleLogManager instance;
/*    */   
/*    */   public HytaleLogManager() {
/* 13 */     instance = this;
/* 14 */     getLogger("Hytale");
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {}
/*    */   
/*    */   private void reset0() {
/* 21 */     super.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Logger getLogger(@Nonnull String name) {
/* 27 */     Logger logger = super.getLogger(name);
/* 28 */     return (logger != null) ? logger : new HytaleJdkLogger(HytaleLoggerBackend.getLogger(name));
/*    */   }
/*    */   
/*    */   public static void resetFinally() {
/* 32 */     HytaleConsole.INSTANCE.shutdown();
/* 33 */     HytaleFileHandler.INSTANCE.shutdown();
/* 34 */     if (instance != null) instance.reset0(); 
/*    */   }
/*    */   
/*    */   private static class HytaleJdkLogger extends Logger {
/*    */     @Nonnull
/*    */     private final HytaleLoggerBackend backend;
/*    */     
/*    */     public HytaleJdkLogger(@Nonnull HytaleLoggerBackend backend) {
/* 42 */       super(backend.getLoggerName(), null);
/* 43 */       this.backend = backend;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getName() {
/* 48 */       return this.backend.getLoggerName();
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Level getLevel() {
/* 54 */       return this.backend.getLevel();
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isLoggable(@Nonnull Level level) {
/* 59 */       return this.backend.isLoggable(level);
/*    */     }
/*    */ 
/*    */     
/*    */     public void log(@Nonnull LogRecord record) {
/* 64 */       this.backend.log(record);
/*    */     }
/*    */ 
/*    */     
/*    */     public void setLevel(@Nonnull Level newLevel) {
/* 69 */       this.backend.setLevel(newLevel);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\backend\HytaleLogManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */