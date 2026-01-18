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
/*      */ import java.nio.ByteBuffer;
/*      */ 
/*      */ public final class Keyset extends GeneratedMessage implements KeysetOrBuilder {
/*      */   private static final long serialVersionUID = 0L;
/*      */   public static final int PRIMARY_KEY_ID_FIELD_NUMBER = 1;
/*      */   private int primaryKeyId_;
/*      */   public static final int KEY_FIELD_NUMBER = 2;
/*      */   private List<Key> key_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   25 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Keyset.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   31 */         .getName());
/*      */   }
/*      */   
/*      */   private Keyset(GeneratedMessage.Builder<?> builder) {
/*   35 */     super(builder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     this.primaryKeyId_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1129 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_Keyset_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_Keyset_fieldAccessorTable.ensureFieldAccessorsInitialized(Keyset.class, Builder.class); } private Keyset() { this.primaryKeyId_ = 0; this.memoizedIsInitialized = -1; this.key_ = Collections.emptyList(); } public static final class Key extends GeneratedMessage implements KeyOrBuilder {
/*      */     private static final long serialVersionUID = 0L; private int bitField0_; public static final int KEY_DATA_FIELD_NUMBER = 1; private KeyData keyData_; public static final int STATUS_FIELD_NUMBER = 2; private int status_; public static final int KEY_ID_FIELD_NUMBER = 3; private int keyId_; public static final int OUTPUT_PREFIX_TYPE_FIELD_NUMBER = 4; private int outputPrefixType_; private byte memoizedIsInitialized; static { RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Key.class.getName()); } private Key(GeneratedMessage.Builder<?> builder) { super(builder); this.status_ = 0; this.keyId_ = 0; this.outputPrefixType_ = 0; this.memoizedIsInitialized = -1; } private Key() { this.status_ = 0; this.keyId_ = 0; this.outputPrefixType_ = 0; this.memoizedIsInitialized = -1; this.status_ = 0; this.outputPrefixType_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_Keyset_Key_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_Keyset_Key_fieldAccessorTable.ensureFieldAccessorsInitialized(Key.class, Builder.class); } public boolean hasKeyData() { return ((this.bitField0_ & 0x1) != 0); } public KeyData getKeyData() { return (this.keyData_ == null) ? KeyData.getDefaultInstance() : this.keyData_; } public KeyDataOrBuilder getKeyDataOrBuilder() { return (this.keyData_ == null) ? KeyData.getDefaultInstance() : this.keyData_; } public int getStatusValue() { return this.status_; } public KeyStatusType getStatus() { KeyStatusType result = KeyStatusType.forNumber(this.status_); return (result == null) ? KeyStatusType.UNRECOGNIZED : result; } public int getKeyId() { return this.keyId_; } public int getOutputPrefixTypeValue() { return this.outputPrefixType_; } public OutputPrefixType getOutputPrefixType() { OutputPrefixType result = OutputPrefixType.forNumber(this.outputPrefixType_); return (result == null) ? OutputPrefixType.UNRECOGNIZED : result; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized; if (isInitialized == 1) return true;  if (isInitialized == 0) return false;  this.memoizedIsInitialized = 1; return true; } public void writeTo(CodedOutputStream output) throws IOException { if ((this.bitField0_ & 0x1) != 0) output.writeMessage(1, (MessageLite)getKeyData());  if (this.status_ != KeyStatusType.UNKNOWN_STATUS.getNumber()) output.writeEnum(2, this.status_);  if (this.keyId_ != 0) output.writeUInt32(3, this.keyId_);  if (this.outputPrefixType_ != OutputPrefixType.UNKNOWN_PREFIX.getNumber()) output.writeEnum(4, this.outputPrefixType_);  getUnknownFields().writeTo(output); } public int getSerializedSize() { int size = this.memoizedSize; if (size != -1) return size;  size = 0; if ((this.bitField0_ & 0x1) != 0) size += CodedOutputStream.computeMessageSize(1, (MessageLite)getKeyData());  if (this.status_ != KeyStatusType.UNKNOWN_STATUS.getNumber()) size += CodedOutputStream.computeEnumSize(2, this.status_);  if (this.keyId_ != 0) size += CodedOutputStream.computeUInt32Size(3, this.keyId_);  if (this.outputPrefixType_ != OutputPrefixType.UNKNOWN_PREFIX.getNumber()) size += CodedOutputStream.computeEnumSize(4, this.outputPrefixType_);  size += getUnknownFields().getSerializedSize(); this.memoizedSize = size; return size; } public boolean equals(Object obj) { if (obj == this) return true;  if (!(obj instanceof Key)) return super.equals(obj);  Key other = (Key)obj; if (hasKeyData() != other.hasKeyData()) return false;  if (hasKeyData() && !getKeyData().equals(other.getKeyData())) return false;  if (this.status_ != other.status_) return false;  if (getKeyId() != other.getKeyId()) return false;  if (this.outputPrefixType_ != other.outputPrefixType_) return false;  if (!getUnknownFields().equals(other.getUnknownFields())) return false;  return true; } public int hashCode() { if (this.memoizedHashCode != 0) return this.memoizedHashCode;  int hash = 41; hash = 19 * hash + getDescriptor().hashCode(); if (hasKeyData()) { hash = 37 * hash + 1; hash = 53 * hash + getKeyData().hashCode(); }  hash = 37 * hash + 2; hash = 53 * hash + this.status_; hash = 37 * hash + 3; hash = 53 * hash + getKeyId(); hash = 37 * hash + 4; hash = 53 * hash + this.outputPrefixType_; hash = 29 * hash + getUnknownFields().hashCode(); this.memoizedHashCode = hash; return hash; } public static Key parseFrom(ByteBuffer data) throws InvalidProtocolBufferException { return (Key)PARSER.parseFrom(data); } public static Key parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (Key)PARSER.parseFrom(data, extensionRegistry); } public static Key parseFrom(ByteString data) throws InvalidProtocolBufferException { return (Key)PARSER.parseFrom(data); } public static Key parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (Key)PARSER.parseFrom(data, extensionRegistry); } public static Key parseFrom(byte[] data) throws InvalidProtocolBufferException { return (Key)PARSER.parseFrom(data); } public static Key parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (Key)PARSER.parseFrom(data, extensionRegistry); } public static Key parseFrom(InputStream input) throws IOException { return (Key)GeneratedMessage.parseWithIOException(PARSER, input); } public static Key parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (Key)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public static Key parseDelimitedFrom(InputStream input) throws IOException { return (Key)GeneratedMessage.parseDelimitedWithIOException(PARSER, input); } public static Key parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (Key)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry); } public static Key parseFrom(CodedInputStream input) throws IOException { return (Key)GeneratedMessage.parseWithIOException(PARSER, input); } public static Key parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (Key)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public Builder newBuilderForType() { return newBuilder(); } public static Builder newBuilder() { return DEFAULT_INSTANCE.toBuilder(); } public static Builder newBuilder(Key prototype) { return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype); } public Builder toBuilder() { return (this == DEFAULT_INSTANCE) ? new Builder() : (new Builder()).mergeFrom(this); } protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) { Builder builder = new Builder(parent); return builder; } public static final class Builder extends GeneratedMessage.Builder<Builder> implements Keyset.KeyOrBuilder {
/*      */       private int bitField0_; private KeyData keyData_; private SingleFieldBuilder<KeyData, KeyData.Builder, KeyDataOrBuilder> keyDataBuilder_; private int status_; private int keyId_; private int outputPrefixType_; public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_Keyset_Key_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_Keyset_Key_fieldAccessorTable.ensureFieldAccessorsInitialized(Keyset.Key.class, Builder.class); } private Builder() { this.status_ = 0; this.outputPrefixType_ = 0; maybeForceBuilderInitialization(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.status_ = 0; this.outputPrefixType_ = 0; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (Keyset.Key.alwaysUseFieldBuilders) internalGetKeyDataFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.keyData_ = null; if (this.keyDataBuilder_ != null) { this.keyDataBuilder_.dispose(); this.keyDataBuilder_ = null; }  this.status_ = 0; this.keyId_ = 0; this.outputPrefixType_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return Tink.internal_static_google_crypto_tink_Keyset_Key_descriptor; } public Keyset.Key getDefaultInstanceForType() { return Keyset.Key.getDefaultInstance(); } public Keyset.Key build() { Keyset.Key result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public Keyset.Key buildPartial() { Keyset.Key result = new Keyset.Key(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(Keyset.Key result) { int from_bitField0_ = this.bitField0_; int to_bitField0_ = 0; if ((from_bitField0_ & 0x1) != 0) { result.keyData_ = (this.keyDataBuilder_ == null) ? this.keyData_ : (KeyData)this.keyDataBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x2) != 0) result.status_ = this.status_;  if ((from_bitField0_ & 0x4) != 0) result.keyId_ = this.keyId_;  if ((from_bitField0_ & 0x8) != 0) result.outputPrefixType_ = this.outputPrefixType_;  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof Keyset.Key) return mergeFrom((Keyset.Key)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(Keyset.Key other) { if (other == Keyset.Key.getDefaultInstance()) return this;  if (other.hasKeyData()) mergeKeyData(other.getKeyData());  if (other.status_ != 0) setStatusValue(other.getStatusValue());  if (other.getKeyId() != 0) setKeyId(other.getKeyId());  if (other.outputPrefixType_ != 0) setOutputPrefixTypeValue(other.getOutputPrefixTypeValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: input.readMessage((MessageLite.Builder)internalGetKeyDataFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x1; continue;case 16: this.status_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 24: this.keyId_ = input.readUInt32(); this.bitField0_ |= 0x4; continue;case 32: this.outputPrefixType_ = input.readEnum(); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public boolean hasKeyData() { return ((this.bitField0_ & 0x1) != 0); } public KeyData getKeyData() { if (this.keyDataBuilder_ == null) return (this.keyData_ == null) ? KeyData.getDefaultInstance() : this.keyData_;  return (KeyData)this.keyDataBuilder_.getMessage(); } public Builder setKeyData(KeyData value) { if (this.keyDataBuilder_ == null) { if (value == null) throw new NullPointerException();  this.keyData_ = value; } else { this.keyDataBuilder_.setMessage(value); }  this.bitField0_ |= 0x1; onChanged(); return this; } public Builder setKeyData(KeyData.Builder builderForValue) { if (this.keyDataBuilder_ == null) { this.keyData_ = builderForValue.build(); } else { this.keyDataBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x1; onChanged(); return this; } public Builder mergeKeyData(KeyData value) { if (this.keyDataBuilder_ == null) { if ((this.bitField0_ & 0x1) != 0 && this.keyData_ != null && this.keyData_ != KeyData.getDefaultInstance()) { getKeyDataBuilder().mergeFrom(value); } else { this.keyData_ = value; }  } else { this.keyDataBuilder_.mergeFrom(value); }  if (this.keyData_ != null) { this.bitField0_ |= 0x1; onChanged(); }  return this; } public Builder clearKeyData() { this.bitField0_ &= 0xFFFFFFFE; this.keyData_ = null; if (this.keyDataBuilder_ != null) { this.keyDataBuilder_.dispose(); this.keyDataBuilder_ = null; }  onChanged(); return this; } public KeyData.Builder getKeyDataBuilder() { this.bitField0_ |= 0x1; onChanged(); return (KeyData.Builder)internalGetKeyDataFieldBuilder().getBuilder(); } public KeyDataOrBuilder getKeyDataOrBuilder() { if (this.keyDataBuilder_ != null) return (KeyDataOrBuilder)this.keyDataBuilder_.getMessageOrBuilder();  return (this.keyData_ == null) ? KeyData.getDefaultInstance() : this.keyData_; } private SingleFieldBuilder<KeyData, KeyData.Builder, KeyDataOrBuilder> internalGetKeyDataFieldBuilder() { if (this.keyDataBuilder_ == null) { this.keyDataBuilder_ = new SingleFieldBuilder(getKeyData(), getParentForChildren(), isClean()); this.keyData_ = null; }  return this.keyDataBuilder_; } public int getStatusValue() { return this.status_; } public Builder setStatusValue(int value) { this.status_ = value; this.bitField0_ |= 0x2; onChanged(); return this; } public KeyStatusType getStatus() { KeyStatusType result = KeyStatusType.forNumber(this.status_); return (result == null) ? KeyStatusType.UNRECOGNIZED : result; } public Builder setStatus(KeyStatusType value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.status_ = value.getNumber(); onChanged(); return this; } public Builder clearStatus() { this.bitField0_ &= 0xFFFFFFFD; this.status_ = 0; onChanged(); return this; } public int getKeyId() { return this.keyId_; } public Builder setKeyId(int value) { this.keyId_ = value; this.bitField0_ |= 0x4; onChanged(); return this; } public Builder clearKeyId() { this.bitField0_ &= 0xFFFFFFFB; this.keyId_ = 0; onChanged(); return this; } public int getOutputPrefixTypeValue() { return this.outputPrefixType_; } public Builder setOutputPrefixTypeValue(int value) { this.outputPrefixType_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public OutputPrefixType getOutputPrefixType() { OutputPrefixType result = OutputPrefixType.forNumber(this.outputPrefixType_); return (result == null) ? OutputPrefixType.UNRECOGNIZED : result; } public Builder setOutputPrefixType(OutputPrefixType value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x8; this.outputPrefixType_ = value.getNumber(); onChanged(); return this; } public Builder clearOutputPrefixType() { this.bitField0_ &= 0xFFFFFFF7; this.outputPrefixType_ = 0; onChanged(); return this; } } private static final Key DEFAULT_INSTANCE = new Key(); public static Key getDefaultInstance() { return DEFAULT_INSTANCE; } private static final Parser<Key> PARSER = (Parser<Key>)new AbstractParser<Key>() { public Keyset.Key parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { Keyset.Key.Builder builder = Keyset.Key.newBuilder(); try { builder.mergeFrom(input, extensionRegistry); } catch (InvalidProtocolBufferException e) { throw e.setUnfinishedMessage(builder.buildPartial()); } catch (UninitializedMessageException e) { throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial()); } catch (IOException e) { throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial()); }  return builder.buildPartial(); } }
/* 1132 */     ; public static Parser<Key> parser() { return PARSER; } public Parser<Key> getParserForType() { return PARSER; } public Key getDefaultInstanceForType() { return DEFAULT_INSTANCE; } } public int getPrimaryKeyId() { return this.primaryKeyId_; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 1133 */     if (isInitialized == 1) return true; 
/* 1134 */     if (isInitialized == 0) return false;
/*      */     
/* 1136 */     this.memoizedIsInitialized = 1;
/* 1137 */     return true; } public List<Key> getKeyList() { return this.key_; }
/*      */   public List<? extends KeyOrBuilder> getKeyOrBuilderList() { return (List)this.key_; }
/*      */   public int getKeyCount() { return this.key_.size(); }
/*      */   public Key getKey(int index) { return this.key_.get(index); }
/*      */   public KeyOrBuilder getKeyOrBuilder(int index) { return this.key_.get(index); }
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/* 1143 */     if (this.primaryKeyId_ != 0) {
/* 1144 */       output.writeUInt32(1, this.primaryKeyId_);
/*      */     }
/* 1146 */     for (int i = 0; i < this.key_.size(); i++) {
/* 1147 */       output.writeMessage(2, (MessageLite)this.key_.get(i));
/*      */     }
/* 1149 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/* 1154 */     int size = this.memoizedSize;
/* 1155 */     if (size != -1) return size;
/*      */     
/* 1157 */     size = 0;
/* 1158 */     if (this.primaryKeyId_ != 0) {
/* 1159 */       size += 
/* 1160 */         CodedOutputStream.computeUInt32Size(1, this.primaryKeyId_);
/*      */     }
/* 1162 */     for (int i = 0; i < this.key_.size(); i++) {
/* 1163 */       size += 
/* 1164 */         CodedOutputStream.computeMessageSize(2, (MessageLite)this.key_.get(i));
/*      */     }
/* 1166 */     size += getUnknownFields().getSerializedSize();
/* 1167 */     this.memoizedSize = size;
/* 1168 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1173 */     if (obj == this) {
/* 1174 */       return true;
/*      */     }
/* 1176 */     if (!(obj instanceof Keyset)) {
/* 1177 */       return super.equals(obj);
/*      */     }
/* 1179 */     Keyset other = (Keyset)obj;
/*      */     
/* 1181 */     if (getPrimaryKeyId() != other
/* 1182 */       .getPrimaryKeyId()) return false;
/*      */     
/* 1184 */     if (!getKeyList().equals(other.getKeyList())) return false; 
/* 1185 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 1186 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1191 */     if (this.memoizedHashCode != 0) {
/* 1192 */       return this.memoizedHashCode;
/*      */     }
/* 1194 */     int hash = 41;
/* 1195 */     hash = 19 * hash + getDescriptor().hashCode();
/* 1196 */     hash = 37 * hash + 1;
/* 1197 */     hash = 53 * hash + getPrimaryKeyId();
/* 1198 */     if (getKeyCount() > 0) {
/* 1199 */       hash = 37 * hash + 2;
/* 1200 */       hash = 53 * hash + getKeyList().hashCode();
/*      */     } 
/* 1202 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 1203 */     this.memoizedHashCode = hash;
/* 1204 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 1210 */     return (Keyset)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1216 */     return (Keyset)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 1221 */     return (Keyset)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1227 */     return (Keyset)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Keyset parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 1231 */     return (Keyset)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1237 */     return (Keyset)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Keyset parseFrom(InputStream input) throws IOException {
/* 1241 */     return 
/* 1242 */       (Keyset)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1248 */     return 
/* 1249 */       (Keyset)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Keyset parseDelimitedFrom(InputStream input) throws IOException {
/* 1254 */     return 
/* 1255 */       (Keyset)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Keyset parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1262 */     return 
/* 1263 */       (Keyset)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(CodedInputStream input) throws IOException {
/* 1268 */     return 
/* 1269 */       (Keyset)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Keyset parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1275 */     return 
/* 1276 */       (Keyset)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/* 1280 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/* 1282 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(Keyset prototype) {
/* 1285 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/* 1289 */     return (this == DEFAULT_INSTANCE) ? 
/* 1290 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 1296 */     Builder builder = new Builder(parent);
/* 1297 */     return builder;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class Builder
/*      */     extends GeneratedMessage.Builder<Builder>
/*      */     implements KeysetOrBuilder
/*      */   {
/*      */     private int bitField0_;
/*      */     
/*      */     private int primaryKeyId_;
/*      */     
/*      */     private List<Keyset.Key> key_;
/*      */     
/*      */     private RepeatedFieldBuilder<Keyset.Key, Keyset.Key.Builder, Keyset.KeyOrBuilder> keyBuilder_;
/*      */ 
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/* 1315 */       return Tink.internal_static_google_crypto_tink_Keyset_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 1321 */       return Tink.internal_static_google_crypto_tink_Keyset_fieldAccessorTable
/* 1322 */         .ensureFieldAccessorsInitialized(Keyset.class, Builder.class);
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
/*      */ 
/*      */ 
/*      */     
/*      */     private Builder()
/*      */     {
/* 1548 */       this
/* 1549 */         .key_ = Collections.emptyList(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.key_ = Collections.emptyList(); } public Builder clear() { super.clear(); this.bitField0_ = 0; this.primaryKeyId_ = 0; if (this.keyBuilder_ == null) { this.key_ = Collections.emptyList(); } else { this.key_ = null; this.keyBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFD; return this; } public Descriptors.Descriptor getDescriptorForType() { return Tink.internal_static_google_crypto_tink_Keyset_descriptor; } public Keyset getDefaultInstanceForType() { return Keyset.getDefaultInstance(); } public Keyset build() { Keyset result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public Keyset buildPartial() { Keyset result = new Keyset(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(Keyset result) { if (this.keyBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0) { this.key_ = Collections.unmodifiableList(this.key_); this.bitField0_ &= 0xFFFFFFFD; }  result.key_ = this.key_; } else { result.key_ = this.keyBuilder_.build(); }  } private void buildPartial0(Keyset result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.primaryKeyId_ = this.primaryKeyId_;  } public Builder mergeFrom(Message other) { if (other instanceof Keyset) return mergeFrom((Keyset)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(Keyset other) { if (other == Keyset.getDefaultInstance()) return this;  if (other.getPrimaryKeyId() != 0) setPrimaryKeyId(other.getPrimaryKeyId());  if (this.keyBuilder_ == null) { if (!other.key_.isEmpty()) { if (this.key_.isEmpty()) { this.key_ = other.key_; this.bitField0_ &= 0xFFFFFFFD; } else { ensureKeyIsMutable(); this.key_.addAll(other.key_); }  onChanged(); }  } else if (!other.key_.isEmpty()) { if (this.keyBuilder_.isEmpty()) { this.keyBuilder_.dispose(); this.keyBuilder_ = null; this.key_ = other.key_; this.bitField0_ &= 0xFFFFFFFD; this.keyBuilder_ = Keyset.alwaysUseFieldBuilders ? internalGetKeyFieldBuilder() : null; } else { this.keyBuilder_.addAllMessages(other.key_); }  }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { Keyset.Key m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.primaryKeyId_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: m = (Keyset.Key)input.readMessage(Keyset.Key.parser(), extensionRegistry); if (this.keyBuilder_ == null) { ensureKeyIsMutable(); this.key_.add(m); continue; }  this.keyBuilder_.addMessage(m); continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public int getPrimaryKeyId() { return this.primaryKeyId_; } public Builder setPrimaryKeyId(int value) { this.primaryKeyId_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public Builder clearPrimaryKeyId() { this.bitField0_ &= 0xFFFFFFFE; this.primaryKeyId_ = 0; onChanged(); return this; }
/* 1551 */     private void ensureKeyIsMutable() { if ((this.bitField0_ & 0x2) == 0) {
/* 1552 */         this.key_ = new ArrayList<>(this.key_);
/* 1553 */         this.bitField0_ |= 0x2;
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
/*      */     public List<Keyset.Key> getKeyList() {
/* 1569 */       if (this.keyBuilder_ == null) {
/* 1570 */         return Collections.unmodifiableList(this.key_);
/*      */       }
/* 1572 */       return this.keyBuilder_.getMessageList();
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
/*      */     public int getKeyCount() {
/* 1584 */       if (this.keyBuilder_ == null) {
/* 1585 */         return this.key_.size();
/*      */       }
/* 1587 */       return this.keyBuilder_.getCount();
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
/*      */     public Keyset.Key getKey(int index) {
/* 1599 */       if (this.keyBuilder_ == null) {
/* 1600 */         return this.key_.get(index);
/*      */       }
/* 1602 */       return (Keyset.Key)this.keyBuilder_.getMessage(index);
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
/*      */     public Builder setKey(int index, Keyset.Key value) {
/* 1615 */       if (this.keyBuilder_ == null) {
/* 1616 */         if (value == null) {
/* 1617 */           throw new NullPointerException();
/*      */         }
/* 1619 */         ensureKeyIsMutable();
/* 1620 */         this.key_.set(index, value);
/* 1621 */         onChanged();
/*      */       } else {
/* 1623 */         this.keyBuilder_.setMessage(index, value);
/*      */       } 
/* 1625 */       return this;
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
/*      */     public Builder setKey(int index, Keyset.Key.Builder builderForValue) {
/* 1637 */       if (this.keyBuilder_ == null) {
/* 1638 */         ensureKeyIsMutable();
/* 1639 */         this.key_.set(index, builderForValue.build());
/* 1640 */         onChanged();
/*      */       } else {
/* 1642 */         this.keyBuilder_.setMessage(index, builderForValue.build());
/*      */       } 
/* 1644 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder addKey(Keyset.Key value) {
/* 1655 */       if (this.keyBuilder_ == null) {
/* 1656 */         if (value == null) {
/* 1657 */           throw new NullPointerException();
/*      */         }
/* 1659 */         ensureKeyIsMutable();
/* 1660 */         this.key_.add(value);
/* 1661 */         onChanged();
/*      */       } else {
/* 1663 */         this.keyBuilder_.addMessage(value);
/*      */       } 
/* 1665 */       return this;
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
/*      */     public Builder addKey(int index, Keyset.Key value) {
/* 1677 */       if (this.keyBuilder_ == null) {
/* 1678 */         if (value == null) {
/* 1679 */           throw new NullPointerException();
/*      */         }
/* 1681 */         ensureKeyIsMutable();
/* 1682 */         this.key_.add(index, value);
/* 1683 */         onChanged();
/*      */       } else {
/* 1685 */         this.keyBuilder_.addMessage(index, value);
/*      */       } 
/* 1687 */       return this;
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
/*      */     public Builder addKey(Keyset.Key.Builder builderForValue) {
/* 1699 */       if (this.keyBuilder_ == null) {
/* 1700 */         ensureKeyIsMutable();
/* 1701 */         this.key_.add(builderForValue.build());
/* 1702 */         onChanged();
/*      */       } else {
/* 1704 */         this.keyBuilder_.addMessage(builderForValue.build());
/*      */       } 
/* 1706 */       return this;
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
/*      */     public Builder addKey(int index, Keyset.Key.Builder builderForValue) {
/* 1718 */       if (this.keyBuilder_ == null) {
/* 1719 */         ensureKeyIsMutable();
/* 1720 */         this.key_.add(index, builderForValue.build());
/* 1721 */         onChanged();
/*      */       } else {
/* 1723 */         this.keyBuilder_.addMessage(index, builderForValue.build());
/*      */       } 
/* 1725 */       return this;
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
/*      */     public Builder addAllKey(Iterable<? extends Keyset.Key> values) {
/* 1737 */       if (this.keyBuilder_ == null) {
/* 1738 */         ensureKeyIsMutable();
/* 1739 */         AbstractMessageLite.Builder.addAll(values, this.key_);
/*      */         
/* 1741 */         onChanged();
/*      */       } else {
/* 1743 */         this.keyBuilder_.addAllMessages(values);
/*      */       } 
/* 1745 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearKey() {
/* 1756 */       if (this.keyBuilder_ == null) {
/* 1757 */         this.key_ = Collections.emptyList();
/* 1758 */         this.bitField0_ &= 0xFFFFFFFD;
/* 1759 */         onChanged();
/*      */       } else {
/* 1761 */         this.keyBuilder_.clear();
/*      */       } 
/* 1763 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder removeKey(int index) {
/* 1774 */       if (this.keyBuilder_ == null) {
/* 1775 */         ensureKeyIsMutable();
/* 1776 */         this.key_.remove(index);
/* 1777 */         onChanged();
/*      */       } else {
/* 1779 */         this.keyBuilder_.remove(index);
/*      */       } 
/* 1781 */       return this;
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
/*      */     public Keyset.Key.Builder getKeyBuilder(int index) {
/* 1793 */       return (Keyset.Key.Builder)internalGetKeyFieldBuilder().getBuilder(index);
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
/*      */     public Keyset.KeyOrBuilder getKeyOrBuilder(int index) {
/* 1805 */       if (this.keyBuilder_ == null)
/* 1806 */         return this.key_.get(index); 
/* 1807 */       return (Keyset.KeyOrBuilder)this.keyBuilder_.getMessageOrBuilder(index);
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
/*      */     public List<? extends Keyset.KeyOrBuilder> getKeyOrBuilderList() {
/* 1820 */       if (this.keyBuilder_ != null) {
/* 1821 */         return this.keyBuilder_.getMessageOrBuilderList();
/*      */       }
/* 1823 */       return Collections.unmodifiableList((List)this.key_);
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
/*      */     public Keyset.Key.Builder addKeyBuilder() {
/* 1835 */       return (Keyset.Key.Builder)internalGetKeyFieldBuilder().addBuilder(
/* 1836 */           Keyset.Key.getDefaultInstance());
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
/*      */     public Keyset.Key.Builder addKeyBuilder(int index) {
/* 1848 */       return (Keyset.Key.Builder)internalGetKeyFieldBuilder().addBuilder(index, 
/* 1849 */           Keyset.Key.getDefaultInstance());
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
/*      */     public List<Keyset.Key.Builder> getKeyBuilderList() {
/* 1861 */       return internalGetKeyFieldBuilder().getBuilderList();
/*      */     }
/*      */ 
/*      */     
/*      */     private RepeatedFieldBuilder<Keyset.Key, Keyset.Key.Builder, Keyset.KeyOrBuilder> internalGetKeyFieldBuilder() {
/* 1866 */       if (this.keyBuilder_ == null) {
/* 1867 */         this
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1872 */           .keyBuilder_ = new RepeatedFieldBuilder(this.key_, ((this.bitField0_ & 0x2) != 0), getParentForChildren(), isClean());
/* 1873 */         this.key_ = null;
/*      */       } 
/* 1875 */       return this.keyBuilder_;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1884 */   private static final Keyset DEFAULT_INSTANCE = new Keyset();
/*      */ 
/*      */   
/*      */   public static Keyset getDefaultInstance() {
/* 1888 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1892 */   private static final Parser<Keyset> PARSER = (Parser<Keyset>)new AbstractParser<Keyset>()
/*      */     {
/*      */ 
/*      */       
/*      */       public Keyset parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1898 */         Keyset.Builder builder = Keyset.newBuilder();
/*      */         try {
/* 1900 */           builder.mergeFrom(input, extensionRegistry);
/* 1901 */         } catch (InvalidProtocolBufferException e) {
/* 1902 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1903 */         } catch (UninitializedMessageException e) {
/* 1904 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1905 */         } catch (IOException e) {
/* 1906 */           throw (new InvalidProtocolBufferException(e))
/* 1907 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1909 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<Keyset> parser() {
/* 1914 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<Keyset> getParserForType() {
/* 1919 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Keyset getDefaultInstanceForType() {
/* 1924 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */   
/*      */   public static interface KeyOrBuilder extends MessageOrBuilder {
/*      */     boolean hasKeyData();
/*      */     
/*      */     KeyData getKeyData();
/*      */     
/*      */     KeyDataOrBuilder getKeyDataOrBuilder();
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Keyset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */