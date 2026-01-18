/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.concurrent.Future;
/*    */ import javax.net.ssl.SSLEngine;
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
/*    */ public interface BoringSSLAsyncPrivateKeyMethod
/*    */ {
/* 23 */   public static final int SSL_SIGN_RSA_PKCS1_SHA1 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA1;
/* 24 */   public static final int SSL_SIGN_RSA_PKCS1_SHA256 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA256;
/* 25 */   public static final int SSL_SIGN_RSA_PKCS1_SHA384 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA384;
/* 26 */   public static final int SSL_SIGN_RSA_PKCS1_SHA512 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA512;
/* 27 */   public static final int SSL_SIGN_ECDSA_SHA1 = BoringSSLPrivateKeyMethod.SSL_SIGN_ECDSA_SHA1;
/* 28 */   public static final int SSL_SIGN_ECDSA_SECP256R1_SHA256 = BoringSSLPrivateKeyMethod.SSL_SIGN_ECDSA_SECP256R1_SHA256;
/* 29 */   public static final int SSL_SIGN_ECDSA_SECP384R1_SHA384 = BoringSSLPrivateKeyMethod.SSL_SIGN_ECDSA_SECP384R1_SHA384;
/* 30 */   public static final int SSL_SIGN_ECDSA_SECP521R1_SHA512 = BoringSSLPrivateKeyMethod.SSL_SIGN_ECDSA_SECP521R1_SHA512;
/* 31 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA256 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA256;
/* 32 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA384 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA384;
/* 33 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA512 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA512;
/* 34 */   public static final int SSL_SIGN_ED25519 = BoringSSLPrivateKeyMethod.SSL_SIGN_ED25519;
/* 35 */   public static final int SSL_SIGN_RSA_PKCS1_MD5_SHA1 = BoringSSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_MD5_SHA1;
/*    */   
/*    */   Future<byte[]> sign(SSLEngine paramSSLEngine, int paramInt, byte[] paramArrayOfbyte);
/*    */   
/*    */   Future<byte[]> decrypt(SSLEngine paramSSLEngine, byte[] paramArrayOfbyte);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLAsyncPrivateKeyMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */