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
/*     */ public class BlockIdMatcher
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 32768023;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public String state;
/*     */   public int tagIndex;
/*     */   
/*     */   public BlockIdMatcher() {}
/*     */   
/*     */   public BlockIdMatcher(@Nullable String id, @Nullable String state, int tagIndex) {
/*  28 */     this.id = id;
/*  29 */     this.state = state;
/*  30 */     this.tagIndex = tagIndex;
/*     */   }
/*     */   
/*     */   public BlockIdMatcher(@Nonnull BlockIdMatcher other) {
/*  34 */     this.id = other.id;
/*  35 */     this.state = other.state;
/*  36 */     this.tagIndex = other.tagIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockIdMatcher deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     BlockIdMatcher obj = new BlockIdMatcher();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.tagIndex = buf.getIntLE(offset + 1);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 5);
/*  47 */       int idLen = VarInt.peek(buf, varPos0);
/*  48 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  49 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  50 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  52 */     if ((nullBits & 0x2) != 0) {
/*  53 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 9);
/*  54 */       int stateLen = VarInt.peek(buf, varPos1);
/*  55 */       if (stateLen < 0) throw ProtocolException.negativeLength("State", stateLen); 
/*  56 */       if (stateLen > 4096000) throw ProtocolException.stringTooLong("State", stateLen, 4096000); 
/*  57 */       obj.state = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int maxEnd = 13;
/*  66 */     if ((nullBits & 0x1) != 0) {
/*  67 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/*  68 */       int pos0 = offset + 13 + fieldOffset0;
/*  69 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  70 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  72 */     if ((nullBits & 0x2) != 0) {
/*  73 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/*  74 */       int pos1 = offset + 13 + fieldOffset1;
/*  75 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  76 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  78 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  83 */     int startPos = buf.writerIndex();
/*  84 */     byte nullBits = 0;
/*  85 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  86 */     if (this.state != null) nullBits = (byte)(nullBits | 0x2); 
/*  87 */     buf.writeByte(nullBits);
/*     */     
/*  89 */     buf.writeIntLE(this.tagIndex);
/*     */     
/*  91 */     int idOffsetSlot = buf.writerIndex();
/*  92 */     buf.writeIntLE(0);
/*  93 */     int stateOffsetSlot = buf.writerIndex();
/*  94 */     buf.writeIntLE(0);
/*     */     
/*  96 */     int varBlockStart = buf.writerIndex();
/*  97 */     if (this.id != null) {
/*  98 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/*  99 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 101 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 103 */     if (this.state != null) {
/* 104 */       buf.setIntLE(stateOffsetSlot, buf.writerIndex() - varBlockStart);
/* 105 */       PacketIO.writeVarString(buf, this.state, 4096000);
/*     */     } else {
/* 107 */       buf.setIntLE(stateOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 113 */     int size = 13;
/* 114 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 115 */     if (this.state != null) size += PacketIO.stringSize(this.state);
/*     */     
/* 117 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 121 */     if (buffer.readableBytes() - offset < 13) {
/* 122 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 125 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 128 */     if ((nullBits & 0x1) != 0) {
/* 129 */       int idOffset = buffer.getIntLE(offset + 5);
/* 130 */       if (idOffset < 0) {
/* 131 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 133 */       int pos = offset + 13 + idOffset;
/* 134 */       if (pos >= buffer.writerIndex()) {
/* 135 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 137 */       int idLen = VarInt.peek(buffer, pos);
/* 138 */       if (idLen < 0) {
/* 139 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 141 */       if (idLen > 4096000) {
/* 142 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 144 */       pos += VarInt.length(buffer, pos);
/* 145 */       pos += idLen;
/* 146 */       if (pos > buffer.writerIndex()) {
/* 147 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 151 */     if ((nullBits & 0x2) != 0) {
/* 152 */       int stateOffset = buffer.getIntLE(offset + 9);
/* 153 */       if (stateOffset < 0) {
/* 154 */         return ValidationResult.error("Invalid offset for State");
/*     */       }
/* 156 */       int pos = offset + 13 + stateOffset;
/* 157 */       if (pos >= buffer.writerIndex()) {
/* 158 */         return ValidationResult.error("Offset out of bounds for State");
/*     */       }
/* 160 */       int stateLen = VarInt.peek(buffer, pos);
/* 161 */       if (stateLen < 0) {
/* 162 */         return ValidationResult.error("Invalid string length for State");
/*     */       }
/* 164 */       if (stateLen > 4096000) {
/* 165 */         return ValidationResult.error("State exceeds max length 4096000");
/*     */       }
/* 167 */       pos += VarInt.length(buffer, pos);
/* 168 */       pos += stateLen;
/* 169 */       if (pos > buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Buffer overflow reading State");
/*     */       }
/*     */     } 
/* 173 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockIdMatcher clone() {
/* 177 */     BlockIdMatcher copy = new BlockIdMatcher();
/* 178 */     copy.id = this.id;
/* 179 */     copy.state = this.state;
/* 180 */     copy.tagIndex = this.tagIndex;
/* 181 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockIdMatcher other;
/* 187 */     if (this == obj) return true; 
/* 188 */     if (obj instanceof BlockIdMatcher) { other = (BlockIdMatcher)obj; } else { return false; }
/* 189 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.state, other.state) && this.tagIndex == other.tagIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     return Objects.hash(new Object[] { this.id, this.state, Integer.valueOf(this.tagIndex) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockIdMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */