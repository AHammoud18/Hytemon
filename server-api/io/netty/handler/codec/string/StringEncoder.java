/*    */ package io.netty.handler.codec.string;
/*    */ 
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.nio.CharBuffer;
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
/*    */ @Sharable
/*    */ public class StringEncoder
/*    */   extends MessageToMessageEncoder<CharSequence>
/*    */ {
/*    */   private final Charset charset;
/*    */   
/*    */   public StringEncoder() {
/* 61 */     this(Charset.defaultCharset());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StringEncoder(Charset charset) {
/* 68 */     super(CharSequence.class);
/* 69 */     this.charset = (Charset)ObjectUtil.checkNotNull(charset, "charset");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
/* 74 */     if (msg.length() == 0) {
/*    */       return;
/*    */     }
/*    */     
/* 78 */     out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), this.charset));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\string\StringEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */