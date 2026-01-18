/*      */ package com.google.crypto.tink.proto;
/*      */ import com.google.protobuf.ByteString;
/*      */ import com.google.protobuf.ExtensionRegistryLite;
/*      */ import com.google.protobuf.InvalidProtocolBufferException;
/*      */ import com.google.protobuf.Message;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ @Deprecated
/*      */ public final class KeyTypeEntry extends GeneratedMessage implements KeyTypeEntryOrBuilder {
/*      */   private static final long serialVersionUID = 0L;
/*      */   public static final int PRIMITIVE_NAME_FIELD_NUMBER = 1;
/*      */   private volatile Object primitiveName_;
/*      */   public static final int TYPE_URL_FIELD_NUMBER = 2;
/*      */   private volatile Object typeUrl_;
/*      */   public static final int KEY_MANAGER_VERSION_FIELD_NUMBER = 3;
/*      */   private int keyManagerVersion_;
/*      */   public static final int NEW_KEY_ALLOWED_FIELD_NUMBER = 4;
/*      */   private boolean newKeyAllowed_;
/*      */   public static final int CATALOGUE_NAME_FIELD_NUMBER = 5;
/*      */   private volatile Object catalogueName_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   24 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KeyTypeEntry.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   30 */         .getName());
/*      */   }
/*      */   
/*      */   private KeyTypeEntry(GeneratedMessage.Builder<?> builder) {
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
/*   56 */     this.primitiveName_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  103 */     this.typeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  150 */     this.keyManagerVersion_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  165 */     this.newKeyAllowed_ = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  180 */     this.catalogueName_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  226 */     this.memoizedIsInitialized = -1; } private KeyTypeEntry() { this.primitiveName_ = ""; this.typeUrl_ = ""; this.keyManagerVersion_ = 0; this.newKeyAllowed_ = false; this.catalogueName_ = ""; this.memoizedIsInitialized = -1; this.primitiveName_ = ""; this.typeUrl_ = ""; this.catalogueName_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return Config.internal_static_google_crypto_tink_KeyTypeEntry_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Config.internal_static_google_crypto_tink_KeyTypeEntry_fieldAccessorTable.ensureFieldAccessorsInitialized(KeyTypeEntry.class, Builder.class); } public String getPrimitiveName() { Object ref = this.primitiveName_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.primitiveName_ = s; return s; } public ByteString getPrimitiveNameBytes() { Object ref = this.primitiveName_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.primitiveName_ = b; return b; }  return (ByteString)ref; } public String getTypeUrl() { Object ref = this.typeUrl_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; } public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; } public int getKeyManagerVersion() { return this.keyManagerVersion_; } public boolean getNewKeyAllowed() { return this.newKeyAllowed_; }
/*      */   public String getCatalogueName() { Object ref = this.catalogueName_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.catalogueName_ = s; return s; }
/*      */   public ByteString getCatalogueNameBytes() { Object ref = this.catalogueName_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.catalogueName_ = b; return b; }  return (ByteString)ref; }
/*  229 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  230 */     if (isInitialized == 1) return true; 
/*  231 */     if (isInitialized == 0) return false;
/*      */     
/*  233 */     this.memoizedIsInitialized = 1;
/*  234 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  240 */     if (!GeneratedMessage.isStringEmpty(this.primitiveName_)) {
/*  241 */       GeneratedMessage.writeString(output, 1, this.primitiveName_);
/*      */     }
/*  243 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/*  244 */       GeneratedMessage.writeString(output, 2, this.typeUrl_);
/*      */     }
/*  246 */     if (this.keyManagerVersion_ != 0) {
/*  247 */       output.writeUInt32(3, this.keyManagerVersion_);
/*      */     }
/*  249 */     if (this.newKeyAllowed_) {
/*  250 */       output.writeBool(4, this.newKeyAllowed_);
/*      */     }
/*  252 */     if (!GeneratedMessage.isStringEmpty(this.catalogueName_)) {
/*  253 */       GeneratedMessage.writeString(output, 5, this.catalogueName_);
/*      */     }
/*  255 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  260 */     int size = this.memoizedSize;
/*  261 */     if (size != -1) return size;
/*      */     
/*  263 */     size = 0;
/*  264 */     if (!GeneratedMessage.isStringEmpty(this.primitiveName_)) {
/*  265 */       size += GeneratedMessage.computeStringSize(1, this.primitiveName_);
/*      */     }
/*  267 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/*  268 */       size += GeneratedMessage.computeStringSize(2, this.typeUrl_);
/*      */     }
/*  270 */     if (this.keyManagerVersion_ != 0) {
/*  271 */       size += 
/*  272 */         CodedOutputStream.computeUInt32Size(3, this.keyManagerVersion_);
/*      */     }
/*  274 */     if (this.newKeyAllowed_) {
/*  275 */       size += 
/*  276 */         CodedOutputStream.computeBoolSize(4, this.newKeyAllowed_);
/*      */     }
/*  278 */     if (!GeneratedMessage.isStringEmpty(this.catalogueName_)) {
/*  279 */       size += GeneratedMessage.computeStringSize(5, this.catalogueName_);
/*      */     }
/*  281 */     size += getUnknownFields().getSerializedSize();
/*  282 */     this.memoizedSize = size;
/*  283 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  288 */     if (obj == this) {
/*  289 */       return true;
/*      */     }
/*  291 */     if (!(obj instanceof KeyTypeEntry)) {
/*  292 */       return super.equals(obj);
/*      */     }
/*  294 */     KeyTypeEntry other = (KeyTypeEntry)obj;
/*      */ 
/*      */     
/*  297 */     if (!getPrimitiveName().equals(other.getPrimitiveName())) return false;
/*      */     
/*  299 */     if (!getTypeUrl().equals(other.getTypeUrl())) return false; 
/*  300 */     if (getKeyManagerVersion() != other
/*  301 */       .getKeyManagerVersion()) return false; 
/*  302 */     if (getNewKeyAllowed() != other
/*  303 */       .getNewKeyAllowed()) return false;
/*      */     
/*  305 */     if (!getCatalogueName().equals(other.getCatalogueName())) return false; 
/*  306 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  307 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  312 */     if (this.memoizedHashCode != 0) {
/*  313 */       return this.memoizedHashCode;
/*      */     }
/*  315 */     int hash = 41;
/*  316 */     hash = 19 * hash + getDescriptor().hashCode();
/*  317 */     hash = 37 * hash + 1;
/*  318 */     hash = 53 * hash + getPrimitiveName().hashCode();
/*  319 */     hash = 37 * hash + 2;
/*  320 */     hash = 53 * hash + getTypeUrl().hashCode();
/*  321 */     hash = 37 * hash + 3;
/*  322 */     hash = 53 * hash + getKeyManagerVersion();
/*  323 */     hash = 37 * hash + 4;
/*  324 */     hash = 53 * hash + Internal.hashBoolean(
/*  325 */         getNewKeyAllowed());
/*  326 */     hash = 37 * hash + 5;
/*  327 */     hash = 53 * hash + getCatalogueName().hashCode();
/*  328 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  329 */     this.memoizedHashCode = hash;
/*  330 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  336 */     return (KeyTypeEntry)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  342 */     return (KeyTypeEntry)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  347 */     return (KeyTypeEntry)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  353 */     return (KeyTypeEntry)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static KeyTypeEntry parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  357 */     return (KeyTypeEntry)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  363 */     return (KeyTypeEntry)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static KeyTypeEntry parseFrom(InputStream input) throws IOException {
/*  367 */     return 
/*  368 */       (KeyTypeEntry)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  374 */     return 
/*  375 */       (KeyTypeEntry)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseDelimitedFrom(InputStream input) throws IOException {
/*  380 */     return 
/*  381 */       (KeyTypeEntry)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  388 */     return 
/*  389 */       (KeyTypeEntry)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(CodedInputStream input) throws IOException {
/*  394 */     return 
/*  395 */       (KeyTypeEntry)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  401 */     return 
/*  402 */       (KeyTypeEntry)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  406 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  408 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(KeyTypeEntry prototype) {
/*  411 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  415 */     return (this == DEFAULT_INSTANCE) ? 
/*  416 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  422 */     Builder builder = new Builder(parent);
/*  423 */     return builder;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class Builder
/*      */     extends GeneratedMessage.Builder<Builder>
/*      */     implements KeyTypeEntryOrBuilder
/*      */   {
/*      */     private int bitField0_;
/*      */     
/*      */     private Object primitiveName_;
/*      */     private Object typeUrl_;
/*      */     private int keyManagerVersion_;
/*      */     private boolean newKeyAllowed_;
/*      */     private Object catalogueName_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  440 */       return Config.internal_static_google_crypto_tink_KeyTypeEntry_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  446 */       return Config.internal_static_google_crypto_tink_KeyTypeEntry_fieldAccessorTable
/*  447 */         .ensureFieldAccessorsInitialized(KeyTypeEntry.class, Builder.class);
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
/*      */     private Builder()
/*      */     {
/*  621 */       this.primitiveName_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  713 */       this.typeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  893 */       this.catalogueName_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.primitiveName_ = ""; this.typeUrl_ = ""; this.keyManagerVersion_ = 0; this.newKeyAllowed_ = false; this.catalogueName_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return Config.internal_static_google_crypto_tink_KeyTypeEntry_descriptor; } public KeyTypeEntry getDefaultInstanceForType() { return KeyTypeEntry.getDefaultInstance(); } public KeyTypeEntry build() { KeyTypeEntry result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public KeyTypeEntry buildPartial() { KeyTypeEntry result = new KeyTypeEntry(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(KeyTypeEntry result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.primitiveName_ = this.primitiveName_;  if ((from_bitField0_ & 0x2) != 0) result.typeUrl_ = this.typeUrl_;  if ((from_bitField0_ & 0x4) != 0) result.keyManagerVersion_ = this.keyManagerVersion_;  if ((from_bitField0_ & 0x8) != 0) result.newKeyAllowed_ = this.newKeyAllowed_;  if ((from_bitField0_ & 0x10) != 0) result.catalogueName_ = this.catalogueName_;  } public Builder mergeFrom(Message other) { if (other instanceof KeyTypeEntry) return mergeFrom((KeyTypeEntry)other);  super.mergeFrom(other); return this; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.primitiveName_ = ""; this.typeUrl_ = ""; this.catalogueName_ = ""; }
/*      */     public Builder mergeFrom(KeyTypeEntry other) { if (other == KeyTypeEntry.getDefaultInstance()) return this;  if (!other.getPrimitiveName().isEmpty()) { this.primitiveName_ = other.primitiveName_; this.bitField0_ |= 0x1; onChanged(); }  if (!other.getTypeUrl().isEmpty()) { this.typeUrl_ = other.typeUrl_; this.bitField0_ |= 0x2; onChanged(); }  if (other.getKeyManagerVersion() != 0) setKeyManagerVersion(other.getKeyManagerVersion());  if (other.getNewKeyAllowed())
/*      */         setNewKeyAllowed(other.getNewKeyAllowed());  if (!other.getCatalogueName().isEmpty()) { this.catalogueName_ = other.catalogueName_; this.bitField0_ |= 0x10; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*      */     public final boolean isInitialized() { return true; }
/*      */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*      */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.primitiveName_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: this.typeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x2; continue;case 24: this.keyManagerVersion_ = input.readUInt32(); this.bitField0_ |= 0x4; continue;case 32: this.newKeyAllowed_ = input.readBool(); this.bitField0_ |= 0x8; continue;case 42: this.catalogueName_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x10; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*      */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*      */     public String getPrimitiveName() { Object ref = this.primitiveName_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.primitiveName_ = s; return s; }  return (String)ref; }
/*      */     public ByteString getPrimitiveNameBytes() { Object ref = this.primitiveName_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.primitiveName_ = b; return b; }  return (ByteString)ref; }
/*      */     public Builder setPrimitiveName(String value) { if (value == null)
/*  903 */         throw new NullPointerException();  this.primitiveName_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public String getCatalogueName() { Object ref = this.catalogueName_;
/*  904 */       if (!(ref instanceof String)) {
/*  905 */         ByteString bs = (ByteString)ref;
/*      */         
/*  907 */         String s = bs.toStringUtf8();
/*  908 */         this.catalogueName_ = s;
/*  909 */         return s;
/*      */       } 
/*  911 */       return (String)ref; } public Builder clearPrimitiveName() { this.primitiveName_ = KeyTypeEntry.getDefaultInstance().getPrimitiveName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*      */     public Builder setPrimitiveNameBytes(ByteString value) { if (value == null) throw new NullPointerException();  KeyTypeEntry.checkByteStringIsUtf8(value); this.primitiveName_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public String getTypeUrl() { Object ref = this.typeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; }  return (String)ref; }
/*      */     public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; }
/*      */     public Builder setTypeUrl(String value) { if (value == null) throw new NullPointerException();  this.typeUrl_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public Builder clearTypeUrl() { this.typeUrl_ = KeyTypeEntry.getDefaultInstance().getTypeUrl(); this.bitField0_ &= 0xFFFFFFFD; onChanged(); return this; }
/*      */     public Builder setTypeUrlBytes(ByteString value) { if (value == null) throw new NullPointerException();  KeyTypeEntry.checkByteStringIsUtf8(value); this.typeUrl_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public int getKeyManagerVersion() { return this.keyManagerVersion_; }
/*      */     public Builder setKeyManagerVersion(int value) { this.keyManagerVersion_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*      */     public Builder clearKeyManagerVersion() { this.bitField0_ &= 0xFFFFFFFB; this.keyManagerVersion_ = 0; onChanged(); return this; }
/*      */     public boolean getNewKeyAllowed() { return this.newKeyAllowed_; }
/*      */     public Builder setNewKeyAllowed(boolean value) { this.newKeyAllowed_ = value; this.bitField0_ |= 0x8; onChanged(); return this; }
/*      */     public Builder clearNewKeyAllowed() { this.bitField0_ &= 0xFFFFFFF7; this.newKeyAllowed_ = false; onChanged(); return this; }
/*  924 */     public ByteString getCatalogueNameBytes() { Object ref = this.catalogueName_;
/*  925 */       if (ref instanceof String) {
/*      */         
/*  927 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*      */         
/*  929 */         this.catalogueName_ = b;
/*  930 */         return b;
/*      */       } 
/*  932 */       return (ByteString)ref; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCatalogueName(String value) {
/*  946 */       if (value == null) throw new NullPointerException(); 
/*  947 */       this.catalogueName_ = value;
/*  948 */       this.bitField0_ |= 0x10;
/*  949 */       onChanged();
/*  950 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearCatalogueName() {
/*  961 */       this.catalogueName_ = KeyTypeEntry.getDefaultInstance().getCatalogueName();
/*  962 */       this.bitField0_ &= 0xFFFFFFEF;
/*  963 */       onChanged();
/*  964 */       return this;
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
/*      */     public Builder setCatalogueNameBytes(ByteString value) {
/*  977 */       if (value == null) throw new NullPointerException(); 
/*  978 */       KeyTypeEntry.checkByteStringIsUtf8(value);
/*  979 */       this.catalogueName_ = value;
/*  980 */       this.bitField0_ |= 0x10;
/*  981 */       onChanged();
/*  982 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  991 */   private static final KeyTypeEntry DEFAULT_INSTANCE = new KeyTypeEntry();
/*      */ 
/*      */   
/*      */   public static KeyTypeEntry getDefaultInstance() {
/*  995 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/*  999 */   private static final Parser<KeyTypeEntry> PARSER = (Parser<KeyTypeEntry>)new AbstractParser<KeyTypeEntry>()
/*      */     {
/*      */ 
/*      */       
/*      */       public KeyTypeEntry parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1005 */         KeyTypeEntry.Builder builder = KeyTypeEntry.newBuilder();
/*      */         try {
/* 1007 */           builder.mergeFrom(input, extensionRegistry);
/* 1008 */         } catch (InvalidProtocolBufferException e) {
/* 1009 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1010 */         } catch (UninitializedMessageException e) {
/* 1011 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1012 */         } catch (IOException e) {
/* 1013 */           throw (new InvalidProtocolBufferException(e))
/* 1014 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1016 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<KeyTypeEntry> parser() {
/* 1021 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<KeyTypeEntry> getParserForType() {
/* 1026 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public KeyTypeEntry getDefaultInstanceForType() {
/* 1031 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeyTypeEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */