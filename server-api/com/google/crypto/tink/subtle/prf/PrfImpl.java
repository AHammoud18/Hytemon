/*    */ package com.google.crypto.tink.subtle.prf;
/*    */ 
/*    */ import com.google.crypto.tink.prf.Prf;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.security.GeneralSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class PrfImpl
/*    */   implements Prf
/*    */ {
/*    */   private final StreamingPrf prfStreamer;
/*    */   
/*    */   private PrfImpl(StreamingPrf prfStreamer) {
/* 30 */     this.prfStreamer = prfStreamer;
/*    */   }
/*    */ 
/*    */   
/*    */   public static PrfImpl wrap(StreamingPrf prfStreamer) {
/* 35 */     return new PrfImpl(prfStreamer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static byte[] readBytesFromStream(InputStream stream, int outputLength) throws GeneralSecurityException {
/*    */     try {
/* 42 */       byte[] output = new byte[outputLength];
/* 43 */       int offset = 0;
/* 44 */       while (offset < outputLength) {
/* 45 */         int bytesRead = stream.read(output, offset, outputLength - offset);
/* 46 */         if (bytesRead <= 0) {
/* 47 */           throw new GeneralSecurityException("Provided StreamingPrf terminated before providing requested number of bytes.");
/*    */         }
/*    */         
/* 50 */         offset += bytesRead;
/*    */       } 
/* 52 */       return output;
/* 53 */     } catch (IOException exception) {
/* 54 */       throw new GeneralSecurityException(exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] compute(byte[] input, int outputLength) throws GeneralSecurityException {
/* 60 */     if (input == null) {
/* 61 */       throw new GeneralSecurityException("Invalid input provided.");
/*    */     }
/* 63 */     if (outputLength <= 0) {
/* 64 */       throw new GeneralSecurityException("Invalid outputLength specified.");
/*    */     }
/* 66 */     InputStream prfStream = this.prfStreamer.computePrf(input);
/* 67 */     return readBytesFromStream(prfStream, outputLength);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\prf\PrfImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */