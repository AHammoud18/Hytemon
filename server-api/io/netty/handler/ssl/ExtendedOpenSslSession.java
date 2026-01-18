/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.ExtendedSSLSession;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSessionBindingEvent;
/*     */ import javax.net.ssl.SSLSessionBindingListener;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ExtendedOpenSslSession
/*     */   extends ExtendedSSLSession
/*     */   implements OpenSslInternalSession
/*     */ {
/*  42 */   private static final String[] LOCAL_SUPPORTED_SIGNATURE_ALGORITHMS = new String[] { "SHA512withRSA", "SHA512withECDSA", "SHA384withRSA", "SHA384withECDSA", "SHA256withRSA", "SHA256withECDSA", "SHA224withRSA", "SHA224withECDSA", "SHA1withRSA", "SHA1withECDSA", "RSASSA-PSS" };
/*     */ 
/*     */ 
/*     */   
/*     */   private final OpenSslInternalSession wrapped;
/*     */ 
/*     */ 
/*     */   
/*     */   ExtendedOpenSslSession(OpenSslInternalSession wrapped) {
/*  51 */     this.wrapped = wrapped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<byte[]> getStatusResponses() {
/*  61 */     return (List)Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareHandshake() {
/*  66 */     this.wrapped.prepareHandshake();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> keyValueStorage() {
/*  71 */     return this.wrapped.keyValueStorage();
/*     */   }
/*     */ 
/*     */   
/*     */   public OpenSslSessionId sessionId() {
/*  76 */     return this.wrapped.sessionId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSessionDetails(long creationTime, long lastAccessedTime, OpenSslSessionId id, Map<String, Object> keyValueStorage) {
/*  82 */     this.wrapped.setSessionDetails(creationTime, lastAccessedTime, id, keyValueStorage);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setLocalCertificate(Certificate[] localCertificate) {
/*  87 */     this.wrapped.setLocalCertificate(localCertificate);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getPeerSupportedSignatureAlgorithms() {
/*  92 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void tryExpandApplicationBufferSize(int packetLengthDataOnly) {
/*  97 */     this.wrapped.tryExpandApplicationBufferSize(packetLengthDataOnly);
/*     */   }
/*     */ 
/*     */   
/*     */   public final String[] getLocalSupportedSignatureAlgorithms() {
/* 102 */     return (String[])LOCAL_SUPPORTED_SIGNATURE_ALGORITHMS.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte[] getId() {
/* 107 */     return this.wrapped.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OpenSslSessionContext getSessionContext() {
/* 112 */     return this.wrapped.getSessionContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getCreationTime() {
/* 117 */     return this.wrapped.getCreationTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getLastAccessedTime() {
/* 122 */     return this.wrapped.getLastAccessedTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastAccessedTime(long time) {
/* 127 */     this.wrapped.setLastAccessedTime(time);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void invalidate() {
/* 132 */     this.wrapped.invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isValid() {
/* 137 */     return this.wrapped.isValid();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putValue(String name, Object value) {
/* 142 */     if (value instanceof SSLSessionBindingListener)
/*     */     {
/* 144 */       value = new SSLSessionBindingListenerDecorator((SSLSessionBindingListener)value);
/*     */     }
/* 146 */     this.wrapped.putValue(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object getValue(String s) {
/* 151 */     Object value = this.wrapped.getValue(s);
/* 152 */     if (value instanceof SSLSessionBindingListenerDecorator)
/*     */     {
/* 154 */       return ((SSLSessionBindingListenerDecorator)value).delegate;
/*     */     }
/* 156 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void removeValue(String s) {
/* 161 */     this.wrapped.removeValue(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final String[] getValueNames() {
/* 166 */     return this.wrapped.getValueNames();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
/* 171 */     return this.wrapped.getPeerCertificates();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPeerCertificates() {
/* 176 */     return this.wrapped.hasPeerCertificates();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Certificate[] getLocalCertificates() {
/* 181 */     return this.wrapped.getLocalCertificates();
/*     */   }
/*     */ 
/*     */   
/*     */   public final X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
/* 186 */     return this.wrapped.getPeerCertificateChain();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/* 191 */     return this.wrapped.getPeerPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Principal getLocalPrincipal() {
/* 196 */     return this.wrapped.getLocalPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getCipherSuite() {
/* 201 */     return this.wrapped.getCipherSuite();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 206 */     return this.wrapped.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getPeerHost() {
/* 211 */     return this.wrapped.getPeerHost();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getPeerPort() {
/* 216 */     return this.wrapped.getPeerPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getPacketBufferSize() {
/* 221 */     return this.wrapped.getPacketBufferSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getApplicationBufferSize() {
/* 226 */     return this.wrapped.getApplicationBufferSize();
/*     */   }
/*     */   
/*     */   private final class SSLSessionBindingListenerDecorator
/*     */     implements SSLSessionBindingListener {
/*     */     final SSLSessionBindingListener delegate;
/*     */     
/*     */     SSLSessionBindingListenerDecorator(SSLSessionBindingListener delegate) {
/* 234 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public void valueBound(SSLSessionBindingEvent event) {
/* 239 */       this.delegate.valueBound(new SSLSessionBindingEvent(ExtendedOpenSslSession.this, event.getName()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void valueUnbound(SSLSessionBindingEvent event) {
/* 244 */       this.delegate.valueUnbound(new SSLSessionBindingEvent(ExtendedOpenSslSession.this, event.getName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handshakeFinished(byte[] id, String cipher, String protocol, byte[] peerCertificate, byte[][] peerCertificateChain, long creationTime, long timeout) throws SSLException {
/* 251 */     this.wrapped.handshakeFinished(id, cipher, protocol, peerCertificate, peerCertificateChain, creationTime, timeout);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 256 */     return this.wrapped.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 261 */     return this.wrapped.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 266 */     return "ExtendedOpenSslSession{wrapped=" + this.wrapped + '}';
/*     */   }
/*     */   
/*     */   public abstract List<SNIServerName> getRequestedServerNames();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ExtendedOpenSslSession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */