/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.buffer.ByteBufAllocator;
/*    */ import io.netty.handler.ssl.SslContext;
/*    */ import java.io.InputStream;
/*    */ import java.security.cert.CertificateException;
/*    */ import java.security.cert.X509Certificate;
/*    */ import javax.net.ssl.SSLEngine;
/*    */ import javax.net.ssl.SSLSessionContext;
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
/*    */ public abstract class QuicSslContext
/*    */   extends SslContext
/*    */ {
/*    */   static X509Certificate[] toX509Certificates0(InputStream stream) throws CertificateException {
/* 41 */     return SslContext.toX509Certificates(stream);
/*    */   }
/*    */   
/*    */   public abstract QuicSslSessionContext sessionContext();
/*    */   
/*    */   public abstract QuicSslEngine newEngine(ByteBufAllocator paramByteBufAllocator, String paramString, int paramInt);
/*    */   
/*    */   public abstract QuicSslEngine newEngine(ByteBufAllocator paramByteBufAllocator);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicSslContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */