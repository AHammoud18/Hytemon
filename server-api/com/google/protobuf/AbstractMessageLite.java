/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
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
/*     */ public abstract class AbstractMessageLite<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends AbstractMessageLite.Builder<MessageType, BuilderType>>
/*     */   implements MessageLite
/*     */ {
/*  38 */   protected int memoizedHashCode = 0;
/*     */ 
/*     */   
/*     */   public ByteString toByteString() {
/*     */     try {
/*  43 */       ByteString.CodedBuilder out = ByteString.newCodedBuilder(getSerializedSize());
/*  44 */       writeTo(out.getCodedOutput());
/*  45 */       return out.build();
/*  46 */     } catch (IOException e) {
/*  47 */       throw new RuntimeException(getSerializingExceptionMessage("ByteString"), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/*     */     try {
/*  54 */       byte[] result = new byte[getSerializedSize()];
/*  55 */       CodedOutputStream output = CodedOutputStream.newInstance(result);
/*  56 */       writeTo(output);
/*  57 */       output.checkNoSpaceLeft();
/*  58 */       return result;
/*  59 */     } catch (IOException e) {
/*  60 */       throw new RuntimeException(getSerializingExceptionMessage("byte array"), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream output) throws IOException {
/*  66 */     int bufferSize = CodedOutputStream.computePreferredBufferSize(getSerializedSize());
/*  67 */     CodedOutputStream codedOutput = CodedOutputStream.newInstance(output, bufferSize);
/*  68 */     writeTo(codedOutput);
/*  69 */     codedOutput.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDelimitedTo(OutputStream output) throws IOException {
/*  74 */     int serialized = getSerializedSize();
/*     */     
/*  76 */     int bufferSize = CodedOutputStream.computePreferredBufferSize(
/*  77 */         CodedOutputStream.computeUInt32SizeNoTag(serialized) + serialized);
/*  78 */     CodedOutputStream codedOutput = CodedOutputStream.newInstance(output, bufferSize);
/*  79 */     codedOutput.writeUInt32NoTag(serialized);
/*  80 */     writeTo(codedOutput);
/*  81 */     codedOutput.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getMemoizedSerializedSize() {
/*  87 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   void setMemoizedSerializedSize(int size) {
/*  91 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   int getSerializedSize(Schema<AbstractMessageLite<MessageType, BuilderType>> schema) {
/*  96 */     int memoizedSerializedSize = getMemoizedSerializedSize();
/*  97 */     if (memoizedSerializedSize == -1) {
/*  98 */       memoizedSerializedSize = schema.getSerializedSize(this);
/*  99 */       setMemoizedSerializedSize(memoizedSerializedSize);
/*     */     } 
/* 101 */     return memoizedSerializedSize;
/*     */   }
/*     */ 
/*     */   
/*     */   UninitializedMessageException newUninitializedMessageException() {
/* 106 */     return new UninitializedMessageException(this);
/*     */   }
/*     */   
/*     */   private String getSerializingExceptionMessage(String target) {
/* 110 */     return "Serializing " + 
/* 111 */       getClass().getName() + " to a " + target + " threw an IOException (should never happen).";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void checkByteStringIsUtf8(ByteString byteString) throws IllegalArgumentException {
/* 119 */     if (!byteString.isValidUtf8()) {
/* 120 */       throw new IllegalArgumentException("Byte string is not UTF-8.");
/*     */     }
/*     */   }
/*     */   
/*     */   protected static <T> void addAll(Iterable<T> values, List<? super T> list) {
/* 125 */     Builder.addAll(values, list);
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
/*     */   public static abstract class Builder<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>>
/*     */     implements MessageLite.Builder
/*     */   {
/*     */     public BuilderType mergeFrom(CodedInputStream input) throws IOException {
/* 152 */       return mergeFrom(input, ExtensionRegistryLite.getEmptyRegistry());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(ByteString data) throws InvalidProtocolBufferException {
/*     */       try {
/* 164 */         CodedInputStream input = data.newCodedInput();
/* 165 */         mergeFrom(input);
/* 166 */         input.checkLastTagWas(0);
/* 167 */         return (BuilderType)this;
/* 168 */       } catch (InvalidProtocolBufferException e) {
/* 169 */         throw e;
/* 170 */       } catch (IOException e) {
/* 171 */         throw new RuntimeException(getReadingExceptionMessage("ByteString"), e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*     */       try {
/* 180 */         CodedInputStream input = data.newCodedInput();
/* 181 */         mergeFrom(input, extensionRegistry);
/* 182 */         input.checkLastTagWas(0);
/* 183 */         return (BuilderType)this;
/* 184 */       } catch (InvalidProtocolBufferException e) {
/* 185 */         throw e;
/* 186 */       } catch (IOException e) {
/* 187 */         throw new RuntimeException(getReadingExceptionMessage("ByteString"), e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data) throws InvalidProtocolBufferException {
/* 193 */       return mergeFrom(data, 0, data.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data, int off, int len) throws InvalidProtocolBufferException {
/*     */       try {
/* 200 */         CodedInputStream input = CodedInputStream.newInstance(data, off, len);
/* 201 */         mergeFrom(input);
/* 202 */         input.checkLastTagWas(0);
/* 203 */         return (BuilderType)this;
/* 204 */       } catch (InvalidProtocolBufferException e) {
/* 205 */         throw e;
/* 206 */       } catch (IOException e) {
/* 207 */         throw new RuntimeException(getReadingExceptionMessage("byte array"), e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 214 */       return mergeFrom(data, 0, data.length, extensionRegistry);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data, int off, int len, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*     */       try {
/* 225 */         CodedInputStream input = CodedInputStream.newInstance(data, off, len);
/* 226 */         mergeFrom(input, extensionRegistry);
/* 227 */         input.checkLastTagWas(0);
/* 228 */         return (BuilderType)this;
/* 229 */       } catch (InvalidProtocolBufferException e) {
/* 230 */         throw e;
/* 231 */       } catch (IOException e) {
/* 232 */         throw new RuntimeException(getReadingExceptionMessage("byte array"), e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(InputStream input) throws IOException {
/* 238 */       CodedInputStream codedInput = CodedInputStream.newInstance(input);
/* 239 */       mergeFrom(codedInput);
/* 240 */       codedInput.checkLastTagWas(0);
/* 241 */       return (BuilderType)this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 247 */       CodedInputStream codedInput = CodedInputStream.newInstance(input);
/* 248 */       mergeFrom(codedInput, extensionRegistry);
/* 249 */       codedInput.checkLastTagWas(0);
/* 250 */       return (BuilderType)this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     static final class LimitedInputStream
/*     */       extends FilterInputStream
/*     */     {
/*     */       private int limit;
/*     */ 
/*     */       
/*     */       LimitedInputStream(InputStream in, int limit) {
/* 262 */         super(in);
/* 263 */         this.limit = limit;
/*     */       }
/*     */ 
/*     */       
/*     */       public int available() throws IOException {
/* 268 */         return Math.min(super.available(), this.limit);
/*     */       }
/*     */ 
/*     */       
/*     */       public int read() throws IOException {
/* 273 */         if (this.limit <= 0) {
/* 274 */           return -1;
/*     */         }
/* 276 */         int result = super.read();
/* 277 */         if (result >= 0) {
/* 278 */           this.limit--;
/*     */         }
/* 280 */         return result;
/*     */       }
/*     */ 
/*     */       
/*     */       public int read(byte[] b, int off, int len) throws IOException {
/* 285 */         if (this.limit <= 0) {
/* 286 */           return -1;
/*     */         }
/* 288 */         len = Math.min(len, this.limit);
/* 289 */         int result = super.read(b, off, len);
/* 290 */         if (result >= 0) {
/* 291 */           this.limit -= result;
/*     */         }
/* 293 */         return result;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public long skip(long n) throws IOException {
/* 300 */         int result = (int)super.skip(Math.min(n, this.limit));
/* 301 */         if (result >= 0)
/*     */         {
/* 303 */           this.limit -= result;
/*     */         }
/* 305 */         return result;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean mergeDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 312 */       int firstByte = input.read();
/* 313 */       if (firstByte == -1) {
/* 314 */         return false;
/*     */       }
/* 316 */       int size = CodedInputStream.readRawVarint32(firstByte, input);
/* 317 */       InputStream limitedInput = new LimitedInputStream(input, size);
/* 318 */       mergeFrom(limitedInput, extensionRegistry);
/* 319 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mergeDelimitedFrom(InputStream input) throws IOException {
/* 324 */       return mergeDelimitedFrom(input, ExtensionRegistryLite.getEmptyRegistry());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(MessageLite other) {
/* 330 */       if (!getDefaultInstanceForType().getClass().isInstance(other)) {
/* 331 */         throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
/*     */       }
/*     */ 
/*     */       
/* 335 */       return internalMergeFrom((MessageType)other);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private String getReadingExceptionMessage(String target) {
/* 341 */       return "Reading " + 
/* 342 */         getClass().getName() + " from a " + target + " threw an IOException (should never happen).";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static <T> void addAllCheckingNulls(Iterable<T> values, List<? super T> list) {
/* 350 */       if (values instanceof Collection) {
/* 351 */         int growth = ((Collection)values).size();
/* 352 */         if (list instanceof ArrayList) {
/* 353 */           ((ArrayList)list).ensureCapacity(list.size() + growth);
/* 354 */         } else if (list instanceof ProtobufArrayList) {
/* 355 */           ((ProtobufArrayList)list).ensureCapacity(list.size() + growth);
/*     */         } 
/*     */       } 
/* 358 */       int begin = list.size();
/* 359 */       if (values instanceof List && values instanceof java.util.RandomAccess) {
/* 360 */         List<T> valuesList = (List<T>)values;
/* 361 */         int n = valuesList.size();
/*     */         
/* 363 */         for (int i = 0; i < n; i++) {
/* 364 */           T value = valuesList.get(i);
/* 365 */           if (value == null) {
/* 366 */             resetListAndThrow(list, begin);
/*     */           }
/* 368 */           list.add(value);
/*     */         } 
/*     */       } else {
/* 371 */         for (T value : values) {
/* 372 */           if (value == null) {
/* 373 */             resetListAndThrow(list, begin);
/*     */           }
/* 375 */           list.add(value);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private static void resetListAndThrow(List<?> list, int begin) {
/* 382 */       String message = "Element at index " + (list.size() - begin) + " is null.";
/* 383 */       for (int i = list.size() - 1; i >= begin; i--) {
/* 384 */         list.remove(i);
/*     */       }
/* 386 */       throw new NullPointerException(message);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected static UninitializedMessageException newUninitializedMessageException(MessageLite message) {
/* 392 */       return new UninitializedMessageException(message);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected static <T> void addAll(Iterable<T> values, Collection<? super T> list) {
/* 398 */       addAll(values, (List<? super T>)list);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected static <T> void addAll(Iterable<T> values, List<? super T> list) {
/* 409 */       Internal.checkNotNull(values);
/* 410 */       if (values instanceof LazyStringList) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 416 */         List<?> lazyValues = ((LazyStringList)values).getUnderlyingElements();
/* 417 */         LazyStringList lazyList = (LazyStringList)list;
/* 418 */         int begin = list.size();
/* 419 */         for (Object value : lazyValues) {
/* 420 */           if (value == null) {
/*     */             
/* 422 */             String message = "Element at index " + (lazyList.size() - begin) + " is null.";
/* 423 */             for (int i = lazyList.size() - 1; i >= begin; i--) {
/* 424 */               lazyList.remove(i);
/*     */             }
/* 426 */             throw new NullPointerException(message);
/*     */           } 
/* 428 */           if (value instanceof ByteString) {
/* 429 */             lazyList.add((ByteString)value); continue;
/* 430 */           }  if (value instanceof byte[]) {
/* 431 */             lazyList.add(ByteString.copyFrom((byte[])value)); continue;
/*     */           } 
/* 433 */           lazyList.add((String)value);
/*     */         }
/*     */       
/*     */       }
/* 437 */       else if (values instanceof PrimitiveNonBoxingCollection) {
/* 438 */         list.addAll((Collection<? extends T>)values);
/*     */       } else {
/* 440 */         addAllCheckingNulls(values, list);
/*     */       } 
/*     */     }
/*     */     
/*     */     public abstract BuilderType clone();
/*     */     
/*     */     public abstract BuilderType mergeFrom(CodedInputStream param1CodedInputStream, ExtensionRegistryLite param1ExtensionRegistryLite) throws IOException;
/*     */     
/*     */     protected abstract BuilderType internalMergeFrom(MessageType param1MessageType);
/*     */   }
/*     */   
/*     */   protected static interface InternalOneOfEnum {
/*     */     int getNumber();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\AbstractMessageLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */