/*     */ package com.google.protobuf;
/*     */ 
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
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class GeneratedMessageV3
/*     */   extends GeneratedMessage.ExtendableMessage<GeneratedMessageV3>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   @Deprecated
/*     */   protected GeneratedMessageV3() {}
/*     */   
/*     */   @Deprecated
/*     */   protected GeneratedMessageV3(Builder<?> builder) {
/*  41 */     super(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Internal.IntList newIntList() {
/*  48 */     return new IntArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Internal.LongList newLongList() {
/*  55 */     return new LongArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Internal.FloatList newFloatList() {
/*  62 */     return new FloatArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Internal.DoubleList newDoubleList() {
/*  69 */     return new DoubleArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Internal.BooleanList newBooleanList() {
/*  76 */     return new BooleanArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static Internal.IntList mutableCopy(Internal.IntList list) {
/*  84 */     return (Internal.IntList)makeMutableCopy(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static Internal.LongList mutableCopy(Internal.LongList list) {
/*  92 */     return (Internal.LongList)makeMutableCopy(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static Internal.FloatList mutableCopy(Internal.FloatList list) {
/* 100 */     return (Internal.FloatList)makeMutableCopy(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static Internal.DoubleList mutableCopy(Internal.DoubleList list) {
/* 108 */     return (Internal.DoubleList)makeMutableCopy(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static Internal.BooleanList mutableCopy(Internal.BooleanList list) {
/* 116 */     return (Internal.BooleanList)makeMutableCopy(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected FieldAccessorTable internalGetFieldAccessorTable() {
/* 128 */     throw new UnsupportedOperationException("Should be overridden in gencode.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static final class UnusedPrivateParameter
/*     */   {
/* 140 */     static final UnusedPrivateParameter INSTANCE = new UnusedPrivateParameter();
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
/*     */   @Deprecated
/*     */   protected Object newInstance(UnusedPrivateParameter unused) {
/* 154 */     throw new UnsupportedOperationException("This method must be overridden by the subclass.");
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
/*     */   @Deprecated
/*     */   protected Message.Builder newBuilderForType(final AbstractMessage.BuilderParent parent) {
/* 172 */     return newBuilderForType(new BuilderParent()
/*     */         {
/*     */           public void markDirty()
/*     */           {
/* 176 */             parent.markDirty();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected abstract Message.Builder newBuilderForType(BuilderParent paramBuilderParent);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static abstract class Builder<BuilderT extends Builder<BuilderT>>
/*     */     extends GeneratedMessage.ExtendableBuilder<GeneratedMessageV3, BuilderT>
/*     */   {
/*     */     private BuilderParentImpl meAsParent;
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected Builder() {
/* 200 */       super((AbstractMessage.BuilderParent)null);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     protected Builder(GeneratedMessageV3.BuilderParent builderParent) {
/* 205 */       super(builderParent);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT clone() {
/* 217 */       return super.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT clear() {
/* 228 */       return super.clear();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
/* 240 */       throw new UnsupportedOperationException("Should be overridden in gencode.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT setField(Descriptors.FieldDescriptor field, Object value) {
/* 252 */       return super.setField(field, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT clearField(Descriptors.FieldDescriptor field) {
/* 264 */       return super.clearField(field);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT clearOneof(Descriptors.OneofDescriptor oneof) {
/* 276 */       return super.clearOneof(oneof);
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
/*     */     @Deprecated
/*     */     public BuilderT setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/* 289 */       return super.setRepeatedField(field, index, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/* 301 */       return super.addRepeatedField(field, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT setUnknownFields(UnknownFieldSet unknownFields) {
/* 313 */       return super.setUnknownFields(unknownFields);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT mergeUnknownFields(UnknownFieldSet unknownFields) {
/* 325 */       return super.mergeUnknownFields(unknownFields);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     private class BuilderParentImpl
/*     */       implements GeneratedMessageV3.BuilderParent {
/*     */       public void markDirty() {
/* 332 */         GeneratedMessageV3.Builder.this.onChanged();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private BuilderParentImpl() {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected GeneratedMessageV3.BuilderParent getParentForChildren() {
/* 346 */       if (this.meAsParent == null) {
/* 347 */         this.meAsParent = new BuilderParentImpl();
/*     */       }
/* 349 */       return this.meAsParent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static abstract class ExtendableMessage<MessageT extends ExtendableMessage<MessageT>>
/*     */     extends GeneratedMessageV3
/*     */     implements ExtendableMessageOrBuilder<MessageT>
/*     */   {
/*     */     @Deprecated
/*     */     protected ExtendableMessage() {}
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
/*     */     @Deprecated
/*     */     protected ExtendableMessage(GeneratedMessageV3.ExtendableBuilder<MessageT, ?> builder) {
/* 388 */       super(builder);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
/* 400 */       throw new UnsupportedOperationException("Should be overridden in gencode.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected class ExtensionWriter
/*     */       extends GeneratedMessage.ExtendableMessage.ExtensionWriter
/*     */     {
/*     */       private ExtensionWriter(boolean messageSetWireFormat) {
/* 414 */         super(messageSetWireFormat);
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
/*     */     @Deprecated
/*     */     protected ExtensionWriter newExtensionWriter() {
/* 428 */       return new ExtensionWriter(false);
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
/*     */     @Deprecated
/*     */     protected ExtensionWriter newMessageSetExtensionWriter() {
/* 441 */       return new ExtensionWriter(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static abstract class ExtendableBuilder<MessageT extends ExtendableMessage<MessageT>, BuilderT extends ExtendableBuilder<MessageT, BuilderT>>
/*     */     extends Builder<BuilderT>
/*     */     implements ExtendableMessageOrBuilder<MessageT>
/*     */   {
/*     */     @Deprecated
/*     */     protected ExtendableBuilder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected ExtendableBuilder(GeneratedMessageV3.BuilderParent parent) {
/* 467 */       super(parent);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public <T> BuilderT setExtension(GeneratedMessage.GeneratedExtension<MessageT, T> extension, T value) {
/* 479 */       return setExtension(extension, value);
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
/*     */     @Deprecated
/*     */     public <T> BuilderT setExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> extension, int index, T value) {
/* 493 */       return setExtension(extension, index, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public <T> BuilderT addExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> extension, T value) {
/* 505 */       return addExtension(extension, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public <T> BuilderT clearExtension(GeneratedMessage.GeneratedExtension<MessageT, T> extension) {
/* 517 */       return clearExtension(extension);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT setField(Descriptors.FieldDescriptor field, Object value) {
/* 529 */       return super.setField(field, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT clearField(Descriptors.FieldDescriptor field) {
/* 541 */       return super.clearField(field);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT clearOneof(Descriptors.OneofDescriptor oneof) {
/* 553 */       return super.clearOneof(oneof);
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
/*     */     @Deprecated
/*     */     public BuilderT setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/* 566 */       return super.setRepeatedField(field, index, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BuilderT addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/* 578 */       return super.addRepeatedField(field, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected final void mergeExtensionFields(GeneratedMessageV3.ExtendableMessage<?> other) {
/* 590 */       mergeExtensionFields(other);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final class FieldAccessorTable
/*     */     extends GeneratedMessage.FieldAccessorTable
/*     */   {
/*     */     @Deprecated
/*     */     public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] camelCaseNames, Class<? extends GeneratedMessageV3> messageClass, Class<? extends GeneratedMessageV3.Builder<?>> builderClass) {
/* 611 */       super(descriptor, camelCaseNames, (Class)messageClass, (Class)builderClass);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] camelCaseNames) {
/* 616 */       super(descriptor, camelCaseNames);
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
/*     */     @Deprecated
/*     */     public FieldAccessorTable ensureFieldAccessorsInitialized(Class<? extends GeneratedMessage> messageClass, Class<? extends GeneratedMessage.Builder<?>> builderClass) {
/* 630 */       return (FieldAccessorTable)super.ensureFieldAccessorsInitialized(messageClass, builderClass);
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected static interface BuilderParent extends AbstractMessage.BuilderParent {}
/*     */   
/*     */   @Deprecated
/*     */   public static interface ExtendableMessageOrBuilder<MessageT extends ExtendableMessage<MessageT>> extends GeneratedMessage.ExtendableMessageOrBuilder<GeneratedMessageV3> {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\GeneratedMessageV3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */