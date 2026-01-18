/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ 
/*     */ public class BlockParticleSet
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 32;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 40;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   
/*     */   public BlockParticleSet(@Nullable String id, @Nullable Color color, float scale, @Nullable Vector3f positionOffset, @Nullable Direction rotationOffset, @Nullable Map<BlockParticleEvent, String> particleSystemIds) {
/*  31 */     this.id = id;
/*  32 */     this.color = color;
/*  33 */     this.scale = scale;
/*  34 */     this.positionOffset = positionOffset;
/*  35 */     this.rotationOffset = rotationOffset;
/*  36 */     this.particleSystemIds = particleSystemIds; } @Nullable
/*     */   public Color color; public float scale; @Nullable
/*     */   public Vector3f positionOffset; @Nullable
/*     */   public Direction rotationOffset; @Nullable
/*  40 */   public Map<BlockParticleEvent, String> particleSystemIds; public BlockParticleSet() {} public BlockParticleSet(@Nonnull BlockParticleSet other) { this.id = other.id;
/*  41 */     this.color = other.color;
/*  42 */     this.scale = other.scale;
/*  43 */     this.positionOffset = other.positionOffset;
/*  44 */     this.rotationOffset = other.rotationOffset;
/*  45 */     this.particleSystemIds = other.particleSystemIds; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BlockParticleSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     BlockParticleSet obj = new BlockParticleSet();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     if ((nullBits & 0x2) != 0) obj.color = Color.deserialize(buf, offset + 1); 
/*  53 */     obj.scale = buf.getFloatLE(offset + 4);
/*  54 */     if ((nullBits & 0x4) != 0) obj.positionOffset = Vector3f.deserialize(buf, offset + 8); 
/*  55 */     if ((nullBits & 0x8) != 0) obj.rotationOffset = Direction.deserialize(buf, offset + 20);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 40 + buf.getIntLE(offset + 32);
/*  59 */       int idLen = VarInt.peek(buf, varPos0);
/*  60 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  61 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  62 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x10) != 0) {
/*  65 */       int varPos1 = offset + 40 + buf.getIntLE(offset + 36);
/*  66 */       int particleSystemIdsCount = VarInt.peek(buf, varPos1);
/*  67 */       if (particleSystemIdsCount < 0) throw ProtocolException.negativeLength("ParticleSystemIds", particleSystemIdsCount); 
/*  68 */       if (particleSystemIdsCount > 4096000) throw ProtocolException.dictionaryTooLarge("ParticleSystemIds", particleSystemIdsCount, 4096000); 
/*  69 */       int varIntLen = VarInt.length(buf, varPos1);
/*  70 */       obj.particleSystemIds = new HashMap<>(particleSystemIdsCount);
/*  71 */       int dictPos = varPos1 + varIntLen;
/*  72 */       for (int i = 0; i < particleSystemIdsCount; i++) {
/*  73 */         BlockParticleEvent key = BlockParticleEvent.fromValue(buf.getByte(dictPos)); dictPos++;
/*  74 */         int valLen = VarInt.peek(buf, dictPos);
/*  75 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  76 */         if (valLen > 4096000) throw ProtocolException.stringTooLong("val", valLen, 4096000); 
/*  77 */         int valVarLen = VarInt.length(buf, dictPos);
/*  78 */         String val = PacketIO.readVarString(buf, dictPos);
/*  79 */         dictPos += valVarLen + valLen;
/*  80 */         if (obj.particleSystemIds.put(key, val) != null) {
/*  81 */           throw ProtocolException.duplicateKey("particleSystemIds", key);
/*     */         }
/*     */       } 
/*     */     } 
/*  85 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  89 */     byte nullBits = buf.getByte(offset);
/*  90 */     int maxEnd = 40;
/*  91 */     if ((nullBits & 0x1) != 0) {
/*  92 */       int fieldOffset0 = buf.getIntLE(offset + 32);
/*  93 */       int pos0 = offset + 40 + fieldOffset0;
/*  94 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  95 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  97 */     if ((nullBits & 0x10) != 0) {
/*  98 */       int fieldOffset1 = buf.getIntLE(offset + 36);
/*  99 */       int pos1 = offset + 40 + fieldOffset1;
/* 100 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 101 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, ++pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/* 102 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 104 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 109 */     int startPos = buf.writerIndex();
/* 110 */     byte nullBits = 0;
/* 111 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 112 */     if (this.color != null) nullBits = (byte)(nullBits | 0x2); 
/* 113 */     if (this.positionOffset != null) nullBits = (byte)(nullBits | 0x4); 
/* 114 */     if (this.rotationOffset != null) nullBits = (byte)(nullBits | 0x8); 
/* 115 */     if (this.particleSystemIds != null) nullBits = (byte)(nullBits | 0x10); 
/* 116 */     buf.writeByte(nullBits);
/*     */     
/* 118 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(3); }
/* 119 */      buf.writeFloatLE(this.scale);
/* 120 */     if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(12); }
/* 121 */      if (this.rotationOffset != null) { this.rotationOffset.serialize(buf); } else { buf.writeZero(12); }
/*     */     
/* 123 */     int idOffsetSlot = buf.writerIndex();
/* 124 */     buf.writeIntLE(0);
/* 125 */     int particleSystemIdsOffsetSlot = buf.writerIndex();
/* 126 */     buf.writeIntLE(0);
/*     */     
/* 128 */     int varBlockStart = buf.writerIndex();
/* 129 */     if (this.id != null) {
/* 130 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 131 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 133 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 135 */     if (this.particleSystemIds != null)
/* 136 */     { buf.setIntLE(particleSystemIdsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 137 */       if (this.particleSystemIds.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ParticleSystemIds", this.particleSystemIds.size(), 4096000);  VarInt.write(buf, this.particleSystemIds.size()); for (Map.Entry<BlockParticleEvent, String> e : this.particleSystemIds.entrySet()) { buf.writeByte(((BlockParticleEvent)e.getKey()).getValue()); PacketIO.writeVarString(buf, e.getValue(), 4096000); }
/*     */        }
/* 139 */     else { buf.setIntLE(particleSystemIdsOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 145 */     int size = 40;
/* 146 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 147 */     if (this.particleSystemIds != null) {
/* 148 */       int particleSystemIdsSize = 0;
/* 149 */       for (Map.Entry<BlockParticleEvent, String> kvp : this.particleSystemIds.entrySet()) particleSystemIdsSize += 1 + PacketIO.stringSize(kvp.getValue()); 
/* 150 */       size += VarInt.size(this.particleSystemIds.size()) + particleSystemIdsSize;
/*     */     } 
/*     */     
/* 153 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 157 */     if (buffer.readableBytes() - offset < 40) {
/* 158 */       return ValidationResult.error("Buffer too small: expected at least 40 bytes");
/*     */     }
/*     */     
/* 161 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 164 */     if ((nullBits & 0x1) != 0) {
/* 165 */       int idOffset = buffer.getIntLE(offset + 32);
/* 166 */       if (idOffset < 0) {
/* 167 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 169 */       int pos = offset + 40 + idOffset;
/* 170 */       if (pos >= buffer.writerIndex()) {
/* 171 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 173 */       int idLen = VarInt.peek(buffer, pos);
/* 174 */       if (idLen < 0) {
/* 175 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 177 */       if (idLen > 4096000) {
/* 178 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 180 */       pos += VarInt.length(buffer, pos);
/* 181 */       pos += idLen;
/* 182 */       if (pos > buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 187 */     if ((nullBits & 0x10) != 0) {
/* 188 */       int particleSystemIdsOffset = buffer.getIntLE(offset + 36);
/* 189 */       if (particleSystemIdsOffset < 0) {
/* 190 */         return ValidationResult.error("Invalid offset for ParticleSystemIds");
/*     */       }
/* 192 */       int pos = offset + 40 + particleSystemIdsOffset;
/* 193 */       if (pos >= buffer.writerIndex()) {
/* 194 */         return ValidationResult.error("Offset out of bounds for ParticleSystemIds");
/*     */       }
/* 196 */       int particleSystemIdsCount = VarInt.peek(buffer, pos);
/* 197 */       if (particleSystemIdsCount < 0) {
/* 198 */         return ValidationResult.error("Invalid dictionary count for ParticleSystemIds");
/*     */       }
/* 200 */       if (particleSystemIdsCount > 4096000) {
/* 201 */         return ValidationResult.error("ParticleSystemIds exceeds max length 4096000");
/*     */       }
/* 203 */       pos += VarInt.length(buffer, pos);
/* 204 */       for (int i = 0; i < particleSystemIdsCount; i++) {
/* 205 */         pos++;
/*     */         
/* 207 */         int valueLen = VarInt.peek(buffer, pos);
/* 208 */         if (valueLen < 0) {
/* 209 */           return ValidationResult.error("Invalid string length for value");
/*     */         }
/* 211 */         if (valueLen > 4096000) {
/* 212 */           return ValidationResult.error("value exceeds max length 4096000");
/*     */         }
/* 214 */         pos += VarInt.length(buffer, pos);
/* 215 */         pos += valueLen;
/* 216 */         if (pos > buffer.writerIndex()) {
/* 217 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 221 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockParticleSet clone() {
/* 225 */     BlockParticleSet copy = new BlockParticleSet();
/* 226 */     copy.id = this.id;
/* 227 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 228 */     copy.scale = this.scale;
/* 229 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 230 */     copy.rotationOffset = (this.rotationOffset != null) ? this.rotationOffset.clone() : null;
/* 231 */     copy.particleSystemIds = (this.particleSystemIds != null) ? new HashMap<>(this.particleSystemIds) : null;
/* 232 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockParticleSet other;
/* 238 */     if (this == obj) return true; 
/* 239 */     if (obj instanceof BlockParticleSet) { other = (BlockParticleSet)obj; } else { return false; }
/* 240 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.color, other.color) && this.scale == other.scale && Objects.equals(this.positionOffset, other.positionOffset) && Objects.equals(this.rotationOffset, other.rotationOffset) && Objects.equals(this.particleSystemIds, other.particleSystemIds));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 245 */     return Objects.hash(new Object[] { this.id, this.color, Float.valueOf(this.scale), this.positionOffset, this.rotationOffset, this.particleSystemIds });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockParticleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */