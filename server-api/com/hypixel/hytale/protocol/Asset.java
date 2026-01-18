/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class Asset
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 64;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 64;
/*     */   public static final int MAX_SIZE = 2117;
/*     */   @Nonnull
/*  20 */   public String hash = ""; @Nonnull
/*  21 */   public String name = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Asset(@Nonnull String hash, @Nonnull String name) {
/*  27 */     this.hash = hash;
/*  28 */     this.name = name;
/*     */   }
/*     */   
/*     */   public Asset(@Nonnull Asset other) {
/*  32 */     this.hash = other.hash;
/*  33 */     this.name = other.name;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Asset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     Asset obj = new Asset();
/*     */     
/*  40 */     obj.hash = PacketIO.readFixedAsciiString(buf, offset + 0, 64);
/*     */     
/*  42 */     int pos = offset + 64;
/*  43 */     int nameLen = VarInt.peek(buf, pos);
/*  44 */     if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  45 */     if (nameLen > 512) throw ProtocolException.stringTooLong("Name", nameLen, 512); 
/*  46 */     int nameVarLen = VarInt.length(buf, pos);
/*  47 */     obj.name = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */     pos += nameVarLen + nameLen;
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     int pos = offset + 64;
/*  55 */     int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl;
/*  56 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     PacketIO.writeFixedAsciiString(buf, this.hash, 64);
/*     */     
/*  64 */     PacketIO.writeVarString(buf, this.name, 512);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  69 */     int size = 64;
/*  70 */     size += PacketIO.stringSize(this.name);
/*     */     
/*  72 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 64) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 64 bytes");
/*     */     }
/*     */ 
/*     */     
/*  81 */     int pos = offset + 64;
/*     */     
/*  83 */     int nameLen = VarInt.peek(buffer, pos);
/*  84 */     if (nameLen < 0) {
/*  85 */       return ValidationResult.error("Invalid string length for Name");
/*     */     }
/*  87 */     if (nameLen > 512) {
/*  88 */       return ValidationResult.error("Name exceeds max length 512");
/*     */     }
/*  90 */     pos += VarInt.length(buffer, pos);
/*  91 */     pos += nameLen;
/*  92 */     if (pos > buffer.writerIndex()) {
/*  93 */       return ValidationResult.error("Buffer overflow reading Name");
/*     */     }
/*  95 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Asset clone() {
/*  99 */     Asset copy = new Asset();
/* 100 */     copy.hash = this.hash;
/* 101 */     copy.name = this.name;
/* 102 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Asset other;
/* 108 */     if (this == obj) return true; 
/* 109 */     if (obj instanceof Asset) { other = (Asset)obj; } else { return false; }
/* 110 */      return (Objects.equals(this.hash, other.hash) && Objects.equals(this.name, other.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 115 */     return Objects.hash(new Object[] { this.hash, this.name });
/*     */   }
/*     */   
/*     */   public Asset() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Asset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */