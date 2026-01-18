/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.buffer.ByteBufAllocator;
/*    */ import java.util.List;
/*    */ import javax.net.ssl.SNIServerName;
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
/*    */ public final class OpenSslEngine
/*    */   extends ReferenceCountedOpenSslEngine
/*    */ {
/*    */   OpenSslEngine(OpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode, String endpointIdentificationAlgorithm, List<SNIServerName> serverNames) {
/* 35 */     super(context, alloc, peerHost, peerPort, jdkCompatibilityMode, false, endpointIdentificationAlgorithm, serverNames);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 42 */     super.finalize();
/* 43 */     OpenSsl.releaseIfNeeded(this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */