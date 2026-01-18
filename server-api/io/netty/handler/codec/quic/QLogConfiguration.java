/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ public final class QLogConfiguration
/*    */ {
/*    */   private final String path;
/*    */   private final String logTitle;
/*    */   private final String logDescription;
/*    */   
/*    */   public QLogConfiguration(String path, String logTitle, String logDescription) {
/* 39 */     this.path = Objects.<String>requireNonNull(path, "path");
/* 40 */     this.logTitle = Objects.<String>requireNonNull(logTitle, "logTitle");
/* 41 */     this.logDescription = Objects.<String>requireNonNull(logDescription, "logDescription");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String path() {
/* 50 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String logTitle() {
/* 59 */     return this.logTitle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String logDescription() {
/* 68 */     return this.logDescription;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QLogConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */