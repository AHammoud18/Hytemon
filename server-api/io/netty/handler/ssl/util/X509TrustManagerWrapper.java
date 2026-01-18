/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.net.Socket;
/*    */ import java.security.cert.CertificateException;
/*    */ import java.security.cert.X509Certificate;
/*    */ import javax.net.ssl.SSLEngine;
/*    */ import javax.net.ssl.X509ExtendedTrustManager;
/*    */ import javax.net.ssl.X509TrustManager;
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
/*    */ final class X509TrustManagerWrapper
/*    */   extends X509ExtendedTrustManager
/*    */ {
/*    */   private final X509TrustManager delegate;
/*    */   
/*    */   X509TrustManagerWrapper(X509TrustManager delegate) {
/* 32 */     this.delegate = (X509TrustManager)ObjectUtil.checkNotNull(delegate, "delegate");
/*    */   }
/*    */ 
/*    */   
/*    */   public void checkClientTrusted(X509Certificate[] chain, String s) throws CertificateException {
/* 37 */     this.delegate.checkClientTrusted(chain, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkClientTrusted(X509Certificate[] chain, String s, Socket socket) throws CertificateException {
/* 43 */     this.delegate.checkClientTrusted(chain, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkClientTrusted(X509Certificate[] chain, String s, SSLEngine sslEngine) throws CertificateException {
/* 49 */     this.delegate.checkClientTrusted(chain, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public void checkServerTrusted(X509Certificate[] chain, String s) throws CertificateException {
/* 54 */     this.delegate.checkServerTrusted(chain, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkServerTrusted(X509Certificate[] chain, String s, Socket socket) throws CertificateException {
/* 60 */     this.delegate.checkServerTrusted(chain, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkServerTrusted(X509Certificate[] chain, String s, SSLEngine sslEngine) throws CertificateException {
/* 66 */     this.delegate.checkServerTrusted(chain, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public X509Certificate[] getAcceptedIssuers() {
/* 71 */     return this.delegate.getAcceptedIssuers();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\X509TrustManagerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */