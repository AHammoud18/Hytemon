/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AmbienceFXSound
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 27;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 27;
/*     */   public static final int MAX_SIZE = 27;
/*     */   public int soundEventIndex;
/*     */   @Nonnull
/*  21 */   public AmbienceFXSoundPlay3D play3D = AmbienceFXSoundPlay3D.Random; public int blockSoundSetIndex;
/*     */   @Nonnull
/*  23 */   public AmbienceFXAltitude altitude = AmbienceFXAltitude.Normal;
/*     */   
/*     */   @Nullable
/*     */   public Rangef frequency;
/*     */   @Nullable
/*     */   public Range radius;
/*     */   
/*     */   public AmbienceFXSound(int soundEventIndex, @Nonnull AmbienceFXSoundPlay3D play3D, int blockSoundSetIndex, @Nonnull AmbienceFXAltitude altitude, @Nullable Rangef frequency, @Nullable Range radius) {
/*  31 */     this.soundEventIndex = soundEventIndex;
/*  32 */     this.play3D = play3D;
/*  33 */     this.blockSoundSetIndex = blockSoundSetIndex;
/*  34 */     this.altitude = altitude;
/*  35 */     this.frequency = frequency;
/*  36 */     this.radius = radius;
/*     */   }
/*     */   
/*     */   public AmbienceFXSound(@Nonnull AmbienceFXSound other) {
/*  40 */     this.soundEventIndex = other.soundEventIndex;
/*  41 */     this.play3D = other.play3D;
/*  42 */     this.blockSoundSetIndex = other.blockSoundSetIndex;
/*  43 */     this.altitude = other.altitude;
/*  44 */     this.frequency = other.frequency;
/*  45 */     this.radius = other.radius;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AmbienceFXSound deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     AmbienceFXSound obj = new AmbienceFXSound();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.soundEventIndex = buf.getIntLE(offset + 1);
/*  53 */     obj.play3D = AmbienceFXSoundPlay3D.fromValue(buf.getByte(offset + 5));
/*  54 */     obj.blockSoundSetIndex = buf.getIntLE(offset + 6);
/*  55 */     obj.altitude = AmbienceFXAltitude.fromValue(buf.getByte(offset + 10));
/*  56 */     if ((nullBits & 0x1) != 0) obj.frequency = Rangef.deserialize(buf, offset + 11); 
/*  57 */     if ((nullBits & 0x2) != 0) obj.radius = Range.deserialize(buf, offset + 19);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 27;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  68 */     byte nullBits = 0;
/*  69 */     if (this.frequency != null) nullBits = (byte)(nullBits | 0x1); 
/*  70 */     if (this.radius != null) nullBits = (byte)(nullBits | 0x2); 
/*  71 */     buf.writeByte(nullBits);
/*     */     
/*  73 */     buf.writeIntLE(this.soundEventIndex);
/*  74 */     buf.writeByte(this.play3D.getValue());
/*  75 */     buf.writeIntLE(this.blockSoundSetIndex);
/*  76 */     buf.writeByte(this.altitude.getValue());
/*  77 */     if (this.frequency != null) { this.frequency.serialize(buf); } else { buf.writeZero(8); }
/*  78 */      if (this.radius != null) { this.radius.serialize(buf); } else { buf.writeZero(8); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  84 */     return 27;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  88 */     if (buffer.readableBytes() - offset < 27) {
/*  89 */       return ValidationResult.error("Buffer too small: expected at least 27 bytes");
/*     */     }
/*     */ 
/*     */     
/*  93 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AmbienceFXSound clone() {
/*  97 */     AmbienceFXSound copy = new AmbienceFXSound();
/*  98 */     copy.soundEventIndex = this.soundEventIndex;
/*  99 */     copy.play3D = this.play3D;
/* 100 */     copy.blockSoundSetIndex = this.blockSoundSetIndex;
/* 101 */     copy.altitude = this.altitude;
/* 102 */     copy.frequency = (this.frequency != null) ? this.frequency.clone() : null;
/* 103 */     copy.radius = (this.radius != null) ? this.radius.clone() : null;
/* 104 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AmbienceFXSound other;
/* 110 */     if (this == obj) return true; 
/* 111 */     if (obj instanceof AmbienceFXSound) { other = (AmbienceFXSound)obj; } else { return false; }
/* 112 */      return (this.soundEventIndex == other.soundEventIndex && Objects.equals(this.play3D, other.play3D) && this.blockSoundSetIndex == other.blockSoundSetIndex && Objects.equals(this.altitude, other.altitude) && Objects.equals(this.frequency, other.frequency) && Objects.equals(this.radius, other.radius));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return Objects.hash(new Object[] { Integer.valueOf(this.soundEventIndex), this.play3D, Integer.valueOf(this.blockSoundSetIndex), this.altitude, this.frequency, this.radius });
/*     */   }
/*     */   
/*     */   public AmbienceFXSound() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AmbienceFXSound.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */