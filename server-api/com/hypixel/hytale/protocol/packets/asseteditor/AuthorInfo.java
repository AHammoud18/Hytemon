/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
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
/*     */ public class AuthorInfo {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 49152028;
/*     */   @Nullable
/*     */   public String name;
/*     */   @Nullable
/*     */   public String email;
/*     */   @Nullable
/*     */   public String url;
/*     */   
/*     */   public AuthorInfo() {}
/*     */   
/*     */   public AuthorInfo(@Nullable String name, @Nullable String email, @Nullable String url) {
/*  28 */     this.name = name;
/*  29 */     this.email = email;
/*  30 */     this.url = url;
/*     */   }
/*     */   
/*     */   public AuthorInfo(@Nonnull AuthorInfo other) {
/*  34 */     this.name = other.name;
/*  35 */     this.email = other.email;
/*  36 */     this.url = other.url;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AuthorInfo deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     AuthorInfo obj = new AuthorInfo();
/*  42 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  44 */     if ((nullBits & 0x1) != 0) {
/*  45 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  46 */       int nameLen = VarInt.peek(buf, varPos0);
/*  47 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  48 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  49 */       obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  51 */     if ((nullBits & 0x2) != 0) {
/*  52 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  53 */       int emailLen = VarInt.peek(buf, varPos1);
/*  54 */       if (emailLen < 0) throw ProtocolException.negativeLength("Email", emailLen); 
/*  55 */       if (emailLen > 4096000) throw ProtocolException.stringTooLong("Email", emailLen, 4096000); 
/*  56 */       obj.email = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  58 */     if ((nullBits & 0x4) != 0) {
/*  59 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  60 */       int urlLen = VarInt.peek(buf, varPos2);
/*  61 */       if (urlLen < 0) throw ProtocolException.negativeLength("Url", urlLen); 
/*  62 */       if (urlLen > 4096000) throw ProtocolException.stringTooLong("Url", urlLen, 4096000); 
/*  63 */       obj.url = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  66 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     int maxEnd = 13;
/*  72 */     if ((nullBits & 0x1) != 0) {
/*  73 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  74 */       int pos0 = offset + 13 + fieldOffset0;
/*  75 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  76 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  78 */     if ((nullBits & 0x2) != 0) {
/*  79 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  80 */       int pos1 = offset + 13 + fieldOffset1;
/*  81 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  82 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  84 */     if ((nullBits & 0x4) != 0) {
/*  85 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/*  86 */       int pos2 = offset + 13 + fieldOffset2;
/*  87 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  88 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  90 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  95 */     int startPos = buf.writerIndex();
/*  96 */     byte nullBits = 0;
/*  97 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/*  98 */     if (this.email != null) nullBits = (byte)(nullBits | 0x2); 
/*  99 */     if (this.url != null) nullBits = (byte)(nullBits | 0x4); 
/* 100 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 103 */     int nameOffsetSlot = buf.writerIndex();
/* 104 */     buf.writeIntLE(0);
/* 105 */     int emailOffsetSlot = buf.writerIndex();
/* 106 */     buf.writeIntLE(0);
/* 107 */     int urlOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/*     */     
/* 110 */     int varBlockStart = buf.writerIndex();
/* 111 */     if (this.name != null) {
/* 112 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 113 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 115 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 117 */     if (this.email != null) {
/* 118 */       buf.setIntLE(emailOffsetSlot, buf.writerIndex() - varBlockStart);
/* 119 */       PacketIO.writeVarString(buf, this.email, 4096000);
/*     */     } else {
/* 121 */       buf.setIntLE(emailOffsetSlot, -1);
/*     */     } 
/* 123 */     if (this.url != null) {
/* 124 */       buf.setIntLE(urlOffsetSlot, buf.writerIndex() - varBlockStart);
/* 125 */       PacketIO.writeVarString(buf, this.url, 4096000);
/*     */     } else {
/* 127 */       buf.setIntLE(urlOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 133 */     int size = 13;
/* 134 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 135 */     if (this.email != null) size += PacketIO.stringSize(this.email); 
/* 136 */     if (this.url != null) size += PacketIO.stringSize(this.url);
/*     */     
/* 138 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 142 */     if (buffer.readableBytes() - offset < 13) {
/* 143 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 146 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 149 */     if ((nullBits & 0x1) != 0) {
/* 150 */       int nameOffset = buffer.getIntLE(offset + 1);
/* 151 */       if (nameOffset < 0) {
/* 152 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 154 */       int pos = offset + 13 + nameOffset;
/* 155 */       if (pos >= buffer.writerIndex()) {
/* 156 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 158 */       int nameLen = VarInt.peek(buffer, pos);
/* 159 */       if (nameLen < 0) {
/* 160 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 162 */       if (nameLen > 4096000) {
/* 163 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 165 */       pos += VarInt.length(buffer, pos);
/* 166 */       pos += nameLen;
/* 167 */       if (pos > buffer.writerIndex()) {
/* 168 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 172 */     if ((nullBits & 0x2) != 0) {
/* 173 */       int emailOffset = buffer.getIntLE(offset + 5);
/* 174 */       if (emailOffset < 0) {
/* 175 */         return ValidationResult.error("Invalid offset for Email");
/*     */       }
/* 177 */       int pos = offset + 13 + emailOffset;
/* 178 */       if (pos >= buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Offset out of bounds for Email");
/*     */       }
/* 181 */       int emailLen = VarInt.peek(buffer, pos);
/* 182 */       if (emailLen < 0) {
/* 183 */         return ValidationResult.error("Invalid string length for Email");
/*     */       }
/* 185 */       if (emailLen > 4096000) {
/* 186 */         return ValidationResult.error("Email exceeds max length 4096000");
/*     */       }
/* 188 */       pos += VarInt.length(buffer, pos);
/* 189 */       pos += emailLen;
/* 190 */       if (pos > buffer.writerIndex()) {
/* 191 */         return ValidationResult.error("Buffer overflow reading Email");
/*     */       }
/*     */     } 
/*     */     
/* 195 */     if ((nullBits & 0x4) != 0) {
/* 196 */       int urlOffset = buffer.getIntLE(offset + 9);
/* 197 */       if (urlOffset < 0) {
/* 198 */         return ValidationResult.error("Invalid offset for Url");
/*     */       }
/* 200 */       int pos = offset + 13 + urlOffset;
/* 201 */       if (pos >= buffer.writerIndex()) {
/* 202 */         return ValidationResult.error("Offset out of bounds for Url");
/*     */       }
/* 204 */       int urlLen = VarInt.peek(buffer, pos);
/* 205 */       if (urlLen < 0) {
/* 206 */         return ValidationResult.error("Invalid string length for Url");
/*     */       }
/* 208 */       if (urlLen > 4096000) {
/* 209 */         return ValidationResult.error("Url exceeds max length 4096000");
/*     */       }
/* 211 */       pos += VarInt.length(buffer, pos);
/* 212 */       pos += urlLen;
/* 213 */       if (pos > buffer.writerIndex()) {
/* 214 */         return ValidationResult.error("Buffer overflow reading Url");
/*     */       }
/*     */     } 
/* 217 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AuthorInfo clone() {
/* 221 */     AuthorInfo copy = new AuthorInfo();
/* 222 */     copy.name = this.name;
/* 223 */     copy.email = this.email;
/* 224 */     copy.url = this.url;
/* 225 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AuthorInfo other;
/* 231 */     if (this == obj) return true; 
/* 232 */     if (obj instanceof AuthorInfo) { other = (AuthorInfo)obj; } else { return false; }
/* 233 */      return (Objects.equals(this.name, other.name) && Objects.equals(this.email, other.email) && Objects.equals(this.url, other.url));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 238 */     return Objects.hash(new Object[] { this.name, this.email, this.url });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AuthorInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */