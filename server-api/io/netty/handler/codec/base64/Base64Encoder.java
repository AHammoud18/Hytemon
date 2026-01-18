/*    */ package io.netty.handler.codec.base64;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
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
/*    */ @Sharable
/*    */ public class Base64Encoder
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final boolean breakLines;
/*    */   private final Base64Dialect dialect;
/*    */   
/*    */   public Base64Encoder() {
/* 50 */     this(true);
/*    */   }
/*    */   
/*    */   public Base64Encoder(boolean breakLines) {
/* 54 */     this(breakLines, Base64Dialect.STANDARD);
/*    */   }
/*    */   
/*    */   public Base64Encoder(boolean breakLines, Base64Dialect dialect) {
/* 58 */     super(ByteBuf.class);
/* 59 */     this.dialect = (Base64Dialect)ObjectUtil.checkNotNull(dialect, "dialect");
/* 60 */     this.breakLines = breakLines;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/* 65 */     out.add(Base64.encode(msg, msg.readerIndex(), msg.readableBytes(), this.breakLines, this.dialect));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\base64\Base64Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */