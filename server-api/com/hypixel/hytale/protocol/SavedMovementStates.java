/*    */ package com.hypixel.hytale.protocol;
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
/*    */ 
/*    */ public class SavedMovementStates
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean flying;
/*    */   
/*    */   public SavedMovementStates() {}
/*    */   
/*    */   public SavedMovementStates(boolean flying) {
/* 26 */     this.flying = flying;
/*    */   }
/*    */   
/*    */   public SavedMovementStates(@Nonnull SavedMovementStates other) {
/* 30 */     this.flying = other.flying;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SavedMovementStates deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     SavedMovementStates obj = new SavedMovementStates();
/*    */     
/* 37 */     obj.flying = (buf.getByte(offset + 0) != 0);
/*    */ 
/*    */     
/* 40 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 44 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 49 */     buf.writeByte(this.flying ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 55 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 59 */     if (buffer.readableBytes() - offset < 1) {
/* 60 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 64 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public SavedMovementStates clone() {
/* 68 */     SavedMovementStates copy = new SavedMovementStates();
/* 69 */     copy.flying = this.flying;
/* 70 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SavedMovementStates other;
/* 76 */     if (this == obj) return true; 
/* 77 */     if (obj instanceof SavedMovementStates) { other = (SavedMovementStates)obj; } else { return false; }
/* 78 */      return (this.flying == other.flying);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     return Objects.hash(new Object[] { Boolean.valueOf(this.flying) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SavedMovementStates.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */