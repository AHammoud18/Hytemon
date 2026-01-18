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
/*    */ public class BlockFlags
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 2;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 2;
/*    */   public static final int MAX_SIZE = 2;
/*    */   public boolean isUsable;
/*    */   public boolean isStackable;
/*    */   
/*    */   public BlockFlags() {}
/*    */   
/*    */   public BlockFlags(boolean isUsable, boolean isStackable) {
/* 27 */     this.isUsable = isUsable;
/* 28 */     this.isStackable = isStackable;
/*    */   }
/*    */   
/*    */   public BlockFlags(@Nonnull BlockFlags other) {
/* 32 */     this.isUsable = other.isUsable;
/* 33 */     this.isStackable = other.isStackable;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BlockFlags deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     BlockFlags obj = new BlockFlags();
/*    */     
/* 40 */     obj.isUsable = (buf.getByte(offset + 0) != 0);
/* 41 */     obj.isStackable = (buf.getByte(offset + 1) != 0);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeByte(this.isUsable ? 1 : 0);
/* 54 */     buf.writeByte(this.isStackable ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 2;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 2) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BlockFlags clone() {
/* 73 */     BlockFlags copy = new BlockFlags();
/* 74 */     copy.isUsable = this.isUsable;
/* 75 */     copy.isStackable = this.isStackable;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BlockFlags other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof BlockFlags) { other = (BlockFlags)obj; } else { return false; }
/* 84 */      return (this.isUsable == other.isUsable && this.isStackable == other.isStackable);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Boolean.valueOf(this.isUsable), Boolean.valueOf(this.isStackable) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockFlags.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */