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
/*     */ public class EntityStatUpdate {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public EntityStatOp op = EntityStatOp.Init;
/*     */   public boolean predictable;
/*     */   public float value;
/*     */   @Nullable
/*     */   public Map<String, Modifier> modifiers;
/*     */   @Nullable
/*     */   public String modifierKey;
/*     */   @Nullable
/*     */   public Modifier modifier;
/*     */   
/*     */   public EntityStatUpdate(@Nonnull EntityStatOp op, boolean predictable, float value, @Nullable Map<String, Modifier> modifiers, @Nullable String modifierKey, @Nullable Modifier modifier) {
/*  31 */     this.op = op;
/*  32 */     this.predictable = predictable;
/*  33 */     this.value = value;
/*  34 */     this.modifiers = modifiers;
/*  35 */     this.modifierKey = modifierKey;
/*  36 */     this.modifier = modifier;
/*     */   }
/*     */   
/*     */   public EntityStatUpdate(@Nonnull EntityStatUpdate other) {
/*  40 */     this.op = other.op;
/*  41 */     this.predictable = other.predictable;
/*  42 */     this.value = other.value;
/*  43 */     this.modifiers = other.modifiers;
/*  44 */     this.modifierKey = other.modifierKey;
/*  45 */     this.modifier = other.modifier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityStatUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     EntityStatUpdate obj = new EntityStatUpdate();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.op = EntityStatOp.fromValue(buf.getByte(offset + 1));
/*  53 */     obj.predictable = (buf.getByte(offset + 2) != 0);
/*  54 */     obj.value = buf.getFloatLE(offset + 3);
/*  55 */     if ((nullBits & 0x4) != 0) obj.modifier = Modifier.deserialize(buf, offset + 7);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 21 + buf.getIntLE(offset + 13);
/*  59 */       int modifiersCount = VarInt.peek(buf, varPos0);
/*  60 */       if (modifiersCount < 0) throw ProtocolException.negativeLength("Modifiers", modifiersCount); 
/*  61 */       if (modifiersCount > 4096000) throw ProtocolException.dictionaryTooLarge("Modifiers", modifiersCount, 4096000); 
/*  62 */       int varIntLen = VarInt.length(buf, varPos0);
/*  63 */       obj.modifiers = new HashMap<>(modifiersCount);
/*  64 */       int dictPos = varPos0 + varIntLen;
/*  65 */       for (int i = 0; i < modifiersCount; i++) {
/*  66 */         int keyLen = VarInt.peek(buf, dictPos);
/*  67 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  68 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  69 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  70 */         String key = PacketIO.readVarString(buf, dictPos);
/*  71 */         dictPos += keyVarLen + keyLen;
/*  72 */         Modifier val = Modifier.deserialize(buf, dictPos);
/*  73 */         dictPos += Modifier.computeBytesConsumed(buf, dictPos);
/*  74 */         if (obj.modifiers.put(key, val) != null)
/*  75 */           throw ProtocolException.duplicateKey("modifiers", key); 
/*     */       } 
/*     */     } 
/*  78 */     if ((nullBits & 0x2) != 0) {
/*  79 */       int varPos1 = offset + 21 + buf.getIntLE(offset + 17);
/*  80 */       int modifierKeyLen = VarInt.peek(buf, varPos1);
/*  81 */       if (modifierKeyLen < 0) throw ProtocolException.negativeLength("ModifierKey", modifierKeyLen); 
/*  82 */       if (modifierKeyLen > 4096000) throw ProtocolException.stringTooLong("ModifierKey", modifierKeyLen, 4096000); 
/*  83 */       obj.modifierKey = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  86 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  90 */     byte nullBits = buf.getByte(offset);
/*  91 */     int maxEnd = 21;
/*  92 */     if ((nullBits & 0x1) != 0) {
/*  93 */       int fieldOffset0 = buf.getIntLE(offset + 13);
/*  94 */       int pos0 = offset + 21 + fieldOffset0;
/*  95 */       int dictLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  96 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl; pos0 += Modifier.computeBytesConsumed(buf, pos0); i++; }
/*  97 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  99 */     if ((nullBits & 0x2) != 0) {
/* 100 */       int fieldOffset1 = buf.getIntLE(offset + 17);
/* 101 */       int pos1 = offset + 21 + fieldOffset1;
/* 102 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 103 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 105 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 110 */     int startPos = buf.writerIndex();
/* 111 */     byte nullBits = 0;
/* 112 */     if (this.modifiers != null) nullBits = (byte)(nullBits | 0x1); 
/* 113 */     if (this.modifierKey != null) nullBits = (byte)(nullBits | 0x2); 
/* 114 */     if (this.modifier != null) nullBits = (byte)(nullBits | 0x4); 
/* 115 */     buf.writeByte(nullBits);
/*     */     
/* 117 */     buf.writeByte(this.op.getValue());
/* 118 */     buf.writeByte(this.predictable ? 1 : 0);
/* 119 */     buf.writeFloatLE(this.value);
/* 120 */     if (this.modifier != null) { this.modifier.serialize(buf); } else { buf.writeZero(6); }
/*     */     
/* 122 */     int modifiersOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/* 124 */     int modifierKeyOffsetSlot = buf.writerIndex();
/* 125 */     buf.writeIntLE(0);
/*     */     
/* 127 */     int varBlockStart = buf.writerIndex();
/* 128 */     if (this.modifiers != null)
/* 129 */     { buf.setIntLE(modifiersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 130 */       if (this.modifiers.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Modifiers", this.modifiers.size(), 4096000);  VarInt.write(buf, this.modifiers.size()); for (Map.Entry<String, Modifier> e : this.modifiers.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((Modifier)e.getValue()).serialize(buf); }
/*     */        }
/* 132 */     else { buf.setIntLE(modifiersOffsetSlot, -1); }
/*     */     
/* 134 */     if (this.modifierKey != null) {
/* 135 */       buf.setIntLE(modifierKeyOffsetSlot, buf.writerIndex() - varBlockStart);
/* 136 */       PacketIO.writeVarString(buf, this.modifierKey, 4096000);
/*     */     } else {
/* 138 */       buf.setIntLE(modifierKeyOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 144 */     int size = 21;
/* 145 */     if (this.modifiers != null) {
/* 146 */       int modifiersSize = 0;
/* 147 */       for (Map.Entry<String, Modifier> kvp : this.modifiers.entrySet()) modifiersSize += PacketIO.stringSize(kvp.getKey()) + 6; 
/* 148 */       size += VarInt.size(this.modifiers.size()) + modifiersSize;
/*     */     } 
/* 150 */     if (this.modifierKey != null) size += PacketIO.stringSize(this.modifierKey);
/*     */     
/* 152 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 156 */     if (buffer.readableBytes() - offset < 21) {
/* 157 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/* 160 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 163 */     if ((nullBits & 0x1) != 0) {
/* 164 */       int modifiersOffset = buffer.getIntLE(offset + 13);
/* 165 */       if (modifiersOffset < 0) {
/* 166 */         return ValidationResult.error("Invalid offset for Modifiers");
/*     */       }
/* 168 */       int pos = offset + 21 + modifiersOffset;
/* 169 */       if (pos >= buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Offset out of bounds for Modifiers");
/*     */       }
/* 172 */       int modifiersCount = VarInt.peek(buffer, pos);
/* 173 */       if (modifiersCount < 0) {
/* 174 */         return ValidationResult.error("Invalid dictionary count for Modifiers");
/*     */       }
/* 176 */       if (modifiersCount > 4096000) {
/* 177 */         return ValidationResult.error("Modifiers exceeds max length 4096000");
/*     */       }
/* 179 */       pos += VarInt.length(buffer, pos);
/* 180 */       for (int i = 0; i < modifiersCount; i++) {
/* 181 */         int keyLen = VarInt.peek(buffer, pos);
/* 182 */         if (keyLen < 0) {
/* 183 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 185 */         if (keyLen > 4096000) {
/* 186 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 188 */         pos += VarInt.length(buffer, pos);
/* 189 */         pos += keyLen;
/* 190 */         if (pos > buffer.writerIndex()) {
/* 191 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 193 */         pos += 6;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 198 */     if ((nullBits & 0x2) != 0) {
/* 199 */       int modifierKeyOffset = buffer.getIntLE(offset + 17);
/* 200 */       if (modifierKeyOffset < 0) {
/* 201 */         return ValidationResult.error("Invalid offset for ModifierKey");
/*     */       }
/* 203 */       int pos = offset + 21 + modifierKeyOffset;
/* 204 */       if (pos >= buffer.writerIndex()) {
/* 205 */         return ValidationResult.error("Offset out of bounds for ModifierKey");
/*     */       }
/* 207 */       int modifierKeyLen = VarInt.peek(buffer, pos);
/* 208 */       if (modifierKeyLen < 0) {
/* 209 */         return ValidationResult.error("Invalid string length for ModifierKey");
/*     */       }
/* 211 */       if (modifierKeyLen > 4096000) {
/* 212 */         return ValidationResult.error("ModifierKey exceeds max length 4096000");
/*     */       }
/* 214 */       pos += VarInt.length(buffer, pos);
/* 215 */       pos += modifierKeyLen;
/* 216 */       if (pos > buffer.writerIndex()) {
/* 217 */         return ValidationResult.error("Buffer overflow reading ModifierKey");
/*     */       }
/*     */     } 
/* 220 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityStatUpdate clone() {
/* 224 */     EntityStatUpdate copy = new EntityStatUpdate();
/* 225 */     copy.op = this.op;
/* 226 */     copy.predictable = this.predictable;
/* 227 */     copy.value = this.value;
/* 228 */     if (this.modifiers != null) {
/* 229 */       Map<String, Modifier> m = new HashMap<>();
/* 230 */       for (Map.Entry<String, Modifier> e : this.modifiers.entrySet()) m.put(e.getKey(), ((Modifier)e.getValue()).clone()); 
/* 231 */       copy.modifiers = m;
/*     */     } 
/* 233 */     copy.modifierKey = this.modifierKey;
/* 234 */     copy.modifier = (this.modifier != null) ? this.modifier.clone() : null;
/* 235 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityStatUpdate other;
/* 241 */     if (this == obj) return true; 
/* 242 */     if (obj instanceof EntityStatUpdate) { other = (EntityStatUpdate)obj; } else { return false; }
/* 243 */      return (Objects.equals(this.op, other.op) && this.predictable == other.predictable && this.value == other.value && Objects.equals(this.modifiers, other.modifiers) && Objects.equals(this.modifierKey, other.modifierKey) && Objects.equals(this.modifier, other.modifier));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 248 */     return Objects.hash(new Object[] { this.op, Boolean.valueOf(this.predictable), Float.valueOf(this.value), this.modifiers, this.modifierKey, this.modifier });
/*     */   }
/*     */   
/*     */   public EntityStatUpdate() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityStatUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */