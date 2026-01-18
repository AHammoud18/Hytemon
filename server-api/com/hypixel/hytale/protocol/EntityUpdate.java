/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class EntityUpdate
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int networkId;
/*     */   @Nullable
/*     */   public ComponentUpdateType[] removed;
/*     */   @Nullable
/*     */   public ComponentUpdate[] updates;
/*     */   
/*     */   public EntityUpdate() {}
/*     */   
/*     */   public EntityUpdate(int networkId, @Nullable ComponentUpdateType[] removed, @Nullable ComponentUpdate[] updates) {
/*  28 */     this.networkId = networkId;
/*  29 */     this.removed = removed;
/*  30 */     this.updates = updates;
/*     */   }
/*     */   
/*     */   public EntityUpdate(@Nonnull EntityUpdate other) {
/*  34 */     this.networkId = other.networkId;
/*  35 */     this.removed = other.removed;
/*  36 */     this.updates = other.updates;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     EntityUpdate obj = new EntityUpdate();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.networkId = buf.getIntLE(offset + 1);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 5);
/*  47 */       int removedCount = VarInt.peek(buf, varPos0);
/*  48 */       if (removedCount < 0) throw ProtocolException.negativeLength("Removed", removedCount); 
/*  49 */       if (removedCount > 4096000) throw ProtocolException.arrayTooLong("Removed", removedCount, 4096000); 
/*  50 */       int varIntLen = VarInt.length(buf, varPos0);
/*  51 */       if ((varPos0 + varIntLen) + removedCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Removed", varPos0 + varIntLen + removedCount * 1, buf.readableBytes()); 
/*  53 */       obj.removed = new ComponentUpdateType[removedCount];
/*  54 */       int elemPos = varPos0 + varIntLen;
/*  55 */       for (int i = 0; i < removedCount; i++) {
/*  56 */         obj.removed[i] = ComponentUpdateType.fromValue(buf.getByte(elemPos)); elemPos++;
/*     */       } 
/*     */     } 
/*  59 */     if ((nullBits & 0x2) != 0) {
/*  60 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 9);
/*  61 */       int updatesCount = VarInt.peek(buf, varPos1);
/*  62 */       if (updatesCount < 0) throw ProtocolException.negativeLength("Updates", updatesCount); 
/*  63 */       if (updatesCount > 4096000) throw ProtocolException.arrayTooLong("Updates", updatesCount, 4096000); 
/*  64 */       int varIntLen = VarInt.length(buf, varPos1);
/*  65 */       if ((varPos1 + varIntLen) + updatesCount * 159L > buf.readableBytes())
/*  66 */         throw ProtocolException.bufferTooSmall("Updates", varPos1 + varIntLen + updatesCount * 159, buf.readableBytes()); 
/*  67 */       obj.updates = new ComponentUpdate[updatesCount];
/*  68 */       int elemPos = varPos1 + varIntLen;
/*  69 */       for (int i = 0; i < updatesCount; i++) {
/*  70 */         obj.updates[i] = ComponentUpdate.deserialize(buf, elemPos);
/*  71 */         elemPos += ComponentUpdate.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  79 */     byte nullBits = buf.getByte(offset);
/*  80 */     int maxEnd = 13;
/*  81 */     if ((nullBits & 0x1) != 0) {
/*  82 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/*  83 */       int pos0 = offset + 13 + fieldOffset0;
/*  84 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 1;
/*  85 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  87 */     if ((nullBits & 0x2) != 0) {
/*  88 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/*  89 */       int pos1 = offset + 13 + fieldOffset1;
/*  90 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  91 */       for (int i = 0; i < arrLen; ) { pos1 += ComponentUpdate.computeBytesConsumed(buf, pos1); i++; }
/*  92 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  94 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  99 */     int startPos = buf.writerIndex();
/* 100 */     byte nullBits = 0;
/* 101 */     if (this.removed != null) nullBits = (byte)(nullBits | 0x1); 
/* 102 */     if (this.updates != null) nullBits = (byte)(nullBits | 0x2); 
/* 103 */     buf.writeByte(nullBits);
/*     */     
/* 105 */     buf.writeIntLE(this.networkId);
/*     */     
/* 107 */     int removedOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/* 109 */     int updatesOffsetSlot = buf.writerIndex();
/* 110 */     buf.writeIntLE(0);
/*     */     
/* 112 */     int varBlockStart = buf.writerIndex();
/* 113 */     if (this.removed != null) {
/* 114 */       buf.setIntLE(removedOffsetSlot, buf.writerIndex() - varBlockStart);
/* 115 */       if (this.removed.length > 4096000) throw ProtocolException.arrayTooLong("Removed", this.removed.length, 4096000);  VarInt.write(buf, this.removed.length); for (ComponentUpdateType item : this.removed) buf.writeByte(item.getValue()); 
/*     */     } else {
/* 117 */       buf.setIntLE(removedOffsetSlot, -1);
/*     */     } 
/* 119 */     if (this.updates != null) {
/* 120 */       buf.setIntLE(updatesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 121 */       if (this.updates.length > 4096000) throw ProtocolException.arrayTooLong("Updates", this.updates.length, 4096000);  VarInt.write(buf, this.updates.length); for (ComponentUpdate item : this.updates) item.serialize(buf); 
/*     */     } else {
/* 123 */       buf.setIntLE(updatesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 129 */     int size = 13;
/* 130 */     if (this.removed != null) size += VarInt.size(this.removed.length) + this.removed.length * 1; 
/* 131 */     if (this.updates != null) {
/* 132 */       int updatesSize = 0;
/* 133 */       for (ComponentUpdate elem : this.updates) updatesSize += elem.computeSize(); 
/* 134 */       size += VarInt.size(this.updates.length) + updatesSize;
/*     */     } 
/*     */     
/* 137 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 141 */     if (buffer.readableBytes() - offset < 13) {
/* 142 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 145 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 148 */     if ((nullBits & 0x1) != 0) {
/* 149 */       int removedOffset = buffer.getIntLE(offset + 5);
/* 150 */       if (removedOffset < 0) {
/* 151 */         return ValidationResult.error("Invalid offset for Removed");
/*     */       }
/* 153 */       int pos = offset + 13 + removedOffset;
/* 154 */       if (pos >= buffer.writerIndex()) {
/* 155 */         return ValidationResult.error("Offset out of bounds for Removed");
/*     */       }
/* 157 */       int removedCount = VarInt.peek(buffer, pos);
/* 158 */       if (removedCount < 0) {
/* 159 */         return ValidationResult.error("Invalid array count for Removed");
/*     */       }
/* 161 */       if (removedCount > 4096000) {
/* 162 */         return ValidationResult.error("Removed exceeds max length 4096000");
/*     */       }
/* 164 */       pos += VarInt.length(buffer, pos);
/* 165 */       pos += removedCount * 1;
/* 166 */       if (pos > buffer.writerIndex()) {
/* 167 */         return ValidationResult.error("Buffer overflow reading Removed");
/*     */       }
/*     */     } 
/*     */     
/* 171 */     if ((nullBits & 0x2) != 0) {
/* 172 */       int updatesOffset = buffer.getIntLE(offset + 9);
/* 173 */       if (updatesOffset < 0) {
/* 174 */         return ValidationResult.error("Invalid offset for Updates");
/*     */       }
/* 176 */       int pos = offset + 13 + updatesOffset;
/* 177 */       if (pos >= buffer.writerIndex()) {
/* 178 */         return ValidationResult.error("Offset out of bounds for Updates");
/*     */       }
/* 180 */       int updatesCount = VarInt.peek(buffer, pos);
/* 181 */       if (updatesCount < 0) {
/* 182 */         return ValidationResult.error("Invalid array count for Updates");
/*     */       }
/* 184 */       if (updatesCount > 4096000) {
/* 185 */         return ValidationResult.error("Updates exceeds max length 4096000");
/*     */       }
/* 187 */       pos += VarInt.length(buffer, pos);
/* 188 */       for (int i = 0; i < updatesCount; i++) {
/* 189 */         ValidationResult structResult = ComponentUpdate.validateStructure(buffer, pos);
/* 190 */         if (!structResult.isValid()) {
/* 191 */           return ValidationResult.error("Invalid ComponentUpdate in Updates[" + i + "]: " + structResult.error());
/*     */         }
/* 193 */         pos += ComponentUpdate.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 196 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityUpdate clone() {
/* 200 */     EntityUpdate copy = new EntityUpdate();
/* 201 */     copy.networkId = this.networkId;
/* 202 */     copy.removed = (this.removed != null) ? Arrays.<ComponentUpdateType>copyOf(this.removed, this.removed.length) : null;
/* 203 */     copy.updates = (this.updates != null) ? (ComponentUpdate[])Arrays.<ComponentUpdate>stream(this.updates).map(e -> e.clone()).toArray(x$0 -> new ComponentUpdate[x$0]) : null;
/* 204 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityUpdate other;
/* 210 */     if (this == obj) return true; 
/* 211 */     if (obj instanceof EntityUpdate) { other = (EntityUpdate)obj; } else { return false; }
/* 212 */      return (this.networkId == other.networkId && Arrays.equals((Object[])this.removed, (Object[])other.removed) && Arrays.equals((Object[])this.updates, (Object[])other.updates));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 217 */     int result = 1;
/* 218 */     result = 31 * result + Integer.hashCode(this.networkId);
/* 219 */     result = 31 * result + Arrays.hashCode((Object[])this.removed);
/* 220 */     result = 31 * result + Arrays.hashCode((Object[])this.updates);
/* 221 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */