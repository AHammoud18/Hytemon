/*     */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*     */ public class ServerInfo
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 223;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   
/*     */   public int getId() {
/*  25 */     return 223;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 13; public static final int MAX_SIZE = 32768023; @Nullable
/*     */   public String serverName;
/*     */   @Nullable
/*     */   public String motd;
/*     */   public int maxPlayers;
/*     */   
/*     */   public ServerInfo() {}
/*     */   
/*     */   public ServerInfo(@Nullable String serverName, @Nullable String motd, int maxPlayers) {
/*  36 */     this.serverName = serverName;
/*  37 */     this.motd = motd;
/*  38 */     this.maxPlayers = maxPlayers;
/*     */   }
/*     */   
/*     */   public ServerInfo(@Nonnull ServerInfo other) {
/*  42 */     this.serverName = other.serverName;
/*  43 */     this.motd = other.motd;
/*  44 */     this.maxPlayers = other.maxPlayers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerInfo deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     ServerInfo obj = new ServerInfo();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.maxPlayers = buf.getIntLE(offset + 1);
/*     */     
/*  53 */     if ((nullBits & 0x1) != 0) {
/*  54 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 5);
/*  55 */       int serverNameLen = VarInt.peek(buf, varPos0);
/*  56 */       if (serverNameLen < 0) throw ProtocolException.negativeLength("ServerName", serverNameLen); 
/*  57 */       if (serverNameLen > 4096000) throw ProtocolException.stringTooLong("ServerName", serverNameLen, 4096000); 
/*  58 */       obj.serverName = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 9);
/*  62 */       int motdLen = VarInt.peek(buf, varPos1);
/*  63 */       if (motdLen < 0) throw ProtocolException.negativeLength("Motd", motdLen); 
/*  64 */       if (motdLen > 4096000) throw ProtocolException.stringTooLong("Motd", motdLen, 4096000); 
/*  65 */       obj.motd = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 13;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/*  76 */       int pos0 = offset + 13 + fieldOffset0;
/*  77 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/*  82 */       int pos1 = offset + 13 + fieldOffset1;
/*  83 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  84 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  86 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  92 */     int startPos = buf.writerIndex();
/*  93 */     byte nullBits = 0;
/*  94 */     if (this.serverName != null) nullBits = (byte)(nullBits | 0x1); 
/*  95 */     if (this.motd != null) nullBits = (byte)(nullBits | 0x2); 
/*  96 */     buf.writeByte(nullBits);
/*     */     
/*  98 */     buf.writeIntLE(this.maxPlayers);
/*     */     
/* 100 */     int serverNameOffsetSlot = buf.writerIndex();
/* 101 */     buf.writeIntLE(0);
/* 102 */     int motdOffsetSlot = buf.writerIndex();
/* 103 */     buf.writeIntLE(0);
/*     */     
/* 105 */     int varBlockStart = buf.writerIndex();
/* 106 */     if (this.serverName != null) {
/* 107 */       buf.setIntLE(serverNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 108 */       PacketIO.writeVarString(buf, this.serverName, 4096000);
/*     */     } else {
/* 110 */       buf.setIntLE(serverNameOffsetSlot, -1);
/*     */     } 
/* 112 */     if (this.motd != null) {
/* 113 */       buf.setIntLE(motdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 114 */       PacketIO.writeVarString(buf, this.motd, 4096000);
/*     */     } else {
/* 116 */       buf.setIntLE(motdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 122 */     int size = 13;
/* 123 */     if (this.serverName != null) size += PacketIO.stringSize(this.serverName); 
/* 124 */     if (this.motd != null) size += PacketIO.stringSize(this.motd);
/*     */     
/* 126 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 130 */     if (buffer.readableBytes() - offset < 13) {
/* 131 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 134 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 137 */     if ((nullBits & 0x1) != 0) {
/* 138 */       int serverNameOffset = buffer.getIntLE(offset + 5);
/* 139 */       if (serverNameOffset < 0) {
/* 140 */         return ValidationResult.error("Invalid offset for ServerName");
/*     */       }
/* 142 */       int pos = offset + 13 + serverNameOffset;
/* 143 */       if (pos >= buffer.writerIndex()) {
/* 144 */         return ValidationResult.error("Offset out of bounds for ServerName");
/*     */       }
/* 146 */       int serverNameLen = VarInt.peek(buffer, pos);
/* 147 */       if (serverNameLen < 0) {
/* 148 */         return ValidationResult.error("Invalid string length for ServerName");
/*     */       }
/* 150 */       if (serverNameLen > 4096000) {
/* 151 */         return ValidationResult.error("ServerName exceeds max length 4096000");
/*     */       }
/* 153 */       pos += VarInt.length(buffer, pos);
/* 154 */       pos += serverNameLen;
/* 155 */       if (pos > buffer.writerIndex()) {
/* 156 */         return ValidationResult.error("Buffer overflow reading ServerName");
/*     */       }
/*     */     } 
/*     */     
/* 160 */     if ((nullBits & 0x2) != 0) {
/* 161 */       int motdOffset = buffer.getIntLE(offset + 9);
/* 162 */       if (motdOffset < 0) {
/* 163 */         return ValidationResult.error("Invalid offset for Motd");
/*     */       }
/* 165 */       int pos = offset + 13 + motdOffset;
/* 166 */       if (pos >= buffer.writerIndex()) {
/* 167 */         return ValidationResult.error("Offset out of bounds for Motd");
/*     */       }
/* 169 */       int motdLen = VarInt.peek(buffer, pos);
/* 170 */       if (motdLen < 0) {
/* 171 */         return ValidationResult.error("Invalid string length for Motd");
/*     */       }
/* 173 */       if (motdLen > 4096000) {
/* 174 */         return ValidationResult.error("Motd exceeds max length 4096000");
/*     */       }
/* 176 */       pos += VarInt.length(buffer, pos);
/* 177 */       pos += motdLen;
/* 178 */       if (pos > buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Buffer overflow reading Motd");
/*     */       }
/*     */     } 
/* 182 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerInfo clone() {
/* 186 */     ServerInfo copy = new ServerInfo();
/* 187 */     copy.serverName = this.serverName;
/* 188 */     copy.motd = this.motd;
/* 189 */     copy.maxPlayers = this.maxPlayers;
/* 190 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerInfo other;
/* 196 */     if (this == obj) return true; 
/* 197 */     if (obj instanceof ServerInfo) { other = (ServerInfo)obj; } else { return false; }
/* 198 */      return (Objects.equals(this.serverName, other.serverName) && Objects.equals(this.motd, other.motd) && this.maxPlayers == other.maxPlayers);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 203 */     return Objects.hash(new Object[] { this.serverName, this.motd, Integer.valueOf(this.maxPlayers) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\ServerInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */