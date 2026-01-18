/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.ParticleSystem;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
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
/*     */ public class UpdateParticleSystems implements Packet {
/*     */   public static final int PACKET_ID = 49;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 49;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, ParticleSystem> particleSystems;
/*     */   @Nullable
/*     */   public String[] removedParticleSystems;
/*     */   
/*     */   public UpdateParticleSystems(@Nonnull UpdateType type, @Nullable Map<String, ParticleSystem> particleSystems, @Nullable String[] removedParticleSystems) {
/*  38 */     this.type = type;
/*  39 */     this.particleSystems = particleSystems;
/*  40 */     this.removedParticleSystems = removedParticleSystems;
/*     */   }
/*     */   
/*     */   public UpdateParticleSystems(@Nonnull UpdateParticleSystems other) {
/*  44 */     this.type = other.type;
/*  45 */     this.particleSystems = other.particleSystems;
/*  46 */     this.removedParticleSystems = other.removedParticleSystems;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateParticleSystems deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateParticleSystems obj = new UpdateParticleSystems();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  55 */     if ((nullBits & 0x1) != 0) {
/*  56 */       int varPos0 = offset + 10 + buf.getIntLE(offset + 2);
/*  57 */       int particleSystemsCount = VarInt.peek(buf, varPos0);
/*  58 */       if (particleSystemsCount < 0) throw ProtocolException.negativeLength("ParticleSystems", particleSystemsCount); 
/*  59 */       if (particleSystemsCount > 4096000) throw ProtocolException.dictionaryTooLarge("ParticleSystems", particleSystemsCount, 4096000); 
/*  60 */       int varIntLen = VarInt.length(buf, varPos0);
/*  61 */       obj.particleSystems = new HashMap<>(particleSystemsCount);
/*  62 */       int dictPos = varPos0 + varIntLen;
/*  63 */       for (int i = 0; i < particleSystemsCount; i++) {
/*  64 */         int keyLen = VarInt.peek(buf, dictPos);
/*  65 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  66 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  67 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  68 */         String key = PacketIO.readVarString(buf, dictPos);
/*  69 */         dictPos += keyVarLen + keyLen;
/*  70 */         ParticleSystem val = ParticleSystem.deserialize(buf, dictPos);
/*  71 */         dictPos += ParticleSystem.computeBytesConsumed(buf, dictPos);
/*  72 */         if (obj.particleSystems.put(key, val) != null)
/*  73 */           throw ProtocolException.duplicateKey("particleSystems", key); 
/*     */       } 
/*     */     } 
/*  76 */     if ((nullBits & 0x2) != 0) {
/*  77 */       int varPos1 = offset + 10 + buf.getIntLE(offset + 6);
/*  78 */       int removedParticleSystemsCount = VarInt.peek(buf, varPos1);
/*  79 */       if (removedParticleSystemsCount < 0) throw ProtocolException.negativeLength("RemovedParticleSystems", removedParticleSystemsCount); 
/*  80 */       if (removedParticleSystemsCount > 4096000) throw ProtocolException.arrayTooLong("RemovedParticleSystems", removedParticleSystemsCount, 4096000); 
/*  81 */       int varIntLen = VarInt.length(buf, varPos1);
/*  82 */       if ((varPos1 + varIntLen) + removedParticleSystemsCount * 1L > buf.readableBytes())
/*  83 */         throw ProtocolException.bufferTooSmall("RemovedParticleSystems", varPos1 + varIntLen + removedParticleSystemsCount * 1, buf.readableBytes()); 
/*  84 */       obj.removedParticleSystems = new String[removedParticleSystemsCount];
/*  85 */       int elemPos = varPos1 + varIntLen;
/*  86 */       for (int i = 0; i < removedParticleSystemsCount; i++) {
/*  87 */         int strLen = VarInt.peek(buf, elemPos);
/*  88 */         if (strLen < 0) throw ProtocolException.negativeLength("removedParticleSystems[" + i + "]", strLen); 
/*  89 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("removedParticleSystems[" + i + "]", strLen, 4096000); 
/*  90 */         int strVarLen = VarInt.length(buf, elemPos);
/*  91 */         obj.removedParticleSystems[i] = PacketIO.readVarString(buf, elemPos);
/*  92 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 100 */     byte nullBits = buf.getByte(offset);
/* 101 */     int maxEnd = 10;
/* 102 */     if ((nullBits & 0x1) != 0) {
/* 103 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/* 104 */       int pos0 = offset + 10 + fieldOffset0;
/* 105 */       int dictLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 106 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl; pos0 += ParticleSystem.computeBytesConsumed(buf, pos0); i++; }
/* 107 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 109 */     if ((nullBits & 0x2) != 0) {
/* 110 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/* 111 */       int pos1 = offset + 10 + fieldOffset1;
/* 112 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 113 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/* 114 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 116 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 122 */     int startPos = buf.writerIndex();
/* 123 */     byte nullBits = 0;
/* 124 */     if (this.particleSystems != null) nullBits = (byte)(nullBits | 0x1); 
/* 125 */     if (this.removedParticleSystems != null) nullBits = (byte)(nullBits | 0x2); 
/* 126 */     buf.writeByte(nullBits);
/*     */     
/* 128 */     buf.writeByte(this.type.getValue());
/*     */     
/* 130 */     int particleSystemsOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int removedParticleSystemsOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/*     */     
/* 135 */     int varBlockStart = buf.writerIndex();
/* 136 */     if (this.particleSystems != null)
/* 137 */     { buf.setIntLE(particleSystemsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       if (this.particleSystems.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ParticleSystems", this.particleSystems.size(), 4096000);  VarInt.write(buf, this.particleSystems.size()); for (Map.Entry<String, ParticleSystem> e : this.particleSystems.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((ParticleSystem)e.getValue()).serialize(buf); }
/*     */        }
/* 140 */     else { buf.setIntLE(particleSystemsOffsetSlot, -1); }
/*     */     
/* 142 */     if (this.removedParticleSystems != null) {
/* 143 */       buf.setIntLE(removedParticleSystemsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 144 */       if (this.removedParticleSystems.length > 4096000) throw ProtocolException.arrayTooLong("RemovedParticleSystems", this.removedParticleSystems.length, 4096000);  VarInt.write(buf, this.removedParticleSystems.length); for (String item : this.removedParticleSystems) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 146 */       buf.setIntLE(removedParticleSystemsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 152 */     int size = 10;
/* 153 */     if (this.particleSystems != null) {
/* 154 */       int particleSystemsSize = 0;
/* 155 */       for (Map.Entry<String, ParticleSystem> kvp : this.particleSystems.entrySet()) particleSystemsSize += PacketIO.stringSize(kvp.getKey()) + ((ParticleSystem)kvp.getValue()).computeSize(); 
/* 156 */       size += VarInt.size(this.particleSystems.size()) + particleSystemsSize;
/*     */     } 
/* 158 */     if (this.removedParticleSystems != null) {
/* 159 */       int removedParticleSystemsSize = 0;
/* 160 */       for (String elem : this.removedParticleSystems) removedParticleSystemsSize += PacketIO.stringSize(elem); 
/* 161 */       size += VarInt.size(this.removedParticleSystems.length) + removedParticleSystemsSize;
/*     */     } 
/*     */     
/* 164 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 168 */     if (buffer.readableBytes() - offset < 10) {
/* 169 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 172 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 175 */     if ((nullBits & 0x1) != 0) {
/* 176 */       int particleSystemsOffset = buffer.getIntLE(offset + 2);
/* 177 */       if (particleSystemsOffset < 0) {
/* 178 */         return ValidationResult.error("Invalid offset for ParticleSystems");
/*     */       }
/* 180 */       int pos = offset + 10 + particleSystemsOffset;
/* 181 */       if (pos >= buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Offset out of bounds for ParticleSystems");
/*     */       }
/* 184 */       int particleSystemsCount = VarInt.peek(buffer, pos);
/* 185 */       if (particleSystemsCount < 0) {
/* 186 */         return ValidationResult.error("Invalid dictionary count for ParticleSystems");
/*     */       }
/* 188 */       if (particleSystemsCount > 4096000) {
/* 189 */         return ValidationResult.error("ParticleSystems exceeds max length 4096000");
/*     */       }
/* 191 */       pos += VarInt.length(buffer, pos);
/* 192 */       for (int i = 0; i < particleSystemsCount; i++) {
/* 193 */         int keyLen = VarInt.peek(buffer, pos);
/* 194 */         if (keyLen < 0) {
/* 195 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 197 */         if (keyLen > 4096000) {
/* 198 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 200 */         pos += VarInt.length(buffer, pos);
/* 201 */         pos += keyLen;
/* 202 */         if (pos > buffer.writerIndex()) {
/* 203 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 205 */         pos += ParticleSystem.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 210 */     if ((nullBits & 0x2) != 0) {
/* 211 */       int removedParticleSystemsOffset = buffer.getIntLE(offset + 6);
/* 212 */       if (removedParticleSystemsOffset < 0) {
/* 213 */         return ValidationResult.error("Invalid offset for RemovedParticleSystems");
/*     */       }
/* 215 */       int pos = offset + 10 + removedParticleSystemsOffset;
/* 216 */       if (pos >= buffer.writerIndex()) {
/* 217 */         return ValidationResult.error("Offset out of bounds for RemovedParticleSystems");
/*     */       }
/* 219 */       int removedParticleSystemsCount = VarInt.peek(buffer, pos);
/* 220 */       if (removedParticleSystemsCount < 0) {
/* 221 */         return ValidationResult.error("Invalid array count for RemovedParticleSystems");
/*     */       }
/* 223 */       if (removedParticleSystemsCount > 4096000) {
/* 224 */         return ValidationResult.error("RemovedParticleSystems exceeds max length 4096000");
/*     */       }
/* 226 */       pos += VarInt.length(buffer, pos);
/* 227 */       for (int i = 0; i < removedParticleSystemsCount; i++) {
/* 228 */         int strLen = VarInt.peek(buffer, pos);
/* 229 */         if (strLen < 0) {
/* 230 */           return ValidationResult.error("Invalid string length in RemovedParticleSystems");
/*     */         }
/* 232 */         pos += VarInt.length(buffer, pos);
/* 233 */         pos += strLen;
/* 234 */         if (pos > buffer.writerIndex()) {
/* 235 */           return ValidationResult.error("Buffer overflow reading string in RemovedParticleSystems");
/*     */         }
/*     */       } 
/*     */     } 
/* 239 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateParticleSystems clone() {
/* 243 */     UpdateParticleSystems copy = new UpdateParticleSystems();
/* 244 */     copy.type = this.type;
/* 245 */     if (this.particleSystems != null) {
/* 246 */       Map<String, ParticleSystem> m = new HashMap<>();
/* 247 */       for (Map.Entry<String, ParticleSystem> e : this.particleSystems.entrySet()) m.put(e.getKey(), ((ParticleSystem)e.getValue()).clone()); 
/* 248 */       copy.particleSystems = m;
/*     */     } 
/* 250 */     copy.removedParticleSystems = (this.removedParticleSystems != null) ? Arrays.<String>copyOf(this.removedParticleSystems, this.removedParticleSystems.length) : null;
/* 251 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateParticleSystems other;
/* 257 */     if (this == obj) return true; 
/* 258 */     if (obj instanceof UpdateParticleSystems) { other = (UpdateParticleSystems)obj; } else { return false; }
/* 259 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.particleSystems, other.particleSystems) && Arrays.equals((Object[])this.removedParticleSystems, (Object[])other.removedParticleSystems));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 264 */     int result = 1;
/* 265 */     result = 31 * result + Objects.hashCode(this.type);
/* 266 */     result = 31 * result + Objects.hashCode(this.particleSystems);
/* 267 */     result = 31 * result + Arrays.hashCode((Object[])this.removedParticleSystems);
/* 268 */     return result;
/*     */   }
/*     */   
/*     */   public UpdateParticleSystems() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateParticleSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */