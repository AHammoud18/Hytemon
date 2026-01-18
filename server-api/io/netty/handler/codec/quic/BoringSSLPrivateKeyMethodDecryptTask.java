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
/*    */ final class BoringSSLPrivateKeyMethodDecryptTask
/*    */   extends BoringSSLPrivateKeyMethodTask
/*    */ {
/*    */   private final byte[] input;
/*    */   
/*    */   BoringSSLPrivateKeyMethodDecryptTask(long ssl, byte[] input, BoringSSLPrivateKeyMethod method) {
/* 24 */     super(ssl, method);
/*    */     
/* 26 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void runMethod(long ssl, BoringSSLPrivateKeyMethod method, BiConsumer<byte[], Throwable> consumer) {
/* 31 */     method.decrypt(ssl, this.input, consumer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLPrivateKeyMethodDecryptTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */