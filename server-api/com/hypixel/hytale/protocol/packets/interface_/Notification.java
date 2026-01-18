/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.FormattedMessage;
/*     */ import com.hypixel.hytale.protocol.ItemWithAllMetadata;
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
/*     */ public class Notification
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 212;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   
/*     */   public int getId() {
/*  26 */     return 212;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 18; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public FormattedMessage message; @Nullable
/*     */   public FormattedMessage secondaryMessage; @Nullable
/*     */   public String icon; @Nullable
/*     */   public ItemWithAllMetadata item; @Nonnull
/*  33 */   public NotificationStyle style = NotificationStyle.Default;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notification(@Nullable FormattedMessage message, @Nullable FormattedMessage secondaryMessage, @Nullable String icon, @Nullable ItemWithAllMetadata item, @Nonnull NotificationStyle style) {
/*  39 */     this.message = message;
/*  40 */     this.secondaryMessage = secondaryMessage;
/*  41 */     this.icon = icon;
/*  42 */     this.item = item;
/*  43 */     this.style = style;
/*     */   }
/*     */   
/*     */   public Notification(@Nonnull Notification other) {
/*  47 */     this.message = other.message;
/*  48 */     this.secondaryMessage = other.secondaryMessage;
/*  49 */     this.icon = other.icon;
/*  50 */     this.item = other.item;
/*  51 */     this.style = other.style;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Notification deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     Notification obj = new Notification();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.style = NotificationStyle.fromValue(buf.getByte(offset + 1));
/*     */     
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int varPos0 = offset + 18 + buf.getIntLE(offset + 2);
/*  62 */       obj.message = FormattedMessage.deserialize(buf, varPos0);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 18 + buf.getIntLE(offset + 6);
/*  66 */       obj.secondaryMessage = FormattedMessage.deserialize(buf, varPos1);
/*     */     } 
/*  68 */     if ((nullBits & 0x4) != 0) {
/*  69 */       int varPos2 = offset + 18 + buf.getIntLE(offset + 10);
/*  70 */       int iconLen = VarInt.peek(buf, varPos2);
/*  71 */       if (iconLen < 0) throw ProtocolException.negativeLength("Icon", iconLen); 
/*  72 */       if (iconLen > 4096000) throw ProtocolException.stringTooLong("Icon", iconLen, 4096000); 
/*  73 */       obj.icon = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  75 */     if ((nullBits & 0x8) != 0) {
/*  76 */       int varPos3 = offset + 18 + buf.getIntLE(offset + 14);
/*  77 */       obj.item = ItemWithAllMetadata.deserialize(buf, varPos3);
/*     */     } 
/*     */     
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     byte nullBits = buf.getByte(offset);
/*  85 */     int maxEnd = 18;
/*  86 */     if ((nullBits & 0x1) != 0) {
/*  87 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  88 */       int pos0 = offset + 18 + fieldOffset0;
/*  89 */       pos0 += FormattedMessage.computeBytesConsumed(buf, pos0);
/*  90 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  92 */     if ((nullBits & 0x2) != 0) {
/*  93 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  94 */       int pos1 = offset + 18 + fieldOffset1;
/*  95 */       pos1 += FormattedMessage.computeBytesConsumed(buf, pos1);
/*  96 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  98 */     if ((nullBits & 0x4) != 0) {
/*  99 */       int fieldOffset2 = buf.getIntLE(offset + 10);
/* 100 */       int pos2 = offset + 18 + fieldOffset2;
/* 101 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 102 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 104 */     if ((nullBits & 0x8) != 0) {
/* 105 */       int fieldOffset3 = buf.getIntLE(offset + 14);
/* 106 */       int pos3 = offset + 18 + fieldOffset3;
/* 107 */       pos3 += ItemWithAllMetadata.computeBytesConsumed(buf, pos3);
/* 108 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 110 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 116 */     int startPos = buf.writerIndex();
/* 117 */     byte nullBits = 0;
/* 118 */     if (this.message != null) nullBits = (byte)(nullBits | 0x1); 
/* 119 */     if (this.secondaryMessage != null) nullBits = (byte)(nullBits | 0x2); 
/* 120 */     if (this.icon != null) nullBits = (byte)(nullBits | 0x4); 
/* 121 */     if (this.item != null) nullBits = (byte)(nullBits | 0x8); 
/* 122 */     buf.writeByte(nullBits);
/*     */     
/* 124 */     buf.writeByte(this.style.getValue());
/*     */     
/* 126 */     int messageOffsetSlot = buf.writerIndex();
/* 127 */     buf.writeIntLE(0);
/* 128 */     int secondaryMessageOffsetSlot = buf.writerIndex();
/* 129 */     buf.writeIntLE(0);
/* 130 */     int iconOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int itemOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/*     */     
/* 135 */     int varBlockStart = buf.writerIndex();
/* 136 */     if (this.message != null) {
/* 137 */       buf.setIntLE(messageOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       this.message.serialize(buf);
/*     */     } else {
/* 140 */       buf.setIntLE(messageOffsetSlot, -1);
/*     */     } 
/* 142 */     if (this.secondaryMessage != null) {
/* 143 */       buf.setIntLE(secondaryMessageOffsetSlot, buf.writerIndex() - varBlockStart);
/* 144 */       this.secondaryMessage.serialize(buf);
/*     */     } else {
/* 146 */       buf.setIntLE(secondaryMessageOffsetSlot, -1);
/*     */     } 
/* 148 */     if (this.icon != null) {
/* 149 */       buf.setIntLE(iconOffsetSlot, buf.writerIndex() - varBlockStart);
/* 150 */       PacketIO.writeVarString(buf, this.icon, 4096000);
/*     */     } else {
/* 152 */       buf.setIntLE(iconOffsetSlot, -1);
/*     */     } 
/* 154 */     if (this.item != null) {
/* 155 */       buf.setIntLE(itemOffsetSlot, buf.writerIndex() - varBlockStart);
/* 156 */       this.item.serialize(buf);
/*     */     } else {
/* 158 */       buf.setIntLE(itemOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 164 */     int size = 18;
/* 165 */     if (this.message != null) size += this.message.computeSize(); 
/* 166 */     if (this.secondaryMessage != null) size += this.secondaryMessage.computeSize(); 
/* 167 */     if (this.icon != null) size += PacketIO.stringSize(this.icon); 
/* 168 */     if (this.item != null) size += this.item.computeSize();
/*     */     
/* 170 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 174 */     if (buffer.readableBytes() - offset < 18) {
/* 175 */       return ValidationResult.error("Buffer too small: expected at least 18 bytes");
/*     */     }
/*     */     
/* 178 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 181 */     if ((nullBits & 0x1) != 0) {
/* 182 */       int messageOffset = buffer.getIntLE(offset + 2);
/* 183 */       if (messageOffset < 0) {
/* 184 */         return ValidationResult.error("Invalid offset for Message");
/*     */       }
/* 186 */       int pos = offset + 18 + messageOffset;
/* 187 */       if (pos >= buffer.writerIndex()) {
/* 188 */         return ValidationResult.error("Offset out of bounds for Message");
/*     */       }
/* 190 */       ValidationResult messageResult = FormattedMessage.validateStructure(buffer, pos);
/* 191 */       if (!messageResult.isValid()) {
/* 192 */         return ValidationResult.error("Invalid Message: " + messageResult.error());
/*     */       }
/* 194 */       pos += FormattedMessage.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 197 */     if ((nullBits & 0x2) != 0) {
/* 198 */       int secondaryMessageOffset = buffer.getIntLE(offset + 6);
/* 199 */       if (secondaryMessageOffset < 0) {
/* 200 */         return ValidationResult.error("Invalid offset for SecondaryMessage");
/*     */       }
/* 202 */       int pos = offset + 18 + secondaryMessageOffset;
/* 203 */       if (pos >= buffer.writerIndex()) {
/* 204 */         return ValidationResult.error("Offset out of bounds for SecondaryMessage");
/*     */       }
/* 206 */       ValidationResult secondaryMessageResult = FormattedMessage.validateStructure(buffer, pos);
/* 207 */       if (!secondaryMessageResult.isValid()) {
/* 208 */         return ValidationResult.error("Invalid SecondaryMessage: " + secondaryMessageResult.error());
/*     */       }
/* 210 */       pos += FormattedMessage.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 213 */     if ((nullBits & 0x4) != 0) {
/* 214 */       int iconOffset = buffer.getIntLE(offset + 10);
/* 215 */       if (iconOffset < 0) {
/* 216 */         return ValidationResult.error("Invalid offset for Icon");
/*     */       }
/* 218 */       int pos = offset + 18 + iconOffset;
/* 219 */       if (pos >= buffer.writerIndex()) {
/* 220 */         return ValidationResult.error("Offset out of bounds for Icon");
/*     */       }
/* 222 */       int iconLen = VarInt.peek(buffer, pos);
/* 223 */       if (iconLen < 0) {
/* 224 */         return ValidationResult.error("Invalid string length for Icon");
/*     */       }
/* 226 */       if (iconLen > 4096000) {
/* 227 */         return ValidationResult.error("Icon exceeds max length 4096000");
/*     */       }
/* 229 */       pos += VarInt.length(buffer, pos);
/* 230 */       pos += iconLen;
/* 231 */       if (pos > buffer.writerIndex()) {
/* 232 */         return ValidationResult.error("Buffer overflow reading Icon");
/*     */       }
/*     */     } 
/*     */     
/* 236 */     if ((nullBits & 0x8) != 0) {
/* 237 */       int itemOffset = buffer.getIntLE(offset + 14);
/* 238 */       if (itemOffset < 0) {
/* 239 */         return ValidationResult.error("Invalid offset for Item");
/*     */       }
/* 241 */       int pos = offset + 18 + itemOffset;
/* 242 */       if (pos >= buffer.writerIndex()) {
/* 243 */         return ValidationResult.error("Offset out of bounds for Item");
/*     */       }
/* 245 */       ValidationResult itemResult = ItemWithAllMetadata.validateStructure(buffer, pos);
/* 246 */       if (!itemResult.isValid()) {
/* 247 */         return ValidationResult.error("Invalid Item: " + itemResult.error());
/*     */       }
/* 249 */       pos += ItemWithAllMetadata.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 251 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Notification clone() {
/* 255 */     Notification copy = new Notification();
/* 256 */     copy.message = (this.message != null) ? this.message.clone() : null;
/* 257 */     copy.secondaryMessage = (this.secondaryMessage != null) ? this.secondaryMessage.clone() : null;
/* 258 */     copy.icon = this.icon;
/* 259 */     copy.item = (this.item != null) ? this.item.clone() : null;
/* 260 */     copy.style = this.style;
/* 261 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Notification other;
/* 267 */     if (this == obj) return true; 
/* 268 */     if (obj instanceof Notification) { other = (Notification)obj; } else { return false; }
/* 269 */      return (Objects.equals(this.message, other.message) && Objects.equals(this.secondaryMessage, other.secondaryMessage) && Objects.equals(this.icon, other.icon) && Objects.equals(this.item, other.item) && Objects.equals(this.style, other.style));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 274 */     return Objects.hash(new Object[] { this.message, this.secondaryMessage, this.icon, this.item, this.style });
/*     */   }
/*     */   
/*     */   public Notification() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\Notification.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */