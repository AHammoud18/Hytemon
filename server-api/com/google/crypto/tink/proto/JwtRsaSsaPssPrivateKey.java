/*      */ package com.google.crypto.tink.proto;public final class JwtRsaSsaPssPrivateKey extends GeneratedMessage implements JwtRsaSsaPssPrivateKeyOrBuilder { private static final long serialVersionUID = 0L;
/*      */   private int bitField0_;
/*      */   public static final int VERSION_FIELD_NUMBER = 1;
/*      */   private int version_;
/*      */   public static final int PUBLIC_KEY_FIELD_NUMBER = 2;
/*      */   private JwtRsaSsaPssPublicKey publicKey_;
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
/*   22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtRsaSsaPssPrivateKey.class
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   28 */         .getName());
/*      */   }
/*      */   
/*      */   private JwtRsaSsaPssPrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*   95 */     this.d_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  111 */     this.p_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  128 */     this.q_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  144 */     this.dp_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     this.dq_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  176 */     this.crt_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  191 */     this.memoizedIsInitialized = -1; } private JwtRsaSsaPssPrivateKey() { this.version_ = 0; this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtRsaSsaPssPrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); } public JwtRsaSsaPssPublicKey getPublicKey() { return (this.publicKey_ == null) ? JwtRsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_; } public JwtRsaSsaPssPublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? JwtRsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_; } public ByteString getD() { return this.d_; } public ByteString getP() { return this.p_; } public ByteString getQ() { return this.q_; } public ByteString getDp() { return this.dp_; }
/*      */   public ByteString getDq() { return this.dq_; }
/*      */   public ByteString getCrt() { return this.crt_; }
/*  194 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  195 */     if (isInitialized == 1) return true; 
/*  196 */     if (isInitialized == 0) return false;
/*      */     
/*  198 */     this.memoizedIsInitialized = 1;
/*  199 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  205 */     if (this.version_ != 0) {
/*  206 */       output.writeUInt32(1, this.version_);
/*      */     }
/*  208 */     if ((this.bitField0_ & 0x1) != 0) {
/*  209 */       output.writeMessage(2, (MessageLite)getPublicKey());
/*      */     }
/*  211 */     if (!this.d_.isEmpty()) {
/*  212 */       output.writeBytes(3, this.d_);
/*      */     }
/*  214 */     if (!this.p_.isEmpty()) {
/*  215 */       output.writeBytes(4, this.p_);
/*      */     }
/*  217 */     if (!this.q_.isEmpty()) {
/*  218 */       output.writeBytes(5, this.q_);
/*      */     }
/*  220 */     if (!this.dp_.isEmpty()) {
/*  221 */       output.writeBytes(6, this.dp_);
/*      */     }
/*  223 */     if (!this.dq_.isEmpty()) {
/*  224 */       output.writeBytes(7, this.dq_);
/*      */     }
/*  226 */     if (!this.crt_.isEmpty()) {
/*  227 */       output.writeBytes(8, this.crt_);
/*      */     }
/*  229 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  234 */     int size = this.memoizedSize;
/*  235 */     if (size != -1) return size;
/*      */     
/*  237 */     size = 0;
/*  238 */     if (this.version_ != 0) {
/*  239 */       size += 
/*  240 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*      */     }
/*  242 */     if ((this.bitField0_ & 0x1) != 0) {
/*  243 */       size += 
/*  244 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getPublicKey());
/*      */     }
/*  246 */     if (!this.d_.isEmpty()) {
/*  247 */       size += 
/*  248 */         CodedOutputStream.computeBytesSize(3, this.d_);
/*      */     }
/*  250 */     if (!this.p_.isEmpty()) {
/*  251 */       size += 
/*  252 */         CodedOutputStream.computeBytesSize(4, this.p_);
/*      */     }
/*  254 */     if (!this.q_.isEmpty()) {
/*  255 */       size += 
/*  256 */         CodedOutputStream.computeBytesSize(5, this.q_);
/*      */     }
/*  258 */     if (!this.dp_.isEmpty()) {
/*  259 */       size += 
/*  260 */         CodedOutputStream.computeBytesSize(6, this.dp_);
/*      */     }
/*  262 */     if (!this.dq_.isEmpty()) {
/*  263 */       size += 
/*  264 */         CodedOutputStream.computeBytesSize(7, this.dq_);
/*      */     }
/*  266 */     if (!this.crt_.isEmpty()) {
/*  267 */       size += 
/*  268 */         CodedOutputStream.computeBytesSize(8, this.crt_);
/*      */     }
/*  270 */     size += getUnknownFields().getSerializedSize();
/*  271 */     this.memoizedSize = size;
/*  272 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  277 */     if (obj == this) {
/*  278 */       return true;
/*      */     }
/*  280 */     if (!(obj instanceof JwtRsaSsaPssPrivateKey)) {
/*  281 */       return super.equals(obj);
/*      */     }
/*  283 */     JwtRsaSsaPssPrivateKey other = (JwtRsaSsaPssPrivateKey)obj;
/*      */     
/*  285 */     if (getVersion() != other
/*  286 */       .getVersion()) return false; 
/*  287 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/*  288 */     if (hasPublicKey() && 
/*      */       
/*  290 */       !getPublicKey().equals(other.getPublicKey())) return false;
/*      */ 
/*      */     
/*  293 */     if (!getD().equals(other.getD())) return false;
/*      */     
/*  295 */     if (!getP().equals(other.getP())) return false;
/*      */     
/*  297 */     if (!getQ().equals(other.getQ())) return false;
/*      */     
/*  299 */     if (!getDp().equals(other.getDp())) return false;
/*      */     
/*  301 */     if (!getDq().equals(other.getDq())) return false;
/*      */     
/*  303 */     if (!getCrt().equals(other.getCrt())) return false; 
/*  304 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  305 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  310 */     if (this.memoizedHashCode != 0) {
/*  311 */       return this.memoizedHashCode;
/*      */     }
/*  313 */     int hash = 41;
/*  314 */     hash = 19 * hash + getDescriptor().hashCode();
/*  315 */     hash = 37 * hash + 1;
/*  316 */     hash = 53 * hash + getVersion();
/*  317 */     if (hasPublicKey()) {
/*  318 */       hash = 37 * hash + 2;
/*  319 */       hash = 53 * hash + getPublicKey().hashCode();
/*      */     } 
/*  321 */     hash = 37 * hash + 3;
/*  322 */     hash = 53 * hash + getD().hashCode();
/*  323 */     hash = 37 * hash + 4;
/*  324 */     hash = 53 * hash + getP().hashCode();
/*  325 */     hash = 37 * hash + 5;
/*  326 */     hash = 53 * hash + getQ().hashCode();
/*  327 */     hash = 37 * hash + 6;
/*  328 */     hash = 53 * hash + getDp().hashCode();
/*  329 */     hash = 37 * hash + 7;
/*  330 */     hash = 53 * hash + getDq().hashCode();
/*  331 */     hash = 37 * hash + 8;
/*  332 */     hash = 53 * hash + getCrt().hashCode();
/*  333 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  334 */     this.memoizedHashCode = hash;
/*  335 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  341 */     return (JwtRsaSsaPssPrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  347 */     return (JwtRsaSsaPssPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  352 */     return (JwtRsaSsaPssPrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  358 */     return (JwtRsaSsaPssPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  362 */     return (JwtRsaSsaPssPrivateKey)PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  368 */     return (JwtRsaSsaPssPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(InputStream input) throws IOException {
/*  372 */     return 
/*  373 */       (JwtRsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  379 */     return 
/*  380 */       (JwtRsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/*  385 */     return 
/*  386 */       (JwtRsaSsaPssPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  393 */     return 
/*  394 */       (JwtRsaSsaPssPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(CodedInputStream input) throws IOException {
/*  399 */     return 
/*  400 */       (JwtRsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  406 */     return 
/*  407 */       (JwtRsaSsaPssPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  411 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  413 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(JwtRsaSsaPssPrivateKey prototype) {
/*  416 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  420 */     return (this == DEFAULT_INSTANCE) ? 
/*  421 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  427 */     Builder builder = new Builder(parent);
/*  428 */     return builder;
/*      */   }
/*      */   
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements JwtRsaSsaPssPrivateKeyOrBuilder { private int bitField0_;
/*      */     private int version_;
/*      */     private JwtRsaSsaPssPublicKey publicKey_;
/*      */     private SingleFieldBuilder<JwtRsaSsaPssPublicKey, JwtRsaSsaPssPublicKey.Builder, JwtRsaSsaPssPublicKeyOrBuilder> publicKeyBuilder_;
/*      */     private ByteString d_;
/*      */     private ByteString p_;
/*      */     private ByteString q_;
/*      */     private ByteString dp_;
/*      */     private ByteString dq_;
/*      */     private ByteString crt_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  443 */       return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  449 */       return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_fieldAccessorTable
/*  450 */         .ensureFieldAccessorsInitialized(JwtRsaSsaPssPrivateKey.class, Builder.class);
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
/*      */     private Builder()
/*      */     {
/*  824 */       this.d_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  871 */       this.p_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  921 */       this.q_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  968 */       this.dp_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1015 */       this.dq_ = ByteString.EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1062 */       this.crt_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (JwtRsaSsaPssPrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssPrivateKey_descriptor; } public JwtRsaSsaPssPrivateKey getDefaultInstanceForType() { return JwtRsaSsaPssPrivateKey.getDefaultInstance(); } public JwtRsaSsaPssPrivateKey build() { JwtRsaSsaPssPrivateKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtRsaSsaPssPrivateKey buildPartial() { JwtRsaSsaPssPrivateKey result = new JwtRsaSsaPssPrivateKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(JwtRsaSsaPssPrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (JwtRsaSsaPssPublicKey)this.publicKeyBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.d_ = this.d_;  if ((from_bitField0_ & 0x8) != 0) result.p_ = this.p_;  if ((from_bitField0_ & 0x10) != 0) result.q_ = this.q_;  if ((from_bitField0_ & 0x20) != 0) result.dp_ = this.dp_;  if ((from_bitField0_ & 0x40) != 0) result.dq_ = this.dq_;  if ((from_bitField0_ & 0x80) != 0) result.crt_ = this.crt_;  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof JwtRsaSsaPssPrivateKey) return mergeFrom((JwtRsaSsaPssPrivateKey)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(JwtRsaSsaPssPrivateKey other) { if (other == JwtRsaSsaPssPrivateKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.hasPublicKey()) mergePublicKey(other.getPublicKey());  if (!other.getD().isEmpty()) setD(other.getD());  if (!other.getP().isEmpty()) setP(other.getP());  if (!other.getQ().isEmpty()) setQ(other.getQ());  if (!other.getDp().isEmpty()) setDp(other.getDp());  if (!other.getDq().isEmpty()) setDq(other.getDq());  if (!other.getCrt().isEmpty()) setCrt(other.getCrt());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.d_ = ByteString.EMPTY; this.p_ = ByteString.EMPTY; this.q_ = ByteString.EMPTY; this.dp_ = ByteString.EMPTY; this.dq_ = ByteString.EMPTY; this.crt_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*      */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.d_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.p_ = input.readBytes(); this.bitField0_ |= 0x8; continue;case 42: this.q_ = input.readBytes(); this.bitField0_ |= 0x10; continue;case 50: this.dp_ = input.readBytes(); this.bitField0_ |= 0x20; continue;case 58: this.dq_ = input.readBytes(); this.bitField0_ |= 0x40; continue;case 66: this.crt_ = input.readBytes(); this.bitField0_ |= 0x80; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*      */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*      */     public int getVersion() { return this.version_; }
/*      */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*      */     public boolean hasPublicKey() { return ((this.bitField0_ & 0x2) != 0); }
/*      */     public JwtRsaSsaPssPublicKey getPublicKey() { if (this.publicKeyBuilder_ == null)
/*      */         return (this.publicKey_ == null) ? JwtRsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_;  return (JwtRsaSsaPssPublicKey)this.publicKeyBuilder_.getMessage(); }
/*      */     public Builder setPublicKey(JwtRsaSsaPssPublicKey value) { if (this.publicKeyBuilder_ == null) { if (value == null)
/*      */           throw new NullPointerException();  this.publicKey_ = value; } else { this.publicKeyBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public Builder setPublicKey(JwtRsaSsaPssPublicKey.Builder builderForValue) { if (this.publicKeyBuilder_ == null) { this.publicKey_ = builderForValue.build(); } else { this.publicKeyBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/* 1074 */     public Builder mergePublicKey(JwtRsaSsaPssPublicKey value) { if (this.publicKeyBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.publicKey_ != null && this.publicKey_ != JwtRsaSsaPssPublicKey.getDefaultInstance()) { getPublicKeyBuilder().mergeFrom(value); } else { this.publicKey_ = value; }  } else { this.publicKeyBuilder_.mergeFrom(value); }  if (this.publicKey_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; } public ByteString getCrt() { return this.crt_; } public Builder clearPublicKey() { this.bitField0_ &= 0xFFFFFFFD; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  onChanged(); return this; } public JwtRsaSsaPssPublicKey.Builder getPublicKeyBuilder() { this.bitField0_ |= 0x2; onChanged(); return (JwtRsaSsaPssPublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder(); } public JwtRsaSsaPssPublicKeyOrBuilder getPublicKeyOrBuilder() { if (this.publicKeyBuilder_ != null) return (JwtRsaSsaPssPublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();  return (this.publicKey_ == null) ? JwtRsaSsaPssPublicKey.getDefaultInstance() : this.publicKey_; } private SingleFieldBuilder<JwtRsaSsaPssPublicKey, JwtRsaSsaPssPublicKey.Builder, JwtRsaSsaPssPublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() { if (this.publicKeyBuilder_ == null) { this.publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean()); this.publicKey_ = null; }  return this.publicKeyBuilder_; } public ByteString getD() { return this.d_; } public Builder setD(ByteString value) { if (value == null) throw new NullPointerException();  this.d_ = value; this.bitField0_ |= 0x4; onChanged(); return this; } public Builder clearD() { this.bitField0_ &= 0xFFFFFFFB; this.d_ = JwtRsaSsaPssPrivateKey.getDefaultInstance().getD(); onChanged(); return this; }
/*      */     public ByteString getP() { return this.p_; }
/*      */     public Builder setP(ByteString value) { if (value == null) throw new NullPointerException();  this.p_ = value; this.bitField0_ |= 0x8; onChanged(); return this; }
/*      */     public Builder clearP() { this.bitField0_ &= 0xFFFFFFF7; this.p_ = JwtRsaSsaPssPrivateKey.getDefaultInstance().getP(); onChanged(); return this; }
/*      */     public ByteString getQ() { return this.q_; }
/*      */     public Builder setQ(ByteString value) { if (value == null) throw new NullPointerException();  this.q_ = value; this.bitField0_ |= 0x10; onChanged(); return this; }
/*      */     public Builder clearQ() { this.bitField0_ &= 0xFFFFFFEF; this.q_ = JwtRsaSsaPssPrivateKey.getDefaultInstance().getQ(); onChanged(); return this; }
/*      */     public ByteString getDp() { return this.dp_; }
/*      */     public Builder setDp(ByteString value) { if (value == null) throw new NullPointerException();  this.dp_ = value; this.bitField0_ |= 0x20; onChanged(); return this; }
/*      */     public Builder clearDp() { this.bitField0_ &= 0xFFFFFFDF; this.dp_ = JwtRsaSsaPssPrivateKey.getDefaultInstance().getDp(); onChanged(); return this; }
/*      */     public ByteString getDq() { return this.dq_; }
/*      */     public Builder setDq(ByteString value) { if (value == null) throw new NullPointerException();  this.dq_ = value; this.bitField0_ |= 0x40; onChanged(); return this; }
/*      */     public Builder clearDq() { this.bitField0_ &= 0xFFFFFFBF; this.dq_ = JwtRsaSsaPssPrivateKey.getDefaultInstance().getDq(); onChanged(); return this; }
/* 1087 */     public Builder setCrt(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 1088 */       this.crt_ = value;
/* 1089 */       this.bitField0_ |= 0x80;
/* 1090 */       onChanged();
/* 1091 */       return this; }
/*      */ 
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
/* 1103 */       this.bitField0_ &= 0xFFFFFF7F;
/* 1104 */       this.crt_ = JwtRsaSsaPssPrivateKey.getDefaultInstance().getCrt();
/* 1105 */       onChanged();
/* 1106 */       return this;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1115 */   private static final JwtRsaSsaPssPrivateKey DEFAULT_INSTANCE = new JwtRsaSsaPssPrivateKey();
/*      */ 
/*      */   
/*      */   public static JwtRsaSsaPssPrivateKey getDefaultInstance() {
/* 1119 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1123 */   private static final Parser<JwtRsaSsaPssPrivateKey> PARSER = (Parser<JwtRsaSsaPssPrivateKey>)new AbstractParser<JwtRsaSsaPssPrivateKey>()
/*      */     {
/*      */ 
/*      */       
/*      */       public JwtRsaSsaPssPrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1129 */         JwtRsaSsaPssPrivateKey.Builder builder = JwtRsaSsaPssPrivateKey.newBuilder();
/*      */         try {
/* 1131 */           builder.mergeFrom(input, extensionRegistry);
/* 1132 */         } catch (InvalidProtocolBufferException e) {
/* 1133 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1134 */         } catch (UninitializedMessageException e) {
/* 1135 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1136 */         } catch (IOException e) {
/* 1137 */           throw (new InvalidProtocolBufferException(e))
/* 1138 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1140 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<JwtRsaSsaPssPrivateKey> parser() {
/* 1145 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<JwtRsaSsaPssPrivateKey> getParserForType() {
/* 1150 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public JwtRsaSsaPssPrivateKey getDefaultInstanceForType() {
/* 1155 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPssPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */