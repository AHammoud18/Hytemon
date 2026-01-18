/*    */ package io.netty.handler.codec.bytes;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ public class ByteArrayDecoder
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   public ByteArrayDecoder() {
/* 54 */     super(ByteBuf.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/* 60 */     out.add(ByteBufUtil.getBytes(msg));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\bytes\ByteArrayDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */