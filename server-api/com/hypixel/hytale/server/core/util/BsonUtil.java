/*     */ package com.hypixel.hytale.server.core.util;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonBinaryReader;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonValue;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.codecs.BsonDocumentCodec;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.io.BasicOutputBuffer;
/*     */ import org.bson.json.JsonWriter;
/*     */ import org.bson.json.JsonWriterSettings;
/*     */ import org.bson.json.StrictJsonWriter;
/*     */ 
/*     */ public class BsonUtil {
/*  37 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*     */   public static final JsonWriterSettings SETTINGS;
/*     */ 
/*     */   
/*     */   static {
/*  44 */     SETTINGS = JsonWriterSettings.builder().outputMode(JsonMode.STRICT).indent(true).newLineCharacters("\n").int64Converter((value, writer) -> writer.writeNumber(Long.toString(value.longValue()))).build();
/*     */   }
/*  46 */   private static final BsonDocumentCodec codec = new BsonDocumentCodec();
/*  47 */   private static final DecoderContext decoderContext = DecoderContext.builder().build();
/*  48 */   private static final EncoderContext encoderContext = EncoderContext.builder().build();
/*  49 */   public static final BsonDocumentCodec BSON_DOCUMENT_CODEC = new BsonDocumentCodec();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] writeToBytes(@Nullable BsonDocument document) {
/*  61 */     if (document == null) return ArrayUtil.EMPTY_BYTE_ARRAY; 
/*  62 */     BasicOutputBuffer buffer = new BasicOutputBuffer(); try {
/*  63 */       codec.encode((BsonWriter)new BsonBinaryWriter((BsonOutput)buffer), document, encoderContext);
/*  64 */       byte[] arrayOfByte = buffer.toByteArray();
/*  65 */       buffer.close();
/*     */       return arrayOfByte;
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         buffer.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static BsonDocument readFromBytes(@Nullable byte[] buf) {
/*  78 */     if (buf == null || buf.length == 0) return null; 
/*  79 */     return codec.decode((BsonReader)new BsonBinaryReader(ByteBuffer.wrap(buf)), decoderContext);
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
/*     */   public static BsonDocument readFromBuffer(@Nullable ByteBuffer buf) {
/*  92 */     if (buf == null || !buf.hasRemaining()) return null; 
/*  93 */     return codec.decode((BsonReader)new BsonBinaryReader(buf), decoderContext);
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
/*     */   public static BsonDocument readFromBinaryStream(@Nonnull ByteBuf buf) {
/* 106 */     return readFromBytes(ByteBufUtil.readByteArray(buf));
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
/*     */   public static void writeToBinaryStream(@Nonnull ByteBuf buf, BsonDocument doc) {
/* 119 */     ByteBufUtil.writeByteArray(buf, writeToBytes(doc));
/*     */   }
/*     */   @Nonnull
/*     */   public static CompletableFuture<Void> writeDocumentBytes(@Nonnull Path file, BsonDocument document) {
/*     */     try {
/*     */       byte[] bytes;
/* 125 */       if (Files.isRegularFile(file, new java.nio.file.LinkOption[0])) {
/* 126 */         Path resolve = file.resolveSibling(String.valueOf(file.getFileName()) + ".bak");
/* 127 */         Files.move(file, resolve, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*     */       } 
/*     */       
/* 130 */       BasicOutputBuffer bob = new BasicOutputBuffer(); 
/* 131 */       try { codec.encode((BsonWriter)new BsonBinaryWriter((BsonOutput)bob), document, encoderContext);
/* 132 */         bytes = bob.toByteArray();
/* 133 */         bob.close(); } catch (Throwable throwable) { try { bob.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 134 */        return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> Files.write(file, bytes, new OpenOption[0])));
/* 135 */     } catch (IOException e) {
/* 136 */       byte[] bytes; return CompletableFuture.failedFuture((Throwable)bytes);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<Void> writeDocument(@Nonnull Path file, BsonDocument document) {
/* 142 */     return writeDocument(file, document, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<Void> writeDocument(@Nonnull Path file, BsonDocument document, boolean backup) {
/*     */     try {
/* 148 */       Path parent = PathUtil.getParent(file);
/* 149 */       if (!Files.exists(parent, new java.nio.file.LinkOption[0])) {
/* 150 */         Files.createDirectories(parent, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/* 152 */       if (backup && Files.isRegularFile(file, new java.nio.file.LinkOption[0])) {
/* 153 */         Path resolve = file.resolveSibling(String.valueOf(file.getFileName()) + ".bak");
/* 154 */         Files.move(file, resolve, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*     */       } 
/* 156 */       String json = toJson(document);
/* 157 */       return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> Files.writeString(file, json, new OpenOption[0])));
/* 158 */     } catch (IOException e) {
/* 159 */       return CompletableFuture.failedFuture(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<BsonDocument> readDocument(@Nonnull Path file) {
/* 165 */     return readDocument(file, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<BsonDocument> readDocument(@Nonnull Path file, boolean backup) {
/*     */     BasicFileAttributes attributes;
/*     */     try {
/* 172 */       attributes = Files.readAttributes(file, BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/* 173 */     } catch (IOException ignored) {
/* 174 */       if (backup) return readDocumentBak(file);
/*     */       
/* 176 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 180 */     if (attributes.size() == 0L) {
/* 181 */       LOGGER.at(Level.WARNING).log("Error loading file %s, file was found to be entirely empty", file);
/* 182 */       if (backup) return readDocumentBak(file); 
/* 183 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 187 */     CompletableFuture<BsonDocument> future = CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> Files.readString(file))).thenApply(BsonDocument::parse);
/*     */     
/* 189 */     return backup ? future.exceptionallyCompose(t -> readDocumentBak(file)) : future;
/*     */   }
/*     */   @Nullable
/*     */   public static BsonDocument readDocumentNow(@Nonnull Path file) {
/*     */     BasicFileAttributes attributes;
/*     */     String contentsString;
/*     */     try {
/* 196 */       attributes = Files.readAttributes(file, BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/* 197 */     } catch (IOException e) {
/* 198 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log(ExceptionUtil.toStringWithStack(e));
/*     */       
/* 200 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     if (attributes.size() == 0L) {
/* 205 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 210 */       contentsString = Files.readString(file);
/* 211 */     } catch (IOException e) {
/* 212 */       return null;
/*     */     } 
/* 214 */     return BsonDocument.parse(contentsString);
/*     */   }
/*     */   @Nonnull
/*     */   public static CompletableFuture<BsonDocument> readDocumentBak(@Nonnull Path fileOrig) {
/*     */     BasicFileAttributes attributes;
/* 219 */     Path file = fileOrig.resolveSibling(String.valueOf(fileOrig.getFileName()) + ".bak");
/*     */ 
/*     */     
/*     */     try {
/* 223 */       attributes = Files.readAttributes(file, BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/* 224 */     } catch (IOException ignored) {
/*     */       
/* 226 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 230 */     if (attributes.size() == 0L) {
/*     */       
/* 232 */       LOGGER.at(Level.WARNING).log("Error loading backup file %s, file was found to be entirely empty", file);
/* 233 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 236 */     LOGGER.at(Level.WARNING).log("Loading %s backup file for %s!", file, fileOrig);
/* 237 */     return CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> Files.readString(file)))
/* 238 */       .thenApply(BsonDocument::parse);
/*     */   }
/*     */   
/*     */   public static BsonValue translateJsonToBson(@Nonnull JsonElement element) {
/* 242 */     if (element.isJsonObject()) {
/* 243 */       return (BsonValue)BsonDocument.parse(element.toString());
/*     */     }
/*     */     
/* 246 */     return (BsonValue)new BsonString(element.getAsString());
/*     */   }
/*     */   public static JsonElement translateBsonToJson(BsonDocument value) {
/*     */     
/* 250 */     try { StringWriter writer = new StringWriter(); 
/* 251 */       try { codec.encode((BsonWriter)new JsonWriter(writer, SETTINGS), value, encoderContext);
/* 252 */         JsonElement jsonElement = JsonParser.parseString(writer.toString());
/* 253 */         writer.close(); return jsonElement; } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 254 */     { throw new RuntimeException(e); }
/*     */   
/*     */   }
/*     */   
/*     */   public static String toJson(BsonDocument document) {
/* 259 */     StringWriter writer = new StringWriter();
/* 260 */     BSON_DOCUMENT_CODEC.encode((BsonWriter)new JsonWriter(writer, SETTINGS), document, encoderContext);
/* 261 */     return writer.toString();
/*     */   }
/*     */   
/*     */   public static <T> void writeSync(@Nonnull Path path, @Nonnull Codec<T> codec, T value, @Nonnull HytaleLogger logger) throws IOException {
/* 265 */     Path parent = PathUtil.getParent(path);
/* 266 */     if (!Files.exists(parent, new java.nio.file.LinkOption[0])) Files.createDirectories(parent, (FileAttribute<?>[])new FileAttribute[0]);
/*     */ 
/*     */     
/* 269 */     if (Files.isRegularFile(path, new java.nio.file.LinkOption[0])) {
/* 270 */       Path resolve = path.resolveSibling(String.valueOf(path.getFileName()) + ".bak");
/* 271 */       Files.move(path, resolve, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*     */     } 
/*     */ 
/*     */     
/* 275 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 276 */     BsonValue bsonValue = codec.encode(value, extraInfo);
/* 277 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(logger);
/*     */     
/* 279 */     BsonDocument document = bsonValue.asDocument();
/* 280 */     BufferedWriter writer = Files.newBufferedWriter(path, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.CREATE }); try {
/* 281 */       BSON_DOCUMENT_CODEC.encode((BsonWriter)new JsonWriter(writer, SETTINGS), document, encoderContext);
/* 282 */       if (writer != null) writer.close(); 
/*     */     } catch (Throwable throwable) {
/*     */       if (writer != null)
/*     */         try {
/*     */           writer.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\BsonUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */