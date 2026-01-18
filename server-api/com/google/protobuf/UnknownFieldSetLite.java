/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnknownFieldSetLite
/*     */ {
/*     */   private static final int MIN_CAPACITY = 8;
/*  29 */   private static final UnknownFieldSetLite DEFAULT_INSTANCE = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
/*     */   
/*     */   private int count;
/*     */   
/*     */   private int[] tags;
/*     */   
/*     */   private Object[] objects;
/*     */   
/*     */   public static UnknownFieldSetLite getDefaultInstance() {
/*  38 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   static UnknownFieldSetLite newInstance() {
/*  43 */     return new UnknownFieldSetLite();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static UnknownFieldSetLite mutableCopyOf(UnknownFieldSetLite first, UnknownFieldSetLite second) {
/*  51 */     int count = first.count + second.count;
/*  52 */     int[] tags = Arrays.copyOf(first.tags, count);
/*  53 */     System.arraycopy(second.tags, 0, tags, first.count, second.count);
/*  54 */     Object[] objects = Arrays.copyOf(first.objects, count);
/*  55 */     System.arraycopy(second.objects, 0, objects, first.count, second.count);
/*  56 */     return new UnknownFieldSetLite(count, tags, objects, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private int memoizedSerializedSize = -1;
/*     */ 
/*     */   
/*     */   private boolean isMutable;
/*     */ 
/*     */   
/*     */   private UnknownFieldSetLite() {
/*  76 */     this(0, new int[8], new Object[8], true);
/*     */   }
/*     */ 
/*     */   
/*     */   private UnknownFieldSetLite(int count, int[] tags, Object[] objects, boolean isMutable) {
/*  81 */     this.count = count;
/*  82 */     this.tags = tags;
/*  83 */     this.objects = objects;
/*  84 */     this.isMutable = isMutable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/*  93 */     if (this.isMutable) {
/*  94 */       this.isMutable = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void checkMutable() {
/* 100 */     if (!this.isMutable) {
/* 101 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 111 */     for (int i = 0; i < this.count; i++) {
/* 112 */       int tag = this.tags[i];
/* 113 */       int fieldNumber = WireFormat.getTagFieldNumber(tag);
/* 114 */       switch (WireFormat.getTagWireType(tag)) {
/*     */         case 0:
/* 116 */           output.writeUInt64(fieldNumber, ((Long)this.objects[i]).longValue());
/*     */           break;
/*     */         case 5:
/* 119 */           output.writeFixed32(fieldNumber, ((Integer)this.objects[i]).intValue());
/*     */           break;
/*     */         case 1:
/* 122 */           output.writeFixed64(fieldNumber, ((Long)this.objects[i]).longValue());
/*     */           break;
/*     */         case 2:
/* 125 */           output.writeBytes(fieldNumber, (ByteString)this.objects[i]);
/*     */           break;
/*     */         case 3:
/* 128 */           output.writeTag(fieldNumber, 3);
/* 129 */           ((UnknownFieldSetLite)this.objects[i]).writeTo(output);
/* 130 */           output.writeTag(fieldNumber, 4);
/*     */           break;
/*     */         default:
/* 133 */           throw InvalidProtocolBufferException.invalidWireType();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAsMessageSetTo(CodedOutputStream output) throws IOException {
/* 144 */     for (int i = 0; i < this.count; i++) {
/* 145 */       int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
/* 146 */       output.writeRawMessageSetExtension(fieldNumber, (ByteString)this.objects[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void writeAsMessageSetTo(Writer writer) throws IOException {
/* 152 */     if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
/*     */       
/* 154 */       for (int i = this.count - 1; i >= 0; i--) {
/* 155 */         int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
/* 156 */         writer.writeMessageSetItem(fieldNumber, this.objects[i]);
/*     */       } 
/*     */     } else {
/*     */       
/* 160 */       for (int i = 0; i < this.count; i++) {
/* 161 */         int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
/* 162 */         writer.writeMessageSetItem(fieldNumber, this.objects[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(Writer writer) throws IOException {
/* 169 */     if (this.count == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 174 */     if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
/* 175 */       for (int i = 0; i < this.count; i++) {
/* 176 */         writeField(this.tags[i], this.objects[i], writer);
/*     */       }
/*     */     } else {
/* 179 */       for (int i = this.count - 1; i >= 0; i--) {
/* 180 */         writeField(this.tags[i], this.objects[i], writer);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeField(int tag, Object object, Writer writer) throws IOException {
/* 186 */     int fieldNumber = WireFormat.getTagFieldNumber(tag);
/* 187 */     switch (WireFormat.getTagWireType(tag)) {
/*     */       case 0:
/* 189 */         writer.writeInt64(fieldNumber, ((Long)object).longValue());
/*     */         return;
/*     */       case 5:
/* 192 */         writer.writeFixed32(fieldNumber, ((Integer)object).intValue());
/*     */         return;
/*     */       case 1:
/* 195 */         writer.writeFixed64(fieldNumber, ((Long)object).longValue());
/*     */         return;
/*     */       case 2:
/* 198 */         writer.writeBytes(fieldNumber, (ByteString)object);
/*     */         return;
/*     */       case 3:
/* 201 */         if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
/* 202 */           writer.writeStartGroup(fieldNumber);
/* 203 */           ((UnknownFieldSetLite)object).writeTo(writer);
/* 204 */           writer.writeEndGroup(fieldNumber);
/*     */         } else {
/* 206 */           writer.writeEndGroup(fieldNumber);
/* 207 */           ((UnknownFieldSetLite)object).writeTo(writer);
/* 208 */           writer.writeStartGroup(fieldNumber);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */     
/* 213 */     throw new RuntimeException(InvalidProtocolBufferException.invalidWireType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSizeAsMessageSet() {
/* 222 */     int size = this.memoizedSerializedSize;
/* 223 */     if (size != -1) {
/* 224 */       return size;
/*     */     }
/*     */     
/* 227 */     size = 0;
/* 228 */     for (int i = 0; i < this.count; i++) {
/* 229 */       int tag = this.tags[i];
/* 230 */       int fieldNumber = WireFormat.getTagFieldNumber(tag);
/* 231 */       size += 
/* 232 */         CodedOutputStream.computeRawMessageSetExtensionSize(fieldNumber, (ByteString)this.objects[i]);
/*     */     } 
/*     */     
/* 235 */     this.memoizedSerializedSize = size;
/*     */     
/* 237 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 246 */     int size = this.memoizedSerializedSize;
/* 247 */     if (size != -1) {
/* 248 */       return size;
/*     */     }
/*     */     
/* 251 */     size = 0;
/* 252 */     for (int i = 0; i < this.count; i++) {
/* 253 */       int tag = this.tags[i];
/* 254 */       int fieldNumber = WireFormat.getTagFieldNumber(tag);
/* 255 */       switch (WireFormat.getTagWireType(tag)) {
/*     */         case 0:
/* 257 */           size += CodedOutputStream.computeUInt64Size(fieldNumber, ((Long)this.objects[i]).longValue());
/*     */           break;
/*     */         case 5:
/* 260 */           size += CodedOutputStream.computeFixed32Size(fieldNumber, ((Integer)this.objects[i]).intValue());
/*     */           break;
/*     */         case 1:
/* 263 */           size += CodedOutputStream.computeFixed64Size(fieldNumber, ((Long)this.objects[i]).longValue());
/*     */           break;
/*     */         case 2:
/* 266 */           size += CodedOutputStream.computeBytesSize(fieldNumber, (ByteString)this.objects[i]);
/*     */           break;
/*     */         case 3:
/* 269 */           size += 
/* 270 */             CodedOutputStream.computeTagSize(fieldNumber) * 2 + ((UnknownFieldSetLite)this.objects[i])
/* 271 */             .getSerializedSize();
/*     */           break;
/*     */         default:
/* 274 */           throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
/*     */       } 
/*     */     
/*     */     } 
/* 278 */     this.memoizedSerializedSize = size;
/*     */     
/* 280 */     return size;
/*     */   }
/*     */   
/*     */   private static boolean tagsEquals(int[] tags1, int[] tags2, int count) {
/* 284 */     for (int i = 0; i < count; i++) {
/* 285 */       if (tags1[i] != tags2[i]) {
/* 286 */         return false;
/*     */       }
/*     */     } 
/* 289 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean objectsEquals(Object[] objects1, Object[] objects2, int count) {
/* 293 */     for (int i = 0; i < count; i++) {
/* 294 */       if (!objects1[i].equals(objects2[i])) {
/* 295 */         return false;
/*     */       }
/*     */     } 
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 304 */     if (this == obj) {
/* 305 */       return true;
/*     */     }
/*     */     
/* 308 */     if (obj == null) {
/* 309 */       return false;
/*     */     }
/*     */     
/* 312 */     if (!(obj instanceof UnknownFieldSetLite)) {
/* 313 */       return false;
/*     */     }
/*     */     
/* 316 */     UnknownFieldSetLite other = (UnknownFieldSetLite)obj;
/* 317 */     if (this.count != other.count || 
/* 318 */       !tagsEquals(this.tags, other.tags, this.count) || 
/* 319 */       !objectsEquals(this.objects, other.objects, this.count)) {
/* 320 */       return false;
/*     */     }
/*     */     
/* 323 */     return true;
/*     */   }
/*     */   
/*     */   private static int hashCode(int[] tags, int count) {
/* 327 */     int hashCode = 17;
/* 328 */     for (int i = 0; i < count; i++) {
/* 329 */       hashCode = 31 * hashCode + tags[i];
/*     */     }
/* 331 */     return hashCode;
/*     */   }
/*     */   
/*     */   private static int hashCode(Object[] objects, int count) {
/* 335 */     int hashCode = 17;
/* 336 */     for (int i = 0; i < count; i++) {
/* 337 */       hashCode = 31 * hashCode + objects[i].hashCode();
/*     */     }
/* 339 */     return hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 344 */     int hashCode = 17;
/*     */     
/* 346 */     hashCode = 31 * hashCode + this.count;
/* 347 */     hashCode = 31 * hashCode + hashCode(this.tags, this.count);
/* 348 */     hashCode = 31 * hashCode + hashCode(this.objects, this.count);
/*     */     
/* 350 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void printWithIndent(StringBuilder buffer, int indent) {
/* 362 */     for (int i = 0; i < this.count; i++) {
/* 363 */       int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
/* 364 */       MessageLiteToString.printField(buffer, indent, String.valueOf(fieldNumber), this.objects[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void storeField(int tag, Object value) {
/* 370 */     checkMutable();
/* 371 */     ensureCapacity(this.count + 1);
/*     */     
/* 373 */     this.tags[this.count] = tag;
/* 374 */     this.objects[this.count] = value;
/* 375 */     this.count++;
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureCapacity(int minCapacity) {
/* 380 */     if (minCapacity > this.tags.length) {
/*     */       
/* 382 */       int newCapacity = this.count + this.count / 2;
/*     */ 
/*     */       
/* 385 */       if (newCapacity < minCapacity) {
/* 386 */         newCapacity = minCapacity;
/*     */       }
/*     */ 
/*     */       
/* 390 */       if (newCapacity < 8) {
/* 391 */         newCapacity = 8;
/*     */       }
/*     */       
/* 394 */       this.tags = Arrays.copyOf(this.tags, newCapacity);
/* 395 */       this.objects = Arrays.copyOf(this.objects, newCapacity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean mergeFieldFrom(int tag, CodedInputStream input) throws IOException {
/*     */     UnknownFieldSetLite subFieldSet;
/* 408 */     checkMutable();
/* 409 */     int fieldNumber = WireFormat.getTagFieldNumber(tag);
/* 410 */     switch (WireFormat.getTagWireType(tag)) {
/*     */       case 0:
/* 412 */         storeField(tag, Long.valueOf(input.readInt64()));
/* 413 */         return true;
/*     */       case 5:
/* 415 */         storeField(tag, Integer.valueOf(input.readFixed32()));
/* 416 */         return true;
/*     */       case 1:
/* 418 */         storeField(tag, Long.valueOf(input.readFixed64()));
/* 419 */         return true;
/*     */       case 2:
/* 421 */         storeField(tag, input.readBytes());
/* 422 */         return true;
/*     */       case 3:
/* 424 */         subFieldSet = new UnknownFieldSetLite();
/* 425 */         subFieldSet.mergeFrom(input);
/* 426 */         input.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/* 427 */         storeField(tag, subFieldSet);
/* 428 */         return true;
/*     */       case 4:
/* 430 */         input.checkValidEndTag();
/* 431 */         return false;
/*     */     } 
/* 433 */     throw InvalidProtocolBufferException.invalidWireType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   UnknownFieldSetLite mergeVarintField(int fieldNumber, int value) {
/* 444 */     checkMutable();
/* 445 */     if (fieldNumber == 0) {
/* 446 */       throw new IllegalArgumentException("Zero is not a valid field number.");
/*     */     }
/*     */     
/* 449 */     storeField(WireFormat.makeTag(fieldNumber, 0), Long.valueOf(value));
/*     */     
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   UnknownFieldSetLite mergeLengthDelimitedField(int fieldNumber, ByteString value) {
/* 460 */     checkMutable();
/* 461 */     if (fieldNumber == 0) {
/* 462 */       throw new IllegalArgumentException("Zero is not a valid field number.");
/*     */     }
/*     */     
/* 465 */     storeField(WireFormat.makeTag(fieldNumber, 2), value);
/*     */     
/* 467 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private UnknownFieldSetLite mergeFrom(CodedInputStream input) throws IOException {
/*     */     int tag;
/*     */     do {
/* 474 */       tag = input.readTag();
/* 475 */     } while (tag != 0 && mergeFieldFrom(tag, input));
/*     */ 
/*     */ 
/*     */     
/* 479 */     return this;
/*     */   }
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   UnknownFieldSetLite mergeFrom(UnknownFieldSetLite other) {
/* 484 */     if (other.equals(getDefaultInstance())) {
/* 485 */       return this;
/*     */     }
/*     */     
/* 488 */     checkMutable();
/* 489 */     int newCount = this.count + other.count;
/* 490 */     ensureCapacity(newCount);
/* 491 */     System.arraycopy(other.tags, 0, this.tags, this.count, other.count);
/* 492 */     System.arraycopy(other.objects, 0, this.objects, this.count, other.count);
/* 493 */     this.count = newCount;
/* 494 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnknownFieldSetLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */