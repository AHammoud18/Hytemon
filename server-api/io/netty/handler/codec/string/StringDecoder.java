/*    */ package io.netty.handler.codec.string;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.nio.charset.Charset;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class StringDecoder
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final Charset charset;
/*    */   
/*    */   public StringDecoder() {
/* 65 */     this(Charset.defaultCharset());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StringDecoder(Charset charset) {
/* 72 */     super(ByteBuf.class);
/* 73 */     this.charset = (Charset)ObjectUtil.checkNotNull(charset, "charset");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/* 78 */     out.add(msg.toString(this.charset));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\string\StringDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */