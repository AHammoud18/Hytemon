/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemUtility {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 3;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 11;
/*     */   public static final int MAX_SIZE = 1626112021;
/*     */   public boolean usable;
/*     */   public boolean compatible;
/*     */   @Nullable
/*     */   public int[] entityStatsToClear;
/*     */   @Nullable
/*     */   public Map<Integer, Modifier[]> statModifiers;
/*     */   
/*     */   public ItemUtility() {}
/*     */   
/*     */   public ItemUtility(boolean usable, boolean compatible, @Nullable int[] entityStatsToClear, @Nullable Map<Integer, Modifier[]> statModifiers) {
/*  29 */     this.usable = usable;
/*  30 */     this.compatible = compatible;
/*  31 */     this.entityStatsToClear = entityStatsToClear;
/*  32 */     this.statModifiers = statModifiers;
/*     */   }
/*     */   
/*     */   public ItemUtility(@Nonnull ItemUtility other) {
/*  36 */     this.usable = other.usable;
/*  37 */     this.compatible = other.compatible;
/*  38 */     this.entityStatsToClear = other.entityStatsToClear;
/*  39 */     this.statModifiers = other.statModifiers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemUtility deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     ItemUtility obj = new ItemUtility();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.usable = (buf.getByte(offset + 1) != 0);
/*  47 */     obj.compatible = (buf.getByte(offset + 2) != 0);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 11 + buf.getIntLE(offset + 3);
/*  51 */       int entityStatsToClearCount = VarInt.peek(buf, varPos0);
/*  52 */       if (entityStatsToClearCount < 0) throw ProtocolException.negativeLength("EntityStatsToClear", entityStatsToClearCount); 
/*  53 */       if (entityStatsToClearCount > 4096000) throw ProtocolException.arrayTooLong("EntityStatsToClear", entityStatsToClearCount, 4096000); 
/*  54 */       int varIntLen = VarInt.length(buf, varPos0);
/*  55 */       if ((varPos0 + varIntLen) + entityStatsToClearCount * 4L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("EntityStatsToClear", varPos0 + varIntLen + entityStatsToClearCount * 4, buf.readableBytes()); 
/*  57 */       obj.entityStatsToClear = new int[entityStatsToClearCount];
/*  58 */       for (int i = 0; i < entityStatsToClearCount; i++) {
/*  59 */         obj.entityStatsToClear[i] = buf.getIntLE(varPos0 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*  62 */     if ((nullBits & 0x2) != 0) {
/*  63 */       int varPos1 = offset + 11 + buf.getIntLE(offset + 7);
/*  64 */       int statModifiersCount = VarInt.peek(buf, varPos1);
/*  65 */       if (statModifiersCount < 0) throw ProtocolException.negativeLength("StatModifiers", statModifiersCount); 
/*  66 */       if (statModifiersCount > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", statModifiersCount, 4096000); 
/*  67 */       int varIntLen = VarInt.length(buf, varPos1);
/*  68 */       obj.statModifiers = (Map)new HashMap<>(statModifiersCount);
/*  69 */       int dictPos = varPos1 + varIntLen;
/*  70 */       for (int i = 0; i < statModifiersCount; i++) {
/*  71 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  72 */         int valLen = VarInt.peek(buf, dictPos);
/*  73 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  74 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/*  75 */         int valVarLen = VarInt.length(buf, dictPos);
/*  76 */         if ((dictPos + valVarLen) + valLen * 6L > buf.readableBytes())
/*  77 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 6, buf.readableBytes()); 
/*  78 */         dictPos += valVarLen;
/*  79 */         Modifier[] val = new Modifier[valLen];
/*  80 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/*  81 */           val[valIdx] = Modifier.deserialize(buf, dictPos);
/*  82 */           dictPos += Modifier.computeBytesConsumed(buf, dictPos);
/*     */         } 
/*  84 */         if (obj.statModifiers.put(Integer.valueOf(key), val) != null) {
/*  85 */           throw ProtocolException.duplicateKey("statModifiers", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/*  89 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  93 */     byte nullBits = buf.getByte(offset);
/*  94 */     int maxEnd = 11;
/*  95 */     if ((nullBits & 0x1) != 0) {
/*  96 */       int fieldOffset0 = buf.getIntLE(offset + 3);
/*  97 */       int pos0 = offset + 11 + fieldOffset0;
/*  98 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 4;
/*  99 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 101 */     if ((nullBits & 0x2) != 0) {
/* 102 */       int fieldOffset1 = buf.getIntLE(offset + 7);
/* 103 */       int pos1 = offset + 11 + fieldOffset1;
/* 104 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 105 */       for (int i = 0; i < dictLen; ) { int al; int j; for (pos1 += 4, al = VarInt.peek(buf, pos1), pos1 += VarInt.length(buf, pos1), j = 0; j < al; ) { pos1 += Modifier.computeBytesConsumed(buf, pos1); j++; }  i++; }
/* 106 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 108 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 113 */     int startPos = buf.writerIndex();
/* 114 */     byte nullBits = 0;
/* 115 */     if (this.entityStatsToClear != null) nullBits = (byte)(nullBits | 0x1); 
/* 116 */     if (this.statModifiers != null) nullBits = (byte)(nullBits | 0x2); 
/* 117 */     buf.writeByte(nullBits);
/*     */     
/* 119 */     buf.writeByte(this.usable ? 1 : 0);
/* 120 */     buf.writeByte(this.compatible ? 1 : 0);
/*     */     
/* 122 */     int entityStatsToClearOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/* 124 */     int statModifiersOffsetSlot = buf.writerIndex();
/* 125 */     buf.writeIntLE(0);
/*     */     
/* 127 */     int varBlockStart = buf.writerIndex();
/* 128 */     if (this.entityStatsToClear != null) {
/* 129 */       buf.setIntLE(entityStatsToClearOffsetSlot, buf.writerIndex() - varBlockStart);
/* 130 */       if (this.entityStatsToClear.length > 4096000) throw ProtocolException.arrayTooLong("EntityStatsToClear", this.entityStatsToClear.length, 4096000);  VarInt.write(buf, this.entityStatsToClear.length); for (int item : this.entityStatsToClear) buf.writeIntLE(item); 
/*     */     } else {
/* 132 */       buf.setIntLE(entityStatsToClearOffsetSlot, -1);
/*     */     } 
/* 134 */     if (this.statModifiers != null)
/* 135 */     { buf.setIntLE(statModifiersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 136 */       if (this.statModifiers.size() > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", this.statModifiers.size(), 4096000);  VarInt.write(buf, this.statModifiers.size()); for (Map.Entry<Integer, Modifier[]> e : this.statModifiers.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); VarInt.write(buf, ((Modifier[])e.getValue()).length); for (Modifier arrItem : (Modifier[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 138 */     else { buf.setIntLE(statModifiersOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 144 */     int size = 11;
/* 145 */     if (this.entityStatsToClear != null) size += VarInt.size(this.entityStatsToClear.length) + this.entityStatsToClear.length * 4; 
/* 146 */     if (this.statModifiers != null) {
/* 147 */       int statModifiersSize = 0;
/* 148 */       for (Map.Entry<Integer, Modifier[]> kvp : this.statModifiers.entrySet()) statModifiersSize += 4 + VarInt.size(((Modifier[])kvp.getValue()).length) + ((Modifier[])kvp.getValue()).length * 6; 
/* 149 */       size += VarInt.size(this.statModifiers.size()) + statModifiersSize;
/*     */     } 
/*     */     
/* 152 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 156 */     if (buffer.readableBytes() - offset < 11) {
/* 157 */       return ValidationResult.error("Buffer too small: expected at least 11 bytes");
/*     */     }
/*     */     
/* 160 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 163 */     if ((nullBits & 0x1) != 0) {
/* 164 */       int entityStatsToClearOffset = buffer.getIntLE(offset + 3);
/* 165 */       if (entityStatsToClearOffset < 0) {
/* 166 */         return ValidationResult.error("Invalid offset for EntityStatsToClear");
/*     */       }
/* 168 */       int pos = offset + 11 + entityStatsToClearOffset;
/* 169 */       if (pos >= buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Offset out of bounds for EntityStatsToClear");
/*     */       }
/* 172 */       int entityStatsToClearCount = VarInt.peek(buffer, pos);
/* 173 */       if (entityStatsToClearCount < 0) {
/* 174 */         return ValidationResult.error("Invalid array count for EntityStatsToClear");
/*     */       }
/* 176 */       if (entityStatsToClearCount > 4096000) {
/* 177 */         return ValidationResult.error("EntityStatsToClear exceeds max length 4096000");
/*     */       }
/* 179 */       pos += VarInt.length(buffer, pos);
/* 180 */       pos += entityStatsToClearCount * 4;
/* 181 */       if (pos > buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Buffer overflow reading EntityStatsToClear");
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if ((nullBits & 0x2) != 0) {
/* 187 */       int statModifiersOffset = buffer.getIntLE(offset + 7);
/* 188 */       if (statModifiersOffset < 0) {
/* 189 */         return ValidationResult.error("Invalid offset for StatModifiers");
/*     */       }
/* 191 */       int pos = offset + 11 + statModifiersOffset;
/* 192 */       if (pos >= buffer.writerIndex()) {
/* 193 */         return ValidationResult.error("Offset out of bounds for StatModifiers");
/*     */       }
/* 195 */       int statModifiersCount = VarInt.peek(buffer, pos);
/* 196 */       if (statModifiersCount < 0) {
/* 197 */         return ValidationResult.error("Invalid dictionary count for StatModifiers");
/*     */       }
/* 199 */       if (statModifiersCount > 4096000) {
/* 200 */         return ValidationResult.error("StatModifiers exceeds max length 4096000");
/*     */       }
/* 202 */       pos += VarInt.length(buffer, pos);
/* 203 */       for (int i = 0; i < statModifiersCount; i++) {
/* 204 */         pos += 4;
/* 205 */         if (pos > buffer.writerIndex()) {
/* 206 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 208 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 209 */         if (valueArrCount < 0) {
/* 210 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 212 */         pos += VarInt.length(buffer, pos);
/* 213 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 214 */           pos += 6;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemUtility clone() {
/* 223 */     ItemUtility copy = new ItemUtility();
/* 224 */     copy.usable = this.usable;
/* 225 */     copy.compatible = this.compatible;
/* 226 */     copy.entityStatsToClear = (this.entityStatsToClear != null) ? Arrays.copyOf(this.entityStatsToClear, this.entityStatsToClear.length) : null;
/* 227 */     if (this.statModifiers != null) {
/* 228 */       Map<Integer, Modifier[]> m = (Map)new HashMap<>();
/* 229 */       for (Map.Entry<Integer, Modifier[]> e : this.statModifiers.entrySet()) m.put(e.getKey(), (Modifier[])Arrays.<Modifier>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new Modifier[x$0])); 
/* 230 */       copy.statModifiers = m;
/*     */     } 
/* 232 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemUtility other;
/* 238 */     if (this == obj) return true; 
/* 239 */     if (obj instanceof ItemUtility) { other = (ItemUtility)obj; } else { return false; }
/* 240 */      return (this.usable == other.usable && this.compatible == other.compatible && Arrays.equals(this.entityStatsToClear, other.entityStatsToClear) && Objects.equals(this.statModifiers, other.statModifiers));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 245 */     int result = 1;
/* 246 */     result = 31 * result + Boolean.hashCode(this.usable);
/* 247 */     result = 31 * result + Boolean.hashCode(this.compatible);
/* 248 */     result = 31 * result + Arrays.hashCode(this.entityStatsToClear);
/* 249 */     result = 31 * result + Objects.hashCode(this.statModifiers);
/* 250 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemUtility.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */