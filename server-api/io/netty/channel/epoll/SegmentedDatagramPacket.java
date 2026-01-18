/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.unix.SegmentedDatagramPacket;
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ @Deprecated
/*     */ public final class SegmentedDatagramPacket
/*     */   extends SegmentedDatagramPacket
/*     */ {
/*     */   public SegmentedDatagramPacket(ByteBuf data, int segmentSize, InetSocketAddress recipient) {
/*  36 */     super(data, segmentSize, recipient);
/*  37 */     checkIsSupported();
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
/*  49 */     super(data, segmentSize, recipient, sender);
/*  50 */     checkIsSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/*  57 */     return (Epoll.isAvailable() && Native.IS_SUPPORTING_SENDMMSG && Native.IS_SUPPORTING_UDP_SEGMENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket copy() {
/*  64 */     return new SegmentedDatagramPacket(((ByteBuf)content()).copy(), segmentSize(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket duplicate() {
/*  69 */     return new SegmentedDatagramPacket(((ByteBuf)content()).duplicate(), segmentSize(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket retainedDuplicate() {
/*  74 */     return new SegmentedDatagramPacket(((ByteBuf)content()).retainedDuplicate(), segmentSize(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket replace(ByteBuf content) {
/*  79 */     return new SegmentedDatagramPacket(content, segmentSize(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket retain() {
/*  84 */     super.retain();
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket retain(int increment) {
/*  90 */     super.retain(increment);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket touch() {
/*  96 */     super.touch();
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SegmentedDatagramPacket touch(Object hint) {
/* 102 */     super.touch(hint);
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   private static void checkIsSupported() {
/* 107 */     if (!isSupported())
/* 108 */       throw new IllegalStateException(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\SegmentedDatagramPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */