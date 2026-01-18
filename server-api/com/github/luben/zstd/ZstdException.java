/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZstdException
/*    */   extends RuntimeException
/*    */ {
/*    */   private long code;
/*    */   
/*    */   public ZstdException(long paramLong) {
/* 15 */     this(Zstd.getErrorCode(paramLong), Zstd.getErrorName(paramLong));
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
/*    */   public ZstdException(long paramLong, String paramString) {
/* 28 */     super(paramString);
/* 29 */     this.code = paramLong;
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
/* 43 */     return this.code;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */