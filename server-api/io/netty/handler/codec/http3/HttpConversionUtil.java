/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.handler.codec.Headers;
/*     */ import io.netty.handler.codec.UnsupportedValueConverter;
/*     */ import io.netty.handler.codec.ValueConverter;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.DefaultHttpRequest;
/*     */ import io.netty.handler.codec.http.DefaultHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpScheme;
/*     */ import io.netty.handler.codec.http.HttpUtil;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.URI;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public final class HttpConversionUtil
/*     */ {
/*  72 */   private static final CharSequenceMap<AsciiString> HTTP_TO_HTTP3_HEADER_BLACKLIST = new CharSequenceMap<>();
/*     */   
/*     */   static {
/*  75 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(HttpHeaderNames.CONNECTION, AsciiString.EMPTY_STRING);
/*     */     
/*  77 */     AsciiString keepAlive = HttpHeaderNames.KEEP_ALIVE;
/*  78 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(keepAlive, AsciiString.EMPTY_STRING);
/*     */     
/*  80 */     AsciiString proxyConnection = HttpHeaderNames.PROXY_CONNECTION;
/*  81 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(proxyConnection, AsciiString.EMPTY_STRING);
/*  82 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(HttpHeaderNames.TRANSFER_ENCODING, AsciiString.EMPTY_STRING);
/*  83 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(HttpHeaderNames.HOST, AsciiString.EMPTY_STRING);
/*  84 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(HttpHeaderNames.UPGRADE, AsciiString.EMPTY_STRING);
/*  85 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(ExtensionHeaderNames.STREAM_ID.text(), AsciiString.EMPTY_STRING);
/*  86 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(ExtensionHeaderNames.SCHEME.text(), AsciiString.EMPTY_STRING);
/*  87 */     HTTP_TO_HTTP3_HEADER_BLACKLIST.add(ExtensionHeaderNames.PATH.text(), AsciiString.EMPTY_STRING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private static final AsciiString EMPTY_REQUEST_PATH = AsciiString.cached("/");
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
/*     */   public enum ExtensionHeaderNames
/*     */   {
/* 109 */     STREAM_ID("x-http3-stream-id"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     SCHEME("x-http3-scheme"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     PATH("x-http3-path"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     STREAM_PROMISE_ID("x-http3-stream-promise-id");
/*     */     
/*     */     private final AsciiString text;
/*     */     
/*     */     ExtensionHeaderNames(String text) {
/* 135 */       this.text = AsciiString.cached(text);
/*     */     }
/*     */     
/*     */     public AsciiString text() {
/* 139 */       return this.text;
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
/*     */   private static HttpResponseStatus parseStatus(long streamId, @Nullable CharSequence status) throws Http3Exception {
/*     */     HttpResponseStatus result;
/*     */     try {
/* 153 */       result = HttpResponseStatus.parseLine(status);
/* 154 */       if (result == HttpResponseStatus.SWITCHING_PROTOCOLS) {
/* 155 */         throw streamError(streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "Invalid HTTP/3 status code '" + status + "'", null);
/*     */       }
/*     */     }
/* 158 */     catch (Http3Exception e) {
/* 159 */       throw e;
/* 160 */     } catch (Throwable t) {
/* 161 */       throw streamError(streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "Unrecognized HTTP status code '" + status + "' encountered in translation to HTTP/1.x" + status, null);
/*     */     } 
/*     */     
/* 164 */     return result;
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
/*     */   static FullHttpResponse toFullHttpResponse(long streamId, Http3Headers http3Headers, ByteBufAllocator alloc, boolean validateHttpHeaders) throws Http3Exception {
/* 182 */     ByteBuf content = alloc.buffer();
/* 183 */     HttpResponseStatus status = parseStatus(streamId, http3Headers.status());
/*     */ 
/*     */     
/* 186 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content, validateHttpHeaders);
/*     */     
/*     */     try {
/* 189 */       addHttp3ToHttpHeaders(streamId, http3Headers, (FullHttpMessage)defaultFullHttpResponse, false);
/* 190 */     } catch (Http3Exception e) {
/* 191 */       defaultFullHttpResponse.release();
/* 192 */       throw e;
/* 193 */     } catch (Throwable t) {
/* 194 */       defaultFullHttpResponse.release();
/* 195 */       throw streamError(streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "HTTP/3 to HTTP/1.x headers conversion error", t);
/*     */     } 
/*     */     
/* 198 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */   
/*     */   private static CharSequence extractPath(CharSequence method, Http3Headers headers) {
/* 202 */     if (HttpMethod.CONNECT.asciiName().contentEqualsIgnoreCase(method))
/*     */     {
/* 204 */       return (CharSequence)ObjectUtil.checkNotNull(headers.authority(), "authority header cannot be null in the conversion to HTTP/1.x");
/*     */     }
/*     */     
/* 207 */     return (CharSequence)ObjectUtil.checkNotNull(headers.path(), "path header cannot be null in conversion to HTTP/1.x");
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
/*     */   static FullHttpRequest toFullHttpRequest(long streamId, Http3Headers http3Headers, ByteBufAllocator alloc, boolean validateHttpHeaders) throws Http3Exception {
/* 227 */     ByteBuf content = alloc.buffer();
/*     */     
/* 229 */     CharSequence method = (CharSequence)ObjectUtil.checkNotNull(http3Headers.method(), "method header cannot be null in conversion to HTTP/1.x");
/*     */     
/* 231 */     CharSequence path = extractPath(method, http3Headers);
/*     */     
/* 233 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(method.toString()), path.toString(), content, validateHttpHeaders);
/*     */     try {
/* 235 */       addHttp3ToHttpHeaders(streamId, http3Headers, (FullHttpMessage)defaultFullHttpRequest, false);
/* 236 */     } catch (Http3Exception e) {
/* 237 */       defaultFullHttpRequest.release();
/* 238 */       throw e;
/* 239 */     } catch (Throwable t) {
/* 240 */       defaultFullHttpRequest.release();
/* 241 */       throw streamError(streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "HTTP/3 to HTTP/1.x headers conversion error", t);
/*     */     } 
/*     */     
/* 244 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/*     */   static HttpRequest toHttpRequest(long streamId, Http3Headers http3Headers, boolean validateHttpHeaders) throws Http3Exception {
/* 262 */     CharSequence method = (CharSequence)ObjectUtil.checkNotNull(http3Headers.method(), "method header cannot be null in conversion to HTTP/1.x");
/*     */     
/* 264 */     CharSequence path = extractPath(method, http3Headers);
/*     */     
/* 266 */     DefaultHttpRequest defaultHttpRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(method.toString()), path.toString(), validateHttpHeaders);
/*     */     try {
/* 268 */       addHttp3ToHttpHeaders(streamId, http3Headers, defaultHttpRequest.headers(), defaultHttpRequest.protocolVersion(), false, true);
/* 269 */     } catch (Http3Exception e) {
/* 270 */       throw e;
/* 271 */     } catch (Throwable t) {
/* 272 */       throw streamError(streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "HTTP/3 to HTTP/1.x headers conversion error", t);
/*     */     } 
/*     */     
/* 275 */     return (HttpRequest)defaultHttpRequest;
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
/*     */   static HttpResponse toHttpResponse(long streamId, Http3Headers http3Headers, boolean validateHttpHeaders) throws Http3Exception {
/* 293 */     HttpResponseStatus status = parseStatus(streamId, http3Headers.status());
/*     */ 
/*     */     
/* 296 */     DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status, validateHttpHeaders);
/*     */     try {
/* 298 */       addHttp3ToHttpHeaders(streamId, http3Headers, defaultHttpResponse.headers(), defaultHttpResponse.protocolVersion(), false, false);
/* 299 */     } catch (Http3Exception e) {
/* 300 */       throw e;
/* 301 */     } catch (Throwable t) {
/* 302 */       throw streamError(streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "HTTP/3 to HTTP/1.x headers conversion error", t);
/*     */     } 
/*     */     
/* 305 */     return (HttpResponse)defaultHttpResponse;
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
/*     */   private static void addHttp3ToHttpHeaders(long streamId, Http3Headers inputHeaders, FullHttpMessage destinationMessage, boolean addToTrailer) throws Http3Exception {
/* 319 */     addHttp3ToHttpHeaders(streamId, inputHeaders, 
/* 320 */         addToTrailer ? destinationMessage.trailingHeaders() : destinationMessage.headers(), destinationMessage
/* 321 */         .protocolVersion(), addToTrailer, destinationMessage instanceof HttpRequest);
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
/*     */   static void addHttp3ToHttpHeaders(long streamId, Http3Headers inputHeaders, HttpHeaders outputHeaders, HttpVersion httpVersion, boolean isTrailer, boolean isRequest) throws Http3Exception {
/* 339 */     Http3ToHttpHeaderTranslator translator = new Http3ToHttpHeaderTranslator(streamId, outputHeaders, isRequest);
/*     */     try {
/* 341 */       translator.translateHeaders((Iterable<Map.Entry<CharSequence, CharSequence>>)inputHeaders);
/* 342 */     } catch (Http3Exception ex) {
/* 343 */       throw ex;
/* 344 */     } catch (Throwable t) {
/* 345 */       throw streamError(streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "HTTP/3 to HTTP/1.x headers conversion error", t);
/*     */     } 
/*     */ 
/*     */     
/* 349 */     outputHeaders.remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/* 350 */     outputHeaders.remove((CharSequence)HttpHeaderNames.TRAILER);
/* 351 */     if (!isTrailer) {
/* 352 */       outputHeaders.set((CharSequence)ExtensionHeaderNames.STREAM_ID.text(), Long.valueOf(streamId));
/* 353 */       HttpUtil.setKeepAlive(outputHeaders, httpVersion, true);
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
/*     */   static Http3Headers toHttp3Headers(HttpMessage in, boolean validateHeaders) {
/* 367 */     HttpHeaders inHeaders = in.headers();
/* 368 */     Http3Headers out = new DefaultHttp3Headers(validateHeaders, inHeaders.size());
/* 369 */     if (in instanceof HttpRequest) {
/* 370 */       HttpRequest request = (HttpRequest)in;
/* 371 */       URI requestTargetUri = URI.create(request.uri());
/* 372 */       out.path((CharSequence)toHttp3Path(requestTargetUri));
/* 373 */       out.method((CharSequence)request.method().asciiName());
/* 374 */       setHttp3Scheme(inHeaders, requestTargetUri, out);
/*     */ 
/*     */       
/* 377 */       String host = inHeaders.getAsString((CharSequence)HttpHeaderNames.HOST);
/* 378 */       if (host != null && !host.isEmpty()) {
/* 379 */         setHttp3Authority(host, out);
/*     */       }
/* 381 */       else if (!HttpUtil.isOriginForm(request.uri()) && !HttpUtil.isAsteriskForm(request.uri())) {
/* 382 */         setHttp3Authority(requestTargetUri.getAuthority(), out);
/*     */       }
/*     */     
/* 385 */     } else if (in instanceof HttpResponse) {
/* 386 */       HttpResponse response = (HttpResponse)in;
/* 387 */       out.status((CharSequence)response.status().codeAsText());
/*     */     } 
/*     */ 
/*     */     
/* 391 */     toHttp3Headers(inHeaders, out);
/* 392 */     return out;
/*     */   }
/*     */   
/*     */   static Http3Headers toHttp3Headers(HttpHeaders inHeaders, boolean validateHeaders) {
/* 396 */     if (inHeaders.isEmpty()) {
/* 397 */       return new DefaultHttp3Headers();
/*     */     }
/*     */     
/* 400 */     Http3Headers out = new DefaultHttp3Headers(validateHeaders, inHeaders.size());
/* 401 */     toHttp3Headers(inHeaders, out);
/* 402 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   private static CharSequenceMap<AsciiString> toLowercaseMap(Iterator<? extends CharSequence> valuesIter, int arraySizeHint) {
/* 407 */     UnsupportedValueConverter<AsciiString> valueConverter = UnsupportedValueConverter.instance();
/* 408 */     CharSequenceMap<AsciiString> result = new CharSequenceMap<>(true, (ValueConverter<AsciiString>)valueConverter, arraySizeHint);
/*     */     
/* 410 */     while (valuesIter.hasNext()) {
/* 411 */       AsciiString lowerCased = AsciiString.of(valuesIter.next()).toLowerCase();
/*     */       try {
/* 413 */         int index = lowerCased.forEachByte(ByteProcessor.FIND_COMMA);
/* 414 */         if (index != -1) {
/* 415 */           int start = 0;
/*     */           do {
/* 417 */             result.add(lowerCased.subSequence(start, index, false).trim(), AsciiString.EMPTY_STRING);
/* 418 */             start = index + 1;
/* 419 */           } while (start < lowerCased.length() && (
/* 420 */             index = lowerCased.forEachByte(start, lowerCased.length() - start, ByteProcessor.FIND_COMMA)) != -1);
/* 421 */           result.add(lowerCased.subSequence(start, lowerCased.length(), false).trim(), AsciiString.EMPTY_STRING); continue;
/*     */         } 
/* 423 */         result.add(lowerCased.trim(), AsciiString.EMPTY_STRING);
/*     */       }
/* 425 */       catch (Exception e) {
/*     */ 
/*     */         
/* 428 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     } 
/* 431 */     return result;
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
/*     */   private static void toHttp3HeadersFilterTE(Map.Entry<CharSequence, CharSequence> entry, Http3Headers out) {
/* 443 */     if (AsciiString.indexOf(entry.getValue(), ',', 0) == -1) {
/* 444 */       if (AsciiString.contentEqualsIgnoreCase(AsciiString.trim(entry.getValue()), (CharSequence)HttpHeaderValues.TRAILERS)) {
/* 445 */         out.add(HttpHeaderNames.TE, HttpHeaderValues.TRAILERS);
/*     */       }
/*     */     } else {
/* 448 */       List<CharSequence> teValues = StringUtil.unescapeCsvFields(entry.getValue());
/* 449 */       for (CharSequence teValue : teValues) {
/* 450 */         if (AsciiString.contentEqualsIgnoreCase(AsciiString.trim(teValue), (CharSequence)HttpHeaderValues.TRAILERS)) {
/* 451 */           out.add(HttpHeaderNames.TE, HttpHeaderValues.TRAILERS);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void toHttp3Headers(HttpHeaders inHeaders, Http3Headers out) {
/* 459 */     Iterator<Map.Entry<CharSequence, CharSequence>> iter = inHeaders.iteratorCharSequence();
/*     */ 
/*     */ 
/*     */     
/* 463 */     CharSequenceMap<AsciiString> connectionBlacklist = toLowercaseMap(inHeaders.valueCharSequenceIterator((CharSequence)HttpHeaderNames.CONNECTION), 8);
/* 464 */     while (iter.hasNext()) {
/* 465 */       Map.Entry<CharSequence, CharSequence> entry = iter.next();
/* 466 */       AsciiString aName = AsciiString.of(entry.getKey()).toLowerCase();
/* 467 */       if (!HTTP_TO_HTTP3_HEADER_BLACKLIST.contains(aName) && !connectionBlacklist.contains(aName)) {
/*     */ 
/*     */         
/* 470 */         if (aName.contentEqualsIgnoreCase((CharSequence)HttpHeaderNames.TE)) {
/* 471 */           toHttp3HeadersFilterTE(entry, out); continue;
/* 472 */         }  if (aName.contentEqualsIgnoreCase((CharSequence)HttpHeaderNames.COOKIE)) {
/* 473 */           AsciiString value = AsciiString.of(entry.getValue());
/*     */           
/*     */           try {
/* 476 */             int index = value.forEachByte(ByteProcessor.FIND_SEMI_COLON);
/* 477 */             if (index != -1) {
/* 478 */               int start = 0;
/*     */               do {
/* 480 */                 out.add(HttpHeaderNames.COOKIE, value.subSequence(start, index, false));
/*     */                 
/* 482 */                 start = index + 2;
/* 483 */               } while (start < value.length() && (
/* 484 */                 index = value.forEachByte(start, value.length() - start, ByteProcessor.FIND_SEMI_COLON)) != -1);
/* 485 */               if (start >= value.length()) {
/* 486 */                 throw new IllegalArgumentException("cookie value is of unexpected format: " + value);
/*     */               }
/* 488 */               out.add(HttpHeaderNames.COOKIE, value.subSequence(start, value.length(), false)); continue;
/*     */             } 
/* 490 */             out.add(HttpHeaderNames.COOKIE, value);
/*     */           }
/* 492 */           catch (Exception e) {
/*     */ 
/*     */             
/* 495 */             throw new IllegalStateException(e);
/*     */           }  continue;
/*     */         } 
/* 498 */         out.add(aName, entry.getValue());
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
/*     */   private static AsciiString toHttp3Path(URI uri) {
/* 510 */     StringBuilder pathBuilder = new StringBuilder(StringUtil.length(uri.getRawPath()) + StringUtil.length(uri.getRawQuery()) + StringUtil.length(uri.getRawFragment()) + 2);
/* 511 */     if (!StringUtil.isNullOrEmpty(uri.getRawPath())) {
/* 512 */       pathBuilder.append(uri.getRawPath());
/*     */     }
/* 514 */     if (!StringUtil.isNullOrEmpty(uri.getRawQuery())) {
/* 515 */       pathBuilder.append('?');
/* 516 */       pathBuilder.append(uri.getRawQuery());
/*     */     } 
/* 518 */     if (!StringUtil.isNullOrEmpty(uri.getRawFragment())) {
/* 519 */       pathBuilder.append('#');
/* 520 */       pathBuilder.append(uri.getRawFragment());
/*     */     } 
/* 522 */     String path = pathBuilder.toString();
/* 523 */     return path.isEmpty() ? EMPTY_REQUEST_PATH : new AsciiString(path);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void setHttp3Authority(@Nullable String authority, Http3Headers out) {
/* 529 */     if (authority != null) {
/* 530 */       if (authority.isEmpty()) {
/* 531 */         out.authority((CharSequence)AsciiString.EMPTY_STRING);
/*     */       } else {
/* 533 */         int start = authority.indexOf('@') + 1;
/* 534 */         int length = authority.length() - start;
/* 535 */         if (length == 0) {
/* 536 */           throw new IllegalArgumentException("authority: " + authority);
/*     */         }
/* 538 */         out.authority((CharSequence)new AsciiString(authority, start, length));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void setHttp3Scheme(HttpHeaders in, URI uri, Http3Headers out) {
/* 544 */     String value = uri.getScheme();
/* 545 */     if (value != null) {
/* 546 */       out.scheme((CharSequence)new AsciiString(value));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 551 */     CharSequence cValue = in.get((CharSequence)ExtensionHeaderNames.SCHEME.text());
/* 552 */     if (cValue != null) {
/* 553 */       out.scheme((CharSequence)AsciiString.of(cValue));
/*     */       
/*     */       return;
/*     */     } 
/* 557 */     if (uri.getPort() == HttpScheme.HTTPS.port()) {
/* 558 */       out.scheme((CharSequence)HttpScheme.HTTPS.name());
/* 559 */     } else if (uri.getPort() == HttpScheme.HTTP.port()) {
/* 560 */       out.scheme((CharSequence)HttpScheme.HTTP.name());
/*     */     } else {
/* 562 */       throw new IllegalArgumentException(":scheme must be specified. see https://quicwg.org/base-drafts/draft-ietf-quic-http.html#section-4.1.1.1");
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
/*     */   private static final class Http3ToHttpHeaderTranslator
/*     */   {
/* 575 */     private static final CharSequenceMap<AsciiString> REQUEST_HEADER_TRANSLATIONS = new CharSequenceMap<>();
/*     */     
/* 577 */     private static final CharSequenceMap<AsciiString> RESPONSE_HEADER_TRANSLATIONS = new CharSequenceMap<>(); private final long streamId;
/*     */     static {
/* 579 */       RESPONSE_HEADER_TRANSLATIONS.add(Http3Headers.PseudoHeaderName.AUTHORITY.value(), HttpHeaderNames.HOST);
/*     */       
/* 581 */       RESPONSE_HEADER_TRANSLATIONS.add(Http3Headers.PseudoHeaderName.SCHEME.value(), HttpConversionUtil.ExtensionHeaderNames.SCHEME
/* 582 */           .text());
/* 583 */       REQUEST_HEADER_TRANSLATIONS.add((Headers)RESPONSE_HEADER_TRANSLATIONS);
/* 584 */       RESPONSE_HEADER_TRANSLATIONS.add(Http3Headers.PseudoHeaderName.PATH.value(), HttpConversionUtil.ExtensionHeaderNames.PATH
/* 585 */           .text());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final HttpHeaders output;
/*     */ 
/*     */ 
/*     */     
/*     */     private final CharSequenceMap<AsciiString> translations;
/*     */ 
/*     */ 
/*     */     
/*     */     Http3ToHttpHeaderTranslator(long streamId, HttpHeaders output, boolean request) {
/* 600 */       this.streamId = streamId;
/* 601 */       this.output = output;
/* 602 */       this.translations = request ? REQUEST_HEADER_TRANSLATIONS : RESPONSE_HEADER_TRANSLATIONS;
/*     */     }
/*     */ 
/*     */     
/*     */     void translateHeaders(Iterable<Map.Entry<CharSequence, CharSequence>> inputHeaders) throws Http3Exception {
/* 607 */       StringBuilder cookies = null;
/*     */       
/* 609 */       for (Map.Entry<CharSequence, CharSequence> entry : inputHeaders) {
/* 610 */         CharSequence name = entry.getKey();
/* 611 */         CharSequence value = entry.getValue();
/* 612 */         AsciiString translatedName = (AsciiString)this.translations.get(name);
/* 613 */         if (translatedName != null) {
/* 614 */           this.output.add((CharSequence)translatedName, AsciiString.of(value)); continue;
/* 615 */         }  if (!Http3Headers.PseudoHeaderName.isPseudoHeader(name)) {
/*     */ 
/*     */           
/* 618 */           if (name.length() == 0 || name.charAt(0) == ':') {
/* 619 */             throw HttpConversionUtil.streamError(this.streamId, Http3ErrorCode.H3_MESSAGE_ERROR, "Invalid HTTP/3 header '" + name + "' encountered in translation to HTTP/1.x", null);
/*     */           }
/*     */ 
/*     */           
/* 623 */           if (HttpHeaderNames.COOKIE.equals(name)) {
/*     */ 
/*     */             
/* 626 */             if (cookies == null) {
/* 627 */               cookies = InternalThreadLocalMap.get().stringBuilder();
/* 628 */             } else if (cookies.length() > 0) {
/* 629 */               cookies.append("; ");
/*     */             } 
/* 631 */             cookies.append(value); continue;
/*     */           } 
/* 633 */           this.output.add(name, value);
/*     */         } 
/*     */       } 
/*     */       
/* 637 */       if (cookies != null) {
/* 638 */         this.output.add((CharSequence)HttpHeaderNames.COOKIE, cookies.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static Http3Exception streamError(long streamId, Http3ErrorCode error, String msg, @Nullable Throwable cause) {
/* 645 */     return new Http3Exception(error, streamId + ": " + msg, cause);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\HttpConversionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */