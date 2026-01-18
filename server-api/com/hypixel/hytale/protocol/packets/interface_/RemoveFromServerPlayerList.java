/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RemoveFromServerPlayerList implements Packet {
/*     */   public static final int PACKET_ID = 225;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 65536006;
/*     */   @Nullable
/*     */   public UUID[] players;
/*     */   
/*     */   public int getId() {
/*  25 */     return 225;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RemoveFromServerPlayerList() {}
/*     */ 
/*     */   
/*     */   public RemoveFromServerPlayerList(@Nullable UUID[] players) {
/*  34 */     this.players = players;
/*     */   }
/*     */   
/*     */   public RemoveFromServerPlayerList(@Nonnull RemoveFromServerPlayerList other) {
/*  38 */     this.players = other.players;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RemoveFromServerPlayerList deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     RemoveFromServerPlayerList obj = new RemoveFromServerPlayerList();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int playersCount = VarInt.peek(buf, pos);
/*  48 */       if (playersCount < 0) throw ProtocolException.negativeLength("Players", playersCount); 
/*  49 */       if (playersCount > 4096000) throw ProtocolException.arrayTooLong("Players", playersCount, 4096000); 
/*  50 */       int playersVarLen = VarInt.size(playersCount);
/*  51 */       if ((pos + playersVarLen) + playersCount * 16L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Players", pos + playersVarLen + playersCount * 16, buf.readableBytes()); 
/*  53 */       pos += playersVarLen;
/*  54 */       obj.players = new UUID[playersCount];
/*  55 */       for (int i = 0; i < playersCount; i++) {
/*  56 */         obj.players[i] = PacketIO.readUUID(buf, pos + i * 16);
/*     */       }
/*  58 */       pos += playersCount * 16; }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 16; }
/*  67 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     byte nullBits = 0;
/*  74 */     if (this.players != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  78 */     if (this.players != null) { if (this.players.length > 4096000) throw ProtocolException.arrayTooLong("Players", this.players.length, 4096000);  VarInt.write(buf, this.players.length); for (UUID item : this.players) PacketIO.writeUUID(buf, item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  83 */     int size = 1;
/*  84 */     if (this.players != null) size += VarInt.size(this.players.length) + this.players.length * 16;
/*     */     
/*  86 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  90 */     if (buffer.readableBytes() - offset < 1) {
/*  91 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  94 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  96 */     int pos = offset + 1;
/*     */     
/*  98 */     if ((nullBits & 0x1) != 0) {
/*  99 */       int playersCount = VarInt.peek(buffer, pos);
/* 100 */       if (playersCount < 0) {
/* 101 */         return ValidationResult.error("Invalid array count for Players");
/*     */       }
/* 103 */       if (playersCount > 4096000) {
/* 104 */         return ValidationResult.error("Players exceeds max length 4096000");
/*     */       }
/* 106 */       pos += VarInt.length(buffer, pos);
/* 107 */       pos += playersCount * 16;
/* 108 */       if (pos > buffer.writerIndex()) {
/* 109 */         return ValidationResult.error("Buffer overflow reading Players");
/*     */       }
/*     */     } 
/* 112 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RemoveFromServerPlayerList clone() {
/* 116 */     RemoveFromServerPlayerList copy = new RemoveFromServerPlayerList();
/* 117 */     copy.players = (this.players != null) ? Arrays.<UUID>copyOf(this.players, this.players.length) : null;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RemoveFromServerPlayerList other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof RemoveFromServerPlayerList) { other = (RemoveFromServerPlayerList)obj; } else { return false; }
/* 126 */      return Arrays.equals((Object[])this.players, (Object[])other.players);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     int result = 1;
/* 132 */     result = 31 * result + Arrays.hashCode((Object[])this.players);
/* 133 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\RemoveFromServerPlayerList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */