/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import java.util.function.BiConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Http3HeadersSink
/*     */   implements BiConsumer<CharSequence, CharSequence>
/*     */ {
/*     */   private final Http3Headers headers;
/*     */   private final long maxHeaderListSize;
/*     */   private final boolean validate;
/*     */   private final boolean trailer;
/*     */   private long headersLength;
/*     */   private boolean exceededMaxLength;
/*     */   private Http3HeadersValidationException validationException;
/*     */   private HeaderType previousType;
/*     */   private boolean request;
/*     */   private int receivedPseudoHeaders;
/*     */   
/*     */   Http3HeadersSink(Http3Headers headers, long maxHeaderListSize, boolean validate, boolean trailer) {
/*  49 */     this.headers = headers;
/*  50 */     this.maxHeaderListSize = maxHeaderListSize;
/*  51 */     this.validate = validate;
/*  52 */     this.trailer = trailer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void finish() throws Http3HeadersValidationException, Http3Exception {
/*  59 */     if (this.exceededMaxLength) {
/*  60 */       throw new Http3Exception(Http3ErrorCode.H3_EXCESSIVE_LOAD, 
/*  61 */           String.format("Header size exceeded max allowed size (%d)", new Object[] { Long.valueOf(this.maxHeaderListSize) }));
/*     */     }
/*  63 */     if (this.validationException != null) {
/*  64 */       throw this.validationException;
/*     */     }
/*  66 */     if (this.validate) {
/*  67 */       if (this.trailer) {
/*  68 */         if (this.receivedPseudoHeaders != 0)
/*     */         {
/*  70 */           throw new Http3HeadersValidationException("Pseudo-header(s) included in trailers.");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  76 */       if (this.request) {
/*  77 */         CharSequence method = this.headers.method();
/*     */         
/*  79 */         if (HttpMethod.CONNECT.asciiName().contentEqualsIgnoreCase(method)) {
/*     */ 
/*     */           
/*  82 */           if ((this.receivedPseudoHeaders & Http3Headers.PseudoHeaderName.PROTOCOL.getFlag()) != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  90 */             int requiredPseudoHeaders = Http3Headers.PseudoHeaderName.METHOD.getFlag() | Http3Headers.PseudoHeaderName.SCHEME.getFlag() | Http3Headers.PseudoHeaderName.AUTHORITY.getFlag() | Http3Headers.PseudoHeaderName.PATH.getFlag() | Http3Headers.PseudoHeaderName.PROTOCOL.getFlag();
/*  91 */             if (this.receivedPseudoHeaders != requiredPseudoHeaders) {
/*  92 */               throw new Http3HeadersValidationException("Not all mandatory pseudo-headers included for Extended CONNECT.");
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */           else {
/*     */             
/*  99 */             int requiredPseudoHeaders = Http3Headers.PseudoHeaderName.METHOD.getFlag() | Http3Headers.PseudoHeaderName.AUTHORITY.getFlag();
/* 100 */             if (this.receivedPseudoHeaders != requiredPseudoHeaders) {
/* 101 */               throw new Http3HeadersValidationException("Not all mandatory pseudo-headers included.");
/*     */             }
/*     */           } 
/* 104 */         } else if (HttpMethod.OPTIONS.asciiName().contentEqualsIgnoreCase(method)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 113 */           int requiredPseudoHeaders = Http3Headers.PseudoHeaderName.METHOD.getFlag() | Http3Headers.PseudoHeaderName.SCHEME.getFlag() | Http3Headers.PseudoHeaderName.PATH.getFlag();
/* 114 */           if ((this.receivedPseudoHeaders & requiredPseudoHeaders) != requiredPseudoHeaders || (
/* 115 */             !authorityOrHostHeaderReceived() && !"*".contentEquals(this.headers.path()))) {
/* 116 */             throw new Http3HeadersValidationException("Not all mandatory pseudo-headers included.");
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 124 */           int requiredPseudoHeaders = Http3Headers.PseudoHeaderName.METHOD.getFlag() | Http3Headers.PseudoHeaderName.SCHEME.getFlag() | Http3Headers.PseudoHeaderName.PATH.getFlag();
/* 125 */           if ((this.receivedPseudoHeaders & requiredPseudoHeaders) != requiredPseudoHeaders || 
/* 126 */             !authorityOrHostHeaderReceived()) {
/* 127 */             throw new Http3HeadersValidationException("Not all mandatory pseudo-headers included.");
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 133 */       else if (this.receivedPseudoHeaders != Http3Headers.PseudoHeaderName.STATUS.getFlag()) {
/* 134 */         throw new Http3HeadersValidationException("Not all mandatory pseudo-headers included.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean authorityOrHostHeaderReceived() {
/* 146 */     return ((this.receivedPseudoHeaders & Http3Headers.PseudoHeaderName.AUTHORITY.getFlag()) == Http3Headers.PseudoHeaderName.AUTHORITY.getFlag() || this.headers
/* 147 */       .contains(HttpHeaderNames.HOST));
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(CharSequence name, CharSequence value) {
/* 152 */     this.headersLength += QpackHeaderField.sizeOf(name, value);
/* 153 */     this.exceededMaxLength |= (this.headersLength > this.maxHeaderListSize) ? 1 : 0;
/*     */     
/* 155 */     if (this.exceededMaxLength || this.validationException != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 160 */     if (this.validate) {
/*     */       try {
/* 162 */         validate(this.headers, name);
/* 163 */       } catch (Http3HeadersValidationException ex) {
/* 164 */         this.validationException = ex;
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/* 169 */     this.headers.add(name, value);
/*     */   }
/*     */   
/*     */   private void validate(Http3Headers headers, CharSequence name) {
/* 173 */     if (Http3Headers.PseudoHeaderName.hasPseudoHeaderFormat(name)) {
/* 174 */       if (this.previousType == HeaderType.REGULAR_HEADER) {
/* 175 */         throw new Http3HeadersValidationException(
/* 176 */             String.format("Pseudo-header field '%s' found after regular header.", new Object[] { name }));
/*     */       }
/*     */       
/* 179 */       Http3Headers.PseudoHeaderName pseudoHeader = Http3Headers.PseudoHeaderName.getPseudoHeader(name);
/* 180 */       if (pseudoHeader == null) {
/* 181 */         throw new Http3HeadersValidationException(
/* 182 */             String.format("Invalid HTTP/3 pseudo-header '%s' encountered.", new Object[] { name }));
/*     */       }
/* 184 */       if ((this.receivedPseudoHeaders & pseudoHeader.getFlag()) != 0)
/*     */       {
/* 186 */         throw new Http3HeadersValidationException(
/* 187 */             String.format("Pseudo-header field '%s' exists already.", new Object[] { name }));
/*     */       }
/* 189 */       this.receivedPseudoHeaders |= pseudoHeader.getFlag();
/*     */ 
/*     */       
/* 192 */       HeaderType currentHeaderType = pseudoHeader.isRequestOnly() ? HeaderType.REQUEST_PSEUDO_HEADER : HeaderType.RESPONSE_PSEUDO_HEADER;
/* 193 */       this.request = pseudoHeader.isRequestOnly();
/* 194 */       this.previousType = currentHeaderType;
/*     */     } else {
/* 196 */       this.previousType = HeaderType.REGULAR_HEADER;
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum HeaderType {
/* 201 */     REGULAR_HEADER,
/* 202 */     REQUEST_PSEUDO_HEADER,
/* 203 */     RESPONSE_PSEUDO_HEADER;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3HeadersSink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */