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
/*     */ public class ModelOverride
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   
/*     */   public ModelOverride(@Nullable String model, @Nullable String texture, @Nullable Map<String, AnimationSet> animationSets) {
/*  28 */     this.model = model;
/*  29 */     this.texture = texture;
/*  30 */     this.animationSets = animationSets; } public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String model; @Nullable
/*     */   public String texture; @Nullable
/*     */   public Map<String, AnimationSet> animationSets; public ModelOverride() {} public ModelOverride(@Nonnull ModelOverride other) {
/*  34 */     this.model = other.model;
/*  35 */     this.texture = other.texture;
/*  36 */     this.animationSets = other.animationSets;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModelOverride deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ModelOverride obj = new ModelOverride();
/*  42 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  44 */     if ((nullBits & 0x1) != 0) {
/*  45 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  46 */       int modelLen = VarInt.peek(buf, varPos0);
/*  47 */       if (modelLen < 0) throw ProtocolException.negativeLength("Model", modelLen); 
/*  48 */       if (modelLen > 4096000) throw ProtocolException.stringTooLong("Model", modelLen, 4096000); 
/*  49 */       obj.model = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  51 */     if ((nullBits & 0x2) != 0) {
/*  52 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  53 */       int textureLen = VarInt.peek(buf, varPos1);
/*  54 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/*  55 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/*  56 */       obj.texture = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  58 */     if ((nullBits & 0x4) != 0) {
/*  59 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  60 */       int animationSetsCount = VarInt.peek(buf, varPos2);
/*  61 */       if (animationSetsCount < 0) throw ProtocolException.negativeLength("AnimationSets", animationSetsCount); 
/*  62 */       if (animationSetsCount > 4096000) throw ProtocolException.dictionaryTooLarge("AnimationSets", animationSetsCount, 4096000); 
/*  63 */       int varIntLen = VarInt.length(buf, varPos2);
/*  64 */       obj.animationSets = new HashMap<>(animationSetsCount);
/*  65 */       int dictPos = varPos2 + varIntLen;
/*  66 */       for (int i = 0; i < animationSetsCount; i++) {
/*  67 */         int keyLen = VarInt.peek(buf, dictPos);
/*  68 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  69 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  70 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  71 */         String key = PacketIO.readVarString(buf, dictPos);
/*  72 */         dictPos += keyVarLen + keyLen;
/*  73 */         AnimationSet val = AnimationSet.deserialize(buf, dictPos);
/*  74 */         dictPos += AnimationSet.computeBytesConsumed(buf, dictPos);
/*  75 */         if (obj.animationSets.put(key, val) != null) {
/*  76 */           throw ProtocolException.duplicateKey("animationSets", key);
/*     */         }
/*     */       } 
/*     */     } 
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     byte nullBits = buf.getByte(offset);
/*  85 */     int maxEnd = 13;
/*  86 */     if ((nullBits & 0x1) != 0) {
/*  87 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  88 */       int pos0 = offset + 13 + fieldOffset0;
/*  89 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  90 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  92 */     if ((nullBits & 0x2) != 0) {
/*  93 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  94 */       int pos1 = offset + 13 + fieldOffset1;
/*  95 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  96 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  98 */     if ((nullBits & 0x4) != 0) {
/*  99 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 100 */       int pos2 = offset + 13 + fieldOffset2;
/* 101 */       int dictLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 102 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl; pos2 += AnimationSet.computeBytesConsumed(buf, pos2); i++; }
/* 103 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 105 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 110 */     int startPos = buf.writerIndex();
/* 111 */     byte nullBits = 0;
/* 112 */     if (this.model != null) nullBits = (byte)(nullBits | 0x1); 
/* 113 */     if (this.texture != null) nullBits = (byte)(nullBits | 0x2); 
/* 114 */     if (this.animationSets != null) nullBits = (byte)(nullBits | 0x4); 
/* 115 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 118 */     int modelOffsetSlot = buf.writerIndex();
/* 119 */     buf.writeIntLE(0);
/* 120 */     int textureOffsetSlot = buf.writerIndex();
/* 121 */     buf.writeIntLE(0);
/* 122 */     int animationSetsOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/*     */     
/* 125 */     int varBlockStart = buf.writerIndex();
/* 126 */     if (this.model != null) {
/* 127 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       PacketIO.writeVarString(buf, this.model, 4096000);
/*     */     } else {
/* 130 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/* 132 */     if (this.texture != null) {
/* 133 */       buf.setIntLE(textureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 134 */       PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */     } else {
/* 136 */       buf.setIntLE(textureOffsetSlot, -1);
/*     */     } 
/* 138 */     if (this.animationSets != null)
/* 139 */     { buf.setIntLE(animationSetsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 140 */       if (this.animationSets.size() > 4096000) throw ProtocolException.dictionaryTooLarge("AnimationSets", this.animationSets.size(), 4096000);  VarInt.write(buf, this.animationSets.size()); for (Map.Entry<String, AnimationSet> e : this.animationSets.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((AnimationSet)e.getValue()).serialize(buf); }
/*     */        }
/* 142 */     else { buf.setIntLE(animationSetsOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 148 */     int size = 13;
/* 149 */     if (this.model != null) size += PacketIO.stringSize(this.model); 
/* 150 */     if (this.texture != null) size += PacketIO.stringSize(this.texture); 
/* 151 */     if (this.animationSets != null) {
/* 152 */       int animationSetsSize = 0;
/* 153 */       for (Map.Entry<String, AnimationSet> kvp : this.animationSets.entrySet()) animationSetsSize += PacketIO.stringSize(kvp.getKey()) + ((AnimationSet)kvp.getValue()).computeSize(); 
/* 154 */       size += VarInt.size(this.animationSets.size()) + animationSetsSize;
/*     */     } 
/*     */     
/* 157 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 161 */     if (buffer.readableBytes() - offset < 13) {
/* 162 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 165 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 168 */     if ((nullBits & 0x1) != 0) {
/* 169 */       int modelOffset = buffer.getIntLE(offset + 1);
/* 170 */       if (modelOffset < 0) {
/* 171 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 173 */       int pos = offset + 13 + modelOffset;
/* 174 */       if (pos >= buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 177 */       int modelLen = VarInt.peek(buffer, pos);
/* 178 */       if (modelLen < 0) {
/* 179 */         return ValidationResult.error("Invalid string length for Model");
/*     */       }
/* 181 */       if (modelLen > 4096000) {
/* 182 */         return ValidationResult.error("Model exceeds max length 4096000");
/*     */       }
/* 184 */       pos += VarInt.length(buffer, pos);
/* 185 */       pos += modelLen;
/* 186 */       if (pos > buffer.writerIndex()) {
/* 187 */         return ValidationResult.error("Buffer overflow reading Model");
/*     */       }
/*     */     } 
/*     */     
/* 191 */     if ((nullBits & 0x2) != 0) {
/* 192 */       int textureOffset = buffer.getIntLE(offset + 5);
/* 193 */       if (textureOffset < 0) {
/* 194 */         return ValidationResult.error("Invalid offset for Texture");
/*     */       }
/* 196 */       int pos = offset + 13 + textureOffset;
/* 197 */       if (pos >= buffer.writerIndex()) {
/* 198 */         return ValidationResult.error("Offset out of bounds for Texture");
/*     */       }
/* 200 */       int textureLen = VarInt.peek(buffer, pos);
/* 201 */       if (textureLen < 0) {
/* 202 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/* 204 */       if (textureLen > 4096000) {
/* 205 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/* 207 */       pos += VarInt.length(buffer, pos);
/* 208 */       pos += textureLen;
/* 209 */       if (pos > buffer.writerIndex()) {
/* 210 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/*     */     
/* 214 */     if ((nullBits & 0x4) != 0) {
/* 215 */       int animationSetsOffset = buffer.getIntLE(offset + 9);
/* 216 */       if (animationSetsOffset < 0) {
/* 217 */         return ValidationResult.error("Invalid offset for AnimationSets");
/*     */       }
/* 219 */       int pos = offset + 13 + animationSetsOffset;
/* 220 */       if (pos >= buffer.writerIndex()) {
/* 221 */         return ValidationResult.error("Offset out of bounds for AnimationSets");
/*     */       }
/* 223 */       int animationSetsCount = VarInt.peek(buffer, pos);
/* 224 */       if (animationSetsCount < 0) {
/* 225 */         return ValidationResult.error("Invalid dictionary count for AnimationSets");
/*     */       }
/* 227 */       if (animationSetsCount > 4096000) {
/* 228 */         return ValidationResult.error("AnimationSets exceeds max length 4096000");
/*     */       }
/* 230 */       pos += VarInt.length(buffer, pos);
/* 231 */       for (int i = 0; i < animationSetsCount; i++) {
/* 232 */         int keyLen = VarInt.peek(buffer, pos);
/* 233 */         if (keyLen < 0) {
/* 234 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 236 */         if (keyLen > 4096000) {
/* 237 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 239 */         pos += VarInt.length(buffer, pos);
/* 240 */         pos += keyLen;
/* 241 */         if (pos > buffer.writerIndex()) {
/* 242 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 244 */         pos += AnimationSet.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelOverride clone() {
/* 252 */     ModelOverride copy = new ModelOverride();
/* 253 */     copy.model = this.model;
/* 254 */     copy.texture = this.texture;
/* 255 */     if (this.animationSets != null) {
/* 256 */       Map<String, AnimationSet> m = new HashMap<>();
/* 257 */       for (Map.Entry<String, AnimationSet> e : this.animationSets.entrySet()) m.put(e.getKey(), ((AnimationSet)e.getValue()).clone()); 
/* 258 */       copy.animationSets = m;
/*     */     } 
/* 260 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelOverride other;
/* 266 */     if (this == obj) return true; 
/* 267 */     if (obj instanceof ModelOverride) { other = (ModelOverride)obj; } else { return false; }
/* 268 */      return (Objects.equals(this.model, other.model) && Objects.equals(this.texture, other.texture) && Objects.equals(this.animationSets, other.animationSets));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 273 */     return Objects.hash(new Object[] { this.model, this.texture, this.animationSets });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelOverride.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */