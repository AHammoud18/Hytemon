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
/*    */ public class BlockRotation
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 3;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 3;
/*    */   public static final int MAX_SIZE = 3;
/*    */   @Nonnull
/* 20 */   public Rotation rotationYaw = Rotation.None; @Nonnull
/* 21 */   public Rotation rotationPitch = Rotation.None; @Nonnull
/* 22 */   public Rotation rotationRoll = Rotation.None;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockRotation(@Nonnull Rotation rotationYaw, @Nonnull Rotation rotationPitch, @Nonnull Rotation rotationRoll) {
/* 28 */     this.rotationYaw = rotationYaw;
/* 29 */     this.rotationPitch = rotationPitch;
/* 30 */     this.rotationRoll = rotationRoll;
/*    */   }
/*    */   
/*    */   public BlockRotation(@Nonnull BlockRotation other) {
/* 34 */     this.rotationYaw = other.rotationYaw;
/* 35 */     this.rotationPitch = other.rotationPitch;
/* 36 */     this.rotationRoll = other.rotationRoll;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BlockRotation deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     BlockRotation obj = new BlockRotation();
/*    */     
/* 43 */     obj.rotationYaw = Rotation.fromValue(buf.getByte(offset + 0));
/* 44 */     obj.rotationPitch = Rotation.fromValue(buf.getByte(offset + 1));
/* 45 */     obj.rotationRoll = Rotation.fromValue(buf.getByte(offset + 2));
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeByte(this.rotationYaw.getValue());
/* 58 */     buf.writeByte(this.rotationPitch.getValue());
/* 59 */     buf.writeByte(this.rotationRoll.getValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 3;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 3) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 3 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BlockRotation clone() {
/* 78 */     BlockRotation copy = new BlockRotation();
/* 79 */     copy.rotationYaw = this.rotationYaw;
/* 80 */     copy.rotationPitch = this.rotationPitch;
/* 81 */     copy.rotationRoll = this.rotationRoll;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BlockRotation other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof BlockRotation) { other = (BlockRotation)obj; } else { return false; }
/* 90 */      return (Objects.equals(this.rotationYaw, other.rotationYaw) && Objects.equals(this.rotationPitch, other.rotationPitch) && Objects.equals(this.rotationRoll, other.rotationRoll));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { this.rotationYaw, this.rotationPitch, this.rotationRoll });
/*    */   }
/*    */   
/*    */   public BlockRotation() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockRotation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */