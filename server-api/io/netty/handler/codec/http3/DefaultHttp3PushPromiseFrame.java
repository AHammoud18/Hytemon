/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import io.netty.util.internal.StringUtil;
/*    */ import java.util.Objects;
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
/*    */ public final class DefaultHttp3PushPromiseFrame
/*    */   implements Http3PushPromiseFrame
/*    */ {
/*    */   private final long id;
/*    */   private final Http3Headers headers;
/*    */   
/*    */   public DefaultHttp3PushPromiseFrame(long id) {
/* 29 */     this(id, new DefaultHttp3Headers());
/*    */   }
/*    */   
/*    */   public DefaultHttp3PushPromiseFrame(long id, Http3Headers headers) {
/* 33 */     this.id = ObjectUtil.checkPositiveOrZero(id, "id");
/* 34 */     this.headers = (Http3Headers)ObjectUtil.checkNotNull(headers, "headers");
/*    */   }
/*    */ 
/*    */   
/*    */   public long id() {
/* 39 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3Headers headers() {
/* 44 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 49 */     if (this == o) {
/* 50 */       return true;
/*    */     }
/* 52 */     if (o == null || getClass() != o.getClass()) {
/* 53 */       return false;
/*    */     }
/* 55 */     DefaultHttp3PushPromiseFrame that = (DefaultHttp3PushPromiseFrame)o;
/* 56 */     return (this.id == that.id && 
/* 57 */       Objects.equals(this.headers, that.headers));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 62 */     return Objects.hash(new Object[] { Long.valueOf(this.id), this.headers });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return StringUtil.simpleClassName(this) + "(id=" + id() + ", headers=" + headers() + ')';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\DefaultHttp3PushPromiseFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */