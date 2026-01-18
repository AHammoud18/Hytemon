/*    */ package com.hypixel.hytale.builtin.hytalegenerator;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class LoggerUtil
/*    */ {
/*    */   public static final String HYTALE_GENERATOR_NAME = "HytaleGenerator";
/*    */   
/*    */   public static Logger getLogger() {
/* 12 */     return Logger.getLogger("HytaleGenerator");
/*    */   }
/*    */   
/*    */   public static void logException(@Nonnull String contextDescription, @Nonnull Throwable e) {
/* 16 */     logException(contextDescription, e, getLogger());
/*    */   }
/*    */   
/*    */   public static void logException(@Nonnull String contextDescription, @Nonnull Throwable e, @Nonnull Logger logger) {
/* 20 */     String msg = "Exception occurred during ";
/* 21 */     msg = msg + msg;
/* 22 */     msg = msg + " \n";
/* 23 */     msg = msg + msg;
/* 24 */     logger.severe(msg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\LoggerUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */