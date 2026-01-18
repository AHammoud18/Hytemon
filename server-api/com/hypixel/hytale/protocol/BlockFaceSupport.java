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
/*     */ public class BlockFaceSupport {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 65536019;
/*     */   @Nullable
/*     */   public String faceType;
/*     */   @Nullable
/*     */   public Vector3i[] filler;
/*     */   
/*     */   public BlockFaceSupport() {}
/*     */   
/*     */   public BlockFaceSupport(@Nullable String faceType, @Nullable Vector3i[] filler) {
/*  27 */     this.faceType = faceType;
/*  28 */     this.filler = filler;
/*     */   }
/*     */   
/*     */   public BlockFaceSupport(@Nonnull BlockFaceSupport other) {
/*  32 */     this.faceType = other.faceType;
/*  33 */     this.filler = other.filler;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockFaceSupport deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     BlockFaceSupport obj = new BlockFaceSupport();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int faceTypeLen = VarInt.peek(buf, varPos0);
/*  44 */       if (faceTypeLen < 0) throw ProtocolException.negativeLength("FaceType", faceTypeLen); 
/*  45 */       if (faceTypeLen > 4096000) throw ProtocolException.stringTooLong("FaceType", faceTypeLen, 4096000); 
/*  46 */       obj.faceType = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int fillerCount = VarInt.peek(buf, varPos1);
/*  51 */       if (fillerCount < 0) throw ProtocolException.negativeLength("Filler", fillerCount); 
/*  52 */       if (fillerCount > 4096000) throw ProtocolException.arrayTooLong("Filler", fillerCount, 4096000); 
/*  53 */       int varIntLen = VarInt.length(buf, varPos1);
/*  54 */       if ((varPos1 + varIntLen) + fillerCount * 12L > buf.readableBytes())
/*  55 */         throw ProtocolException.bufferTooSmall("Filler", varPos1 + varIntLen + fillerCount * 12, buf.readableBytes()); 
/*  56 */       obj.filler = new Vector3i[fillerCount];
/*  57 */       int elemPos = varPos1 + varIntLen;
/*  58 */       for (int i = 0; i < fillerCount; i++) {
/*  59 */         obj.filler[i] = Vector3i.deserialize(buf, elemPos);
/*  60 */         elemPos += Vector3i.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int maxEnd = 9;
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  72 */       int pos0 = offset + 9 + fieldOffset0;
/*  73 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  74 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  76 */     if ((nullBits & 0x2) != 0) {
/*  77 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  78 */       int pos1 = offset + 9 + fieldOffset1;
/*  79 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  80 */       for (int i = 0; i < arrLen; ) { pos1 += Vector3i.computeBytesConsumed(buf, pos1); i++; }
/*  81 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  83 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  88 */     int startPos = buf.writerIndex();
/*  89 */     byte nullBits = 0;
/*  90 */     if (this.faceType != null) nullBits = (byte)(nullBits | 0x1); 
/*  91 */     if (this.filler != null) nullBits = (byte)(nullBits | 0x2); 
/*  92 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  95 */     int faceTypeOffsetSlot = buf.writerIndex();
/*  96 */     buf.writeIntLE(0);
/*  97 */     int fillerOffsetSlot = buf.writerIndex();
/*  98 */     buf.writeIntLE(0);
/*     */     
/* 100 */     int varBlockStart = buf.writerIndex();
/* 101 */     if (this.faceType != null) {
/* 102 */       buf.setIntLE(faceTypeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 103 */       PacketIO.writeVarString(buf, this.faceType, 4096000);
/*     */     } else {
/* 105 */       buf.setIntLE(faceTypeOffsetSlot, -1);
/*     */     } 
/* 107 */     if (this.filler != null) {
/* 108 */       buf.setIntLE(fillerOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       if (this.filler.length > 4096000) throw ProtocolException.arrayTooLong("Filler", this.filler.length, 4096000);  VarInt.write(buf, this.filler.length); for (Vector3i item : this.filler) item.serialize(buf); 
/*     */     } else {
/* 111 */       buf.setIntLE(fillerOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 117 */     int size = 9;
/* 118 */     if (this.faceType != null) size += PacketIO.stringSize(this.faceType); 
/* 119 */     if (this.filler != null) size += VarInt.size(this.filler.length) + this.filler.length * 12;
/*     */     
/* 121 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 125 */     if (buffer.readableBytes() - offset < 9) {
/* 126 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 129 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 132 */     if ((nullBits & 0x1) != 0) {
/* 133 */       int faceTypeOffset = buffer.getIntLE(offset + 1);
/* 134 */       if (faceTypeOffset < 0) {
/* 135 */         return ValidationResult.error("Invalid offset for FaceType");
/*     */       }
/* 137 */       int pos = offset + 9 + faceTypeOffset;
/* 138 */       if (pos >= buffer.writerIndex()) {
/* 139 */         return ValidationResult.error("Offset out of bounds for FaceType");
/*     */       }
/* 141 */       int faceTypeLen = VarInt.peek(buffer, pos);
/* 142 */       if (faceTypeLen < 0) {
/* 143 */         return ValidationResult.error("Invalid string length for FaceType");
/*     */       }
/* 145 */       if (faceTypeLen > 4096000) {
/* 146 */         return ValidationResult.error("FaceType exceeds max length 4096000");
/*     */       }
/* 148 */       pos += VarInt.length(buffer, pos);
/* 149 */       pos += faceTypeLen;
/* 150 */       if (pos > buffer.writerIndex()) {
/* 151 */         return ValidationResult.error("Buffer overflow reading FaceType");
/*     */       }
/*     */     } 
/*     */     
/* 155 */     if ((nullBits & 0x2) != 0) {
/* 156 */       int fillerOffset = buffer.getIntLE(offset + 5);
/* 157 */       if (fillerOffset < 0) {
/* 158 */         return ValidationResult.error("Invalid offset for Filler");
/*     */       }
/* 160 */       int pos = offset + 9 + fillerOffset;
/* 161 */       if (pos >= buffer.writerIndex()) {
/* 162 */         return ValidationResult.error("Offset out of bounds for Filler");
/*     */       }
/* 164 */       int fillerCount = VarInt.peek(buffer, pos);
/* 165 */       if (fillerCount < 0) {
/* 166 */         return ValidationResult.error("Invalid array count for Filler");
/*     */       }
/* 168 */       if (fillerCount > 4096000) {
/* 169 */         return ValidationResult.error("Filler exceeds max length 4096000");
/*     */       }
/* 171 */       pos += VarInt.length(buffer, pos);
/* 172 */       pos += fillerCount * 12;
/* 173 */       if (pos > buffer.writerIndex()) {
/* 174 */         return ValidationResult.error("Buffer overflow reading Filler");
/*     */       }
/*     */     } 
/* 177 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockFaceSupport clone() {
/* 181 */     BlockFaceSupport copy = new BlockFaceSupport();
/* 182 */     copy.faceType = this.faceType;
/* 183 */     copy.filler = (this.filler != null) ? (Vector3i[])Arrays.<Vector3i>stream(this.filler).map(e -> e.clone()).toArray(x$0 -> new Vector3i[x$0]) : null;
/* 184 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockFaceSupport other;
/* 190 */     if (this == obj) return true; 
/* 191 */     if (obj instanceof BlockFaceSupport) { other = (BlockFaceSupport)obj; } else { return false; }
/* 192 */      return (Objects.equals(this.faceType, other.faceType) && Arrays.equals((Object[])this.filler, (Object[])other.filler));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     int result = 1;
/* 198 */     result = 31 * result + Objects.hashCode(this.faceType);
/* 199 */     result = 31 * result + Arrays.hashCode((Object[])this.filler);
/* 200 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockFaceSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */