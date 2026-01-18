/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import java.util.Objects;
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
/*     */ final class QpackAttributes
/*     */ {
/*     */   private final QuicChannel channel;
/*     */   private final boolean dynamicTableDisabled;
/*     */   private final Promise<QuicStreamChannel> encoderStreamPromise;
/*     */   private final Promise<QuicStreamChannel> decoderStreamPromise;
/*     */   private QuicStreamChannel encoderStream;
/*     */   private QuicStreamChannel decoderStream;
/*     */   
/*     */   QpackAttributes(QuicChannel channel, boolean disableDynamicTable) {
/*  37 */     this.channel = channel;
/*  38 */     this.dynamicTableDisabled = disableDynamicTable;
/*  39 */     this.encoderStreamPromise = this.dynamicTableDisabled ? null : channel.eventLoop().newPromise();
/*  40 */     this.decoderStreamPromise = this.dynamicTableDisabled ? null : channel.eventLoop().newPromise();
/*     */   }
/*     */   
/*     */   boolean dynamicTableDisabled() {
/*  44 */     return this.dynamicTableDisabled;
/*     */   }
/*     */   
/*     */   boolean decoderStreamAvailable() {
/*  48 */     return (!this.dynamicTableDisabled && this.decoderStream != null);
/*     */   }
/*     */   
/*     */   boolean encoderStreamAvailable() {
/*  52 */     return (!this.dynamicTableDisabled && this.encoderStream != null);
/*     */   }
/*     */   
/*     */   void whenEncoderStreamAvailable(GenericFutureListener<Future<? super QuicStreamChannel>> listener) {
/*  56 */     assert !this.dynamicTableDisabled;
/*  57 */     assert this.encoderStreamPromise != null;
/*  58 */     this.encoderStreamPromise.addListener(listener);
/*     */   }
/*     */   
/*     */   void whenDecoderStreamAvailable(GenericFutureListener<Future<? super QuicStreamChannel>> listener) {
/*  62 */     assert !this.dynamicTableDisabled;
/*  63 */     assert this.decoderStreamPromise != null;
/*  64 */     this.decoderStreamPromise.addListener(listener);
/*     */   }
/*     */   
/*     */   QuicStreamChannel decoderStream() {
/*  68 */     assert decoderStreamAvailable();
/*  69 */     return this.decoderStream;
/*     */   }
/*     */   
/*     */   QuicStreamChannel encoderStream() {
/*  73 */     assert encoderStreamAvailable();
/*  74 */     return this.encoderStream;
/*     */   }
/*     */   
/*     */   void decoderStream(QuicStreamChannel decoderStream) {
/*  78 */     assert this.channel.eventLoop().inEventLoop();
/*  79 */     assert !this.dynamicTableDisabled;
/*  80 */     assert this.decoderStreamPromise != null;
/*  81 */     assert this.decoderStream == null;
/*  82 */     this.decoderStream = Objects.<QuicStreamChannel>requireNonNull(decoderStream);
/*  83 */     this.decoderStreamPromise.setSuccess(decoderStream);
/*     */   }
/*     */   
/*     */   void encoderStream(QuicStreamChannel encoderStream) {
/*  87 */     assert this.channel.eventLoop().inEventLoop();
/*  88 */     assert !this.dynamicTableDisabled;
/*  89 */     assert this.encoderStreamPromise != null;
/*  90 */     assert this.encoderStream == null;
/*  91 */     this.encoderStream = Objects.<QuicStreamChannel>requireNonNull(encoderStream);
/*  92 */     this.encoderStreamPromise.setSuccess(encoderStream);
/*     */   }
/*     */   
/*     */   void encoderStreamInactive(Throwable cause) {
/*  96 */     assert this.channel.eventLoop().inEventLoop();
/*  97 */     assert !this.dynamicTableDisabled;
/*  98 */     assert this.encoderStreamPromise != null;
/*  99 */     this.encoderStreamPromise.tryFailure(cause);
/*     */   }
/*     */   
/*     */   void decoderStreamInactive(Throwable cause) {
/* 103 */     assert this.channel.eventLoop().inEventLoop();
/* 104 */     assert !this.dynamicTableDisabled;
/* 105 */     assert this.decoderStreamPromise != null;
/* 106 */     this.decoderStreamPromise.tryFailure(cause);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */