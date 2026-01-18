/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.MouseButtonEvent;
/*     */ import com.hypixel.hytale.protocol.MouseMotionEvent;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Vector2f;
/*     */ import com.hypixel.hytale.protocol.WorldInteraction;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class MouseInteraction
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 111;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 44;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 52;
/*     */   public static final int MAX_SIZE = 20480071;
/*     */   
/*     */   public int getId() {
/*  28 */     return 111;
/*     */   }
/*     */ 
/*     */   
/*     */   public long clientTimestamp;
/*     */   
/*     */   public int activeSlot;
/*     */   
/*     */   @Nullable
/*     */   public String itemInHandId;
/*     */   
/*     */   @Nullable
/*     */   public Vector2f screenPoint;
/*     */   
/*     */   public MouseInteraction(long clientTimestamp, int activeSlot, @Nullable String itemInHandId, @Nullable Vector2f screenPoint, @Nullable MouseButtonEvent mouseButton, @Nullable MouseMotionEvent mouseMotion, @Nullable WorldInteraction worldInteraction) {
/*  43 */     this.clientTimestamp = clientTimestamp;
/*  44 */     this.activeSlot = activeSlot;
/*  45 */     this.itemInHandId = itemInHandId;
/*  46 */     this.screenPoint = screenPoint;
/*  47 */     this.mouseButton = mouseButton;
/*  48 */     this.mouseMotion = mouseMotion;
/*  49 */     this.worldInteraction = worldInteraction; } @Nullable
/*     */   public MouseButtonEvent mouseButton; @Nullable
/*     */   public MouseMotionEvent mouseMotion; @Nullable
/*     */   public WorldInteraction worldInteraction; public MouseInteraction() {} public MouseInteraction(@Nonnull MouseInteraction other) {
/*  53 */     this.clientTimestamp = other.clientTimestamp;
/*  54 */     this.activeSlot = other.activeSlot;
/*  55 */     this.itemInHandId = other.itemInHandId;
/*  56 */     this.screenPoint = other.screenPoint;
/*  57 */     this.mouseButton = other.mouseButton;
/*  58 */     this.mouseMotion = other.mouseMotion;
/*  59 */     this.worldInteraction = other.worldInteraction;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MouseInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  64 */     MouseInteraction obj = new MouseInteraction();
/*  65 */     byte nullBits = buf.getByte(offset);
/*  66 */     obj.clientTimestamp = buf.getLongLE(offset + 1);
/*  67 */     obj.activeSlot = buf.getIntLE(offset + 9);
/*  68 */     if ((nullBits & 0x2) != 0) obj.screenPoint = Vector2f.deserialize(buf, offset + 13); 
/*  69 */     if ((nullBits & 0x4) != 0) obj.mouseButton = MouseButtonEvent.deserialize(buf, offset + 21); 
/*  70 */     if ((nullBits & 0x10) != 0) obj.worldInteraction = WorldInteraction.deserialize(buf, offset + 24);
/*     */     
/*  72 */     if ((nullBits & 0x1) != 0) {
/*  73 */       int varPos0 = offset + 52 + buf.getIntLE(offset + 44);
/*  74 */       int itemInHandIdLen = VarInt.peek(buf, varPos0);
/*  75 */       if (itemInHandIdLen < 0) throw ProtocolException.negativeLength("ItemInHandId", itemInHandIdLen); 
/*  76 */       if (itemInHandIdLen > 4096000) throw ProtocolException.stringTooLong("ItemInHandId", itemInHandIdLen, 4096000); 
/*  77 */       obj.itemInHandId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  79 */     if ((nullBits & 0x8) != 0) {
/*  80 */       int varPos1 = offset + 52 + buf.getIntLE(offset + 48);
/*  81 */       obj.mouseMotion = MouseMotionEvent.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  84 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  88 */     byte nullBits = buf.getByte(offset);
/*  89 */     int maxEnd = 52;
/*  90 */     if ((nullBits & 0x1) != 0) {
/*  91 */       int fieldOffset0 = buf.getIntLE(offset + 44);
/*  92 */       int pos0 = offset + 52 + fieldOffset0;
/*  93 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  94 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  96 */     if ((nullBits & 0x8) != 0) {
/*  97 */       int fieldOffset1 = buf.getIntLE(offset + 48);
/*  98 */       int pos1 = offset + 52 + fieldOffset1;
/*  99 */       pos1 += MouseMotionEvent.computeBytesConsumed(buf, pos1);
/* 100 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 102 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 108 */     int startPos = buf.writerIndex();
/* 109 */     byte nullBits = 0;
/* 110 */     if (this.itemInHandId != null) nullBits = (byte)(nullBits | 0x1); 
/* 111 */     if (this.screenPoint != null) nullBits = (byte)(nullBits | 0x2); 
/* 112 */     if (this.mouseButton != null) nullBits = (byte)(nullBits | 0x4); 
/* 113 */     if (this.mouseMotion != null) nullBits = (byte)(nullBits | 0x8); 
/* 114 */     if (this.worldInteraction != null) nullBits = (byte)(nullBits | 0x10); 
/* 115 */     buf.writeByte(nullBits);
/*     */     
/* 117 */     buf.writeLongLE(this.clientTimestamp);
/* 118 */     buf.writeIntLE(this.activeSlot);
/* 119 */     if (this.screenPoint != null) { this.screenPoint.serialize(buf); } else { buf.writeZero(8); }
/* 120 */      if (this.mouseButton != null) { this.mouseButton.serialize(buf); } else { buf.writeZero(3); }
/* 121 */      if (this.worldInteraction != null) { this.worldInteraction.serialize(buf); } else { buf.writeZero(20); }
/*     */     
/* 123 */     int itemInHandIdOffsetSlot = buf.writerIndex();
/* 124 */     buf.writeIntLE(0);
/* 125 */     int mouseMotionOffsetSlot = buf.writerIndex();
/* 126 */     buf.writeIntLE(0);
/*     */     
/* 128 */     int varBlockStart = buf.writerIndex();
/* 129 */     if (this.itemInHandId != null) {
/* 130 */       buf.setIntLE(itemInHandIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 131 */       PacketIO.writeVarString(buf, this.itemInHandId, 4096000);
/*     */     } else {
/* 133 */       buf.setIntLE(itemInHandIdOffsetSlot, -1);
/*     */     } 
/* 135 */     if (this.mouseMotion != null) {
/* 136 */       buf.setIntLE(mouseMotionOffsetSlot, buf.writerIndex() - varBlockStart);
/* 137 */       this.mouseMotion.serialize(buf);
/*     */     } else {
/* 139 */       buf.setIntLE(mouseMotionOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 145 */     int size = 52;
/* 146 */     if (this.itemInHandId != null) size += PacketIO.stringSize(this.itemInHandId); 
/* 147 */     if (this.mouseMotion != null) size += this.mouseMotion.computeSize();
/*     */     
/* 149 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 153 */     if (buffer.readableBytes() - offset < 52) {
/* 154 */       return ValidationResult.error("Buffer too small: expected at least 52 bytes");
/*     */     }
/*     */     
/* 157 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 160 */     if ((nullBits & 0x1) != 0) {
/* 161 */       int itemInHandIdOffset = buffer.getIntLE(offset + 44);
/* 162 */       if (itemInHandIdOffset < 0) {
/* 163 */         return ValidationResult.error("Invalid offset for ItemInHandId");
/*     */       }
/* 165 */       int pos = offset + 52 + itemInHandIdOffset;
/* 166 */       if (pos >= buffer.writerIndex()) {
/* 167 */         return ValidationResult.error("Offset out of bounds for ItemInHandId");
/*     */       }
/* 169 */       int itemInHandIdLen = VarInt.peek(buffer, pos);
/* 170 */       if (itemInHandIdLen < 0) {
/* 171 */         return ValidationResult.error("Invalid string length for ItemInHandId");
/*     */       }
/* 173 */       if (itemInHandIdLen > 4096000) {
/* 174 */         return ValidationResult.error("ItemInHandId exceeds max length 4096000");
/*     */       }
/* 176 */       pos += VarInt.length(buffer, pos);
/* 177 */       pos += itemInHandIdLen;
/* 178 */       if (pos > buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Buffer overflow reading ItemInHandId");
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if ((nullBits & 0x8) != 0) {
/* 184 */       int mouseMotionOffset = buffer.getIntLE(offset + 48);
/* 185 */       if (mouseMotionOffset < 0) {
/* 186 */         return ValidationResult.error("Invalid offset for MouseMotion");
/*     */       }
/* 188 */       int pos = offset + 52 + mouseMotionOffset;
/* 189 */       if (pos >= buffer.writerIndex()) {
/* 190 */         return ValidationResult.error("Offset out of bounds for MouseMotion");
/*     */       }
/* 192 */       ValidationResult mouseMotionResult = MouseMotionEvent.validateStructure(buffer, pos);
/* 193 */       if (!mouseMotionResult.isValid()) {
/* 194 */         return ValidationResult.error("Invalid MouseMotion: " + mouseMotionResult.error());
/*     */       }
/* 196 */       pos += MouseMotionEvent.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 198 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MouseInteraction clone() {
/* 202 */     MouseInteraction copy = new MouseInteraction();
/* 203 */     copy.clientTimestamp = this.clientTimestamp;
/* 204 */     copy.activeSlot = this.activeSlot;
/* 205 */     copy.itemInHandId = this.itemInHandId;
/* 206 */     copy.screenPoint = (this.screenPoint != null) ? this.screenPoint.clone() : null;
/* 207 */     copy.mouseButton = (this.mouseButton != null) ? this.mouseButton.clone() : null;
/* 208 */     copy.mouseMotion = (this.mouseMotion != null) ? this.mouseMotion.clone() : null;
/* 209 */     copy.worldInteraction = (this.worldInteraction != null) ? this.worldInteraction.clone() : null;
/* 210 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MouseInteraction other;
/* 216 */     if (this == obj) return true; 
/* 217 */     if (obj instanceof MouseInteraction) { other = (MouseInteraction)obj; } else { return false; }
/* 218 */      return (this.clientTimestamp == other.clientTimestamp && this.activeSlot == other.activeSlot && Objects.equals(this.itemInHandId, other.itemInHandId) && Objects.equals(this.screenPoint, other.screenPoint) && Objects.equals(this.mouseButton, other.mouseButton) && Objects.equals(this.mouseMotion, other.mouseMotion) && Objects.equals(this.worldInteraction, other.worldInteraction));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 223 */     return Objects.hash(new Object[] { Long.valueOf(this.clientTimestamp), Integer.valueOf(this.activeSlot), this.itemInHandId, this.screenPoint, this.mouseButton, this.mouseMotion, this.worldInteraction });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\MouseInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */