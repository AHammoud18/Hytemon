/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockSet {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 32768019;
/*     */   @Nullable
/*     */   public String name;
/*     */   @Nullable
/*     */   public int[] blocks;
/*     */   
/*     */   public BlockSet() {}
/*     */   
/*     */   public BlockSet(@Nullable String name, @Nullable int[] blocks) {
/*  27 */     this.name = name;
/*  28 */     this.blocks = blocks;
/*     */   }
/*     */   
/*     */   public BlockSet(@Nonnull BlockSet other) {
/*  32 */     this.name = other.name;
/*  33 */     this.blocks = other.blocks;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     BlockSet obj = new BlockSet();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int nameLen = VarInt.peek(buf, varPos0);
/*  44 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  45 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  46 */       obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int blocksCount = VarInt.peek(buf, varPos1);
/*  51 */       if (blocksCount < 0) throw ProtocolException.negativeLength("Blocks", blocksCount); 
/*  52 */       if (blocksCount > 4096000) throw ProtocolException.arrayTooLong("Blocks", blocksCount, 4096000); 
/*  53 */       int varIntLen = VarInt.length(buf, varPos1);
/*  54 */       if ((varPos1 + varIntLen) + blocksCount * 4L > buf.readableBytes())
/*  55 */         throw ProtocolException.bufferTooSmall("Blocks", varPos1 + varIntLen + blocksCount * 4, buf.readableBytes()); 
/*  56 */       obj.blocks = new int[blocksCount];
/*  57 */       for (int i = 0; i < blocksCount; i++) {
/*  58 */         obj.blocks[i] = buf.getIntLE(varPos1 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     int maxEnd = 9;
/*  68 */     if ((nullBits & 0x1) != 0) {
/*  69 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  70 */       int pos0 = offset + 9 + fieldOffset0;
/*  71 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  72 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  74 */     if ((nullBits & 0x2) != 0) {
/*  75 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  76 */       int pos1 = offset + 9 + fieldOffset1;
/*  77 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/*  78 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  80 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     int startPos = buf.writerIndex();
/*  86 */     byte nullBits = 0;
/*  87 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/*  88 */     if (this.blocks != null) nullBits = (byte)(nullBits | 0x2); 
/*  89 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  92 */     int nameOffsetSlot = buf.writerIndex();
/*  93 */     buf.writeIntLE(0);
/*  94 */     int blocksOffsetSlot = buf.writerIndex();
/*  95 */     buf.writeIntLE(0);
/*     */     
/*  97 */     int varBlockStart = buf.writerIndex();
/*  98 */     if (this.name != null) {
/*  99 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 100 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 102 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 104 */     if (this.blocks != null) {
/* 105 */       buf.setIntLE(blocksOffsetSlot, buf.writerIndex() - varBlockStart);
/* 106 */       if (this.blocks.length > 4096000) throw ProtocolException.arrayTooLong("Blocks", this.blocks.length, 4096000);  VarInt.write(buf, this.blocks.length); for (int item : this.blocks) buf.writeIntLE(item); 
/*     */     } else {
/* 108 */       buf.setIntLE(blocksOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 114 */     int size = 9;
/* 115 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 116 */     if (this.blocks != null) size += VarInt.size(this.blocks.length) + this.blocks.length * 4;
/*     */     
/* 118 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 122 */     if (buffer.readableBytes() - offset < 9) {
/* 123 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 126 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 129 */     if ((nullBits & 0x1) != 0) {
/* 130 */       int nameOffset = buffer.getIntLE(offset + 1);
/* 131 */       if (nameOffset < 0) {
/* 132 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 134 */       int pos = offset + 9 + nameOffset;
/* 135 */       if (pos >= buffer.writerIndex()) {
/* 136 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 138 */       int nameLen = VarInt.peek(buffer, pos);
/* 139 */       if (nameLen < 0) {
/* 140 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 142 */       if (nameLen > 4096000) {
/* 143 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 145 */       pos += VarInt.length(buffer, pos);
/* 146 */       pos += nameLen;
/* 147 */       if (pos > buffer.writerIndex()) {
/* 148 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 152 */     if ((nullBits & 0x2) != 0) {
/* 153 */       int blocksOffset = buffer.getIntLE(offset + 5);
/* 154 */       if (blocksOffset < 0) {
/* 155 */         return ValidationResult.error("Invalid offset for Blocks");
/*     */       }
/* 157 */       int pos = offset + 9 + blocksOffset;
/* 158 */       if (pos >= buffer.writerIndex()) {
/* 159 */         return ValidationResult.error("Offset out of bounds for Blocks");
/*     */       }
/* 161 */       int blocksCount = VarInt.peek(buffer, pos);
/* 162 */       if (blocksCount < 0) {
/* 163 */         return ValidationResult.error("Invalid array count for Blocks");
/*     */       }
/* 165 */       if (blocksCount > 4096000) {
/* 166 */         return ValidationResult.error("Blocks exceeds max length 4096000");
/*     */       }
/* 168 */       pos += VarInt.length(buffer, pos);
/* 169 */       pos += blocksCount * 4;
/* 170 */       if (pos > buffer.writerIndex()) {
/* 171 */         return ValidationResult.error("Buffer overflow reading Blocks");
/*     */       }
/*     */     } 
/* 174 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockSet clone() {
/* 178 */     BlockSet copy = new BlockSet();
/* 179 */     copy.name = this.name;
/* 180 */     copy.blocks = (this.blocks != null) ? Arrays.copyOf(this.blocks, this.blocks.length) : null;
/* 181 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockSet other;
/* 187 */     if (this == obj) return true; 
/* 188 */     if (obj instanceof BlockSet) { other = (BlockSet)obj; } else { return false; }
/* 189 */      return (Objects.equals(this.name, other.name) && Arrays.equals(this.blocks, other.blocks));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     int result = 1;
/* 195 */     result = 31 * result + Objects.hashCode(this.name);
/* 196 */     result = 31 * result + Arrays.hashCode(this.blocks);
/* 197 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */