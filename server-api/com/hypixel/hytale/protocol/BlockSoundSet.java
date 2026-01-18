/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockSoundSet
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   
/*     */   public BlockSoundSet(@Nullable String id, @Nullable Map<BlockSoundEvent, Integer> soundEventIndices, @Nullable FloatRange moveInRepeatRange) {
/*  28 */     this.id = id;
/*  29 */     this.soundEventIndices = soundEventIndices;
/*  30 */     this.moveInRepeatRange = moveInRepeatRange; } public static final int MAX_SIZE = 36864027; @Nullable
/*     */   public String id; @Nullable
/*     */   public Map<BlockSoundEvent, Integer> soundEventIndices; @Nullable
/*     */   public FloatRange moveInRepeatRange; public BlockSoundSet() {} public BlockSoundSet(@Nonnull BlockSoundSet other) {
/*  34 */     this.id = other.id;
/*  35 */     this.soundEventIndices = other.soundEventIndices;
/*  36 */     this.moveInRepeatRange = other.moveInRepeatRange;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockSoundSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     BlockSoundSet obj = new BlockSoundSet();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x4) != 0) obj.moveInRepeatRange = FloatRange.deserialize(buf, offset + 1);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 9);
/*  47 */       int idLen = VarInt.peek(buf, varPos0);
/*  48 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  49 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  50 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  52 */     if ((nullBits & 0x2) != 0) {
/*  53 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 13);
/*  54 */       int soundEventIndicesCount = VarInt.peek(buf, varPos1);
/*  55 */       if (soundEventIndicesCount < 0) throw ProtocolException.negativeLength("SoundEventIndices", soundEventIndicesCount); 
/*  56 */       if (soundEventIndicesCount > 4096000) throw ProtocolException.dictionaryTooLarge("SoundEventIndices", soundEventIndicesCount, 4096000); 
/*  57 */       int varIntLen = VarInt.length(buf, varPos1);
/*  58 */       obj.soundEventIndices = new HashMap<>(soundEventIndicesCount);
/*  59 */       int dictPos = varPos1 + varIntLen;
/*  60 */       for (int i = 0; i < soundEventIndicesCount; i++) {
/*  61 */         BlockSoundEvent key = BlockSoundEvent.fromValue(buf.getByte(dictPos)); dictPos++;
/*  62 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  63 */         if (obj.soundEventIndices.put(key, Integer.valueOf(val)) != null) {
/*  64 */           throw ProtocolException.duplicateKey("soundEventIndices", key);
/*     */         }
/*     */       } 
/*     */     } 
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 17;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  76 */       int pos0 = offset + 17 + fieldOffset0;
/*  77 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/*  82 */       int pos1 = offset + 17 + fieldOffset1;
/*  83 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  84 */       for (int i = 0; i < dictLen; ) { pos1++; pos1 += 4; i++; }
/*  85 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  87 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  92 */     int startPos = buf.writerIndex();
/*  93 */     byte nullBits = 0;
/*  94 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  95 */     if (this.soundEventIndices != null) nullBits = (byte)(nullBits | 0x2); 
/*  96 */     if (this.moveInRepeatRange != null) nullBits = (byte)(nullBits | 0x4); 
/*  97 */     buf.writeByte(nullBits);
/*     */     
/*  99 */     if (this.moveInRepeatRange != null) { this.moveInRepeatRange.serialize(buf); } else { buf.writeZero(8); }
/*     */     
/* 101 */     int idOffsetSlot = buf.writerIndex();
/* 102 */     buf.writeIntLE(0);
/* 103 */     int soundEventIndicesOffsetSlot = buf.writerIndex();
/* 104 */     buf.writeIntLE(0);
/*     */     
/* 106 */     int varBlockStart = buf.writerIndex();
/* 107 */     if (this.id != null) {
/* 108 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 111 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 113 */     if (this.soundEventIndices != null)
/* 114 */     { buf.setIntLE(soundEventIndicesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 115 */       if (this.soundEventIndices.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SoundEventIndices", this.soundEventIndices.size(), 4096000);  VarInt.write(buf, this.soundEventIndices.size()); for (Map.Entry<BlockSoundEvent, Integer> e : this.soundEventIndices.entrySet()) { buf.writeByte(((BlockSoundEvent)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 117 */     else { buf.setIntLE(soundEventIndicesOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 123 */     int size = 17;
/* 124 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 125 */     if (this.soundEventIndices != null) size += VarInt.size(this.soundEventIndices.size()) + this.soundEventIndices.size() * 5;
/*     */     
/* 127 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 131 */     if (buffer.readableBytes() - offset < 17) {
/* 132 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 135 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 138 */     if ((nullBits & 0x1) != 0) {
/* 139 */       int idOffset = buffer.getIntLE(offset + 9);
/* 140 */       if (idOffset < 0) {
/* 141 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 143 */       int pos = offset + 17 + idOffset;
/* 144 */       if (pos >= buffer.writerIndex()) {
/* 145 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 147 */       int idLen = VarInt.peek(buffer, pos);
/* 148 */       if (idLen < 0) {
/* 149 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 151 */       if (idLen > 4096000) {
/* 152 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 154 */       pos += VarInt.length(buffer, pos);
/* 155 */       pos += idLen;
/* 156 */       if (pos > buffer.writerIndex()) {
/* 157 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 161 */     if ((nullBits & 0x2) != 0) {
/* 162 */       int soundEventIndicesOffset = buffer.getIntLE(offset + 13);
/* 163 */       if (soundEventIndicesOffset < 0) {
/* 164 */         return ValidationResult.error("Invalid offset for SoundEventIndices");
/*     */       }
/* 166 */       int pos = offset + 17 + soundEventIndicesOffset;
/* 167 */       if (pos >= buffer.writerIndex()) {
/* 168 */         return ValidationResult.error("Offset out of bounds for SoundEventIndices");
/*     */       }
/* 170 */       int soundEventIndicesCount = VarInt.peek(buffer, pos);
/* 171 */       if (soundEventIndicesCount < 0) {
/* 172 */         return ValidationResult.error("Invalid dictionary count for SoundEventIndices");
/*     */       }
/* 174 */       if (soundEventIndicesCount > 4096000) {
/* 175 */         return ValidationResult.error("SoundEventIndices exceeds max length 4096000");
/*     */       }
/* 177 */       pos += VarInt.length(buffer, pos);
/* 178 */       for (int i = 0; i < soundEventIndicesCount; i++) {
/* 179 */         pos++;
/*     */         
/* 181 */         pos += 4;
/* 182 */         if (pos > buffer.writerIndex()) {
/* 183 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 187 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockSoundSet clone() {
/* 191 */     BlockSoundSet copy = new BlockSoundSet();
/* 192 */     copy.id = this.id;
/* 193 */     copy.soundEventIndices = (this.soundEventIndices != null) ? new HashMap<>(this.soundEventIndices) : null;
/* 194 */     copy.moveInRepeatRange = (this.moveInRepeatRange != null) ? this.moveInRepeatRange.clone() : null;
/* 195 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockSoundSet other;
/* 201 */     if (this == obj) return true; 
/* 202 */     if (obj instanceof BlockSoundSet) { other = (BlockSoundSet)obj; } else { return false; }
/* 203 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.soundEventIndices, other.soundEventIndices) && Objects.equals(this.moveInRepeatRange, other.moveInRepeatRange));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 208 */     return Objects.hash(new Object[] { this.id, this.soundEventIndices, this.moveInRepeatRange });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockSoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */