/*     */ package com.hypixel.hytale.protocol.packets.window;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ExtraResources;
/*     */ import com.hypixel.hytale.protocol.InventorySection;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class OpenWindow implements Packet {
/*     */   public static final int PACKET_ID = 200;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 18;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int id;
/*     */   
/*     */   public int getId() {
/*  26 */     return 200;
/*     */   }
/*     */   
/*     */   @Nonnull
/*  30 */   public WindowType windowType = WindowType.Container;
/*     */   @Nullable
/*     */   public String windowData;
/*     */   @Nullable
/*     */   public InventorySection inventory;
/*     */   @Nullable
/*     */   public ExtraResources extraResources;
/*     */   
/*     */   public OpenWindow(int id, @Nonnull WindowType windowType, @Nullable String windowData, @Nullable InventorySection inventory, @Nullable ExtraResources extraResources) {
/*  39 */     this.id = id;
/*  40 */     this.windowType = windowType;
/*  41 */     this.windowData = windowData;
/*  42 */     this.inventory = inventory;
/*  43 */     this.extraResources = extraResources;
/*     */   }
/*     */   
/*     */   public OpenWindow(@Nonnull OpenWindow other) {
/*  47 */     this.id = other.id;
/*  48 */     this.windowType = other.windowType;
/*  49 */     this.windowData = other.windowData;
/*  50 */     this.inventory = other.inventory;
/*  51 */     this.extraResources = other.extraResources;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static OpenWindow deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     OpenWindow obj = new OpenWindow();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.id = buf.getIntLE(offset + 1);
/*  59 */     obj.windowType = WindowType.fromValue(buf.getByte(offset + 5));
/*     */     
/*  61 */     if ((nullBits & 0x1) != 0) {
/*  62 */       int varPos0 = offset + 18 + buf.getIntLE(offset + 6);
/*  63 */       int windowDataLen = VarInt.peek(buf, varPos0);
/*  64 */       if (windowDataLen < 0) throw ProtocolException.negativeLength("WindowData", windowDataLen); 
/*  65 */       if (windowDataLen > 4096000) throw ProtocolException.stringTooLong("WindowData", windowDataLen, 4096000); 
/*  66 */       obj.windowData = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int varPos1 = offset + 18 + buf.getIntLE(offset + 10);
/*  70 */       obj.inventory = InventorySection.deserialize(buf, varPos1);
/*     */     } 
/*  72 */     if ((nullBits & 0x4) != 0) {
/*  73 */       int varPos2 = offset + 18 + buf.getIntLE(offset + 14);
/*  74 */       obj.extraResources = ExtraResources.deserialize(buf, varPos2);
/*     */     } 
/*     */     
/*  77 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  81 */     byte nullBits = buf.getByte(offset);
/*  82 */     int maxEnd = 18;
/*  83 */     if ((nullBits & 0x1) != 0) {
/*  84 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/*  85 */       int pos0 = offset + 18 + fieldOffset0;
/*  86 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  87 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  89 */     if ((nullBits & 0x2) != 0) {
/*  90 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/*  91 */       int pos1 = offset + 18 + fieldOffset1;
/*  92 */       pos1 += InventorySection.computeBytesConsumed(buf, pos1);
/*  93 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  95 */     if ((nullBits & 0x4) != 0) {
/*  96 */       int fieldOffset2 = buf.getIntLE(offset + 14);
/*  97 */       int pos2 = offset + 18 + fieldOffset2;
/*  98 */       pos2 += ExtraResources.computeBytesConsumed(buf, pos2);
/*  99 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 101 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 107 */     int startPos = buf.writerIndex();
/* 108 */     byte nullBits = 0;
/* 109 */     if (this.windowData != null) nullBits = (byte)(nullBits | 0x1); 
/* 110 */     if (this.inventory != null) nullBits = (byte)(nullBits | 0x2); 
/* 111 */     if (this.extraResources != null) nullBits = (byte)(nullBits | 0x4); 
/* 112 */     buf.writeByte(nullBits);
/*     */     
/* 114 */     buf.writeIntLE(this.id);
/* 115 */     buf.writeByte(this.windowType.getValue());
/*     */     
/* 117 */     int windowDataOffsetSlot = buf.writerIndex();
/* 118 */     buf.writeIntLE(0);
/* 119 */     int inventoryOffsetSlot = buf.writerIndex();
/* 120 */     buf.writeIntLE(0);
/* 121 */     int extraResourcesOffsetSlot = buf.writerIndex();
/* 122 */     buf.writeIntLE(0);
/*     */     
/* 124 */     int varBlockStart = buf.writerIndex();
/* 125 */     if (this.windowData != null) {
/* 126 */       buf.setIntLE(windowDataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 127 */       PacketIO.writeVarString(buf, this.windowData, 4096000);
/*     */     } else {
/* 129 */       buf.setIntLE(windowDataOffsetSlot, -1);
/*     */     } 
/* 131 */     if (this.inventory != null) {
/* 132 */       buf.setIntLE(inventoryOffsetSlot, buf.writerIndex() - varBlockStart);
/* 133 */       this.inventory.serialize(buf);
/*     */     } else {
/* 135 */       buf.setIntLE(inventoryOffsetSlot, -1);
/*     */     } 
/* 137 */     if (this.extraResources != null) {
/* 138 */       buf.setIntLE(extraResourcesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 139 */       this.extraResources.serialize(buf);
/*     */     } else {
/* 141 */       buf.setIntLE(extraResourcesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 147 */     int size = 18;
/* 148 */     if (this.windowData != null) size += PacketIO.stringSize(this.windowData); 
/* 149 */     if (this.inventory != null) size += this.inventory.computeSize(); 
/* 150 */     if (this.extraResources != null) size += this.extraResources.computeSize();
/*     */     
/* 152 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 156 */     if (buffer.readableBytes() - offset < 18) {
/* 157 */       return ValidationResult.error("Buffer too small: expected at least 18 bytes");
/*     */     }
/*     */     
/* 160 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 163 */     if ((nullBits & 0x1) != 0) {
/* 164 */       int windowDataOffset = buffer.getIntLE(offset + 6);
/* 165 */       if (windowDataOffset < 0) {
/* 166 */         return ValidationResult.error("Invalid offset for WindowData");
/*     */       }
/* 168 */       int pos = offset + 18 + windowDataOffset;
/* 169 */       if (pos >= buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Offset out of bounds for WindowData");
/*     */       }
/* 172 */       int windowDataLen = VarInt.peek(buffer, pos);
/* 173 */       if (windowDataLen < 0) {
/* 174 */         return ValidationResult.error("Invalid string length for WindowData");
/*     */       }
/* 176 */       if (windowDataLen > 4096000) {
/* 177 */         return ValidationResult.error("WindowData exceeds max length 4096000");
/*     */       }
/* 179 */       pos += VarInt.length(buffer, pos);
/* 180 */       pos += windowDataLen;
/* 181 */       if (pos > buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Buffer overflow reading WindowData");
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if ((nullBits & 0x2) != 0) {
/* 187 */       int inventoryOffset = buffer.getIntLE(offset + 10);
/* 188 */       if (inventoryOffset < 0) {
/* 189 */         return ValidationResult.error("Invalid offset for Inventory");
/*     */       }
/* 191 */       int pos = offset + 18 + inventoryOffset;
/* 192 */       if (pos >= buffer.writerIndex()) {
/* 193 */         return ValidationResult.error("Offset out of bounds for Inventory");
/*     */       }
/* 195 */       ValidationResult inventoryResult = InventorySection.validateStructure(buffer, pos);
/* 196 */       if (!inventoryResult.isValid()) {
/* 197 */         return ValidationResult.error("Invalid Inventory: " + inventoryResult.error());
/*     */       }
/* 199 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 202 */     if ((nullBits & 0x4) != 0) {
/* 203 */       int extraResourcesOffset = buffer.getIntLE(offset + 14);
/* 204 */       if (extraResourcesOffset < 0) {
/* 205 */         return ValidationResult.error("Invalid offset for ExtraResources");
/*     */       }
/* 207 */       int pos = offset + 18 + extraResourcesOffset;
/* 208 */       if (pos >= buffer.writerIndex()) {
/* 209 */         return ValidationResult.error("Offset out of bounds for ExtraResources");
/*     */       }
/* 211 */       ValidationResult extraResourcesResult = ExtraResources.validateStructure(buffer, pos);
/* 212 */       if (!extraResourcesResult.isValid()) {
/* 213 */         return ValidationResult.error("Invalid ExtraResources: " + extraResourcesResult.error());
/*     */       }
/* 215 */       pos += ExtraResources.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 217 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public OpenWindow clone() {
/* 221 */     OpenWindow copy = new OpenWindow();
/* 222 */     copy.id = this.id;
/* 223 */     copy.windowType = this.windowType;
/* 224 */     copy.windowData = this.windowData;
/* 225 */     copy.inventory = (this.inventory != null) ? this.inventory.clone() : null;
/* 226 */     copy.extraResources = (this.extraResources != null) ? this.extraResources.clone() : null;
/* 227 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     OpenWindow other;
/* 233 */     if (this == obj) return true; 
/* 234 */     if (obj instanceof OpenWindow) { other = (OpenWindow)obj; } else { return false; }
/* 235 */      return (this.id == other.id && Objects.equals(this.windowType, other.windowType) && Objects.equals(this.windowData, other.windowData) && Objects.equals(this.inventory, other.inventory) && Objects.equals(this.extraResources, other.extraResources));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 240 */     return Objects.hash(new Object[] { Integer.valueOf(this.id), this.windowType, this.windowData, this.inventory, this.extraResources });
/*     */   }
/*     */   
/*     */   public OpenWindow() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\OpenWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */