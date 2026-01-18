/*    */ package io.sentry.vendor.gson.stream;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*    */ @Internal
/*    */ public final class MalformedJsonException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MalformedJsonException(String msg) {
/* 36 */     super(msg);
/*    */   }
/*    */   
/*    */   public MalformedJsonException(String msg, Throwable throwable) {
/* 40 */     super(msg);
/*    */ 
/*    */     
/* 43 */     initCause(throwable);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MalformedJsonException(Throwable throwable) {
/* 49 */     initCause(throwable);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\vendor\gson\stream\MalformedJsonException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */