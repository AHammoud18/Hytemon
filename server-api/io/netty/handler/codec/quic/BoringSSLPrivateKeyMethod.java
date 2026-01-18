/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.util.function.BiConsumer;
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
/*    */ interface BoringSSLPrivateKeyMethod
/*    */ {
/* 24 */   public static final int SSL_SIGN_RSA_PKCS1_SHA1 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pkcs_sha1();
/* 25 */   public static final int SSL_SIGN_RSA_PKCS1_SHA256 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pkcs_sha256();
/* 26 */   public static final int SSL_SIGN_RSA_PKCS1_SHA384 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pkcs_sha384();
/* 27 */   public static final int SSL_SIGN_RSA_PKCS1_SHA512 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pkcs_sha512();
/* 28 */   public static final int SSL_SIGN_ECDSA_SHA1 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_ecdsa_pkcs_sha1();
/*    */   
/* 30 */   public static final int SSL_SIGN_ECDSA_SECP256R1_SHA256 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_ecdsa_secp256r1_sha256();
/*    */   
/* 32 */   public static final int SSL_SIGN_ECDSA_SECP384R1_SHA384 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_ecdsa_secp384r1_sha384();
/*    */   
/* 34 */   public static final int SSL_SIGN_ECDSA_SECP521R1_SHA512 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_ecdsa_secp521r1_sha512();
/* 35 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA256 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pss_rsae_sha256();
/* 36 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA384 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pss_rsae_sha384();
/* 37 */   public static final int SSL_SIGN_RSA_PSS_RSAE_SHA512 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pss_rsae_sha512();
/* 38 */   public static final int SSL_SIGN_ED25519 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_ed25519();
/* 39 */   public static final int SSL_SIGN_RSA_PKCS1_MD5_SHA1 = BoringSSLNativeStaticallyReferencedJniMethods.ssl_sign_rsa_pkcs1_md5_sha1();
/*    */   
/*    */   void sign(long paramLong, int paramInt, byte[] paramArrayOfbyte, BiConsumer<byte[], Throwable> paramBiConsumer);
/*    */   
/*    */   void decrypt(long paramLong, byte[] paramArrayOfbyte, BiConsumer<byte[], Throwable> paramBiConsumer);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLPrivateKeyMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */