/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class LazyFieldLite
/*     */ {
/*     */   private ByteString delayedBytes;
/*     */   private ExtensionRegistryLite extensionRegistry;
/*     */   protected volatile MessageLite value;
/*     */   private volatile ByteString memoizedBytes;
/*     */   private volatile boolean corrupted;
/*     */   
/*     */   public LazyFieldLite(ExtensionRegistryLite extensionRegistry, ByteString bytes) {
/*  96 */     checkArguments(extensionRegistry, bytes);
/*  97 */     this.extensionRegistry = extensionRegistry;
/*  98 */     this.delayedBytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LazyFieldLite() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static LazyFieldLite fromValue(MessageLite value) {
/* 109 */     LazyFieldLite lf = new LazyFieldLite();
/* 110 */     lf.setValue(value);
/* 111 */     return lf;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 117 */     if (this == o) {
/* 118 */       return true;
/*     */     }
/*     */     
/* 121 */     if (!(o instanceof LazyFieldLite)) {
/* 122 */       return false;
/*     */     }
/*     */     
/* 125 */     LazyFieldLite other = (LazyFieldLite)o;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     MessageLite value1 = this.value;
/* 132 */     MessageLite value2 = other.value;
/* 133 */     if (value1 == null && value2 == null)
/* 134 */       return toByteString().equals(other.toByteString()); 
/* 135 */     if (value1 != null && value2 != null)
/* 136 */       return value1.equals(value2); 
/* 137 */     if (value1 != null) {
/* 138 */       return value1.equals(other.getValue(value1.getDefaultInstanceForType()));
/*     */     }
/* 140 */     return getValue(value2.getDefaultInstanceForType()).equals(value2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 149 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsDefaultInstance() {
/* 156 */     return (this.memoizedBytes == ByteString.EMPTY || (this.value == null && (this.delayedBytes == null || this.delayedBytes == ByteString.EMPTY)));
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
/*     */   public void clear() {
/* 170 */     this.delayedBytes = null;
/* 171 */     this.value = null;
/* 172 */     this.memoizedBytes = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(LazyFieldLite other) {
/* 182 */     this.delayedBytes = other.delayedBytes;
/* 183 */     this.value = other.value;
/* 184 */     this.memoizedBytes = other.memoizedBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (other.extensionRegistry != null) {
/* 190 */       this.extensionRegistry = other.extensionRegistry;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageLite getValue(MessageLite defaultInstance) {
/* 201 */     ensureInitialized(defaultInstance);
/* 202 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageLite setValue(MessageLite value) {
/* 212 */     MessageLite originalValue = this.value;
/* 213 */     this.delayedBytes = null;
/* 214 */     this.memoizedBytes = null;
/* 215 */     this.value = value;
/* 216 */     return originalValue;
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
/*     */   public void merge(LazyFieldLite other) {
/* 228 */     if (other.containsDefaultInstance()) {
/*     */       return;
/*     */     }
/*     */     
/* 232 */     if (containsDefaultInstance()) {
/* 233 */       set(other);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 239 */     if (this.extensionRegistry == null) {
/* 240 */       this.extensionRegistry = other.extensionRegistry;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     if (this.delayedBytes != null && other.delayedBytes != null) {
/* 249 */       this.delayedBytes = this.delayedBytes.concat(other.delayedBytes);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 256 */     if (this.value == null && other.value != null) {
/* 257 */       setValue(mergeValueAndBytes(other.value, this.delayedBytes, this.extensionRegistry)); return;
/*     */     } 
/* 259 */     if (this.value != null && other.value == null) {
/* 260 */       setValue(mergeValueAndBytes(this.value, other.delayedBytes, other.extensionRegistry));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 265 */     setValue(this.value.toBuilder().mergeFrom(other.value).build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 276 */     if (containsDefaultInstance()) {
/* 277 */       setByteString(input.readBytes(), extensionRegistry);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 283 */     if (this.extensionRegistry == null) {
/* 284 */       this.extensionRegistry = extensionRegistry;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     if (this.delayedBytes != null) {
/* 293 */       setByteString(this.delayedBytes.concat(input.readBytes()), this.extensionRegistry);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 301 */       setValue(this.value.toBuilder().mergeFrom(input, extensionRegistry).build());
/* 302 */     } catch (InvalidProtocolBufferException invalidProtocolBufferException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MessageLite mergeValueAndBytes(MessageLite value, ByteString otherBytes, ExtensionRegistryLite extensionRegistry) {
/*     */     try {
/* 311 */       return value.toBuilder().mergeFrom(otherBytes, extensionRegistry).build();
/* 312 */     } catch (InvalidProtocolBufferException e) {
/*     */ 
/*     */       
/* 315 */       return value;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setByteString(ByteString bytes, ExtensionRegistryLite extensionRegistry) {
/* 321 */     checkArguments(extensionRegistry, bytes);
/* 322 */     this.delayedBytes = bytes;
/* 323 */     this.extensionRegistry = extensionRegistry;
/* 324 */     this.value = null;
/* 325 */     this.memoizedBytes = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 335 */     if (this.memoizedBytes != null)
/* 336 */       return this.memoizedBytes.size(); 
/* 337 */     if (this.delayedBytes != null)
/* 338 */       return this.delayedBytes.size(); 
/* 339 */     if (this.value != null) {
/* 340 */       return this.value.getSerializedSize();
/*     */     }
/* 342 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteString toByteString() {
/* 348 */     if (this.memoizedBytes != null) {
/* 349 */       return this.memoizedBytes;
/*     */     }
/*     */ 
/*     */     
/* 353 */     if (this.delayedBytes != null) {
/* 354 */       return this.delayedBytes;
/*     */     }
/* 356 */     synchronized (this) {
/* 357 */       if (this.memoizedBytes != null) {
/* 358 */         return this.memoizedBytes;
/*     */       }
/* 360 */       if (this.value == null) {
/* 361 */         this.memoizedBytes = ByteString.EMPTY;
/*     */       } else {
/* 363 */         this.memoizedBytes = this.value.toByteString();
/*     */       } 
/* 365 */       return this.memoizedBytes;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSizeNoTag() {
/* 374 */     return CodedOutputStream.computeLengthDelimitedFieldSize(getSerializedSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize(int fieldNumber) {
/* 382 */     return CodedOutputStream.computeTagSize(fieldNumber) + computeSizeNoTag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeMessageSetExtensionSize(int fieldNumber) {
/* 390 */     return CodedOutputStream.computeTagSize(1) * 2 + 
/* 391 */       CodedOutputStream.computeUInt32Size(2, fieldNumber) + 
/* 392 */       computeSize(3);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeTo(Writer writer, int fieldNumber) throws IOException {
/* 397 */     if (this.memoizedBytes != null) {
/* 398 */       writer.writeBytes(fieldNumber, this.memoizedBytes);
/* 399 */     } else if (this.delayedBytes != null) {
/* 400 */       writer.writeBytes(fieldNumber, this.delayedBytes);
/* 401 */     } else if (this.value != null) {
/* 402 */       writer.writeMessage(fieldNumber, this.value);
/*     */     } else {
/* 404 */       writer.writeBytes(fieldNumber, ByteString.EMPTY);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void ensureInitialized(MessageLite defaultInstance) {
/* 410 */     if (this.value != null) {
/*     */       return;
/*     */     }
/* 413 */     synchronized (this) {
/* 414 */       if (this.value != null) {
/*     */         return;
/*     */       }
/*     */       try {
/* 418 */         if (this.delayedBytes != null) {
/*     */ 
/*     */           
/* 421 */           MessageLite parsedValue = defaultInstance.getParserForType().parseFrom(this.delayedBytes, this.extensionRegistry);
/* 422 */           this.value = parsedValue;
/* 423 */           this.memoizedBytes = this.delayedBytes;
/*     */         } else {
/* 425 */           this.value = defaultInstance;
/* 426 */           this.memoizedBytes = ByteString.EMPTY;
/*     */         } 
/* 428 */       } catch (InvalidProtocolBufferException e) {
/*     */ 
/*     */         
/* 431 */         this.corrupted = true;
/* 432 */         this.value = defaultInstance;
/* 433 */         this.memoizedBytes = ByteString.EMPTY;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkArguments(ExtensionRegistryLite extensionRegistry, ByteString bytes) {
/* 439 */     if (extensionRegistry == null) {
/* 440 */       throw new NullPointerException("found null ExtensionRegistry");
/*     */     }
/* 442 */     if (bytes == null) {
/* 443 */       throw new NullPointerException("found null ByteString");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isCorrupted() {
/* 449 */     return this.corrupted;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\LazyFieldLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */