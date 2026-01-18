/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.internal.tcnative.SSLPrivateKeyMethod;
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
/*    */ public interface OpenSslAsyncPrivateKeyMethod
/*    */ {
/* 24 */   public static final int SSL_SIGN_RSA_PKCS1_SHA1 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA1;
/* 25 */   public static final int SSL_SIGN_RSA_PKCS1_SHA256 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA256;
/* 26 */   public static final int SSL_SIGN_RSA_PKCS1_SHA384 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA384;
/* 27 */   public static final int SSL_SIGN_RSA_PKCS1_SHA512 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA512;
/* 28 */   public static final int SSL_SIGN_ECDSA_SHA1 = SSLPrivateKeyMethod.SSL_SIGN_ECDSA_SHA1;
/* 29 */   public static final int SSL_SIGN_ECDSA_SECP256R1_SHA256 = SSLPrivateKeyMethod.SSL_SIGN_ECDSA_SECP256R1_SHA256;
/* 30 */   public static final int SSL_SIGN_ECDSA_SECP384R1_SHA384 = SSLPrivateKeyMethod.SSL_SIGN_ECDSA_SECP384R1_SHA384;
/* 31 */   public static final int SSL_SIGN_ECDSA_SECP521R1_SHA512 = SSLPrivateKeyMethod.SSL_SIGN_ECDSA_SECP521R1_SHA512;
/* 32 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA256 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA256;
/* 33 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA384 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA384;
/* 34 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA512 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA512;
/* 35 */   public static final int SSL_SIGN_ED25519 = SSLPrivateKeyMethod.SSL_SIGN_ED25519;
/* 36 */   public static final int SSL_SIGN_RSA_PKCS1_MD5_SHA1 = SSLPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_MD5_SHA1;
/*    */   
/*    */   Future<byte[]> sign(SSLEngine paramSSLEngine, int paramInt, byte[] paramArrayOfbyte);
/*    */   
/*    */   Future<byte[]> decrypt(SSLEngine paramSSLEngine, byte[] paramArrayOfbyte);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslAsyncPrivateKeyMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */