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
/*     */ public class AuthGrant
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 11;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 11;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 49171; @Nullable
/*     */   public String authorizationGrant;
/*     */   @Nullable
/*     */   public String serverIdentityToken;
/*     */   
/*     */   public AuthGrant() {}
/*     */   
/*     */   public AuthGrant(@Nullable String authorizationGrant, @Nullable String serverIdentityToken) {
/*  35 */     this.authorizationGrant = authorizationGrant;
/*  36 */     this.serverIdentityToken = serverIdentityToken;
/*     */   }
/*     */   
/*     */   public AuthGrant(@Nonnull AuthGrant other) {
/*  40 */     this.authorizationGrant = other.authorizationGrant;
/*  41 */     this.serverIdentityToken = other.serverIdentityToken;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AuthGrant deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AuthGrant obj = new AuthGrant();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       int authorizationGrantLen = VarInt.peek(buf, varPos0);
/*  52 */       if (authorizationGrantLen < 0) throw ProtocolException.negativeLength("AuthorizationGrant", authorizationGrantLen); 
/*  53 */       if (authorizationGrantLen > 4096) throw ProtocolException.stringTooLong("AuthorizationGrant", authorizationGrantLen, 4096); 
/*  54 */       obj.authorizationGrant = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  58 */       int serverIdentityTokenLen = VarInt.peek(buf, varPos1);
/*  59 */       if (serverIdentityTokenLen < 0) throw ProtocolException.negativeLength("ServerIdentityToken", serverIdentityTokenLen); 
/*  60 */       if (serverIdentityTokenLen > 8192) throw ProtocolException.stringTooLong("ServerIdentityToken", serverIdentityTokenLen, 8192); 
/*  61 */       obj.serverIdentityToken = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
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
/*  90 */     if (this.authorizationGrant != null) nullBits = (byte)(nullBits | 0x1); 
/*  91 */     if (this.serverIdentityToken != null) nullBits = (byte)(nullBits | 0x2); 
/*  92 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  95 */     int authorizationGrantOffsetSlot = buf.writerIndex();
/*  96 */     buf.writeIntLE(0);
/*  97 */     int serverIdentityTokenOffsetSlot = buf.writerIndex();
/*  98 */     buf.writeIntLE(0);
/*     */     
/* 100 */     int varBlockStart = buf.writerIndex();
/* 101 */     if (this.authorizationGrant != null) {
/* 102 */       buf.setIntLE(authorizationGrantOffsetSlot, buf.writerIndex() - varBlockStart);
/* 103 */       PacketIO.writeVarString(buf, this.authorizationGrant, 4096);
/*     */     } else {
/* 105 */       buf.setIntLE(authorizationGrantOffsetSlot, -1);
/*     */     } 
/* 107 */     if (this.serverIdentityToken != null) {
/* 108 */       buf.setIntLE(serverIdentityTokenOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       PacketIO.writeVarString(buf, this.serverIdentityToken, 8192);
/*     */     } else {
/* 111 */       buf.setIntLE(serverIdentityTokenOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 117 */     int size = 9;
/* 118 */     if (this.authorizationGrant != null) size += PacketIO.stringSize(this.authorizationGrant); 
/* 119 */     if (this.serverIdentityToken != null) size += PacketIO.stringSize(this.serverIdentityToken);
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
/* 133 */       int authorizationGrantOffset = buffer.getIntLE(offset + 1);
/* 134 */       if (authorizationGrantOffset < 0) {
/* 135 */         return ValidationResult.error("Invalid offset for AuthorizationGrant");
/*     */       }
/* 137 */       int pos = offset + 9 + authorizationGrantOffset;
/* 138 */       if (pos >= buffer.writerIndex()) {
/* 139 */         return ValidationResult.error("Offset out of bounds for AuthorizationGrant");
/*     */       }
/* 141 */       int authorizationGrantLen = VarInt.peek(buffer, pos);
/* 142 */       if (authorizationGrantLen < 0) {
/* 143 */         return ValidationResult.error("Invalid string length for AuthorizationGrant");
/*     */       }
/* 145 */       if (authorizationGrantLen > 4096) {
/* 146 */         return ValidationResult.error("AuthorizationGrant exceeds max length 4096");
/*     */       }
/* 148 */       pos += VarInt.length(buffer, pos);
/* 149 */       pos += authorizationGrantLen;
/* 150 */       if (pos > buffer.writerIndex()) {
/* 151 */         return ValidationResult.error("Buffer overflow reading AuthorizationGrant");
/*     */       }
/*     */     } 
/*     */     
/* 155 */     if ((nullBits & 0x2) != 0) {
/* 156 */       int serverIdentityTokenOffset = buffer.getIntLE(offset + 5);
/* 157 */       if (serverIdentityTokenOffset < 0) {
/* 158 */         return ValidationResult.error("Invalid offset for ServerIdentityToken");
/*     */       }
/* 160 */       int pos = offset + 9 + serverIdentityTokenOffset;
/* 161 */       if (pos >= buffer.writerIndex()) {
/* 162 */         return ValidationResult.error("Offset out of bounds for ServerIdentityToken");
/*     */       }
/* 164 */       int serverIdentityTokenLen = VarInt.peek(buffer, pos);
/* 165 */       if (serverIdentityTokenLen < 0) {
/* 166 */         return ValidationResult.error("Invalid string length for ServerIdentityToken");
/*     */       }
/* 168 */       if (serverIdentityTokenLen > 8192) {
/* 169 */         return ValidationResult.error("ServerIdentityToken exceeds max length 8192");
/*     */       }
/* 171 */       pos += VarInt.length(buffer, pos);
/* 172 */       pos += serverIdentityTokenLen;
/* 173 */       if (pos > buffer.writerIndex()) {
/* 174 */         return ValidationResult.error("Buffer overflow reading ServerIdentityToken");
/*     */       }
/*     */     } 
/* 177 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AuthGrant clone() {
/* 181 */     AuthGrant copy = new AuthGrant();
/* 182 */     copy.authorizationGrant = this.authorizationGrant;
/* 183 */     copy.serverIdentityToken = this.serverIdentityToken;
/* 184 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AuthGrant other;
/* 190 */     if (this == obj) return true; 
/* 191 */     if (obj instanceof AuthGrant) { other = (AuthGrant)obj; } else { return false; }
/* 192 */      return (Objects.equals(this.authorizationGrant, other.authorizationGrant) && Objects.equals(this.serverIdentityToken, other.serverIdentityToken));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     return Objects.hash(new Object[] { this.authorizationGrant, this.serverIdentityToken });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\auth\AuthGrant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */