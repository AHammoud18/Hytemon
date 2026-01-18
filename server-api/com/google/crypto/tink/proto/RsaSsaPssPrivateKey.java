/*      */ package com.google.crypto.tink.proto;public final class RsaSsaPssPrivateKey extends GeneratedMessage implements RsaSsaPssPrivateKeyOrBuilder { private static final long serialVersionUID = 0L;
/*      */   private int bitField0_;
/*      */   public static final int VERSION_FIELD_NUMBER = 1;
/*      */   private int version_;
/*      */   public static final int PUBLIC_KEY_FIELD_NUMBER = 2;
/*      */   private RsaSsaPssPublicKey publicKey_;
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
/*   22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPssPrivateKey.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   28 */         .getName());
/*      */   }
/*      */   
/*      */   private RsaSsaPssPrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*  213 */     this.memoizedIsInitialized = -1; } private RsaSsaPssPrivateKey() { this.version_ = 0; this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssPrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssPrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(RsaSsaPssPrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); } public RsaSsaPssPublicKey getPublicKey() { return (this.publicKey_ == null) ? RsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_; } public RsaSsaPssPublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? RsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_; } public ByteString getD() { return this.d_; } public ByteString getP() { return this.p_; } public ByteString getQ() { return this.q_; } public ByteString getDp() { return this.dp_; }
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
/*  302 */     if (!(obj instanceof RsaSsaPssPrivateKey)) {
/*  303 */       return super.equals(obj);
/*      */     }
/*  305 */     RsaSsaPssPrivateKey other = (RsaSsaPssPrivateKey)obj;
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
/*      */   public static RsaSsaPssPrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  363 */     return (RsaSsaPssPrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  369 */     return (RsaSsaPssPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  374 */     return (RsaSsaPssPrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  380 */     return (RsaSsaPssPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  384 */     return (RsaSsaPssPrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  390 */     return (RsaSsaPssPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(InputStream input) throws IOException {
/*  394 */     return 
/*  395 */       (RsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  401 */     return 
/*  402 */       (RsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/*  407 */     return 
/*  408 */       (RsaSsaPssPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  415 */     return 
/*  416 */       (RsaSsaPssPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(CodedInputStream input) throws IOException {
/*  421 */     return 
/*  422 */       (RsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  428 */     return 
/*  429 */       (RsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  433 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  435 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(RsaSsaPssPrivateKey prototype) {
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
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements RsaSsaPssPrivateKeyOrBuilder { private int bitField0_;
/*      */     private int version_;
/*      */     private RsaSsaPssPublicKey publicKey_;
/*      */     private SingleFieldBuilder<RsaSsaPssPublicKey, RsaSsaPssPublicKey.Builder, RsaSsaPssPublicKeyOrBuilder> publicKeyBuilder_;
/*      */     private ByteString d_;
/*      */     private ByteString p_;
/*      */     private ByteString q_;
/*      */     private ByteString dp_;
/*      */     private ByteString dq_;
/*      */     private ByteString crt_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  465 */       return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssPrivateKey_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  471 */       return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssPrivateKey_fieldAccessorTable
/*  472 */         .ensureFieldAccessorsInitialized(RsaSsaPssPrivateKey.class, Builder.class);
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
/* 1147 */       this.crt_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (RsaSsaPssPrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssPrivateKey_descriptor; } public RsaSsaPssPrivateKey getDefaultInstanceForType() { return RsaSsaPssPrivateKey.getDefaultInstance(); } public RsaSsaPssPrivateKey build() { RsaSsaPssPrivateKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public RsaSsaPssPrivateKey buildPartial() { RsaSsaPssPrivateKey result = new RsaSsaPssPrivateKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(RsaSsaPssPrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (RsaSsaPssPublicKey)this.publicKeyBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.d_ = this.d_;  if ((from_bitField0_ & 0x8) != 0) result.p_ = this.p_;  if ((from_bitField0_ & 0x10) != 0) result.q_ = this.q_;  if ((from_bitField0_ & 0x20) != 0) result.dp_ = this.dp_;  if ((from_bitField0_ & 0x40) != 0) result.dq_ = this.dq_;  if ((from_bitField0_ & 0x80) != 0) result.crt_ = this.crt_;  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof RsaSsaPssPrivateKey) return mergeFrom((RsaSsaPssPrivateKey)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(RsaSsaPssPrivateKey other) { if (other == RsaSsaPssPrivateKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.hasPublicKey()) mergePublicKey(other.getPublicKey());  if (!other.getD().isEmpty()) setD(other.getD());  if (!other.getP().isEmpty()) setP(other.getP());  if (!other.getQ().isEmpty()) setQ(other.getQ());  if (!other.getDp().isEmpty()) setDp(other.getDp());  if (!other.getDq().isEmpty()) setDq(other.getDq());  if (!other.getCrt().isEmpty()) setCrt(other.getCrt());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*      */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*      */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.d_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.p_ = input.readBytes(); this.bitField0_ |= 0x8; continue;case 42: this.q_ = input.readBytes(); this.bitField0_ |= 0x10; continue;case 50: this.dp_ = input.readBytes(); this.bitField0_ |= 0x20; continue;case 58: this.dq_ = input.readBytes(); this.bitField0_ |= 0x40; continue;case 66: this.crt_ = input.readBytes(); this.bitField0_ |= 0x80; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*      */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*      */     public int getVersion() { return this.version_; }
/*      */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*      */     public boolean hasPublicKey() { return ((this.bitField0_ & 0x2) != 0); }
/*      */     public RsaSsaPssPublicKey getPublicKey() { if (this.publicKeyBuilder_ == null)
/*      */         return (this.publicKey_ == null) ? RsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_;  return (RsaSsaPssPublicKey)this.publicKeyBuilder_.getMessage(); }
/*      */     public Builder setPublicKey(RsaSsaPssPublicKey value) { if (this.publicKeyBuilder_ == null) { if (value == null)
/*      */           throw new NullPointerException();  this.publicKey_ = value; } else { this.publicKeyBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public Builder setPublicKey(RsaSsaPssPublicKey.Builder builderForValue) { if (this.publicKeyBuilder_ == null) { this.publicKey_ = builderForValue.build(); } else { this.publicKeyBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/* 1160 */     public Builder mergePublicKey(RsaSsaPssPublicKey value) { if (this.publicKeyBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.publicKey_ != null && this.publicKey_ != RsaSsaPssPublicKey.getDefaultInstance()) { getPublicKeyBuilder().mergeFrom(value); } else { this.publicKey_ = value; }  } else { this.publicKeyBuilder_.mergeFrom(value); }  if (this.publicKey_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; } public ByteString getCrt() { return this.crt_; } public Builder clearPublicKey() { this.bitField0_ &= 0xFFFFFFFD; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  onChanged(); return this; } public RsaSsaPssPublicKey.Builder getPublicKeyBuilder() { this.bitField0_ |= 0x2; onChanged(); return (RsaSsaPssPublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder(); } public RsaSsaPssPublicKeyOrBuilder getPublicKeyOrBuilder() { if (this.publicKeyBuilder_ != null) return (RsaSsaPssPublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();  return (this.publicKey_ == null) ? RsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_; } private SingleFieldBuilder<RsaSsaPssPublicKey, RsaSsaPssPublicKey.Builder, RsaSsaPssPublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() { if (this.publicKeyBuilder_ == null) { this.publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean()); this.publicKey_ = null; }  return this.publicKeyBuilder_; } public ByteString getD() { return this.d_; } public Builder setD(ByteString value) { if (value == null) throw new NullPointerException();  this.d_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*      */     public Builder clearD() { this.bitField0_ &= 0xFFFFFFFB; this.d_ = RsaSsaPssPrivateKey.getDefaultInstance().getD(); onChanged(); return this; }
/*      */     public ByteString getP() { return this.p_; }
/*      */     public Builder setP(ByteString value) { if (value == null) throw new NullPointerException();  this.p_ = value; this.bitField0_ |= 0x8; onChanged(); return this; }
/*      */     public Builder clearP() { this.bitField0_ &= 0xFFFFFFF7; this.p_ = RsaSsaPssPrivateKey.getDefaultInstance().getP(); onChanged(); return this; }
/*      */     public ByteString getQ() { return this.q_; }
/*      */     public Builder setQ(ByteString value) { if (value == null) throw new NullPointerException();  this.q_ = value; this.bitField0_ |= 0x10; onChanged(); return this; }
/*      */     public Builder clearQ() { this.bitField0_ &= 0xFFFFFFEF; this.q_ = RsaSsaPssPrivateKey.getDefaultInstance().getQ(); onChanged(); return this; }
/*      */     public ByteString getDp() { return this.dp_; }
/*      */     public Builder setDp(ByteString value) { if (value == null) throw new NullPointerException();  this.dp_ = value; this.bitField0_ |= 0x20; onChanged(); return this; }
/*      */     public Builder clearDp() { this.bitField0_ &= 0xFFFFFFDF; this.dp_ = RsaSsaPssPrivateKey.getDefaultInstance().getDp(); onChanged(); return this; }
/*      */     public ByteString getDq() { return this.dq_; }
/*      */     public Builder setDq(ByteString value) { if (value == null) throw new NullPointerException();  this.dq_ = value; this.bitField0_ |= 0x40; onChanged(); return this; }
/*      */     public Builder clearDq() { this.bitField0_ &= 0xFFFFFFBF; this.dq_ = RsaSsaPssPrivateKey.getDefaultInstance().getDq(); onChanged(); return this; }
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
/* 1192 */       this.crt_ = RsaSsaPssPrivateKey.getDefaultInstance().getCrt();
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
/* 1203 */   private static final RsaSsaPssPrivateKey DEFAULT_INSTANCE = new RsaSsaPssPrivateKey();
/*      */ 
/*      */   
/*      */   public static RsaSsaPssPrivateKey getDefaultInstance() {
/* 1207 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1211 */   private static final Parser<RsaSsaPssPrivateKey> PARSER = (Parser<RsaSsaPssPrivateKey>)new AbstractParser<RsaSsaPssPrivateKey>()
/*      */     {
/*      */ 
/*      */       
/*      */       public RsaSsaPssPrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1217 */         RsaSsaPssPrivateKey.Builder builder = RsaSsaPssPrivateKey.newBuilder();
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
/*      */   public static Parser<RsaSsaPssPrivateKey> parser() {
/* 1233 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<RsaSsaPssPrivateKey> getParserForType() {
/* 1238 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public RsaSsaPssPrivateKey getDefaultInstanceForType() {
/* 1243 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPssPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */