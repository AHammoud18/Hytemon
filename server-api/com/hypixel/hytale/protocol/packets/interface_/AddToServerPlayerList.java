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
/*     */ public class AddToServerPlayerList
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 224;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public ServerPlayerListPlayer[] players;
/*     */   
/*     */   public int getId() {
/*  25 */     return 224;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AddToServerPlayerList() {}
/*     */ 
/*     */   
/*     */   public AddToServerPlayerList(@Nullable ServerPlayerListPlayer[] players) {
/*  34 */     this.players = players;
/*     */   }
/*     */   
/*     */   public AddToServerPlayerList(@Nonnull AddToServerPlayerList other) {
/*  38 */     this.players = other.players;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AddToServerPlayerList deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AddToServerPlayerList obj = new AddToServerPlayerList();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int playersCount = VarInt.peek(buf, pos);
/*  48 */       if (playersCount < 0) throw ProtocolException.negativeLength("Players", playersCount); 
/*  49 */       if (playersCount > 4096000) throw ProtocolException.arrayTooLong("Players", playersCount, 4096000); 
/*  50 */       int playersVarLen = VarInt.size(playersCount);
/*  51 */       if ((pos + playersVarLen) + playersCount * 37L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Players", pos + playersVarLen + playersCount * 37, buf.readableBytes()); 
/*  53 */       pos += playersVarLen;
/*  54 */       obj.players = new ServerPlayerListPlayer[playersCount];
/*  55 */       for (int i = 0; i < playersCount; i++) {
/*  56 */         obj.players[i] = ServerPlayerListPlayer.deserialize(buf, pos);
/*  57 */         pos += ServerPlayerListPlayer.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { pos += ServerPlayerListPlayer.computeBytesConsumed(buf, pos); i++; }  }
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
/*  79 */     if (this.players != null) { if (this.players.length > 4096000) throw ProtocolException.arrayTooLong("Players", this.players.length, 4096000);  VarInt.write(buf, this.players.length); for (ServerPlayerListPlayer item : this.players) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 1;
/*  85 */     if (this.players != null) {
/*  86 */       int playersSize = 0;
/*  87 */       for (ServerPlayerListPlayer elem : this.players) playersSize += elem.computeSize(); 
/*  88 */       size += VarInt.size(this.players.length) + playersSize;
/*     */     } 
/*     */     
/*  91 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  95 */     if (buffer.readableBytes() - offset < 1) {
/*  96 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  99 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 101 */     int pos = offset + 1;
/*     */     
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int playersCount = VarInt.peek(buffer, pos);
/* 105 */       if (playersCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for Players");
/*     */       }
/* 108 */       if (playersCount > 4096000) {
/* 109 */         return ValidationResult.error("Players exceeds max length 4096000");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       for (int i = 0; i < playersCount; i++) {
/* 113 */         ValidationResult structResult = ServerPlayerListPlayer.validateStructure(buffer, pos);
/* 114 */         if (!structResult.isValid()) {
/* 115 */           return ValidationResult.error("Invalid ServerPlayerListPlayer in Players[" + i + "]: " + structResult.error());
/*     */         }
/* 117 */         pos += ServerPlayerListPlayer.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 120 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AddToServerPlayerList clone() {
/* 124 */     AddToServerPlayerList copy = new AddToServerPlayerList();
/* 125 */     copy.players = (this.players != null) ? (ServerPlayerListPlayer[])Arrays.<ServerPlayerListPlayer>stream(this.players).map(e -> e.clone()).toArray(x$0 -> new ServerPlayerListPlayer[x$0]) : null;
/* 126 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AddToServerPlayerList other;
/* 132 */     if (this == obj) return true; 
/* 133 */     if (obj instanceof AddToServerPlayerList) { other = (AddToServerPlayerList)obj; } else { return false; }
/* 134 */      return Arrays.equals((Object[])this.players, (Object[])other.players);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     int result = 1;
/* 140 */     result = 31 * result + Arrays.hashCode((Object[])this.players);
/* 141 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\AddToServerPlayerList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */