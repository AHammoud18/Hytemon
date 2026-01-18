/*      */ package io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.handler.codec.http.HttpConstants;
/*      */ import io.netty.handler.codec.http.HttpContent;
/*      */ import io.netty.handler.codec.http.HttpHeaderNames;
/*      */ import io.netty.handler.codec.http.HttpHeaderValues;
/*      */ import io.netty.handler.codec.http.HttpRequest;
/*      */ import io.netty.handler.codec.http.QueryStringDecoder;
/*      */ import io.netty.util.CharsetUtil;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.InternalThreadLocalMap;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.IOException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.IllegalCharsetNameException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpPostMultipartRequestDecoder
/*      */   implements InterfaceHttpPostRequestDecoder
/*      */ {
/*      */   private final HttpDataFactory factory;
/*      */   private final HttpRequest request;
/*      */   private final int maxFields;
/*      */   private final int maxBufferedBytes;
/*      */   private Charset charset;
/*      */   private boolean isLastChunk;
/*   90 */   private final List<InterfaceHttpData> bodyListHttpData = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap<>(CaseIgnoringComparator.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf undecodedChunk;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int bodyListHttpDataRank;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String multipartDataBoundary;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String multipartMixedBoundary;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   private HttpPostRequestDecoder.MultiPartStatus currentStatus = HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<CharSequence, Attribute> currentFieldAttributes;
/*      */ 
/*      */ 
/*      */   
/*      */   private FileUpload currentFileUpload;
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute currentAttribute;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean destroyed;
/*      */ 
/*      */   
/*  141 */   private int discardThreshold = 10485760;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostMultipartRequestDecoder(HttpRequest request) {
/*  154 */     this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request) {
/*  170 */     this(factory, request, HttpConstants.DEFAULT_CHARSET);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
/*  188 */     this(factory, request, charset, 128, 1024);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset, int maxFields, int maxBufferedBytes) {
/*  212 */     this.request = (HttpRequest)ObjectUtil.checkNotNull(request, "request");
/*  213 */     this.charset = (Charset)ObjectUtil.checkNotNull(charset, "charset");
/*  214 */     this.factory = (HttpDataFactory)ObjectUtil.checkNotNull(factory, "factory");
/*  215 */     this.maxFields = maxFields;
/*  216 */     this.maxBufferedBytes = maxBufferedBytes;
/*      */ 
/*      */     
/*  219 */     String contentTypeValue = this.request.headers().get((CharSequence)HttpHeaderNames.CONTENT_TYPE);
/*  220 */     if (contentTypeValue == null) {
/*  221 */       throw new HttpPostRequestDecoder.ErrorDataDecoderException("No '" + HttpHeaderNames.CONTENT_TYPE + "' header present.");
/*      */     }
/*      */     
/*  224 */     String[] dataBoundary = HttpPostRequestDecoder.getMultipartDataBoundary(contentTypeValue);
/*  225 */     if (dataBoundary != null) {
/*  226 */       this.multipartDataBoundary = dataBoundary[0];
/*  227 */       if (dataBoundary.length > 1 && dataBoundary[1] != null) {
/*      */         try {
/*  229 */           this.charset = Charset.forName(dataBoundary[1]);
/*  230 */         } catch (IllegalCharsetNameException e) {
/*  231 */           throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */         } 
/*      */       }
/*      */     } else {
/*  235 */       this.multipartDataBoundary = null;
/*      */     } 
/*  237 */     this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
/*      */     
/*      */     try {
/*  240 */       if (request instanceof HttpContent) {
/*      */ 
/*      */         
/*  243 */         offer((HttpContent)request);
/*      */       } else {
/*  245 */         parseBody();
/*      */       } 
/*  247 */     } catch (Throwable e) {
/*  248 */       destroy();
/*  249 */       PlatformDependent.throwException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkDestroyed() {
/*  254 */     if (this.destroyed) {
/*  255 */       throw new IllegalStateException(HttpPostMultipartRequestDecoder.class.getSimpleName() + " was destroyed already");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMultipart() {
/*  267 */     checkDestroyed();
/*  268 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDiscardThreshold(int discardThreshold) {
/*  278 */     this.discardThreshold = ObjectUtil.checkPositiveOrZero(discardThreshold, "discardThreshold");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDiscardThreshold() {
/*  286 */     return this.discardThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<InterfaceHttpData> getBodyHttpDatas() {
/*  301 */     checkDestroyed();
/*      */     
/*  303 */     if (!this.isLastChunk) {
/*  304 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */     }
/*  306 */     return this.bodyListHttpData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<InterfaceHttpData> getBodyHttpDatas(String name) {
/*  322 */     checkDestroyed();
/*      */     
/*  324 */     if (!this.isLastChunk) {
/*  325 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */     }
/*  327 */     return this.bodyMapHttpData.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InterfaceHttpData getBodyHttpData(String name) {
/*  344 */     checkDestroyed();
/*      */     
/*  346 */     if (!this.isLastChunk) {
/*  347 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */     }
/*  349 */     List<InterfaceHttpData> list = this.bodyMapHttpData.get(name);
/*  350 */     if (list != null) {
/*  351 */       return list.get(0);
/*      */     }
/*  353 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostMultipartRequestDecoder offer(HttpContent content) {
/*  367 */     checkDestroyed();
/*      */     
/*  369 */     if (content instanceof io.netty.handler.codec.http.LastHttpContent) {
/*  370 */       this.isLastChunk = true;
/*      */     }
/*      */     
/*  373 */     ByteBuf buf = content.content();
/*  374 */     if (this.undecodedChunk == null) {
/*  375 */       this
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  380 */         .undecodedChunk = buf.alloc().buffer(buf.readableBytes()).writeBytes(buf);
/*      */     } else {
/*  382 */       this.undecodedChunk.writeBytes(buf);
/*      */     } 
/*  384 */     parseBody();
/*  385 */     if (this.maxBufferedBytes > 0 && this.undecodedChunk != null && this.undecodedChunk.readableBytes() > this.maxBufferedBytes) {
/*  386 */       throw new HttpPostRequestDecoder.TooLongFormFieldException();
/*      */     }
/*  388 */     if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
/*  389 */       if (this.undecodedChunk.refCnt() == 1) {
/*      */         
/*  391 */         this.undecodedChunk.discardReadBytes();
/*      */       }
/*      */       else {
/*      */         
/*  395 */         ByteBuf buffer = this.undecodedChunk.alloc().buffer(this.undecodedChunk.readableBytes());
/*  396 */         buffer.writeBytes(this.undecodedChunk);
/*  397 */         this.undecodedChunk.release();
/*  398 */         this.undecodedChunk = buffer;
/*      */       } 
/*      */     }
/*  401 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasNext() {
/*  416 */     checkDestroyed();
/*      */     
/*  418 */     if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE)
/*      */     {
/*  420 */       if (this.bodyListHttpDataRank >= this.bodyListHttpData.size()) {
/*  421 */         throw new HttpPostRequestDecoder.EndOfDataDecoderException();
/*      */       }
/*      */     }
/*  424 */     return (!this.bodyListHttpData.isEmpty() && this.bodyListHttpDataRank < this.bodyListHttpData.size());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InterfaceHttpData next() {
/*  441 */     checkDestroyed();
/*      */     
/*  443 */     if (hasNext()) {
/*  444 */       return this.bodyListHttpData.get(this.bodyListHttpDataRank++);
/*      */     }
/*  446 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public InterfaceHttpData currentPartialHttpData() {
/*  451 */     if (this.currentFileUpload != null) {
/*  452 */       return this.currentFileUpload;
/*      */     }
/*  454 */     return this.currentAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBody() {
/*  466 */     if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE || this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
/*  467 */       if (this.isLastChunk) {
/*  468 */         this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
/*      */       }
/*      */       return;
/*      */     } 
/*  472 */     parseBodyMultipart();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addHttpData(InterfaceHttpData data) {
/*  479 */     if (data == null) {
/*      */       return;
/*      */     }
/*  482 */     if (this.maxFields > 0 && this.bodyListHttpData.size() >= this.maxFields) {
/*  483 */       throw new HttpPostRequestDecoder.TooManyFormFieldsException();
/*      */     }
/*  485 */     List<InterfaceHttpData> datas = this.bodyMapHttpData.get(data.getName());
/*  486 */     if (datas == null) {
/*  487 */       datas = new ArrayList<>(1);
/*  488 */       this.bodyMapHttpData.put(data.getName(), datas);
/*      */     } 
/*  490 */     datas.add(data);
/*  491 */     this.bodyListHttpData.add(data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBodyMultipart() {
/*  502 */     if (this.undecodedChunk == null || this.undecodedChunk.readableBytes() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  506 */     InterfaceHttpData data = decodeMultipart(this.currentStatus);
/*  507 */     while (data != null) {
/*  508 */       addHttpData(data);
/*  509 */       if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE || this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
/*      */         break;
/*      */       }
/*  512 */       data = decodeMultipart(this.currentStatus);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData decodeMultipart(HttpPostRequestDecoder.MultiPartStatus state) {
/*      */     Charset localCharset;
/*      */     Attribute charsetAttribute;
/*      */     Attribute nameAttribute;
/*      */     Attribute finalAttribute;
/*  533 */     switch (state) {
/*      */       case NOTSTARTED:
/*  535 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */       
/*      */       case PREAMBLE:
/*  538 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */       
/*      */       case HEADERDELIMITER:
/*  541 */         return findMultipartDelimiter(this.multipartDataBoundary, HttpPostRequestDecoder.MultiPartStatus.DISPOSITION, HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case DISPOSITION:
/*  554 */         return findMultipartDisposition();
/*      */ 
/*      */       
/*      */       case FIELD:
/*  558 */         localCharset = null;
/*  559 */         charsetAttribute = this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
/*  560 */         if (charsetAttribute != null) {
/*      */           try {
/*  562 */             localCharset = Charset.forName(charsetAttribute.getValue());
/*  563 */           } catch (IOException|java.nio.charset.UnsupportedCharsetException e) {
/*  564 */             throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */           } 
/*      */         }
/*  567 */         nameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.NAME);
/*  568 */         if (this.currentAttribute == null) {
/*      */           long size;
/*  570 */           Attribute lengthAttribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
/*      */ 
/*      */           
/*      */           try {
/*  574 */             size = (lengthAttribute != null) ? Long.parseLong(lengthAttribute.getValue()) : 0L;
/*  575 */           } catch (IOException e) {
/*  576 */             throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*  577 */           } catch (NumberFormatException ignored) {
/*  578 */             size = 0L;
/*      */           } 
/*      */           try {
/*  581 */             if (size > 0L) {
/*  582 */               this.currentAttribute = this.factory.createAttribute(this.request, 
/*  583 */                   cleanString(nameAttribute.getValue()), size);
/*      */             } else {
/*  585 */               this.currentAttribute = this.factory.createAttribute(this.request, 
/*  586 */                   cleanString(nameAttribute.getValue()));
/*      */             } 
/*  588 */           } catch (NullPointerException|IllegalArgumentException|IOException e) {
/*  589 */             throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */           } 
/*  591 */           if (localCharset != null) {
/*  592 */             this.currentAttribute.setCharset(localCharset);
/*      */           }
/*      */         } 
/*      */         
/*  596 */         if (!loadDataMultipartOptimized(this.undecodedChunk, this.multipartDataBoundary, this.currentAttribute))
/*      */         {
/*  598 */           return null;
/*      */         }
/*  600 */         finalAttribute = this.currentAttribute;
/*  601 */         this.currentAttribute = null;
/*  602 */         this.currentFieldAttributes = null;
/*      */         
/*  604 */         this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
/*  605 */         return finalAttribute;
/*      */ 
/*      */       
/*      */       case FILEUPLOAD:
/*  609 */         return getFileUpload(this.multipartDataBoundary);
/*      */ 
/*      */ 
/*      */       
/*      */       case MIXEDDELIMITER:
/*  614 */         return findMultipartDelimiter(this.multipartMixedBoundary, HttpPostRequestDecoder.MultiPartStatus.MIXEDDISPOSITION, HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
/*      */ 
/*      */       
/*      */       case MIXEDDISPOSITION:
/*  618 */         return findMultipartDisposition();
/*      */ 
/*      */       
/*      */       case MIXEDFILEUPLOAD:
/*  622 */         return getFileUpload(this.multipartMixedBoundary);
/*      */       
/*      */       case PREEPILOGUE:
/*  625 */         return null;
/*      */       case EPILOGUE:
/*  627 */         return null;
/*      */     } 
/*  629 */     throw new HttpPostRequestDecoder.ErrorDataDecoderException("Shouldn't reach here.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void skipControlCharacters(ByteBuf undecodedChunk) {
/*  639 */     if (!undecodedChunk.hasArray()) {
/*      */       try {
/*  641 */         skipControlCharactersStandard(undecodedChunk);
/*  642 */       } catch (IndexOutOfBoundsException e1) {
/*  643 */         throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e1);
/*      */       } 
/*      */       return;
/*      */     } 
/*  647 */     HttpPostBodyUtil.SeekAheadOptimize sao = new HttpPostBodyUtil.SeekAheadOptimize(undecodedChunk);
/*  648 */     while (sao.pos < sao.limit) {
/*  649 */       char c = (char)(sao.bytes[sao.pos++] & 0xFF);
/*  650 */       if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
/*  651 */         sao.setReadPosition(1);
/*      */         return;
/*      */       } 
/*      */     } 
/*  655 */     throw new HttpPostRequestDecoder.NotEnoughDataDecoderException("Access out of bounds");
/*      */   }
/*      */   
/*      */   private static void skipControlCharactersStandard(ByteBuf undecodedChunk) {
/*      */     while (true) {
/*  660 */       char c = (char)undecodedChunk.readUnsignedByte();
/*  661 */       if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
/*  662 */         undecodedChunk.readerIndex(undecodedChunk.readerIndex() - 1);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData findMultipartDelimiter(String delimiter, HttpPostRequestDecoder.MultiPartStatus dispositionStatus, HttpPostRequestDecoder.MultiPartStatus closeDelimiterStatus) {
/*      */     String newline;
/*  683 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/*  685 */       skipControlCharacters(this.undecodedChunk);
/*  686 */     } catch (NotEnoughDataDecoderException ignored) {
/*  687 */       this.undecodedChunk.readerIndex(readerIndex);
/*  688 */       return null;
/*      */     } 
/*  690 */     skipOneLine();
/*      */     
/*      */     try {
/*  693 */       newline = readDelimiterOptimized(this.undecodedChunk, delimiter, this.charset);
/*  694 */     } catch (NotEnoughDataDecoderException ignored) {
/*  695 */       this.undecodedChunk.readerIndex(readerIndex);
/*  696 */       return null;
/*      */     } 
/*  698 */     if (newline.equals(delimiter)) {
/*  699 */       this.currentStatus = dispositionStatus;
/*  700 */       return decodeMultipart(dispositionStatus);
/*      */     } 
/*  702 */     if (newline.equals(delimiter + "--")) {
/*      */       
/*  704 */       this.currentStatus = closeDelimiterStatus;
/*  705 */       if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER) {
/*      */ 
/*      */         
/*  708 */         this.currentFieldAttributes = null;
/*  709 */         return decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
/*      */       } 
/*  711 */       return null;
/*      */     } 
/*  713 */     this.undecodedChunk.readerIndex(readerIndex);
/*  714 */     throw new HttpPostRequestDecoder.ErrorDataDecoderException("No Multipart delimiter found");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData findMultipartDisposition() {
/*  724 */     int readerIndex = this.undecodedChunk.readerIndex();
/*  725 */     if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
/*  726 */       this.currentFieldAttributes = new TreeMap<>(CaseIgnoringComparator.INSTANCE);
/*      */     }
/*      */     
/*  729 */     while (!skipOneLine()) {
/*      */       String newline;
/*      */       try {
/*  732 */         skipControlCharacters(this.undecodedChunk);
/*  733 */         newline = readLineOptimized(this.undecodedChunk, this.charset);
/*  734 */       } catch (NotEnoughDataDecoderException ignored) {
/*  735 */         this.undecodedChunk.readerIndex(readerIndex);
/*  736 */         return null;
/*      */       } 
/*  738 */       String[] contents = splitMultipartHeader(newline);
/*  739 */       if (HttpHeaderNames.CONTENT_DISPOSITION.contentEqualsIgnoreCase(contents[0])) {
/*      */         boolean checkSecondArg;
/*  741 */         if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
/*  742 */           checkSecondArg = HttpHeaderValues.FORM_DATA.contentEqualsIgnoreCase(contents[1]);
/*      */         } else {
/*      */           
/*  745 */           checkSecondArg = (HttpHeaderValues.ATTACHMENT.contentEqualsIgnoreCase(contents[1]) || HttpHeaderValues.FILE.contentEqualsIgnoreCase(contents[1]));
/*      */         } 
/*  747 */         if (checkSecondArg)
/*      */         {
/*  749 */           for (int i = 2; i < contents.length; i++) {
/*  750 */             Attribute attribute; String[] values = contents[i].split("=", 2);
/*      */             
/*      */             try {
/*  753 */               attribute = getContentDispositionAttribute(values);
/*  754 */             } catch (NullPointerException|IllegalArgumentException e) {
/*  755 */               throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */             } 
/*  757 */             this.currentFieldAttributes.put(attribute.getName(), attribute);
/*      */           }  }  continue;
/*      */       } 
/*  760 */       if (HttpHeaderNames.CONTENT_TRANSFER_ENCODING.contentEqualsIgnoreCase(contents[0])) {
/*      */         Attribute attribute;
/*      */         try {
/*  763 */           attribute = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_TRANSFER_ENCODING.toString(), 
/*  764 */               cleanString(contents[1]));
/*  765 */         } catch (NullPointerException|IllegalArgumentException e) {
/*  766 */           throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */         } 
/*      */         
/*  769 */         this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_TRANSFER_ENCODING, attribute); continue;
/*  770 */       }  if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(contents[0])) {
/*      */         Attribute attribute;
/*      */         try {
/*  773 */           attribute = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_LENGTH.toString(), 
/*  774 */               cleanString(contents[1]));
/*  775 */         } catch (NullPointerException|IllegalArgumentException e) {
/*  776 */           throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */         } 
/*      */         
/*  779 */         this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_LENGTH, attribute); continue;
/*  780 */       }  if (HttpHeaderNames.CONTENT_TYPE.contentEqualsIgnoreCase(contents[0])) {
/*      */         
/*  782 */         if (HttpHeaderValues.MULTIPART_MIXED.contentEqualsIgnoreCase(contents[1])) {
/*  783 */           if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
/*  784 */             String values = StringUtil.substringAfter(contents[2], '=');
/*  785 */             this.multipartMixedBoundary = "--" + values;
/*  786 */             this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER;
/*  787 */             return decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER);
/*      */           } 
/*  789 */           throw new HttpPostRequestDecoder.ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
/*      */         } 
/*      */         
/*  792 */         for (int i = 1; i < contents.length; i++) {
/*  793 */           String charsetHeader = HttpHeaderValues.CHARSET.toString();
/*  794 */           if (contents[i].regionMatches(true, 0, charsetHeader, 0, charsetHeader.length())) {
/*  795 */             Attribute attribute; String values = StringUtil.substringAfter(contents[i], '=');
/*      */             
/*      */             try {
/*  798 */               attribute = this.factory.createAttribute(this.request, charsetHeader, cleanString(values));
/*  799 */             } catch (NullPointerException|IllegalArgumentException e) {
/*  800 */               throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */             } 
/*  802 */             this.currentFieldAttributes.put(HttpHeaderValues.CHARSET, attribute);
/*  803 */           } else if (contents[i].contains("=")) {
/*  804 */             Attribute attribute; String name = StringUtil.substringBefore(contents[i], '=');
/*  805 */             String values = StringUtil.substringAfter(contents[i], '=');
/*      */             
/*      */             try {
/*  808 */               attribute = this.factory.createAttribute(this.request, cleanString(name), values);
/*  809 */             } catch (NullPointerException|IllegalArgumentException e) {
/*  810 */               throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */             } 
/*  812 */             this.currentFieldAttributes.put(name, attribute);
/*      */           } else {
/*      */             Attribute attribute;
/*      */             try {
/*  816 */               attribute = this.factory.createAttribute(this.request, 
/*  817 */                   cleanString(contents[0]), contents[i]);
/*  818 */             } catch (NullPointerException|IllegalArgumentException e) {
/*  819 */               Attribute attribute1; throw new HttpPostRequestDecoder.ErrorDataDecoderException(attribute1);
/*      */             } 
/*  821 */             this.currentFieldAttributes.put(attribute.getName(), attribute);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  828 */     Attribute filenameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
/*  829 */     if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
/*  830 */       if (filenameAttribute != null) {
/*      */         
/*  832 */         this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD;
/*      */         
/*  834 */         return decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD);
/*      */       } 
/*      */       
/*  837 */       this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FIELD;
/*      */       
/*  839 */       return decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.FIELD);
/*      */     } 
/*      */     
/*  842 */     if (filenameAttribute != null) {
/*      */       
/*  844 */       this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD;
/*      */       
/*  846 */       return decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD);
/*      */     } 
/*      */     
/*  849 */     throw new HttpPostRequestDecoder.ErrorDataDecoderException("Filename not found");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  854 */   private static final String FILENAME_ENCODED = HttpHeaderValues.FILENAME.toString() + '*';
/*      */   
/*      */   private Attribute getContentDispositionAttribute(String... values) {
/*  857 */     String name = cleanString(values[0]);
/*  858 */     String value = values[1];
/*      */ 
/*      */     
/*  861 */     if (HttpHeaderValues.FILENAME.contentEquals(name)) {
/*      */       
/*  863 */       int last = value.length() - 1;
/*  864 */       if (last > 0 && value
/*  865 */         .charAt(0) == '"' && value
/*  866 */         .charAt(last) == '"') {
/*  867 */         value = value.substring(1, last);
/*      */       }
/*  869 */     } else if (FILENAME_ENCODED.equals(name)) {
/*      */       try {
/*  871 */         name = HttpHeaderValues.FILENAME.toString();
/*  872 */         String[] split = cleanString(value).split("'", 3);
/*  873 */         value = QueryStringDecoder.decodeComponent(split[2], Charset.forName(split[0]));
/*  874 */       } catch (ArrayIndexOutOfBoundsException|java.nio.charset.UnsupportedCharsetException e) {
/*  875 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */       } 
/*      */     } else {
/*      */       
/*  879 */       value = cleanString(value);
/*      */     } 
/*  881 */     return this.factory.createAttribute(this.request, name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InterfaceHttpData getFileUpload(String delimiter) {
/*  895 */     Attribute encoding = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
/*  896 */     Charset localCharset = this.charset;
/*      */     
/*  898 */     HttpPostBodyUtil.TransferEncodingMechanism mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
/*  899 */     if (encoding != null) {
/*      */       String code;
/*      */       try {
/*  902 */         code = encoding.getValue().toLowerCase();
/*  903 */       } catch (IOException e) {
/*  904 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */       } 
/*  906 */       if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
/*  907 */         localCharset = CharsetUtil.US_ASCII;
/*  908 */       } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
/*  909 */         localCharset = CharsetUtil.ISO_8859_1;
/*  910 */         mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
/*  911 */       } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
/*      */         
/*  913 */         mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
/*      */       } else {
/*  915 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException("TransferEncoding Unknown: " + code);
/*      */       } 
/*      */     } 
/*  918 */     Attribute charsetAttribute = this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
/*  919 */     if (charsetAttribute != null) {
/*      */       try {
/*  921 */         localCharset = Charset.forName(charsetAttribute.getValue());
/*  922 */       } catch (IOException|java.nio.charset.UnsupportedCharsetException e) {
/*  923 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */       } 
/*      */     }
/*  926 */     if (this.currentFileUpload == null) {
/*  927 */       long size; Attribute filenameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
/*  928 */       Attribute nameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.NAME);
/*  929 */       Attribute contentTypeAttribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TYPE);
/*  930 */       Attribute lengthAttribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
/*      */       
/*      */       try {
/*  933 */         size = (lengthAttribute != null) ? Long.parseLong(lengthAttribute.getValue()) : 0L;
/*  934 */       } catch (IOException e) {
/*  935 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*  936 */       } catch (NumberFormatException ignored) {
/*  937 */         size = 0L;
/*      */       } 
/*      */       try {
/*      */         String contentType;
/*  941 */         if (contentTypeAttribute != null) {
/*  942 */           contentType = contentTypeAttribute.getValue();
/*      */         } else {
/*  944 */           contentType = "application/octet-stream";
/*      */         } 
/*  946 */         this.currentFileUpload = this.factory.createFileUpload(this.request, 
/*  947 */             cleanString(nameAttribute.getValue()), cleanString(filenameAttribute.getValue()), contentType, mechanism
/*  948 */             .value(), localCharset, size);
/*      */       }
/*  950 */       catch (NullPointerException|IllegalArgumentException|IOException e) {
/*  951 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */       } 
/*      */     } 
/*      */     
/*  955 */     if (!loadDataMultipartOptimized(this.undecodedChunk, delimiter, this.currentFileUpload))
/*      */     {
/*  957 */       return null;
/*      */     }
/*  959 */     if (this.currentFileUpload.isCompleted()) {
/*      */       
/*  961 */       if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD) {
/*  962 */         this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
/*  963 */         this.currentFieldAttributes = null;
/*      */       } else {
/*  965 */         this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER;
/*  966 */         cleanMixedAttributes();
/*      */       } 
/*  968 */       FileUpload fileUpload = this.currentFileUpload;
/*  969 */       this.currentFileUpload = null;
/*  970 */       return fileUpload;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  975 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() {
/*  985 */     cleanFiles();
/*      */     
/*  987 */     for (InterfaceHttpData httpData : this.bodyListHttpData) {
/*      */       
/*  989 */       if (httpData.refCnt() > 0) {
/*  990 */         httpData.release();
/*      */       }
/*      */     } 
/*      */     
/*  994 */     this.destroyed = true;
/*      */     
/*  996 */     if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
/*  997 */       this.undecodedChunk.release();
/*  998 */       this.undecodedChunk = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanFiles() {
/* 1007 */     checkDestroyed();
/*      */     
/* 1009 */     this.factory.cleanRequestHttpData(this.request);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeHttpDataFromClean(InterfaceHttpData data) {
/* 1017 */     checkDestroyed();
/*      */     
/* 1019 */     this.factory.removeHttpDataFromClean(this.request, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanMixedAttributes() {
/* 1027 */     this.currentFieldAttributes.remove(HttpHeaderValues.CHARSET);
/* 1028 */     this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_LENGTH);
/* 1029 */     this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
/* 1030 */     this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TYPE);
/* 1031 */     this.currentFieldAttributes.remove(HttpHeaderValues.FILENAME);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String readLineOptimized(ByteBuf undecodedChunk, Charset charset) {
/* 1043 */     int readerIndex = undecodedChunk.readerIndex();
/* 1044 */     ByteBuf line = null;
/*      */     try {
/* 1046 */       if (undecodedChunk.isReadable()) {
/* 1047 */         int posLfOrCrLf = HttpPostBodyUtil.findLineBreak(undecodedChunk, undecodedChunk.readerIndex());
/* 1048 */         if (posLfOrCrLf <= 0) {
/* 1049 */           throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */         }
/*      */         try {
/* 1052 */           line = undecodedChunk.alloc().heapBuffer(posLfOrCrLf);
/* 1053 */           line.writeBytes(undecodedChunk, posLfOrCrLf);
/*      */           
/* 1055 */           byte nextByte = undecodedChunk.readByte();
/* 1056 */           if (nextByte == 13)
/*      */           {
/* 1058 */             undecodedChunk.readByte();
/*      */           }
/* 1060 */           return line.toString(charset);
/*      */         } finally {
/* 1062 */           line.release();
/*      */         } 
/*      */       } 
/* 1065 */     } catch (IndexOutOfBoundsException e) {
/* 1066 */       undecodedChunk.readerIndex(readerIndex);
/* 1067 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
/*      */     } 
/* 1069 */     undecodedChunk.readerIndex(readerIndex);
/* 1070 */     throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String readDelimiterOptimized(ByteBuf undecodedChunk, String delimiter, Charset charset) {
/* 1089 */     int readerIndex = undecodedChunk.readerIndex();
/* 1090 */     byte[] bdelimiter = delimiter.getBytes(charset);
/* 1091 */     int delimiterLength = bdelimiter.length;
/*      */     try {
/* 1093 */       int delimiterPos = HttpPostBodyUtil.findDelimiter(undecodedChunk, readerIndex, bdelimiter, false);
/* 1094 */       if (delimiterPos < 0) {
/*      */         
/* 1096 */         undecodedChunk.readerIndex(readerIndex);
/* 1097 */         throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */       } 
/* 1099 */       StringBuilder sb = new StringBuilder(delimiter);
/* 1100 */       undecodedChunk.readerIndex(readerIndex + delimiterPos + delimiterLength);
/*      */       
/* 1102 */       if (undecodedChunk.isReadable()) {
/* 1103 */         byte nextByte = undecodedChunk.readByte();
/*      */         
/* 1105 */         if (nextByte == 13) {
/* 1106 */           nextByte = undecodedChunk.readByte();
/* 1107 */           if (nextByte == 10) {
/* 1108 */             return sb.toString();
/*      */           }
/*      */ 
/*      */           
/* 1112 */           undecodedChunk.readerIndex(readerIndex);
/* 1113 */           throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */         } 
/* 1115 */         if (nextByte == 10)
/* 1116 */           return sb.toString(); 
/* 1117 */         if (nextByte == 45) {
/* 1118 */           sb.append('-');
/*      */           
/* 1120 */           nextByte = undecodedChunk.readByte();
/* 1121 */           if (nextByte == 45) {
/* 1122 */             sb.append('-');
/*      */             
/* 1124 */             if (undecodedChunk.isReadable()) {
/* 1125 */               nextByte = undecodedChunk.readByte();
/* 1126 */               if (nextByte == 13) {
/* 1127 */                 nextByte = undecodedChunk.readByte();
/* 1128 */                 if (nextByte == 10) {
/* 1129 */                   return sb.toString();
/*      */                 }
/*      */ 
/*      */                 
/* 1133 */                 undecodedChunk.readerIndex(readerIndex);
/* 1134 */                 throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */               } 
/* 1136 */               if (nextByte == 10) {
/* 1137 */                 return sb.toString();
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1142 */               undecodedChunk.readerIndex(undecodedChunk.readerIndex() - 1);
/* 1143 */               return sb.toString();
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1150 */             return sb.toString();
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } 
/* 1156 */     } catch (IndexOutOfBoundsException e) {
/* 1157 */       undecodedChunk.readerIndex(readerIndex);
/* 1158 */       throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
/*      */     } 
/* 1160 */     undecodedChunk.readerIndex(readerIndex);
/* 1161 */     throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rewriteCurrentBuffer(ByteBuf buffer, int lengthToSkip) {
/* 1174 */     if (lengthToSkip == 0) {
/*      */       return;
/*      */     }
/* 1177 */     int readerIndex = buffer.readerIndex();
/* 1178 */     int readableBytes = buffer.readableBytes();
/* 1179 */     if (readableBytes == lengthToSkip) {
/* 1180 */       buffer.readerIndex(readerIndex);
/* 1181 */       buffer.writerIndex(readerIndex);
/*      */       return;
/*      */     } 
/* 1184 */     buffer.setBytes(readerIndex, buffer, readerIndex + lengthToSkip, readableBytes - lengthToSkip);
/* 1185 */     buffer.readerIndex(readerIndex);
/* 1186 */     buffer.writerIndex(readerIndex + readableBytes - lengthToSkip);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean loadDataMultipartOptimized(ByteBuf undecodedChunk, String delimiter, HttpData httpData) {
/* 1196 */     if (!undecodedChunk.isReadable()) {
/* 1197 */       return false;
/*      */     }
/* 1199 */     int startReaderIndex = undecodedChunk.readerIndex();
/* 1200 */     byte[] bdelimiter = delimiter.getBytes(httpData.getCharset());
/* 1201 */     int posDelimiter = HttpPostBodyUtil.findDelimiter(undecodedChunk, startReaderIndex, bdelimiter, true);
/* 1202 */     if (posDelimiter < 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1207 */       int readableBytes = undecodedChunk.readableBytes();
/* 1208 */       int lastPosition = readableBytes - bdelimiter.length - 1;
/* 1209 */       if (lastPosition < 0)
/*      */       {
/* 1211 */         lastPosition = 0;
/*      */       }
/* 1213 */       posDelimiter = HttpPostBodyUtil.findLastLineBreak(undecodedChunk, startReaderIndex + lastPosition);
/*      */ 
/*      */       
/* 1216 */       if (posDelimiter < 0 && httpData
/* 1217 */         .definedLength() == httpData.length() + readableBytes - 1L && undecodedChunk
/* 1218 */         .getByte(readableBytes + startReaderIndex - 1) == 13) {
/*      */         
/* 1220 */         lastPosition = 0;
/* 1221 */         posDelimiter = readableBytes - 1;
/*      */       } 
/* 1223 */       if (posDelimiter < 0) {
/*      */         
/* 1225 */         ByteBuf byteBuf1 = undecodedChunk.copy();
/*      */         try {
/* 1227 */           httpData.addContent(byteBuf1, false);
/* 1228 */         } catch (IOException e) {
/* 1229 */           throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */         } 
/* 1231 */         undecodedChunk.readerIndex(startReaderIndex);
/* 1232 */         undecodedChunk.writerIndex(startReaderIndex);
/* 1233 */         return false;
/*      */       } 
/*      */       
/* 1236 */       posDelimiter += lastPosition;
/* 1237 */       if (posDelimiter == 0)
/*      */       {
/* 1239 */         return false;
/*      */       }
/*      */       
/* 1242 */       ByteBuf byteBuf = undecodedChunk.copy(startReaderIndex, posDelimiter);
/*      */       try {
/* 1244 */         httpData.addContent(byteBuf, false);
/* 1245 */       } catch (IOException e) {
/* 1246 */         throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */       } 
/* 1248 */       rewriteCurrentBuffer(undecodedChunk, posDelimiter);
/* 1249 */       return false;
/*      */     } 
/*      */     
/* 1252 */     ByteBuf content = undecodedChunk.copy(startReaderIndex, posDelimiter);
/*      */     try {
/* 1254 */       httpData.addContent(content, true);
/* 1255 */     } catch (IOException e) {
/* 1256 */       throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
/*      */     } 
/* 1258 */     rewriteCurrentBuffer(undecodedChunk, posDelimiter);
/* 1259 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String cleanString(String field) {
/* 1268 */     int size = field.length();
/* 1269 */     StringBuilder sb = new StringBuilder(size);
/* 1270 */     for (int i = 0; i < size; i++) {
/* 1271 */       char nextChar = field.charAt(i);
/* 1272 */       switch (nextChar) {
/*      */         case '\t':
/*      */         case ',':
/*      */         case ':':
/*      */         case ';':
/*      */         case '=':
/* 1278 */           sb.append(' ');
/*      */           break;
/*      */         
/*      */         case '"':
/*      */           break;
/*      */         default:
/* 1284 */           sb.append(nextChar);
/*      */           break;
/*      */       } 
/*      */     } 
/* 1288 */     return sb.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean skipOneLine() {
/* 1297 */     if (!this.undecodedChunk.isReadable()) {
/* 1298 */       return false;
/*      */     }
/* 1300 */     byte nextByte = this.undecodedChunk.readByte();
/* 1301 */     if (nextByte == 13) {
/* 1302 */       if (!this.undecodedChunk.isReadable()) {
/* 1303 */         this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 1304 */         return false;
/*      */       } 
/* 1306 */       nextByte = this.undecodedChunk.readByte();
/* 1307 */       if (nextByte == 10) {
/* 1308 */         return true;
/*      */       }
/* 1310 */       this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
/* 1311 */       return false;
/*      */     } 
/* 1313 */     if (nextByte == 10) {
/* 1314 */       return true;
/*      */     }
/* 1316 */     this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 1317 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitMultipartHeader(String sb) {
/*      */     String[] values;
/* 1327 */     ArrayList<String> headers = new ArrayList<>(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1333 */     int nameStart = HttpPostBodyUtil.findNonWhitespace(sb, 0); int nameEnd;
/* 1334 */     for (nameEnd = nameStart; nameEnd < sb.length(); nameEnd++) {
/* 1335 */       char ch = sb.charAt(nameEnd);
/* 1336 */       if (ch == ':' || Character.isWhitespace(ch))
/*      */         break; 
/*      */     } 
/*      */     int colonEnd;
/* 1340 */     for (colonEnd = nameEnd; colonEnd < sb.length(); colonEnd++) {
/* 1341 */       if (sb.charAt(colonEnd) == ':') {
/* 1342 */         colonEnd++;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1346 */     int valueStart = HttpPostBodyUtil.findNonWhitespace(sb, colonEnd);
/* 1347 */     int valueEnd = HttpPostBodyUtil.findEndOfString(sb);
/* 1348 */     headers.add(sb.substring(nameStart, nameEnd));
/* 1349 */     String svalue = (valueStart >= valueEnd) ? "" : sb.substring(valueStart, valueEnd);
/*      */     
/* 1351 */     if (svalue.indexOf(';') >= 0) {
/* 1352 */       values = splitMultipartHeaderValues(svalue);
/*      */     } else {
/* 1354 */       values = svalue.split(",");
/*      */     } 
/* 1356 */     for (String value : values) {
/* 1357 */       headers.add(value.trim());
/*      */     }
/* 1359 */     String[] array = new String[headers.size()];
/* 1360 */     for (int i = 0; i < headers.size(); i++) {
/* 1361 */       array[i] = headers.get(i);
/*      */     }
/* 1363 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitMultipartHeaderValues(String svalue) {
/* 1371 */     List<String> values = InternalThreadLocalMap.get().arrayList(1);
/* 1372 */     boolean inQuote = false;
/* 1373 */     boolean escapeNext = false;
/* 1374 */     int start = 0;
/* 1375 */     for (int i = 0; i < svalue.length(); i++) {
/* 1376 */       char c = svalue.charAt(i);
/* 1377 */       if (inQuote) {
/* 1378 */         if (escapeNext) {
/* 1379 */           escapeNext = false;
/*      */         }
/* 1381 */         else if (c == '\\') {
/* 1382 */           escapeNext = true;
/* 1383 */         } else if (c == '"') {
/* 1384 */           inQuote = false;
/*      */         }
/*      */       
/*      */       }
/* 1388 */       else if (c == '"') {
/* 1389 */         inQuote = true;
/* 1390 */       } else if (c == ';') {
/* 1391 */         values.add(svalue.substring(start, i));
/* 1392 */         start = i + 1;
/*      */       } 
/*      */     } 
/*      */     
/* 1396 */     values.add(svalue.substring(start));
/* 1397 */     return values.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getCurrentAllocatedCapacity() {
/* 1408 */     return this.undecodedChunk.capacity();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\HttpPostMultipartRequestDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */