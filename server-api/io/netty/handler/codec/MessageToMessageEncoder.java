/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseCombiner;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
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
/*     */ public abstract class MessageToMessageEncoder<I>
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   
/*     */   protected MessageToMessageEncoder() {
/*  61 */     this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageToMessageEncoder(Class<? extends I> outboundMessageType) {
/*  70 */     this.matcher = TypeParameterMatcher.get(outboundMessageType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/*  78 */     return this.matcher.match(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  83 */     CodecOutputList out = null;
/*     */     try {
/*  85 */       if (acceptOutboundMessage(msg)) {
/*  86 */         out = CodecOutputList.newInstance();
/*     */         
/*  88 */         I cast = (I)msg;
/*     */         try {
/*  90 */           encode(ctx, cast, out);
/*  91 */         } catch (Throwable th) {
/*  92 */           ReferenceCountUtil.safeRelease(cast);
/*  93 */           PlatformDependent.throwException(th);
/*     */         } 
/*  95 */         ReferenceCountUtil.release(cast);
/*     */         
/*  97 */         if (out.isEmpty()) {
/*  98 */           throw new EncoderException(
/*  99 */               StringUtil.simpleClassName(this) + " must produce at least one message.");
/*     */         }
/*     */       } else {
/* 102 */         ctx.write(msg, promise);
/*     */       } 
/* 104 */     } catch (EncoderException e) {
/* 105 */       throw e;
/* 106 */     } catch (Throwable t) {
/* 107 */       throw new EncoderException(t);
/*     */     } finally {
/* 109 */       if (out != null) {
/*     */         try {
/* 111 */           int sizeMinusOne = out.size() - 1;
/* 112 */           if (sizeMinusOne == 0) {
/* 113 */             ctx.write(out.getUnsafe(0), promise);
/* 114 */           } else if (sizeMinusOne > 0) {
/*     */ 
/*     */             
/* 117 */             if (promise == ctx.voidPromise()) {
/* 118 */               writeVoidPromise(ctx, out);
/*     */             } else {
/* 120 */               writePromiseCombiner(ctx, out, promise);
/*     */             } 
/*     */           } 
/*     */         } finally {
/* 124 */           out.recycle();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeVoidPromise(ChannelHandlerContext ctx, CodecOutputList out) {
/* 131 */     ChannelPromise voidPromise = ctx.voidPromise();
/* 132 */     for (int i = 0; i < out.size(); i++) {
/* 133 */       ctx.write(out.getUnsafe(i), voidPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void writePromiseCombiner(ChannelHandlerContext ctx, CodecOutputList out, ChannelPromise promise) {
/* 138 */     PromiseCombiner combiner = new PromiseCombiner(ctx.executor());
/* 139 */     for (int i = 0; i < out.size(); i++) {
/* 140 */       combiner.add((Future)ctx.write(out.getUnsafe(i)));
/*     */     }
/* 142 */     combiner.finish((Promise)promise);
/*     */   }
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, List<Object> paramList) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\MessageToMessageEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */