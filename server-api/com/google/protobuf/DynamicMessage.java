/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class DynamicMessage
/*     */   extends AbstractMessage
/*     */ {
/*     */   private final Descriptors.Descriptor type;
/*     */   private final FieldSet<Descriptors.FieldDescriptor> fields;
/*     */   private final Descriptors.FieldDescriptor[] oneofCases;
/*     */   private final UnknownFieldSet unknownFields;
/*  34 */   private int memoizedSize = -1;
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
/*     */   DynamicMessage(Descriptors.Descriptor type, FieldSet<Descriptors.FieldDescriptor> fields, Descriptors.FieldDescriptor[] oneofCases, UnknownFieldSet unknownFields) {
/*  49 */     this.type = type;
/*  50 */     this.fields = fields;
/*  51 */     this.oneofCases = oneofCases;
/*  52 */     this.unknownFields = unknownFields;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynamicMessage getDefaultInstance(Descriptors.Descriptor type) {
/*  57 */     int oneofDeclCount = type.toProto().getOneofDeclCount();
/*  58 */     Descriptors.FieldDescriptor[] oneofCases = new Descriptors.FieldDescriptor[oneofDeclCount];
/*  59 */     return new DynamicMessage(type, 
/*     */         
/*  61 */         FieldSet.emptySet(), oneofCases, 
/*     */         
/*  63 */         UnknownFieldSet.getDefaultInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, CodedInputStream input) throws IOException {
/*  69 */     return newBuilder(type).mergeFrom(input).buildParsed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, CodedInputStream input, ExtensionRegistry extensionRegistry) throws IOException {
/*  76 */     return newBuilder(type).mergeFrom(input, extensionRegistry).buildParsed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, ByteString data) throws InvalidProtocolBufferException {
/*  82 */     return newBuilder(type).mergeFrom(data).buildParsed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, ByteString data, ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
/*  89 */     return newBuilder(type).mergeFrom(data, extensionRegistry).buildParsed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, byte[] data) throws InvalidProtocolBufferException {
/*  95 */     return newBuilder(type).mergeFrom(data).buildParsed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, byte[] data, ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
/* 102 */     return newBuilder(type).mergeFrom(data, extensionRegistry).buildParsed();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, InputStream input) throws IOException {
/* 107 */     return newBuilder(type).mergeFrom(input).buildParsed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicMessage parseFrom(Descriptors.Descriptor type, InputStream input, ExtensionRegistry extensionRegistry) throws IOException {
/* 113 */     return newBuilder(type).mergeFrom(input, extensionRegistry).buildParsed();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Builder newBuilder(Descriptors.Descriptor type) {
/* 118 */     return new Builder(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder newBuilder(Message prototype) {
/* 126 */     return (new Builder(prototype.getDescriptorForType())).mergeFrom(prototype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Descriptors.Descriptor getDescriptorForType() {
/* 134 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public DynamicMessage getDefaultInstanceForType() {
/* 139 */     return getDefaultInstance(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/* 144 */     return this.fields.getAllFields();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/* 149 */     verifyOneofContainingType(oneof);
/* 150 */     Descriptors.FieldDescriptor field = this.oneofCases[oneof.getIndex()];
/* 151 */     if (field == null) {
/* 152 */       return false;
/*     */     }
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/* 159 */     verifyOneofContainingType(oneof);
/* 160 */     return this.oneofCases[oneof.getIndex()];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasField(Descriptors.FieldDescriptor field) {
/* 165 */     verifyContainingType(field);
/* 166 */     return this.fields.hasField(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getField(Descriptors.FieldDescriptor field) {
/* 171 */     verifyContainingType(field);
/* 172 */     Object<?> result = (Object<?>)this.fields.getField(field);
/* 173 */     if (result == null) {
/* 174 */       if (field.isRepeated()) {
/* 175 */         result = Collections.emptyList();
/* 176 */       } else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 177 */         result = (Object<?>)getDefaultInstance(field.getMessageType());
/*     */       } else {
/* 179 */         result = (Object<?>)field.getDefaultValue();
/*     */       } 
/*     */     }
/* 182 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/* 187 */     verifyContainingType(field);
/* 188 */     return this.fields.getRepeatedFieldCount(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/* 193 */     verifyContainingType(field);
/* 194 */     return this.fields.getRepeatedField(field, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public UnknownFieldSet getUnknownFields() {
/* 199 */     return this.unknownFields;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isInitialized(Descriptors.Descriptor type, FieldSet<Descriptors.FieldDescriptor> fields) {
/* 204 */     for (Descriptors.FieldDescriptor field : type.getFields()) {
/* 205 */       if (field.isRequired() && 
/* 206 */         !fields.hasField(field)) {
/* 207 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 213 */     return fields.isInitialized();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 218 */     return isInitialized(this.type, this.fields);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 223 */     if (this.type.getOptions().getMessageSetWireFormat()) {
/* 224 */       this.fields.writeMessageSetTo(output);
/* 225 */       this.unknownFields.writeAsMessageSetTo(output);
/*     */     } else {
/* 227 */       this.fields.writeTo(output);
/* 228 */       this.unknownFields.writeTo(output);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 234 */     int size = this.memoizedSize;
/* 235 */     if (size != -1) {
/* 236 */       return size;
/*     */     }
/*     */     
/* 239 */     if (this.type.getOptions().getMessageSetWireFormat()) {
/* 240 */       size = this.fields.getMessageSetSerializedSize();
/* 241 */       size += this.unknownFields.getSerializedSizeAsMessageSet();
/*     */     } else {
/* 243 */       size = this.fields.getSerializedSize();
/* 244 */       size += this.unknownFields.getSerializedSize();
/*     */     } 
/*     */     
/* 247 */     this.memoizedSize = size;
/* 248 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder newBuilderForType() {
/* 253 */     return new Builder(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder toBuilder() {
/* 258 */     return newBuilderForType().mergeFrom(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<DynamicMessage> getParserForType() {
/* 263 */     return new AbstractParser<DynamicMessage>()
/*     */       {
/*     */         
/*     */         public DynamicMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */         {
/* 268 */           DynamicMessage.Builder builder = DynamicMessage.newBuilder(DynamicMessage.this.type);
/*     */           try {
/* 270 */             builder.mergeFrom(input, extensionRegistry);
/* 271 */           } catch (InvalidProtocolBufferException e) {
/* 272 */             throw e.setUnfinishedMessage(builder.buildPartial());
/* 273 */           } catch (IOException e) {
/* 274 */             throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial());
/*     */           } 
/* 276 */           return builder.buildPartial();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private void verifyContainingType(Descriptors.FieldDescriptor field) {
/* 283 */     if (field.getContainingType() != this.type) {
/* 284 */       throw new IllegalArgumentException("FieldDescriptor does not match message type.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void verifyOneofContainingType(Descriptors.OneofDescriptor oneof) {
/* 290 */     if (oneof.getContainingType() != this.type) {
/* 291 */       throw new IllegalArgumentException("OneofDescriptor does not match message type.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends AbstractMessage.Builder<Builder>
/*     */   {
/*     */     private final Descriptors.Descriptor type;
/*     */     
/*     */     private FieldSet.Builder<Descriptors.FieldDescriptor> fields;
/*     */     private final Descriptors.FieldDescriptor[] oneofCases;
/*     */     private UnknownFieldSet unknownFields;
/*     */     
/*     */     private Builder(Descriptors.Descriptor type) {
/* 306 */       this.type = type;
/* 307 */       this.fields = FieldSet.newBuilder();
/* 308 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 309 */       this.oneofCases = new Descriptors.FieldDescriptor[type.toProto().getOneofDeclCount()];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 317 */       this.fields = FieldSet.newBuilder();
/* 318 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 319 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 324 */       if (other instanceof DynamicMessage) {
/*     */         
/* 326 */         DynamicMessage otherDynamicMessage = (DynamicMessage)other;
/* 327 */         if (otherDynamicMessage.type != this.type) {
/* 328 */           throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
/*     */         }
/*     */         
/* 331 */         this.fields.mergeFrom(otherDynamicMessage.fields);
/* 332 */         mergeUnknownFields(otherDynamicMessage.unknownFields);
/* 333 */         for (int i = 0; i < this.oneofCases.length; i++) {
/* 334 */           if (this.oneofCases[i] == null) {
/* 335 */             this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
/*     */           }
/* 337 */           else if (otherDynamicMessage.oneofCases[i] != null && this.oneofCases[i] != otherDynamicMessage
/* 338 */             .oneofCases[i]) {
/* 339 */             this.fields.clearField(this.oneofCases[i]);
/* 340 */             this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
/*     */           } 
/*     */         } 
/*     */         
/* 344 */         return this;
/*     */       } 
/* 346 */       return super.mergeFrom(other);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public DynamicMessage build() {
/* 352 */       if (!isInitialized()) {
/* 353 */         throw newUninitializedMessageException(new DynamicMessage(this.type, this.fields
/*     */               
/* 355 */               .build(), (Descriptors.FieldDescriptor[])Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields));
/*     */       }
/* 357 */       return buildPartial();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private DynamicMessage buildParsed() throws InvalidProtocolBufferException {
/* 365 */       if (!isInitialized()) {
/* 366 */         throw newUninitializedMessageException(new DynamicMessage(this.type, this.fields
/*     */ 
/*     */               
/* 369 */               .build(), 
/* 370 */               (Descriptors.FieldDescriptor[])Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields))
/*     */           
/* 372 */           .asInvalidProtocolBufferException();
/*     */       }
/* 374 */       return buildPartial();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public DynamicMessage buildPartial() {
/* 380 */       if (this.type.getOptions().getMapEntry()) {
/* 381 */         for (Descriptors.FieldDescriptor field : this.type.getFields()) {
/* 382 */           if (field.isOptional() && !this.fields.hasField(field)) {
/* 383 */             if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 384 */               this.fields.setField(field, DynamicMessage.getDefaultInstance(field.getMessageType())); continue;
/*     */             } 
/* 386 */             this.fields.setField(field, field.getDefaultValue());
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 396 */       DynamicMessage result = new DynamicMessage(this.type, this.fields.buildPartial(), Arrays.<Descriptors.FieldDescriptor>copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields);
/*     */       
/* 398 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clone() {
/* 403 */       Builder result = new Builder(this.type);
/* 404 */       result.fields.mergeFrom(this.fields.build());
/* 405 */       result.mergeUnknownFields(this.unknownFields);
/* 406 */       System.arraycopy(this.oneofCases, 0, result.oneofCases, 0, this.oneofCases.length);
/* 407 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isInitialized() {
/* 413 */       for (Descriptors.FieldDescriptor field : this.type.getFields()) {
/* 414 */         if (field.isRequired() && 
/* 415 */           !this.fields.hasField(field)) {
/* 416 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 422 */       return this.fields.isInitialized();
/*     */     }
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 427 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public DynamicMessage getDefaultInstanceForType() {
/* 432 */       return DynamicMessage.getDefaultInstance(this.type);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/* 437 */       return this.fields.getAllFields();
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder newBuilderForField(Descriptors.FieldDescriptor field) {
/* 442 */       verifyContainingType(field);
/*     */       
/* 444 */       if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 445 */         throw new IllegalArgumentException("newBuilderForField is only valid for fields with message type.");
/*     */       }
/*     */ 
/*     */       
/* 449 */       return new Builder(field.getMessageType());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/* 454 */       verifyOneofContainingType(oneof);
/* 455 */       Descriptors.FieldDescriptor field = this.oneofCases[oneof.getIndex()];
/* 456 */       if (field == null) {
/* 457 */         return false;
/*     */       }
/* 459 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/* 464 */       verifyOneofContainingType(oneof);
/* 465 */       return this.oneofCases[oneof.getIndex()];
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
/* 470 */       verifyOneofContainingType(oneof);
/* 471 */       Descriptors.FieldDescriptor field = this.oneofCases[oneof.getIndex()];
/* 472 */       if (field != null) {
/* 473 */         clearField(field);
/*     */       }
/* 475 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasField(Descriptors.FieldDescriptor field) {
/* 480 */       verifyContainingType(field);
/* 481 */       return this.fields.hasField(field);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getField(Descriptors.FieldDescriptor field) {
/* 486 */       verifyContainingType(field);
/* 487 */       Object<?> result = (Object<?>)this.fields.getField(field);
/* 488 */       if (result == null) {
/* 489 */         if (field.isRepeated()) {
/* 490 */           result = Collections.emptyList();
/* 491 */         } else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 492 */           result = (Object<?>)DynamicMessage.getDefaultInstance(field.getMessageType());
/*     */         } else {
/* 494 */           result = (Object<?>)field.getDefaultValue();
/*     */         } 
/*     */       }
/* 497 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder setField(Descriptors.FieldDescriptor field, Object value) {
/* 502 */       verifyContainingType(field);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 508 */       verifyType(field, value);
/* 509 */       Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
/* 510 */       if (oneofDescriptor != null) {
/* 511 */         int index = oneofDescriptor.getIndex();
/* 512 */         Descriptors.FieldDescriptor oldField = this.oneofCases[index];
/* 513 */         if (oldField != null && oldField != field) {
/* 514 */           this.fields.clearField(oldField);
/*     */         }
/* 516 */         this.oneofCases[index] = field;
/* 517 */       } else if (!field.hasPresence() && (
/* 518 */         field.isRepeated() ? ((List)value)
/* 519 */         .isEmpty() : value
/* 520 */         .equals(field.getDefaultValue()))) {
/*     */ 
/*     */         
/* 523 */         this.fields.clearField(field);
/* 524 */         return this;
/*     */       } 
/*     */       
/* 527 */       this.fields.setField(field, value);
/* 528 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clearField(Descriptors.FieldDescriptor field) {
/* 533 */       verifyContainingType(field);
/* 534 */       Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
/* 535 */       if (oneofDescriptor != null) {
/* 536 */         int index = oneofDescriptor.getIndex();
/* 537 */         if (this.oneofCases[index] == field) {
/* 538 */           this.oneofCases[index] = null;
/*     */         }
/*     */       } 
/* 541 */       this.fields.clearField(field);
/* 542 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/* 547 */       verifyContainingType(field);
/* 548 */       return this.fields.getRepeatedFieldCount(field);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/* 553 */       verifyContainingType(field);
/* 554 */       return this.fields.getRepeatedField(field, index);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/* 559 */       verifyContainingType(field);
/* 560 */       verifySingularValueType(field, value);
/* 561 */       this.fields.setRepeatedField(field, index, value);
/* 562 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/* 567 */       verifyContainingType(field);
/* 568 */       verifySingularValueType(field, value);
/* 569 */       this.fields.addRepeatedField(field, value);
/* 570 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public UnknownFieldSet getUnknownFields() {
/* 575 */       return this.unknownFields;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder setUnknownFields(UnknownFieldSet unknownFields) {
/* 580 */       this.unknownFields = unknownFields;
/* 581 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
/* 586 */       this
/* 587 */         .unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFields).build();
/* 588 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private void verifyContainingType(Descriptors.FieldDescriptor field) {
/* 593 */       if (field.getContainingType() != this.type) {
/* 594 */         throw new IllegalArgumentException("FieldDescriptor does not match message type.");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void verifyOneofContainingType(Descriptors.OneofDescriptor oneof) {
/* 600 */       if (oneof.getContainingType() != this.type) {
/* 601 */         throw new IllegalArgumentException("OneofDescriptor does not match message type.");
/*     */       }
/*     */     }
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
/*     */     private void verifySingularValueType(Descriptors.FieldDescriptor field, Object value) {
/*     */       // Byte code:
/*     */       //   0: getstatic com/google/protobuf/DynamicMessage$2.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type : [I
/*     */       //   3: aload_1
/*     */       //   4: invokevirtual getType : ()Lcom/google/protobuf/Descriptors$FieldDescriptor$Type;
/*     */       //   7: invokevirtual ordinal : ()I
/*     */       //   10: iaload
/*     */       //   11: lookupswitch default -> 114, 1 -> 36, 2 -> 59
/*     */       //   36: aload_2
/*     */       //   37: invokestatic checkNotNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */       //   40: pop
/*     */       //   41: aload_2
/*     */       //   42: instanceof com/google/protobuf/Descriptors$EnumValueDescriptor
/*     */       //   45: ifne -> 114
/*     */       //   48: new java/lang/IllegalArgumentException
/*     */       //   51: dup
/*     */       //   52: ldc_w 'DynamicMessage should use EnumValueDescriptor to set Enum Value.'
/*     */       //   55: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   58: athrow
/*     */       //   59: aload_2
/*     */       //   60: instanceof com/google/protobuf/Message$Builder
/*     */       //   63: ifeq -> 114
/*     */       //   66: new java/lang/IllegalArgumentException
/*     */       //   69: dup
/*     */       //   70: ldc_w 'Wrong object type used with protocol message reflection.\\nField number: %d, field java type: %s, value type: %s\\n'
/*     */       //   73: iconst_3
/*     */       //   74: anewarray java/lang/Object
/*     */       //   77: dup
/*     */       //   78: iconst_0
/*     */       //   79: aload_1
/*     */       //   80: invokevirtual getNumber : ()I
/*     */       //   83: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */       //   86: aastore
/*     */       //   87: dup
/*     */       //   88: iconst_1
/*     */       //   89: aload_1
/*     */       //   90: invokevirtual getLiteType : ()Lcom/google/protobuf/WireFormat$FieldType;
/*     */       //   93: invokevirtual getJavaType : ()Lcom/google/protobuf/WireFormat$JavaType;
/*     */       //   96: aastore
/*     */       //   97: dup
/*     */       //   98: iconst_2
/*     */       //   99: aload_2
/*     */       //   100: invokevirtual getClass : ()Ljava/lang/Class;
/*     */       //   103: invokevirtual getName : ()Ljava/lang/String;
/*     */       //   106: aastore
/*     */       //   107: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */       //   110: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   113: athrow
/*     */       //   114: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #613	-> 0
/*     */       //   #615	-> 36
/*     */       //   #617	-> 41
/*     */       //   #618	-> 48
/*     */       //   #633	-> 59
/*     */       //   #634	-> 66
/*     */       //   #638	-> 80
/*     */       //   #639	-> 90
/*     */       //   #640	-> 100
/*     */       //   #635	-> 107
/*     */       //   #646	-> 114
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	115	0	this	Lcom/google/protobuf/DynamicMessage$Builder;
/*     */       //   0	115	1	field	Lcom/google/protobuf/Descriptors$FieldDescriptor;
/*     */       //   0	115	2	value	Ljava/lang/Object;
/*     */     }
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
/*     */     private void verifyType(Descriptors.FieldDescriptor field, Object value) {
/* 653 */       if (field.isRepeated()) {
/* 654 */         for (Object item : value) {
/* 655 */           verifySingularValueType(field, item);
/*     */         }
/*     */       } else {
/* 658 */         verifySingularValueType(field, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor field) {
/* 664 */       verifyContainingType(field);
/*     */       
/* 666 */       if (field.isMapField()) {
/* 667 */         throw new UnsupportedOperationException("Nested builder not supported for map fields.");
/*     */       }
/* 669 */       if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 670 */         throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
/*     */       }
/*     */       
/* 673 */       Object existingValue = this.fields.getFieldAllowBuilders(field);
/*     */ 
/*     */ 
/*     */       
/* 677 */       Message.Builder builder = (existingValue == null) ? new Builder(field.getMessageType()) : toMessageBuilder(existingValue);
/* 678 */       this.fields.setField(field, builder);
/* 679 */       return builder;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor field, int index) {
/* 685 */       verifyContainingType(field);
/*     */       
/* 687 */       if (field.isMapField()) {
/* 688 */         throw new UnsupportedOperationException("Map fields cannot be repeated");
/*     */       }
/* 690 */       if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 691 */         throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 696 */       Message.Builder builder = toMessageBuilder(this.fields.getRepeatedFieldAllowBuilders(field, index));
/* 697 */       this.fields.setRepeatedField(field, index, builder);
/* 698 */       return builder;
/*     */     }
/*     */     
/*     */     private static Message.Builder toMessageBuilder(Object o) {
/* 702 */       if (o instanceof Message.Builder) {
/* 703 */         return (Message.Builder)o;
/*     */       }
/*     */       
/* 706 */       if (o instanceof LazyField) {
/* 707 */         o = ((LazyField)o).getValue();
/*     */       }
/* 709 */       if (o instanceof Message) {
/* 710 */         return ((Message)o).toBuilder();
/*     */       }
/*     */       
/* 713 */       throw new IllegalArgumentException(
/* 714 */           String.format("Cannot convert %s to Message.Builder", new Object[] { o.getClass() }));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\DynamicMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */