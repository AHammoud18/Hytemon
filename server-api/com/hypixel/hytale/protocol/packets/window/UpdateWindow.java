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
/*     */ 
/*     */ public class UpdateWindow
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 201;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  26 */     return 201;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int id;
/*     */ 
/*     */   
/*     */   public UpdateWindow(int id, @Nullable String windowData, @Nullable InventorySection inventory, @Nullable ExtraResources extraResources) {
/*  38 */     this.id = id;
/*  39 */     this.windowData = windowData;
/*  40 */     this.inventory = inventory;
/*  41 */     this.extraResources = extraResources; } @Nullable
/*     */   public String windowData; @Nullable
/*     */   public InventorySection inventory; @Nullable
/*     */   public ExtraResources extraResources; public UpdateWindow() {} public UpdateWindow(@Nonnull UpdateWindow other) {
/*  45 */     this.id = other.id;
/*  46 */     this.windowData = other.windowData;
/*  47 */     this.inventory = other.inventory;
/*  48 */     this.extraResources = other.extraResources;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateWindow deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     UpdateWindow obj = new UpdateWindow();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.id = buf.getIntLE(offset + 1);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 5);
/*  59 */       int windowDataLen = VarInt.peek(buf, varPos0);
/*  60 */       if (windowDataLen < 0) throw ProtocolException.negativeLength("WindowData", windowDataLen); 
/*  61 */       if (windowDataLen > 4096000) throw ProtocolException.stringTooLong("WindowData", windowDataLen, 4096000); 
/*  62 */       obj.windowData = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 9);
/*  66 */       obj.inventory = InventorySection.deserialize(buf, varPos1);
/*     */     } 
/*  68 */     if ((nullBits & 0x4) != 0) {
/*  69 */       int varPos2 = offset + 17 + buf.getIntLE(offset + 13);
/*  70 */       obj.extraResources = ExtraResources.deserialize(buf, varPos2);
/*     */     } 
/*     */     
/*  73 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  77 */     byte nullBits = buf.getByte(offset);
/*  78 */     int maxEnd = 17;
/*  79 */     if ((nullBits & 0x1) != 0) {
/*  80 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/*  81 */       int pos0 = offset + 17 + fieldOffset0;
/*  82 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  83 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  85 */     if ((nullBits & 0x2) != 0) {
/*  86 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/*  87 */       int pos1 = offset + 17 + fieldOffset1;
/*  88 */       pos1 += InventorySection.computeBytesConsumed(buf, pos1);
/*  89 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  91 */     if ((nullBits & 0x4) != 0) {
/*  92 */       int fieldOffset2 = buf.getIntLE(offset + 13);
/*  93 */       int pos2 = offset + 17 + fieldOffset2;
/*  94 */       pos2 += ExtraResources.computeBytesConsumed(buf, pos2);
/*  95 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  97 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 103 */     int startPos = buf.writerIndex();
/* 104 */     byte nullBits = 0;
/* 105 */     if (this.windowData != null) nullBits = (byte)(nullBits | 0x1); 
/* 106 */     if (this.inventory != null) nullBits = (byte)(nullBits | 0x2); 
/* 107 */     if (this.extraResources != null) nullBits = (byte)(nullBits | 0x4); 
/* 108 */     buf.writeByte(nullBits);
/*     */     
/* 110 */     buf.writeIntLE(this.id);
/*     */     
/* 112 */     int windowDataOffsetSlot = buf.writerIndex();
/* 113 */     buf.writeIntLE(0);
/* 114 */     int inventoryOffsetSlot = buf.writerIndex();
/* 115 */     buf.writeIntLE(0);
/* 116 */     int extraResourcesOffsetSlot = buf.writerIndex();
/* 117 */     buf.writeIntLE(0);
/*     */     
/* 119 */     int varBlockStart = buf.writerIndex();
/* 120 */     if (this.windowData != null) {
/* 121 */       buf.setIntLE(windowDataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       PacketIO.writeVarString(buf, this.windowData, 4096000);
/*     */     } else {
/* 124 */       buf.setIntLE(windowDataOffsetSlot, -1);
/*     */     } 
/* 126 */     if (this.inventory != null) {
/* 127 */       buf.setIntLE(inventoryOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       this.inventory.serialize(buf);
/*     */     } else {
/* 130 */       buf.setIntLE(inventoryOffsetSlot, -1);
/*     */     } 
/* 132 */     if (this.extraResources != null) {
/* 133 */       buf.setIntLE(extraResourcesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 134 */       this.extraResources.serialize(buf);
/*     */     } else {
/* 136 */       buf.setIntLE(extraResourcesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 142 */     int size = 17;
/* 143 */     if (this.windowData != null) size += PacketIO.stringSize(this.windowData); 
/* 144 */     if (this.inventory != null) size += this.inventory.computeSize(); 
/* 145 */     if (this.extraResources != null) size += this.extraResources.computeSize();
/*     */     
/* 147 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 151 */     if (buffer.readableBytes() - offset < 17) {
/* 152 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 155 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 158 */     if ((nullBits & 0x1) != 0) {
/* 159 */       int windowDataOffset = buffer.getIntLE(offset + 5);
/* 160 */       if (windowDataOffset < 0) {
/* 161 */         return ValidationResult.error("Invalid offset for WindowData");
/*     */       }
/* 163 */       int pos = offset + 17 + windowDataOffset;
/* 164 */       if (pos >= buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Offset out of bounds for WindowData");
/*     */       }
/* 167 */       int windowDataLen = VarInt.peek(buffer, pos);
/* 168 */       if (windowDataLen < 0) {
/* 169 */         return ValidationResult.error("Invalid string length for WindowData");
/*     */       }
/* 171 */       if (windowDataLen > 4096000) {
/* 172 */         return ValidationResult.error("WindowData exceeds max length 4096000");
/*     */       }
/* 174 */       pos += VarInt.length(buffer, pos);
/* 175 */       pos += windowDataLen;
/* 176 */       if (pos > buffer.writerIndex()) {
/* 177 */         return ValidationResult.error("Buffer overflow reading WindowData");
/*     */       }
/*     */     } 
/*     */     
/* 181 */     if ((nullBits & 0x2) != 0) {
/* 182 */       int inventoryOffset = buffer.getIntLE(offset + 9);
/* 183 */       if (inventoryOffset < 0) {
/* 184 */         return ValidationResult.error("Invalid offset for Inventory");
/*     */       }
/* 186 */       int pos = offset + 17 + inventoryOffset;
/* 187 */       if (pos >= buffer.writerIndex()) {
/* 188 */         return ValidationResult.error("Offset out of bounds for Inventory");
/*     */       }
/* 190 */       ValidationResult inventoryResult = InventorySection.validateStructure(buffer, pos);
/* 191 */       if (!inventoryResult.isValid()) {
/* 192 */         return ValidationResult.error("Invalid Inventory: " + inventoryResult.error());
/*     */       }
/* 194 */       pos += InventorySection.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 197 */     if ((nullBits & 0x4) != 0) {
/* 198 */       int extraResourcesOffset = buffer.getIntLE(offset + 13);
/* 199 */       if (extraResourcesOffset < 0) {
/* 200 */         return ValidationResult.error("Invalid offset for ExtraResources");
/*     */       }
/* 202 */       int pos = offset + 17 + extraResourcesOffset;
/* 203 */       if (pos >= buffer.writerIndex()) {
/* 204 */         return ValidationResult.error("Offset out of bounds for ExtraResources");
/*     */       }
/* 206 */       ValidationResult extraResourcesResult = ExtraResources.validateStructure(buffer, pos);
/* 207 */       if (!extraResourcesResult.isValid()) {
/* 208 */         return ValidationResult.error("Invalid ExtraResources: " + extraResourcesResult.error());
/*     */       }
/* 210 */       pos += ExtraResources.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 212 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateWindow clone() {
/* 216 */     UpdateWindow copy = new UpdateWindow();
/* 217 */     copy.id = this.id;
/* 218 */     copy.windowData = this.windowData;
/* 219 */     copy.inventory = (this.inventory != null) ? this.inventory.clone() : null;
/* 220 */     copy.extraResources = (this.extraResources != null) ? this.extraResources.clone() : null;
/* 221 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateWindow other;
/* 227 */     if (this == obj) return true; 
/* 228 */     if (obj instanceof UpdateWindow) { other = (UpdateWindow)obj; } else { return false; }
/* 229 */      return (this.id == other.id && Objects.equals(this.windowData, other.windowData) && Objects.equals(this.inventory, other.inventory) && Objects.equals(this.extraResources, other.extraResources));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 234 */     return Objects.hash(new Object[] { Integer.valueOf(this.id), this.windowData, this.inventory, this.extraResources });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\UpdateWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */