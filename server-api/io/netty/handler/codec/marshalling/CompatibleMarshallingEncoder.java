/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import org.jboss.marshalling.Marshaller;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class CompatibleMarshallingEncoder
/*    */   extends MessageToByteEncoder<Object>
/*    */ {
/*    */   private final MarshallerProvider provider;
/*    */   
/*    */   public CompatibleMarshallingEncoder(MarshallerProvider provider) {
/* 47 */     super(Object.class);
/* 48 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
/* 53 */     Marshaller marshaller = this.provider.getMarshaller(ctx);
/* 54 */     marshaller.start(new ChannelBufferByteOutput(out));
/* 55 */     marshaller.writeObject(msg);
/* 56 */     marshaller.finish();
/* 57 */     marshaller.close();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\marshalling\CompatibleMarshallingEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */