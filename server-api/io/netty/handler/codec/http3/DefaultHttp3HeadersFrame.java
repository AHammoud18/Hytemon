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
/*    */ public final class DefaultHttp3HeadersFrame
/*    */   implements Http3HeadersFrame
/*    */ {
/*    */   private final Http3Headers headers;
/*    */   
/*    */   public DefaultHttp3HeadersFrame() {
/* 28 */     this(new DefaultHttp3Headers());
/*    */   }
/*    */   
/*    */   public DefaultHttp3HeadersFrame(Http3Headers headers) {
/* 32 */     this.headers = (Http3Headers)ObjectUtil.checkNotNull(headers, "headers");
/*    */   }
/*    */ 
/*    */   
/*    */   public Http3Headers headers() {
/* 37 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 42 */     if (this == o) {
/* 43 */       return true;
/*    */     }
/* 45 */     if (o == null || getClass() != o.getClass()) {
/* 46 */       return false;
/*    */     }
/* 48 */     DefaultHttp3HeadersFrame that = (DefaultHttp3HeadersFrame)o;
/* 49 */     return Objects.equals(this.headers, that.headers);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 54 */     return Objects.hash(new Object[] { this.headers });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return StringUtil.simpleClassName(this) + "(headers=" + headers() + ')';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\DefaultHttp3HeadersFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */