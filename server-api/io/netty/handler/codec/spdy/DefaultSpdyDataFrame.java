/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public class DefaultSpdyDataFrame
/*     */   extends DefaultSpdyStreamFrame
/*     */   implements SpdyDataFrame
/*     */ {
/*     */   private final ByteBuf data;
/*     */   
/*     */   public DefaultSpdyDataFrame(int streamId) {
/*  37 */     this(streamId, Unpooled.buffer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSpdyDataFrame(int streamId, ByteBuf data) {
/*  47 */     super(streamId);
/*  48 */     this.data = validate(
/*  49 */         (ByteBuf)ObjectUtil.checkNotNull(data, "data"));
/*     */   }
/*     */   
/*     */   private static ByteBuf validate(ByteBuf data) {
/*  53 */     if (data.readableBytes() > 16777215) {
/*  54 */       throw new IllegalArgumentException("data payload cannot exceed 16777215 bytes");
/*     */     }
/*     */     
/*  57 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame setStreamId(int streamId) {
/*  62 */     super.setStreamId(streamId);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame setLast(boolean last) {
/*  68 */     super.setLast(last);
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  74 */     return ByteBufUtil.ensureAccessible(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame copy() {
/*  79 */     return replace(content().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame duplicate() {
/*  84 */     return replace(content().duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame retainedDuplicate() {
/*  89 */     return replace(content().retainedDuplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame replace(ByteBuf content) {
/*  94 */     SpdyDataFrame frame = new DefaultSpdyDataFrame(streamId(), content);
/*  95 */     frame.setLast(isLast());
/*  96 */     return frame;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 101 */     return this.data.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame retain() {
/* 106 */     this.data.retain();
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame retain(int increment) {
/* 112 */     this.data.retain(increment);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame touch() {
/* 118 */     this.data.touch();
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyDataFrame touch(Object hint) {
/* 124 */     this.data.touch(hint);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 130 */     return this.data.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 135 */     return this.data.release(decrement);
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
/*     */   public String toString() {
/* 149 */     StringBuilder buf = (new StringBuilder()).append(StringUtil.simpleClassName(this)).append("(last: ").append(isLast()).append(')').append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(streamId()).append(StringUtil.NEWLINE).append("--> Size = ");
/* 150 */     if (refCnt() == 0) {
/* 151 */       buf.append("(freed)");
/*     */     } else {
/* 153 */       buf.append(content().readableBytes());
/*     */     } 
/* 155 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\DefaultSpdyDataFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */