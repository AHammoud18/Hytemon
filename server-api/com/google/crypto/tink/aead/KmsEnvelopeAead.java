/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.TinkProtoParametersFormat;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.Serialization;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KmsEnvelopeAead
/*     */   implements Aead
/*     */ {
/*  60 */   private static final byte[] EMPTY_AAD = new byte[0];
/*     */   
/*     */   private final String typeUrlForParsing;
/*     */   
/*     */   private final Parameters parametersForNewKeys;
/*     */   private final Aead remote;
/*     */   private static final int LENGTH_ENCRYPTED_DEK = 4;
/*     */   private static final int MAX_LENGTH_ENCRYPTED_DEK = 4096;
/*     */   
/*     */   private static Set<String> listSupportedDekKeyTypes() {
/*  70 */     HashSet<String> dekKeyTypeUrls = new HashSet<>();
/*  71 */     dekKeyTypeUrls.add("type.googleapis.com/google.crypto.tink.AesGcmKey");
/*  72 */     dekKeyTypeUrls.add("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key");
/*  73 */     dekKeyTypeUrls.add("type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key");
/*  74 */     dekKeyTypeUrls.add("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey");
/*  75 */     dekKeyTypeUrls.add("type.googleapis.com/google.crypto.tink.AesGcmSivKey");
/*  76 */     dekKeyTypeUrls.add("type.googleapis.com/google.crypto.tink.AesEaxKey");
/*  77 */     return Collections.unmodifiableSet(dekKeyTypeUrls);
/*     */   }
/*     */   
/*  80 */   private static final Set<String> supportedDekKeyTypes = listSupportedDekKeyTypes();
/*     */   
/*     */   public static boolean isSupportedDekKeyType(String dekKeyTypeUrl) {
/*  83 */     return supportedDekKeyTypes.contains(dekKeyTypeUrl);
/*     */   }
/*     */ 
/*     */   
/*     */   private Parameters getRawParameters(KeyTemplate dekTemplate) throws GeneralSecurityException {
/*  88 */     KeyTemplate rawTemplate = KeyTemplate.newBuilder(dekTemplate).setOutputPrefixType(OutputPrefixType.RAW).build();
/*  89 */     return TinkProtoParametersFormat.parse(rawTemplate.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KmsEnvelopeAead(KeyTemplate dekTemplate, Aead remote) throws GeneralSecurityException {
/* 110 */     if (!isSupportedDekKeyType(dekTemplate.getTypeUrl())) {
/* 111 */       throw new IllegalArgumentException("Unsupported DEK key type: " + dekTemplate
/*     */           
/* 113 */           .getTypeUrl() + ". Only Tink AEAD key types are supported.");
/*     */     }
/*     */     
/* 116 */     this.typeUrlForParsing = dekTemplate.getTypeUrl();
/* 117 */     this.parametersForNewKeys = getRawParameters(dekTemplate);
/* 118 */     this.remote = remote;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Aead create(AeadParameters dekParameters, Aead remote) throws GeneralSecurityException {
/*     */     KeyTemplate dekTemplate;
/*     */     try {
/* 137 */       dekTemplate = KeyTemplate.parseFrom(
/* 138 */           TinkProtoParametersFormat.serialize(dekParameters), 
/* 139 */           ExtensionRegistryLite.getEmptyRegistry());
/* 140 */     } catch (InvalidProtocolBufferException e) {
/* 141 */       throw new GeneralSecurityException(e);
/*     */     } 
/* 143 */     return new KmsEnvelopeAead(dekTemplate, remote);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 151 */     Key key = MutableKeyCreationRegistry.globalInstance().createKey(this.parametersForNewKeys, null);
/*     */ 
/*     */ 
/*     */     
/* 155 */     ProtoKeySerialization serialization = (ProtoKeySerialization)MutableSerializationRegistry.globalInstance().serializeKey(key, ProtoKeySerialization.class, InsecureSecretKeyAccess.get());
/* 156 */     byte[] dek = serialization.getValue().toByteArray();
/*     */     
/* 158 */     byte[] encryptedDek = this.remote.encrypt(dek, EMPTY_AAD);
/* 159 */     if (encryptedDek.length > 4096) {
/* 160 */       throw new GeneralSecurityException("length of encrypted DEK too large");
/*     */     }
/*     */     
/* 163 */     Aead aead = (Aead)MutablePrimitiveRegistry.globalInstance().getPrimitive(key, Aead.class);
/* 164 */     byte[] payload = aead.encrypt(plaintext, associatedData);
/*     */     
/* 166 */     return buildCiphertext(encryptedDek, payload);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*     */     try {
/* 173 */       ByteBuffer buffer = ByteBuffer.wrap(ciphertext);
/* 174 */       int encryptedDekSize = buffer.getInt();
/* 175 */       if (encryptedDekSize <= 0 || encryptedDekSize > 4096 || encryptedDekSize > ciphertext.length - 4)
/*     */       {
/*     */         
/* 178 */         throw new GeneralSecurityException("length of encrypted DEK too large");
/*     */       }
/* 180 */       byte[] encryptedDek = new byte[encryptedDekSize];
/* 181 */       buffer.get(encryptedDek, 0, encryptedDekSize);
/* 182 */       byte[] payload = new byte[buffer.remaining()];
/* 183 */       buffer.get(payload, 0, buffer.remaining());
/*     */       
/* 185 */       byte[] dek = this.remote.decrypt(encryptedDek, EMPTY_AAD);
/*     */ 
/*     */       
/* 188 */       ProtoKeySerialization serialization = ProtoKeySerialization.create(this.typeUrlForParsing, 
/*     */           
/* 190 */           ByteString.copyFrom(dek), KeyData.KeyMaterialType.SYMMETRIC, OutputPrefixType.RAW, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 196 */       Key key = MutableSerializationRegistry.globalInstance().parseKey((Serialization)serialization, InsecureSecretKeyAccess.get());
/*     */       
/* 198 */       Aead aead = (Aead)MutablePrimitiveRegistry.globalInstance().getPrimitive(key, Aead.class);
/* 199 */       return aead.decrypt(payload, associatedData);
/* 200 */     } catch (IndexOutOfBoundsException|java.nio.BufferUnderflowException|NegativeArraySizeException e) {
/*     */ 
/*     */       
/* 203 */       throw new GeneralSecurityException("invalid ciphertext", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] buildCiphertext(byte[] encryptedDek, byte[] payload) {
/* 208 */     return ByteBuffer.allocate(4 + encryptedDek.length + payload.length)
/* 209 */       .putInt(encryptedDek.length)
/* 210 */       .put(encryptedDek)
/* 211 */       .put(payload)
/* 212 */       .array();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\KmsEnvelopeAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */