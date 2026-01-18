/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Trail
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 61;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 69;
/*     */   public static final int MAX_SIZE = 32768079;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nonnull
/*  27 */   public FXRenderMode renderMode = FXRenderMode.BlendLinear; @Nullable
/*     */   public String texture; public int lifeSpan; public float roll; @Nullable
/*     */   public Edge start; @Nullable
/*     */   public Edge end; public float lightInfluence; @Nullable
/*     */   public IntersectionHighlight intersectionHighlight; public boolean smooth; @Nullable
/*     */   public Vector2i frameSize;
/*     */   @Nullable
/*     */   public Range frameRange;
/*     */   public int frameLifeSpan;
/*     */   
/*     */   public Trail(@Nullable String id, @Nullable String texture, int lifeSpan, float roll, @Nullable Edge start, @Nullable Edge end, float lightInfluence, @Nonnull FXRenderMode renderMode, @Nullable IntersectionHighlight intersectionHighlight, boolean smooth, @Nullable Vector2i frameSize, @Nullable Range frameRange, int frameLifeSpan) {
/*  38 */     this.id = id;
/*  39 */     this.texture = texture;
/*  40 */     this.lifeSpan = lifeSpan;
/*  41 */     this.roll = roll;
/*  42 */     this.start = start;
/*  43 */     this.end = end;
/*  44 */     this.lightInfluence = lightInfluence;
/*  45 */     this.renderMode = renderMode;
/*  46 */     this.intersectionHighlight = intersectionHighlight;
/*  47 */     this.smooth = smooth;
/*  48 */     this.frameSize = frameSize;
/*  49 */     this.frameRange = frameRange;
/*  50 */     this.frameLifeSpan = frameLifeSpan;
/*     */   }
/*     */   
/*     */   public Trail(@Nonnull Trail other) {
/*  54 */     this.id = other.id;
/*  55 */     this.texture = other.texture;
/*  56 */     this.lifeSpan = other.lifeSpan;
/*  57 */     this.roll = other.roll;
/*  58 */     this.start = other.start;
/*  59 */     this.end = other.end;
/*  60 */     this.lightInfluence = other.lightInfluence;
/*  61 */     this.renderMode = other.renderMode;
/*  62 */     this.intersectionHighlight = other.intersectionHighlight;
/*  63 */     this.smooth = other.smooth;
/*  64 */     this.frameSize = other.frameSize;
/*  65 */     this.frameRange = other.frameRange;
/*  66 */     this.frameLifeSpan = other.frameLifeSpan;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Trail deserialize(@Nonnull ByteBuf buf, int offset) {
/*  71 */     Trail obj = new Trail();
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     obj.lifeSpan = buf.getIntLE(offset + 1);
/*  74 */     obj.roll = buf.getFloatLE(offset + 5);
/*  75 */     if ((nullBits & 0x4) != 0) obj.start = Edge.deserialize(buf, offset + 9); 
/*  76 */     if ((nullBits & 0x8) != 0) obj.end = Edge.deserialize(buf, offset + 18); 
/*  77 */     obj.lightInfluence = buf.getFloatLE(offset + 27);
/*  78 */     obj.renderMode = FXRenderMode.fromValue(buf.getByte(offset + 31));
/*  79 */     if ((nullBits & 0x10) != 0) obj.intersectionHighlight = IntersectionHighlight.deserialize(buf, offset + 32); 
/*  80 */     obj.smooth = (buf.getByte(offset + 40) != 0);
/*  81 */     if ((nullBits & 0x20) != 0) obj.frameSize = Vector2i.deserialize(buf, offset + 41); 
/*  82 */     if ((nullBits & 0x40) != 0) obj.frameRange = Range.deserialize(buf, offset + 49); 
/*  83 */     obj.frameLifeSpan = buf.getIntLE(offset + 57);
/*     */     
/*  85 */     if ((nullBits & 0x1) != 0) {
/*  86 */       int varPos0 = offset + 69 + buf.getIntLE(offset + 61);
/*  87 */       int idLen = VarInt.peek(buf, varPos0);
/*  88 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  89 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  90 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  92 */     if ((nullBits & 0x2) != 0) {
/*  93 */       int varPos1 = offset + 69 + buf.getIntLE(offset + 65);
/*  94 */       int textureLen = VarInt.peek(buf, varPos1);
/*  95 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/*  96 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/*  97 */       obj.texture = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 100 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 104 */     byte nullBits = buf.getByte(offset);
/* 105 */     int maxEnd = 69;
/* 106 */     if ((nullBits & 0x1) != 0) {
/* 107 */       int fieldOffset0 = buf.getIntLE(offset + 61);
/* 108 */       int pos0 = offset + 69 + fieldOffset0;
/* 109 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 110 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 112 */     if ((nullBits & 0x2) != 0) {
/* 113 */       int fieldOffset1 = buf.getIntLE(offset + 65);
/* 114 */       int pos1 = offset + 69 + fieldOffset1;
/* 115 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 116 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 118 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 123 */     int startPos = buf.writerIndex();
/* 124 */     byte nullBits = 0;
/* 125 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 126 */     if (this.texture != null) nullBits = (byte)(nullBits | 0x2); 
/* 127 */     if (this.start != null) nullBits = (byte)(nullBits | 0x4); 
/* 128 */     if (this.end != null) nullBits = (byte)(nullBits | 0x8); 
/* 129 */     if (this.intersectionHighlight != null) nullBits = (byte)(nullBits | 0x10); 
/* 130 */     if (this.frameSize != null) nullBits = (byte)(nullBits | 0x20); 
/* 131 */     if (this.frameRange != null) nullBits = (byte)(nullBits | 0x40); 
/* 132 */     buf.writeByte(nullBits);
/*     */     
/* 134 */     buf.writeIntLE(this.lifeSpan);
/* 135 */     buf.writeFloatLE(this.roll);
/* 136 */     if (this.start != null) { this.start.serialize(buf); } else { buf.writeZero(9); }
/* 137 */      if (this.end != null) { this.end.serialize(buf); } else { buf.writeZero(9); }
/* 138 */      buf.writeFloatLE(this.lightInfluence);
/* 139 */     buf.writeByte(this.renderMode.getValue());
/* 140 */     if (this.intersectionHighlight != null) { this.intersectionHighlight.serialize(buf); } else { buf.writeZero(8); }
/* 141 */      buf.writeByte(this.smooth ? 1 : 0);
/* 142 */     if (this.frameSize != null) { this.frameSize.serialize(buf); } else { buf.writeZero(8); }
/* 143 */      if (this.frameRange != null) { this.frameRange.serialize(buf); } else { buf.writeZero(8); }
/* 144 */      buf.writeIntLE(this.frameLifeSpan);
/*     */     
/* 146 */     int idOffsetSlot = buf.writerIndex();
/* 147 */     buf.writeIntLE(0);
/* 148 */     int textureOffsetSlot = buf.writerIndex();
/* 149 */     buf.writeIntLE(0);
/*     */     
/* 151 */     int varBlockStart = buf.writerIndex();
/* 152 */     if (this.id != null) {
/* 153 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 154 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 156 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 158 */     if (this.texture != null) {
/* 159 */       buf.setIntLE(textureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 160 */       PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */     } else {
/* 162 */       buf.setIntLE(textureOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 168 */     int size = 69;
/* 169 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 170 */     if (this.texture != null) size += PacketIO.stringSize(this.texture);
/*     */     
/* 172 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 176 */     if (buffer.readableBytes() - offset < 69) {
/* 177 */       return ValidationResult.error("Buffer too small: expected at least 69 bytes");
/*     */     }
/*     */     
/* 180 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 183 */     if ((nullBits & 0x1) != 0) {
/* 184 */       int idOffset = buffer.getIntLE(offset + 61);
/* 185 */       if (idOffset < 0) {
/* 186 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 188 */       int pos = offset + 69 + idOffset;
/* 189 */       if (pos >= buffer.writerIndex()) {
/* 190 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 192 */       int idLen = VarInt.peek(buffer, pos);
/* 193 */       if (idLen < 0) {
/* 194 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 196 */       if (idLen > 4096000) {
/* 197 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 199 */       pos += VarInt.length(buffer, pos);
/* 200 */       pos += idLen;
/* 201 */       if (pos > buffer.writerIndex()) {
/* 202 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 206 */     if ((nullBits & 0x2) != 0) {
/* 207 */       int textureOffset = buffer.getIntLE(offset + 65);
/* 208 */       if (textureOffset < 0) {
/* 209 */         return ValidationResult.error("Invalid offset for Texture");
/*     */       }
/* 211 */       int pos = offset + 69 + textureOffset;
/* 212 */       if (pos >= buffer.writerIndex()) {
/* 213 */         return ValidationResult.error("Offset out of bounds for Texture");
/*     */       }
/* 215 */       int textureLen = VarInt.peek(buffer, pos);
/* 216 */       if (textureLen < 0) {
/* 217 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/* 219 */       if (textureLen > 4096000) {
/* 220 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/* 222 */       pos += VarInt.length(buffer, pos);
/* 223 */       pos += textureLen;
/* 224 */       if (pos > buffer.writerIndex()) {
/* 225 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/* 228 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Trail clone() {
/* 232 */     Trail copy = new Trail();
/* 233 */     copy.id = this.id;
/* 234 */     copy.texture = this.texture;
/* 235 */     copy.lifeSpan = this.lifeSpan;
/* 236 */     copy.roll = this.roll;
/* 237 */     copy.start = (this.start != null) ? this.start.clone() : null;
/* 238 */     copy.end = (this.end != null) ? this.end.clone() : null;
/* 239 */     copy.lightInfluence = this.lightInfluence;
/* 240 */     copy.renderMode = this.renderMode;
/* 241 */     copy.intersectionHighlight = (this.intersectionHighlight != null) ? this.intersectionHighlight.clone() : null;
/* 242 */     copy.smooth = this.smooth;
/* 243 */     copy.frameSize = (this.frameSize != null) ? this.frameSize.clone() : null;
/* 244 */     copy.frameRange = (this.frameRange != null) ? this.frameRange.clone() : null;
/* 245 */     copy.frameLifeSpan = this.frameLifeSpan;
/* 246 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Trail other;
/* 252 */     if (this == obj) return true; 
/* 253 */     if (obj instanceof Trail) { other = (Trail)obj; } else { return false; }
/* 254 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.texture, other.texture) && this.lifeSpan == other.lifeSpan && this.roll == other.roll && Objects.equals(this.start, other.start) && Objects.equals(this.end, other.end) && this.lightInfluence == other.lightInfluence && Objects.equals(this.renderMode, other.renderMode) && Objects.equals(this.intersectionHighlight, other.intersectionHighlight) && this.smooth == other.smooth && Objects.equals(this.frameSize, other.frameSize) && Objects.equals(this.frameRange, other.frameRange) && this.frameLifeSpan == other.frameLifeSpan);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 259 */     return Objects.hash(new Object[] { this.id, this.texture, Integer.valueOf(this.lifeSpan), Float.valueOf(this.roll), this.start, this.end, Float.valueOf(this.lightInfluence), this.renderMode, this.intersectionHighlight, Boolean.valueOf(this.smooth), this.frameSize, this.frameRange, Integer.valueOf(this.frameLifeSpan) });
/*     */   }
/*     */   
/*     */   public Trail() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Trail.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */