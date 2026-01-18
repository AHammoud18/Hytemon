/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class PlaySoundEvent3D
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 155;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 38;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 38;
/*     */   public static final int MAX_SIZE = 38;
/*     */   public int soundEventIndex;
/*     */   
/*     */   public int getId() {
/*  26 */     return 155;
/*     */   }
/*     */   
/*     */   @Nonnull
/*  30 */   public SoundCategory category = SoundCategory.Music;
/*     */   
/*     */   @Nullable
/*     */   public Position position;
/*     */   
/*     */   public float volumeModifier;
/*     */   public float pitchModifier;
/*     */   
/*     */   public PlaySoundEvent3D(int soundEventIndex, @Nonnull SoundCategory category, @Nullable Position position, float volumeModifier, float pitchModifier) {
/*  39 */     this.soundEventIndex = soundEventIndex;
/*  40 */     this.category = category;
/*  41 */     this.position = position;
/*  42 */     this.volumeModifier = volumeModifier;
/*  43 */     this.pitchModifier = pitchModifier;
/*     */   }
/*     */   
/*     */   public PlaySoundEvent3D(@Nonnull PlaySoundEvent3D other) {
/*  47 */     this.soundEventIndex = other.soundEventIndex;
/*  48 */     this.category = other.category;
/*  49 */     this.position = other.position;
/*  50 */     this.volumeModifier = other.volumeModifier;
/*  51 */     this.pitchModifier = other.pitchModifier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PlaySoundEvent3D deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     PlaySoundEvent3D obj = new PlaySoundEvent3D();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.soundEventIndex = buf.getIntLE(offset + 1);
/*  59 */     obj.category = SoundCategory.fromValue(buf.getByte(offset + 5));
/*  60 */     if ((nullBits & 0x1) != 0) obj.position = Position.deserialize(buf, offset + 6); 
/*  61 */     obj.volumeModifier = buf.getFloatLE(offset + 30);
/*  62 */     obj.pitchModifier = buf.getFloatLE(offset + 34);
/*     */ 
/*     */     
/*  65 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  69 */     return 38;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */     
/*  78 */     buf.writeIntLE(this.soundEventIndex);
/*  79 */     buf.writeByte(this.category.getValue());
/*  80 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(24); }
/*  81 */      buf.writeFloatLE(this.volumeModifier);
/*  82 */     buf.writeFloatLE(this.pitchModifier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  88 */     return 38;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  92 */     if (buffer.readableBytes() - offset < 38) {
/*  93 */       return ValidationResult.error("Buffer too small: expected at least 38 bytes");
/*     */     }
/*     */ 
/*     */     
/*  97 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PlaySoundEvent3D clone() {
/* 101 */     PlaySoundEvent3D copy = new PlaySoundEvent3D();
/* 102 */     copy.soundEventIndex = this.soundEventIndex;
/* 103 */     copy.category = this.category;
/* 104 */     copy.position = (this.position != null) ? this.position.clone() : null;
/* 105 */     copy.volumeModifier = this.volumeModifier;
/* 106 */     copy.pitchModifier = this.pitchModifier;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PlaySoundEvent3D other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof PlaySoundEvent3D) { other = (PlaySoundEvent3D)obj; } else { return false; }
/* 115 */      return (this.soundEventIndex == other.soundEventIndex && Objects.equals(this.category, other.category) && Objects.equals(this.position, other.position) && this.volumeModifier == other.volumeModifier && this.pitchModifier == other.pitchModifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { Integer.valueOf(this.soundEventIndex), this.category, this.position, Float.valueOf(this.volumeModifier), Float.valueOf(this.pitchModifier) });
/*     */   }
/*     */   
/*     */   public PlaySoundEvent3D() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\PlaySoundEvent3D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */