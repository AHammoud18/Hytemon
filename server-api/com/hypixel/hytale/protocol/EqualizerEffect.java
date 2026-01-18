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
/*     */ 
/*     */ public class EqualizerEffect
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 41;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 41;
/*     */   public static final int MAX_SIZE = 16384046;
/*     */   @Nullable
/*     */   public String id;
/*     */   public float lowGain;
/*     */   public float lowCutOff;
/*     */   public float lowMidGain;
/*     */   public float lowMidCenter;
/*     */   public float lowMidWidth;
/*     */   public float highMidGain;
/*     */   public float highMidCenter;
/*     */   public float highMidWidth;
/*     */   public float highGain;
/*     */   public float highCutOff;
/*     */   
/*     */   public EqualizerEffect() {}
/*     */   
/*     */   public EqualizerEffect(@Nullable String id, float lowGain, float lowCutOff, float lowMidGain, float lowMidCenter, float lowMidWidth, float highMidGain, float highMidCenter, float highMidWidth, float highGain, float highCutOff) {
/*  36 */     this.id = id;
/*  37 */     this.lowGain = lowGain;
/*  38 */     this.lowCutOff = lowCutOff;
/*  39 */     this.lowMidGain = lowMidGain;
/*  40 */     this.lowMidCenter = lowMidCenter;
/*  41 */     this.lowMidWidth = lowMidWidth;
/*  42 */     this.highMidGain = highMidGain;
/*  43 */     this.highMidCenter = highMidCenter;
/*  44 */     this.highMidWidth = highMidWidth;
/*  45 */     this.highGain = highGain;
/*  46 */     this.highCutOff = highCutOff;
/*     */   }
/*     */   
/*     */   public EqualizerEffect(@Nonnull EqualizerEffect other) {
/*  50 */     this.id = other.id;
/*  51 */     this.lowGain = other.lowGain;
/*  52 */     this.lowCutOff = other.lowCutOff;
/*  53 */     this.lowMidGain = other.lowMidGain;
/*  54 */     this.lowMidCenter = other.lowMidCenter;
/*  55 */     this.lowMidWidth = other.lowMidWidth;
/*  56 */     this.highMidGain = other.highMidGain;
/*  57 */     this.highMidCenter = other.highMidCenter;
/*  58 */     this.highMidWidth = other.highMidWidth;
/*  59 */     this.highGain = other.highGain;
/*  60 */     this.highCutOff = other.highCutOff;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EqualizerEffect deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     EqualizerEffect obj = new EqualizerEffect();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.lowGain = buf.getFloatLE(offset + 1);
/*  68 */     obj.lowCutOff = buf.getFloatLE(offset + 5);
/*  69 */     obj.lowMidGain = buf.getFloatLE(offset + 9);
/*  70 */     obj.lowMidCenter = buf.getFloatLE(offset + 13);
/*  71 */     obj.lowMidWidth = buf.getFloatLE(offset + 17);
/*  72 */     obj.highMidGain = buf.getFloatLE(offset + 21);
/*  73 */     obj.highMidCenter = buf.getFloatLE(offset + 25);
/*  74 */     obj.highMidWidth = buf.getFloatLE(offset + 29);
/*  75 */     obj.highGain = buf.getFloatLE(offset + 33);
/*  76 */     obj.highCutOff = buf.getFloatLE(offset + 37);
/*     */     
/*  78 */     int pos = offset + 41;
/*  79 */     if ((nullBits & 0x1) != 0) { int idLen = VarInt.peek(buf, pos);
/*  80 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  81 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  82 */       int idVarLen = VarInt.length(buf, pos);
/*  83 */       obj.id = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  84 */       pos += idVarLen + idLen; }
/*     */     
/*  86 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  90 */     byte nullBits = buf.getByte(offset);
/*  91 */     int pos = offset + 41;
/*  92 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  93 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  98 */     byte nullBits = 0;
/*  99 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 100 */     buf.writeByte(nullBits);
/*     */     
/* 102 */     buf.writeFloatLE(this.lowGain);
/* 103 */     buf.writeFloatLE(this.lowCutOff);
/* 104 */     buf.writeFloatLE(this.lowMidGain);
/* 105 */     buf.writeFloatLE(this.lowMidCenter);
/* 106 */     buf.writeFloatLE(this.lowMidWidth);
/* 107 */     buf.writeFloatLE(this.highMidGain);
/* 108 */     buf.writeFloatLE(this.highMidCenter);
/* 109 */     buf.writeFloatLE(this.highMidWidth);
/* 110 */     buf.writeFloatLE(this.highGain);
/* 111 */     buf.writeFloatLE(this.highCutOff);
/*     */     
/* 113 */     if (this.id != null) PacketIO.writeVarString(buf, this.id, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 118 */     int size = 41;
/* 119 */     if (this.id != null) size += PacketIO.stringSize(this.id);
/*     */     
/* 121 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 125 */     if (buffer.readableBytes() - offset < 41) {
/* 126 */       return ValidationResult.error("Buffer too small: expected at least 41 bytes");
/*     */     }
/*     */     
/* 129 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 131 */     int pos = offset + 41;
/*     */     
/* 133 */     if ((nullBits & 0x1) != 0) {
/* 134 */       int idLen = VarInt.peek(buffer, pos);
/* 135 */       if (idLen < 0) {
/* 136 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 138 */       if (idLen > 4096000) {
/* 139 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 141 */       pos += VarInt.length(buffer, pos);
/* 142 */       pos += idLen;
/* 143 */       if (pos > buffer.writerIndex()) {
/* 144 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/* 147 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EqualizerEffect clone() {
/* 151 */     EqualizerEffect copy = new EqualizerEffect();
/* 152 */     copy.id = this.id;
/* 153 */     copy.lowGain = this.lowGain;
/* 154 */     copy.lowCutOff = this.lowCutOff;
/* 155 */     copy.lowMidGain = this.lowMidGain;
/* 156 */     copy.lowMidCenter = this.lowMidCenter;
/* 157 */     copy.lowMidWidth = this.lowMidWidth;
/* 158 */     copy.highMidGain = this.highMidGain;
/* 159 */     copy.highMidCenter = this.highMidCenter;
/* 160 */     copy.highMidWidth = this.highMidWidth;
/* 161 */     copy.highGain = this.highGain;
/* 162 */     copy.highCutOff = this.highCutOff;
/* 163 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EqualizerEffect other;
/* 169 */     if (this == obj) return true; 
/* 170 */     if (obj instanceof EqualizerEffect) { other = (EqualizerEffect)obj; } else { return false; }
/* 171 */      return (Objects.equals(this.id, other.id) && this.lowGain == other.lowGain && this.lowCutOff == other.lowCutOff && this.lowMidGain == other.lowMidGain && this.lowMidCenter == other.lowMidCenter && this.lowMidWidth == other.lowMidWidth && this.highMidGain == other.highMidGain && this.highMidCenter == other.highMidCenter && this.highMidWidth == other.highMidWidth && this.highGain == other.highGain && this.highCutOff == other.highCutOff);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 176 */     return Objects.hash(new Object[] { this.id, Float.valueOf(this.lowGain), Float.valueOf(this.lowCutOff), Float.valueOf(this.lowMidGain), Float.valueOf(this.lowMidCenter), Float.valueOf(this.lowMidWidth), Float.valueOf(this.highMidGain), Float.valueOf(this.highMidCenter), Float.valueOf(this.highMidWidth), Float.valueOf(this.highGain), Float.valueOf(this.highCutOff) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EqualizerEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */