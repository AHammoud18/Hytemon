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
/*    */ public class HitboxCollisionConfig
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 5;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 5;
/*    */   public static final int MAX_SIZE = 5;
/*    */   @Nonnull
/* 20 */   public CollisionType collisionType = CollisionType.Hard;
/*    */ 
/*    */   
/*    */   public float softCollisionOffsetRatio;
/*    */ 
/*    */   
/*    */   public HitboxCollisionConfig(@Nonnull CollisionType collisionType, float softCollisionOffsetRatio) {
/* 27 */     this.collisionType = collisionType;
/* 28 */     this.softCollisionOffsetRatio = softCollisionOffsetRatio;
/*    */   }
/*    */   
/*    */   public HitboxCollisionConfig(@Nonnull HitboxCollisionConfig other) {
/* 32 */     this.collisionType = other.collisionType;
/* 33 */     this.softCollisionOffsetRatio = other.softCollisionOffsetRatio;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static HitboxCollisionConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     HitboxCollisionConfig obj = new HitboxCollisionConfig();
/*    */     
/* 40 */     obj.collisionType = CollisionType.fromValue(buf.getByte(offset + 0));
/* 41 */     obj.softCollisionOffsetRatio = buf.getFloatLE(offset + 1);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeByte(this.collisionType.getValue());
/* 54 */     buf.writeFloatLE(this.softCollisionOffsetRatio);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 5;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 5) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public HitboxCollisionConfig clone() {
/* 73 */     HitboxCollisionConfig copy = new HitboxCollisionConfig();
/* 74 */     copy.collisionType = this.collisionType;
/* 75 */     copy.softCollisionOffsetRatio = this.softCollisionOffsetRatio;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     HitboxCollisionConfig other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof HitboxCollisionConfig) { other = (HitboxCollisionConfig)obj; } else { return false; }
/* 84 */      return (Objects.equals(this.collisionType, other.collisionType) && this.softCollisionOffsetRatio == other.softCollisionOffsetRatio);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { this.collisionType, Float.valueOf(this.softCollisionOffsetRatio) });
/*    */   }
/*    */   
/*    */   public HitboxCollisionConfig() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\HitboxCollisionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */