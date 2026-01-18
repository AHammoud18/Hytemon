/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufInputStream;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/*    */ public class ObjectDecoder
/*    */   extends LengthFieldBasedFrameDecoder
/*    */ {
/*    */   private final ClassResolver classResolver;
/*    */   
/*    */   public ObjectDecoder(ClassResolver classResolver) {
/* 60 */     this(1048576, classResolver);
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
/*    */   
/*    */   public ObjectDecoder(int maxObjectSize, ClassResolver classResolver) {
/* 74 */     super(maxObjectSize, 0, 4, 0, 4);
/* 75 */     this.classResolver = classResolver;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
/* 80 */     ByteBuf frame = (ByteBuf)super.decode(ctx, in);
/* 81 */     if (frame == null) {
/* 82 */       return null;
/*    */     }
/*    */     
/* 85 */     ObjectInputStream ois = new CompactObjectInputStream((InputStream)new ByteBufInputStream(frame, true), this.classResolver);
/*    */     try {
/* 87 */       return ois.readObject();
/*    */     } finally {
/* 89 */       ois.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\serialization\ObjectDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */