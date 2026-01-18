/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpUtil
/*     */ {
/*  40 */   private static final AsciiString CHARSET_EQUALS = AsciiString.of(HttpHeaderValues.CHARSET + "=");
/*  41 */   private static final AsciiString SEMICOLON = AsciiString.cached(";");
/*  42 */   private static final String COMMA_STRING = String.valueOf(',');
/*     */ 
/*     */   
/*     */   private static final long TOKEN_CHARS_HIGH = 6341068274398134270L;
/*     */   
/*     */   private static final long TOKEN_CHARS_LOW = 288068722172624896L;
/*     */ 
/*     */   
/*     */   public static boolean isOriginForm(URI uri) {
/*  51 */     return isOriginForm(uri.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOriginForm(String uri) {
/*  59 */     return uri.startsWith("/");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAsteriskForm(URI uri) {
/*  67 */     return isAsteriskForm(uri.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAsteriskForm(String uri) {
/*  75 */     return "*".equals(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void validateRequestLineTokens(HttpMethod method, String uri) {
/*  81 */     if (method.getClass() != HttpMethod.class && 
/*  82 */       !isEncodingSafeStartLineToken((CharSequence)method.asciiName())) {
/*  83 */       throw new IllegalArgumentException("The HTTP method name contain illegal characters: " + method
/*  84 */           .asciiName());
/*     */     }
/*     */ 
/*     */     
/*  88 */     if (!isEncodingSafeStartLineToken(uri)) {
/*  89 */       throw new IllegalArgumentException("The URI contain illegal characters: " + uri);
/*     */     }
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
/*     */   public static boolean isEncodingSafeStartLineToken(CharSequence token) {
/* 106 */     int lenBytes = token.length();
/* 107 */     for (int i = 0; i < lenBytes; i++) {
/* 108 */       char ch = token.charAt(i);
/*     */       
/* 110 */       if (ch <= ' ') {
/* 111 */         switch (ch) {
/*     */           case '\n':
/*     */           case '\r':
/*     */           case ' ':
/* 115 */             return false;
/*     */         } 
/*     */       }
/*     */     } 
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isKeepAlive(HttpMessage message) {
/* 129 */     return (!message.headers().containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.CLOSE, true) && (message
/* 130 */       .protocolVersion().isKeepAliveDefault() || message
/* 131 */       .headers().containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.KEEP_ALIVE, true)));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
/* 155 */     setKeepAlive(message.headers(), message.protocolVersion(), keepAlive);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setKeepAlive(HttpHeaders h, HttpVersion httpVersion, boolean keepAlive) {
/* 178 */     if (httpVersion.isKeepAliveDefault()) {
/* 179 */       if (keepAlive) {
/* 180 */         h.remove((CharSequence)HttpHeaderNames.CONNECTION);
/*     */       } else {
/* 182 */         h.set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
/*     */       }
/*     */     
/* 185 */     } else if (keepAlive) {
/* 186 */       h.set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
/*     */     } else {
/* 188 */       h.remove((CharSequence)HttpHeaderNames.CONNECTION);
/*     */     } 
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
/*     */   public static long getContentLength(HttpMessage message) {
/* 206 */     String value = message.headers().get((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/* 207 */     if (value != null) {
/* 208 */       return Long.parseLong(value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 213 */     long webSocketContentLength = getWebSocketContentLength(message);
/* 214 */     if (webSocketContentLength >= 0L) {
/* 215 */       return webSocketContentLength;
/*     */     }
/*     */ 
/*     */     
/* 219 */     throw new NumberFormatException("header not found: " + HttpHeaderNames.CONTENT_LENGTH);
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
/*     */   public static long getContentLength(HttpMessage message, long defaultValue) {
/* 233 */     String value = message.headers().get((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/* 234 */     if (value != null) {
/* 235 */       return Long.parseLong(value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 240 */     long webSocketContentLength = getWebSocketContentLength(message);
/* 241 */     if (webSocketContentLength >= 0L) {
/* 242 */       return webSocketContentLength;
/*     */     }
/*     */ 
/*     */     
/* 246 */     return defaultValue;
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
/*     */   public static int getContentLength(HttpMessage message, int defaultValue) {
/* 258 */     return (int)Math.min(2147483647L, getContentLength(message, defaultValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getWebSocketContentLength(HttpMessage message) {
/* 267 */     HttpHeaders h = message.headers();
/* 268 */     if (message instanceof HttpRequest) {
/* 269 */       HttpRequest req = (HttpRequest)message;
/* 270 */       if (HttpMethod.GET.equals(req.method()) && h
/* 271 */         .contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY1) && h
/* 272 */         .contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY2)) {
/* 273 */         return 8;
/*     */       }
/* 275 */     } else if (message instanceof HttpResponse) {
/* 276 */       HttpResponse res = (HttpResponse)message;
/* 277 */       if (res.status().code() == 101 && h
/* 278 */         .contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN) && h
/* 279 */         .contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_LOCATION)) {
/* 280 */         return 16;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 285 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setContentLength(HttpMessage message, long length) {
/* 292 */     message.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, Long.valueOf(length));
/*     */   }
/*     */   
/*     */   public static boolean isContentLengthSet(HttpMessage m) {
/* 296 */     return m.headers().contains((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
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
/*     */   public static boolean is100ContinueExpected(HttpMessage message) {
/* 309 */     return (isExpectHeaderValid(message) && message
/*     */       
/* 311 */       .headers().contains((CharSequence)HttpHeaderNames.EXPECT, (CharSequence)HttpHeaderValues.CONTINUE, true));
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
/*     */   static boolean isUnsupportedExpectation(HttpMessage message) {
/* 323 */     if (!isExpectHeaderValid(message)) {
/* 324 */       return false;
/*     */     }
/*     */     
/* 327 */     String expectValue = message.headers().get((CharSequence)HttpHeaderNames.EXPECT);
/* 328 */     return (expectValue != null && !HttpHeaderValues.CONTINUE.toString().equalsIgnoreCase(expectValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isExpectHeaderValid(HttpMessage message) {
/* 337 */     return (message instanceof HttpRequest && message
/* 338 */       .protocolVersion().compareTo(HttpVersion.HTTP_1_1) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void set100ContinueExpected(HttpMessage message, boolean expected) {
/* 349 */     if (expected) {
/* 350 */       message.headers().set((CharSequence)HttpHeaderNames.EXPECT, HttpHeaderValues.CONTINUE);
/*     */     } else {
/* 352 */       message.headers().remove((CharSequence)HttpHeaderNames.EXPECT);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTransferEncodingChunked(HttpMessage message) {
/* 363 */     return message.headers().containsValue((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (CharSequence)HttpHeaderValues.CHUNKED, true);
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
/*     */   public static void setTransferEncodingChunked(HttpMessage m, boolean chunked) {
/* 375 */     if (chunked) {
/* 376 */       m.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
/* 377 */       m.headers().remove((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/*     */     } else {
/* 379 */       List<String> encodings = m.headers().getAll((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/* 380 */       if (encodings.isEmpty()) {
/*     */         return;
/*     */       }
/* 383 */       List<CharSequence> values = new ArrayList<>((Collection)encodings);
/* 384 */       Iterator<CharSequence> valuesIt = values.iterator();
/* 385 */       while (valuesIt.hasNext()) {
/* 386 */         CharSequence value = valuesIt.next();
/* 387 */         if (HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase(value)) {
/* 388 */           valuesIt.remove();
/*     */         }
/*     */       } 
/* 391 */       if (values.isEmpty()) {
/* 392 */         m.headers().remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/*     */       } else {
/* 394 */         m.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, values);
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
/*     */   
/*     */   public static Charset getCharset(HttpMessage message) {
/* 407 */     return getCharset(message, CharsetUtil.ISO_8859_1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Charset getCharset(CharSequence contentTypeValue) {
/* 418 */     if (contentTypeValue != null) {
/* 419 */       return getCharset(contentTypeValue, CharsetUtil.ISO_8859_1);
/*     */     }
/* 421 */     return CharsetUtil.ISO_8859_1;
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
/*     */   public static Charset getCharset(HttpMessage message, Charset defaultCharset) {
/* 434 */     CharSequence contentTypeValue = message.headers().get((CharSequence)HttpHeaderNames.CONTENT_TYPE);
/* 435 */     if (contentTypeValue != null) {
/* 436 */       return getCharset(contentTypeValue, defaultCharset);
/*     */     }
/* 438 */     return defaultCharset;
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
/*     */   public static Charset getCharset(CharSequence contentTypeValue, Charset defaultCharset) {
/* 451 */     if (contentTypeValue != null) {
/* 452 */       CharSequence charsetRaw = getCharsetAsSequence(contentTypeValue);
/* 453 */       if (charsetRaw != null) {
/* 454 */         if (charsetRaw.length() > 2 && 
/* 455 */           charsetRaw.charAt(0) == '"' && charsetRaw.charAt(charsetRaw.length() - 1) == '"') {
/* 456 */           charsetRaw = charsetRaw.subSequence(1, charsetRaw.length() - 1);
/*     */         }
/*     */         
/*     */         try {
/* 460 */           return Charset.forName(charsetRaw.toString());
/* 461 */         } catch (IllegalCharsetNameException|java.nio.charset.UnsupportedCharsetException illegalCharsetNameException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 466 */     return defaultCharset;
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
/*     */   @Deprecated
/*     */   public static CharSequence getCharsetAsString(HttpMessage message) {
/* 482 */     return getCharsetAsSequence(message);
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
/*     */   public static CharSequence getCharsetAsSequence(HttpMessage message) {
/* 495 */     CharSequence contentTypeValue = message.headers().get((CharSequence)HttpHeaderNames.CONTENT_TYPE);
/* 496 */     if (contentTypeValue != null) {
/* 497 */       return getCharsetAsSequence(contentTypeValue);
/*     */     }
/* 499 */     return null;
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
/*     */   public static CharSequence getCharsetAsSequence(CharSequence contentTypeValue) {
/* 515 */     ObjectUtil.checkNotNull(contentTypeValue, "contentTypeValue");
/*     */     
/* 517 */     int indexOfCharset = AsciiString.indexOfIgnoreCaseAscii(contentTypeValue, (CharSequence)CHARSET_EQUALS, 0);
/* 518 */     if (indexOfCharset == -1) {
/* 519 */       return null;
/*     */     }
/*     */     
/* 522 */     int indexOfEncoding = indexOfCharset + CHARSET_EQUALS.length();
/* 523 */     if (indexOfEncoding < contentTypeValue.length()) {
/* 524 */       CharSequence charsetCandidate = contentTypeValue.subSequence(indexOfEncoding, contentTypeValue.length());
/* 525 */       int indexOfSemicolon = AsciiString.indexOfIgnoreCaseAscii(charsetCandidate, (CharSequence)SEMICOLON, 0);
/* 526 */       if (indexOfSemicolon == -1) {
/* 527 */         return charsetCandidate;
/*     */       }
/*     */       
/* 530 */       return charsetCandidate.subSequence(0, indexOfSemicolon);
/*     */     } 
/*     */     
/* 533 */     return null;
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
/*     */   public static CharSequence getMimeType(HttpMessage message) {
/* 548 */     CharSequence contentTypeValue = message.headers().get((CharSequence)HttpHeaderNames.CONTENT_TYPE);
/* 549 */     if (contentTypeValue != null) {
/* 550 */       return getMimeType(contentTypeValue);
/*     */     }
/* 552 */     return null;
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
/*     */   public static CharSequence getMimeType(CharSequence contentTypeValue) {
/* 569 */     ObjectUtil.checkNotNull(contentTypeValue, "contentTypeValue");
/*     */     
/* 571 */     int indexOfSemicolon = AsciiString.indexOfIgnoreCaseAscii(contentTypeValue, (CharSequence)SEMICOLON, 0);
/* 572 */     if (indexOfSemicolon != -1) {
/* 573 */       return contentTypeValue.subSequence(0, indexOfSemicolon);
/*     */     }
/* 575 */     return (contentTypeValue.length() > 0) ? contentTypeValue : null;
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
/*     */   public static String formatHostnameForHttp(InetSocketAddress addr) {
/* 587 */     String hostString = NetUtil.getHostname(addr);
/* 588 */     if (NetUtil.isValidIpV6Address(hostString)) {
/* 589 */       if (!addr.isUnresolved()) {
/* 590 */         hostString = NetUtil.toAddressString(addr.getAddress());
/* 591 */       } else if (hostString.charAt(0) == '[' && hostString.charAt(hostString.length() - 1) == ']') {
/*     */         
/* 593 */         return hostString;
/*     */       } 
/*     */       
/* 596 */       return '[' + hostString + ']';
/*     */     } 
/* 598 */     return hostString;
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
/*     */   public static long normalizeAndGetContentLength(List<? extends CharSequence> contentLengthFields, boolean isHttp10OrEarlier, boolean allowDuplicateContentLengths) {
/* 614 */     if (contentLengthFields.isEmpty()) {
/* 615 */       return -1L;
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
/* 631 */     String firstField = ((CharSequence)contentLengthFields.get(0)).toString();
/*     */     
/* 633 */     boolean multipleContentLengths = (contentLengthFields.size() > 1 || firstField.indexOf(',') >= 0);
/*     */     
/* 635 */     if (multipleContentLengths && !isHttp10OrEarlier) {
/* 636 */       if (allowDuplicateContentLengths) {
/*     */         
/* 638 */         String firstValue = null;
/* 639 */         for (CharSequence field : contentLengthFields) {
/* 640 */           String[] tokens = field.toString().split(COMMA_STRING, -1);
/* 641 */           for (String token : tokens) {
/* 642 */             String trimmed = token.trim();
/* 643 */             if (firstValue == null) {
/* 644 */               firstValue = trimmed;
/* 645 */             } else if (!trimmed.equals(firstValue)) {
/* 646 */               throw new IllegalArgumentException("Multiple Content-Length values found: " + contentLengthFields);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 652 */         firstField = firstValue;
/*     */       } else {
/*     */         
/* 655 */         throw new IllegalArgumentException("Multiple Content-Length values found: " + contentLengthFields);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 661 */     if (firstField.isEmpty() || !Character.isDigit(firstField.charAt(0)))
/*     */     {
/* 663 */       throw new IllegalArgumentException("Content-Length value is not a number: " + firstField);
/*     */     }
/*     */     
/*     */     try {
/* 667 */       long value = Long.parseLong(firstField);
/* 668 */       return ObjectUtil.checkPositiveOrZero(value, "Content-Length value");
/* 669 */     } catch (NumberFormatException e) {
/*     */       
/* 671 */       throw new IllegalArgumentException("Content-Length value is not a number: " + firstField, e);
/*     */     } 
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
/*     */   static int validateToken(CharSequence token) {
/* 690 */     if (token instanceof AsciiString) {
/* 691 */       return validateAsciiStringToken((AsciiString)token);
/*     */     }
/* 693 */     return validateCharSequenceToken(token);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int validateAsciiStringToken(AsciiString token) {
/* 703 */     byte[] array = token.array();
/* 704 */     for (int i = token.arrayOffset(), len = token.arrayOffset() + token.length(); i < len; i++) {
/* 705 */       if (!isValidTokenChar(array[i])) {
/* 706 */         return i - token.arrayOffset();
/*     */       }
/*     */     } 
/* 709 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int validateCharSequenceToken(CharSequence token) {
/* 719 */     for (int i = 0, len = token.length(); i < len; i++) {
/* 720 */       byte value = (byte)token.charAt(i);
/* 721 */       if (!isValidTokenChar(value)) {
/* 722 */         return i;
/*     */       }
/*     */     } 
/* 725 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidTokenChar(byte bit) {
/* 810 */     if (bit < 0) {
/* 811 */       return false;
/*     */     }
/* 813 */     if (bit < 64) {
/* 814 */       return (0L != (0x3FF6CFA00000000L & 1L << bit));
/*     */     }
/* 816 */     return (0L != (0x57FFFFFFC7FFFFFEL & 1L << bit - 64));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */