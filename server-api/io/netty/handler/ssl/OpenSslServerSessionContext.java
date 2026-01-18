/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.internal.tcnative.SSL;
/*    */ import io.netty.internal.tcnative.SSLContext;
/*    */ import java.util.concurrent.locks.Lock;
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
/*    */ public final class OpenSslServerSessionContext
/*    */   extends OpenSslSessionContext
/*    */ {
/*    */   OpenSslServerSessionContext(ReferenceCountedOpenSslContext context, OpenSslKeyMaterialProvider provider) {
/* 29 */     super(context, provider, SSL.SSL_SESS_CACHE_SERVER, new OpenSslSessionCache(context.engines));
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
/*    */   public boolean setSessionIdContext(byte[] sidCtx) {
/* 42 */     Lock writerLock = this.context.ctxLock.writeLock();
/* 43 */     writerLock.lock();
/*    */     try {
/* 45 */       return SSLContext.setSessionIdContext(this.context.ctx, sidCtx);
/*    */     } finally {
/* 47 */       writerLock.unlock();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslServerSessionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */