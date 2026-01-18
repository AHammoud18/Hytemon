/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZstdIOException
/*    */   extends IOException
/*    */ {
/*    */   private long code;
/*    */   
/*    */   public ZstdIOException(long paramLong) {
/* 17 */     this(Zstd.getErrorCode(paramLong), Zstd.getErrorName(paramLong));
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
/*    */   public ZstdIOException(long paramLong, String paramString) {
/* 30 */     super(paramString);
/* 31 */     this.code = paramLong;
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
/*    */   
/*    */   public long getErrorCode() {
/* 45 */     return this.code;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdIOException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */