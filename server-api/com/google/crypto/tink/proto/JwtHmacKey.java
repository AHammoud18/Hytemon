/*      */ package com.google.crypto.tink.proto;
/*      */ import com.google.protobuf.ByteString;
/*      */ import com.google.protobuf.ExtensionRegistryLite;
/*      */ import com.google.protobuf.InvalidProtocolBufferException;
/*      */ import com.google.protobuf.Message;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ public final class JwtHmacKey extends GeneratedMessage implements JwtHmacKeyOrBuilder {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private int bitField0_;
/*      */   public static final int VERSION_FIELD_NUMBER = 1;
/*      */   private int version_;
/*      */   public static final int ALGORITHM_FIELD_NUMBER = 2;
/*      */   private int algorithm_;
/*      */   public static final int KEY_VALUE_FIELD_NUMBER = 3;
/*      */   private ByteString keyValue_;
/*      */   public static final int CUSTOM_KID_FIELD_NUMBER = 4;
/*      */   private CustomKid customKid_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtHmacKey.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   28 */         .getName());
/*      */   }
/*      */   
/*      */   private JwtHmacKey(GeneratedMessage.Builder<?> builder) {
/*   32 */     super(builder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  574 */     this.version_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  585 */     this.algorithm_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  603 */     this.keyValue_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  643 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtHmacKey.class, Builder.class); } public static final class CustomKid extends GeneratedMessage implements CustomKidOrBuilder { private static final long serialVersionUID = 0L; public static final int VALUE_FIELD_NUMBER = 1; private volatile Object value_; private byte memoizedIsInitialized; static { RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", CustomKid.class.getName()); } private CustomKid(GeneratedMessage.Builder<?> builder) { super(builder); this.value_ = ""; this.memoizedIsInitialized = -1; } private CustomKid() { this.value_ = ""; this.memoizedIsInitialized = -1; this.value_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_CustomKid_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_CustomKid_fieldAccessorTable.ensureFieldAccessorsInitialized(CustomKid.class, Builder.class); } public String getValue() { Object ref = this.value_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.value_ = s; return s; } public ByteString getValueBytes() { Object ref = this.value_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.value_ = b; return b; }  return (ByteString)ref; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized; if (isInitialized == 1) return true;  if (isInitialized == 0) return false;  this.memoizedIsInitialized = 1; return true; } public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.value_)) GeneratedMessage.writeString(output, 1, this.value_);  getUnknownFields().writeTo(output); } public int getSerializedSize() { int size = this.memoizedSize; if (size != -1) return size;  size = 0; if (!GeneratedMessage.isStringEmpty(this.value_)) size += GeneratedMessage.computeStringSize(1, this.value_);  size += getUnknownFields().getSerializedSize(); this.memoizedSize = size; return size; } public boolean equals(Object obj) { if (obj == this) return true;  if (!(obj instanceof CustomKid)) return super.equals(obj);  CustomKid other = (CustomKid)obj; if (!getValue().equals(other.getValue())) return false;  if (!getUnknownFields().equals(other.getUnknownFields())) return false;  return true; } public int hashCode() { if (this.memoizedHashCode != 0) return this.memoizedHashCode;  int hash = 41; hash = 19 * hash + getDescriptor().hashCode(); hash = 37 * hash + 1; hash = 53 * hash + getValue().hashCode(); hash = 29 * hash + getUnknownFields().hashCode(); this.memoizedHashCode = hash; return hash; } public static CustomKid parseFrom(ByteBuffer data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(ByteString data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(byte[] data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(InputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input); } public static CustomKid parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public static CustomKid parseDelimitedFrom(InputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseDelimitedWithIOException(PARSER, input); } public static CustomKid parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry); } public static CustomKid parseFrom(CodedInputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input); } public static CustomKid parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public Builder newBuilderForType() { return newBuilder(); } public static Builder newBuilder() { return DEFAULT_INSTANCE.toBuilder(); } public static Builder newBuilder(CustomKid prototype) { return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype); } public Builder toBuilder() { return (this == DEFAULT_INSTANCE) ? new Builder() : (new Builder()).mergeFrom(this); } protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) { Builder builder = new Builder(parent); return builder; } public static final class Builder extends GeneratedMessage.Builder<Builder> implements JwtHmacKey.CustomKidOrBuilder { private int bitField0_; private Object value_; public static final Descriptors.Descriptor getDescriptor() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_CustomKid_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_CustomKid_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtHmacKey.CustomKid.class, Builder.class); } private Builder() { this.value_ = ""; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.value_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.value_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_CustomKid_descriptor; } public JwtHmacKey.CustomKid getDefaultInstanceForType() { return JwtHmacKey.CustomKid.getDefaultInstance(); } public JwtHmacKey.CustomKid build() { JwtHmacKey.CustomKid result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtHmacKey.CustomKid buildPartial() { JwtHmacKey.CustomKid result = new JwtHmacKey.CustomKid(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JwtHmacKey.CustomKid result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.value_ = this.value_;  } public Builder mergeFrom(Message other) { if (other instanceof JwtHmacKey.CustomKid) return mergeFrom((JwtHmacKey.CustomKid)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JwtHmacKey.CustomKid other) { if (other == JwtHmacKey.CustomKid.getDefaultInstance()) return this;  if (!other.getValue().isEmpty()) { this.value_ = other.value_; this.bitField0_ |= 0x1; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.value_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getValue() { Object ref = this.value_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.value_ = s; return s; }  return (String)ref; } public ByteString getValueBytes() { Object ref = this.value_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.value_ = b; return b; }  return (ByteString)ref; } public Builder setValue(String value) { if (value == null) throw new NullPointerException();  this.value_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearValue() { this.value_ = JwtHmacKey.CustomKid.getDefaultInstance().getValue(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; } public Builder setValueBytes(ByteString value) { if (value == null) throw new NullPointerException();  JwtHmacKey.CustomKid.checkByteStringIsUtf8(value); this.value_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } } private static final CustomKid DEFAULT_INSTANCE = new CustomKid(); public static CustomKid getDefaultInstance() { return DEFAULT_INSTANCE; } private static final Parser<CustomKid> PARSER = (Parser<CustomKid>)new AbstractParser<CustomKid>() { public JwtHmacKey.CustomKid parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { JwtHmacKey.CustomKid.Builder builder = JwtHmacKey.CustomKid.newBuilder(); try { builder.mergeFrom(input, extensionRegistry); } catch (InvalidProtocolBufferException e) { throw e.setUnfinishedMessage(builder.buildPartial()); } catch (UninitializedMessageException e) { throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial()); } catch (IOException e) { throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial()); }  return builder.buildPartial(); } }; public static Parser<CustomKid> parser() { return PARSER; } public Parser<CustomKid> getParserForType() { return PARSER; } public CustomKid getDefaultInstanceForType() { return DEFAULT_INSTANCE; } } private JwtHmacKey() { this.version_ = 0; this.algorithm_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.algorithm_ = 0;
/*      */     this.keyValue_ = ByteString.EMPTY; }
/*      */   public int getVersion() { return this.version_; }
/*  646 */   public int getAlgorithmValue() { return this.algorithm_; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  647 */     if (isInitialized == 1) return true; 
/*  648 */     if (isInitialized == 0) return false;
/*      */     
/*  650 */     this.memoizedIsInitialized = 1;
/*  651 */     return true; } public JwtHmacAlgorithm getAlgorithm() { JwtHmacAlgorithm result = JwtHmacAlgorithm.forNumber(this.algorithm_);
/*      */     return (result == null) ? JwtHmacAlgorithm.UNRECOGNIZED : result; }
/*      */   public ByteString getKeyValue() { return this.keyValue_; }
/*      */   public boolean hasCustomKid() { return ((this.bitField0_ & 0x1) != 0); }
/*      */   public CustomKid getCustomKid() { return (this.customKid_ == null) ? CustomKid.getDefaultInstance() : this.customKid_; }
/*      */   public CustomKidOrBuilder getCustomKidOrBuilder() { return (this.customKid_ == null) ? CustomKid.getDefaultInstance() : this.customKid_; }
/*  657 */   public void writeTo(CodedOutputStream output) throws IOException { if (this.version_ != 0) {
/*  658 */       output.writeUInt32(1, this.version_);
/*      */     }
/*  660 */     if (this.algorithm_ != JwtHmacAlgorithm.HS_UNKNOWN.getNumber()) {
/*  661 */       output.writeEnum(2, this.algorithm_);
/*      */     }
/*  663 */     if (!this.keyValue_.isEmpty()) {
/*  664 */       output.writeBytes(3, this.keyValue_);
/*      */     }
/*  666 */     if ((this.bitField0_ & 0x1) != 0) {
/*  667 */       output.writeMessage(4, (MessageLite)getCustomKid());
/*      */     }
/*  669 */     getUnknownFields().writeTo(output); }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  674 */     int size = this.memoizedSize;
/*  675 */     if (size != -1) return size;
/*      */     
/*  677 */     size = 0;
/*  678 */     if (this.version_ != 0) {
/*  679 */       size += 
/*  680 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*      */     }
/*  682 */     if (this.algorithm_ != JwtHmacAlgorithm.HS_UNKNOWN.getNumber()) {
/*  683 */       size += 
/*  684 */         CodedOutputStream.computeEnumSize(2, this.algorithm_);
/*      */     }
/*  686 */     if (!this.keyValue_.isEmpty()) {
/*  687 */       size += 
/*  688 */         CodedOutputStream.computeBytesSize(3, this.keyValue_);
/*      */     }
/*  690 */     if ((this.bitField0_ & 0x1) != 0) {
/*  691 */       size += 
/*  692 */         CodedOutputStream.computeMessageSize(4, (MessageLite)getCustomKid());
/*      */     }
/*  694 */     size += getUnknownFields().getSerializedSize();
/*  695 */     this.memoizedSize = size;
/*  696 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  701 */     if (obj == this) {
/*  702 */       return true;
/*      */     }
/*  704 */     if (!(obj instanceof JwtHmacKey)) {
/*  705 */       return super.equals(obj);
/*      */     }
/*  707 */     JwtHmacKey other = (JwtHmacKey)obj;
/*      */     
/*  709 */     if (getVersion() != other
/*  710 */       .getVersion()) return false; 
/*  711 */     if (this.algorithm_ != other.algorithm_) return false;
/*      */     
/*  713 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/*  714 */     if (hasCustomKid() != other.hasCustomKid()) return false; 
/*  715 */     if (hasCustomKid() && 
/*      */       
/*  717 */       !getCustomKid().equals(other.getCustomKid())) return false;
/*      */     
/*  719 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  720 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  725 */     if (this.memoizedHashCode != 0) {
/*  726 */       return this.memoizedHashCode;
/*      */     }
/*  728 */     int hash = 41;
/*  729 */     hash = 19 * hash + getDescriptor().hashCode();
/*  730 */     hash = 37 * hash + 1;
/*  731 */     hash = 53 * hash + getVersion();
/*  732 */     hash = 37 * hash + 2;
/*  733 */     hash = 53 * hash + this.algorithm_;
/*  734 */     hash = 37 * hash + 3;
/*  735 */     hash = 53 * hash + getKeyValue().hashCode();
/*  736 */     if (hasCustomKid()) {
/*  737 */       hash = 37 * hash + 4;
/*  738 */       hash = 53 * hash + getCustomKid().hashCode();
/*      */     } 
/*  740 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  741 */     this.memoizedHashCode = hash;
/*  742 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  748 */     return (JwtHmacKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  754 */     return (JwtHmacKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  759 */     return (JwtHmacKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  765 */     return (JwtHmacKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtHmacKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  769 */     return (JwtHmacKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  775 */     return (JwtHmacKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtHmacKey parseFrom(InputStream input) throws IOException {
/*  779 */     return 
/*  780 */       (JwtHmacKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  786 */     return 
/*  787 */       (JwtHmacKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseDelimitedFrom(InputStream input) throws IOException {
/*  792 */     return 
/*  793 */       (JwtHmacKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  800 */     return 
/*  801 */       (JwtHmacKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(CodedInputStream input) throws IOException {
/*  806 */     return 
/*  807 */       (JwtHmacKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtHmacKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  813 */     return 
/*  814 */       (JwtHmacKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  818 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  820 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(JwtHmacKey prototype) {
/*  823 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  827 */     return (this == DEFAULT_INSTANCE) ? 
/*  828 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  834 */     Builder builder = new Builder(parent);
/*  835 */     return builder;
/*      */   }
/*      */   
/*      */   public static final class Builder
/*      */     extends GeneratedMessage.Builder<Builder>
/*      */     implements JwtHmacKeyOrBuilder
/*      */   {
/*      */     private int bitField0_;
/*      */     private int version_;
/*      */     private int algorithm_;
/*      */     private ByteString keyValue_;
/*      */     private JwtHmacKey.CustomKid customKid_;
/*      */     private SingleFieldBuilder<JwtHmacKey.CustomKid, JwtHmacKey.CustomKid.Builder, JwtHmacKey.CustomKidOrBuilder> customKidBuilder_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  850 */       return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  856 */       return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_fieldAccessorTable
/*  857 */         .ensureFieldAccessorsInitialized(JwtHmacKey.class, Builder.class);
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
/*      */     private Builder()
/*      */     {
/* 1062 */       this.algorithm_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1113 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.algorithm_ = 0; this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (JwtHmacKey.alwaysUseFieldBuilders) internalGetCustomKidFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.algorithm_ = 0; this.keyValue_ = ByteString.EMPTY; this.customKid_ = null; if (this.customKidBuilder_ != null) { this.customKidBuilder_.dispose(); this.customKidBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKey_descriptor; } public JwtHmacKey getDefaultInstanceForType() { return JwtHmacKey.getDefaultInstance(); } public JwtHmacKey build() { JwtHmacKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtHmacKey buildPartial() { JwtHmacKey result = new JwtHmacKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JwtHmacKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0) result.algorithm_ = this.algorithm_;  if ((from_bitField0_ & 0x4) != 0) result.keyValue_ = this.keyValue_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x8) != 0) { result.customKid_ = (this.customKidBuilder_ == null) ? this.customKid_ : (JwtHmacKey.CustomKid)this.customKidBuilder_.build(); to_bitField0_ |= 0x1; }  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof JwtHmacKey) return mergeFrom((JwtHmacKey)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JwtHmacKey other) { if (other == JwtHmacKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.algorithm_ != 0) setAlgorithmValue(other.getAlgorithmValue());  if (!other.getKeyValue().isEmpty()) setKeyValue(other.getKeyValue());  if (other.hasCustomKid()) mergeCustomKid(other.getCustomKid());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*      */     public final boolean isInitialized() { return true; }
/*      */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 16: this.algorithm_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 26: this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: input.readMessage((MessageLite.Builder)internalGetCustomKidFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*      */     public int getVersion() { return this.version_; }
/*      */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*      */     public int getAlgorithmValue() { return this.algorithm_; }
/*      */     public Builder setAlgorithmValue(int value) { this.algorithm_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public JwtHmacAlgorithm getAlgorithm() { JwtHmacAlgorithm result = JwtHmacAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtHmacAlgorithm.UNRECOGNIZED : result; }
/*      */     public Builder setAlgorithm(JwtHmacAlgorithm value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.algorithm_ = value.getNumber(); onChanged(); return this; }
/*      */     public Builder clearAlgorithm() { this.bitField0_ &= 0xFFFFFFFD; this.algorithm_ = 0; onChanged(); return this; }
/* 1124 */     public ByteString getKeyValue() { return this.keyValue_; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setKeyValue(ByteString value) {
/* 1136 */       if (value == null) throw new NullPointerException(); 
/* 1137 */       this.keyValue_ = value;
/* 1138 */       this.bitField0_ |= 0x4;
/* 1139 */       onChanged();
/* 1140 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearKeyValue() {
/* 1151 */       this.bitField0_ &= 0xFFFFFFFB;
/* 1152 */       this.keyValue_ = JwtHmacKey.getDefaultInstance().getKeyValue();
/* 1153 */       onChanged();
/* 1154 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasCustomKid() {
/* 1165 */       return ((this.bitField0_ & 0x8) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtHmacKey.CustomKid getCustomKid() {
/* 1172 */       if (this.customKidBuilder_ == null) {
/* 1173 */         return (this.customKid_ == null) ? JwtHmacKey.CustomKid.getDefaultInstance() : this.customKid_;
/*      */       }
/* 1175 */       return (JwtHmacKey.CustomKid)this.customKidBuilder_.getMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCustomKid(JwtHmacKey.CustomKid value) {
/* 1182 */       if (this.customKidBuilder_ == null) {
/* 1183 */         if (value == null) {
/* 1184 */           throw new NullPointerException();
/*      */         }
/* 1186 */         this.customKid_ = value;
/*      */       } else {
/* 1188 */         this.customKidBuilder_.setMessage(value);
/*      */       } 
/* 1190 */       this.bitField0_ |= 0x8;
/* 1191 */       onChanged();
/* 1192 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCustomKid(JwtHmacKey.CustomKid.Builder builderForValue) {
/* 1199 */       if (this.customKidBuilder_ == null) {
/* 1200 */         this.customKid_ = builderForValue.build();
/*      */       } else {
/* 1202 */         this.customKidBuilder_.setMessage(builderForValue.build());
/*      */       } 
/* 1204 */       this.bitField0_ |= 0x8;
/* 1205 */       onChanged();
/* 1206 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeCustomKid(JwtHmacKey.CustomKid value) {
/* 1212 */       if (this.customKidBuilder_ == null) {
/* 1213 */         if ((this.bitField0_ & 0x8) != 0 && this.customKid_ != null && this.customKid_ != 
/*      */           
/* 1215 */           JwtHmacKey.CustomKid.getDefaultInstance()) {
/* 1216 */           getCustomKidBuilder().mergeFrom(value);
/*      */         } else {
/* 1218 */           this.customKid_ = value;
/*      */         } 
/*      */       } else {
/* 1221 */         this.customKidBuilder_.mergeFrom(value);
/*      */       } 
/* 1223 */       if (this.customKid_ != null) {
/* 1224 */         this.bitField0_ |= 0x8;
/* 1225 */         onChanged();
/*      */       } 
/* 1227 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearCustomKid() {
/* 1233 */       this.bitField0_ &= 0xFFFFFFF7;
/* 1234 */       this.customKid_ = null;
/* 1235 */       if (this.customKidBuilder_ != null) {
/* 1236 */         this.customKidBuilder_.dispose();
/* 1237 */         this.customKidBuilder_ = null;
/*      */       } 
/* 1239 */       onChanged();
/* 1240 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtHmacKey.CustomKid.Builder getCustomKidBuilder() {
/* 1246 */       this.bitField0_ |= 0x8;
/* 1247 */       onChanged();
/* 1248 */       return (JwtHmacKey.CustomKid.Builder)internalGetCustomKidFieldBuilder().getBuilder();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtHmacKey.CustomKidOrBuilder getCustomKidOrBuilder() {
/* 1254 */       if (this.customKidBuilder_ != null) {
/* 1255 */         return (JwtHmacKey.CustomKidOrBuilder)this.customKidBuilder_.getMessageOrBuilder();
/*      */       }
/* 1257 */       return (this.customKid_ == null) ? 
/* 1258 */         JwtHmacKey.CustomKid.getDefaultInstance() : this.customKid_;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private SingleFieldBuilder<JwtHmacKey.CustomKid, JwtHmacKey.CustomKid.Builder, JwtHmacKey.CustomKidOrBuilder> internalGetCustomKidFieldBuilder() {
/* 1267 */       if (this.customKidBuilder_ == null) {
/* 1268 */         this
/*      */ 
/*      */ 
/*      */           
/* 1272 */           .customKidBuilder_ = new SingleFieldBuilder(getCustomKid(), getParentForChildren(), isClean());
/* 1273 */         this.customKid_ = null;
/*      */       } 
/* 1275 */       return this.customKidBuilder_;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1284 */   private static final JwtHmacKey DEFAULT_INSTANCE = new JwtHmacKey();
/*      */ 
/*      */   
/*      */   public static JwtHmacKey getDefaultInstance() {
/* 1288 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1292 */   private static final Parser<JwtHmacKey> PARSER = (Parser<JwtHmacKey>)new AbstractParser<JwtHmacKey>()
/*      */     {
/*      */ 
/*      */       
/*      */       public JwtHmacKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1298 */         JwtHmacKey.Builder builder = JwtHmacKey.newBuilder();
/*      */         try {
/* 1300 */           builder.mergeFrom(input, extensionRegistry);
/* 1301 */         } catch (InvalidProtocolBufferException e) {
/* 1302 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1303 */         } catch (UninitializedMessageException e) {
/* 1304 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1305 */         } catch (IOException e) {
/* 1306 */           throw (new InvalidProtocolBufferException(e))
/* 1307 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1309 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<JwtHmacKey> parser() {
/* 1314 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<JwtHmacKey> getParserForType() {
/* 1319 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public JwtHmacKey getDefaultInstanceForType() {
/* 1324 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */   
/*      */   public static interface CustomKidOrBuilder extends MessageOrBuilder {
/*      */     String getValue();
/*      */     
/*      */     ByteString getValueBytes();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtHmacKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */