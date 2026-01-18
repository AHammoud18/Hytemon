/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ public final class Http3Exception
/*    */   extends Exception
/*    */ {
/*    */   private final Http3ErrorCode errorCode;
/*    */   
/*    */   public Http3Exception(Http3ErrorCode errorCode, @Nullable String message) {
/* 34 */     super(message);
/* 35 */     this.errorCode = (Http3ErrorCode)ObjectUtil.checkNotNull(errorCode, "errorCode");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Http3Exception(Http3ErrorCode errorCode, String message, @Nullable Throwable cause) {
/* 46 */     super(message, cause);
/* 47 */     this.errorCode = (Http3ErrorCode)ObjectUtil.checkNotNull(errorCode, "errorCode");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Http3ErrorCode errorCode() {
/* 56 */     return this.errorCode;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3Exception.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */