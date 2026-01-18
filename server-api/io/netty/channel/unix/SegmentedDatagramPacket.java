/*     */ package io.netty.channel.unix;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.InetSocketAddress;
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
/*     */ public class SegmentedDatagramPacket
/*     */   extends DatagramPacket
/*     */ {
/*     */   private final int segmentSize;
/*     */   
/*     */   public SegmentedDatagramPacket(ByteBuf data, int segmentSize, InetSocketAddress recipient) {
/*  40 */     super(data, recipient);
/*  41 */     this.segmentSize = ObjectUtil.checkPositive(segmentSize, "segmentSize");
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
/*     */   public SegmentedDatagramPacket(ByteBuf data, int segmentSize, InetSocketAddress recipient, InetSocketAddress sender) {
/*  53 */     super(data, recipient, sender);
/*  54 */     this.segmentSize = ObjectUtil.checkPositive(segmentSize, "segmentSize");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int segmentSize() {
/*  63 */     return this.segmentSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket copy() {
/*  68 */     return new SegmentedDatagramPacket(((ByteBuf)content()).copy(), this.segmentSize, (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket duplicate() {
/*  73 */     return new SegmentedDatagramPacket(((ByteBuf)content()).duplicate(), this.segmentSize, (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket retainedDuplicate() {
/*  78 */     return new SegmentedDatagramPacket(((ByteBuf)content()).retainedDuplicate(), this.segmentSize, (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket replace(ByteBuf content) {
/*  83 */     return new SegmentedDatagramPacket(content, this.segmentSize, (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket retain() {
/*  88 */     super.retain();
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket retain(int increment) {
/*  94 */     super.retain(increment);
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket touch() {
/* 100 */     super.touch();
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket touch(Object hint) {
/* 106 */     super.touch(hint);
/* 107 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\SegmentedDatagramPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */