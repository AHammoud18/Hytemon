/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionCooldown {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 8;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 16;
/*     */   public static final int MAX_SIZE = 32768026;
/*     */   @Nullable
/*     */   public String cooldownId;
/*     */   public float cooldown;
/*     */   public boolean clickBypass;
/*     */   @Nullable
/*     */   public float[] chargeTimes;
/*     */   public boolean skipCooldownReset;
/*     */   public boolean interruptRecharge;
/*     */   
/*     */   public InteractionCooldown() {}
/*     */   
/*     */   public InteractionCooldown(@Nullable String cooldownId, float cooldown, boolean clickBypass, @Nullable float[] chargeTimes, boolean skipCooldownReset, boolean interruptRecharge) {
/*  31 */     this.cooldownId = cooldownId;
/*  32 */     this.cooldown = cooldown;
/*  33 */     this.clickBypass = clickBypass;
/*  34 */     this.chargeTimes = chargeTimes;
/*  35 */     this.skipCooldownReset = skipCooldownReset;
/*  36 */     this.interruptRecharge = interruptRecharge;
/*     */   }
/*     */   
/*     */   public InteractionCooldown(@Nonnull InteractionCooldown other) {
/*  40 */     this.cooldownId = other.cooldownId;
/*  41 */     this.cooldown = other.cooldown;
/*  42 */     this.clickBypass = other.clickBypass;
/*  43 */     this.chargeTimes = other.chargeTimes;
/*  44 */     this.skipCooldownReset = other.skipCooldownReset;
/*  45 */     this.interruptRecharge = other.interruptRecharge;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionCooldown deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     InteractionCooldown obj = new InteractionCooldown();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.cooldown = buf.getFloatLE(offset + 1);
/*  53 */     obj.clickBypass = (buf.getByte(offset + 5) != 0);
/*  54 */     obj.skipCooldownReset = (buf.getByte(offset + 6) != 0);
/*  55 */     obj.interruptRecharge = (buf.getByte(offset + 7) != 0);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 16 + buf.getIntLE(offset + 8);
/*  59 */       int cooldownIdLen = VarInt.peek(buf, varPos0);
/*  60 */       if (cooldownIdLen < 0) throw ProtocolException.negativeLength("CooldownId", cooldownIdLen); 
/*  61 */       if (cooldownIdLen > 4096000) throw ProtocolException.stringTooLong("CooldownId", cooldownIdLen, 4096000); 
/*  62 */       obj.cooldownId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 16 + buf.getIntLE(offset + 12);
/*  66 */       int chargeTimesCount = VarInt.peek(buf, varPos1);
/*  67 */       if (chargeTimesCount < 0) throw ProtocolException.negativeLength("ChargeTimes", chargeTimesCount); 
/*  68 */       if (chargeTimesCount > 4096000) throw ProtocolException.arrayTooLong("ChargeTimes", chargeTimesCount, 4096000); 
/*  69 */       int varIntLen = VarInt.length(buf, varPos1);
/*  70 */       if ((varPos1 + varIntLen) + chargeTimesCount * 4L > buf.readableBytes())
/*  71 */         throw ProtocolException.bufferTooSmall("ChargeTimes", varPos1 + varIntLen + chargeTimesCount * 4, buf.readableBytes()); 
/*  72 */       obj.chargeTimes = new float[chargeTimesCount];
/*  73 */       for (int i = 0; i < chargeTimesCount; i++) {
/*  74 */         obj.chargeTimes[i] = buf.getFloatLE(varPos1 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/*  78 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  82 */     byte nullBits = buf.getByte(offset);
/*  83 */     int maxEnd = 16;
/*  84 */     if ((nullBits & 0x1) != 0) {
/*  85 */       int fieldOffset0 = buf.getIntLE(offset + 8);
/*  86 */       int pos0 = offset + 16 + fieldOffset0;
/*  87 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  88 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  90 */     if ((nullBits & 0x2) != 0) {
/*  91 */       int fieldOffset1 = buf.getIntLE(offset + 12);
/*  92 */       int pos1 = offset + 16 + fieldOffset1;
/*  93 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/*  94 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  96 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 101 */     int startPos = buf.writerIndex();
/* 102 */     byte nullBits = 0;
/* 103 */     if (this.cooldownId != null) nullBits = (byte)(nullBits | 0x1); 
/* 104 */     if (this.chargeTimes != null) nullBits = (byte)(nullBits | 0x2); 
/* 105 */     buf.writeByte(nullBits);
/*     */     
/* 107 */     buf.writeFloatLE(this.cooldown);
/* 108 */     buf.writeByte(this.clickBypass ? 1 : 0);
/* 109 */     buf.writeByte(this.skipCooldownReset ? 1 : 0);
/* 110 */     buf.writeByte(this.interruptRecharge ? 1 : 0);
/*     */     
/* 112 */     int cooldownIdOffsetSlot = buf.writerIndex();
/* 113 */     buf.writeIntLE(0);
/* 114 */     int chargeTimesOffsetSlot = buf.writerIndex();
/* 115 */     buf.writeIntLE(0);
/*     */     
/* 117 */     int varBlockStart = buf.writerIndex();
/* 118 */     if (this.cooldownId != null) {
/* 119 */       buf.setIntLE(cooldownIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 120 */       PacketIO.writeVarString(buf, this.cooldownId, 4096000);
/*     */     } else {
/* 122 */       buf.setIntLE(cooldownIdOffsetSlot, -1);
/*     */     } 
/* 124 */     if (this.chargeTimes != null) {
/* 125 */       buf.setIntLE(chargeTimesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 126 */       if (this.chargeTimes.length > 4096000) throw ProtocolException.arrayTooLong("ChargeTimes", this.chargeTimes.length, 4096000);  VarInt.write(buf, this.chargeTimes.length); for (float item : this.chargeTimes) buf.writeFloatLE(item); 
/*     */     } else {
/* 128 */       buf.setIntLE(chargeTimesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 134 */     int size = 16;
/* 135 */     if (this.cooldownId != null) size += PacketIO.stringSize(this.cooldownId); 
/* 136 */     if (this.chargeTimes != null) size += VarInt.size(this.chargeTimes.length) + this.chargeTimes.length * 4;
/*     */     
/* 138 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 142 */     if (buffer.readableBytes() - offset < 16) {
/* 143 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */     
/* 146 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 149 */     if ((nullBits & 0x1) != 0) {
/* 150 */       int cooldownIdOffset = buffer.getIntLE(offset + 8);
/* 151 */       if (cooldownIdOffset < 0) {
/* 152 */         return ValidationResult.error("Invalid offset for CooldownId");
/*     */       }
/* 154 */       int pos = offset + 16 + cooldownIdOffset;
/* 155 */       if (pos >= buffer.writerIndex()) {
/* 156 */         return ValidationResult.error("Offset out of bounds for CooldownId");
/*     */       }
/* 158 */       int cooldownIdLen = VarInt.peek(buffer, pos);
/* 159 */       if (cooldownIdLen < 0) {
/* 160 */         return ValidationResult.error("Invalid string length for CooldownId");
/*     */       }
/* 162 */       if (cooldownIdLen > 4096000) {
/* 163 */         return ValidationResult.error("CooldownId exceeds max length 4096000");
/*     */       }
/* 165 */       pos += VarInt.length(buffer, pos);
/* 166 */       pos += cooldownIdLen;
/* 167 */       if (pos > buffer.writerIndex()) {
/* 168 */         return ValidationResult.error("Buffer overflow reading CooldownId");
/*     */       }
/*     */     } 
/*     */     
/* 172 */     if ((nullBits & 0x2) != 0) {
/* 173 */       int chargeTimesOffset = buffer.getIntLE(offset + 12);
/* 174 */       if (chargeTimesOffset < 0) {
/* 175 */         return ValidationResult.error("Invalid offset for ChargeTimes");
/*     */       }
/* 177 */       int pos = offset + 16 + chargeTimesOffset;
/* 178 */       if (pos >= buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Offset out of bounds for ChargeTimes");
/*     */       }
/* 181 */       int chargeTimesCount = VarInt.peek(buffer, pos);
/* 182 */       if (chargeTimesCount < 0) {
/* 183 */         return ValidationResult.error("Invalid array count for ChargeTimes");
/*     */       }
/* 185 */       if (chargeTimesCount > 4096000) {
/* 186 */         return ValidationResult.error("ChargeTimes exceeds max length 4096000");
/*     */       }
/* 188 */       pos += VarInt.length(buffer, pos);
/* 189 */       pos += chargeTimesCount * 4;
/* 190 */       if (pos > buffer.writerIndex()) {
/* 191 */         return ValidationResult.error("Buffer overflow reading ChargeTimes");
/*     */       }
/*     */     } 
/* 194 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionCooldown clone() {
/* 198 */     InteractionCooldown copy = new InteractionCooldown();
/* 199 */     copy.cooldownId = this.cooldownId;
/* 200 */     copy.cooldown = this.cooldown;
/* 201 */     copy.clickBypass = this.clickBypass;
/* 202 */     copy.chargeTimes = (this.chargeTimes != null) ? Arrays.copyOf(this.chargeTimes, this.chargeTimes.length) : null;
/* 203 */     copy.skipCooldownReset = this.skipCooldownReset;
/* 204 */     copy.interruptRecharge = this.interruptRecharge;
/* 205 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionCooldown other;
/* 211 */     if (this == obj) return true; 
/* 212 */     if (obj instanceof InteractionCooldown) { other = (InteractionCooldown)obj; } else { return false; }
/* 213 */      return (Objects.equals(this.cooldownId, other.cooldownId) && this.cooldown == other.cooldown && this.clickBypass == other.clickBypass && Arrays.equals(this.chargeTimes, other.chargeTimes) && this.skipCooldownReset == other.skipCooldownReset && this.interruptRecharge == other.interruptRecharge);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 218 */     int result = 1;
/* 219 */     result = 31 * result + Objects.hashCode(this.cooldownId);
/* 220 */     result = 31 * result + Float.hashCode(this.cooldown);
/* 221 */     result = 31 * result + Boolean.hashCode(this.clickBypass);
/* 222 */     result = 31 * result + Arrays.hashCode(this.chargeTimes);
/* 223 */     result = 31 * result + Boolean.hashCode(this.skipCooldownReset);
/* 224 */     result = 31 * result + Boolean.hashCode(this.interruptRecharge);
/* 225 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionCooldown.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */