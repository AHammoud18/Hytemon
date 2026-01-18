/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class Objective {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 17;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 33;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public UUID objectiveUuid = new UUID(0L, 0L); @Nullable
/*     */   public String objectiveTitleKey;
/*     */   @Nullable
/*     */   public String objectiveDescriptionKey;
/*     */   @Nullable
/*     */   public String objectiveLineId;
/*     */   @Nullable
/*     */   public ObjectiveTask[] tasks;
/*     */   
/*     */   public Objective(@Nonnull UUID objectiveUuid, @Nullable String objectiveTitleKey, @Nullable String objectiveDescriptionKey, @Nullable String objectiveLineId, @Nullable ObjectiveTask[] tasks) {
/*  30 */     this.objectiveUuid = objectiveUuid;
/*  31 */     this.objectiveTitleKey = objectiveTitleKey;
/*  32 */     this.objectiveDescriptionKey = objectiveDescriptionKey;
/*  33 */     this.objectiveLineId = objectiveLineId;
/*  34 */     this.tasks = tasks;
/*     */   }
/*     */   
/*     */   public Objective(@Nonnull Objective other) {
/*  38 */     this.objectiveUuid = other.objectiveUuid;
/*  39 */     this.objectiveTitleKey = other.objectiveTitleKey;
/*  40 */     this.objectiveDescriptionKey = other.objectiveDescriptionKey;
/*  41 */     this.objectiveLineId = other.objectiveLineId;
/*  42 */     this.tasks = other.tasks;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Objective deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     Objective obj = new Objective();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     obj.objectiveUuid = PacketIO.readUUID(buf, offset + 1);
/*     */     
/*  51 */     if ((nullBits & 0x1) != 0) {
/*  52 */       int varPos0 = offset + 33 + buf.getIntLE(offset + 17);
/*  53 */       int objectiveTitleKeyLen = VarInt.peek(buf, varPos0);
/*  54 */       if (objectiveTitleKeyLen < 0) throw ProtocolException.negativeLength("ObjectiveTitleKey", objectiveTitleKeyLen); 
/*  55 */       if (objectiveTitleKeyLen > 4096000) throw ProtocolException.stringTooLong("ObjectiveTitleKey", objectiveTitleKeyLen, 4096000); 
/*  56 */       obj.objectiveTitleKey = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  58 */     if ((nullBits & 0x2) != 0) {
/*  59 */       int varPos1 = offset + 33 + buf.getIntLE(offset + 21);
/*  60 */       int objectiveDescriptionKeyLen = VarInt.peek(buf, varPos1);
/*  61 */       if (objectiveDescriptionKeyLen < 0) throw ProtocolException.negativeLength("ObjectiveDescriptionKey", objectiveDescriptionKeyLen); 
/*  62 */       if (objectiveDescriptionKeyLen > 4096000) throw ProtocolException.stringTooLong("ObjectiveDescriptionKey", objectiveDescriptionKeyLen, 4096000); 
/*  63 */       obj.objectiveDescriptionKey = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  65 */     if ((nullBits & 0x4) != 0) {
/*  66 */       int varPos2 = offset + 33 + buf.getIntLE(offset + 25);
/*  67 */       int objectiveLineIdLen = VarInt.peek(buf, varPos2);
/*  68 */       if (objectiveLineIdLen < 0) throw ProtocolException.negativeLength("ObjectiveLineId", objectiveLineIdLen); 
/*  69 */       if (objectiveLineIdLen > 4096000) throw ProtocolException.stringTooLong("ObjectiveLineId", objectiveLineIdLen, 4096000); 
/*  70 */       obj.objectiveLineId = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  72 */     if ((nullBits & 0x8) != 0) {
/*  73 */       int varPos3 = offset + 33 + buf.getIntLE(offset + 29);
/*  74 */       int tasksCount = VarInt.peek(buf, varPos3);
/*  75 */       if (tasksCount < 0) throw ProtocolException.negativeLength("Tasks", tasksCount); 
/*  76 */       if (tasksCount > 4096000) throw ProtocolException.arrayTooLong("Tasks", tasksCount, 4096000); 
/*  77 */       int varIntLen = VarInt.length(buf, varPos3);
/*  78 */       if ((varPos3 + varIntLen) + tasksCount * 9L > buf.readableBytes())
/*  79 */         throw ProtocolException.bufferTooSmall("Tasks", varPos3 + varIntLen + tasksCount * 9, buf.readableBytes()); 
/*  80 */       obj.tasks = new ObjectiveTask[tasksCount];
/*  81 */       int elemPos = varPos3 + varIntLen;
/*  82 */       for (int i = 0; i < tasksCount; i++) {
/*  83 */         obj.tasks[i] = ObjectiveTask.deserialize(buf, elemPos);
/*  84 */         elemPos += ObjectiveTask.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  92 */     byte nullBits = buf.getByte(offset);
/*  93 */     int maxEnd = 33;
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int fieldOffset0 = buf.getIntLE(offset + 17);
/*  96 */       int pos0 = offset + 33 + fieldOffset0;
/*  97 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  98 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 100 */     if ((nullBits & 0x2) != 0) {
/* 101 */       int fieldOffset1 = buf.getIntLE(offset + 21);
/* 102 */       int pos1 = offset + 33 + fieldOffset1;
/* 103 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 104 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 106 */     if ((nullBits & 0x4) != 0) {
/* 107 */       int fieldOffset2 = buf.getIntLE(offset + 25);
/* 108 */       int pos2 = offset + 33 + fieldOffset2;
/* 109 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 110 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 112 */     if ((nullBits & 0x8) != 0) {
/* 113 */       int fieldOffset3 = buf.getIntLE(offset + 29);
/* 114 */       int pos3 = offset + 33 + fieldOffset3;
/* 115 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 116 */       for (int i = 0; i < arrLen; ) { pos3 += ObjectiveTask.computeBytesConsumed(buf, pos3); i++; }
/* 117 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 119 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 124 */     int startPos = buf.writerIndex();
/* 125 */     byte nullBits = 0;
/* 126 */     if (this.objectiveTitleKey != null) nullBits = (byte)(nullBits | 0x1); 
/* 127 */     if (this.objectiveDescriptionKey != null) nullBits = (byte)(nullBits | 0x2); 
/* 128 */     if (this.objectiveLineId != null) nullBits = (byte)(nullBits | 0x4); 
/* 129 */     if (this.tasks != null) nullBits = (byte)(nullBits | 0x8); 
/* 130 */     buf.writeByte(nullBits);
/*     */     
/* 132 */     PacketIO.writeUUID(buf, this.objectiveUuid);
/*     */     
/* 134 */     int objectiveTitleKeyOffsetSlot = buf.writerIndex();
/* 135 */     buf.writeIntLE(0);
/* 136 */     int objectiveDescriptionKeyOffsetSlot = buf.writerIndex();
/* 137 */     buf.writeIntLE(0);
/* 138 */     int objectiveLineIdOffsetSlot = buf.writerIndex();
/* 139 */     buf.writeIntLE(0);
/* 140 */     int tasksOffsetSlot = buf.writerIndex();
/* 141 */     buf.writeIntLE(0);
/*     */     
/* 143 */     int varBlockStart = buf.writerIndex();
/* 144 */     if (this.objectiveTitleKey != null) {
/* 145 */       buf.setIntLE(objectiveTitleKeyOffsetSlot, buf.writerIndex() - varBlockStart);
/* 146 */       PacketIO.writeVarString(buf, this.objectiveTitleKey, 4096000);
/*     */     } else {
/* 148 */       buf.setIntLE(objectiveTitleKeyOffsetSlot, -1);
/*     */     } 
/* 150 */     if (this.objectiveDescriptionKey != null) {
/* 151 */       buf.setIntLE(objectiveDescriptionKeyOffsetSlot, buf.writerIndex() - varBlockStart);
/* 152 */       PacketIO.writeVarString(buf, this.objectiveDescriptionKey, 4096000);
/*     */     } else {
/* 154 */       buf.setIntLE(objectiveDescriptionKeyOffsetSlot, -1);
/*     */     } 
/* 156 */     if (this.objectiveLineId != null) {
/* 157 */       buf.setIntLE(objectiveLineIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 158 */       PacketIO.writeVarString(buf, this.objectiveLineId, 4096000);
/*     */     } else {
/* 160 */       buf.setIntLE(objectiveLineIdOffsetSlot, -1);
/*     */     } 
/* 162 */     if (this.tasks != null) {
/* 163 */       buf.setIntLE(tasksOffsetSlot, buf.writerIndex() - varBlockStart);
/* 164 */       if (this.tasks.length > 4096000) throw ProtocolException.arrayTooLong("Tasks", this.tasks.length, 4096000);  VarInt.write(buf, this.tasks.length); for (ObjectiveTask item : this.tasks) item.serialize(buf); 
/*     */     } else {
/* 166 */       buf.setIntLE(tasksOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 172 */     int size = 33;
/* 173 */     if (this.objectiveTitleKey != null) size += PacketIO.stringSize(this.objectiveTitleKey); 
/* 174 */     if (this.objectiveDescriptionKey != null) size += PacketIO.stringSize(this.objectiveDescriptionKey); 
/* 175 */     if (this.objectiveLineId != null) size += PacketIO.stringSize(this.objectiveLineId); 
/* 176 */     if (this.tasks != null) {
/* 177 */       int tasksSize = 0;
/* 178 */       for (ObjectiveTask elem : this.tasks) tasksSize += elem.computeSize(); 
/* 179 */       size += VarInt.size(this.tasks.length) + tasksSize;
/*     */     } 
/*     */     
/* 182 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 186 */     if (buffer.readableBytes() - offset < 33) {
/* 187 */       return ValidationResult.error("Buffer too small: expected at least 33 bytes");
/*     */     }
/*     */     
/* 190 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 193 */     if ((nullBits & 0x1) != 0) {
/* 194 */       int objectiveTitleKeyOffset = buffer.getIntLE(offset + 17);
/* 195 */       if (objectiveTitleKeyOffset < 0) {
/* 196 */         return ValidationResult.error("Invalid offset for ObjectiveTitleKey");
/*     */       }
/* 198 */       int pos = offset + 33 + objectiveTitleKeyOffset;
/* 199 */       if (pos >= buffer.writerIndex()) {
/* 200 */         return ValidationResult.error("Offset out of bounds for ObjectiveTitleKey");
/*     */       }
/* 202 */       int objectiveTitleKeyLen = VarInt.peek(buffer, pos);
/* 203 */       if (objectiveTitleKeyLen < 0) {
/* 204 */         return ValidationResult.error("Invalid string length for ObjectiveTitleKey");
/*     */       }
/* 206 */       if (objectiveTitleKeyLen > 4096000) {
/* 207 */         return ValidationResult.error("ObjectiveTitleKey exceeds max length 4096000");
/*     */       }
/* 209 */       pos += VarInt.length(buffer, pos);
/* 210 */       pos += objectiveTitleKeyLen;
/* 211 */       if (pos > buffer.writerIndex()) {
/* 212 */         return ValidationResult.error("Buffer overflow reading ObjectiveTitleKey");
/*     */       }
/*     */     } 
/*     */     
/* 216 */     if ((nullBits & 0x2) != 0) {
/* 217 */       int objectiveDescriptionKeyOffset = buffer.getIntLE(offset + 21);
/* 218 */       if (objectiveDescriptionKeyOffset < 0) {
/* 219 */         return ValidationResult.error("Invalid offset for ObjectiveDescriptionKey");
/*     */       }
/* 221 */       int pos = offset + 33 + objectiveDescriptionKeyOffset;
/* 222 */       if (pos >= buffer.writerIndex()) {
/* 223 */         return ValidationResult.error("Offset out of bounds for ObjectiveDescriptionKey");
/*     */       }
/* 225 */       int objectiveDescriptionKeyLen = VarInt.peek(buffer, pos);
/* 226 */       if (objectiveDescriptionKeyLen < 0) {
/* 227 */         return ValidationResult.error("Invalid string length for ObjectiveDescriptionKey");
/*     */       }
/* 229 */       if (objectiveDescriptionKeyLen > 4096000) {
/* 230 */         return ValidationResult.error("ObjectiveDescriptionKey exceeds max length 4096000");
/*     */       }
/* 232 */       pos += VarInt.length(buffer, pos);
/* 233 */       pos += objectiveDescriptionKeyLen;
/* 234 */       if (pos > buffer.writerIndex()) {
/* 235 */         return ValidationResult.error("Buffer overflow reading ObjectiveDescriptionKey");
/*     */       }
/*     */     } 
/*     */     
/* 239 */     if ((nullBits & 0x4) != 0) {
/* 240 */       int objectiveLineIdOffset = buffer.getIntLE(offset + 25);
/* 241 */       if (objectiveLineIdOffset < 0) {
/* 242 */         return ValidationResult.error("Invalid offset for ObjectiveLineId");
/*     */       }
/* 244 */       int pos = offset + 33 + objectiveLineIdOffset;
/* 245 */       if (pos >= buffer.writerIndex()) {
/* 246 */         return ValidationResult.error("Offset out of bounds for ObjectiveLineId");
/*     */       }
/* 248 */       int objectiveLineIdLen = VarInt.peek(buffer, pos);
/* 249 */       if (objectiveLineIdLen < 0) {
/* 250 */         return ValidationResult.error("Invalid string length for ObjectiveLineId");
/*     */       }
/* 252 */       if (objectiveLineIdLen > 4096000) {
/* 253 */         return ValidationResult.error("ObjectiveLineId exceeds max length 4096000");
/*     */       }
/* 255 */       pos += VarInt.length(buffer, pos);
/* 256 */       pos += objectiveLineIdLen;
/* 257 */       if (pos > buffer.writerIndex()) {
/* 258 */         return ValidationResult.error("Buffer overflow reading ObjectiveLineId");
/*     */       }
/*     */     } 
/*     */     
/* 262 */     if ((nullBits & 0x8) != 0) {
/* 263 */       int tasksOffset = buffer.getIntLE(offset + 29);
/* 264 */       if (tasksOffset < 0) {
/* 265 */         return ValidationResult.error("Invalid offset for Tasks");
/*     */       }
/* 267 */       int pos = offset + 33 + tasksOffset;
/* 268 */       if (pos >= buffer.writerIndex()) {
/* 269 */         return ValidationResult.error("Offset out of bounds for Tasks");
/*     */       }
/* 271 */       int tasksCount = VarInt.peek(buffer, pos);
/* 272 */       if (tasksCount < 0) {
/* 273 */         return ValidationResult.error("Invalid array count for Tasks");
/*     */       }
/* 275 */       if (tasksCount > 4096000) {
/* 276 */         return ValidationResult.error("Tasks exceeds max length 4096000");
/*     */       }
/* 278 */       pos += VarInt.length(buffer, pos);
/* 279 */       for (int i = 0; i < tasksCount; i++) {
/* 280 */         ValidationResult structResult = ObjectiveTask.validateStructure(buffer, pos);
/* 281 */         if (!structResult.isValid()) {
/* 282 */           return ValidationResult.error("Invalid ObjectiveTask in Tasks[" + i + "]: " + structResult.error());
/*     */         }
/* 284 */         pos += ObjectiveTask.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 287 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Objective clone() {
/* 291 */     Objective copy = new Objective();
/* 292 */     copy.objectiveUuid = this.objectiveUuid;
/* 293 */     copy.objectiveTitleKey = this.objectiveTitleKey;
/* 294 */     copy.objectiveDescriptionKey = this.objectiveDescriptionKey;
/* 295 */     copy.objectiveLineId = this.objectiveLineId;
/* 296 */     copy.tasks = (this.tasks != null) ? (ObjectiveTask[])Arrays.<ObjectiveTask>stream(this.tasks).map(e -> e.clone()).toArray(x$0 -> new ObjectiveTask[x$0]) : null;
/* 297 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Objective other;
/* 303 */     if (this == obj) return true; 
/* 304 */     if (obj instanceof Objective) { other = (Objective)obj; } else { return false; }
/* 305 */      return (Objects.equals(this.objectiveUuid, other.objectiveUuid) && Objects.equals(this.objectiveTitleKey, other.objectiveTitleKey) && Objects.equals(this.objectiveDescriptionKey, other.objectiveDescriptionKey) && Objects.equals(this.objectiveLineId, other.objectiveLineId) && Arrays.equals((Object[])this.tasks, (Object[])other.tasks));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 310 */     int result = 1;
/* 311 */     result = 31 * result + Objects.hashCode(this.objectiveUuid);
/* 312 */     result = 31 * result + Objects.hashCode(this.objectiveTitleKey);
/* 313 */     result = 31 * result + Objects.hashCode(this.objectiveDescriptionKey);
/* 314 */     result = 31 * result + Objects.hashCode(this.objectiveLineId);
/* 315 */     result = 31 * result + Arrays.hashCode((Object[])this.tasks);
/* 316 */     return result;
/*     */   }
/*     */   
/*     */   public Objective() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Objective.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */