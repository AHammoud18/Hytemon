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
/*     */ public class ParticleSystem {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 14;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 22;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public ParticleSpawnerGroup[] spawners;
/*     */   public float lifeSpan;
/*     */   public float cullDistance;
/*     */   public float boundingRadius;
/*     */   public boolean isImportant;
/*     */   
/*     */   public ParticleSystem() {}
/*     */   
/*     */   public ParticleSystem(@Nullable String id, @Nullable ParticleSpawnerGroup[] spawners, float lifeSpan, float cullDistance, float boundingRadius, boolean isImportant) {
/*  31 */     this.id = id;
/*  32 */     this.spawners = spawners;
/*  33 */     this.lifeSpan = lifeSpan;
/*  34 */     this.cullDistance = cullDistance;
/*  35 */     this.boundingRadius = boundingRadius;
/*  36 */     this.isImportant = isImportant;
/*     */   }
/*     */   
/*     */   public ParticleSystem(@Nonnull ParticleSystem other) {
/*  40 */     this.id = other.id;
/*  41 */     this.spawners = other.spawners;
/*  42 */     this.lifeSpan = other.lifeSpan;
/*  43 */     this.cullDistance = other.cullDistance;
/*  44 */     this.boundingRadius = other.boundingRadius;
/*  45 */     this.isImportant = other.isImportant;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ParticleSystem deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     ParticleSystem obj = new ParticleSystem();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.lifeSpan = buf.getFloatLE(offset + 1);
/*  53 */     obj.cullDistance = buf.getFloatLE(offset + 5);
/*  54 */     obj.boundingRadius = buf.getFloatLE(offset + 9);
/*  55 */     obj.isImportant = (buf.getByte(offset + 13) != 0);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 22 + buf.getIntLE(offset + 14);
/*  59 */       int idLen = VarInt.peek(buf, varPos0);
/*  60 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  61 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  62 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 22 + buf.getIntLE(offset + 18);
/*  66 */       int spawnersCount = VarInt.peek(buf, varPos1);
/*  67 */       if (spawnersCount < 0) throw ProtocolException.negativeLength("Spawners", spawnersCount); 
/*  68 */       if (spawnersCount > 4096000) throw ProtocolException.arrayTooLong("Spawners", spawnersCount, 4096000); 
/*  69 */       int varIntLen = VarInt.length(buf, varPos1);
/*  70 */       if ((varPos1 + varIntLen) + spawnersCount * 113L > buf.readableBytes())
/*  71 */         throw ProtocolException.bufferTooSmall("Spawners", varPos1 + varIntLen + spawnersCount * 113, buf.readableBytes()); 
/*  72 */       obj.spawners = new ParticleSpawnerGroup[spawnersCount];
/*  73 */       int elemPos = varPos1 + varIntLen;
/*  74 */       for (int i = 0; i < spawnersCount; i++) {
/*  75 */         obj.spawners[i] = ParticleSpawnerGroup.deserialize(buf, elemPos);
/*  76 */         elemPos += ParticleSpawnerGroup.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     byte nullBits = buf.getByte(offset);
/*  85 */     int maxEnd = 22;
/*  86 */     if ((nullBits & 0x1) != 0) {
/*  87 */       int fieldOffset0 = buf.getIntLE(offset + 14);
/*  88 */       int pos0 = offset + 22 + fieldOffset0;
/*  89 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  90 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  92 */     if ((nullBits & 0x2) != 0) {
/*  93 */       int fieldOffset1 = buf.getIntLE(offset + 18);
/*  94 */       int pos1 = offset + 22 + fieldOffset1;
/*  95 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  96 */       for (int i = 0; i < arrLen; ) { pos1 += ParticleSpawnerGroup.computeBytesConsumed(buf, pos1); i++; }
/*  97 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  99 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 104 */     int startPos = buf.writerIndex();
/* 105 */     byte nullBits = 0;
/* 106 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 107 */     if (this.spawners != null) nullBits = (byte)(nullBits | 0x2); 
/* 108 */     buf.writeByte(nullBits);
/*     */     
/* 110 */     buf.writeFloatLE(this.lifeSpan);
/* 111 */     buf.writeFloatLE(this.cullDistance);
/* 112 */     buf.writeFloatLE(this.boundingRadius);
/* 113 */     buf.writeByte(this.isImportant ? 1 : 0);
/*     */     
/* 115 */     int idOffsetSlot = buf.writerIndex();
/* 116 */     buf.writeIntLE(0);
/* 117 */     int spawnersOffsetSlot = buf.writerIndex();
/* 118 */     buf.writeIntLE(0);
/*     */     
/* 120 */     int varBlockStart = buf.writerIndex();
/* 121 */     if (this.id != null) {
/* 122 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 123 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 125 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 127 */     if (this.spawners != null) {
/* 128 */       buf.setIntLE(spawnersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 129 */       if (this.spawners.length > 4096000) throw ProtocolException.arrayTooLong("Spawners", this.spawners.length, 4096000);  VarInt.write(buf, this.spawners.length); for (ParticleSpawnerGroup item : this.spawners) item.serialize(buf); 
/*     */     } else {
/* 131 */       buf.setIntLE(spawnersOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 137 */     int size = 22;
/* 138 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 139 */     if (this.spawners != null) {
/* 140 */       int spawnersSize = 0;
/* 141 */       for (ParticleSpawnerGroup elem : this.spawners) spawnersSize += elem.computeSize(); 
/* 142 */       size += VarInt.size(this.spawners.length) + spawnersSize;
/*     */     } 
/*     */     
/* 145 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 149 */     if (buffer.readableBytes() - offset < 22) {
/* 150 */       return ValidationResult.error("Buffer too small: expected at least 22 bytes");
/*     */     }
/*     */     
/* 153 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 156 */     if ((nullBits & 0x1) != 0) {
/* 157 */       int idOffset = buffer.getIntLE(offset + 14);
/* 158 */       if (idOffset < 0) {
/* 159 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 161 */       int pos = offset + 22 + idOffset;
/* 162 */       if (pos >= buffer.writerIndex()) {
/* 163 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 165 */       int idLen = VarInt.peek(buffer, pos);
/* 166 */       if (idLen < 0) {
/* 167 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 169 */       if (idLen > 4096000) {
/* 170 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 172 */       pos += VarInt.length(buffer, pos);
/* 173 */       pos += idLen;
/* 174 */       if (pos > buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 179 */     if ((nullBits & 0x2) != 0) {
/* 180 */       int spawnersOffset = buffer.getIntLE(offset + 18);
/* 181 */       if (spawnersOffset < 0) {
/* 182 */         return ValidationResult.error("Invalid offset for Spawners");
/*     */       }
/* 184 */       int pos = offset + 22 + spawnersOffset;
/* 185 */       if (pos >= buffer.writerIndex()) {
/* 186 */         return ValidationResult.error("Offset out of bounds for Spawners");
/*     */       }
/* 188 */       int spawnersCount = VarInt.peek(buffer, pos);
/* 189 */       if (spawnersCount < 0) {
/* 190 */         return ValidationResult.error("Invalid array count for Spawners");
/*     */       }
/* 192 */       if (spawnersCount > 4096000) {
/* 193 */         return ValidationResult.error("Spawners exceeds max length 4096000");
/*     */       }
/* 195 */       pos += VarInt.length(buffer, pos);
/* 196 */       for (int i = 0; i < spawnersCount; i++) {
/* 197 */         ValidationResult structResult = ParticleSpawnerGroup.validateStructure(buffer, pos);
/* 198 */         if (!structResult.isValid()) {
/* 199 */           return ValidationResult.error("Invalid ParticleSpawnerGroup in Spawners[" + i + "]: " + structResult.error());
/*     */         }
/* 201 */         pos += ParticleSpawnerGroup.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 204 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ParticleSystem clone() {
/* 208 */     ParticleSystem copy = new ParticleSystem();
/* 209 */     copy.id = this.id;
/* 210 */     copy.spawners = (this.spawners != null) ? (ParticleSpawnerGroup[])Arrays.<ParticleSpawnerGroup>stream(this.spawners).map(e -> e.clone()).toArray(x$0 -> new ParticleSpawnerGroup[x$0]) : null;
/* 211 */     copy.lifeSpan = this.lifeSpan;
/* 212 */     copy.cullDistance = this.cullDistance;
/* 213 */     copy.boundingRadius = this.boundingRadius;
/* 214 */     copy.isImportant = this.isImportant;
/* 215 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ParticleSystem other;
/* 221 */     if (this == obj) return true; 
/* 222 */     if (obj instanceof ParticleSystem) { other = (ParticleSystem)obj; } else { return false; }
/* 223 */      return (Objects.equals(this.id, other.id) && Arrays.equals((Object[])this.spawners, (Object[])other.spawners) && this.lifeSpan == other.lifeSpan && this.cullDistance == other.cullDistance && this.boundingRadius == other.boundingRadius && this.isImportant == other.isImportant);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 228 */     int result = 1;
/* 229 */     result = 31 * result + Objects.hashCode(this.id);
/* 230 */     result = 31 * result + Arrays.hashCode((Object[])this.spawners);
/* 231 */     result = 31 * result + Float.hashCode(this.lifeSpan);
/* 232 */     result = 31 * result + Float.hashCode(this.cullDistance);
/* 233 */     result = 31 * result + Float.hashCode(this.boundingRadius);
/* 234 */     result = 31 * result + Boolean.hashCode(this.isImportant);
/* 235 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ParticleSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */