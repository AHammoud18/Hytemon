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
/*    */ public class BlockSelectorToolData
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public float durabilityLossOnUse;
/*    */   
/*    */   public BlockSelectorToolData() {}
/*    */   
/*    */   public BlockSelectorToolData(float durabilityLossOnUse) {
/* 26 */     this.durabilityLossOnUse = durabilityLossOnUse;
/*    */   }
/*    */   
/*    */   public BlockSelectorToolData(@Nonnull BlockSelectorToolData other) {
/* 30 */     this.durabilityLossOnUse = other.durabilityLossOnUse;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BlockSelectorToolData deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     BlockSelectorToolData obj = new BlockSelectorToolData();
/*    */     
/* 37 */     obj.durabilityLossOnUse = buf.getFloatLE(offset + 0);
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
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 49 */     buf.writeFloatLE(this.durabilityLossOnUse);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 55 */     return 4;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 59 */     if (buffer.readableBytes() - offset < 4) {
/* 60 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*    */     }
/*    */ 
/*    */     
/* 64 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BlockSelectorToolData clone() {
/* 68 */     BlockSelectorToolData copy = new BlockSelectorToolData();
/* 69 */     copy.durabilityLossOnUse = this.durabilityLossOnUse;
/* 70 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BlockSelectorToolData other;
/* 76 */     if (this == obj) return true; 
/* 77 */     if (obj instanceof BlockSelectorToolData) { other = (BlockSelectorToolData)obj; } else { return false; }
/* 78 */      return (this.durabilityLossOnUse == other.durabilityLossOnUse);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     return Objects.hash(new Object[] { Float.valueOf(this.durabilityLossOnUse) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockSelectorToolData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */