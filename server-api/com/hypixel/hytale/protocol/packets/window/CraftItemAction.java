/*    */ package com.hypixel.hytale.protocol.packets.window;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CraftItemAction
/*    */   extends WindowAction
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 0;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 0;
/*    */   public static final int MAX_SIZE = 0;
/*    */   
/*    */   @Nonnull
/*    */   public static CraftItemAction deserialize(@Nonnull ByteBuf buf, int offset) {
/* 24 */     CraftItemAction obj = new CraftItemAction();
/*    */ 
/*    */ 
/*    */     
/* 28 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 32 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int serialize(@Nonnull ByteBuf buf) {
/* 37 */     int startPos = buf.writerIndex();
/*    */ 
/*    */     
/* 40 */     return buf.writerIndex() - startPos;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 46 */     return 0;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 50 */     if (buffer.readableBytes() - offset < 0) {
/* 51 */       return ValidationResult.error("Buffer too small: expected at least 0 bytes");
/*    */     }
/*    */ 
/*    */     
/* 55 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public CraftItemAction clone() {
/* 59 */     return new CraftItemAction();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 64 */     if (this == obj) return true; 
/* 65 */     if (obj instanceof CraftItemAction) { CraftItemAction other = (CraftItemAction)obj; } else { return false; }
/* 66 */      return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 71 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\CraftItemAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */