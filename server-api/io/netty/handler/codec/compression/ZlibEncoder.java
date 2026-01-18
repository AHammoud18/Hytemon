/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ZlibEncoder
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   protected ZlibEncoder() {
/* 29 */     super(ByteBuf.class, false);
/*    */   }
/*    */   
/*    */   public abstract boolean isClosed();
/*    */   
/*    */   public abstract ChannelFuture close();
/*    */   
/*    */   public abstract ChannelFuture close(ChannelPromise paramChannelPromise);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZlibEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */