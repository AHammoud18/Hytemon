/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public abstract class DefaultSpdyStreamFrame
/*    */   implements SpdyStreamFrame
/*    */ {
/*    */   private int streamId;
/*    */   private boolean last;
/*    */   
/*    */   protected DefaultSpdyStreamFrame(int streamId) {
/* 34 */     setStreamId(streamId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int streamId() {
/* 39 */     return this.streamId;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyStreamFrame setStreamId(int streamId) {
/* 44 */     ObjectUtil.checkPositive(streamId, "streamId");
/* 45 */     this.streamId = streamId;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLast() {
/* 51 */     return this.last;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyStreamFrame setLast(boolean last) {
/* 56 */     this.last = last;
/* 57 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\DefaultSpdyStreamFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */