/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ComponentUpdate {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 3;
/*     */   public static final int FIXED_BLOCK_SIZE = 159;
/*     */   public static final int VARIABLE_FIELD_COUNT = 13;
/*     */   public static final int VARIABLE_BLOCK_START = 211;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  21 */   public ComponentUpdateType type = ComponentUpdateType.Nameplate;
/*     */   
/*     */   @Nullable
/*     */   public Nameplate nameplate;
/*     */   
/*     */   @Nullable
/*     */   public int[] entityUIComponents;
/*     */   @Nullable
/*     */   public CombatTextUpdate combatTextUpdate;
/*     */   @Nullable
/*     */   public Model model;
/*     */   @Nullable
/*     */   public PlayerSkin skin;
/*     */   @Nullable
/*     */   public ItemWithAllMetadata item;
/*     */   public int blockId;
/*     */   public float entityScale;
/*     */   @Nonnull
/*  39 */   public UUID predictionId = new UUID(0L, 0L); @Nullable public Equipment equipment; @Nullable
/*     */   public Map<Integer, EntityStatUpdate[]> entityStatUpdates; @Nullable
/*     */   public ModelTransform transform; @Nullable
/*     */   public MovementStates movementStates; @Nullable
/*     */   public EntityEffectUpdate[] entityEffectUpdates; @Nullable
/*     */   public Map<InteractionType, Integer> interactions; @Nullable
/*     */   public ColorLight dynamicLight; public int hitboxCollisionConfigIndex; public int repulsionConfigIndex; @Nullable
/*     */   public int[] soundEventIds; @Nullable
/*     */   public String interactionHint; @Nullable
/*     */   public MountedUpdate mounted; @Nullable
/*  49 */   public String[] activeAnimations; public ComponentUpdate(@Nonnull ComponentUpdateType type, @Nullable Nameplate nameplate, @Nullable int[] entityUIComponents, @Nullable CombatTextUpdate combatTextUpdate, @Nullable Model model, @Nullable PlayerSkin skin, @Nullable ItemWithAllMetadata item, int blockId, float entityScale, @Nullable Equipment equipment, @Nullable Map<Integer, EntityStatUpdate[]> entityStatUpdates, @Nullable ModelTransform transform, @Nullable MovementStates movementStates, @Nullable EntityEffectUpdate[] entityEffectUpdates, @Nullable Map<InteractionType, Integer> interactions, @Nullable ColorLight dynamicLight, int hitboxCollisionConfigIndex, int repulsionConfigIndex, @Nonnull UUID predictionId, @Nullable int[] soundEventIds, @Nullable String interactionHint, @Nullable MountedUpdate mounted, @Nullable String[] activeAnimations) { this.type = type;
/*  50 */     this.nameplate = nameplate;
/*  51 */     this.entityUIComponents = entityUIComponents;
/*  52 */     this.combatTextUpdate = combatTextUpdate;
/*  53 */     this.model = model;
/*  54 */     this.skin = skin;
/*  55 */     this.item = item;
/*  56 */     this.blockId = blockId;
/*  57 */     this.entityScale = entityScale;
/*  58 */     this.equipment = equipment;
/*  59 */     this.entityStatUpdates = entityStatUpdates;
/*  60 */     this.transform = transform;
/*  61 */     this.movementStates = movementStates;
/*  62 */     this.entityEffectUpdates = entityEffectUpdates;
/*  63 */     this.interactions = interactions;
/*  64 */     this.dynamicLight = dynamicLight;
/*  65 */     this.hitboxCollisionConfigIndex = hitboxCollisionConfigIndex;
/*  66 */     this.repulsionConfigIndex = repulsionConfigIndex;
/*  67 */     this.predictionId = predictionId;
/*  68 */     this.soundEventIds = soundEventIds;
/*  69 */     this.interactionHint = interactionHint;
/*  70 */     this.mounted = mounted;
/*  71 */     this.activeAnimations = activeAnimations; }
/*     */ 
/*     */   
/*     */   public ComponentUpdate(@Nonnull ComponentUpdate other) {
/*  75 */     this.type = other.type;
/*  76 */     this.nameplate = other.nameplate;
/*  77 */     this.entityUIComponents = other.entityUIComponents;
/*  78 */     this.combatTextUpdate = other.combatTextUpdate;
/*  79 */     this.model = other.model;
/*  80 */     this.skin = other.skin;
/*  81 */     this.item = other.item;
/*  82 */     this.blockId = other.blockId;
/*  83 */     this.entityScale = other.entityScale;
/*  84 */     this.equipment = other.equipment;
/*  85 */     this.entityStatUpdates = other.entityStatUpdates;
/*  86 */     this.transform = other.transform;
/*  87 */     this.movementStates = other.movementStates;
/*  88 */     this.entityEffectUpdates = other.entityEffectUpdates;
/*  89 */     this.interactions = other.interactions;
/*  90 */     this.dynamicLight = other.dynamicLight;
/*  91 */     this.hitboxCollisionConfigIndex = other.hitboxCollisionConfigIndex;
/*  92 */     this.repulsionConfigIndex = other.repulsionConfigIndex;
/*  93 */     this.predictionId = other.predictionId;
/*  94 */     this.soundEventIds = other.soundEventIds;
/*  95 */     this.interactionHint = other.interactionHint;
/*  96 */     this.mounted = other.mounted;
/*  97 */     this.activeAnimations = other.activeAnimations;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/* 102 */     ComponentUpdate obj = new ComponentUpdate();
/* 103 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 3);
/* 104 */     obj.type = ComponentUpdateType.fromValue(buf.getByte(offset + 3));
/* 105 */     obj.blockId = buf.getIntLE(offset + 4);
/* 106 */     obj.entityScale = buf.getFloatLE(offset + 8);
/* 107 */     if ((nullBits[1] & 0x1) != 0) obj.transform = ModelTransform.deserialize(buf, offset + 12); 
/* 108 */     if ((nullBits[1] & 0x2) != 0) obj.movementStates = MovementStates.deserialize(buf, offset + 61); 
/* 109 */     if ((nullBits[1] & 0x10) != 0) obj.dynamicLight = ColorLight.deserialize(buf, offset + 83); 
/* 110 */     obj.hitboxCollisionConfigIndex = buf.getIntLE(offset + 87);
/* 111 */     obj.repulsionConfigIndex = buf.getIntLE(offset + 91);
/* 112 */     obj.predictionId = PacketIO.readUUID(buf, offset + 95);
/* 113 */     if ((nullBits[1] & 0x80) != 0) obj.mounted = MountedUpdate.deserialize(buf, offset + 111);
/*     */     
/* 115 */     if ((nullBits[0] & 0x1) != 0) {
/* 116 */       int varPos0 = offset + 211 + buf.getIntLE(offset + 159);
/* 117 */       obj.nameplate = Nameplate.deserialize(buf, varPos0);
/*     */     } 
/* 119 */     if ((nullBits[0] & 0x2) != 0) {
/* 120 */       int varPos1 = offset + 211 + buf.getIntLE(offset + 163);
/* 121 */       int entityUIComponentsCount = VarInt.peek(buf, varPos1);
/* 122 */       if (entityUIComponentsCount < 0) throw ProtocolException.negativeLength("EntityUIComponents", entityUIComponentsCount); 
/* 123 */       if (entityUIComponentsCount > 4096000) throw ProtocolException.arrayTooLong("EntityUIComponents", entityUIComponentsCount, 4096000); 
/* 124 */       int varIntLen = VarInt.length(buf, varPos1);
/* 125 */       if ((varPos1 + varIntLen) + entityUIComponentsCount * 4L > buf.readableBytes())
/* 126 */         throw ProtocolException.bufferTooSmall("EntityUIComponents", varPos1 + varIntLen + entityUIComponentsCount * 4, buf.readableBytes()); 
/* 127 */       obj.entityUIComponents = new int[entityUIComponentsCount];
/* 128 */       for (int i = 0; i < entityUIComponentsCount; i++) {
/* 129 */         obj.entityUIComponents[i] = buf.getIntLE(varPos1 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 132 */     if ((nullBits[0] & 0x4) != 0) {
/* 133 */       int varPos2 = offset + 211 + buf.getIntLE(offset + 167);
/* 134 */       obj.combatTextUpdate = CombatTextUpdate.deserialize(buf, varPos2);
/*     */     } 
/* 136 */     if ((nullBits[0] & 0x8) != 0) {
/* 137 */       int varPos3 = offset + 211 + buf.getIntLE(offset + 171);
/* 138 */       obj.model = Model.deserialize(buf, varPos3);
/*     */     } 
/* 140 */     if ((nullBits[0] & 0x10) != 0) {
/* 141 */       int varPos4 = offset + 211 + buf.getIntLE(offset + 175);
/* 142 */       obj.skin = PlayerSkin.deserialize(buf, varPos4);
/*     */     } 
/* 144 */     if ((nullBits[0] & 0x20) != 0) {
/* 145 */       int varPos5 = offset + 211 + buf.getIntLE(offset + 179);
/* 146 */       obj.item = ItemWithAllMetadata.deserialize(buf, varPos5);
/*     */     } 
/* 148 */     if ((nullBits[0] & 0x40) != 0) {
/* 149 */       int varPos6 = offset + 211 + buf.getIntLE(offset + 183);
/* 150 */       obj.equipment = Equipment.deserialize(buf, varPos6);
/*     */     } 
/* 152 */     if ((nullBits[0] & 0x80) != 0) {
/* 153 */       int varPos7 = offset + 211 + buf.getIntLE(offset + 187);
/* 154 */       int entityStatUpdatesCount = VarInt.peek(buf, varPos7);
/* 155 */       if (entityStatUpdatesCount < 0) throw ProtocolException.negativeLength("EntityStatUpdates", entityStatUpdatesCount); 
/* 156 */       if (entityStatUpdatesCount > 4096000) throw ProtocolException.dictionaryTooLarge("EntityStatUpdates", entityStatUpdatesCount, 4096000); 
/* 157 */       int varIntLen = VarInt.length(buf, varPos7);
/* 158 */       obj.entityStatUpdates = (Map)new HashMap<>(entityStatUpdatesCount);
/* 159 */       int dictPos = varPos7 + varIntLen;
/* 160 */       for (int i = 0; i < entityStatUpdatesCount; i++) {
/* 161 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/* 162 */         int valLen = VarInt.peek(buf, dictPos);
/* 163 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/* 164 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/* 165 */         int valVarLen = VarInt.length(buf, dictPos);
/* 166 */         if ((dictPos + valVarLen) + valLen * 13L > buf.readableBytes())
/* 167 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 13, buf.readableBytes()); 
/* 168 */         dictPos += valVarLen;
/* 169 */         EntityStatUpdate[] val = new EntityStatUpdate[valLen];
/* 170 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/* 171 */           val[valIdx] = EntityStatUpdate.deserialize(buf, dictPos);
/* 172 */           dictPos += EntityStatUpdate.computeBytesConsumed(buf, dictPos);
/*     */         } 
/* 174 */         if (obj.entityStatUpdates.put(Integer.valueOf(key), val) != null)
/* 175 */           throw ProtocolException.duplicateKey("entityStatUpdates", Integer.valueOf(key)); 
/*     */       } 
/*     */     } 
/* 178 */     if ((nullBits[1] & 0x4) != 0) {
/* 179 */       int varPos8 = offset + 211 + buf.getIntLE(offset + 191);
/* 180 */       int entityEffectUpdatesCount = VarInt.peek(buf, varPos8);
/* 181 */       if (entityEffectUpdatesCount < 0) throw ProtocolException.negativeLength("EntityEffectUpdates", entityEffectUpdatesCount); 
/* 182 */       if (entityEffectUpdatesCount > 4096000) throw ProtocolException.arrayTooLong("EntityEffectUpdates", entityEffectUpdatesCount, 4096000); 
/* 183 */       int varIntLen = VarInt.length(buf, varPos8);
/* 184 */       if ((varPos8 + varIntLen) + entityEffectUpdatesCount * 12L > buf.readableBytes())
/* 185 */         throw ProtocolException.bufferTooSmall("EntityEffectUpdates", varPos8 + varIntLen + entityEffectUpdatesCount * 12, buf.readableBytes()); 
/* 186 */       obj.entityEffectUpdates = new EntityEffectUpdate[entityEffectUpdatesCount];
/* 187 */       int elemPos = varPos8 + varIntLen;
/* 188 */       for (int i = 0; i < entityEffectUpdatesCount; i++) {
/* 189 */         obj.entityEffectUpdates[i] = EntityEffectUpdate.deserialize(buf, elemPos);
/* 190 */         elemPos += EntityEffectUpdate.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 193 */     if ((nullBits[1] & 0x8) != 0) {
/* 194 */       int varPos9 = offset + 211 + buf.getIntLE(offset + 195);
/* 195 */       int interactionsCount = VarInt.peek(buf, varPos9);
/* 196 */       if (interactionsCount < 0) throw ProtocolException.negativeLength("Interactions", interactionsCount); 
/* 197 */       if (interactionsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", interactionsCount, 4096000); 
/* 198 */       int varIntLen = VarInt.length(buf, varPos9);
/* 199 */       obj.interactions = new HashMap<>(interactionsCount);
/* 200 */       int dictPos = varPos9 + varIntLen;
/* 201 */       for (int i = 0; i < interactionsCount; i++) {
/* 202 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/* 203 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 204 */         if (obj.interactions.put(key, Integer.valueOf(val)) != null)
/* 205 */           throw ProtocolException.duplicateKey("interactions", key); 
/*     */       } 
/*     */     } 
/* 208 */     if ((nullBits[1] & 0x20) != 0) {
/* 209 */       int varPos10 = offset + 211 + buf.getIntLE(offset + 199);
/* 210 */       int soundEventIdsCount = VarInt.peek(buf, varPos10);
/* 211 */       if (soundEventIdsCount < 0) throw ProtocolException.negativeLength("SoundEventIds", soundEventIdsCount); 
/* 212 */       if (soundEventIdsCount > 4096000) throw ProtocolException.arrayTooLong("SoundEventIds", soundEventIdsCount, 4096000); 
/* 213 */       int varIntLen = VarInt.length(buf, varPos10);
/* 214 */       if ((varPos10 + varIntLen) + soundEventIdsCount * 4L > buf.readableBytes())
/* 215 */         throw ProtocolException.bufferTooSmall("SoundEventIds", varPos10 + varIntLen + soundEventIdsCount * 4, buf.readableBytes()); 
/* 216 */       obj.soundEventIds = new int[soundEventIdsCount];
/* 217 */       for (int i = 0; i < soundEventIdsCount; i++) {
/* 218 */         obj.soundEventIds[i] = buf.getIntLE(varPos10 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 221 */     if ((nullBits[1] & 0x40) != 0) {
/* 222 */       int varPos11 = offset + 211 + buf.getIntLE(offset + 203);
/* 223 */       int interactionHintLen = VarInt.peek(buf, varPos11);
/* 224 */       if (interactionHintLen < 0) throw ProtocolException.negativeLength("InteractionHint", interactionHintLen); 
/* 225 */       if (interactionHintLen > 4096000) throw ProtocolException.stringTooLong("InteractionHint", interactionHintLen, 4096000); 
/* 226 */       obj.interactionHint = PacketIO.readVarString(buf, varPos11, PacketIO.UTF8);
/*     */     } 
/* 228 */     if ((nullBits[2] & 0x1) != 0) {
/* 229 */       int varPos12 = offset + 211 + buf.getIntLE(offset + 207);
/* 230 */       int activeAnimationsCount = VarInt.peek(buf, varPos12);
/* 231 */       if (activeAnimationsCount < 0) throw ProtocolException.negativeLength("ActiveAnimations", activeAnimationsCount); 
/* 232 */       if (activeAnimationsCount > 4096000) throw ProtocolException.arrayTooLong("ActiveAnimations", activeAnimationsCount, 4096000); 
/* 233 */       int varIntLen = VarInt.length(buf, varPos12);
/* 234 */       int activeAnimationsBitfieldSize = (activeAnimationsCount + 7) / 8;
/* 235 */       byte[] activeAnimationsBitfield = PacketIO.readBytes(buf, varPos12 + varIntLen, activeAnimationsBitfieldSize);
/* 236 */       obj.activeAnimations = new String[activeAnimationsCount];
/* 237 */       int elemPos = varPos12 + varIntLen + activeAnimationsBitfieldSize;
/* 238 */       for (int i = 0; i < activeAnimationsCount; i++) {
/* 239 */         if ((activeAnimationsBitfield[i / 8] & 1 << i % 8) != 0) {
/* 240 */           int strLen = VarInt.peek(buf, elemPos);
/* 241 */           if (strLen < 0) throw ProtocolException.negativeLength("activeAnimations[" + i + "]", strLen); 
/* 242 */           if (strLen > 4096000) throw ProtocolException.stringTooLong("activeAnimations[" + i + "]", strLen, 4096000); 
/* 243 */           int strVarLen = VarInt.length(buf, elemPos);
/* 244 */           obj.activeAnimations[i] = PacketIO.readVarString(buf, elemPos);
/* 245 */           elemPos += strVarLen + strLen;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 254 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 3);
/* 255 */     int maxEnd = 211;
/* 256 */     if ((nullBits[0] & 0x1) != 0) {
/* 257 */       int fieldOffset0 = buf.getIntLE(offset + 159);
/* 258 */       int pos0 = offset + 211 + fieldOffset0;
/* 259 */       pos0 += Nameplate.computeBytesConsumed(buf, pos0);
/* 260 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 262 */     if ((nullBits[0] & 0x2) != 0) {
/* 263 */       int fieldOffset1 = buf.getIntLE(offset + 163);
/* 264 */       int pos1 = offset + 211 + fieldOffset1;
/* 265 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/* 266 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 268 */     if ((nullBits[0] & 0x4) != 0) {
/* 269 */       int fieldOffset2 = buf.getIntLE(offset + 167);
/* 270 */       int pos2 = offset + 211 + fieldOffset2;
/* 271 */       pos2 += CombatTextUpdate.computeBytesConsumed(buf, pos2);
/* 272 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 274 */     if ((nullBits[0] & 0x8) != 0) {
/* 275 */       int fieldOffset3 = buf.getIntLE(offset + 171);
/* 276 */       int pos3 = offset + 211 + fieldOffset3;
/* 277 */       pos3 += Model.computeBytesConsumed(buf, pos3);
/* 278 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 280 */     if ((nullBits[0] & 0x10) != 0) {
/* 281 */       int fieldOffset4 = buf.getIntLE(offset + 175);
/* 282 */       int pos4 = offset + 211 + fieldOffset4;
/* 283 */       pos4 += PlayerSkin.computeBytesConsumed(buf, pos4);
/* 284 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 286 */     if ((nullBits[0] & 0x20) != 0) {
/* 287 */       int fieldOffset5 = buf.getIntLE(offset + 179);
/* 288 */       int pos5 = offset + 211 + fieldOffset5;
/* 289 */       pos5 += ItemWithAllMetadata.computeBytesConsumed(buf, pos5);
/* 290 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 292 */     if ((nullBits[0] & 0x40) != 0) {
/* 293 */       int fieldOffset6 = buf.getIntLE(offset + 183);
/* 294 */       int pos6 = offset + 211 + fieldOffset6;
/* 295 */       pos6 += Equipment.computeBytesConsumed(buf, pos6);
/* 296 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 298 */     if ((nullBits[0] & 0x80) != 0) {
/* 299 */       int fieldOffset7 = buf.getIntLE(offset + 187);
/* 300 */       int pos7 = offset + 211 + fieldOffset7;
/* 301 */       int dictLen = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7);
/* 302 */       for (int i = 0; i < dictLen; ) { int al; int j; for (pos7 += 4, al = VarInt.peek(buf, pos7), pos7 += VarInt.length(buf, pos7), j = 0; j < al; ) { pos7 += EntityStatUpdate.computeBytesConsumed(buf, pos7); j++; }  i++; }
/* 303 */        if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*     */     } 
/* 305 */     if ((nullBits[1] & 0x4) != 0) {
/* 306 */       int fieldOffset8 = buf.getIntLE(offset + 191);
/* 307 */       int pos8 = offset + 211 + fieldOffset8;
/* 308 */       int arrLen = VarInt.peek(buf, pos8); pos8 += VarInt.length(buf, pos8);
/* 309 */       for (int i = 0; i < arrLen; ) { pos8 += EntityEffectUpdate.computeBytesConsumed(buf, pos8); i++; }
/* 310 */        if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*     */     } 
/* 312 */     if ((nullBits[1] & 0x8) != 0) {
/* 313 */       int fieldOffset9 = buf.getIntLE(offset + 195);
/* 314 */       int pos9 = offset + 211 + fieldOffset9;
/* 315 */       int dictLen = VarInt.peek(buf, pos9); pos9 += VarInt.length(buf, pos9);
/* 316 */       for (int i = 0; i < dictLen; ) { pos9++; pos9 += 4; i++; }
/* 317 */        if (pos9 - offset > maxEnd) maxEnd = pos9 - offset; 
/*     */     } 
/* 319 */     if ((nullBits[1] & 0x20) != 0) {
/* 320 */       int fieldOffset10 = buf.getIntLE(offset + 199);
/* 321 */       int pos10 = offset + 211 + fieldOffset10;
/* 322 */       int arrLen = VarInt.peek(buf, pos10); pos10 += VarInt.length(buf, pos10) + arrLen * 4;
/* 323 */       if (pos10 - offset > maxEnd) maxEnd = pos10 - offset; 
/*     */     } 
/* 325 */     if ((nullBits[1] & 0x40) != 0) {
/* 326 */       int fieldOffset11 = buf.getIntLE(offset + 203);
/* 327 */       int pos11 = offset + 211 + fieldOffset11;
/* 328 */       int sl = VarInt.peek(buf, pos11); pos11 += VarInt.length(buf, pos11) + sl;
/* 329 */       if (pos11 - offset > maxEnd) maxEnd = pos11 - offset; 
/*     */     } 
/* 331 */     if ((nullBits[2] & 0x1) != 0) {
/* 332 */       int fieldOffset12 = buf.getIntLE(offset + 207);
/* 333 */       int pos12 = offset + 211 + fieldOffset12;
/* 334 */       int arrLen = VarInt.peek(buf, pos12); pos12 += VarInt.length(buf, pos12);
/* 335 */       int bitfieldSize = (arrLen + 7) / 8;
/* 336 */       byte[] bitfield = PacketIO.readBytes(buf, pos12, bitfieldSize);
/* 337 */       pos12 += bitfieldSize;
/* 338 */       for (int i = 0; i < arrLen; ) { if ((bitfield[i / 8] & 1 << i % 8) != 0) { int sl = VarInt.peek(buf, pos12); pos12 += VarInt.length(buf, pos12) + sl; }  i++; }
/* 339 */        if (pos12 - offset > maxEnd) maxEnd = pos12 - offset; 
/*     */     } 
/* 341 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 346 */     int startPos = buf.writerIndex();
/* 347 */     byte[] nullBits = new byte[3];
/* 348 */     if (this.nameplate != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 349 */     if (this.entityUIComponents != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 350 */     if (this.combatTextUpdate != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 351 */     if (this.model != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 352 */     if (this.skin != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 353 */     if (this.item != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 354 */     if (this.equipment != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 355 */     if (this.entityStatUpdates != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 356 */     if (this.transform != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 357 */     if (this.movementStates != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 358 */     if (this.entityEffectUpdates != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/* 359 */     if (this.interactions != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/* 360 */     if (this.dynamicLight != null) nullBits[1] = (byte)(nullBits[1] | 0x10); 
/* 361 */     if (this.soundEventIds != null) nullBits[1] = (byte)(nullBits[1] | 0x20); 
/* 362 */     if (this.interactionHint != null) nullBits[1] = (byte)(nullBits[1] | 0x40); 
/* 363 */     if (this.mounted != null) nullBits[1] = (byte)(nullBits[1] | 0x80); 
/* 364 */     if (this.activeAnimations != null) nullBits[2] = (byte)(nullBits[2] | 0x1); 
/* 365 */     buf.writeBytes(nullBits);
/*     */     
/* 367 */     buf.writeByte(this.type.getValue());
/* 368 */     buf.writeIntLE(this.blockId);
/* 369 */     buf.writeFloatLE(this.entityScale);
/* 370 */     if (this.transform != null) { this.transform.serialize(buf); } else { buf.writeZero(49); }
/* 371 */      if (this.movementStates != null) { this.movementStates.serialize(buf); } else { buf.writeZero(22); }
/* 372 */      if (this.dynamicLight != null) { this.dynamicLight.serialize(buf); } else { buf.writeZero(4); }
/* 373 */      buf.writeIntLE(this.hitboxCollisionConfigIndex);
/* 374 */     buf.writeIntLE(this.repulsionConfigIndex);
/* 375 */     PacketIO.writeUUID(buf, this.predictionId);
/* 376 */     if (this.mounted != null) { this.mounted.serialize(buf); } else { buf.writeZero(48); }
/*     */     
/* 378 */     int nameplateOffsetSlot = buf.writerIndex();
/* 379 */     buf.writeIntLE(0);
/* 380 */     int entityUIComponentsOffsetSlot = buf.writerIndex();
/* 381 */     buf.writeIntLE(0);
/* 382 */     int combatTextUpdateOffsetSlot = buf.writerIndex();
/* 383 */     buf.writeIntLE(0);
/* 384 */     int modelOffsetSlot = buf.writerIndex();
/* 385 */     buf.writeIntLE(0);
/* 386 */     int skinOffsetSlot = buf.writerIndex();
/* 387 */     buf.writeIntLE(0);
/* 388 */     int itemOffsetSlot = buf.writerIndex();
/* 389 */     buf.writeIntLE(0);
/* 390 */     int equipmentOffsetSlot = buf.writerIndex();
/* 391 */     buf.writeIntLE(0);
/* 392 */     int entityStatUpdatesOffsetSlot = buf.writerIndex();
/* 393 */     buf.writeIntLE(0);
/* 394 */     int entityEffectUpdatesOffsetSlot = buf.writerIndex();
/* 395 */     buf.writeIntLE(0);
/* 396 */     int interactionsOffsetSlot = buf.writerIndex();
/* 397 */     buf.writeIntLE(0);
/* 398 */     int soundEventIdsOffsetSlot = buf.writerIndex();
/* 399 */     buf.writeIntLE(0);
/* 400 */     int interactionHintOffsetSlot = buf.writerIndex();
/* 401 */     buf.writeIntLE(0);
/* 402 */     int activeAnimationsOffsetSlot = buf.writerIndex();
/* 403 */     buf.writeIntLE(0);
/*     */     
/* 405 */     int varBlockStart = buf.writerIndex();
/* 406 */     if (this.nameplate != null) {
/* 407 */       buf.setIntLE(nameplateOffsetSlot, buf.writerIndex() - varBlockStart);
/* 408 */       this.nameplate.serialize(buf);
/*     */     } else {
/* 410 */       buf.setIntLE(nameplateOffsetSlot, -1);
/*     */     } 
/* 412 */     if (this.entityUIComponents != null) {
/* 413 */       buf.setIntLE(entityUIComponentsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 414 */       if (this.entityUIComponents.length > 4096000) throw ProtocolException.arrayTooLong("EntityUIComponents", this.entityUIComponents.length, 4096000);  VarInt.write(buf, this.entityUIComponents.length); for (int item : this.entityUIComponents) buf.writeIntLE(item); 
/*     */     } else {
/* 416 */       buf.setIntLE(entityUIComponentsOffsetSlot, -1);
/*     */     } 
/* 418 */     if (this.combatTextUpdate != null) {
/* 419 */       buf.setIntLE(combatTextUpdateOffsetSlot, buf.writerIndex() - varBlockStart);
/* 420 */       this.combatTextUpdate.serialize(buf);
/*     */     } else {
/* 422 */       buf.setIntLE(combatTextUpdateOffsetSlot, -1);
/*     */     } 
/* 424 */     if (this.model != null) {
/* 425 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 426 */       this.model.serialize(buf);
/*     */     } else {
/* 428 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/* 430 */     if (this.skin != null) {
/* 431 */       buf.setIntLE(skinOffsetSlot, buf.writerIndex() - varBlockStart);
/* 432 */       this.skin.serialize(buf);
/*     */     } else {
/* 434 */       buf.setIntLE(skinOffsetSlot, -1);
/*     */     } 
/* 436 */     if (this.item != null) {
/* 437 */       buf.setIntLE(itemOffsetSlot, buf.writerIndex() - varBlockStart);
/* 438 */       this.item.serialize(buf);
/*     */     } else {
/* 440 */       buf.setIntLE(itemOffsetSlot, -1);
/*     */     } 
/* 442 */     if (this.equipment != null) {
/* 443 */       buf.setIntLE(equipmentOffsetSlot, buf.writerIndex() - varBlockStart);
/* 444 */       this.equipment.serialize(buf);
/*     */     } else {
/* 446 */       buf.setIntLE(equipmentOffsetSlot, -1);
/*     */     } 
/* 448 */     if (this.entityStatUpdates != null)
/* 449 */     { buf.setIntLE(entityStatUpdatesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 450 */       if (this.entityStatUpdates.size() > 4096000) throw ProtocolException.dictionaryTooLarge("EntityStatUpdates", this.entityStatUpdates.size(), 4096000);  VarInt.write(buf, this.entityStatUpdates.size()); for (Map.Entry<Integer, EntityStatUpdate[]> e : this.entityStatUpdates.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); VarInt.write(buf, ((EntityStatUpdate[])e.getValue()).length); for (EntityStatUpdate arrItem : (EntityStatUpdate[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 452 */     else { buf.setIntLE(entityStatUpdatesOffsetSlot, -1); }
/*     */     
/* 454 */     if (this.entityEffectUpdates != null) {
/* 455 */       buf.setIntLE(entityEffectUpdatesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 456 */       if (this.entityEffectUpdates.length > 4096000) throw ProtocolException.arrayTooLong("EntityEffectUpdates", this.entityEffectUpdates.length, 4096000);  VarInt.write(buf, this.entityEffectUpdates.length); for (EntityEffectUpdate item : this.entityEffectUpdates) item.serialize(buf); 
/*     */     } else {
/* 458 */       buf.setIntLE(entityEffectUpdatesOffsetSlot, -1);
/*     */     } 
/* 460 */     if (this.interactions != null)
/* 461 */     { buf.setIntLE(interactionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 462 */       if (this.interactions.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", this.interactions.size(), 4096000);  VarInt.write(buf, this.interactions.size()); for (Map.Entry<InteractionType, Integer> e : this.interactions.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 464 */     else { buf.setIntLE(interactionsOffsetSlot, -1); }
/*     */     
/* 466 */     if (this.soundEventIds != null) {
/* 467 */       buf.setIntLE(soundEventIdsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 468 */       if (this.soundEventIds.length > 4096000) throw ProtocolException.arrayTooLong("SoundEventIds", this.soundEventIds.length, 4096000);  VarInt.write(buf, this.soundEventIds.length); for (int item : this.soundEventIds) buf.writeIntLE(item); 
/*     */     } else {
/* 470 */       buf.setIntLE(soundEventIdsOffsetSlot, -1);
/*     */     } 
/* 472 */     if (this.interactionHint != null) {
/* 473 */       buf.setIntLE(interactionHintOffsetSlot, buf.writerIndex() - varBlockStart);
/* 474 */       PacketIO.writeVarString(buf, this.interactionHint, 4096000);
/*     */     } else {
/* 476 */       buf.setIntLE(interactionHintOffsetSlot, -1);
/*     */     } 
/* 478 */     if (this.activeAnimations != null) {
/* 479 */       buf.setIntLE(activeAnimationsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 480 */       if (this.activeAnimations.length > 4096000) throw ProtocolException.arrayTooLong("ActiveAnimations", this.activeAnimations.length, 4096000);  VarInt.write(buf, this.activeAnimations.length);
/* 481 */       int activeAnimationsBitfieldSize = (this.activeAnimations.length + 7) / 8;
/* 482 */       byte[] activeAnimationsBitfield = new byte[activeAnimationsBitfieldSize]; int i;
/* 483 */       for (i = 0; i < this.activeAnimations.length; i++) {
/* 484 */         if (this.activeAnimations[i] != null) activeAnimationsBitfield[i / 8] = (byte)(activeAnimationsBitfield[i / 8] | (byte)(1 << i % 8)); 
/*     */       } 
/* 486 */       buf.writeBytes(activeAnimationsBitfield);
/* 487 */       for (i = 0; i < this.activeAnimations.length; i++) {
/* 488 */         if (this.activeAnimations[i] != null) PacketIO.writeVarString(buf, this.activeAnimations[i], 4096000); 
/*     */       } 
/*     */     } else {
/* 491 */       buf.setIntLE(activeAnimationsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 497 */     int size = 211;
/* 498 */     if (this.nameplate != null) size += this.nameplate.computeSize(); 
/* 499 */     if (this.entityUIComponents != null) size += VarInt.size(this.entityUIComponents.length) + this.entityUIComponents.length * 4; 
/* 500 */     if (this.combatTextUpdate != null) size += this.combatTextUpdate.computeSize(); 
/* 501 */     if (this.model != null) size += this.model.computeSize(); 
/* 502 */     if (this.skin != null) size += this.skin.computeSize(); 
/* 503 */     if (this.item != null) size += this.item.computeSize(); 
/* 504 */     if (this.equipment != null) size += this.equipment.computeSize(); 
/* 505 */     if (this.entityStatUpdates != null) {
/* 506 */       int entityStatUpdatesSize = 0;
/* 507 */       for (Map.Entry<Integer, EntityStatUpdate[]> kvp : this.entityStatUpdates.entrySet()) entityStatUpdatesSize += 4 + VarInt.size(((EntityStatUpdate[])kvp.getValue()).length) + Arrays.<EntityStatUpdate>stream(kvp.getValue()).mapToInt(inner -> inner.computeSize()).sum(); 
/* 508 */       size += VarInt.size(this.entityStatUpdates.size()) + entityStatUpdatesSize;
/*     */     } 
/* 510 */     if (this.entityEffectUpdates != null) {
/* 511 */       int entityEffectUpdatesSize = 0;
/* 512 */       for (EntityEffectUpdate elem : this.entityEffectUpdates) entityEffectUpdatesSize += elem.computeSize(); 
/* 513 */       size += VarInt.size(this.entityEffectUpdates.length) + entityEffectUpdatesSize;
/*     */     } 
/* 515 */     if (this.interactions != null) size += VarInt.size(this.interactions.size()) + this.interactions.size() * 5; 
/* 516 */     if (this.soundEventIds != null) size += VarInt.size(this.soundEventIds.length) + this.soundEventIds.length * 4; 
/* 517 */     if (this.interactionHint != null) size += PacketIO.stringSize(this.interactionHint); 
/* 518 */     if (this.activeAnimations != null) {
/* 519 */       int activeAnimationsSize = 0;
/* 520 */       for (String elem : this.activeAnimations) { if (elem != null) activeAnimationsSize += PacketIO.stringSize(elem);  }
/* 521 */        size += VarInt.size(this.activeAnimations.length) + (this.activeAnimations.length + 7) / 8 + activeAnimationsSize;
/*     */     } 
/*     */     
/* 524 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 528 */     if (buffer.readableBytes() - offset < 211) {
/* 529 */       return ValidationResult.error("Buffer too small: expected at least 211 bytes");
/*     */     }
/*     */     
/* 532 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 3);
/*     */     
/* 534 */     if ((nullBits[0] & 0x1) != 0) {
/* 535 */       int nameplateOffset = buffer.getIntLE(offset + 159);
/* 536 */       if (nameplateOffset < 0) {
/* 537 */         return ValidationResult.error("Invalid offset for Nameplate");
/*     */       }
/* 539 */       int pos = offset + 211 + nameplateOffset;
/* 540 */       if (pos >= buffer.writerIndex()) {
/* 541 */         return ValidationResult.error("Offset out of bounds for Nameplate");
/*     */       }
/* 543 */       ValidationResult nameplateResult = Nameplate.validateStructure(buffer, pos);
/* 544 */       if (!nameplateResult.isValid()) {
/* 545 */         return ValidationResult.error("Invalid Nameplate: " + nameplateResult.error());
/*     */       }
/* 547 */       pos += Nameplate.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 550 */     if ((nullBits[0] & 0x2) != 0) {
/* 551 */       int entityUIComponentsOffset = buffer.getIntLE(offset + 163);
/* 552 */       if (entityUIComponentsOffset < 0) {
/* 553 */         return ValidationResult.error("Invalid offset for EntityUIComponents");
/*     */       }
/* 555 */       int pos = offset + 211 + entityUIComponentsOffset;
/* 556 */       if (pos >= buffer.writerIndex()) {
/* 557 */         return ValidationResult.error("Offset out of bounds for EntityUIComponents");
/*     */       }
/* 559 */       int entityUIComponentsCount = VarInt.peek(buffer, pos);
/* 560 */       if (entityUIComponentsCount < 0) {
/* 561 */         return ValidationResult.error("Invalid array count for EntityUIComponents");
/*     */       }
/* 563 */       if (entityUIComponentsCount > 4096000) {
/* 564 */         return ValidationResult.error("EntityUIComponents exceeds max length 4096000");
/*     */       }
/* 566 */       pos += VarInt.length(buffer, pos);
/* 567 */       pos += entityUIComponentsCount * 4;
/* 568 */       if (pos > buffer.writerIndex()) {
/* 569 */         return ValidationResult.error("Buffer overflow reading EntityUIComponents");
/*     */       }
/*     */     } 
/*     */     
/* 573 */     if ((nullBits[0] & 0x4) != 0) {
/* 574 */       int combatTextUpdateOffset = buffer.getIntLE(offset + 167);
/* 575 */       if (combatTextUpdateOffset < 0) {
/* 576 */         return ValidationResult.error("Invalid offset for CombatTextUpdate");
/*     */       }
/* 578 */       int pos = offset + 211 + combatTextUpdateOffset;
/* 579 */       if (pos >= buffer.writerIndex()) {
/* 580 */         return ValidationResult.error("Offset out of bounds for CombatTextUpdate");
/*     */       }
/* 582 */       ValidationResult combatTextUpdateResult = CombatTextUpdate.validateStructure(buffer, pos);
/* 583 */       if (!combatTextUpdateResult.isValid()) {
/* 584 */         return ValidationResult.error("Invalid CombatTextUpdate: " + combatTextUpdateResult.error());
/*     */       }
/* 586 */       pos += CombatTextUpdate.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 589 */     if ((nullBits[0] & 0x8) != 0) {
/* 590 */       int modelOffset = buffer.getIntLE(offset + 171);
/* 591 */       if (modelOffset < 0) {
/* 592 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 594 */       int pos = offset + 211 + modelOffset;
/* 595 */       if (pos >= buffer.writerIndex()) {
/* 596 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 598 */       ValidationResult modelResult = Model.validateStructure(buffer, pos);
/* 599 */       if (!modelResult.isValid()) {
/* 600 */         return ValidationResult.error("Invalid Model: " + modelResult.error());
/*     */       }
/* 602 */       pos += Model.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 605 */     if ((nullBits[0] & 0x10) != 0) {
/* 606 */       int skinOffset = buffer.getIntLE(offset + 175);
/* 607 */       if (skinOffset < 0) {
/* 608 */         return ValidationResult.error("Invalid offset for Skin");
/*     */       }
/* 610 */       int pos = offset + 211 + skinOffset;
/* 611 */       if (pos >= buffer.writerIndex()) {
/* 612 */         return ValidationResult.error("Offset out of bounds for Skin");
/*     */       }
/* 614 */       ValidationResult skinResult = PlayerSkin.validateStructure(buffer, pos);
/* 615 */       if (!skinResult.isValid()) {
/* 616 */         return ValidationResult.error("Invalid Skin: " + skinResult.error());
/*     */       }
/* 618 */       pos += PlayerSkin.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 621 */     if ((nullBits[0] & 0x20) != 0) {
/* 622 */       int itemOffset = buffer.getIntLE(offset + 179);
/* 623 */       if (itemOffset < 0) {
/* 624 */         return ValidationResult.error("Invalid offset for Item");
/*     */       }
/* 626 */       int pos = offset + 211 + itemOffset;
/* 627 */       if (pos >= buffer.writerIndex()) {
/* 628 */         return ValidationResult.error("Offset out of bounds for Item");
/*     */       }
/* 630 */       ValidationResult itemResult = ItemWithAllMetadata.validateStructure(buffer, pos);
/* 631 */       if (!itemResult.isValid()) {
/* 632 */         return ValidationResult.error("Invalid Item: " + itemResult.error());
/*     */       }
/* 634 */       pos += ItemWithAllMetadata.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 637 */     if ((nullBits[0] & 0x40) != 0) {
/* 638 */       int equipmentOffset = buffer.getIntLE(offset + 183);
/* 639 */       if (equipmentOffset < 0) {
/* 640 */         return ValidationResult.error("Invalid offset for Equipment");
/*     */       }
/* 642 */       int pos = offset + 211 + equipmentOffset;
/* 643 */       if (pos >= buffer.writerIndex()) {
/* 644 */         return ValidationResult.error("Offset out of bounds for Equipment");
/*     */       }
/* 646 */       ValidationResult equipmentResult = Equipment.validateStructure(buffer, pos);
/* 647 */       if (!equipmentResult.isValid()) {
/* 648 */         return ValidationResult.error("Invalid Equipment: " + equipmentResult.error());
/*     */       }
/* 650 */       pos += Equipment.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 653 */     if ((nullBits[0] & 0x80) != 0) {
/* 654 */       int entityStatUpdatesOffset = buffer.getIntLE(offset + 187);
/* 655 */       if (entityStatUpdatesOffset < 0) {
/* 656 */         return ValidationResult.error("Invalid offset for EntityStatUpdates");
/*     */       }
/* 658 */       int pos = offset + 211 + entityStatUpdatesOffset;
/* 659 */       if (pos >= buffer.writerIndex()) {
/* 660 */         return ValidationResult.error("Offset out of bounds for EntityStatUpdates");
/*     */       }
/* 662 */       int entityStatUpdatesCount = VarInt.peek(buffer, pos);
/* 663 */       if (entityStatUpdatesCount < 0) {
/* 664 */         return ValidationResult.error("Invalid dictionary count for EntityStatUpdates");
/*     */       }
/* 666 */       if (entityStatUpdatesCount > 4096000) {
/* 667 */         return ValidationResult.error("EntityStatUpdates exceeds max length 4096000");
/*     */       }
/* 669 */       pos += VarInt.length(buffer, pos);
/* 670 */       for (int i = 0; i < entityStatUpdatesCount; i++) {
/* 671 */         pos += 4;
/* 672 */         if (pos > buffer.writerIndex()) {
/* 673 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 675 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 676 */         if (valueArrCount < 0) {
/* 677 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 679 */         pos += VarInt.length(buffer, pos);
/* 680 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 681 */           pos += EntityStatUpdate.computeBytesConsumed(buffer, pos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 687 */     if ((nullBits[1] & 0x4) != 0) {
/* 688 */       int entityEffectUpdatesOffset = buffer.getIntLE(offset + 191);
/* 689 */       if (entityEffectUpdatesOffset < 0) {
/* 690 */         return ValidationResult.error("Invalid offset for EntityEffectUpdates");
/*     */       }
/* 692 */       int pos = offset + 211 + entityEffectUpdatesOffset;
/* 693 */       if (pos >= buffer.writerIndex()) {
/* 694 */         return ValidationResult.error("Offset out of bounds for EntityEffectUpdates");
/*     */       }
/* 696 */       int entityEffectUpdatesCount = VarInt.peek(buffer, pos);
/* 697 */       if (entityEffectUpdatesCount < 0) {
/* 698 */         return ValidationResult.error("Invalid array count for EntityEffectUpdates");
/*     */       }
/* 700 */       if (entityEffectUpdatesCount > 4096000) {
/* 701 */         return ValidationResult.error("EntityEffectUpdates exceeds max length 4096000");
/*     */       }
/* 703 */       pos += VarInt.length(buffer, pos);
/* 704 */       for (int i = 0; i < entityEffectUpdatesCount; i++) {
/* 705 */         ValidationResult structResult = EntityEffectUpdate.validateStructure(buffer, pos);
/* 706 */         if (!structResult.isValid()) {
/* 707 */           return ValidationResult.error("Invalid EntityEffectUpdate in EntityEffectUpdates[" + i + "]: " + structResult.error());
/*     */         }
/* 709 */         pos += EntityEffectUpdate.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 713 */     if ((nullBits[1] & 0x8) != 0) {
/* 714 */       int interactionsOffset = buffer.getIntLE(offset + 195);
/* 715 */       if (interactionsOffset < 0) {
/* 716 */         return ValidationResult.error("Invalid offset for Interactions");
/*     */       }
/* 718 */       int pos = offset + 211 + interactionsOffset;
/* 719 */       if (pos >= buffer.writerIndex()) {
/* 720 */         return ValidationResult.error("Offset out of bounds for Interactions");
/*     */       }
/* 722 */       int interactionsCount = VarInt.peek(buffer, pos);
/* 723 */       if (interactionsCount < 0) {
/* 724 */         return ValidationResult.error("Invalid dictionary count for Interactions");
/*     */       }
/* 726 */       if (interactionsCount > 4096000) {
/* 727 */         return ValidationResult.error("Interactions exceeds max length 4096000");
/*     */       }
/* 729 */       pos += VarInt.length(buffer, pos);
/* 730 */       for (int i = 0; i < interactionsCount; i++) {
/* 731 */         pos++;
/*     */         
/* 733 */         pos += 4;
/* 734 */         if (pos > buffer.writerIndex()) {
/* 735 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 740 */     if ((nullBits[1] & 0x20) != 0) {
/* 741 */       int soundEventIdsOffset = buffer.getIntLE(offset + 199);
/* 742 */       if (soundEventIdsOffset < 0) {
/* 743 */         return ValidationResult.error("Invalid offset for SoundEventIds");
/*     */       }
/* 745 */       int pos = offset + 211 + soundEventIdsOffset;
/* 746 */       if (pos >= buffer.writerIndex()) {
/* 747 */         return ValidationResult.error("Offset out of bounds for SoundEventIds");
/*     */       }
/* 749 */       int soundEventIdsCount = VarInt.peek(buffer, pos);
/* 750 */       if (soundEventIdsCount < 0) {
/* 751 */         return ValidationResult.error("Invalid array count for SoundEventIds");
/*     */       }
/* 753 */       if (soundEventIdsCount > 4096000) {
/* 754 */         return ValidationResult.error("SoundEventIds exceeds max length 4096000");
/*     */       }
/* 756 */       pos += VarInt.length(buffer, pos);
/* 757 */       pos += soundEventIdsCount * 4;
/* 758 */       if (pos > buffer.writerIndex()) {
/* 759 */         return ValidationResult.error("Buffer overflow reading SoundEventIds");
/*     */       }
/*     */     } 
/*     */     
/* 763 */     if ((nullBits[1] & 0x40) != 0) {
/* 764 */       int interactionHintOffset = buffer.getIntLE(offset + 203);
/* 765 */       if (interactionHintOffset < 0) {
/* 766 */         return ValidationResult.error("Invalid offset for InteractionHint");
/*     */       }
/* 768 */       int pos = offset + 211 + interactionHintOffset;
/* 769 */       if (pos >= buffer.writerIndex()) {
/* 770 */         return ValidationResult.error("Offset out of bounds for InteractionHint");
/*     */       }
/* 772 */       int interactionHintLen = VarInt.peek(buffer, pos);
/* 773 */       if (interactionHintLen < 0) {
/* 774 */         return ValidationResult.error("Invalid string length for InteractionHint");
/*     */       }
/* 776 */       if (interactionHintLen > 4096000) {
/* 777 */         return ValidationResult.error("InteractionHint exceeds max length 4096000");
/*     */       }
/* 779 */       pos += VarInt.length(buffer, pos);
/* 780 */       pos += interactionHintLen;
/* 781 */       if (pos > buffer.writerIndex()) {
/* 782 */         return ValidationResult.error("Buffer overflow reading InteractionHint");
/*     */       }
/*     */     } 
/*     */     
/* 786 */     if ((nullBits[2] & 0x1) != 0) {
/* 787 */       int activeAnimationsOffset = buffer.getIntLE(offset + 207);
/* 788 */       if (activeAnimationsOffset < 0) {
/* 789 */         return ValidationResult.error("Invalid offset for ActiveAnimations");
/*     */       }
/* 791 */       int pos = offset + 211 + activeAnimationsOffset;
/* 792 */       if (pos >= buffer.writerIndex()) {
/* 793 */         return ValidationResult.error("Offset out of bounds for ActiveAnimations");
/*     */       }
/* 795 */       int activeAnimationsCount = VarInt.peek(buffer, pos);
/* 796 */       if (activeAnimationsCount < 0) {
/* 797 */         return ValidationResult.error("Invalid array count for ActiveAnimations");
/*     */       }
/* 799 */       if (activeAnimationsCount > 4096000) {
/* 800 */         return ValidationResult.error("ActiveAnimations exceeds max length 4096000");
/*     */       }
/* 802 */       pos += VarInt.length(buffer, pos);
/* 803 */       for (int i = 0; i < activeAnimationsCount; i++) {
/* 804 */         int strLen = VarInt.peek(buffer, pos);
/* 805 */         if (strLen < 0) {
/* 806 */           return ValidationResult.error("Invalid string length in ActiveAnimations");
/*     */         }
/* 808 */         pos += VarInt.length(buffer, pos);
/* 809 */         pos += strLen;
/* 810 */         if (pos > buffer.writerIndex()) {
/* 811 */           return ValidationResult.error("Buffer overflow reading string in ActiveAnimations");
/*     */         }
/*     */       } 
/*     */     } 
/* 815 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ComponentUpdate clone() {
/* 819 */     ComponentUpdate copy = new ComponentUpdate();
/* 820 */     copy.type = this.type;
/* 821 */     copy.nameplate = (this.nameplate != null) ? this.nameplate.clone() : null;
/* 822 */     copy.entityUIComponents = (this.entityUIComponents != null) ? Arrays.copyOf(this.entityUIComponents, this.entityUIComponents.length) : null;
/* 823 */     copy.combatTextUpdate = (this.combatTextUpdate != null) ? this.combatTextUpdate.clone() : null;
/* 824 */     copy.model = (this.model != null) ? this.model.clone() : null;
/* 825 */     copy.skin = (this.skin != null) ? this.skin.clone() : null;
/* 826 */     copy.item = (this.item != null) ? this.item.clone() : null;
/* 827 */     copy.blockId = this.blockId;
/* 828 */     copy.entityScale = this.entityScale;
/* 829 */     copy.equipment = (this.equipment != null) ? this.equipment.clone() : null;
/* 830 */     if (this.entityStatUpdates != null) {
/* 831 */       Map<Integer, EntityStatUpdate[]> m = (Map)new HashMap<>();
/* 832 */       for (Map.Entry<Integer, EntityStatUpdate[]> e : this.entityStatUpdates.entrySet()) m.put(e.getKey(), (EntityStatUpdate[])Arrays.<EntityStatUpdate>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new EntityStatUpdate[x$0])); 
/* 833 */       copy.entityStatUpdates = m;
/*     */     } 
/* 835 */     copy.transform = (this.transform != null) ? this.transform.clone() : null;
/* 836 */     copy.movementStates = (this.movementStates != null) ? this.movementStates.clone() : null;
/* 837 */     copy.entityEffectUpdates = (this.entityEffectUpdates != null) ? (EntityEffectUpdate[])Arrays.<EntityEffectUpdate>stream(this.entityEffectUpdates).map(e -> e.clone()).toArray(x$0 -> new EntityEffectUpdate[x$0]) : null;
/* 838 */     copy.interactions = (this.interactions != null) ? new HashMap<>(this.interactions) : null;
/* 839 */     copy.dynamicLight = (this.dynamicLight != null) ? this.dynamicLight.clone() : null;
/* 840 */     copy.hitboxCollisionConfigIndex = this.hitboxCollisionConfigIndex;
/* 841 */     copy.repulsionConfigIndex = this.repulsionConfigIndex;
/* 842 */     copy.predictionId = this.predictionId;
/* 843 */     copy.soundEventIds = (this.soundEventIds != null) ? Arrays.copyOf(this.soundEventIds, this.soundEventIds.length) : null;
/* 844 */     copy.interactionHint = this.interactionHint;
/* 845 */     copy.mounted = (this.mounted != null) ? this.mounted.clone() : null;
/* 846 */     copy.activeAnimations = (this.activeAnimations != null) ? Arrays.<String>copyOf(this.activeAnimations, this.activeAnimations.length) : null;
/* 847 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ComponentUpdate other;
/* 853 */     if (this == obj) return true; 
/* 854 */     if (obj instanceof ComponentUpdate) { other = (ComponentUpdate)obj; } else { return false; }
/* 855 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.nameplate, other.nameplate) && Arrays.equals(this.entityUIComponents, other.entityUIComponents) && Objects.equals(this.combatTextUpdate, other.combatTextUpdate) && Objects.equals(this.model, other.model) && Objects.equals(this.skin, other.skin) && Objects.equals(this.item, other.item) && this.blockId == other.blockId && this.entityScale == other.entityScale && Objects.equals(this.equipment, other.equipment) && Objects.equals(this.entityStatUpdates, other.entityStatUpdates) && Objects.equals(this.transform, other.transform) && Objects.equals(this.movementStates, other.movementStates) && Arrays.equals((Object[])this.entityEffectUpdates, (Object[])other.entityEffectUpdates) && Objects.equals(this.interactions, other.interactions) && Objects.equals(this.dynamicLight, other.dynamicLight) && this.hitboxCollisionConfigIndex == other.hitboxCollisionConfigIndex && this.repulsionConfigIndex == other.repulsionConfigIndex && Objects.equals(this.predictionId, other.predictionId) && Arrays.equals(this.soundEventIds, other.soundEventIds) && Objects.equals(this.interactionHint, other.interactionHint) && Objects.equals(this.mounted, other.mounted) && Arrays.equals((Object[])this.activeAnimations, (Object[])other.activeAnimations));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 860 */     int result = 1;
/* 861 */     result = 31 * result + Objects.hashCode(this.type);
/* 862 */     result = 31 * result + Objects.hashCode(this.nameplate);
/* 863 */     result = 31 * result + Arrays.hashCode(this.entityUIComponents);
/* 864 */     result = 31 * result + Objects.hashCode(this.combatTextUpdate);
/* 865 */     result = 31 * result + Objects.hashCode(this.model);
/* 866 */     result = 31 * result + Objects.hashCode(this.skin);
/* 867 */     result = 31 * result + Objects.hashCode(this.item);
/* 868 */     result = 31 * result + Integer.hashCode(this.blockId);
/* 869 */     result = 31 * result + Float.hashCode(this.entityScale);
/* 870 */     result = 31 * result + Objects.hashCode(this.equipment);
/* 871 */     result = 31 * result + Objects.hashCode(this.entityStatUpdates);
/* 872 */     result = 31 * result + Objects.hashCode(this.transform);
/* 873 */     result = 31 * result + Objects.hashCode(this.movementStates);
/* 874 */     result = 31 * result + Arrays.hashCode((Object[])this.entityEffectUpdates);
/* 875 */     result = 31 * result + Objects.hashCode(this.interactions);
/* 876 */     result = 31 * result + Objects.hashCode(this.dynamicLight);
/* 877 */     result = 31 * result + Integer.hashCode(this.hitboxCollisionConfigIndex);
/* 878 */     result = 31 * result + Integer.hashCode(this.repulsionConfigIndex);
/* 879 */     result = 31 * result + Objects.hashCode(this.predictionId);
/* 880 */     result = 31 * result + Arrays.hashCode(this.soundEventIds);
/* 881 */     result = 31 * result + Objects.hashCode(this.interactionHint);
/* 882 */     result = 31 * result + Objects.hashCode(this.mounted);
/* 883 */     result = 31 * result + Arrays.hashCode((Object[])this.activeAnimations);
/* 884 */     return result;
/*     */   }
/*     */   
/*     */   public ComponentUpdate() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ComponentUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */