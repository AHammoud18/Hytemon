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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleSpawnerGroup
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 113;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 121;
/*     */   public static final int MAX_SIZE = 364544131;
/*     */   @Nullable
/*     */   public String spawnerId;
/*     */   @Nullable
/*     */   public Vector3f positionOffset;
/*     */   @Nullable
/*     */   public Direction rotationOffset;
/*     */   public boolean fixedRotation;
/*     */   
/*     */   public ParticleSpawnerGroup(@Nullable String spawnerId, @Nullable Vector3f positionOffset, @Nullable Direction rotationOffset, boolean fixedRotation, float startDelay, @Nullable Rangef spawnRate, @Nullable Rangef waveDelay, int totalSpawners, int maxConcurrent, @Nullable InitialVelocity initialVelocity, @Nullable RangeVector3f emitOffset, @Nullable Rangef lifeSpan, @Nullable ParticleAttractor[] attractors) {
/*  38 */     this.spawnerId = spawnerId;
/*  39 */     this.positionOffset = positionOffset;
/*  40 */     this.rotationOffset = rotationOffset;
/*  41 */     this.fixedRotation = fixedRotation;
/*  42 */     this.startDelay = startDelay;
/*  43 */     this.spawnRate = spawnRate;
/*  44 */     this.waveDelay = waveDelay;
/*  45 */     this.totalSpawners = totalSpawners;
/*  46 */     this.maxConcurrent = maxConcurrent;
/*  47 */     this.initialVelocity = initialVelocity;
/*  48 */     this.emitOffset = emitOffset;
/*  49 */     this.lifeSpan = lifeSpan;
/*  50 */     this.attractors = attractors; } public float startDelay; @Nullable public Rangef spawnRate; @Nullable public Rangef waveDelay; public int totalSpawners; public int maxConcurrent; @Nullable
/*     */   public InitialVelocity initialVelocity; @Nullable
/*     */   public RangeVector3f emitOffset; @Nullable
/*     */   public Rangef lifeSpan; @Nullable
/*  54 */   public ParticleAttractor[] attractors; public ParticleSpawnerGroup() {} public ParticleSpawnerGroup(@Nonnull ParticleSpawnerGroup other) { this.spawnerId = other.spawnerId;
/*  55 */     this.positionOffset = other.positionOffset;
/*  56 */     this.rotationOffset = other.rotationOffset;
/*  57 */     this.fixedRotation = other.fixedRotation;
/*  58 */     this.startDelay = other.startDelay;
/*  59 */     this.spawnRate = other.spawnRate;
/*  60 */     this.waveDelay = other.waveDelay;
/*  61 */     this.totalSpawners = other.totalSpawners;
/*  62 */     this.maxConcurrent = other.maxConcurrent;
/*  63 */     this.initialVelocity = other.initialVelocity;
/*  64 */     this.emitOffset = other.emitOffset;
/*  65 */     this.lifeSpan = other.lifeSpan;
/*  66 */     this.attractors = other.attractors; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ParticleSpawnerGroup deserialize(@Nonnull ByteBuf buf, int offset) {
/*  71 */     ParticleSpawnerGroup obj = new ParticleSpawnerGroup();
/*  72 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  73 */     if ((nullBits[0] & 0x2) != 0) obj.positionOffset = Vector3f.deserialize(buf, offset + 2); 
/*  74 */     if ((nullBits[0] & 0x4) != 0) obj.rotationOffset = Direction.deserialize(buf, offset + 14); 
/*  75 */     obj.fixedRotation = (buf.getByte(offset + 26) != 0);
/*  76 */     obj.startDelay = buf.getFloatLE(offset + 27);
/*  77 */     if ((nullBits[0] & 0x8) != 0) obj.spawnRate = Rangef.deserialize(buf, offset + 31); 
/*  78 */     if ((nullBits[0] & 0x10) != 0) obj.waveDelay = Rangef.deserialize(buf, offset + 39); 
/*  79 */     obj.totalSpawners = buf.getIntLE(offset + 47);
/*  80 */     obj.maxConcurrent = buf.getIntLE(offset + 51);
/*  81 */     if ((nullBits[0] & 0x20) != 0) obj.initialVelocity = InitialVelocity.deserialize(buf, offset + 55); 
/*  82 */     if ((nullBits[0] & 0x40) != 0) obj.emitOffset = RangeVector3f.deserialize(buf, offset + 80); 
/*  83 */     if ((nullBits[0] & 0x80) != 0) obj.lifeSpan = Rangef.deserialize(buf, offset + 105);
/*     */     
/*  85 */     if ((nullBits[0] & 0x1) != 0) {
/*  86 */       int varPos0 = offset + 121 + buf.getIntLE(offset + 113);
/*  87 */       int spawnerIdLen = VarInt.peek(buf, varPos0);
/*  88 */       if (spawnerIdLen < 0) throw ProtocolException.negativeLength("SpawnerId", spawnerIdLen); 
/*  89 */       if (spawnerIdLen > 4096000) throw ProtocolException.stringTooLong("SpawnerId", spawnerIdLen, 4096000); 
/*  90 */       obj.spawnerId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  92 */     if ((nullBits[1] & 0x1) != 0) {
/*  93 */       int varPos1 = offset + 121 + buf.getIntLE(offset + 117);
/*  94 */       int attractorsCount = VarInt.peek(buf, varPos1);
/*  95 */       if (attractorsCount < 0) throw ProtocolException.negativeLength("Attractors", attractorsCount); 
/*  96 */       if (attractorsCount > 4096000) throw ProtocolException.arrayTooLong("Attractors", attractorsCount, 4096000); 
/*  97 */       int varIntLen = VarInt.length(buf, varPos1);
/*  98 */       if ((varPos1 + varIntLen) + attractorsCount * 85L > buf.readableBytes())
/*  99 */         throw ProtocolException.bufferTooSmall("Attractors", varPos1 + varIntLen + attractorsCount * 85, buf.readableBytes()); 
/* 100 */       obj.attractors = new ParticleAttractor[attractorsCount];
/* 101 */       int elemPos = varPos1 + varIntLen;
/* 102 */       for (int i = 0; i < attractorsCount; i++) {
/* 103 */         obj.attractors[i] = ParticleAttractor.deserialize(buf, elemPos);
/* 104 */         elemPos += ParticleAttractor.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 112 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 113 */     int maxEnd = 121;
/* 114 */     if ((nullBits[0] & 0x1) != 0) {
/* 115 */       int fieldOffset0 = buf.getIntLE(offset + 113);
/* 116 */       int pos0 = offset + 121 + fieldOffset0;
/* 117 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 118 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 120 */     if ((nullBits[1] & 0x1) != 0) {
/* 121 */       int fieldOffset1 = buf.getIntLE(offset + 117);
/* 122 */       int pos1 = offset + 121 + fieldOffset1;
/* 123 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 124 */       for (int i = 0; i < arrLen; ) { pos1 += ParticleAttractor.computeBytesConsumed(buf, pos1); i++; }
/* 125 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 127 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 132 */     int startPos = buf.writerIndex();
/* 133 */     byte[] nullBits = new byte[2];
/* 134 */     if (this.spawnerId != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 135 */     if (this.positionOffset != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 136 */     if (this.rotationOffset != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 137 */     if (this.spawnRate != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 138 */     if (this.waveDelay != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 139 */     if (this.initialVelocity != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 140 */     if (this.emitOffset != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 141 */     if (this.lifeSpan != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 142 */     if (this.attractors != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 143 */     buf.writeBytes(nullBits);
/*     */     
/* 145 */     if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(12); }
/* 146 */      if (this.rotationOffset != null) { this.rotationOffset.serialize(buf); } else { buf.writeZero(12); }
/* 147 */      buf.writeByte(this.fixedRotation ? 1 : 0);
/* 148 */     buf.writeFloatLE(this.startDelay);
/* 149 */     if (this.spawnRate != null) { this.spawnRate.serialize(buf); } else { buf.writeZero(8); }
/* 150 */      if (this.waveDelay != null) { this.waveDelay.serialize(buf); } else { buf.writeZero(8); }
/* 151 */      buf.writeIntLE(this.totalSpawners);
/* 152 */     buf.writeIntLE(this.maxConcurrent);
/* 153 */     if (this.initialVelocity != null) { this.initialVelocity.serialize(buf); } else { buf.writeZero(25); }
/* 154 */      if (this.emitOffset != null) { this.emitOffset.serialize(buf); } else { buf.writeZero(25); }
/* 155 */      if (this.lifeSpan != null) { this.lifeSpan.serialize(buf); } else { buf.writeZero(8); }
/*     */     
/* 157 */     int spawnerIdOffsetSlot = buf.writerIndex();
/* 158 */     buf.writeIntLE(0);
/* 159 */     int attractorsOffsetSlot = buf.writerIndex();
/* 160 */     buf.writeIntLE(0);
/*     */     
/* 162 */     int varBlockStart = buf.writerIndex();
/* 163 */     if (this.spawnerId != null) {
/* 164 */       buf.setIntLE(spawnerIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 165 */       PacketIO.writeVarString(buf, this.spawnerId, 4096000);
/*     */     } else {
/* 167 */       buf.setIntLE(spawnerIdOffsetSlot, -1);
/*     */     } 
/* 169 */     if (this.attractors != null) {
/* 170 */       buf.setIntLE(attractorsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 171 */       if (this.attractors.length > 4096000) throw ProtocolException.arrayTooLong("Attractors", this.attractors.length, 4096000);  VarInt.write(buf, this.attractors.length); for (ParticleAttractor item : this.attractors) item.serialize(buf); 
/*     */     } else {
/* 173 */       buf.setIntLE(attractorsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 179 */     int size = 121;
/* 180 */     if (this.spawnerId != null) size += PacketIO.stringSize(this.spawnerId); 
/* 181 */     if (this.attractors != null) size += VarInt.size(this.attractors.length) + this.attractors.length * 85;
/*     */     
/* 183 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 187 */     if (buffer.readableBytes() - offset < 121) {
/* 188 */       return ValidationResult.error("Buffer too small: expected at least 121 bytes");
/*     */     }
/*     */     
/* 191 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 193 */     if ((nullBits[0] & 0x1) != 0) {
/* 194 */       int spawnerIdOffset = buffer.getIntLE(offset + 113);
/* 195 */       if (spawnerIdOffset < 0) {
/* 196 */         return ValidationResult.error("Invalid offset for SpawnerId");
/*     */       }
/* 198 */       int pos = offset + 121 + spawnerIdOffset;
/* 199 */       if (pos >= buffer.writerIndex()) {
/* 200 */         return ValidationResult.error("Offset out of bounds for SpawnerId");
/*     */       }
/* 202 */       int spawnerIdLen = VarInt.peek(buffer, pos);
/* 203 */       if (spawnerIdLen < 0) {
/* 204 */         return ValidationResult.error("Invalid string length for SpawnerId");
/*     */       }
/* 206 */       if (spawnerIdLen > 4096000) {
/* 207 */         return ValidationResult.error("SpawnerId exceeds max length 4096000");
/*     */       }
/* 209 */       pos += VarInt.length(buffer, pos);
/* 210 */       pos += spawnerIdLen;
/* 211 */       if (pos > buffer.writerIndex()) {
/* 212 */         return ValidationResult.error("Buffer overflow reading SpawnerId");
/*     */       }
/*     */     } 
/*     */     
/* 216 */     if ((nullBits[1] & 0x1) != 0) {
/* 217 */       int attractorsOffset = buffer.getIntLE(offset + 117);
/* 218 */       if (attractorsOffset < 0) {
/* 219 */         return ValidationResult.error("Invalid offset for Attractors");
/*     */       }
/* 221 */       int pos = offset + 121 + attractorsOffset;
/* 222 */       if (pos >= buffer.writerIndex()) {
/* 223 */         return ValidationResult.error("Offset out of bounds for Attractors");
/*     */       }
/* 225 */       int attractorsCount = VarInt.peek(buffer, pos);
/* 226 */       if (attractorsCount < 0) {
/* 227 */         return ValidationResult.error("Invalid array count for Attractors");
/*     */       }
/* 229 */       if (attractorsCount > 4096000) {
/* 230 */         return ValidationResult.error("Attractors exceeds max length 4096000");
/*     */       }
/* 232 */       pos += VarInt.length(buffer, pos);
/* 233 */       pos += attractorsCount * 85;
/* 234 */       if (pos > buffer.writerIndex()) {
/* 235 */         return ValidationResult.error("Buffer overflow reading Attractors");
/*     */       }
/*     */     } 
/* 238 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ParticleSpawnerGroup clone() {
/* 242 */     ParticleSpawnerGroup copy = new ParticleSpawnerGroup();
/* 243 */     copy.spawnerId = this.spawnerId;
/* 244 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 245 */     copy.rotationOffset = (this.rotationOffset != null) ? this.rotationOffset.clone() : null;
/* 246 */     copy.fixedRotation = this.fixedRotation;
/* 247 */     copy.startDelay = this.startDelay;
/* 248 */     copy.spawnRate = (this.spawnRate != null) ? this.spawnRate.clone() : null;
/* 249 */     copy.waveDelay = (this.waveDelay != null) ? this.waveDelay.clone() : null;
/* 250 */     copy.totalSpawners = this.totalSpawners;
/* 251 */     copy.maxConcurrent = this.maxConcurrent;
/* 252 */     copy.initialVelocity = (this.initialVelocity != null) ? this.initialVelocity.clone() : null;
/* 253 */     copy.emitOffset = (this.emitOffset != null) ? this.emitOffset.clone() : null;
/* 254 */     copy.lifeSpan = (this.lifeSpan != null) ? this.lifeSpan.clone() : null;
/* 255 */     copy.attractors = (this.attractors != null) ? (ParticleAttractor[])Arrays.<ParticleAttractor>stream(this.attractors).map(e -> e.clone()).toArray(x$0 -> new ParticleAttractor[x$0]) : null;
/* 256 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ParticleSpawnerGroup other;
/* 262 */     if (this == obj) return true; 
/* 263 */     if (obj instanceof ParticleSpawnerGroup) { other = (ParticleSpawnerGroup)obj; } else { return false; }
/* 264 */      return (Objects.equals(this.spawnerId, other.spawnerId) && Objects.equals(this.positionOffset, other.positionOffset) && Objects.equals(this.rotationOffset, other.rotationOffset) && this.fixedRotation == other.fixedRotation && this.startDelay == other.startDelay && Objects.equals(this.spawnRate, other.spawnRate) && Objects.equals(this.waveDelay, other.waveDelay) && this.totalSpawners == other.totalSpawners && this.maxConcurrent == other.maxConcurrent && Objects.equals(this.initialVelocity, other.initialVelocity) && Objects.equals(this.emitOffset, other.emitOffset) && Objects.equals(this.lifeSpan, other.lifeSpan) && Arrays.equals((Object[])this.attractors, (Object[])other.attractors));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 269 */     int result = 1;
/* 270 */     result = 31 * result + Objects.hashCode(this.spawnerId);
/* 271 */     result = 31 * result + Objects.hashCode(this.positionOffset);
/* 272 */     result = 31 * result + Objects.hashCode(this.rotationOffset);
/* 273 */     result = 31 * result + Boolean.hashCode(this.fixedRotation);
/* 274 */     result = 31 * result + Float.hashCode(this.startDelay);
/* 275 */     result = 31 * result + Objects.hashCode(this.spawnRate);
/* 276 */     result = 31 * result + Objects.hashCode(this.waveDelay);
/* 277 */     result = 31 * result + Integer.hashCode(this.totalSpawners);
/* 278 */     result = 31 * result + Integer.hashCode(this.maxConcurrent);
/* 279 */     result = 31 * result + Objects.hashCode(this.initialVelocity);
/* 280 */     result = 31 * result + Objects.hashCode(this.emitOffset);
/* 281 */     result = 31 * result + Objects.hashCode(this.lifeSpan);
/* 282 */     result = 31 * result + Arrays.hashCode((Object[])this.attractors);
/* 283 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ParticleSpawnerGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */