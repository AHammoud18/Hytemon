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
/*     */ public class InteractionRules {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 17;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 33;
/*     */   public static final int MAX_SIZE = 16384053;
/*     */   @Nullable
/*     */   public InteractionType[] blockedBy;
/*     */   @Nullable
/*     */   public InteractionType[] blocking;
/*     */   @Nullable
/*     */   public InteractionType[] interruptedBy;
/*     */   @Nullable
/*     */   public InteractionType[] interrupting;
/*     */   public int blockedByBypassIndex;
/*     */   public int blockingBypassIndex;
/*     */   public int interruptedByBypassIndex;
/*     */   public int interruptingBypassIndex;
/*     */   
/*     */   public InteractionRules() {}
/*     */   
/*     */   public InteractionRules(@Nullable InteractionType[] blockedBy, @Nullable InteractionType[] blocking, @Nullable InteractionType[] interruptedBy, @Nullable InteractionType[] interrupting, int blockedByBypassIndex, int blockingBypassIndex, int interruptedByBypassIndex, int interruptingBypassIndex) {
/*  33 */     this.blockedBy = blockedBy;
/*  34 */     this.blocking = blocking;
/*  35 */     this.interruptedBy = interruptedBy;
/*  36 */     this.interrupting = interrupting;
/*  37 */     this.blockedByBypassIndex = blockedByBypassIndex;
/*  38 */     this.blockingBypassIndex = blockingBypassIndex;
/*  39 */     this.interruptedByBypassIndex = interruptedByBypassIndex;
/*  40 */     this.interruptingBypassIndex = interruptingBypassIndex;
/*     */   }
/*     */   
/*     */   public InteractionRules(@Nonnull InteractionRules other) {
/*  44 */     this.blockedBy = other.blockedBy;
/*  45 */     this.blocking = other.blocking;
/*  46 */     this.interruptedBy = other.interruptedBy;
/*  47 */     this.interrupting = other.interrupting;
/*  48 */     this.blockedByBypassIndex = other.blockedByBypassIndex;
/*  49 */     this.blockingBypassIndex = other.blockingBypassIndex;
/*  50 */     this.interruptedByBypassIndex = other.interruptedByBypassIndex;
/*  51 */     this.interruptingBypassIndex = other.interruptingBypassIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionRules deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     InteractionRules obj = new InteractionRules();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.blockedByBypassIndex = buf.getIntLE(offset + 1);
/*  59 */     obj.blockingBypassIndex = buf.getIntLE(offset + 5);
/*  60 */     obj.interruptedByBypassIndex = buf.getIntLE(offset + 9);
/*  61 */     obj.interruptingBypassIndex = buf.getIntLE(offset + 13);
/*     */     
/*  63 */     if ((nullBits & 0x1) != 0) {
/*  64 */       int varPos0 = offset + 33 + buf.getIntLE(offset + 17);
/*  65 */       int blockedByCount = VarInt.peek(buf, varPos0);
/*  66 */       if (blockedByCount < 0) throw ProtocolException.negativeLength("BlockedBy", blockedByCount); 
/*  67 */       if (blockedByCount > 4096000) throw ProtocolException.arrayTooLong("BlockedBy", blockedByCount, 4096000); 
/*  68 */       int varIntLen = VarInt.length(buf, varPos0);
/*  69 */       if ((varPos0 + varIntLen) + blockedByCount * 1L > buf.readableBytes())
/*  70 */         throw ProtocolException.bufferTooSmall("BlockedBy", varPos0 + varIntLen + blockedByCount * 1, buf.readableBytes()); 
/*  71 */       obj.blockedBy = new InteractionType[blockedByCount];
/*  72 */       int elemPos = varPos0 + varIntLen;
/*  73 */       for (int i = 0; i < blockedByCount; i++) {
/*  74 */         obj.blockedBy[i] = InteractionType.fromValue(buf.getByte(elemPos)); elemPos++;
/*     */       } 
/*     */     } 
/*  77 */     if ((nullBits & 0x2) != 0) {
/*  78 */       int varPos1 = offset + 33 + buf.getIntLE(offset + 21);
/*  79 */       int blockingCount = VarInt.peek(buf, varPos1);
/*  80 */       if (blockingCount < 0) throw ProtocolException.negativeLength("Blocking", blockingCount); 
/*  81 */       if (blockingCount > 4096000) throw ProtocolException.arrayTooLong("Blocking", blockingCount, 4096000); 
/*  82 */       int varIntLen = VarInt.length(buf, varPos1);
/*  83 */       if ((varPos1 + varIntLen) + blockingCount * 1L > buf.readableBytes())
/*  84 */         throw ProtocolException.bufferTooSmall("Blocking", varPos1 + varIntLen + blockingCount * 1, buf.readableBytes()); 
/*  85 */       obj.blocking = new InteractionType[blockingCount];
/*  86 */       int elemPos = varPos1 + varIntLen;
/*  87 */       for (int i = 0; i < blockingCount; i++) {
/*  88 */         obj.blocking[i] = InteractionType.fromValue(buf.getByte(elemPos)); elemPos++;
/*     */       } 
/*     */     } 
/*  91 */     if ((nullBits & 0x4) != 0) {
/*  92 */       int varPos2 = offset + 33 + buf.getIntLE(offset + 25);
/*  93 */       int interruptedByCount = VarInt.peek(buf, varPos2);
/*  94 */       if (interruptedByCount < 0) throw ProtocolException.negativeLength("InterruptedBy", interruptedByCount); 
/*  95 */       if (interruptedByCount > 4096000) throw ProtocolException.arrayTooLong("InterruptedBy", interruptedByCount, 4096000); 
/*  96 */       int varIntLen = VarInt.length(buf, varPos2);
/*  97 */       if ((varPos2 + varIntLen) + interruptedByCount * 1L > buf.readableBytes())
/*  98 */         throw ProtocolException.bufferTooSmall("InterruptedBy", varPos2 + varIntLen + interruptedByCount * 1, buf.readableBytes()); 
/*  99 */       obj.interruptedBy = new InteractionType[interruptedByCount];
/* 100 */       int elemPos = varPos2 + varIntLen;
/* 101 */       for (int i = 0; i < interruptedByCount; i++) {
/* 102 */         obj.interruptedBy[i] = InteractionType.fromValue(buf.getByte(elemPos)); elemPos++;
/*     */       } 
/*     */     } 
/* 105 */     if ((nullBits & 0x8) != 0) {
/* 106 */       int varPos3 = offset + 33 + buf.getIntLE(offset + 29);
/* 107 */       int interruptingCount = VarInt.peek(buf, varPos3);
/* 108 */       if (interruptingCount < 0) throw ProtocolException.negativeLength("Interrupting", interruptingCount); 
/* 109 */       if (interruptingCount > 4096000) throw ProtocolException.arrayTooLong("Interrupting", interruptingCount, 4096000); 
/* 110 */       int varIntLen = VarInt.length(buf, varPos3);
/* 111 */       if ((varPos3 + varIntLen) + interruptingCount * 1L > buf.readableBytes())
/* 112 */         throw ProtocolException.bufferTooSmall("Interrupting", varPos3 + varIntLen + interruptingCount * 1, buf.readableBytes()); 
/* 113 */       obj.interrupting = new InteractionType[interruptingCount];
/* 114 */       int elemPos = varPos3 + varIntLen;
/* 115 */       for (int i = 0; i < interruptingCount; i++) {
/* 116 */         obj.interrupting[i] = InteractionType.fromValue(buf.getByte(elemPos)); elemPos++;
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 124 */     byte nullBits = buf.getByte(offset);
/* 125 */     int maxEnd = 33;
/* 126 */     if ((nullBits & 0x1) != 0) {
/* 127 */       int fieldOffset0 = buf.getIntLE(offset + 17);
/* 128 */       int pos0 = offset + 33 + fieldOffset0;
/* 129 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 1;
/* 130 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 132 */     if ((nullBits & 0x2) != 0) {
/* 133 */       int fieldOffset1 = buf.getIntLE(offset + 21);
/* 134 */       int pos1 = offset + 33 + fieldOffset1;
/* 135 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 1;
/* 136 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x4) != 0) {
/* 139 */       int fieldOffset2 = buf.getIntLE(offset + 25);
/* 140 */       int pos2 = offset + 33 + fieldOffset2;
/* 141 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 1;
/* 142 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 144 */     if ((nullBits & 0x8) != 0) {
/* 145 */       int fieldOffset3 = buf.getIntLE(offset + 29);
/* 146 */       int pos3 = offset + 33 + fieldOffset3;
/* 147 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 1;
/* 148 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 150 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 155 */     int startPos = buf.writerIndex();
/* 156 */     byte nullBits = 0;
/* 157 */     if (this.blockedBy != null) nullBits = (byte)(nullBits | 0x1); 
/* 158 */     if (this.blocking != null) nullBits = (byte)(nullBits | 0x2); 
/* 159 */     if (this.interruptedBy != null) nullBits = (byte)(nullBits | 0x4); 
/* 160 */     if (this.interrupting != null) nullBits = (byte)(nullBits | 0x8); 
/* 161 */     buf.writeByte(nullBits);
/*     */     
/* 163 */     buf.writeIntLE(this.blockedByBypassIndex);
/* 164 */     buf.writeIntLE(this.blockingBypassIndex);
/* 165 */     buf.writeIntLE(this.interruptedByBypassIndex);
/* 166 */     buf.writeIntLE(this.interruptingBypassIndex);
/*     */     
/* 168 */     int blockedByOffsetSlot = buf.writerIndex();
/* 169 */     buf.writeIntLE(0);
/* 170 */     int blockingOffsetSlot = buf.writerIndex();
/* 171 */     buf.writeIntLE(0);
/* 172 */     int interruptedByOffsetSlot = buf.writerIndex();
/* 173 */     buf.writeIntLE(0);
/* 174 */     int interruptingOffsetSlot = buf.writerIndex();
/* 175 */     buf.writeIntLE(0);
/*     */     
/* 177 */     int varBlockStart = buf.writerIndex();
/* 178 */     if (this.blockedBy != null) {
/* 179 */       buf.setIntLE(blockedByOffsetSlot, buf.writerIndex() - varBlockStart);
/* 180 */       if (this.blockedBy.length > 4096000) throw ProtocolException.arrayTooLong("BlockedBy", this.blockedBy.length, 4096000);  VarInt.write(buf, this.blockedBy.length); for (InteractionType item : this.blockedBy) buf.writeByte(item.getValue()); 
/*     */     } else {
/* 182 */       buf.setIntLE(blockedByOffsetSlot, -1);
/*     */     } 
/* 184 */     if (this.blocking != null) {
/* 185 */       buf.setIntLE(blockingOffsetSlot, buf.writerIndex() - varBlockStart);
/* 186 */       if (this.blocking.length > 4096000) throw ProtocolException.arrayTooLong("Blocking", this.blocking.length, 4096000);  VarInt.write(buf, this.blocking.length); for (InteractionType item : this.blocking) buf.writeByte(item.getValue()); 
/*     */     } else {
/* 188 */       buf.setIntLE(blockingOffsetSlot, -1);
/*     */     } 
/* 190 */     if (this.interruptedBy != null) {
/* 191 */       buf.setIntLE(interruptedByOffsetSlot, buf.writerIndex() - varBlockStart);
/* 192 */       if (this.interruptedBy.length > 4096000) throw ProtocolException.arrayTooLong("InterruptedBy", this.interruptedBy.length, 4096000);  VarInt.write(buf, this.interruptedBy.length); for (InteractionType item : this.interruptedBy) buf.writeByte(item.getValue()); 
/*     */     } else {
/* 194 */       buf.setIntLE(interruptedByOffsetSlot, -1);
/*     */     } 
/* 196 */     if (this.interrupting != null) {
/* 197 */       buf.setIntLE(interruptingOffsetSlot, buf.writerIndex() - varBlockStart);
/* 198 */       if (this.interrupting.length > 4096000) throw ProtocolException.arrayTooLong("Interrupting", this.interrupting.length, 4096000);  VarInt.write(buf, this.interrupting.length); for (InteractionType item : this.interrupting) buf.writeByte(item.getValue()); 
/*     */     } else {
/* 200 */       buf.setIntLE(interruptingOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 206 */     int size = 33;
/* 207 */     if (this.blockedBy != null) size += VarInt.size(this.blockedBy.length) + this.blockedBy.length * 1; 
/* 208 */     if (this.blocking != null) size += VarInt.size(this.blocking.length) + this.blocking.length * 1; 
/* 209 */     if (this.interruptedBy != null) size += VarInt.size(this.interruptedBy.length) + this.interruptedBy.length * 1; 
/* 210 */     if (this.interrupting != null) size += VarInt.size(this.interrupting.length) + this.interrupting.length * 1;
/*     */     
/* 212 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 216 */     if (buffer.readableBytes() - offset < 33) {
/* 217 */       return ValidationResult.error("Buffer too small: expected at least 33 bytes");
/*     */     }
/*     */     
/* 220 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 223 */     if ((nullBits & 0x1) != 0) {
/* 224 */       int blockedByOffset = buffer.getIntLE(offset + 17);
/* 225 */       if (blockedByOffset < 0) {
/* 226 */         return ValidationResult.error("Invalid offset for BlockedBy");
/*     */       }
/* 228 */       int pos = offset + 33 + blockedByOffset;
/* 229 */       if (pos >= buffer.writerIndex()) {
/* 230 */         return ValidationResult.error("Offset out of bounds for BlockedBy");
/*     */       }
/* 232 */       int blockedByCount = VarInt.peek(buffer, pos);
/* 233 */       if (blockedByCount < 0) {
/* 234 */         return ValidationResult.error("Invalid array count for BlockedBy");
/*     */       }
/* 236 */       if (blockedByCount > 4096000) {
/* 237 */         return ValidationResult.error("BlockedBy exceeds max length 4096000");
/*     */       }
/* 239 */       pos += VarInt.length(buffer, pos);
/* 240 */       pos += blockedByCount * 1;
/* 241 */       if (pos > buffer.writerIndex()) {
/* 242 */         return ValidationResult.error("Buffer overflow reading BlockedBy");
/*     */       }
/*     */     } 
/*     */     
/* 246 */     if ((nullBits & 0x2) != 0) {
/* 247 */       int blockingOffset = buffer.getIntLE(offset + 21);
/* 248 */       if (blockingOffset < 0) {
/* 249 */         return ValidationResult.error("Invalid offset for Blocking");
/*     */       }
/* 251 */       int pos = offset + 33 + blockingOffset;
/* 252 */       if (pos >= buffer.writerIndex()) {
/* 253 */         return ValidationResult.error("Offset out of bounds for Blocking");
/*     */       }
/* 255 */       int blockingCount = VarInt.peek(buffer, pos);
/* 256 */       if (blockingCount < 0) {
/* 257 */         return ValidationResult.error("Invalid array count for Blocking");
/*     */       }
/* 259 */       if (blockingCount > 4096000) {
/* 260 */         return ValidationResult.error("Blocking exceeds max length 4096000");
/*     */       }
/* 262 */       pos += VarInt.length(buffer, pos);
/* 263 */       pos += blockingCount * 1;
/* 264 */       if (pos > buffer.writerIndex()) {
/* 265 */         return ValidationResult.error("Buffer overflow reading Blocking");
/*     */       }
/*     */     } 
/*     */     
/* 269 */     if ((nullBits & 0x4) != 0) {
/* 270 */       int interruptedByOffset = buffer.getIntLE(offset + 25);
/* 271 */       if (interruptedByOffset < 0) {
/* 272 */         return ValidationResult.error("Invalid offset for InterruptedBy");
/*     */       }
/* 274 */       int pos = offset + 33 + interruptedByOffset;
/* 275 */       if (pos >= buffer.writerIndex()) {
/* 276 */         return ValidationResult.error("Offset out of bounds for InterruptedBy");
/*     */       }
/* 278 */       int interruptedByCount = VarInt.peek(buffer, pos);
/* 279 */       if (interruptedByCount < 0) {
/* 280 */         return ValidationResult.error("Invalid array count for InterruptedBy");
/*     */       }
/* 282 */       if (interruptedByCount > 4096000) {
/* 283 */         return ValidationResult.error("InterruptedBy exceeds max length 4096000");
/*     */       }
/* 285 */       pos += VarInt.length(buffer, pos);
/* 286 */       pos += interruptedByCount * 1;
/* 287 */       if (pos > buffer.writerIndex()) {
/* 288 */         return ValidationResult.error("Buffer overflow reading InterruptedBy");
/*     */       }
/*     */     } 
/*     */     
/* 292 */     if ((nullBits & 0x8) != 0) {
/* 293 */       int interruptingOffset = buffer.getIntLE(offset + 29);
/* 294 */       if (interruptingOffset < 0) {
/* 295 */         return ValidationResult.error("Invalid offset for Interrupting");
/*     */       }
/* 297 */       int pos = offset + 33 + interruptingOffset;
/* 298 */       if (pos >= buffer.writerIndex()) {
/* 299 */         return ValidationResult.error("Offset out of bounds for Interrupting");
/*     */       }
/* 301 */       int interruptingCount = VarInt.peek(buffer, pos);
/* 302 */       if (interruptingCount < 0) {
/* 303 */         return ValidationResult.error("Invalid array count for Interrupting");
/*     */       }
/* 305 */       if (interruptingCount > 4096000) {
/* 306 */         return ValidationResult.error("Interrupting exceeds max length 4096000");
/*     */       }
/* 308 */       pos += VarInt.length(buffer, pos);
/* 309 */       pos += interruptingCount * 1;
/* 310 */       if (pos > buffer.writerIndex()) {
/* 311 */         return ValidationResult.error("Buffer overflow reading Interrupting");
/*     */       }
/*     */     } 
/* 314 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionRules clone() {
/* 318 */     InteractionRules copy = new InteractionRules();
/* 319 */     copy.blockedBy = (this.blockedBy != null) ? Arrays.<InteractionType>copyOf(this.blockedBy, this.blockedBy.length) : null;
/* 320 */     copy.blocking = (this.blocking != null) ? Arrays.<InteractionType>copyOf(this.blocking, this.blocking.length) : null;
/* 321 */     copy.interruptedBy = (this.interruptedBy != null) ? Arrays.<InteractionType>copyOf(this.interruptedBy, this.interruptedBy.length) : null;
/* 322 */     copy.interrupting = (this.interrupting != null) ? Arrays.<InteractionType>copyOf(this.interrupting, this.interrupting.length) : null;
/* 323 */     copy.blockedByBypassIndex = this.blockedByBypassIndex;
/* 324 */     copy.blockingBypassIndex = this.blockingBypassIndex;
/* 325 */     copy.interruptedByBypassIndex = this.interruptedByBypassIndex;
/* 326 */     copy.interruptingBypassIndex = this.interruptingBypassIndex;
/* 327 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionRules other;
/* 333 */     if (this == obj) return true; 
/* 334 */     if (obj instanceof InteractionRules) { other = (InteractionRules)obj; } else { return false; }
/* 335 */      return (Arrays.equals((Object[])this.blockedBy, (Object[])other.blockedBy) && Arrays.equals((Object[])this.blocking, (Object[])other.blocking) && Arrays.equals((Object[])this.interruptedBy, (Object[])other.interruptedBy) && Arrays.equals((Object[])this.interrupting, (Object[])other.interrupting) && this.blockedByBypassIndex == other.blockedByBypassIndex && this.blockingBypassIndex == other.blockingBypassIndex && this.interruptedByBypassIndex == other.interruptedByBypassIndex && this.interruptingBypassIndex == other.interruptingBypassIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 340 */     int result = 1;
/* 341 */     result = 31 * result + Arrays.hashCode((Object[])this.blockedBy);
/* 342 */     result = 31 * result + Arrays.hashCode((Object[])this.blocking);
/* 343 */     result = 31 * result + Arrays.hashCode((Object[])this.interruptedBy);
/* 344 */     result = 31 * result + Arrays.hashCode((Object[])this.interrupting);
/* 345 */     result = 31 * result + Integer.hashCode(this.blockedByBypassIndex);
/* 346 */     result = 31 * result + Integer.hashCode(this.blockingBypassIndex);
/* 347 */     result = 31 * result + Integer.hashCode(this.interruptedByBypassIndex);
/* 348 */     result = 31 * result + Integer.hashCode(this.interruptingBypassIndex);
/* 349 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionRules.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */