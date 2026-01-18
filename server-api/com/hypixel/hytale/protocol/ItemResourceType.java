/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemResourceType
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 16384010;
/*     */   @Nullable
/*     */   public String id;
/*  21 */   public int quantity = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemResourceType(@Nullable String id, int quantity) {
/*  27 */     this.id = id;
/*  28 */     this.quantity = quantity;
/*     */   }
/*     */   
/*     */   public ItemResourceType(@Nonnull ItemResourceType other) {
/*  32 */     this.id = other.id;
/*  33 */     this.quantity = other.quantity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemResourceType deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ItemResourceType obj = new ItemResourceType();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.quantity = buf.getIntLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int idLen = VarInt.peek(buf, pos);
/*  44 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  45 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  46 */       int idVarLen = VarInt.length(buf, pos);
/*  47 */       obj.id = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */       pos += idVarLen + idLen; }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 5;
/*  56 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  57 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeIntLE(this.quantity);
/*     */     
/*  68 */     if (this.id != null) PacketIO.writeVarString(buf, this.id, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 5;
/*  74 */     if (this.id != null) size += PacketIO.stringSize(this.id);
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 5) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 5;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       int idLen = VarInt.peek(buffer, pos);
/*  90 */       if (idLen < 0) {
/*  91 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/*  93 */       if (idLen > 4096000) {
/*  94 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/*  96 */       pos += VarInt.length(buffer, pos);
/*  97 */       pos += idLen;
/*  98 */       if (pos > buffer.writerIndex()) {
/*  99 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemResourceType clone() {
/* 106 */     ItemResourceType copy = new ItemResourceType();
/* 107 */     copy.id = this.id;
/* 108 */     copy.quantity = this.quantity;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemResourceType other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof ItemResourceType) { other = (ItemResourceType)obj; } else { return false; }
/* 117 */      return (Objects.equals(this.id, other.id) && this.quantity == other.quantity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { this.id, Integer.valueOf(this.quantity) });
/*     */   }
/*     */   
/*     */   public ItemResourceType() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemResourceType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */