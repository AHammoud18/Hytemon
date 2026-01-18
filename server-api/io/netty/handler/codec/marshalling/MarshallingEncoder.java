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
/*    */ @Sharable
/*    */ public class MarshallingEncoder
/*    */   extends MessageToByteEncoder<Object>
/*    */ {
/* 40 */   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
/*    */ 
/*    */ 
/*    */   
/*    */   private final MarshallerProvider provider;
/*    */ 
/*    */ 
/*    */   
/*    */   public MarshallingEncoder(MarshallerProvider provider) {
/* 49 */     super(Object.class);
/* 50 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
/* 55 */     Marshaller marshaller = this.provider.getMarshaller(ctx);
/* 56 */     int lengthPos = out.writerIndex();
/* 57 */     out.writeBytes(LENGTH_PLACEHOLDER);
/* 58 */     ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
/* 59 */     marshaller.start(output);
/* 60 */     marshaller.writeObject(msg);
/* 61 */     marshaller.finish();
/* 62 */     marshaller.close();
/*    */     
/* 64 */     out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\marshalling\MarshallingEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */