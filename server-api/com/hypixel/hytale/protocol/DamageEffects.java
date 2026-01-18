/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class DamageEffects
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public ModelParticle[] modelParticles;
/*     */   @Nullable
/*     */   public WorldParticle[] worldParticles;
/*     */   public int soundEventIndex;
/*     */   
/*     */   public DamageEffects() {}
/*     */   
/*     */   public DamageEffects(@Nullable ModelParticle[] modelParticles, @Nullable WorldParticle[] worldParticles, int soundEventIndex) {
/*  28 */     this.modelParticles = modelParticles;
/*  29 */     this.worldParticles = worldParticles;
/*  30 */     this.soundEventIndex = soundEventIndex;
/*     */   }
/*     */   
/*     */   public DamageEffects(@Nonnull DamageEffects other) {
/*  34 */     this.modelParticles = other.modelParticles;
/*  35 */     this.worldParticles = other.worldParticles;
/*  36 */     this.soundEventIndex = other.soundEventIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DamageEffects deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     DamageEffects obj = new DamageEffects();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.soundEventIndex = buf.getIntLE(offset + 1);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 5);
/*  47 */       int modelParticlesCount = VarInt.peek(buf, varPos0);
/*  48 */       if (modelParticlesCount < 0) throw ProtocolException.negativeLength("ModelParticles", modelParticlesCount); 
/*  49 */       if (modelParticlesCount > 4096000) throw ProtocolException.arrayTooLong("ModelParticles", modelParticlesCount, 4096000); 
/*  50 */       int varIntLen = VarInt.length(buf, varPos0);
/*  51 */       if ((varPos0 + varIntLen) + modelParticlesCount * 34L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("ModelParticles", varPos0 + varIntLen + modelParticlesCount * 34, buf.readableBytes()); 
/*  53 */       obj.modelParticles = new ModelParticle[modelParticlesCount];
/*  54 */       int elemPos = varPos0 + varIntLen;
/*  55 */       for (int i = 0; i < modelParticlesCount; i++) {
/*  56 */         obj.modelParticles[i] = ModelParticle.deserialize(buf, elemPos);
/*  57 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 9);
/*  62 */       int worldParticlesCount = VarInt.peek(buf, varPos1);
/*  63 */       if (worldParticlesCount < 0) throw ProtocolException.negativeLength("WorldParticles", worldParticlesCount); 
/*  64 */       if (worldParticlesCount > 4096000) throw ProtocolException.arrayTooLong("WorldParticles", worldParticlesCount, 4096000); 
/*  65 */       int varIntLen = VarInt.length(buf, varPos1);
/*  66 */       if ((varPos1 + varIntLen) + worldParticlesCount * 32L > buf.readableBytes())
/*  67 */         throw ProtocolException.bufferTooSmall("WorldParticles", varPos1 + varIntLen + worldParticlesCount * 32, buf.readableBytes()); 
/*  68 */       obj.worldParticles = new WorldParticle[worldParticlesCount];
/*  69 */       int elemPos = varPos1 + varIntLen;
/*  70 */       for (int i = 0; i < worldParticlesCount; i++) {
/*  71 */         obj.worldParticles[i] = WorldParticle.deserialize(buf, elemPos);
/*  72 */         elemPos += WorldParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     byte nullBits = buf.getByte(offset);
/*  81 */     int maxEnd = 13;
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/*  84 */       int pos0 = offset + 13 + fieldOffset0;
/*  85 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  86 */       for (int i = 0; i < arrLen; ) { pos0 += ModelParticle.computeBytesConsumed(buf, pos0); i++; }
/*  87 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  89 */     if ((nullBits & 0x2) != 0) {
/*  90 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/*  91 */       int pos1 = offset + 13 + fieldOffset1;
/*  92 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  93 */       for (int i = 0; i < arrLen; ) { pos1 += WorldParticle.computeBytesConsumed(buf, pos1); i++; }
/*  94 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  96 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 101 */     int startPos = buf.writerIndex();
/* 102 */     byte nullBits = 0;
/* 103 */     if (this.modelParticles != null) nullBits = (byte)(nullBits | 0x1); 
/* 104 */     if (this.worldParticles != null) nullBits = (byte)(nullBits | 0x2); 
/* 105 */     buf.writeByte(nullBits);
/*     */     
/* 107 */     buf.writeIntLE(this.soundEventIndex);
/*     */     
/* 109 */     int modelParticlesOffsetSlot = buf.writerIndex();
/* 110 */     buf.writeIntLE(0);
/* 111 */     int worldParticlesOffsetSlot = buf.writerIndex();
/* 112 */     buf.writeIntLE(0);
/*     */     
/* 114 */     int varBlockStart = buf.writerIndex();
/* 115 */     if (this.modelParticles != null) {
/* 116 */       buf.setIntLE(modelParticlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 117 */       if (this.modelParticles.length > 4096000) throw ProtocolException.arrayTooLong("ModelParticles", this.modelParticles.length, 4096000);  VarInt.write(buf, this.modelParticles.length); for (ModelParticle item : this.modelParticles) item.serialize(buf); 
/*     */     } else {
/* 119 */       buf.setIntLE(modelParticlesOffsetSlot, -1);
/*     */     } 
/* 121 */     if (this.worldParticles != null) {
/* 122 */       buf.setIntLE(worldParticlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 123 */       if (this.worldParticles.length > 4096000) throw ProtocolException.arrayTooLong("WorldParticles", this.worldParticles.length, 4096000);  VarInt.write(buf, this.worldParticles.length); for (WorldParticle item : this.worldParticles) item.serialize(buf); 
/*     */     } else {
/* 125 */       buf.setIntLE(worldParticlesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 131 */     int size = 13;
/* 132 */     if (this.modelParticles != null) {
/* 133 */       int modelParticlesSize = 0;
/* 134 */       for (ModelParticle elem : this.modelParticles) modelParticlesSize += elem.computeSize(); 
/* 135 */       size += VarInt.size(this.modelParticles.length) + modelParticlesSize;
/*     */     } 
/* 137 */     if (this.worldParticles != null) {
/* 138 */       int worldParticlesSize = 0;
/* 139 */       for (WorldParticle elem : this.worldParticles) worldParticlesSize += elem.computeSize(); 
/* 140 */       size += VarInt.size(this.worldParticles.length) + worldParticlesSize;
/*     */     } 
/*     */     
/* 143 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 147 */     if (buffer.readableBytes() - offset < 13) {
/* 148 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 151 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 154 */     if ((nullBits & 0x1) != 0) {
/* 155 */       int modelParticlesOffset = buffer.getIntLE(offset + 5);
/* 156 */       if (modelParticlesOffset < 0) {
/* 157 */         return ValidationResult.error("Invalid offset for ModelParticles");
/*     */       }
/* 159 */       int pos = offset + 13 + modelParticlesOffset;
/* 160 */       if (pos >= buffer.writerIndex()) {
/* 161 */         return ValidationResult.error("Offset out of bounds for ModelParticles");
/*     */       }
/* 163 */       int modelParticlesCount = VarInt.peek(buffer, pos);
/* 164 */       if (modelParticlesCount < 0) {
/* 165 */         return ValidationResult.error("Invalid array count for ModelParticles");
/*     */       }
/* 167 */       if (modelParticlesCount > 4096000) {
/* 168 */         return ValidationResult.error("ModelParticles exceeds max length 4096000");
/*     */       }
/* 170 */       pos += VarInt.length(buffer, pos);
/* 171 */       for (int i = 0; i < modelParticlesCount; i++) {
/* 172 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 173 */         if (!structResult.isValid()) {
/* 174 */           return ValidationResult.error("Invalid ModelParticle in ModelParticles[" + i + "]: " + structResult.error());
/*     */         }
/* 176 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 180 */     if ((nullBits & 0x2) != 0) {
/* 181 */       int worldParticlesOffset = buffer.getIntLE(offset + 9);
/* 182 */       if (worldParticlesOffset < 0) {
/* 183 */         return ValidationResult.error("Invalid offset for WorldParticles");
/*     */       }
/* 185 */       int pos = offset + 13 + worldParticlesOffset;
/* 186 */       if (pos >= buffer.writerIndex()) {
/* 187 */         return ValidationResult.error("Offset out of bounds for WorldParticles");
/*     */       }
/* 189 */       int worldParticlesCount = VarInt.peek(buffer, pos);
/* 190 */       if (worldParticlesCount < 0) {
/* 191 */         return ValidationResult.error("Invalid array count for WorldParticles");
/*     */       }
/* 193 */       if (worldParticlesCount > 4096000) {
/* 194 */         return ValidationResult.error("WorldParticles exceeds max length 4096000");
/*     */       }
/* 196 */       pos += VarInt.length(buffer, pos);
/* 197 */       for (int i = 0; i < worldParticlesCount; i++) {
/* 198 */         ValidationResult structResult = WorldParticle.validateStructure(buffer, pos);
/* 199 */         if (!structResult.isValid()) {
/* 200 */           return ValidationResult.error("Invalid WorldParticle in WorldParticles[" + i + "]: " + structResult.error());
/*     */         }
/* 202 */         pos += WorldParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 205 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DamageEffects clone() {
/* 209 */     DamageEffects copy = new DamageEffects();
/* 210 */     copy.modelParticles = (this.modelParticles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.modelParticles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 211 */     copy.worldParticles = (this.worldParticles != null) ? (WorldParticle[])Arrays.<WorldParticle>stream(this.worldParticles).map(e -> e.clone()).toArray(x$0 -> new WorldParticle[x$0]) : null;
/* 212 */     copy.soundEventIndex = this.soundEventIndex;
/* 213 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DamageEffects other;
/* 219 */     if (this == obj) return true; 
/* 220 */     if (obj instanceof DamageEffects) { other = (DamageEffects)obj; } else { return false; }
/* 221 */      return (Arrays.equals((Object[])this.modelParticles, (Object[])other.modelParticles) && Arrays.equals((Object[])this.worldParticles, (Object[])other.worldParticles) && this.soundEventIndex == other.soundEventIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 226 */     int result = 1;
/* 227 */     result = 31 * result + Arrays.hashCode((Object[])this.modelParticles);
/* 228 */     result = 31 * result + Arrays.hashCode((Object[])this.worldParticles);
/* 229 */     result = 31 * result + Integer.hashCode(this.soundEventIndex);
/* 230 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\DamageEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */