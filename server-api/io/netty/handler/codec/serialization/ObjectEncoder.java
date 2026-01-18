/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufOutputStream;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ @Sharable
/*    */ public class ObjectEncoder
/*    */   extends MessageToByteEncoder<Serializable>
/*    */ {
/* 49 */   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
/*    */   
/*    */   public ObjectEncoder() {
/* 52 */     super(Serializable.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
/* 57 */     int startIdx = out.writerIndex();
/*    */     
/* 59 */     ByteBufOutputStream bout = new ByteBufOutputStream(out);
/* 60 */     ObjectOutputStream oout = null;
/*    */     try {
/* 62 */       bout.write(LENGTH_PLACEHOLDER);
/* 63 */       oout = new CompactObjectOutputStream((OutputStream)bout);
/* 64 */       oout.writeObject(msg);
/* 65 */       oout.flush();
/*    */     } finally {
/* 67 */       if (oout != null) {
/* 68 */         oout.close();
/*    */       } else {
/* 70 */         bout.close();
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     int endIdx = out.writerIndex();
/*    */     
/* 76 */     out.setInt(startIdx, endIdx - startIdx - 4);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\serialization\ObjectEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */