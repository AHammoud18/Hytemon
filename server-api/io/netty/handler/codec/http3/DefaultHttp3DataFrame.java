/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.buffer.DefaultByteBufHolder;
/*    */ import io.netty.util.ReferenceCounted;
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ public final class DefaultHttp3DataFrame
/*    */   extends DefaultByteBufHolder
/*    */   implements Http3DataFrame
/*    */ {
/*    */   public DefaultHttp3DataFrame(ByteBuf data) {
/* 25 */     super(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame copy() {
/* 30 */     return new DefaultHttp3DataFrame(content().copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame duplicate() {
/* 35 */     return new DefaultHttp3DataFrame(content().duplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame retainedDuplicate() {
/* 40 */     return new DefaultHttp3DataFrame(content().retainedDuplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame replace(ByteBuf content) {
/* 45 */     return new DefaultHttp3DataFrame(content);
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame retain() {
/* 50 */     super.retain();
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame retain(int increment) {
/* 56 */     super.retain(increment);
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame touch() {
/* 62 */     super.touch();
/* 63 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3DataFrame touch(Object hint) {
/* 68 */     super.touch(hint);
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     return StringUtil.simpleClassName(this) + "(content=" + content() + ')';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\DefaultHttp3DataFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */