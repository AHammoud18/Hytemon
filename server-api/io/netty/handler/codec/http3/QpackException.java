/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.util.internal.ThrowableUtil;
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
/*    */ public final class QpackException
/*    */   extends Exception
/*    */ {
/*    */   private QpackException(String message, @Nullable Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
/* 28 */     super(message, cause, enableSuppression, writableStackTrace);
/*    */   }
/*    */   
/*    */   static QpackException newStatic(Class<?> clazz, String method, String message) {
/* 32 */     return (QpackException)ThrowableUtil.unknownStackTrace(new QpackException(message, null, false, false), clazz, method);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */