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
/*     */ public class BlockBreaking
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 25;
/*     */   public static final int MAX_SIZE = 49152040;
/*     */   @Nullable
/*     */   public String gatherType;
/*     */   public float health;
/*  22 */   public int quantity = 1;
/*     */   
/*     */   public int quality;
/*     */   @Nullable
/*     */   public String itemId;
/*     */   @Nullable
/*     */   public String dropListId;
/*     */   
/*     */   public BlockBreaking(@Nullable String gatherType, float health, int quantity, int quality, @Nullable String itemId, @Nullable String dropListId) {
/*  31 */     this.gatherType = gatherType;
/*  32 */     this.health = health;
/*  33 */     this.quantity = quantity;
/*  34 */     this.quality = quality;
/*  35 */     this.itemId = itemId;
/*  36 */     this.dropListId = dropListId;
/*     */   }
/*     */   
/*     */   public BlockBreaking(@Nonnull BlockBreaking other) {
/*  40 */     this.gatherType = other.gatherType;
/*  41 */     this.health = other.health;
/*  42 */     this.quantity = other.quantity;
/*  43 */     this.quality = other.quality;
/*  44 */     this.itemId = other.itemId;
/*  45 */     this.dropListId = other.dropListId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockBreaking deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     BlockBreaking obj = new BlockBreaking();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.health = buf.getFloatLE(offset + 1);
/*  53 */     obj.quantity = buf.getIntLE(offset + 5);
/*  54 */     obj.quality = buf.getIntLE(offset + 9);
/*     */     
/*  56 */     if ((nullBits & 0x1) != 0) {
/*  57 */       int varPos0 = offset + 25 + buf.getIntLE(offset + 13);
/*  58 */       int gatherTypeLen = VarInt.peek(buf, varPos0);
/*  59 */       if (gatherTypeLen < 0) throw ProtocolException.negativeLength("GatherType", gatherTypeLen); 
/*  60 */       if (gatherTypeLen > 4096000) throw ProtocolException.stringTooLong("GatherType", gatherTypeLen, 4096000); 
/*  61 */       obj.gatherType = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  63 */     if ((nullBits & 0x2) != 0) {
/*  64 */       int varPos1 = offset + 25 + buf.getIntLE(offset + 17);
/*  65 */       int itemIdLen = VarInt.peek(buf, varPos1);
/*  66 */       if (itemIdLen < 0) throw ProtocolException.negativeLength("ItemId", itemIdLen); 
/*  67 */       if (itemIdLen > 4096000) throw ProtocolException.stringTooLong("ItemId", itemIdLen, 4096000); 
/*  68 */       obj.itemId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  70 */     if ((nullBits & 0x4) != 0) {
/*  71 */       int varPos2 = offset + 25 + buf.getIntLE(offset + 21);
/*  72 */       int dropListIdLen = VarInt.peek(buf, varPos2);
/*  73 */       if (dropListIdLen < 0) throw ProtocolException.negativeLength("DropListId", dropListIdLen); 
/*  74 */       if (dropListIdLen > 4096000) throw ProtocolException.stringTooLong("DropListId", dropListIdLen, 4096000); 
/*  75 */       obj.dropListId = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  78 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  82 */     byte nullBits = buf.getByte(offset);
/*  83 */     int maxEnd = 25;
/*  84 */     if ((nullBits & 0x1) != 0) {
/*  85 */       int fieldOffset0 = buf.getIntLE(offset + 13);
/*  86 */       int pos0 = offset + 25 + fieldOffset0;
/*  87 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  88 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  90 */     if ((nullBits & 0x2) != 0) {
/*  91 */       int fieldOffset1 = buf.getIntLE(offset + 17);
/*  92 */       int pos1 = offset + 25 + fieldOffset1;
/*  93 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  94 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  96 */     if ((nullBits & 0x4) != 0) {
/*  97 */       int fieldOffset2 = buf.getIntLE(offset + 21);
/*  98 */       int pos2 = offset + 25 + fieldOffset2;
/*  99 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 100 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 102 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 107 */     int startPos = buf.writerIndex();
/* 108 */     byte nullBits = 0;
/* 109 */     if (this.gatherType != null) nullBits = (byte)(nullBits | 0x1); 
/* 110 */     if (this.itemId != null) nullBits = (byte)(nullBits | 0x2); 
/* 111 */     if (this.dropListId != null) nullBits = (byte)(nullBits | 0x4); 
/* 112 */     buf.writeByte(nullBits);
/*     */     
/* 114 */     buf.writeFloatLE(this.health);
/* 115 */     buf.writeIntLE(this.quantity);
/* 116 */     buf.writeIntLE(this.quality);
/*     */     
/* 118 */     int gatherTypeOffsetSlot = buf.writerIndex();
/* 119 */     buf.writeIntLE(0);
/* 120 */     int itemIdOffsetSlot = buf.writerIndex();
/* 121 */     buf.writeIntLE(0);
/* 122 */     int dropListIdOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/*     */     
/* 125 */     int varBlockStart = buf.writerIndex();
/* 126 */     if (this.gatherType != null) {
/* 127 */       buf.setIntLE(gatherTypeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       PacketIO.writeVarString(buf, this.gatherType, 4096000);
/*     */     } else {
/* 130 */       buf.setIntLE(gatherTypeOffsetSlot, -1);
/*     */     } 
/* 132 */     if (this.itemId != null) {
/* 133 */       buf.setIntLE(itemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 134 */       PacketIO.writeVarString(buf, this.itemId, 4096000);
/*     */     } else {
/* 136 */       buf.setIntLE(itemIdOffsetSlot, -1);
/*     */     } 
/* 138 */     if (this.dropListId != null) {
/* 139 */       buf.setIntLE(dropListIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 140 */       PacketIO.writeVarString(buf, this.dropListId, 4096000);
/*     */     } else {
/* 142 */       buf.setIntLE(dropListIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 148 */     int size = 25;
/* 149 */     if (this.gatherType != null) size += PacketIO.stringSize(this.gatherType); 
/* 150 */     if (this.itemId != null) size += PacketIO.stringSize(this.itemId); 
/* 151 */     if (this.dropListId != null) size += PacketIO.stringSize(this.dropListId);
/*     */     
/* 153 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 157 */     if (buffer.readableBytes() - offset < 25) {
/* 158 */       return ValidationResult.error("Buffer too small: expected at least 25 bytes");
/*     */     }
/*     */     
/* 161 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 164 */     if ((nullBits & 0x1) != 0) {
/* 165 */       int gatherTypeOffset = buffer.getIntLE(offset + 13);
/* 166 */       if (gatherTypeOffset < 0) {
/* 167 */         return ValidationResult.error("Invalid offset for GatherType");
/*     */       }
/* 169 */       int pos = offset + 25 + gatherTypeOffset;
/* 170 */       if (pos >= buffer.writerIndex()) {
/* 171 */         return ValidationResult.error("Offset out of bounds for GatherType");
/*     */       }
/* 173 */       int gatherTypeLen = VarInt.peek(buffer, pos);
/* 174 */       if (gatherTypeLen < 0) {
/* 175 */         return ValidationResult.error("Invalid string length for GatherType");
/*     */       }
/* 177 */       if (gatherTypeLen > 4096000) {
/* 178 */         return ValidationResult.error("GatherType exceeds max length 4096000");
/*     */       }
/* 180 */       pos += VarInt.length(buffer, pos);
/* 181 */       pos += gatherTypeLen;
/* 182 */       if (pos > buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Buffer overflow reading GatherType");
/*     */       }
/*     */     } 
/*     */     
/* 187 */     if ((nullBits & 0x2) != 0) {
/* 188 */       int itemIdOffset = buffer.getIntLE(offset + 17);
/* 189 */       if (itemIdOffset < 0) {
/* 190 */         return ValidationResult.error("Invalid offset for ItemId");
/*     */       }
/* 192 */       int pos = offset + 25 + itemIdOffset;
/* 193 */       if (pos >= buffer.writerIndex()) {
/* 194 */         return ValidationResult.error("Offset out of bounds for ItemId");
/*     */       }
/* 196 */       int itemIdLen = VarInt.peek(buffer, pos);
/* 197 */       if (itemIdLen < 0) {
/* 198 */         return ValidationResult.error("Invalid string length for ItemId");
/*     */       }
/* 200 */       if (itemIdLen > 4096000) {
/* 201 */         return ValidationResult.error("ItemId exceeds max length 4096000");
/*     */       }
/* 203 */       pos += VarInt.length(buffer, pos);
/* 204 */       pos += itemIdLen;
/* 205 */       if (pos > buffer.writerIndex()) {
/* 206 */         return ValidationResult.error("Buffer overflow reading ItemId");
/*     */       }
/*     */     } 
/*     */     
/* 210 */     if ((nullBits & 0x4) != 0) {
/* 211 */       int dropListIdOffset = buffer.getIntLE(offset + 21);
/* 212 */       if (dropListIdOffset < 0) {
/* 213 */         return ValidationResult.error("Invalid offset for DropListId");
/*     */       }
/* 215 */       int pos = offset + 25 + dropListIdOffset;
/* 216 */       if (pos >= buffer.writerIndex()) {
/* 217 */         return ValidationResult.error("Offset out of bounds for DropListId");
/*     */       }
/* 219 */       int dropListIdLen = VarInt.peek(buffer, pos);
/* 220 */       if (dropListIdLen < 0) {
/* 221 */         return ValidationResult.error("Invalid string length for DropListId");
/*     */       }
/* 223 */       if (dropListIdLen > 4096000) {
/* 224 */         return ValidationResult.error("DropListId exceeds max length 4096000");
/*     */       }
/* 226 */       pos += VarInt.length(buffer, pos);
/* 227 */       pos += dropListIdLen;
/* 228 */       if (pos > buffer.writerIndex()) {
/* 229 */         return ValidationResult.error("Buffer overflow reading DropListId");
/*     */       }
/*     */     } 
/* 232 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockBreaking clone() {
/* 236 */     BlockBreaking copy = new BlockBreaking();
/* 237 */     copy.gatherType = this.gatherType;
/* 238 */     copy.health = this.health;
/* 239 */     copy.quantity = this.quantity;
/* 240 */     copy.quality = this.quality;
/* 241 */     copy.itemId = this.itemId;
/* 242 */     copy.dropListId = this.dropListId;
/* 243 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockBreaking other;
/* 249 */     if (this == obj) return true; 
/* 250 */     if (obj instanceof BlockBreaking) { other = (BlockBreaking)obj; } else { return false; }
/* 251 */      return (Objects.equals(this.gatherType, other.gatherType) && this.health == other.health && this.quantity == other.quantity && this.quality == other.quality && Objects.equals(this.itemId, other.itemId) && Objects.equals(this.dropListId, other.dropListId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     return Objects.hash(new Object[] { this.gatherType, Float.valueOf(this.health), Integer.valueOf(this.quantity), Integer.valueOf(this.quality), this.itemId, this.dropListId });
/*     */   }
/*     */   
/*     */   public BlockBreaking() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockBreaking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */