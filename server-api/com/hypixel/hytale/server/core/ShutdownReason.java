/*    */ package com.hypixel.hytale.server.core;
/*    */ 
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class ShutdownReason
/*    */ {
/* 16 */   public static final ShutdownReason SIGINT = new ShutdownReason(130);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public static final ShutdownReason SHUTDOWN = new ShutdownReason(0);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   public static final ShutdownReason CRASH = new ShutdownReason(1);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public static final ShutdownReason AUTH_FAILED = new ShutdownReason(2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public static final ShutdownReason WORLD_GEN = new ShutdownReason(3);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public static final ShutdownReason CLIENT_GONE = new ShutdownReason(4);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public static final ShutdownReason MISSING_REQUIRED_PLUGIN = new ShutdownReason(5);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public static final ShutdownReason VALIDATE_ERROR = new ShutdownReason(6);
/*    */   
/*    */   private final int exitCode;
/*    */   private final String message;
/*    */   
/*    */   public ShutdownReason(int exitCode) {
/* 58 */     this(exitCode, null);
/*    */   }
/*    */   
/*    */   public ShutdownReason(int exitCode, String message) {
/* 62 */     this.exitCode = exitCode;
/* 63 */     this.message = message;
/*    */   }
/*    */   
/*    */   public int getExitCode() {
/* 67 */     return this.exitCode;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 71 */     return this.message;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ShutdownReason withMessage(String message) {
/* 76 */     return new ShutdownReason(this.exitCode, message);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 82 */     return "ShutdownReason{exitCode=" + this.exitCode + ", message='" + this.message + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\ShutdownReason.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */