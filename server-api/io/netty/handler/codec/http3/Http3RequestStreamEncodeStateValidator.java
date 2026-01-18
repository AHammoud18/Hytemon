/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.HttpStatusClass;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ final class Http3RequestStreamEncodeStateValidator
/*     */   extends ChannelOutboundHandlerAdapter
/*     */   implements Http3RequestStreamCodecState
/*     */ {
/*     */   enum State
/*     */   {
/*  29 */     None,
/*  30 */     Headers,
/*  31 */     FinalHeaders,
/*  32 */     Trailers;
/*     */   }
/*  34 */   private State state = State.None;
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  38 */     if (!(msg instanceof Http3RequestStreamFrame)) {
/*  39 */       super.write(ctx, msg, promise);
/*     */       return;
/*     */     } 
/*  42 */     Http3RequestStreamFrame frame = (Http3RequestStreamFrame)msg;
/*  43 */     State nextState = evaluateFrame(this.state, frame);
/*  44 */     if (nextState == null) {
/*  45 */       Http3FrameValidationUtils.frameTypeUnexpected(ctx, msg);
/*     */       return;
/*     */     } 
/*  48 */     this.state = nextState;
/*  49 */     super.write(ctx, msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean started() {
/*  54 */     return isStreamStarted(this.state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receivedFinalHeaders() {
/*  59 */     return isFinalHeadersReceived(this.state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean terminated() {
/*  64 */     return isTrailersReceived(this.state);
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
/*     */   @Nullable
/*     */   static State evaluateFrame(State state, Http3RequestStreamFrame frame) {
/*  81 */     if (frame instanceof Http3PushPromiseFrame || frame instanceof Http3UnknownFrame)
/*     */     {
/*  83 */       return state;
/*     */     }
/*  85 */     switch (state) {
/*     */       case None:
/*     */       case Headers:
/*  88 */         if (!(frame instanceof Http3HeadersFrame)) {
/*  89 */           return null;
/*     */         }
/*  91 */         return isInformationalResponse((Http3HeadersFrame)frame) ? State.Headers : State.FinalHeaders;
/*     */       case FinalHeaders:
/*  93 */         if (frame instanceof Http3HeadersFrame) {
/*  94 */           if (isInformationalResponse((Http3HeadersFrame)frame))
/*     */           {
/*  96 */             return null;
/*     */           }
/*     */           
/*  99 */           return State.Trailers;
/*     */         } 
/* 101 */         return state;
/*     */       case Trailers:
/* 103 */         return null;
/*     */     } 
/* 105 */     throw new Error("Unexpected frame state: " + state);
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isStreamStarted(State state) {
/* 110 */     return (state != State.None);
/*     */   }
/*     */   
/*     */   static boolean isFinalHeadersReceived(State state) {
/* 114 */     return (isStreamStarted(state) && state != State.Headers);
/*     */   }
/*     */   
/*     */   static boolean isTrailersReceived(State state) {
/* 118 */     return (state == State.Trailers);
/*     */   }
/*     */   
/*     */   private static boolean isInformationalResponse(Http3HeadersFrame headersFrame) {
/* 122 */     return (HttpStatusClass.valueOf(headersFrame.headers().status()) == HttpStatusClass.INFORMATIONAL);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamEncodeStateValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */