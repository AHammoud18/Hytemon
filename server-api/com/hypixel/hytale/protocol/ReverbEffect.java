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
/*     */ public class ReverbEffect
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 54;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 54;
/*     */   public static final int MAX_SIZE = 16384059;
/*     */   @Nullable
/*     */   public String id;
/*     */   public float dryGain;
/*     */   public float modalDensity;
/*     */   public float diffusion;
/*     */   public float gain;
/*     */   public float highFrequencyGain;
/*     */   public float decayTime;
/*     */   public float highFrequencyDecayRatio;
/*     */   public float reflectionGain;
/*     */   public float reflectionDelay;
/*     */   public float lateReverbGain;
/*     */   public float lateReverbDelay;
/*     */   public float roomRolloffFactor;
/*     */   public float airAbsorptionHighFrequencyGain;
/*     */   public boolean limitDecayHighFrequency;
/*     */   
/*     */   public ReverbEffect() {}
/*     */   
/*     */   public ReverbEffect(@Nullable String id, float dryGain, float modalDensity, float diffusion, float gain, float highFrequencyGain, float decayTime, float highFrequencyDecayRatio, float reflectionGain, float reflectionDelay, float lateReverbGain, float lateReverbDelay, float roomRolloffFactor, float airAbsorptionHighFrequencyGain, boolean limitDecayHighFrequency) {
/*  40 */     this.id = id;
/*  41 */     this.dryGain = dryGain;
/*  42 */     this.modalDensity = modalDensity;
/*  43 */     this.diffusion = diffusion;
/*  44 */     this.gain = gain;
/*  45 */     this.highFrequencyGain = highFrequencyGain;
/*  46 */     this.decayTime = decayTime;
/*  47 */     this.highFrequencyDecayRatio = highFrequencyDecayRatio;
/*  48 */     this.reflectionGain = reflectionGain;
/*  49 */     this.reflectionDelay = reflectionDelay;
/*  50 */     this.lateReverbGain = lateReverbGain;
/*  51 */     this.lateReverbDelay = lateReverbDelay;
/*  52 */     this.roomRolloffFactor = roomRolloffFactor;
/*  53 */     this.airAbsorptionHighFrequencyGain = airAbsorptionHighFrequencyGain;
/*  54 */     this.limitDecayHighFrequency = limitDecayHighFrequency;
/*     */   }
/*     */   
/*     */   public ReverbEffect(@Nonnull ReverbEffect other) {
/*  58 */     this.id = other.id;
/*  59 */     this.dryGain = other.dryGain;
/*  60 */     this.modalDensity = other.modalDensity;
/*  61 */     this.diffusion = other.diffusion;
/*  62 */     this.gain = other.gain;
/*  63 */     this.highFrequencyGain = other.highFrequencyGain;
/*  64 */     this.decayTime = other.decayTime;
/*  65 */     this.highFrequencyDecayRatio = other.highFrequencyDecayRatio;
/*  66 */     this.reflectionGain = other.reflectionGain;
/*  67 */     this.reflectionDelay = other.reflectionDelay;
/*  68 */     this.lateReverbGain = other.lateReverbGain;
/*  69 */     this.lateReverbDelay = other.lateReverbDelay;
/*  70 */     this.roomRolloffFactor = other.roomRolloffFactor;
/*  71 */     this.airAbsorptionHighFrequencyGain = other.airAbsorptionHighFrequencyGain;
/*  72 */     this.limitDecayHighFrequency = other.limitDecayHighFrequency;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ReverbEffect deserialize(@Nonnull ByteBuf buf, int offset) {
/*  77 */     ReverbEffect obj = new ReverbEffect();
/*  78 */     byte nullBits = buf.getByte(offset);
/*  79 */     obj.dryGain = buf.getFloatLE(offset + 1);
/*  80 */     obj.modalDensity = buf.getFloatLE(offset + 5);
/*  81 */     obj.diffusion = buf.getFloatLE(offset + 9);
/*  82 */     obj.gain = buf.getFloatLE(offset + 13);
/*  83 */     obj.highFrequencyGain = buf.getFloatLE(offset + 17);
/*  84 */     obj.decayTime = buf.getFloatLE(offset + 21);
/*  85 */     obj.highFrequencyDecayRatio = buf.getFloatLE(offset + 25);
/*  86 */     obj.reflectionGain = buf.getFloatLE(offset + 29);
/*  87 */     obj.reflectionDelay = buf.getFloatLE(offset + 33);
/*  88 */     obj.lateReverbGain = buf.getFloatLE(offset + 37);
/*  89 */     obj.lateReverbDelay = buf.getFloatLE(offset + 41);
/*  90 */     obj.roomRolloffFactor = buf.getFloatLE(offset + 45);
/*  91 */     obj.airAbsorptionHighFrequencyGain = buf.getFloatLE(offset + 49);
/*  92 */     obj.limitDecayHighFrequency = (buf.getByte(offset + 53) != 0);
/*     */     
/*  94 */     int pos = offset + 54;
/*  95 */     if ((nullBits & 0x1) != 0) { int idLen = VarInt.peek(buf, pos);
/*  96 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  97 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  98 */       int idVarLen = VarInt.length(buf, pos);
/*  99 */       obj.id = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/* 100 */       pos += idVarLen + idLen; }
/*     */     
/* 102 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 106 */     byte nullBits = buf.getByte(offset);
/* 107 */     int pos = offset + 54;
/* 108 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/* 109 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 114 */     byte nullBits = 0;
/* 115 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 116 */     buf.writeByte(nullBits);
/*     */     
/* 118 */     buf.writeFloatLE(this.dryGain);
/* 119 */     buf.writeFloatLE(this.modalDensity);
/* 120 */     buf.writeFloatLE(this.diffusion);
/* 121 */     buf.writeFloatLE(this.gain);
/* 122 */     buf.writeFloatLE(this.highFrequencyGain);
/* 123 */     buf.writeFloatLE(this.decayTime);
/* 124 */     buf.writeFloatLE(this.highFrequencyDecayRatio);
/* 125 */     buf.writeFloatLE(this.reflectionGain);
/* 126 */     buf.writeFloatLE(this.reflectionDelay);
/* 127 */     buf.writeFloatLE(this.lateReverbGain);
/* 128 */     buf.writeFloatLE(this.lateReverbDelay);
/* 129 */     buf.writeFloatLE(this.roomRolloffFactor);
/* 130 */     buf.writeFloatLE(this.airAbsorptionHighFrequencyGain);
/* 131 */     buf.writeByte(this.limitDecayHighFrequency ? 1 : 0);
/*     */     
/* 133 */     if (this.id != null) PacketIO.writeVarString(buf, this.id, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 138 */     int size = 54;
/* 139 */     if (this.id != null) size += PacketIO.stringSize(this.id);
/*     */     
/* 141 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 145 */     if (buffer.readableBytes() - offset < 54) {
/* 146 */       return ValidationResult.error("Buffer too small: expected at least 54 bytes");
/*     */     }
/*     */     
/* 149 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 151 */     int pos = offset + 54;
/*     */     
/* 153 */     if ((nullBits & 0x1) != 0) {
/* 154 */       int idLen = VarInt.peek(buffer, pos);
/* 155 */       if (idLen < 0) {
/* 156 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 158 */       if (idLen > 4096000) {
/* 159 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 161 */       pos += VarInt.length(buffer, pos);
/* 162 */       pos += idLen;
/* 163 */       if (pos > buffer.writerIndex()) {
/* 164 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/* 167 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ReverbEffect clone() {
/* 171 */     ReverbEffect copy = new ReverbEffect();
/* 172 */     copy.id = this.id;
/* 173 */     copy.dryGain = this.dryGain;
/* 174 */     copy.modalDensity = this.modalDensity;
/* 175 */     copy.diffusion = this.diffusion;
/* 176 */     copy.gain = this.gain;
/* 177 */     copy.highFrequencyGain = this.highFrequencyGain;
/* 178 */     copy.decayTime = this.decayTime;
/* 179 */     copy.highFrequencyDecayRatio = this.highFrequencyDecayRatio;
/* 180 */     copy.reflectionGain = this.reflectionGain;
/* 181 */     copy.reflectionDelay = this.reflectionDelay;
/* 182 */     copy.lateReverbGain = this.lateReverbGain;
/* 183 */     copy.lateReverbDelay = this.lateReverbDelay;
/* 184 */     copy.roomRolloffFactor = this.roomRolloffFactor;
/* 185 */     copy.airAbsorptionHighFrequencyGain = this.airAbsorptionHighFrequencyGain;
/* 186 */     copy.limitDecayHighFrequency = this.limitDecayHighFrequency;
/* 187 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ReverbEffect other;
/* 193 */     if (this == obj) return true; 
/* 194 */     if (obj instanceof ReverbEffect) { other = (ReverbEffect)obj; } else { return false; }
/* 195 */      return (Objects.equals(this.id, other.id) && this.dryGain == other.dryGain && this.modalDensity == other.modalDensity && this.diffusion == other.diffusion && this.gain == other.gain && this.highFrequencyGain == other.highFrequencyGain && this.decayTime == other.decayTime && this.highFrequencyDecayRatio == other.highFrequencyDecayRatio && this.reflectionGain == other.reflectionGain && this.reflectionDelay == other.reflectionDelay && this.lateReverbGain == other.lateReverbGain && this.lateReverbDelay == other.lateReverbDelay && this.roomRolloffFactor == other.roomRolloffFactor && this.airAbsorptionHighFrequencyGain == other.airAbsorptionHighFrequencyGain && this.limitDecayHighFrequency == other.limitDecayHighFrequency);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     return Objects.hash(new Object[] { this.id, Float.valueOf(this.dryGain), Float.valueOf(this.modalDensity), Float.valueOf(this.diffusion), Float.valueOf(this.gain), Float.valueOf(this.highFrequencyGain), Float.valueOf(this.decayTime), Float.valueOf(this.highFrequencyDecayRatio), Float.valueOf(this.reflectionGain), Float.valueOf(this.reflectionDelay), Float.valueOf(this.lateReverbGain), Float.valueOf(this.lateReverbDelay), Float.valueOf(this.roomRolloffFactor), Float.valueOf(this.airAbsorptionHighFrequencyGain), Boolean.valueOf(this.limitDecayHighFrequency) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ReverbEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */