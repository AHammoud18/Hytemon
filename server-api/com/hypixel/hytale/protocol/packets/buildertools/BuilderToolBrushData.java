/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderToolBrushData
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 3;
/*     */   public static final int FIXED_BLOCK_SIZE = 48;
/*     */   public static final int VARIABLE_FIELD_COUNT = 9;
/*     */   public static final int VARIABLE_BLOCK_START = 84;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public BuilderToolIntArg width;
/*     */   @Nullable
/*     */   public BuilderToolIntArg height;
/*     */   @Nullable
/*     */   public BuilderToolIntArg thickness;
/*     */   @Nullable
/*     */   public BuilderToolBoolArg capped;
/*     */   @Nullable
/*     */   public BuilderToolBrushShapeArg shape;
/*     */   @Nullable
/*     */   public BuilderToolBrushOriginArg origin;
/*     */   @Nullable
/*     */   public BuilderToolBoolArg originRotation;
/*     */   @Nullable
/*     */   public BuilderToolBrushAxisArg rotationAxis;
/*     */   
/*     */   public BuilderToolBrushData(@Nullable BuilderToolIntArg width, @Nullable BuilderToolIntArg height, @Nullable BuilderToolIntArg thickness, @Nullable BuilderToolBoolArg capped, @Nullable BuilderToolBrushShapeArg shape, @Nullable BuilderToolBrushOriginArg origin, @Nullable BuilderToolBoolArg originRotation, @Nullable BuilderToolBrushAxisArg rotationAxis, @Nullable BuilderToolRotationArg rotationAngle, @Nullable BuilderToolBrushAxisArg mirrorAxis, @Nullable BuilderToolBlockArg material, @Nullable BuilderToolBlockArg[] favoriteMaterials, @Nullable BuilderToolMaskArg mask, @Nullable BuilderToolMaskArg maskAbove, @Nullable BuilderToolMaskArg maskNot, @Nullable BuilderToolMaskArg maskBelow, @Nullable BuilderToolMaskArg maskAdjacent, @Nullable BuilderToolMaskArg maskNeighbor, @Nullable BuilderToolStringArg[] maskCommands, @Nullable BuilderToolBoolArg useMaskCommands, @Nullable BuilderToolBoolArg invertMask) {
/*  46 */     this.width = width;
/*  47 */     this.height = height;
/*  48 */     this.thickness = thickness;
/*  49 */     this.capped = capped;
/*  50 */     this.shape = shape;
/*  51 */     this.origin = origin;
/*  52 */     this.originRotation = originRotation;
/*  53 */     this.rotationAxis = rotationAxis;
/*  54 */     this.rotationAngle = rotationAngle;
/*  55 */     this.mirrorAxis = mirrorAxis;
/*  56 */     this.material = material;
/*  57 */     this.favoriteMaterials = favoriteMaterials;
/*  58 */     this.mask = mask;
/*  59 */     this.maskAbove = maskAbove;
/*  60 */     this.maskNot = maskNot;
/*  61 */     this.maskBelow = maskBelow;
/*  62 */     this.maskAdjacent = maskAdjacent;
/*  63 */     this.maskNeighbor = maskNeighbor;
/*  64 */     this.maskCommands = maskCommands;
/*  65 */     this.useMaskCommands = useMaskCommands;
/*  66 */     this.invertMask = invertMask; } @Nullable public BuilderToolRotationArg rotationAngle; @Nullable public BuilderToolBrushAxisArg mirrorAxis; @Nullable public BuilderToolBlockArg material; @Nullable public BuilderToolBlockArg[] favoriteMaterials; @Nullable public BuilderToolMaskArg mask; @Nullable public BuilderToolMaskArg maskAbove; @Nullable public BuilderToolMaskArg maskNot; @Nullable public BuilderToolMaskArg maskBelow; @Nullable public BuilderToolMaskArg maskAdjacent; @Nullable
/*     */   public BuilderToolMaskArg maskNeighbor; @Nullable
/*     */   public BuilderToolStringArg[] maskCommands; @Nullable
/*     */   public BuilderToolBoolArg useMaskCommands; @Nullable
/*  70 */   public BuilderToolBoolArg invertMask; public BuilderToolBrushData() {} public BuilderToolBrushData(@Nonnull BuilderToolBrushData other) { this.width = other.width;
/*  71 */     this.height = other.height;
/*  72 */     this.thickness = other.thickness;
/*  73 */     this.capped = other.capped;
/*  74 */     this.shape = other.shape;
/*  75 */     this.origin = other.origin;
/*  76 */     this.originRotation = other.originRotation;
/*  77 */     this.rotationAxis = other.rotationAxis;
/*  78 */     this.rotationAngle = other.rotationAngle;
/*  79 */     this.mirrorAxis = other.mirrorAxis;
/*  80 */     this.material = other.material;
/*  81 */     this.favoriteMaterials = other.favoriteMaterials;
/*  82 */     this.mask = other.mask;
/*  83 */     this.maskAbove = other.maskAbove;
/*  84 */     this.maskNot = other.maskNot;
/*  85 */     this.maskBelow = other.maskBelow;
/*  86 */     this.maskAdjacent = other.maskAdjacent;
/*  87 */     this.maskNeighbor = other.maskNeighbor;
/*  88 */     this.maskCommands = other.maskCommands;
/*  89 */     this.useMaskCommands = other.useMaskCommands;
/*  90 */     this.invertMask = other.invertMask; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolBrushData deserialize(@Nonnull ByteBuf buf, int offset) {
/*  95 */     BuilderToolBrushData obj = new BuilderToolBrushData();
/*  96 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 3);
/*  97 */     if ((nullBits[0] & 0x1) != 0) obj.width = BuilderToolIntArg.deserialize(buf, offset + 3); 
/*  98 */     if ((nullBits[0] & 0x2) != 0) obj.height = BuilderToolIntArg.deserialize(buf, offset + 15); 
/*  99 */     if ((nullBits[0] & 0x4) != 0) obj.thickness = BuilderToolIntArg.deserialize(buf, offset + 27); 
/* 100 */     if ((nullBits[0] & 0x8) != 0) obj.capped = BuilderToolBoolArg.deserialize(buf, offset + 39); 
/* 101 */     if ((nullBits[0] & 0x10) != 0) obj.shape = BuilderToolBrushShapeArg.deserialize(buf, offset + 40); 
/* 102 */     if ((nullBits[0] & 0x20) != 0) obj.origin = BuilderToolBrushOriginArg.deserialize(buf, offset + 41); 
/* 103 */     if ((nullBits[0] & 0x40) != 0) obj.originRotation = BuilderToolBoolArg.deserialize(buf, offset + 42); 
/* 104 */     if ((nullBits[0] & 0x80) != 0) obj.rotationAxis = BuilderToolBrushAxisArg.deserialize(buf, offset + 43); 
/* 105 */     if ((nullBits[1] & 0x1) != 0) obj.rotationAngle = BuilderToolRotationArg.deserialize(buf, offset + 44); 
/* 106 */     if ((nullBits[1] & 0x2) != 0) obj.mirrorAxis = BuilderToolBrushAxisArg.deserialize(buf, offset + 45); 
/* 107 */     if ((nullBits[2] & 0x8) != 0) obj.useMaskCommands = BuilderToolBoolArg.deserialize(buf, offset + 46); 
/* 108 */     if ((nullBits[2] & 0x10) != 0) obj.invertMask = BuilderToolBoolArg.deserialize(buf, offset + 47);
/*     */     
/* 110 */     if ((nullBits[1] & 0x4) != 0) {
/* 111 */       int varPos0 = offset + 84 + buf.getIntLE(offset + 48);
/* 112 */       obj.material = BuilderToolBlockArg.deserialize(buf, varPos0);
/*     */     } 
/* 114 */     if ((nullBits[1] & 0x8) != 0) {
/* 115 */       int varPos1 = offset + 84 + buf.getIntLE(offset + 52);
/* 116 */       int favoriteMaterialsCount = VarInt.peek(buf, varPos1);
/* 117 */       if (favoriteMaterialsCount < 0) throw ProtocolException.negativeLength("FavoriteMaterials", favoriteMaterialsCount); 
/* 118 */       if (favoriteMaterialsCount > 4096000) throw ProtocolException.arrayTooLong("FavoriteMaterials", favoriteMaterialsCount, 4096000); 
/* 119 */       int varIntLen = VarInt.length(buf, varPos1);
/* 120 */       if ((varPos1 + varIntLen) + favoriteMaterialsCount * 2L > buf.readableBytes())
/* 121 */         throw ProtocolException.bufferTooSmall("FavoriteMaterials", varPos1 + varIntLen + favoriteMaterialsCount * 2, buf.readableBytes()); 
/* 122 */       obj.favoriteMaterials = new BuilderToolBlockArg[favoriteMaterialsCount];
/* 123 */       int elemPos = varPos1 + varIntLen;
/* 124 */       for (int i = 0; i < favoriteMaterialsCount; i++) {
/* 125 */         obj.favoriteMaterials[i] = BuilderToolBlockArg.deserialize(buf, elemPos);
/* 126 */         elemPos += BuilderToolBlockArg.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 129 */     if ((nullBits[1] & 0x10) != 0) {
/* 130 */       int varPos2 = offset + 84 + buf.getIntLE(offset + 56);
/* 131 */       obj.mask = BuilderToolMaskArg.deserialize(buf, varPos2);
/*     */     } 
/* 133 */     if ((nullBits[1] & 0x20) != 0) {
/* 134 */       int varPos3 = offset + 84 + buf.getIntLE(offset + 60);
/* 135 */       obj.maskAbove = BuilderToolMaskArg.deserialize(buf, varPos3);
/*     */     } 
/* 137 */     if ((nullBits[1] & 0x40) != 0) {
/* 138 */       int varPos4 = offset + 84 + buf.getIntLE(offset + 64);
/* 139 */       obj.maskNot = BuilderToolMaskArg.deserialize(buf, varPos4);
/*     */     } 
/* 141 */     if ((nullBits[1] & 0x80) != 0) {
/* 142 */       int varPos5 = offset + 84 + buf.getIntLE(offset + 68);
/* 143 */       obj.maskBelow = BuilderToolMaskArg.deserialize(buf, varPos5);
/*     */     } 
/* 145 */     if ((nullBits[2] & 0x1) != 0) {
/* 146 */       int varPos6 = offset + 84 + buf.getIntLE(offset + 72);
/* 147 */       obj.maskAdjacent = BuilderToolMaskArg.deserialize(buf, varPos6);
/*     */     } 
/* 149 */     if ((nullBits[2] & 0x2) != 0) {
/* 150 */       int varPos7 = offset + 84 + buf.getIntLE(offset + 76);
/* 151 */       obj.maskNeighbor = BuilderToolMaskArg.deserialize(buf, varPos7);
/*     */     } 
/* 153 */     if ((nullBits[2] & 0x4) != 0) {
/* 154 */       int varPos8 = offset + 84 + buf.getIntLE(offset + 80);
/* 155 */       int maskCommandsCount = VarInt.peek(buf, varPos8);
/* 156 */       if (maskCommandsCount < 0) throw ProtocolException.negativeLength("MaskCommands", maskCommandsCount); 
/* 157 */       if (maskCommandsCount > 4096000) throw ProtocolException.arrayTooLong("MaskCommands", maskCommandsCount, 4096000); 
/* 158 */       int varIntLen = VarInt.length(buf, varPos8);
/* 159 */       if ((varPos8 + varIntLen) + maskCommandsCount * 1L > buf.readableBytes())
/* 160 */         throw ProtocolException.bufferTooSmall("MaskCommands", varPos8 + varIntLen + maskCommandsCount * 1, buf.readableBytes()); 
/* 161 */       obj.maskCommands = new BuilderToolStringArg[maskCommandsCount];
/* 162 */       int elemPos = varPos8 + varIntLen;
/* 163 */       for (int i = 0; i < maskCommandsCount; i++) {
/* 164 */         obj.maskCommands[i] = BuilderToolStringArg.deserialize(buf, elemPos);
/* 165 */         elemPos += BuilderToolStringArg.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 173 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 3);
/* 174 */     int maxEnd = 84;
/* 175 */     if ((nullBits[1] & 0x4) != 0) {
/* 176 */       int fieldOffset0 = buf.getIntLE(offset + 48);
/* 177 */       int pos0 = offset + 84 + fieldOffset0;
/* 178 */       pos0 += BuilderToolBlockArg.computeBytesConsumed(buf, pos0);
/* 179 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 181 */     if ((nullBits[1] & 0x8) != 0) {
/* 182 */       int fieldOffset1 = buf.getIntLE(offset + 52);
/* 183 */       int pos1 = offset + 84 + fieldOffset1;
/* 184 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 185 */       for (int i = 0; i < arrLen; ) { pos1 += BuilderToolBlockArg.computeBytesConsumed(buf, pos1); i++; }
/* 186 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 188 */     if ((nullBits[1] & 0x10) != 0) {
/* 189 */       int fieldOffset2 = buf.getIntLE(offset + 56);
/* 190 */       int pos2 = offset + 84 + fieldOffset2;
/* 191 */       pos2 += BuilderToolMaskArg.computeBytesConsumed(buf, pos2);
/* 192 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 194 */     if ((nullBits[1] & 0x20) != 0) {
/* 195 */       int fieldOffset3 = buf.getIntLE(offset + 60);
/* 196 */       int pos3 = offset + 84 + fieldOffset3;
/* 197 */       pos3 += BuilderToolMaskArg.computeBytesConsumed(buf, pos3);
/* 198 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 200 */     if ((nullBits[1] & 0x40) != 0) {
/* 201 */       int fieldOffset4 = buf.getIntLE(offset + 64);
/* 202 */       int pos4 = offset + 84 + fieldOffset4;
/* 203 */       pos4 += BuilderToolMaskArg.computeBytesConsumed(buf, pos4);
/* 204 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 206 */     if ((nullBits[1] & 0x80) != 0) {
/* 207 */       int fieldOffset5 = buf.getIntLE(offset + 68);
/* 208 */       int pos5 = offset + 84 + fieldOffset5;
/* 209 */       pos5 += BuilderToolMaskArg.computeBytesConsumed(buf, pos5);
/* 210 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 212 */     if ((nullBits[2] & 0x1) != 0) {
/* 213 */       int fieldOffset6 = buf.getIntLE(offset + 72);
/* 214 */       int pos6 = offset + 84 + fieldOffset6;
/* 215 */       pos6 += BuilderToolMaskArg.computeBytesConsumed(buf, pos6);
/* 216 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 218 */     if ((nullBits[2] & 0x2) != 0) {
/* 219 */       int fieldOffset7 = buf.getIntLE(offset + 76);
/* 220 */       int pos7 = offset + 84 + fieldOffset7;
/* 221 */       pos7 += BuilderToolMaskArg.computeBytesConsumed(buf, pos7);
/* 222 */       if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*     */     } 
/* 224 */     if ((nullBits[2] & 0x4) != 0) {
/* 225 */       int fieldOffset8 = buf.getIntLE(offset + 80);
/* 226 */       int pos8 = offset + 84 + fieldOffset8;
/* 227 */       int arrLen = VarInt.peek(buf, pos8); pos8 += VarInt.length(buf, pos8);
/* 228 */       for (int i = 0; i < arrLen; ) { pos8 += BuilderToolStringArg.computeBytesConsumed(buf, pos8); i++; }
/* 229 */        if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*     */     } 
/* 231 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 236 */     int startPos = buf.writerIndex();
/* 237 */     byte[] nullBits = new byte[3];
/* 238 */     if (this.width != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 239 */     if (this.height != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 240 */     if (this.thickness != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 241 */     if (this.capped != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 242 */     if (this.shape != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 243 */     if (this.origin != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 244 */     if (this.originRotation != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 245 */     if (this.rotationAxis != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 246 */     if (this.rotationAngle != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 247 */     if (this.mirrorAxis != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 248 */     if (this.material != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/* 249 */     if (this.favoriteMaterials != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/* 250 */     if (this.mask != null) nullBits[1] = (byte)(nullBits[1] | 0x10); 
/* 251 */     if (this.maskAbove != null) nullBits[1] = (byte)(nullBits[1] | 0x20); 
/* 252 */     if (this.maskNot != null) nullBits[1] = (byte)(nullBits[1] | 0x40); 
/* 253 */     if (this.maskBelow != null) nullBits[1] = (byte)(nullBits[1] | 0x80); 
/* 254 */     if (this.maskAdjacent != null) nullBits[2] = (byte)(nullBits[2] | 0x1); 
/* 255 */     if (this.maskNeighbor != null) nullBits[2] = (byte)(nullBits[2] | 0x2); 
/* 256 */     if (this.maskCommands != null) nullBits[2] = (byte)(nullBits[2] | 0x4); 
/* 257 */     if (this.useMaskCommands != null) nullBits[2] = (byte)(nullBits[2] | 0x8); 
/* 258 */     if (this.invertMask != null) nullBits[2] = (byte)(nullBits[2] | 0x10); 
/* 259 */     buf.writeBytes(nullBits);
/*     */     
/* 261 */     if (this.width != null) { this.width.serialize(buf); } else { buf.writeZero(12); }
/* 262 */      if (this.height != null) { this.height.serialize(buf); } else { buf.writeZero(12); }
/* 263 */      if (this.thickness != null) { this.thickness.serialize(buf); } else { buf.writeZero(12); }
/* 264 */      if (this.capped != null) { this.capped.serialize(buf); } else { buf.writeZero(1); }
/* 265 */      if (this.shape != null) { this.shape.serialize(buf); } else { buf.writeZero(1); }
/* 266 */      if (this.origin != null) { this.origin.serialize(buf); } else { buf.writeZero(1); }
/* 267 */      if (this.originRotation != null) { this.originRotation.serialize(buf); } else { buf.writeZero(1); }
/* 268 */      if (this.rotationAxis != null) { this.rotationAxis.serialize(buf); } else { buf.writeZero(1); }
/* 269 */      if (this.rotationAngle != null) { this.rotationAngle.serialize(buf); } else { buf.writeZero(1); }
/* 270 */      if (this.mirrorAxis != null) { this.mirrorAxis.serialize(buf); } else { buf.writeZero(1); }
/* 271 */      if (this.useMaskCommands != null) { this.useMaskCommands.serialize(buf); } else { buf.writeZero(1); }
/* 272 */      if (this.invertMask != null) { this.invertMask.serialize(buf); } else { buf.writeZero(1); }
/*     */     
/* 274 */     int materialOffsetSlot = buf.writerIndex();
/* 275 */     buf.writeIntLE(0);
/* 276 */     int favoriteMaterialsOffsetSlot = buf.writerIndex();
/* 277 */     buf.writeIntLE(0);
/* 278 */     int maskOffsetSlot = buf.writerIndex();
/* 279 */     buf.writeIntLE(0);
/* 280 */     int maskAboveOffsetSlot = buf.writerIndex();
/* 281 */     buf.writeIntLE(0);
/* 282 */     int maskNotOffsetSlot = buf.writerIndex();
/* 283 */     buf.writeIntLE(0);
/* 284 */     int maskBelowOffsetSlot = buf.writerIndex();
/* 285 */     buf.writeIntLE(0);
/* 286 */     int maskAdjacentOffsetSlot = buf.writerIndex();
/* 287 */     buf.writeIntLE(0);
/* 288 */     int maskNeighborOffsetSlot = buf.writerIndex();
/* 289 */     buf.writeIntLE(0);
/* 290 */     int maskCommandsOffsetSlot = buf.writerIndex();
/* 291 */     buf.writeIntLE(0);
/*     */     
/* 293 */     int varBlockStart = buf.writerIndex();
/* 294 */     if (this.material != null) {
/* 295 */       buf.setIntLE(materialOffsetSlot, buf.writerIndex() - varBlockStart);
/* 296 */       this.material.serialize(buf);
/*     */     } else {
/* 298 */       buf.setIntLE(materialOffsetSlot, -1);
/*     */     } 
/* 300 */     if (this.favoriteMaterials != null) {
/* 301 */       buf.setIntLE(favoriteMaterialsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 302 */       if (this.favoriteMaterials.length > 4096000) throw ProtocolException.arrayTooLong("FavoriteMaterials", this.favoriteMaterials.length, 4096000);  VarInt.write(buf, this.favoriteMaterials.length); for (BuilderToolBlockArg item : this.favoriteMaterials) item.serialize(buf); 
/*     */     } else {
/* 304 */       buf.setIntLE(favoriteMaterialsOffsetSlot, -1);
/*     */     } 
/* 306 */     if (this.mask != null) {
/* 307 */       buf.setIntLE(maskOffsetSlot, buf.writerIndex() - varBlockStart);
/* 308 */       this.mask.serialize(buf);
/*     */     } else {
/* 310 */       buf.setIntLE(maskOffsetSlot, -1);
/*     */     } 
/* 312 */     if (this.maskAbove != null) {
/* 313 */       buf.setIntLE(maskAboveOffsetSlot, buf.writerIndex() - varBlockStart);
/* 314 */       this.maskAbove.serialize(buf);
/*     */     } else {
/* 316 */       buf.setIntLE(maskAboveOffsetSlot, -1);
/*     */     } 
/* 318 */     if (this.maskNot != null) {
/* 319 */       buf.setIntLE(maskNotOffsetSlot, buf.writerIndex() - varBlockStart);
/* 320 */       this.maskNot.serialize(buf);
/*     */     } else {
/* 322 */       buf.setIntLE(maskNotOffsetSlot, -1);
/*     */     } 
/* 324 */     if (this.maskBelow != null) {
/* 325 */       buf.setIntLE(maskBelowOffsetSlot, buf.writerIndex() - varBlockStart);
/* 326 */       this.maskBelow.serialize(buf);
/*     */     } else {
/* 328 */       buf.setIntLE(maskBelowOffsetSlot, -1);
/*     */     } 
/* 330 */     if (this.maskAdjacent != null) {
/* 331 */       buf.setIntLE(maskAdjacentOffsetSlot, buf.writerIndex() - varBlockStart);
/* 332 */       this.maskAdjacent.serialize(buf);
/*     */     } else {
/* 334 */       buf.setIntLE(maskAdjacentOffsetSlot, -1);
/*     */     } 
/* 336 */     if (this.maskNeighbor != null) {
/* 337 */       buf.setIntLE(maskNeighborOffsetSlot, buf.writerIndex() - varBlockStart);
/* 338 */       this.maskNeighbor.serialize(buf);
/*     */     } else {
/* 340 */       buf.setIntLE(maskNeighborOffsetSlot, -1);
/*     */     } 
/* 342 */     if (this.maskCommands != null) {
/* 343 */       buf.setIntLE(maskCommandsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 344 */       if (this.maskCommands.length > 4096000) throw ProtocolException.arrayTooLong("MaskCommands", this.maskCommands.length, 4096000);  VarInt.write(buf, this.maskCommands.length); for (BuilderToolStringArg item : this.maskCommands) item.serialize(buf); 
/*     */     } else {
/* 346 */       buf.setIntLE(maskCommandsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 352 */     int size = 84;
/* 353 */     if (this.material != null) size += this.material.computeSize(); 
/* 354 */     if (this.favoriteMaterials != null) {
/* 355 */       int favoriteMaterialsSize = 0;
/* 356 */       for (BuilderToolBlockArg elem : this.favoriteMaterials) favoriteMaterialsSize += elem.computeSize(); 
/* 357 */       size += VarInt.size(this.favoriteMaterials.length) + favoriteMaterialsSize;
/*     */     } 
/* 359 */     if (this.mask != null) size += this.mask.computeSize(); 
/* 360 */     if (this.maskAbove != null) size += this.maskAbove.computeSize(); 
/* 361 */     if (this.maskNot != null) size += this.maskNot.computeSize(); 
/* 362 */     if (this.maskBelow != null) size += this.maskBelow.computeSize(); 
/* 363 */     if (this.maskAdjacent != null) size += this.maskAdjacent.computeSize(); 
/* 364 */     if (this.maskNeighbor != null) size += this.maskNeighbor.computeSize(); 
/* 365 */     if (this.maskCommands != null) {
/* 366 */       int maskCommandsSize = 0;
/* 367 */       for (BuilderToolStringArg elem : this.maskCommands) maskCommandsSize += elem.computeSize(); 
/* 368 */       size += VarInt.size(this.maskCommands.length) + maskCommandsSize;
/*     */     } 
/*     */     
/* 371 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 375 */     if (buffer.readableBytes() - offset < 84) {
/* 376 */       return ValidationResult.error("Buffer too small: expected at least 84 bytes");
/*     */     }
/*     */     
/* 379 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 3);
/*     */     
/* 381 */     if ((nullBits[1] & 0x4) != 0) {
/* 382 */       int materialOffset = buffer.getIntLE(offset + 48);
/* 383 */       if (materialOffset < 0) {
/* 384 */         return ValidationResult.error("Invalid offset for Material");
/*     */       }
/* 386 */       int pos = offset + 84 + materialOffset;
/* 387 */       if (pos >= buffer.writerIndex()) {
/* 388 */         return ValidationResult.error("Offset out of bounds for Material");
/*     */       }
/* 390 */       ValidationResult materialResult = BuilderToolBlockArg.validateStructure(buffer, pos);
/* 391 */       if (!materialResult.isValid()) {
/* 392 */         return ValidationResult.error("Invalid Material: " + materialResult.error());
/*     */       }
/* 394 */       pos += BuilderToolBlockArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 397 */     if ((nullBits[1] & 0x8) != 0) {
/* 398 */       int favoriteMaterialsOffset = buffer.getIntLE(offset + 52);
/* 399 */       if (favoriteMaterialsOffset < 0) {
/* 400 */         return ValidationResult.error("Invalid offset for FavoriteMaterials");
/*     */       }
/* 402 */       int pos = offset + 84 + favoriteMaterialsOffset;
/* 403 */       if (pos >= buffer.writerIndex()) {
/* 404 */         return ValidationResult.error("Offset out of bounds for FavoriteMaterials");
/*     */       }
/* 406 */       int favoriteMaterialsCount = VarInt.peek(buffer, pos);
/* 407 */       if (favoriteMaterialsCount < 0) {
/* 408 */         return ValidationResult.error("Invalid array count for FavoriteMaterials");
/*     */       }
/* 410 */       if (favoriteMaterialsCount > 4096000) {
/* 411 */         return ValidationResult.error("FavoriteMaterials exceeds max length 4096000");
/*     */       }
/* 413 */       pos += VarInt.length(buffer, pos);
/* 414 */       for (int i = 0; i < favoriteMaterialsCount; i++) {
/* 415 */         ValidationResult structResult = BuilderToolBlockArg.validateStructure(buffer, pos);
/* 416 */         if (!structResult.isValid()) {
/* 417 */           return ValidationResult.error("Invalid BuilderToolBlockArg in FavoriteMaterials[" + i + "]: " + structResult.error());
/*     */         }
/* 419 */         pos += BuilderToolBlockArg.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 423 */     if ((nullBits[1] & 0x10) != 0) {
/* 424 */       int maskOffset = buffer.getIntLE(offset + 56);
/* 425 */       if (maskOffset < 0) {
/* 426 */         return ValidationResult.error("Invalid offset for Mask");
/*     */       }
/* 428 */       int pos = offset + 84 + maskOffset;
/* 429 */       if (pos >= buffer.writerIndex()) {
/* 430 */         return ValidationResult.error("Offset out of bounds for Mask");
/*     */       }
/* 432 */       ValidationResult maskResult = BuilderToolMaskArg.validateStructure(buffer, pos);
/* 433 */       if (!maskResult.isValid()) {
/* 434 */         return ValidationResult.error("Invalid Mask: " + maskResult.error());
/*     */       }
/* 436 */       pos += BuilderToolMaskArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 439 */     if ((nullBits[1] & 0x20) != 0) {
/* 440 */       int maskAboveOffset = buffer.getIntLE(offset + 60);
/* 441 */       if (maskAboveOffset < 0) {
/* 442 */         return ValidationResult.error("Invalid offset for MaskAbove");
/*     */       }
/* 444 */       int pos = offset + 84 + maskAboveOffset;
/* 445 */       if (pos >= buffer.writerIndex()) {
/* 446 */         return ValidationResult.error("Offset out of bounds for MaskAbove");
/*     */       }
/* 448 */       ValidationResult maskAboveResult = BuilderToolMaskArg.validateStructure(buffer, pos);
/* 449 */       if (!maskAboveResult.isValid()) {
/* 450 */         return ValidationResult.error("Invalid MaskAbove: " + maskAboveResult.error());
/*     */       }
/* 452 */       pos += BuilderToolMaskArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 455 */     if ((nullBits[1] & 0x40) != 0) {
/* 456 */       int maskNotOffset = buffer.getIntLE(offset + 64);
/* 457 */       if (maskNotOffset < 0) {
/* 458 */         return ValidationResult.error("Invalid offset for MaskNot");
/*     */       }
/* 460 */       int pos = offset + 84 + maskNotOffset;
/* 461 */       if (pos >= buffer.writerIndex()) {
/* 462 */         return ValidationResult.error("Offset out of bounds for MaskNot");
/*     */       }
/* 464 */       ValidationResult maskNotResult = BuilderToolMaskArg.validateStructure(buffer, pos);
/* 465 */       if (!maskNotResult.isValid()) {
/* 466 */         return ValidationResult.error("Invalid MaskNot: " + maskNotResult.error());
/*     */       }
/* 468 */       pos += BuilderToolMaskArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 471 */     if ((nullBits[1] & 0x80) != 0) {
/* 472 */       int maskBelowOffset = buffer.getIntLE(offset + 68);
/* 473 */       if (maskBelowOffset < 0) {
/* 474 */         return ValidationResult.error("Invalid offset for MaskBelow");
/*     */       }
/* 476 */       int pos = offset + 84 + maskBelowOffset;
/* 477 */       if (pos >= buffer.writerIndex()) {
/* 478 */         return ValidationResult.error("Offset out of bounds for MaskBelow");
/*     */       }
/* 480 */       ValidationResult maskBelowResult = BuilderToolMaskArg.validateStructure(buffer, pos);
/* 481 */       if (!maskBelowResult.isValid()) {
/* 482 */         return ValidationResult.error("Invalid MaskBelow: " + maskBelowResult.error());
/*     */       }
/* 484 */       pos += BuilderToolMaskArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 487 */     if ((nullBits[2] & 0x1) != 0) {
/* 488 */       int maskAdjacentOffset = buffer.getIntLE(offset + 72);
/* 489 */       if (maskAdjacentOffset < 0) {
/* 490 */         return ValidationResult.error("Invalid offset for MaskAdjacent");
/*     */       }
/* 492 */       int pos = offset + 84 + maskAdjacentOffset;
/* 493 */       if (pos >= buffer.writerIndex()) {
/* 494 */         return ValidationResult.error("Offset out of bounds for MaskAdjacent");
/*     */       }
/* 496 */       ValidationResult maskAdjacentResult = BuilderToolMaskArg.validateStructure(buffer, pos);
/* 497 */       if (!maskAdjacentResult.isValid()) {
/* 498 */         return ValidationResult.error("Invalid MaskAdjacent: " + maskAdjacentResult.error());
/*     */       }
/* 500 */       pos += BuilderToolMaskArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 503 */     if ((nullBits[2] & 0x2) != 0) {
/* 504 */       int maskNeighborOffset = buffer.getIntLE(offset + 76);
/* 505 */       if (maskNeighborOffset < 0) {
/* 506 */         return ValidationResult.error("Invalid offset for MaskNeighbor");
/*     */       }
/* 508 */       int pos = offset + 84 + maskNeighborOffset;
/* 509 */       if (pos >= buffer.writerIndex()) {
/* 510 */         return ValidationResult.error("Offset out of bounds for MaskNeighbor");
/*     */       }
/* 512 */       ValidationResult maskNeighborResult = BuilderToolMaskArg.validateStructure(buffer, pos);
/* 513 */       if (!maskNeighborResult.isValid()) {
/* 514 */         return ValidationResult.error("Invalid MaskNeighbor: " + maskNeighborResult.error());
/*     */       }
/* 516 */       pos += BuilderToolMaskArg.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 519 */     if ((nullBits[2] & 0x4) != 0) {
/* 520 */       int maskCommandsOffset = buffer.getIntLE(offset + 80);
/* 521 */       if (maskCommandsOffset < 0) {
/* 522 */         return ValidationResult.error("Invalid offset for MaskCommands");
/*     */       }
/* 524 */       int pos = offset + 84 + maskCommandsOffset;
/* 525 */       if (pos >= buffer.writerIndex()) {
/* 526 */         return ValidationResult.error("Offset out of bounds for MaskCommands");
/*     */       }
/* 528 */       int maskCommandsCount = VarInt.peek(buffer, pos);
/* 529 */       if (maskCommandsCount < 0) {
/* 530 */         return ValidationResult.error("Invalid array count for MaskCommands");
/*     */       }
/* 532 */       if (maskCommandsCount > 4096000) {
/* 533 */         return ValidationResult.error("MaskCommands exceeds max length 4096000");
/*     */       }
/* 535 */       pos += VarInt.length(buffer, pos);
/* 536 */       for (int i = 0; i < maskCommandsCount; i++) {
/* 537 */         ValidationResult structResult = BuilderToolStringArg.validateStructure(buffer, pos);
/* 538 */         if (!structResult.isValid()) {
/* 539 */           return ValidationResult.error("Invalid BuilderToolStringArg in MaskCommands[" + i + "]: " + structResult.error());
/*     */         }
/* 541 */         pos += BuilderToolStringArg.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 544 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolBrushData clone() {
/* 548 */     BuilderToolBrushData copy = new BuilderToolBrushData();
/* 549 */     copy.width = (this.width != null) ? this.width.clone() : null;
/* 550 */     copy.height = (this.height != null) ? this.height.clone() : null;
/* 551 */     copy.thickness = (this.thickness != null) ? this.thickness.clone() : null;
/* 552 */     copy.capped = (this.capped != null) ? this.capped.clone() : null;
/* 553 */     copy.shape = (this.shape != null) ? this.shape.clone() : null;
/* 554 */     copy.origin = (this.origin != null) ? this.origin.clone() : null;
/* 555 */     copy.originRotation = (this.originRotation != null) ? this.originRotation.clone() : null;
/* 556 */     copy.rotationAxis = (this.rotationAxis != null) ? this.rotationAxis.clone() : null;
/* 557 */     copy.rotationAngle = (this.rotationAngle != null) ? this.rotationAngle.clone() : null;
/* 558 */     copy.mirrorAxis = (this.mirrorAxis != null) ? this.mirrorAxis.clone() : null;
/* 559 */     copy.material = (this.material != null) ? this.material.clone() : null;
/* 560 */     copy.favoriteMaterials = (this.favoriteMaterials != null) ? (BuilderToolBlockArg[])Arrays.<BuilderToolBlockArg>stream(this.favoriteMaterials).map(e -> e.clone()).toArray(x$0 -> new BuilderToolBlockArg[x$0]) : null;
/* 561 */     copy.mask = (this.mask != null) ? this.mask.clone() : null;
/* 562 */     copy.maskAbove = (this.maskAbove != null) ? this.maskAbove.clone() : null;
/* 563 */     copy.maskNot = (this.maskNot != null) ? this.maskNot.clone() : null;
/* 564 */     copy.maskBelow = (this.maskBelow != null) ? this.maskBelow.clone() : null;
/* 565 */     copy.maskAdjacent = (this.maskAdjacent != null) ? this.maskAdjacent.clone() : null;
/* 566 */     copy.maskNeighbor = (this.maskNeighbor != null) ? this.maskNeighbor.clone() : null;
/* 567 */     copy.maskCommands = (this.maskCommands != null) ? (BuilderToolStringArg[])Arrays.<BuilderToolStringArg>stream(this.maskCommands).map(e -> e.clone()).toArray(x$0 -> new BuilderToolStringArg[x$0]) : null;
/* 568 */     copy.useMaskCommands = (this.useMaskCommands != null) ? this.useMaskCommands.clone() : null;
/* 569 */     copy.invertMask = (this.invertMask != null) ? this.invertMask.clone() : null;
/* 570 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolBrushData other;
/* 576 */     if (this == obj) return true; 
/* 577 */     if (obj instanceof BuilderToolBrushData) { other = (BuilderToolBrushData)obj; } else { return false; }
/* 578 */      return (Objects.equals(this.width, other.width) && Objects.equals(this.height, other.height) && Objects.equals(this.thickness, other.thickness) && Objects.equals(this.capped, other.capped) && Objects.equals(this.shape, other.shape) && Objects.equals(this.origin, other.origin) && Objects.equals(this.originRotation, other.originRotation) && Objects.equals(this.rotationAxis, other.rotationAxis) && Objects.equals(this.rotationAngle, other.rotationAngle) && Objects.equals(this.mirrorAxis, other.mirrorAxis) && Objects.equals(this.material, other.material) && Arrays.equals((Object[])this.favoriteMaterials, (Object[])other.favoriteMaterials) && Objects.equals(this.mask, other.mask) && Objects.equals(this.maskAbove, other.maskAbove) && Objects.equals(this.maskNot, other.maskNot) && Objects.equals(this.maskBelow, other.maskBelow) && Objects.equals(this.maskAdjacent, other.maskAdjacent) && Objects.equals(this.maskNeighbor, other.maskNeighbor) && Arrays.equals((Object[])this.maskCommands, (Object[])other.maskCommands) && Objects.equals(this.useMaskCommands, other.useMaskCommands) && Objects.equals(this.invertMask, other.invertMask));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 583 */     int result = 1;
/* 584 */     result = 31 * result + Objects.hashCode(this.width);
/* 585 */     result = 31 * result + Objects.hashCode(this.height);
/* 586 */     result = 31 * result + Objects.hashCode(this.thickness);
/* 587 */     result = 31 * result + Objects.hashCode(this.capped);
/* 588 */     result = 31 * result + Objects.hashCode(this.shape);
/* 589 */     result = 31 * result + Objects.hashCode(this.origin);
/* 590 */     result = 31 * result + Objects.hashCode(this.originRotation);
/* 591 */     result = 31 * result + Objects.hashCode(this.rotationAxis);
/* 592 */     result = 31 * result + Objects.hashCode(this.rotationAngle);
/* 593 */     result = 31 * result + Objects.hashCode(this.mirrorAxis);
/* 594 */     result = 31 * result + Objects.hashCode(this.material);
/* 595 */     result = 31 * result + Arrays.hashCode((Object[])this.favoriteMaterials);
/* 596 */     result = 31 * result + Objects.hashCode(this.mask);
/* 597 */     result = 31 * result + Objects.hashCode(this.maskAbove);
/* 598 */     result = 31 * result + Objects.hashCode(this.maskNot);
/* 599 */     result = 31 * result + Objects.hashCode(this.maskBelow);
/* 600 */     result = 31 * result + Objects.hashCode(this.maskAdjacent);
/* 601 */     result = 31 * result + Objects.hashCode(this.maskNeighbor);
/* 602 */     result = 31 * result + Arrays.hashCode((Object[])this.maskCommands);
/* 603 */     result = 31 * result + Objects.hashCode(this.useMaskCommands);
/* 604 */     result = 31 * result + Objects.hashCode(this.invertMask);
/* 605 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolBrushData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */