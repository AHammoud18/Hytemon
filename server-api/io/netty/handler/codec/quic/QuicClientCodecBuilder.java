/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.function.Function;
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
/*    */ public final class QuicClientCodecBuilder
/*    */   extends QuicCodecBuilder<QuicClientCodecBuilder>
/*    */ {
/*    */   public QuicClientCodecBuilder() {
/* 33 */     super(false);
/*    */   }
/*    */   
/*    */   private QuicClientCodecBuilder(QuicCodecBuilder<QuicClientCodecBuilder> builder) {
/* 37 */     super(builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public QuicClientCodecBuilder clone() {
/* 42 */     return new QuicClientCodecBuilder(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ChannelHandler build(QuicheConfig config, Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider, Executor sslTaskExecutor, int localConnIdLength, FlushStrategy flushStrategy) {
/* 50 */     return (ChannelHandler)new QuicheQuicClientCodec(config, sslEngineProvider, sslTaskExecutor, localConnIdLength, flushStrategy);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicClientCodecBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */