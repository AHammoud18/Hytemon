/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class LengthFieldPrepender
/*     */   extends MessageToMessageEncoder<ByteBuf>
/*     */ {
/*     */   private final ByteOrder byteOrder;
/*     */   private final int lengthFieldLength;
/*     */   private final boolean lengthIncludesLengthFieldLength;
/*     */   private final int lengthAdjustment;
/*     */   
/*     */   public LengthFieldPrepender(int lengthFieldLength) {
/*  73 */     this(lengthFieldLength, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldPrepender(int lengthFieldLength, boolean lengthIncludesLengthFieldLength) {
/*  90 */     this(lengthFieldLength, 0, lengthIncludesLengthFieldLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment) {
/* 105 */     this(lengthFieldLength, lengthAdjustment, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
/* 124 */     this(ByteOrder.BIG_ENDIAN, lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldPrepender(ByteOrder byteOrder, int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
/* 146 */     super(ByteBuf.class);
/* 147 */     if (lengthFieldLength != 1 && lengthFieldLength != 2 && lengthFieldLength != 3 && lengthFieldLength != 4 && lengthFieldLength != 8)
/*     */     {
/*     */       
/* 150 */       throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength);
/*     */     }
/*     */ 
/*     */     
/* 154 */     this.byteOrder = (ByteOrder)ObjectUtil.checkNotNull(byteOrder, "byteOrder");
/* 155 */     this.lengthFieldLength = lengthFieldLength;
/* 156 */     this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength;
/* 157 */     this.lengthAdjustment = lengthAdjustment;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/* 162 */     int length = msg.readableBytes() + this.lengthAdjustment;
/* 163 */     if (this.lengthIncludesLengthFieldLength) {
/* 164 */       length += this.lengthFieldLength;
/*     */     }
/*     */     
/* 167 */     ObjectUtil.checkPositiveOrZero(length, "length");
/*     */     
/* 169 */     switch (this.lengthFieldLength) {
/*     */       case 1:
/* 171 */         if (length >= 256) {
/* 172 */           throw new IllegalArgumentException("length does not fit into a byte: " + length);
/*     */         }
/*     */         
/* 175 */         out.add(ctx.alloc().buffer(1).order(this.byteOrder).writeByte((byte)length));
/*     */         break;
/*     */       case 2:
/* 178 */         if (length >= 65536) {
/* 179 */           throw new IllegalArgumentException("length does not fit into a short integer: " + length);
/*     */         }
/*     */         
/* 182 */         out.add(ctx.alloc().buffer(2).order(this.byteOrder).writeShort((short)length));
/*     */         break;
/*     */       case 3:
/* 185 */         if (length >= 16777216) {
/* 186 */           throw new IllegalArgumentException("length does not fit into a medium integer: " + length);
/*     */         }
/*     */         
/* 189 */         out.add(ctx.alloc().buffer(3).order(this.byteOrder).writeMedium(length));
/*     */         break;
/*     */       case 4:
/* 192 */         out.add(ctx.alloc().buffer(4).order(this.byteOrder).writeInt(length));
/*     */         break;
/*     */       case 8:
/* 195 */         out.add(ctx.alloc().buffer(8).order(this.byteOrder).writeLong(length));
/*     */         break;
/*     */       default:
/* 198 */         throw new Error("Unexpected length field length: " + this.lengthFieldLength);
/*     */     } 
/* 200 */     out.add(msg.retain());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\LengthFieldPrepender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */