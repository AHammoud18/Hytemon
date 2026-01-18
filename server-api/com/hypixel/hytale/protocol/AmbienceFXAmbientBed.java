/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AmbienceFXAmbientBed {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 16384011;
/*     */   @Nullable
/*     */   public String track;
/*     */   public float volume;
/*     */   @Nonnull
/*  22 */   public AmbienceTransitionSpeed transitionSpeed = AmbienceTransitionSpeed.Default;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AmbienceFXAmbientBed(@Nullable String track, float volume, @Nonnull AmbienceTransitionSpeed transitionSpeed) {
/*  28 */     this.track = track;
/*  29 */     this.volume = volume;
/*  30 */     this.transitionSpeed = transitionSpeed;
/*     */   }
/*     */   
/*     */   public AmbienceFXAmbientBed(@Nonnull AmbienceFXAmbientBed other) {
/*  34 */     this.track = other.track;
/*  35 */     this.volume = other.volume;
/*  36 */     this.transitionSpeed = other.transitionSpeed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AmbienceFXAmbientBed deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     AmbienceFXAmbientBed obj = new AmbienceFXAmbientBed();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.volume = buf.getFloatLE(offset + 1);
/*  44 */     obj.transitionSpeed = AmbienceTransitionSpeed.fromValue(buf.getByte(offset + 5));
/*     */     
/*  46 */     int pos = offset + 6;
/*  47 */     if ((nullBits & 0x1) != 0) { int trackLen = VarInt.peek(buf, pos);
/*  48 */       if (trackLen < 0) throw ProtocolException.negativeLength("Track", trackLen); 
/*  49 */       if (trackLen > 4096000) throw ProtocolException.stringTooLong("Track", trackLen, 4096000); 
/*  50 */       int trackVarLen = VarInt.length(buf, pos);
/*  51 */       obj.track = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += trackVarLen + trackLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 6;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.track != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeFloatLE(this.volume);
/*  71 */     buf.writeByte(this.transitionSpeed.getValue());
/*     */     
/*  73 */     if (this.track != null) PacketIO.writeVarString(buf, this.track, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 6;
/*  79 */     if (this.track != null) size += PacketIO.stringSize(this.track);
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 6) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 6;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       int trackLen = VarInt.peek(buffer, pos);
/*  95 */       if (trackLen < 0) {
/*  96 */         return ValidationResult.error("Invalid string length for Track");
/*     */       }
/*  98 */       if (trackLen > 4096000) {
/*  99 */         return ValidationResult.error("Track exceeds max length 4096000");
/*     */       }
/* 101 */       pos += VarInt.length(buffer, pos);
/* 102 */       pos += trackLen;
/* 103 */       if (pos > buffer.writerIndex()) {
/* 104 */         return ValidationResult.error("Buffer overflow reading Track");
/*     */       }
/*     */     } 
/* 107 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AmbienceFXAmbientBed clone() {
/* 111 */     AmbienceFXAmbientBed copy = new AmbienceFXAmbientBed();
/* 112 */     copy.track = this.track;
/* 113 */     copy.volume = this.volume;
/* 114 */     copy.transitionSpeed = this.transitionSpeed;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AmbienceFXAmbientBed other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof AmbienceFXAmbientBed) { other = (AmbienceFXAmbientBed)obj; } else { return false; }
/* 123 */      return (Objects.equals(this.track, other.track) && this.volume == other.volume && Objects.equals(this.transitionSpeed, other.transitionSpeed));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return Objects.hash(new Object[] { this.track, Float.valueOf(this.volume), this.transitionSpeed });
/*     */   }
/*     */   
/*     */   public AmbienceFXAmbientBed() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AmbienceFXAmbientBed.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */