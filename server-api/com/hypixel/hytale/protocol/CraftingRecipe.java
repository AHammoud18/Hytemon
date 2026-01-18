/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ public class CraftingRecipe
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 10;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public MaterialQuantity[] inputs;
/*     */   
/*     */   public CraftingRecipe(@Nullable String id, @Nullable MaterialQuantity[] inputs, @Nullable MaterialQuantity[] outputs, @Nullable MaterialQuantity primaryOutput, @Nullable BenchRequirement[] benchRequirement, boolean knowledgeRequired, float timeSeconds, int requiredMemoriesLevel) {
/*  33 */     this.id = id;
/*  34 */     this.inputs = inputs;
/*  35 */     this.outputs = outputs;
/*  36 */     this.primaryOutput = primaryOutput;
/*  37 */     this.benchRequirement = benchRequirement;
/*  38 */     this.knowledgeRequired = knowledgeRequired;
/*  39 */     this.timeSeconds = timeSeconds;
/*  40 */     this.requiredMemoriesLevel = requiredMemoriesLevel; } @Nullable
/*     */   public MaterialQuantity[] outputs; @Nullable
/*     */   public MaterialQuantity primaryOutput; @Nullable
/*     */   public BenchRequirement[] benchRequirement; public boolean knowledgeRequired; public float timeSeconds; public int requiredMemoriesLevel; public CraftingRecipe() {} public CraftingRecipe(@Nonnull CraftingRecipe other) {
/*  44 */     this.id = other.id;
/*  45 */     this.inputs = other.inputs;
/*  46 */     this.outputs = other.outputs;
/*  47 */     this.primaryOutput = other.primaryOutput;
/*  48 */     this.benchRequirement = other.benchRequirement;
/*  49 */     this.knowledgeRequired = other.knowledgeRequired;
/*  50 */     this.timeSeconds = other.timeSeconds;
/*  51 */     this.requiredMemoriesLevel = other.requiredMemoriesLevel;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CraftingRecipe deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     CraftingRecipe obj = new CraftingRecipe();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.knowledgeRequired = (buf.getByte(offset + 1) != 0);
/*  59 */     obj.timeSeconds = buf.getFloatLE(offset + 2);
/*  60 */     obj.requiredMemoriesLevel = buf.getIntLE(offset + 6);
/*     */     
/*  62 */     if ((nullBits & 0x1) != 0) {
/*  63 */       int varPos0 = offset + 30 + buf.getIntLE(offset + 10);
/*  64 */       int idLen = VarInt.peek(buf, varPos0);
/*  65 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  66 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  67 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  69 */     if ((nullBits & 0x2) != 0) {
/*  70 */       int varPos1 = offset + 30 + buf.getIntLE(offset + 14);
/*  71 */       int inputsCount = VarInt.peek(buf, varPos1);
/*  72 */       if (inputsCount < 0) throw ProtocolException.negativeLength("Inputs", inputsCount); 
/*  73 */       if (inputsCount > 4096000) throw ProtocolException.arrayTooLong("Inputs", inputsCount, 4096000); 
/*  74 */       int varIntLen = VarInt.length(buf, varPos1);
/*  75 */       if ((varPos1 + varIntLen) + inputsCount * 9L > buf.readableBytes())
/*  76 */         throw ProtocolException.bufferTooSmall("Inputs", varPos1 + varIntLen + inputsCount * 9, buf.readableBytes()); 
/*  77 */       obj.inputs = new MaterialQuantity[inputsCount];
/*  78 */       int elemPos = varPos1 + varIntLen;
/*  79 */       for (int i = 0; i < inputsCount; i++) {
/*  80 */         obj.inputs[i] = MaterialQuantity.deserialize(buf, elemPos);
/*  81 */         elemPos += MaterialQuantity.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  84 */     if ((nullBits & 0x4) != 0) {
/*  85 */       int varPos2 = offset + 30 + buf.getIntLE(offset + 18);
/*  86 */       int outputsCount = VarInt.peek(buf, varPos2);
/*  87 */       if (outputsCount < 0) throw ProtocolException.negativeLength("Outputs", outputsCount); 
/*  88 */       if (outputsCount > 4096000) throw ProtocolException.arrayTooLong("Outputs", outputsCount, 4096000); 
/*  89 */       int varIntLen = VarInt.length(buf, varPos2);
/*  90 */       if ((varPos2 + varIntLen) + outputsCount * 9L > buf.readableBytes())
/*  91 */         throw ProtocolException.bufferTooSmall("Outputs", varPos2 + varIntLen + outputsCount * 9, buf.readableBytes()); 
/*  92 */       obj.outputs = new MaterialQuantity[outputsCount];
/*  93 */       int elemPos = varPos2 + varIntLen;
/*  94 */       for (int i = 0; i < outputsCount; i++) {
/*  95 */         obj.outputs[i] = MaterialQuantity.deserialize(buf, elemPos);
/*  96 */         elemPos += MaterialQuantity.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  99 */     if ((nullBits & 0x8) != 0) {
/* 100 */       int varPos3 = offset + 30 + buf.getIntLE(offset + 22);
/* 101 */       obj.primaryOutput = MaterialQuantity.deserialize(buf, varPos3);
/*     */     } 
/* 103 */     if ((nullBits & 0x10) != 0) {
/* 104 */       int varPos4 = offset + 30 + buf.getIntLE(offset + 26);
/* 105 */       int benchRequirementCount = VarInt.peek(buf, varPos4);
/* 106 */       if (benchRequirementCount < 0) throw ProtocolException.negativeLength("BenchRequirement", benchRequirementCount); 
/* 107 */       if (benchRequirementCount > 4096000) throw ProtocolException.arrayTooLong("BenchRequirement", benchRequirementCount, 4096000); 
/* 108 */       int varIntLen = VarInt.length(buf, varPos4);
/* 109 */       if ((varPos4 + varIntLen) + benchRequirementCount * 6L > buf.readableBytes())
/* 110 */         throw ProtocolException.bufferTooSmall("BenchRequirement", varPos4 + varIntLen + benchRequirementCount * 6, buf.readableBytes()); 
/* 111 */       obj.benchRequirement = new BenchRequirement[benchRequirementCount];
/* 112 */       int elemPos = varPos4 + varIntLen;
/* 113 */       for (int i = 0; i < benchRequirementCount; i++) {
/* 114 */         obj.benchRequirement[i] = BenchRequirement.deserialize(buf, elemPos);
/* 115 */         elemPos += BenchRequirement.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 123 */     byte nullBits = buf.getByte(offset);
/* 124 */     int maxEnd = 30;
/* 125 */     if ((nullBits & 0x1) != 0) {
/* 126 */       int fieldOffset0 = buf.getIntLE(offset + 10);
/* 127 */       int pos0 = offset + 30 + fieldOffset0;
/* 128 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 129 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 131 */     if ((nullBits & 0x2) != 0) {
/* 132 */       int fieldOffset1 = buf.getIntLE(offset + 14);
/* 133 */       int pos1 = offset + 30 + fieldOffset1;
/* 134 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 135 */       for (int i = 0; i < arrLen; ) { pos1 += MaterialQuantity.computeBytesConsumed(buf, pos1); i++; }
/* 136 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x4) != 0) {
/* 139 */       int fieldOffset2 = buf.getIntLE(offset + 18);
/* 140 */       int pos2 = offset + 30 + fieldOffset2;
/* 141 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 142 */       for (int i = 0; i < arrLen; ) { pos2 += MaterialQuantity.computeBytesConsumed(buf, pos2); i++; }
/* 143 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 145 */     if ((nullBits & 0x8) != 0) {
/* 146 */       int fieldOffset3 = buf.getIntLE(offset + 22);
/* 147 */       int pos3 = offset + 30 + fieldOffset3;
/* 148 */       pos3 += MaterialQuantity.computeBytesConsumed(buf, pos3);
/* 149 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 151 */     if ((nullBits & 0x10) != 0) {
/* 152 */       int fieldOffset4 = buf.getIntLE(offset + 26);
/* 153 */       int pos4 = offset + 30 + fieldOffset4;
/* 154 */       int arrLen = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4);
/* 155 */       for (int i = 0; i < arrLen; ) { pos4 += BenchRequirement.computeBytesConsumed(buf, pos4); i++; }
/* 156 */        if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 158 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 163 */     int startPos = buf.writerIndex();
/* 164 */     byte nullBits = 0;
/* 165 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 166 */     if (this.inputs != null) nullBits = (byte)(nullBits | 0x2); 
/* 167 */     if (this.outputs != null) nullBits = (byte)(nullBits | 0x4); 
/* 168 */     if (this.primaryOutput != null) nullBits = (byte)(nullBits | 0x8); 
/* 169 */     if (this.benchRequirement != null) nullBits = (byte)(nullBits | 0x10); 
/* 170 */     buf.writeByte(nullBits);
/*     */     
/* 172 */     buf.writeByte(this.knowledgeRequired ? 1 : 0);
/* 173 */     buf.writeFloatLE(this.timeSeconds);
/* 174 */     buf.writeIntLE(this.requiredMemoriesLevel);
/*     */     
/* 176 */     int idOffsetSlot = buf.writerIndex();
/* 177 */     buf.writeIntLE(0);
/* 178 */     int inputsOffsetSlot = buf.writerIndex();
/* 179 */     buf.writeIntLE(0);
/* 180 */     int outputsOffsetSlot = buf.writerIndex();
/* 181 */     buf.writeIntLE(0);
/* 182 */     int primaryOutputOffsetSlot = buf.writerIndex();
/* 183 */     buf.writeIntLE(0);
/* 184 */     int benchRequirementOffsetSlot = buf.writerIndex();
/* 185 */     buf.writeIntLE(0);
/*     */     
/* 187 */     int varBlockStart = buf.writerIndex();
/* 188 */     if (this.id != null) {
/* 189 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 190 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 192 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 194 */     if (this.inputs != null) {
/* 195 */       buf.setIntLE(inputsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 196 */       if (this.inputs.length > 4096000) throw ProtocolException.arrayTooLong("Inputs", this.inputs.length, 4096000);  VarInt.write(buf, this.inputs.length); for (MaterialQuantity item : this.inputs) item.serialize(buf); 
/*     */     } else {
/* 198 */       buf.setIntLE(inputsOffsetSlot, -1);
/*     */     } 
/* 200 */     if (this.outputs != null) {
/* 201 */       buf.setIntLE(outputsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 202 */       if (this.outputs.length > 4096000) throw ProtocolException.arrayTooLong("Outputs", this.outputs.length, 4096000);  VarInt.write(buf, this.outputs.length); for (MaterialQuantity item : this.outputs) item.serialize(buf); 
/*     */     } else {
/* 204 */       buf.setIntLE(outputsOffsetSlot, -1);
/*     */     } 
/* 206 */     if (this.primaryOutput != null) {
/* 207 */       buf.setIntLE(primaryOutputOffsetSlot, buf.writerIndex() - varBlockStart);
/* 208 */       this.primaryOutput.serialize(buf);
/*     */     } else {
/* 210 */       buf.setIntLE(primaryOutputOffsetSlot, -1);
/*     */     } 
/* 212 */     if (this.benchRequirement != null) {
/* 213 */       buf.setIntLE(benchRequirementOffsetSlot, buf.writerIndex() - varBlockStart);
/* 214 */       if (this.benchRequirement.length > 4096000) throw ProtocolException.arrayTooLong("BenchRequirement", this.benchRequirement.length, 4096000);  VarInt.write(buf, this.benchRequirement.length); for (BenchRequirement item : this.benchRequirement) item.serialize(buf); 
/*     */     } else {
/* 216 */       buf.setIntLE(benchRequirementOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 222 */     int size = 30;
/* 223 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 224 */     if (this.inputs != null) {
/* 225 */       int inputsSize = 0;
/* 226 */       for (MaterialQuantity elem : this.inputs) inputsSize += elem.computeSize(); 
/* 227 */       size += VarInt.size(this.inputs.length) + inputsSize;
/*     */     } 
/* 229 */     if (this.outputs != null) {
/* 230 */       int outputsSize = 0;
/* 231 */       for (MaterialQuantity elem : this.outputs) outputsSize += elem.computeSize(); 
/* 232 */       size += VarInt.size(this.outputs.length) + outputsSize;
/*     */     } 
/* 234 */     if (this.primaryOutput != null) size += this.primaryOutput.computeSize(); 
/* 235 */     if (this.benchRequirement != null) {
/* 236 */       int benchRequirementSize = 0;
/* 237 */       for (BenchRequirement elem : this.benchRequirement) benchRequirementSize += elem.computeSize(); 
/* 238 */       size += VarInt.size(this.benchRequirement.length) + benchRequirementSize;
/*     */     } 
/*     */     
/* 241 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 245 */     if (buffer.readableBytes() - offset < 30) {
/* 246 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */     
/* 249 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 252 */     if ((nullBits & 0x1) != 0) {
/* 253 */       int idOffset = buffer.getIntLE(offset + 10);
/* 254 */       if (idOffset < 0) {
/* 255 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 257 */       int pos = offset + 30 + idOffset;
/* 258 */       if (pos >= buffer.writerIndex()) {
/* 259 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 261 */       int idLen = VarInt.peek(buffer, pos);
/* 262 */       if (idLen < 0) {
/* 263 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 265 */       if (idLen > 4096000) {
/* 266 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 268 */       pos += VarInt.length(buffer, pos);
/* 269 */       pos += idLen;
/* 270 */       if (pos > buffer.writerIndex()) {
/* 271 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 275 */     if ((nullBits & 0x2) != 0) {
/* 276 */       int inputsOffset = buffer.getIntLE(offset + 14);
/* 277 */       if (inputsOffset < 0) {
/* 278 */         return ValidationResult.error("Invalid offset for Inputs");
/*     */       }
/* 280 */       int pos = offset + 30 + inputsOffset;
/* 281 */       if (pos >= buffer.writerIndex()) {
/* 282 */         return ValidationResult.error("Offset out of bounds for Inputs");
/*     */       }
/* 284 */       int inputsCount = VarInt.peek(buffer, pos);
/* 285 */       if (inputsCount < 0) {
/* 286 */         return ValidationResult.error("Invalid array count for Inputs");
/*     */       }
/* 288 */       if (inputsCount > 4096000) {
/* 289 */         return ValidationResult.error("Inputs exceeds max length 4096000");
/*     */       }
/* 291 */       pos += VarInt.length(buffer, pos);
/* 292 */       for (int i = 0; i < inputsCount; i++) {
/* 293 */         ValidationResult structResult = MaterialQuantity.validateStructure(buffer, pos);
/* 294 */         if (!structResult.isValid()) {
/* 295 */           return ValidationResult.error("Invalid MaterialQuantity in Inputs[" + i + "]: " + structResult.error());
/*     */         }
/* 297 */         pos += MaterialQuantity.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     if ((nullBits & 0x4) != 0) {
/* 302 */       int outputsOffset = buffer.getIntLE(offset + 18);
/* 303 */       if (outputsOffset < 0) {
/* 304 */         return ValidationResult.error("Invalid offset for Outputs");
/*     */       }
/* 306 */       int pos = offset + 30 + outputsOffset;
/* 307 */       if (pos >= buffer.writerIndex()) {
/* 308 */         return ValidationResult.error("Offset out of bounds for Outputs");
/*     */       }
/* 310 */       int outputsCount = VarInt.peek(buffer, pos);
/* 311 */       if (outputsCount < 0) {
/* 312 */         return ValidationResult.error("Invalid array count for Outputs");
/*     */       }
/* 314 */       if (outputsCount > 4096000) {
/* 315 */         return ValidationResult.error("Outputs exceeds max length 4096000");
/*     */       }
/* 317 */       pos += VarInt.length(buffer, pos);
/* 318 */       for (int i = 0; i < outputsCount; i++) {
/* 319 */         ValidationResult structResult = MaterialQuantity.validateStructure(buffer, pos);
/* 320 */         if (!structResult.isValid()) {
/* 321 */           return ValidationResult.error("Invalid MaterialQuantity in Outputs[" + i + "]: " + structResult.error());
/*     */         }
/* 323 */         pos += MaterialQuantity.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     if ((nullBits & 0x8) != 0) {
/* 328 */       int primaryOutputOffset = buffer.getIntLE(offset + 22);
/* 329 */       if (primaryOutputOffset < 0) {
/* 330 */         return ValidationResult.error("Invalid offset for PrimaryOutput");
/*     */       }
/* 332 */       int pos = offset + 30 + primaryOutputOffset;
/* 333 */       if (pos >= buffer.writerIndex()) {
/* 334 */         return ValidationResult.error("Offset out of bounds for PrimaryOutput");
/*     */       }
/* 336 */       ValidationResult primaryOutputResult = MaterialQuantity.validateStructure(buffer, pos);
/* 337 */       if (!primaryOutputResult.isValid()) {
/* 338 */         return ValidationResult.error("Invalid PrimaryOutput: " + primaryOutputResult.error());
/*     */       }
/* 340 */       pos += MaterialQuantity.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 343 */     if ((nullBits & 0x10) != 0) {
/* 344 */       int benchRequirementOffset = buffer.getIntLE(offset + 26);
/* 345 */       if (benchRequirementOffset < 0) {
/* 346 */         return ValidationResult.error("Invalid offset for BenchRequirement");
/*     */       }
/* 348 */       int pos = offset + 30 + benchRequirementOffset;
/* 349 */       if (pos >= buffer.writerIndex()) {
/* 350 */         return ValidationResult.error("Offset out of bounds for BenchRequirement");
/*     */       }
/* 352 */       int benchRequirementCount = VarInt.peek(buffer, pos);
/* 353 */       if (benchRequirementCount < 0) {
/* 354 */         return ValidationResult.error("Invalid array count for BenchRequirement");
/*     */       }
/* 356 */       if (benchRequirementCount > 4096000) {
/* 357 */         return ValidationResult.error("BenchRequirement exceeds max length 4096000");
/*     */       }
/* 359 */       pos += VarInt.length(buffer, pos);
/* 360 */       for (int i = 0; i < benchRequirementCount; i++) {
/* 361 */         ValidationResult structResult = BenchRequirement.validateStructure(buffer, pos);
/* 362 */         if (!structResult.isValid()) {
/* 363 */           return ValidationResult.error("Invalid BenchRequirement in BenchRequirement[" + i + "]: " + structResult.error());
/*     */         }
/* 365 */         pos += BenchRequirement.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 368 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CraftingRecipe clone() {
/* 372 */     CraftingRecipe copy = new CraftingRecipe();
/* 373 */     copy.id = this.id;
/* 374 */     copy.inputs = (this.inputs != null) ? (MaterialQuantity[])Arrays.<MaterialQuantity>stream(this.inputs).map(e -> e.clone()).toArray(x$0 -> new MaterialQuantity[x$0]) : null;
/* 375 */     copy.outputs = (this.outputs != null) ? (MaterialQuantity[])Arrays.<MaterialQuantity>stream(this.outputs).map(e -> e.clone()).toArray(x$0 -> new MaterialQuantity[x$0]) : null;
/* 376 */     copy.primaryOutput = (this.primaryOutput != null) ? this.primaryOutput.clone() : null;
/* 377 */     copy.benchRequirement = (this.benchRequirement != null) ? (BenchRequirement[])Arrays.<BenchRequirement>stream(this.benchRequirement).map(e -> e.clone()).toArray(x$0 -> new BenchRequirement[x$0]) : null;
/* 378 */     copy.knowledgeRequired = this.knowledgeRequired;
/* 379 */     copy.timeSeconds = this.timeSeconds;
/* 380 */     copy.requiredMemoriesLevel = this.requiredMemoriesLevel;
/* 381 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CraftingRecipe other;
/* 387 */     if (this == obj) return true; 
/* 388 */     if (obj instanceof CraftingRecipe) { other = (CraftingRecipe)obj; } else { return false; }
/* 389 */      return (Objects.equals(this.id, other.id) && Arrays.equals((Object[])this.inputs, (Object[])other.inputs) && Arrays.equals((Object[])this.outputs, (Object[])other.outputs) && Objects.equals(this.primaryOutput, other.primaryOutput) && Arrays.equals((Object[])this.benchRequirement, (Object[])other.benchRequirement) && this.knowledgeRequired == other.knowledgeRequired && this.timeSeconds == other.timeSeconds && this.requiredMemoriesLevel == other.requiredMemoriesLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 394 */     int result = 1;
/* 395 */     result = 31 * result + Objects.hashCode(this.id);
/* 396 */     result = 31 * result + Arrays.hashCode((Object[])this.inputs);
/* 397 */     result = 31 * result + Arrays.hashCode((Object[])this.outputs);
/* 398 */     result = 31 * result + Objects.hashCode(this.primaryOutput);
/* 399 */     result = 31 * result + Arrays.hashCode((Object[])this.benchRequirement);
/* 400 */     result = 31 * result + Boolean.hashCode(this.knowledgeRequired);
/* 401 */     result = 31 * result + Float.hashCode(this.timeSeconds);
/* 402 */     result = 31 * result + Integer.hashCode(this.requiredMemoriesLevel);
/* 403 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CraftingRecipe.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */