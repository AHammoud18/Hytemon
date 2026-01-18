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
/*     */ public class CustomPageEvent
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 219;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 16384007;
/*     */   
/*     */   public int getId() {
/*  25 */     return 219;
/*     */   }
/*     */   @Nonnull
/*  28 */   public CustomPageEventType type = CustomPageEventType.Acknowledge;
/*     */   
/*     */   @Nullable
/*     */   public String data;
/*     */ 
/*     */   
/*     */   public CustomPageEvent(@Nonnull CustomPageEventType type, @Nullable String data) {
/*  35 */     this.type = type;
/*  36 */     this.data = data;
/*     */   }
/*     */   
/*     */   public CustomPageEvent(@Nonnull CustomPageEvent other) {
/*  40 */     this.type = other.type;
/*  41 */     this.data = other.data;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CustomPageEvent deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     CustomPageEvent obj = new CustomPageEvent();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.type = CustomPageEventType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  50 */     int pos = offset + 2;
/*  51 */     if ((nullBits & 0x1) != 0) { int dataLen = VarInt.peek(buf, pos);
/*  52 */       if (dataLen < 0) throw ProtocolException.negativeLength("Data", dataLen); 
/*  53 */       if (dataLen > 4096000) throw ProtocolException.stringTooLong("Data", dataLen, 4096000); 
/*  54 */       int dataVarLen = VarInt.length(buf, pos);
/*  55 */       obj.data = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  56 */       pos += dataVarLen + dataLen; }
/*     */     
/*  58 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  62 */     byte nullBits = buf.getByte(offset);
/*  63 */     int pos = offset + 2;
/*  64 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  65 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  71 */     byte nullBits = 0;
/*  72 */     if (this.data != null) nullBits = (byte)(nullBits | 0x1); 
/*  73 */     buf.writeByte(nullBits);
/*     */     
/*  75 */     buf.writeByte(this.type.getValue());
/*     */     
/*  77 */     if (this.data != null) PacketIO.writeVarString(buf, this.data, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  82 */     int size = 2;
/*  83 */     if (this.data != null) size += PacketIO.stringSize(this.data);
/*     */     
/*  85 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  89 */     if (buffer.readableBytes() - offset < 2) {
/*  90 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/*  93 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  95 */     int pos = offset + 2;
/*     */     
/*  97 */     if ((nullBits & 0x1) != 0) {
/*  98 */       int dataLen = VarInt.peek(buffer, pos);
/*  99 */       if (dataLen < 0) {
/* 100 */         return ValidationResult.error("Invalid string length for Data");
/*     */       }
/* 102 */       if (dataLen > 4096000) {
/* 103 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 105 */       pos += VarInt.length(buffer, pos);
/* 106 */       pos += dataLen;
/* 107 */       if (pos > buffer.writerIndex()) {
/* 108 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CustomPageEvent clone() {
/* 115 */     CustomPageEvent copy = new CustomPageEvent();
/* 116 */     copy.type = this.type;
/* 117 */     copy.data = this.data;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CustomPageEvent other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof CustomPageEvent) { other = (CustomPageEvent)obj; } else { return false; }
/* 126 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.data, other.data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     return Objects.hash(new Object[] { this.type, this.data });
/*     */   }
/*     */   
/*     */   public CustomPageEvent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\CustomPageEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */