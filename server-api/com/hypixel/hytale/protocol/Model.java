/*     */ package com.hypixel.hytale.protocol;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Model
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 43;
/*     */   public static final int VARIABLE_FIELD_COUNT = 12;
/*     */   public static final int VARIABLE_BLOCK_START = 91;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String assetId;
/*     */   @Nullable
/*     */   public String path;
/*     */   @Nullable
/*     */   public String texture;
/*     */   @Nullable
/*     */   public String gradientSet;
/*     */   @Nullable
/*     */   public String gradientId;
/*     */   @Nonnull
/*  36 */   public Phobia phobia = Phobia.None; @Nullable public CameraSettings camera; public float scale; public float eyeHeight; public float crouchOffset; @Nullable public Map<String, AnimationSet> animationSets; @Nullable
/*     */   public ModelAttachment[] attachments; @Nullable
/*     */   public Hitbox hitbox; @Nullable
/*     */   public ModelParticle[] particles; @Nullable
/*     */   public ModelTrail[] trails; @Nullable
/*     */   public ColorLight light; @Nullable
/*     */   public Map<String, DetailBox[]> detailBoxes; @Nullable
/*  43 */   public Model phobiaModel; public Model(@Nullable String assetId, @Nullable String path, @Nullable String texture, @Nullable String gradientSet, @Nullable String gradientId, @Nullable CameraSettings camera, float scale, float eyeHeight, float crouchOffset, @Nullable Map<String, AnimationSet> animationSets, @Nullable ModelAttachment[] attachments, @Nullable Hitbox hitbox, @Nullable ModelParticle[] particles, @Nullable ModelTrail[] trails, @Nullable ColorLight light, @Nullable Map<String, DetailBox[]> detailBoxes, @Nonnull Phobia phobia, @Nullable Model phobiaModel) { this.assetId = assetId;
/*  44 */     this.path = path;
/*  45 */     this.texture = texture;
/*  46 */     this.gradientSet = gradientSet;
/*  47 */     this.gradientId = gradientId;
/*  48 */     this.camera = camera;
/*  49 */     this.scale = scale;
/*  50 */     this.eyeHeight = eyeHeight;
/*  51 */     this.crouchOffset = crouchOffset;
/*  52 */     this.animationSets = animationSets;
/*  53 */     this.attachments = attachments;
/*  54 */     this.hitbox = hitbox;
/*  55 */     this.particles = particles;
/*  56 */     this.trails = trails;
/*  57 */     this.light = light;
/*  58 */     this.detailBoxes = detailBoxes;
/*  59 */     this.phobia = phobia;
/*  60 */     this.phobiaModel = phobiaModel; }
/*     */ 
/*     */   
/*     */   public Model(@Nonnull Model other) {
/*  64 */     this.assetId = other.assetId;
/*  65 */     this.path = other.path;
/*  66 */     this.texture = other.texture;
/*  67 */     this.gradientSet = other.gradientSet;
/*  68 */     this.gradientId = other.gradientId;
/*  69 */     this.camera = other.camera;
/*  70 */     this.scale = other.scale;
/*  71 */     this.eyeHeight = other.eyeHeight;
/*  72 */     this.crouchOffset = other.crouchOffset;
/*  73 */     this.animationSets = other.animationSets;
/*  74 */     this.attachments = other.attachments;
/*  75 */     this.hitbox = other.hitbox;
/*  76 */     this.particles = other.particles;
/*  77 */     this.trails = other.trails;
/*  78 */     this.light = other.light;
/*  79 */     this.detailBoxes = other.detailBoxes;
/*  80 */     this.phobia = other.phobia;
/*  81 */     this.phobiaModel = other.phobiaModel;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model deserialize(@Nonnull ByteBuf buf, int offset) {
/*  86 */     Model obj = new Model();
/*  87 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  88 */     obj.scale = buf.getFloatLE(offset + 2);
/*  89 */     obj.eyeHeight = buf.getFloatLE(offset + 6);
/*  90 */     obj.crouchOffset = buf.getFloatLE(offset + 10);
/*  91 */     if ((nullBits[1] & 0x1) != 0) obj.hitbox = Hitbox.deserialize(buf, offset + 14); 
/*  92 */     if ((nullBits[1] & 0x8) != 0) obj.light = ColorLight.deserialize(buf, offset + 38); 
/*  93 */     obj.phobia = Phobia.fromValue(buf.getByte(offset + 42));
/*     */     
/*  95 */     if ((nullBits[0] & 0x1) != 0) {
/*  96 */       int varPos0 = offset + 91 + buf.getIntLE(offset + 43);
/*  97 */       int assetIdLen = VarInt.peek(buf, varPos0);
/*  98 */       if (assetIdLen < 0) throw ProtocolException.negativeLength("AssetId", assetIdLen); 
/*  99 */       if (assetIdLen > 4096000) throw ProtocolException.stringTooLong("AssetId", assetIdLen, 4096000); 
/* 100 */       obj.assetId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/* 102 */     if ((nullBits[0] & 0x2) != 0) {
/* 103 */       int varPos1 = offset + 91 + buf.getIntLE(offset + 47);
/* 104 */       int pathLen = VarInt.peek(buf, varPos1);
/* 105 */       if (pathLen < 0) throw ProtocolException.negativeLength("Path", pathLen); 
/* 106 */       if (pathLen > 4096000) throw ProtocolException.stringTooLong("Path", pathLen, 4096000); 
/* 107 */       obj.path = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/* 109 */     if ((nullBits[0] & 0x4) != 0) {
/* 110 */       int varPos2 = offset + 91 + buf.getIntLE(offset + 51);
/* 111 */       int textureLen = VarInt.peek(buf, varPos2);
/* 112 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/* 113 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/* 114 */       obj.texture = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/* 116 */     if ((nullBits[0] & 0x8) != 0) {
/* 117 */       int varPos3 = offset + 91 + buf.getIntLE(offset + 55);
/* 118 */       int gradientSetLen = VarInt.peek(buf, varPos3);
/* 119 */       if (gradientSetLen < 0) throw ProtocolException.negativeLength("GradientSet", gradientSetLen); 
/* 120 */       if (gradientSetLen > 4096000) throw ProtocolException.stringTooLong("GradientSet", gradientSetLen, 4096000); 
/* 121 */       obj.gradientSet = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/* 123 */     if ((nullBits[0] & 0x10) != 0) {
/* 124 */       int varPos4 = offset + 91 + buf.getIntLE(offset + 59);
/* 125 */       int gradientIdLen = VarInt.peek(buf, varPos4);
/* 126 */       if (gradientIdLen < 0) throw ProtocolException.negativeLength("GradientId", gradientIdLen); 
/* 127 */       if (gradientIdLen > 4096000) throw ProtocolException.stringTooLong("GradientId", gradientIdLen, 4096000); 
/* 128 */       obj.gradientId = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/* 130 */     if ((nullBits[0] & 0x20) != 0) {
/* 131 */       int varPos5 = offset + 91 + buf.getIntLE(offset + 63);
/* 132 */       obj.camera = CameraSettings.deserialize(buf, varPos5);
/*     */     } 
/* 134 */     if ((nullBits[0] & 0x40) != 0) {
/* 135 */       int varPos6 = offset + 91 + buf.getIntLE(offset + 67);
/* 136 */       int animationSetsCount = VarInt.peek(buf, varPos6);
/* 137 */       if (animationSetsCount < 0) throw ProtocolException.negativeLength("AnimationSets", animationSetsCount); 
/* 138 */       if (animationSetsCount > 4096000) throw ProtocolException.dictionaryTooLarge("AnimationSets", animationSetsCount, 4096000); 
/* 139 */       int varIntLen = VarInt.length(buf, varPos6);
/* 140 */       obj.animationSets = new HashMap<>(animationSetsCount);
/* 141 */       int dictPos = varPos6 + varIntLen;
/* 142 */       for (int i = 0; i < animationSetsCount; i++) {
/* 143 */         int keyLen = VarInt.peek(buf, dictPos);
/* 144 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 145 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 146 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 147 */         String key = PacketIO.readVarString(buf, dictPos);
/* 148 */         dictPos += keyVarLen + keyLen;
/* 149 */         AnimationSet val = AnimationSet.deserialize(buf, dictPos);
/* 150 */         dictPos += AnimationSet.computeBytesConsumed(buf, dictPos);
/* 151 */         if (obj.animationSets.put(key, val) != null)
/* 152 */           throw ProtocolException.duplicateKey("animationSets", key); 
/*     */       } 
/*     */     } 
/* 155 */     if ((nullBits[0] & 0x80) != 0) {
/* 156 */       int varPos7 = offset + 91 + buf.getIntLE(offset + 71);
/* 157 */       int attachmentsCount = VarInt.peek(buf, varPos7);
/* 158 */       if (attachmentsCount < 0) throw ProtocolException.negativeLength("Attachments", attachmentsCount); 
/* 159 */       if (attachmentsCount > 4096000) throw ProtocolException.arrayTooLong("Attachments", attachmentsCount, 4096000); 
/* 160 */       int varIntLen = VarInt.length(buf, varPos7);
/* 161 */       if ((varPos7 + varIntLen) + attachmentsCount * 1L > buf.readableBytes())
/* 162 */         throw ProtocolException.bufferTooSmall("Attachments", varPos7 + varIntLen + attachmentsCount * 1, buf.readableBytes()); 
/* 163 */       obj.attachments = new ModelAttachment[attachmentsCount];
/* 164 */       int elemPos = varPos7 + varIntLen;
/* 165 */       for (int i = 0; i < attachmentsCount; i++) {
/* 166 */         obj.attachments[i] = ModelAttachment.deserialize(buf, elemPos);
/* 167 */         elemPos += ModelAttachment.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 170 */     if ((nullBits[1] & 0x2) != 0) {
/* 171 */       int varPos8 = offset + 91 + buf.getIntLE(offset + 75);
/* 172 */       int particlesCount = VarInt.peek(buf, varPos8);
/* 173 */       if (particlesCount < 0) throw ProtocolException.negativeLength("Particles", particlesCount); 
/* 174 */       if (particlesCount > 4096000) throw ProtocolException.arrayTooLong("Particles", particlesCount, 4096000); 
/* 175 */       int varIntLen = VarInt.length(buf, varPos8);
/* 176 */       if ((varPos8 + varIntLen) + particlesCount * 34L > buf.readableBytes())
/* 177 */         throw ProtocolException.bufferTooSmall("Particles", varPos8 + varIntLen + particlesCount * 34, buf.readableBytes()); 
/* 178 */       obj.particles = new ModelParticle[particlesCount];
/* 179 */       int elemPos = varPos8 + varIntLen;
/* 180 */       for (int i = 0; i < particlesCount; i++) {
/* 181 */         obj.particles[i] = ModelParticle.deserialize(buf, elemPos);
/* 182 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 185 */     if ((nullBits[1] & 0x4) != 0) {
/* 186 */       int varPos9 = offset + 91 + buf.getIntLE(offset + 79);
/* 187 */       int trailsCount = VarInt.peek(buf, varPos9);
/* 188 */       if (trailsCount < 0) throw ProtocolException.negativeLength("Trails", trailsCount); 
/* 189 */       if (trailsCount > 4096000) throw ProtocolException.arrayTooLong("Trails", trailsCount, 4096000); 
/* 190 */       int varIntLen = VarInt.length(buf, varPos9);
/* 191 */       if ((varPos9 + varIntLen) + trailsCount * 27L > buf.readableBytes())
/* 192 */         throw ProtocolException.bufferTooSmall("Trails", varPos9 + varIntLen + trailsCount * 27, buf.readableBytes()); 
/* 193 */       obj.trails = new ModelTrail[trailsCount];
/* 194 */       int elemPos = varPos9 + varIntLen;
/* 195 */       for (int i = 0; i < trailsCount; i++) {
/* 196 */         obj.trails[i] = ModelTrail.deserialize(buf, elemPos);
/* 197 */         elemPos += ModelTrail.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 200 */     if ((nullBits[1] & 0x10) != 0) {
/* 201 */       int varPos10 = offset + 91 + buf.getIntLE(offset + 83);
/* 202 */       int detailBoxesCount = VarInt.peek(buf, varPos10);
/* 203 */       if (detailBoxesCount < 0) throw ProtocolException.negativeLength("DetailBoxes", detailBoxesCount); 
/* 204 */       if (detailBoxesCount > 4096000) throw ProtocolException.dictionaryTooLarge("DetailBoxes", detailBoxesCount, 4096000); 
/* 205 */       int varIntLen = VarInt.length(buf, varPos10);
/* 206 */       obj.detailBoxes = (Map)new HashMap<>(detailBoxesCount);
/* 207 */       int dictPos = varPos10 + varIntLen;
/* 208 */       for (int i = 0; i < detailBoxesCount; i++) {
/* 209 */         int keyLen = VarInt.peek(buf, dictPos);
/* 210 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 211 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 212 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 213 */         String key = PacketIO.readVarString(buf, dictPos);
/* 214 */         dictPos += keyVarLen + keyLen;
/* 215 */         int valLen = VarInt.peek(buf, dictPos);
/* 216 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/* 217 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/* 218 */         int valVarLen = VarInt.length(buf, dictPos);
/* 219 */         if ((dictPos + valVarLen) + valLen * 37L > buf.readableBytes())
/* 220 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 37, buf.readableBytes()); 
/* 221 */         dictPos += valVarLen;
/* 222 */         DetailBox[] val = new DetailBox[valLen];
/* 223 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/* 224 */           val[valIdx] = DetailBox.deserialize(buf, dictPos);
/* 225 */           dictPos += DetailBox.computeBytesConsumed(buf, dictPos);
/*     */         } 
/* 227 */         if (obj.detailBoxes.put(key, val) != null)
/* 228 */           throw ProtocolException.duplicateKey("detailBoxes", key); 
/*     */       } 
/*     */     } 
/* 231 */     if ((nullBits[1] & 0x20) != 0) {
/* 232 */       int varPos11 = offset + 91 + buf.getIntLE(offset + 87);
/* 233 */       obj.phobiaModel = deserialize(buf, varPos11);
/*     */     } 
/*     */     
/* 236 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 240 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 241 */     int maxEnd = 91;
/* 242 */     if ((nullBits[0] & 0x1) != 0) {
/* 243 */       int fieldOffset0 = buf.getIntLE(offset + 43);
/* 244 */       int pos0 = offset + 91 + fieldOffset0;
/* 245 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 246 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 248 */     if ((nullBits[0] & 0x2) != 0) {
/* 249 */       int fieldOffset1 = buf.getIntLE(offset + 47);
/* 250 */       int pos1 = offset + 91 + fieldOffset1;
/* 251 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 252 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 254 */     if ((nullBits[0] & 0x4) != 0) {
/* 255 */       int fieldOffset2 = buf.getIntLE(offset + 51);
/* 256 */       int pos2 = offset + 91 + fieldOffset2;
/* 257 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 258 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 260 */     if ((nullBits[0] & 0x8) != 0) {
/* 261 */       int fieldOffset3 = buf.getIntLE(offset + 55);
/* 262 */       int pos3 = offset + 91 + fieldOffset3;
/* 263 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 264 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 266 */     if ((nullBits[0] & 0x10) != 0) {
/* 267 */       int fieldOffset4 = buf.getIntLE(offset + 59);
/* 268 */       int pos4 = offset + 91 + fieldOffset4;
/* 269 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 270 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 272 */     if ((nullBits[0] & 0x20) != 0) {
/* 273 */       int fieldOffset5 = buf.getIntLE(offset + 63);
/* 274 */       int pos5 = offset + 91 + fieldOffset5;
/* 275 */       pos5 += CameraSettings.computeBytesConsumed(buf, pos5);
/* 276 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 278 */     if ((nullBits[0] & 0x40) != 0) {
/* 279 */       int fieldOffset6 = buf.getIntLE(offset + 67);
/* 280 */       int pos6 = offset + 91 + fieldOffset6;
/* 281 */       int dictLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/* 282 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6) + sl; pos6 += AnimationSet.computeBytesConsumed(buf, pos6); i++; }
/* 283 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 285 */     if ((nullBits[0] & 0x80) != 0) {
/* 286 */       int fieldOffset7 = buf.getIntLE(offset + 71);
/* 287 */       int pos7 = offset + 91 + fieldOffset7;
/* 288 */       int arrLen = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7);
/* 289 */       for (int i = 0; i < arrLen; ) { pos7 += ModelAttachment.computeBytesConsumed(buf, pos7); i++; }
/* 290 */        if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*     */     } 
/* 292 */     if ((nullBits[1] & 0x2) != 0) {
/* 293 */       int fieldOffset8 = buf.getIntLE(offset + 75);
/* 294 */       int pos8 = offset + 91 + fieldOffset8;
/* 295 */       int arrLen = VarInt.peek(buf, pos8); pos8 += VarInt.length(buf, pos8);
/* 296 */       for (int i = 0; i < arrLen; ) { pos8 += ModelParticle.computeBytesConsumed(buf, pos8); i++; }
/* 297 */        if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*     */     } 
/* 299 */     if ((nullBits[1] & 0x4) != 0) {
/* 300 */       int fieldOffset9 = buf.getIntLE(offset + 79);
/* 301 */       int pos9 = offset + 91 + fieldOffset9;
/* 302 */       int arrLen = VarInt.peek(buf, pos9); pos9 += VarInt.length(buf, pos9);
/* 303 */       for (int i = 0; i < arrLen; ) { pos9 += ModelTrail.computeBytesConsumed(buf, pos9); i++; }
/* 304 */        if (pos9 - offset > maxEnd) maxEnd = pos9 - offset; 
/*     */     } 
/* 306 */     if ((nullBits[1] & 0x10) != 0) {
/* 307 */       int fieldOffset10 = buf.getIntLE(offset + 83);
/* 308 */       int pos10 = offset + 91 + fieldOffset10;
/* 309 */       int dictLen = VarInt.peek(buf, pos10); pos10 += VarInt.length(buf, pos10);
/* 310 */       for (int i = 0; i < dictLen; ) { for (int sl = VarInt.peek(buf, pos10), al = VarInt.peek(buf, pos10), j = 0; j < al; ) { pos10 += DetailBox.computeBytesConsumed(buf, pos10); j++; }  i++; }
/* 311 */        if (pos10 - offset > maxEnd) maxEnd = pos10 - offset; 
/*     */     } 
/* 313 */     if ((nullBits[1] & 0x20) != 0) {
/* 314 */       int fieldOffset11 = buf.getIntLE(offset + 87);
/* 315 */       int pos11 = offset + 91 + fieldOffset11;
/* 316 */       pos11 += computeBytesConsumed(buf, pos11);
/* 317 */       if (pos11 - offset > maxEnd) maxEnd = pos11 - offset; 
/*     */     } 
/* 319 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 324 */     int startPos = buf.writerIndex();
/* 325 */     byte[] nullBits = new byte[2];
/* 326 */     if (this.assetId != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 327 */     if (this.path != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 328 */     if (this.texture != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 329 */     if (this.gradientSet != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 330 */     if (this.gradientId != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 331 */     if (this.camera != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 332 */     if (this.animationSets != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 333 */     if (this.attachments != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 334 */     if (this.hitbox != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 335 */     if (this.particles != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 336 */     if (this.trails != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/* 337 */     if (this.light != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/* 338 */     if (this.detailBoxes != null) nullBits[1] = (byte)(nullBits[1] | 0x10); 
/* 339 */     if (this.phobiaModel != null) nullBits[1] = (byte)(nullBits[1] | 0x20); 
/* 340 */     buf.writeBytes(nullBits);
/*     */     
/* 342 */     buf.writeFloatLE(this.scale);
/* 343 */     buf.writeFloatLE(this.eyeHeight);
/* 344 */     buf.writeFloatLE(this.crouchOffset);
/* 345 */     if (this.hitbox != null) { this.hitbox.serialize(buf); } else { buf.writeZero(24); }
/* 346 */      if (this.light != null) { this.light.serialize(buf); } else { buf.writeZero(4); }
/* 347 */      buf.writeByte(this.phobia.getValue());
/*     */     
/* 349 */     int assetIdOffsetSlot = buf.writerIndex();
/* 350 */     buf.writeIntLE(0);
/* 351 */     int pathOffsetSlot = buf.writerIndex();
/* 352 */     buf.writeIntLE(0);
/* 353 */     int textureOffsetSlot = buf.writerIndex();
/* 354 */     buf.writeIntLE(0);
/* 355 */     int gradientSetOffsetSlot = buf.writerIndex();
/* 356 */     buf.writeIntLE(0);
/* 357 */     int gradientIdOffsetSlot = buf.writerIndex();
/* 358 */     buf.writeIntLE(0);
/* 359 */     int cameraOffsetSlot = buf.writerIndex();
/* 360 */     buf.writeIntLE(0);
/* 361 */     int animationSetsOffsetSlot = buf.writerIndex();
/* 362 */     buf.writeIntLE(0);
/* 363 */     int attachmentsOffsetSlot = buf.writerIndex();
/* 364 */     buf.writeIntLE(0);
/* 365 */     int particlesOffsetSlot = buf.writerIndex();
/* 366 */     buf.writeIntLE(0);
/* 367 */     int trailsOffsetSlot = buf.writerIndex();
/* 368 */     buf.writeIntLE(0);
/* 369 */     int detailBoxesOffsetSlot = buf.writerIndex();
/* 370 */     buf.writeIntLE(0);
/* 371 */     int phobiaModelOffsetSlot = buf.writerIndex();
/* 372 */     buf.writeIntLE(0);
/*     */     
/* 374 */     int varBlockStart = buf.writerIndex();
/* 375 */     if (this.assetId != null) {
/* 376 */       buf.setIntLE(assetIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 377 */       PacketIO.writeVarString(buf, this.assetId, 4096000);
/*     */     } else {
/* 379 */       buf.setIntLE(assetIdOffsetSlot, -1);
/*     */     } 
/* 381 */     if (this.path != null) {
/* 382 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 383 */       PacketIO.writeVarString(buf, this.path, 4096000);
/*     */     } else {
/* 385 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 387 */     if (this.texture != null) {
/* 388 */       buf.setIntLE(textureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 389 */       PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */     } else {
/* 391 */       buf.setIntLE(textureOffsetSlot, -1);
/*     */     } 
/* 393 */     if (this.gradientSet != null) {
/* 394 */       buf.setIntLE(gradientSetOffsetSlot, buf.writerIndex() - varBlockStart);
/* 395 */       PacketIO.writeVarString(buf, this.gradientSet, 4096000);
/*     */     } else {
/* 397 */       buf.setIntLE(gradientSetOffsetSlot, -1);
/*     */     } 
/* 399 */     if (this.gradientId != null) {
/* 400 */       buf.setIntLE(gradientIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 401 */       PacketIO.writeVarString(buf, this.gradientId, 4096000);
/*     */     } else {
/* 403 */       buf.setIntLE(gradientIdOffsetSlot, -1);
/*     */     } 
/* 405 */     if (this.camera != null) {
/* 406 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 407 */       this.camera.serialize(buf);
/*     */     } else {
/* 409 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 411 */     if (this.animationSets != null)
/* 412 */     { buf.setIntLE(animationSetsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 413 */       if (this.animationSets.size() > 4096000) throw ProtocolException.dictionaryTooLarge("AnimationSets", this.animationSets.size(), 4096000);  VarInt.write(buf, this.animationSets.size()); for (Map.Entry<String, AnimationSet> e : this.animationSets.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((AnimationSet)e.getValue()).serialize(buf); }
/*     */        }
/* 415 */     else { buf.setIntLE(animationSetsOffsetSlot, -1); }
/*     */     
/* 417 */     if (this.attachments != null) {
/* 418 */       buf.setIntLE(attachmentsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 419 */       if (this.attachments.length > 4096000) throw ProtocolException.arrayTooLong("Attachments", this.attachments.length, 4096000);  VarInt.write(buf, this.attachments.length); for (ModelAttachment item : this.attachments) item.serialize(buf); 
/*     */     } else {
/* 421 */       buf.setIntLE(attachmentsOffsetSlot, -1);
/*     */     } 
/* 423 */     if (this.particles != null) {
/* 424 */       buf.setIntLE(particlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 425 */       if (this.particles.length > 4096000) throw ProtocolException.arrayTooLong("Particles", this.particles.length, 4096000);  VarInt.write(buf, this.particles.length); for (ModelParticle item : this.particles) item.serialize(buf); 
/*     */     } else {
/* 427 */       buf.setIntLE(particlesOffsetSlot, -1);
/*     */     } 
/* 429 */     if (this.trails != null) {
/* 430 */       buf.setIntLE(trailsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 431 */       if (this.trails.length > 4096000) throw ProtocolException.arrayTooLong("Trails", this.trails.length, 4096000);  VarInt.write(buf, this.trails.length); for (ModelTrail item : this.trails) item.serialize(buf); 
/*     */     } else {
/* 433 */       buf.setIntLE(trailsOffsetSlot, -1);
/*     */     } 
/* 435 */     if (this.detailBoxes != null)
/* 436 */     { buf.setIntLE(detailBoxesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 437 */       if (this.detailBoxes.size() > 4096000) throw ProtocolException.dictionaryTooLarge("DetailBoxes", this.detailBoxes.size(), 4096000);  VarInt.write(buf, this.detailBoxes.size()); for (Map.Entry<String, DetailBox[]> e : this.detailBoxes.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); VarInt.write(buf, ((DetailBox[])e.getValue()).length); for (DetailBox arrItem : (DetailBox[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 439 */     else { buf.setIntLE(detailBoxesOffsetSlot, -1); }
/*     */     
/* 441 */     if (this.phobiaModel != null) {
/* 442 */       buf.setIntLE(phobiaModelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 443 */       this.phobiaModel.serialize(buf);
/*     */     } else {
/* 445 */       buf.setIntLE(phobiaModelOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 451 */     int size = 91;
/* 452 */     if (this.assetId != null) size += PacketIO.stringSize(this.assetId); 
/* 453 */     if (this.path != null) size += PacketIO.stringSize(this.path); 
/* 454 */     if (this.texture != null) size += PacketIO.stringSize(this.texture); 
/* 455 */     if (this.gradientSet != null) size += PacketIO.stringSize(this.gradientSet); 
/* 456 */     if (this.gradientId != null) size += PacketIO.stringSize(this.gradientId); 
/* 457 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 458 */     if (this.animationSets != null) {
/* 459 */       int animationSetsSize = 0;
/* 460 */       for (Map.Entry<String, AnimationSet> kvp : this.animationSets.entrySet()) animationSetsSize += PacketIO.stringSize(kvp.getKey()) + ((AnimationSet)kvp.getValue()).computeSize(); 
/* 461 */       size += VarInt.size(this.animationSets.size()) + animationSetsSize;
/*     */     } 
/* 463 */     if (this.attachments != null) {
/* 464 */       int attachmentsSize = 0;
/* 465 */       for (ModelAttachment elem : this.attachments) attachmentsSize += elem.computeSize(); 
/* 466 */       size += VarInt.size(this.attachments.length) + attachmentsSize;
/*     */     } 
/* 468 */     if (this.particles != null) {
/* 469 */       int particlesSize = 0;
/* 470 */       for (ModelParticle elem : this.particles) particlesSize += elem.computeSize(); 
/* 471 */       size += VarInt.size(this.particles.length) + particlesSize;
/*     */     } 
/* 473 */     if (this.trails != null) {
/* 474 */       int trailsSize = 0;
/* 475 */       for (ModelTrail elem : this.trails) trailsSize += elem.computeSize(); 
/* 476 */       size += VarInt.size(this.trails.length) + trailsSize;
/*     */     } 
/* 478 */     if (this.detailBoxes != null) {
/* 479 */       int detailBoxesSize = 0;
/* 480 */       for (Map.Entry<String, DetailBox[]> kvp : this.detailBoxes.entrySet()) detailBoxesSize += PacketIO.stringSize(kvp.getKey()) + VarInt.size(((DetailBox[])kvp.getValue()).length) + ((DetailBox[])kvp.getValue()).length * 37; 
/* 481 */       size += VarInt.size(this.detailBoxes.size()) + detailBoxesSize;
/*     */     } 
/* 483 */     if (this.phobiaModel != null) size += this.phobiaModel.computeSize();
/*     */     
/* 485 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 489 */     if (buffer.readableBytes() - offset < 91) {
/* 490 */       return ValidationResult.error("Buffer too small: expected at least 91 bytes");
/*     */     }
/*     */     
/* 493 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 495 */     if ((nullBits[0] & 0x1) != 0) {
/* 496 */       int assetIdOffset = buffer.getIntLE(offset + 43);
/* 497 */       if (assetIdOffset < 0) {
/* 498 */         return ValidationResult.error("Invalid offset for AssetId");
/*     */       }
/* 500 */       int pos = offset + 91 + assetIdOffset;
/* 501 */       if (pos >= buffer.writerIndex()) {
/* 502 */         return ValidationResult.error("Offset out of bounds for AssetId");
/*     */       }
/* 504 */       int assetIdLen = VarInt.peek(buffer, pos);
/* 505 */       if (assetIdLen < 0) {
/* 506 */         return ValidationResult.error("Invalid string length for AssetId");
/*     */       }
/* 508 */       if (assetIdLen > 4096000) {
/* 509 */         return ValidationResult.error("AssetId exceeds max length 4096000");
/*     */       }
/* 511 */       pos += VarInt.length(buffer, pos);
/* 512 */       pos += assetIdLen;
/* 513 */       if (pos > buffer.writerIndex()) {
/* 514 */         return ValidationResult.error("Buffer overflow reading AssetId");
/*     */       }
/*     */     } 
/*     */     
/* 518 */     if ((nullBits[0] & 0x2) != 0) {
/* 519 */       int pathOffset = buffer.getIntLE(offset + 47);
/* 520 */       if (pathOffset < 0) {
/* 521 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 523 */       int pos = offset + 91 + pathOffset;
/* 524 */       if (pos >= buffer.writerIndex()) {
/* 525 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 527 */       int pathLen = VarInt.peek(buffer, pos);
/* 528 */       if (pathLen < 0) {
/* 529 */         return ValidationResult.error("Invalid string length for Path");
/*     */       }
/* 531 */       if (pathLen > 4096000) {
/* 532 */         return ValidationResult.error("Path exceeds max length 4096000");
/*     */       }
/* 534 */       pos += VarInt.length(buffer, pos);
/* 535 */       pos += pathLen;
/* 536 */       if (pos > buffer.writerIndex()) {
/* 537 */         return ValidationResult.error("Buffer overflow reading Path");
/*     */       }
/*     */     } 
/*     */     
/* 541 */     if ((nullBits[0] & 0x4) != 0) {
/* 542 */       int textureOffset = buffer.getIntLE(offset + 51);
/* 543 */       if (textureOffset < 0) {
/* 544 */         return ValidationResult.error("Invalid offset for Texture");
/*     */       }
/* 546 */       int pos = offset + 91 + textureOffset;
/* 547 */       if (pos >= buffer.writerIndex()) {
/* 548 */         return ValidationResult.error("Offset out of bounds for Texture");
/*     */       }
/* 550 */       int textureLen = VarInt.peek(buffer, pos);
/* 551 */       if (textureLen < 0) {
/* 552 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/* 554 */       if (textureLen > 4096000) {
/* 555 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/* 557 */       pos += VarInt.length(buffer, pos);
/* 558 */       pos += textureLen;
/* 559 */       if (pos > buffer.writerIndex()) {
/* 560 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/*     */     
/* 564 */     if ((nullBits[0] & 0x8) != 0) {
/* 565 */       int gradientSetOffset = buffer.getIntLE(offset + 55);
/* 566 */       if (gradientSetOffset < 0) {
/* 567 */         return ValidationResult.error("Invalid offset for GradientSet");
/*     */       }
/* 569 */       int pos = offset + 91 + gradientSetOffset;
/* 570 */       if (pos >= buffer.writerIndex()) {
/* 571 */         return ValidationResult.error("Offset out of bounds for GradientSet");
/*     */       }
/* 573 */       int gradientSetLen = VarInt.peek(buffer, pos);
/* 574 */       if (gradientSetLen < 0) {
/* 575 */         return ValidationResult.error("Invalid string length for GradientSet");
/*     */       }
/* 577 */       if (gradientSetLen > 4096000) {
/* 578 */         return ValidationResult.error("GradientSet exceeds max length 4096000");
/*     */       }
/* 580 */       pos += VarInt.length(buffer, pos);
/* 581 */       pos += gradientSetLen;
/* 582 */       if (pos > buffer.writerIndex()) {
/* 583 */         return ValidationResult.error("Buffer overflow reading GradientSet");
/*     */       }
/*     */     } 
/*     */     
/* 587 */     if ((nullBits[0] & 0x10) != 0) {
/* 588 */       int gradientIdOffset = buffer.getIntLE(offset + 59);
/* 589 */       if (gradientIdOffset < 0) {
/* 590 */         return ValidationResult.error("Invalid offset for GradientId");
/*     */       }
/* 592 */       int pos = offset + 91 + gradientIdOffset;
/* 593 */       if (pos >= buffer.writerIndex()) {
/* 594 */         return ValidationResult.error("Offset out of bounds for GradientId");
/*     */       }
/* 596 */       int gradientIdLen = VarInt.peek(buffer, pos);
/* 597 */       if (gradientIdLen < 0) {
/* 598 */         return ValidationResult.error("Invalid string length for GradientId");
/*     */       }
/* 600 */       if (gradientIdLen > 4096000) {
/* 601 */         return ValidationResult.error("GradientId exceeds max length 4096000");
/*     */       }
/* 603 */       pos += VarInt.length(buffer, pos);
/* 604 */       pos += gradientIdLen;
/* 605 */       if (pos > buffer.writerIndex()) {
/* 606 */         return ValidationResult.error("Buffer overflow reading GradientId");
/*     */       }
/*     */     } 
/*     */     
/* 610 */     if ((nullBits[0] & 0x20) != 0) {
/* 611 */       int cameraOffset = buffer.getIntLE(offset + 63);
/* 612 */       if (cameraOffset < 0) {
/* 613 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 615 */       int pos = offset + 91 + cameraOffset;
/* 616 */       if (pos >= buffer.writerIndex()) {
/* 617 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 619 */       ValidationResult cameraResult = CameraSettings.validateStructure(buffer, pos);
/* 620 */       if (!cameraResult.isValid()) {
/* 621 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 623 */       pos += CameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 626 */     if ((nullBits[0] & 0x40) != 0) {
/* 627 */       int animationSetsOffset = buffer.getIntLE(offset + 67);
/* 628 */       if (animationSetsOffset < 0) {
/* 629 */         return ValidationResult.error("Invalid offset for AnimationSets");
/*     */       }
/* 631 */       int pos = offset + 91 + animationSetsOffset;
/* 632 */       if (pos >= buffer.writerIndex()) {
/* 633 */         return ValidationResult.error("Offset out of bounds for AnimationSets");
/*     */       }
/* 635 */       int animationSetsCount = VarInt.peek(buffer, pos);
/* 636 */       if (animationSetsCount < 0) {
/* 637 */         return ValidationResult.error("Invalid dictionary count for AnimationSets");
/*     */       }
/* 639 */       if (animationSetsCount > 4096000) {
/* 640 */         return ValidationResult.error("AnimationSets exceeds max length 4096000");
/*     */       }
/* 642 */       pos += VarInt.length(buffer, pos);
/* 643 */       for (int i = 0; i < animationSetsCount; i++) {
/* 644 */         int keyLen = VarInt.peek(buffer, pos);
/* 645 */         if (keyLen < 0) {
/* 646 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 648 */         if (keyLen > 4096000) {
/* 649 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 651 */         pos += VarInt.length(buffer, pos);
/* 652 */         pos += keyLen;
/* 653 */         if (pos > buffer.writerIndex()) {
/* 654 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 656 */         pos += AnimationSet.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 661 */     if ((nullBits[0] & 0x80) != 0) {
/* 662 */       int attachmentsOffset = buffer.getIntLE(offset + 71);
/* 663 */       if (attachmentsOffset < 0) {
/* 664 */         return ValidationResult.error("Invalid offset for Attachments");
/*     */       }
/* 666 */       int pos = offset + 91 + attachmentsOffset;
/* 667 */       if (pos >= buffer.writerIndex()) {
/* 668 */         return ValidationResult.error("Offset out of bounds for Attachments");
/*     */       }
/* 670 */       int attachmentsCount = VarInt.peek(buffer, pos);
/* 671 */       if (attachmentsCount < 0) {
/* 672 */         return ValidationResult.error("Invalid array count for Attachments");
/*     */       }
/* 674 */       if (attachmentsCount > 4096000) {
/* 675 */         return ValidationResult.error("Attachments exceeds max length 4096000");
/*     */       }
/* 677 */       pos += VarInt.length(buffer, pos);
/* 678 */       for (int i = 0; i < attachmentsCount; i++) {
/* 679 */         ValidationResult structResult = ModelAttachment.validateStructure(buffer, pos);
/* 680 */         if (!structResult.isValid()) {
/* 681 */           return ValidationResult.error("Invalid ModelAttachment in Attachments[" + i + "]: " + structResult.error());
/*     */         }
/* 683 */         pos += ModelAttachment.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 687 */     if ((nullBits[1] & 0x2) != 0) {
/* 688 */       int particlesOffset = buffer.getIntLE(offset + 75);
/* 689 */       if (particlesOffset < 0) {
/* 690 */         return ValidationResult.error("Invalid offset for Particles");
/*     */       }
/* 692 */       int pos = offset + 91 + particlesOffset;
/* 693 */       if (pos >= buffer.writerIndex()) {
/* 694 */         return ValidationResult.error("Offset out of bounds for Particles");
/*     */       }
/* 696 */       int particlesCount = VarInt.peek(buffer, pos);
/* 697 */       if (particlesCount < 0) {
/* 698 */         return ValidationResult.error("Invalid array count for Particles");
/*     */       }
/* 700 */       if (particlesCount > 4096000) {
/* 701 */         return ValidationResult.error("Particles exceeds max length 4096000");
/*     */       }
/* 703 */       pos += VarInt.length(buffer, pos);
/* 704 */       for (int i = 0; i < particlesCount; i++) {
/* 705 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 706 */         if (!structResult.isValid()) {
/* 707 */           return ValidationResult.error("Invalid ModelParticle in Particles[" + i + "]: " + structResult.error());
/*     */         }
/* 709 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 713 */     if ((nullBits[1] & 0x4) != 0) {
/* 714 */       int trailsOffset = buffer.getIntLE(offset + 79);
/* 715 */       if (trailsOffset < 0) {
/* 716 */         return ValidationResult.error("Invalid offset for Trails");
/*     */       }
/* 718 */       int pos = offset + 91 + trailsOffset;
/* 719 */       if (pos >= buffer.writerIndex()) {
/* 720 */         return ValidationResult.error("Offset out of bounds for Trails");
/*     */       }
/* 722 */       int trailsCount = VarInt.peek(buffer, pos);
/* 723 */       if (trailsCount < 0) {
/* 724 */         return ValidationResult.error("Invalid array count for Trails");
/*     */       }
/* 726 */       if (trailsCount > 4096000) {
/* 727 */         return ValidationResult.error("Trails exceeds max length 4096000");
/*     */       }
/* 729 */       pos += VarInt.length(buffer, pos);
/* 730 */       for (int i = 0; i < trailsCount; i++) {
/* 731 */         ValidationResult structResult = ModelTrail.validateStructure(buffer, pos);
/* 732 */         if (!structResult.isValid()) {
/* 733 */           return ValidationResult.error("Invalid ModelTrail in Trails[" + i + "]: " + structResult.error());
/*     */         }
/* 735 */         pos += ModelTrail.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 739 */     if ((nullBits[1] & 0x10) != 0) {
/* 740 */       int detailBoxesOffset = buffer.getIntLE(offset + 83);
/* 741 */       if (detailBoxesOffset < 0) {
/* 742 */         return ValidationResult.error("Invalid offset for DetailBoxes");
/*     */       }
/* 744 */       int pos = offset + 91 + detailBoxesOffset;
/* 745 */       if (pos >= buffer.writerIndex()) {
/* 746 */         return ValidationResult.error("Offset out of bounds for DetailBoxes");
/*     */       }
/* 748 */       int detailBoxesCount = VarInt.peek(buffer, pos);
/* 749 */       if (detailBoxesCount < 0) {
/* 750 */         return ValidationResult.error("Invalid dictionary count for DetailBoxes");
/*     */       }
/* 752 */       if (detailBoxesCount > 4096000) {
/* 753 */         return ValidationResult.error("DetailBoxes exceeds max length 4096000");
/*     */       }
/* 755 */       pos += VarInt.length(buffer, pos);
/* 756 */       for (int i = 0; i < detailBoxesCount; i++) {
/* 757 */         int keyLen = VarInt.peek(buffer, pos);
/* 758 */         if (keyLen < 0) {
/* 759 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 761 */         if (keyLen > 4096000) {
/* 762 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 764 */         pos += VarInt.length(buffer, pos);
/* 765 */         pos += keyLen;
/* 766 */         if (pos > buffer.writerIndex()) {
/* 767 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 769 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 770 */         if (valueArrCount < 0) {
/* 771 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 773 */         pos += VarInt.length(buffer, pos);
/* 774 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 775 */           pos += 37;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 781 */     if ((nullBits[1] & 0x20) != 0) {
/* 782 */       int phobiaModelOffset = buffer.getIntLE(offset + 87);
/* 783 */       if (phobiaModelOffset < 0) {
/* 784 */         return ValidationResult.error("Invalid offset for PhobiaModel");
/*     */       }
/* 786 */       int pos = offset + 91 + phobiaModelOffset;
/* 787 */       if (pos >= buffer.writerIndex()) {
/* 788 */         return ValidationResult.error("Offset out of bounds for PhobiaModel");
/*     */       }
/* 790 */       ValidationResult phobiaModelResult = validateStructure(buffer, pos);
/* 791 */       if (!phobiaModelResult.isValid()) {
/* 792 */         return ValidationResult.error("Invalid PhobiaModel: " + phobiaModelResult.error());
/*     */       }
/* 794 */       pos += computeBytesConsumed(buffer, pos);
/*     */     } 
/* 796 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Model clone() {
/* 800 */     Model copy = new Model();
/* 801 */     copy.assetId = this.assetId;
/* 802 */     copy.path = this.path;
/* 803 */     copy.texture = this.texture;
/* 804 */     copy.gradientSet = this.gradientSet;
/* 805 */     copy.gradientId = this.gradientId;
/* 806 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 807 */     copy.scale = this.scale;
/* 808 */     copy.eyeHeight = this.eyeHeight;
/* 809 */     copy.crouchOffset = this.crouchOffset;
/* 810 */     if (this.animationSets != null) {
/* 811 */       Map<String, AnimationSet> m = new HashMap<>();
/* 812 */       for (Map.Entry<String, AnimationSet> e : this.animationSets.entrySet()) m.put(e.getKey(), ((AnimationSet)e.getValue()).clone()); 
/* 813 */       copy.animationSets = m;
/*     */     } 
/* 815 */     copy.attachments = (this.attachments != null) ? (ModelAttachment[])Arrays.<ModelAttachment>stream(this.attachments).map(e -> e.clone()).toArray(x$0 -> new ModelAttachment[x$0]) : null;
/* 816 */     copy.hitbox = (this.hitbox != null) ? this.hitbox.clone() : null;
/* 817 */     copy.particles = (this.particles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.particles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 818 */     copy.trails = (this.trails != null) ? (ModelTrail[])Arrays.<ModelTrail>stream(this.trails).map(e -> e.clone()).toArray(x$0 -> new ModelTrail[x$0]) : null;
/* 819 */     copy.light = (this.light != null) ? this.light.clone() : null;
/* 820 */     if (this.detailBoxes != null) {
/* 821 */       Map<String, DetailBox[]> m = (Map)new HashMap<>();
/* 822 */       for (Map.Entry<String, DetailBox[]> e : this.detailBoxes.entrySet()) m.put(e.getKey(), (DetailBox[])Arrays.<DetailBox>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new DetailBox[x$0])); 
/* 823 */       copy.detailBoxes = m;
/*     */     } 
/* 825 */     copy.phobia = this.phobia;
/* 826 */     copy.phobiaModel = (this.phobiaModel != null) ? this.phobiaModel.clone() : null;
/* 827 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Model other;
/* 833 */     if (this == obj) return true; 
/* 834 */     if (obj instanceof Model) { other = (Model)obj; } else { return false; }
/* 835 */      return (Objects.equals(this.assetId, other.assetId) && Objects.equals(this.path, other.path) && Objects.equals(this.texture, other.texture) && Objects.equals(this.gradientSet, other.gradientSet) && Objects.equals(this.gradientId, other.gradientId) && Objects.equals(this.camera, other.camera) && this.scale == other.scale && this.eyeHeight == other.eyeHeight && this.crouchOffset == other.crouchOffset && Objects.equals(this.animationSets, other.animationSets) && Arrays.equals((Object[])this.attachments, (Object[])other.attachments) && Objects.equals(this.hitbox, other.hitbox) && Arrays.equals((Object[])this.particles, (Object[])other.particles) && Arrays.equals((Object[])this.trails, (Object[])other.trails) && Objects.equals(this.light, other.light) && Objects.equals(this.detailBoxes, other.detailBoxes) && Objects.equals(this.phobia, other.phobia) && Objects.equals(this.phobiaModel, other.phobiaModel));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 840 */     int result = 1;
/* 841 */     result = 31 * result + Objects.hashCode(this.assetId);
/* 842 */     result = 31 * result + Objects.hashCode(this.path);
/* 843 */     result = 31 * result + Objects.hashCode(this.texture);
/* 844 */     result = 31 * result + Objects.hashCode(this.gradientSet);
/* 845 */     result = 31 * result + Objects.hashCode(this.gradientId);
/* 846 */     result = 31 * result + Objects.hashCode(this.camera);
/* 847 */     result = 31 * result + Float.hashCode(this.scale);
/* 848 */     result = 31 * result + Float.hashCode(this.eyeHeight);
/* 849 */     result = 31 * result + Float.hashCode(this.crouchOffset);
/* 850 */     result = 31 * result + Objects.hashCode(this.animationSets);
/* 851 */     result = 31 * result + Arrays.hashCode((Object[])this.attachments);
/* 852 */     result = 31 * result + Objects.hashCode(this.hitbox);
/* 853 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 854 */     result = 31 * result + Arrays.hashCode((Object[])this.trails);
/* 855 */     result = 31 * result + Objects.hashCode(this.light);
/* 856 */     result = 31 * result + Objects.hashCode(this.detailBoxes);
/* 857 */     result = 31 * result + Objects.hashCode(this.phobia);
/* 858 */     result = 31 * result + Objects.hashCode(this.phobiaModel);
/* 859 */     return result;
/*     */   }
/*     */   
/*     */   public Model() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Model.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */