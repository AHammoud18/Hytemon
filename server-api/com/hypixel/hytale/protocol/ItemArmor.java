/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ItemArmor {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 10;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public ItemArmorSlot armorSlot = ItemArmorSlot.Head; @Nullable
/*     */   public Cosmetic[] cosmeticsToHide; @Nullable
/*     */   public Map<Integer, Modifier[]> statModifiers;
/*     */   public double baseDamageResistance;
/*     */   @Nullable
/*     */   public Map<String, Modifier[]> damageResistance;
/*     */   @Nullable
/*     */   public Map<String, Modifier[]> damageEnhancement;
/*     */   @Nullable
/*     */   public Map<String, Modifier[]> damageClassEnhancement;
/*     */   
/*     */   public ItemArmor(@Nonnull ItemArmorSlot armorSlot, @Nullable Cosmetic[] cosmeticsToHide, @Nullable Map<Integer, Modifier[]> statModifiers, double baseDamageResistance, @Nullable Map<String, Modifier[]> damageResistance, @Nullable Map<String, Modifier[]> damageEnhancement, @Nullable Map<String, Modifier[]> damageClassEnhancement) {
/*  32 */     this.armorSlot = armorSlot;
/*  33 */     this.cosmeticsToHide = cosmeticsToHide;
/*  34 */     this.statModifiers = statModifiers;
/*  35 */     this.baseDamageResistance = baseDamageResistance;
/*  36 */     this.damageResistance = damageResistance;
/*  37 */     this.damageEnhancement = damageEnhancement;
/*  38 */     this.damageClassEnhancement = damageClassEnhancement;
/*     */   }
/*     */   
/*     */   public ItemArmor(@Nonnull ItemArmor other) {
/*  42 */     this.armorSlot = other.armorSlot;
/*  43 */     this.cosmeticsToHide = other.cosmeticsToHide;
/*  44 */     this.statModifiers = other.statModifiers;
/*  45 */     this.baseDamageResistance = other.baseDamageResistance;
/*  46 */     this.damageResistance = other.damageResistance;
/*  47 */     this.damageEnhancement = other.damageEnhancement;
/*  48 */     this.damageClassEnhancement = other.damageClassEnhancement;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemArmor deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     ItemArmor obj = new ItemArmor();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.armorSlot = ItemArmorSlot.fromValue(buf.getByte(offset + 1));
/*  56 */     obj.baseDamageResistance = buf.getDoubleLE(offset + 2);
/*     */     
/*  58 */     if ((nullBits & 0x1) != 0) {
/*  59 */       int varPos0 = offset + 30 + buf.getIntLE(offset + 10);
/*  60 */       int cosmeticsToHideCount = VarInt.peek(buf, varPos0);
/*  61 */       if (cosmeticsToHideCount < 0) throw ProtocolException.negativeLength("CosmeticsToHide", cosmeticsToHideCount); 
/*  62 */       if (cosmeticsToHideCount > 4096000) throw ProtocolException.arrayTooLong("CosmeticsToHide", cosmeticsToHideCount, 4096000); 
/*  63 */       int varIntLen = VarInt.length(buf, varPos0);
/*  64 */       if ((varPos0 + varIntLen) + cosmeticsToHideCount * 1L > buf.readableBytes())
/*  65 */         throw ProtocolException.bufferTooSmall("CosmeticsToHide", varPos0 + varIntLen + cosmeticsToHideCount * 1, buf.readableBytes()); 
/*  66 */       obj.cosmeticsToHide = new Cosmetic[cosmeticsToHideCount];
/*  67 */       int elemPos = varPos0 + varIntLen;
/*  68 */       for (int i = 0; i < cosmeticsToHideCount; i++) {
/*  69 */         obj.cosmeticsToHide[i] = Cosmetic.fromValue(buf.getByte(elemPos)); elemPos++;
/*     */       } 
/*     */     } 
/*  72 */     if ((nullBits & 0x2) != 0) {
/*  73 */       int varPos1 = offset + 30 + buf.getIntLE(offset + 14);
/*  74 */       int statModifiersCount = VarInt.peek(buf, varPos1);
/*  75 */       if (statModifiersCount < 0) throw ProtocolException.negativeLength("StatModifiers", statModifiersCount); 
/*  76 */       if (statModifiersCount > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", statModifiersCount, 4096000); 
/*  77 */       int varIntLen = VarInt.length(buf, varPos1);
/*  78 */       obj.statModifiers = (Map)new HashMap<>(statModifiersCount);
/*  79 */       int dictPos = varPos1 + varIntLen;
/*  80 */       for (int i = 0; i < statModifiersCount; i++) {
/*  81 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  82 */         int valLen = VarInt.peek(buf, dictPos);
/*  83 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  84 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/*  85 */         int valVarLen = VarInt.length(buf, dictPos);
/*  86 */         if ((dictPos + valVarLen) + valLen * 6L > buf.readableBytes())
/*  87 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 6, buf.readableBytes()); 
/*  88 */         dictPos += valVarLen;
/*  89 */         Modifier[] val = new Modifier[valLen];
/*  90 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/*  91 */           val[valIdx] = Modifier.deserialize(buf, dictPos);
/*  92 */           dictPos += Modifier.computeBytesConsumed(buf, dictPos);
/*     */         } 
/*  94 */         if (obj.statModifiers.put(Integer.valueOf(key), val) != null)
/*  95 */           throw ProtocolException.duplicateKey("statModifiers", Integer.valueOf(key)); 
/*     */       } 
/*     */     } 
/*  98 */     if ((nullBits & 0x4) != 0) {
/*  99 */       int varPos2 = offset + 30 + buf.getIntLE(offset + 18);
/* 100 */       int damageResistanceCount = VarInt.peek(buf, varPos2);
/* 101 */       if (damageResistanceCount < 0) throw ProtocolException.negativeLength("DamageResistance", damageResistanceCount); 
/* 102 */       if (damageResistanceCount > 4096000) throw ProtocolException.dictionaryTooLarge("DamageResistance", damageResistanceCount, 4096000); 
/* 103 */       int varIntLen = VarInt.length(buf, varPos2);
/* 104 */       obj.damageResistance = (Map)new HashMap<>(damageResistanceCount);
/* 105 */       int dictPos = varPos2 + varIntLen;
/* 106 */       for (int i = 0; i < damageResistanceCount; i++) {
/* 107 */         int keyLen = VarInt.peek(buf, dictPos);
/* 108 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 109 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 110 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 111 */         String key = PacketIO.readVarString(buf, dictPos);
/* 112 */         dictPos += keyVarLen + keyLen;
/* 113 */         int valLen = VarInt.peek(buf, dictPos);
/* 114 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/* 115 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/* 116 */         int valVarLen = VarInt.length(buf, dictPos);
/* 117 */         if ((dictPos + valVarLen) + valLen * 6L > buf.readableBytes())
/* 118 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 6, buf.readableBytes()); 
/* 119 */         dictPos += valVarLen;
/* 120 */         Modifier[] val = new Modifier[valLen];
/* 121 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/* 122 */           val[valIdx] = Modifier.deserialize(buf, dictPos);
/* 123 */           dictPos += Modifier.computeBytesConsumed(buf, dictPos);
/*     */         } 
/* 125 */         if (obj.damageResistance.put(key, val) != null)
/* 126 */           throw ProtocolException.duplicateKey("damageResistance", key); 
/*     */       } 
/*     */     } 
/* 129 */     if ((nullBits & 0x8) != 0) {
/* 130 */       int varPos3 = offset + 30 + buf.getIntLE(offset + 22);
/* 131 */       int damageEnhancementCount = VarInt.peek(buf, varPos3);
/* 132 */       if (damageEnhancementCount < 0) throw ProtocolException.negativeLength("DamageEnhancement", damageEnhancementCount); 
/* 133 */       if (damageEnhancementCount > 4096000) throw ProtocolException.dictionaryTooLarge("DamageEnhancement", damageEnhancementCount, 4096000); 
/* 134 */       int varIntLen = VarInt.length(buf, varPos3);
/* 135 */       obj.damageEnhancement = (Map)new HashMap<>(damageEnhancementCount);
/* 136 */       int dictPos = varPos3 + varIntLen;
/* 137 */       for (int i = 0; i < damageEnhancementCount; i++) {
/* 138 */         int keyLen = VarInt.peek(buf, dictPos);
/* 139 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 140 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 141 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 142 */         String key = PacketIO.readVarString(buf, dictPos);
/* 143 */         dictPos += keyVarLen + keyLen;
/* 144 */         int valLen = VarInt.peek(buf, dictPos);
/* 145 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/* 146 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/* 147 */         int valVarLen = VarInt.length(buf, dictPos);
/* 148 */         if ((dictPos + valVarLen) + valLen * 6L > buf.readableBytes())
/* 149 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 6, buf.readableBytes()); 
/* 150 */         dictPos += valVarLen;
/* 151 */         Modifier[] val = new Modifier[valLen];
/* 152 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/* 153 */           val[valIdx] = Modifier.deserialize(buf, dictPos);
/* 154 */           dictPos += Modifier.computeBytesConsumed(buf, dictPos);
/*     */         } 
/* 156 */         if (obj.damageEnhancement.put(key, val) != null)
/* 157 */           throw ProtocolException.duplicateKey("damageEnhancement", key); 
/*     */       } 
/*     */     } 
/* 160 */     if ((nullBits & 0x10) != 0) {
/* 161 */       int varPos4 = offset + 30 + buf.getIntLE(offset + 26);
/* 162 */       int damageClassEnhancementCount = VarInt.peek(buf, varPos4);
/* 163 */       if (damageClassEnhancementCount < 0) throw ProtocolException.negativeLength("DamageClassEnhancement", damageClassEnhancementCount); 
/* 164 */       if (damageClassEnhancementCount > 4096000) throw ProtocolException.dictionaryTooLarge("DamageClassEnhancement", damageClassEnhancementCount, 4096000); 
/* 165 */       int varIntLen = VarInt.length(buf, varPos4);
/* 166 */       obj.damageClassEnhancement = (Map)new HashMap<>(damageClassEnhancementCount);
/* 167 */       int dictPos = varPos4 + varIntLen;
/* 168 */       for (int i = 0; i < damageClassEnhancementCount; i++) {
/* 169 */         int keyLen = VarInt.peek(buf, dictPos);
/* 170 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 171 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 172 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 173 */         String key = PacketIO.readVarString(buf, dictPos);
/* 174 */         dictPos += keyVarLen + keyLen;
/* 175 */         int valLen = VarInt.peek(buf, dictPos);
/* 176 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/* 177 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/* 178 */         int valVarLen = VarInt.length(buf, dictPos);
/* 179 */         if ((dictPos + valVarLen) + valLen * 6L > buf.readableBytes())
/* 180 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 6, buf.readableBytes()); 
/* 181 */         dictPos += valVarLen;
/* 182 */         Modifier[] val = new Modifier[valLen];
/* 183 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/* 184 */           val[valIdx] = Modifier.deserialize(buf, dictPos);
/* 185 */           dictPos += Modifier.computeBytesConsumed(buf, dictPos);
/*     */         } 
/* 187 */         if (obj.damageClassEnhancement.put(key, val) != null) {
/* 188 */           throw ProtocolException.duplicateKey("damageClassEnhancement", key);
/*     */         }
/*     */       } 
/*     */     } 
/* 192 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 196 */     byte nullBits = buf.getByte(offset);
/* 197 */     int maxEnd = 30;
/* 198 */     if ((nullBits & 0x1) != 0) {
/* 199 */       int fieldOffset0 = buf.getIntLE(offset + 10);
/* 200 */       int pos0 = offset + 30 + fieldOffset0;
/* 201 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 1;
/* 202 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 204 */     if ((nullBits & 0x2) != 0) {
/* 205 */       int fieldOffset1 = buf.getIntLE(offset + 14);
/* 206 */       int pos1 = offset + 30 + fieldOffset1;
/* 207 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 208 */       for (int i = 0; i < dictLen; ) { int al; int j; for (pos1 += 4, al = VarInt.peek(buf, pos1), pos1 += VarInt.length(buf, pos1), j = 0; j < al; ) { pos1 += Modifier.computeBytesConsumed(buf, pos1); j++; }  i++; }
/* 209 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 211 */     if ((nullBits & 0x4) != 0) {
/* 212 */       int fieldOffset2 = buf.getIntLE(offset + 18);
/* 213 */       int pos2 = offset + 30 + fieldOffset2;
/* 214 */       int dictLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 215 */       for (int i = 0; i < dictLen; ) { for (int sl = VarInt.peek(buf, pos2), al = VarInt.peek(buf, pos2), j = 0; j < al; ) { pos2 += Modifier.computeBytesConsumed(buf, pos2); j++; }  i++; }
/* 216 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 218 */     if ((nullBits & 0x8) != 0) {
/* 219 */       int fieldOffset3 = buf.getIntLE(offset + 22);
/* 220 */       int pos3 = offset + 30 + fieldOffset3;
/* 221 */       int dictLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 222 */       for (int i = 0; i < dictLen; ) { for (int sl = VarInt.peek(buf, pos3), al = VarInt.peek(buf, pos3), j = 0; j < al; ) { pos3 += Modifier.computeBytesConsumed(buf, pos3); j++; }  i++; }
/* 223 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 225 */     if ((nullBits & 0x10) != 0) {
/* 226 */       int fieldOffset4 = buf.getIntLE(offset + 26);
/* 227 */       int pos4 = offset + 30 + fieldOffset4;
/* 228 */       int dictLen = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4);
/* 229 */       for (int i = 0; i < dictLen; ) { for (int sl = VarInt.peek(buf, pos4), al = VarInt.peek(buf, pos4), j = 0; j < al; ) { pos4 += Modifier.computeBytesConsumed(buf, pos4); j++; }  i++; }
/* 230 */        if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 232 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 237 */     int startPos = buf.writerIndex();
/* 238 */     byte nullBits = 0;
/* 239 */     if (this.cosmeticsToHide != null) nullBits = (byte)(nullBits | 0x1); 
/* 240 */     if (this.statModifiers != null) nullBits = (byte)(nullBits | 0x2); 
/* 241 */     if (this.damageResistance != null) nullBits = (byte)(nullBits | 0x4); 
/* 242 */     if (this.damageEnhancement != null) nullBits = (byte)(nullBits | 0x8); 
/* 243 */     if (this.damageClassEnhancement != null) nullBits = (byte)(nullBits | 0x10); 
/* 244 */     buf.writeByte(nullBits);
/*     */     
/* 246 */     buf.writeByte(this.armorSlot.getValue());
/* 247 */     buf.writeDoubleLE(this.baseDamageResistance);
/*     */     
/* 249 */     int cosmeticsToHideOffsetSlot = buf.writerIndex();
/* 250 */     buf.writeIntLE(0);
/* 251 */     int statModifiersOffsetSlot = buf.writerIndex();
/* 252 */     buf.writeIntLE(0);
/* 253 */     int damageResistanceOffsetSlot = buf.writerIndex();
/* 254 */     buf.writeIntLE(0);
/* 255 */     int damageEnhancementOffsetSlot = buf.writerIndex();
/* 256 */     buf.writeIntLE(0);
/* 257 */     int damageClassEnhancementOffsetSlot = buf.writerIndex();
/* 258 */     buf.writeIntLE(0);
/*     */     
/* 260 */     int varBlockStart = buf.writerIndex();
/* 261 */     if (this.cosmeticsToHide != null) {
/* 262 */       buf.setIntLE(cosmeticsToHideOffsetSlot, buf.writerIndex() - varBlockStart);
/* 263 */       if (this.cosmeticsToHide.length > 4096000) throw ProtocolException.arrayTooLong("CosmeticsToHide", this.cosmeticsToHide.length, 4096000);  VarInt.write(buf, this.cosmeticsToHide.length); for (Cosmetic item : this.cosmeticsToHide) buf.writeByte(item.getValue()); 
/*     */     } else {
/* 265 */       buf.setIntLE(cosmeticsToHideOffsetSlot, -1);
/*     */     } 
/* 267 */     if (this.statModifiers != null)
/* 268 */     { buf.setIntLE(statModifiersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 269 */       if (this.statModifiers.size() > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", this.statModifiers.size(), 4096000);  VarInt.write(buf, this.statModifiers.size()); for (Map.Entry<Integer, Modifier[]> e : this.statModifiers.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); VarInt.write(buf, ((Modifier[])e.getValue()).length); for (Modifier arrItem : (Modifier[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 271 */     else { buf.setIntLE(statModifiersOffsetSlot, -1); }
/*     */     
/* 273 */     if (this.damageResistance != null)
/* 274 */     { buf.setIntLE(damageResistanceOffsetSlot, buf.writerIndex() - varBlockStart);
/* 275 */       if (this.damageResistance.size() > 4096000) throw ProtocolException.dictionaryTooLarge("DamageResistance", this.damageResistance.size(), 4096000);  VarInt.write(buf, this.damageResistance.size()); for (Map.Entry<String, Modifier[]> e : this.damageResistance.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); VarInt.write(buf, ((Modifier[])e.getValue()).length); for (Modifier arrItem : (Modifier[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 277 */     else { buf.setIntLE(damageResistanceOffsetSlot, -1); }
/*     */     
/* 279 */     if (this.damageEnhancement != null)
/* 280 */     { buf.setIntLE(damageEnhancementOffsetSlot, buf.writerIndex() - varBlockStart);
/* 281 */       if (this.damageEnhancement.size() > 4096000) throw ProtocolException.dictionaryTooLarge("DamageEnhancement", this.damageEnhancement.size(), 4096000);  VarInt.write(buf, this.damageEnhancement.size()); for (Map.Entry<String, Modifier[]> e : this.damageEnhancement.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); VarInt.write(buf, ((Modifier[])e.getValue()).length); for (Modifier arrItem : (Modifier[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 283 */     else { buf.setIntLE(damageEnhancementOffsetSlot, -1); }
/*     */     
/* 285 */     if (this.damageClassEnhancement != null)
/* 286 */     { buf.setIntLE(damageClassEnhancementOffsetSlot, buf.writerIndex() - varBlockStart);
/* 287 */       if (this.damageClassEnhancement.size() > 4096000) throw ProtocolException.dictionaryTooLarge("DamageClassEnhancement", this.damageClassEnhancement.size(), 4096000);  VarInt.write(buf, this.damageClassEnhancement.size()); for (Map.Entry<String, Modifier[]> e : this.damageClassEnhancement.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); VarInt.write(buf, ((Modifier[])e.getValue()).length); for (Modifier arrItem : (Modifier[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 289 */     else { buf.setIntLE(damageClassEnhancementOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 295 */     int size = 30;
/* 296 */     if (this.cosmeticsToHide != null) size += VarInt.size(this.cosmeticsToHide.length) + this.cosmeticsToHide.length * 1; 
/* 297 */     if (this.statModifiers != null) {
/* 298 */       int statModifiersSize = 0;
/* 299 */       for (Map.Entry<Integer, Modifier[]> kvp : this.statModifiers.entrySet()) statModifiersSize += 4 + VarInt.size(((Modifier[])kvp.getValue()).length) + ((Modifier[])kvp.getValue()).length * 6; 
/* 300 */       size += VarInt.size(this.statModifiers.size()) + statModifiersSize;
/*     */     } 
/* 302 */     if (this.damageResistance != null) {
/* 303 */       int damageResistanceSize = 0;
/* 304 */       for (Map.Entry<String, Modifier[]> kvp : this.damageResistance.entrySet()) damageResistanceSize += PacketIO.stringSize(kvp.getKey()) + VarInt.size(((Modifier[])kvp.getValue()).length) + ((Modifier[])kvp.getValue()).length * 6; 
/* 305 */       size += VarInt.size(this.damageResistance.size()) + damageResistanceSize;
/*     */     } 
/* 307 */     if (this.damageEnhancement != null) {
/* 308 */       int damageEnhancementSize = 0;
/* 309 */       for (Map.Entry<String, Modifier[]> kvp : this.damageEnhancement.entrySet()) damageEnhancementSize += PacketIO.stringSize(kvp.getKey()) + VarInt.size(((Modifier[])kvp.getValue()).length) + ((Modifier[])kvp.getValue()).length * 6; 
/* 310 */       size += VarInt.size(this.damageEnhancement.size()) + damageEnhancementSize;
/*     */     } 
/* 312 */     if (this.damageClassEnhancement != null) {
/* 313 */       int damageClassEnhancementSize = 0;
/* 314 */       for (Map.Entry<String, Modifier[]> kvp : this.damageClassEnhancement.entrySet()) damageClassEnhancementSize += PacketIO.stringSize(kvp.getKey()) + VarInt.size(((Modifier[])kvp.getValue()).length) + ((Modifier[])kvp.getValue()).length * 6; 
/* 315 */       size += VarInt.size(this.damageClassEnhancement.size()) + damageClassEnhancementSize;
/*     */     } 
/*     */     
/* 318 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 322 */     if (buffer.readableBytes() - offset < 30) {
/* 323 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */     
/* 326 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 329 */     if ((nullBits & 0x1) != 0) {
/* 330 */       int cosmeticsToHideOffset = buffer.getIntLE(offset + 10);
/* 331 */       if (cosmeticsToHideOffset < 0) {
/* 332 */         return ValidationResult.error("Invalid offset for CosmeticsToHide");
/*     */       }
/* 334 */       int pos = offset + 30 + cosmeticsToHideOffset;
/* 335 */       if (pos >= buffer.writerIndex()) {
/* 336 */         return ValidationResult.error("Offset out of bounds for CosmeticsToHide");
/*     */       }
/* 338 */       int cosmeticsToHideCount = VarInt.peek(buffer, pos);
/* 339 */       if (cosmeticsToHideCount < 0) {
/* 340 */         return ValidationResult.error("Invalid array count for CosmeticsToHide");
/*     */       }
/* 342 */       if (cosmeticsToHideCount > 4096000) {
/* 343 */         return ValidationResult.error("CosmeticsToHide exceeds max length 4096000");
/*     */       }
/* 345 */       pos += VarInt.length(buffer, pos);
/* 346 */       pos += cosmeticsToHideCount * 1;
/* 347 */       if (pos > buffer.writerIndex()) {
/* 348 */         return ValidationResult.error("Buffer overflow reading CosmeticsToHide");
/*     */       }
/*     */     } 
/*     */     
/* 352 */     if ((nullBits & 0x2) != 0) {
/* 353 */       int statModifiersOffset = buffer.getIntLE(offset + 14);
/* 354 */       if (statModifiersOffset < 0) {
/* 355 */         return ValidationResult.error("Invalid offset for StatModifiers");
/*     */       }
/* 357 */       int pos = offset + 30 + statModifiersOffset;
/* 358 */       if (pos >= buffer.writerIndex()) {
/* 359 */         return ValidationResult.error("Offset out of bounds for StatModifiers");
/*     */       }
/* 361 */       int statModifiersCount = VarInt.peek(buffer, pos);
/* 362 */       if (statModifiersCount < 0) {
/* 363 */         return ValidationResult.error("Invalid dictionary count for StatModifiers");
/*     */       }
/* 365 */       if (statModifiersCount > 4096000) {
/* 366 */         return ValidationResult.error("StatModifiers exceeds max length 4096000");
/*     */       }
/* 368 */       pos += VarInt.length(buffer, pos);
/* 369 */       for (int i = 0; i < statModifiersCount; i++) {
/* 370 */         pos += 4;
/* 371 */         if (pos > buffer.writerIndex()) {
/* 372 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 374 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 375 */         if (valueArrCount < 0) {
/* 376 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 378 */         pos += VarInt.length(buffer, pos);
/* 379 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 380 */           pos += 6;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 386 */     if ((nullBits & 0x4) != 0) {
/* 387 */       int damageResistanceOffset = buffer.getIntLE(offset + 18);
/* 388 */       if (damageResistanceOffset < 0) {
/* 389 */         return ValidationResult.error("Invalid offset for DamageResistance");
/*     */       }
/* 391 */       int pos = offset + 30 + damageResistanceOffset;
/* 392 */       if (pos >= buffer.writerIndex()) {
/* 393 */         return ValidationResult.error("Offset out of bounds for DamageResistance");
/*     */       }
/* 395 */       int damageResistanceCount = VarInt.peek(buffer, pos);
/* 396 */       if (damageResistanceCount < 0) {
/* 397 */         return ValidationResult.error("Invalid dictionary count for DamageResistance");
/*     */       }
/* 399 */       if (damageResistanceCount > 4096000) {
/* 400 */         return ValidationResult.error("DamageResistance exceeds max length 4096000");
/*     */       }
/* 402 */       pos += VarInt.length(buffer, pos);
/* 403 */       for (int i = 0; i < damageResistanceCount; i++) {
/* 404 */         int keyLen = VarInt.peek(buffer, pos);
/* 405 */         if (keyLen < 0) {
/* 406 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 408 */         if (keyLen > 4096000) {
/* 409 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 411 */         pos += VarInt.length(buffer, pos);
/* 412 */         pos += keyLen;
/* 413 */         if (pos > buffer.writerIndex()) {
/* 414 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 416 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 417 */         if (valueArrCount < 0) {
/* 418 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 420 */         pos += VarInt.length(buffer, pos);
/* 421 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 422 */           pos += 6;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 428 */     if ((nullBits & 0x8) != 0) {
/* 429 */       int damageEnhancementOffset = buffer.getIntLE(offset + 22);
/* 430 */       if (damageEnhancementOffset < 0) {
/* 431 */         return ValidationResult.error("Invalid offset for DamageEnhancement");
/*     */       }
/* 433 */       int pos = offset + 30 + damageEnhancementOffset;
/* 434 */       if (pos >= buffer.writerIndex()) {
/* 435 */         return ValidationResult.error("Offset out of bounds for DamageEnhancement");
/*     */       }
/* 437 */       int damageEnhancementCount = VarInt.peek(buffer, pos);
/* 438 */       if (damageEnhancementCount < 0) {
/* 439 */         return ValidationResult.error("Invalid dictionary count for DamageEnhancement");
/*     */       }
/* 441 */       if (damageEnhancementCount > 4096000) {
/* 442 */         return ValidationResult.error("DamageEnhancement exceeds max length 4096000");
/*     */       }
/* 444 */       pos += VarInt.length(buffer, pos);
/* 445 */       for (int i = 0; i < damageEnhancementCount; i++) {
/* 446 */         int keyLen = VarInt.peek(buffer, pos);
/* 447 */         if (keyLen < 0) {
/* 448 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 450 */         if (keyLen > 4096000) {
/* 451 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 453 */         pos += VarInt.length(buffer, pos);
/* 454 */         pos += keyLen;
/* 455 */         if (pos > buffer.writerIndex()) {
/* 456 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 458 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 459 */         if (valueArrCount < 0) {
/* 460 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 462 */         pos += VarInt.length(buffer, pos);
/* 463 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 464 */           pos += 6;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 470 */     if ((nullBits & 0x10) != 0) {
/* 471 */       int damageClassEnhancementOffset = buffer.getIntLE(offset + 26);
/* 472 */       if (damageClassEnhancementOffset < 0) {
/* 473 */         return ValidationResult.error("Invalid offset for DamageClassEnhancement");
/*     */       }
/* 475 */       int pos = offset + 30 + damageClassEnhancementOffset;
/* 476 */       if (pos >= buffer.writerIndex()) {
/* 477 */         return ValidationResult.error("Offset out of bounds for DamageClassEnhancement");
/*     */       }
/* 479 */       int damageClassEnhancementCount = VarInt.peek(buffer, pos);
/* 480 */       if (damageClassEnhancementCount < 0) {
/* 481 */         return ValidationResult.error("Invalid dictionary count for DamageClassEnhancement");
/*     */       }
/* 483 */       if (damageClassEnhancementCount > 4096000) {
/* 484 */         return ValidationResult.error("DamageClassEnhancement exceeds max length 4096000");
/*     */       }
/* 486 */       pos += VarInt.length(buffer, pos);
/* 487 */       for (int i = 0; i < damageClassEnhancementCount; i++) {
/* 488 */         int keyLen = VarInt.peek(buffer, pos);
/* 489 */         if (keyLen < 0) {
/* 490 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 492 */         if (keyLen > 4096000) {
/* 493 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 495 */         pos += VarInt.length(buffer, pos);
/* 496 */         pos += keyLen;
/* 497 */         if (pos > buffer.writerIndex()) {
/* 498 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 500 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 501 */         if (valueArrCount < 0) {
/* 502 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 504 */         pos += VarInt.length(buffer, pos);
/* 505 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 506 */           pos += 6;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 511 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemArmor clone() {
/* 515 */     ItemArmor copy = new ItemArmor();
/* 516 */     copy.armorSlot = this.armorSlot;
/* 517 */     copy.cosmeticsToHide = (this.cosmeticsToHide != null) ? Arrays.<Cosmetic>copyOf(this.cosmeticsToHide, this.cosmeticsToHide.length) : null;
/* 518 */     if (this.statModifiers != null) {
/* 519 */       Map<Integer, Modifier[]> m = (Map)new HashMap<>();
/* 520 */       for (Map.Entry<Integer, Modifier[]> e : this.statModifiers.entrySet()) m.put(e.getKey(), (Modifier[])Arrays.<Modifier>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new Modifier[x$0])); 
/* 521 */       copy.statModifiers = m;
/*     */     } 
/* 523 */     copy.baseDamageResistance = this.baseDamageResistance;
/* 524 */     if (this.damageResistance != null) {
/* 525 */       Map<String, Modifier[]> m = (Map)new HashMap<>();
/* 526 */       for (Map.Entry<String, Modifier[]> e : this.damageResistance.entrySet()) m.put(e.getKey(), (Modifier[])Arrays.<Modifier>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new Modifier[x$0])); 
/* 527 */       copy.damageResistance = m;
/*     */     } 
/* 529 */     if (this.damageEnhancement != null) {
/* 530 */       Map<String, Modifier[]> m = (Map)new HashMap<>();
/* 531 */       for (Map.Entry<String, Modifier[]> e : this.damageEnhancement.entrySet()) m.put(e.getKey(), (Modifier[])Arrays.<Modifier>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new Modifier[x$0])); 
/* 532 */       copy.damageEnhancement = m;
/*     */     } 
/* 534 */     if (this.damageClassEnhancement != null) {
/* 535 */       Map<String, Modifier[]> m = (Map)new HashMap<>();
/* 536 */       for (Map.Entry<String, Modifier[]> e : this.damageClassEnhancement.entrySet()) m.put(e.getKey(), (Modifier[])Arrays.<Modifier>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new Modifier[x$0])); 
/* 537 */       copy.damageClassEnhancement = m;
/*     */     } 
/* 539 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemArmor other;
/* 545 */     if (this == obj) return true; 
/* 546 */     if (obj instanceof ItemArmor) { other = (ItemArmor)obj; } else { return false; }
/* 547 */      return (Objects.equals(this.armorSlot, other.armorSlot) && Arrays.equals((Object[])this.cosmeticsToHide, (Object[])other.cosmeticsToHide) && Objects.equals(this.statModifiers, other.statModifiers) && this.baseDamageResistance == other.baseDamageResistance && Objects.equals(this.damageResistance, other.damageResistance) && Objects.equals(this.damageEnhancement, other.damageEnhancement) && Objects.equals(this.damageClassEnhancement, other.damageClassEnhancement));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 552 */     int result = 1;
/* 553 */     result = 31 * result + Objects.hashCode(this.armorSlot);
/* 554 */     result = 31 * result + Arrays.hashCode((Object[])this.cosmeticsToHide);
/* 555 */     result = 31 * result + Objects.hashCode(this.statModifiers);
/* 556 */     result = 31 * result + Double.hashCode(this.baseDamageResistance);
/* 557 */     result = 31 * result + Objects.hashCode(this.damageResistance);
/* 558 */     result = 31 * result + Objects.hashCode(this.damageEnhancement);
/* 559 */     result = 31 * result + Objects.hashCode(this.damageClassEnhancement);
/* 560 */     return result;
/*     */   }
/*     */   
/*     */   public ItemArmor() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemArmor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */