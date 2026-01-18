/*    */ package io.netty.handler.codec.string;
/*    */ 
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.util.CharsetUtil;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public final class LineSeparator
/*    */ {
/* 31 */   public static final LineSeparator DEFAULT = new LineSeparator(StringUtil.NEWLINE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public static final LineSeparator UNIX = new LineSeparator("\n");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public static final LineSeparator WINDOWS = new LineSeparator("\r\n");
/*    */ 
/*    */   
/*    */   private final String value;
/*    */ 
/*    */ 
/*    */   
/*    */   public LineSeparator(String lineSeparator) {
/* 49 */     this.value = (String)ObjectUtil.checkNotNull(lineSeparator, "lineSeparator");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String value() {
/* 56 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 61 */     if (this == o) {
/* 62 */       return true;
/*    */     }
/* 64 */     if (!(o instanceof LineSeparator)) {
/* 65 */       return false;
/*    */     }
/* 67 */     LineSeparator that = (LineSeparator)o;
/* 68 */     return (this.value != null) ? this.value.equals(that.value) : ((that.value == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 73 */     return (this.value != null) ? this.value.hashCode() : 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return ByteBufUtil.hexDump(this.value.getBytes(CharsetUtil.UTF_8));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\string\LineSeparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */