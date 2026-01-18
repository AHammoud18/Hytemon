/*     */ package com.hypixel.hytale.protocol.packets.connection;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.HostAddress;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class Connect implements Packet {
/*     */   public static final int PACKET_ID = 0;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 82;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 102;
/*     */   public static final int MAX_SIZE = 38161;
/*     */   
/*     */   public int getId() {
/*  26 */     return 0;
/*     */   }
/*     */   @Nonnull
/*  29 */   public String protocolHash = ""; @Nonnull
/*  30 */   public ClientType clientType = ClientType.Game; @Nullable
/*     */   public String language;
/*     */   @Nonnull
/*  33 */   public UUID uuid = new UUID(0L, 0L); @Nullable public String identityToken; @Nonnull
/*  34 */   public String username = "";
/*     */   
/*     */   @Nullable
/*     */   public byte[] referralData;
/*     */   @Nullable
/*     */   public HostAddress referralSource;
/*     */   
/*     */   public Connect(@Nonnull String protocolHash, @Nonnull ClientType clientType, @Nullable String language, @Nullable String identityToken, @Nonnull UUID uuid, @Nonnull String username, @Nullable byte[] referralData, @Nullable HostAddress referralSource) {
/*  42 */     this.protocolHash = protocolHash;
/*  43 */     this.clientType = clientType;
/*  44 */     this.language = language;
/*  45 */     this.identityToken = identityToken;
/*  46 */     this.uuid = uuid;
/*  47 */     this.username = username;
/*  48 */     this.referralData = referralData;
/*  49 */     this.referralSource = referralSource;
/*     */   }
/*     */   
/*     */   public Connect(@Nonnull Connect other) {
/*  53 */     this.protocolHash = other.protocolHash;
/*  54 */     this.clientType = other.clientType;
/*  55 */     this.language = other.language;
/*  56 */     this.identityToken = other.identityToken;
/*  57 */     this.uuid = other.uuid;
/*  58 */     this.username = other.username;
/*  59 */     this.referralData = other.referralData;
/*  60 */     this.referralSource = other.referralSource;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Connect deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     Connect obj = new Connect();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.protocolHash = PacketIO.readFixedAsciiString(buf, offset + 1, 64);
/*  68 */     obj.clientType = ClientType.fromValue(buf.getByte(offset + 65));
/*  69 */     obj.uuid = PacketIO.readUUID(buf, offset + 66);
/*     */     
/*  71 */     if ((nullBits & 0x1) != 0) {
/*  72 */       int varPos0 = offset + 102 + buf.getIntLE(offset + 82);
/*  73 */       int languageLen = VarInt.peek(buf, varPos0);
/*  74 */       if (languageLen < 0) throw ProtocolException.negativeLength("Language", languageLen); 
/*  75 */       if (languageLen > 128) throw ProtocolException.stringTooLong("Language", languageLen, 128); 
/*  76 */       obj.language = PacketIO.readVarString(buf, varPos0, PacketIO.ASCII);
/*     */     } 
/*  78 */     if ((nullBits & 0x2) != 0) {
/*  79 */       int varPos1 = offset + 102 + buf.getIntLE(offset + 86);
/*  80 */       int identityTokenLen = VarInt.peek(buf, varPos1);
/*  81 */       if (identityTokenLen < 0) throw ProtocolException.negativeLength("IdentityToken", identityTokenLen); 
/*  82 */       if (identityTokenLen > 8192) throw ProtocolException.stringTooLong("IdentityToken", identityTokenLen, 8192); 
/*  83 */       obj.identityToken = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  86 */     int varPos2 = offset + 102 + buf.getIntLE(offset + 90);
/*  87 */     int usernameLen = VarInt.peek(buf, varPos2);
/*  88 */     if (usernameLen < 0) throw ProtocolException.negativeLength("Username", usernameLen); 
/*  89 */     if (usernameLen > 16) throw ProtocolException.stringTooLong("Username", usernameLen, 16); 
/*  90 */     obj.username = PacketIO.readVarString(buf, varPos2, PacketIO.ASCII);
/*     */     
/*  92 */     if ((nullBits & 0x4) != 0) {
/*  93 */       int varPos3 = offset + 102 + buf.getIntLE(offset + 94);
/*  94 */       int referralDataCount = VarInt.peek(buf, varPos3);
/*  95 */       if (referralDataCount < 0) throw ProtocolException.negativeLength("ReferralData", referralDataCount); 
/*  96 */       if (referralDataCount > 4096) throw ProtocolException.arrayTooLong("ReferralData", referralDataCount, 4096); 
/*  97 */       int varIntLen = VarInt.length(buf, varPos3);
/*  98 */       if ((varPos3 + varIntLen) + referralDataCount * 1L > buf.readableBytes())
/*  99 */         throw ProtocolException.bufferTooSmall("ReferralData", varPos3 + varIntLen + referralDataCount * 1, buf.readableBytes()); 
/* 100 */       obj.referralData = new byte[referralDataCount];
/* 101 */       for (int i = 0; i < referralDataCount; i++) {
/* 102 */         obj.referralData[i] = buf.getByte(varPos3 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/* 105 */     if ((nullBits & 0x8) != 0) {
/* 106 */       int varPos4 = offset + 102 + buf.getIntLE(offset + 98);
/* 107 */       obj.referralSource = HostAddress.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 110 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 114 */     byte nullBits = buf.getByte(offset);
/* 115 */     int maxEnd = 102;
/* 116 */     if ((nullBits & 0x1) != 0) {
/* 117 */       int fieldOffset0 = buf.getIntLE(offset + 82);
/* 118 */       int pos0 = offset + 102 + fieldOffset0;
/* 119 */       int i = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + i;
/* 120 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 122 */     if ((nullBits & 0x2) != 0) {
/* 123 */       int fieldOffset1 = buf.getIntLE(offset + 86);
/* 124 */       int pos1 = offset + 102 + fieldOffset1;
/* 125 */       int i = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + i;
/* 126 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset;
/*     */     
/*     */     } 
/* 129 */     int fieldOffset2 = buf.getIntLE(offset + 90);
/* 130 */     int pos2 = offset + 102 + fieldOffset2;
/* 131 */     int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 132 */     if (pos2 - offset > maxEnd) maxEnd = pos2 - offset;
/*     */     
/* 134 */     if ((nullBits & 0x4) != 0) {
/* 135 */       int fieldOffset3 = buf.getIntLE(offset + 94);
/* 136 */       int pos3 = offset + 102 + fieldOffset3;
/* 137 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 1;
/* 138 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x8) != 0) {
/* 141 */       int fieldOffset4 = buf.getIntLE(offset + 98);
/* 142 */       int pos4 = offset + 102 + fieldOffset4;
/* 143 */       pos4 += HostAddress.computeBytesConsumed(buf, pos4);
/* 144 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 146 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 152 */     int startPos = buf.writerIndex();
/* 153 */     byte nullBits = 0;
/* 154 */     if (this.language != null) nullBits = (byte)(nullBits | 0x1); 
/* 155 */     if (this.identityToken != null) nullBits = (byte)(nullBits | 0x2); 
/* 156 */     if (this.referralData != null) nullBits = (byte)(nullBits | 0x4); 
/* 157 */     if (this.referralSource != null) nullBits = (byte)(nullBits | 0x8); 
/* 158 */     buf.writeByte(nullBits);
/*     */     
/* 160 */     PacketIO.writeFixedAsciiString(buf, this.protocolHash, 64);
/* 161 */     buf.writeByte(this.clientType.getValue());
/* 162 */     PacketIO.writeUUID(buf, this.uuid);
/*     */     
/* 164 */     int languageOffsetSlot = buf.writerIndex();
/* 165 */     buf.writeIntLE(0);
/* 166 */     int identityTokenOffsetSlot = buf.writerIndex();
/* 167 */     buf.writeIntLE(0);
/* 168 */     int usernameOffsetSlot = buf.writerIndex();
/* 169 */     buf.writeIntLE(0);
/* 170 */     int referralDataOffsetSlot = buf.writerIndex();
/* 171 */     buf.writeIntLE(0);
/* 172 */     int referralSourceOffsetSlot = buf.writerIndex();
/* 173 */     buf.writeIntLE(0);
/*     */     
/* 175 */     int varBlockStart = buf.writerIndex();
/* 176 */     if (this.language != null) {
/* 177 */       buf.setIntLE(languageOffsetSlot, buf.writerIndex() - varBlockStart);
/* 178 */       PacketIO.writeVarAsciiString(buf, this.language, 128);
/*     */     } else {
/* 180 */       buf.setIntLE(languageOffsetSlot, -1);
/*     */     } 
/* 182 */     if (this.identityToken != null) {
/* 183 */       buf.setIntLE(identityTokenOffsetSlot, buf.writerIndex() - varBlockStart);
/* 184 */       PacketIO.writeVarString(buf, this.identityToken, 8192);
/*     */     } else {
/* 186 */       buf.setIntLE(identityTokenOffsetSlot, -1);
/*     */     } 
/* 188 */     buf.setIntLE(usernameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 189 */     PacketIO.writeVarAsciiString(buf, this.username, 16);
/* 190 */     if (this.referralData != null) {
/* 191 */       buf.setIntLE(referralDataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 192 */       if (this.referralData.length > 4096) throw ProtocolException.arrayTooLong("ReferralData", this.referralData.length, 4096);  VarInt.write(buf, this.referralData.length); for (byte item : this.referralData) buf.writeByte(item); 
/*     */     } else {
/* 194 */       buf.setIntLE(referralDataOffsetSlot, -1);
/*     */     } 
/* 196 */     if (this.referralSource != null) {
/* 197 */       buf.setIntLE(referralSourceOffsetSlot, buf.writerIndex() - varBlockStart);
/* 198 */       this.referralSource.serialize(buf);
/*     */     } else {
/* 200 */       buf.setIntLE(referralSourceOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 206 */     int size = 102;
/* 207 */     if (this.language != null) size += VarInt.size(this.language.length()) + this.language.length(); 
/* 208 */     if (this.identityToken != null) size += PacketIO.stringSize(this.identityToken); 
/* 209 */     size += VarInt.size(this.username.length()) + this.username.length();
/* 210 */     if (this.referralData != null) size += VarInt.size(this.referralData.length) + this.referralData.length * 1; 
/* 211 */     if (this.referralSource != null) size += this.referralSource.computeSize();
/*     */     
/* 213 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 217 */     if (buffer.readableBytes() - offset < 102) {
/* 218 */       return ValidationResult.error("Buffer too small: expected at least 102 bytes");
/*     */     }
/*     */     
/* 221 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 224 */     if ((nullBits & 0x1) != 0) {
/* 225 */       int languageOffset = buffer.getIntLE(offset + 82);
/* 226 */       if (languageOffset < 0) {
/* 227 */         return ValidationResult.error("Invalid offset for Language");
/*     */       }
/* 229 */       int i = offset + 102 + languageOffset;
/* 230 */       if (i >= buffer.writerIndex()) {
/* 231 */         return ValidationResult.error("Offset out of bounds for Language");
/*     */       }
/* 233 */       int languageLen = VarInt.peek(buffer, i);
/* 234 */       if (languageLen < 0) {
/* 235 */         return ValidationResult.error("Invalid string length for Language");
/*     */       }
/* 237 */       if (languageLen > 128) {
/* 238 */         return ValidationResult.error("Language exceeds max length 128");
/*     */       }
/* 240 */       i += VarInt.length(buffer, i);
/* 241 */       i += languageLen;
/* 242 */       if (i > buffer.writerIndex()) {
/* 243 */         return ValidationResult.error("Buffer overflow reading Language");
/*     */       }
/*     */     } 
/*     */     
/* 247 */     if ((nullBits & 0x2) != 0) {
/* 248 */       int identityTokenOffset = buffer.getIntLE(offset + 86);
/* 249 */       if (identityTokenOffset < 0) {
/* 250 */         return ValidationResult.error("Invalid offset for IdentityToken");
/*     */       }
/* 252 */       int i = offset + 102 + identityTokenOffset;
/* 253 */       if (i >= buffer.writerIndex()) {
/* 254 */         return ValidationResult.error("Offset out of bounds for IdentityToken");
/*     */       }
/* 256 */       int identityTokenLen = VarInt.peek(buffer, i);
/* 257 */       if (identityTokenLen < 0) {
/* 258 */         return ValidationResult.error("Invalid string length for IdentityToken");
/*     */       }
/* 260 */       if (identityTokenLen > 8192) {
/* 261 */         return ValidationResult.error("IdentityToken exceeds max length 8192");
/*     */       }
/* 263 */       i += VarInt.length(buffer, i);
/* 264 */       i += identityTokenLen;
/* 265 */       if (i > buffer.writerIndex()) {
/* 266 */         return ValidationResult.error("Buffer overflow reading IdentityToken");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 271 */     int usernameOffset = buffer.getIntLE(offset + 90);
/* 272 */     if (usernameOffset < 0) {
/* 273 */       return ValidationResult.error("Invalid offset for Username");
/*     */     }
/* 275 */     int pos = offset + 102 + usernameOffset;
/* 276 */     if (pos >= buffer.writerIndex()) {
/* 277 */       return ValidationResult.error("Offset out of bounds for Username");
/*     */     }
/* 279 */     int usernameLen = VarInt.peek(buffer, pos);
/* 280 */     if (usernameLen < 0) {
/* 281 */       return ValidationResult.error("Invalid string length for Username");
/*     */     }
/* 283 */     if (usernameLen > 16) {
/* 284 */       return ValidationResult.error("Username exceeds max length 16");
/*     */     }
/* 286 */     pos += VarInt.length(buffer, pos);
/* 287 */     pos += usernameLen;
/* 288 */     if (pos > buffer.writerIndex()) {
/* 289 */       return ValidationResult.error("Buffer overflow reading Username");
/*     */     }
/*     */ 
/*     */     
/* 293 */     if ((nullBits & 0x4) != 0) {
/* 294 */       int referralDataOffset = buffer.getIntLE(offset + 94);
/* 295 */       if (referralDataOffset < 0) {
/* 296 */         return ValidationResult.error("Invalid offset for ReferralData");
/*     */       }
/* 298 */       pos = offset + 102 + referralDataOffset;
/* 299 */       if (pos >= buffer.writerIndex()) {
/* 300 */         return ValidationResult.error("Offset out of bounds for ReferralData");
/*     */       }
/* 302 */       int referralDataCount = VarInt.peek(buffer, pos);
/* 303 */       if (referralDataCount < 0) {
/* 304 */         return ValidationResult.error("Invalid array count for ReferralData");
/*     */       }
/* 306 */       if (referralDataCount > 4096) {
/* 307 */         return ValidationResult.error("ReferralData exceeds max length 4096");
/*     */       }
/* 309 */       pos += VarInt.length(buffer, pos);
/* 310 */       pos += referralDataCount * 1;
/* 311 */       if (pos > buffer.writerIndex()) {
/* 312 */         return ValidationResult.error("Buffer overflow reading ReferralData");
/*     */       }
/*     */     } 
/*     */     
/* 316 */     if ((nullBits & 0x8) != 0) {
/* 317 */       int referralSourceOffset = buffer.getIntLE(offset + 98);
/* 318 */       if (referralSourceOffset < 0) {
/* 319 */         return ValidationResult.error("Invalid offset for ReferralSource");
/*     */       }
/* 321 */       pos = offset + 102 + referralSourceOffset;
/* 322 */       if (pos >= buffer.writerIndex()) {
/* 323 */         return ValidationResult.error("Offset out of bounds for ReferralSource");
/*     */       }
/* 325 */       ValidationResult referralSourceResult = HostAddress.validateStructure(buffer, pos);
/* 326 */       if (!referralSourceResult.isValid()) {
/* 327 */         return ValidationResult.error("Invalid ReferralSource: " + referralSourceResult.error());
/*     */       }
/* 329 */       pos += HostAddress.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 331 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Connect clone() {
/* 335 */     Connect copy = new Connect();
/* 336 */     copy.protocolHash = this.protocolHash;
/* 337 */     copy.clientType = this.clientType;
/* 338 */     copy.language = this.language;
/* 339 */     copy.identityToken = this.identityToken;
/* 340 */     copy.uuid = this.uuid;
/* 341 */     copy.username = this.username;
/* 342 */     copy.referralData = (this.referralData != null) ? Arrays.copyOf(this.referralData, this.referralData.length) : null;
/* 343 */     copy.referralSource = (this.referralSource != null) ? this.referralSource.clone() : null;
/* 344 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Connect other;
/* 350 */     if (this == obj) return true; 
/* 351 */     if (obj instanceof Connect) { other = (Connect)obj; } else { return false; }
/* 352 */      return (Objects.equals(this.protocolHash, other.protocolHash) && Objects.equals(this.clientType, other.clientType) && Objects.equals(this.language, other.language) && Objects.equals(this.identityToken, other.identityToken) && Objects.equals(this.uuid, other.uuid) && Objects.equals(this.username, other.username) && Arrays.equals(this.referralData, other.referralData) && Objects.equals(this.referralSource, other.referralSource));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 357 */     int result = 1;
/* 358 */     result = 31 * result + Objects.hashCode(this.protocolHash);
/* 359 */     result = 31 * result + Objects.hashCode(this.clientType);
/* 360 */     result = 31 * result + Objects.hashCode(this.language);
/* 361 */     result = 31 * result + Objects.hashCode(this.identityToken);
/* 362 */     result = 31 * result + Objects.hashCode(this.uuid);
/* 363 */     result = 31 * result + Objects.hashCode(this.username);
/* 364 */     result = 31 * result + Arrays.hashCode(this.referralData);
/* 365 */     result = 31 * result + Objects.hashCode(this.referralSource);
/* 366 */     return result;
/*     */   }
/*     */   
/*     */   public Connect() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\connection\Connect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */