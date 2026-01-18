/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.DebugShape;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class DisplayDebug
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 114;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 27;
/*     */   public static final int MAX_SIZE = 32768037;
/*     */   
/*     */   public int getId() {
/*  26 */     return 114;
/*     */   }
/*     */   @Nonnull
/*  29 */   public DebugShape shape = DebugShape.Sphere;
/*     */   @Nullable
/*     */   public float[] matrix;
/*     */   @Nullable
/*     */   public Vector3f color;
/*     */   public float time;
/*     */   public boolean fade;
/*     */   @Nullable
/*     */   public float[] frustumProjection;
/*     */   
/*     */   public DisplayDebug(@Nonnull DebugShape shape, @Nullable float[] matrix, @Nullable Vector3f color, float time, boolean fade, @Nullable float[] frustumProjection) {
/*  40 */     this.shape = shape;
/*  41 */     this.matrix = matrix;
/*  42 */     this.color = color;
/*  43 */     this.time = time;
/*  44 */     this.fade = fade;
/*  45 */     this.frustumProjection = frustumProjection;
/*     */   }
/*     */   
/*     */   public DisplayDebug(@Nonnull DisplayDebug other) {
/*  49 */     this.shape = other.shape;
/*  50 */     this.matrix = other.matrix;
/*  51 */     this.color = other.color;
/*  52 */     this.time = other.time;
/*  53 */     this.fade = other.fade;
/*  54 */     this.frustumProjection = other.frustumProjection;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DisplayDebug deserialize(@Nonnull ByteBuf buf, int offset) {
/*  59 */     DisplayDebug obj = new DisplayDebug();
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     obj.shape = DebugShape.fromValue(buf.getByte(offset + 1));
/*  62 */     if ((nullBits & 0x2) != 0) obj.color = Vector3f.deserialize(buf, offset + 2); 
/*  63 */     obj.time = buf.getFloatLE(offset + 14);
/*  64 */     obj.fade = (buf.getByte(offset + 18) != 0);
/*     */     
/*  66 */     if ((nullBits & 0x1) != 0) {
/*  67 */       int varPos0 = offset + 27 + buf.getIntLE(offset + 19);
/*  68 */       int matrixCount = VarInt.peek(buf, varPos0);
/*  69 */       if (matrixCount < 0) throw ProtocolException.negativeLength("Matrix", matrixCount); 
/*  70 */       if (matrixCount > 4096000) throw ProtocolException.arrayTooLong("Matrix", matrixCount, 4096000); 
/*  71 */       int varIntLen = VarInt.length(buf, varPos0);
/*  72 */       if ((varPos0 + varIntLen) + matrixCount * 4L > buf.readableBytes())
/*  73 */         throw ProtocolException.bufferTooSmall("Matrix", varPos0 + varIntLen + matrixCount * 4, buf.readableBytes()); 
/*  74 */       obj.matrix = new float[matrixCount];
/*  75 */       for (int i = 0; i < matrixCount; i++) {
/*  76 */         obj.matrix[i] = buf.getFloatLE(varPos0 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*  79 */     if ((nullBits & 0x4) != 0) {
/*  80 */       int varPos1 = offset + 27 + buf.getIntLE(offset + 23);
/*  81 */       int frustumProjectionCount = VarInt.peek(buf, varPos1);
/*  82 */       if (frustumProjectionCount < 0) throw ProtocolException.negativeLength("FrustumProjection", frustumProjectionCount); 
/*  83 */       if (frustumProjectionCount > 4096000) throw ProtocolException.arrayTooLong("FrustumProjection", frustumProjectionCount, 4096000); 
/*  84 */       int varIntLen = VarInt.length(buf, varPos1);
/*  85 */       if ((varPos1 + varIntLen) + frustumProjectionCount * 4L > buf.readableBytes())
/*  86 */         throw ProtocolException.bufferTooSmall("FrustumProjection", varPos1 + varIntLen + frustumProjectionCount * 4, buf.readableBytes()); 
/*  87 */       obj.frustumProjection = new float[frustumProjectionCount];
/*  88 */       for (int i = 0; i < frustumProjectionCount; i++) {
/*  89 */         obj.frustumProjection[i] = buf.getFloatLE(varPos1 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  97 */     byte nullBits = buf.getByte(offset);
/*  98 */     int maxEnd = 27;
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       int fieldOffset0 = buf.getIntLE(offset + 19);
/* 101 */       int pos0 = offset + 27 + fieldOffset0;
/* 102 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 4;
/* 103 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 105 */     if ((nullBits & 0x4) != 0) {
/* 106 */       int fieldOffset1 = buf.getIntLE(offset + 23);
/* 107 */       int pos1 = offset + 27 + fieldOffset1;
/* 108 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/* 109 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 111 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 117 */     int startPos = buf.writerIndex();
/* 118 */     byte nullBits = 0;
/* 119 */     if (this.matrix != null) nullBits = (byte)(nullBits | 0x1); 
/* 120 */     if (this.color != null) nullBits = (byte)(nullBits | 0x2); 
/* 121 */     if (this.frustumProjection != null) nullBits = (byte)(nullBits | 0x4); 
/* 122 */     buf.writeByte(nullBits);
/*     */     
/* 124 */     buf.writeByte(this.shape.getValue());
/* 125 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(12); }
/* 126 */      buf.writeFloatLE(this.time);
/* 127 */     buf.writeByte(this.fade ? 1 : 0);
/*     */     
/* 129 */     int matrixOffsetSlot = buf.writerIndex();
/* 130 */     buf.writeIntLE(0);
/* 131 */     int frustumProjectionOffsetSlot = buf.writerIndex();
/* 132 */     buf.writeIntLE(0);
/*     */     
/* 134 */     int varBlockStart = buf.writerIndex();
/* 135 */     if (this.matrix != null) {
/* 136 */       buf.setIntLE(matrixOffsetSlot, buf.writerIndex() - varBlockStart);
/* 137 */       if (this.matrix.length > 4096000) throw ProtocolException.arrayTooLong("Matrix", this.matrix.length, 4096000);  VarInt.write(buf, this.matrix.length); for (float item : this.matrix) buf.writeFloatLE(item); 
/*     */     } else {
/* 139 */       buf.setIntLE(matrixOffsetSlot, -1);
/*     */     } 
/* 141 */     if (this.frustumProjection != null) {
/* 142 */       buf.setIntLE(frustumProjectionOffsetSlot, buf.writerIndex() - varBlockStart);
/* 143 */       if (this.frustumProjection.length > 4096000) throw ProtocolException.arrayTooLong("FrustumProjection", this.frustumProjection.length, 4096000);  VarInt.write(buf, this.frustumProjection.length); for (float item : this.frustumProjection) buf.writeFloatLE(item); 
/*     */     } else {
/* 145 */       buf.setIntLE(frustumProjectionOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 151 */     int size = 27;
/* 152 */     if (this.matrix != null) size += VarInt.size(this.matrix.length) + this.matrix.length * 4; 
/* 153 */     if (this.frustumProjection != null) size += VarInt.size(this.frustumProjection.length) + this.frustumProjection.length * 4;
/*     */     
/* 155 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 159 */     if (buffer.readableBytes() - offset < 27) {
/* 160 */       return ValidationResult.error("Buffer too small: expected at least 27 bytes");
/*     */     }
/*     */     
/* 163 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 166 */     if ((nullBits & 0x1) != 0) {
/* 167 */       int matrixOffset = buffer.getIntLE(offset + 19);
/* 168 */       if (matrixOffset < 0) {
/* 169 */         return ValidationResult.error("Invalid offset for Matrix");
/*     */       }
/* 171 */       int pos = offset + 27 + matrixOffset;
/* 172 */       if (pos >= buffer.writerIndex()) {
/* 173 */         return ValidationResult.error("Offset out of bounds for Matrix");
/*     */       }
/* 175 */       int matrixCount = VarInt.peek(buffer, pos);
/* 176 */       if (matrixCount < 0) {
/* 177 */         return ValidationResult.error("Invalid array count for Matrix");
/*     */       }
/* 179 */       if (matrixCount > 4096000) {
/* 180 */         return ValidationResult.error("Matrix exceeds max length 4096000");
/*     */       }
/* 182 */       pos += VarInt.length(buffer, pos);
/* 183 */       pos += matrixCount * 4;
/* 184 */       if (pos > buffer.writerIndex()) {
/* 185 */         return ValidationResult.error("Buffer overflow reading Matrix");
/*     */       }
/*     */     } 
/*     */     
/* 189 */     if ((nullBits & 0x4) != 0) {
/* 190 */       int frustumProjectionOffset = buffer.getIntLE(offset + 23);
/* 191 */       if (frustumProjectionOffset < 0) {
/* 192 */         return ValidationResult.error("Invalid offset for FrustumProjection");
/*     */       }
/* 194 */       int pos = offset + 27 + frustumProjectionOffset;
/* 195 */       if (pos >= buffer.writerIndex()) {
/* 196 */         return ValidationResult.error("Offset out of bounds for FrustumProjection");
/*     */       }
/* 198 */       int frustumProjectionCount = VarInt.peek(buffer, pos);
/* 199 */       if (frustumProjectionCount < 0) {
/* 200 */         return ValidationResult.error("Invalid array count for FrustumProjection");
/*     */       }
/* 202 */       if (frustumProjectionCount > 4096000) {
/* 203 */         return ValidationResult.error("FrustumProjection exceeds max length 4096000");
/*     */       }
/* 205 */       pos += VarInt.length(buffer, pos);
/* 206 */       pos += frustumProjectionCount * 4;
/* 207 */       if (pos > buffer.writerIndex()) {
/* 208 */         return ValidationResult.error("Buffer overflow reading FrustumProjection");
/*     */       }
/*     */     } 
/* 211 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DisplayDebug clone() {
/* 215 */     DisplayDebug copy = new DisplayDebug();
/* 216 */     copy.shape = this.shape;
/* 217 */     copy.matrix = (this.matrix != null) ? Arrays.copyOf(this.matrix, this.matrix.length) : null;
/* 218 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 219 */     copy.time = this.time;
/* 220 */     copy.fade = this.fade;
/* 221 */     copy.frustumProjection = (this.frustumProjection != null) ? Arrays.copyOf(this.frustumProjection, this.frustumProjection.length) : null;
/* 222 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DisplayDebug other;
/* 228 */     if (this == obj) return true; 
/* 229 */     if (obj instanceof DisplayDebug) { other = (DisplayDebug)obj; } else { return false; }
/* 230 */      return (Objects.equals(this.shape, other.shape) && Arrays.equals(this.matrix, other.matrix) && Objects.equals(this.color, other.color) && this.time == other.time && this.fade == other.fade && Arrays.equals(this.frustumProjection, other.frustumProjection));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 235 */     int result = 1;
/* 236 */     result = 31 * result + Objects.hashCode(this.shape);
/* 237 */     result = 31 * result + Arrays.hashCode(this.matrix);
/* 238 */     result = 31 * result + Objects.hashCode(this.color);
/* 239 */     result = 31 * result + Float.hashCode(this.time);
/* 240 */     result = 31 * result + Boolean.hashCode(this.fade);
/* 241 */     result = 31 * result + Arrays.hashCode(this.frustumProjection);
/* 242 */     return result;
/*     */   }
/*     */   
/*     */   public DisplayDebug() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\DisplayDebug.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */