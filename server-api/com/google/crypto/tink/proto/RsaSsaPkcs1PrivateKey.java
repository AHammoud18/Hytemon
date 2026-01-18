/*      */ package com.google.crypto.tink.proto;public final class RsaSsaPkcs1PrivateKey extends GeneratedMessage implements RsaSsaPkcs1PrivateKeyOrBuilder { private static final long serialVersionUID = 0L;
/*      */   private int bitField0_;
/*      */   public static final int VERSION_FIELD_NUMBER = 1;
/*      */   private int version_;
/*      */   public static final int PUBLIC_KEY_FIELD_NUMBER = 2;
/*      */   private RsaSsaPkcs1PublicKey publicKey_;
/*      */   public static final int D_FIELD_NUMBER = 3;
/*      */   private ByteString d_;
/*      */   public static final int P_FIELD_NUMBER = 4;
/*      */   private ByteString p_;
/*      */   public static final int Q_FIELD_NUMBER = 5;
/*      */   private ByteString q_;
/*      */   public static final int DP_FIELD_NUMBER = 6;
/*      */   private ByteString dp_;
/*      */   public static final int DQ_FIELD_NUMBER = 7;
/*      */   private ByteString dq_;
/*      */   public static final int CRT_FIELD_NUMBER = 8;
/*      */   private ByteString crt_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPkcs1PrivateKey.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   28 */         .getName());
/*      */   }
/*      */   
/*      */   private RsaSsaPkcs1PrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*   58 */     this.version_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  111 */     this.d_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  128 */     this.p_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  146 */     this.q_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  163 */     this.dp_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  180 */     this.dq_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  197 */     this.crt_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  213 */     this.memoizedIsInitialized = -1; } private RsaSsaPkcs1PrivateKey() { this.version_ = 0; this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(RsaSsaPkcs1PrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); } public RsaSsaPkcs1PublicKey getPublicKey() { return (this.publicKey_ == null) ? RsaSsaPkcs1PublicKey.getDefaultInstance() : this.publicKey_; } public RsaSsaPkcs1PublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? RsaSsaPkcs1PublicKey.getDefaultInstance() : this.publicKey_; } public ByteString getD() { return this.d_; } public ByteString getP() { return this.p_; } public ByteString getQ() { return this.q_; } public ByteString getDp() { return this.dp_; }
/*      */   public ByteString getDq() { return this.dq_; }
/*      */   public ByteString getCrt() { return this.crt_; }
/*  216 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  217 */     if (isInitialized == 1) return true; 
/*  218 */     if (isInitialized == 0) return false;
/*      */     
/*  220 */     this.memoizedIsInitialized = 1;
/*  221 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  227 */     if (this.version_ != 0) {
/*  228 */       output.writeUInt32(1, this.version_);
/*      */     }
/*  230 */     if ((this.bitField0_ & 0x1) != 0) {
/*  231 */       output.writeMessage(2, (MessageLite)getPublicKey());
/*      */     }
/*  233 */     if (!this.d_.isEmpty()) {
/*  234 */       output.writeBytes(3, this.d_);
/*      */     }
/*  236 */     if (!this.p_.isEmpty()) {
/*  237 */       output.writeBytes(4, this.p_);
/*      */     }
/*  239 */     if (!this.q_.isEmpty()) {
/*  240 */       output.writeBytes(5, this.q_);
/*      */     }
/*  242 */     if (!this.dp_.isEmpty()) {
/*  243 */       output.writeBytes(6, this.dp_);
/*      */     }
/*  245 */     if (!this.dq_.isEmpty()) {
/*  246 */       output.writeBytes(7, this.dq_);
/*      */     }
/*  248 */     if (!this.crt_.isEmpty()) {
/*  249 */       output.writeBytes(8, this.crt_);
/*      */     }
/*  251 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  256 */     int size = this.memoizedSize;
/*  257 */     if (size != -1) return size;
/*      */     
/*  259 */     size = 0;
/*  260 */     if (this.version_ != 0) {
/*  261 */       size += 
/*  262 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*      */     }
/*  264 */     if ((this.bitField0_ & 0x1) != 0) {
/*  265 */       size += 
/*  266 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getPublicKey());
/*      */     }
/*  268 */     if (!this.d_.isEmpty()) {
/*  269 */       size += 
/*  270 */         CodedOutputStream.computeBytesSize(3, this.d_);
/*      */     }
/*  272 */     if (!this.p_.isEmpty()) {
/*  273 */       size += 
/*  274 */         CodedOutputStream.computeBytesSize(4, this.p_);
/*      */     }
/*  276 */     if (!this.q_.isEmpty()) {
/*  277 */       size += 
/*  278 */         CodedOutputStream.computeBytesSize(5, this.q_);
/*      */     }
/*  280 */     if (!this.dp_.isEmpty()) {
/*  281 */       size += 
/*  282 */         CodedOutputStream.computeBytesSize(6, this.dp_);
/*      */     }
/*  284 */     if (!this.dq_.isEmpty()) {
/*  285 */       size += 
/*  286 */         CodedOutputStream.computeBytesSize(7, this.dq_);
/*      */     }
/*  288 */     if (!this.crt_.isEmpty()) {
/*  289 */       size += 
/*  290 */         CodedOutputStream.computeBytesSize(8, this.crt_);
/*      */     }
/*  292 */     size += getUnknownFields().getSerializedSize();
/*  293 */     this.memoizedSize = size;
/*  294 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  299 */     if (obj == this) {
/*  300 */       return true;
/*      */     }
/*  302 */     if (!(obj instanceof RsaSsaPkcs1PrivateKey)) {
/*  303 */       return super.equals(obj);
/*      */     }
/*  305 */     RsaSsaPkcs1PrivateKey other = (RsaSsaPkcs1PrivateKey)obj;
/*      */     
/*  307 */     if (getVersion() != other
/*  308 */       .getVersion()) return false; 
/*  309 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/*  310 */     if (hasPublicKey() && 
/*      */       
/*  312 */       !getPublicKey().equals(other.getPublicKey())) return false;
/*      */ 
/*      */     
/*  315 */     if (!getD().equals(other.getD())) return false;
/*      */     
/*  317 */     if (!getP().equals(other.getP())) return false;
/*      */     
/*  319 */     if (!getQ().equals(other.getQ())) return false;
/*      */     
/*  321 */     if (!getDp().equals(other.getDp())) return false;
/*      */     
/*  323 */     if (!getDq().equals(other.getDq())) return false;
/*      */     
/*  325 */     if (!getCrt().equals(other.getCrt())) return false; 
/*  326 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  327 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  332 */     if (this.memoizedHashCode != 0) {
/*  333 */       return this.memoizedHashCode;
/*      */     }
/*  335 */     int hash = 41;
/*  336 */     hash = 19 * hash + getDescriptor().hashCode();
/*  337 */     hash = 37 * hash + 1;
/*  338 */     hash = 53 * hash + getVersion();
/*  339 */     if (hasPublicKey()) {
/*  340 */       hash = 37 * hash + 2;
/*  341 */       hash = 53 * hash + getPublicKey().hashCode();
/*      */     } 
/*  343 */     hash = 37 * hash + 3;
/*  344 */     hash = 53 * hash + getD().hashCode();
/*  345 */     hash = 37 * hash + 4;
/*  346 */     hash = 53 * hash + getP().hashCode();
/*  347 */     hash = 37 * hash + 5;
/*  348 */     hash = 53 * hash + getQ().hashCode();
/*  349 */     hash = 37 * hash + 6;
/*  350 */     hash = 53 * hash + getDp().hashCode();
/*  351 */     hash = 37 * hash + 7;
/*  352 */     hash = 53 * hash + getDq().hashCode();
/*  353 */     hash = 37 * hash + 8;
/*  354 */     hash = 53 * hash + getCrt().hashCode();
/*  355 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  356 */     this.memoizedHashCode = hash;
/*  357 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  363 */     return (RsaSsaPkcs1PrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  369 */     return (RsaSsaPkcs1PrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  374 */     return (RsaSsaPkcs1PrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  380 */     return (RsaSsaPkcs1PrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  384 */     return (RsaSsaPkcs1PrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  390 */     return (RsaSsaPkcs1PrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(InputStream input) throws IOException {
/*  394 */     return 
/*  395 */       (RsaSsaPkcs1PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  401 */     return 
/*  402 */       (RsaSsaPkcs1PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/*  407 */     return 
/*  408 */       (RsaSsaPkcs1PrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  415 */     return 
/*  416 */       (RsaSsaPkcs1PrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(CodedInputStream input) throws IOException {
/*  421 */     return 
/*  422 */       (RsaSsaPkcs1PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  428 */     return 
/*  429 */       (RsaSsaPkcs1PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  433 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  435 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(RsaSsaPkcs1PrivateKey prototype) {
/*  438 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  442 */     return (this == DEFAULT_INSTANCE) ? 
/*  443 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  449 */     Builder builder = new Builder(parent);
/*  450 */     return builder;
/*      */   }
/*      */   
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements RsaSsaPkcs1PrivateKeyOrBuilder { private int bitField0_;
/*      */     private int version_;
/*      */     private RsaSsaPkcs1PublicKey publicKey_;
/*      */     private SingleFieldBuilder<RsaSsaPkcs1PublicKey, RsaSsaPkcs1PublicKey.Builder, RsaSsaPkcs1PublicKeyOrBuilder> publicKeyBuilder_;
/*      */     private ByteString d_;
/*      */     private ByteString p_;
/*      */     private ByteString q_;
/*      */     private ByteString dp_;
/*      */     private ByteString dq_;
/*      */     private ByteString crt_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  465 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  471 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_fieldAccessorTable
/*  472 */         .ensureFieldAccessorsInitialized(RsaSsaPkcs1PrivateKey.class, Builder.class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  894 */       this.d_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  944 */       this.p_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  997 */       this.q_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1047 */       this.dp_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1097 */       this.dq_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1147 */       this.crt_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (RsaSsaPkcs1PrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PrivateKey_descriptor; } public RsaSsaPkcs1PrivateKey getDefaultInstanceForType() { return RsaSsaPkcs1PrivateKey.getDefaultInstance(); } public RsaSsaPkcs1PrivateKey build() { RsaSsaPkcs1PrivateKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public RsaSsaPkcs1PrivateKey buildPartial() { RsaSsaPkcs1PrivateKey result = new RsaSsaPkcs1PrivateKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(RsaSsaPkcs1PrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (RsaSsaPkcs1PublicKey)this.publicKeyBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.d_ = this.d_;  if ((from_bitField0_ & 0x8) != 0) result.p_ = this.p_;  if ((from_bitField0_ & 0x10) != 0) result.q_ = this.q_;  if ((from_bitField0_ & 0x20) != 0) result.dp_ = this.dp_;  if ((from_bitField0_ & 0x40) != 0) result.dq_ = this.dq_;  if ((from_bitField0_ & 0x80) != 0) result.crt_ = this.crt_;  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof RsaSsaPkcs1PrivateKey) return mergeFrom((RsaSsaPkcs1PrivateKey)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(RsaSsaPkcs1PrivateKey other) { if (other == RsaSsaPkcs1PrivateKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.hasPublicKey()) mergePublicKey(other.getPublicKey());  if (!other.getD().isEmpty()) setD(other.getD());  if (!other.getP().isEmpty()) setP(other.getP());  if (!other.getQ().isEmpty()) setQ(other.getQ());  if (!other.getDp().isEmpty()) setDp(other.getDp());  if (!other.getDq().isEmpty()) setDq(other.getDq());  if (!other.getCrt().isEmpty()) setCrt(other.getCrt());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*      */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*      */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.d_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.p_ = input.readBytes(); this.bitField0_ |= 0x8; continue;case 42: this.q_ = input.readBytes(); this.bitField0_ |= 0x10; continue;case 50: this.dp_ = input.readBytes(); this.bitField0_ |= 0x20; continue;case 58: this.dq_ = input.readBytes(); this.bitField0_ |= 0x40; continue;case 66: this.crt_ = input.readBytes(); this.bitField0_ |= 0x80; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*      */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*      */     public int getVersion() { return this.version_; }
/*      */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*      */     public boolean hasPublicKey() { return ((this.bitField0_ & 0x2) != 0); }
/*      */     public RsaSsaPkcs1PublicKey getPublicKey() { if (this.publicKeyBuilder_ == null)
/*      */         return (this.publicKey_ == null) ? RsaSsaPkcs1PublicKey.getDefaultInstance() : this.publicKey_;  return (RsaSsaPkcs1PublicKey)this.publicKeyBuilder_.getMessage(); }
/*      */     public Builder setPublicKey(RsaSsaPkcs1PublicKey value) { if (this.publicKeyBuilder_ == null) { if (value == null)
/*      */           throw new NullPointerException();  this.publicKey_ = value; } else { this.publicKeyBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public Builder setPublicKey(RsaSsaPkcs1PublicKey.Builder builderForValue) { if (this.publicKeyBuilder_ == null) { this.publicKey_ = builderForValue.build(); } else { this.publicKeyBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/* 1160 */     public Builder mergePublicKey(RsaSsaPkcs1PublicKey value) { if (this.publicKeyBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.publicKey_ != null && this.publicKey_ != RsaSsaPkcs1PublicKey.getDefaultInstance()) { getPublicKeyBuilder().mergeFrom(value); } else { this.publicKey_ = value; }  } else { this.publicKeyBuilder_.mergeFrom(value); }  if (this.publicKey_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; } public ByteString getCrt() { return this.crt_; } public Builder clearPublicKey() { this.bitField0_ &= 0xFFFFFFFD; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  onChanged(); return this; } public RsaSsaPkcs1PublicKey.Builder getPublicKeyBuilder() { this.bitField0_ |= 0x2; onChanged(); return (RsaSsaPkcs1PublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder(); } public RsaSsaPkcs1PublicKeyOrBuilder getPublicKeyOrBuilder() { if (this.publicKeyBuilder_ != null) return (RsaSsaPkcs1PublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();  return (this.publicKey_ == null) ? RsaSsaPkcs1PublicKey.getDefaultInstance() : this.publicKey_; } private SingleFieldBuilder<RsaSsaPkcs1PublicKey, RsaSsaPkcs1PublicKey.Builder, RsaSsaPkcs1PublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() { if (this.publicKeyBuilder_ == null) { this.publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean()); this.publicKey_ = null; }  return this.publicKeyBuilder_; } public ByteString getD() { return this.d_; } public Builder setD(ByteString value) { if (value == null) throw new NullPointerException();  this.d_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*      */     public Builder clearD() { this.bitField0_ &= 0xFFFFFFFB; this.d_ = RsaSsaPkcs1PrivateKey.getDefaultInstance().getD(); onChanged(); return this; }
/*      */     public ByteString getP() { return this.p_; }
/*      */     public Builder setP(ByteString value) { if (value == null) throw new NullPointerException();  this.p_ = value; this.bitField0_ |= 0x8; onChanged(); return this; }
/*      */     public Builder clearP() { this.bitField0_ &= 0xFFFFFFF7; this.p_ = RsaSsaPkcs1PrivateKey.getDefaultInstance().getP(); onChanged(); return this; }
/*      */     public ByteString getQ() { return this.q_; }
/*      */     public Builder setQ(ByteString value) { if (value == null) throw new NullPointerException();  this.q_ = value; this.bitField0_ |= 0x10; onChanged(); return this; }
/*      */     public Builder clearQ() { this.bitField0_ &= 0xFFFFFFEF; this.q_ = RsaSsaPkcs1PrivateKey.getDefaultInstance().getQ(); onChanged(); return this; }
/*      */     public ByteString getDp() { return this.dp_; }
/*      */     public Builder setDp(ByteString value) { if (value == null) throw new NullPointerException();  this.dp_ = value; this.bitField0_ |= 0x20; onChanged(); return this; }
/*      */     public Builder clearDp() { this.bitField0_ &= 0xFFFFFFDF; this.dp_ = RsaSsaPkcs1PrivateKey.getDefaultInstance().getDp(); onChanged(); return this; }
/*      */     public ByteString getDq() { return this.dq_; }
/*      */     public Builder setDq(ByteString value) { if (value == null) throw new NullPointerException();  this.dq_ = value; this.bitField0_ |= 0x40; onChanged(); return this; }
/*      */     public Builder clearDq() { this.bitField0_ &= 0xFFFFFFBF; this.dq_ = RsaSsaPkcs1PrivateKey.getDefaultInstance().getDq(); onChanged(); return this; }
/* 1174 */     public Builder setCrt(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 1175 */       this.crt_ = value;
/* 1176 */       this.bitField0_ |= 0x80;
/* 1177 */       onChanged();
/* 1178 */       return this; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearCrt() {
/* 1191 */       this.bitField0_ &= 0xFFFFFF7F;
/* 1192 */       this.crt_ = RsaSsaPkcs1PrivateKey.getDefaultInstance().getCrt();
/* 1193 */       onChanged();
/* 1194 */       return this;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1203 */   private static final RsaSsaPkcs1PrivateKey DEFAULT_INSTANCE = new RsaSsaPkcs1PrivateKey();
/*      */ 
/*      */   
/*      */   public static RsaSsaPkcs1PrivateKey getDefaultInstance() {
/* 1207 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1211 */   private static final Parser<RsaSsaPkcs1PrivateKey> PARSER = (Parser<RsaSsaPkcs1PrivateKey>)new AbstractParser<RsaSsaPkcs1PrivateKey>()
/*      */     {
/*      */ 
/*      */       
/*      */       public RsaSsaPkcs1PrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1217 */         RsaSsaPkcs1PrivateKey.Builder builder = RsaSsaPkcs1PrivateKey.newBuilder();
/*      */         try {
/* 1219 */           builder.mergeFrom(input, extensionRegistry);
/* 1220 */         } catch (InvalidProtocolBufferException e) {
/* 1221 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1222 */         } catch (UninitializedMessageException e) {
/* 1223 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1224 */         } catch (IOException e) {
/* 1225 */           throw (new InvalidProtocolBufferException(e))
/* 1226 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1228 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<RsaSsaPkcs1PrivateKey> parser() {
/* 1233 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<RsaSsaPkcs1PrivateKey> getParserForType() {
/* 1238 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public RsaSsaPkcs1PrivateKey getDefaultInstanceForType() {
/* 1243 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPkcs1PrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */