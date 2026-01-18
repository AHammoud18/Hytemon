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
/*    */ public class AmbienceFXSoundEffect
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 9;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 9;
/*    */   public static final int MAX_SIZE = 9;
/*    */   public int reverbEffectIndex;
/*    */   public int equalizerEffectIndex;
/*    */   public boolean isInstant;
/*    */   
/*    */   public AmbienceFXSoundEffect() {}
/*    */   
/*    */   public AmbienceFXSoundEffect(int reverbEffectIndex, int equalizerEffectIndex, boolean isInstant) {
/* 28 */     this.reverbEffectIndex = reverbEffectIndex;
/* 29 */     this.equalizerEffectIndex = equalizerEffectIndex;
/* 30 */     this.isInstant = isInstant;
/*    */   }
/*    */   
/*    */   public AmbienceFXSoundEffect(@Nonnull AmbienceFXSoundEffect other) {
/* 34 */     this.reverbEffectIndex = other.reverbEffectIndex;
/* 35 */     this.equalizerEffectIndex = other.equalizerEffectIndex;
/* 36 */     this.isInstant = other.isInstant;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AmbienceFXSoundEffect deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     AmbienceFXSoundEffect obj = new AmbienceFXSoundEffect();
/*    */     
/* 43 */     obj.reverbEffectIndex = buf.getIntLE(offset + 0);
/* 44 */     obj.equalizerEffectIndex = buf.getIntLE(offset + 4);
/* 45 */     obj.isInstant = (buf.getByte(offset + 8) != 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeIntLE(this.reverbEffectIndex);
/* 58 */     buf.writeIntLE(this.equalizerEffectIndex);
/* 59 */     buf.writeByte(this.isInstant ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 9;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 9) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AmbienceFXSoundEffect clone() {
/* 78 */     AmbienceFXSoundEffect copy = new AmbienceFXSoundEffect();
/* 79 */     copy.reverbEffectIndex = this.reverbEffectIndex;
/* 80 */     copy.equalizerEffectIndex = this.equalizerEffectIndex;
/* 81 */     copy.isInstant = this.isInstant;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AmbienceFXSoundEffect other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof AmbienceFXSoundEffect) { other = (AmbienceFXSoundEffect)obj; } else { return false; }
/* 90 */      return (this.reverbEffectIndex == other.reverbEffectIndex && this.equalizerEffectIndex == other.equalizerEffectIndex && this.isInstant == other.isInstant);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Integer.valueOf(this.reverbEffectIndex), Integer.valueOf(this.equalizerEffectIndex), Boolean.valueOf(this.isInstant) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AmbienceFXSoundEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */