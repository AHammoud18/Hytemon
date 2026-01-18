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
/*     */ public class Harvesting
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 32768019;
/*     */   @Nullable
/*     */   public String itemId;
/*     */   @Nullable
/*     */   public String dropListId;
/*     */   
/*     */   public Harvesting() {}
/*     */   
/*     */   public Harvesting(@Nullable String itemId, @Nullable String dropListId) {
/*  27 */     this.itemId = itemId;
/*  28 */     this.dropListId = dropListId;
/*     */   }
/*     */   
/*     */   public Harvesting(@Nonnull Harvesting other) {
/*  32 */     this.itemId = other.itemId;
/*  33 */     this.dropListId = other.dropListId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Harvesting deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     Harvesting obj = new Harvesting();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int itemIdLen = VarInt.peek(buf, varPos0);
/*  44 */       if (itemIdLen < 0) throw ProtocolException.negativeLength("ItemId", itemIdLen); 
/*  45 */       if (itemIdLen > 4096000) throw ProtocolException.stringTooLong("ItemId", itemIdLen, 4096000); 
/*  46 */       obj.itemId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int dropListIdLen = VarInt.peek(buf, varPos1);
/*  51 */       if (dropListIdLen < 0) throw ProtocolException.negativeLength("DropListId", dropListIdLen); 
/*  52 */       if (dropListIdLen > 4096000) throw ProtocolException.stringTooLong("DropListId", dropListIdLen, 4096000); 
/*  53 */       obj.dropListId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int maxEnd = 9;
/*  62 */     if ((nullBits & 0x1) != 0) {
/*  63 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  64 */       int pos0 = offset + 9 + fieldOffset0;
/*  65 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  66 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  70 */       int pos1 = offset + 9 + fieldOffset1;
/*  71 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  72 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  74 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  79 */     int startPos = buf.writerIndex();
/*  80 */     byte nullBits = 0;
/*  81 */     if (this.itemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  82 */     if (this.dropListId != null) nullBits = (byte)(nullBits | 0x2); 
/*  83 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  86 */     int itemIdOffsetSlot = buf.writerIndex();
/*  87 */     buf.writeIntLE(0);
/*  88 */     int dropListIdOffsetSlot = buf.writerIndex();
/*  89 */     buf.writeIntLE(0);
/*     */     
/*  91 */     int varBlockStart = buf.writerIndex();
/*  92 */     if (this.itemId != null) {
/*  93 */       buf.setIntLE(itemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/*  94 */       PacketIO.writeVarString(buf, this.itemId, 4096000);
/*     */     } else {
/*  96 */       buf.setIntLE(itemIdOffsetSlot, -1);
/*     */     } 
/*  98 */     if (this.dropListId != null) {
/*  99 */       buf.setIntLE(dropListIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 100 */       PacketIO.writeVarString(buf, this.dropListId, 4096000);
/*     */     } else {
/* 102 */       buf.setIntLE(dropListIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 108 */     int size = 9;
/* 109 */     if (this.itemId != null) size += PacketIO.stringSize(this.itemId); 
/* 110 */     if (this.dropListId != null) size += PacketIO.stringSize(this.dropListId);
/*     */     
/* 112 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 116 */     if (buffer.readableBytes() - offset < 9) {
/* 117 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 120 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 123 */     if ((nullBits & 0x1) != 0) {
/* 124 */       int itemIdOffset = buffer.getIntLE(offset + 1);
/* 125 */       if (itemIdOffset < 0) {
/* 126 */         return ValidationResult.error("Invalid offset for ItemId");
/*     */       }
/* 128 */       int pos = offset + 9 + itemIdOffset;
/* 129 */       if (pos >= buffer.writerIndex()) {
/* 130 */         return ValidationResult.error("Offset out of bounds for ItemId");
/*     */       }
/* 132 */       int itemIdLen = VarInt.peek(buffer, pos);
/* 133 */       if (itemIdLen < 0) {
/* 134 */         return ValidationResult.error("Invalid string length for ItemId");
/*     */       }
/* 136 */       if (itemIdLen > 4096000) {
/* 137 */         return ValidationResult.error("ItemId exceeds max length 4096000");
/*     */       }
/* 139 */       pos += VarInt.length(buffer, pos);
/* 140 */       pos += itemIdLen;
/* 141 */       if (pos > buffer.writerIndex()) {
/* 142 */         return ValidationResult.error("Buffer overflow reading ItemId");
/*     */       }
/*     */     } 
/*     */     
/* 146 */     if ((nullBits & 0x2) != 0) {
/* 147 */       int dropListIdOffset = buffer.getIntLE(offset + 5);
/* 148 */       if (dropListIdOffset < 0) {
/* 149 */         return ValidationResult.error("Invalid offset for DropListId");
/*     */       }
/* 151 */       int pos = offset + 9 + dropListIdOffset;
/* 152 */       if (pos >= buffer.writerIndex()) {
/* 153 */         return ValidationResult.error("Offset out of bounds for DropListId");
/*     */       }
/* 155 */       int dropListIdLen = VarInt.peek(buffer, pos);
/* 156 */       if (dropListIdLen < 0) {
/* 157 */         return ValidationResult.error("Invalid string length for DropListId");
/*     */       }
/* 159 */       if (dropListIdLen > 4096000) {
/* 160 */         return ValidationResult.error("DropListId exceeds max length 4096000");
/*     */       }
/* 162 */       pos += VarInt.length(buffer, pos);
/* 163 */       pos += dropListIdLen;
/* 164 */       if (pos > buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Buffer overflow reading DropListId");
/*     */       }
/*     */     } 
/* 168 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Harvesting clone() {
/* 172 */     Harvesting copy = new Harvesting();
/* 173 */     copy.itemId = this.itemId;
/* 174 */     copy.dropListId = this.dropListId;
/* 175 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Harvesting other;
/* 181 */     if (this == obj) return true; 
/* 182 */     if (obj instanceof Harvesting) { other = (Harvesting)obj; } else { return false; }
/* 183 */      return (Objects.equals(this.itemId, other.itemId) && Objects.equals(this.dropListId, other.dropListId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 188 */     return Objects.hash(new Object[] { this.itemId, this.dropListId });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Harvesting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */