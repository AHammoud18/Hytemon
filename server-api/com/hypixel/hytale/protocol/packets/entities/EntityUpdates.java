/*     */ package com.hypixel.hytale.protocol.packets.entities;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.EntityUpdate;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityUpdates
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 161;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 161;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public int[] removed;
/*     */   @Nullable
/*     */   public EntityUpdate[] updates;
/*     */   
/*     */   public EntityUpdates() {}
/*     */   
/*     */   public EntityUpdates(@Nullable int[] removed, @Nullable EntityUpdate[] updates) {
/*  35 */     this.removed = removed;
/*  36 */     this.updates = updates;
/*     */   }
/*     */   
/*     */   public EntityUpdates(@Nonnull EntityUpdates other) {
/*  40 */     this.removed = other.removed;
/*  41 */     this.updates = other.updates;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityUpdates deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     EntityUpdates obj = new EntityUpdates();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       int removedCount = VarInt.peek(buf, varPos0);
/*  52 */       if (removedCount < 0) throw ProtocolException.negativeLength("Removed", removedCount); 
/*  53 */       if (removedCount > 4096000) throw ProtocolException.arrayTooLong("Removed", removedCount, 4096000); 
/*  54 */       int varIntLen = VarInt.length(buf, varPos0);
/*  55 */       if ((varPos0 + varIntLen) + removedCount * 4L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("Removed", varPos0 + varIntLen + removedCount * 4, buf.readableBytes()); 
/*  57 */       obj.removed = new int[removedCount];
/*  58 */       for (int i = 0; i < removedCount; i++) {
/*  59 */         obj.removed[i] = buf.getIntLE(varPos0 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*  62 */     if ((nullBits & 0x2) != 0) {
/*  63 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  64 */       int updatesCount = VarInt.peek(buf, varPos1);
/*  65 */       if (updatesCount < 0) throw ProtocolException.negativeLength("Updates", updatesCount); 
/*  66 */       if (updatesCount > 4096000) throw ProtocolException.arrayTooLong("Updates", updatesCount, 4096000); 
/*  67 */       int varIntLen = VarInt.length(buf, varPos1);
/*  68 */       if ((varPos1 + varIntLen) + updatesCount * 5L > buf.readableBytes())
/*  69 */         throw ProtocolException.bufferTooSmall("Updates", varPos1 + varIntLen + updatesCount * 5, buf.readableBytes()); 
/*  70 */       obj.updates = new EntityUpdate[updatesCount];
/*  71 */       int elemPos = varPos1 + varIntLen;
/*  72 */       for (int i = 0; i < updatesCount; i++) {
/*  73 */         obj.updates[i] = EntityUpdate.deserialize(buf, elemPos);
/*  74 */         elemPos += EntityUpdate.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  82 */     byte nullBits = buf.getByte(offset);
/*  83 */     int maxEnd = 9;
/*  84 */     if ((nullBits & 0x1) != 0) {
/*  85 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  86 */       int pos0 = offset + 9 + fieldOffset0;
/*  87 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 4;
/*  88 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  90 */     if ((nullBits & 0x2) != 0) {
/*  91 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  92 */       int pos1 = offset + 9 + fieldOffset1;
/*  93 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  94 */       for (int i = 0; i < arrLen; ) { pos1 += EntityUpdate.computeBytesConsumed(buf, pos1); i++; }
/*  95 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  97 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 103 */     int startPos = buf.writerIndex();
/* 104 */     byte nullBits = 0;
/* 105 */     if (this.removed != null) nullBits = (byte)(nullBits | 0x1); 
/* 106 */     if (this.updates != null) nullBits = (byte)(nullBits | 0x2); 
/* 107 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 110 */     int removedOffsetSlot = buf.writerIndex();
/* 111 */     buf.writeIntLE(0);
/* 112 */     int updatesOffsetSlot = buf.writerIndex();
/* 113 */     buf.writeIntLE(0);
/*     */     
/* 115 */     int varBlockStart = buf.writerIndex();
/* 116 */     if (this.removed != null) {
/* 117 */       buf.setIntLE(removedOffsetSlot, buf.writerIndex() - varBlockStart);
/* 118 */       if (this.removed.length > 4096000) throw ProtocolException.arrayTooLong("Removed", this.removed.length, 4096000);  VarInt.write(buf, this.removed.length); for (int item : this.removed) buf.writeIntLE(item); 
/*     */     } else {
/* 120 */       buf.setIntLE(removedOffsetSlot, -1);
/*     */     } 
/* 122 */     if (this.updates != null) {
/* 123 */       buf.setIntLE(updatesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 124 */       if (this.updates.length > 4096000) throw ProtocolException.arrayTooLong("Updates", this.updates.length, 4096000);  VarInt.write(buf, this.updates.length); for (EntityUpdate item : this.updates) item.serialize(buf); 
/*     */     } else {
/* 126 */       buf.setIntLE(updatesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 132 */     int size = 9;
/* 133 */     if (this.removed != null) size += VarInt.size(this.removed.length) + this.removed.length * 4; 
/* 134 */     if (this.updates != null) {
/* 135 */       int updatesSize = 0;
/* 136 */       for (EntityUpdate elem : this.updates) updatesSize += elem.computeSize(); 
/* 137 */       size += VarInt.size(this.updates.length) + updatesSize;
/*     */     } 
/*     */     
/* 140 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 144 */     if (buffer.readableBytes() - offset < 9) {
/* 145 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 148 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 151 */     if ((nullBits & 0x1) != 0) {
/* 152 */       int removedOffset = buffer.getIntLE(offset + 1);
/* 153 */       if (removedOffset < 0) {
/* 154 */         return ValidationResult.error("Invalid offset for Removed");
/*     */       }
/* 156 */       int pos = offset + 9 + removedOffset;
/* 157 */       if (pos >= buffer.writerIndex()) {
/* 158 */         return ValidationResult.error("Offset out of bounds for Removed");
/*     */       }
/* 160 */       int removedCount = VarInt.peek(buffer, pos);
/* 161 */       if (removedCount < 0) {
/* 162 */         return ValidationResult.error("Invalid array count for Removed");
/*     */       }
/* 164 */       if (removedCount > 4096000) {
/* 165 */         return ValidationResult.error("Removed exceeds max length 4096000");
/*     */       }
/* 167 */       pos += VarInt.length(buffer, pos);
/* 168 */       pos += removedCount * 4;
/* 169 */       if (pos > buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Buffer overflow reading Removed");
/*     */       }
/*     */     } 
/*     */     
/* 174 */     if ((nullBits & 0x2) != 0) {
/* 175 */       int updatesOffset = buffer.getIntLE(offset + 5);
/* 176 */       if (updatesOffset < 0) {
/* 177 */         return ValidationResult.error("Invalid offset for Updates");
/*     */       }
/* 179 */       int pos = offset + 9 + updatesOffset;
/* 180 */       if (pos >= buffer.writerIndex()) {
/* 181 */         return ValidationResult.error("Offset out of bounds for Updates");
/*     */       }
/* 183 */       int updatesCount = VarInt.peek(buffer, pos);
/* 184 */       if (updatesCount < 0) {
/* 185 */         return ValidationResult.error("Invalid array count for Updates");
/*     */       }
/* 187 */       if (updatesCount > 4096000) {
/* 188 */         return ValidationResult.error("Updates exceeds max length 4096000");
/*     */       }
/* 190 */       pos += VarInt.length(buffer, pos);
/* 191 */       for (int i = 0; i < updatesCount; i++) {
/* 192 */         ValidationResult structResult = EntityUpdate.validateStructure(buffer, pos);
/* 193 */         if (!structResult.isValid()) {
/* 194 */           return ValidationResult.error("Invalid EntityUpdate in Updates[" + i + "]: " + structResult.error());
/*     */         }
/* 196 */         pos += EntityUpdate.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 199 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityUpdates clone() {
/* 203 */     EntityUpdates copy = new EntityUpdates();
/* 204 */     copy.removed = (this.removed != null) ? Arrays.copyOf(this.removed, this.removed.length) : null;
/* 205 */     copy.updates = (this.updates != null) ? (EntityUpdate[])Arrays.<EntityUpdate>stream(this.updates).map(e -> e.clone()).toArray(x$0 -> new EntityUpdate[x$0]) : null;
/* 206 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityUpdates other;
/* 212 */     if (this == obj) return true; 
/* 213 */     if (obj instanceof EntityUpdates) { other = (EntityUpdates)obj; } else { return false; }
/* 214 */      return (Arrays.equals(this.removed, other.removed) && Arrays.equals((Object[])this.updates, (Object[])other.updates));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 219 */     int result = 1;
/* 220 */     result = 31 * result + Arrays.hashCode(this.removed);
/* 221 */     result = 31 * result + Arrays.hashCode((Object[])this.updates);
/* 222 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\entities\EntityUpdates.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */