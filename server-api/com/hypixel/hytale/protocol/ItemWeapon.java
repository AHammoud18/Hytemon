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
/*     */ public class ItemWeapon {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 1626112020;
/*     */   @Nullable
/*     */   public int[] entityStatsToClear;
/*     */   @Nullable
/*     */   public Map<Integer, Modifier[]> statModifiers;
/*     */   public boolean renderDualWielded;
/*     */   
/*     */   public ItemWeapon() {}
/*     */   
/*     */   public ItemWeapon(@Nullable int[] entityStatsToClear, @Nullable Map<Integer, Modifier[]> statModifiers, boolean renderDualWielded) {
/*  28 */     this.entityStatsToClear = entityStatsToClear;
/*  29 */     this.statModifiers = statModifiers;
/*  30 */     this.renderDualWielded = renderDualWielded;
/*     */   }
/*     */   
/*     */   public ItemWeapon(@Nonnull ItemWeapon other) {
/*  34 */     this.entityStatsToClear = other.entityStatsToClear;
/*  35 */     this.statModifiers = other.statModifiers;
/*  36 */     this.renderDualWielded = other.renderDualWielded;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemWeapon deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ItemWeapon obj = new ItemWeapon();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.renderDualWielded = (buf.getByte(offset + 1) != 0);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 10 + buf.getIntLE(offset + 2);
/*  47 */       int entityStatsToClearCount = VarInt.peek(buf, varPos0);
/*  48 */       if (entityStatsToClearCount < 0) throw ProtocolException.negativeLength("EntityStatsToClear", entityStatsToClearCount); 
/*  49 */       if (entityStatsToClearCount > 4096000) throw ProtocolException.arrayTooLong("EntityStatsToClear", entityStatsToClearCount, 4096000); 
/*  50 */       int varIntLen = VarInt.length(buf, varPos0);
/*  51 */       if ((varPos0 + varIntLen) + entityStatsToClearCount * 4L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("EntityStatsToClear", varPos0 + varIntLen + entityStatsToClearCount * 4, buf.readableBytes()); 
/*  53 */       obj.entityStatsToClear = new int[entityStatsToClearCount];
/*  54 */       for (int i = 0; i < entityStatsToClearCount; i++) {
/*  55 */         obj.entityStatsToClear[i] = buf.getIntLE(varPos0 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*  58 */     if ((nullBits & 0x2) != 0) {
/*  59 */       int varPos1 = offset + 10 + buf.getIntLE(offset + 6);
/*  60 */       int statModifiersCount = VarInt.peek(buf, varPos1);
/*  61 */       if (statModifiersCount < 0) throw ProtocolException.negativeLength("StatModifiers", statModifiersCount); 
/*  62 */       if (statModifiersCount > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", statModifiersCount, 4096000); 
/*  63 */       int varIntLen = VarInt.length(buf, varPos1);
/*  64 */       obj.statModifiers = (Map)new HashMap<>(statModifiersCount);
/*  65 */       int dictPos = varPos1 + varIntLen;
/*  66 */       for (int i = 0; i < statModifiersCount; i++) {
/*  67 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  68 */         int valLen = VarInt.peek(buf, dictPos);
/*  69 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  70 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/*  71 */         int valVarLen = VarInt.length(buf, dictPos);
/*  72 */         if ((dictPos + valVarLen) + valLen * 6L > buf.readableBytes())
/*  73 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 6, buf.readableBytes()); 
/*  74 */         dictPos += valVarLen;
/*  75 */         Modifier[] val = new Modifier[valLen];
/*  76 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/*  77 */           val[valIdx] = Modifier.deserialize(buf, dictPos);
/*  78 */           dictPos += Modifier.computeBytesConsumed(buf, dictPos);
/*     */         } 
/*  80 */         if (obj.statModifiers.put(Integer.valueOf(key), val) != null) {
/*  81 */           throw ProtocolException.duplicateKey("statModifiers", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/*  85 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  89 */     byte nullBits = buf.getByte(offset);
/*  90 */     int maxEnd = 10;
/*  91 */     if ((nullBits & 0x1) != 0) {
/*  92 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  93 */       int pos0 = offset + 10 + fieldOffset0;
/*  94 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 4;
/*  95 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  97 */     if ((nullBits & 0x2) != 0) {
/*  98 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  99 */       int pos1 = offset + 10 + fieldOffset1;
/* 100 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 101 */       for (int i = 0; i < dictLen; ) { int al; int j; for (pos1 += 4, al = VarInt.peek(buf, pos1), pos1 += VarInt.length(buf, pos1), j = 0; j < al; ) { pos1 += Modifier.computeBytesConsumed(buf, pos1); j++; }  i++; }
/* 102 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 104 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 109 */     int startPos = buf.writerIndex();
/* 110 */     byte nullBits = 0;
/* 111 */     if (this.entityStatsToClear != null) nullBits = (byte)(nullBits | 0x1); 
/* 112 */     if (this.statModifiers != null) nullBits = (byte)(nullBits | 0x2); 
/* 113 */     buf.writeByte(nullBits);
/*     */     
/* 115 */     buf.writeByte(this.renderDualWielded ? 1 : 0);
/*     */     
/* 117 */     int entityStatsToClearOffsetSlot = buf.writerIndex();
/* 118 */     buf.writeIntLE(0);
/* 119 */     int statModifiersOffsetSlot = buf.writerIndex();
/* 120 */     buf.writeIntLE(0);
/*     */     
/* 122 */     int varBlockStart = buf.writerIndex();
/* 123 */     if (this.entityStatsToClear != null) {
/* 124 */       buf.setIntLE(entityStatsToClearOffsetSlot, buf.writerIndex() - varBlockStart);
/* 125 */       if (this.entityStatsToClear.length > 4096000) throw ProtocolException.arrayTooLong("EntityStatsToClear", this.entityStatsToClear.length, 4096000);  VarInt.write(buf, this.entityStatsToClear.length); for (int item : this.entityStatsToClear) buf.writeIntLE(item); 
/*     */     } else {
/* 127 */       buf.setIntLE(entityStatsToClearOffsetSlot, -1);
/*     */     } 
/* 129 */     if (this.statModifiers != null)
/* 130 */     { buf.setIntLE(statModifiersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 131 */       if (this.statModifiers.size() > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", this.statModifiers.size(), 4096000);  VarInt.write(buf, this.statModifiers.size()); for (Map.Entry<Integer, Modifier[]> e : this.statModifiers.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); VarInt.write(buf, ((Modifier[])e.getValue()).length); for (Modifier arrItem : (Modifier[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/* 133 */     else { buf.setIntLE(statModifiersOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 139 */     int size = 10;
/* 140 */     if (this.entityStatsToClear != null) size += VarInt.size(this.entityStatsToClear.length) + this.entityStatsToClear.length * 4; 
/* 141 */     if (this.statModifiers != null) {
/* 142 */       int statModifiersSize = 0;
/* 143 */       for (Map.Entry<Integer, Modifier[]> kvp : this.statModifiers.entrySet()) statModifiersSize += 4 + VarInt.size(((Modifier[])kvp.getValue()).length) + ((Modifier[])kvp.getValue()).length * 6; 
/* 144 */       size += VarInt.size(this.statModifiers.size()) + statModifiersSize;
/*     */     } 
/*     */     
/* 147 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 151 */     if (buffer.readableBytes() - offset < 10) {
/* 152 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 155 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 158 */     if ((nullBits & 0x1) != 0) {
/* 159 */       int entityStatsToClearOffset = buffer.getIntLE(offset + 2);
/* 160 */       if (entityStatsToClearOffset < 0) {
/* 161 */         return ValidationResult.error("Invalid offset for EntityStatsToClear");
/*     */       }
/* 163 */       int pos = offset + 10 + entityStatsToClearOffset;
/* 164 */       if (pos >= buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Offset out of bounds for EntityStatsToClear");
/*     */       }
/* 167 */       int entityStatsToClearCount = VarInt.peek(buffer, pos);
/* 168 */       if (entityStatsToClearCount < 0) {
/* 169 */         return ValidationResult.error("Invalid array count for EntityStatsToClear");
/*     */       }
/* 171 */       if (entityStatsToClearCount > 4096000) {
/* 172 */         return ValidationResult.error("EntityStatsToClear exceeds max length 4096000");
/*     */       }
/* 174 */       pos += VarInt.length(buffer, pos);
/* 175 */       pos += entityStatsToClearCount * 4;
/* 176 */       if (pos > buffer.writerIndex()) {
/* 177 */         return ValidationResult.error("Buffer overflow reading EntityStatsToClear");
/*     */       }
/*     */     } 
/*     */     
/* 181 */     if ((nullBits & 0x2) != 0) {
/* 182 */       int statModifiersOffset = buffer.getIntLE(offset + 6);
/* 183 */       if (statModifiersOffset < 0) {
/* 184 */         return ValidationResult.error("Invalid offset for StatModifiers");
/*     */       }
/* 186 */       int pos = offset + 10 + statModifiersOffset;
/* 187 */       if (pos >= buffer.writerIndex()) {
/* 188 */         return ValidationResult.error("Offset out of bounds for StatModifiers");
/*     */       }
/* 190 */       int statModifiersCount = VarInt.peek(buffer, pos);
/* 191 */       if (statModifiersCount < 0) {
/* 192 */         return ValidationResult.error("Invalid dictionary count for StatModifiers");
/*     */       }
/* 194 */       if (statModifiersCount > 4096000) {
/* 195 */         return ValidationResult.error("StatModifiers exceeds max length 4096000");
/*     */       }
/* 197 */       pos += VarInt.length(buffer, pos);
/* 198 */       for (int i = 0; i < statModifiersCount; i++) {
/* 199 */         pos += 4;
/* 200 */         if (pos > buffer.writerIndex()) {
/* 201 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 203 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 204 */         if (valueArrCount < 0) {
/* 205 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 207 */         pos += VarInt.length(buffer, pos);
/* 208 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 209 */           pos += 6;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 214 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemWeapon clone() {
/* 218 */     ItemWeapon copy = new ItemWeapon();
/* 219 */     copy.entityStatsToClear = (this.entityStatsToClear != null) ? Arrays.copyOf(this.entityStatsToClear, this.entityStatsToClear.length) : null;
/* 220 */     if (this.statModifiers != null) {
/* 221 */       Map<Integer, Modifier[]> m = (Map)new HashMap<>();
/* 222 */       for (Map.Entry<Integer, Modifier[]> e : this.statModifiers.entrySet()) m.put(e.getKey(), (Modifier[])Arrays.<Modifier>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new Modifier[x$0])); 
/* 223 */       copy.statModifiers = m;
/*     */     } 
/* 225 */     copy.renderDualWielded = this.renderDualWielded;
/* 226 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemWeapon other;
/* 232 */     if (this == obj) return true; 
/* 233 */     if (obj instanceof ItemWeapon) { other = (ItemWeapon)obj; } else { return false; }
/* 234 */      return (Arrays.equals(this.entityStatsToClear, other.entityStatsToClear) && Objects.equals(this.statModifiers, other.statModifiers) && this.renderDualWielded == other.renderDualWielded);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 239 */     int result = 1;
/* 240 */     result = 31 * result + Arrays.hashCode(this.entityStatsToClear);
/* 241 */     result = 31 * result + Objects.hashCode(this.statModifiers);
/* 242 */     result = 31 * result + Boolean.hashCode(this.renderDualWielded);
/* 243 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemWeapon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */