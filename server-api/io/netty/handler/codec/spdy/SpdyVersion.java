/*    */ package io.netty.handler.codec.spdy;
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
/*    */ public enum SpdyVersion
/*    */ {
/* 19 */   SPDY_3_1(3, 1);
/*    */   
/*    */   private final int minorVersion;
/*    */   private final int version;
/*    */   
/*    */   SpdyVersion(int version, int minorVersion) {
/* 25 */     this.version = version;
/* 26 */     this.minorVersion = minorVersion;
/*    */   }
/*    */   
/*    */   public int version() {
/* 30 */     return this.version;
/*    */   }
/*    */   
/*    */   public int minorVersion() {
/* 34 */     return this.minorVersion;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */