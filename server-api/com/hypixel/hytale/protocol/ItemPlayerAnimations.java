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
/*     */ public class ItemPlayerAnimations
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 91;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 103;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   
/*     */   public ItemPlayerAnimations(@Nullable String id, @Nullable Map<String, ItemAnimation> animations, @Nullable WiggleWeights wiggleWeights, @Nullable CameraSettings camera, @Nullable ItemPullbackConfiguration pullbackConfig, boolean useFirstPersonOverride) {
/*  31 */     this.id = id;
/*  32 */     this.animations = animations;
/*  33 */     this.wiggleWeights = wiggleWeights;
/*  34 */     this.camera = camera;
/*  35 */     this.pullbackConfig = pullbackConfig;
/*  36 */     this.useFirstPersonOverride = useFirstPersonOverride; } @Nullable
/*     */   public Map<String, ItemAnimation> animations; @Nullable
/*     */   public WiggleWeights wiggleWeights; @Nullable
/*     */   public CameraSettings camera; @Nullable
/*  40 */   public ItemPullbackConfiguration pullbackConfig; public boolean useFirstPersonOverride; public ItemPlayerAnimations() {} public ItemPlayerAnimations(@Nonnull ItemPlayerAnimations other) { this.id = other.id;
/*  41 */     this.animations = other.animations;
/*  42 */     this.wiggleWeights = other.wiggleWeights;
/*  43 */     this.camera = other.camera;
/*  44 */     this.pullbackConfig = other.pullbackConfig;
/*  45 */     this.useFirstPersonOverride = other.useFirstPersonOverride; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ItemPlayerAnimations deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     ItemPlayerAnimations obj = new ItemPlayerAnimations();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     if ((nullBits & 0x4) != 0) obj.wiggleWeights = WiggleWeights.deserialize(buf, offset + 1); 
/*  53 */     if ((nullBits & 0x10) != 0) obj.pullbackConfig = ItemPullbackConfiguration.deserialize(buf, offset + 41); 
/*  54 */     obj.useFirstPersonOverride = (buf.getByte(offset + 90) != 0);
/*     */     
/*  56 */     if ((nullBits & 0x1) != 0) {
/*  57 */       int varPos0 = offset + 103 + buf.getIntLE(offset + 91);
/*  58 */       int idLen = VarInt.peek(buf, varPos0);
/*  59 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  60 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  61 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  63 */     if ((nullBits & 0x2) != 0) {
/*  64 */       int varPos1 = offset + 103 + buf.getIntLE(offset + 95);
/*  65 */       int animationsCount = VarInt.peek(buf, varPos1);
/*  66 */       if (animationsCount < 0) throw ProtocolException.negativeLength("Animations", animationsCount); 
/*  67 */       if (animationsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Animations", animationsCount, 4096000); 
/*  68 */       int varIntLen = VarInt.length(buf, varPos1);
/*  69 */       obj.animations = new HashMap<>(animationsCount);
/*  70 */       int dictPos = varPos1 + varIntLen;
/*  71 */       for (int i = 0; i < animationsCount; i++) {
/*  72 */         int keyLen = VarInt.peek(buf, dictPos);
/*  73 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  74 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  75 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  76 */         String key = PacketIO.readVarString(buf, dictPos);
/*  77 */         dictPos += keyVarLen + keyLen;
/*  78 */         ItemAnimation val = ItemAnimation.deserialize(buf, dictPos);
/*  79 */         dictPos += ItemAnimation.computeBytesConsumed(buf, dictPos);
/*  80 */         if (obj.animations.put(key, val) != null)
/*  81 */           throw ProtocolException.duplicateKey("animations", key); 
/*     */       } 
/*     */     } 
/*  84 */     if ((nullBits & 0x8) != 0) {
/*  85 */       int varPos2 = offset + 103 + buf.getIntLE(offset + 99);
/*  86 */       obj.camera = CameraSettings.deserialize(buf, varPos2);
/*     */     } 
/*     */     
/*  89 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  93 */     byte nullBits = buf.getByte(offset);
/*  94 */     int maxEnd = 103;
/*  95 */     if ((nullBits & 0x1) != 0) {
/*  96 */       int fieldOffset0 = buf.getIntLE(offset + 91);
/*  97 */       int pos0 = offset + 103 + fieldOffset0;
/*  98 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  99 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 101 */     if ((nullBits & 0x2) != 0) {
/* 102 */       int fieldOffset1 = buf.getIntLE(offset + 95);
/* 103 */       int pos1 = offset + 103 + fieldOffset1;
/* 104 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 105 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; pos1 += ItemAnimation.computeBytesConsumed(buf, pos1); i++; }
/* 106 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 108 */     if ((nullBits & 0x8) != 0) {
/* 109 */       int fieldOffset2 = buf.getIntLE(offset + 99);
/* 110 */       int pos2 = offset + 103 + fieldOffset2;
/* 111 */       pos2 += CameraSettings.computeBytesConsumed(buf, pos2);
/* 112 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 114 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 119 */     int startPos = buf.writerIndex();
/* 120 */     byte nullBits = 0;
/* 121 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 122 */     if (this.animations != null) nullBits = (byte)(nullBits | 0x2); 
/* 123 */     if (this.wiggleWeights != null) nullBits = (byte)(nullBits | 0x4); 
/* 124 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x8); 
/* 125 */     if (this.pullbackConfig != null) nullBits = (byte)(nullBits | 0x10); 
/* 126 */     buf.writeByte(nullBits);
/*     */     
/* 128 */     if (this.wiggleWeights != null) { this.wiggleWeights.serialize(buf); } else { buf.writeZero(40); }
/* 129 */      if (this.pullbackConfig != null) { this.pullbackConfig.serialize(buf); } else { buf.writeZero(49); }
/* 130 */      buf.writeByte(this.useFirstPersonOverride ? 1 : 0);
/*     */     
/* 132 */     int idOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/* 134 */     int animationsOffsetSlot = buf.writerIndex();
/* 135 */     buf.writeIntLE(0);
/* 136 */     int cameraOffsetSlot = buf.writerIndex();
/* 137 */     buf.writeIntLE(0);
/*     */     
/* 139 */     int varBlockStart = buf.writerIndex();
/* 140 */     if (this.id != null) {
/* 141 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 142 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 144 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 146 */     if (this.animations != null)
/* 147 */     { buf.setIntLE(animationsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 148 */       if (this.animations.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Animations", this.animations.size(), 4096000);  VarInt.write(buf, this.animations.size()); for (Map.Entry<String, ItemAnimation> e : this.animations.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((ItemAnimation)e.getValue()).serialize(buf); }
/*     */        }
/* 150 */     else { buf.setIntLE(animationsOffsetSlot, -1); }
/*     */     
/* 152 */     if (this.camera != null) {
/* 153 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 154 */       this.camera.serialize(buf);
/*     */     } else {
/* 156 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 162 */     int size = 103;
/* 163 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 164 */     if (this.animations != null) {
/* 165 */       int animationsSize = 0;
/* 166 */       for (Map.Entry<String, ItemAnimation> kvp : this.animations.entrySet()) animationsSize += PacketIO.stringSize(kvp.getKey()) + ((ItemAnimation)kvp.getValue()).computeSize(); 
/* 167 */       size += VarInt.size(this.animations.size()) + animationsSize;
/*     */     } 
/* 169 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 171 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 175 */     if (buffer.readableBytes() - offset < 103) {
/* 176 */       return ValidationResult.error("Buffer too small: expected at least 103 bytes");
/*     */     }
/*     */     
/* 179 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 182 */     if ((nullBits & 0x1) != 0) {
/* 183 */       int idOffset = buffer.getIntLE(offset + 91);
/* 184 */       if (idOffset < 0) {
/* 185 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 187 */       int pos = offset + 103 + idOffset;
/* 188 */       if (pos >= buffer.writerIndex()) {
/* 189 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 191 */       int idLen = VarInt.peek(buffer, pos);
/* 192 */       if (idLen < 0) {
/* 193 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 195 */       if (idLen > 4096000) {
/* 196 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 198 */       pos += VarInt.length(buffer, pos);
/* 199 */       pos += idLen;
/* 200 */       if (pos > buffer.writerIndex()) {
/* 201 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 205 */     if ((nullBits & 0x2) != 0) {
/* 206 */       int animationsOffset = buffer.getIntLE(offset + 95);
/* 207 */       if (animationsOffset < 0) {
/* 208 */         return ValidationResult.error("Invalid offset for Animations");
/*     */       }
/* 210 */       int pos = offset + 103 + animationsOffset;
/* 211 */       if (pos >= buffer.writerIndex()) {
/* 212 */         return ValidationResult.error("Offset out of bounds for Animations");
/*     */       }
/* 214 */       int animationsCount = VarInt.peek(buffer, pos);
/* 215 */       if (animationsCount < 0) {
/* 216 */         return ValidationResult.error("Invalid dictionary count for Animations");
/*     */       }
/* 218 */       if (animationsCount > 4096000) {
/* 219 */         return ValidationResult.error("Animations exceeds max length 4096000");
/*     */       }
/* 221 */       pos += VarInt.length(buffer, pos);
/* 222 */       for (int i = 0; i < animationsCount; i++) {
/* 223 */         int keyLen = VarInt.peek(buffer, pos);
/* 224 */         if (keyLen < 0) {
/* 225 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 227 */         if (keyLen > 4096000) {
/* 228 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 230 */         pos += VarInt.length(buffer, pos);
/* 231 */         pos += keyLen;
/* 232 */         if (pos > buffer.writerIndex()) {
/* 233 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 235 */         pos += ItemAnimation.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 240 */     if ((nullBits & 0x8) != 0) {
/* 241 */       int cameraOffset = buffer.getIntLE(offset + 99);
/* 242 */       if (cameraOffset < 0) {
/* 243 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 245 */       int pos = offset + 103 + cameraOffset;
/* 246 */       if (pos >= buffer.writerIndex()) {
/* 247 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 249 */       ValidationResult cameraResult = CameraSettings.validateStructure(buffer, pos);
/* 250 */       if (!cameraResult.isValid()) {
/* 251 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 253 */       pos += CameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 255 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemPlayerAnimations clone() {
/* 259 */     ItemPlayerAnimations copy = new ItemPlayerAnimations();
/* 260 */     copy.id = this.id;
/* 261 */     if (this.animations != null) {
/* 262 */       Map<String, ItemAnimation> m = new HashMap<>();
/* 263 */       for (Map.Entry<String, ItemAnimation> e : this.animations.entrySet()) m.put(e.getKey(), ((ItemAnimation)e.getValue()).clone()); 
/* 264 */       copy.animations = m;
/*     */     } 
/* 266 */     copy.wiggleWeights = (this.wiggleWeights != null) ? this.wiggleWeights.clone() : null;
/* 267 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 268 */     copy.pullbackConfig = (this.pullbackConfig != null) ? this.pullbackConfig.clone() : null;
/* 269 */     copy.useFirstPersonOverride = this.useFirstPersonOverride;
/* 270 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemPlayerAnimations other;
/* 276 */     if (this == obj) return true; 
/* 277 */     if (obj instanceof ItemPlayerAnimations) { other = (ItemPlayerAnimations)obj; } else { return false; }
/* 278 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.animations, other.animations) && Objects.equals(this.wiggleWeights, other.wiggleWeights) && Objects.equals(this.camera, other.camera) && Objects.equals(this.pullbackConfig, other.pullbackConfig) && this.useFirstPersonOverride == other.useFirstPersonOverride);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 283 */     return Objects.hash(new Object[] { this.id, this.animations, this.wiggleWeights, this.camera, this.pullbackConfig, Boolean.valueOf(this.useFirstPersonOverride) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemPlayerAnimations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */