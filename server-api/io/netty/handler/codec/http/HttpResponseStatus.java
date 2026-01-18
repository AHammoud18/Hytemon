/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpResponseStatus
/*     */   implements Comparable<HttpResponseStatus>
/*     */ {
/*  39 */   public static final HttpResponseStatus CONTINUE = newStatus(100, "Continue");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final HttpResponseStatus SWITCHING_PROTOCOLS = newStatus(101, "Switching Protocols");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final HttpResponseStatus PROCESSING = newStatus(102, "Processing");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final HttpResponseStatus EARLY_HINTS = newStatus(103, "Early Hints");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final HttpResponseStatus OK = newStatus(200, "OK");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final HttpResponseStatus CREATED = newStatus(201, "Created");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final HttpResponseStatus ACCEPTED = newStatus(202, "Accepted");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION = newStatus(203, "Non-Authoritative Information");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final HttpResponseStatus NO_CONTENT = newStatus(204, "No Content");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final HttpResponseStatus RESET_CONTENT = newStatus(205, "Reset Content");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final HttpResponseStatus PARTIAL_CONTENT = newStatus(206, "Partial Content");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static final HttpResponseStatus MULTI_STATUS = newStatus(207, "Multi-Status");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final HttpResponseStatus MULTIPLE_CHOICES = newStatus(300, "Multiple Choices");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static final HttpResponseStatus MOVED_PERMANENTLY = newStatus(301, "Moved Permanently");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public static final HttpResponseStatus FOUND = newStatus(302, "Found");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static final HttpResponseStatus SEE_OTHER = newStatus(303, "See Other");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static final HttpResponseStatus NOT_MODIFIED = newStatus(304, "Not Modified");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public static final HttpResponseStatus USE_PROXY = newStatus(305, "Use Proxy");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public static final HttpResponseStatus TEMPORARY_REDIRECT = newStatus(307, "Temporary Redirect");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public static final HttpResponseStatus PERMANENT_REDIRECT = newStatus(308, "Permanent Redirect");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public static final HttpResponseStatus BAD_REQUEST = newStatus(400, "Bad Request");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static final HttpResponseStatus UNAUTHORIZED = newStatus(401, "Unauthorized");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public static final HttpResponseStatus PAYMENT_REQUIRED = newStatus(402, "Payment Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public static final HttpResponseStatus FORBIDDEN = newStatus(403, "Forbidden");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static final HttpResponseStatus NOT_FOUND = newStatus(404, "Not Found");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final HttpResponseStatus METHOD_NOT_ALLOWED = newStatus(405, "Method Not Allowed");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public static final HttpResponseStatus NOT_ACCEPTABLE = newStatus(406, "Not Acceptable");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED = newStatus(407, "Proxy Authentication Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public static final HttpResponseStatus REQUEST_TIMEOUT = newStatus(408, "Request Timeout");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public static final HttpResponseStatus CONFLICT = newStatus(409, "Conflict");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public static final HttpResponseStatus GONE = newStatus(410, "Gone");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   public static final HttpResponseStatus LENGTH_REQUIRED = newStatus(411, "Length Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   public static final HttpResponseStatus PRECONDITION_FAILED = newStatus(412, "Precondition Failed");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE = newStatus(413, "Request Entity Too Large");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public static final HttpResponseStatus REQUEST_URI_TOO_LONG = newStatus(414, "Request-URI Too Long");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = newStatus(415, "Unsupported Media Type");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE = newStatus(416, "Requested Range Not Satisfiable");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public static final HttpResponseStatus EXPECTATION_FAILED = newStatus(417, "Expectation Failed");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 235 */   public static final HttpResponseStatus MISDIRECTED_REQUEST = newStatus(421, "Misdirected Request");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public static final HttpResponseStatus UNPROCESSABLE_ENTITY = newStatus(422, "Unprocessable Entity");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public static final HttpResponseStatus LOCKED = newStatus(423, "Locked");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public static final HttpResponseStatus FAILED_DEPENDENCY = newStatus(424, "Failed Dependency");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   public static final HttpResponseStatus UNORDERED_COLLECTION = newStatus(425, "Unordered Collection");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public static final HttpResponseStatus UPGRADE_REQUIRED = newStatus(426, "Upgrade Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   public static final HttpResponseStatus PRECONDITION_REQUIRED = newStatus(428, "Precondition Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public static final HttpResponseStatus TOO_MANY_REQUESTS = newStatus(429, "Too Many Requests");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   public static final HttpResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE = newStatus(431, "Request Header Fields Too Large");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 281 */   public static final HttpResponseStatus INTERNAL_SERVER_ERROR = newStatus(500, "Internal Server Error");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   public static final HttpResponseStatus NOT_IMPLEMENTED = newStatus(501, "Not Implemented");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 291 */   public static final HttpResponseStatus BAD_GATEWAY = newStatus(502, "Bad Gateway");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 296 */   public static final HttpResponseStatus SERVICE_UNAVAILABLE = newStatus(503, "Service Unavailable");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 301 */   public static final HttpResponseStatus GATEWAY_TIMEOUT = newStatus(504, "Gateway Timeout");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED = newStatus(505, "HTTP Version Not Supported");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES = newStatus(506, "Variant Also Negotiates");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public static final HttpResponseStatus INSUFFICIENT_STORAGE = newStatus(507, "Insufficient Storage");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 322 */   public static final HttpResponseStatus NOT_EXTENDED = newStatus(510, "Not Extended");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public static final HttpResponseStatus NETWORK_AUTHENTICATION_REQUIRED = newStatus(511, "Network Authentication Required");
/*     */   
/*     */   private static HttpResponseStatus newStatus(int statusCode, String reasonPhrase) {
/* 331 */     return new HttpResponseStatus(statusCode, reasonPhrase, true);
/*     */   }
/*     */   private final int code;
/*     */   private final AsciiString codeAsText;
/*     */   private final HttpStatusClass codeClass;
/*     */   private final String reasonPhrase;
/*     */   private final byte[] bytes;
/*     */   
/*     */   public static HttpResponseStatus valueOf(int code) {
/* 340 */     HttpResponseStatus status = valueOf0(code);
/* 341 */     return (status != null) ? status : new HttpResponseStatus(code);
/*     */   }
/*     */   
/*     */   private static HttpResponseStatus valueOf0(int code) {
/* 345 */     switch (code) {
/*     */       case 100:
/* 347 */         return CONTINUE;
/*     */       case 101:
/* 349 */         return SWITCHING_PROTOCOLS;
/*     */       case 102:
/* 351 */         return PROCESSING;
/*     */       case 103:
/* 353 */         return EARLY_HINTS;
/*     */       case 200:
/* 355 */         return OK;
/*     */       case 201:
/* 357 */         return CREATED;
/*     */       case 202:
/* 359 */         return ACCEPTED;
/*     */       case 203:
/* 361 */         return NON_AUTHORITATIVE_INFORMATION;
/*     */       case 204:
/* 363 */         return NO_CONTENT;
/*     */       case 205:
/* 365 */         return RESET_CONTENT;
/*     */       case 206:
/* 367 */         return PARTIAL_CONTENT;
/*     */       case 207:
/* 369 */         return MULTI_STATUS;
/*     */       case 300:
/* 371 */         return MULTIPLE_CHOICES;
/*     */       case 301:
/* 373 */         return MOVED_PERMANENTLY;
/*     */       case 302:
/* 375 */         return FOUND;
/*     */       case 303:
/* 377 */         return SEE_OTHER;
/*     */       case 304:
/* 379 */         return NOT_MODIFIED;
/*     */       case 305:
/* 381 */         return USE_PROXY;
/*     */       case 307:
/* 383 */         return TEMPORARY_REDIRECT;
/*     */       case 308:
/* 385 */         return PERMANENT_REDIRECT;
/*     */       case 400:
/* 387 */         return BAD_REQUEST;
/*     */       case 401:
/* 389 */         return UNAUTHORIZED;
/*     */       case 402:
/* 391 */         return PAYMENT_REQUIRED;
/*     */       case 403:
/* 393 */         return FORBIDDEN;
/*     */       case 404:
/* 395 */         return NOT_FOUND;
/*     */       case 405:
/* 397 */         return METHOD_NOT_ALLOWED;
/*     */       case 406:
/* 399 */         return NOT_ACCEPTABLE;
/*     */       case 407:
/* 401 */         return PROXY_AUTHENTICATION_REQUIRED;
/*     */       case 408:
/* 403 */         return REQUEST_TIMEOUT;
/*     */       case 409:
/* 405 */         return CONFLICT;
/*     */       case 410:
/* 407 */         return GONE;
/*     */       case 411:
/* 409 */         return LENGTH_REQUIRED;
/*     */       case 412:
/* 411 */         return PRECONDITION_FAILED;
/*     */       case 413:
/* 413 */         return REQUEST_ENTITY_TOO_LARGE;
/*     */       case 414:
/* 415 */         return REQUEST_URI_TOO_LONG;
/*     */       case 415:
/* 417 */         return UNSUPPORTED_MEDIA_TYPE;
/*     */       case 416:
/* 419 */         return REQUESTED_RANGE_NOT_SATISFIABLE;
/*     */       case 417:
/* 421 */         return EXPECTATION_FAILED;
/*     */       case 421:
/* 423 */         return MISDIRECTED_REQUEST;
/*     */       case 422:
/* 425 */         return UNPROCESSABLE_ENTITY;
/*     */       case 423:
/* 427 */         return LOCKED;
/*     */       case 424:
/* 429 */         return FAILED_DEPENDENCY;
/*     */       case 425:
/* 431 */         return UNORDERED_COLLECTION;
/*     */       case 426:
/* 433 */         return UPGRADE_REQUIRED;
/*     */       case 428:
/* 435 */         return PRECONDITION_REQUIRED;
/*     */       case 429:
/* 437 */         return TOO_MANY_REQUESTS;
/*     */       case 431:
/* 439 */         return REQUEST_HEADER_FIELDS_TOO_LARGE;
/*     */       case 500:
/* 441 */         return INTERNAL_SERVER_ERROR;
/*     */       case 501:
/* 443 */         return NOT_IMPLEMENTED;
/*     */       case 502:
/* 445 */         return BAD_GATEWAY;
/*     */       case 503:
/* 447 */         return SERVICE_UNAVAILABLE;
/*     */       case 504:
/* 449 */         return GATEWAY_TIMEOUT;
/*     */       case 505:
/* 451 */         return HTTP_VERSION_NOT_SUPPORTED;
/*     */       case 506:
/* 453 */         return VARIANT_ALSO_NEGOTIATES;
/*     */       case 507:
/* 455 */         return INSUFFICIENT_STORAGE;
/*     */       case 510:
/* 457 */         return NOT_EXTENDED;
/*     */       case 511:
/* 459 */         return NETWORK_AUTHENTICATION_REQUIRED;
/*     */     } 
/* 461 */     return null;
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
/*     */   public static HttpResponseStatus valueOf(int code, String reasonPhrase) {
/* 473 */     HttpResponseStatus responseStatus = valueOf0(code);
/* 474 */     return (responseStatus != null && responseStatus.reasonPhrase().contentEquals(reasonPhrase)) ? responseStatus : 
/* 475 */       new HttpResponseStatus(code, reasonPhrase);
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
/*     */   public static HttpResponseStatus parseLine(CharSequence line) {
/* 488 */     return (line instanceof AsciiString) ? parseLine((AsciiString)line) : parseLine(line.toString());
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
/*     */   public static HttpResponseStatus parseLine(String line) {
/*     */     try {
/* 502 */       int space = line.indexOf(' ');
/* 503 */       return (space == -1) ? valueOf(Integer.parseInt(line)) : 
/* 504 */         valueOf(Integer.parseInt(line.substring(0, space)), line.substring(space + 1));
/* 505 */     } catch (Exception e) {
/* 506 */       throw new IllegalArgumentException("malformed status line: " + line, e);
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
/*     */   public static HttpResponseStatus parseLine(AsciiString line) {
/*     */     try {
/* 521 */       int space = line.forEachByte(ByteProcessor.FIND_ASCII_SPACE);
/* 522 */       return (space == -1) ? valueOf(line.parseInt()) : valueOf(line.parseInt(0, space), line.toString(space + 1));
/* 523 */     } catch (Exception e) {
/* 524 */       throw new IllegalArgumentException("malformed status line: " + line, e);
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
/*     */   private HttpResponseStatus(int code) {
/* 539 */     this(code, HttpStatusClass.valueOf(code).defaultReasonPhrase() + " (" + code + ')', false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResponseStatus(int code, String reasonPhrase) {
/* 546 */     this(code, reasonPhrase, false);
/*     */   }
/*     */   
/*     */   private HttpResponseStatus(int code, String reasonPhrase, boolean bytes) {
/* 550 */     ObjectUtil.checkPositiveOrZero(code, "code");
/* 551 */     ObjectUtil.checkNotNull(reasonPhrase, "reasonPhrase");
/*     */     
/* 553 */     for (int i = 0; i < reasonPhrase.length(); i++) {
/* 554 */       char c = reasonPhrase.charAt(i);
/*     */       
/* 556 */       switch (c) { case '\n':
/*     */         case '\r':
/* 558 */           throw new IllegalArgumentException("reasonPhrase contains one of the following prohibited characters: \\r\\n: " + reasonPhrase); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 564 */     this.code = code;
/* 565 */     this.codeClass = HttpStatusClass.valueOf(code);
/* 566 */     String codeString = Integer.toString(code);
/* 567 */     this.codeAsText = new AsciiString(codeString);
/* 568 */     this.reasonPhrase = reasonPhrase;
/* 569 */     if (bytes) {
/* 570 */       this.bytes = (codeString + ' ' + reasonPhrase).getBytes(CharsetUtil.US_ASCII);
/*     */     } else {
/* 572 */       this.bytes = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int code() {
/* 580 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AsciiString codeAsText() {
/* 587 */     return this.codeAsText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String reasonPhrase() {
/* 594 */     return this.reasonPhrase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpStatusClass codeClass() {
/* 601 */     return this.codeClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 606 */     return code();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 615 */     if (!(o instanceof HttpResponseStatus)) {
/* 616 */       return false;
/*     */     }
/*     */     
/* 619 */     return (code() == ((HttpResponseStatus)o).code());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(HttpResponseStatus o) {
/* 628 */     return code() - o.code();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 633 */     return (new StringBuilder(this.reasonPhrase.length() + 4))
/* 634 */       .append((CharSequence)this.codeAsText)
/* 635 */       .append(' ')
/* 636 */       .append(this.reasonPhrase)
/* 637 */       .toString();
/*     */   }
/*     */   
/*     */   void encode(ByteBuf buf) {
/* 641 */     if (this.bytes == null) {
/* 642 */       ByteBufUtil.copy(this.codeAsText, buf);
/* 643 */       buf.writeByte(32);
/* 644 */       buf.writeCharSequence(this.reasonPhrase, CharsetUtil.US_ASCII);
/*     */     } else {
/* 646 */       buf.writeBytes(this.bytes);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpResponseStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */