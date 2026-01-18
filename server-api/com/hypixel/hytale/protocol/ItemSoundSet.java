/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ItemSoundSet {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 36864019;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public Map<ItemSoundEvent, Integer> soundEventIndices;
/*     */   
/*     */   public ItemSoundSet() {}
/*     */   
/*     */   public ItemSoundSet(@Nullable String id, @Nullable Map<ItemSoundEvent, Integer> soundEventIndices) {
/*  27 */     this.id = id;
/*  28 */     this.soundEventIndices = soundEventIndices;
/*     */   }
/*     */   
/*     */   public ItemSoundSet(@Nonnull ItemSoundSet other) {
/*  32 */     this.id = other.id;
/*  33 */     this.soundEventIndices = other.soundEventIndices;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemSoundSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ItemSoundSet obj = new ItemSoundSet();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int idLen = VarInt.peek(buf, varPos0);
/*  44 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  45 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  46 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int soundEventIndicesCount = VarInt.peek(buf, varPos1);
/*  51 */       if (soundEventIndicesCount < 0) throw ProtocolException.negativeLength("SoundEventIndices", soundEventIndicesCount); 
/*  52 */       if (soundEventIndicesCount > 4096000) throw ProtocolException.dictionaryTooLarge("SoundEventIndices", soundEventIndicesCount, 4096000); 
/*  53 */       int varIntLen = VarInt.length(buf, varPos1);
/*  54 */       obj.soundEventIndices = new HashMap<>(soundEventIndicesCount);
/*  55 */       int dictPos = varPos1 + varIntLen;
/*  56 */       for (int i = 0; i < soundEventIndicesCount; i++) {
/*  57 */         ItemSoundEvent key = ItemSoundEvent.fromValue(buf.getByte(dictPos)); dictPos++;
/*  58 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  59 */         if (obj.soundEventIndices.put(key, Integer.valueOf(val)) != null) {
/*  60 */           throw ProtocolException.duplicateKey("soundEventIndices", key);
/*     */         }
/*     */       } 
/*     */     } 
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
/*  79 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  80 */       for (int i = 0; i < dictLen; ) { pos1++; pos1 += 4; i++; }
/*  81 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  83 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  88 */     int startPos = buf.writerIndex();
/*  89 */     byte nullBits = 0;
/*  90 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  91 */     if (this.soundEventIndices != null) nullBits = (byte)(nullBits | 0x2); 
/*  92 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  95 */     int idOffsetSlot = buf.writerIndex();
/*  96 */     buf.writeIntLE(0);
/*  97 */     int soundEventIndicesOffsetSlot = buf.writerIndex();
/*  98 */     buf.writeIntLE(0);
/*     */     
/* 100 */     int varBlockStart = buf.writerIndex();
/* 101 */     if (this.id != null) {
/* 102 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 103 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 105 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 107 */     if (this.soundEventIndices != null)
/* 108 */     { buf.setIntLE(soundEventIndicesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       if (this.soundEventIndices.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SoundEventIndices", this.soundEventIndices.size(), 4096000);  VarInt.write(buf, this.soundEventIndices.size()); for (Map.Entry<ItemSoundEvent, Integer> e : this.soundEventIndices.entrySet()) { buf.writeByte(((ItemSoundEvent)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 111 */     else { buf.setIntLE(soundEventIndicesOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 117 */     int size = 9;
/* 118 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 119 */     if (this.soundEventIndices != null) size += VarInt.size(this.soundEventIndices.size()) + this.soundEventIndices.size() * 5;
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
/* 133 */       int idOffset = buffer.getIntLE(offset + 1);
/* 134 */       if (idOffset < 0) {
/* 135 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 137 */       int pos = offset + 9 + idOffset;
/* 138 */       if (pos >= buffer.writerIndex()) {
/* 139 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 141 */       int idLen = VarInt.peek(buffer, pos);
/* 142 */       if (idLen < 0) {
/* 143 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 145 */       if (idLen > 4096000) {
/* 146 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 148 */       pos += VarInt.length(buffer, pos);
/* 149 */       pos += idLen;
/* 150 */       if (pos > buffer.writerIndex()) {
/* 151 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 155 */     if ((nullBits & 0x2) != 0) {
/* 156 */       int soundEventIndicesOffset = buffer.getIntLE(offset + 5);
/* 157 */       if (soundEventIndicesOffset < 0) {
/* 158 */         return ValidationResult.error("Invalid offset for SoundEventIndices");
/*     */       }
/* 160 */       int pos = offset + 9 + soundEventIndicesOffset;
/* 161 */       if (pos >= buffer.writerIndex()) {
/* 162 */         return ValidationResult.error("Offset out of bounds for SoundEventIndices");
/*     */       }
/* 164 */       int soundEventIndicesCount = VarInt.peek(buffer, pos);
/* 165 */       if (soundEventIndicesCount < 0) {
/* 166 */         return ValidationResult.error("Invalid dictionary count for SoundEventIndices");
/*     */       }
/* 168 */       if (soundEventIndicesCount > 4096000) {
/* 169 */         return ValidationResult.error("SoundEventIndices exceeds max length 4096000");
/*     */       }
/* 171 */       pos += VarInt.length(buffer, pos);
/* 172 */       for (int i = 0; i < soundEventIndicesCount; i++) {
/* 173 */         pos++;
/*     */         
/* 175 */         pos += 4;
/* 176 */         if (pos > buffer.writerIndex()) {
/* 177 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 181 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemSoundSet clone() {
/* 185 */     ItemSoundSet copy = new ItemSoundSet();
/* 186 */     copy.id = this.id;
/* 187 */     copy.soundEventIndices = (this.soundEventIndices != null) ? new HashMap<>(this.soundEventIndices) : null;
/* 188 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemSoundSet other;
/* 194 */     if (this == obj) return true; 
/* 195 */     if (obj instanceof ItemSoundSet) { other = (ItemSoundSet)obj; } else { return false; }
/* 196 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.soundEventIndices, other.soundEventIndices));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 201 */     return Objects.hash(new Object[] { this.id, this.soundEventIndices });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemSoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */