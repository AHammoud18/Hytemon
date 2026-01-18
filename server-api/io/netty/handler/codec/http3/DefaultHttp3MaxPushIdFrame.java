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
/*    */ public final class DefaultHttp3MaxPushIdFrame
/*    */   implements Http3MaxPushIdFrame
/*    */ {
/*    */   private final long id;
/*    */   
/*    */   public DefaultHttp3MaxPushIdFrame(long id) {
/* 27 */     this.id = ObjectUtil.checkPositiveOrZero(id, "id");
/*    */   }
/*    */ 
/*    */   
/*    */   public long id() {
/* 32 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 37 */     if (this == o) {
/* 38 */       return true;
/*    */     }
/* 40 */     if (o == null || getClass() != o.getClass()) {
/* 41 */       return false;
/*    */     }
/* 43 */     DefaultHttp3MaxPushIdFrame that = (DefaultHttp3MaxPushIdFrame)o;
/* 44 */     return (this.id == that.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     return Objects.hash(new Object[] { Long.valueOf(this.id) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return StringUtil.simpleClassName(this) + "(id=" + id() + ')';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\DefaultHttp3MaxPushIdFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */