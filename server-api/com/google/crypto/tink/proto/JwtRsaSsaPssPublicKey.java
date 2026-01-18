/*      */ package com.google.crypto.tink.proto;
/*      */ import com.google.protobuf.ExtensionRegistryLite;
/*      */ import com.google.protobuf.InvalidProtocolBufferException;
/*      */ import com.google.protobuf.Message;
/*      */ 
/*      */ public final class JwtRsaSsaPssPublicKey extends GeneratedMessage implements JwtRsaSsaPssPublicKeyOrBuilder {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private int bitField0_;
/*      */   public static final int VERSION_FIELD_NUMBER = 1;
/*      */   private int version_;
/*      */   public static final int ALGORITHM_FIELD_NUMBER = 2;
/*      */   private int algorithm_;
/*      */   public static final int N_FIELD_NUMBER = 3;
/*      */   private ByteString n_;
/*      */   public static final int E_FIELD_NUMBER = 4;
/*      */   private ByteString e_;
/*      */   public static final int CUSTOM_KID_FIELD_NUMBER = 5;
/*      */   private CustomKid customKid_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtRsaSsaPssPublicKey.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   28 */         .getName());
/*      */   }
/*      */   
/*      */   private JwtRsaSsaPssPublicKey(GeneratedMessage.Builder<?> builder) {
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
/*      */     
/*  575 */     this.version_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  586 */     this.algorithm_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  604 */     this.n_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  620 */     this.e_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  661 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtRsaSsaPssPublicKey.class, Builder.class); } public static final class CustomKid extends GeneratedMessage implements CustomKidOrBuilder { private static final long serialVersionUID = 0L; public static final int VALUE_FIELD_NUMBER = 1; private volatile Object value_; private byte memoizedIsInitialized; static { RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", CustomKid.class.getName()); } private CustomKid(GeneratedMessage.Builder<?> builder) { super(builder); this.value_ = ""; this.memoizedIsInitialized = -1; } private CustomKid() { this.value_ = ""; this.memoizedIsInitialized = -1; this.value_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_fieldAccessorTable.ensureFieldAccessorsInitialized(CustomKid.class, Builder.class); } public String getValue() { Object ref = this.value_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.value_ = s; return s; } public ByteString getValueBytes() { Object ref = this.value_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.value_ = b; return b; }  return (ByteString)ref; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized; if (isInitialized == 1) return true;  if (isInitialized == 0) return false;  this.memoizedIsInitialized = 1; return true; } public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.value_)) GeneratedMessage.writeString(output, 1, this.value_);  getUnknownFields().writeTo(output); } public int getSerializedSize() { int size = this.memoizedSize; if (size != -1) return size;  size = 0; if (!GeneratedMessage.isStringEmpty(this.value_)) size += GeneratedMessage.computeStringSize(1, this.value_);  size += getUnknownFields().getSerializedSize(); this.memoizedSize = size; return size; } public boolean equals(Object obj) { if (obj == this) return true;  if (!(obj instanceof CustomKid)) return super.equals(obj);  CustomKid other = (CustomKid)obj; if (!getValue().equals(other.getValue())) return false;  if (!getUnknownFields().equals(other.getUnknownFields())) return false;  return true; } public int hashCode() { if (this.memoizedHashCode != 0) return this.memoizedHashCode;  int hash = 41; hash = 19 * hash + getDescriptor().hashCode(); hash = 37 * hash + 1; hash = 53 * hash + getValue().hashCode(); hash = 29 * hash + getUnknownFields().hashCode(); this.memoizedHashCode = hash; return hash; } public static CustomKid parseFrom(ByteBuffer data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(ByteString data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(byte[] data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(InputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input); } public static CustomKid parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public static CustomKid parseDelimitedFrom(InputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseDelimitedWithIOException(PARSER, input); } public static CustomKid parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry); } public static CustomKid parseFrom(CodedInputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input); } public static CustomKid parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public Builder newBuilderForType() { return newBuilder(); } public static Builder newBuilder() { return DEFAULT_INSTANCE.toBuilder(); } public static Builder newBuilder(CustomKid prototype) { return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype); } public Builder toBuilder() { return (this == DEFAULT_INSTANCE) ? new Builder() : (new Builder()).mergeFrom(this); } protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) { Builder builder = new Builder(parent); return builder; } public static final class Builder extends GeneratedMessage.Builder<Builder> implements JwtRsaSsaPssPublicKey.CustomKidOrBuilder { private int bitField0_; private Object value_; public static final Descriptors.Descriptor getDescriptor() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtRsaSsaPssPublicKey.CustomKid.class, Builder.class); } private Builder() { this.value_ = ""; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.value_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.value_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_CustomKid_descriptor; } public JwtRsaSsaPssPublicKey.CustomKid getDefaultInstanceForType() { return JwtRsaSsaPssPublicKey.CustomKid.getDefaultInstance(); } public JwtRsaSsaPssPublicKey.CustomKid build() { JwtRsaSsaPssPublicKey.CustomKid result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtRsaSsaPssPublicKey.CustomKid buildPartial() { JwtRsaSsaPssPublicKey.CustomKid result = new JwtRsaSsaPssPublicKey.CustomKid(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JwtRsaSsaPssPublicKey.CustomKid result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.value_ = this.value_;  } public Builder mergeFrom(Message other) { if (other instanceof JwtRsaSsaPssPublicKey.CustomKid) return mergeFrom((JwtRsaSsaPssPublicKey.CustomKid)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JwtRsaSsaPssPublicKey.CustomKid other) { if (other == JwtRsaSsaPssPublicKey.CustomKid.getDefaultInstance()) return this;  if (!other.getValue().isEmpty()) { this.value_ = other.value_; this.bitField0_ |= 0x1; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.value_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getValue() { Object ref = this.value_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.value_ = s; return s; }  return (String)ref; } public ByteString getValueBytes() { Object ref = this.value_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.value_ = b; return b; }  return (ByteString)ref; } public Builder setValue(String value) { if (value == null) throw new NullPointerException();  this.value_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearValue() { this.value_ = JwtRsaSsaPssPublicKey.CustomKid.getDefaultInstance().getValue(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; } public Builder setValueBytes(ByteString value) { if (value == null) throw new NullPointerException();  JwtRsaSsaPssPublicKey.CustomKid.checkByteStringIsUtf8(value); this.value_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } } private static final CustomKid DEFAULT_INSTANCE = new CustomKid(); public static CustomKid getDefaultInstance() { return DEFAULT_INSTANCE; } private static final Parser<CustomKid> PARSER = (Parser<CustomKid>)new AbstractParser<CustomKid>() { public JwtRsaSsaPssPublicKey.CustomKid parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { JwtRsaSsaPssPublicKey.CustomKid.Builder builder = JwtRsaSsaPssPublicKey.CustomKid.newBuilder(); try { builder.mergeFrom(input, extensionRegistry); } catch (InvalidProtocolBufferException e) { throw e.setUnfinishedMessage(builder.buildPartial()); } catch (UninitializedMessageException e) { throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial()); } catch (IOException e) { throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial()); }  return builder.buildPartial(); } }; public static Parser<CustomKid> parser() { return PARSER; } public Parser<CustomKid> getParserForType() { return PARSER; } public CustomKid getDefaultInstanceForType() { return DEFAULT_INSTANCE; } } private JwtRsaSsaPssPublicKey() { this.version_ = 0; this.algorithm_ = 0; this.n_ = ByteString.EMPTY; this.e_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.algorithm_ = 0; this.n_ = ByteString.EMPTY;
/*      */     this.e_ = ByteString.EMPTY; }
/*      */   public int getVersion() { return this.version_; }
/*  664 */   public int getAlgorithmValue() { return this.algorithm_; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  665 */     if (isInitialized == 1) return true; 
/*  666 */     if (isInitialized == 0) return false;
/*      */     
/*  668 */     this.memoizedIsInitialized = 1;
/*  669 */     return true; } public JwtRsaSsaPssAlgorithm getAlgorithm() { JwtRsaSsaPssAlgorithm result = JwtRsaSsaPssAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtRsaSsaPssAlgorithm.UNRECOGNIZED : result; }
/*      */   public ByteString getN() { return this.n_; }
/*      */   public ByteString getE() { return this.e_; }
/*      */   public boolean hasCustomKid() { return ((this.bitField0_ & 0x1) != 0); }
/*      */   public CustomKid getCustomKid() { return (this.customKid_ == null) ? CustomKid.getDefaultInstance() : this.customKid_; }
/*      */   public CustomKidOrBuilder getCustomKidOrBuilder() { return (this.customKid_ == null) ? CustomKid.getDefaultInstance() : this.customKid_; }
/*  675 */   public void writeTo(CodedOutputStream output) throws IOException { if (this.version_ != 0) {
/*  676 */       output.writeUInt32(1, this.version_);
/*      */     }
/*  678 */     if (this.algorithm_ != JwtRsaSsaPssAlgorithm.PS_UNKNOWN.getNumber()) {
/*  679 */       output.writeEnum(2, this.algorithm_);
/*      */     }
/*  681 */     if (!this.n_.isEmpty()) {
/*  682 */       output.writeBytes(3, this.n_);
/*      */     }
/*  684 */     if (!this.e_.isEmpty()) {
/*  685 */       output.writeBytes(4, this.e_);
/*      */     }
/*  687 */     if ((this.bitField0_ & 0x1) != 0) {
/*  688 */       output.writeMessage(5, (MessageLite)getCustomKid());
/*      */     }
/*  690 */     getUnknownFields().writeTo(output); }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  695 */     int size = this.memoizedSize;
/*  696 */     if (size != -1) return size;
/*      */     
/*  698 */     size = 0;
/*  699 */     if (this.version_ != 0) {
/*  700 */       size += 
/*  701 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*      */     }
/*  703 */     if (this.algorithm_ != JwtRsaSsaPssAlgorithm.PS_UNKNOWN.getNumber()) {
/*  704 */       size += 
/*  705 */         CodedOutputStream.computeEnumSize(2, this.algorithm_);
/*      */     }
/*  707 */     if (!this.n_.isEmpty()) {
/*  708 */       size += 
/*  709 */         CodedOutputStream.computeBytesSize(3, this.n_);
/*      */     }
/*  711 */     if (!this.e_.isEmpty()) {
/*  712 */       size += 
/*  713 */         CodedOutputStream.computeBytesSize(4, this.e_);
/*      */     }
/*  715 */     if ((this.bitField0_ & 0x1) != 0) {
/*  716 */       size += 
/*  717 */         CodedOutputStream.computeMessageSize(5, (MessageLite)getCustomKid());
/*      */     }
/*  719 */     size += getUnknownFields().getSerializedSize();
/*  720 */     this.memoizedSize = size;
/*  721 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  726 */     if (obj == this) {
/*  727 */       return true;
/*      */     }
/*  729 */     if (!(obj instanceof JwtRsaSsaPssPublicKey)) {
/*  730 */       return super.equals(obj);
/*      */     }
/*  732 */     JwtRsaSsaPssPublicKey other = (JwtRsaSsaPssPublicKey)obj;
/*      */     
/*  734 */     if (getVersion() != other
/*  735 */       .getVersion()) return false; 
/*  736 */     if (this.algorithm_ != other.algorithm_) return false;
/*      */     
/*  738 */     if (!getN().equals(other.getN())) return false;
/*      */     
/*  740 */     if (!getE().equals(other.getE())) return false; 
/*  741 */     if (hasCustomKid() != other.hasCustomKid()) return false; 
/*  742 */     if (hasCustomKid() && 
/*      */       
/*  744 */       !getCustomKid().equals(other.getCustomKid())) return false;
/*      */     
/*  746 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  747 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  752 */     if (this.memoizedHashCode != 0) {
/*  753 */       return this.memoizedHashCode;
/*      */     }
/*  755 */     int hash = 41;
/*  756 */     hash = 19 * hash + getDescriptor().hashCode();
/*  757 */     hash = 37 * hash + 1;
/*  758 */     hash = 53 * hash + getVersion();
/*  759 */     hash = 37 * hash + 2;
/*  760 */     hash = 53 * hash + this.algorithm_;
/*  761 */     hash = 37 * hash + 3;
/*  762 */     hash = 53 * hash + getN().hashCode();
/*  763 */     hash = 37 * hash + 4;
/*  764 */     hash = 53 * hash + getE().hashCode();
/*  765 */     if (hasCustomKid()) {
/*  766 */       hash = 37 * hash + 5;
/*  767 */       hash = 53 * hash + getCustomKid().hashCode();
/*      */     } 
/*  769 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  770 */     this.memoizedHashCode = hash;
/*  771 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  777 */     return (JwtRsaSsaPssPublicKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  783 */     return (JwtRsaSsaPssPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  788 */     return (JwtRsaSsaPssPublicKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  794 */     return (JwtRsaSsaPssPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  798 */     return (JwtRsaSsaPssPublicKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  804 */     return (JwtRsaSsaPssPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(InputStream input) throws IOException {
/*  808 */     return 
/*  809 */       (JwtRsaSsaPssPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  815 */     return 
/*  816 */       (JwtRsaSsaPssPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseDelimitedFrom(InputStream input) throws IOException {
/*  821 */     return 
/*  822 */       (JwtRsaSsaPssPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  829 */     return 
/*  830 */       (JwtRsaSsaPssPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(CodedInputStream input) throws IOException {
/*  835 */     return 
/*  836 */       (JwtRsaSsaPssPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  842 */     return 
/*  843 */       (JwtRsaSsaPssPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  847 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  849 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(JwtRsaSsaPssPublicKey prototype) {
/*  852 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  856 */     return (this == DEFAULT_INSTANCE) ? 
/*  857 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  863 */     Builder builder = new Builder(parent);
/*  864 */     return builder;
/*      */   }
/*      */   
/*      */   public static final class Builder
/*      */     extends GeneratedMessage.Builder<Builder>
/*      */     implements JwtRsaSsaPssPublicKeyOrBuilder {
/*      */     private int bitField0_;
/*      */     private int version_;
/*      */     private int algorithm_;
/*      */     private ByteString n_;
/*      */     private ByteString e_;
/*      */     private JwtRsaSsaPssPublicKey.CustomKid customKid_;
/*      */     private SingleFieldBuilder<JwtRsaSsaPssPublicKey.CustomKid, JwtRsaSsaPssPublicKey.CustomKid.Builder, JwtRsaSsaPssPublicKey.CustomKidOrBuilder> customKidBuilder_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  879 */       return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  885 */       return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_fieldAccessorTable
/*  886 */         .ensureFieldAccessorsInitialized(JwtRsaSsaPssPublicKey.class, Builder.class);
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
/*      */     private Builder()
/*      */     {
/* 1103 */       this.algorithm_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1154 */       this.n_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1201 */       this.e_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.algorithm_ = 0; this.n_ = ByteString.EMPTY; this.e_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (JwtRsaSsaPssPublicKey.alwaysUseFieldBuilders) internalGetCustomKidFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.algorithm_ = 0; this.n_ = ByteString.EMPTY; this.e_ = ByteString.EMPTY; this.customKid_ = null; if (this.customKidBuilder_ != null) { this.customKidBuilder_.dispose(); this.customKidBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPublicKey_descriptor; } public JwtRsaSsaPssPublicKey getDefaultInstanceForType() { return JwtRsaSsaPssPublicKey.getDefaultInstance(); } public JwtRsaSsaPssPublicKey build() { JwtRsaSsaPssPublicKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtRsaSsaPssPublicKey buildPartial() { JwtRsaSsaPssPublicKey result = new JwtRsaSsaPssPublicKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JwtRsaSsaPssPublicKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0) result.algorithm_ = this.algorithm_;  if ((from_bitField0_ & 0x4) != 0) result.n_ = this.n_;  if ((from_bitField0_ & 0x8) != 0) result.e_ = this.e_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x10) != 0) { result.customKid_ = (this.customKidBuilder_ == null) ? this.customKid_ : (JwtRsaSsaPssPublicKey.CustomKid)this.customKidBuilder_.build(); to_bitField0_ |= 0x1; }  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof JwtRsaSsaPssPublicKey) return mergeFrom((JwtRsaSsaPssPublicKey)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JwtRsaSsaPssPublicKey other) { if (other == JwtRsaSsaPssPublicKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.algorithm_ != 0) setAlgorithmValue(other.getAlgorithmValue());  if (!other.getN().isEmpty()) setN(other.getN());  if (!other.getE().isEmpty()) setE(other.getE());  if (other.hasCustomKid()) mergeCustomKid(other.getCustomKid());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 16: this.algorithm_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 26: this.n_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.e_ = input.readBytes(); this.bitField0_ |= 0x8; continue;case 42: input.readMessage((MessageLite.Builder)internalGetCustomKidFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x10; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*      */     public int getVersion() { return this.version_; }
/*      */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*      */     public int getAlgorithmValue() { return this.algorithm_; }
/*      */     public Builder setAlgorithmValue(int value) { this.algorithm_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public JwtRsaSsaPssAlgorithm getAlgorithm() { JwtRsaSsaPssAlgorithm result = JwtRsaSsaPssAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtRsaSsaPssAlgorithm.UNRECOGNIZED : result; }
/*      */     public Builder setAlgorithm(JwtRsaSsaPssAlgorithm value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.algorithm_ = value.getNumber(); onChanged(); return this; }
/*      */     public Builder clearAlgorithm() { this.bitField0_ &= 0xFFFFFFFD; this.algorithm_ = 0; onChanged(); return this; }
/*      */     public ByteString getN() { return this.n_; }
/*      */     public Builder setN(ByteString value) { if (value == null) throw new NullPointerException();  this.n_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*      */     public Builder clearN() { this.bitField0_ &= 0xFFFFFFFB; this.n_ = JwtRsaSsaPssPublicKey.getDefaultInstance().getN(); onChanged(); return this; }
/* 1213 */     public ByteString getE() { return this.e_; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setE(ByteString value) {
/* 1226 */       if (value == null) throw new NullPointerException(); 
/* 1227 */       this.e_ = value;
/* 1228 */       this.bitField0_ |= 0x8;
/* 1229 */       onChanged();
/* 1230 */       return this;
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
/*      */     public Builder clearE() {
/* 1242 */       this.bitField0_ &= 0xFFFFFFF7;
/* 1243 */       this.e_ = JwtRsaSsaPssPublicKey.getDefaultInstance().getE();
/* 1244 */       onChanged();
/* 1245 */       return this;
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
/* 1256 */       return ((this.bitField0_ & 0x10) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtRsaSsaPssPublicKey.CustomKid getCustomKid() {
/* 1263 */       if (this.customKidBuilder_ == null) {
/* 1264 */         return (this.customKid_ == null) ? JwtRsaSsaPssPublicKey.CustomKid.getDefaultInstance() : this.customKid_;
/*      */       }
/* 1266 */       return (JwtRsaSsaPssPublicKey.CustomKid)this.customKidBuilder_.getMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCustomKid(JwtRsaSsaPssPublicKey.CustomKid value) {
/* 1273 */       if (this.customKidBuilder_ == null) {
/* 1274 */         if (value == null) {
/* 1275 */           throw new NullPointerException();
/*      */         }
/* 1277 */         this.customKid_ = value;
/*      */       } else {
/* 1279 */         this.customKidBuilder_.setMessage(value);
/*      */       } 
/* 1281 */       this.bitField0_ |= 0x10;
/* 1282 */       onChanged();
/* 1283 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCustomKid(JwtRsaSsaPssPublicKey.CustomKid.Builder builderForValue) {
/* 1290 */       if (this.customKidBuilder_ == null) {
/* 1291 */         this.customKid_ = builderForValue.build();
/*      */       } else {
/* 1293 */         this.customKidBuilder_.setMessage(builderForValue.build());
/*      */       } 
/* 1295 */       this.bitField0_ |= 0x10;
/* 1296 */       onChanged();
/* 1297 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeCustomKid(JwtRsaSsaPssPublicKey.CustomKid value) {
/* 1303 */       if (this.customKidBuilder_ == null) {
/* 1304 */         if ((this.bitField0_ & 0x10) != 0 && this.customKid_ != null && this.customKid_ != 
/*      */           
/* 1306 */           JwtRsaSsaPssPublicKey.CustomKid.getDefaultInstance()) {
/* 1307 */           getCustomKidBuilder().mergeFrom(value);
/*      */         } else {
/* 1309 */           this.customKid_ = value;
/*      */         } 
/*      */       } else {
/* 1312 */         this.customKidBuilder_.mergeFrom(value);
/*      */       } 
/* 1314 */       if (this.customKid_ != null) {
/* 1315 */         this.bitField0_ |= 0x10;
/* 1316 */         onChanged();
/*      */       } 
/* 1318 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearCustomKid() {
/* 1324 */       this.bitField0_ &= 0xFFFFFFEF;
/* 1325 */       this.customKid_ = null;
/* 1326 */       if (this.customKidBuilder_ != null) {
/* 1327 */         this.customKidBuilder_.dispose();
/* 1328 */         this.customKidBuilder_ = null;
/*      */       } 
/* 1330 */       onChanged();
/* 1331 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtRsaSsaPssPublicKey.CustomKid.Builder getCustomKidBuilder() {
/* 1337 */       this.bitField0_ |= 0x10;
/* 1338 */       onChanged();
/* 1339 */       return (JwtRsaSsaPssPublicKey.CustomKid.Builder)internalGetCustomKidFieldBuilder().getBuilder();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtRsaSsaPssPublicKey.CustomKidOrBuilder getCustomKidOrBuilder() {
/* 1345 */       if (this.customKidBuilder_ != null) {
/* 1346 */         return (JwtRsaSsaPssPublicKey.CustomKidOrBuilder)this.customKidBuilder_.getMessageOrBuilder();
/*      */       }
/* 1348 */       return (this.customKid_ == null) ? 
/* 1349 */         JwtRsaSsaPssPublicKey.CustomKid.getDefaultInstance() : this.customKid_;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private SingleFieldBuilder<JwtRsaSsaPssPublicKey.CustomKid, JwtRsaSsaPssPublicKey.CustomKid.Builder, JwtRsaSsaPssPublicKey.CustomKidOrBuilder> internalGetCustomKidFieldBuilder() {
/* 1358 */       if (this.customKidBuilder_ == null) {
/* 1359 */         this
/*      */ 
/*      */ 
/*      */           
/* 1363 */           .customKidBuilder_ = new SingleFieldBuilder(getCustomKid(), getParentForChildren(), isClean());
/* 1364 */         this.customKid_ = null;
/*      */       } 
/* 1366 */       return this.customKidBuilder_;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1375 */   private static final JwtRsaSsaPssPublicKey DEFAULT_INSTANCE = new JwtRsaSsaPssPublicKey();
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPublicKey getDefaultInstance() {
/* 1379 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1383 */   private static final Parser<JwtRsaSsaPssPublicKey> PARSER = (Parser<JwtRsaSsaPssPublicKey>)new AbstractParser<JwtRsaSsaPssPublicKey>()
/*      */     {
/*      */ 
/*      */       
/*      */       public JwtRsaSsaPssPublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1389 */         JwtRsaSsaPssPublicKey.Builder builder = JwtRsaSsaPssPublicKey.newBuilder();
/*      */         try {
/* 1391 */           builder.mergeFrom(input, extensionRegistry);
/* 1392 */         } catch (InvalidProtocolBufferException e) {
/* 1393 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1394 */         } catch (UninitializedMessageException e) {
/* 1395 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1396 */         } catch (IOException e) {
/* 1397 */           throw (new InvalidProtocolBufferException(e))
/* 1398 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1400 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<JwtRsaSsaPssPublicKey> parser() {
/* 1405 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<JwtRsaSsaPssPublicKey> getParserForType() {
/* 1410 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public JwtRsaSsaPssPublicKey getDefaultInstanceForType() {
/* 1415 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */   
/*      */   public static interface CustomKidOrBuilder extends MessageOrBuilder {
/*      */     String getValue();
/*      */     
/*      */     ByteString getValueBytes();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPssPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */