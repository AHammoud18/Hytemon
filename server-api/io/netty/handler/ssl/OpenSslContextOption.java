/*     */ package io.netty.handler.ssl;
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
/*     */ public final class OpenSslContextOption<T>
/*     */   extends SslContextOption<T>
/*     */ {
/*     */   private OpenSslContextOption(String name) {
/*  26 */     super(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   public static final OpenSslContextOption<Boolean> USE_TASKS = new OpenSslContextOption("USE_TASKS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static final OpenSslContextOption<Boolean> TLS_FALSE_START = new OpenSslContextOption("TLS_FALSE_START");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final OpenSslContextOption<OpenSslPrivateKeyMethod> PRIVATE_KEY_METHOD = new OpenSslContextOption("PRIVATE_KEY_METHOD");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final OpenSslContextOption<OpenSslAsyncPrivateKeyMethod> ASYNC_PRIVATE_KEY_METHOD = new OpenSslContextOption("ASYNC_PRIVATE_KEY_METHOD");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final OpenSslContextOption<OpenSslCertificateCompressionConfig> CERTIFICATE_COMPRESSION_ALGORITHMS = new OpenSslContextOption("CERTIFICATE_COMPRESSION_ALGORITHMS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final OpenSslContextOption<Integer> MAX_CERTIFICATE_LIST_BYTES = new OpenSslContextOption("MAX_CERTIFICATE_LIST_BYTES");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final OpenSslContextOption<String[]> GROUPS = new OpenSslContextOption("GROUPS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final OpenSslContextOption<Integer> TMP_DH_KEYLENGTH = new OpenSslContextOption("TMP_DH_KEYLENGTH");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final OpenSslContextOption<Boolean> USE_JDK_PROVIDER_SIGNATURES = new OpenSslContextOption("USE_JDK_PROVIDER_SIGNATURES");
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslContextOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */