/*    */ package io.netty.handler.codec;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public class FixedLengthFrameDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   private final int frameLength;
/*    */   
/*    */   public FixedLengthFrameDecoder(int frameLength) {
/* 51 */     ObjectUtil.checkPositive(frameLength, "frameLength");
/* 52 */     this.frameLength = frameLength;
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 57 */     Object decoded = decode(ctx, in);
/* 58 */     if (decoded != null) {
/* 59 */       out.add(decoded);
/*    */     }
/*    */   }
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
/*    */   protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
/* 73 */     if (in.readableBytes() < this.frameLength) {
/* 74 */       return null;
/*    */     }
/* 76 */     return in.readRetainedSlice(this.frameLength);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\FixedLengthFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */