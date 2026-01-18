/*    */ package io.netty.handler.codec.base64;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ @Sharable
/*    */ public class Base64Decoder
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final Base64Dialect dialect;
/*    */   
/*    */   public Base64Decoder() {
/* 53 */     this(Base64Dialect.STANDARD);
/*    */   }
/*    */   
/*    */   public Base64Decoder(Base64Dialect dialect) {
/* 57 */     super(ByteBuf.class);
/* 58 */     this.dialect = (Base64Dialect)ObjectUtil.checkNotNull(dialect, "dialect");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/* 63 */     out.add(Base64.decode(msg, msg.readerIndex(), msg.readableBytes(), this.dialect));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\base64\Base64Decoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */