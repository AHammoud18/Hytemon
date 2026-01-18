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
/*     */ public class Cloud
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   
/*     */   public Cloud(@Nullable String texture, @Nullable Map<Float, Float> speeds, @Nullable Map<Float, ColorAlpha> colors) {
/*  28 */     this.texture = texture;
/*  29 */     this.speeds = speeds;
/*  30 */     this.colors = colors; } public static final int MAX_SIZE = 81920028; @Nullable
/*     */   public String texture; @Nullable
/*     */   public Map<Float, Float> speeds; @Nullable
/*     */   public Map<Float, ColorAlpha> colors; public Cloud() {} public Cloud(@Nonnull Cloud other) {
/*  34 */     this.texture = other.texture;
/*  35 */     this.speeds = other.speeds;
/*  36 */     this.colors = other.colors;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Cloud deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     Cloud obj = new Cloud();
/*  42 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  44 */     if ((nullBits & 0x1) != 0) {
/*  45 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  46 */       int textureLen = VarInt.peek(buf, varPos0);
/*  47 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/*  48 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/*  49 */       obj.texture = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  51 */     if ((nullBits & 0x2) != 0) {
/*  52 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  53 */       int speedsCount = VarInt.peek(buf, varPos1);
/*  54 */       if (speedsCount < 0) throw ProtocolException.negativeLength("Speeds", speedsCount); 
/*  55 */       if (speedsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Speeds", speedsCount, 4096000); 
/*  56 */       int varIntLen = VarInt.length(buf, varPos1);
/*  57 */       obj.speeds = new HashMap<>(speedsCount);
/*  58 */       int dictPos = varPos1 + varIntLen;
/*  59 */       for (int i = 0; i < speedsCount; i++) {
/*  60 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  61 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/*  62 */         if (obj.speeds.put(Float.valueOf(key), Float.valueOf(val)) != null)
/*  63 */           throw ProtocolException.duplicateKey("speeds", Float.valueOf(key)); 
/*     */       } 
/*     */     } 
/*  66 */     if ((nullBits & 0x4) != 0) {
/*  67 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  68 */       int colorsCount = VarInt.peek(buf, varPos2);
/*  69 */       if (colorsCount < 0) throw ProtocolException.negativeLength("Colors", colorsCount); 
/*  70 */       if (colorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Colors", colorsCount, 4096000); 
/*  71 */       int varIntLen = VarInt.length(buf, varPos2);
/*  72 */       obj.colors = new HashMap<>(colorsCount);
/*  73 */       int dictPos = varPos2 + varIntLen;
/*  74 */       for (int i = 0; i < colorsCount; i++) {
/*  75 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  76 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  77 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  78 */         if (obj.colors.put(Float.valueOf(key), val) != null) {
/*  79 */           throw ProtocolException.duplicateKey("colors", Float.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/*  83 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  87 */     byte nullBits = buf.getByte(offset);
/*  88 */     int maxEnd = 13;
/*  89 */     if ((nullBits & 0x1) != 0) {
/*  90 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  91 */       int pos0 = offset + 13 + fieldOffset0;
/*  92 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  93 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  95 */     if ((nullBits & 0x2) != 0) {
/*  96 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  97 */       int pos1 = offset + 13 + fieldOffset1;
/*  98 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  99 */       for (int i = 0; i < dictLen; ) { pos1 += 4; pos1 += 4; i++; }
/* 100 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 102 */     if ((nullBits & 0x4) != 0) {
/* 103 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 104 */       int pos2 = offset + 13 + fieldOffset2;
/* 105 */       int dictLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 106 */       for (int i = 0; i < dictLen; ) { pos2 += 4; pos2 += ColorAlpha.computeBytesConsumed(buf, pos2); i++; }
/* 107 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 109 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 114 */     int startPos = buf.writerIndex();
/* 115 */     byte nullBits = 0;
/* 116 */     if (this.texture != null) nullBits = (byte)(nullBits | 0x1); 
/* 117 */     if (this.speeds != null) nullBits = (byte)(nullBits | 0x2); 
/* 118 */     if (this.colors != null) nullBits = (byte)(nullBits | 0x4); 
/* 119 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 122 */     int textureOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/* 124 */     int speedsOffsetSlot = buf.writerIndex();
/* 125 */     buf.writeIntLE(0);
/* 126 */     int colorsOffsetSlot = buf.writerIndex();
/* 127 */     buf.writeIntLE(0);
/*     */     
/* 129 */     int varBlockStart = buf.writerIndex();
/* 130 */     if (this.texture != null) {
/* 131 */       buf.setIntLE(textureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 132 */       PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */     } else {
/* 134 */       buf.setIntLE(textureOffsetSlot, -1);
/*     */     } 
/* 136 */     if (this.speeds != null)
/* 137 */     { buf.setIntLE(speedsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       if (this.speeds.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Speeds", this.speeds.size(), 4096000);  VarInt.write(buf, this.speeds.size()); for (Map.Entry<Float, Float> e : this.speeds.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*     */        }
/* 140 */     else { buf.setIntLE(speedsOffsetSlot, -1); }
/*     */     
/* 142 */     if (this.colors != null)
/* 143 */     { buf.setIntLE(colorsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 144 */       if (this.colors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Colors", this.colors.size(), 4096000);  VarInt.write(buf, this.colors.size()); for (Map.Entry<Float, ColorAlpha> e : this.colors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*     */        }
/* 146 */     else { buf.setIntLE(colorsOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 152 */     int size = 13;
/* 153 */     if (this.texture != null) size += PacketIO.stringSize(this.texture); 
/* 154 */     if (this.speeds != null) size += VarInt.size(this.speeds.size()) + this.speeds.size() * 8; 
/* 155 */     if (this.colors != null) size += VarInt.size(this.colors.size()) + this.colors.size() * 8;
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
/* 169 */       int textureOffset = buffer.getIntLE(offset + 1);
/* 170 */       if (textureOffset < 0) {
/* 171 */         return ValidationResult.error("Invalid offset for Texture");
/*     */       }
/* 173 */       int pos = offset + 13 + textureOffset;
/* 174 */       if (pos >= buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Offset out of bounds for Texture");
/*     */       }
/* 177 */       int textureLen = VarInt.peek(buffer, pos);
/* 178 */       if (textureLen < 0) {
/* 179 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/* 181 */       if (textureLen > 4096000) {
/* 182 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/* 184 */       pos += VarInt.length(buffer, pos);
/* 185 */       pos += textureLen;
/* 186 */       if (pos > buffer.writerIndex()) {
/* 187 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/*     */     
/* 191 */     if ((nullBits & 0x2) != 0) {
/* 192 */       int speedsOffset = buffer.getIntLE(offset + 5);
/* 193 */       if (speedsOffset < 0) {
/* 194 */         return ValidationResult.error("Invalid offset for Speeds");
/*     */       }
/* 196 */       int pos = offset + 13 + speedsOffset;
/* 197 */       if (pos >= buffer.writerIndex()) {
/* 198 */         return ValidationResult.error("Offset out of bounds for Speeds");
/*     */       }
/* 200 */       int speedsCount = VarInt.peek(buffer, pos);
/* 201 */       if (speedsCount < 0) {
/* 202 */         return ValidationResult.error("Invalid dictionary count for Speeds");
/*     */       }
/* 204 */       if (speedsCount > 4096000) {
/* 205 */         return ValidationResult.error("Speeds exceeds max length 4096000");
/*     */       }
/* 207 */       pos += VarInt.length(buffer, pos);
/* 208 */       for (int i = 0; i < speedsCount; i++) {
/* 209 */         pos += 4;
/* 210 */         if (pos > buffer.writerIndex()) {
/* 211 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 213 */         pos += 4;
/* 214 */         if (pos > buffer.writerIndex()) {
/* 215 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     if ((nullBits & 0x4) != 0) {
/* 221 */       int colorsOffset = buffer.getIntLE(offset + 9);
/* 222 */       if (colorsOffset < 0) {
/* 223 */         return ValidationResult.error("Invalid offset for Colors");
/*     */       }
/* 225 */       int pos = offset + 13 + colorsOffset;
/* 226 */       if (pos >= buffer.writerIndex()) {
/* 227 */         return ValidationResult.error("Offset out of bounds for Colors");
/*     */       }
/* 229 */       int colorsCount = VarInt.peek(buffer, pos);
/* 230 */       if (colorsCount < 0) {
/* 231 */         return ValidationResult.error("Invalid dictionary count for Colors");
/*     */       }
/* 233 */       if (colorsCount > 4096000) {
/* 234 */         return ValidationResult.error("Colors exceeds max length 4096000");
/*     */       }
/* 236 */       pos += VarInt.length(buffer, pos);
/* 237 */       for (int i = 0; i < colorsCount; i++) {
/* 238 */         pos += 4;
/* 239 */         if (pos > buffer.writerIndex()) {
/* 240 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 242 */         pos += 4;
/*     */       } 
/*     */     } 
/*     */     
/* 246 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Cloud clone() {
/* 250 */     Cloud copy = new Cloud();
/* 251 */     copy.texture = this.texture;
/* 252 */     copy.speeds = (this.speeds != null) ? new HashMap<>(this.speeds) : null;
/* 253 */     if (this.colors != null) {
/* 254 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 255 */       for (Map.Entry<Float, ColorAlpha> e : this.colors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 256 */       copy.colors = m;
/*     */     } 
/* 258 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Cloud other;
/* 264 */     if (this == obj) return true; 
/* 265 */     if (obj instanceof Cloud) { other = (Cloud)obj; } else { return false; }
/* 266 */      return (Objects.equals(this.texture, other.texture) && Objects.equals(this.speeds, other.speeds) && Objects.equals(this.colors, other.colors));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 271 */     return Objects.hash(new Object[] { this.texture, this.speeds, this.colors });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Cloud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */