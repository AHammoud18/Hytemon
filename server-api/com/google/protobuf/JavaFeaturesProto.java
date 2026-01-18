/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ 
/*      */ public final class JavaFeaturesProto
/*      */   extends GeneratedFile {
/*      */   public static final int JAVA_FIELD_NUMBER = 1001;
/*      */   
/*      */   static {
/*   12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "JavaFeaturesProto");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registerAllExtensions(ExtensionRegistryLite registry) {
/*   22 */     registry.add(java_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerAllExtensions(ExtensionRegistry registry) {
/*   27 */     registerAllExtensions(registry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class JavaFeatures
/*      */     extends GeneratedMessage
/*      */     implements JavaFeaturesOrBuilder
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int bitField0_;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int LEGACY_CLOSED_ENUM_FIELD_NUMBER = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean legacyClosedEnum_;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int UTF8_VALIDATION_FIELD_NUMBER = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int utf8Validation_;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int LARGE_ENUM_FIELD_NUMBER = 3;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean largeEnum_;
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int USE_OLD_OUTER_CLASSNAME_DEFAULT_FIELD_NUMBER = 4;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean useOldOuterClassnameDefault_;
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int NEST_IN_FILE_CLASS_FIELD_NUMBER = 5;
/*      */ 
/*      */ 
/*      */     
/*      */     private int nestInFileClass_;
/*      */ 
/*      */ 
/*      */     
/*      */     private byte memoizedIsInitialized;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*   98 */       RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "JavaFeatures");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private JavaFeatures(GeneratedMessage.Builder<?> builder)
/*      */     {
/*  108 */       super(builder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  722 */       this.legacyClosedEnum_ = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  741 */       this.utf8Validation_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  759 */       this.largeEnum_ = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  778 */       this.useOldOuterClassnameDefault_ = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  797 */       this.nestInFileClass_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  814 */       this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)JavaFeatures.class, (Class)Builder.class); } public enum Utf8Validation implements ProtocolMessageEnum { UTF8_VALIDATION_UNKNOWN(0), DEFAULT(1), VERIFY(2); public static final int UTF8_VALIDATION_UNKNOWN_VALUE = 0; public static final int DEFAULT_VALUE = 1; public static final int VERIFY_VALUE = 2; private static final Internal.EnumLiteMap<Utf8Validation> internalValueMap = new Internal.EnumLiteMap<Utf8Validation>() { public JavaFeaturesProto.JavaFeatures.Utf8Validation findValueByNumber(int number) { return JavaFeaturesProto.JavaFeatures.Utf8Validation.forNumber(number); } }; private static final Utf8Validation[] VALUES = values(); private final int value; static {  } public final int getNumber() { return this.value; } public static Utf8Validation forNumber(int value) { switch (value) { case 0: return UTF8_VALIDATION_UNKNOWN;case 1: return DEFAULT;case 2: return VERIFY; }  return null; } public static Internal.EnumLiteMap<Utf8Validation> internalGetValueMap() { return internalValueMap; } public final Descriptors.EnumValueDescriptor getValueDescriptor() { return getDescriptor().getValues().get(ordinal()); } public final Descriptors.EnumDescriptor getDescriptorForType() { return getDescriptor(); } public static Descriptors.EnumDescriptor getDescriptor() { return JavaFeaturesProto.JavaFeatures.getDescriptor().getEnumTypes().get(0); } Utf8Validation(int value) { this.value = value; } } public static final class NestInFileClassFeature extends GeneratedMessage implements NestInFileClassFeatureOrBuilder { private static final long serialVersionUID = 0L; private byte memoizedIsInitialized; static { RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "NestInFileClassFeature"); } private NestInFileClassFeature(GeneratedMessage.Builder<?> builder) { super(builder); this.memoizedIsInitialized = -1; } private NestInFileClassFeature() { this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_NestInFileClassFeature_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_NestInFileClassFeature_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)NestInFileClassFeature.class, (Class)Builder.class); } public enum NestInFileClass implements ProtocolMessageEnum { NEST_IN_FILE_CLASS_UNKNOWN(0), NO(1), YES(2), LEGACY(3); public static final int NEST_IN_FILE_CLASS_UNKNOWN_VALUE = 0; public static final int NO_VALUE = 1; public static final int YES_VALUE = 2; public static final int LEGACY_VALUE = 3; private static final Internal.EnumLiteMap<NestInFileClass> internalValueMap = new Internal.EnumLiteMap<NestInFileClass>() { public JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass findValueByNumber(int number) { return JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass.forNumber(number); } }; private static final NestInFileClass[] VALUES = values(); private final int value; static {  } public final int getNumber() { return this.value; } public static NestInFileClass forNumber(int value) { switch (value) { case 0: return NEST_IN_FILE_CLASS_UNKNOWN;case 1: return NO;case 2: return YES;case 3: return LEGACY; }  return null; } public static Internal.EnumLiteMap<NestInFileClass> internalGetValueMap() { return internalValueMap; } public final Descriptors.EnumValueDescriptor getValueDescriptor() { return getDescriptor().getValues().get(ordinal()); } public final Descriptors.EnumDescriptor getDescriptorForType() { return getDescriptor(); } public static Descriptors.EnumDescriptor getDescriptor() { return JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.getDescriptor().getEnumTypes().get(0); } NestInFileClass(int value) { this.value = value; } } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized; if (isInitialized == 1) return true;  if (isInitialized == 0) return false;  this.memoizedIsInitialized = 1; return true; } public void writeTo(CodedOutputStream output) throws IOException { getUnknownFields().writeTo(output); } public int getSerializedSize() { int size = this.memoizedSize; if (size != -1) return size;  size = 0; size += getUnknownFields().getSerializedSize(); this.memoizedSize = size; return size; } public boolean equals(Object obj) { if (obj == this) return true;  if (!(obj instanceof NestInFileClassFeature)) return super.equals(obj);  NestInFileClassFeature other = (NestInFileClassFeature)obj; if (!getUnknownFields().equals(other.getUnknownFields())) return false;  return true; } public int hashCode() { if (this.memoizedHashCode != 0) return this.memoizedHashCode;  int hash = 41; hash = 19 * hash + getDescriptor().hashCode(); hash = 29 * hash + getUnknownFields().hashCode(); this.memoizedHashCode = hash; return hash; } public static NestInFileClassFeature parseFrom(ByteBuffer data) throws InvalidProtocolBufferException { return PARSER.parseFrom(data); } public static NestInFileClassFeature parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return PARSER.parseFrom(data, extensionRegistry); } public static NestInFileClassFeature parseFrom(ByteString data) throws InvalidProtocolBufferException { return PARSER.parseFrom(data); } public static NestInFileClassFeature parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return PARSER.parseFrom(data, extensionRegistry); } public static NestInFileClassFeature parseFrom(byte[] data) throws InvalidProtocolBufferException { return PARSER.parseFrom(data); } public static NestInFileClassFeature parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return PARSER.parseFrom(data, extensionRegistry); } public static NestInFileClassFeature parseFrom(InputStream input) throws IOException { return GeneratedMessage.<NestInFileClassFeature>parseWithIOException(PARSER, input); } public static NestInFileClassFeature parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return GeneratedMessage.<NestInFileClassFeature>parseWithIOException(PARSER, input, extensionRegistry); } public static NestInFileClassFeature parseDelimitedFrom(InputStream input) throws IOException { return GeneratedMessage.<NestInFileClassFeature>parseDelimitedWithIOException(PARSER, input); } public static NestInFileClassFeature parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return GeneratedMessage.<NestInFileClassFeature>parseDelimitedWithIOException(PARSER, input, extensionRegistry); } public static NestInFileClassFeature parseFrom(CodedInputStream input) throws IOException { return GeneratedMessage.<NestInFileClassFeature>parseWithIOException(PARSER, input); } public static NestInFileClassFeature parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return GeneratedMessage.<NestInFileClassFeature>parseWithIOException(PARSER, input, extensionRegistry); } public Builder newBuilderForType() { return newBuilder(); } public static Builder newBuilder() { return DEFAULT_INSTANCE.toBuilder(); } public static Builder newBuilder(NestInFileClassFeature prototype) { return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype); } public Builder toBuilder() { return (this == DEFAULT_INSTANCE) ? new Builder() : (new Builder()).mergeFrom(this); } protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) { Builder builder = new Builder(parent); return builder; } public static final class Builder extends GeneratedMessage.Builder<Builder> implements JavaFeaturesProto.JavaFeatures.NestInFileClassFeatureOrBuilder { public static final Descriptors.Descriptor getDescriptor() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_NestInFileClassFeature_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_NestInFileClassFeature_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.class, (Class)Builder.class); } private Builder() {} private Builder(AbstractMessage.BuilderParent parent) { super(parent); } public Builder clear() { super.clear(); return this; } public Descriptors.Descriptor getDescriptorForType() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_NestInFileClassFeature_descriptor; } public JavaFeaturesProto.JavaFeatures.NestInFileClassFeature getDefaultInstanceForType() { return JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.getDefaultInstance(); } public JavaFeaturesProto.JavaFeatures.NestInFileClassFeature build() { JavaFeaturesProto.JavaFeatures.NestInFileClassFeature result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JavaFeaturesProto.JavaFeatures.NestInFileClassFeature buildPartial() { JavaFeaturesProto.JavaFeatures.NestInFileClassFeature result = new JavaFeaturesProto.JavaFeatures.NestInFileClassFeature(this); onBuilt(); return result; } public Builder mergeFrom(Message other) { if (other instanceof JavaFeaturesProto.JavaFeatures.NestInFileClassFeature) return mergeFrom((JavaFeaturesProto.JavaFeatures.NestInFileClassFeature)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JavaFeaturesProto.JavaFeatures.NestInFileClassFeature other) { if (other == JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.getDefaultInstance()) return this;  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } } private static final NestInFileClassFeature DEFAULT_INSTANCE = new NestInFileClassFeature(); public static NestInFileClassFeature getDefaultInstance() { return DEFAULT_INSTANCE; } private static final Parser<NestInFileClassFeature> PARSER = new AbstractParser<NestInFileClassFeature>() { public JavaFeaturesProto.JavaFeatures.NestInFileClassFeature parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.Builder builder = JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.newBuilder(); try { builder.mergeFrom(input, extensionRegistry); } catch (InvalidProtocolBufferException e) { throw e.setUnfinishedMessage(builder.buildPartial()); } catch (UninitializedMessageException e) { throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial()); } catch (IOException e) { throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial()); }  return builder.buildPartial(); } }; public static Parser<NestInFileClassFeature> parser() { return PARSER; } public Parser<NestInFileClassFeature> getParserForType() { return PARSER; } public NestInFileClassFeature getDefaultInstanceForType() { return DEFAULT_INSTANCE; } } private JavaFeatures() { this.legacyClosedEnum_ = false; this.utf8Validation_ = 0; this.largeEnum_ = false; this.useOldOuterClassnameDefault_ = false; this.nestInFileClass_ = 0; this.memoizedIsInitialized = -1; this.utf8Validation_ = 0; this.nestInFileClass_ = 0; } public enum NestInFileClass implements ProtocolMessageEnum {
/*      */       NEST_IN_FILE_CLASS_UNKNOWN(0), NO(1), YES(2), LEGACY(3); public static final int NEST_IN_FILE_CLASS_UNKNOWN_VALUE = 0; public static final int NO_VALUE = 1; public static final int YES_VALUE = 2; public static final int LEGACY_VALUE = 3; private static final Internal.EnumLiteMap<NestInFileClass> internalValueMap = new Internal.EnumLiteMap<NestInFileClass>() { public JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass findValueByNumber(int number) { return JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass.forNumber(number); } }
/*      */       ; private static final NestInFileClass[] VALUES = values(); private final int value; static {  } public final int getNumber() { return this.value; } public static NestInFileClass forNumber(int value) { switch (value) { case 0: return NEST_IN_FILE_CLASS_UNKNOWN;case 1: return NO;case 2: return YES;case 3: return LEGACY; }  return null; } public static Internal.EnumLiteMap<NestInFileClass> internalGetValueMap() { return internalValueMap; } public final Descriptors.EnumValueDescriptor getValueDescriptor() { return getDescriptor().getValues().get(ordinal()); } public final Descriptors.EnumDescriptor getDescriptorForType() { return getDescriptor(); } public static Descriptors.EnumDescriptor getDescriptor() { return JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.getDescriptor().getEnumTypes().get(0); } NestInFileClass(int value) { this.value = value; }
/*  817 */     } public boolean hasLegacyClosedEnum() { return ((this.bitField0_ & 0x1) != 0); } public boolean getLegacyClosedEnum() { return this.legacyClosedEnum_; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  818 */       if (isInitialized == 1) return true; 
/*  819 */       if (isInitialized == 0) return false;
/*      */       
/*  821 */       this.memoizedIsInitialized = 1;
/*  822 */       return true; } public boolean hasUtf8Validation() { return ((this.bitField0_ & 0x2) != 0); } public Utf8Validation getUtf8Validation() { Utf8Validation result = Utf8Validation.forNumber(this.utf8Validation_); return (result == null) ? Utf8Validation.UTF8_VALIDATION_UNKNOWN : result; } public boolean hasLargeEnum() { return ((this.bitField0_ & 0x4) != 0); }
/*      */     public boolean getLargeEnum() { return this.largeEnum_; }
/*      */     public boolean hasUseOldOuterClassnameDefault() { return ((this.bitField0_ & 0x8) != 0); }
/*      */     public boolean getUseOldOuterClassnameDefault() { return this.useOldOuterClassnameDefault_; }
/*      */     public boolean hasNestInFileClass() { return ((this.bitField0_ & 0x10) != 0); }
/*      */     public NestInFileClassFeature.NestInFileClass getNestInFileClass() { NestInFileClassFeature.NestInFileClass result = NestInFileClassFeature.NestInFileClass.forNumber(this.nestInFileClass_); return (result == null) ? NestInFileClassFeature.NestInFileClass.NEST_IN_FILE_CLASS_UNKNOWN : result; }
/*  828 */     public void writeTo(CodedOutputStream output) throws IOException { if ((this.bitField0_ & 0x1) != 0) {
/*  829 */         output.writeBool(1, this.legacyClosedEnum_);
/*      */       }
/*  831 */       if ((this.bitField0_ & 0x2) != 0) {
/*  832 */         output.writeEnum(2, this.utf8Validation_);
/*      */       }
/*  834 */       if ((this.bitField0_ & 0x4) != 0) {
/*  835 */         output.writeBool(3, this.largeEnum_);
/*      */       }
/*  837 */       if ((this.bitField0_ & 0x8) != 0) {
/*  838 */         output.writeBool(4, this.useOldOuterClassnameDefault_);
/*      */       }
/*  840 */       if ((this.bitField0_ & 0x10) != 0) {
/*  841 */         output.writeEnum(5, this.nestInFileClass_);
/*      */       }
/*  843 */       getUnknownFields().writeTo(output); }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSerializedSize() {
/*  848 */       int size = this.memoizedSize;
/*  849 */       if (size != -1) return size;
/*      */       
/*  851 */       size = 0;
/*  852 */       if ((this.bitField0_ & 0x1) != 0) {
/*  853 */         size += 
/*  854 */           CodedOutputStream.computeBoolSize(1, this.legacyClosedEnum_);
/*      */       }
/*  856 */       if ((this.bitField0_ & 0x2) != 0) {
/*  857 */         size += 
/*  858 */           CodedOutputStream.computeEnumSize(2, this.utf8Validation_);
/*      */       }
/*  860 */       if ((this.bitField0_ & 0x4) != 0) {
/*  861 */         size += 
/*  862 */           CodedOutputStream.computeBoolSize(3, this.largeEnum_);
/*      */       }
/*  864 */       if ((this.bitField0_ & 0x8) != 0) {
/*  865 */         size += 
/*  866 */           CodedOutputStream.computeBoolSize(4, this.useOldOuterClassnameDefault_);
/*      */       }
/*  868 */       if ((this.bitField0_ & 0x10) != 0) {
/*  869 */         size += 
/*  870 */           CodedOutputStream.computeEnumSize(5, this.nestInFileClass_);
/*      */       }
/*  872 */       size += getUnknownFields().getSerializedSize();
/*  873 */       this.memoizedSize = size;
/*  874 */       return size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  879 */       if (obj == this) {
/*  880 */         return true;
/*      */       }
/*  882 */       if (!(obj instanceof JavaFeatures)) {
/*  883 */         return super.equals(obj);
/*      */       }
/*  885 */       JavaFeatures other = (JavaFeatures)obj;
/*      */       
/*  887 */       if (hasLegacyClosedEnum() != other.hasLegacyClosedEnum()) return false; 
/*  888 */       if (hasLegacyClosedEnum() && 
/*  889 */         getLegacyClosedEnum() != other
/*  890 */         .getLegacyClosedEnum()) return false;
/*      */       
/*  892 */       if (hasUtf8Validation() != other.hasUtf8Validation()) return false; 
/*  893 */       if (hasUtf8Validation() && 
/*  894 */         this.utf8Validation_ != other.utf8Validation_) return false;
/*      */       
/*  896 */       if (hasLargeEnum() != other.hasLargeEnum()) return false; 
/*  897 */       if (hasLargeEnum() && 
/*  898 */         getLargeEnum() != other
/*  899 */         .getLargeEnum()) return false;
/*      */       
/*  901 */       if (hasUseOldOuterClassnameDefault() != other.hasUseOldOuterClassnameDefault()) return false; 
/*  902 */       if (hasUseOldOuterClassnameDefault() && 
/*  903 */         getUseOldOuterClassnameDefault() != other
/*  904 */         .getUseOldOuterClassnameDefault()) return false;
/*      */       
/*  906 */       if (hasNestInFileClass() != other.hasNestInFileClass()) return false; 
/*  907 */       if (hasNestInFileClass() && 
/*  908 */         this.nestInFileClass_ != other.nestInFileClass_) return false;
/*      */       
/*  910 */       if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  911 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  916 */       if (this.memoizedHashCode != 0) {
/*  917 */         return this.memoizedHashCode;
/*      */       }
/*  919 */       int hash = 41;
/*  920 */       hash = 19 * hash + getDescriptor().hashCode();
/*  921 */       if (hasLegacyClosedEnum()) {
/*  922 */         hash = 37 * hash + 1;
/*  923 */         hash = 53 * hash + Internal.hashBoolean(
/*  924 */             getLegacyClosedEnum());
/*      */       } 
/*  926 */       if (hasUtf8Validation()) {
/*  927 */         hash = 37 * hash + 2;
/*  928 */         hash = 53 * hash + this.utf8Validation_;
/*      */       } 
/*  930 */       if (hasLargeEnum()) {
/*  931 */         hash = 37 * hash + 3;
/*  932 */         hash = 53 * hash + Internal.hashBoolean(
/*  933 */             getLargeEnum());
/*      */       } 
/*  935 */       if (hasUseOldOuterClassnameDefault()) {
/*  936 */         hash = 37 * hash + 4;
/*  937 */         hash = 53 * hash + Internal.hashBoolean(
/*  938 */             getUseOldOuterClassnameDefault());
/*      */       } 
/*  940 */       if (hasNestInFileClass()) {
/*  941 */         hash = 37 * hash + 5;
/*  942 */         hash = 53 * hash + this.nestInFileClass_;
/*      */       } 
/*  944 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  945 */       this.memoizedHashCode = hash;
/*  946 */       return hash;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  952 */       return PARSER.parseFrom(data);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  958 */       return PARSER.parseFrom(data, extensionRegistry);
/*      */     }
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  963 */       return PARSER.parseFrom(data);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  969 */       return PARSER.parseFrom(data, extensionRegistry);
/*      */     }
/*      */     
/*      */     public static JavaFeatures parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  973 */       return PARSER.parseFrom(data);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  979 */       return PARSER.parseFrom(data, extensionRegistry);
/*      */     }
/*      */     
/*      */     public static JavaFeatures parseFrom(InputStream input) throws IOException {
/*  983 */       return 
/*  984 */         GeneratedMessage.<JavaFeatures>parseWithIOException(PARSER, input);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  990 */       return 
/*  991 */         GeneratedMessage.<JavaFeatures>parseWithIOException(PARSER, input, extensionRegistry);
/*      */     }
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseDelimitedFrom(InputStream input) throws IOException {
/*  996 */       return 
/*  997 */         GeneratedMessage.<JavaFeatures>parseDelimitedWithIOException(PARSER, input);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1004 */       return 
/* 1005 */         GeneratedMessage.<JavaFeatures>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */     }
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(CodedInputStream input) throws IOException {
/* 1010 */       return 
/* 1011 */         GeneratedMessage.<JavaFeatures>parseWithIOException(PARSER, input);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static JavaFeatures parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1017 */       return 
/* 1018 */         GeneratedMessage.<JavaFeatures>parseWithIOException(PARSER, input, extensionRegistry);
/*      */     }
/*      */     
/*      */     public Builder newBuilderForType() {
/* 1022 */       return newBuilder();
/*      */     } public static Builder newBuilder() {
/* 1024 */       return DEFAULT_INSTANCE.toBuilder();
/*      */     }
/*      */     public static Builder newBuilder(JavaFeatures prototype) {
/* 1027 */       return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */     }
/*      */     
/*      */     public Builder toBuilder() {
/* 1031 */       return (this == DEFAULT_INSTANCE) ? 
/* 1032 */         new Builder() : (new Builder()).mergeFrom(this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 1038 */       Builder builder = new Builder(parent);
/* 1039 */       return builder;
/*      */     }
/*      */     
/*      */     public static final class Builder extends GeneratedMessage.Builder<Builder> implements JavaFeaturesProto.JavaFeaturesOrBuilder { private int bitField0_;
/*      */       private boolean legacyClosedEnum_;
/*      */       private int utf8Validation_;
/*      */       private boolean largeEnum_;
/*      */       private boolean useOldOuterClassnameDefault_;
/*      */       private int nestInFileClass_;
/*      */       
/*      */       public static final Descriptors.Descriptor getDescriptor() {
/* 1050 */         return JavaFeaturesProto.internal_static_pb_JavaFeatures_descriptor;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 1056 */         return JavaFeaturesProto.internal_static_pb_JavaFeatures_fieldAccessorTable
/* 1057 */           .ensureFieldAccessorsInitialized((Class)JavaFeaturesProto.JavaFeatures.class, (Class)Builder.class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private Builder()
/*      */       {
/* 1286 */         this.utf8Validation_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1406 */         this.nestInFileClass_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.utf8Validation_ = 0; this.nestInFileClass_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.legacyClosedEnum_ = false; this.utf8Validation_ = 0; this.largeEnum_ = false; this.useOldOuterClassnameDefault_ = false; this.nestInFileClass_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return JavaFeaturesProto.internal_static_pb_JavaFeatures_descriptor; } public JavaFeaturesProto.JavaFeatures getDefaultInstanceForType() { return JavaFeaturesProto.JavaFeatures.getDefaultInstance(); } public JavaFeaturesProto.JavaFeatures build() { JavaFeaturesProto.JavaFeatures result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JavaFeaturesProto.JavaFeatures buildPartial() { JavaFeaturesProto.JavaFeatures result = new JavaFeaturesProto.JavaFeatures(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JavaFeaturesProto.JavaFeatures result) { int from_bitField0_ = this.bitField0_; int to_bitField0_ = 0; if ((from_bitField0_ & 0x1) != 0) { result.legacyClosedEnum_ = this.legacyClosedEnum_; to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x2) != 0) { result.utf8Validation_ = this.utf8Validation_; to_bitField0_ |= 0x2; }  if ((from_bitField0_ & 0x4) != 0) { result.largeEnum_ = this.largeEnum_; to_bitField0_ |= 0x4; }  if ((from_bitField0_ & 0x8) != 0) { result.useOldOuterClassnameDefault_ = this.useOldOuterClassnameDefault_; to_bitField0_ |= 0x8; }  if ((from_bitField0_ & 0x10) != 0) { result.nestInFileClass_ = this.nestInFileClass_; to_bitField0_ |= 0x10; }  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof JavaFeaturesProto.JavaFeatures) return mergeFrom((JavaFeaturesProto.JavaFeatures)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JavaFeaturesProto.JavaFeatures other) { if (other == JavaFeaturesProto.JavaFeatures.getDefaultInstance()) return this;  if (other.hasLegacyClosedEnum()) setLegacyClosedEnum(other.getLegacyClosedEnum());  if (other.hasUtf8Validation()) setUtf8Validation(other.getUtf8Validation());  if (other.hasLargeEnum()) setLargeEnum(other.getLargeEnum());  if (other.hasUseOldOuterClassnameDefault()) setUseOldOuterClassnameDefault(other.getUseOldOuterClassnameDefault());  if (other.hasNestInFileClass()) setNestInFileClass(other.getNestInFileClass());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tmpRaw; JavaFeaturesProto.JavaFeatures.Utf8Validation utf8Validation; JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass tmpValue; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.legacyClosedEnum_ = input.readBool(); this.bitField0_ |= 0x1; continue;case 16: tmpRaw = input.readEnum(); utf8Validation = JavaFeaturesProto.JavaFeatures.Utf8Validation.forNumber(tmpRaw); if (utf8Validation == null) { mergeUnknownVarintField(2, tmpRaw); continue; }  this.utf8Validation_ = tmpRaw; this.bitField0_ |= 0x2; continue;case 24: this.largeEnum_ = input.readBool(); this.bitField0_ |= 0x4; continue;case 32: this.useOldOuterClassnameDefault_ = input.readBool(); this.bitField0_ |= 0x8; continue;case 40: tmpRaw = input.readEnum(); tmpValue = JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass.forNumber(tmpRaw); if (tmpValue == null) { mergeUnknownVarintField(5, tmpRaw); continue; }  this.nestInFileClass_ = tmpRaw; this.bitField0_ |= 0x10; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public boolean hasLegacyClosedEnum() { return ((this.bitField0_ & 0x1) != 0); } public boolean getLegacyClosedEnum() { return this.legacyClosedEnum_; } public Builder setLegacyClosedEnum(boolean value) { this.legacyClosedEnum_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearLegacyClosedEnum() { this.bitField0_ &= 0xFFFFFFFE; this.legacyClosedEnum_ = false; onChanged(); return this; } public boolean hasUtf8Validation() { return ((this.bitField0_ & 0x2) != 0); } public JavaFeaturesProto.JavaFeatures.Utf8Validation getUtf8Validation() { JavaFeaturesProto.JavaFeatures.Utf8Validation result = JavaFeaturesProto.JavaFeatures.Utf8Validation.forNumber(this.utf8Validation_); return (result == null) ? JavaFeaturesProto.JavaFeatures.Utf8Validation.UTF8_VALIDATION_UNKNOWN : result; } public Builder setUtf8Validation(JavaFeaturesProto.JavaFeatures.Utf8Validation value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.utf8Validation_ = value.getNumber(); onChanged(); return this; } public Builder clearUtf8Validation() { this.bitField0_ &= 0xFFFFFFFD; this.utf8Validation_ = 0; onChanged(); return this; } public boolean hasLargeEnum() { return ((this.bitField0_ & 0x4) != 0); } public boolean getLargeEnum() { return this.largeEnum_; } public Builder setLargeEnum(boolean value) { this.largeEnum_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*      */       public Builder clearLargeEnum() { this.bitField0_ &= 0xFFFFFFFB; this.largeEnum_ = false; onChanged(); return this; }
/*      */       public boolean hasUseOldOuterClassnameDefault() { return ((this.bitField0_ & 0x8) != 0); }
/*      */       public boolean getUseOldOuterClassnameDefault() { return this.useOldOuterClassnameDefault_; }
/*      */       public Builder setUseOldOuterClassnameDefault(boolean value) { this.useOldOuterClassnameDefault_ = value; this.bitField0_ |= 0x8; onChanged(); return this; }
/*      */       public Builder clearUseOldOuterClassnameDefault() { this.bitField0_ &= 0xFFFFFFF7; this.useOldOuterClassnameDefault_ = false; onChanged(); return this; }
/* 1412 */       public boolean hasNestInFileClass() { return ((this.bitField0_ & 0x10) != 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass getNestInFileClass() {
/* 1420 */         JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass result = JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass.forNumber(this.nestInFileClass_);
/* 1421 */         return (result == null) ? JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass.NEST_IN_FILE_CLASS_UNKNOWN : result;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder setNestInFileClass(JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass value) {
/* 1429 */         if (value == null) throw new NullPointerException(); 
/* 1430 */         this.bitField0_ |= 0x10;
/* 1431 */         this.nestInFileClass_ = value.getNumber();
/* 1432 */         onChanged();
/* 1433 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder clearNestInFileClass() {
/* 1440 */         this.bitField0_ &= 0xFFFFFFEF;
/* 1441 */         this.nestInFileClass_ = 0;
/* 1442 */         onChanged();
/* 1443 */         return this;
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1452 */     private static final JavaFeatures DEFAULT_INSTANCE = new JavaFeatures();
/*      */ 
/*      */     
/*      */     public static JavaFeatures getDefaultInstance() {
/* 1456 */       return DEFAULT_INSTANCE;
/*      */     }
/*      */ 
/*      */     
/* 1460 */     private static final Parser<JavaFeatures> PARSER = new AbstractParser<JavaFeatures>()
/*      */       {
/*      */ 
/*      */         
/*      */         public JavaFeaturesProto.JavaFeatures parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */         {
/* 1466 */           JavaFeaturesProto.JavaFeatures.Builder builder = JavaFeaturesProto.JavaFeatures.newBuilder();
/*      */           try {
/* 1468 */             builder.mergeFrom(input, extensionRegistry);
/* 1469 */           } catch (InvalidProtocolBufferException e) {
/* 1470 */             throw e.setUnfinishedMessage(builder.buildPartial());
/* 1471 */           } catch (UninitializedMessageException e) {
/* 1472 */             throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1473 */           } catch (IOException e) {
/* 1474 */             throw (new InvalidProtocolBufferException(e))
/* 1475 */               .setUnfinishedMessage(builder.buildPartial());
/*      */           } 
/* 1477 */           return builder.buildPartial();
/*      */         }
/*      */       };
/*      */     
/*      */     public static Parser<JavaFeatures> parser() {
/* 1482 */       return PARSER;
/*      */     }
/*      */ 
/*      */     
/*      */     public Parser<JavaFeatures> getParserForType() {
/* 1487 */       return PARSER;
/*      */     }
/*      */ 
/*      */     
/*      */     public JavaFeatures getDefaultInstanceForType() {
/* 1492 */       return DEFAULT_INSTANCE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static interface NestInFileClassFeatureOrBuilder
/*      */       extends MessageOrBuilder {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1505 */   public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.FeatureSet, JavaFeatures> java_ = GeneratedMessage.newFileScopedGeneratedExtension(JavaFeatures.class, 
/*      */       
/* 1507 */       JavaFeatures.getDefaultInstance());
/*      */   
/*      */   private static final Descriptors.Descriptor internal_static_pb_JavaFeatures_descriptor;
/*      */   
/*      */   private static final GeneratedMessage.FieldAccessorTable internal_static_pb_JavaFeatures_fieldAccessorTable;
/*      */   
/*      */   private static final Descriptors.Descriptor internal_static_pb_JavaFeatures_NestInFileClassFeature_descriptor;
/*      */   
/*      */   private static final GeneratedMessage.FieldAccessorTable internal_static_pb_JavaFeatures_NestInFileClassFeature_fieldAccessorTable;
/*      */   
/*      */   private static Descriptors.FileDescriptor descriptor;
/*      */ 
/*      */   
/*      */   public static Descriptors.FileDescriptor getDescriptor() {
/* 1521 */     return descriptor;
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/* 1526 */     String[] descriptorData = { "\n#google/protobuf/java_features.proto\022\002pb\032 google/protobuf/descriptor.proto\"ß\b\n\fJavaFeatures\022\002\n\022legacy_closed_enum\030\001 \001(\bBá\001\001\001\001\004\001\001¢\001\t\022\004true\030\007¢\001\n\022\005false\030ç\007²\001»\001\bè\007\020è\007\032²\001The legacy closed enum behavior in Java is deprecated and is scheduled to be removed in edition 2025.  See http://protobuf.dev/programming-guides/enum/#java for more information.R\020legacyClosedEnum\022¯\002\n\017utf8_validation\030\002 \001(\0162\037.pb.JavaFeatures.Utf8ValidationBä\001\001\001\001\004\001\001¢\001\f\022\007DEFAULT\030\007²\001È\001\bè\007\020é\007\032¿\001The Java-specific utf8 validation feature is deprecated and is scheduled to be removed in edition 2025.  Utf8 validation behavior should use the global cross-language utf8_validation feature.R\016utf8Validation\022;\n\nlarge_enum\030\003 \001(\bB\034\001\001\001\006\001\001¢\001\n\022\005false\030\007²\001\003\bé\007R\tlargeEnum\022n\n\037use_old_outer_classname_default\030\004 \001(\bB(\001\001\001\001¢\001\t\022\004true\030\007¢\001\n\022\005false\030é\007²\001\006\bé\007 é\007R\033useOldOuterClassnameDefault\022\001\n\022nest_in_file_class\030\005 \001(\01627.pb.JavaFeatures.NestInFileClassFeature.NestInFileClassB*\001\001\001\003\001\006\001\b¢\001\013\022\006LEGACY\030\007¢\001\007\022\002NO\030é\007²\001\003\bé\007R\017nestInFileClass\032|\n\026NestInFileClassFeature\"X\n\017NestInFileClass\022\036\n\032NEST_IN_FILE_CLASS_UNKNOWN\020\000\022\006\n\002NO\020\001\022\007\n\003YES\020\002\022\024\n\006LEGACY\020\003\032\b\"\006\bé\007 é\007J\b\b\001\020\002\"F\n\016Utf8Validation\022\033\n\027UTF8_VALIDATION_UNKNOWN\020\000\022\013\n\007DEFAULT\020\001\022\n\n\006VERIFY\020\002J\004\b\006\020\007:B\n\004java\022\033.google.protobuf.FeatureSet\030é\007 \001(\0132\020.pb.JavaFeaturesR\004javaB(\n\023com.google.protobufB\021JavaFeaturesProto" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1562 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*      */           
/* 1564 */           DescriptorProtos.getDescriptor()
/*      */         });
/*      */     
/* 1567 */     internal_static_pb_JavaFeatures_descriptor = getDescriptor().getMessageType(0);
/* 1568 */     internal_static_pb_JavaFeatures_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_pb_JavaFeatures_descriptor, new String[] { "LegacyClosedEnum", "Utf8Validation", "LargeEnum", "UseOldOuterClassnameDefault", "NestInFileClass" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1573 */     internal_static_pb_JavaFeatures_NestInFileClassFeature_descriptor = internal_static_pb_JavaFeatures_descriptor.getNestedType(0);
/* 1574 */     internal_static_pb_JavaFeatures_NestInFileClassFeature_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_pb_JavaFeatures_NestInFileClassFeature_descriptor, new String[0]);
/*      */ 
/*      */ 
/*      */     
/* 1578 */     java_.internalInit(descriptor.getExtension(0));
/* 1579 */     descriptor.resolveAllFeaturesImmutable();
/* 1580 */     DescriptorProtos.getDescriptor();
/*      */   }
/*      */   
/*      */   public static interface JavaFeaturesOrBuilder extends MessageOrBuilder {
/*      */     boolean hasLegacyClosedEnum();
/*      */     
/*      */     boolean getLegacyClosedEnum();
/*      */     
/*      */     boolean hasUtf8Validation();
/*      */     
/*      */     JavaFeaturesProto.JavaFeatures.Utf8Validation getUtf8Validation();
/*      */     
/*      */     boolean hasLargeEnum();
/*      */     
/*      */     boolean getLargeEnum();
/*      */     
/*      */     boolean hasUseOldOuterClassnameDefault();
/*      */     
/*      */     boolean getUseOldOuterClassnameDefault();
/*      */     
/*      */     boolean hasNestInFileClass();
/*      */     
/*      */     JavaFeaturesProto.JavaFeatures.NestInFileClassFeature.NestInFileClass getNestInFileClass();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\JavaFeaturesProto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */