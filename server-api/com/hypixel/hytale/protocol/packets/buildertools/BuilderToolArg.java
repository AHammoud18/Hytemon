/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderToolArg
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 33;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 49;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public boolean required;
/*     */   @Nonnull
/*  21 */   public BuilderToolArgType argType = BuilderToolArgType.Bool;
/*     */   
/*     */   @Nullable
/*     */   public BuilderToolBoolArg boolArg;
/*     */   
/*     */   @Nullable
/*     */   public BuilderToolFloatArg floatArg;
/*     */   
/*     */   @Nullable
/*     */   public BuilderToolIntArg intArg;
/*     */   
/*     */   @Nullable
/*     */   public BuilderToolStringArg stringArg;
/*     */   @Nullable
/*     */   public BuilderToolBlockArg blockArg;
/*     */   
/*     */   public BuilderToolArg(boolean required, @Nonnull BuilderToolArgType argType, @Nullable BuilderToolBoolArg boolArg, @Nullable BuilderToolFloatArg floatArg, @Nullable BuilderToolIntArg intArg, @Nullable BuilderToolStringArg stringArg, @Nullable BuilderToolBlockArg blockArg, @Nullable BuilderToolMaskArg maskArg, @Nullable BuilderToolBrushShapeArg brushShapeArg, @Nullable BuilderToolBrushOriginArg brushOriginArg, @Nullable BuilderToolBrushAxisArg brushAxisArg, @Nullable BuilderToolRotationArg rotationArg, @Nullable BuilderToolOptionArg optionArg) {
/*  38 */     this.required = required;
/*  39 */     this.argType = argType;
/*  40 */     this.boolArg = boolArg;
/*  41 */     this.floatArg = floatArg;
/*  42 */     this.intArg = intArg;
/*  43 */     this.stringArg = stringArg;
/*  44 */     this.blockArg = blockArg;
/*  45 */     this.maskArg = maskArg;
/*  46 */     this.brushShapeArg = brushShapeArg;
/*  47 */     this.brushOriginArg = brushOriginArg;
/*  48 */     this.brushAxisArg = brushAxisArg;
/*  49 */     this.rotationArg = rotationArg;
/*  50 */     this.optionArg = optionArg; } @Nullable public BuilderToolMaskArg maskArg; @Nullable public BuilderToolBrushShapeArg brushShapeArg; @Nullable
/*     */   public BuilderToolBrushOriginArg brushOriginArg; @Nullable
/*     */   public BuilderToolBrushAxisArg brushAxisArg; @Nullable
/*     */   public BuilderToolRotationArg rotationArg; @Nullable
/*  54 */   public BuilderToolOptionArg optionArg; public BuilderToolArg(@Nonnull BuilderToolArg other) { this.required = other.required;
/*  55 */     this.argType = other.argType;
/*  56 */     this.boolArg = other.boolArg;
/*  57 */     this.floatArg = other.floatArg;
/*  58 */     this.intArg = other.intArg;
/*  59 */     this.stringArg = other.stringArg;
/*  60 */     this.blockArg = other.blockArg;
/*  61 */     this.maskArg = other.maskArg;
/*  62 */     this.brushShapeArg = other.brushShapeArg;
/*  63 */     this.brushOriginArg = other.brushOriginArg;
/*  64 */     this.brushAxisArg = other.brushAxisArg;
/*  65 */     this.rotationArg = other.rotationArg;
/*  66 */     this.optionArg = other.optionArg; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolArg deserialize(@Nonnull ByteBuf buf, int offset) {
/*  71 */     BuilderToolArg obj = new BuilderToolArg();
/*  72 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  73 */     obj.required = (buf.getByte(offset + 2) != 0);
/*  74 */     obj.argType = BuilderToolArgType.fromValue(buf.getByte(offset + 3));
/*  75 */     if ((nullBits[0] & 0x1) != 0) obj.boolArg = BuilderToolBoolArg.deserialize(buf, offset + 4); 
/*  76 */     if ((nullBits[0] & 0x2) != 0) obj.floatArg = BuilderToolFloatArg.deserialize(buf, offset + 5); 
/*  77 */     if ((nullBits[0] & 0x4) != 0) obj.intArg = BuilderToolIntArg.deserialize(buf, offset + 17); 
/*  78 */     if ((nullBits[0] & 0x40) != 0) obj.brushShapeArg = BuilderToolBrushShapeArg.deserialize(buf, offset + 29); 
/*  79 */     if ((nullBits[0] & 0x80) != 0) obj.brushOriginArg = BuilderToolBrushOriginArg.deserialize(buf, offset + 30); 
/*  80 */     if ((nullBits[1] & 0x1) != 0) obj.brushAxisArg = BuilderToolBrushAxisArg.deserialize(buf, offset + 31); 
/*  81 */     if ((nullBits[1] & 0x2) != 0) obj.rotationArg = BuilderToolRotationArg.deserialize(buf, offset + 32);
/*     */     
/*  83 */     if ((nullBits[0] & 0x8) != 0) {
/*  84 */       int varPos0 = offset + 49 + buf.getIntLE(offset + 33);
/*  85 */       obj.stringArg = BuilderToolStringArg.deserialize(buf, varPos0);
/*     */     } 
/*  87 */     if ((nullBits[0] & 0x10) != 0) {
/*  88 */       int varPos1 = offset + 49 + buf.getIntLE(offset + 37);
/*  89 */       obj.blockArg = BuilderToolBlockArg.deserialize(buf, varPos1);
/*     */     } 
/*  91 */     if ((nullBits[0] & 0x20) != 0) {
/*  92 */       int varPos2 = offset + 49 + buf.getIntLE(offset + 41);
/*  93 */       obj.maskArg = BuilderToolMaskArg.deserialize(buf, varPos2);
/*     */     } 
/*  95 */     if ((nullBits[1] & 0x4) != 0) {
/*  96 */       int varPos3 = offset + 49 + buf.getIntLE(offset + 45);
/*  97 */       obj.optionArg = BuilderToolOptionArg.deserialize(buf, varPos3);
/*     */     } 
/*     */     
/* 100 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 104 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 105 */     int maxEnd = 49;
/* 106 */     if ((nullBits[0] & 0x8) != 0) {
/* 107 */       int fieldOffset0 = buf.getIntLE(offset + 33);
/* 108 */       int pos0 = offset + 49 + fieldOffset0;
/* 109 */       pos0 += BuilderToolStringArg.computeBytesConsumed(buf, pos0);
/* 110 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 112 */     if ((nullBits[0] & 0x10) != 0) {
/* 113 */       int fieldOffset1 = buf.getIntLE(offset + 37);
/* 114 */       int pos1 = offset + 49 + fieldOffset1;
/* 115 */       pos1 += BuilderToolBlockArg.computeBytesConsumed(buf, pos1);
/* 116 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 118 */     if ((nullBits[0] & 0x20) != 0) {
/* 119 */       int fieldOffset2 = buf.getIntLE(offset + 41);
/* 120 */       int pos2 = offset + 49 + fieldOffset2;
/* 121 */       pos2 += BuilderToolMaskArg.computeBytesConsumed(buf, pos2);
/* 122 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 124 */     if ((nullBits[1] & 0x4) != 0) {
/* 125 */       int fieldOffset3 = buf.getIntLE(offset + 45);
/* 126 */       int pos3 = offset + 49 + fieldOffset3;
/* 127 */       pos3 += BuilderToolOptionArg.computeBytesConsumed(buf, pos3);
/* 128 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 130 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 135 */     int startPos = buf.writerIndex();
/* 136 */     byte[] nullBits = new byte[2];
/* 137 */     if (this.boolArg != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 138 */     if (this.floatArg != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 139 */     if (this.intArg != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 140 */     if (this.stringArg != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 141 */     if (this.blockArg != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 142 */     if (this.maskArg != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 143 */     if (this.brushShapeArg != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 144 */     if (this.brushOriginArg != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 145 */     if (this.brushAxisArg != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 146 */     if (this.rotationArg != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 147 */     if (this.optionArg != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/* 148 */     buf.writeBytes(nullBits);
/*     */     
/* 150 */     buf.writeByte(this.required ? 1 : 0);
/* 151 */     buf.writeByte(this.argType.getValue());
/* 152 */     if (this.boolArg != null) { this.boolArg.serialize(buf); } else { buf.writeZero(1); }
/* 153 */      if (this.floatArg != null) { this.floatArg.serialize(buf); } else { buf.writeZero(12); }
/* 154 */      if (this.intArg != null) { this.intArg.serialize(buf); } else { buf.writeZero(12); }
/* 155 */      if (this.brushShapeArg != null) { this.brushShapeArg.serialize(buf); } else { buf.writeZero(1); }
/* 156 */      if (this.brushOriginArg != null) { this.brushOriginArg.serialize(buf); } else { buf.writeZero(1); }
/* 157 */      if (this.brushAxisArg != null) { this.brushAxisArg.serialize(buf); } else { buf.writeZero(1); }
/* 158 */      if (this.rotationArg != null) { this.rotationArg.serialize(buf); } else { buf.writeZero(1); }
/*     */     
/* 160 */     int stringArgOffsetSlot = buf.writerIndex();
/* 161 */     buf.writeIntLE(0);
/* 162 */     int blockArgOffsetSlot = buf.writerIndex();
/* 163 */     buf.writeIntLE(0);
/* 164 */     int maskArgOffsetSlot = buf.writerIndex();
/* 165 */     buf.writeIntLE(0);
/* 166 */     int optionArgOffsetSlot = buf.writerIndex();
/* 167 */     buf.writeIntLE(0);
/*     */     
/* 169 */     int varBlockStart = buf.writerIndex();
/* 170 */     if (this.stringArg != null) {
/* 171 */       buf.setIntLE(stringArgOffsetSlot, buf.writerIndex() - varBlockStart);
/* 172 */       this.stringArg.serialize(buf);
/*     */     } else {
/* 174 */       buf.setIntLE(stringArgOffsetSlot, -1);
/*     */     } 
/* 176 */     if (this.blockArg != null) {
/* 177 */       buf.setIntLE(blockArgOffsetSlot, buf.writerIndex() - varBlockStart);
/* 178 */       this.blockArg.serialize(buf);
/*     */     } else {
/* 180 */       buf.setIntLE(blockArgOffsetSlot, -1);
/*     */     } 
/* 182 */     if (this.maskArg != null) {
/* 183 */       buf.setIntLE(maskArgOffsetSlot, buf.writerIndex() - varBlockStart);
/* 184 */       this.maskArg.serialize(buf);
/*     */     } else {
/* 186 */       buf.setIntLE(maskArgOffsetSlot, -1);
/*     */     } 
/* 188 */     if (this.optionArg != null) {
/* 189 */       buf.setIntLE(optionArgOffsetSlot, buf.writerIndex() - varBlockStart);
/* 190 */       this.optionArg.serialize(buf);
/*     */     } else {
/* 192 */       buf.setIntLE(optionArgOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 198 */     int size = 49;
/* 199 */     if (this.stringArg != null) size += this.stringArg.computeSize(); 
/* 200 */     if (this.blockArg != null) size += this.blockArg.computeSize(); 
/* 201 */     if (this.maskArg != null) size += this.maskArg.computeSize(); 
/* 202 */     if (this.optionArg != null) size += this.optionArg.computeSize();
/*     */     
/* 204 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 208 */     if (buffer.readableBytes() - offset < 49) {
/* 209 */       return ValidationResult.error("Buffer too small: expected at least 49 bytes");
/*     */     }
/*     */     
/* 212 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 214 */     if ((nullBits[0] & 0x8) != 0) {
/* 215 */       int stringArgOffset = buffer.getIntLE(offset + 33);
/* 216 */       if (stringArgOffset < 0) {
/* 217 */         return ValidationResult.error("Invalid offset for StringArg");
/*     */       }
/* 219 */       int pos = offset + 49 + stringArgOffset;
/* 220 */       if (pos >= buffer.writerIndex()) {
/* 221 */         return ValidationResult.error("Offset out of bounds for StringArg");
/*     */       }
/* 223 */       ValidationResult stringArgResult = BuilderToolStringArg.validateStructure(buffer, pos);
/* 224 */       if (!stringArgResult.isValid()) {
/* 225 */         return ValidationResult.error("Invalid StringArg: " + stringArgResult.error());
/*     */       }
/* 227 */       pos += BuilderToolStringArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 230 */     if ((nullBits[0] & 0x10) != 0) {
/* 231 */       int blockArgOffset = buffer.getIntLE(offset + 37);
/* 232 */       if (blockArgOffset < 0) {
/* 233 */         return ValidationResult.error("Invalid offset for BlockArg");
/*     */       }
/* 235 */       int pos = offset + 49 + blockArgOffset;
/* 236 */       if (pos >= buffer.writerIndex()) {
/* 237 */         return ValidationResult.error("Offset out of bounds for BlockArg");
/*     */       }
/* 239 */       ValidationResult blockArgResult = BuilderToolBlockArg.validateStructure(buffer, pos);
/* 240 */       if (!blockArgResult.isValid()) {
/* 241 */         return ValidationResult.error("Invalid BlockArg: " + blockArgResult.error());
/*     */       }
/* 243 */       pos += BuilderToolBlockArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 246 */     if ((nullBits[0] & 0x20) != 0) {
/* 247 */       int maskArgOffset = buffer.getIntLE(offset + 41);
/* 248 */       if (maskArgOffset < 0) {
/* 249 */         return ValidationResult.error("Invalid offset for MaskArg");
/*     */       }
/* 251 */       int pos = offset + 49 + maskArgOffset;
/* 252 */       if (pos >= buffer.writerIndex()) {
/* 253 */         return ValidationResult.error("Offset out of bounds for MaskArg");
/*     */       }
/* 255 */       ValidationResult maskArgResult = BuilderToolMaskArg.validateStructure(buffer, pos);
/* 256 */       if (!maskArgResult.isValid()) {
/* 257 */         return ValidationResult.error("Invalid MaskArg: " + maskArgResult.error());
/*     */       }
/* 259 */       pos += BuilderToolMaskArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 262 */     if ((nullBits[1] & 0x4) != 0) {
/* 263 */       int optionArgOffset = buffer.getIntLE(offset + 45);
/* 264 */       if (optionArgOffset < 0) {
/* 265 */         return ValidationResult.error("Invalid offset for OptionArg");
/*     */       }
/* 267 */       int pos = offset + 49 + optionArgOffset;
/* 268 */       if (pos >= buffer.writerIndex()) {
/* 269 */         return ValidationResult.error("Offset out of bounds for OptionArg");
/*     */       }
/* 271 */       ValidationResult optionArgResult = BuilderToolOptionArg.validateStructure(buffer, pos);
/* 272 */       if (!optionArgResult.isValid()) {
/* 273 */         return ValidationResult.error("Invalid OptionArg: " + optionArgResult.error());
/*     */       }
/* 275 */       pos += BuilderToolOptionArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 277 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolArg clone() {
/* 281 */     BuilderToolArg copy = new BuilderToolArg();
/* 282 */     copy.required = this.required;
/* 283 */     copy.argType = this.argType;
/* 284 */     copy.boolArg = (this.boolArg != null) ? this.boolArg.clone() : null;
/* 285 */     copy.floatArg = (this.floatArg != null) ? this.floatArg.clone() : null;
/* 286 */     copy.intArg = (this.intArg != null) ? this.intArg.clone() : null;
/* 287 */     copy.stringArg = (this.stringArg != null) ? this.stringArg.clone() : null;
/* 288 */     copy.blockArg = (this.blockArg != null) ? this.blockArg.clone() : null;
/* 289 */     copy.maskArg = (this.maskArg != null) ? this.maskArg.clone() : null;
/* 290 */     copy.brushShapeArg = (this.brushShapeArg != null) ? this.brushShapeArg.clone() : null;
/* 291 */     copy.brushOriginArg = (this.brushOriginArg != null) ? this.brushOriginArg.clone() : null;
/* 292 */     copy.brushAxisArg = (this.brushAxisArg != null) ? this.brushAxisArg.clone() : null;
/* 293 */     copy.rotationArg = (this.rotationArg != null) ? this.rotationArg.clone() : null;
/* 294 */     copy.optionArg = (this.optionArg != null) ? this.optionArg.clone() : null;
/* 295 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolArg other;
/* 301 */     if (this == obj) return true; 
/* 302 */     if (obj instanceof BuilderToolArg) { other = (BuilderToolArg)obj; } else { return false; }
/* 303 */      return (this.required == other.required && Objects.equals(this.argType, other.argType) && Objects.equals(this.boolArg, other.boolArg) && Objects.equals(this.floatArg, other.floatArg) && Objects.equals(this.intArg, other.intArg) && Objects.equals(this.stringArg, other.stringArg) && Objects.equals(this.blockArg, other.blockArg) && Objects.equals(this.maskArg, other.maskArg) && Objects.equals(this.brushShapeArg, other.brushShapeArg) && Objects.equals(this.brushOriginArg, other.brushOriginArg) && Objects.equals(this.brushAxisArg, other.brushAxisArg) && Objects.equals(this.rotationArg, other.rotationArg) && Objects.equals(this.optionArg, other.optionArg));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 308 */     return Objects.hash(new Object[] { Boolean.valueOf(this.required), this.argType, this.boolArg, this.floatArg, this.intArg, this.stringArg, this.blockArg, this.maskArg, this.brushShapeArg, this.brushOriginArg, this.brushAxisArg, this.rotationArg, this.optionArg });
/*     */   }
/*     */   
/*     */   public BuilderToolArg() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */