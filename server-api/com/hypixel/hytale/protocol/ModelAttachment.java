/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ModelAttachment {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 65536037;
/*     */   @Nullable
/*     */   public String model;
/*     */   @Nullable
/*     */   public String texture;
/*     */   @Nullable
/*     */   public String gradientSet;
/*     */   @Nullable
/*     */   public String gradientId;
/*     */   
/*     */   public ModelAttachment() {}
/*     */   
/*     */   public ModelAttachment(@Nullable String model, @Nullable String texture, @Nullable String gradientSet, @Nullable String gradientId) {
/*  29 */     this.model = model;
/*  30 */     this.texture = texture;
/*  31 */     this.gradientSet = gradientSet;
/*  32 */     this.gradientId = gradientId;
/*     */   }
/*     */   
/*     */   public ModelAttachment(@Nonnull ModelAttachment other) {
/*  36 */     this.model = other.model;
/*  37 */     this.texture = other.texture;
/*  38 */     this.gradientSet = other.gradientSet;
/*  39 */     this.gradientId = other.gradientId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModelAttachment deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     ModelAttachment obj = new ModelAttachment();
/*  45 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  47 */     if ((nullBits & 0x1) != 0) {
/*  48 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 1);
/*  49 */       int modelLen = VarInt.peek(buf, varPos0);
/*  50 */       if (modelLen < 0) throw ProtocolException.negativeLength("Model", modelLen); 
/*  51 */       if (modelLen > 4096000) throw ProtocolException.stringTooLong("Model", modelLen, 4096000); 
/*  52 */       obj.model = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  54 */     if ((nullBits & 0x2) != 0) {
/*  55 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 5);
/*  56 */       int textureLen = VarInt.peek(buf, varPos1);
/*  57 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/*  58 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/*  59 */       obj.texture = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  61 */     if ((nullBits & 0x4) != 0) {
/*  62 */       int varPos2 = offset + 17 + buf.getIntLE(offset + 9);
/*  63 */       int gradientSetLen = VarInt.peek(buf, varPos2);
/*  64 */       if (gradientSetLen < 0) throw ProtocolException.negativeLength("GradientSet", gradientSetLen); 
/*  65 */       if (gradientSetLen > 4096000) throw ProtocolException.stringTooLong("GradientSet", gradientSetLen, 4096000); 
/*  66 */       obj.gradientSet = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  68 */     if ((nullBits & 0x8) != 0) {
/*  69 */       int varPos3 = offset + 17 + buf.getIntLE(offset + 13);
/*  70 */       int gradientIdLen = VarInt.peek(buf, varPos3);
/*  71 */       if (gradientIdLen < 0) throw ProtocolException.negativeLength("GradientId", gradientIdLen); 
/*  72 */       if (gradientIdLen > 4096000) throw ProtocolException.stringTooLong("GradientId", gradientIdLen, 4096000); 
/*  73 */       obj.gradientId = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     byte nullBits = buf.getByte(offset);
/*  81 */     int maxEnd = 17;
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  84 */       int pos0 = offset + 17 + fieldOffset0;
/*  85 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  86 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  88 */     if ((nullBits & 0x2) != 0) {
/*  89 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  90 */       int pos1 = offset + 17 + fieldOffset1;
/*  91 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  92 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  94 */     if ((nullBits & 0x4) != 0) {
/*  95 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/*  96 */       int pos2 = offset + 17 + fieldOffset2;
/*  97 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  98 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 100 */     if ((nullBits & 0x8) != 0) {
/* 101 */       int fieldOffset3 = buf.getIntLE(offset + 13);
/* 102 */       int pos3 = offset + 17 + fieldOffset3;
/* 103 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 104 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 106 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 111 */     int startPos = buf.writerIndex();
/* 112 */     byte nullBits = 0;
/* 113 */     if (this.model != null) nullBits = (byte)(nullBits | 0x1); 
/* 114 */     if (this.texture != null) nullBits = (byte)(nullBits | 0x2); 
/* 115 */     if (this.gradientSet != null) nullBits = (byte)(nullBits | 0x4); 
/* 116 */     if (this.gradientId != null) nullBits = (byte)(nullBits | 0x8); 
/* 117 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 120 */     int modelOffsetSlot = buf.writerIndex();
/* 121 */     buf.writeIntLE(0);
/* 122 */     int textureOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/* 124 */     int gradientSetOffsetSlot = buf.writerIndex();
/* 125 */     buf.writeIntLE(0);
/* 126 */     int gradientIdOffsetSlot = buf.writerIndex();
/* 127 */     buf.writeIntLE(0);
/*     */     
/* 129 */     int varBlockStart = buf.writerIndex();
/* 130 */     if (this.model != null) {
/* 131 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 132 */       PacketIO.writeVarString(buf, this.model, 4096000);
/*     */     } else {
/* 134 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/* 136 */     if (this.texture != null) {
/* 137 */       buf.setIntLE(textureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */     } else {
/* 140 */       buf.setIntLE(textureOffsetSlot, -1);
/*     */     } 
/* 142 */     if (this.gradientSet != null) {
/* 143 */       buf.setIntLE(gradientSetOffsetSlot, buf.writerIndex() - varBlockStart);
/* 144 */       PacketIO.writeVarString(buf, this.gradientSet, 4096000);
/*     */     } else {
/* 146 */       buf.setIntLE(gradientSetOffsetSlot, -1);
/*     */     } 
/* 148 */     if (this.gradientId != null) {
/* 149 */       buf.setIntLE(gradientIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 150 */       PacketIO.writeVarString(buf, this.gradientId, 4096000);
/*     */     } else {
/* 152 */       buf.setIntLE(gradientIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 158 */     int size = 17;
/* 159 */     if (this.model != null) size += PacketIO.stringSize(this.model); 
/* 160 */     if (this.texture != null) size += PacketIO.stringSize(this.texture); 
/* 161 */     if (this.gradientSet != null) size += PacketIO.stringSize(this.gradientSet); 
/* 162 */     if (this.gradientId != null) size += PacketIO.stringSize(this.gradientId);
/*     */     
/* 164 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 168 */     if (buffer.readableBytes() - offset < 17) {
/* 169 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 172 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 175 */     if ((nullBits & 0x1) != 0) {
/* 176 */       int modelOffset = buffer.getIntLE(offset + 1);
/* 177 */       if (modelOffset < 0) {
/* 178 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 180 */       int pos = offset + 17 + modelOffset;
/* 181 */       if (pos >= buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 184 */       int modelLen = VarInt.peek(buffer, pos);
/* 185 */       if (modelLen < 0) {
/* 186 */         return ValidationResult.error("Invalid string length for Model");
/*     */       }
/* 188 */       if (modelLen > 4096000) {
/* 189 */         return ValidationResult.error("Model exceeds max length 4096000");
/*     */       }
/* 191 */       pos += VarInt.length(buffer, pos);
/* 192 */       pos += modelLen;
/* 193 */       if (pos > buffer.writerIndex()) {
/* 194 */         return ValidationResult.error("Buffer overflow reading Model");
/*     */       }
/*     */     } 
/*     */     
/* 198 */     if ((nullBits & 0x2) != 0) {
/* 199 */       int textureOffset = buffer.getIntLE(offset + 5);
/* 200 */       if (textureOffset < 0) {
/* 201 */         return ValidationResult.error("Invalid offset for Texture");
/*     */       }
/* 203 */       int pos = offset + 17 + textureOffset;
/* 204 */       if (pos >= buffer.writerIndex()) {
/* 205 */         return ValidationResult.error("Offset out of bounds for Texture");
/*     */       }
/* 207 */       int textureLen = VarInt.peek(buffer, pos);
/* 208 */       if (textureLen < 0) {
/* 209 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/* 211 */       if (textureLen > 4096000) {
/* 212 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/* 214 */       pos += VarInt.length(buffer, pos);
/* 215 */       pos += textureLen;
/* 216 */       if (pos > buffer.writerIndex()) {
/* 217 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/*     */     
/* 221 */     if ((nullBits & 0x4) != 0) {
/* 222 */       int gradientSetOffset = buffer.getIntLE(offset + 9);
/* 223 */       if (gradientSetOffset < 0) {
/* 224 */         return ValidationResult.error("Invalid offset for GradientSet");
/*     */       }
/* 226 */       int pos = offset + 17 + gradientSetOffset;
/* 227 */       if (pos >= buffer.writerIndex()) {
/* 228 */         return ValidationResult.error("Offset out of bounds for GradientSet");
/*     */       }
/* 230 */       int gradientSetLen = VarInt.peek(buffer, pos);
/* 231 */       if (gradientSetLen < 0) {
/* 232 */         return ValidationResult.error("Invalid string length for GradientSet");
/*     */       }
/* 234 */       if (gradientSetLen > 4096000) {
/* 235 */         return ValidationResult.error("GradientSet exceeds max length 4096000");
/*     */       }
/* 237 */       pos += VarInt.length(buffer, pos);
/* 238 */       pos += gradientSetLen;
/* 239 */       if (pos > buffer.writerIndex()) {
/* 240 */         return ValidationResult.error("Buffer overflow reading GradientSet");
/*     */       }
/*     */     } 
/*     */     
/* 244 */     if ((nullBits & 0x8) != 0) {
/* 245 */       int gradientIdOffset = buffer.getIntLE(offset + 13);
/* 246 */       if (gradientIdOffset < 0) {
/* 247 */         return ValidationResult.error("Invalid offset for GradientId");
/*     */       }
/* 249 */       int pos = offset + 17 + gradientIdOffset;
/* 250 */       if (pos >= buffer.writerIndex()) {
/* 251 */         return ValidationResult.error("Offset out of bounds for GradientId");
/*     */       }
/* 253 */       int gradientIdLen = VarInt.peek(buffer, pos);
/* 254 */       if (gradientIdLen < 0) {
/* 255 */         return ValidationResult.error("Invalid string length for GradientId");
/*     */       }
/* 257 */       if (gradientIdLen > 4096000) {
/* 258 */         return ValidationResult.error("GradientId exceeds max length 4096000");
/*     */       }
/* 260 */       pos += VarInt.length(buffer, pos);
/* 261 */       pos += gradientIdLen;
/* 262 */       if (pos > buffer.writerIndex()) {
/* 263 */         return ValidationResult.error("Buffer overflow reading GradientId");
/*     */       }
/*     */     } 
/* 266 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelAttachment clone() {
/* 270 */     ModelAttachment copy = new ModelAttachment();
/* 271 */     copy.model = this.model;
/* 272 */     copy.texture = this.texture;
/* 273 */     copy.gradientSet = this.gradientSet;
/* 274 */     copy.gradientId = this.gradientId;
/* 275 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelAttachment other;
/* 281 */     if (this == obj) return true; 
/* 282 */     if (obj instanceof ModelAttachment) { other = (ModelAttachment)obj; } else { return false; }
/* 283 */      return (Objects.equals(this.model, other.model) && Objects.equals(this.texture, other.texture) && Objects.equals(this.gradientSet, other.gradientSet) && Objects.equals(this.gradientId, other.gradientId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 288 */     return Objects.hash(new Object[] { this.model, this.texture, this.gradientSet, this.gradientId });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelAttachment.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */