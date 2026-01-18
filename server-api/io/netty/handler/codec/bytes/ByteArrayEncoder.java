/*    */ package io.netty.handler.codec.bytes;
/*    */ 
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import java.util.List;
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
/*    */ public class ByteArrayEncoder
/*    */   extends MessageToMessageEncoder<byte[]>
/*    */ {
/*    */   public ByteArrayEncoder() {
/* 56 */     super(byte[].class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
/* 61 */     out.add(Unpooled.wrappedBuffer(msg));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\bytes\ByteArrayEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */