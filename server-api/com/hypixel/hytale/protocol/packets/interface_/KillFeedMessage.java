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
/*     */ 
/*     */ public class KillFeedMessage
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 213;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 213;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public FormattedMessage killer;
/*     */   
/*     */   public KillFeedMessage(@Nullable FormattedMessage killer, @Nullable FormattedMessage decedent, @Nullable String icon) {
/*  36 */     this.killer = killer;
/*  37 */     this.decedent = decedent;
/*  38 */     this.icon = icon;
/*     */   } @Nullable
/*     */   public FormattedMessage decedent; @Nullable
/*     */   public String icon; public KillFeedMessage() {} public KillFeedMessage(@Nonnull KillFeedMessage other) {
/*  42 */     this.killer = other.killer;
/*  43 */     this.decedent = other.decedent;
/*  44 */     this.icon = other.icon;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static KillFeedMessage deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     KillFeedMessage obj = new KillFeedMessage();
/*  50 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  52 */     if ((nullBits & 0x1) != 0) {
/*  53 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  54 */       obj.killer = FormattedMessage.deserialize(buf, varPos0);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  58 */       obj.decedent = FormattedMessage.deserialize(buf, varPos1);
/*     */     } 
/*  60 */     if ((nullBits & 0x4) != 0) {
/*  61 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  62 */       int iconLen = VarInt.peek(buf, varPos2);
/*  63 */       if (iconLen < 0) throw ProtocolException.negativeLength("Icon", iconLen); 
/*  64 */       if (iconLen > 4096000) throw ProtocolException.stringTooLong("Icon", iconLen, 4096000); 
/*  65 */       obj.icon = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 13;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  76 */       int pos0 = offset + 13 + fieldOffset0;
/*  77 */       pos0 += FormattedMessage.computeBytesConsumed(buf, pos0);
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  82 */       int pos1 = offset + 13 + fieldOffset1;
/*  83 */       pos1 += FormattedMessage.computeBytesConsumed(buf, pos1);
/*  84 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  86 */     if ((nullBits & 0x4) != 0) {
/*  87 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/*  88 */       int pos2 = offset + 13 + fieldOffset2;
/*  89 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  90 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  92 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  98 */     int startPos = buf.writerIndex();
/*  99 */     byte nullBits = 0;
/* 100 */     if (this.killer != null) nullBits = (byte)(nullBits | 0x1); 
/* 101 */     if (this.decedent != null) nullBits = (byte)(nullBits | 0x2); 
/* 102 */     if (this.icon != null) nullBits = (byte)(nullBits | 0x4); 
/* 103 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 106 */     int killerOffsetSlot = buf.writerIndex();
/* 107 */     buf.writeIntLE(0);
/* 108 */     int decedentOffsetSlot = buf.writerIndex();
/* 109 */     buf.writeIntLE(0);
/* 110 */     int iconOffsetSlot = buf.writerIndex();
/* 111 */     buf.writeIntLE(0);
/*     */     
/* 113 */     int varBlockStart = buf.writerIndex();
/* 114 */     if (this.killer != null) {
/* 115 */       buf.setIntLE(killerOffsetSlot, buf.writerIndex() - varBlockStart);
/* 116 */       this.killer.serialize(buf);
/*     */     } else {
/* 118 */       buf.setIntLE(killerOffsetSlot, -1);
/*     */     } 
/* 120 */     if (this.decedent != null) {
/* 121 */       buf.setIntLE(decedentOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       this.decedent.serialize(buf);
/*     */     } else {
/* 124 */       buf.setIntLE(decedentOffsetSlot, -1);
/*     */     } 
/* 126 */     if (this.icon != null) {
/* 127 */       buf.setIntLE(iconOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       PacketIO.writeVarString(buf, this.icon, 4096000);
/*     */     } else {
/* 130 */       buf.setIntLE(iconOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 136 */     int size = 13;
/* 137 */     if (this.killer != null) size += this.killer.computeSize(); 
/* 138 */     if (this.decedent != null) size += this.decedent.computeSize(); 
/* 139 */     if (this.icon != null) size += PacketIO.stringSize(this.icon);
/*     */     
/* 141 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 145 */     if (buffer.readableBytes() - offset < 13) {
/* 146 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 149 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 152 */     if ((nullBits & 0x1) != 0) {
/* 153 */       int killerOffset = buffer.getIntLE(offset + 1);
/* 154 */       if (killerOffset < 0) {
/* 155 */         return ValidationResult.error("Invalid offset for Killer");
/*     */       }
/* 157 */       int pos = offset + 13 + killerOffset;
/* 158 */       if (pos >= buffer.writerIndex()) {
/* 159 */         return ValidationResult.error("Offset out of bounds for Killer");
/*     */       }
/* 161 */       ValidationResult killerResult = FormattedMessage.validateStructure(buffer, pos);
/* 162 */       if (!killerResult.isValid()) {
/* 163 */         return ValidationResult.error("Invalid Killer: " + killerResult.error());
/*     */       }
/* 165 */       pos += FormattedMessage.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 168 */     if ((nullBits & 0x2) != 0) {
/* 169 */       int decedentOffset = buffer.getIntLE(offset + 5);
/* 170 */       if (decedentOffset < 0) {
/* 171 */         return ValidationResult.error("Invalid offset for Decedent");
/*     */       }
/* 173 */       int pos = offset + 13 + decedentOffset;
/* 174 */       if (pos >= buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Offset out of bounds for Decedent");
/*     */       }
/* 177 */       ValidationResult decedentResult = FormattedMessage.validateStructure(buffer, pos);
/* 178 */       if (!decedentResult.isValid()) {
/* 179 */         return ValidationResult.error("Invalid Decedent: " + decedentResult.error());
/*     */       }
/* 181 */       pos += FormattedMessage.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 184 */     if ((nullBits & 0x4) != 0) {
/* 185 */       int iconOffset = buffer.getIntLE(offset + 9);
/* 186 */       if (iconOffset < 0) {
/* 187 */         return ValidationResult.error("Invalid offset for Icon");
/*     */       }
/* 189 */       int pos = offset + 13 + iconOffset;
/* 190 */       if (pos >= buffer.writerIndex()) {
/* 191 */         return ValidationResult.error("Offset out of bounds for Icon");
/*     */       }
/* 193 */       int iconLen = VarInt.peek(buffer, pos);
/* 194 */       if (iconLen < 0) {
/* 195 */         return ValidationResult.error("Invalid string length for Icon");
/*     */       }
/* 197 */       if (iconLen > 4096000) {
/* 198 */         return ValidationResult.error("Icon exceeds max length 4096000");
/*     */       }
/* 200 */       pos += VarInt.length(buffer, pos);
/* 201 */       pos += iconLen;
/* 202 */       if (pos > buffer.writerIndex()) {
/* 203 */         return ValidationResult.error("Buffer overflow reading Icon");
/*     */       }
/*     */     } 
/* 206 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public KillFeedMessage clone() {
/* 210 */     KillFeedMessage copy = new KillFeedMessage();
/* 211 */     copy.killer = (this.killer != null) ? this.killer.clone() : null;
/* 212 */     copy.decedent = (this.decedent != null) ? this.decedent.clone() : null;
/* 213 */     copy.icon = this.icon;
/* 214 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     KillFeedMessage other;
/* 220 */     if (this == obj) return true; 
/* 221 */     if (obj instanceof KillFeedMessage) { other = (KillFeedMessage)obj; } else { return false; }
/* 222 */      return (Objects.equals(this.killer, other.killer) && Objects.equals(this.decedent, other.decedent) && Objects.equals(this.icon, other.icon));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 227 */     return Objects.hash(new Object[] { this.killer, this.decedent, this.icon });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\KillFeedMessage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */