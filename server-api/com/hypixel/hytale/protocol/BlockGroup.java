/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class BlockGroup
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String[] names;
/*     */   
/*     */   public BlockGroup() {}
/*     */   
/*     */   public BlockGroup(@Nullable String[] names) {
/*  26 */     this.names = names;
/*     */   }
/*     */   
/*     */   public BlockGroup(@Nonnull BlockGroup other) {
/*  30 */     this.names = other.names;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockGroup deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     BlockGroup obj = new BlockGroup();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int namesCount = VarInt.peek(buf, pos);
/*  40 */       if (namesCount < 0) throw ProtocolException.negativeLength("Names", namesCount); 
/*  41 */       if (namesCount > 4096000) throw ProtocolException.arrayTooLong("Names", namesCount, 4096000); 
/*  42 */       int namesVarLen = VarInt.size(namesCount);
/*  43 */       if ((pos + namesVarLen) + namesCount * 1L > buf.readableBytes())
/*  44 */         throw ProtocolException.bufferTooSmall("Names", pos + namesVarLen + namesCount * 1, buf.readableBytes()); 
/*  45 */       pos += namesVarLen;
/*  46 */       obj.names = new String[namesCount];
/*  47 */       for (int i = 0; i < namesCount; i++) {
/*  48 */         int strLen = VarInt.peek(buf, pos);
/*  49 */         if (strLen < 0) throw ProtocolException.negativeLength("names[" + i + "]", strLen); 
/*  50 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("names[" + i + "]", strLen, 4096000); 
/*  51 */         int strVarLen = VarInt.length(buf, pos);
/*  52 */         obj.names[i] = PacketIO.readVarString(buf, pos);
/*  53 */         pos += strVarLen + strLen;
/*     */       }  }
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int pos = offset + 1;
/*  62 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  63 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  64 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     byte nullBits = 0;
/*  70 */     if (this.names != null) nullBits = (byte)(nullBits | 0x1); 
/*  71 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  74 */     if (this.names != null) { if (this.names.length > 4096000) throw ProtocolException.arrayTooLong("Names", this.names.length, 4096000);  VarInt.write(buf, this.names.length); for (String item : this.names) PacketIO.writeVarString(buf, item, 4096000);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 1;
/*  80 */     if (this.names != null) {
/*  81 */       int namesSize = 0;
/*  82 */       for (String elem : this.names) namesSize += PacketIO.stringSize(elem); 
/*  83 */       size += VarInt.size(this.names.length) + namesSize;
/*     */     } 
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
/*  99 */       int namesCount = VarInt.peek(buffer, pos);
/* 100 */       if (namesCount < 0) {
/* 101 */         return ValidationResult.error("Invalid array count for Names");
/*     */       }
/* 103 */       if (namesCount > 4096000) {
/* 104 */         return ValidationResult.error("Names exceeds max length 4096000");
/*     */       }
/* 106 */       pos += VarInt.length(buffer, pos);
/* 107 */       for (int i = 0; i < namesCount; i++) {
/* 108 */         int strLen = VarInt.peek(buffer, pos);
/* 109 */         if (strLen < 0) {
/* 110 */           return ValidationResult.error("Invalid string length in Names");
/*     */         }
/* 112 */         pos += VarInt.length(buffer, pos);
/* 113 */         pos += strLen;
/* 114 */         if (pos > buffer.writerIndex()) {
/* 115 */           return ValidationResult.error("Buffer overflow reading string in Names");
/*     */         }
/*     */       } 
/*     */     } 
/* 119 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockGroup clone() {
/* 123 */     BlockGroup copy = new BlockGroup();
/* 124 */     copy.names = (this.names != null) ? Arrays.<String>copyOf(this.names, this.names.length) : null;
/* 125 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockGroup other;
/* 131 */     if (this == obj) return true; 
/* 132 */     if (obj instanceof BlockGroup) { other = (BlockGroup)obj; } else { return false; }
/* 133 */      return Arrays.equals((Object[])this.names, (Object[])other.names);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     int result = 1;
/* 139 */     result = 31 * result + Arrays.hashCode((Object[])this.names);
/* 140 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */