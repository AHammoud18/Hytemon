/*      */ package com.google.crypto.tink.proto;
/*      */ import com.google.protobuf.AbstractMessage;
/*      */ import com.google.protobuf.AbstractMessageLite;
/*      */ import com.google.protobuf.ByteString;
/*      */ import com.google.protobuf.CodedInputStream;
/*      */ import com.google.protobuf.Descriptors;
/*      */ import com.google.protobuf.ExtensionRegistryLite;
/*      */ import com.google.protobuf.GeneratedMessage;
/*      */ import com.google.protobuf.InvalidProtocolBufferException;
/*      */ import com.google.protobuf.Message;
/*      */ import com.google.protobuf.MessageLite;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ public final class KeysetInfo extends GeneratedMessage implements KeysetInfoOrBuilder {
/*      */   private static final long serialVersionUID = 0L;
/*      */   public static final int PRIMARY_KEY_ID_FIELD_NUMBER = 1;
/*      */   private int primaryKeyId_;
/*      */   public static final int KEY_INFO_FIELD_NUMBER = 2;
/*      */   private List<KeyInfo> keyInfo_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   24 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KeysetInfo.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   30 */         .getName());
/*      */   }
/*      */   
/*      */   private KeysetInfo(GeneratedMessage.Builder<?> builder) {
/*   34 */     super(builder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  979 */     this.primaryKeyId_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1059 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_KeysetInfo_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_KeysetInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(KeysetInfo.class, Builder.class); } private KeysetInfo() { this.primaryKeyId_ = 0; this.memoizedIsInitialized = -1; this.keyInfo_ = Collections.emptyList(); } public static final class KeyInfo extends GeneratedMessage implements KeyInfoOrBuilder {
/*      */     private static final long serialVersionUID = 0L; public static final int TYPE_URL_FIELD_NUMBER = 1; private volatile Object typeUrl_; public static final int STATUS_FIELD_NUMBER = 2; private int status_; public static final int KEY_ID_FIELD_NUMBER = 3; private int keyId_; public static final int OUTPUT_PREFIX_TYPE_FIELD_NUMBER = 4; private int outputPrefixType_; private byte memoizedIsInitialized; static { RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KeyInfo.class.getName()); } private KeyInfo(GeneratedMessage.Builder<?> builder) { super(builder); this.typeUrl_ = ""; this.status_ = 0; this.keyId_ = 0; this.outputPrefixType_ = 0; this.memoizedIsInitialized = -1; } private KeyInfo() { this.typeUrl_ = ""; this.status_ = 0; this.keyId_ = 0; this.outputPrefixType_ = 0; this.memoizedIsInitialized = -1; this.typeUrl_ = ""; this.status_ = 0; this.outputPrefixType_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_KeysetInfo_KeyInfo_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_KeysetInfo_KeyInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(KeyInfo.class, Builder.class); } public String getTypeUrl() { Object ref = this.typeUrl_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; } public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; } public int getStatusValue() { return this.status_; } public KeyStatusType getStatus() { KeyStatusType result = KeyStatusType.forNumber(this.status_); return (result == null) ? KeyStatusType.UNRECOGNIZED : result; } public int getKeyId() { return this.keyId_; } public int getOutputPrefixTypeValue() { return this.outputPrefixType_; } public OutputPrefixType getOutputPrefixType() { OutputPrefixType result = OutputPrefixType.forNumber(this.outputPrefixType_); return (result == null) ? OutputPrefixType.UNRECOGNIZED : result; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized; if (isInitialized == 1) return true;  if (isInitialized == 0) return false;  this.memoizedIsInitialized = 1; return true; } public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) GeneratedMessage.writeString(output, 1, this.typeUrl_);  if (this.status_ != KeyStatusType.UNKNOWN_STATUS.getNumber()) output.writeEnum(2, this.status_);  if (this.keyId_ != 0) output.writeUInt32(3, this.keyId_);  if (this.outputPrefixType_ != OutputPrefixType.UNKNOWN_PREFIX.getNumber()) output.writeEnum(4, this.outputPrefixType_);  getUnknownFields().writeTo(output); } public int getSerializedSize() { int size = this.memoizedSize; if (size != -1) return size;  size = 0; if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) size += GeneratedMessage.computeStringSize(1, this.typeUrl_);  if (this.status_ != KeyStatusType.UNKNOWN_STATUS.getNumber()) size += CodedOutputStream.computeEnumSize(2, this.status_);  if (this.keyId_ != 0) size += CodedOutputStream.computeUInt32Size(3, this.keyId_);  if (this.outputPrefixType_ != OutputPrefixType.UNKNOWN_PREFIX.getNumber()) size += CodedOutputStream.computeEnumSize(4, this.outputPrefixType_);  size += getUnknownFields().getSerializedSize(); this.memoizedSize = size; return size; } public boolean equals(Object obj) { if (obj == this) return true;  if (!(obj instanceof KeyInfo)) return super.equals(obj);  KeyInfo other = (KeyInfo)obj; if (!getTypeUrl().equals(other.getTypeUrl())) return false;  if (this.status_ != other.status_) return false;  if (getKeyId() != other.getKeyId()) return false;  if (this.outputPrefixType_ != other.outputPrefixType_) return false;  if (!getUnknownFields().equals(other.getUnknownFields())) return false;  return true; } public int hashCode() { if (this.memoizedHashCode != 0) return this.memoizedHashCode;  int hash = 41; hash = 19 * hash + getDescriptor().hashCode(); hash = 37 * hash + 1; hash = 53 * hash + getTypeUrl().hashCode(); hash = 37 * hash + 2; hash = 53 * hash + this.status_; hash = 37 * hash + 3; hash = 53 * hash + getKeyId(); hash = 37 * hash + 4; hash = 53 * hash + this.outputPrefixType_; hash = 29 * hash + getUnknownFields().hashCode(); this.memoizedHashCode = hash; return hash; } public static KeyInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException { return (KeyInfo)PARSER.parseFrom(data); } public static KeyInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (KeyInfo)PARSER.parseFrom(data, extensionRegistry); } public static KeyInfo parseFrom(ByteString data) throws InvalidProtocolBufferException { return (KeyInfo)PARSER.parseFrom(data); } public static KeyInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (KeyInfo)PARSER.parseFrom(data, extensionRegistry); } public static KeyInfo parseFrom(byte[] data) throws InvalidProtocolBufferException { return (KeyInfo)PARSER.parseFrom(data); } public static KeyInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (KeyInfo)PARSER.parseFrom(data, extensionRegistry); } public static KeyInfo parseFrom(InputStream input) throws IOException { return (KeyInfo)GeneratedMessage.parseWithIOException(PARSER, input); } public static KeyInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (KeyInfo)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public static KeyInfo parseDelimitedFrom(InputStream input) throws IOException { return (KeyInfo)GeneratedMessage.parseDelimitedWithIOException(PARSER, input); } public static KeyInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (KeyInfo)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry); } public static KeyInfo parseFrom(CodedInputStream input) throws IOException { return (KeyInfo)GeneratedMessage.parseWithIOException(PARSER, input); } public static KeyInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (KeyInfo)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public Builder newBuilderForType() { return newBuilder(); } public static Builder newBuilder() { return DEFAULT_INSTANCE.toBuilder(); } public static Builder newBuilder(KeyInfo prototype) { return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype); } public Builder toBuilder() { return (this == DEFAULT_INSTANCE) ? new Builder() : (new Builder()).mergeFrom(this); } protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) { Builder builder = new Builder(parent); return builder; } public static final class Builder extends GeneratedMessage.Builder<Builder> implements KeysetInfo.KeyInfoOrBuilder {
/*      */       private int bitField0_; private Object typeUrl_; private int status_; private int keyId_; private int outputPrefixType_; public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_KeysetInfo_KeyInfo_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_KeysetInfo_KeyInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(KeysetInfo.KeyInfo.class, Builder.class); } private Builder() { this.typeUrl_ = ""; this.status_ = 0; this.outputPrefixType_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.typeUrl_ = ""; this.status_ = 0; this.outputPrefixType_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.typeUrl_ = ""; this.status_ = 0; this.keyId_ = 0; this.outputPrefixType_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return Tink.internal_static_google_crypto_tink_KeysetInfo_KeyInfo_descriptor; } public KeysetInfo.KeyInfo getDefaultInstanceForType() { return KeysetInfo.KeyInfo.getDefaultInstance(); } public KeysetInfo.KeyInfo build() { KeysetInfo.KeyInfo result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public KeysetInfo.KeyInfo buildPartial() { KeysetInfo.KeyInfo result = new KeysetInfo.KeyInfo(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(KeysetInfo.KeyInfo result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.typeUrl_ = this.typeUrl_;  if ((from_bitField0_ & 0x2) != 0) result.status_ = this.status_;  if ((from_bitField0_ & 0x4) != 0) result.keyId_ = this.keyId_;  if ((from_bitField0_ & 0x8) != 0) result.outputPrefixType_ = this.outputPrefixType_;  } public Builder mergeFrom(Message other) { if (other instanceof KeysetInfo.KeyInfo) return mergeFrom((KeysetInfo.KeyInfo)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(KeysetInfo.KeyInfo other) { if (other == KeysetInfo.KeyInfo.getDefaultInstance()) return this;  if (!other.getTypeUrl().isEmpty()) { this.typeUrl_ = other.typeUrl_; this.bitField0_ |= 0x1; onChanged(); }  if (other.status_ != 0) setStatusValue(other.getStatusValue());  if (other.getKeyId() != 0) setKeyId(other.getKeyId());  if (other.outputPrefixType_ != 0) setOutputPrefixTypeValue(other.getOutputPrefixTypeValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.typeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 16: this.status_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 24: this.keyId_ = input.readUInt32(); this.bitField0_ |= 0x4; continue;case 32: this.outputPrefixType_ = input.readEnum(); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getTypeUrl() { Object ref = this.typeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; }  return (String)ref; } public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; } public Builder setTypeUrl(String value) { if (value == null) throw new NullPointerException();  this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearTypeUrl() { this.typeUrl_ = KeysetInfo.KeyInfo.getDefaultInstance().getTypeUrl(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; } public Builder setTypeUrlBytes(ByteString value) { if (value == null) throw new NullPointerException();  KeysetInfo.KeyInfo.checkByteStringIsUtf8(value); this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public int getStatusValue() { return this.status_; } public Builder setStatusValue(int value) { this.status_ = value; this.bitField0_ |= 0x2; onChanged(); return this; } public KeyStatusType getStatus() { KeyStatusType result = KeyStatusType.forNumber(this.status_); return (result == null) ? KeyStatusType.UNRECOGNIZED : result; } public Builder setStatus(KeyStatusType value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.status_ = value.getNumber(); onChanged(); return this; } public Builder clearStatus() { this.bitField0_ &= 0xFFFFFFFD; this.status_ = 0; onChanged(); return this; } public int getKeyId() { return this.keyId_; } public Builder setKeyId(int value) { this.keyId_ = value; this.bitField0_ |= 0x4; onChanged(); return this; } public Builder clearKeyId() { this.bitField0_ &= 0xFFFFFFFB; this.keyId_ = 0; onChanged(); return this; } public int getOutputPrefixTypeValue() { return this.outputPrefixType_; } public Builder setOutputPrefixTypeValue(int value) { this.outputPrefixType_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public OutputPrefixType getOutputPrefixType() { OutputPrefixType result = OutputPrefixType.forNumber(this.outputPrefixType_); return (result == null) ? OutputPrefixType.UNRECOGNIZED : result; } public Builder setOutputPrefixType(OutputPrefixType value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x8; this.outputPrefixType_ = value.getNumber(); onChanged(); return this; } public Builder clearOutputPrefixType() { this.bitField0_ &= 0xFFFFFFF7; this.outputPrefixType_ = 0; onChanged(); return this; } } private static final KeyInfo DEFAULT_INSTANCE = new KeyInfo(); public static KeyInfo getDefaultInstance() { return DEFAULT_INSTANCE; } private static final Parser<KeyInfo> PARSER = (Parser<KeyInfo>)new AbstractParser<KeyInfo>() { public KeysetInfo.KeyInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { KeysetInfo.KeyInfo.Builder builder = KeysetInfo.KeyInfo.newBuilder(); try { builder.mergeFrom(input, extensionRegistry); } catch (InvalidProtocolBufferException e) { throw e.setUnfinishedMessage(builder.buildPartial()); } catch (UninitializedMessageException e) { throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial()); } catch (IOException e) { throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial()); }  return builder.buildPartial(); } }
/* 1062 */     ; public static Parser<KeyInfo> parser() { return PARSER; } public Parser<KeyInfo> getParserForType() { return PARSER; } public KeyInfo getDefaultInstanceForType() { return DEFAULT_INSTANCE; } } public int getPrimaryKeyId() { return this.primaryKeyId_; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 1063 */     if (isInitialized == 1) return true; 
/* 1064 */     if (isInitialized == 0) return false;
/*      */     
/* 1066 */     this.memoizedIsInitialized = 1;
/* 1067 */     return true; } public List<KeyInfo> getKeyInfoList() { return this.keyInfo_; }
/*      */   public List<? extends KeyInfoOrBuilder> getKeyInfoOrBuilderList() { return (List)this.keyInfo_; }
/*      */   public int getKeyInfoCount() { return this.keyInfo_.size(); }
/*      */   public KeyInfo getKeyInfo(int index) { return this.keyInfo_.get(index); }
/*      */   public KeyInfoOrBuilder getKeyInfoOrBuilder(int index) { return this.keyInfo_.get(index); }
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/* 1073 */     if (this.primaryKeyId_ != 0) {
/* 1074 */       output.writeUInt32(1, this.primaryKeyId_);
/*      */     }
/* 1076 */     for (int i = 0; i < this.keyInfo_.size(); i++) {
/* 1077 */       output.writeMessage(2, (MessageLite)this.keyInfo_.get(i));
/*      */     }
/* 1079 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/* 1084 */     int size = this.memoizedSize;
/* 1085 */     if (size != -1) return size;
/*      */     
/* 1087 */     size = 0;
/* 1088 */     if (this.primaryKeyId_ != 0) {
/* 1089 */       size += 
/* 1090 */         CodedOutputStream.computeUInt32Size(1, this.primaryKeyId_);
/*      */     }
/* 1092 */     for (int i = 0; i < this.keyInfo_.size(); i++) {
/* 1093 */       size += 
/* 1094 */         CodedOutputStream.computeMessageSize(2, (MessageLite)this.keyInfo_.get(i));
/*      */     }
/* 1096 */     size += getUnknownFields().getSerializedSize();
/* 1097 */     this.memoizedSize = size;
/* 1098 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1103 */     if (obj == this) {
/* 1104 */       return true;
/*      */     }
/* 1106 */     if (!(obj instanceof KeysetInfo)) {
/* 1107 */       return super.equals(obj);
/*      */     }
/* 1109 */     KeysetInfo other = (KeysetInfo)obj;
/*      */     
/* 1111 */     if (getPrimaryKeyId() != other
/* 1112 */       .getPrimaryKeyId()) return false;
/*      */     
/* 1114 */     if (!getKeyInfoList().equals(other.getKeyInfoList())) return false; 
/* 1115 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 1116 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1121 */     if (this.memoizedHashCode != 0) {
/* 1122 */       return this.memoizedHashCode;
/*      */     }
/* 1124 */     int hash = 41;
/* 1125 */     hash = 19 * hash + getDescriptor().hashCode();
/* 1126 */     hash = 37 * hash + 1;
/* 1127 */     hash = 53 * hash + getPrimaryKeyId();
/* 1128 */     if (getKeyInfoCount() > 0) {
/* 1129 */       hash = 37 * hash + 2;
/* 1130 */       hash = 53 * hash + getKeyInfoList().hashCode();
/*      */     } 
/* 1132 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 1133 */     this.memoizedHashCode = hash;
/* 1134 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 1140 */     return (KeysetInfo)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1146 */     return (KeysetInfo)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 1151 */     return (KeysetInfo)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1157 */     return (KeysetInfo)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static KeysetInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 1161 */     return (KeysetInfo)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1167 */     return (KeysetInfo)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static KeysetInfo parseFrom(InputStream input) throws IOException {
/* 1171 */     return 
/* 1172 */       (KeysetInfo)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1178 */     return 
/* 1179 */       (KeysetInfo)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseDelimitedFrom(InputStream input) throws IOException {
/* 1184 */     return 
/* 1185 */       (KeysetInfo)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1192 */     return 
/* 1193 */       (KeysetInfo)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(CodedInputStream input) throws IOException {
/* 1198 */     return 
/* 1199 */       (KeysetInfo)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeysetInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1205 */     return 
/* 1206 */       (KeysetInfo)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/* 1210 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/* 1212 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(KeysetInfo prototype) {
/* 1215 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/* 1219 */     return (this == DEFAULT_INSTANCE) ? 
/* 1220 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 1226 */     Builder builder = new Builder(parent);
/* 1227 */     return builder;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class Builder
/*      */     extends GeneratedMessage.Builder<Builder>
/*      */     implements KeysetInfoOrBuilder
/*      */   {
/*      */     private int bitField0_;
/*      */     
/*      */     private int primaryKeyId_;
/*      */     
/*      */     private List<KeysetInfo.KeyInfo> keyInfo_;
/*      */     
/*      */     private RepeatedFieldBuilder<KeysetInfo.KeyInfo, KeysetInfo.KeyInfo.Builder, KeysetInfo.KeyInfoOrBuilder> keyInfoBuilder_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/* 1244 */       return Tink.internal_static_google_crypto_tink_KeysetInfo_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 1250 */       return Tink.internal_static_google_crypto_tink_KeysetInfo_fieldAccessorTable
/* 1251 */         .ensureFieldAccessorsInitialized(KeysetInfo.class, Builder.class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Builder()
/*      */     {
/* 1474 */       this
/* 1475 */         .keyInfo_ = Collections.emptyList(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyInfo_ = Collections.emptyList(); } public Builder clear() { super.clear(); this.bitField0_ = 0; this.primaryKeyId_ = 0; if (this.keyInfoBuilder_ == null) { this.keyInfo_ = Collections.emptyList(); } else { this.keyInfo_ = null; this.keyInfoBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFD; return this; } public Descriptors.Descriptor getDescriptorForType() { return Tink.internal_static_google_crypto_tink_KeysetInfo_descriptor; } public KeysetInfo getDefaultInstanceForType() { return KeysetInfo.getDefaultInstance(); } public KeysetInfo build() { KeysetInfo result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public KeysetInfo buildPartial() { KeysetInfo result = new KeysetInfo(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(KeysetInfo result) { if (this.keyInfoBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0) { this.keyInfo_ = Collections.unmodifiableList(this.keyInfo_); this.bitField0_ &= 0xFFFFFFFD; }  result.keyInfo_ = this.keyInfo_; } else { result.keyInfo_ = this.keyInfoBuilder_.build(); }  } private void buildPartial0(KeysetInfo result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.primaryKeyId_ = this.primaryKeyId_;  } public Builder mergeFrom(Message other) { if (other instanceof KeysetInfo) return mergeFrom((KeysetInfo)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(KeysetInfo other) { if (other == KeysetInfo.getDefaultInstance()) return this;  if (other.getPrimaryKeyId() != 0) setPrimaryKeyId(other.getPrimaryKeyId());  if (this.keyInfoBuilder_ == null) { if (!other.keyInfo_.isEmpty()) { if (this.keyInfo_.isEmpty()) { this.keyInfo_ = other.keyInfo_; this.bitField0_ &= 0xFFFFFFFD; } else { ensureKeyInfoIsMutable(); this.keyInfo_.addAll(other.keyInfo_); }  onChanged(); }  } else if (!other.keyInfo_.isEmpty()) { if (this.keyInfoBuilder_.isEmpty()) { this.keyInfoBuilder_.dispose(); this.keyInfoBuilder_ = null; this.keyInfo_ = other.keyInfo_; this.bitField0_ &= 0xFFFFFFFD; this.keyInfoBuilder_ = KeysetInfo.alwaysUseFieldBuilders ? internalGetKeyInfoFieldBuilder() : null; } else { this.keyInfoBuilder_.addAllMessages(other.keyInfo_); }  }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { KeysetInfo.KeyInfo m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.primaryKeyId_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: m = (KeysetInfo.KeyInfo)input.readMessage(KeysetInfo.KeyInfo.parser(), extensionRegistry); if (this.keyInfoBuilder_ == null) { ensureKeyInfoIsMutable(); this.keyInfo_.add(m); continue; }  this.keyInfoBuilder_.addMessage(m); continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public int getPrimaryKeyId() { return this.primaryKeyId_; } public Builder setPrimaryKeyId(int value) { this.primaryKeyId_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public Builder clearPrimaryKeyId() { this.bitField0_ &= 0xFFFFFFFE; this.primaryKeyId_ = 0; onChanged(); return this; }
/* 1477 */     private void ensureKeyInfoIsMutable() { if ((this.bitField0_ & 0x2) == 0) {
/* 1478 */         this.keyInfo_ = new ArrayList<>(this.keyInfo_);
/* 1479 */         this.bitField0_ |= 0x2;
/*      */       }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<KeysetInfo.KeyInfo> getKeyInfoList() {
/* 1495 */       if (this.keyInfoBuilder_ == null) {
/* 1496 */         return Collections.unmodifiableList(this.keyInfo_);
/*      */       }
/* 1498 */       return this.keyInfoBuilder_.getMessageList();
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
/*      */     public int getKeyInfoCount() {
/* 1510 */       if (this.keyInfoBuilder_ == null) {
/* 1511 */         return this.keyInfo_.size();
/*      */       }
/* 1513 */       return this.keyInfoBuilder_.getCount();
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
/*      */     public KeysetInfo.KeyInfo getKeyInfo(int index) {
/* 1525 */       if (this.keyInfoBuilder_ == null) {
/* 1526 */         return this.keyInfo_.get(index);
/*      */       }
/* 1528 */       return (KeysetInfo.KeyInfo)this.keyInfoBuilder_.getMessage(index);
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
/*      */     public Builder setKeyInfo(int index, KeysetInfo.KeyInfo value) {
/* 1541 */       if (this.keyInfoBuilder_ == null) {
/* 1542 */         if (value == null) {
/* 1543 */           throw new NullPointerException();
/*      */         }
/* 1545 */         ensureKeyInfoIsMutable();
/* 1546 */         this.keyInfo_.set(index, value);
/* 1547 */         onChanged();
/*      */       } else {
/* 1549 */         this.keyInfoBuilder_.setMessage(index, value);
/*      */       } 
/* 1551 */       return this;
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
/*      */     public Builder setKeyInfo(int index, KeysetInfo.KeyInfo.Builder builderForValue) {
/* 1563 */       if (this.keyInfoBuilder_ == null) {
/* 1564 */         ensureKeyInfoIsMutable();
/* 1565 */         this.keyInfo_.set(index, builderForValue.build());
/* 1566 */         onChanged();
/*      */       } else {
/* 1568 */         this.keyInfoBuilder_.setMessage(index, builderForValue.build());
/*      */       } 
/* 1570 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder addKeyInfo(KeysetInfo.KeyInfo value) {
/* 1581 */       if (this.keyInfoBuilder_ == null) {
/* 1582 */         if (value == null) {
/* 1583 */           throw new NullPointerException();
/*      */         }
/* 1585 */         ensureKeyInfoIsMutable();
/* 1586 */         this.keyInfo_.add(value);
/* 1587 */         onChanged();
/*      */       } else {
/* 1589 */         this.keyInfoBuilder_.addMessage(value);
/*      */       } 
/* 1591 */       return this;
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
/*      */     public Builder addKeyInfo(int index, KeysetInfo.KeyInfo value) {
/* 1603 */       if (this.keyInfoBuilder_ == null) {
/* 1604 */         if (value == null) {
/* 1605 */           throw new NullPointerException();
/*      */         }
/* 1607 */         ensureKeyInfoIsMutable();
/* 1608 */         this.keyInfo_.add(index, value);
/* 1609 */         onChanged();
/*      */       } else {
/* 1611 */         this.keyInfoBuilder_.addMessage(index, value);
/*      */       } 
/* 1613 */       return this;
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
/*      */     public Builder addKeyInfo(KeysetInfo.KeyInfo.Builder builderForValue) {
/* 1625 */       if (this.keyInfoBuilder_ == null) {
/* 1626 */         ensureKeyInfoIsMutable();
/* 1627 */         this.keyInfo_.add(builderForValue.build());
/* 1628 */         onChanged();
/*      */       } else {
/* 1630 */         this.keyInfoBuilder_.addMessage(builderForValue.build());
/*      */       } 
/* 1632 */       return this;
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
/*      */     public Builder addKeyInfo(int index, KeysetInfo.KeyInfo.Builder builderForValue) {
/* 1644 */       if (this.keyInfoBuilder_ == null) {
/* 1645 */         ensureKeyInfoIsMutable();
/* 1646 */         this.keyInfo_.add(index, builderForValue.build());
/* 1647 */         onChanged();
/*      */       } else {
/* 1649 */         this.keyInfoBuilder_.addMessage(index, builderForValue.build());
/*      */       } 
/* 1651 */       return this;
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
/*      */     public Builder addAllKeyInfo(Iterable<? extends KeysetInfo.KeyInfo> values) {
/* 1663 */       if (this.keyInfoBuilder_ == null) {
/* 1664 */         ensureKeyInfoIsMutable();
/* 1665 */         AbstractMessageLite.Builder.addAll(values, this.keyInfo_);
/*      */         
/* 1667 */         onChanged();
/*      */       } else {
/* 1669 */         this.keyInfoBuilder_.addAllMessages(values);
/*      */       } 
/* 1671 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearKeyInfo() {
/* 1682 */       if (this.keyInfoBuilder_ == null) {
/* 1683 */         this.keyInfo_ = Collections.emptyList();
/* 1684 */         this.bitField0_ &= 0xFFFFFFFD;
/* 1685 */         onChanged();
/*      */       } else {
/* 1687 */         this.keyInfoBuilder_.clear();
/*      */       } 
/* 1689 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder removeKeyInfo(int index) {
/* 1700 */       if (this.keyInfoBuilder_ == null) {
/* 1701 */         ensureKeyInfoIsMutable();
/* 1702 */         this.keyInfo_.remove(index);
/* 1703 */         onChanged();
/*      */       } else {
/* 1705 */         this.keyInfoBuilder_.remove(index);
/*      */       } 
/* 1707 */       return this;
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
/*      */     public KeysetInfo.KeyInfo.Builder getKeyInfoBuilder(int index) {
/* 1719 */       return (KeysetInfo.KeyInfo.Builder)internalGetKeyInfoFieldBuilder().getBuilder(index);
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
/*      */     public KeysetInfo.KeyInfoOrBuilder getKeyInfoOrBuilder(int index) {
/* 1731 */       if (this.keyInfoBuilder_ == null)
/* 1732 */         return this.keyInfo_.get(index); 
/* 1733 */       return (KeysetInfo.KeyInfoOrBuilder)this.keyInfoBuilder_.getMessageOrBuilder(index);
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
/*      */     public List<? extends KeysetInfo.KeyInfoOrBuilder> getKeyInfoOrBuilderList() {
/* 1746 */       if (this.keyInfoBuilder_ != null) {
/* 1747 */         return this.keyInfoBuilder_.getMessageOrBuilderList();
/*      */       }
/* 1749 */       return Collections.unmodifiableList((List)this.keyInfo_);
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
/*      */     public KeysetInfo.KeyInfo.Builder addKeyInfoBuilder() {
/* 1761 */       return (KeysetInfo.KeyInfo.Builder)internalGetKeyInfoFieldBuilder().addBuilder(
/* 1762 */           KeysetInfo.KeyInfo.getDefaultInstance());
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
/*      */     public KeysetInfo.KeyInfo.Builder addKeyInfoBuilder(int index) {
/* 1774 */       return (KeysetInfo.KeyInfo.Builder)internalGetKeyInfoFieldBuilder().addBuilder(index, 
/* 1775 */           KeysetInfo.KeyInfo.getDefaultInstance());
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
/*      */     public List<KeysetInfo.KeyInfo.Builder> getKeyInfoBuilderList() {
/* 1787 */       return internalGetKeyInfoFieldBuilder().getBuilderList();
/*      */     }
/*      */ 
/*      */     
/*      */     private RepeatedFieldBuilder<KeysetInfo.KeyInfo, KeysetInfo.KeyInfo.Builder, KeysetInfo.KeyInfoOrBuilder> internalGetKeyInfoFieldBuilder() {
/* 1792 */       if (this.keyInfoBuilder_ == null) {
/* 1793 */         this
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1798 */           .keyInfoBuilder_ = new RepeatedFieldBuilder(this.keyInfo_, ((this.bitField0_ & 0x2) != 0), getParentForChildren(), isClean());
/* 1799 */         this.keyInfo_ = null;
/*      */       } 
/* 1801 */       return this.keyInfoBuilder_;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1810 */   private static final KeysetInfo DEFAULT_INSTANCE = new KeysetInfo();
/*      */ 
/*      */   
/*      */   public static KeysetInfo getDefaultInstance() {
/* 1814 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1818 */   private static final Parser<KeysetInfo> PARSER = (Parser<KeysetInfo>)new AbstractParser<KeysetInfo>()
/*      */     {
/*      */ 
/*      */       
/*      */       public KeysetInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1824 */         KeysetInfo.Builder builder = KeysetInfo.newBuilder();
/*      */         try {
/* 1826 */           builder.mergeFrom(input, extensionRegistry);
/* 1827 */         } catch (InvalidProtocolBufferException e) {
/* 1828 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1829 */         } catch (UninitializedMessageException e) {
/* 1830 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1831 */         } catch (IOException e) {
/* 1832 */           throw (new InvalidProtocolBufferException(e))
/* 1833 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1835 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<KeysetInfo> parser() {
/* 1840 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<KeysetInfo> getParserForType() {
/* 1845 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public KeysetInfo getDefaultInstanceForType() {
/* 1850 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */   
/*      */   public static interface KeyInfoOrBuilder extends MessageOrBuilder {
/*      */     String getTypeUrl();
/*      */     
/*      */     ByteString getTypeUrlBytes();
/*      */     
/*      */     int getStatusValue();
/*      */     
/*      */     KeyStatusType getStatus();
/*      */     
/*      */     int getKeyId();
/*      */     
/*      */     int getOutputPrefixTypeValue();
/*      */     
/*      */     OutputPrefixType getOutputPrefixType();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeysetInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */