/*      */ package com.google.crypto.tink.proto;
/*      */ import com.google.protobuf.ExtensionRegistryLite;
/*      */ import com.google.protobuf.InvalidProtocolBufferException;
/*      */ import com.google.protobuf.Message;
/*      */ 
/*      */ public final class JwtEcdsaPublicKey extends GeneratedMessage implements JwtEcdsaPublicKeyOrBuilder {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private int bitField0_;
/*      */   public static final int VERSION_FIELD_NUMBER = 1;
/*      */   private int version_;
/*      */   public static final int ALGORITHM_FIELD_NUMBER = 2;
/*      */   private int algorithm_;
/*      */   public static final int X_FIELD_NUMBER = 3;
/*      */   private ByteString x_;
/*      */   public static final int Y_FIELD_NUMBER = 4;
/*      */   private ByteString y_;
/*      */   public static final int CUSTOM_KID_FIELD_NUMBER = 5;
/*      */   private CustomKid customKid_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtEcdsaPublicKey.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   28 */         .getName());
/*      */   }
/*      */   
/*      */   private JwtEcdsaPublicKey(GeneratedMessage.Builder<?> builder) {
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
/*  604 */     this.x_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  620 */     this.y_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  656 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtEcdsaPublicKey.class, Builder.class); } public static final class CustomKid extends GeneratedMessage implements CustomKidOrBuilder { private static final long serialVersionUID = 0L; public static final int VALUE_FIELD_NUMBER = 1; private volatile Object value_; private byte memoizedIsInitialized; static { RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", CustomKid.class.getName()); } private CustomKid(GeneratedMessage.Builder<?> builder) { super(builder); this.value_ = ""; this.memoizedIsInitialized = -1; } private CustomKid() { this.value_ = ""; this.memoizedIsInitialized = -1; this.value_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_fieldAccessorTable.ensureFieldAccessorsInitialized(CustomKid.class, Builder.class); } public String getValue() { Object ref = this.value_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.value_ = s; return s; } public ByteString getValueBytes() { Object ref = this.value_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.value_ = b; return b; }  return (ByteString)ref; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized; if (isInitialized == 1) return true;  if (isInitialized == 0) return false;  this.memoizedIsInitialized = 1; return true; } public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.value_)) GeneratedMessage.writeString(output, 1, this.value_);  getUnknownFields().writeTo(output); } public int getSerializedSize() { int size = this.memoizedSize; if (size != -1) return size;  size = 0; if (!GeneratedMessage.isStringEmpty(this.value_)) size += GeneratedMessage.computeStringSize(1, this.value_);  size += getUnknownFields().getSerializedSize(); this.memoizedSize = size; return size; } public boolean equals(Object obj) { if (obj == this) return true;  if (!(obj instanceof CustomKid)) return super.equals(obj);  CustomKid other = (CustomKid)obj; if (!getValue().equals(other.getValue())) return false;  if (!getUnknownFields().equals(other.getUnknownFields())) return false;  return true; } public int hashCode() { if (this.memoizedHashCode != 0) return this.memoizedHashCode;  int hash = 41; hash = 19 * hash + getDescriptor().hashCode(); hash = 37 * hash + 1; hash = 53 * hash + getValue().hashCode(); hash = 29 * hash + getUnknownFields().hashCode(); this.memoizedHashCode = hash; return hash; } public static CustomKid parseFrom(ByteBuffer data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(ByteString data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(byte[] data) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data); } public static CustomKid parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { return (CustomKid)PARSER.parseFrom(data, extensionRegistry); } public static CustomKid parseFrom(InputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input); } public static CustomKid parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public static CustomKid parseDelimitedFrom(InputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseDelimitedWithIOException(PARSER, input); } public static CustomKid parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry); } public static CustomKid parseFrom(CodedInputStream input) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input); } public static CustomKid parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { return (CustomKid)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry); } public Builder newBuilderForType() { return newBuilder(); } public static Builder newBuilder() { return DEFAULT_INSTANCE.toBuilder(); } public static Builder newBuilder(CustomKid prototype) { return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype); } public Builder toBuilder() { return (this == DEFAULT_INSTANCE) ? new Builder() : (new Builder()).mergeFrom(this); } protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) { Builder builder = new Builder(parent); return builder; } public static final class Builder extends GeneratedMessage.Builder<Builder> implements JwtEcdsaPublicKey.CustomKidOrBuilder { private int bitField0_; private Object value_; public static final Descriptors.Descriptor getDescriptor() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtEcdsaPublicKey.CustomKid.class, Builder.class); } private Builder() { this.value_ = ""; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.value_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.value_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_CustomKid_descriptor; } public JwtEcdsaPublicKey.CustomKid getDefaultInstanceForType() { return JwtEcdsaPublicKey.CustomKid.getDefaultInstance(); } public JwtEcdsaPublicKey.CustomKid build() { JwtEcdsaPublicKey.CustomKid result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtEcdsaPublicKey.CustomKid buildPartial() { JwtEcdsaPublicKey.CustomKid result = new JwtEcdsaPublicKey.CustomKid(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JwtEcdsaPublicKey.CustomKid result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.value_ = this.value_;  } public Builder mergeFrom(Message other) { if (other instanceof JwtEcdsaPublicKey.CustomKid) return mergeFrom((JwtEcdsaPublicKey.CustomKid)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JwtEcdsaPublicKey.CustomKid other) { if (other == JwtEcdsaPublicKey.CustomKid.getDefaultInstance()) return this;  if (!other.getValue().isEmpty()) { this.value_ = other.value_; this.bitField0_ |= 0x1; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.value_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getValue() { Object ref = this.value_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.value_ = s; return s; }  return (String)ref; } public ByteString getValueBytes() { Object ref = this.value_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.value_ = b; return b; }  return (ByteString)ref; } public Builder setValue(String value) { if (value == null) throw new NullPointerException();  this.value_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearValue() { this.value_ = JwtEcdsaPublicKey.CustomKid.getDefaultInstance().getValue(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; } public Builder setValueBytes(ByteString value) { if (value == null) throw new NullPointerException();  JwtEcdsaPublicKey.CustomKid.checkByteStringIsUtf8(value); this.value_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } } private static final CustomKid DEFAULT_INSTANCE = new CustomKid(); public static CustomKid getDefaultInstance() { return DEFAULT_INSTANCE; } private static final Parser<CustomKid> PARSER = (Parser<CustomKid>)new AbstractParser<CustomKid>() { public JwtEcdsaPublicKey.CustomKid parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { JwtEcdsaPublicKey.CustomKid.Builder builder = JwtEcdsaPublicKey.CustomKid.newBuilder(); try { builder.mergeFrom(input, extensionRegistry); } catch (InvalidProtocolBufferException e) { throw e.setUnfinishedMessage(builder.buildPartial()); } catch (UninitializedMessageException e) { throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial()); } catch (IOException e) { throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial()); }  return builder.buildPartial(); } }; public static Parser<CustomKid> parser() { return PARSER; } public Parser<CustomKid> getParserForType() { return PARSER; } public CustomKid getDefaultInstanceForType() { return DEFAULT_INSTANCE; } } private JwtEcdsaPublicKey() { this.version_ = 0; this.algorithm_ = 0; this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.algorithm_ = 0; this.x_ = ByteString.EMPTY;
/*      */     this.y_ = ByteString.EMPTY; }
/*      */   public int getVersion() { return this.version_; }
/*  659 */   public int getAlgorithmValue() { return this.algorithm_; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  660 */     if (isInitialized == 1) return true; 
/*  661 */     if (isInitialized == 0) return false;
/*      */     
/*  663 */     this.memoizedIsInitialized = 1;
/*  664 */     return true; } public JwtEcdsaAlgorithm getAlgorithm() { JwtEcdsaAlgorithm result = JwtEcdsaAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtEcdsaAlgorithm.UNRECOGNIZED : result; }
/*      */   public ByteString getX() { return this.x_; }
/*      */   public ByteString getY() { return this.y_; }
/*      */   public boolean hasCustomKid() { return ((this.bitField0_ & 0x1) != 0); }
/*      */   public CustomKid getCustomKid() { return (this.customKid_ == null) ? CustomKid.getDefaultInstance() : this.customKid_; }
/*      */   public CustomKidOrBuilder getCustomKidOrBuilder() { return (this.customKid_ == null) ? CustomKid.getDefaultInstance() : this.customKid_; }
/*  670 */   public void writeTo(CodedOutputStream output) throws IOException { if (this.version_ != 0) {
/*  671 */       output.writeUInt32(1, this.version_);
/*      */     }
/*  673 */     if (this.algorithm_ != JwtEcdsaAlgorithm.ES_UNKNOWN.getNumber()) {
/*  674 */       output.writeEnum(2, this.algorithm_);
/*      */     }
/*  676 */     if (!this.x_.isEmpty()) {
/*  677 */       output.writeBytes(3, this.x_);
/*      */     }
/*  679 */     if (!this.y_.isEmpty()) {
/*  680 */       output.writeBytes(4, this.y_);
/*      */     }
/*  682 */     if ((this.bitField0_ & 0x1) != 0) {
/*  683 */       output.writeMessage(5, (MessageLite)getCustomKid());
/*      */     }
/*  685 */     getUnknownFields().writeTo(output); }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  690 */     int size = this.memoizedSize;
/*  691 */     if (size != -1) return size;
/*      */     
/*  693 */     size = 0;
/*  694 */     if (this.version_ != 0) {
/*  695 */       size += 
/*  696 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*      */     }
/*  698 */     if (this.algorithm_ != JwtEcdsaAlgorithm.ES_UNKNOWN.getNumber()) {
/*  699 */       size += 
/*  700 */         CodedOutputStream.computeEnumSize(2, this.algorithm_);
/*      */     }
/*  702 */     if (!this.x_.isEmpty()) {
/*  703 */       size += 
/*  704 */         CodedOutputStream.computeBytesSize(3, this.x_);
/*      */     }
/*  706 */     if (!this.y_.isEmpty()) {
/*  707 */       size += 
/*  708 */         CodedOutputStream.computeBytesSize(4, this.y_);
/*      */     }
/*  710 */     if ((this.bitField0_ & 0x1) != 0) {
/*  711 */       size += 
/*  712 */         CodedOutputStream.computeMessageSize(5, (MessageLite)getCustomKid());
/*      */     }
/*  714 */     size += getUnknownFields().getSerializedSize();
/*  715 */     this.memoizedSize = size;
/*  716 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  721 */     if (obj == this) {
/*  722 */       return true;
/*      */     }
/*  724 */     if (!(obj instanceof JwtEcdsaPublicKey)) {
/*  725 */       return super.equals(obj);
/*      */     }
/*  727 */     JwtEcdsaPublicKey other = (JwtEcdsaPublicKey)obj;
/*      */     
/*  729 */     if (getVersion() != other
/*  730 */       .getVersion()) return false; 
/*  731 */     if (this.algorithm_ != other.algorithm_) return false;
/*      */     
/*  733 */     if (!getX().equals(other.getX())) return false;
/*      */     
/*  735 */     if (!getY().equals(other.getY())) return false; 
/*  736 */     if (hasCustomKid() != other.hasCustomKid()) return false; 
/*  737 */     if (hasCustomKid() && 
/*      */       
/*  739 */       !getCustomKid().equals(other.getCustomKid())) return false;
/*      */     
/*  741 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  742 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  747 */     if (this.memoizedHashCode != 0) {
/*  748 */       return this.memoizedHashCode;
/*      */     }
/*  750 */     int hash = 41;
/*  751 */     hash = 19 * hash + getDescriptor().hashCode();
/*  752 */     hash = 37 * hash + 1;
/*  753 */     hash = 53 * hash + getVersion();
/*  754 */     hash = 37 * hash + 2;
/*  755 */     hash = 53 * hash + this.algorithm_;
/*  756 */     hash = 37 * hash + 3;
/*  757 */     hash = 53 * hash + getX().hashCode();
/*  758 */     hash = 37 * hash + 4;
/*  759 */     hash = 53 * hash + getY().hashCode();
/*  760 */     if (hasCustomKid()) {
/*  761 */       hash = 37 * hash + 5;
/*  762 */       hash = 53 * hash + getCustomKid().hashCode();
/*      */     } 
/*  764 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  765 */     this.memoizedHashCode = hash;
/*  766 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  772 */     return (JwtEcdsaPublicKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  778 */     return (JwtEcdsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  783 */     return (JwtEcdsaPublicKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  789 */     return (JwtEcdsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  793 */     return (JwtEcdsaPublicKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  799 */     return (JwtEcdsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(InputStream input) throws IOException {
/*  803 */     return 
/*  804 */       (JwtEcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  810 */     return 
/*  811 */       (JwtEcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseDelimitedFrom(InputStream input) throws IOException {
/*  816 */     return 
/*  817 */       (JwtEcdsaPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  824 */     return 
/*  825 */       (JwtEcdsaPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(CodedInputStream input) throws IOException {
/*  830 */     return 
/*  831 */       (JwtEcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  837 */     return 
/*  838 */       (JwtEcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  842 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  844 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(JwtEcdsaPublicKey prototype) {
/*  847 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  851 */     return (this == DEFAULT_INSTANCE) ? 
/*  852 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  858 */     Builder builder = new Builder(parent);
/*  859 */     return builder;
/*      */   }
/*      */   
/*      */   public static final class Builder
/*      */     extends GeneratedMessage.Builder<Builder>
/*      */     implements JwtEcdsaPublicKeyOrBuilder {
/*      */     private int bitField0_;
/*      */     private int version_;
/*      */     private int algorithm_;
/*      */     private ByteString x_;
/*      */     private ByteString y_;
/*      */     private JwtEcdsaPublicKey.CustomKid customKid_;
/*      */     private SingleFieldBuilder<JwtEcdsaPublicKey.CustomKid, JwtEcdsaPublicKey.CustomKid.Builder, JwtEcdsaPublicKey.CustomKidOrBuilder> customKidBuilder_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  874 */       return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  880 */       return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_fieldAccessorTable
/*  881 */         .ensureFieldAccessorsInitialized(JwtEcdsaPublicKey.class, Builder.class);
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
/* 1098 */       this.algorithm_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1149 */       this.x_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1196 */       this.y_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.algorithm_ = 0; this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (JwtEcdsaPublicKey.alwaysUseFieldBuilders) internalGetCustomKidFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.algorithm_ = 0; this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; this.customKid_ = null; if (this.customKidBuilder_ != null) { this.customKidBuilder_.dispose(); this.customKidBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPublicKey_descriptor; } public JwtEcdsaPublicKey getDefaultInstanceForType() { return JwtEcdsaPublicKey.getDefaultInstance(); } public JwtEcdsaPublicKey build() { JwtEcdsaPublicKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtEcdsaPublicKey buildPartial() { JwtEcdsaPublicKey result = new JwtEcdsaPublicKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JwtEcdsaPublicKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0) result.algorithm_ = this.algorithm_;  if ((from_bitField0_ & 0x4) != 0) result.x_ = this.x_;  if ((from_bitField0_ & 0x8) != 0) result.y_ = this.y_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x10) != 0) { result.customKid_ = (this.customKidBuilder_ == null) ? this.customKid_ : (JwtEcdsaPublicKey.CustomKid)this.customKidBuilder_.build(); to_bitField0_ |= 0x1; }  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof JwtEcdsaPublicKey) return mergeFrom((JwtEcdsaPublicKey)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JwtEcdsaPublicKey other) { if (other == JwtEcdsaPublicKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.algorithm_ != 0) setAlgorithmValue(other.getAlgorithmValue());  if (!other.getX().isEmpty()) setX(other.getX());  if (!other.getY().isEmpty()) setY(other.getY());  if (other.hasCustomKid()) mergeCustomKid(other.getCustomKid());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 16: this.algorithm_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 26: this.x_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.y_ = input.readBytes(); this.bitField0_ |= 0x8; continue;case 42: input.readMessage((MessageLite.Builder)internalGetCustomKidFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x10; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public int getVersion() { return this.version_; } public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; } public int getAlgorithmValue() { return this.algorithm_; } public Builder setAlgorithmValue(int value) { this.algorithm_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public JwtEcdsaAlgorithm getAlgorithm() { JwtEcdsaAlgorithm result = JwtEcdsaAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtEcdsaAlgorithm.UNRECOGNIZED : result; }
/*      */     public Builder setAlgorithm(JwtEcdsaAlgorithm value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.algorithm_ = value.getNumber(); onChanged(); return this; }
/*      */     public Builder clearAlgorithm() { this.bitField0_ &= 0xFFFFFFFD; this.algorithm_ = 0; onChanged(); return this; }
/*      */     public ByteString getX() { return this.x_; }
/*      */     public Builder setX(ByteString value) { if (value == null) throw new NullPointerException();  this.x_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*      */     public Builder clearX() { this.bitField0_ &= 0xFFFFFFFB; this.x_ = JwtEcdsaPublicKey.getDefaultInstance().getX(); onChanged(); return this; }
/* 1203 */     public ByteString getY() { return this.y_; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setY(ByteString value) {
/* 1211 */       if (value == null) throw new NullPointerException(); 
/* 1212 */       this.y_ = value;
/* 1213 */       this.bitField0_ |= 0x8;
/* 1214 */       onChanged();
/* 1215 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearY() {
/* 1222 */       this.bitField0_ &= 0xFFFFFFF7;
/* 1223 */       this.y_ = JwtEcdsaPublicKey.getDefaultInstance().getY();
/* 1224 */       onChanged();
/* 1225 */       return this;
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
/* 1236 */       return ((this.bitField0_ & 0x10) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtEcdsaPublicKey.CustomKid getCustomKid() {
/* 1243 */       if (this.customKidBuilder_ == null) {
/* 1244 */         return (this.customKid_ == null) ? JwtEcdsaPublicKey.CustomKid.getDefaultInstance() : this.customKid_;
/*      */       }
/* 1246 */       return (JwtEcdsaPublicKey.CustomKid)this.customKidBuilder_.getMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCustomKid(JwtEcdsaPublicKey.CustomKid value) {
/* 1253 */       if (this.customKidBuilder_ == null) {
/* 1254 */         if (value == null) {
/* 1255 */           throw new NullPointerException();
/*      */         }
/* 1257 */         this.customKid_ = value;
/*      */       } else {
/* 1259 */         this.customKidBuilder_.setMessage(value);
/*      */       } 
/* 1261 */       this.bitField0_ |= 0x10;
/* 1262 */       onChanged();
/* 1263 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCustomKid(JwtEcdsaPublicKey.CustomKid.Builder builderForValue) {
/* 1270 */       if (this.customKidBuilder_ == null) {
/* 1271 */         this.customKid_ = builderForValue.build();
/*      */       } else {
/* 1273 */         this.customKidBuilder_.setMessage(builderForValue.build());
/*      */       } 
/* 1275 */       this.bitField0_ |= 0x10;
/* 1276 */       onChanged();
/* 1277 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeCustomKid(JwtEcdsaPublicKey.CustomKid value) {
/* 1283 */       if (this.customKidBuilder_ == null) {
/* 1284 */         if ((this.bitField0_ & 0x10) != 0 && this.customKid_ != null && this.customKid_ != 
/*      */           
/* 1286 */           JwtEcdsaPublicKey.CustomKid.getDefaultInstance()) {
/* 1287 */           getCustomKidBuilder().mergeFrom(value);
/*      */         } else {
/* 1289 */           this.customKid_ = value;
/*      */         } 
/*      */       } else {
/* 1292 */         this.customKidBuilder_.mergeFrom(value);
/*      */       } 
/* 1294 */       if (this.customKid_ != null) {
/* 1295 */         this.bitField0_ |= 0x10;
/* 1296 */         onChanged();
/*      */       } 
/* 1298 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearCustomKid() {
/* 1304 */       this.bitField0_ &= 0xFFFFFFEF;
/* 1305 */       this.customKid_ = null;
/* 1306 */       if (this.customKidBuilder_ != null) {
/* 1307 */         this.customKidBuilder_.dispose();
/* 1308 */         this.customKidBuilder_ = null;
/*      */       } 
/* 1310 */       onChanged();
/* 1311 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtEcdsaPublicKey.CustomKid.Builder getCustomKidBuilder() {
/* 1317 */       this.bitField0_ |= 0x10;
/* 1318 */       onChanged();
/* 1319 */       return (JwtEcdsaPublicKey.CustomKid.Builder)internalGetCustomKidFieldBuilder().getBuilder();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JwtEcdsaPublicKey.CustomKidOrBuilder getCustomKidOrBuilder() {
/* 1325 */       if (this.customKidBuilder_ != null) {
/* 1326 */         return (JwtEcdsaPublicKey.CustomKidOrBuilder)this.customKidBuilder_.getMessageOrBuilder();
/*      */       }
/* 1328 */       return (this.customKid_ == null) ? 
/* 1329 */         JwtEcdsaPublicKey.CustomKid.getDefaultInstance() : this.customKid_;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private SingleFieldBuilder<JwtEcdsaPublicKey.CustomKid, JwtEcdsaPublicKey.CustomKid.Builder, JwtEcdsaPublicKey.CustomKidOrBuilder> internalGetCustomKidFieldBuilder() {
/* 1338 */       if (this.customKidBuilder_ == null) {
/* 1339 */         this
/*      */ 
/*      */ 
/*      */           
/* 1343 */           .customKidBuilder_ = new SingleFieldBuilder(getCustomKid(), getParentForChildren(), isClean());
/* 1344 */         this.customKid_ = null;
/*      */       } 
/* 1346 */       return this.customKidBuilder_;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1355 */   private static final JwtEcdsaPublicKey DEFAULT_INSTANCE = new JwtEcdsaPublicKey();
/*      */ 
/*      */   
/*      */   public static JwtEcdsaPublicKey getDefaultInstance() {
/* 1359 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1363 */   private static final Parser<JwtEcdsaPublicKey> PARSER = (Parser<JwtEcdsaPublicKey>)new AbstractParser<JwtEcdsaPublicKey>()
/*      */     {
/*      */ 
/*      */       
/*      */       public JwtEcdsaPublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1369 */         JwtEcdsaPublicKey.Builder builder = JwtEcdsaPublicKey.newBuilder();
/*      */         try {
/* 1371 */           builder.mergeFrom(input, extensionRegistry);
/* 1372 */         } catch (InvalidProtocolBufferException e) {
/* 1373 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1374 */         } catch (UninitializedMessageException e) {
/* 1375 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1376 */         } catch (IOException e) {
/* 1377 */           throw (new InvalidProtocolBufferException(e))
/* 1378 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1380 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<JwtEcdsaPublicKey> parser() {
/* 1385 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<JwtEcdsaPublicKey> getParserForType() {
/* 1390 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public JwtEcdsaPublicKey getDefaultInstanceForType() {
/* 1395 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */   
/*      */   public static interface CustomKidOrBuilder extends MessageOrBuilder {
/*      */     String getValue();
/*      */     
/*      */     ByteString getValueBytes();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtEcdsaPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */