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
/*     */ public class Status
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 10;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   
/*     */   public int getId() {
/*  25 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   
/*     */   public static final int MAX_SIZE = 2587;
/*     */   
/*     */   @Nullable
/*     */   public String name;
/*     */   
/*     */   public Status(@Nullable String name, @Nullable String motd, int playerCount, int maxPlayers) {
/*  37 */     this.name = name;
/*  38 */     this.motd = motd;
/*  39 */     this.playerCount = playerCount;
/*  40 */     this.maxPlayers = maxPlayers;
/*     */   } @Nullable
/*     */   public String motd; public int playerCount; public int maxPlayers; public Status() {}
/*     */   public Status(@Nonnull Status other) {
/*  44 */     this.name = other.name;
/*  45 */     this.motd = other.motd;
/*  46 */     this.playerCount = other.playerCount;
/*  47 */     this.maxPlayers = other.maxPlayers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Status deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     Status obj = new Status();
/*  53 */     byte nullBits = buf.getByte(offset);
/*  54 */     obj.playerCount = buf.getIntLE(offset + 1);
/*  55 */     obj.maxPlayers = buf.getIntLE(offset + 5);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 9);
/*  59 */       int nameLen = VarInt.peek(buf, varPos0);
/*  60 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  61 */       if (nameLen > 128) throw ProtocolException.stringTooLong("Name", nameLen, 128); 
/*  62 */       obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 13);
/*  66 */       int motdLen = VarInt.peek(buf, varPos1);
/*  67 */       if (motdLen < 0) throw ProtocolException.negativeLength("Motd", motdLen); 
/*  68 */       if (motdLen > 512) throw ProtocolException.stringTooLong("Motd", motdLen, 512); 
/*  69 */       obj.motd = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int maxEnd = 17;
/*  78 */     if ((nullBits & 0x1) != 0) {
/*  79 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  80 */       int pos0 = offset + 17 + fieldOffset0;
/*  81 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  82 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  84 */     if ((nullBits & 0x2) != 0) {
/*  85 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/*  86 */       int pos1 = offset + 17 + fieldOffset1;
/*  87 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  88 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  90 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  96 */     int startPos = buf.writerIndex();
/*  97 */     byte nullBits = 0;
/*  98 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/*  99 */     if (this.motd != null) nullBits = (byte)(nullBits | 0x2); 
/* 100 */     buf.writeByte(nullBits);
/*     */     
/* 102 */     buf.writeIntLE(this.playerCount);
/* 103 */     buf.writeIntLE(this.maxPlayers);
/*     */     
/* 105 */     int nameOffsetSlot = buf.writerIndex();
/* 106 */     buf.writeIntLE(0);
/* 107 */     int motdOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/*     */     
/* 110 */     int varBlockStart = buf.writerIndex();
/* 111 */     if (this.name != null) {
/* 112 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 113 */       PacketIO.writeVarString(buf, this.name, 128);
/*     */     } else {
/* 115 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 117 */     if (this.motd != null) {
/* 118 */       buf.setIntLE(motdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 119 */       PacketIO.writeVarString(buf, this.motd, 512);
/*     */     } else {
/* 121 */       buf.setIntLE(motdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 127 */     int size = 17;
/* 128 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 129 */     if (this.motd != null) size += PacketIO.stringSize(this.motd);
/*     */     
/* 131 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 135 */     if (buffer.readableBytes() - offset < 17) {
/* 136 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 139 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 142 */     if ((nullBits & 0x1) != 0) {
/* 143 */       int nameOffset = buffer.getIntLE(offset + 9);
/* 144 */       if (nameOffset < 0) {
/* 145 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 147 */       int pos = offset + 17 + nameOffset;
/* 148 */       if (pos >= buffer.writerIndex()) {
/* 149 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 151 */       int nameLen = VarInt.peek(buffer, pos);
/* 152 */       if (nameLen < 0) {
/* 153 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 155 */       if (nameLen > 128) {
/* 156 */         return ValidationResult.error("Name exceeds max length 128");
/*     */       }
/* 158 */       pos += VarInt.length(buffer, pos);
/* 159 */       pos += nameLen;
/* 160 */       if (pos > buffer.writerIndex()) {
/* 161 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 165 */     if ((nullBits & 0x2) != 0) {
/* 166 */       int motdOffset = buffer.getIntLE(offset + 13);
/* 167 */       if (motdOffset < 0) {
/* 168 */         return ValidationResult.error("Invalid offset for Motd");
/*     */       }
/* 170 */       int pos = offset + 17 + motdOffset;
/* 171 */       if (pos >= buffer.writerIndex()) {
/* 172 */         return ValidationResult.error("Offset out of bounds for Motd");
/*     */       }
/* 174 */       int motdLen = VarInt.peek(buffer, pos);
/* 175 */       if (motdLen < 0) {
/* 176 */         return ValidationResult.error("Invalid string length for Motd");
/*     */       }
/* 178 */       if (motdLen > 512) {
/* 179 */         return ValidationResult.error("Motd exceeds max length 512");
/*     */       }
/* 181 */       pos += VarInt.length(buffer, pos);
/* 182 */       pos += motdLen;
/* 183 */       if (pos > buffer.writerIndex()) {
/* 184 */         return ValidationResult.error("Buffer overflow reading Motd");
/*     */       }
/*     */     } 
/* 187 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Status clone() {
/* 191 */     Status copy = new Status();
/* 192 */     copy.name = this.name;
/* 193 */     copy.motd = this.motd;
/* 194 */     copy.playerCount = this.playerCount;
/* 195 */     copy.maxPlayers = this.maxPlayers;
/* 196 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Status other;
/* 202 */     if (this == obj) return true; 
/* 203 */     if (obj instanceof Status) { other = (Status)obj; } else { return false; }
/* 204 */      return (Objects.equals(this.name, other.name) && Objects.equals(this.motd, other.motd) && this.playerCount == other.playerCount && this.maxPlayers == other.maxPlayers);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     return Objects.hash(new Object[] { this.name, this.motd, Integer.valueOf(this.playerCount), Integer.valueOf(this.maxPlayers) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\auth\Status.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */