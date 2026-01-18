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
/*     */ public class WeatherParticle
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 16384018;
/*     */   @Nullable
/*     */   public String systemId;
/*     */   @Nullable
/*     */   public Color color;
/*     */   public float scale;
/*     */   public boolean isOvergroundOnly;
/*     */   public float positionOffsetMultiplier;
/*     */   
/*     */   public WeatherParticle() {}
/*     */   
/*     */   public WeatherParticle(@Nullable String systemId, @Nullable Color color, float scale, boolean isOvergroundOnly, float positionOffsetMultiplier) {
/*  30 */     this.systemId = systemId;
/*  31 */     this.color = color;
/*  32 */     this.scale = scale;
/*  33 */     this.isOvergroundOnly = isOvergroundOnly;
/*  34 */     this.positionOffsetMultiplier = positionOffsetMultiplier;
/*     */   }
/*     */   
/*     */   public WeatherParticle(@Nonnull WeatherParticle other) {
/*  38 */     this.systemId = other.systemId;
/*  39 */     this.color = other.color;
/*  40 */     this.scale = other.scale;
/*  41 */     this.isOvergroundOnly = other.isOvergroundOnly;
/*  42 */     this.positionOffsetMultiplier = other.positionOffsetMultiplier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static WeatherParticle deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     WeatherParticle obj = new WeatherParticle();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     if ((nullBits & 0x2) != 0) obj.color = Color.deserialize(buf, offset + 1); 
/*  50 */     obj.scale = buf.getFloatLE(offset + 4);
/*  51 */     obj.isOvergroundOnly = (buf.getByte(offset + 8) != 0);
/*  52 */     obj.positionOffsetMultiplier = buf.getFloatLE(offset + 9);
/*     */     
/*  54 */     int pos = offset + 13;
/*  55 */     if ((nullBits & 0x1) != 0) { int systemIdLen = VarInt.peek(buf, pos);
/*  56 */       if (systemIdLen < 0) throw ProtocolException.negativeLength("SystemId", systemIdLen); 
/*  57 */       if (systemIdLen > 4096000) throw ProtocolException.stringTooLong("SystemId", systemIdLen, 4096000); 
/*  58 */       int systemIdVarLen = VarInt.length(buf, pos);
/*  59 */       obj.systemId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  60 */       pos += systemIdVarLen + systemIdLen; }
/*     */     
/*  62 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     int pos = offset + 13;
/*  68 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  69 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.systemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     if (this.color != null) nullBits = (byte)(nullBits | 0x2); 
/*  77 */     buf.writeByte(nullBits);
/*     */     
/*  79 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(3); }
/*  80 */      buf.writeFloatLE(this.scale);
/*  81 */     buf.writeByte(this.isOvergroundOnly ? 1 : 0);
/*  82 */     buf.writeFloatLE(this.positionOffsetMultiplier);
/*     */     
/*  84 */     if (this.systemId != null) PacketIO.writeVarString(buf, this.systemId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 13;
/*  90 */     if (this.systemId != null) size += PacketIO.stringSize(this.systemId);
/*     */     
/*  92 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  96 */     if (buffer.readableBytes() - offset < 13) {
/*  97 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 100 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 102 */     int pos = offset + 13;
/*     */     
/* 104 */     if ((nullBits & 0x1) != 0) {
/* 105 */       int systemIdLen = VarInt.peek(buffer, pos);
/* 106 */       if (systemIdLen < 0) {
/* 107 */         return ValidationResult.error("Invalid string length for SystemId");
/*     */       }
/* 109 */       if (systemIdLen > 4096000) {
/* 110 */         return ValidationResult.error("SystemId exceeds max length 4096000");
/*     */       }
/* 112 */       pos += VarInt.length(buffer, pos);
/* 113 */       pos += systemIdLen;
/* 114 */       if (pos > buffer.writerIndex()) {
/* 115 */         return ValidationResult.error("Buffer overflow reading SystemId");
/*     */       }
/*     */     } 
/* 118 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public WeatherParticle clone() {
/* 122 */     WeatherParticle copy = new WeatherParticle();
/* 123 */     copy.systemId = this.systemId;
/* 124 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 125 */     copy.scale = this.scale;
/* 126 */     copy.isOvergroundOnly = this.isOvergroundOnly;
/* 127 */     copy.positionOffsetMultiplier = this.positionOffsetMultiplier;
/* 128 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     WeatherParticle other;
/* 134 */     if (this == obj) return true; 
/* 135 */     if (obj instanceof WeatherParticle) { other = (WeatherParticle)obj; } else { return false; }
/* 136 */      return (Objects.equals(this.systemId, other.systemId) && Objects.equals(this.color, other.color) && this.scale == other.scale && this.isOvergroundOnly == other.isOvergroundOnly && this.positionOffsetMultiplier == other.positionOffsetMultiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return Objects.hash(new Object[] { this.systemId, this.color, Float.valueOf(this.scale), Boolean.valueOf(this.isOvergroundOnly), Float.valueOf(this.positionOffsetMultiplier) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\WeatherParticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */