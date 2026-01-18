/*     */ package com.hypixel.hytale.protocol.packets.setup;
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
/*     */ public class WorldLoadProgress
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 21;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 21;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 16384014;
/*     */   @Nullable
/*     */   public String status;
/*     */   public int percentComplete;
/*     */   public int percentCompleteSubitem;
/*     */   
/*     */   public WorldLoadProgress() {}
/*     */   
/*     */   public WorldLoadProgress(@Nullable String status, int percentComplete, int percentCompleteSubitem) {
/*  36 */     this.status = status;
/*  37 */     this.percentComplete = percentComplete;
/*  38 */     this.percentCompleteSubitem = percentCompleteSubitem;
/*     */   }
/*     */   
/*     */   public WorldLoadProgress(@Nonnull WorldLoadProgress other) {
/*  42 */     this.status = other.status;
/*  43 */     this.percentComplete = other.percentComplete;
/*  44 */     this.percentCompleteSubitem = other.percentCompleteSubitem;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static WorldLoadProgress deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     WorldLoadProgress obj = new WorldLoadProgress();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.percentComplete = buf.getIntLE(offset + 1);
/*  52 */     obj.percentCompleteSubitem = buf.getIntLE(offset + 5);
/*     */     
/*  54 */     int pos = offset + 9;
/*  55 */     if ((nullBits & 0x1) != 0) { int statusLen = VarInt.peek(buf, pos);
/*  56 */       if (statusLen < 0) throw ProtocolException.negativeLength("Status", statusLen); 
/*  57 */       if (statusLen > 4096000) throw ProtocolException.stringTooLong("Status", statusLen, 4096000); 
/*  58 */       int statusVarLen = VarInt.length(buf, pos);
/*  59 */       obj.status = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  60 */       pos += statusVarLen + statusLen; }
/*     */     
/*  62 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     int pos = offset + 9;
/*  68 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  69 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  75 */     byte nullBits = 0;
/*  76 */     if (this.status != null) nullBits = (byte)(nullBits | 0x1); 
/*  77 */     buf.writeByte(nullBits);
/*     */     
/*  79 */     buf.writeIntLE(this.percentComplete);
/*  80 */     buf.writeIntLE(this.percentCompleteSubitem);
/*     */     
/*  82 */     if (this.status != null) PacketIO.writeVarString(buf, this.status, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  87 */     int size = 9;
/*  88 */     if (this.status != null) size += PacketIO.stringSize(this.status);
/*     */     
/*  90 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  94 */     if (buffer.readableBytes() - offset < 9) {
/*  95 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  98 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 100 */     int pos = offset + 9;
/*     */     
/* 102 */     if ((nullBits & 0x1) != 0) {
/* 103 */       int statusLen = VarInt.peek(buffer, pos);
/* 104 */       if (statusLen < 0) {
/* 105 */         return ValidationResult.error("Invalid string length for Status");
/*     */       }
/* 107 */       if (statusLen > 4096000) {
/* 108 */         return ValidationResult.error("Status exceeds max length 4096000");
/*     */       }
/* 110 */       pos += VarInt.length(buffer, pos);
/* 111 */       pos += statusLen;
/* 112 */       if (pos > buffer.writerIndex()) {
/* 113 */         return ValidationResult.error("Buffer overflow reading Status");
/*     */       }
/*     */     } 
/* 116 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public WorldLoadProgress clone() {
/* 120 */     WorldLoadProgress copy = new WorldLoadProgress();
/* 121 */     copy.status = this.status;
/* 122 */     copy.percentComplete = this.percentComplete;
/* 123 */     copy.percentCompleteSubitem = this.percentCompleteSubitem;
/* 124 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     WorldLoadProgress other;
/* 130 */     if (this == obj) return true; 
/* 131 */     if (obj instanceof WorldLoadProgress) { other = (WorldLoadProgress)obj; } else { return false; }
/* 132 */      return (Objects.equals(this.status, other.status) && this.percentComplete == other.percentComplete && this.percentCompleteSubitem == other.percentCompleteSubitem);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     return Objects.hash(new Object[] { this.status, Integer.valueOf(this.percentComplete), Integer.valueOf(this.percentCompleteSubitem) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\WorldLoadProgress.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */