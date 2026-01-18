/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.CopyOnWriteArraySet;
/*      */ import java.util.logging.Logger;
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
/*      */ public abstract class GeneratedMessage
/*      */   extends AbstractMessage
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*   58 */   private static final Logger logger = Logger.getLogger(GeneratedMessage.class.getName());
/*      */ 
/*      */   
/*      */   protected static boolean alwaysUseFieldBuilders = false;
/*      */ 
/*      */   
/*      */   protected UnknownFieldSet unknownFields;
/*      */ 
/*      */   
/*      */   static final String PRE22_GENCODE_SILENCE_PROPERTY = "com.google.protobuf.use_unsafe_pre22_gencode";
/*      */   
/*      */   static final String PRE22_GENCODE_ERROR_PROPERTY = "com.google.protobuf.error_on_unsafe_pre22_gencode";
/*      */   
/*      */   static final String PRE22_GENCODE_VULNERABILITY_MESSAGE = "As of 2022/09/29 (release 21.7) makeExtensionsImmutable should not be called from protobuf gencode. If you are seeing this message, your gencode is vulnerable to a denial of service attack. You should regenerate your code using protobuf 25.6 or later. Use the latest version that meets your needs. However, if you understand the risks and wish to continue with vulnerable gencode, you can set the system property `-Dcom.google.protobuf.use_unsafe_pre22_gencode` on the command line to silence this warning. You also can set `-Dcom.google.protobuf.error_on_unsafe_pre22_gencode` to throw an error instead. See security vulnerability: https://github.com/protocolbuffers/protobuf/security/advisories/GHSA-h4h5-3hr4-j3g2";
/*      */ 
/*      */   
/*      */   protected GeneratedMessage() {
/*   75 */     this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*      */   }
/*      */   
/*      */   protected GeneratedMessage(Builder<?> builder) {
/*   79 */     this.unknownFields = builder.getUnknownFields();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Parser<? extends GeneratedMessage> getParserForType() {
/*   85 */     throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void enableAlwaysUseFieldBuildersForTesting() {
/*   94 */     setAlwaysUseFieldBuildersForTesting(true);
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
/*      */   static void setAlwaysUseFieldBuildersForTesting(boolean useBuilders) {
/*  106 */     alwaysUseFieldBuilders = useBuilders;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Descriptors.Descriptor getDescriptorForType() {
/*  117 */     return (internalGetFieldAccessorTable()).descriptor;
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
/*      */   @Deprecated
/*      */   protected void mergeFromAndMakeImmutableInternal(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  130 */     Schema<GeneratedMessage> schema = Protobuf.getInstance().schemaFor(this);
/*      */     try {
/*  132 */       schema.mergeFrom(this, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
/*  133 */     } catch (InvalidProtocolBufferException e) {
/*  134 */       throw e.setUnfinishedMessage(this);
/*  135 */     } catch (IOException e) {
/*  136 */       throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(this);
/*      */     } 
/*  138 */     schema.makeImmutable(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable(boolean getBytesForString) {
/*  149 */     TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<>();
/*  150 */     FieldAccessorTable fieldAccessorTable = internalGetFieldAccessorTable();
/*      */     
/*  152 */     Descriptors.Descriptor descriptor = fieldAccessorTable.descriptor;
/*  153 */     List<Descriptors.FieldDescriptor> fields = descriptor.getFields();
/*      */     
/*  155 */     for (int i = 0; i < fields.size(); i++) {
/*  156 */       Descriptors.FieldDescriptor field = fields.get(i);
/*  157 */       Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  163 */       if (oneofDescriptor != null) {
/*      */         
/*  165 */         i += oneofDescriptor.getFieldCount() - 1;
/*  166 */         if (!hasOneof(oneofDescriptor)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  171 */         field = getOneofFieldDescriptor(oneofDescriptor);
/*      */       } else {
/*      */         
/*  174 */         if (field.isRepeated()) {
/*  175 */           List<?> value = (List)getField(field);
/*  176 */           if (!value.isEmpty()) {
/*  177 */             result.put(field, value);
/*      */           }
/*      */           continue;
/*      */         } 
/*  181 */         if (!hasField(field)) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */       
/*  186 */       if (getBytesForString && field.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING) {
/*  187 */         result.put(field, getFieldRaw(field));
/*      */       } else {
/*  189 */         result.put(field, getField(field));
/*      */       }  continue;
/*      */     } 
/*  192 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInitialized() {
/*  198 */     for (Descriptors.FieldDescriptor field : getDescriptorForType().getFields()) {
/*      */       
/*  200 */       if (field.isRequired() && 
/*  201 */         !hasField(field)) {
/*  202 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  206 */       if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*  207 */         if (field.isRepeated()) {
/*      */           
/*  209 */           List<Message> messageList = (List<Message>)getField(field);
/*  210 */           for (Message element : messageList) {
/*  211 */             if (!element.isInitialized())
/*  212 */               return false; 
/*      */           } 
/*      */           continue;
/*      */         } 
/*  216 */         if (hasField(field) && !((Message)getField(field)).isInitialized()) {
/*  217 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  223 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/*  228 */     return Collections.unmodifiableMap(getAllFieldsMutable(false));
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
/*      */   Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
/*  240 */     return Collections.unmodifiableMap(getAllFieldsMutable(true));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/*  245 */     return internalGetFieldAccessorTable().getOneof(oneof).has(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/*  250 */     return internalGetFieldAccessorTable().getOneof(oneof).get(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasField(Descriptors.FieldDescriptor field) {
/*  255 */     return internalGetFieldAccessorTable().getField(field).has(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getField(Descriptors.FieldDescriptor field) {
/*  260 */     return internalGetFieldAccessorTable().getField(field).get(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object getFieldRaw(Descriptors.FieldDescriptor field) {
/*  271 */     return internalGetFieldAccessorTable().getField(field).getRaw(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/*  276 */     return internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/*  281 */     return internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public UnknownFieldSet getUnknownFields() {
/*  287 */     return this.unknownFields;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setUnknownFields(UnknownFieldSet unknownFields) {
/*  293 */     this.unknownFields = unknownFields;
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
/*      */   protected boolean parseUnknownField(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
/*  309 */     if (input.shouldDiscardUnknownFields()) {
/*  310 */       return input.skipField(tag);
/*      */     }
/*  312 */     return unknownFields.mergeFieldFrom(tag, input);
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
/*      */   protected boolean parseUnknownFieldProto3(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
/*  327 */     return parseUnknownField(input, unknownFields, extensionRegistry, tag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream input) throws IOException {
/*      */     try {
/*  335 */       return parser.parseFrom(input);
/*  336 */     } catch (InvalidProtocolBufferException e) {
/*  337 */       throw e.unwrapIOException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream input, ExtensionRegistryLite extensions) throws IOException {
/*      */     try {
/*  345 */       return parser.parseFrom(input, extensions);
/*  346 */     } catch (InvalidProtocolBufferException e) {
/*  347 */       throw e.unwrapIOException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream input) throws IOException {
/*      */     try {
/*  356 */       return parser.parseFrom(input);
/*  357 */     } catch (InvalidProtocolBufferException e) {
/*  358 */       throw e.unwrapIOException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream input, ExtensionRegistryLite extensions) throws IOException {
/*      */     try {
/*  367 */       return parser.parseFrom(input, extensions);
/*  368 */     } catch (InvalidProtocolBufferException e) {
/*  369 */       throw e.unwrapIOException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream input) throws IOException {
/*      */     try {
/*  378 */       return parser.parseDelimitedFrom(input);
/*  379 */     } catch (InvalidProtocolBufferException e) {
/*  380 */       throw e.unwrapIOException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream input, ExtensionRegistryLite extensions) throws IOException {
/*      */     try {
/*  388 */       return parser.parseDelimitedFrom(input, extensions);
/*  389 */     } catch (InvalidProtocolBufferException e) {
/*  390 */       throw e.unwrapIOException();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static boolean canUseUnsafe() {
/*  395 */     return (UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations());
/*      */   }
/*      */   
/*      */   protected static Internal.IntList emptyIntList() {
/*  399 */     return IntArrayList.emptyList();
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
/*  419 */   protected static final Set<String> loggedPre22TypeNames = new CopyOnWriteArraySet<>();
/*      */   
/*      */   static void warnPre22Gencode(Class<?> messageClass) {
/*  422 */     if (System.getProperty("com.google.protobuf.use_unsafe_pre22_gencode") != null) {
/*      */       return;
/*      */     }
/*  425 */     String messageName = messageClass.getName();
/*  426 */     String vulnerabilityMessage = "Vulnerable protobuf generated type in use: " + messageName + "\n" + "As of 2022/09/29 (release 21.7) makeExtensionsImmutable should not be called from protobuf gencode. If you are seeing this message, your gencode is vulnerable to a denial of service attack. You should regenerate your code using protobuf 25.6 or later. Use the latest version that meets your needs. However, if you understand the risks and wish to continue with vulnerable gencode, you can set the system property `-Dcom.google.protobuf.use_unsafe_pre22_gencode` on the command line to silence this warning. You also can set `-Dcom.google.protobuf.error_on_unsafe_pre22_gencode` to throw an error instead. See security vulnerability: https://github.com/protocolbuffers/protobuf/security/advisories/GHSA-h4h5-3hr4-j3g2";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  432 */     if (System.getProperty("com.google.protobuf.error_on_unsafe_pre22_gencode") != null) {
/*  433 */       throw new UnsupportedOperationException(vulnerabilityMessage);
/*      */     }
/*      */     
/*  436 */     if (!loggedPre22TypeNames.add(messageName)) {
/*      */       return;
/*      */     }
/*      */     
/*  440 */     logger.warning(vulnerabilityMessage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void makeExtensionsImmutable() {
/*  451 */     warnPre22Gencode(getClass());
/*      */   }
/*      */   
/*      */   protected static Internal.LongList emptyLongList() {
/*  455 */     return LongArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.FloatList emptyFloatList() {
/*  459 */     return FloatArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.DoubleList emptyDoubleList() {
/*  463 */     return DoubleArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static Internal.BooleanList emptyBooleanList() {
/*  467 */     return BooleanArrayList.emptyList();
/*      */   }
/*      */   
/*      */   protected static <ListT extends Internal.ProtobufList<?>> ListT makeMutableCopy(ListT list) {
/*  471 */     return makeMutableCopy(list, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <ListT extends Internal.ProtobufList<?>> ListT makeMutableCopy(ListT list, int minCapacity) {
/*  477 */     int size = list.size();
/*  478 */     if (minCapacity <= size) {
/*  479 */       minCapacity = size * 2;
/*      */     }
/*  481 */     if (minCapacity <= 0) {
/*  482 */       minCapacity = 10;
/*      */     }
/*      */     
/*  485 */     return (ListT)list.mutableCopyWithCapacity(minCapacity);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static <T> Internal.ProtobufList<T> emptyList(Class<T> elementType) {
/*  490 */     return ProtobufArrayList.emptyList();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  495 */     MessageReflection.writeMessageTo(this, getAllFieldsRaw(), output, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  500 */     int size = this.memoizedSize;
/*  501 */     if (size != -1) {
/*  502 */       return size;
/*      */     }
/*      */     
/*  505 */     this.memoizedSize = MessageReflection.getSerializedSize(this, 
/*  506 */         getAllFieldsRaw());
/*  507 */     return this.memoizedSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final class UnusedPrivateParameter
/*      */   {
/*  518 */     static final UnusedPrivateParameter INSTANCE = new UnusedPrivateParameter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object newInstance(UnusedPrivateParameter unused) {
/*  526 */     throw new UnsupportedOperationException("This method must be overridden by the subclass.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class Builder<BuilderT extends Builder<BuilderT>>
/*      */     extends AbstractMessage.Builder<BuilderT>
/*      */   {
/*      */     private AbstractMessage.BuilderParent builderParent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private BuilderParentImpl meAsParent;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isClean;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  553 */     private Object unknownFieldsOrBuilder = UnknownFieldSet.getDefaultInstance();
/*      */ 
/*      */ 
/*      */     
/*      */     protected Builder(AbstractMessage.BuilderParent builderParent) {
/*  558 */       this.builderParent = builderParent;
/*      */     }
/*      */ 
/*      */     
/*      */     void dispose() {
/*  563 */       this.builderParent = null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onBuilt() {
/*  568 */       if (this.builderParent != null) {
/*  569 */         markClean();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void markClean() {
/*  579 */       this.isClean = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isClean() {
/*  588 */       return this.isClean;
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderT clone() {
/*  593 */       Builder<Builder> builder = (Builder)getDefaultInstanceForType().newBuilderForType();
/*  594 */       return (BuilderT)builder.mergeFrom(buildPartial());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderT clear() {
/*  603 */       this.unknownFieldsOrBuilder = UnknownFieldSet.getDefaultInstance();
/*  604 */       onChanged();
/*  605 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getDescriptorForType() {
/*  616 */       return (internalGetFieldAccessorTable()).descriptor;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/*  621 */       return Collections.unmodifiableMap(getAllFieldsMutable());
/*      */     }
/*      */ 
/*      */     
/*      */     private Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable() {
/*  626 */       TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<>();
/*  627 */       GeneratedMessage.FieldAccessorTable fieldAccessorTable = internalGetFieldAccessorTable();
/*  628 */       Descriptors.Descriptor descriptor = fieldAccessorTable.descriptor;
/*  629 */       List<Descriptors.FieldDescriptor> fields = descriptor.getFields();
/*      */       
/*  631 */       for (int i = 0; i < fields.size(); i++) {
/*  632 */         Descriptors.FieldDescriptor field = fields.get(i);
/*  633 */         Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  639 */         if (oneofDescriptor != null) {
/*      */           
/*  641 */           i += oneofDescriptor.getFieldCount() - 1;
/*  642 */           if (!hasOneof(oneofDescriptor)) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */           
/*  647 */           field = getOneofFieldDescriptor(oneofDescriptor);
/*      */         } else {
/*      */           
/*  650 */           if (field.isRepeated()) {
/*  651 */             List<?> value = (List)getField(field);
/*  652 */             if (!value.isEmpty()) {
/*  653 */               result.put(field, value);
/*      */             }
/*      */             continue;
/*      */           } 
/*  657 */           if (!hasField(field)) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */         
/*  662 */         result.put(field, getField(field)); continue;
/*      */       } 
/*  664 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     public Message.Builder newBuilderForField(Descriptors.FieldDescriptor field) {
/*  669 */       return internalGetFieldAccessorTable().getField(field).newBuilder();
/*      */     }
/*      */ 
/*      */     
/*      */     public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor field) {
/*  674 */       return internalGetFieldAccessorTable().getField(field).getBuilder(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor field, int index) {
/*  679 */       return internalGetFieldAccessorTable().getField(field).getRepeatedBuilder(this, index);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/*  684 */       return internalGetFieldAccessorTable().getOneof(oneof).has(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/*  689 */       return internalGetFieldAccessorTable().getOneof(oneof).get(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasField(Descriptors.FieldDescriptor field) {
/*  694 */       return internalGetFieldAccessorTable().getField(field).has(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getField(Descriptors.FieldDescriptor field) {
/*  699 */       Object object = internalGetFieldAccessorTable().getField(field).get(this);
/*  700 */       if (field.isRepeated())
/*      */       {
/*      */         
/*  703 */         return Collections.unmodifiableList((List)object);
/*      */       }
/*  705 */       return object;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderT setField(Descriptors.FieldDescriptor field, Object value) {
/*  711 */       internalGetFieldAccessorTable().getField(field).set(this, value);
/*  712 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderT clearField(Descriptors.FieldDescriptor field) {
/*  717 */       internalGetFieldAccessorTable().getField(field).clear(this);
/*  718 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderT clearOneof(Descriptors.OneofDescriptor oneof) {
/*  723 */       internalGetFieldAccessorTable().getOneof(oneof).clear(this);
/*  724 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/*  729 */       return internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/*  734 */       return internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderT setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/*  740 */       internalGetFieldAccessorTable().getField(field).setRepeated(this, index, value);
/*  741 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderT addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/*  746 */       internalGetFieldAccessorTable().getField(field).addRepeated(this, value);
/*  747 */       return (BuilderT)this;
/*      */     }
/*      */     
/*      */     private BuilderT setUnknownFieldsInternal(UnknownFieldSet unknownFields) {
/*  751 */       this.unknownFieldsOrBuilder = unknownFields;
/*  752 */       onChanged();
/*  753 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderT setUnknownFields(UnknownFieldSet unknownFields) {
/*  758 */       return setUnknownFieldsInternal(unknownFields);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected BuilderT setUnknownFieldsProto3(UnknownFieldSet unknownFields) {
/*  765 */       return setUnknownFieldsInternal(unknownFields);
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderT mergeUnknownFields(UnknownFieldSet unknownFields) {
/*  770 */       if (UnknownFieldSet.getDefaultInstance().equals(unknownFields)) {
/*  771 */         return (BuilderT)this;
/*      */       }
/*      */       
/*  774 */       if (UnknownFieldSet.getDefaultInstance().equals(this.unknownFieldsOrBuilder)) {
/*  775 */         this.unknownFieldsOrBuilder = unknownFields;
/*  776 */         onChanged();
/*  777 */         return (BuilderT)this;
/*      */       } 
/*      */       
/*  780 */       getUnknownFieldSetBuilder().mergeFrom(unknownFields);
/*  781 */       onChanged();
/*  782 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isInitialized() {
/*  787 */       for (Descriptors.FieldDescriptor field : getDescriptorForType().getFields()) {
/*      */         
/*  789 */         if (field.isRequired() && 
/*  790 */           !hasField(field)) {
/*  791 */           return false;
/*      */         }
/*      */ 
/*      */         
/*  795 */         if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*  796 */           if (field.isRepeated()) {
/*      */             
/*  798 */             List<Message> messageList = (List<Message>)getField(field);
/*  799 */             for (Message element : messageList) {
/*  800 */               if (!element.isInitialized())
/*  801 */                 return false; 
/*      */             } 
/*      */             continue;
/*      */           } 
/*  805 */           if (hasField(field) && !((Message)getField(field)).isInitialized()) {
/*  806 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  811 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public final UnknownFieldSet getUnknownFields() {
/*  816 */       if (this.unknownFieldsOrBuilder instanceof UnknownFieldSet) {
/*  817 */         return (UnknownFieldSet)this.unknownFieldsOrBuilder;
/*      */       }
/*  819 */       return ((UnknownFieldSet.Builder)this.unknownFieldsOrBuilder).buildPartial();
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
/*      */     protected boolean parseUnknownField(CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
/*  831 */       if (input.shouldDiscardUnknownFields()) {
/*  832 */         return input.skipField(tag);
/*      */       }
/*  834 */       return getUnknownFieldSetBuilder().mergeFieldFrom(tag, input);
/*      */     }
/*      */ 
/*      */     
/*      */     protected final void mergeUnknownLengthDelimitedField(int number, ByteString bytes) {
/*  839 */       getUnknownFieldSetBuilder().mergeLengthDelimitedField(number, bytes);
/*      */     }
/*      */ 
/*      */     
/*      */     protected final void mergeUnknownVarintField(int number, int value) {
/*  844 */       getUnknownFieldSetBuilder().mergeVarintField(number, value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected UnknownFieldSet.Builder getUnknownFieldSetBuilder() {
/*  849 */       if (this.unknownFieldsOrBuilder instanceof UnknownFieldSet) {
/*  850 */         this.unknownFieldsOrBuilder = ((UnknownFieldSet)this.unknownFieldsOrBuilder).toBuilder();
/*      */       }
/*  852 */       onChanged();
/*  853 */       return (UnknownFieldSet.Builder)this.unknownFieldsOrBuilder;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void setUnknownFieldSetBuilder(UnknownFieldSet.Builder builder) {
/*  858 */       this.unknownFieldsOrBuilder = builder;
/*  859 */       onChanged();
/*      */     }
/*      */ 
/*      */     
/*      */     private class BuilderParentImpl
/*      */       implements AbstractMessage.BuilderParent
/*      */     {
/*      */       private BuilderParentImpl() {}
/*      */ 
/*      */       
/*      */       public void markDirty() {
/*  870 */         GeneratedMessage.Builder.this.onChanged();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AbstractMessage.BuilderParent getParentForChildren() {
/*  880 */       if (this.meAsParent == null) {
/*  881 */         this.meAsParent = new BuilderParentImpl();
/*      */       }
/*  883 */       return this.meAsParent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final void onChanged() {
/*  891 */       if (this.isClean && this.builderParent != null) {
/*  892 */         this.builderParent.markDirty();
/*      */ 
/*      */         
/*  895 */         this.isClean = false;
/*      */       } 
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
/*      */     protected MapFieldReflectionAccessor internalGetMapFieldReflection(int fieldNumber) {
/*  910 */       return internalGetMapField(fieldNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     protected MapField internalGetMapField(int fieldNumber) {
/*  919 */       throw new IllegalArgumentException("No map fields found in " + getClass().getName());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapFieldReflectionAccessor internalGetMutableMapFieldReflection(int fieldNumber) {
/*  925 */       return internalGetMutableMapField(fieldNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     protected MapField internalGetMutableMapField(int fieldNumber) {
/*  934 */       throw new IllegalArgumentException("No map fields found in " + getClass().getName());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Builder() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface ExtendableMessageOrBuilder<MessageT extends ExtendableMessage<MessageT>>
/*      */     extends MessageOrBuilder
/*      */   {
/*      */     default <T> boolean hasExtension(Extension<? extends MessageT, T> extension) {
/*  957 */       return hasExtension(extension);
/*      */     }
/*      */ 
/*      */     
/*      */     default <T> boolean hasExtension(GeneratedMessage.GeneratedExtension<? extends MessageT, T> extension) {
/*  962 */       return hasExtension(extension);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     default <T> int getExtensionCount(Extension<? extends MessageT, List<T>> extension) {
/*  970 */       return getExtensionCount(extension);
/*      */     }
/*      */ 
/*      */     
/*      */     default <T> int getExtensionCount(GeneratedMessage.GeneratedExtension<MessageT, List<T>> extension) {
/*  975 */       return getExtensionCount(extension);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     default <T> T getExtension(Extension<? extends MessageT, T> extension) {
/*  983 */       return getExtension(extension);
/*      */     }
/*      */ 
/*      */     
/*      */     default <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, T> extension) {
/*  988 */       return getExtension(extension);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     default <T> T getExtension(Extension<? extends MessageT, List<T>> extension, int index) {
/*  996 */       return getExtension(extension, index);
/*      */     }
/*      */ 
/*      */     
/*      */     default <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> extension, int index) {
/* 1001 */       return getExtension(extension, index);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Message getDefaultInstanceForType();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <T> boolean hasExtension(ExtensionLite<? extends MessageT, T> param1ExtensionLite);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <T> int getExtensionCount(ExtensionLite<? extends MessageT, List<T>> param1ExtensionLite);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <T> T getExtension(ExtensionLite<? extends MessageT, T> param1ExtensionLite);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <T> T getExtension(ExtensionLite<? extends MessageT, List<T>> param1ExtensionLite, int param1Int);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class ExtendableMessage<MessageT extends ExtendableMessage<MessageT>>
/*      */     extends GeneratedMessage
/*      */     implements ExtendableMessageOrBuilder<MessageT>
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */     
/*      */     private final FieldSet<Descriptors.FieldDescriptor> extensions;
/*      */ 
/*      */ 
/*      */     
/*      */     protected ExtendableMessage() {
/* 1045 */       this.extensions = FieldSet.newFieldSet();
/*      */     }
/*      */     
/*      */     protected ExtendableMessage(GeneratedMessage.ExtendableBuilder<MessageT, ?> builder) {
/* 1049 */       super(builder);
/* 1050 */       this.extensions = builder.buildExtensions();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Iterator<FieldEntry> extensionsIterator() {
/* 1058 */       return new FieldEntryIterator(this.extensions);
/*      */     }
/*      */     
/*      */     private void verifyExtensionContainingType(Descriptors.FieldDescriptor descriptor) {
/* 1062 */       if (descriptor.getContainingType() != getDescriptorForType())
/*      */       {
/* 1064 */         throw new IllegalArgumentException("Extension is for type \"" + descriptor
/*      */             
/* 1066 */             .getContainingType().getFullName() + "\" which does not match message type \"" + 
/*      */             
/* 1068 */             getDescriptorForType().getFullName() + "\".");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> boolean hasExtension(ExtensionLite<? extends MessageT, T> extensionLite) {
/* 1077 */       Extension<MessageT, T> extension = GeneratedMessage.checkNotLite(extensionLite);
/*      */       
/* 1079 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1080 */       verifyExtensionContainingType(descriptor);
/* 1081 */       return this.extensions.hasField(descriptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> int getExtensionCount(ExtensionLite<? extends MessageT, List<T>> extensionLite) {
/* 1088 */       Extension<MessageT, List<T>> extension = (Extension)GeneratedMessage.checkNotLite((ExtensionLite)extensionLite);
/*      */       
/* 1090 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1091 */       verifyExtensionContainingType(descriptor);
/* 1092 */       return this.extensions.getRepeatedFieldCount(descriptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> T getExtension(ExtensionLite<? extends MessageT, T> extensionLite) {
/* 1099 */       Extension<MessageT, T> extension = GeneratedMessage.checkNotLite(extensionLite);
/*      */       
/* 1101 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1102 */       verifyExtensionContainingType(descriptor);
/* 1103 */       Object value = this.extensions.getField(descriptor);
/* 1104 */       T result = null;
/* 1105 */       if (value == null) {
/* 1106 */         if (descriptor.isRepeated()) {
/* 1107 */           ProtobufArrayList<?> protobufArrayList = ProtobufArrayList.emptyList();
/* 1108 */         } else if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 1109 */           Message message = extension.getMessageDefaultInstance();
/*      */         } else {
/* 1111 */           result = (T)extension.fromReflectionType(descriptor.getDefaultValue());
/*      */         } 
/*      */       } else {
/* 1114 */         result = (T)extension.fromReflectionType(value);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1120 */       if (this.extensions.lazyFieldCorrupted(descriptor)) {
/* 1121 */         setMemoizedSerializedSize(-1);
/*      */       }
/* 1123 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> T getExtension(ExtensionLite<? extends MessageT, List<T>> extensionLite, int index) {
/* 1131 */       Extension<MessageT, List<T>> extension = (Extension)GeneratedMessage.checkNotLite((ExtensionLite)extensionLite);
/*      */       
/* 1133 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1134 */       verifyExtensionContainingType(descriptor);
/* 1135 */       return (T)extension
/* 1136 */         .singularFromReflectionType(this.extensions.getRepeatedField(descriptor, index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean extensionsAreInitialized() {
/* 1141 */       return this.extensions.isInitialized();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInitialized() {
/* 1147 */       return (super.isInitialized() && extensionsAreInitialized());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void makeExtensionsImmutable() {
/* 1156 */       GeneratedMessage.warnPre22Gencode(getClass());
/* 1157 */       this.extensions.makeImmutable();
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
/*      */     private static final class NoOpExtensionSerializer
/*      */       implements ExtensionSerializer
/*      */     {
/* 1172 */       private static final NoOpExtensionSerializer INSTANCE = new NoOpExtensionSerializer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void writeUntil(int end, CodedOutputStream output) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class ExtensionWriter
/*      */       implements ExtensionSerializer
/*      */     {
/* 1187 */       private final Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> iter = GeneratedMessage.ExtendableMessage.this.extensions.iterator();
/*      */       
/*      */       private Map.Entry<Descriptors.FieldDescriptor, Object> next;
/*      */       private final boolean messageSetWireFormat;
/*      */       
/*      */       protected ExtensionWriter(boolean messageSetWireFormat) {
/* 1193 */         if (this.iter.hasNext()) {
/* 1194 */           this.next = this.iter.next();
/*      */         }
/* 1196 */         this.messageSetWireFormat = messageSetWireFormat;
/*      */       }
/*      */ 
/*      */       
/*      */       public void writeUntil(int end, CodedOutputStream output) throws IOException {
/* 1201 */         while (this.next != null && ((Descriptors.FieldDescriptor)this.next.getKey()).getNumber() < end) {
/* 1202 */           Descriptors.FieldDescriptor descriptor = this.next.getKey();
/* 1203 */           if (this.messageSetWireFormat && descriptor
/* 1204 */             .getLiteJavaType() == WireFormat.JavaType.MESSAGE && 
/* 1205 */             !descriptor.isRepeated()) {
/* 1206 */             if (this.next instanceof LazyField.LazyEntry) {
/* 1207 */               output.writeRawMessageSetExtension(descriptor
/* 1208 */                   .getNumber(), ((LazyField.LazyEntry)this.next)
/* 1209 */                   .getField().toByteString());
/*      */             } else {
/* 1211 */               output.writeMessageSetExtension(descriptor.getNumber(), (Message)this.next.getValue());
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 1221 */             FieldSet.writeField(descriptor, this.next.getValue(), output);
/*      */           } 
/* 1223 */           if (this.iter.hasNext()) {
/* 1224 */             this.next = this.iter.next(); continue;
/*      */           } 
/* 1226 */           this.next = null;
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
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     protected ExtensionWriter newExtensionWriter() {
/* 1241 */       return new ExtensionWriter(false);
/*      */     }
/*      */ 
/*      */     
/*      */     protected ExtensionSerializer newExtensionSerializer() {
/* 1246 */       if (this.extensions.isEmpty()) {
/* 1247 */         return NoOpExtensionSerializer.INSTANCE;
/*      */       }
/* 1249 */       return new ExtensionWriter(false);
/*      */     }
/*      */ 
/*      */     
/*      */     protected ExtensionWriter newMessageSetExtensionWriter() {
/* 1254 */       return new ExtensionWriter(true);
/*      */     }
/*      */ 
/*      */     
/*      */     protected ExtensionSerializer newMessageSetExtensionSerializer() {
/* 1259 */       if (this.extensions.isEmpty()) {
/* 1260 */         return NoOpExtensionSerializer.INSTANCE;
/*      */       }
/* 1262 */       return new ExtensionWriter(true);
/*      */     }
/*      */ 
/*      */     
/*      */     protected int extensionsSerializedSize() {
/* 1267 */       return this.extensions.getSerializedSize();
/*      */     }
/*      */     
/*      */     protected int extensionsSerializedSizeAsMessageSet() {
/* 1271 */       return this.extensions.getMessageSetSerializedSize();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Map<Descriptors.FieldDescriptor, Object> getExtensionFields() {
/* 1278 */       return this.extensions.getAllFields();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/* 1284 */       Map<Descriptors.FieldDescriptor, Object> result = getAllFieldsMutable(false);
/* 1285 */       result.putAll(getExtensionFields());
/* 1286 */       return Collections.unmodifiableMap(result);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
/* 1292 */       Map<Descriptors.FieldDescriptor, Object> result = getAllFieldsMutable(false);
/* 1293 */       result.putAll(getExtensionFields());
/* 1294 */       return Collections.unmodifiableMap(result);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasField(Descriptors.FieldDescriptor field) {
/* 1299 */       if (field.isExtension()) {
/* 1300 */         verifyContainingType(field);
/* 1301 */         return this.extensions.hasField(field);
/*      */       } 
/* 1303 */       return super.hasField(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getField(Descriptors.FieldDescriptor field) {
/* 1309 */       if (field.isExtension()) {
/* 1310 */         verifyContainingType(field);
/* 1311 */         Object value = this.extensions.getField(field);
/* 1312 */         if (value == null) {
/* 1313 */           if (field.isRepeated())
/* 1314 */             return Collections.emptyList(); 
/* 1315 */           if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE)
/*      */           {
/*      */             
/* 1318 */             return DynamicMessage.getDefaultInstance(field.getMessageType());
/*      */           }
/* 1320 */           return field.getDefaultValue();
/*      */         } 
/*      */         
/* 1323 */         return value;
/*      */       } 
/*      */       
/* 1326 */       return super.getField(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/* 1332 */       if (field.isExtension()) {
/* 1333 */         verifyContainingType(field);
/* 1334 */         return this.extensions.getRepeatedFieldCount(field);
/*      */       } 
/* 1336 */       return super.getRepeatedFieldCount(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/* 1342 */       if (field.isExtension()) {
/* 1343 */         verifyContainingType(field);
/* 1344 */         return this.extensions.getRepeatedField(field, index);
/*      */       } 
/* 1346 */       return super.getRepeatedField(field, index);
/*      */     }
/*      */ 
/*      */     
/*      */     private void verifyContainingType(Descriptors.FieldDescriptor field) {
/* 1351 */       if (field.getContainingType() != getDescriptorForType()) {
/* 1352 */         throw new IllegalArgumentException("FieldDescriptor does not match message type.");
/*      */       }
/*      */     }
/*      */     
/*      */     public static final class FieldEntry
/*      */     {
/*      */       private final Descriptors.FieldDescriptor descriptor;
/*      */       private final Object value;
/*      */       
/*      */       public Descriptors.FieldDescriptor getDescriptor() {
/* 1362 */         return this.descriptor;
/*      */       }
/*      */       
/*      */       public Object getValue() {
/* 1366 */         return this.value;
/*      */       }
/*      */       
/*      */       FieldEntry(Descriptors.FieldDescriptor descriptor, Object value) {
/* 1370 */         this.descriptor = descriptor;
/* 1371 */         this.value = value;
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class FieldEntryIterator implements Iterator<FieldEntry> {
/*      */       private final Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> iter;
/*      */       
/*      */       FieldEntryIterator(FieldSet<Descriptors.FieldDescriptor> fieldSet) {
/* 1379 */         this.iter = fieldSet.iterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public final boolean hasNext() {
/* 1384 */         return this.iter.hasNext();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public final GeneratedMessage.ExtendableMessage.FieldEntry next() {
/* 1390 */         Map.Entry<Descriptors.FieldDescriptor, Object> entry = this.iter.next();
/* 1391 */         return new GeneratedMessage.ExtendableMessage.FieldEntry(entry.getKey(), entry.getValue());
/*      */       }
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
/*      */     protected static interface ExtensionSerializer
/*      */     {
/*      */       void writeUntil(int param2Int, CodedOutputStream param2CodedOutputStream) throws IOException;
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
/*      */   public static abstract class ExtendableBuilder<MessageT extends ExtendableMessage<MessageT>, BuilderT extends ExtendableBuilder<MessageT, BuilderT>>
/*      */     extends Builder<BuilderT>
/*      */     implements ExtendableMessageOrBuilder<MessageT>
/*      */   {
/*      */     private FieldSet.Builder<Descriptors.FieldDescriptor> extensions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected ExtendableBuilder() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected ExtendableBuilder(AbstractMessage.BuilderParent parent) {
/* 1441 */       super(parent);
/*      */     }
/*      */ 
/*      */     
/*      */     void internalSetExtensionSet(FieldSet<Descriptors.FieldDescriptor> extensions) {
/* 1446 */       this.extensions = FieldSet.Builder.fromFieldSet(extensions);
/*      */     }
/*      */ 
/*      */     
/*      */     public BuilderT clear() {
/* 1451 */       this.extensions = null;
/* 1452 */       return super.clear();
/*      */     }
/*      */     
/*      */     private void ensureExtensionsIsMutable() {
/* 1456 */       if (this.extensions == null) {
/* 1457 */         this.extensions = FieldSet.newBuilder();
/*      */       }
/*      */     }
/*      */     
/*      */     private void verifyExtensionContainingType(Extension<MessageT, ?> extension) {
/* 1462 */       if (extension.getDescriptor().getContainingType() != getDescriptorForType())
/*      */       {
/* 1464 */         throw new IllegalArgumentException("Extension is for type \"" + extension
/*      */             
/* 1466 */             .getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + 
/*      */             
/* 1468 */             getDescriptorForType().getFullName() + "\".");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> boolean hasExtension(ExtensionLite<? extends MessageT, T> extensionLite) {
/* 1477 */       Extension<MessageT, T> extension = GeneratedMessage.checkNotLite(extensionLite);
/*      */       
/* 1479 */       verifyExtensionContainingType(extension);
/* 1480 */       return (this.extensions != null && this.extensions.hasField(extension.getDescriptor()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> int getExtensionCount(ExtensionLite<? extends MessageT, List<T>> extensionLite) {
/* 1487 */       Extension<MessageT, List<T>> extension = (Extension)GeneratedMessage.checkNotLite((ExtensionLite)extensionLite);
/*      */       
/* 1489 */       verifyExtensionContainingType(extension);
/* 1490 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1491 */       return (this.extensions == null) ? 0 : this.extensions.getRepeatedFieldCount(descriptor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> T getExtension(ExtensionLite<? extends MessageT, T> extensionLite) {
/* 1497 */       Extension<MessageT, T> extension = GeneratedMessage.checkNotLite(extensionLite);
/*      */       
/* 1499 */       verifyExtensionContainingType(extension);
/* 1500 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1501 */       Object value = (this.extensions == null) ? null : this.extensions.getField(descriptor);
/* 1502 */       if (value == null) {
/* 1503 */         if (descriptor.isRepeated())
/* 1504 */           return (T)Collections.emptyList(); 
/* 1505 */         if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 1506 */           return (T)extension.getMessageDefaultInstance();
/*      */         }
/* 1508 */         return (T)extension.fromReflectionType(descriptor.getDefaultValue());
/*      */       } 
/*      */       
/* 1511 */       return (T)extension.fromReflectionType(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> T getExtension(ExtensionLite<? extends MessageT, List<T>> extensionLite, int index) {
/* 1519 */       Extension<MessageT, List<T>> extension = (Extension)GeneratedMessage.checkNotLite((ExtensionLite)extensionLite);
/*      */       
/* 1521 */       verifyExtensionContainingType(extension);
/* 1522 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1523 */       if (this.extensions == null) {
/* 1524 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 1526 */       return (T)extension
/* 1527 */         .singularFromReflectionType(this.extensions.getRepeatedField(descriptor, index));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> BuilderT setExtension(Extension<? extends MessageT, T> extension, T value) {
/* 1533 */       return setExtension(extension, value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> BuilderT setExtension(ExtensionLite<? extends MessageT, T> extensionLite, T value) {
/* 1539 */       Extension<MessageT, T> extension = GeneratedMessage.checkNotLite(extensionLite);
/*      */       
/* 1541 */       verifyExtensionContainingType(extension);
/* 1542 */       ensureExtensionsIsMutable();
/* 1543 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1544 */       this.extensions.setField(descriptor, extension.toReflectionType(value));
/* 1545 */       onChanged();
/* 1546 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> BuilderT setExtension(Extension<? extends MessageT, List<T>> extension, int index, T value) {
/* 1555 */       return setExtension(extension, index, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> BuilderT setExtension(ExtensionLite<? extends MessageT, List<T>> extensionLite, int index, T value) {
/* 1563 */       Extension<MessageT, List<T>> extension = (Extension)GeneratedMessage.checkNotLite((ExtensionLite)extensionLite);
/*      */       
/* 1565 */       verifyExtensionContainingType(extension);
/* 1566 */       ensureExtensionsIsMutable();
/* 1567 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1568 */       this.extensions.setRepeatedField(descriptor, index, extension.singularToReflectionType(value));
/* 1569 */       onChanged();
/* 1570 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> BuilderT addExtension(Extension<? extends MessageT, List<T>> extension, T value) {
/* 1576 */       return addExtension(extension, value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> BuilderT addExtension(ExtensionLite<? extends MessageT, List<T>> extensionLite, T value) {
/* 1582 */       Extension<MessageT, List<T>> extension = (Extension)GeneratedMessage.checkNotLite((ExtensionLite)extensionLite);
/*      */       
/* 1584 */       verifyExtensionContainingType(extension);
/* 1585 */       ensureExtensionsIsMutable();
/* 1586 */       Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
/* 1587 */       this.extensions.addRepeatedField(descriptor, extension.singularToReflectionType(value));
/* 1588 */       onChanged();
/* 1589 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     public final <T> BuilderT clearExtension(Extension<? extends MessageT, T> extension) {
/* 1594 */       return clearExtension(extension);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final <T> BuilderT clearExtension(ExtensionLite<? extends MessageT, T> extensionLite) {
/* 1600 */       Extension<MessageT, T> extension = GeneratedMessage.checkNotLite(extensionLite);
/*      */       
/* 1602 */       verifyExtensionContainingType(extension);
/* 1603 */       ensureExtensionsIsMutable();
/* 1604 */       this.extensions.clearField(extension.getDescriptor());
/* 1605 */       onChanged();
/* 1606 */       return (BuilderT)this;
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean extensionsAreInitialized() {
/* 1611 */       return (this.extensions == null || this.extensions.isInitialized());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FieldSet<Descriptors.FieldDescriptor> buildExtensions() {
/* 1618 */       return (this.extensions == null) ? 
/* 1619 */         FieldSet.<Descriptors.FieldDescriptor>emptySet() : 
/* 1620 */         this.extensions.buildPartial();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isInitialized() {
/* 1625 */       return (super.isInitialized() && extensionsAreInitialized());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/* 1633 */       Map<Descriptors.FieldDescriptor, Object> result = getAllFieldsMutable();
/* 1634 */       if (this.extensions != null) {
/* 1635 */         result.putAll(this.extensions.getAllFields());
/*      */       }
/* 1637 */       return Collections.unmodifiableMap(result);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getField(Descriptors.FieldDescriptor field) {
/* 1642 */       if (field.isExtension()) {
/* 1643 */         verifyContainingType(field);
/* 1644 */         Object value = (this.extensions == null) ? null : this.extensions.getField(field);
/* 1645 */         if (value == null) {
/* 1646 */           if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE)
/*      */           {
/*      */             
/* 1649 */             return DynamicMessage.getDefaultInstance(field.getMessageType());
/*      */           }
/* 1651 */           return field.getDefaultValue();
/*      */         } 
/*      */         
/* 1654 */         return value;
/*      */       } 
/*      */       
/* 1657 */       return super.getField(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor field) {
/* 1663 */       if (field.isExtension()) {
/* 1664 */         verifyContainingType(field);
/* 1665 */         if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 1666 */           throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
/*      */         }
/*      */         
/* 1669 */         ensureExtensionsIsMutable();
/* 1670 */         Object value = this.extensions.getFieldAllowBuilders(field);
/* 1671 */         if (value == null) {
/* 1672 */           Message.Builder builder = DynamicMessage.newBuilder(field.getMessageType());
/* 1673 */           this.extensions.setField(field, builder);
/* 1674 */           onChanged();
/* 1675 */           return builder;
/*      */         } 
/* 1677 */         if (value instanceof Message.Builder)
/* 1678 */           return (Message.Builder)value; 
/* 1679 */         if (value instanceof Message) {
/* 1680 */           Message.Builder builder = ((Message)value).toBuilder();
/* 1681 */           this.extensions.setField(field, builder);
/* 1682 */           onChanged();
/* 1683 */           return builder;
/*      */         } 
/* 1685 */         throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1690 */       return super.getFieldBuilder(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/* 1696 */       if (field.isExtension()) {
/* 1697 */         verifyContainingType(field);
/* 1698 */         return (this.extensions == null) ? 0 : this.extensions.getRepeatedFieldCount(field);
/*      */       } 
/* 1700 */       return super.getRepeatedFieldCount(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/* 1706 */       if (field.isExtension()) {
/* 1707 */         verifyContainingType(field);
/* 1708 */         if (this.extensions == null) {
/* 1709 */           throw new IndexOutOfBoundsException();
/*      */         }
/* 1711 */         return this.extensions.getRepeatedField(field, index);
/*      */       } 
/* 1713 */       return super.getRepeatedField(field, index);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor field, int index) {
/* 1719 */       if (field.isExtension()) {
/* 1720 */         verifyContainingType(field);
/* 1721 */         ensureExtensionsIsMutable();
/* 1722 */         if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 1723 */           throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
/*      */         }
/*      */         
/* 1726 */         Object value = this.extensions.getRepeatedFieldAllowBuilders(field, index);
/* 1727 */         if (value instanceof Message.Builder)
/* 1728 */           return (Message.Builder)value; 
/* 1729 */         if (value instanceof Message) {
/* 1730 */           Message.Builder builder = ((Message)value).toBuilder();
/* 1731 */           this.extensions.setRepeatedField(field, index, builder);
/* 1732 */           onChanged();
/* 1733 */           return builder;
/*      */         } 
/* 1735 */         throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
/*      */       } 
/*      */ 
/*      */       
/* 1739 */       return super.getRepeatedFieldBuilder(field, index);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasField(Descriptors.FieldDescriptor field) {
/* 1745 */       if (field.isExtension()) {
/* 1746 */         verifyContainingType(field);
/* 1747 */         return (this.extensions != null && this.extensions.hasField(field));
/*      */       } 
/* 1749 */       return super.hasField(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderT setField(Descriptors.FieldDescriptor field, Object value) {
/* 1755 */       if (field.isExtension()) {
/* 1756 */         verifyContainingType(field);
/* 1757 */         ensureExtensionsIsMutable();
/* 1758 */         this.extensions.setField(field, value);
/* 1759 */         onChanged();
/* 1760 */         return (BuilderT)this;
/*      */       } 
/* 1762 */       return super.setField(field, value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderT clearField(Descriptors.FieldDescriptor field) {
/* 1768 */       if (field.isExtension()) {
/* 1769 */         verifyContainingType(field);
/* 1770 */         ensureExtensionsIsMutable();
/* 1771 */         this.extensions.clearField(field);
/* 1772 */         onChanged();
/* 1773 */         return (BuilderT)this;
/*      */       } 
/* 1775 */       return super.clearField(field);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderT setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/* 1782 */       if (field.isExtension()) {
/* 1783 */         verifyContainingType(field);
/* 1784 */         ensureExtensionsIsMutable();
/* 1785 */         this.extensions.setRepeatedField(field, index, value);
/* 1786 */         onChanged();
/* 1787 */         return (BuilderT)this;
/*      */       } 
/* 1789 */       return super.setRepeatedField(field, index, value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BuilderT addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/* 1795 */       if (field.isExtension()) {
/* 1796 */         verifyContainingType(field);
/* 1797 */         ensureExtensionsIsMutable();
/* 1798 */         this.extensions.addRepeatedField(field, value);
/* 1799 */         onChanged();
/* 1800 */         return (BuilderT)this;
/*      */       } 
/* 1802 */       return super.addRepeatedField(field, value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Message.Builder newBuilderForField(Descriptors.FieldDescriptor field) {
/* 1808 */       if (field.isExtension()) {
/* 1809 */         return DynamicMessage.newBuilder(field.getMessageType());
/*      */       }
/* 1811 */       return super.newBuilderForField(field);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void mergeExtensionFields(GeneratedMessage.ExtendableMessage<?> other) {
/* 1817 */       if (other.extensions != null) {
/* 1818 */         ensureExtensionsIsMutable();
/* 1819 */         this.extensions.mergeFrom(other.extensions);
/* 1820 */         onChanged();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean parseUnknownField(CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
/* 1828 */       ensureExtensionsIsMutable();
/* 1829 */       return MessageReflection.mergeFieldFrom(input, 
/*      */           
/* 1831 */           input.shouldDiscardUnknownFields() ? null : getUnknownFieldSetBuilder(), extensionRegistry, 
/*      */           
/* 1833 */           getDescriptorForType(), new MessageReflection.ExtensionBuilderAdapter(this.extensions), tag);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void verifyContainingType(Descriptors.FieldDescriptor field) {
/* 1839 */       if (field.getContainingType() != getDescriptorForType()) {
/* 1840 */         throw new IllegalArgumentException("FieldDescriptor does not match message type.");
/*      */       }
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
/*      */   public static <ContainingT extends Message, T> GeneratedExtension<ContainingT, T> newMessageScopedGeneratedExtension(final Message scope, final int descriptorIndex, Class<?> singularType, Message defaultInstance) {
/* 1865 */     return new GeneratedExtension<>(new CachedDescriptorRetriever()
/*      */         {
/*      */           public Descriptors.FieldDescriptor loadDescriptor()
/*      */           {
/* 1869 */             return scope.getDescriptorForType().getExtension(descriptorIndex);
/*      */           }
/*      */         },  singularType, defaultInstance, Extension.ExtensionType.IMMUTABLE);
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
/*      */   public static <ContainingT extends Message, T> GeneratedExtension<ContainingT, T> newFileScopedGeneratedExtension(Class<?> singularType, Message defaultInstance) {
/* 1884 */     return new GeneratedExtension<>(null, singularType, defaultInstance, Extension.ExtensionType.IMMUTABLE);
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class CachedDescriptorRetriever
/*      */     implements ExtensionDescriptorRetriever
/*      */   {
/*      */     private volatile Descriptors.FieldDescriptor descriptor;
/*      */ 
/*      */     
/*      */     private CachedDescriptorRetriever() {}
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor getDescriptor() {
/* 1898 */       if (this.descriptor == null) {
/* 1899 */         Descriptors.FieldDescriptor tmpDescriptor = loadDescriptor();
/* 1900 */         synchronized (this) {
/* 1901 */           if (this.descriptor == null) {
/* 1902 */             this.descriptor = tmpDescriptor;
/*      */           }
/*      */         } 
/*      */       } 
/* 1906 */       return this.descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract Descriptors.FieldDescriptor loadDescriptor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class GeneratedExtension<ContainingT extends Message, T>
/*      */     extends Extension<ContainingT, T>
/*      */   {
/*      */     private GeneratedMessage.ExtensionDescriptorRetriever descriptorRetriever;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Class<?> singularType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Message messageDefaultInstance;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Method enumValueOf;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Method enumGetValueDescriptor;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Extension.ExtensionType extensionType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     GeneratedExtension(GeneratedMessage.ExtensionDescriptorRetriever descriptorRetriever, Class<?> singularType, Message messageDefaultInstance, Extension.ExtensionType extensionType) {
/* 1954 */       if (Message.class.isAssignableFrom(singularType) && 
/* 1955 */         !singularType.isInstance(messageDefaultInstance)) {
/* 1956 */         throw new IllegalArgumentException("Bad messageDefaultInstance for " + singularType
/* 1957 */             .getName());
/*      */       }
/* 1959 */       this.descriptorRetriever = descriptorRetriever;
/* 1960 */       this.singularType = singularType;
/* 1961 */       this.messageDefaultInstance = messageDefaultInstance;
/*      */       
/* 1963 */       if (ProtocolMessageEnum.class.isAssignableFrom(singularType)) {
/* 1964 */         this.enumValueOf = GeneratedMessage.getMethodOrDie(singularType, "valueOf", new Class[] { Descriptors.EnumValueDescriptor.class });
/* 1965 */         this.enumGetValueDescriptor = GeneratedMessage.getMethodOrDie(singularType, "getValueDescriptor", new Class[0]);
/*      */       } else {
/* 1967 */         this.enumValueOf = null;
/* 1968 */         this.enumGetValueDescriptor = null;
/*      */       } 
/* 1970 */       this.extensionType = extensionType;
/*      */     }
/*      */ 
/*      */     
/*      */     public void internalInit(final Descriptors.FieldDescriptor descriptor) {
/* 1975 */       if (this.descriptorRetriever != null) {
/* 1976 */         throw new IllegalStateException("Already initialized.");
/*      */       }
/* 1978 */       this.descriptorRetriever = new GeneratedMessage.ExtensionDescriptorRetriever()
/*      */         {
/*      */           public Descriptors.FieldDescriptor getDescriptor()
/*      */           {
/* 1982 */             return descriptor;
/*      */           }
/*      */         };
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
/*      */     public Descriptors.FieldDescriptor getDescriptor() {
/* 1996 */       if (this.descriptorRetriever == null) {
/* 1997 */         throw new IllegalStateException("getDescriptor() called before internalInit()");
/*      */       }
/* 1999 */       return this.descriptorRetriever.getDescriptor();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Message getMessageDefaultInstance() {
/* 2008 */       return this.messageDefaultInstance;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Extension.ExtensionType getExtensionType() {
/* 2013 */       return this.extensionType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object fromReflectionType(Object value) {
/* 2023 */       Descriptors.FieldDescriptor descriptor = getDescriptor();
/* 2024 */       if (descriptor.isRepeated()) {
/* 2025 */         if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE || descriptor
/* 2026 */           .getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
/*      */           
/* 2028 */           ProtobufArrayList<Object> result = new ProtobufArrayList();
/* 2029 */           result.ensureCapacity(((List)value).size());
/* 2030 */           for (Object element : value) {
/* 2031 */             result.add(singularFromReflectionType(element));
/*      */           }
/* 2033 */           result.makeImmutable();
/* 2034 */           return result;
/*      */         } 
/* 2036 */         return value;
/*      */       } 
/*      */       
/* 2039 */       return singularFromReflectionType(value);
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
/*      */     protected Object singularFromReflectionType(Object value) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: invokevirtual getDescriptor : ()Lcom/google/protobuf/Descriptors$FieldDescriptor;
/*      */       //   4: astore_2
/*      */       //   5: getstatic com/google/protobuf/GeneratedMessage$2.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType : [I
/*      */       //   8: aload_2
/*      */       //   9: invokevirtual getJavaType : ()Lcom/google/protobuf/Descriptors$FieldDescriptor$JavaType;
/*      */       //   12: invokevirtual ordinal : ()I
/*      */       //   15: iaload
/*      */       //   16: lookupswitch default -> 98, 1 -> 44, 2 -> 81
/*      */       //   44: aload_0
/*      */       //   45: getfield singularType : Ljava/lang/Class;
/*      */       //   48: aload_1
/*      */       //   49: invokevirtual isInstance : (Ljava/lang/Object;)Z
/*      */       //   52: ifeq -> 57
/*      */       //   55: aload_1
/*      */       //   56: areturn
/*      */       //   57: aload_0
/*      */       //   58: getfield messageDefaultInstance : Lcom/google/protobuf/Message;
/*      */       //   61: invokeinterface newBuilderForType : ()Lcom/google/protobuf/Message$Builder;
/*      */       //   66: aload_1
/*      */       //   67: checkcast com/google/protobuf/Message
/*      */       //   70: invokeinterface mergeFrom : (Lcom/google/protobuf/Message;)Lcom/google/protobuf/Message$Builder;
/*      */       //   75: invokeinterface build : ()Lcom/google/protobuf/Message;
/*      */       //   80: areturn
/*      */       //   81: aload_0
/*      */       //   82: getfield enumValueOf : Ljava/lang/reflect/Method;
/*      */       //   85: aconst_null
/*      */       //   86: iconst_1
/*      */       //   87: anewarray java/lang/Object
/*      */       //   90: dup
/*      */       //   91: iconst_0
/*      */       //   92: aload_1
/*      */       //   93: aastore
/*      */       //   94: invokestatic access$1200 : (Ljava/lang/reflect/Method;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */       //   97: areturn
/*      */       //   98: aload_1
/*      */       //   99: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2049	-> 0
/*      */       //   #2050	-> 5
/*      */       //   #2052	-> 44
/*      */       //   #2053	-> 55
/*      */       //   #2055	-> 57
/*      */       //   #2058	-> 81
/*      */       //   #2060	-> 98
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	100	0	this	Lcom/google/protobuf/GeneratedMessage$GeneratedExtension;
/*      */       //   0	100	1	value	Ljava/lang/Object;
/*      */       //   5	95	2	descriptor	Lcom/google/protobuf/Descriptors$FieldDescriptor;
/*      */       // Local variable type table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	100	0	this	Lcom/google/protobuf/GeneratedMessage$GeneratedExtension<TContainingT;TT;>;
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
/*      */     protected Object toReflectionType(Object value) {
/* 2071 */       Descriptors.FieldDescriptor descriptor = getDescriptor();
/* 2072 */       if (descriptor.isRepeated()) {
/* 2073 */         if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
/*      */           
/* 2075 */           List<Object> result = new ArrayList();
/* 2076 */           for (Object element : value) {
/* 2077 */             result.add(singularToReflectionType(element));
/*      */           }
/* 2079 */           return result;
/*      */         } 
/* 2081 */         return value;
/*      */       } 
/*      */       
/* 2084 */       return singularToReflectionType(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object singularToReflectionType(Object value) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: invokevirtual getDescriptor : ()Lcom/google/protobuf/Descriptors$FieldDescriptor;
/*      */       //   4: astore_2
/*      */       //   5: getstatic com/google/protobuf/GeneratedMessage$2.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType : [I
/*      */       //   8: aload_2
/*      */       //   9: invokevirtual getJavaType : ()Lcom/google/protobuf/Descriptors$FieldDescriptor$JavaType;
/*      */       //   12: invokevirtual ordinal : ()I
/*      */       //   15: iaload
/*      */       //   16: lookupswitch default -> 49, 2 -> 36
/*      */       //   36: aload_0
/*      */       //   37: getfield enumGetValueDescriptor : Ljava/lang/reflect/Method;
/*      */       //   40: aload_1
/*      */       //   41: iconst_0
/*      */       //   42: anewarray java/lang/Object
/*      */       //   45: invokestatic access$1200 : (Ljava/lang/reflect/Method;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */       //   48: areturn
/*      */       //   49: aload_1
/*      */       //   50: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2094	-> 0
/*      */       //   #2095	-> 5
/*      */       //   #2097	-> 36
/*      */       //   #2099	-> 49
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	51	0	this	Lcom/google/protobuf/GeneratedMessage$GeneratedExtension;
/*      */       //   0	51	1	value	Ljava/lang/Object;
/*      */       //   5	46	2	descriptor	Lcom/google/protobuf/Descriptors$FieldDescriptor;
/*      */       // Local variable type table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	51	0	this	Lcom/google/protobuf/GeneratedMessage$GeneratedExtension<TContainingT;TT;>;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getNumber() {
/* 2105 */       return getDescriptor().getNumber();
/*      */     }
/*      */ 
/*      */     
/*      */     public WireFormat.FieldType getLiteType() {
/* 2110 */       return getDescriptor().getLiteType();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRepeated() {
/* 2115 */       return getDescriptor().isRepeated();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public T getDefaultValue() {
/* 2121 */       if (isRepeated()) {
/* 2122 */         return (T)Collections.emptyList();
/*      */       }
/* 2124 */       if (getDescriptor().getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 2125 */         return (T)this.messageDefaultInstance;
/*      */       }
/* 2127 */       return (T)singularFromReflectionType(getDescriptor().getDefaultValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method getMethodOrDie(Class<?> clazz, String name, Class<?>... params) {
/*      */     try {
/* 2137 */       return clazz.getMethod(name, params);
/* 2138 */     } catch (NoSuchMethodException e) {
/* 2139 */       throw new IllegalStateException("Generated message class \"" + clazz
/* 2140 */           .getName() + "\" missing method \"" + name + "\".", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CanIgnoreReturnValue
/*      */   private static Object invokeOrDie(Method method, Object object, Object... params) {
/*      */     try {
/* 2150 */       return method.invoke(object, params);
/* 2151 */     } catch (IllegalAccessException e) {
/* 2152 */       throw new IllegalStateException("Couldn't use Java reflection to implement protocol message reflection.", e);
/*      */     }
/* 2154 */     catch (InvocationTargetException e) {
/* 2155 */       Throwable cause = e.getCause();
/* 2156 */       if (cause instanceof RuntimeException)
/* 2157 */         throw (RuntimeException)cause; 
/* 2158 */       if (cause instanceof Error) {
/* 2159 */         throw (Error)cause;
/*      */       }
/* 2161 */       throw new IllegalStateException("Unexpected exception thrown by generated accessor method.", cause);
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
/*      */   protected MapFieldReflectionAccessor internalGetMapFieldReflection(int fieldNumber) {
/* 2178 */     return internalGetMapField(fieldNumber);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected MapField internalGetMapField(int fieldNumber) {
/* 2187 */     throw new IllegalArgumentException("No map fields found in " + getClass().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FieldAccessorTable
/*      */   {
/*      */     private final Descriptors.Descriptor descriptor;
/*      */ 
/*      */     
/*      */     private final FieldAccessor[] fields;
/*      */ 
/*      */     
/*      */     private String[] camelCaseNames;
/*      */ 
/*      */     
/*      */     private final OneofAccessor[] oneofs;
/*      */ 
/*      */     
/*      */     private volatile boolean initialized;
/*      */ 
/*      */ 
/*      */     
/*      */     public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] camelCaseNames, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 2212 */       this(descriptor, camelCaseNames);
/* 2213 */       ensureFieldAccessorsInitialized(messageClass, builderClass);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] camelCaseNames) {
/* 2221 */       this.descriptor = descriptor;
/* 2222 */       this.camelCaseNames = camelCaseNames;
/* 2223 */       this.fields = new FieldAccessor[descriptor.getFieldCount()];
/* 2224 */       this.oneofs = new OneofAccessor[descriptor.getOneofCount()];
/* 2225 */       this.initialized = false;
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
/*      */     @CanIgnoreReturnValue
/*      */     public FieldAccessorTable ensureFieldAccessorsInitialized(Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 2238 */       if (this.initialized) {
/* 2239 */         return this;
/*      */       }
/* 2241 */       synchronized (this) {
/* 2242 */         if (this.initialized) {
/* 2243 */           return this;
/*      */         }
/* 2245 */         int fieldsSize = this.fields.length; int i;
/* 2246 */         for (i = 0; i < fieldsSize; i++) {
/* 2247 */           Descriptors.FieldDescriptor field = this.descriptor.getField(i);
/* 2248 */           String containingOneofCamelCaseName = null;
/* 2249 */           if (field.getContainingOneof() != null) {
/* 2250 */             int index = fieldsSize + field.getContainingOneof().getIndex();
/* 2251 */             if (index < this.camelCaseNames.length) {
/* 2252 */               containingOneofCamelCaseName = this.camelCaseNames[index];
/*      */             }
/*      */           } 
/* 2255 */           if (field.isRepeated()) {
/* 2256 */             if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 2257 */               if (field.isMapField()) {
/* 2258 */                 this.fields[i] = new MapFieldAccessor(field, messageClass);
/*      */               } else {
/* 2260 */                 this.fields[i] = new RepeatedMessageFieldAccessor(this.camelCaseNames[i], messageClass, builderClass);
/*      */               }
/*      */             
/* 2263 */             } else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
/* 2264 */               this.fields[i] = new RepeatedEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
/*      */             }
/*      */             else {
/*      */               
/* 2268 */               this.fields[i] = new RepeatedFieldAccessor(this.camelCaseNames[i], messageClass, builderClass);
/*      */             }
/*      */           
/* 2271 */           } else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 2272 */             this.fields[i] = new SingularMessageFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 2279 */           else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
/* 2280 */             this.fields[i] = new SingularEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 2287 */           else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING) {
/* 2288 */             this.fields[i] = new SingularStringFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/* 2296 */             this.fields[i] = new SingularFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2307 */         for (i = 0; i < this.descriptor.getOneofCount(); i++) {
/* 2308 */           if (i < this.descriptor.getRealOneofCount()) {
/* 2309 */             this.oneofs[i] = new RealOneofAccessor(this.descriptor, this.camelCaseNames[i + fieldsSize], messageClass, builderClass);
/*      */           }
/*      */           else {
/*      */             
/* 2313 */             this.oneofs[i] = new SyntheticOneofAccessor(this.descriptor, i);
/*      */           } 
/*      */         } 
/* 2316 */         this.initialized = true;
/* 2317 */         this.camelCaseNames = null;
/* 2318 */         return this;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FieldAccessor getField(Descriptors.FieldDescriptor field) {
/* 2330 */       if (field.getContainingType() != this.descriptor)
/* 2331 */         throw new IllegalArgumentException("FieldDescriptor does not match message type."); 
/* 2332 */       if (field.isExtension())
/*      */       {
/*      */         
/* 2335 */         throw new IllegalArgumentException("This type does not have extensions.");
/*      */       }
/* 2337 */       return this.fields[field.getIndex()];
/*      */     }
/*      */ 
/*      */     
/*      */     private OneofAccessor getOneof(Descriptors.OneofDescriptor oneof) {
/* 2342 */       if (oneof.getContainingType() != this.descriptor) {
/* 2343 */         throw new IllegalArgumentException("OneofDescriptor does not match message type.");
/*      */       }
/* 2345 */       return this.oneofs[oneof.getIndex()];
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
/*      */     private static class RealOneofAccessor
/*      */       implements OneofAccessor
/*      */     {
/*      */       private final Descriptors.Descriptor descriptor;
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
/*      */       private final Method caseMethod;
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
/*      */       private final Method caseMethodBuilder;
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
/*      */       private final Method clearMethod;
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
/*      */       RealOneofAccessor(Descriptors.Descriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 2406 */         this.descriptor = descriptor;
/* 2407 */         this.caseMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Case", new Class[0]);
/* 2408 */         this.caseMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Case", new Class[0]);
/* 2409 */         this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage message) {
/* 2419 */         return (((Internal.EnumLite)GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber() != 0);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage.Builder<?> builder) {
/* 2424 */         return (((Internal.EnumLite)GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber() != 0);
/*      */       }
/*      */ 
/*      */       
/*      */       public Descriptors.FieldDescriptor get(GeneratedMessage message) {
/* 2429 */         int fieldNumber = ((Internal.EnumLite)GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
/* 2430 */         if (fieldNumber > 0) {
/* 2431 */           return this.descriptor.findFieldByNumber(fieldNumber);
/*      */         }
/* 2433 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Descriptors.FieldDescriptor get(GeneratedMessage.Builder<?> builder) {
/* 2438 */         int fieldNumber = ((Internal.EnumLite)GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
/* 2439 */         if (fieldNumber > 0) {
/* 2440 */           return this.descriptor.findFieldByNumber(fieldNumber);
/*      */         }
/* 2442 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void clear(GeneratedMessage.Builder<?> builder) {
/* 2448 */         Object unused = GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
/*      */       } }
/*      */     
/*      */     private static class SyntheticOneofAccessor implements OneofAccessor {
/*      */       private final Descriptors.FieldDescriptor fieldDescriptor;
/*      */       
/*      */       SyntheticOneofAccessor(Descriptors.Descriptor descriptor, int oneofIndex) {
/* 2455 */         Descriptors.OneofDescriptor oneofDescriptor = descriptor.getOneof(oneofIndex);
/* 2456 */         this.fieldDescriptor = oneofDescriptor.getField(0);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage message) {
/* 2463 */         return message.hasField(this.fieldDescriptor);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage.Builder<?> builder) {
/* 2468 */         return builder.hasField(this.fieldDescriptor);
/*      */       }
/*      */ 
/*      */       
/*      */       public Descriptors.FieldDescriptor get(GeneratedMessage message) {
/* 2473 */         return message.hasField(this.fieldDescriptor) ? this.fieldDescriptor : null;
/*      */       }
/*      */       
/*      */       public Descriptors.FieldDescriptor get(GeneratedMessage.Builder<?> builder) {
/* 2477 */         return builder.hasField(this.fieldDescriptor) ? this.fieldDescriptor : null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void clear(GeneratedMessage.Builder<?> builder) {
/* 2482 */         builder.clearField(this.fieldDescriptor);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static class SingularFieldAccessor
/*      */       implements FieldAccessor
/*      */     {
/*      */       protected final Class<?> type;
/*      */ 
/*      */       
/*      */       protected final Descriptors.FieldDescriptor field;
/*      */ 
/*      */       
/*      */       protected final boolean isOneofField;
/*      */       
/*      */       protected final boolean hasHasMethod;
/*      */       
/*      */       protected final MethodInvoker invoker;
/*      */ 
/*      */       
/*      */       private static final class ReflectionInvoker
/*      */         implements MethodInvoker
/*      */       {
/*      */         private final Method getMethod;
/*      */         
/*      */         private final Method getMethodBuilder;
/*      */         
/*      */         private final Method setMethod;
/*      */         
/*      */         private final Method hasMethod;
/*      */         
/*      */         private final Method hasMethodBuilder;
/*      */         
/*      */         private final Method clearMethod;
/*      */         
/*      */         private final Method caseMethod;
/*      */         
/*      */         private final Method caseMethodBuilder;
/*      */ 
/*      */         
/*      */         ReflectionInvoker(String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass, String containingOneofCamelCaseName, boolean isOneofField, boolean hasHasMethod) {
/* 2525 */           this.getMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName, new Class[0]);
/* 2526 */           this.getMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName, new Class[0]);
/* 2527 */           Class<?> type = this.getMethod.getReturnType();
/* 2528 */           this.setMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName, new Class[] { type });
/* 2529 */           this.hasMethod = hasHasMethod ? GeneratedMessage.getMethodOrDie(messageClass, "has" + camelCaseName, new Class[0]) : null;
/* 2530 */           this
/* 2531 */             .hasMethodBuilder = hasHasMethod ? GeneratedMessage.getMethodOrDie(builderClass, "has" + camelCaseName, new Class[0]) : null;
/* 2532 */           this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
/* 2533 */           this
/*      */ 
/*      */             
/* 2536 */             .caseMethod = isOneofField ? GeneratedMessage.getMethodOrDie(messageClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]) : null;
/* 2537 */           this
/*      */ 
/*      */             
/* 2540 */             .caseMethodBuilder = isOneofField ? GeneratedMessage.getMethodOrDie(builderClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]) : null;
/*      */         }
/*      */ 
/*      */         
/*      */         public Object get(GeneratedMessage message) {
/* 2545 */           return GeneratedMessage.invokeOrDie(this.getMethod, message, new Object[0]);
/*      */         }
/*      */ 
/*      */         
/*      */         public Object get(GeneratedMessage.Builder<?> builder) {
/* 2550 */           return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
/*      */         }
/*      */ 
/*      */         
/*      */         public int getOneofFieldNumber(GeneratedMessage message) {
/* 2555 */           return ((Internal.EnumLite)GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
/*      */         }
/*      */ 
/*      */         
/*      */         public int getOneofFieldNumber(GeneratedMessage.Builder<?> builder) {
/* 2560 */           return ((Internal.EnumLite)GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void set(GeneratedMessage.Builder<?> builder, Object value) {
/* 2566 */           Object unused = GeneratedMessage.invokeOrDie(this.setMethod, builder, new Object[] { value });
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean has(GeneratedMessage message) {
/* 2571 */           return ((Boolean)GeneratedMessage.invokeOrDie(this.hasMethod, message, new Object[0])).booleanValue();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean has(GeneratedMessage.Builder<?> builder) {
/* 2576 */           return ((Boolean)GeneratedMessage.invokeOrDie(this.hasMethodBuilder, builder, new Object[0])).booleanValue();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void clear(GeneratedMessage.Builder<?> builder) {
/* 2582 */           Object unused = GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       SingularFieldAccessor(Descriptors.FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass, String containingOneofCamelCaseName) {
/* 2592 */         this.isOneofField = (descriptor.getRealContainingOneof() != null);
/* 2593 */         this.hasHasMethod = descriptor.hasPresence();
/* 2594 */         ReflectionInvoker reflectionInvoker = new ReflectionInvoker(camelCaseName, messageClass, builderClass, containingOneofCamelCaseName, this.isOneofField, this.hasHasMethod);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2602 */         this.field = descriptor;
/* 2603 */         this.type = reflectionInvoker.getMethod.getReturnType();
/* 2604 */         this.invoker = getMethodInvoker(reflectionInvoker);
/*      */       }
/*      */       
/*      */       static MethodInvoker getMethodInvoker(ReflectionInvoker accessor) {
/* 2608 */         return accessor;
/*      */       }
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
/*      */       public Object get(GeneratedMessage message) {
/* 2622 */         return this.invoker.get(message);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object get(GeneratedMessage.Builder<?> builder) {
/* 2627 */         return this.invoker.get(builder);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRaw(GeneratedMessage message) {
/* 2632 */         return get(message);
/*      */       }
/*      */ 
/*      */       
/*      */       public void set(GeneratedMessage.Builder<?> builder, Object value) {
/* 2637 */         this.invoker.set(builder, value);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage message, int index) {
/* 2642 */         throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage.Builder<?> builder, int index) {
/* 2647 */         throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public void setRepeated(GeneratedMessage.Builder<?> builder, int index, Object value) {
/* 2652 */         throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public void addRepeated(GeneratedMessage.Builder<?> builder, Object value) {
/* 2657 */         throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage message) {
/* 2662 */         if (!this.hasHasMethod) {
/* 2663 */           if (this.isOneofField) {
/* 2664 */             return (this.invoker.getOneofFieldNumber(message) == this.field.getNumber());
/*      */           }
/* 2666 */           return !get(message).equals(this.field.getDefaultValue());
/*      */         } 
/* 2668 */         return this.invoker.has(message);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage.Builder<?> builder) {
/* 2673 */         if (!this.hasHasMethod) {
/* 2674 */           if (this.isOneofField) {
/* 2675 */             return (this.invoker.getOneofFieldNumber(builder) == this.field.getNumber());
/*      */           }
/* 2677 */           return !get(builder).equals(this.field.getDefaultValue());
/*      */         } 
/* 2679 */         return this.invoker.has(builder);
/*      */       }
/*      */ 
/*      */       
/*      */       public int getRepeatedCount(GeneratedMessage message) {
/* 2684 */         throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public int getRepeatedCount(GeneratedMessage.Builder<?> builder) {
/* 2690 */         throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void clear(GeneratedMessage.Builder<?> builder) {
/* 2696 */         this.invoker.clear(builder);
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder newBuilder() {
/* 2701 */         throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public Message.Builder getBuilder(GeneratedMessage.Builder<?> builder) {
/* 2707 */         throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder getRepeatedBuilder(GeneratedMessage.Builder<?> builder, int index) {
/* 2712 */         throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
/*      */       }
/*      */       
/*      */       private static interface MethodInvoker
/*      */       {
/*      */         Object get(GeneratedMessage param3GeneratedMessage);
/*      */         
/*      */         Object get(GeneratedMessage.Builder<?> param3Builder);
/*      */         
/*      */         int getOneofFieldNumber(GeneratedMessage param3GeneratedMessage);
/*      */         
/*      */         int getOneofFieldNumber(GeneratedMessage.Builder<?> param3Builder);
/*      */         
/*      */         void set(GeneratedMessage.Builder<?> param3Builder, Object param3Object);
/*      */         
/*      */         boolean has(GeneratedMessage param3GeneratedMessage);
/*      */         
/*      */         boolean has(GeneratedMessage.Builder<?> param3Builder);
/*      */         
/*      */         void clear(GeneratedMessage.Builder<?> param3Builder);
/*      */       }
/*      */     }
/*      */     
/*      */     private static class RepeatedFieldAccessor
/*      */       implements FieldAccessor
/*      */     {
/*      */       protected final Class<?> type;
/*      */       protected final MethodInvoker invoker;
/*      */       
/*      */       private static final class ReflectionInvoker
/*      */         implements MethodInvoker
/*      */       {
/*      */         private final Method getMethod;
/*      */         private final Method getMethodBuilder;
/*      */         private final Method getRepeatedMethod;
/*      */         private final Method getRepeatedMethodBuilder;
/*      */         private final Method setRepeatedMethod;
/*      */         private final Method addRepeatedMethod;
/*      */         private final Method getCountMethod;
/*      */         private final Method getCountMethodBuilder;
/*      */         private final Method clearMethod;
/*      */         
/*      */         ReflectionInvoker(String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 2755 */           this.getMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "List", new Class[0]);
/* 2756 */           this.getMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "List", new Class[0]);
/* 2757 */           this.getRepeatedMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName, new Class[] { int.class });
/* 2758 */           this
/* 2759 */             .getRepeatedMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName, new Class[] { int.class });
/* 2760 */           Class<?> type = this.getRepeatedMethod.getReturnType();
/* 2761 */           this
/* 2762 */             .setRepeatedMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName, new Class[] { int.class, type });
/* 2763 */           this.addRepeatedMethod = GeneratedMessage.getMethodOrDie(builderClass, "add" + camelCaseName, new Class[] { type });
/* 2764 */           this.getCountMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Count", new Class[0]);
/* 2765 */           this.getCountMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Count", new Class[0]);
/* 2766 */           this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
/*      */         }
/*      */ 
/*      */         
/*      */         public Object get(GeneratedMessage message) {
/* 2771 */           return GeneratedMessage.invokeOrDie(this.getMethod, message, new Object[0]);
/*      */         }
/*      */ 
/*      */         
/*      */         public Object get(GeneratedMessage.Builder<?> builder) {
/* 2776 */           return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
/*      */         }
/*      */ 
/*      */         
/*      */         public Object getRepeated(GeneratedMessage message, int index) {
/* 2781 */           return GeneratedMessage.invokeOrDie(this.getRepeatedMethod, message, new Object[] { Integer.valueOf(index) });
/*      */         }
/*      */ 
/*      */         
/*      */         public Object getRepeated(GeneratedMessage.Builder<?> builder, int index) {
/* 2786 */           return GeneratedMessage.invokeOrDie(this.getRepeatedMethodBuilder, builder, new Object[] { Integer.valueOf(index) });
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setRepeated(GeneratedMessage.Builder<?> builder, int index, Object value) {
/* 2793 */           Object unused = GeneratedMessage.invokeOrDie(this.setRepeatedMethod, builder, new Object[] { Integer.valueOf(index), value });
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void addRepeated(GeneratedMessage.Builder<?> builder, Object value) {
/* 2799 */           Object unused = GeneratedMessage.invokeOrDie(this.addRepeatedMethod, builder, new Object[] { value });
/*      */         }
/*      */ 
/*      */         
/*      */         public int getRepeatedCount(GeneratedMessage message) {
/* 2804 */           return ((Integer)GeneratedMessage.invokeOrDie(this.getCountMethod, message, new Object[0])).intValue();
/*      */         }
/*      */ 
/*      */         
/*      */         public int getRepeatedCount(GeneratedMessage.Builder<?> builder) {
/* 2809 */           return ((Integer)GeneratedMessage.invokeOrDie(this.getCountMethodBuilder, builder, new Object[0])).intValue();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void clear(GeneratedMessage.Builder<?> builder) {
/* 2815 */           Object unused = GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       RepeatedFieldAccessor(String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 2826 */         ReflectionInvoker reflectionInvoker = new ReflectionInvoker(camelCaseName, messageClass, builderClass);
/*      */         
/* 2828 */         this.type = reflectionInvoker.getRepeatedMethod.getReturnType();
/* 2829 */         this.invoker = getMethodInvoker(reflectionInvoker);
/*      */       }
/*      */       
/*      */       static MethodInvoker getMethodInvoker(ReflectionInvoker accessor) {
/* 2833 */         return accessor;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object get(GeneratedMessage message) {
/* 2838 */         return this.invoker.get(message);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object get(GeneratedMessage.Builder<?> builder) {
/* 2843 */         return this.invoker.get(builder);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRaw(GeneratedMessage message) {
/* 2848 */         return get(message);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void set(GeneratedMessage.Builder<?> builder, Object value) {
/* 2857 */         clear(builder);
/* 2858 */         for (Object element : value) {
/* 2859 */           addRepeated(builder, element);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage message, int index) {
/* 2865 */         return this.invoker.getRepeated(message, index);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage.Builder<?> builder, int index) {
/* 2870 */         return this.invoker.getRepeated(builder, index);
/*      */       }
/*      */ 
/*      */       
/*      */       public void setRepeated(GeneratedMessage.Builder<?> builder, int index, Object value) {
/* 2875 */         this.invoker.setRepeated(builder, index, value);
/*      */       }
/*      */ 
/*      */       
/*      */       public void addRepeated(GeneratedMessage.Builder<?> builder, Object value) {
/* 2880 */         this.invoker.addRepeated(builder, value);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage message) {
/* 2885 */         throw new UnsupportedOperationException("hasField() called on a repeated field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage.Builder<?> builder) {
/* 2890 */         throw new UnsupportedOperationException("hasField() called on a repeated field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public int getRepeatedCount(GeneratedMessage message) {
/* 2895 */         return this.invoker.getRepeatedCount(message);
/*      */       }
/*      */ 
/*      */       
/*      */       public int getRepeatedCount(GeneratedMessage.Builder<?> builder) {
/* 2900 */         return this.invoker.getRepeatedCount(builder);
/*      */       }
/*      */ 
/*      */       
/*      */       public void clear(GeneratedMessage.Builder<?> builder) {
/* 2905 */         this.invoker.clear(builder);
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder newBuilder() {
/* 2910 */         throw new UnsupportedOperationException("newBuilderForField() called on a repeated field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder getBuilder(GeneratedMessage.Builder<?> builder) {
/* 2915 */         throw new UnsupportedOperationException("getFieldBuilder() called on a repeated field.");
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder getRepeatedBuilder(GeneratedMessage.Builder<?> builder, int index) {
/* 2920 */         throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
/*      */       } static interface MethodInvoker { Object get(GeneratedMessage param3GeneratedMessage); Object get(GeneratedMessage.Builder<?> param3Builder); Object getRepeated(GeneratedMessage param3GeneratedMessage, int param3Int); Object getRepeated(GeneratedMessage.Builder<?> param3Builder, int param3Int); void setRepeated(GeneratedMessage.Builder<?> param3Builder, int param3Int, Object param3Object); void addRepeated(GeneratedMessage.Builder<?> param3Builder, Object param3Object);
/*      */         int getRepeatedCount(GeneratedMessage param3GeneratedMessage);
/*      */         int getRepeatedCount(GeneratedMessage.Builder<?> param3Builder);
/*      */         void clear(GeneratedMessage.Builder<?> param3Builder); } }
/*      */     private static class MapFieldAccessor implements FieldAccessor {
/*      */       private final Descriptors.FieldDescriptor field; private final Message mapEntryMessageDefaultInstance;
/*      */       MapFieldAccessor(Descriptors.FieldDescriptor descriptor, Class<? extends GeneratedMessage> messageClass) {
/* 2928 */         this.field = descriptor;
/* 2929 */         Method getDefaultInstanceMethod = GeneratedMessage.getMethodOrDie(messageClass, "getDefaultInstance", new Class[0]);
/*      */         
/* 2931 */         MapFieldReflectionAccessor defaultMapField = getMapField((GeneratedMessage)GeneratedMessage.invokeOrDie(getDefaultInstanceMethod, (Object)null, new Object[0]));
/* 2932 */         this.mapEntryMessageDefaultInstance = defaultMapField.getMapEntryMessageDefaultInstance();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private MapFieldReflectionAccessor getMapField(GeneratedMessage message) {
/* 2939 */         return message.internalGetMapFieldReflection(this.field.getNumber());
/*      */       }
/*      */       
/*      */       private MapFieldReflectionAccessor getMapField(GeneratedMessage.Builder<?> builder) {
/* 2943 */         return builder.internalGetMapFieldReflection(this.field.getNumber());
/*      */       }
/*      */       
/*      */       private MapFieldReflectionAccessor getMutableMapField(GeneratedMessage.Builder<?> builder) {
/* 2947 */         return builder.internalGetMutableMapFieldReflection(this.field.getNumber());
/*      */       }
/*      */       
/*      */       private Message coerceType(Message value) {
/* 2951 */         if (value == null) {
/* 2952 */           return null;
/*      */         }
/* 2954 */         if (this.mapEntryMessageDefaultInstance.getClass().isInstance(value)) {
/* 2955 */           return value;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2961 */         return this.mapEntryMessageDefaultInstance.toBuilder().mergeFrom(value).build();
/*      */       }
/*      */ 
/*      */       
/*      */       public Object get(GeneratedMessage message) {
/* 2966 */         List<Object> result = new ArrayList();
/* 2967 */         for (int i = 0; i < getRepeatedCount(message); i++) {
/* 2968 */           result.add(getRepeated(message, i));
/*      */         }
/* 2970 */         return Collections.unmodifiableList(result);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object get(GeneratedMessage.Builder<?> builder) {
/* 2975 */         List<Object> result = new ArrayList();
/* 2976 */         for (int i = 0; i < getRepeatedCount(builder); i++) {
/* 2977 */           result.add(getRepeated(builder, i));
/*      */         }
/* 2979 */         return Collections.unmodifiableList(result);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRaw(GeneratedMessage message) {
/* 2984 */         return get(message);
/*      */       }
/*      */ 
/*      */       
/*      */       public void set(GeneratedMessage.Builder<?> builder, Object value) {
/* 2989 */         clear(builder);
/* 2990 */         for (Object entry : value) {
/* 2991 */           addRepeated(builder, entry);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage message, int index) {
/* 2997 */         return getMapField(message).getList().get(index);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage.Builder<?> builder, int index) {
/* 3002 */         return getMapField(builder).getList().get(index);
/*      */       }
/*      */ 
/*      */       
/*      */       public void setRepeated(GeneratedMessage.Builder<?> builder, int index, Object value) {
/* 3007 */         getMutableMapField(builder).getMutableList().set(index, coerceType((Message)value));
/*      */       }
/*      */ 
/*      */       
/*      */       public void addRepeated(GeneratedMessage.Builder<?> builder, Object value) {
/* 3012 */         getMutableMapField(builder).getMutableList().add(coerceType((Message)value));
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage message) {
/* 3017 */         throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean has(GeneratedMessage.Builder<?> builder) {
/* 3022 */         throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
/*      */       }
/*      */ 
/*      */       
/*      */       public int getRepeatedCount(GeneratedMessage message) {
/* 3027 */         return getMapField(message).getList().size();
/*      */       }
/*      */ 
/*      */       
/*      */       public int getRepeatedCount(GeneratedMessage.Builder<?> builder) {
/* 3032 */         return getMapField(builder).getList().size();
/*      */       }
/*      */ 
/*      */       
/*      */       public void clear(GeneratedMessage.Builder<?> builder) {
/* 3037 */         getMutableMapField(builder).getMutableList().clear();
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder newBuilder() {
/* 3042 */         return this.mapEntryMessageDefaultInstance.newBuilderForType();
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder getBuilder(GeneratedMessage.Builder<?> builder) {
/* 3047 */         throw new UnsupportedOperationException("Nested builder not supported for map fields.");
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder getRepeatedBuilder(GeneratedMessage.Builder<?> builder, int index) {
/* 3052 */         throw new UnsupportedOperationException("Map fields cannot be repeated");
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class SingularEnumFieldAccessor extends SingularFieldAccessor { private final Descriptors.EnumDescriptor enumDescriptor;
/*      */       private final Method valueOfMethod;
/*      */       private final Method getValueDescriptorMethod;
/*      */       private final boolean supportUnknownEnumValue;
/*      */       private Method getValueMethod;
/*      */       private Method getValueMethodBuilder;
/*      */       private Method setValueMethod;
/*      */       
/*      */       SingularEnumFieldAccessor(Descriptors.FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass, String containingOneofCamelCaseName) {
/* 3065 */         super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
/*      */         
/* 3067 */         this.enumDescriptor = descriptor.getEnumType();
/*      */         
/* 3069 */         this.valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", new Class[] { Descriptors.EnumValueDescriptor.class });
/* 3070 */         this.getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
/*      */         
/* 3072 */         this.supportUnknownEnumValue = !descriptor.legacyEnumFieldTreatedAsClosed();
/* 3073 */         if (this.supportUnknownEnumValue) {
/* 3074 */           this.getValueMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Value", new Class[0]);
/* 3075 */           this.getValueMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Value", new Class[0]);
/* 3076 */           this.setValueMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName + "Value", new Class[] { int.class });
/*      */         } 
/*      */       }
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
/*      */       public Object get(GeneratedMessage message) {
/* 3092 */         if (this.supportUnknownEnumValue) {
/* 3093 */           int value = ((Integer)GeneratedMessage.invokeOrDie(this.getValueMethod, message, new Object[0])).intValue();
/* 3094 */           return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
/*      */         } 
/* 3096 */         return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(message), new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object get(GeneratedMessage.Builder<?> builder) {
/* 3101 */         if (this.supportUnknownEnumValue) {
/* 3102 */           int value = ((Integer)GeneratedMessage.invokeOrDie(this.getValueMethodBuilder, builder, new Object[0])).intValue();
/* 3103 */           return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
/*      */         } 
/* 3105 */         return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(builder), new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*      */       public void set(GeneratedMessage.Builder<?> builder, Object value) {
/* 3110 */         if (this.supportUnknownEnumValue) {
/*      */ 
/*      */           
/* 3113 */           Object unused = GeneratedMessage.invokeOrDie(this.setValueMethod, builder, new Object[] { Integer.valueOf(((Descriptors.EnumValueDescriptor)value).getNumber()) });
/*      */           return;
/*      */         } 
/* 3116 */         super.set(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, (Object)null, new Object[] { value }));
/*      */       } }
/*      */     private static final class RepeatedEnumFieldAccessor extends RepeatedFieldAccessor { private final Descriptors.EnumDescriptor enumDescriptor; private final Method valueOfMethod; private final Method getValueDescriptorMethod;
/*      */       private final boolean supportUnknownEnumValue;
/*      */       private Method getRepeatedValueMethod;
/*      */       private Method getRepeatedValueMethodBuilder;
/*      */       private Method setRepeatedValueMethod;
/*      */       private Method addRepeatedValueMethod;
/*      */       
/*      */       RepeatedEnumFieldAccessor(Descriptors.FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 3126 */         super(camelCaseName, messageClass, builderClass);
/*      */         
/* 3128 */         this.enumDescriptor = descriptor.getEnumType();
/*      */         
/* 3130 */         this.valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", new Class[] { Descriptors.EnumValueDescriptor.class });
/* 3131 */         this.getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
/*      */         
/* 3133 */         this.supportUnknownEnumValue = !descriptor.legacyEnumFieldTreatedAsClosed();
/* 3134 */         if (this.supportUnknownEnumValue) {
/* 3135 */           this
/* 3136 */             .getRepeatedValueMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Value", new Class[] { int.class });
/* 3137 */           this
/* 3138 */             .getRepeatedValueMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Value", new Class[] { int.class });
/* 3139 */           this
/* 3140 */             .setRepeatedValueMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName + "Value", new Class[] { int.class, int.class });
/* 3141 */           this
/* 3142 */             .addRepeatedValueMethod = GeneratedMessage.getMethodOrDie(builderClass, "add" + camelCaseName + "Value", new Class[] { int.class });
/*      */         } 
/*      */       }
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
/*      */       public Object get(GeneratedMessage message) {
/* 3160 */         List<Object> newList = new ArrayList();
/* 3161 */         int size = getRepeatedCount(message);
/* 3162 */         for (int i = 0; i < size; i++) {
/* 3163 */           newList.add(getRepeated(message, i));
/*      */         }
/* 3165 */         return Collections.unmodifiableList(newList);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object get(GeneratedMessage.Builder<?> builder) {
/* 3170 */         List<Object> newList = new ArrayList();
/* 3171 */         int size = getRepeatedCount(builder);
/* 3172 */         for (int i = 0; i < size; i++) {
/* 3173 */           newList.add(getRepeated(builder, i));
/*      */         }
/* 3175 */         return Collections.unmodifiableList(newList);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage message, int index) {
/* 3180 */         if (this.supportUnknownEnumValue) {
/* 3181 */           int value = ((Integer)GeneratedMessage.invokeOrDie(this.getRepeatedValueMethod, message, new Object[] { Integer.valueOf(index) })).intValue();
/* 3182 */           return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
/*      */         } 
/* 3184 */         return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(message, index), new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object getRepeated(GeneratedMessage.Builder<?> builder, int index) {
/* 3189 */         if (this.supportUnknownEnumValue) {
/* 3190 */           int value = ((Integer)GeneratedMessage.invokeOrDie(this.getRepeatedValueMethodBuilder, builder, new Object[] { Integer.valueOf(index) })).intValue();
/* 3191 */           return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
/*      */         } 
/* 3193 */         return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(builder, index), new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*      */       public void setRepeated(GeneratedMessage.Builder<?> builder, int index, Object value) {
/* 3198 */         if (this.supportUnknownEnumValue) {
/*      */ 
/*      */           
/* 3201 */           Object unused = GeneratedMessage.invokeOrDie(this.setRepeatedValueMethod, builder, new Object[] {
/*      */ 
/*      */                 
/* 3204 */                 Integer.valueOf(index), 
/* 3205 */                 Integer.valueOf(((Descriptors.EnumValueDescriptor)value).getNumber()) });
/*      */           return;
/*      */         } 
/* 3208 */         super.setRepeated(builder, index, GeneratedMessage.invokeOrDie(this.valueOfMethod, (Object)null, new Object[] { value }));
/*      */       }
/*      */ 
/*      */       
/*      */       public void addRepeated(GeneratedMessage.Builder<?> builder, Object value) {
/* 3213 */         if (this.supportUnknownEnumValue) {
/*      */ 
/*      */           
/* 3216 */           Object unused = GeneratedMessage.invokeOrDie(this.addRepeatedValueMethod, builder, new Object[] {
/* 3217 */                 Integer.valueOf(((Descriptors.EnumValueDescriptor)value).getNumber()) });
/*      */           return;
/*      */         } 
/* 3220 */         super.addRepeated(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, (Object)null, new Object[] { value }));
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final class SingularStringFieldAccessor
/*      */       extends SingularFieldAccessor
/*      */     {
/*      */       private final Method getBytesMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private final Method setBytesMethodBuilder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       SingularStringFieldAccessor(Descriptors.FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass, String containingOneofCamelCaseName) {
/* 3243 */         super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
/* 3244 */         this.getBytesMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Bytes", new Class[0]);
/* 3245 */         this
/* 3246 */           .setBytesMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName + "Bytes", new Class[] { ByteString.class });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Object getRaw(GeneratedMessage message) {
/* 3254 */         return GeneratedMessage.invokeOrDie(this.getBytesMethod, message, new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*      */       public void set(GeneratedMessage.Builder<?> builder, Object value) {
/* 3259 */         if (value instanceof ByteString) {
/*      */           
/* 3261 */           Object object = GeneratedMessage.invokeOrDie(this.setBytesMethodBuilder, builder, new Object[] { value });
/*      */         } else {
/* 3263 */           super.set(builder, value);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private static final class SingularMessageFieldAccessor
/*      */       extends SingularFieldAccessor
/*      */     {
/*      */       private final Method newBuilderMethod;
/*      */       
/*      */       private final Method getBuilderMethodBuilder;
/*      */       
/*      */       SingularMessageFieldAccessor(Descriptors.FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass, String containingOneofCamelCaseName) {
/* 3277 */         super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
/*      */         
/* 3279 */         this.newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);
/* 3280 */         this.getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Builder", new Class[0]);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private Object coerceType(Object value) {
/* 3287 */         if (this.type.isInstance(value)) {
/* 3288 */           return value;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3294 */         return ((Message.Builder)GeneratedMessage.invokeOrDie(this.newBuilderMethod, (Object)null, new Object[0]))
/* 3295 */           .mergeFrom((Message)value)
/* 3296 */           .buildPartial();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void set(GeneratedMessage.Builder<?> builder, Object value) {
/* 3302 */         super.set(builder, coerceType(value));
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder newBuilder() {
/* 3307 */         return (Message.Builder)GeneratedMessage.invokeOrDie(this.newBuilderMethod, (Object)null, new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder getBuilder(GeneratedMessage.Builder<?> builder) {
/* 3312 */         return (Message.Builder)GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[0]);
/*      */       }
/*      */     }
/*      */     
/*      */     private static final class RepeatedMessageFieldAccessor extends RepeatedFieldAccessor {
/*      */       private final Method newBuilderMethod;
/*      */       private final Method getBuilderMethodBuilder;
/*      */       
/*      */       RepeatedMessageFieldAccessor(String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 3321 */         super(camelCaseName, messageClass, builderClass);
/*      */         
/* 3323 */         this.newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);
/* 3324 */         this
/* 3325 */           .getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Builder", new Class[] { int.class });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private Object coerceType(Object value) {
/* 3332 */         if (this.type.isInstance(value)) {
/* 3333 */           return value;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3339 */         return ((Message.Builder)GeneratedMessage.invokeOrDie(this.newBuilderMethod, (Object)null, new Object[0]))
/* 3340 */           .mergeFrom((Message)value)
/* 3341 */           .build();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setRepeated(GeneratedMessage.Builder<?> builder, int index, Object value) {
/* 3347 */         super.setRepeated(builder, index, coerceType(value));
/*      */       }
/*      */ 
/*      */       
/*      */       public void addRepeated(GeneratedMessage.Builder<?> builder, Object value) {
/* 3352 */         super.addRepeated(builder, coerceType(value));
/*      */       }
/*      */ 
/*      */       
/*      */       public Message.Builder newBuilder() {
/* 3357 */         return (Message.Builder)GeneratedMessage.invokeOrDie(this.newBuilderMethod, (Object)null, new Object[0]);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public Message.Builder getRepeatedBuilder(GeneratedMessage.Builder<?> builder, int index) {
/* 3363 */         return (Message.Builder)GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[] { Integer.valueOf(index) });
/*      */       }
/*      */     } private static interface FieldAccessor { Object get(GeneratedMessage param2GeneratedMessage); Object get(GeneratedMessage.Builder<?> param2Builder); Object getRaw(GeneratedMessage param2GeneratedMessage); void set(GeneratedMessage.Builder<?> param2Builder, Object param2Object); Object getRepeated(GeneratedMessage param2GeneratedMessage, int param2Int); Object getRepeated(GeneratedMessage.Builder<?> param2Builder, int param2Int); void setRepeated(GeneratedMessage.Builder<?> param2Builder, int param2Int, Object param2Object); void addRepeated(GeneratedMessage.Builder<?> param2Builder, Object param2Object); boolean has(GeneratedMessage param2GeneratedMessage); boolean has(GeneratedMessage.Builder<?> param2Builder); int getRepeatedCount(GeneratedMessage param2GeneratedMessage); int getRepeatedCount(GeneratedMessage.Builder<?> param2Builder); void clear(GeneratedMessage.Builder<?> param2Builder);
/*      */       Message.Builder newBuilder();
/*      */       Message.Builder getBuilder(GeneratedMessage.Builder<?> param2Builder);
/*      */       Message.Builder getRepeatedBuilder(GeneratedMessage.Builder<?> param2Builder, int param2Int); }
/*      */     private static interface OneofAccessor { boolean has(GeneratedMessage param2GeneratedMessage);
/*      */       boolean has(GeneratedMessage.Builder<?> param2Builder);
/*      */       Descriptors.FieldDescriptor get(GeneratedMessage param2GeneratedMessage);
/*      */       Descriptors.FieldDescriptor get(GeneratedMessage.Builder<?> param2Builder);
/*      */       void clear(GeneratedMessage.Builder<?> param2Builder); }
/*      */   }
/*      */   protected Object writeReplace() throws ObjectStreamException {
/* 3376 */     return new GeneratedMessageLite.SerializedForm(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <MessageT extends ExtendableMessage<MessageT>, T> Extension<MessageT, T> checkNotLite(ExtensionLite<? extends MessageT, T> extension) {
/* 3385 */     if (extension.isLite()) {
/* 3386 */       throw new IllegalArgumentException("Expected non-lite extension.");
/*      */     }
/*      */     
/* 3389 */     return (Extension)extension;
/*      */   }
/*      */   
/*      */   protected static boolean isStringEmpty(Object value) {
/* 3393 */     if (value instanceof String) {
/* 3394 */       return ((String)value).isEmpty();
/*      */     }
/* 3396 */     return ((ByteString)value).isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   protected static int computeStringSize(int fieldNumber, Object value) {
/* 3401 */     if (value instanceof String) {
/* 3402 */       return CodedOutputStream.computeStringSize(fieldNumber, (String)value);
/*      */     }
/* 3404 */     return CodedOutputStream.computeBytesSize(fieldNumber, (ByteString)value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static int computeStringSizeNoTag(Object value) {
/* 3409 */     if (value instanceof String) {
/* 3410 */       return CodedOutputStream.computeStringSizeNoTag((String)value);
/*      */     }
/* 3412 */     return CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void writeString(CodedOutputStream output, int fieldNumber, Object value) throws IOException {
/* 3418 */     if (value instanceof String) {
/* 3419 */       output.writeString(fieldNumber, (String)value);
/*      */     } else {
/* 3421 */       output.writeBytes(fieldNumber, (ByteString)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected static void writeStringNoTag(CodedOutputStream output, Object value) throws IOException {
/* 3427 */     if (value instanceof String) {
/* 3428 */       output.writeStringNoTag((String)value);
/*      */     } else {
/* 3430 */       output.writeBytesNoTag((ByteString)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <V> void serializeIntegerMapTo(CodedOutputStream out, MapField<Integer, V> field, MapEntry<Integer, V> defaultEntry, int fieldNumber) throws IOException {
/* 3440 */     Map<Integer, V> m = field.getMap();
/* 3441 */     if (!out.isSerializationDeterministic()) {
/* 3442 */       serializeMapTo(out, m, defaultEntry, fieldNumber);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3447 */     int[] keys = new int[m.size()];
/* 3448 */     int index = 0;
/* 3449 */     for (Iterator<Integer> iterator = m.keySet().iterator(); iterator.hasNext(); ) { int k = ((Integer)iterator.next()).intValue();
/* 3450 */       keys[index++] = k; }
/*      */     
/* 3452 */     Arrays.sort(keys);
/* 3453 */     for (int key : keys) {
/* 3454 */       out.writeMessage(fieldNumber, defaultEntry
/* 3455 */           .newBuilderForType().setKey(Integer.valueOf(key)).setValue(m.get(Integer.valueOf(key))).build());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <V> void serializeLongMapTo(CodedOutputStream out, MapField<Long, V> field, MapEntry<Long, V> defaultEntry, int fieldNumber) throws IOException {
/* 3465 */     Map<Long, V> m = field.getMap();
/* 3466 */     if (!out.isSerializationDeterministic()) {
/* 3467 */       serializeMapTo(out, m, defaultEntry, fieldNumber);
/*      */       
/*      */       return;
/*      */     } 
/* 3471 */     long[] keys = new long[m.size()];
/* 3472 */     int index = 0;
/* 3473 */     for (Iterator<Long> iterator = m.keySet().iterator(); iterator.hasNext(); ) { long k = ((Long)iterator.next()).longValue();
/* 3474 */       keys[index++] = k; }
/*      */     
/* 3476 */     Arrays.sort(keys);
/* 3477 */     for (long key : keys) {
/* 3478 */       out.writeMessage(fieldNumber, defaultEntry
/* 3479 */           .newBuilderForType().setKey(Long.valueOf(key)).setValue(m.get(Long.valueOf(key))).build());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <V> void serializeStringMapTo(CodedOutputStream out, MapField<String, V> field, MapEntry<String, V> defaultEntry, int fieldNumber) throws IOException {
/* 3489 */     Map<String, V> m = field.getMap();
/* 3490 */     if (!out.isSerializationDeterministic()) {
/* 3491 */       serializeMapTo(out, m, defaultEntry, fieldNumber);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3497 */     String[] keys = new String[m.size()];
/* 3498 */     keys = (String[])m.keySet().toArray((Object[])keys);
/* 3499 */     Arrays.sort((Object[])keys);
/* 3500 */     for (String key : keys) {
/* 3501 */       out.writeMessage(fieldNumber, defaultEntry
/* 3502 */           .newBuilderForType().setKey(key).setValue(m.get(key)).build());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <V> void serializeBooleanMapTo(CodedOutputStream out, MapField<Boolean, V> field, MapEntry<Boolean, V> defaultEntry, int fieldNumber) throws IOException {
/* 3512 */     Map<Boolean, V> m = field.getMap();
/* 3513 */     if (!out.isSerializationDeterministic()) {
/* 3514 */       serializeMapTo(out, m, defaultEntry, fieldNumber);
/*      */       return;
/*      */     } 
/* 3517 */     maybeSerializeBooleanEntryTo(out, m, defaultEntry, fieldNumber, false);
/* 3518 */     maybeSerializeBooleanEntryTo(out, m, defaultEntry, fieldNumber, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <V> void maybeSerializeBooleanEntryTo(CodedOutputStream out, Map<Boolean, V> m, MapEntry<Boolean, V> defaultEntry, int fieldNumber, boolean key) throws IOException {
/* 3528 */     if (m.containsKey(Boolean.valueOf(key))) {
/* 3529 */       out.writeMessage(fieldNumber, defaultEntry
/* 3530 */           .newBuilderForType().setKey(Boolean.valueOf(key)).setValue(m.get(Boolean.valueOf(key))).build());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> void serializeMapTo(CodedOutputStream out, Map<K, V> m, MapEntry<K, V> defaultEntry, int fieldNumber) throws IOException {
/* 3538 */     for (Map.Entry<K, V> entry : m.entrySet())
/* 3539 */       out.writeMessage(fieldNumber, defaultEntry
/*      */ 
/*      */           
/* 3542 */           .newBuilderForType()
/* 3543 */           .setKey(entry.getKey())
/* 3544 */           .setValue(entry.getValue())
/* 3545 */           .build()); 
/*      */   }
/*      */   
/*      */   protected abstract FieldAccessorTable internalGetFieldAccessorTable();
/*      */   
/*      */   private static interface OneofAccessor {
/*      */     boolean has(GeneratedMessage param1GeneratedMessage);
/*      */     
/*      */     boolean has(GeneratedMessage.Builder<?> param1Builder);
/*      */     
/*      */     Descriptors.FieldDescriptor get(GeneratedMessage param1GeneratedMessage);
/*      */     
/*      */     Descriptors.FieldDescriptor get(GeneratedMessage.Builder<?> param1Builder);
/*      */     
/*      */     void clear(GeneratedMessage.Builder<?> param1Builder);
/*      */   }
/*      */   
/*      */   private static interface FieldAccessor {
/*      */     Object get(GeneratedMessage param1GeneratedMessage);
/*      */     
/*      */     Object get(GeneratedMessage.Builder<?> param1Builder);
/*      */     
/*      */     Object getRaw(GeneratedMessage param1GeneratedMessage);
/*      */     
/*      */     void set(GeneratedMessage.Builder<?> param1Builder, Object param1Object);
/*      */     
/*      */     Object getRepeated(GeneratedMessage param1GeneratedMessage, int param1Int);
/*      */     
/*      */     Object getRepeated(GeneratedMessage.Builder<?> param1Builder, int param1Int);
/*      */     
/*      */     void setRepeated(GeneratedMessage.Builder<?> param1Builder, int param1Int, Object param1Object);
/*      */     
/*      */     void addRepeated(GeneratedMessage.Builder<?> param1Builder, Object param1Object);
/*      */     
/*      */     boolean has(GeneratedMessage param1GeneratedMessage);
/*      */     
/*      */     boolean has(GeneratedMessage.Builder<?> param1Builder);
/*      */     
/*      */     int getRepeatedCount(GeneratedMessage param1GeneratedMessage);
/*      */     
/*      */     int getRepeatedCount(GeneratedMessage.Builder<?> param1Builder);
/*      */     
/*      */     void clear(GeneratedMessage.Builder<?> param1Builder);
/*      */     
/*      */     Message.Builder newBuilder();
/*      */     
/*      */     Message.Builder getBuilder(GeneratedMessage.Builder<?> param1Builder);
/*      */     
/*      */     Message.Builder getRepeatedBuilder(GeneratedMessage.Builder<?> param1Builder, int param1Int);
/*      */   }
/*      */   
/*      */   static interface ExtensionDescriptorRetriever {
/*      */     Descriptors.FieldDescriptor getDescriptor();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\GeneratedMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */