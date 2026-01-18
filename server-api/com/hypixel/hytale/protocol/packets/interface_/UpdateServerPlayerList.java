/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateServerPlayerList
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 226;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 131072006;
/*     */   @Nullable
/*     */   public ServerPlayerListUpdate[] players;
/*     */   
/*     */   public int getId() {
/*  25 */     return 226;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateServerPlayerList() {}
/*     */ 
/*     */   
/*     */   public UpdateServerPlayerList(@Nullable ServerPlayerListUpdate[] players) {
/*  34 */     this.players = players;
/*     */   }
/*     */   
/*     */   public UpdateServerPlayerList(@Nonnull UpdateServerPlayerList other) {
/*  38 */     this.players = other.players;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateServerPlayerList deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     UpdateServerPlayerList obj = new UpdateServerPlayerList();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int playersCount = VarInt.peek(buf, pos);
/*  48 */       if (playersCount < 0) throw ProtocolException.negativeLength("Players", playersCount); 
/*  49 */       if (playersCount > 4096000) throw ProtocolException.arrayTooLong("Players", playersCount, 4096000); 
/*  50 */       int playersVarLen = VarInt.size(playersCount);
/*  51 */       if ((pos + playersVarLen) + playersCount * 32L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Players", pos + playersVarLen + playersCount * 32, buf.readableBytes()); 
/*  53 */       pos += playersVarLen;
/*  54 */       obj.players = new ServerPlayerListUpdate[playersCount];
/*  55 */       for (int i = 0; i < playersCount; i++) {
/*  56 */         obj.players[i] = ServerPlayerListUpdate.deserialize(buf, pos);
/*  57 */         pos += ServerPlayerListUpdate.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { pos += ServerPlayerListUpdate.computeBytesConsumed(buf, pos); i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.players != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  79 */     if (this.players != null) { if (this.players.length > 4096000) throw ProtocolException.arrayTooLong("Players", this.players.length, 4096000);  VarInt.write(buf, this.players.length); for (ServerPlayerListUpdate item : this.players) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 1;
/*  85 */     if (this.players != null) size += VarInt.size(this.players.length) + this.players.length * 32;
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 1) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  95 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  97 */     int pos = offset + 1;
/*     */     
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       int playersCount = VarInt.peek(buffer, pos);
/* 101 */       if (playersCount < 0) {
/* 102 */         return ValidationResult.error("Invalid array count for Players");
/*     */       }
/* 104 */       if (playersCount > 4096000) {
/* 105 */         return ValidationResult.error("Players exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       pos += playersCount * 32;
/* 109 */       if (pos > buffer.writerIndex()) {
/* 110 */         return ValidationResult.error("Buffer overflow reading Players");
/*     */       }
/*     */     } 
/* 113 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateServerPlayerList clone() {
/* 117 */     UpdateServerPlayerList copy = new UpdateServerPlayerList();
/* 118 */     copy.players = (this.players != null) ? (ServerPlayerListUpdate[])Arrays.<ServerPlayerListUpdate>stream(this.players).map(e -> e.clone()).toArray(x$0 -> new ServerPlayerListUpdate[x$0]) : null;
/* 119 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateServerPlayerList other;
/* 125 */     if (this == obj) return true; 
/* 126 */     if (obj instanceof UpdateServerPlayerList) { other = (UpdateServerPlayerList)obj; } else { return false; }
/* 127 */      return Arrays.equals((Object[])this.players, (Object[])other.players);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 132 */     int result = 1;
/* 133 */     result = 31 * result + Arrays.hashCode((Object[])this.players);
/* 134 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\UpdateServerPlayerList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */