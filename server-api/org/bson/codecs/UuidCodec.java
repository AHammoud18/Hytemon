/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import org.bson.BSONException;
/*     */ import org.bson.BsonBinary;
/*     */ import org.bson.BsonBinarySubType;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.UuidRepresentation;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
/*     */ import org.bson.internal.UuidHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UuidCodec
/*     */   implements Codec<UUID>
/*     */ {
/*     */   private final UuidRepresentation uuidRepresentation;
/*     */   
/*     */   public UuidCodec(UuidRepresentation uuidRepresentation) {
/*  48 */     Assertions.notNull("uuidRepresentation", uuidRepresentation);
/*  49 */     this.uuidRepresentation = uuidRepresentation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UuidCodec() {
/*  56 */     this.uuidRepresentation = UuidRepresentation.UNSPECIFIED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UuidRepresentation getUuidRepresentation() {
/*  66 */     return this.uuidRepresentation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, UUID value, EncoderContext encoderContext) {
/*  71 */     if (this.uuidRepresentation == UuidRepresentation.UNSPECIFIED) {
/*  72 */       throw new CodecConfigurationException("The uuidRepresentation has not been specified, so the UUID cannot be encoded.");
/*     */     }
/*  74 */     byte[] binaryData = UuidHelper.encodeUuidToBinary(value, this.uuidRepresentation);
/*     */     
/*  76 */     if (this.uuidRepresentation == UuidRepresentation.STANDARD) {
/*  77 */       writer.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_STANDARD, binaryData));
/*     */     } else {
/*  79 */       writer.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_LEGACY, binaryData));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID decode(BsonReader reader, DecoderContext decoderContext) {
/*  85 */     byte subType = reader.peekBinarySubType();
/*     */     
/*  87 */     if (subType != BsonBinarySubType.UUID_LEGACY.getValue() && subType != BsonBinarySubType.UUID_STANDARD.getValue()) {
/*  88 */       throw new BSONException("Unexpected BsonBinarySubType");
/*     */     }
/*     */     
/*  91 */     byte[] bytes = reader.readBinaryData().getData();
/*     */     
/*  93 */     return UuidHelper.decodeBinaryToUuid(bytes, subType, this.uuidRepresentation);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<UUID> getEncoderClass() {
/*  98 */     return UUID.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return "UuidCodec{uuidRepresentation=" + this.uuidRepresentation + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\UuidCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */