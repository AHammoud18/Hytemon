/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.FormattedMessage;
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
/*     */ public class ShowEventTitle
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 214;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 14;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 26;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  25 */     return 214;
/*     */   }
/*     */ 
/*     */   
/*     */   public float fadeInDuration;
/*     */   
/*     */   public float fadeOutDuration;
/*     */   
/*     */   public float duration;
/*     */   
/*     */   @Nullable
/*     */   public String icon;
/*     */ 
/*     */   
/*     */   public ShowEventTitle(float fadeInDuration, float fadeOutDuration, float duration, @Nullable String icon, boolean isMajor, @Nullable FormattedMessage primaryTitle, @Nullable FormattedMessage secondaryTitle) {
/*  40 */     this.fadeInDuration = fadeInDuration;
/*  41 */     this.fadeOutDuration = fadeOutDuration;
/*  42 */     this.duration = duration;
/*  43 */     this.icon = icon;
/*  44 */     this.isMajor = isMajor;
/*  45 */     this.primaryTitle = primaryTitle;
/*  46 */     this.secondaryTitle = secondaryTitle;
/*     */   } public boolean isMajor; @Nullable
/*     */   public FormattedMessage primaryTitle; @Nullable
/*     */   public FormattedMessage secondaryTitle; public ShowEventTitle() {} public ShowEventTitle(@Nonnull ShowEventTitle other) {
/*  50 */     this.fadeInDuration = other.fadeInDuration;
/*  51 */     this.fadeOutDuration = other.fadeOutDuration;
/*  52 */     this.duration = other.duration;
/*  53 */     this.icon = other.icon;
/*  54 */     this.isMajor = other.isMajor;
/*  55 */     this.primaryTitle = other.primaryTitle;
/*  56 */     this.secondaryTitle = other.secondaryTitle;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ShowEventTitle deserialize(@Nonnull ByteBuf buf, int offset) {
/*  61 */     ShowEventTitle obj = new ShowEventTitle();
/*  62 */     byte nullBits = buf.getByte(offset);
/*  63 */     obj.fadeInDuration = buf.getFloatLE(offset + 1);
/*  64 */     obj.fadeOutDuration = buf.getFloatLE(offset + 5);
/*  65 */     obj.duration = buf.getFloatLE(offset + 9);
/*  66 */     obj.isMajor = (buf.getByte(offset + 13) != 0);
/*     */     
/*  68 */     if ((nullBits & 0x1) != 0) {
/*  69 */       int varPos0 = offset + 26 + buf.getIntLE(offset + 14);
/*  70 */       int iconLen = VarInt.peek(buf, varPos0);
/*  71 */       if (iconLen < 0) throw ProtocolException.negativeLength("Icon", iconLen); 
/*  72 */       if (iconLen > 4096000) throw ProtocolException.stringTooLong("Icon", iconLen, 4096000); 
/*  73 */       obj.icon = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  75 */     if ((nullBits & 0x2) != 0) {
/*  76 */       int varPos1 = offset + 26 + buf.getIntLE(offset + 18);
/*  77 */       obj.primaryTitle = FormattedMessage.deserialize(buf, varPos1);
/*     */     } 
/*  79 */     if ((nullBits & 0x4) != 0) {
/*  80 */       int varPos2 = offset + 26 + buf.getIntLE(offset + 22);
/*  81 */       obj.secondaryTitle = FormattedMessage.deserialize(buf, varPos2);
/*     */     } 
/*     */     
/*  84 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  88 */     byte nullBits = buf.getByte(offset);
/*  89 */     int maxEnd = 26;
/*  90 */     if ((nullBits & 0x1) != 0) {
/*  91 */       int fieldOffset0 = buf.getIntLE(offset + 14);
/*  92 */       int pos0 = offset + 26 + fieldOffset0;
/*  93 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  94 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  96 */     if ((nullBits & 0x2) != 0) {
/*  97 */       int fieldOffset1 = buf.getIntLE(offset + 18);
/*  98 */       int pos1 = offset + 26 + fieldOffset1;
/*  99 */       pos1 += FormattedMessage.computeBytesConsumed(buf, pos1);
/* 100 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 102 */     if ((nullBits & 0x4) != 0) {
/* 103 */       int fieldOffset2 = buf.getIntLE(offset + 22);
/* 104 */       int pos2 = offset + 26 + fieldOffset2;
/* 105 */       pos2 += FormattedMessage.computeBytesConsumed(buf, pos2);
/* 106 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 108 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 114 */     int startPos = buf.writerIndex();
/* 115 */     byte nullBits = 0;
/* 116 */     if (this.icon != null) nullBits = (byte)(nullBits | 0x1); 
/* 117 */     if (this.primaryTitle != null) nullBits = (byte)(nullBits | 0x2); 
/* 118 */     if (this.secondaryTitle != null) nullBits = (byte)(nullBits | 0x4); 
/* 119 */     buf.writeByte(nullBits);
/*     */     
/* 121 */     buf.writeFloatLE(this.fadeInDuration);
/* 122 */     buf.writeFloatLE(this.fadeOutDuration);
/* 123 */     buf.writeFloatLE(this.duration);
/* 124 */     buf.writeByte(this.isMajor ? 1 : 0);
/*     */     
/* 126 */     int iconOffsetSlot = buf.writerIndex();
/* 127 */     buf.writeIntLE(0);
/* 128 */     int primaryTitleOffsetSlot = buf.writerIndex();
/* 129 */     buf.writeIntLE(0);
/* 130 */     int secondaryTitleOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/*     */     
/* 133 */     int varBlockStart = buf.writerIndex();
/* 134 */     if (this.icon != null) {
/* 135 */       buf.setIntLE(iconOffsetSlot, buf.writerIndex() - varBlockStart);
/* 136 */       PacketIO.writeVarString(buf, this.icon, 4096000);
/*     */     } else {
/* 138 */       buf.setIntLE(iconOffsetSlot, -1);
/*     */     } 
/* 140 */     if (this.primaryTitle != null) {
/* 141 */       buf.setIntLE(primaryTitleOffsetSlot, buf.writerIndex() - varBlockStart);
/* 142 */       this.primaryTitle.serialize(buf);
/*     */     } else {
/* 144 */       buf.setIntLE(primaryTitleOffsetSlot, -1);
/*     */     } 
/* 146 */     if (this.secondaryTitle != null) {
/* 147 */       buf.setIntLE(secondaryTitleOffsetSlot, buf.writerIndex() - varBlockStart);
/* 148 */       this.secondaryTitle.serialize(buf);
/*     */     } else {
/* 150 */       buf.setIntLE(secondaryTitleOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 156 */     int size = 26;
/* 157 */     if (this.icon != null) size += PacketIO.stringSize(this.icon); 
/* 158 */     if (this.primaryTitle != null) size += this.primaryTitle.computeSize(); 
/* 159 */     if (this.secondaryTitle != null) size += this.secondaryTitle.computeSize();
/*     */     
/* 161 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 165 */     if (buffer.readableBytes() - offset < 26) {
/* 166 */       return ValidationResult.error("Buffer too small: expected at least 26 bytes");
/*     */     }
/*     */     
/* 169 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 172 */     if ((nullBits & 0x1) != 0) {
/* 173 */       int iconOffset = buffer.getIntLE(offset + 14);
/* 174 */       if (iconOffset < 0) {
/* 175 */         return ValidationResult.error("Invalid offset for Icon");
/*     */       }
/* 177 */       int pos = offset + 26 + iconOffset;
/* 178 */       if (pos >= buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Offset out of bounds for Icon");
/*     */       }
/* 181 */       int iconLen = VarInt.peek(buffer, pos);
/* 182 */       if (iconLen < 0) {
/* 183 */         return ValidationResult.error("Invalid string length for Icon");
/*     */       }
/* 185 */       if (iconLen > 4096000) {
/* 186 */         return ValidationResult.error("Icon exceeds max length 4096000");
/*     */       }
/* 188 */       pos += VarInt.length(buffer, pos);
/* 189 */       pos += iconLen;
/* 190 */       if (pos > buffer.writerIndex()) {
/* 191 */         return ValidationResult.error("Buffer overflow reading Icon");
/*     */       }
/*     */     } 
/*     */     
/* 195 */     if ((nullBits & 0x2) != 0) {
/* 196 */       int primaryTitleOffset = buffer.getIntLE(offset + 18);
/* 197 */       if (primaryTitleOffset < 0) {
/* 198 */         return ValidationResult.error("Invalid offset for PrimaryTitle");
/*     */       }
/* 200 */       int pos = offset + 26 + primaryTitleOffset;
/* 201 */       if (pos >= buffer.writerIndex()) {
/* 202 */         return ValidationResult.error("Offset out of bounds for PrimaryTitle");
/*     */       }
/* 204 */       ValidationResult primaryTitleResult = FormattedMessage.validateStructure(buffer, pos);
/* 205 */       if (!primaryTitleResult.isValid()) {
/* 206 */         return ValidationResult.error("Invalid PrimaryTitle: " + primaryTitleResult.error());
/*     */       }
/* 208 */       pos += FormattedMessage.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 211 */     if ((nullBits & 0x4) != 0) {
/* 212 */       int secondaryTitleOffset = buffer.getIntLE(offset + 22);
/* 213 */       if (secondaryTitleOffset < 0) {
/* 214 */         return ValidationResult.error("Invalid offset for SecondaryTitle");
/*     */       }
/* 216 */       int pos = offset + 26 + secondaryTitleOffset;
/* 217 */       if (pos >= buffer.writerIndex()) {
/* 218 */         return ValidationResult.error("Offset out of bounds for SecondaryTitle");
/*     */       }
/* 220 */       ValidationResult secondaryTitleResult = FormattedMessage.validateStructure(buffer, pos);
/* 221 */       if (!secondaryTitleResult.isValid()) {
/* 222 */         return ValidationResult.error("Invalid SecondaryTitle: " + secondaryTitleResult.error());
/*     */       }
/* 224 */       pos += FormattedMessage.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 226 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ShowEventTitle clone() {
/* 230 */     ShowEventTitle copy = new ShowEventTitle();
/* 231 */     copy.fadeInDuration = this.fadeInDuration;
/* 232 */     copy.fadeOutDuration = this.fadeOutDuration;
/* 233 */     copy.duration = this.duration;
/* 234 */     copy.icon = this.icon;
/* 235 */     copy.isMajor = this.isMajor;
/* 236 */     copy.primaryTitle = (this.primaryTitle != null) ? this.primaryTitle.clone() : null;
/* 237 */     copy.secondaryTitle = (this.secondaryTitle != null) ? this.secondaryTitle.clone() : null;
/* 238 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ShowEventTitle other;
/* 244 */     if (this == obj) return true; 
/* 245 */     if (obj instanceof ShowEventTitle) { other = (ShowEventTitle)obj; } else { return false; }
/* 246 */      return (this.fadeInDuration == other.fadeInDuration && this.fadeOutDuration == other.fadeOutDuration && this.duration == other.duration && Objects.equals(this.icon, other.icon) && this.isMajor == other.isMajor && Objects.equals(this.primaryTitle, other.primaryTitle) && Objects.equals(this.secondaryTitle, other.secondaryTitle));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 251 */     return Objects.hash(new Object[] { Float.valueOf(this.fadeInDuration), Float.valueOf(this.fadeOutDuration), Float.valueOf(this.duration), this.icon, Boolean.valueOf(this.isMajor), this.primaryTitle, this.secondaryTitle });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\ShowEventTitle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */