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
/*     */ public class SoundSet
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   @Nonnull
/*  22 */   public SoundCategory category = SoundCategory.Music; public static final int VARIABLE_BLOCK_START = 10; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public Map<String, Integer> sounds;
/*     */   
/*     */   public SoundSet(@Nullable String id, @Nullable Map<String, Integer> sounds, @Nonnull SoundCategory category) {
/*  28 */     this.id = id;
/*  29 */     this.sounds = sounds;
/*  30 */     this.category = category;
/*     */   }
/*     */   
/*     */   public SoundSet(@Nonnull SoundSet other) {
/*  34 */     this.id = other.id;
/*  35 */     this.sounds = other.sounds;
/*  36 */     this.category = other.category;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SoundSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     SoundSet obj = new SoundSet();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.category = SoundCategory.fromValue(buf.getByte(offset + 1));
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 10 + buf.getIntLE(offset + 2);
/*  47 */       int idLen = VarInt.peek(buf, varPos0);
/*  48 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  49 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  50 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  52 */     if ((nullBits & 0x2) != 0) {
/*  53 */       int varPos1 = offset + 10 + buf.getIntLE(offset + 6);
/*  54 */       int soundsCount = VarInt.peek(buf, varPos1);
/*  55 */       if (soundsCount < 0) throw ProtocolException.negativeLength("Sounds", soundsCount); 
/*  56 */       if (soundsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Sounds", soundsCount, 4096000); 
/*  57 */       int varIntLen = VarInt.length(buf, varPos1);
/*  58 */       obj.sounds = new HashMap<>(soundsCount);
/*  59 */       int dictPos = varPos1 + varIntLen;
/*  60 */       for (int i = 0; i < soundsCount; i++) {
/*  61 */         int keyLen = VarInt.peek(buf, dictPos);
/*  62 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  63 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  64 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  65 */         String key = PacketIO.readVarString(buf, dictPos);
/*  66 */         dictPos += keyVarLen + keyLen;
/*  67 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  68 */         if (obj.sounds.put(key, Integer.valueOf(val)) != null) {
/*  69 */           throw ProtocolException.duplicateKey("sounds", key);
/*     */         }
/*     */       } 
/*     */     } 
/*  73 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  77 */     byte nullBits = buf.getByte(offset);
/*  78 */     int maxEnd = 10;
/*  79 */     if ((nullBits & 0x1) != 0) {
/*  80 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  81 */       int pos0 = offset + 10 + fieldOffset0;
/*  82 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  83 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  85 */     if ((nullBits & 0x2) != 0) {
/*  86 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  87 */       int pos1 = offset + 10 + fieldOffset1;
/*  88 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  89 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; pos1 += 4; i++; }
/*  90 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  92 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  97 */     int startPos = buf.writerIndex();
/*  98 */     byte nullBits = 0;
/*  99 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 100 */     if (this.sounds != null) nullBits = (byte)(nullBits | 0x2); 
/* 101 */     buf.writeByte(nullBits);
/*     */     
/* 103 */     buf.writeByte(this.category.getValue());
/*     */     
/* 105 */     int idOffsetSlot = buf.writerIndex();
/* 106 */     buf.writeIntLE(0);
/* 107 */     int soundsOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/*     */     
/* 110 */     int varBlockStart = buf.writerIndex();
/* 111 */     if (this.id != null) {
/* 112 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 113 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 115 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 117 */     if (this.sounds != null)
/* 118 */     { buf.setIntLE(soundsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 119 */       if (this.sounds.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Sounds", this.sounds.size(), 4096000);  VarInt.write(buf, this.sounds.size()); for (Map.Entry<String, Integer> e : this.sounds.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 121 */     else { buf.setIntLE(soundsOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 127 */     int size = 10;
/* 128 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 129 */     if (this.sounds != null) {
/* 130 */       int soundsSize = 0;
/* 131 */       for (Map.Entry<String, Integer> kvp : this.sounds.entrySet()) soundsSize += PacketIO.stringSize(kvp.getKey()) + 4; 
/* 132 */       size += VarInt.size(this.sounds.size()) + soundsSize;
/*     */     } 
/*     */     
/* 135 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 139 */     if (buffer.readableBytes() - offset < 10) {
/* 140 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 143 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 146 */     if ((nullBits & 0x1) != 0) {
/* 147 */       int idOffset = buffer.getIntLE(offset + 2);
/* 148 */       if (idOffset < 0) {
/* 149 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 151 */       int pos = offset + 10 + idOffset;
/* 152 */       if (pos >= buffer.writerIndex()) {
/* 153 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 155 */       int idLen = VarInt.peek(buffer, pos);
/* 156 */       if (idLen < 0) {
/* 157 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 159 */       if (idLen > 4096000) {
/* 160 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 162 */       pos += VarInt.length(buffer, pos);
/* 163 */       pos += idLen;
/* 164 */       if (pos > buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 169 */     if ((nullBits & 0x2) != 0) {
/* 170 */       int soundsOffset = buffer.getIntLE(offset + 6);
/* 171 */       if (soundsOffset < 0) {
/* 172 */         return ValidationResult.error("Invalid offset for Sounds");
/*     */       }
/* 174 */       int pos = offset + 10 + soundsOffset;
/* 175 */       if (pos >= buffer.writerIndex()) {
/* 176 */         return ValidationResult.error("Offset out of bounds for Sounds");
/*     */       }
/* 178 */       int soundsCount = VarInt.peek(buffer, pos);
/* 179 */       if (soundsCount < 0) {
/* 180 */         return ValidationResult.error("Invalid dictionary count for Sounds");
/*     */       }
/* 182 */       if (soundsCount > 4096000) {
/* 183 */         return ValidationResult.error("Sounds exceeds max length 4096000");
/*     */       }
/* 185 */       pos += VarInt.length(buffer, pos);
/* 186 */       for (int i = 0; i < soundsCount; i++) {
/* 187 */         int keyLen = VarInt.peek(buffer, pos);
/* 188 */         if (keyLen < 0) {
/* 189 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 191 */         if (keyLen > 4096000) {
/* 192 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 194 */         pos += VarInt.length(buffer, pos);
/* 195 */         pos += keyLen;
/* 196 */         if (pos > buffer.writerIndex()) {
/* 197 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 199 */         pos += 4;
/* 200 */         if (pos > buffer.writerIndex()) {
/* 201 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 205 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SoundSet clone() {
/* 209 */     SoundSet copy = new SoundSet();
/* 210 */     copy.id = this.id;
/* 211 */     copy.sounds = (this.sounds != null) ? new HashMap<>(this.sounds) : null;
/* 212 */     copy.category = this.category;
/* 213 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SoundSet other;
/* 219 */     if (this == obj) return true; 
/* 220 */     if (obj instanceof SoundSet) { other = (SoundSet)obj; } else { return false; }
/* 221 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.sounds, other.sounds) && Objects.equals(this.category, other.category));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 226 */     return Objects.hash(new Object[] { this.id, this.sounds, this.category });
/*     */   }
/*     */   
/*     */   public SoundSet() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */