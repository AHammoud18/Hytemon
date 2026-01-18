/*     */ package com.hypixel.hytale.protocol.packets.inventory;
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
/*     */ public class SwitchHotbarBlockSet implements Packet {
/*     */   public static final int PACKET_ID = 178;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384006;
/*     */   @Nullable
/*     */   public String itemId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 178;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SwitchHotbarBlockSet() {}
/*     */ 
/*     */   
/*     */   public SwitchHotbarBlockSet(@Nullable String itemId) {
/*  34 */     this.itemId = itemId;
/*     */   }
/*     */   
/*     */   public SwitchHotbarBlockSet(@Nonnull SwitchHotbarBlockSet other) {
/*  38 */     this.itemId = other.itemId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SwitchHotbarBlockSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     SwitchHotbarBlockSet obj = new SwitchHotbarBlockSet();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int itemIdLen = VarInt.peek(buf, pos);
/*  48 */       if (itemIdLen < 0) throw ProtocolException.negativeLength("ItemId", itemIdLen); 
/*  49 */       if (itemIdLen > 4096000) throw ProtocolException.stringTooLong("ItemId", itemIdLen, 4096000); 
/*  50 */       int itemIdVarLen = VarInt.length(buf, pos);
/*  51 */       obj.itemId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += itemIdVarLen + itemIdLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 1;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.itemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  72 */     if (this.itemId != null) PacketIO.writeVarString(buf, this.itemId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  77 */     int size = 1;
/*  78 */     if (this.itemId != null) size += PacketIO.stringSize(this.itemId);
/*     */     
/*  80 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 1) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  88 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  90 */     int pos = offset + 1;
/*     */     
/*  92 */     if ((nullBits & 0x1) != 0) {
/*  93 */       int itemIdLen = VarInt.peek(buffer, pos);
/*  94 */       if (itemIdLen < 0) {
/*  95 */         return ValidationResult.error("Invalid string length for ItemId");
/*     */       }
/*  97 */       if (itemIdLen > 4096000) {
/*  98 */         return ValidationResult.error("ItemId exceeds max length 4096000");
/*     */       }
/* 100 */       pos += VarInt.length(buffer, pos);
/* 101 */       pos += itemIdLen;
/* 102 */       if (pos > buffer.writerIndex()) {
/* 103 */         return ValidationResult.error("Buffer overflow reading ItemId");
/*     */       }
/*     */     } 
/* 106 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SwitchHotbarBlockSet clone() {
/* 110 */     SwitchHotbarBlockSet copy = new SwitchHotbarBlockSet();
/* 111 */     copy.itemId = this.itemId;
/* 112 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SwitchHotbarBlockSet other;
/* 118 */     if (this == obj) return true; 
/* 119 */     if (obj instanceof SwitchHotbarBlockSet) { other = (SwitchHotbarBlockSet)obj; } else { return false; }
/* 120 */      return Objects.equals(this.itemId, other.itemId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Objects.hash(new Object[] { this.itemId });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\SwitchHotbarBlockSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */