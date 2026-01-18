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
/*     */ public class SoftBlock
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 32768020;
/*     */   @Nullable
/*     */   public String itemId;
/*     */   @Nullable
/*     */   public String dropListId;
/*     */   public boolean isWeaponBreakable;
/*     */   
/*     */   public SoftBlock() {}
/*     */   
/*     */   public SoftBlock(@Nullable String itemId, @Nullable String dropListId, boolean isWeaponBreakable) {
/*  28 */     this.itemId = itemId;
/*  29 */     this.dropListId = dropListId;
/*  30 */     this.isWeaponBreakable = isWeaponBreakable;
/*     */   }
/*     */   
/*     */   public SoftBlock(@Nonnull SoftBlock other) {
/*  34 */     this.itemId = other.itemId;
/*  35 */     this.dropListId = other.dropListId;
/*  36 */     this.isWeaponBreakable = other.isWeaponBreakable;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SoftBlock deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     SoftBlock obj = new SoftBlock();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.isWeaponBreakable = (buf.getByte(offset + 1) != 0);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 10 + buf.getIntLE(offset + 2);
/*  47 */       int itemIdLen = VarInt.peek(buf, varPos0);
/*  48 */       if (itemIdLen < 0) throw ProtocolException.negativeLength("ItemId", itemIdLen); 
/*  49 */       if (itemIdLen > 4096000) throw ProtocolException.stringTooLong("ItemId", itemIdLen, 4096000); 
/*  50 */       obj.itemId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  52 */     if ((nullBits & 0x2) != 0) {
/*  53 */       int varPos1 = offset + 10 + buf.getIntLE(offset + 6);
/*  54 */       int dropListIdLen = VarInt.peek(buf, varPos1);
/*  55 */       if (dropListIdLen < 0) throw ProtocolException.negativeLength("DropListId", dropListIdLen); 
/*  56 */       if (dropListIdLen > 4096000) throw ProtocolException.stringTooLong("DropListId", dropListIdLen, 4096000); 
/*  57 */       obj.dropListId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int maxEnd = 10;
/*  66 */     if ((nullBits & 0x1) != 0) {
/*  67 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  68 */       int pos0 = offset + 10 + fieldOffset0;
/*  69 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  70 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  72 */     if ((nullBits & 0x2) != 0) {
/*  73 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  74 */       int pos1 = offset + 10 + fieldOffset1;
/*  75 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  76 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  78 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  83 */     int startPos = buf.writerIndex();
/*  84 */     byte nullBits = 0;
/*  85 */     if (this.itemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  86 */     if (this.dropListId != null) nullBits = (byte)(nullBits | 0x2); 
/*  87 */     buf.writeByte(nullBits);
/*     */     
/*  89 */     buf.writeByte(this.isWeaponBreakable ? 1 : 0);
/*     */     
/*  91 */     int itemIdOffsetSlot = buf.writerIndex();
/*  92 */     buf.writeIntLE(0);
/*  93 */     int dropListIdOffsetSlot = buf.writerIndex();
/*  94 */     buf.writeIntLE(0);
/*     */     
/*  96 */     int varBlockStart = buf.writerIndex();
/*  97 */     if (this.itemId != null) {
/*  98 */       buf.setIntLE(itemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/*  99 */       PacketIO.writeVarString(buf, this.itemId, 4096000);
/*     */     } else {
/* 101 */       buf.setIntLE(itemIdOffsetSlot, -1);
/*     */     } 
/* 103 */     if (this.dropListId != null) {
/* 104 */       buf.setIntLE(dropListIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 105 */       PacketIO.writeVarString(buf, this.dropListId, 4096000);
/*     */     } else {
/* 107 */       buf.setIntLE(dropListIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 113 */     int size = 10;
/* 114 */     if (this.itemId != null) size += PacketIO.stringSize(this.itemId); 
/* 115 */     if (this.dropListId != null) size += PacketIO.stringSize(this.dropListId);
/*     */     
/* 117 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 121 */     if (buffer.readableBytes() - offset < 10) {
/* 122 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 125 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 128 */     if ((nullBits & 0x1) != 0) {
/* 129 */       int itemIdOffset = buffer.getIntLE(offset + 2);
/* 130 */       if (itemIdOffset < 0) {
/* 131 */         return ValidationResult.error("Invalid offset for ItemId");
/*     */       }
/* 133 */       int pos = offset + 10 + itemIdOffset;
/* 134 */       if (pos >= buffer.writerIndex()) {
/* 135 */         return ValidationResult.error("Offset out of bounds for ItemId");
/*     */       }
/* 137 */       int itemIdLen = VarInt.peek(buffer, pos);
/* 138 */       if (itemIdLen < 0) {
/* 139 */         return ValidationResult.error("Invalid string length for ItemId");
/*     */       }
/* 141 */       if (itemIdLen > 4096000) {
/* 142 */         return ValidationResult.error("ItemId exceeds max length 4096000");
/*     */       }
/* 144 */       pos += VarInt.length(buffer, pos);
/* 145 */       pos += itemIdLen;
/* 146 */       if (pos > buffer.writerIndex()) {
/* 147 */         return ValidationResult.error("Buffer overflow reading ItemId");
/*     */       }
/*     */     } 
/*     */     
/* 151 */     if ((nullBits & 0x2) != 0) {
/* 152 */       int dropListIdOffset = buffer.getIntLE(offset + 6);
/* 153 */       if (dropListIdOffset < 0) {
/* 154 */         return ValidationResult.error("Invalid offset for DropListId");
/*     */       }
/* 156 */       int pos = offset + 10 + dropListIdOffset;
/* 157 */       if (pos >= buffer.writerIndex()) {
/* 158 */         return ValidationResult.error("Offset out of bounds for DropListId");
/*     */       }
/* 160 */       int dropListIdLen = VarInt.peek(buffer, pos);
/* 161 */       if (dropListIdLen < 0) {
/* 162 */         return ValidationResult.error("Invalid string length for DropListId");
/*     */       }
/* 164 */       if (dropListIdLen > 4096000) {
/* 165 */         return ValidationResult.error("DropListId exceeds max length 4096000");
/*     */       }
/* 167 */       pos += VarInt.length(buffer, pos);
/* 168 */       pos += dropListIdLen;
/* 169 */       if (pos > buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Buffer overflow reading DropListId");
/*     */       }
/*     */     } 
/* 173 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SoftBlock clone() {
/* 177 */     SoftBlock copy = new SoftBlock();
/* 178 */     copy.itemId = this.itemId;
/* 179 */     copy.dropListId = this.dropListId;
/* 180 */     copy.isWeaponBreakable = this.isWeaponBreakable;
/* 181 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SoftBlock other;
/* 187 */     if (this == obj) return true; 
/* 188 */     if (obj instanceof SoftBlock) { other = (SoftBlock)obj; } else { return false; }
/* 189 */      return (Objects.equals(this.itemId, other.itemId) && Objects.equals(this.dropListId, other.dropListId) && this.isWeaponBreakable == other.isWeaponBreakable);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     return Objects.hash(new Object[] { this.itemId, this.dropListId, Boolean.valueOf(this.isWeaponBreakable) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SoftBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */