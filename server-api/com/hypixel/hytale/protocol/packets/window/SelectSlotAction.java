/*    */ package com.hypixel.hytale.protocol.packets.window;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectSlotAction
/*    */   extends WindowAction
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int slot;
/*    */   
/*    */   public SelectSlotAction() {}
/*    */   
/*    */   public SelectSlotAction(int slot) {
/* 26 */     this.slot = slot;
/*    */   }
/*    */   
/*    */   public SelectSlotAction(@Nonnull SelectSlotAction other) {
/* 30 */     this.slot = other.slot;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SelectSlotAction deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     SelectSlotAction obj = new SelectSlotAction();
/*    */     
/* 37 */     obj.slot = buf.getIntLE(offset + 0);
/*    */ 
/*    */     
/* 40 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 44 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public int serialize(@Nonnull ByteBuf buf) {
/* 49 */     int startPos = buf.writerIndex();
/*    */     
/* 51 */     buf.writeIntLE(this.slot);
/*    */     
/* 53 */     return buf.writerIndex() - startPos;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 59 */     return 4;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 63 */     if (buffer.readableBytes() - offset < 4) {
/* 64 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*    */     }
/*    */ 
/*    */     
/* 68 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public SelectSlotAction clone() {
/* 72 */     SelectSlotAction copy = new SelectSlotAction();
/* 73 */     copy.slot = this.slot;
/* 74 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SelectSlotAction other;
/* 80 */     if (this == obj) return true; 
/* 81 */     if (obj instanceof SelectSlotAction) { other = (SelectSlotAction)obj; } else { return false; }
/* 82 */      return (this.slot == other.slot);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 87 */     return Objects.hash(new Object[] { Integer.valueOf(this.slot) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\SelectSlotAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */