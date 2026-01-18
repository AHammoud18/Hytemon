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
/*    */ public class BlockPosition
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 12;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 12;
/*    */   public static final int MAX_SIZE = 12;
/*    */   public int x;
/*    */   public int y;
/*    */   public int z;
/*    */   
/*    */   public BlockPosition() {}
/*    */   
/*    */   public BlockPosition(int x, int y, int z) {
/* 28 */     this.x = x;
/* 29 */     this.y = y;
/* 30 */     this.z = z;
/*    */   }
/*    */   
/*    */   public BlockPosition(@Nonnull BlockPosition other) {
/* 34 */     this.x = other.x;
/* 35 */     this.y = other.y;
/* 36 */     this.z = other.z;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BlockPosition deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     BlockPosition obj = new BlockPosition();
/*    */     
/* 43 */     obj.x = buf.getIntLE(offset + 0);
/* 44 */     obj.y = buf.getIntLE(offset + 4);
/* 45 */     obj.z = buf.getIntLE(offset + 8);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 12;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeIntLE(this.x);
/* 58 */     buf.writeIntLE(this.y);
/* 59 */     buf.writeIntLE(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 12;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 12) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BlockPosition clone() {
/* 78 */     BlockPosition copy = new BlockPosition();
/* 79 */     copy.x = this.x;
/* 80 */     copy.y = this.y;
/* 81 */     copy.z = this.z;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BlockPosition other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof BlockPosition) { other = (BlockPosition)obj; } else { return false; }
/* 90 */      return (this.x == other.x && this.y == other.y && this.z == other.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */