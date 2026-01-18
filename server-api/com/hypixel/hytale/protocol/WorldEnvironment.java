/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldEnvironment
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 16;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public WorldEnvironment(@Nullable String id, @Nullable Color waterTint, @Nullable Map<Integer, FluidParticle> fluidParticles, @Nullable int[] tagIndexes) {
/*  29 */     this.id = id;
/*  30 */     this.waterTint = waterTint;
/*  31 */     this.fluidParticles = fluidParticles;
/*  32 */     this.tagIndexes = tagIndexes; } @Nullable
/*     */   public String id; @Nullable
/*     */   public Color waterTint; @Nullable
/*     */   public Map<Integer, FluidParticle> fluidParticles; @Nullable
/*  36 */   public int[] tagIndexes; public WorldEnvironment() {} public WorldEnvironment(@Nonnull WorldEnvironment other) { this.id = other.id;
/*  37 */     this.waterTint = other.waterTint;
/*  38 */     this.fluidParticles = other.fluidParticles;
/*  39 */     this.tagIndexes = other.tagIndexes; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static WorldEnvironment deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     WorldEnvironment obj = new WorldEnvironment();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     if ((nullBits & 0x2) != 0) obj.waterTint = Color.deserialize(buf, offset + 1);
/*     */     
/*  48 */     if ((nullBits & 0x1) != 0) {
/*  49 */       int varPos0 = offset + 16 + buf.getIntLE(offset + 4);
/*  50 */       int idLen = VarInt.peek(buf, varPos0);
/*  51 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  52 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  53 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  55 */     if ((nullBits & 0x4) != 0) {
/*  56 */       int varPos1 = offset + 16 + buf.getIntLE(offset + 8);
/*  57 */       int fluidParticlesCount = VarInt.peek(buf, varPos1);
/*  58 */       if (fluidParticlesCount < 0) throw ProtocolException.negativeLength("FluidParticles", fluidParticlesCount); 
/*  59 */       if (fluidParticlesCount > 4096000) throw ProtocolException.dictionaryTooLarge("FluidParticles", fluidParticlesCount, 4096000); 
/*  60 */       int varIntLen = VarInt.length(buf, varPos1);
/*  61 */       obj.fluidParticles = new HashMap<>(fluidParticlesCount);
/*  62 */       int dictPos = varPos1 + varIntLen;
/*  63 */       for (int i = 0; i < fluidParticlesCount; i++) {
/*  64 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  65 */         FluidParticle val = FluidParticle.deserialize(buf, dictPos);
/*  66 */         dictPos += FluidParticle.computeBytesConsumed(buf, dictPos);
/*  67 */         if (obj.fluidParticles.put(Integer.valueOf(key), val) != null)
/*  68 */           throw ProtocolException.duplicateKey("fluidParticles", Integer.valueOf(key)); 
/*     */       } 
/*     */     } 
/*  71 */     if ((nullBits & 0x8) != 0) {
/*  72 */       int varPos2 = offset + 16 + buf.getIntLE(offset + 12);
/*  73 */       int tagIndexesCount = VarInt.peek(buf, varPos2);
/*  74 */       if (tagIndexesCount < 0) throw ProtocolException.negativeLength("TagIndexes", tagIndexesCount); 
/*  75 */       if (tagIndexesCount > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", tagIndexesCount, 4096000); 
/*  76 */       int varIntLen = VarInt.length(buf, varPos2);
/*  77 */       if ((varPos2 + varIntLen) + tagIndexesCount * 4L > buf.readableBytes())
/*  78 */         throw ProtocolException.bufferTooSmall("TagIndexes", varPos2 + varIntLen + tagIndexesCount * 4, buf.readableBytes()); 
/*  79 */       obj.tagIndexes = new int[tagIndexesCount];
/*  80 */       for (int i = 0; i < tagIndexesCount; i++) {
/*  81 */         obj.tagIndexes[i] = buf.getIntLE(varPos2 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  89 */     byte nullBits = buf.getByte(offset);
/*  90 */     int maxEnd = 16;
/*  91 */     if ((nullBits & 0x1) != 0) {
/*  92 */       int fieldOffset0 = buf.getIntLE(offset + 4);
/*  93 */       int pos0 = offset + 16 + fieldOffset0;
/*  94 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  95 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  97 */     if ((nullBits & 0x4) != 0) {
/*  98 */       int fieldOffset1 = buf.getIntLE(offset + 8);
/*  99 */       int pos1 = offset + 16 + fieldOffset1;
/* 100 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 101 */       for (int i = 0; i < dictLen; ) { pos1 += 4; pos1 += FluidParticle.computeBytesConsumed(buf, pos1); i++; }
/* 102 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 104 */     if ((nullBits & 0x8) != 0) {
/* 105 */       int fieldOffset2 = buf.getIntLE(offset + 12);
/* 106 */       int pos2 = offset + 16 + fieldOffset2;
/* 107 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 4;
/* 108 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 110 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 115 */     int startPos = buf.writerIndex();
/* 116 */     byte nullBits = 0;
/* 117 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 118 */     if (this.waterTint != null) nullBits = (byte)(nullBits | 0x2); 
/* 119 */     if (this.fluidParticles != null) nullBits = (byte)(nullBits | 0x4); 
/* 120 */     if (this.tagIndexes != null) nullBits = (byte)(nullBits | 0x8); 
/* 121 */     buf.writeByte(nullBits);
/*     */     
/* 123 */     if (this.waterTint != null) { this.waterTint.serialize(buf); } else { buf.writeZero(3); }
/*     */     
/* 125 */     int idOffsetSlot = buf.writerIndex();
/* 126 */     buf.writeIntLE(0);
/* 127 */     int fluidParticlesOffsetSlot = buf.writerIndex();
/* 128 */     buf.writeIntLE(0);
/* 129 */     int tagIndexesOffsetSlot = buf.writerIndex();
/* 130 */     buf.writeIntLE(0);
/*     */     
/* 132 */     int varBlockStart = buf.writerIndex();
/* 133 */     if (this.id != null) {
/* 134 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 135 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 137 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 139 */     if (this.fluidParticles != null)
/* 140 */     { buf.setIntLE(fluidParticlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 141 */       if (this.fluidParticles.size() > 4096000) throw ProtocolException.dictionaryTooLarge("FluidParticles", this.fluidParticles.size(), 4096000);  VarInt.write(buf, this.fluidParticles.size()); for (Map.Entry<Integer, FluidParticle> e : this.fluidParticles.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((FluidParticle)e.getValue()).serialize(buf); }
/*     */        }
/* 143 */     else { buf.setIntLE(fluidParticlesOffsetSlot, -1); }
/*     */     
/* 145 */     if (this.tagIndexes != null) {
/* 146 */       buf.setIntLE(tagIndexesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 147 */       if (this.tagIndexes.length > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", this.tagIndexes.length, 4096000);  VarInt.write(buf, this.tagIndexes.length); for (int item : this.tagIndexes) buf.writeIntLE(item); 
/*     */     } else {
/* 149 */       buf.setIntLE(tagIndexesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 155 */     int size = 16;
/* 156 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 157 */     if (this.fluidParticles != null) {
/* 158 */       int fluidParticlesSize = 0;
/* 159 */       for (Map.Entry<Integer, FluidParticle> kvp : this.fluidParticles.entrySet()) fluidParticlesSize += 4 + ((FluidParticle)kvp.getValue()).computeSize(); 
/* 160 */       size += VarInt.size(this.fluidParticles.size()) + fluidParticlesSize;
/*     */     } 
/* 162 */     if (this.tagIndexes != null) size += VarInt.size(this.tagIndexes.length) + this.tagIndexes.length * 4;
/*     */     
/* 164 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 168 */     if (buffer.readableBytes() - offset < 16) {
/* 169 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */     
/* 172 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 175 */     if ((nullBits & 0x1) != 0) {
/* 176 */       int idOffset = buffer.getIntLE(offset + 4);
/* 177 */       if (idOffset < 0) {
/* 178 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 180 */       int pos = offset + 16 + idOffset;
/* 181 */       if (pos >= buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 184 */       int idLen = VarInt.peek(buffer, pos);
/* 185 */       if (idLen < 0) {
/* 186 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 188 */       if (idLen > 4096000) {
/* 189 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 191 */       pos += VarInt.length(buffer, pos);
/* 192 */       pos += idLen;
/* 193 */       if (pos > buffer.writerIndex()) {
/* 194 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 198 */     if ((nullBits & 0x4) != 0) {
/* 199 */       int fluidParticlesOffset = buffer.getIntLE(offset + 8);
/* 200 */       if (fluidParticlesOffset < 0) {
/* 201 */         return ValidationResult.error("Invalid offset for FluidParticles");
/*     */       }
/* 203 */       int pos = offset + 16 + fluidParticlesOffset;
/* 204 */       if (pos >= buffer.writerIndex()) {
/* 205 */         return ValidationResult.error("Offset out of bounds for FluidParticles");
/*     */       }
/* 207 */       int fluidParticlesCount = VarInt.peek(buffer, pos);
/* 208 */       if (fluidParticlesCount < 0) {
/* 209 */         return ValidationResult.error("Invalid dictionary count for FluidParticles");
/*     */       }
/* 211 */       if (fluidParticlesCount > 4096000) {
/* 212 */         return ValidationResult.error("FluidParticles exceeds max length 4096000");
/*     */       }
/* 214 */       pos += VarInt.length(buffer, pos);
/* 215 */       for (int i = 0; i < fluidParticlesCount; i++) {
/* 216 */         pos += 4;
/* 217 */         if (pos > buffer.writerIndex()) {
/* 218 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 220 */         pos += FluidParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 225 */     if ((nullBits & 0x8) != 0) {
/* 226 */       int tagIndexesOffset = buffer.getIntLE(offset + 12);
/* 227 */       if (tagIndexesOffset < 0) {
/* 228 */         return ValidationResult.error("Invalid offset for TagIndexes");
/*     */       }
/* 230 */       int pos = offset + 16 + tagIndexesOffset;
/* 231 */       if (pos >= buffer.writerIndex()) {
/* 232 */         return ValidationResult.error("Offset out of bounds for TagIndexes");
/*     */       }
/* 234 */       int tagIndexesCount = VarInt.peek(buffer, pos);
/* 235 */       if (tagIndexesCount < 0) {
/* 236 */         return ValidationResult.error("Invalid array count for TagIndexes");
/*     */       }
/* 238 */       if (tagIndexesCount > 4096000) {
/* 239 */         return ValidationResult.error("TagIndexes exceeds max length 4096000");
/*     */       }
/* 241 */       pos += VarInt.length(buffer, pos);
/* 242 */       pos += tagIndexesCount * 4;
/* 243 */       if (pos > buffer.writerIndex()) {
/* 244 */         return ValidationResult.error("Buffer overflow reading TagIndexes");
/*     */       }
/*     */     } 
/* 247 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public WorldEnvironment clone() {
/* 251 */     WorldEnvironment copy = new WorldEnvironment();
/* 252 */     copy.id = this.id;
/* 253 */     copy.waterTint = (this.waterTint != null) ? this.waterTint.clone() : null;
/* 254 */     if (this.fluidParticles != null) {
/* 255 */       Map<Integer, FluidParticle> m = new HashMap<>();
/* 256 */       for (Map.Entry<Integer, FluidParticle> e : this.fluidParticles.entrySet()) m.put(e.getKey(), ((FluidParticle)e.getValue()).clone()); 
/* 257 */       copy.fluidParticles = m;
/*     */     } 
/* 259 */     copy.tagIndexes = (this.tagIndexes != null) ? Arrays.copyOf(this.tagIndexes, this.tagIndexes.length) : null;
/* 260 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     WorldEnvironment other;
/* 266 */     if (this == obj) return true; 
/* 267 */     if (obj instanceof WorldEnvironment) { other = (WorldEnvironment)obj; } else { return false; }
/* 268 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.waterTint, other.waterTint) && Objects.equals(this.fluidParticles, other.fluidParticles) && Arrays.equals(this.tagIndexes, other.tagIndexes));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 273 */     int result = 1;
/* 274 */     result = 31 * result + Objects.hashCode(this.id);
/* 275 */     result = 31 * result + Objects.hashCode(this.waterTint);
/* 276 */     result = 31 * result + Objects.hashCode(this.fluidParticles);
/* 277 */     result = 31 * result + Arrays.hashCode(this.tagIndexes);
/* 278 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\WorldEnvironment.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */