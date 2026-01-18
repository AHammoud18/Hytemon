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
/*    */ public class ParticleCollision
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 3;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 3;
/*    */   public static final int MAX_SIZE = 3;
/*    */   @Nonnull
/* 20 */   public ParticleCollisionBlockType blockType = ParticleCollisionBlockType.None; @Nonnull
/* 21 */   public ParticleCollisionAction action = ParticleCollisionAction.Expire; @Nonnull
/* 22 */   public ParticleRotationInfluence particleRotationInfluence = ParticleRotationInfluence.None;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParticleCollision(@Nonnull ParticleCollisionBlockType blockType, @Nonnull ParticleCollisionAction action, @Nonnull ParticleRotationInfluence particleRotationInfluence) {
/* 28 */     this.blockType = blockType;
/* 29 */     this.action = action;
/* 30 */     this.particleRotationInfluence = particleRotationInfluence;
/*    */   }
/*    */   
/*    */   public ParticleCollision(@Nonnull ParticleCollision other) {
/* 34 */     this.blockType = other.blockType;
/* 35 */     this.action = other.action;
/* 36 */     this.particleRotationInfluence = other.particleRotationInfluence;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ParticleCollision deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     ParticleCollision obj = new ParticleCollision();
/*    */     
/* 43 */     obj.blockType = ParticleCollisionBlockType.fromValue(buf.getByte(offset + 0));
/* 44 */     obj.action = ParticleCollisionAction.fromValue(buf.getByte(offset + 1));
/* 45 */     obj.particleRotationInfluence = ParticleRotationInfluence.fromValue(buf.getByte(offset + 2));
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
/* 57 */     buf.writeByte(this.blockType.getValue());
/* 58 */     buf.writeByte(this.action.getValue());
/* 59 */     buf.writeByte(this.particleRotationInfluence.getValue());
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
/*    */   public ParticleCollision clone() {
/* 78 */     ParticleCollision copy = new ParticleCollision();
/* 79 */     copy.blockType = this.blockType;
/* 80 */     copy.action = this.action;
/* 81 */     copy.particleRotationInfluence = this.particleRotationInfluence;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     ParticleCollision other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof ParticleCollision) { other = (ParticleCollision)obj; } else { return false; }
/* 90 */      return (Objects.equals(this.blockType, other.blockType) && Objects.equals(this.action, other.action) && Objects.equals(this.particleRotationInfluence, other.particleRotationInfluence));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { this.blockType, this.action, this.particleRotationInfluence });
/*    */   }
/*    */   
/*    */   public ParticleCollision() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ParticleCollision.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */