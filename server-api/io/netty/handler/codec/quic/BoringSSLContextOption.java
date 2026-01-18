/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.handler.ssl.SslContextOption;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public final class BoringSSLContextOption<T>
/*    */   extends SslContextOption<T>
/*    */ {
/*    */   private BoringSSLContextOption(String name) {
/* 30 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public static final BoringSSLContextOption<String[]> GROUPS = new BoringSSLContextOption("GROUPS");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public static final BoringSSLContextOption<String[]> SIGNATURE_ALGORITHMS = new BoringSSLContextOption("SIGNATURE_ALGORITHMS");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public static final BoringSSLContextOption<Set<String>> CLIENT_KEY_TYPES = new BoringSSLContextOption("CLIENT_KEY_TYPES");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public static final BoringSSLContextOption<Map<String, String>> SERVER_KEY_TYPES = new BoringSSLContextOption("SERVER_KEY_TYPES");
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLContextOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */