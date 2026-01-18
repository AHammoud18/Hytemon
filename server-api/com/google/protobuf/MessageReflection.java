/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
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
/*      */ class MessageReflection
/*      */ {
/*      */   static void writeMessageTo(Message message, Map<Descriptors.FieldDescriptor, Object> fields, CodedOutputStream output, boolean alwaysWriteRequiredFields) throws IOException {
/*   32 */     boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
/*   33 */     if (alwaysWriteRequiredFields) {
/*   34 */       fields = new TreeMap<>(fields);
/*   35 */       for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
/*   36 */         if (field.isRequired() && !fields.containsKey(field)) {
/*   37 */           fields.put(field, message.getField(field));
/*      */         }
/*      */       } 
/*      */     } 
/*   41 */     for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
/*   42 */       Descriptors.FieldDescriptor field = entry.getKey();
/*   43 */       Object value = entry.getValue();
/*   44 */       if (isMessageSet && field
/*   45 */         .isExtension() && field
/*   46 */         .getType() == Descriptors.FieldDescriptor.Type.MESSAGE && 
/*   47 */         !field.isRepeated()) {
/*   48 */         output.writeMessageSetExtension(field.getNumber(), (Message)value); continue;
/*      */       } 
/*   50 */       FieldSet.writeField(field, value, output);
/*      */     } 
/*      */ 
/*      */     
/*   54 */     UnknownFieldSet unknownFields = message.getUnknownFields();
/*   55 */     if (isMessageSet) {
/*   56 */       unknownFields.writeAsMessageSetTo(output);
/*      */     } else {
/*   58 */       unknownFields.writeTo(output);
/*      */     } 
/*      */   }
/*      */   
/*      */   static int getSerializedSize(Message message, Map<Descriptors.FieldDescriptor, Object> fields) {
/*   63 */     int size = 0;
/*      */     
/*   65 */     boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
/*      */     
/*   67 */     for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
/*   68 */       Descriptors.FieldDescriptor field = entry.getKey();
/*   69 */       Object value = entry.getValue();
/*   70 */       if (isMessageSet && field
/*   71 */         .isExtension() && field
/*   72 */         .getType() == Descriptors.FieldDescriptor.Type.MESSAGE && 
/*   73 */         !field.isRepeated()) {
/*   74 */         size += 
/*   75 */           CodedOutputStream.computeMessageSetExtensionSize(field.getNumber(), (Message)value); continue;
/*      */       } 
/*   77 */       size += FieldSet.computeFieldSize(field, value);
/*      */     } 
/*      */ 
/*      */     
/*   81 */     UnknownFieldSet unknownFields = message.getUnknownFields();
/*   82 */     if (isMessageSet) {
/*   83 */       size += unknownFields.getSerializedSizeAsMessageSet();
/*      */     } else {
/*   85 */       size += unknownFields.getSerializedSize();
/*      */     } 
/*   87 */     return size;
/*      */   }
/*      */   
/*      */   static String delimitWithCommas(List<String> parts) {
/*   91 */     StringBuilder result = new StringBuilder();
/*   92 */     for (String part : parts) {
/*   93 */       if (result.length() > 0) {
/*   94 */         result.append(", ");
/*      */       }
/*   96 */       result.append(part);
/*      */     } 
/*   98 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isInitialized(MessageOrBuilder message) {
/*  104 */     for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
/*  105 */       if (field.isRequired() && 
/*  106 */         !message.hasField(field)) {
/*  107 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  114 */     for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
/*  115 */       Descriptors.FieldDescriptor field = entry.getKey();
/*  116 */       if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*  117 */         if (field.isRepeated()) {
/*  118 */           for (Message element : entry.getValue()) {
/*  119 */             if (!element.isInitialized())
/*  120 */               return false; 
/*      */           } 
/*      */           continue;
/*      */         } 
/*  124 */         if (!((Message)entry.getValue()).isInitialized()) {
/*  125 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  131 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String subMessagePrefix(String prefix, Descriptors.FieldDescriptor field, int index) {
/*  136 */     StringBuilder result = new StringBuilder(prefix);
/*  137 */     if (field.isExtension()) {
/*  138 */       result.append('(').append(field.getFullName()).append(')');
/*      */     } else {
/*  140 */       result.append(field.getName());
/*      */     } 
/*  142 */     if (index != -1) {
/*  143 */       result.append('[').append(index).append(']');
/*      */     }
/*  145 */     result.append('.');
/*  146 */     return result.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void findMissingFields(MessageOrBuilder message, String prefix, List<String> results) {
/*  151 */     for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
/*  152 */       if (field.isRequired() && !message.hasField(field)) {
/*  153 */         results.add(prefix + field.getName());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  158 */     for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
/*  159 */       Descriptors.FieldDescriptor field = entry.getKey();
/*  160 */       Object value = entry.getValue();
/*      */       
/*  162 */       if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*  163 */         if (field.isRepeated()) {
/*  164 */           int i = 0;
/*  165 */           for (Object element : value)
/*  166 */             findMissingFields((MessageOrBuilder)element, 
/*  167 */                 subMessagePrefix(prefix, field, i++), results); 
/*      */           continue;
/*      */         } 
/*  170 */         if (message.hasField(field)) {
/*  171 */           findMissingFields((MessageOrBuilder)value, 
/*  172 */               subMessagePrefix(prefix, field, -1), results);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static List<String> findMissingFields(MessageOrBuilder message) {
/*  184 */     List<String> results = new ArrayList<>();
/*  185 */     findMissingFields(message, "", results);
/*  186 */     return results;
/*      */   } static interface MergeTarget { Descriptors.Descriptor getDescriptorForType(); ContainerType getContainerType(); ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry param1ExtensionRegistry, String param1String); ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry param1ExtensionRegistry, Descriptors.Descriptor param1Descriptor, int param1Int); Object getField(Descriptors.FieldDescriptor param1FieldDescriptor); boolean hasField(Descriptors.FieldDescriptor param1FieldDescriptor); MergeTarget setField(Descriptors.FieldDescriptor param1FieldDescriptor, Object param1Object); MergeTarget clearField(Descriptors.FieldDescriptor param1FieldDescriptor); MergeTarget setRepeatedField(Descriptors.FieldDescriptor param1FieldDescriptor, int param1Int, Object param1Object); MergeTarget addRepeatedField(Descriptors.FieldDescriptor param1FieldDescriptor, Object param1Object); boolean hasOneof(Descriptors.OneofDescriptor param1OneofDescriptor); MergeTarget clearOneof(Descriptors.OneofDescriptor param1OneofDescriptor); Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor param1OneofDescriptor); Object parseGroup(CodedInputStream param1CodedInputStream, ExtensionRegistryLite param1ExtensionRegistryLite, Descriptors.FieldDescriptor param1FieldDescriptor, Message param1Message) throws IOException; Object parseMessage(CodedInputStream param1CodedInputStream, ExtensionRegistryLite param1ExtensionRegistryLite, Descriptors.FieldDescriptor param1FieldDescriptor, Message param1Message) throws IOException; Object parseMessageFromBytes(ByteString param1ByteString, ExtensionRegistryLite param1ExtensionRegistryLite, Descriptors.FieldDescriptor param1FieldDescriptor, Message param1Message) throws IOException; void mergeGroup(CodedInputStream param1CodedInputStream, ExtensionRegistryLite param1ExtensionRegistryLite, Descriptors.FieldDescriptor param1FieldDescriptor, Message param1Message) throws IOException; void mergeMessage(CodedInputStream param1CodedInputStream, ExtensionRegistryLite param1ExtensionRegistryLite, Descriptors.FieldDescriptor param1FieldDescriptor, Message param1Message) throws IOException; WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor param1FieldDescriptor);
/*      */     MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor param1FieldDescriptor, Message param1Message);
/*      */     MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor param1FieldDescriptor, Message param1Message);
/*      */     Object finish();
/*  191 */     public enum ContainerType { MESSAGE,
/*  192 */       EXTENSION_SET; } } public enum ContainerType { MESSAGE, EXTENSION_SET; }
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
/*      */   static class BuilderAdapter
/*      */     implements MergeTarget
/*      */   {
/*      */     private final Message.Builder builder;
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
/*      */     private boolean hasNestedBuilders = true;
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
/*      */     public Descriptors.Descriptor getDescriptorForType() {
/*  362 */       return this.builder.getDescriptorForType();
/*      */     }
/*      */     
/*      */     public BuilderAdapter(Message.Builder builder) {
/*  366 */       this.builder = builder;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getField(Descriptors.FieldDescriptor field) {
/*  371 */       return this.builder.getField(field);
/*      */     }
/*      */     
/*      */     private Message.Builder getFieldBuilder(Descriptors.FieldDescriptor field) {
/*  375 */       if (this.hasNestedBuilders) {
/*      */         try {
/*  377 */           return this.builder.getFieldBuilder(field);
/*  378 */         } catch (UnsupportedOperationException e) {
/*  379 */           this.hasNestedBuilders = false;
/*      */         } 
/*      */       }
/*  382 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasField(Descriptors.FieldDescriptor field) {
/*  387 */       return this.builder.hasField(field);
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget setField(Descriptors.FieldDescriptor field, Object value) {
/*  392 */       if (!field.isRepeated() && value instanceof MessageLite.Builder) {
/*  393 */         if (value != getFieldBuilder(field)) {
/*  394 */           this.builder.setField(field, ((MessageLite.Builder)value).buildPartial());
/*      */         }
/*  396 */         return this;
/*      */       } 
/*  398 */       this.builder.setField(field, value);
/*  399 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget clearField(Descriptors.FieldDescriptor field) {
/*  404 */       this.builder.clearField(field);
/*  405 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/*  411 */       if (value instanceof MessageLite.Builder) {
/*  412 */         value = ((MessageLite.Builder)value).buildPartial();
/*      */       }
/*  414 */       this.builder.setRepeatedField(field, index, value);
/*  415 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/*  420 */       if (value instanceof MessageLite.Builder) {
/*  421 */         value = ((MessageLite.Builder)value).buildPartial();
/*      */       }
/*  423 */       this.builder.addRepeatedField(field, value);
/*  424 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/*  429 */       return this.builder.hasOneof(oneof);
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget clearOneof(Descriptors.OneofDescriptor oneof) {
/*  434 */       this.builder.clearOneof(oneof);
/*  435 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/*  440 */       return this.builder.getOneofFieldDescriptor(oneof);
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget.ContainerType getContainerType() {
/*  445 */       return MessageReflection.MergeTarget.ContainerType.MESSAGE;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
/*  451 */       return registry.findImmutableExtensionByName(name);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptors.Descriptor containingType, int fieldNumber) {
/*  457 */       return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*      */       Message.Builder subBuilder;
/*  469 */       if (defaultInstance != null) {
/*  470 */         subBuilder = defaultInstance.newBuilderForType();
/*      */       } else {
/*  472 */         subBuilder = this.builder.newBuilderForField(field);
/*      */       } 
/*  474 */       if (!field.isRepeated()) {
/*  475 */         Message originalMessage = (Message)getField(field);
/*  476 */         if (originalMessage != null) {
/*  477 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  480 */       input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/*  481 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*      */       Message.Builder subBuilder;
/*  493 */       if (defaultInstance != null) {
/*  494 */         subBuilder = defaultInstance.newBuilderForType();
/*      */       } else {
/*  496 */         subBuilder = this.builder.newBuilderForField(field);
/*      */       } 
/*  498 */       if (!field.isRepeated()) {
/*  499 */         Message originalMessage = (Message)getField(field);
/*  500 */         if (originalMessage != null) {
/*  501 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  504 */       input.readMessage(subBuilder, extensionRegistry);
/*  505 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*      */       Message.Builder subBuilder;
/*  517 */       if (defaultInstance != null) {
/*  518 */         subBuilder = defaultInstance.newBuilderForType();
/*      */       } else {
/*  520 */         subBuilder = this.builder.newBuilderForField(field);
/*      */       } 
/*  522 */       if (!field.isRepeated()) {
/*  523 */         Message originalMessage = (Message)getField(field);
/*  524 */         if (originalMessage != null) {
/*  525 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  528 */       subBuilder.mergeFrom(bytes, extensionRegistry);
/*  529 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mergeGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  539 */       if (!field.isRepeated()) {
/*      */         Message.Builder subBuilder;
/*  541 */         if (hasField(field)) {
/*  542 */           subBuilder = getFieldBuilder(field);
/*  543 */           if (subBuilder != null) {
/*  544 */             input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/*      */             return;
/*      */           } 
/*  547 */           subBuilder = newMessageFieldInstance(field, defaultInstance);
/*  548 */           subBuilder.mergeFrom((Message)getField(field));
/*      */         } else {
/*      */           
/*  551 */           subBuilder = newMessageFieldInstance(field, defaultInstance);
/*      */         } 
/*  553 */         input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/*  554 */         MessageReflection.MergeTarget mergeTarget = setField(field, subBuilder.buildPartial());
/*      */       } else {
/*  556 */         Message.Builder subBuilder = newMessageFieldInstance(field, defaultInstance);
/*  557 */         input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/*  558 */         MessageReflection.MergeTarget mergeTarget = addRepeatedField(field, subBuilder.buildPartial());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mergeMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  569 */       if (!field.isRepeated()) {
/*      */         Message.Builder subBuilder;
/*  571 */         if (hasField(field)) {
/*  572 */           subBuilder = getFieldBuilder(field);
/*  573 */           if (subBuilder != null) {
/*  574 */             input.readMessage(subBuilder, extensionRegistry);
/*      */             return;
/*      */           } 
/*  577 */           subBuilder = newMessageFieldInstance(field, defaultInstance);
/*  578 */           subBuilder.mergeFrom((Message)getField(field));
/*      */         } else {
/*      */           
/*  581 */           subBuilder = newMessageFieldInstance(field, defaultInstance);
/*      */         } 
/*  583 */         input.readMessage(subBuilder, extensionRegistry);
/*  584 */         MessageReflection.MergeTarget mergeTarget = setField(field, subBuilder.buildPartial());
/*      */       } else {
/*  586 */         Message.Builder subBuilder = newMessageFieldInstance(field, defaultInstance);
/*  587 */         input.readMessage(subBuilder, extensionRegistry);
/*  588 */         MessageReflection.MergeTarget mergeTarget = addRepeatedField(field, subBuilder.buildPartial());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private Message.Builder newMessageFieldInstance(Descriptors.FieldDescriptor field, Message defaultInstance) {
/*  595 */       if (defaultInstance != null) {
/*  596 */         return defaultInstance.newBuilderForType();
/*      */       }
/*  598 */       return this.builder.newBuilderForField(field);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor field, Message defaultInstance) {
/*  606 */       if (!field.isRepeated() && hasField(field)) {
/*  607 */         Message.Builder builder = getFieldBuilder(field);
/*  608 */         if (builder != null) {
/*  609 */           return new BuilderAdapter(builder);
/*      */         }
/*      */       } 
/*      */       
/*  613 */       Message.Builder subBuilder = newMessageFieldInstance(field, defaultInstance);
/*  614 */       if (!field.isRepeated()) {
/*  615 */         Message originalMessage = (Message)getField(field);
/*  616 */         if (originalMessage != null) {
/*  617 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  620 */       return new BuilderAdapter(subBuilder);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor field, Message defaultInstance) {
/*      */       Message.Builder subBuilder;
/*  627 */       if (defaultInstance != null) {
/*  628 */         subBuilder = defaultInstance.newBuilderForType();
/*      */       } else {
/*  630 */         subBuilder = this.builder.newBuilderForField(field);
/*      */       } 
/*  632 */       return new BuilderAdapter(subBuilder);
/*      */     }
/*      */ 
/*      */     
/*      */     public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor descriptor) {
/*  637 */       if (descriptor.needsUtf8Check()) {
/*  638 */         return WireFormat.Utf8Validation.STRICT;
/*      */       }
/*      */       
/*  641 */       if (!descriptor.isRepeated() && this.builder instanceof GeneratedMessage.Builder) {
/*  642 */         return WireFormat.Utf8Validation.LAZY;
/*      */       }
/*  644 */       return WireFormat.Utf8Validation.LOOSE;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object finish() {
/*  649 */       return this.builder;
/*      */     }
/*      */   }
/*      */   
/*      */   static class ExtensionAdapter
/*      */     implements MergeTarget {
/*      */     private final FieldSet<Descriptors.FieldDescriptor> extensions;
/*      */     
/*      */     ExtensionAdapter(FieldSet<Descriptors.FieldDescriptor> extensions) {
/*  658 */       this.extensions = extensions;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getDescriptorForType() {
/*  663 */       throw new UnsupportedOperationException("getDescriptorForType() called on FieldSet object");
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getField(Descriptors.FieldDescriptor field) {
/*  668 */       return this.extensions.getField(field);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasField(Descriptors.FieldDescriptor field) {
/*  673 */       return this.extensions.hasField(field);
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget setField(Descriptors.FieldDescriptor field, Object value) {
/*  678 */       this.extensions.setField(field, value);
/*  679 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget clearField(Descriptors.FieldDescriptor field) {
/*  684 */       this.extensions.clearField(field);
/*  685 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/*  691 */       this.extensions.setRepeatedField(field, index, value);
/*  692 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/*  697 */       this.extensions.addRepeatedField(field, value);
/*  698 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/*  703 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget clearOneof(Descriptors.OneofDescriptor oneof) {
/*  709 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/*  714 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget.ContainerType getContainerType() {
/*  719 */       return MessageReflection.MergeTarget.ContainerType.EXTENSION_SET;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
/*  725 */       return registry.findImmutableExtensionByName(name);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptors.Descriptor containingType, int fieldNumber) {
/*  731 */       return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseGroup(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  741 */       Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  742 */       if (!field.isRepeated()) {
/*  743 */         Message originalMessage = (Message)getField(field);
/*  744 */         if (originalMessage != null) {
/*  745 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  748 */       input.readGroup(field.getNumber(), subBuilder, registry);
/*  749 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseMessage(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  759 */       Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  760 */       if (!field.isRepeated()) {
/*  761 */         Message originalMessage = (Message)getField(field);
/*  762 */         if (originalMessage != null) {
/*  763 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  766 */       input.readMessage(subBuilder, registry);
/*  767 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mergeGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  777 */       if (!field.isRepeated()) {
/*  778 */         if (hasField(field)) {
/*  779 */           MessageLite.Builder current = ((MessageLite)getField(field)).toBuilder();
/*  780 */           input.readGroup(field.getNumber(), current, extensionRegistry);
/*  781 */           Object unused = setField(field, current.buildPartial());
/*      */           return;
/*      */         } 
/*  784 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  785 */         input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/*  786 */         MessageReflection.MergeTarget mergeTarget = setField(field, subBuilder.buildPartial());
/*      */       } else {
/*  788 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  789 */         input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/*  790 */         MessageReflection.MergeTarget mergeTarget = addRepeatedField(field, subBuilder.buildPartial());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mergeMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  801 */       if (!field.isRepeated()) {
/*  802 */         if (hasField(field)) {
/*  803 */           MessageLite.Builder current = ((MessageLite)getField(field)).toBuilder();
/*  804 */           input.readMessage(current, extensionRegistry);
/*  805 */           Object unused = setField(field, current.buildPartial());
/*      */           return;
/*      */         } 
/*  808 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  809 */         input.readMessage(subBuilder, extensionRegistry);
/*  810 */         MessageReflection.MergeTarget mergeTarget = setField(field, subBuilder.buildPartial());
/*      */       } else {
/*  812 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  813 */         input.readMessage(subBuilder, extensionRegistry);
/*  814 */         MessageReflection.MergeTarget mergeTarget = addRepeatedField(field, subBuilder.buildPartial());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  825 */       Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  826 */       if (!field.isRepeated()) {
/*  827 */         Message originalMessage = (Message)getField(field);
/*  828 */         if (originalMessage != null) {
/*  829 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  832 */       subBuilder.mergeFrom(bytes, registry);
/*  833 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
/*  839 */       throw new UnsupportedOperationException("newMergeTargetForField() called on FieldSet object");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
/*  845 */       throw new UnsupportedOperationException("newEmptyTargetForField() called on FieldSet object");
/*      */     }
/*      */ 
/*      */     
/*      */     public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor descriptor) {
/*  850 */       if (descriptor.needsUtf8Check()) {
/*  851 */         return WireFormat.Utf8Validation.STRICT;
/*      */       }
/*      */       
/*  854 */       return WireFormat.Utf8Validation.LOOSE;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object finish() {
/*  859 */       throw new UnsupportedOperationException("finish() called on FieldSet object");
/*      */     }
/*      */   }
/*      */   
/*      */   static class ExtensionBuilderAdapter
/*      */     implements MergeTarget {
/*      */     private final FieldSet.Builder<Descriptors.FieldDescriptor> extensions;
/*      */     
/*      */     ExtensionBuilderAdapter(FieldSet.Builder<Descriptors.FieldDescriptor> extensions) {
/*  868 */       this.extensions = extensions;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.Descriptor getDescriptorForType() {
/*  873 */       throw new UnsupportedOperationException("getDescriptorForType() called on FieldSet object");
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getField(Descriptors.FieldDescriptor field) {
/*  878 */       return this.extensions.getField(field);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasField(Descriptors.FieldDescriptor field) {
/*  883 */       return this.extensions.hasField(field);
/*      */     }
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     public MessageReflection.MergeTarget setField(Descriptors.FieldDescriptor field, Object value) {
/*  889 */       this.extensions.setField(field, value);
/*  890 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     public MessageReflection.MergeTarget clearField(Descriptors.FieldDescriptor field) {
/*  896 */       this.extensions.clearField(field);
/*  897 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     public MessageReflection.MergeTarget setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/*  904 */       this.extensions.setRepeatedField(field, index, value);
/*  905 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     public MessageReflection.MergeTarget addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/*  911 */       this.extensions.addRepeatedField(field, value);
/*  912 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/*  917 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     public MessageReflection.MergeTarget clearOneof(Descriptors.OneofDescriptor oneof) {
/*  924 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/*  929 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget.ContainerType getContainerType() {
/*  934 */       return MessageReflection.MergeTarget.ContainerType.EXTENSION_SET;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
/*  940 */       return registry.findImmutableExtensionByName(name);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptors.Descriptor containingType, int fieldNumber) {
/*  946 */       return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseGroup(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  956 */       Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  957 */       if (!field.isRepeated()) {
/*  958 */         Message originalMessage = (Message)getField(field);
/*  959 */         if (originalMessage != null) {
/*  960 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  963 */       input.readGroup(field.getNumber(), subBuilder, registry);
/*  964 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseMessage(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  974 */       Message.Builder subBuilder = defaultInstance.newBuilderForType();
/*  975 */       if (!field.isRepeated()) {
/*  976 */         Message originalMessage = (Message)getField(field);
/*  977 */         if (originalMessage != null) {
/*  978 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/*  981 */       input.readMessage(subBuilder, registry);
/*  982 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mergeGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/*  992 */       if (!field.isRepeated()) {
/*  993 */         if (hasField(field)) {
/*  994 */           MessageLite.Builder builder; Object fieldOrBuilder = this.extensions.getFieldAllowBuilders(field);
/*      */           
/*  996 */           if (fieldOrBuilder instanceof MessageLite.Builder) {
/*  997 */             builder = (MessageLite.Builder)fieldOrBuilder;
/*      */           } else {
/*  999 */             builder = ((MessageLite)fieldOrBuilder).toBuilder();
/* 1000 */             this.extensions.setField(field, builder);
/*      */           } 
/* 1002 */           input.readGroup(field.getNumber(), builder, extensionRegistry);
/*      */           return;
/*      */         } 
/* 1005 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/* 1006 */         input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/* 1007 */         MessageReflection.MergeTarget mergeTarget = setField(field, subBuilder);
/*      */       } else {
/* 1009 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/* 1010 */         input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
/* 1011 */         MessageReflection.MergeTarget mergeTarget = addRepeatedField(field, subBuilder.buildPartial());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mergeMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/* 1022 */       if (!field.isRepeated()) {
/* 1023 */         if (hasField(field)) {
/* 1024 */           MessageLite.Builder builder; Object fieldOrBuilder = this.extensions.getFieldAllowBuilders(field);
/*      */           
/* 1026 */           if (fieldOrBuilder instanceof MessageLite.Builder) {
/* 1027 */             builder = (MessageLite.Builder)fieldOrBuilder;
/*      */           } else {
/* 1029 */             builder = ((MessageLite)fieldOrBuilder).toBuilder();
/* 1030 */             this.extensions.setField(field, builder);
/*      */           } 
/* 1032 */           input.readMessage(builder, extensionRegistry);
/*      */           return;
/*      */         } 
/* 1035 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/* 1036 */         input.readMessage(subBuilder, extensionRegistry);
/* 1037 */         MessageReflection.MergeTarget mergeTarget = setField(field, subBuilder);
/*      */       } else {
/* 1039 */         Message.Builder subBuilder = defaultInstance.newBuilderForType();
/* 1040 */         input.readMessage(subBuilder, extensionRegistry);
/* 1041 */         MessageReflection.MergeTarget mergeTarget = addRepeatedField(field, subBuilder.buildPartial());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
/* 1052 */       Message.Builder subBuilder = defaultInstance.newBuilderForType();
/* 1053 */       if (!field.isRepeated()) {
/* 1054 */         Message originalMessage = (Message)getField(field);
/* 1055 */         if (originalMessage != null) {
/* 1056 */           subBuilder.mergeFrom(originalMessage);
/*      */         }
/*      */       } 
/* 1059 */       subBuilder.mergeFrom(bytes, registry);
/* 1060 */       return subBuilder.buildPartial();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
/* 1066 */       throw new UnsupportedOperationException("newMergeTargetForField() called on FieldSet object");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageReflection.MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
/* 1072 */       throw new UnsupportedOperationException("newEmptyTargetForField() called on FieldSet object");
/*      */     }
/*      */ 
/*      */     
/*      */     public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor descriptor) {
/* 1077 */       if (descriptor.needsUtf8Check()) {
/* 1078 */         return WireFormat.Utf8Validation.STRICT;
/*      */       }
/*      */       
/* 1081 */       return WireFormat.Utf8Validation.LOOSE;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object finish() {
/* 1086 */       throw new UnsupportedOperationException("finish() called on FieldSet object");
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
/*      */   static boolean mergeFieldFrom(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptors.Descriptor type, MergeTarget target, int tag) throws IOException {
/*      */     Descriptors.FieldDescriptor field;
/* 1109 */     if (type.getOptions().getMessageSetWireFormat() && tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
/* 1110 */       mergeMessageSetExtensionFromCodedStream(input, unknownFields, extensionRegistry, type, target);
/*      */       
/* 1112 */       return true;
/*      */     } 
/*      */     
/* 1115 */     int wireType = WireFormat.getTagWireType(tag);
/* 1116 */     int fieldNumber = WireFormat.getTagFieldNumber(tag);
/*      */ 
/*      */     
/* 1119 */     Message defaultInstance = null;
/*      */     
/* 1121 */     if (type.isExtensionNumber(fieldNumber)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1127 */       if (extensionRegistry instanceof ExtensionRegistry) {
/*      */         
/* 1129 */         ExtensionRegistry.ExtensionInfo extension = target.findExtensionByNumber((ExtensionRegistry)extensionRegistry, type, fieldNumber);
/* 1130 */         if (extension == null) {
/* 1131 */           field = null;
/*      */         } else {
/* 1133 */           field = extension.descriptor;
/* 1134 */           defaultInstance = extension.defaultInstance;
/* 1135 */           if (defaultInstance == null && field
/* 1136 */             .getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 1137 */             throw new IllegalStateException("Message-typed extension lacked default instance: " + field
/* 1138 */                 .getFullName());
/*      */           }
/*      */         } 
/*      */       } else {
/* 1142 */         field = null;
/*      */       } 
/* 1144 */     } else if (target.getContainerType() == MergeTarget.ContainerType.MESSAGE) {
/* 1145 */       field = type.findFieldByNumber(fieldNumber);
/*      */     } else {
/* 1147 */       field = null;
/*      */     } 
/*      */     
/* 1150 */     boolean unknown = false;
/* 1151 */     boolean packed = false;
/* 1152 */     if (field == null) {
/* 1153 */       unknown = true;
/* 1154 */     } else if (wireType == 
/* 1155 */       FieldSet.getWireFormatForFieldType(field.getLiteType(), false)) {
/* 1156 */       packed = false;
/* 1157 */     } else if (field.isPackable() && wireType == 
/*      */       
/* 1159 */       FieldSet.getWireFormatForFieldType(field.getLiteType(), true)) {
/* 1160 */       packed = true;
/*      */     } else {
/* 1162 */       unknown = true;
/*      */     } 
/*      */     
/* 1165 */     if (unknown) {
/* 1166 */       if (unknownFields != null) {
/* 1167 */         return unknownFields.mergeFieldFrom(tag, input);
/*      */       }
/* 1169 */       return input.skipField(tag);
/*      */     } 
/*      */ 
/*      */     
/* 1173 */     if (packed) {
/* 1174 */       int length = input.readRawVarint32();
/* 1175 */       int limit = input.pushLimit(length);
/* 1176 */       if (field.getLiteType() == WireFormat.FieldType.ENUM) {
/* 1177 */         while (input.getBytesUntilLimit() > 0) {
/* 1178 */           int rawValue = input.readEnum();
/* 1179 */           if (field.legacyEnumFieldTreatedAsClosed()) {
/* 1180 */             Object value = field.getEnumType().findValueByNumber(rawValue);
/*      */ 
/*      */             
/* 1183 */             if (value == null) {
/* 1184 */               if (unknownFields != null)
/* 1185 */                 unknownFields.mergeVarintField(fieldNumber, rawValue); 
/*      */               continue;
/*      */             } 
/* 1188 */             target.addRepeatedField(field, value);
/*      */             continue;
/*      */           } 
/* 1191 */           target.addRepeatedField(field, field
/* 1192 */               .getEnumType().findValueByNumberCreatingIfUnknown(rawValue));
/*      */         } 
/*      */       } else {
/*      */         
/* 1196 */         while (input.getBytesUntilLimit() > 0) {
/*      */           
/* 1198 */           Object value = input.readPrimitiveField(field.getLiteType(), target.getUtf8Validation(field));
/* 1199 */           target.addRepeatedField(field, value);
/*      */         } 
/*      */       } 
/* 1202 */       input.popLimit(limit);
/*      */     } else {
/*      */       Object value; int rawValue;
/* 1205 */       switch (field.getType()) {
/*      */         
/*      */         case GROUP:
/* 1208 */           target.mergeGroup(input, extensionRegistry, field, defaultInstance);
/* 1209 */           return true;
/*      */ 
/*      */         
/*      */         case MESSAGE:
/* 1213 */           target.mergeMessage(input, extensionRegistry, field, defaultInstance);
/* 1214 */           return true;
/*      */         
/*      */         case ENUM:
/* 1217 */           rawValue = input.readEnum();
/* 1218 */           if (field.legacyEnumFieldTreatedAsClosed()) {
/* 1219 */             Object object = field.getEnumType().findValueByNumber(rawValue);
/*      */ 
/*      */             
/* 1222 */             if (object == null) {
/* 1223 */               if (unknownFields != null) {
/* 1224 */                 unknownFields.mergeVarintField(fieldNumber, rawValue);
/*      */               }
/* 1226 */               return true;
/*      */             }  break;
/*      */           } 
/* 1229 */           value = field.getEnumType().findValueByNumberCreatingIfUnknown(rawValue);
/*      */           break;
/*      */         
/*      */         default:
/* 1233 */           value = input.readPrimitiveField(field.getLiteType(), target.getUtf8Validation(field));
/*      */           break;
/*      */       } 
/*      */       
/* 1237 */       if (field.isRepeated()) {
/* 1238 */         target.addRepeatedField(field, value);
/*      */       } else {
/* 1240 */         target.setField(field, value);
/*      */       } 
/*      */     } 
/*      */     
/* 1244 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void mergeMessageFrom(Message.Builder target, UnknownFieldSet.Builder unknownFields, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*      */     int tag;
/* 1254 */     BuilderAdapter builderAdapter = new BuilderAdapter(target);
/* 1255 */     Descriptors.Descriptor descriptorForType = target.getDescriptorForType();
/*      */     do {
/* 1257 */       tag = input.readTag();
/* 1258 */       if (tag == 0) {
/*      */         break;
/*      */       }
/*      */     }
/* 1262 */     while (mergeFieldFrom(input, unknownFields, extensionRegistry, descriptorForType, builderAdapter, tag));
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
/*      */   
/*      */   private static void mergeMessageSetExtensionFromCodedStream(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptors.Descriptor type, MergeTarget target) throws IOException {
/* 1295 */     int typeId = 0;
/* 1296 */     ByteString rawBytes = null;
/* 1297 */     ExtensionRegistry.ExtensionInfo extension = null;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1302 */       int tag = input.readTag();
/* 1303 */       if (tag == 0) {
/*      */         break;
/*      */       }
/*      */       
/* 1307 */       if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
/* 1308 */         typeId = input.readUInt32();
/* 1309 */         if (typeId != 0)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1315 */           if (extensionRegistry instanceof ExtensionRegistry)
/*      */           {
/* 1317 */             extension = target.findExtensionByNumber((ExtensionRegistry)extensionRegistry, type, typeId); } 
/*      */         }
/*      */         continue;
/*      */       } 
/* 1321 */       if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
/* 1322 */         if (typeId != 0 && 
/* 1323 */           extension != null && ExtensionRegistryLite.isEagerlyParseMessageSets()) {
/*      */ 
/*      */           
/* 1326 */           eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, target);
/* 1327 */           rawBytes = null;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1332 */         rawBytes = input.readBytes(); continue;
/*      */       } 
/* 1334 */       if (tag == WireFormat.MESSAGE_SET_ITEM_END_TAG) {
/*      */         break;
/*      */       }
/* 1337 */       if (!input.skipField(tag)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1342 */     input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
/*      */ 
/*      */     
/* 1345 */     if (rawBytes != null && typeId != 0) {
/* 1346 */       if (extension != null) {
/* 1347 */         mergeMessageSetExtensionFromBytes(rawBytes, extension, extensionRegistry, target);
/*      */       }
/* 1349 */       else if (rawBytes != null && unknownFields != null) {
/* 1350 */         unknownFields.mergeField(typeId, 
/* 1351 */             UnknownFieldSet.Field.newBuilder().addLengthDelimited(rawBytes).build());
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
/*      */   private static void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionRegistry.ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
/* 1364 */     Descriptors.FieldDescriptor field = extension.descriptor;
/* 1365 */     boolean hasOriginalValue = target.hasField(field);
/*      */     
/* 1367 */     if (hasOriginalValue || ExtensionRegistryLite.isEagerlyParseMessageSets()) {
/*      */ 
/*      */       
/* 1370 */       Object value = target.parseMessageFromBytes(rawBytes, extensionRegistry, field, extension.defaultInstance);
/*      */       
/* 1372 */       target.setField(field, value);
/*      */     } else {
/*      */       
/* 1375 */       LazyField lazyField = new LazyField(extension.defaultInstance, extensionRegistry, rawBytes);
/* 1376 */       target.setField(field, lazyField);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void eagerlyMergeMessageSetExtension(CodedInputStream input, ExtensionRegistry.ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
/* 1386 */     Descriptors.FieldDescriptor field = extension.descriptor;
/* 1387 */     Object value = target.parseMessage(input, extensionRegistry, field, extension.defaultInstance);
/* 1388 */     target.setField(field, value);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MessageReflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */