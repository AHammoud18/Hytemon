/*     */ package io.sentry;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class EnvelopeReader
/*     */   implements IEnvelopeReader
/*     */ {
/*  19 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   @NotNull
/*     */   private final ISerializer serializer;
/*     */   
/*     */   public EnvelopeReader(@NotNull ISerializer serializer) {
/*  24 */     this.serializer = serializer;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryEnvelope read(@NotNull InputStream stream) throws IOException {
/*  29 */     byte[] buffer = new byte[1024];
/*     */     
/*  31 */     int streamOffset = 0;
/*     */     
/*  33 */     int envelopeEndHeaderOffset = -1;
/*  34 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
/*  35 */     try { int currentLength; while ((currentLength = stream.read(buffer)) > 0) {
/*  36 */         for (int i = 0; envelopeEndHeaderOffset == -1 && i < currentLength; i++) {
/*  37 */           if (buffer[i] == 10) {
/*  38 */             envelopeEndHeaderOffset = streamOffset + i;
/*     */             break;
/*     */           } 
/*     */         } 
/*  42 */         outputStream.write(buffer, 0, currentLength);
/*  43 */         streamOffset += currentLength;
/*     */       } 
/*     */       
/*  46 */       byte[] envelopeBytes = outputStream.toByteArray();
/*     */       
/*  48 */       if (envelopeBytes.length == 0) {
/*  49 */         throw new IllegalArgumentException("Empty stream.");
/*     */       }
/*  51 */       if (envelopeEndHeaderOffset == -1) {
/*  52 */         throw new IllegalArgumentException("Envelope contains no header.");
/*     */       }
/*     */ 
/*     */       
/*  56 */       SentryEnvelopeHeader header = deserializeEnvelopeHeader(envelopeBytes, 0, envelopeEndHeaderOffset);
/*  57 */       if (header == null) {
/*  58 */         throw new IllegalArgumentException("Envelope header is null.");
/*     */       }
/*     */       
/*  61 */       int itemHeaderStartOffset = envelopeEndHeaderOffset + 1;
/*     */ 
/*     */       
/*  64 */       List<SentryEnvelopeItem> items = new ArrayList<>();
/*     */       while (true) {
/*  66 */         int lineBreakIndex = -1;
/*     */         
/*  68 */         for (int i = itemHeaderStartOffset; i < envelopeBytes.length; i++) {
/*  69 */           if (envelopeBytes[i] == 10) {
/*  70 */             lineBreakIndex = i;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  75 */         if (lineBreakIndex == -1) {
/*  76 */           throw new IllegalArgumentException("Invalid envelope. Item at index '" + items
/*     */               
/*  78 */               .size() + "'. has no header delimiter.");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         SentryEnvelopeItemHeader itemHeader = deserializeEnvelopeItemHeader(envelopeBytes, itemHeaderStartOffset, lineBreakIndex - itemHeaderStartOffset);
/*     */ 
/*     */         
/*  87 */         if (itemHeader == null || itemHeader.getLength() <= 0) {
/*  88 */           throw new IllegalArgumentException("Item header at index '" + items
/*  89 */               .size() + "' is null or empty.");
/*     */         }
/*     */         
/*  92 */         int payloadEndOffsetExclusive = lineBreakIndex + itemHeader.getLength() + 1;
/*  93 */         if (payloadEndOffsetExclusive > envelopeBytes.length) {
/*  94 */           throw new IllegalArgumentException("Invalid length for item at index '" + items
/*     */               
/*  96 */               .size() + "'. Item is '" + payloadEndOffsetExclusive + "' bytes. There are '" + envelopeBytes.length + "' in the buffer.");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 108 */         byte[] envelopeItemBytes = Arrays.copyOfRange(envelopeBytes, lineBreakIndex + 1, payloadEndOffsetExclusive);
/*     */ 
/*     */         
/* 111 */         SentryEnvelopeItem item = new SentryEnvelopeItem(itemHeader, envelopeItemBytes);
/* 112 */         items.add(item);
/*     */         
/* 114 */         if (payloadEndOffsetExclusive == envelopeBytes.length) {
/*     */           break;
/*     */         }
/* 117 */         if (payloadEndOffsetExclusive + 1 == envelopeBytes.length) {
/*     */           
/* 119 */           if (envelopeBytes[payloadEndOffsetExclusive] == 10) {
/*     */             break;
/*     */           }
/* 122 */           throw new IllegalArgumentException("Envelope has invalid data following an item.");
/*     */         } 
/*     */ 
/*     */         
/* 126 */         itemHeaderStartOffset = payloadEndOffsetExclusive + 1;
/*     */       } 
/*     */       
/* 129 */       SentryEnvelope sentryEnvelope = new SentryEnvelope(header, items);
/* 130 */       outputStream.close(); return sentryEnvelope; }
/*     */     catch (Throwable throwable) { try { outputStream.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 135 */      } @Nullable private SentryEnvelopeHeader deserializeEnvelopeHeader(@NotNull byte[] buffer, int offset, int length) { String json = new String(buffer, offset, length, UTF_8);
/* 136 */     StringReader reader = new StringReader(json); 
/* 137 */     try { SentryEnvelopeHeader sentryEnvelopeHeader = this.serializer.<SentryEnvelopeHeader>deserialize(reader, SentryEnvelopeHeader.class);
/* 138 */       reader.close(); return sentryEnvelopeHeader; }
/*     */     catch (Throwable throwable) { try { reader.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 143 */      } @Nullable private SentryEnvelopeItemHeader deserializeEnvelopeItemHeader(@NotNull byte[] buffer, int offset, int length) { String json = new String(buffer, offset, length, UTF_8);
/* 144 */     StringReader reader = new StringReader(json); try {
/* 145 */       SentryEnvelopeItemHeader sentryEnvelopeItemHeader = this.serializer.<SentryEnvelopeItemHeader>deserialize(reader, SentryEnvelopeItemHeader.class);
/* 146 */       reader.close();
/*     */       return sentryEnvelopeItemHeader;
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         reader.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\EnvelopeReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */