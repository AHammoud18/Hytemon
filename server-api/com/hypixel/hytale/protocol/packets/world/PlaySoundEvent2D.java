/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlaySoundEvent2D
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 154;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 13;
/*     */   public int soundEventIndex;
/*     */   
/*     */   public int getId() {
/*  25 */     return 154;
/*     */   }
/*     */   
/*     */   @Nonnull
/*  29 */   public SoundCategory category = SoundCategory.Music;
/*     */   
/*     */   public float volumeModifier;
/*     */   
/*     */   public float pitchModifier;
/*     */ 
/*     */   
/*     */   public PlaySoundEvent2D(int soundEventIndex, @Nonnull SoundCategory category, float volumeModifier, float pitchModifier) {
/*  37 */     this.soundEventIndex = soundEventIndex;
/*  38 */     this.category = category;
/*  39 */     this.volumeModifier = volumeModifier;
/*  40 */     this.pitchModifier = pitchModifier;
/*     */   }
/*     */   
/*     */   public PlaySoundEvent2D(@Nonnull PlaySoundEvent2D other) {
/*  44 */     this.soundEventIndex = other.soundEventIndex;
/*  45 */     this.category = other.category;
/*  46 */     this.volumeModifier = other.volumeModifier;
/*  47 */     this.pitchModifier = other.pitchModifier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PlaySoundEvent2D deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     PlaySoundEvent2D obj = new PlaySoundEvent2D();
/*     */     
/*  54 */     obj.soundEventIndex = buf.getIntLE(offset + 0);
/*  55 */     obj.category = SoundCategory.fromValue(buf.getByte(offset + 4));
/*  56 */     obj.volumeModifier = buf.getFloatLE(offset + 5);
/*  57 */     obj.pitchModifier = buf.getFloatLE(offset + 9);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 13;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  70 */     buf.writeIntLE(this.soundEventIndex);
/*  71 */     buf.writeByte(this.category.getValue());
/*  72 */     buf.writeFloatLE(this.volumeModifier);
/*  73 */     buf.writeFloatLE(this.pitchModifier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  79 */     return 13;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  83 */     if (buffer.readableBytes() - offset < 13) {
/*  84 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */ 
/*     */     
/*  88 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PlaySoundEvent2D clone() {
/*  92 */     PlaySoundEvent2D copy = new PlaySoundEvent2D();
/*  93 */     copy.soundEventIndex = this.soundEventIndex;
/*  94 */     copy.category = this.category;
/*  95 */     copy.volumeModifier = this.volumeModifier;
/*  96 */     copy.pitchModifier = this.pitchModifier;
/*  97 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PlaySoundEvent2D other;
/* 103 */     if (this == obj) return true; 
/* 104 */     if (obj instanceof PlaySoundEvent2D) { other = (PlaySoundEvent2D)obj; } else { return false; }
/* 105 */      return (this.soundEventIndex == other.soundEventIndex && Objects.equals(this.category, other.category) && this.volumeModifier == other.volumeModifier && this.pitchModifier == other.pitchModifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     return Objects.hash(new Object[] { Integer.valueOf(this.soundEventIndex), this.category, Float.valueOf(this.volumeModifier), Float.valueOf(this.pitchModifier) });
/*     */   }
/*     */   
/*     */   public PlaySoundEvent2D() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\PlaySoundEvent2D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */