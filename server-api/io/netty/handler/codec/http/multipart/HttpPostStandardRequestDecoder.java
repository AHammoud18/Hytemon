/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.handler.codec.http.HttpContent;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.QueryStringDecoder;
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpPostStandardRequestDecoder
/*     */   implements InterfaceHttpPostRequestDecoder
/*     */ {
/*     */   private final HttpDataFactory factory;
/*     */   private final HttpRequest request;
/*     */   private final Charset charset;
/*     */   private final int maxFields;
/*     */   private final int maxBufferedBytes;
/*     */   private boolean isLastChunk;
/*  88 */   private final List<InterfaceHttpData> bodyListHttpData = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap<>(CaseIgnoringComparator.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuf undecodedChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int bodyListHttpDataRank;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private HttpPostRequestDecoder.MultiPartStatus currentStatus = HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED;
/*     */ 
/*     */   
/*     */   private Attribute currentAttribute;
/*     */ 
/*     */   
/*     */   private boolean destroyed;
/*     */ 
/*     */   
/* 118 */   private int discardThreshold = 10485760;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpPostStandardRequestDecoder(HttpRequest request) {
/* 131 */     this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
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
/*     */   public HttpPostStandardRequestDecoder(HttpDataFactory factory, HttpRequest request) {
/* 147 */     this(factory, request, HttpConstants.DEFAULT_CHARSET);
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
/*     */   public HttpPostStandardRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
/* 165 */     this(factory, request, charset, 128, 1024);
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
/*     */   public HttpPostStandardRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset, int maxFields, int maxBufferedBytes) {
/* 189 */     this.request = (HttpRequest)ObjectUtil.checkNotNull(request, "request");
/* 190 */     this.charset = (Charset)ObjectUtil.checkNotNull(charset, "charset");
/* 191 */     this.factory = (HttpDataFactory)ObjectUtil.checkNotNull(factory, "factory");
/* 192 */     this.maxFields = maxFields;
/* 193 */     this.maxBufferedBytes = maxBufferedBytes;
/*     */     try {
/* 195 */       if (request instanceof HttpContent) {
/*     */ 
/*     */         
/* 198 */         offer((HttpContent)request);
/*     */       } else {
/* 200 */         parseBody();
/*     */       } 
/* 202 */     } catch (Throwable e) {
/* 203 */       destroy();
/* 204 */       PlatformDependent.throwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkDestroyed() {
/* 209 */     if (this.destroyed) {
/* 210 */       throw new IllegalStateException(HttpPostStandardRequestDecoder.class.getSimpleName() + " was destroyed already");
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
/*     */   public boolean isMultipart() {
/* 222 */     checkDestroyed();
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscardThreshold(int discardThreshold) {
/* 233 */     this.discardThreshold = ObjectUtil.checkPositiveOrZero(discardThreshold, "discardThreshold");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDiscardThreshold() {
/* 241 */     return this.discardThreshold;
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
/*     */   public List<InterfaceHttpData> getBodyHttpDatas() {
/* 256 */     checkDestroyed();
/*     */     
/* 258 */     if (!this.isLastChunk) {
/* 259 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*     */     }
/* 261 */     return this.bodyListHttpData;
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
/*     */   public List<InterfaceHttpData> getBodyHttpDatas(String name) {
/* 277 */     checkDestroyed();
/*     */     
/* 279 */     if (!this.isLastChunk) {
/* 280 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*     */     }
/* 282 */     return this.bodyMapHttpData.get(name);
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
/*     */   public InterfaceHttpData getBodyHttpData(String name) {
/* 299 */     checkDestroyed();
/*     */     
/* 301 */     if (!this.isLastChunk) {
/* 302 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*     */     }
/* 304 */     List<InterfaceHttpData> list = this.bodyMapHttpData.get(name);
/* 305 */     if (list != null) {
/* 306 */       return list.get(0);
/*     */     }
/* 308 */     return null;
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
/*     */   public HttpPostStandardRequestDecoder offer(HttpContent content) {
/* 322 */     checkDestroyed();
/*     */     
/* 324 */     if (content instanceof io.netty.handler.codec.http.LastHttpContent) {
/* 325 */       this.isLastChunk = true;
/*     */     }
/*     */     
/* 328 */     ByteBuf buf = content.content();
/* 329 */     if (this.undecodedChunk == null) {
/* 330 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 335 */         .undecodedChunk = buf.alloc().buffer(buf.readableBytes()).writeBytes(buf);
/*     */     } else {
/* 337 */       this.undecodedChunk.writeBytes(buf);
/*     */     } 
/* 339 */     parseBody();
/* 340 */     if (this.maxBufferedBytes > 0 && this.undecodedChunk != null && this.undecodedChunk.readableBytes() > this.maxBufferedBytes) {
/* 341 */       throw new HttpPostRequestDecoder.TooLongFormFieldException();
/*     */     }
/* 343 */     if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
/* 344 */       if (this.undecodedChunk.refCnt() == 1) {
/*     */         
/* 346 */         this.undecodedChunk.discardReadBytes();
/*     */       }
/*     */       else {
/*     */         
/* 350 */         ByteBuf buffer = this.undecodedChunk.alloc().buffer(this.undecodedChunk.readableBytes());
/* 351 */         buffer.writeBytes(this.undecodedChunk);
/* 352 */         this.undecodedChunk.release();
/* 353 */         this.undecodedChunk = buffer;
/*     */       } 
/*     */     }
/* 356 */     return this;
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
/*     */   public boolean hasNext() {
/* 371 */     checkDestroyed();
/*     */     
/* 373 */     if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE)
/*     */     {
/* 375 */       if (this.bodyListHttpDataRank >= this.bodyListHttpData.size()) {
/* 376 */         throw new HttpPostRequestDecoder.EndOfDataDecoderException();
/*     */       }
/*     */     }
/* 379 */     return (!this.bodyListHttpData.isEmpty() && this.bodyListHttpDataRank < this.bodyListHttpData.size());
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
/*     */   public InterfaceHttpData next() {
/* 396 */     checkDestroyed();
/*     */     
/* 398 */     if (hasNext()) {
/* 399 */       return this.bodyListHttpData.get(this.bodyListHttpDataRank++);
/*     */     }
/* 401 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData currentPartialHttpData() {
/* 406 */     return this.currentAttribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseBody() {
/* 417 */     if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE || this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
/* 418 */       if (this.isLastChunk) {
/* 419 */         this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
/*     */       }
/*     */       return;
/*     */     } 
/* 423 */     parseBodyAttributes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addHttpData(InterfaceHttpData data) {
/* 430 */     if (data == null) {
/*     */       return;
/*     */     }
/* 433 */     if (this.maxFields > 0 && this.bodyListHttpData.size() >= this.maxFields) {
/* 434 */       throw new HttpPostRequestDecoder.TooManyFormFieldsException();
/*     */     }
/* 436 */     List<InterfaceHttpData> datas = this.bodyMapHttpData.get(data.getName());
/* 437 */     if (datas == null) {
/* 438 */       datas = new ArrayList<>(1);
/* 439 */       this.bodyMapHttpData.put(data.getName(), datas);
/*     */     } 
/* 441 */     datas.add(data);
/* 442 */     this.bodyListHttpData.add(data);
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
/*     */   private void parseBodyAttributesStandard() {
/* 454 */     int firstpos = this.undecodedChunk.readerIndex();
/* 455 */     int currentpos = firstpos;
/*     */ 
/*     */     
/* 458 */     if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED) {
/* 459 */       this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
/*     */     }
/* 461 */     boolean contRead = true;
/*     */     try {
/* 463 */       while (this.undecodedChunk.isReadable() && contRead) {
/* 464 */         char read = (char)this.undecodedChunk.readUnsignedByte();
/* 465 */         currentpos++;
/* 466 */         switch (this.currentStatus) {
/*     */           case DISPOSITION:
/* 468 */             if (read == '=') {
/* 469 */               this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FIELD;
/* 470 */               int equalpos = currentpos - 1;
/* 471 */               String key = decodeAttribute(this.undecodedChunk.toString(firstpos, equalpos - firstpos, this.charset), this.charset);
/*     */               
/* 473 */               this.currentAttribute = this.factory.createAttribute(this.request, key);
/* 474 */               firstpos = currentpos; continue;
/* 475 */             }  if (read == '&' || (this.isLastChunk && 
/* 476 */               !this.undecodedChunk.isReadable() && hasFormBody())) {
/* 477 */               this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
/* 478 */               int ampersandpos = (read == '&') ? (currentpos - 1) : currentpos;
/* 479 */               String key = decodeAttribute(this.undecodedChunk
/* 480 */                   .toString(firstpos, ampersandpos - firstpos, this.charset), this.charset);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 485 */               if (!key.isEmpty()) {
/* 486 */                 this.currentAttribute = this.factory.createAttribute(this.request, key);
/* 487 */                 this.currentAttribute.setValue("");
/* 488 */                 addHttpData(this.currentAttribute);
/*     */               } 
/* 490 */               this.currentAttribute = null;
/* 491 */               firstpos = currentpos;
/* 492 */               contRead = true;
/*     */             } 
/*     */             continue;
/*     */           case FIELD:
/* 496 */             if (read == '&') {
/* 497 */               this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
/* 498 */               int ampersandpos = currentpos - 1;
/* 499 */               setFinalBuffer(this.undecodedChunk.retainedSlice(firstpos, ampersandpos - firstpos));
/* 500 */               firstpos = currentpos;
/* 501 */               contRead = true; continue;
/* 502 */             }  if (read == '\r') {
/* 503 */               if (this.undecodedChunk.isReadable()) {
/* 504 */                 read = (char)this.undecodedChunk.readUnsignedByte();
/* 505 */                 currentpos++;
/* 506 */                 if (read == '\n') {
/* 507 */                   this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
/* 508 */                   int ampersandpos = currentpos - 2;
/* 509 */                   setFinalBuffer(this.undecodedChunk.retainedSlice(firstpos, ampersandpos - firstpos));
/* 510 */                   firstpos = currentpos;
/* 511 */                   contRead = false;
/*     */                   continue;
/*     */                 } 
/* 514 */                 throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad end of line");
/*     */               } 
/*     */               
/* 517 */               currentpos--; continue;
/*     */             } 
/* 519 */             if (read == '\n') {
/* 520 */               this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
/* 521 */               int ampersandpos = currentpos - 1;
/* 522 */               setFinalBuffer(this.undecodedChunk.retainedSlice(firstpos, ampersandpos - firstpos));
/* 523 */               firstpos = currentpos;
/* 524 */               contRead = false;
/*     */             } 
/*     */             continue;
/*     */         } 
/*     */         
/* 529 */         contRead = false;
/*     */       } 
/*     */       
/* 532 */       if (this.isLastChunk && this.currentAttribute != null) {
/*     */         
/* 534 */         int ampersandpos = currentpos;
/* 535 */         if (ampersandpos > firstpos) {
/* 536 */           setFinalBuffer(this.undecodedChunk.retainedSlice(firstpos, ampersandpos - firstpos));
/* 537 */         } else if (!this.currentAttribute.isCompleted()) {
/* 538 */           setFinalBuffer(Unpooled.EMPTY_BUFFER);
/*     */         } 
/* 540 */         firstpos = currentpos;
/* 541 */         this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
/* 542 */       } else if (contRead && this.currentAttribute != null && this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.FIELD) {
/*     */         
/* 544 */         this.currentAttribute.addContent(this.undecodedChunk.retainedSlice(firstpos, currentpos - firstpos), false);
/*     */         
/* 546 */         firstpos = currentpos;
/*     */       } 
/* 548 */       this.undecodedChunk.readerIndex(firstpos);
/* 549 */     } catch (ErrorDataDecoderException e) {
/*     */       
/* 551 */       this.undecodedChunk.readerIndex(firstpos);
/* 552 */       throw e;
/* 553 */     } catch (IOException|IllegalArgumentException e) {
/*     */       
/* 555 */       this.undecodedChunk.readerIndex(firstpos);
/* 556 */       throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
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
/*     */   private void parseBodyAttributes() {
/* 569 */     if (this.undecodedChunk == null) {
/*     */       return;
/*     */     }
/* 572 */     parseBodyAttributesStandard();
/*     */   }
/*     */   
/*     */   private void setFinalBuffer(ByteBuf buffer) throws IOException {
/* 576 */     this.currentAttribute.addContent(buffer, true);
/* 577 */     ByteBuf decodedBuf = decodeAttribute(this.currentAttribute.getByteBuf(), this.charset);
/* 578 */     if (decodedBuf != null) {
/* 579 */       this.currentAttribute.setContent(decodedBuf);
/*     */     }
/* 581 */     addHttpData(this.currentAttribute);
/* 582 */     this.currentAttribute = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String decodeAttribute(String s, Charset charset) {
/*     */     try {
/* 592 */       return QueryStringDecoder.decodeComponent(s, charset);
/* 593 */     } catch (IllegalArgumentException e) {
/* 594 */       throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad string: '" + s + '\'', e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ByteBuf decodeAttribute(ByteBuf b, Charset charset) {
/* 599 */     int firstEscaped = b.forEachByte(new UrlEncodedDetector());
/* 600 */     if (firstEscaped == -1) {
/* 601 */       return null;
/*     */     }
/*     */     
/* 604 */     ByteBuf buf = b.alloc().buffer(b.readableBytes());
/* 605 */     UrlDecoder urlDecode = new UrlDecoder(buf);
/* 606 */     int idx = b.forEachByte(urlDecode);
/* 607 */     if (urlDecode.nextEscapedIdx != 0) {
/* 608 */       if (idx == -1) {
/* 609 */         idx = b.readableBytes() - 1;
/*     */       }
/* 611 */       idx -= urlDecode.nextEscapedIdx - 1;
/* 612 */       buf.release();
/* 613 */       throw new HttpPostRequestDecoder.ErrorDataDecoderException(
/* 614 */           String.format("Invalid hex byte at index '%d' in string: '%s'", new Object[] { Integer.valueOf(idx), b.toString(charset) }));
/*     */     } 
/*     */     
/* 617 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 627 */     cleanFiles();
/*     */     
/* 629 */     for (InterfaceHttpData httpData : this.bodyListHttpData) {
/*     */       
/* 631 */       if (httpData.refCnt() > 0) {
/* 632 */         httpData.release();
/*     */       }
/*     */     } 
/*     */     
/* 636 */     this.destroyed = true;
/*     */     
/* 638 */     if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
/* 639 */       this.undecodedChunk.release();
/* 640 */       this.undecodedChunk = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanFiles() {
/* 649 */     checkDestroyed();
/*     */     
/* 651 */     this.factory.cleanRequestHttpData(this.request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHttpDataFromClean(InterfaceHttpData data) {
/* 659 */     checkDestroyed();
/*     */     
/* 661 */     this.factory.removeHttpDataFromClean(this.request, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasFormBody() {
/* 668 */     String contentHeaderValue = this.request.headers().get((CharSequence)HttpHeaderNames.CONTENT_TYPE);
/* 669 */     if (contentHeaderValue == null) {
/* 670 */       return false;
/*     */     }
/* 672 */     return (HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.contentEquals(contentHeaderValue) || HttpHeaderValues.MULTIPART_FORM_DATA
/* 673 */       .contentEquals(contentHeaderValue));
/*     */   }
/*     */   
/*     */   private static final class UrlEncodedDetector implements ByteProcessor { private UrlEncodedDetector() {}
/*     */     
/*     */     public boolean process(byte value) throws Exception {
/* 679 */       return (value != 37 && value != 43);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class UrlDecoder
/*     */     implements ByteProcessor {
/*     */     private final ByteBuf output;
/*     */     private int nextEscapedIdx;
/*     */     private byte hiByte;
/*     */     
/*     */     UrlDecoder(ByteBuf output) {
/* 690 */       this.output = output;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte value) {
/* 695 */       if (this.nextEscapedIdx != 0) {
/* 696 */         if (this.nextEscapedIdx == 1) {
/* 697 */           this.hiByte = value;
/* 698 */           this.nextEscapedIdx++;
/*     */         } else {
/* 700 */           int hi = StringUtil.decodeHexNibble((char)this.hiByte);
/* 701 */           int lo = StringUtil.decodeHexNibble((char)value);
/* 702 */           if (hi == -1 || lo == -1) {
/* 703 */             this.nextEscapedIdx++;
/* 704 */             return false;
/*     */           } 
/* 706 */           this.output.writeByte((hi << 4) + lo);
/* 707 */           this.nextEscapedIdx = 0;
/*     */         } 
/* 709 */       } else if (value == 37) {
/* 710 */         this.nextEscapedIdx = 1;
/* 711 */       } else if (value == 43) {
/* 712 */         this.output.writeByte(32);
/*     */       } else {
/* 714 */         this.output.writeByte(value);
/*     */       } 
/* 716 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\HttpPostStandardRequestDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */