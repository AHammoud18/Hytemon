/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.Builder<MessageType, BuilderType>>
/*      */   extends AbstractMessageLite<MessageType, BuilderType>
/*      */ {
/*      */   static final int UNINITIALIZED_SERIALIZED_SIZE = 2147483647;
/*      */   private static final int MUTABLE_FLAG_MASK = -2147483648;
/*      */   private static final int MEMOIZED_SERIALIZED_SIZE_MASK = 2147483647;
/*   61 */   private int memoizedSerializedSize = -1;
/*      */ 
/*      */   
/*      */   static final int UNINITIALIZED_HASH_CODE = 0;
/*      */ 
/*      */   
/*   67 */   protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();
/*      */   
/*      */   boolean isMutable() {
/*   70 */     return ((this.memoizedSerializedSize & Integer.MIN_VALUE) != 0);
/*      */   }
/*      */   
/*      */   void markImmutable() {
/*   74 */     this.memoizedSerializedSize &= Integer.MAX_VALUE;
/*      */   }
/*      */   
/*      */   int getMemoizedHashCode() {
/*   78 */     return this.memoizedHashCode;
/*      */   }
/*      */   
/*      */   void setMemoizedHashCode(int value) {
/*   82 */     this.memoizedHashCode = value;
/*      */   }
/*      */   
/*      */   void clearMemoizedHashCode() {
/*   86 */     this.memoizedHashCode = 0;
/*      */   }
/*      */   
/*      */   boolean hashCodeIsNotMemoized() {
/*   90 */     return (0 == getMemoizedHashCode());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final Parser<MessageType> getParserForType() {
/*   96 */     return (Parser<MessageType>)dynamicMethod(MethodToInvoke.GET_PARSER, (Object)null, (Object)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final MessageType getDefaultInstanceForType() {
/*  102 */     return (MessageType)dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE, (Object)null, (Object)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final BuilderType newBuilderForType() {
/*  108 */     return (BuilderType)dynamicMethod(MethodToInvoke.NEW_BUILDER, (Object)null, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   MessageType newMutableInstance() {
/*  113 */     return (MessageType)dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE, (Object)null, (Object)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  130 */     return MessageLiteToString.toString(this, super.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  135 */     if (isMutable()) {
/*  136 */       return computeHashCode();
/*      */     }
/*      */     
/*  139 */     if (hashCodeIsNotMemoized()) {
/*  140 */       setMemoizedHashCode(computeHashCode());
/*      */     }
/*      */     
/*  143 */     return getMemoizedHashCode();
/*      */   }
/*      */   
/*      */   int computeHashCode() {
/*  147 */     return Protobuf.getInstance().<GeneratedMessageLite<MessageType, BuilderType>>schemaFor(this).hashCode(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/*  154 */     if (this == other) {
/*  155 */       return true;
/*      */     }
/*      */     
/*  158 */     if (other == null) {
/*  159 */       return false;
/*      */     }
/*      */     
/*  162 */     if (getClass() != other.getClass()) {
/*  163 */       return false;
/*      */     }
/*      */     
/*  166 */     return Protobuf.getInstance().<GeneratedMessageLite<MessageType, BuilderType>>schemaFor(this).equals(this, (GeneratedMessageLite<MessageType, BuilderType>)other);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureUnknownFieldsInitialized() {
/*  175 */     if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
/*  176 */       this.unknownFields = UnknownFieldSetLite.newInstance();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean parseUnknownField(int tag, CodedInputStream input) throws IOException {
/*  187 */     if (WireFormat.getTagWireType(tag) == 4) {
/*  188 */       return false;
/*      */     }
/*      */     
/*  191 */     ensureUnknownFieldsInitialized();
/*  192 */     return this.unknownFields.mergeFieldFrom(tag, input);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void mergeVarintField(int tag, int value) {
/*  197 */     ensureUnknownFieldsInitialized();
/*  198 */     this.unknownFields.mergeVarintField(tag, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void mergeLengthDelimitedField(int fieldNumber, ByteString value) {
/*  203 */     ensureUnknownFieldsInitialized();
/*  204 */     this.unknownFields.mergeLengthDelimitedField(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void makeImmutable() {
/*  209 */     Protobuf.getInstance().<GeneratedMessageLite<MessageType, BuilderType>>schemaFor(this).makeImmutable(this);
/*  210 */     markImmutable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final <MessageType2 extends GeneratedMessageLite<MessageType2, BuilderType2>, BuilderType2 extends Builder<MessageType2, BuilderType2>> BuilderType2 createBuilder() {
/*  218 */     return (BuilderType2)dynamicMethod(MethodToInvoke.NEW_BUILDER, (Object)null, (Object)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final <MessageType2 extends GeneratedMessageLite<MessageType2, BuilderType2>, BuilderType2 extends Builder<MessageType2, BuilderType2>> BuilderType2 createBuilder(MessageType2 prototype) {
/*  225 */     return createBuilder().mergeFrom(prototype);
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isInitialized() {
/*  230 */     return isInitialized(this, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final BuilderType toBuilder() {
/*  236 */     Builder<GeneratedMessageLite<MessageType, BuilderType>, BuilderType> builder = (Builder)dynamicMethod(MethodToInvoke.NEW_BUILDER, (Object)null, (Object)null);
/*  237 */     return builder.mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum MethodToInvoke
/*      */   {
/*  248 */     GET_MEMOIZED_IS_INITIALIZED,
/*  249 */     SET_MEMOIZED_IS_INITIALIZED,
/*      */ 
/*      */     
/*  252 */     BUILD_MESSAGE_INFO,
/*  253 */     NEW_MUTABLE_INSTANCE,
/*  254 */     NEW_BUILDER,
/*  255 */     GET_DEFAULT_INSTANCE,
/*  256 */     GET_PARSER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void clearMemoizedSerializedSize() {
/*  288 */     setMemoizedSerializedSize(2147483647);
/*      */   }
/*      */ 
/*      */   
/*      */   int getMemoizedSerializedSize() {
/*  293 */     return this.memoizedSerializedSize & Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */   
/*      */   void setMemoizedSerializedSize(int size) {
/*  298 */     if (size < 0) {
/*  299 */       throw new IllegalStateException("serialized size must be non-negative, was " + size);
/*      */     }
/*  301 */     this.memoizedSerializedSize = this.memoizedSerializedSize & Integer.MIN_VALUE | size & Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  307 */     Protobuf.getInstance()
/*  308 */       .<GeneratedMessageLite<MessageType, BuilderType>>schemaFor(this)
/*  309 */       .writeTo(this, CodedOutputStreamWriter.forCodedOutput(output));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getSerializedSize(Schema<?> schema) {
/*  315 */     if (isMutable()) {
/*      */       
/*  317 */       int i = computeSerializedSize(schema);
/*  318 */       if (i < 0) {
/*  319 */         throw new IllegalStateException("serialized size must be non-negative, was " + i);
/*      */       }
/*  321 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  325 */     if (getMemoizedSerializedSize() != Integer.MAX_VALUE) {
/*  326 */       return getMemoizedSerializedSize();
/*      */     }
/*      */ 
/*      */     
/*  330 */     int size = computeSerializedSize(schema);
/*  331 */     setMemoizedSerializedSize(size);
/*  332 */     return size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  339 */     return getSerializedSize((Schema)null);
/*      */   }
/*      */ 
/*      */   
/*      */   private int computeSerializedSize(Schema<?> nullableSchema) {
/*  344 */     if (nullableSchema == null) {
/*  345 */       return Protobuf.getInstance().<GeneratedMessageLite<MessageType, BuilderType>>schemaFor(this).getSerializedSize(this);
/*      */     }
/*  347 */     return nullableSchema
/*  348 */       .getSerializedSize(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Object buildMessageInfo() throws Exception {
/*  354 */     return dynamicMethod(MethodToInvoke.BUILD_MESSAGE_INFO, (Object)null, (Object)null);
/*      */   }
/*      */   
/*  357 */   private static Map<Class<?>, GeneratedMessageLite<?, ?>> defaultInstanceMap = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */   
/*      */   static <T extends GeneratedMessageLite<?, ?>> T getDefaultInstance(Class<T> clazz) {
/*  362 */     GeneratedMessageLite<?, ?> generatedMessageLite = defaultInstanceMap.get(clazz);
/*  363 */     if (generatedMessageLite == null) {
/*      */ 
/*      */       
/*      */       try {
/*  367 */         Class.forName(clazz.getName(), true, clazz.getClassLoader());
/*  368 */       } catch (ClassNotFoundException e) {
/*  369 */         throw new IllegalStateException("Class initialization cannot fail.", e);
/*      */       } 
/*  371 */       generatedMessageLite = defaultInstanceMap.get(clazz);
/*      */     } 
/*  373 */     if (generatedMessageLite == null) {
/*      */ 
/*      */       
/*  376 */       generatedMessageLite = (GeneratedMessageLite<?, ?>)((GeneratedMessageLite)UnsafeUtil.<GeneratedMessageLite>allocateInstance(clazz)).getDefaultInstanceForType();
/*      */       
/*  378 */       if (generatedMessageLite == null) {
/*  379 */         throw new IllegalStateException();
/*      */       }
/*  381 */       defaultInstanceMap.put(clazz, generatedMessageLite);
/*      */     } 
/*  383 */     return (T)generatedMessageLite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<?, ?>> void registerDefaultInstance(Class<T> clazz, T defaultInstance) {
/*  393 */     defaultInstance.markImmutable();
/*  394 */     defaultInstanceMap.put(clazz, (GeneratedMessageLite<?, ?>)defaultInstance);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static Object newMessageInfo(MessageLite defaultInstance, String info, Object[] objects) {
/*  399 */     return new RawMessageInfo(defaultInstance, info, objects);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void mergeUnknownFields(UnknownFieldSetLite unknownFields) {
/*  408 */     this.unknownFields = UnknownFieldSetLite.mutableCopyOf(this.unknownFields, unknownFields);
/*      */   }
/*      */ 
/*      */   
/*      */   public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>>
/*      */     extends AbstractMessageLite.Builder<MessageType, BuilderType>
/*      */   {
/*      */     private final MessageType defaultInstance;
/*      */     
/*      */     protected MessageType instance;
/*      */ 
/*      */     
/*      */     protected Builder(MessageType defaultInstance) {
/*  421 */       this.defaultInstance = defaultInstance;
/*  422 */       if (defaultInstance.isMutable()) {
/*  423 */         throw new IllegalArgumentException("Default instance must be immutable.");
/*      */       }
/*      */ 
/*      */       
/*  427 */       this.instance = newMutableInstance();
/*      */     }
/*      */     
/*      */     private MessageType newMutableInstance() {
/*  431 */       return (MessageType)this.defaultInstance.newMutableInstance();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final void copyOnWrite() {
/*  439 */       if (!this.instance.isMutable()) {
/*  440 */         copyOnWriteInternal();
/*      */       }
/*      */     }
/*      */     
/*      */     protected void copyOnWriteInternal() {
/*  445 */       MessageType newInstance = newMutableInstance();
/*  446 */       mergeFromInstance(newInstance, this.instance);
/*  447 */       this.instance = newInstance;
/*      */     }
/*      */ 
/*      */     
/*      */     public final boolean isInitialized() {
/*  452 */       return GeneratedMessageLite.isInitialized((T)this.instance, false);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final BuilderType clear() {
/*  458 */       if (this.defaultInstance.isMutable()) {
/*  459 */         throw new IllegalArgumentException("Default instance must be immutable.");
/*      */       }
/*  461 */       this.instance = newMutableInstance();
/*  462 */       return (BuilderType)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderType clone() {
/*  467 */       BuilderType builder = (BuilderType)getDefaultInstanceForType().newBuilderForType();
/*  468 */       ((Builder)builder).instance = buildPartial();
/*  469 */       return builder;
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageType buildPartial() {
/*  474 */       if (!this.instance.isMutable()) {
/*  475 */         return this.instance;
/*      */       }
/*      */       
/*  478 */       this.instance.makeImmutable();
/*  479 */       return this.instance;
/*      */     }
/*      */ 
/*      */     
/*      */     public final MessageType build() {
/*  484 */       MessageType result = buildPartial();
/*  485 */       if (!result.isInitialized()) {
/*  486 */         throw newUninitializedMessageException(result);
/*      */       }
/*  488 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     protected BuilderType internalMergeFrom(MessageType message) {
/*  493 */       return mergeFrom(message);
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderType mergeFrom(MessageType message) {
/*  498 */       if (getDefaultInstanceForType().equals(message)) {
/*  499 */         return (BuilderType)this;
/*      */       }
/*  501 */       copyOnWrite();
/*  502 */       mergeFromInstance(this.instance, message);
/*  503 */       return (BuilderType)this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderType mergeFrom(byte[] input, int offset, int length, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  510 */       copyOnWrite();
/*      */       try {
/*  512 */         Protobuf.getInstance()
/*  513 */           .<MessageType>schemaFor(this.instance)
/*  514 */           .mergeFrom(this.instance, input, offset, offset + length, new ArrayDecoders.Registers(extensionRegistry));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  520 */       catch (InvalidProtocolBufferException e) {
/*  521 */         throw e;
/*  522 */       } catch (IndexOutOfBoundsException e) {
/*  523 */         throw InvalidProtocolBufferException.truncatedMessage();
/*  524 */       } catch (IOException e) {
/*  525 */         throw new RuntimeException("Reading from byte array should not throw IOException.", e);
/*      */       } 
/*  527 */       return (BuilderType)this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderType mergeFrom(byte[] input, int offset, int length) throws InvalidProtocolBufferException {
/*  533 */       return mergeFrom(input, offset, length, ExtensionRegistryLite.getEmptyRegistry());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderType mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  541 */       copyOnWrite();
/*      */ 
/*      */       
/*      */       try {
/*  545 */         Protobuf.getInstance()
/*  546 */           .<MessageType>schemaFor(this.instance)
/*  547 */           .mergeFrom(this.instance, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
/*  548 */       } catch (RuntimeException e) {
/*  549 */         if (e.getCause() instanceof IOException) {
/*  550 */           throw (IOException)e.getCause();
/*      */         }
/*  552 */         throw e;
/*      */       } 
/*  554 */       return (BuilderType)this;
/*      */     }
/*      */     
/*      */     private static <MessageType> void mergeFromInstance(MessageType dest, MessageType src) {
/*  558 */       Protobuf.getInstance().<MessageType>schemaFor(dest).mergeFrom(dest, src);
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageType getDefaultInstanceForType() {
/*  563 */       return this.defaultInstance;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>>
/*      */     extends GeneratedMessageLite<MessageType, BuilderType>
/*      */     implements ExtendableMessageOrBuilder<MessageType, BuilderType>
/*      */   {
/*  597 */     protected FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = FieldSet.emptySet();
/*      */ 
/*      */     
/*      */     protected final void mergeExtensionFields(MessageType other) {
/*  601 */       if (this.extensions.isImmutable()) {
/*  602 */         this.extensions = this.extensions.clone();
/*      */       }
/*  604 */       this.extensions.mergeFrom(((ExtendableMessage)other).extensions);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected <MessageType2 extends MessageLite> boolean parseUnknownField(MessageType2 defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
/*  620 */       int fieldNumber = WireFormat.getTagFieldNumber(tag);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  625 */       GeneratedMessageLite.GeneratedExtension<MessageType2, ?> extension = extensionRegistry.findLiteExtensionByNumber(defaultInstance, fieldNumber);
/*      */       
/*  627 */       return parseExtension(input, extensionRegistry, extension, tag, fieldNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean parseExtension(CodedInputStream input, ExtensionRegistryLite extensionRegistry, GeneratedMessageLite.GeneratedExtension<?, ?> extension, int tag, int fieldNumber) throws IOException {
/*  637 */       int wireType = WireFormat.getTagWireType(tag);
/*  638 */       boolean unknown = false;
/*  639 */       boolean packed = false;
/*  640 */       if (extension == null) {
/*  641 */         unknown = true;
/*  642 */       } else if (wireType == 
/*  643 */         FieldSet.getWireFormatForFieldType(extension.descriptor
/*  644 */           .getLiteType(), false)) {
/*  645 */         packed = false;
/*  646 */       } else if (extension.descriptor.isRepeated && extension.descriptor.type
/*  647 */         .isPackable() && wireType == 
/*      */         
/*  649 */         FieldSet.getWireFormatForFieldType(extension.descriptor
/*  650 */           .getLiteType(), true)) {
/*  651 */         packed = true;
/*      */       } else {
/*  653 */         unknown = true;
/*      */       } 
/*      */       
/*  656 */       if (unknown) {
/*  657 */         return parseUnknownField(tag, input);
/*      */       }
/*      */ 
/*      */       
/*  661 */       FieldSet<GeneratedMessageLite.ExtensionDescriptor> unused = ensureExtensionsAreMutable();
/*      */       
/*  663 */       if (packed) {
/*  664 */         int length = input.readRawVarint32();
/*  665 */         int limit = input.pushLimit(length);
/*  666 */         if (extension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
/*  667 */           while (input.getBytesUntilLimit() > 0) {
/*  668 */             int rawValue = input.readEnum();
/*  669 */             Object value = extension.descriptor.getEnumType().findValueByNumber(rawValue);
/*  670 */             if (value == null)
/*      */             {
/*      */               
/*  673 */               return true;
/*      */             }
/*  675 */             this.extensions.addRepeatedField(extension.descriptor, extension
/*  676 */                 .singularToFieldSetType(value));
/*      */           } 
/*      */         } else {
/*  679 */           while (input.getBytesUntilLimit() > 0) {
/*      */             
/*  681 */             Object value = FieldSet.readPrimitiveField(input, extension.descriptor
/*  682 */                 .getLiteType(), false);
/*  683 */             this.extensions.addRepeatedField(extension.descriptor, value);
/*      */           } 
/*      */         } 
/*  686 */         input.popLimit(limit);
/*      */       } else {
/*      */         Object value; MessageLite.Builder subBuilder; int rawValue;
/*  689 */         switch (extension.descriptor.getLiteJavaType()) {
/*      */           
/*      */           case MESSAGE:
/*  692 */             subBuilder = null;
/*  693 */             if (!extension.descriptor.isRepeated()) {
/*  694 */               MessageLite existingValue = (MessageLite)this.extensions.getField(extension.descriptor);
/*  695 */               if (existingValue != null) {
/*  696 */                 subBuilder = existingValue.toBuilder();
/*      */               }
/*      */             } 
/*  699 */             if (subBuilder == null) {
/*  700 */               subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
/*      */             }
/*  702 */             if (extension.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
/*  703 */               input.readGroup(extension.getNumber(), subBuilder, extensionRegistry);
/*      */             } else {
/*  705 */               input.readMessage(subBuilder, extensionRegistry);
/*      */             } 
/*  707 */             value = subBuilder.build();
/*      */             break;
/*      */           
/*      */           case ENUM:
/*  711 */             rawValue = input.readEnum();
/*  712 */             value = extension.descriptor.getEnumType().findValueByNumber(rawValue);
/*      */ 
/*      */             
/*  715 */             if (value == null) {
/*  716 */               mergeVarintField(fieldNumber, rawValue);
/*  717 */               return true;
/*      */             } 
/*      */             break;
/*      */           
/*      */           default:
/*  722 */             value = FieldSet.readPrimitiveField(input, extension.descriptor
/*  723 */                 .getLiteType(), false);
/*      */             break;
/*      */         } 
/*      */         
/*  727 */         if (extension.descriptor.isRepeated()) {
/*  728 */           this.extensions.addRepeatedField(extension.descriptor, extension
/*  729 */               .singularToFieldSetType(value));
/*      */         } else {
/*  731 */           this.extensions.setField(extension.descriptor, extension.singularToFieldSetType(value));
/*      */         } 
/*      */       } 
/*  734 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected <MessageType2 extends MessageLite> boolean parseUnknownFieldAsMessageSet(MessageType2 defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
/*  751 */       if (tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
/*  752 */         mergeMessageSetExtensionFromCodedStream(defaultInstance, input, extensionRegistry);
/*  753 */         return true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  758 */       int wireType = WireFormat.getTagWireType(tag);
/*  759 */       if (wireType == 2) {
/*  760 */         return parseUnknownField(defaultInstance, input, extensionRegistry, tag);
/*      */       }
/*      */       
/*  763 */       return input.skipField(tag);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private <MessageType2 extends MessageLite> void mergeMessageSetExtensionFromCodedStream(MessageType2 defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  795 */       int typeId = 0;
/*  796 */       ByteString rawBytes = null;
/*  797 */       GeneratedMessageLite.GeneratedExtension<?, ?> extension = null;
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  802 */         int tag = input.readTag();
/*  803 */         if (tag == 0) {
/*      */           break;
/*      */         }
/*      */         
/*  807 */         if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
/*  808 */           typeId = input.readUInt32();
/*  809 */           if (typeId != 0)
/*  810 */             extension = extensionRegistry.findLiteExtensionByNumber(defaultInstance, typeId); 
/*      */           continue;
/*      */         } 
/*  813 */         if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
/*  814 */           if (typeId != 0 && 
/*  815 */             extension != null) {
/*      */ 
/*      */             
/*  818 */             eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, typeId);
/*  819 */             rawBytes = null;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  824 */           rawBytes = input.readBytes();
/*      */           continue;
/*      */         } 
/*  827 */         if (!input.skipField(tag)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  832 */       input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
/*      */ 
/*      */       
/*  835 */       if (rawBytes != null && typeId != 0) {
/*  836 */         if (extension != null) {
/*  837 */           mergeMessageSetExtensionFromBytes(rawBytes, extensionRegistry, extension);
/*      */         }
/*  839 */         else if (rawBytes != null) {
/*  840 */           mergeLengthDelimitedField(typeId, rawBytes);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void eagerlyMergeMessageSetExtension(CodedInputStream input, GeneratedMessageLite.GeneratedExtension<?, ?> extension, ExtensionRegistryLite extensionRegistry, int typeId) throws IOException {
/*  852 */       int fieldNumber = typeId;
/*  853 */       int tag = WireFormat.makeTag(typeId, 2);
/*      */       
/*  855 */       boolean unused = parseExtension(input, extensionRegistry, extension, tag, fieldNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionRegistryLite extensionRegistry, GeneratedMessageLite.GeneratedExtension<?, ?> extension) throws IOException {
/*  863 */       MessageLite.Builder subBuilder = null;
/*  864 */       MessageLite existingValue = (MessageLite)this.extensions.getField(extension.descriptor);
/*  865 */       if (existingValue != null) {
/*  866 */         subBuilder = existingValue.toBuilder();
/*      */       }
/*  868 */       if (subBuilder == null) {
/*  869 */         subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
/*      */       }
/*  871 */       subBuilder.mergeFrom(rawBytes, extensionRegistry);
/*  872 */       MessageLite value = subBuilder.build();
/*      */       
/*  874 */       ensureExtensionsAreMutable()
/*  875 */         .setField(extension.descriptor, extension.singularToFieldSetType(value));
/*      */     }
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     FieldSet<GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable() {
/*  880 */       if (this.extensions.isImmutable()) {
/*  881 */         this.extensions = this.extensions.clone();
/*      */       }
/*  883 */       return this.extensions;
/*      */     }
/*      */     
/*      */     private void verifyExtensionContainingType(GeneratedMessageLite.GeneratedExtension<MessageType, ?> extension) {
/*  887 */       if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType())
/*      */       {
/*  889 */         throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
/*  898 */       GeneratedMessageLite.GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
/*      */       
/*  900 */       verifyExtensionContainingType(extensionLite);
/*  901 */       return this.extensions.hasField(extensionLite.descriptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
/*  908 */       GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedMessageLite.GeneratedExtension)GeneratedMessageLite.checkIsLite((ExtensionLite)extension);
/*      */       
/*  910 */       verifyExtensionContainingType(extensionLite);
/*  911 */       return this.extensions.getRepeatedFieldCount(extensionLite.descriptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extension) {
/*  918 */       GeneratedMessageLite.GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
/*      */       
/*  920 */       verifyExtensionContainingType(extensionLite);
/*  921 */       Object value = this.extensions.getField(extensionLite.descriptor);
/*  922 */       if (value == null) {
/*  923 */         return extensionLite.defaultValue;
/*      */       }
/*  925 */       return (Type)extensionLite.fromFieldSetType(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extension, int index) {
/*  934 */       GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedMessageLite.GeneratedExtension)GeneratedMessageLite.checkIsLite((ExtensionLite)extension);
/*      */       
/*  936 */       verifyExtensionContainingType(extensionLite);
/*  937 */       return (Type)extensionLite
/*  938 */         .singularFromFieldSetType(this.extensions
/*  939 */           .getRepeatedField(extensionLite.descriptor, index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean extensionsAreInitialized() {
/*  944 */       return this.extensions.isInitialized();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class ExtensionWriter
/*      */     {
/*  956 */       private final Iterator<Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object>> iter = GeneratedMessageLite.ExtendableMessage.this.extensions.iterator();
/*      */       private Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object> next;
/*      */       private final boolean messageSetWireFormat;
/*      */       
/*      */       private ExtensionWriter(boolean messageSetWireFormat) {
/*  961 */         if (this.iter.hasNext()) {
/*  962 */           this.next = this.iter.next();
/*      */         }
/*  964 */         this.messageSetWireFormat = messageSetWireFormat;
/*      */       }
/*      */       
/*      */       public void writeUntil(int end, CodedOutputStream output) throws IOException {
/*  968 */         while (this.next != null && ((GeneratedMessageLite.ExtensionDescriptor)this.next.getKey()).getNumber() < end) {
/*  969 */           GeneratedMessageLite.ExtensionDescriptor extension = this.next.getKey();
/*  970 */           if (this.messageSetWireFormat && extension
/*  971 */             .getLiteJavaType() == WireFormat.JavaType.MESSAGE && 
/*  972 */             !extension.isRepeated()) {
/*  973 */             output.writeMessageSetExtension(extension.getNumber(), (MessageLite)this.next.getValue());
/*      */           } else {
/*  975 */             FieldSet.writeField(extension, this.next.getValue(), output);
/*      */           } 
/*  977 */           if (this.iter.hasNext()) {
/*  978 */             this.next = this.iter.next(); continue;
/*      */           } 
/*  980 */           this.next = null;
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected ExtensionWriter newExtensionWriter() {
/*  987 */       return new ExtensionWriter(false);
/*      */     }
/*      */     
/*      */     protected ExtensionWriter newMessageSetExtensionWriter() {
/*  991 */       return new ExtensionWriter(true);
/*      */     }
/*      */ 
/*      */     
/*      */     protected int extensionsSerializedSize() {
/*  996 */       return this.extensions.getSerializedSize();
/*      */     }
/*      */     
/*      */     protected int extensionsSerializedSizeAsMessageSet() {
/* 1000 */       return this.extensions.getMessageSetSerializedSize();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>>
/*      */     extends Builder<MessageType, BuilderType>
/*      */     implements ExtendableMessageOrBuilder<MessageType, BuilderType>
/*      */   {
/*      */     protected ExtendableBuilder(MessageType defaultInstance) {
/* 1012 */       super(defaultInstance);
/*      */     }
/*      */ 
/*      */     
/*      */     void internalSetExtensionSet(FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) {
/* 1017 */       copyOnWrite();
/* 1018 */       ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions = extensions;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void copyOnWriteInternal() {
/* 1023 */       super.copyOnWriteInternal();
/* 1024 */       if (((GeneratedMessageLite.ExtendableMessage)this.instance).extensions != FieldSet.emptySet()) {
/* 1025 */         ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions = ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions.clone();
/*      */       }
/*      */     }
/*      */     
/*      */     private FieldSet<GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable() {
/* 1030 */       FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions;
/* 1031 */       if (extensions.isImmutable()) {
/* 1032 */         extensions = extensions.clone();
/* 1033 */         ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions = extensions;
/*      */       } 
/* 1035 */       return extensions;
/*      */     }
/*      */ 
/*      */     
/*      */     public final MessageType buildPartial() {
/* 1040 */       if (!((GeneratedMessageLite.ExtendableMessage)this.instance).isMutable()) {
/* 1041 */         return this.instance;
/*      */       }
/*      */       
/* 1044 */       ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions.makeImmutable();
/* 1045 */       return super.buildPartial();
/*      */     }
/*      */     
/*      */     private void verifyExtensionContainingType(GeneratedMessageLite.GeneratedExtension<MessageType, ?> extension) {
/* 1049 */       if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType())
/*      */       {
/* 1051 */         throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
/* 1060 */       return ((GeneratedMessageLite.ExtendableMessage)this.instance).hasExtension(extension);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
/* 1067 */       return ((GeneratedMessageLite.ExtendableMessage)this.instance).getExtensionCount(extension);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extension) {
/* 1073 */       return (Type)((GeneratedMessageLite.ExtendableMessage)this.instance).getExtension(extension);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extension, int index) {
/* 1080 */       return (Type)((GeneratedMessageLite.ExtendableMessage)this.instance).getExtension(extension, index);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extension, Type value) {
/* 1086 */       GeneratedMessageLite.GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
/*      */       
/* 1088 */       verifyExtensionContainingType(extensionLite);
/* 1089 */       copyOnWrite();
/* 1090 */       ensureExtensionsAreMutable()
/* 1091 */         .setField(extensionLite.descriptor, extensionLite.toFieldSetType(value));
/* 1092 */       return (BuilderType)this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extension, int index, Type value) {
/* 1098 */       GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedMessageLite.GeneratedExtension)GeneratedMessageLite.checkIsLite((ExtensionLite)extension);
/*      */       
/* 1100 */       verifyExtensionContainingType(extensionLite);
/* 1101 */       copyOnWrite();
/* 1102 */       ensureExtensionsAreMutable()
/* 1103 */         .setRepeatedField(extensionLite.descriptor, index, extensionLite
/* 1104 */           .singularToFieldSetType(value));
/* 1105 */       return (BuilderType)this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extension, Type value) {
/* 1111 */       GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedMessageLite.GeneratedExtension)GeneratedMessageLite.checkIsLite((ExtensionLite)extension);
/*      */       
/* 1113 */       verifyExtensionContainingType(extensionLite);
/* 1114 */       copyOnWrite();
/* 1115 */       ensureExtensionsAreMutable()
/* 1116 */         .addRepeatedField(extensionLite.descriptor, extensionLite.singularToFieldSetType(value));
/* 1117 */       return (BuilderType)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public final BuilderType clearExtension(ExtensionLite<MessageType, ?> extension) {
/* 1122 */       GeneratedMessageLite.GeneratedExtension<MessageType, ?> extensionLite = GeneratedMessageLite.checkIsLite((ExtensionLite)extension);
/*      */       
/* 1124 */       verifyExtensionContainingType(extensionLite);
/* 1125 */       copyOnWrite();
/* 1126 */       ensureExtensionsAreMutable().clearField(extensionLite.descriptor);
/* 1127 */       return (BuilderType)this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, Class singularType) {
/* 1143 */     return new GeneratedExtension<>(containingTypeDefaultInstance, defaultValue, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, false, false), singularType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingTypeDefaultInstance, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, boolean isPacked, Class singularType) {
/* 1163 */     ProtobufArrayList<?> protobufArrayList = ProtobufArrayList.emptyList();
/* 1164 */     return new GeneratedExtension<>(containingTypeDefaultInstance, (Type)protobufArrayList, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, true, isPacked), singularType);
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ExtensionDescriptor
/*      */     implements FieldSet.FieldDescriptorLite<ExtensionDescriptor>
/*      */   {
/*      */     final Internal.EnumLiteMap<?> enumTypeMap;
/*      */     
/*      */     final int number;
/*      */     
/*      */     final WireFormat.FieldType type;
/*      */     final boolean isRepeated;
/*      */     final boolean isPacked;
/*      */     
/*      */     ExtensionDescriptor(Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, boolean isRepeated, boolean isPacked) {
/* 1180 */       this.enumTypeMap = enumTypeMap;
/* 1181 */       this.number = number;
/* 1182 */       this.type = type;
/* 1183 */       this.isRepeated = isRepeated;
/* 1184 */       this.isPacked = isPacked;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getNumber() {
/* 1195 */       return this.number;
/*      */     }
/*      */ 
/*      */     
/*      */     public WireFormat.FieldType getLiteType() {
/* 1200 */       return this.type;
/*      */     }
/*      */ 
/*      */     
/*      */     public WireFormat.JavaType getLiteJavaType() {
/* 1205 */       return this.type.getJavaType();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRepeated() {
/* 1210 */       return this.isRepeated;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isPacked() {
/* 1215 */       return this.isPacked;
/*      */     }
/*      */ 
/*      */     
/*      */     public Internal.EnumLiteMap<?> getEnumType() {
/* 1220 */       return this.enumTypeMap;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean internalMessageIsImmutable(Object message) {
/* 1225 */       return message instanceof MessageLite;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void internalMergeFrom(Object to, Object from) {
/* 1231 */       ((GeneratedMessageLite.Builder)to).mergeFrom(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(ExtensionDescriptor other) {
/* 1236 */       return this.number - other.number;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Method getMethodOrDie(Class clazz, String name, Class... params) {
/*      */     try {
/* 1246 */       return clazz.getMethod(name, params);
/* 1247 */     } catch (NoSuchMethodException e) {
/* 1248 */       throw new RuntimeException("Generated message class \"" + clazz
/* 1249 */           .getName() + "\" missing method \"" + name + "\".", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Object invokeOrDie(Method method, Object object, Object... params) {
/*      */     try {
/* 1257 */       return method.invoke(object, params);
/* 1258 */     } catch (IllegalAccessException e) {
/* 1259 */       throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
/*      */     }
/* 1261 */     catch (InvocationTargetException e) {
/* 1262 */       Throwable cause = e.getCause();
/* 1263 */       if (cause instanceof RuntimeException)
/* 1264 */         throw (RuntimeException)cause; 
/* 1265 */       if (cause instanceof Error) {
/* 1266 */         throw (Error)cause;
/*      */       }
/* 1268 */       throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class GeneratedExtension<ContainingType extends MessageLite, Type>
/*      */     extends ExtensionLite<ContainingType, Type>
/*      */   {
/*      */     final ContainingType containingTypeDefaultInstance;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Type defaultValue;
/*      */ 
/*      */ 
/*      */     
/*      */     final MessageLite messageDefaultInstance;
/*      */ 
/*      */ 
/*      */     
/*      */     final GeneratedMessageLite.ExtensionDescriptor descriptor;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     GeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, GeneratedMessageLite.ExtensionDescriptor descriptor, Class singularType) {
/* 1298 */       if (containingTypeDefaultInstance == null) {
/* 1299 */         throw new IllegalArgumentException("Null containingTypeDefaultInstance");
/*      */       }
/* 1301 */       if (descriptor.getLiteType() == WireFormat.FieldType.MESSAGE && messageDefaultInstance == null)
/*      */       {
/* 1303 */         throw new IllegalArgumentException("Null messageDefaultInstance");
/*      */       }
/* 1305 */       this.containingTypeDefaultInstance = containingTypeDefaultInstance;
/* 1306 */       this.defaultValue = defaultValue;
/* 1307 */       this.messageDefaultInstance = messageDefaultInstance;
/* 1308 */       this.descriptor = descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ContainingType getContainingTypeDefaultInstance() {
/* 1318 */       return this.containingTypeDefaultInstance;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getNumber() {
/* 1324 */       return this.descriptor.getNumber();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageLite getMessageDefaultInstance() {
/* 1333 */       return this.messageDefaultInstance;
/*      */     }
/*      */     
/*      */     Object fromFieldSetType(Object value) {
/* 1337 */       if (this.descriptor.isRepeated()) {
/* 1338 */         if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
/* 1339 */           ProtobufArrayList<Object> result = new ProtobufArrayList();
/* 1340 */           result.ensureCapacity(((List)value).size());
/* 1341 */           for (Object element : value) {
/* 1342 */             result.add(singularFromFieldSetType(element));
/*      */           }
/* 1344 */           result.makeImmutable();
/* 1345 */           return result;
/*      */         } 
/* 1347 */         return value;
/*      */       } 
/*      */       
/* 1350 */       return singularFromFieldSetType(value);
/*      */     }
/*      */ 
/*      */     
/*      */     Object singularFromFieldSetType(Object value) {
/* 1355 */       if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
/* 1356 */         return this.descriptor.enumTypeMap.findValueByNumber(((Integer)value).intValue());
/*      */       }
/* 1358 */       return value;
/*      */     }
/*      */ 
/*      */     
/*      */     Object toFieldSetType(Object value) {
/* 1363 */       if (this.descriptor.isRepeated()) {
/* 1364 */         if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
/* 1365 */           List<Object> result = new ArrayList();
/* 1366 */           for (Object element : value) {
/* 1367 */             result.add(singularToFieldSetType(element));
/*      */           }
/* 1369 */           return result;
/*      */         } 
/* 1371 */         return value;
/*      */       } 
/*      */       
/* 1374 */       return singularToFieldSetType(value);
/*      */     }
/*      */ 
/*      */     
/*      */     Object singularToFieldSetType(Object value) {
/* 1379 */       if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
/* 1380 */         return Integer.valueOf(((Internal.EnumLite)value).getNumber());
/*      */       }
/* 1382 */       return value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public WireFormat.FieldType getLiteType() {
/* 1388 */       return this.descriptor.getLiteType();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRepeated() {
/* 1393 */       return this.descriptor.isRepeated;
/*      */     }
/*      */ 
/*      */     
/*      */     public Type getDefaultValue() {
/* 1398 */       return this.defaultValue;
/*      */     }
/*      */   }
/*      */   
/*      */   protected static final class SerializedForm implements Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     private final Class<?> messageClass;
/*      */     private final String messageClassName;
/*      */     private final byte[] asBytes;
/*      */     
/*      */     public static SerializedForm of(MessageLite message) {
/* 1409 */       return new SerializedForm(message);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SerializedForm(MessageLite regularForm) {
/* 1425 */       this.messageClass = regularForm.getClass();
/* 1426 */       this.messageClassName = regularForm.getClass().getName();
/* 1427 */       this.asBytes = regularForm.toByteArray();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object readResolve() throws ObjectStreamException {
/*      */       try {
/* 1438 */         Class<?> messageClass = resolveMessageClass();
/*      */         
/* 1440 */         Field defaultInstanceField = messageClass.getDeclaredField("DEFAULT_INSTANCE");
/* 1441 */         defaultInstanceField.setAccessible(true);
/*      */         
/* 1443 */         MessageLite.Builder builder = ((MessageLite)defaultInstanceField.get(null)).newBuilderForType();
/* 1444 */         builder.mergeFrom(this.asBytes);
/* 1445 */         return builder.buildPartial();
/* 1446 */       } catch (ClassNotFoundException e) {
/* 1447 */         throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e);
/* 1448 */       } catch (NoSuchFieldException e) {
/* 1449 */         throw new RuntimeException("Unable to find DEFAULT_INSTANCE in " + this.messageClassName, e);
/* 1450 */       } catch (SecurityException e) {
/* 1451 */         throw new RuntimeException("Unable to call DEFAULT_INSTANCE in " + this.messageClassName, e);
/* 1452 */       } catch (IllegalAccessException e) {
/* 1453 */         throw new RuntimeException("Unable to call parsePartialFrom", e);
/* 1454 */       } catch (InvalidProtocolBufferException e) {
/* 1455 */         throw new RuntimeException("Unable to understand proto buffer", e);
/*      */       } 
/*      */     }
/*      */     
/*      */     private Class<?> resolveMessageClass() throws ClassNotFoundException {
/* 1460 */       if (this.messageClass == null) {
/*      */         
/* 1462 */         Class<?> clazz = Class.forName(this.messageClassName, false, 
/* 1463 */             getClass().getClassLoader());
/* 1464 */         if (!MessageLite.class.isAssignableFrom(clazz)) {
/* 1465 */           throw new ClassNotFoundException();
/*      */         }
/* 1467 */         return clazz;
/*      */       } 
/* 1469 */       return this.messageClass;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> checkIsLite(ExtensionLite<MessageType, T> extension) {
/* 1479 */     if (!extension.isLite()) {
/* 1480 */       throw new IllegalArgumentException("Expected a lite extension.");
/*      */     }
/*      */     
/* 1483 */     return (GeneratedExtension<MessageType, T>)extension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final <T extends GeneratedMessageLite<T, ?>> boolean isInitialized(T message, boolean shouldMemoize) {
/* 1490 */     byte memoizedIsInitialized = ((Byte)message.dynamicMethod(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED, (Object)null, (Object)null)).byteValue();
/* 1491 */     if (memoizedIsInitialized == 1) {
/* 1492 */       return true;
/*      */     }
/* 1494 */     if (memoizedIsInitialized == 0) {
/* 1495 */       return false;
/*      */     }
/* 1497 */     boolean isInitialized = Protobuf.getInstance().<T>schemaFor(message).isInitialized(message);
/* 1498 */     if (shouldMemoize)
/*      */     {
/*      */       
/* 1501 */       Object object = message.dynamicMethod(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, 
/* 1502 */           isInitialized ? message : null, (Object)null);
/*      */     }
/* 1504 */     return isInitialized;
/*      */   }
/*      */   
/*      */   protected static Internal.IntList emptyIntList() {
/* 1508 */     return IntArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.IntList mutableCopy(Internal.IntList list) {
/* 1512 */     int size = list.size();
/* 1513 */     return list.mutableCopyWithCapacity(size * 2);
/*      */   }
/*      */   
/*      */   protected static Internal.LongList emptyLongList() {
/* 1517 */     return LongArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.LongList mutableCopy(Internal.LongList list) {
/* 1521 */     int size = list.size();
/* 1522 */     return list.mutableCopyWithCapacity(size * 2);
/*      */   }
/*      */   
/*      */   protected static Internal.FloatList emptyFloatList() {
/* 1526 */     return FloatArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.FloatList mutableCopy(Internal.FloatList list) {
/* 1530 */     int size = list.size();
/* 1531 */     return list.mutableCopyWithCapacity(size * 2);
/*      */   }
/*      */   
/*      */   protected static Internal.DoubleList emptyDoubleList() {
/* 1535 */     return DoubleArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.DoubleList mutableCopy(Internal.DoubleList list) {
/* 1539 */     int size = list.size();
/* 1540 */     return list.mutableCopyWithCapacity(size * 2);
/*      */   }
/*      */   
/*      */   protected static Internal.BooleanList emptyBooleanList() {
/* 1544 */     return BooleanArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.BooleanList mutableCopy(Internal.BooleanList list) {
/* 1548 */     int size = list.size();
/* 1549 */     return list.mutableCopyWithCapacity(size * 2);
/*      */   }
/*      */   
/*      */   protected static <E> Internal.ProtobufList<E> emptyProtobufList() {
/* 1553 */     return ProtobufArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static <E> Internal.ProtobufList<E> mutableCopy(Internal.ProtobufList<E> list) {
/* 1557 */     int size = list.size();
/* 1558 */     return list.mutableCopyWithCapacity(size * 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>>
/*      */     extends AbstractParser<T>
/*      */   {
/*      */     private final T defaultInstance;
/*      */ 
/*      */ 
/*      */     
/*      */     public DefaultInstanceBasedParser(T defaultInstance) {
/* 1572 */       this.defaultInstance = defaultInstance;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public T parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1578 */       return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input, extensionRegistry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public T parsePartialFrom(byte[] input, int offset, int length, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1585 */       return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input, offset, length, extensionRegistry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T instance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1598 */     T result = (T)instance.newMutableInstance();
/*      */ 
/*      */     
/*      */     try {
/* 1602 */       Schema<T> schema = Protobuf.getInstance().schemaFor(result);
/* 1603 */       schema.mergeFrom(result, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
/* 1604 */       schema.makeImmutable(result);
/* 1605 */     } catch (InvalidProtocolBufferException e) {
/* 1606 */       if (e.getThrownFromInputStream()) {
/* 1607 */         e = new InvalidProtocolBufferException(e);
/*      */       }
/* 1609 */       throw e.setUnfinishedMessage(result);
/* 1610 */     } catch (UninitializedMessageException e) {
/* 1611 */       throw e.asInvalidProtocolBufferException().setUnfinishedMessage(result);
/* 1612 */     } catch (IOException e) {
/* 1613 */       if (e.getCause() instanceof InvalidProtocolBufferException) {
/* 1614 */         throw (InvalidProtocolBufferException)e.getCause();
/*      */       }
/* 1616 */       throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(result);
/* 1617 */     } catch (RuntimeException e) {
/* 1618 */       if (e.getCause() instanceof InvalidProtocolBufferException) {
/* 1619 */         throw (InvalidProtocolBufferException)e.getCause();
/*      */       }
/* 1621 */       throw e;
/*      */     } 
/* 1623 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T defaultInstance, byte[] input, int offset, int length, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1634 */     if (length == 0) {
/* 1635 */       return defaultInstance;
/*      */     }
/* 1637 */     T result = (T)defaultInstance.newMutableInstance();
/*      */     try {
/* 1639 */       Schema<T> schema = Protobuf.getInstance().schemaFor(result);
/* 1640 */       schema.mergeFrom(result, input, offset, offset + length, new ArrayDecoders.Registers(extensionRegistry));
/*      */       
/* 1642 */       schema.makeImmutable(result);
/* 1643 */     } catch (InvalidProtocolBufferException e) {
/* 1644 */       if (e.getThrownFromInputStream()) {
/* 1645 */         e = new InvalidProtocolBufferException(e);
/*      */       }
/* 1647 */       throw e.setUnfinishedMessage(result);
/* 1648 */     } catch (UninitializedMessageException e) {
/* 1649 */       throw e.asInvalidProtocolBufferException().setUnfinishedMessage(result);
/* 1650 */     } catch (IOException e) {
/* 1651 */       if (e.getCause() instanceof InvalidProtocolBufferException) {
/* 1652 */         throw (InvalidProtocolBufferException)e.getCause();
/*      */       }
/* 1654 */       throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(result);
/* 1655 */     } catch (IndexOutOfBoundsException e) {
/* 1656 */       throw InvalidProtocolBufferException.truncatedMessage().setUnfinishedMessage(result);
/*      */     } 
/* 1658 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T defaultInstance, CodedInputStream input) throws InvalidProtocolBufferException {
/* 1663 */     return parsePartialFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(T message) throws InvalidProtocolBufferException {
/* 1674 */     if (message != null && !message.isInitialized()) {
/* 1675 */       throw message
/* 1676 */         .newUninitializedMessageException()
/* 1677 */         .asInvalidProtocolBufferException()
/* 1678 */         .setUnfinishedMessage(message);
/*      */     }
/* 1680 */     return message;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1687 */     return checkMessageInitialized(
/* 1688 */         parseFrom(defaultInstance, CodedInputStream.newInstance(data), extensionRegistry));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteBuffer data) throws InvalidProtocolBufferException {
/* 1694 */     return parseFrom(defaultInstance, data, ExtensionRegistryLite.getEmptyRegistry());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteString data) throws InvalidProtocolBufferException {
/* 1700 */     return checkMessageInitialized(
/* 1701 */         parseFrom(defaultInstance, data, ExtensionRegistryLite.getEmptyRegistry()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1708 */     return checkMessageInitialized(parsePartialFrom(defaultInstance, data, extensionRegistry));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T defaultInstance, ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1716 */     CodedInputStream input = data.newCodedInput();
/* 1717 */     T message = parsePartialFrom(defaultInstance, input, extensionRegistry);
/*      */     try {
/* 1719 */       input.checkLastTagWas(0);
/* 1720 */     } catch (InvalidProtocolBufferException e) {
/* 1721 */       throw e.setUnfinishedMessage(message);
/*      */     } 
/* 1723 */     return message;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, byte[] data) throws InvalidProtocolBufferException {
/* 1729 */     return checkMessageInitialized(
/* 1730 */         parsePartialFrom(defaultInstance, data, 0, data.length, 
/* 1731 */           ExtensionRegistryLite.getEmptyRegistry()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1738 */     return checkMessageInitialized(
/* 1739 */         parsePartialFrom(defaultInstance, data, 0, data.length, extensionRegistry));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, InputStream input) throws InvalidProtocolBufferException {
/* 1745 */     return checkMessageInitialized(
/* 1746 */         parsePartialFrom(defaultInstance, 
/*      */           
/* 1748 */           CodedInputStream.newInstance(input), 
/* 1749 */           ExtensionRegistryLite.getEmptyRegistry()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1756 */     return checkMessageInitialized(
/* 1757 */         parsePartialFrom(defaultInstance, CodedInputStream.newInstance(input), extensionRegistry));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, CodedInputStream input) throws InvalidProtocolBufferException {
/* 1763 */     return parseFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1770 */     return checkMessageInitialized(parsePartialFrom(defaultInstance, input, extensionRegistry));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T defaultInstance, InputStream input) throws InvalidProtocolBufferException {
/* 1776 */     return checkMessageInitialized(
/* 1777 */         parsePartialDelimitedFrom(defaultInstance, input, 
/* 1778 */           ExtensionRegistryLite.getEmptyRegistry()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1785 */     return checkMessageInitialized(
/* 1786 */         parsePartialDelimitedFrom(defaultInstance, input, extensionRegistry));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*      */     int size;
/*      */     try {
/* 1794 */       int firstByte = input.read();
/* 1795 */       if (firstByte == -1) {
/* 1796 */         return null;
/*      */       }
/* 1798 */       size = CodedInputStream.readRawVarint32(firstByte, input);
/* 1799 */     } catch (InvalidProtocolBufferException e) {
/* 1800 */       if (e.getThrownFromInputStream()) {
/* 1801 */         e = new InvalidProtocolBufferException(e);
/*      */       }
/* 1803 */       throw e;
/* 1804 */     } catch (IOException e) {
/* 1805 */       throw new InvalidProtocolBufferException(e);
/*      */     } 
/* 1807 */     InputStream limitedInput = new AbstractMessageLite.Builder.LimitedInputStream(input, size);
/* 1808 */     CodedInputStream codedInput = CodedInputStream.newInstance(limitedInput);
/* 1809 */     T message = parsePartialFrom(defaultInstance, codedInput, extensionRegistry);
/*      */     try {
/* 1811 */       codedInput.checkLastTagWas(0);
/* 1812 */     } catch (InvalidProtocolBufferException e) {
/* 1813 */       throw e.setUnfinishedMessage(message);
/*      */     } 
/* 1815 */     return message;
/*      */   }
/*      */   
/*      */   protected abstract Object dynamicMethod(MethodToInvoke paramMethodToInvoke, Object paramObject1, Object paramObject2);
/*      */   
/*      */   public static interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends MessageLiteOrBuilder {
/*      */     <Type> boolean hasExtension(ExtensionLite<MessageType, Type> param1ExtensionLite);
/*      */     
/*      */     <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> param1ExtensionLite);
/*      */     
/*      */     <Type> Type getExtension(ExtensionLite<MessageType, Type> param1ExtensionLite);
/*      */     
/*      */     <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> param1ExtensionLite, int param1Int);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\GeneratedMessageLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */