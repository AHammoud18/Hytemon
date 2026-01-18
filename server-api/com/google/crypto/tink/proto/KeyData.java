/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class KeyData extends GeneratedMessage implements KeyDataOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int TYPE_URL_FIELD_NUMBER = 1;
/*     */   private volatile Object typeUrl_;
/*     */   public static final int VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString value_;
/*     */   public static final int KEY_MATERIAL_TYPE_FIELD_NUMBER = 3;
/*     */   private int keyMaterialType_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  25 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KeyData.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  31 */         .getName());
/*     */   }
/*     */   
/*     */   private KeyData(GeneratedMessage.Builder<?> builder) {
/*  35 */     super(builder);
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
/* 209 */     this.typeUrl_ = "";
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
/* 256 */     this.value_ = ByteString.EMPTY;
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
/* 272 */     this.keyMaterialType_ = 0;
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
/* 297 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_KeyData_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_KeyData_fieldAccessorTable.ensureFieldAccessorsInitialized(KeyData.class, Builder.class); } private KeyData() { this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.keyMaterialType_ = 0; this.memoizedIsInitialized = -1; this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.keyMaterialType_ = 0; } public enum KeyMaterialType implements ProtocolMessageEnum {
/*     */     UNKNOWN_KEYMATERIAL(0), SYMMETRIC(1), ASYMMETRIC_PRIVATE(2), ASYMMETRIC_PUBLIC(3), REMOTE(4), UNRECOGNIZED(-1); public static final int UNKNOWN_KEYMATERIAL_VALUE = 0; public static final int SYMMETRIC_VALUE = 1; public static final int ASYMMETRIC_PRIVATE_VALUE = 2; public static final int ASYMMETRIC_PUBLIC_VALUE = 3; public static final int REMOTE_VALUE = 4; private static final Internal.EnumLiteMap<KeyMaterialType> internalValueMap = new Internal.EnumLiteMap<KeyMaterialType>() { public KeyData.KeyMaterialType findValueByNumber(int number) { return KeyData.KeyMaterialType.forNumber(number); } }
/*     */     ; private static final KeyMaterialType[] VALUES = values(); private final int value; static {  } public final int getNumber() { if (this == UNRECOGNIZED) throw new IllegalArgumentException("Can't get the number of an unknown enum value.");  return this.value; } public static KeyMaterialType forNumber(int value) { switch (value) { case 0: return UNKNOWN_KEYMATERIAL;case 1: return SYMMETRIC;case 2: return ASYMMETRIC_PRIVATE;case 3: return ASYMMETRIC_PUBLIC;case 4: return REMOTE; }  return null; } public static Internal.EnumLiteMap<KeyMaterialType> internalGetValueMap() { return internalValueMap; } public final Descriptors.EnumValueDescriptor getValueDescriptor() { if (this == UNRECOGNIZED) throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");  return getDescriptor().getValues().get(ordinal()); } public final Descriptors.EnumDescriptor getDescriptorForType() { return getDescriptor(); } public static Descriptors.EnumDescriptor getDescriptor() { return KeyData.getDescriptor().getEnumTypes().get(0); } KeyMaterialType(int value) { this.value = value; }
/* 300 */   } public String getTypeUrl() { Object ref = this.typeUrl_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 301 */     if (isInitialized == 1) return true; 
/* 302 */     if (isInitialized == 0) return false;
/*     */     
/* 304 */     this.memoizedIsInitialized = 1;
/* 305 */     return true; } public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) {
/*     */       ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b;
/*     */     }  return (ByteString)ref; }
/*     */   public ByteString getValue() { return this.value_; }
/*     */   public int getKeyMaterialTypeValue() { return this.keyMaterialType_; }
/*     */   public KeyMaterialType getKeyMaterialType() { KeyMaterialType result = KeyMaterialType.forNumber(this.keyMaterialType_); return (result == null) ? KeyMaterialType.UNRECOGNIZED : result; }
/* 311 */   public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/* 312 */       GeneratedMessage.writeString(output, 1, this.typeUrl_);
/*     */     }
/* 314 */     if (!this.value_.isEmpty()) {
/* 315 */       output.writeBytes(2, this.value_);
/*     */     }
/* 317 */     if (this.keyMaterialType_ != KeyMaterialType.UNKNOWN_KEYMATERIAL.getNumber()) {
/* 318 */       output.writeEnum(3, this.keyMaterialType_);
/*     */     }
/* 320 */     getUnknownFields().writeTo(output); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 325 */     int size = this.memoizedSize;
/* 326 */     if (size != -1) return size;
/*     */     
/* 328 */     size = 0;
/* 329 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/* 330 */       size += GeneratedMessage.computeStringSize(1, this.typeUrl_);
/*     */     }
/* 332 */     if (!this.value_.isEmpty()) {
/* 333 */       size += 
/* 334 */         CodedOutputStream.computeBytesSize(2, this.value_);
/*     */     }
/* 336 */     if (this.keyMaterialType_ != KeyMaterialType.UNKNOWN_KEYMATERIAL.getNumber()) {
/* 337 */       size += 
/* 338 */         CodedOutputStream.computeEnumSize(3, this.keyMaterialType_);
/*     */     }
/* 340 */     size += getUnknownFields().getSerializedSize();
/* 341 */     this.memoizedSize = size;
/* 342 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 347 */     if (obj == this) {
/* 348 */       return true;
/*     */     }
/* 350 */     if (!(obj instanceof KeyData)) {
/* 351 */       return super.equals(obj);
/*     */     }
/* 353 */     KeyData other = (KeyData)obj;
/*     */ 
/*     */     
/* 356 */     if (!getTypeUrl().equals(other.getTypeUrl())) return false;
/*     */     
/* 358 */     if (!getValue().equals(other.getValue())) return false; 
/* 359 */     if (this.keyMaterialType_ != other.keyMaterialType_) return false; 
/* 360 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 361 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 366 */     if (this.memoizedHashCode != 0) {
/* 367 */       return this.memoizedHashCode;
/*     */     }
/* 369 */     int hash = 41;
/* 370 */     hash = 19 * hash + getDescriptor().hashCode();
/* 371 */     hash = 37 * hash + 1;
/* 372 */     hash = 53 * hash + getTypeUrl().hashCode();
/* 373 */     hash = 37 * hash + 2;
/* 374 */     hash = 53 * hash + getValue().hashCode();
/* 375 */     hash = 37 * hash + 3;
/* 376 */     hash = 53 * hash + this.keyMaterialType_;
/* 377 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 378 */     this.memoizedHashCode = hash;
/* 379 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 385 */     return (KeyData)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 391 */     return (KeyData)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 396 */     return (KeyData)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 402 */     return (KeyData)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KeyData parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 406 */     return (KeyData)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 412 */     return (KeyData)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KeyData parseFrom(InputStream input) throws IOException {
/* 416 */     return 
/* 417 */       (KeyData)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 423 */     return 
/* 424 */       (KeyData)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyData parseDelimitedFrom(InputStream input) throws IOException {
/* 429 */     return 
/* 430 */       (KeyData)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 437 */     return 
/* 438 */       (KeyData)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(CodedInputStream input) throws IOException {
/* 443 */     return 
/* 444 */       (KeyData)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 450 */     return 
/* 451 */       (KeyData)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 455 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 457 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(KeyData prototype) {
/* 460 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 464 */     return (this == DEFAULT_INSTANCE) ? 
/* 465 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 471 */     Builder builder = new Builder(parent);
/* 472 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements KeyDataOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private Object typeUrl_;
/*     */     
/*     */     private ByteString value_;
/*     */     
/*     */     private int keyMaterialType_;
/*     */ 
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 490 */       return Tink.internal_static_google_crypto_tink_KeyData_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 496 */       return Tink.internal_static_google_crypto_tink_KeyData_fieldAccessorTable
/* 497 */         .ensureFieldAccessorsInitialized(KeyData.class, Builder.class);
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
/*     */ 
/*     */     
/*     */     private Builder()
/*     */     {
/* 643 */       this.typeUrl_ = "";
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
/* 735 */       this.value_ = ByteString.EMPTY;
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
/* 782 */       this.keyMaterialType_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.keyMaterialType_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.keyMaterialType_ = 0; return this; }
/*     */     public Descriptors.Descriptor getDescriptorForType() { return Tink.internal_static_google_crypto_tink_KeyData_descriptor; }
/*     */     public KeyData getDefaultInstanceForType() { return KeyData.getDefaultInstance(); }
/*     */     public KeyData build() { KeyData result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/*     */     public KeyData buildPartial() { KeyData result = new KeyData(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(KeyData result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.typeUrl_ = this.typeUrl_;  if ((from_bitField0_ & 0x2) != 0) result.value_ = this.value_;  if ((from_bitField0_ & 0x4) != 0) result.keyMaterialType_ = this.keyMaterialType_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof KeyData) return mergeFrom((KeyData)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(KeyData other) { if (other == KeyData.getDefaultInstance()) return this;  if (!other.getTypeUrl().isEmpty()) { this.typeUrl_ = other.typeUrl_; this.bitField0_ |= 0x1; onChanged(); }  if (!other.getValue().isEmpty()) setValue(other.getValue());  if (other.keyMaterialType_ != 0)
/*     */         setKeyMaterialTypeValue(other.getKeyMaterialTypeValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 792 */     public int getKeyMaterialTypeValue() { return this.keyMaterialType_; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 10: this.typeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.value_ = input.readBytes(); this.bitField0_ |= 0x2; continue;
/*     */             case 24:
/*     */               this.keyMaterialType_ = input.readEnum(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public String getTypeUrl() { Object ref = this.typeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; }  return (String)ref; }
/*     */     public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; }
/*     */     public Builder setTypeUrl(String value) { if (value == null)
/*     */         throw new NullPointerException();  this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/* 804 */     public Builder setKeyMaterialTypeValue(int value) { this.keyMaterialType_ = value;
/* 805 */       this.bitField0_ |= 0x4;
/* 806 */       onChanged();
/* 807 */       return this; } public Builder clearTypeUrl() { this.typeUrl_ = KeyData.getDefaultInstance().getTypeUrl(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*     */     public Builder setTypeUrlBytes(ByteString value) { if (value == null)
/*     */         throw new NullPointerException();  KeyData.checkByteStringIsUtf8(value); this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public ByteString getValue() { return this.value_; }
/*     */     public Builder setValue(ByteString value) { if (value == null)
/*     */         throw new NullPointerException();  this.value_ = value; this.bitField0_ |= 0x2;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearValue() { this.bitField0_ &= 0xFFFFFFFD;
/*     */       this.value_ = KeyData.getDefaultInstance().getValue();
/*     */       onChanged();
/*     */       return this; }
/* 819 */     public KeyData.KeyMaterialType getKeyMaterialType() { KeyData.KeyMaterialType result = KeyData.KeyMaterialType.forNumber(this.keyMaterialType_);
/* 820 */       return (result == null) ? KeyData.KeyMaterialType.UNRECOGNIZED : result; }
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
/*     */     public Builder setKeyMaterialType(KeyData.KeyMaterialType value) {
/* 832 */       if (value == null) throw new NullPointerException(); 
/* 833 */       this.bitField0_ |= 0x4;
/* 834 */       this.keyMaterialType_ = value.getNumber();
/* 835 */       onChanged();
/* 836 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeyMaterialType() {
/* 847 */       this.bitField0_ &= 0xFFFFFFFB;
/* 848 */       this.keyMaterialType_ = 0;
/* 849 */       onChanged();
/* 850 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 859 */   private static final KeyData DEFAULT_INSTANCE = new KeyData();
/*     */ 
/*     */   
/*     */   public static KeyData getDefaultInstance() {
/* 863 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 867 */   private static final Parser<KeyData> PARSER = (Parser<KeyData>)new AbstractParser<KeyData>()
/*     */     {
/*     */ 
/*     */       
/*     */       public KeyData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 873 */         KeyData.Builder builder = KeyData.newBuilder();
/*     */         try {
/* 875 */           builder.mergeFrom(input, extensionRegistry);
/* 876 */         } catch (InvalidProtocolBufferException e) {
/* 877 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 878 */         } catch (UninitializedMessageException e) {
/* 879 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 880 */         } catch (IOException e) {
/* 881 */           throw (new InvalidProtocolBufferException(e))
/* 882 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 884 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<KeyData> parser() {
/* 889 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<KeyData> getParserForType() {
/* 894 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyData getDefaultInstanceForType() {
/* 899 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeyData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */