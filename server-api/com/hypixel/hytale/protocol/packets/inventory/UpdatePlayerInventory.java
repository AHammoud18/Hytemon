/*     */ package com.hypixel.hytale.protocol.packets.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InventorySection;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.SortType;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdatePlayerInventory
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 170;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  26 */     return 170;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InventorySection storage;
/*     */   @Nullable
/*     */   public InventorySection armor;
/*     */   @Nullable
/*     */   public InventorySection hotbar;
/*     */   @Nonnull
/*  36 */   public SortType sortType = SortType.Name; @Nullable
/*     */   public InventorySection utility;
/*     */   @Nullable
/*     */   public InventorySection builderMaterial;
/*     */   
/*     */   public UpdatePlayerInventory(@Nullable InventorySection storage, @Nullable InventorySection armor, @Nullable InventorySection hotbar, @Nullable InventorySection utility, @Nullable InventorySection builderMaterial, @Nullable InventorySection tools, @Nullable InventorySection backpack, @Nonnull SortType sortType) {
/*  42 */     this.storage = storage;
/*  43 */     this.armor = armor;
/*  44 */     this.hotbar = hotbar;
/*  45 */     this.utility = utility;
/*  46 */     this.builderMaterial = builderMaterial;
/*  47 */     this.tools = tools;
/*  48 */     this.backpack = backpack;
/*  49 */     this.sortType = sortType;
/*     */   } @Nullable
/*     */   public InventorySection tools; @Nullable
/*     */   public InventorySection backpack; public UpdatePlayerInventory(@Nonnull UpdatePlayerInventory other) {
/*  53 */     this.storage = other.storage;
/*  54 */     this.armor = other.armor;
/*  55 */     this.hotbar = other.hotbar;
/*  56 */     this.utility = other.utility;
/*  57 */     this.builderMaterial = other.builderMaterial;
/*  58 */     this.tools = other.tools;
/*  59 */     this.backpack = other.backpack;
/*  60 */     this.sortType = other.sortType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdatePlayerInventory deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     UpdatePlayerInventory obj = new UpdatePlayerInventory();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.sortType = SortType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  69 */     if ((nullBits & 0x1) != 0) {
/*  70 */       int varPos0 = offset + 30 + buf.getIntLE(offset + 2);
/*  71 */       obj.storage = InventorySection.deserialize(buf, varPos0);
/*     */     } 
/*  73 */     if ((nullBits & 0x2) != 0) {
/*  74 */       int varPos1 = offset + 30 + buf.getIntLE(offset + 6);
/*  75 */       obj.armor = InventorySection.deserialize(buf, varPos1);
/*     */     } 
/*  77 */     if ((nullBits & 0x4) != 0) {
/*  78 */       int varPos2 = offset + 30 + buf.getIntLE(offset + 10);
/*  79 */       obj.hotbar = InventorySection.deserialize(buf, varPos2);
/*     */     } 
/*  81 */     if ((nullBits & 0x8) != 0) {
/*  82 */       int varPos3 = offset + 30 + buf.getIntLE(offset + 14);
/*  83 */       obj.utility = InventorySection.deserialize(buf, varPos3);
/*     */     } 
/*  85 */     if ((nullBits & 0x10) != 0) {
/*  86 */       int varPos4 = offset + 30 + buf.getIntLE(offset + 18);
/*  87 */       obj.builderMaterial = InventorySection.deserialize(buf, varPos4);
/*     */     } 
/*  89 */     if ((nullBits & 0x20) != 0) {
/*  90 */       int varPos5 = offset + 30 + buf.getIntLE(offset + 22);
/*  91 */       obj.tools = InventorySection.deserialize(buf, varPos5);
/*     */     } 
/*  93 */     if ((nullBits & 0x40) != 0) {
/*  94 */       int varPos6 = offset + 30 + buf.getIntLE(offset + 26);
/*  95 */       obj.backpack = InventorySection.deserialize(buf, varPos6);
/*     */     } 
/*     */     
/*  98 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 102 */     byte nullBits = buf.getByte(offset);
/* 103 */     int maxEnd = 30;
/* 104 */     if ((nullBits & 0x1) != 0) {
/* 105 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/* 106 */       int pos0 = offset + 30 + fieldOffset0;
/* 107 */       pos0 += InventorySection.computeBytesConsumed(buf, pos0);
/* 108 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 110 */     if ((nullBits & 0x2) != 0) {
/* 111 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/* 112 */       int pos1 = offset + 30 + fieldOffset1;
/* 113 */       pos1 += InventorySection.computeBytesConsumed(buf, pos1);
/* 114 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 116 */     if ((nullBits & 0x4) != 0) {
/* 117 */       int fieldOffset2 = buf.getIntLE(offset + 10);
/* 118 */       int pos2 = offset + 30 + fieldOffset2;
/* 119 */       pos2 += InventorySection.computeBytesConsumed(buf, pos2);
/* 120 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 122 */     if ((nullBits & 0x8) != 0) {
/* 123 */       int fieldOffset3 = buf.getIntLE(offset + 14);
/* 124 */       int pos3 = offset + 30 + fieldOffset3;
/* 125 */       pos3 += InventorySection.computeBytesConsumed(buf, pos3);
/* 126 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 128 */     if ((nullBits & 0x10) != 0) {
/* 129 */       int fieldOffset4 = buf.getIntLE(offset + 18);
/* 130 */       int pos4 = offset + 30 + fieldOffset4;
/* 131 */       pos4 += InventorySection.computeBytesConsumed(buf, pos4);
/* 132 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 134 */     if ((nullBits & 0x20) != 0) {
/* 135 */       int fieldOffset5 = buf.getIntLE(offset + 22);
/* 136 */       int pos5 = offset + 30 + fieldOffset5;
/* 137 */       pos5 += InventorySection.computeBytesConsumed(buf, pos5);
/* 138 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x40) != 0) {
/* 141 */       int fieldOffset6 = buf.getIntLE(offset + 26);
/* 142 */       int pos6 = offset + 30 + fieldOffset6;
/* 143 */       pos6 += InventorySection.computeBytesConsumed(buf, pos6);
/* 144 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 146 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 152 */     int startPos = buf.writerIndex();
/* 153 */     byte nullBits = 0;
/* 154 */     if (this.storage != null) nullBits = (byte)(nullBits | 0x1); 
/* 155 */     if (this.armor != null) nullBits = (byte)(nullBits | 0x2); 
/* 156 */     if (this.hotbar != null) nullBits = (byte)(nullBits | 0x4); 
/* 157 */     if (this.utility != null) nullBits = (byte)(nullBits | 0x8); 
/* 158 */     if (this.builderMaterial != null) nullBits = (byte)(nullBits | 0x10); 
/* 159 */     if (this.tools != null) nullBits = (byte)(nullBits | 0x20); 
/* 160 */     if (this.backpack != null) nullBits = (byte)(nullBits | 0x40); 
/* 161 */     buf.writeByte(nullBits);
/*     */     
/* 163 */     buf.writeByte(this.sortType.getValue());
/*     */     
/* 165 */     int storageOffsetSlot = buf.writerIndex();
/* 166 */     buf.writeIntLE(0);
/* 167 */     int armorOffsetSlot = buf.writerIndex();
/* 168 */     buf.writeIntLE(0);
/* 169 */     int hotbarOffsetSlot = buf.writerIndex();
/* 170 */     buf.writeIntLE(0);
/* 171 */     int utilityOffsetSlot = buf.writerIndex();
/* 172 */     buf.writeIntLE(0);
/* 173 */     int builderMaterialOffsetSlot = buf.writerIndex();
/* 174 */     buf.writeIntLE(0);
/* 175 */     int toolsOffsetSlot = buf.writerIndex();
/* 176 */     buf.writeIntLE(0);
/* 177 */     int backpackOffsetSlot = buf.writerIndex();
/* 178 */     buf.writeIntLE(0);
/*     */     
/* 180 */     int varBlockStart = buf.writerIndex();
/* 181 */     if (this.storage != null) {
/* 182 */       buf.setIntLE(storageOffsetSlot, buf.writerIndex() - varBlockStart);
/* 183 */       this.storage.serialize(buf);
/*     */     } else {
/* 185 */       buf.setIntLE(storageOffsetSlot, -1);
/*     */     } 
/* 187 */     if (this.armor != null) {
/* 188 */       buf.setIntLE(armorOffsetSlot, buf.writerIndex() - varBlockStart);
/* 189 */       this.armor.serialize(buf);
/*     */     } else {
/* 191 */       buf.setIntLE(armorOffsetSlot, -1);
/*     */     } 
/* 193 */     if (this.hotbar != null) {
/* 194 */       buf.setIntLE(hotbarOffsetSlot, buf.writerIndex() - varBlockStart);
/* 195 */       this.hotbar.serialize(buf);
/*     */     } else {
/* 197 */       buf.setIntLE(hotbarOffsetSlot, -1);
/*     */     } 
/* 199 */     if (this.utility != null) {
/* 200 */       buf.setIntLE(utilityOffsetSlot, buf.writerIndex() - varBlockStart);
/* 201 */       this.utility.serialize(buf);
/*     */     } else {
/* 203 */       buf.setIntLE(utilityOffsetSlot, -1);
/*     */     } 
/* 205 */     if (this.builderMaterial != null) {
/* 206 */       buf.setIntLE(builderMaterialOffsetSlot, buf.writerIndex() - varBlockStart);
/* 207 */       this.builderMaterial.serialize(buf);
/*     */     } else {
/* 209 */       buf.setIntLE(builderMaterialOffsetSlot, -1);
/*     */     } 
/* 211 */     if (this.tools != null) {
/* 212 */       buf.setIntLE(toolsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 213 */       this.tools.serialize(buf);
/*     */     } else {
/* 215 */       buf.setIntLE(toolsOffsetSlot, -1);
/*     */     } 
/* 217 */     if (this.backpack != null) {
/* 218 */       buf.setIntLE(backpackOffsetSlot, buf.writerIndex() - varBlockStart);
/* 219 */       this.backpack.serialize(buf);
/*     */     } else {
/* 221 */       buf.setIntLE(backpackOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 227 */     int size = 30;
/* 228 */     if (this.storage != null) size += this.storage.computeSize(); 
/* 229 */     if (this.armor != null) size += this.armor.computeSize(); 
/* 230 */     if (this.hotbar != null) size += this.hotbar.computeSize(); 
/* 231 */     if (this.utility != null) size += this.utility.computeSize(); 
/* 232 */     if (this.builderMaterial != null) size += this.builderMaterial.computeSize(); 
/* 233 */     if (this.tools != null) size += this.tools.computeSize(); 
/* 234 */     if (this.backpack != null) size += this.backpack.computeSize();
/*     */     
/* 236 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 240 */     if (buffer.readableBytes() - offset < 30) {
/* 241 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */     
/* 244 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 247 */     if ((nullBits & 0x1) != 0) {
/* 248 */       int storageOffset = buffer.getIntLE(offset + 2);
/* 249 */       if (storageOffset < 0) {
/* 250 */         return ValidationResult.error("Invalid offset for Storage");
/*     */       }
/* 252 */       int pos = offset + 30 + storageOffset;
/* 253 */       if (pos >= buffer.writerIndex()) {
/* 254 */         return ValidationResult.error("Offset out of bounds for Storage");
/*     */       }
/* 256 */       ValidationResult storageResult = InventorySection.validateStructure(buffer, pos);
/* 257 */       if (!storageResult.isValid()) {
/* 258 */         return ValidationResult.error("Invalid Storage: " + storageResult.error());
/*     */       }
/* 260 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 263 */     if ((nullBits & 0x2) != 0) {
/* 264 */       int armorOffset = buffer.getIntLE(offset + 6);
/* 265 */       if (armorOffset < 0) {
/* 266 */         return ValidationResult.error("Invalid offset for Armor");
/*     */       }
/* 268 */       int pos = offset + 30 + armorOffset;
/* 269 */       if (pos >= buffer.writerIndex()) {
/* 270 */         return ValidationResult.error("Offset out of bounds for Armor");
/*     */       }
/* 272 */       ValidationResult armorResult = InventorySection.validateStructure(buffer, pos);
/* 273 */       if (!armorResult.isValid()) {
/* 274 */         return ValidationResult.error("Invalid Armor: " + armorResult.error());
/*     */       }
/* 276 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 279 */     if ((nullBits & 0x4) != 0) {
/* 280 */       int hotbarOffset = buffer.getIntLE(offset + 10);
/* 281 */       if (hotbarOffset < 0) {
/* 282 */         return ValidationResult.error("Invalid offset for Hotbar");
/*     */       }
/* 284 */       int pos = offset + 30 + hotbarOffset;
/* 285 */       if (pos >= buffer.writerIndex()) {
/* 286 */         return ValidationResult.error("Offset out of bounds for Hotbar");
/*     */       }
/* 288 */       ValidationResult hotbarResult = InventorySection.validateStructure(buffer, pos);
/* 289 */       if (!hotbarResult.isValid()) {
/* 290 */         return ValidationResult.error("Invalid Hotbar: " + hotbarResult.error());
/*     */       }
/* 292 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 295 */     if ((nullBits & 0x8) != 0) {
/* 296 */       int utilityOffset = buffer.getIntLE(offset + 14);
/* 297 */       if (utilityOffset < 0) {
/* 298 */         return ValidationResult.error("Invalid offset for Utility");
/*     */       }
/* 300 */       int pos = offset + 30 + utilityOffset;
/* 301 */       if (pos >= buffer.writerIndex()) {
/* 302 */         return ValidationResult.error("Offset out of bounds for Utility");
/*     */       }
/* 304 */       ValidationResult utilityResult = InventorySection.validateStructure(buffer, pos);
/* 305 */       if (!utilityResult.isValid()) {
/* 306 */         return ValidationResult.error("Invalid Utility: " + utilityResult.error());
/*     */       }
/* 308 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 311 */     if ((nullBits & 0x10) != 0) {
/* 312 */       int builderMaterialOffset = buffer.getIntLE(offset + 18);
/* 313 */       if (builderMaterialOffset < 0) {
/* 314 */         return ValidationResult.error("Invalid offset for BuilderMaterial");
/*     */       }
/* 316 */       int pos = offset + 30 + builderMaterialOffset;
/* 317 */       if (pos >= buffer.writerIndex()) {
/* 318 */         return ValidationResult.error("Offset out of bounds for BuilderMaterial");
/*     */       }
/* 320 */       ValidationResult builderMaterialResult = InventorySection.validateStructure(buffer, pos);
/* 321 */       if (!builderMaterialResult.isValid()) {
/* 322 */         return ValidationResult.error("Invalid BuilderMaterial: " + builderMaterialResult.error());
/*     */       }
/* 324 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 327 */     if ((nullBits & 0x20) != 0) {
/* 328 */       int toolsOffset = buffer.getIntLE(offset + 22);
/* 329 */       if (toolsOffset < 0) {
/* 330 */         return ValidationResult.error("Invalid offset for Tools");
/*     */       }
/* 332 */       int pos = offset + 30 + toolsOffset;
/* 333 */       if (pos >= buffer.writerIndex()) {
/* 334 */         return ValidationResult.error("Offset out of bounds for Tools");
/*     */       }
/* 336 */       ValidationResult toolsResult = InventorySection.validateStructure(buffer, pos);
/* 337 */       if (!toolsResult.isValid()) {
/* 338 */         return ValidationResult.error("Invalid Tools: " + toolsResult.error());
/*     */       }
/* 340 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 343 */     if ((nullBits & 0x40) != 0) {
/* 344 */       int backpackOffset = buffer.getIntLE(offset + 26);
/* 345 */       if (backpackOffset < 0) {
/* 346 */         return ValidationResult.error("Invalid offset for Backpack");
/*     */       }
/* 348 */       int pos = offset + 30 + backpackOffset;
/* 349 */       if (pos >= buffer.writerIndex()) {
/* 350 */         return ValidationResult.error("Offset out of bounds for Backpack");
/*     */       }
/* 352 */       ValidationResult backpackResult = InventorySection.validateStructure(buffer, pos);
/* 353 */       if (!backpackResult.isValid()) {
/* 354 */         return ValidationResult.error("Invalid Backpack: " + backpackResult.error());
/*     */       }
/* 356 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 358 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdatePlayerInventory clone() {
/* 362 */     UpdatePlayerInventory copy = new UpdatePlayerInventory();
/* 363 */     copy.storage = (this.storage != null) ? this.storage.clone() : null;
/* 364 */     copy.armor = (this.armor != null) ? this.armor.clone() : null;
/* 365 */     copy.hotbar = (this.hotbar != null) ? this.hotbar.clone() : null;
/* 366 */     copy.utility = (this.utility != null) ? this.utility.clone() : null;
/* 367 */     copy.builderMaterial = (this.builderMaterial != null) ? this.builderMaterial.clone() : null;
/* 368 */     copy.tools = (this.tools != null) ? this.tools.clone() : null;
/* 369 */     copy.backpack = (this.backpack != null) ? this.backpack.clone() : null;
/* 370 */     copy.sortType = this.sortType;
/* 371 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdatePlayerInventory other;
/* 377 */     if (this == obj) return true; 
/* 378 */     if (obj instanceof UpdatePlayerInventory) { other = (UpdatePlayerInventory)obj; } else { return false; }
/* 379 */      return (Objects.equals(this.storage, other.storage) && Objects.equals(this.armor, other.armor) && Objects.equals(this.hotbar, other.hotbar) && Objects.equals(this.utility, other.utility) && Objects.equals(this.builderMaterial, other.builderMaterial) && Objects.equals(this.tools, other.tools) && Objects.equals(this.backpack, other.backpack) && Objects.equals(this.sortType, other.sortType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 384 */     return Objects.hash(new Object[] { this.storage, this.armor, this.hotbar, this.utility, this.builderMaterial, this.tools, this.backpack, this.sortType });
/*     */   }
/*     */   
/*     */   public UpdatePlayerInventory() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\UpdatePlayerInventory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */