/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.EmptyArrays;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ final class BoringSSLSessionCallback
/*    */ {
/* 30 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(BoringSSLSessionCallback.class);
/*    */   private final QuicClientSessionCache sessionCache;
/*    */   private final QuicheQuicSslEngineMap engineMap;
/*    */   
/*    */   BoringSSLSessionCallback(QuicheQuicSslEngineMap engineMap, @Nullable QuicClientSessionCache sessionCache) {
/* 35 */     this.engineMap = engineMap;
/* 36 */     this.sessionCache = sessionCache;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void newSession(long ssl, long creationTime, long timeout, byte[] session, boolean isSingleUse, byte[] peerParams) {
/* 42 */     if (this.sessionCache == null) {
/*    */       return;
/*    */     }
/*    */     
/* 46 */     QuicheQuicSslEngine engine = this.engineMap.get(ssl);
/* 47 */     if (engine == null) {
/* 48 */       logger.warn("engine is null ssl: {}", Long.valueOf(ssl));
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     if (peerParams == null) {
/* 53 */       peerParams = EmptyArrays.EMPTY_BYTES;
/*    */     }
/* 55 */     if (logger.isDebugEnabled()) {
/* 56 */       logger.debug("ssl: {}, session: {}, peerParams: {}", new Object[] { Long.valueOf(ssl), Arrays.toString(session), 
/* 57 */             Arrays.toString(peerParams) });
/*    */     }
/* 59 */     byte[] quicSession = toQuicheQuicSession(session, peerParams);
/* 60 */     if (quicSession != null) {
/* 61 */       logger.debug("save session host={}, port={}", engine
/* 62 */           .getSession().getPeerHost(), Integer.valueOf(engine.getSession().getPeerPort()));
/* 63 */       this.sessionCache.saveSession(engine.getSession().getPeerHost(), engine.getSession().getPeerPort(), TimeUnit.SECONDS
/* 64 */           .toMillis(creationTime), TimeUnit.SECONDS.toMillis(timeout), quicSession, isSingleUse);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static byte[] toQuicheQuicSession(byte[] sslSession, byte[] peerParams) {
/* 71 */     if (sslSession != null && peerParams != null) {
/* 72 */       try { ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
/* 73 */         try { DataOutputStream dos = new DataOutputStream(bos); 
/* 74 */           try { dos.writeLong(sslSession.length);
/* 75 */             dos.write(sslSession);
/* 76 */             dos.writeLong(peerParams.length);
/* 77 */             dos.write(peerParams);
/* 78 */             byte[] arrayOfByte = bos.toByteArray();
/* 79 */             dos.close(); bos.close(); return arrayOfByte; } catch (Throwable throwable) { try { dos.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { bos.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 80 */       { return null; }
/*    */     
/*    */     }
/* 83 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLSessionCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */