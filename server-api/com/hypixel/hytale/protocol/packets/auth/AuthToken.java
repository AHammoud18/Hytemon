/*     */ package com.hypixel.hytale.protocol.packets.auth;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ public class AuthToken
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 12;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 12;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 49171; @Nullable
/*     */   public String accessToken;
/*     */   @Nullable
/*     */   public String serverAuthorizationGrant;
/*     */   
/*     */   public AuthToken() {}
/*     */   
/*     */   public AuthToken(@Nullable String accessToken, @Nullable String serverAuthorizationGrant) {
/*  35 */     this.accessToken = accessToken;
/*  36 */     this.serverAuthorizationGrant = serverAuthorizationGrant;
/*     */   }
/*     */   
/*     */   public AuthToken(@Nonnull AuthToken other) {
/*  40 */     this.accessToken = other.accessToken;
/*  41 */     this.serverAuthorizationGrant = other.serverAuthorizationGrant;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AuthToken deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AuthToken obj = new AuthToken();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       int accessTokenLen = VarInt.peek(buf, varPos0);
/*  52 */       if (accessTokenLen < 0) throw ProtocolException.negativeLength("AccessToken", accessTokenLen); 
/*  53 */       if (accessTokenLen > 8192) throw ProtocolException.stringTooLong("AccessToken", accessTokenLen, 8192); 
/*  54 */       obj.accessToken = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  58 */       int serverAuthorizationGrantLen = VarInt.peek(buf, varPos1);
/*  59 */       if (serverAuthorizationGrantLen < 0) throw ProtocolException.negativeLength("ServerAuthorizationGrant", serverAuthorizationGrantLen); 
/*  60 */       if (serverAuthorizationGrantLen > 4096) throw ProtocolException.stringTooLong("ServerAuthorizationGrant", serverAuthorizationGrantLen, 4096); 
/*  61 */       obj.serverAuthorizationGrant = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int maxEnd = 9;
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  72 */       int pos0 = offset + 9 + fieldOffset0;
/*  73 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  74 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  76 */     if ((nullBits & 0x2) != 0) {
/*  77 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  78 */       int pos1 = offset + 9 + fieldOffset1;
/*  79 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  80 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  82 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  88 */     int startPos = buf.writerIndex();
/*  89 */     byte nullBits = 0;
/*  90 */     if (this.accessToken != null) nullBits = (byte)(nullBits | 0x1); 
/*  91 */     if (this.serverAuthorizationGrant != null) nullBits = (byte)(nullBits | 0x2); 
/*  92 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  95 */     int accessTokenOffsetSlot = buf.writerIndex();
/*  96 */     buf.writeIntLE(0);
/*  97 */     int serverAuthorizationGrantOffsetSlot = buf.writerIndex();
/*  98 */     buf.writeIntLE(0);
/*     */     
/* 100 */     int varBlockStart = buf.writerIndex();
/* 101 */     if (this.accessToken != null) {
/* 102 */       buf.setIntLE(accessTokenOffsetSlot, buf.writerIndex() - varBlockStart);
/* 103 */       PacketIO.writeVarString(buf, this.accessToken, 8192);
/*     */     } else {
/* 105 */       buf.setIntLE(accessTokenOffsetSlot, -1);
/*     */     } 
/* 107 */     if (this.serverAuthorizationGrant != null) {
/* 108 */       buf.setIntLE(serverAuthorizationGrantOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       PacketIO.writeVarString(buf, this.serverAuthorizationGrant, 4096);
/*     */     } else {
/* 111 */       buf.setIntLE(serverAuthorizationGrantOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 117 */     int size = 9;
/* 118 */     if (this.accessToken != null) size += PacketIO.stringSize(this.accessToken); 
/* 119 */     if (this.serverAuthorizationGrant != null) size += PacketIO.stringSize(this.serverAuthorizationGrant);
/*     */     
/* 121 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 125 */     if (buffer.readableBytes() - offset < 9) {
/* 126 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 129 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 132 */     if ((nullBits & 0x1) != 0) {
/* 133 */       int accessTokenOffset = buffer.getIntLE(offset + 1);
/* 134 */       if (accessTokenOffset < 0) {
/* 135 */         return ValidationResult.error("Invalid offset for AccessToken");
/*     */       }
/* 137 */       int pos = offset + 9 + accessTokenOffset;
/* 138 */       if (pos >= buffer.writerIndex()) {
/* 139 */         return ValidationResult.error("Offset out of bounds for AccessToken");
/*     */       }
/* 141 */       int accessTokenLen = VarInt.peek(buffer, pos);
/* 142 */       if (accessTokenLen < 0) {
/* 143 */         return ValidationResult.error("Invalid string length for AccessToken");
/*     */       }
/* 145 */       if (accessTokenLen > 8192) {
/* 146 */         return ValidationResult.error("AccessToken exceeds max length 8192");
/*     */       }
/* 148 */       pos += VarInt.length(buffer, pos);
/* 149 */       pos += accessTokenLen;
/* 150 */       if (pos > buffer.writerIndex()) {
/* 151 */         return ValidationResult.error("Buffer overflow reading AccessToken");
/*     */       }
/*     */     } 
/*     */     
/* 155 */     if ((nullBits & 0x2) != 0) {
/* 156 */       int serverAuthorizationGrantOffset = buffer.getIntLE(offset + 5);
/* 157 */       if (serverAuthorizationGrantOffset < 0) {
/* 158 */         return ValidationResult.error("Invalid offset for ServerAuthorizationGrant");
/*     */       }
/* 160 */       int pos = offset + 9 + serverAuthorizationGrantOffset;
/* 161 */       if (pos >= buffer.writerIndex()) {
/* 162 */         return ValidationResult.error("Offset out of bounds for ServerAuthorizationGrant");
/*     */       }
/* 164 */       int serverAuthorizationGrantLen = VarInt.peek(buffer, pos);
/* 165 */       if (serverAuthorizationGrantLen < 0) {
/* 166 */         return ValidationResult.error("Invalid string length for ServerAuthorizationGrant");
/*     */       }
/* 168 */       if (serverAuthorizationGrantLen > 4096) {
/* 169 */         return ValidationResult.error("ServerAuthorizationGrant exceeds max length 4096");
/*     */       }
/* 171 */       pos += VarInt.length(buffer, pos);
/* 172 */       pos += serverAuthorizationGrantLen;
/* 173 */       if (pos > buffer.writerIndex()) {
/* 174 */         return ValidationResult.error("Buffer overflow reading ServerAuthorizationGrant");
/*     */       }
/*     */     } 
/* 177 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AuthToken clone() {
/* 181 */     AuthToken copy = new AuthToken();
/* 182 */     copy.accessToken = this.accessToken;
/* 183 */     copy.serverAuthorizationGrant = this.serverAuthorizationGrant;
/* 184 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AuthToken other;
/* 190 */     if (this == obj) return true; 
/* 191 */     if (obj instanceof AuthToken) { other = (AuthToken)obj; } else { return false; }
/* 192 */      return (Objects.equals(this.accessToken, other.accessToken) && Objects.equals(this.serverAuthorizationGrant, other.serverAuthorizationGrant));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     return Objects.hash(new Object[] { this.accessToken, this.serverAuthorizationGrant });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\auth\AuthToken.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */