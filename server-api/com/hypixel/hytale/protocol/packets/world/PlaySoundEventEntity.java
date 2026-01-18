/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PlaySoundEventEntity
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 156;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 16;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 16;
/*     */   public static final int MAX_SIZE = 16;
/*     */   public int soundEventIndex;
/*     */   public int networkId;
/*     */   public float volumeModifier;
/*     */   public float pitchModifier;
/*     */   
/*     */   public int getId() {
/*  25 */     return 156;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlaySoundEventEntity() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlaySoundEventEntity(int soundEventIndex, int networkId, float volumeModifier, float pitchModifier) {
/*  37 */     this.soundEventIndex = soundEventIndex;
/*  38 */     this.networkId = networkId;
/*  39 */     this.volumeModifier = volumeModifier;
/*  40 */     this.pitchModifier = pitchModifier;
/*     */   }
/*     */   
/*     */   public PlaySoundEventEntity(@Nonnull PlaySoundEventEntity other) {
/*  44 */     this.soundEventIndex = other.soundEventIndex;
/*  45 */     this.networkId = other.networkId;
/*  46 */     this.volumeModifier = other.volumeModifier;
/*  47 */     this.pitchModifier = other.pitchModifier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PlaySoundEventEntity deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     PlaySoundEventEntity obj = new PlaySoundEventEntity();
/*     */     
/*  54 */     obj.soundEventIndex = buf.getIntLE(offset + 0);
/*  55 */     obj.networkId = buf.getIntLE(offset + 4);
/*  56 */     obj.volumeModifier = buf.getFloatLE(offset + 8);
/*  57 */     obj.pitchModifier = buf.getFloatLE(offset + 12);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  70 */     buf.writeIntLE(this.soundEventIndex);
/*  71 */     buf.writeIntLE(this.networkId);
/*  72 */     buf.writeFloatLE(this.volumeModifier);
/*  73 */     buf.writeFloatLE(this.pitchModifier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  79 */     return 16;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  83 */     if (buffer.readableBytes() - offset < 16) {
/*  84 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */ 
/*     */     
/*  88 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PlaySoundEventEntity clone() {
/*  92 */     PlaySoundEventEntity copy = new PlaySoundEventEntity();
/*  93 */     copy.soundEventIndex = this.soundEventIndex;
/*  94 */     copy.networkId = this.networkId;
/*  95 */     copy.volumeModifier = this.volumeModifier;
/*  96 */     copy.pitchModifier = this.pitchModifier;
/*  97 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PlaySoundEventEntity other;
/* 103 */     if (this == obj) return true; 
/* 104 */     if (obj instanceof PlaySoundEventEntity) { other = (PlaySoundEventEntity)obj; } else { return false; }
/* 105 */      return (this.soundEventIndex == other.soundEventIndex && this.networkId == other.networkId && this.volumeModifier == other.volumeModifier && this.pitchModifier == other.pitchModifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     return Objects.hash(new Object[] { Integer.valueOf(this.soundEventIndex), Integer.valueOf(this.networkId), Float.valueOf(this.volumeModifier), Float.valueOf(this.pitchModifier) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\PlaySoundEventEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */