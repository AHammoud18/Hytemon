/*      */ package io.netty.handler.codec.http;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.handler.codec.ByteToMessageDecoder;
/*      */ import io.netty.handler.codec.DecoderResult;
/*      */ import io.netty.handler.codec.PrematureChannelClosureException;
/*      */ import io.netty.handler.codec.TooLongFrameException;
/*      */ import io.netty.util.AsciiString;
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.SystemPropertyUtil;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*      */ public abstract class HttpObjectDecoder
/*      */   extends ByteToMessageDecoder
/*      */ {
/*      */   public static final int DEFAULT_MAX_INITIAL_LINE_LENGTH = 4096;
/*      */   public static final int DEFAULT_MAX_HEADER_SIZE = 8192;
/*      */   public static final boolean DEFAULT_CHUNKED_SUPPORTED = true;
/*      */   public static final boolean DEFAULT_ALLOW_PARTIAL_CHUNKS = true;
/*      */   public static final int DEFAULT_MAX_CHUNK_SIZE = 8192;
/*      */   public static final boolean DEFAULT_VALIDATE_HEADERS = true;
/*      */   public static final int DEFAULT_INITIAL_BUFFER_SIZE = 128;
/*      */   public static final boolean DEFAULT_ALLOW_DUPLICATE_CONTENT_LENGTHS = false;
/*  156 */   public static final boolean DEFAULT_STRICT_LINE_PARSING = SystemPropertyUtil.getBoolean("io.netty.handler.codec.http.defaultStrictLineParsing", true);
/*      */   
/*  158 */   private static final Runnable THROW_INVALID_CHUNK_EXTENSION = new Runnable()
/*      */     {
/*      */       public void run() {
/*  161 */         throw new InvalidChunkExtensionException();
/*      */       }
/*      */     };
/*      */   
/*  165 */   private static final Runnable THROW_INVALID_LINE_SEPARATOR = new Runnable()
/*      */     {
/*      */       public void run() {
/*  168 */         throw new InvalidLineSeparatorException();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   private final int maxChunkSize;
/*      */   
/*      */   private final boolean chunkedSupported;
/*      */   
/*      */   private final boolean allowPartialChunks;
/*      */   
/*      */   @Deprecated
/*      */   protected final boolean validateHeaders;
/*      */   protected final HttpHeadersFactory headersFactory;
/*      */   protected final HttpHeadersFactory trailersFactory;
/*      */   private final boolean allowDuplicateContentLengths;
/*      */   private final ByteBuf parserScratchBuffer;
/*      */   private final Runnable defaultStrictCRLFCheck;
/*      */   private final HeaderParser headerParser;
/*      */   private final LineParser lineParser;
/*      */   private HttpMessage message;
/*      */   private long chunkSize;
/*  190 */   private long contentLength = Long.MIN_VALUE;
/*      */   
/*      */   private boolean chunked;
/*      */   private boolean isSwitchingToNonHttp1Protocol;
/*  194 */   private final AtomicBoolean resetRequested = new AtomicBoolean();
/*      */   
/*      */   private AsciiString name;
/*      */   
/*      */   private String value;
/*      */   
/*      */   private LastHttpContent trailer;
/*      */   
/*      */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/*      */     try {
/*  204 */       this.parserScratchBuffer.release();
/*      */     } finally {
/*  206 */       super.handlerRemoved0(ctx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum State
/*      */   {
/*  215 */     SKIP_CONTROL_CHARS,
/*  216 */     READ_INITIAL,
/*  217 */     READ_HEADER,
/*  218 */     READ_VARIABLE_LENGTH_CONTENT,
/*  219 */     READ_FIXED_LENGTH_CONTENT,
/*  220 */     READ_CHUNK_SIZE,
/*  221 */     READ_CHUNKED_CONTENT,
/*  222 */     READ_CHUNK_DELIMITER,
/*  223 */     READ_CHUNK_FOOTER,
/*  224 */     BAD_MESSAGE,
/*  225 */     UPGRADED;
/*      */   }
/*      */   
/*  228 */   private State currentState = State.SKIP_CONTROL_CHARS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected HttpObjectDecoder() {
/*  236 */     this(new HttpDecoderConfig());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported) {
/*  247 */     this((new HttpDecoderConfig())
/*  248 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  249 */         .setMaxHeaderSize(maxHeaderSize)
/*  250 */         .setMaxChunkSize(maxChunkSize)
/*  251 */         .setChunkedSupported(chunkedSupported));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported, boolean validateHeaders) {
/*  263 */     this((new HttpDecoderConfig())
/*  264 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  265 */         .setMaxHeaderSize(maxHeaderSize)
/*  266 */         .setMaxChunkSize(maxChunkSize)
/*  267 */         .setChunkedSupported(chunkedSupported)
/*  268 */         .setValidateHeaders(validateHeaders));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported, boolean validateHeaders, int initialBufferSize) {
/*  280 */     this((new HttpDecoderConfig())
/*  281 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  282 */         .setMaxHeaderSize(maxHeaderSize)
/*  283 */         .setMaxChunkSize(maxChunkSize)
/*  284 */         .setChunkedSupported(chunkedSupported)
/*  285 */         .setValidateHeaders(validateHeaders)
/*  286 */         .setInitialBufferSize(initialBufferSize));
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
/*      */   @Deprecated
/*      */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths) {
/*  299 */     this((new HttpDecoderConfig())
/*  300 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  301 */         .setMaxHeaderSize(maxHeaderSize)
/*  302 */         .setMaxChunkSize(maxChunkSize)
/*  303 */         .setChunkedSupported(chunkedSupported)
/*  304 */         .setValidateHeaders(validateHeaders)
/*  305 */         .setInitialBufferSize(initialBufferSize)
/*  306 */         .setAllowDuplicateContentLengths(allowDuplicateContentLengths));
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
/*      */   @Deprecated
/*      */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths, boolean allowPartialChunks) {
/*  319 */     this((new HttpDecoderConfig())
/*  320 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  321 */         .setMaxHeaderSize(maxHeaderSize)
/*  322 */         .setMaxChunkSize(maxChunkSize)
/*  323 */         .setChunkedSupported(chunkedSupported)
/*  324 */         .setValidateHeaders(validateHeaders)
/*  325 */         .setInitialBufferSize(initialBufferSize)
/*  326 */         .setAllowDuplicateContentLengths(allowDuplicateContentLengths)
/*  327 */         .setAllowPartialChunks(allowPartialChunks));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected HttpObjectDecoder(HttpDecoderConfig config) {
/*  334 */     ObjectUtil.checkNotNull(config, "config");
/*      */     
/*  336 */     this.parserScratchBuffer = Unpooled.buffer(config.getInitialBufferSize());
/*  337 */     this.defaultStrictCRLFCheck = config.isStrictLineParsing() ? THROW_INVALID_LINE_SEPARATOR : null;
/*  338 */     this.lineParser = new LineParser(this.parserScratchBuffer, config.getMaxInitialLineLength());
/*  339 */     this.headerParser = new HeaderParser(this.parserScratchBuffer, config.getMaxHeaderSize());
/*  340 */     this.maxChunkSize = config.getMaxChunkSize();
/*  341 */     this.chunkedSupported = config.isChunkedSupported();
/*  342 */     this.headersFactory = config.getHeadersFactory();
/*  343 */     this.trailersFactory = config.getTrailersFactory();
/*  344 */     this.validateHeaders = isValidating(this.headersFactory);
/*  345 */     this.allowDuplicateContentLengths = config.isAllowDuplicateContentLengths();
/*  346 */     this.allowPartialChunks = config.isAllowPartialChunks();
/*      */   }
/*      */   
/*      */   protected boolean isValidating(HttpHeadersFactory headersFactory) {
/*  350 */     if (headersFactory instanceof DefaultHttpHeadersFactory) {
/*  351 */       DefaultHttpHeadersFactory builder = (DefaultHttpHeadersFactory)headersFactory;
/*  352 */       return (builder.isValidatingHeaderNames() || builder.isValidatingHeaderValues());
/*      */     } 
/*  354 */     return true; } protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception { int i; int readLimit; int toRead;
/*      */     int readableBytes;
/*      */     int j;
/*      */     HttpContent chunk;
/*      */     ByteBuf content;
/*  359 */     if (this.resetRequested.get()) {
/*  360 */       resetNow();
/*      */     }
/*      */     
/*  363 */     switch (this.currentState) {
/*      */       case SKIP_CONTROL_CHARS:
/*      */       case READ_INITIAL:
/*      */         try {
/*  367 */           ByteBuf line = this.lineParser.parse(buffer, this.defaultStrictCRLFCheck);
/*  368 */           if (line == null) {
/*      */             return;
/*      */           }
/*  371 */           String[] initialLine = splitInitialLine(line);
/*  372 */           assert initialLine.length == 3 : "initialLine::length must be 3";
/*      */           
/*  374 */           this.message = createMessage(initialLine);
/*  375 */           this.currentState = State.READ_HEADER;
/*      */         }
/*  377 */         catch (Exception e) {
/*  378 */           out.add(invalidMessage(this.message, buffer, e)); return;
/*      */         } 
/*      */       case READ_HEADER:
/*      */         try {
/*  382 */           State nextState = readHeaders(buffer);
/*  383 */           if (nextState == null) {
/*      */             return;
/*      */           }
/*  386 */           this.currentState = nextState;
/*  387 */           switch (nextState) {
/*      */ 
/*      */             
/*      */             case SKIP_CONTROL_CHARS:
/*  391 */               addCurrentMessage(out);
/*  392 */               out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*  393 */               resetNow();
/*      */               return;
/*      */             case READ_CHUNK_SIZE:
/*  396 */               if (!this.chunkedSupported) {
/*  397 */                 throw new IllegalArgumentException("Chunked messages not supported");
/*      */               }
/*      */               
/*  400 */               addCurrentMessage(out);
/*      */               return;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  409 */           if (this.contentLength == 0L || (this.contentLength == -1L && isDecodingRequest())) {
/*  410 */             addCurrentMessage(out);
/*  411 */             out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*  412 */             resetNow();
/*      */             
/*      */             return;
/*      */           } 
/*  416 */           assert nextState == State.READ_FIXED_LENGTH_CONTENT || nextState == State.READ_VARIABLE_LENGTH_CONTENT;
/*      */ 
/*      */           
/*  419 */           addCurrentMessage(out);
/*      */           
/*  421 */           if (nextState == State.READ_FIXED_LENGTH_CONTENT)
/*      */           {
/*  423 */             this.chunkSize = this.contentLength;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*  429 */         } catch (Exception e) {
/*  430 */           out.add(invalidMessage(this.message, buffer, e));
/*      */           return;
/*      */         } 
/*      */       
/*      */       case READ_VARIABLE_LENGTH_CONTENT:
/*  435 */         i = Math.min(buffer.readableBytes(), this.maxChunkSize);
/*  436 */         if (i > 0) {
/*  437 */           ByteBuf byteBuf = buffer.readRetainedSlice(i);
/*  438 */           out.add(new DefaultHttpContent(byteBuf));
/*      */         } 
/*      */         return;
/*      */       
/*      */       case READ_FIXED_LENGTH_CONTENT:
/*  443 */         readLimit = buffer.readableBytes();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  451 */         if (readLimit == 0) {
/*      */           return;
/*      */         }
/*      */         
/*  455 */         j = Math.min(readLimit, this.maxChunkSize);
/*  456 */         if (j > this.chunkSize) {
/*  457 */           j = (int)this.chunkSize;
/*      */         }
/*  459 */         content = buffer.readRetainedSlice(j);
/*  460 */         this.chunkSize -= j;
/*      */         
/*  462 */         if (this.chunkSize == 0L) {
/*      */           
/*  464 */           out.add(new DefaultLastHttpContent(content, this.trailersFactory));
/*  465 */           resetNow();
/*      */         } else {
/*  467 */           out.add(new DefaultHttpContent(content));
/*      */         } 
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case READ_CHUNK_SIZE:
/*      */         try {
/*  476 */           ByteBuf line = this.lineParser.parse(buffer, THROW_INVALID_CHUNK_EXTENSION);
/*  477 */           if (line == null) {
/*      */             return;
/*      */           }
/*  480 */           int chunkSize = getChunkSize(line.array(), line.arrayOffset() + line.readerIndex(), line.readableBytes());
/*  481 */           this.chunkSize = chunkSize;
/*  482 */           if (chunkSize == 0) {
/*  483 */             this.currentState = State.READ_CHUNK_FOOTER;
/*      */             return;
/*      */           } 
/*  486 */           this.currentState = State.READ_CHUNKED_CONTENT;
/*      */         }
/*  488 */         catch (Exception e) {
/*  489 */           out.add(invalidChunk(buffer, e));
/*      */           return;
/*      */         } 
/*      */       case READ_CHUNKED_CONTENT:
/*  493 */         assert this.chunkSize <= 2147483647L;
/*  494 */         toRead = Math.min((int)this.chunkSize, this.maxChunkSize);
/*  495 */         if (!this.allowPartialChunks && buffer.readableBytes() < toRead) {
/*      */           return;
/*      */         }
/*  498 */         toRead = Math.min(toRead, buffer.readableBytes());
/*  499 */         if (toRead == 0) {
/*      */           return;
/*      */         }
/*  502 */         chunk = new DefaultHttpContent(buffer.readRetainedSlice(toRead));
/*  503 */         this.chunkSize -= toRead;
/*      */         
/*  505 */         out.add(chunk);
/*      */         
/*  507 */         if (this.chunkSize != 0L) {
/*      */           return;
/*      */         }
/*  510 */         this.currentState = State.READ_CHUNK_DELIMITER;
/*      */ 
/*      */       
/*      */       case READ_CHUNK_DELIMITER:
/*  514 */         if (buffer.readableBytes() >= 2) {
/*  515 */           int rIdx = buffer.readerIndex();
/*  516 */           if (buffer.getByte(rIdx) == 13 && buffer
/*  517 */             .getByte(rIdx + 1) == 10) {
/*  518 */             buffer.skipBytes(2);
/*  519 */             this.currentState = State.READ_CHUNK_SIZE;
/*      */           } else {
/*  521 */             out.add(invalidChunk(buffer, (Exception)new InvalidChunkTerminationException()));
/*      */           } 
/*      */         } 
/*      */         return;
/*      */       case READ_CHUNK_FOOTER:
/*      */         try {
/*  527 */           LastHttpContent trailer = readTrailingHeaders(buffer);
/*  528 */           if (trailer == null) {
/*      */             return;
/*      */           }
/*  531 */           out.add(trailer);
/*  532 */           resetNow();
/*      */           return;
/*  534 */         } catch (Exception e) {
/*  535 */           out.add(invalidChunk(buffer, e));
/*      */           return;
/*      */         } 
/*      */       
/*      */       case BAD_MESSAGE:
/*  540 */         buffer.skipBytes(buffer.readableBytes());
/*      */         break;
/*      */       
/*      */       case UPGRADED:
/*  544 */         readableBytes = buffer.readableBytes();
/*  545 */         if (readableBytes > 0)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  550 */           out.add(buffer.readBytes(readableBytes));
/*      */         }
/*      */         break;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*      */     boolean prematureClosure;
/*  561 */     super.decodeLast(ctx, in, out);
/*      */     
/*  563 */     if (this.resetRequested.get())
/*      */     {
/*      */       
/*  566 */       resetNow();
/*      */     }
/*      */ 
/*      */     
/*  570 */     switch (this.currentState) {
/*      */       case READ_VARIABLE_LENGTH_CONTENT:
/*  572 */         if (!this.chunked && !in.isReadable()) {
/*      */           
/*  574 */           out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*  575 */           resetNow();
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case READ_HEADER:
/*  581 */         out.add(invalidMessage(this.message, Unpooled.EMPTY_BUFFER, (Exception)new PrematureChannelClosureException("Connection closed before received headers")));
/*      */         
/*  583 */         resetNow();
/*      */         return;
/*      */ 
/*      */       
/*      */       case READ_CHUNK_SIZE:
/*      */       case READ_FIXED_LENGTH_CONTENT:
/*      */       case READ_CHUNKED_CONTENT:
/*      */       case READ_CHUNK_DELIMITER:
/*      */       case READ_CHUNK_FOOTER:
/*  592 */         if (isDecodingRequest() || this.chunked) {
/*      */           
/*  594 */           prematureClosure = true;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  599 */           prematureClosure = (this.contentLength > 0L);
/*      */         } 
/*  601 */         if (!prematureClosure) {
/*  602 */           out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*      */         }
/*  604 */         resetNow();
/*      */         return;
/*      */       
/*      */       case SKIP_CONTROL_CHARS:
/*      */       case READ_INITIAL:
/*      */       case BAD_MESSAGE:
/*      */       case UPGRADED:
/*      */         return;
/*      */     } 
/*  613 */     throw new IllegalStateException("Unhandled state " + this.currentState);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/*  619 */     if (evt instanceof HttpExpectationFailedEvent) {
/*  620 */       switch (this.currentState) {
/*      */         case READ_CHUNK_SIZE:
/*      */         case READ_VARIABLE_LENGTH_CONTENT:
/*      */         case READ_FIXED_LENGTH_CONTENT:
/*  624 */           reset();
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/*      */     }
/*  630 */     super.userEventTriggered(ctx, evt);
/*      */   }
/*      */   
/*      */   private void addCurrentMessage(List<Object> out) {
/*  634 */     HttpMessage message = this.message;
/*  635 */     assert message != null;
/*  636 */     this.message = null;
/*  637 */     out.add(message);
/*      */   }
/*      */   
/*      */   protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/*  641 */     if (msg instanceof HttpResponse) {
/*  642 */       HttpResponse res = (HttpResponse)msg;
/*  643 */       HttpResponseStatus status = res.status();
/*  644 */       int code = status.code();
/*  645 */       HttpStatusClass statusClass = status.codeClass();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  652 */       if (statusClass == HttpStatusClass.INFORMATIONAL)
/*      */       {
/*  654 */         return (code != 101 || res.headers().contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ACCEPT) || 
/*  655 */           !res.headers().contains((CharSequence)HttpHeaderNames.UPGRADE, (CharSequence)HttpHeaderValues.WEBSOCKET, true));
/*      */       }
/*      */       
/*  658 */       switch (code) { case 204:
/*      */         case 304:
/*  660 */           return true; }
/*      */       
/*  662 */       return false;
/*      */     } 
/*      */     
/*  665 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSwitchingToNonHttp1Protocol(HttpResponse msg) {
/*  673 */     if (msg.status().code() != HttpResponseStatus.SWITCHING_PROTOCOLS.code()) {
/*  674 */       return false;
/*      */     }
/*  676 */     String newProtocol = msg.headers().get((CharSequence)HttpHeaderNames.UPGRADE);
/*  677 */     return (newProtocol == null || (
/*  678 */       !newProtocol.contains(HttpVersion.HTTP_1_0.text()) && 
/*  679 */       !newProtocol.contains(HttpVersion.HTTP_1_1.text())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  687 */     this.resetRequested.lazySet(true);
/*      */   }
/*      */   
/*      */   private void resetNow() {
/*  691 */     this.message = null;
/*  692 */     this.name = null;
/*  693 */     this.value = null;
/*  694 */     this.contentLength = Long.MIN_VALUE;
/*  695 */     this.chunked = false;
/*  696 */     this.lineParser.reset();
/*  697 */     this.headerParser.reset();
/*  698 */     this.trailer = null;
/*  699 */     if (this.isSwitchingToNonHttp1Protocol) {
/*  700 */       this.isSwitchingToNonHttp1Protocol = false;
/*  701 */       this.currentState = State.UPGRADED;
/*      */       
/*      */       return;
/*      */     } 
/*  705 */     this.resetRequested.lazySet(false);
/*  706 */     this.currentState = State.SKIP_CONTROL_CHARS;
/*      */   }
/*      */   
/*      */   private HttpMessage invalidMessage(HttpMessage current, ByteBuf in, Exception cause) {
/*  710 */     this.currentState = State.BAD_MESSAGE;
/*  711 */     this.message = null;
/*  712 */     this.trailer = null;
/*      */ 
/*      */ 
/*      */     
/*  716 */     in.skipBytes(in.readableBytes());
/*      */     
/*  718 */     if (current == null) {
/*  719 */       current = createInvalidMessage();
/*      */     }
/*  721 */     current.setDecoderResult(DecoderResult.failure(cause));
/*      */     
/*  723 */     return current;
/*      */   }
/*      */   
/*      */   private HttpContent invalidChunk(ByteBuf in, Exception cause) {
/*  727 */     this.currentState = State.BAD_MESSAGE;
/*  728 */     this.message = null;
/*  729 */     this.trailer = null;
/*      */ 
/*      */ 
/*      */     
/*  733 */     in.skipBytes(in.readableBytes());
/*      */     
/*  735 */     HttpContent chunk = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
/*  736 */     chunk.setDecoderResult(DecoderResult.failure(cause));
/*  737 */     return chunk;
/*      */   }
/*      */   
/*      */   private State readHeaders(ByteBuf buffer) {
/*  741 */     HttpMessage message = this.message;
/*  742 */     HttpHeaders headers = message.headers();
/*      */     
/*  744 */     HeaderParser headerParser = this.headerParser;
/*      */     
/*  746 */     ByteBuf line = headerParser.parse(buffer, this.defaultStrictCRLFCheck);
/*  747 */     if (line == null) {
/*  748 */       return null;
/*      */     }
/*  750 */     int lineLength = line.readableBytes();
/*  751 */     while (lineLength > 0) {
/*  752 */       byte[] lineContent = line.array();
/*  753 */       int startLine = line.arrayOffset() + line.readerIndex();
/*  754 */       byte firstChar = lineContent[startLine];
/*  755 */       if (this.name != null && (firstChar == 32 || firstChar == 9)) {
/*      */ 
/*      */         
/*  758 */         String trimmedLine = langAsciiString(lineContent, startLine, lineLength).trim();
/*  759 */         String valueStr = this.value;
/*  760 */         this.value = valueStr + ' ' + trimmedLine;
/*      */       } else {
/*  762 */         if (this.name != null) {
/*  763 */           headers.add((CharSequence)this.name, this.value);
/*      */         }
/*  765 */         splitHeader(lineContent, startLine, lineLength);
/*      */       } 
/*      */       
/*  768 */       line = headerParser.parse(buffer, this.defaultStrictCRLFCheck);
/*  769 */       if (line == null) {
/*  770 */         return null;
/*      */       }
/*  772 */       lineLength = line.readableBytes();
/*      */     } 
/*      */ 
/*      */     
/*  776 */     if (this.name != null) {
/*  777 */       headers.add((CharSequence)this.name, this.value);
/*      */     }
/*      */ 
/*      */     
/*  781 */     this.name = null;
/*  782 */     this.value = null;
/*      */ 
/*      */     
/*  785 */     HttpMessageDecoderResult decoderResult = new HttpMessageDecoderResult(this.lineParser.size, headerParser.size);
/*  786 */     message.setDecoderResult(decoderResult);
/*      */     
/*  788 */     List<String> contentLengthFields = headers.getAll((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/*  789 */     if (!contentLengthFields.isEmpty()) {
/*  790 */       HttpVersion version = message.protocolVersion();
/*      */       
/*  792 */       boolean isHttp10OrEarlier = (version.majorVersion() < 1 || (version.majorVersion() == 1 && version.minorVersion() == 0));
/*      */ 
/*      */       
/*  795 */       this.contentLength = HttpUtil.normalizeAndGetContentLength((List)contentLengthFields, isHttp10OrEarlier, this.allowDuplicateContentLengths);
/*      */       
/*  797 */       if (this.contentLength != -1L) {
/*  798 */         String lengthValue = ((String)contentLengthFields.get(0)).trim();
/*  799 */         if (contentLengthFields.size() > 1 || 
/*  800 */           !lengthValue.equals(Long.toString(this.contentLength))) {
/*  801 */           headers.set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, Long.valueOf(this.contentLength));
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  807 */       this.contentLength = HttpUtil.getWebSocketContentLength(message);
/*      */     } 
/*  809 */     if (!isDecodingRequest() && message instanceof HttpResponse) {
/*  810 */       HttpResponse res = (HttpResponse)message;
/*  811 */       this.isSwitchingToNonHttp1Protocol = isSwitchingToNonHttp1Protocol(res);
/*      */     } 
/*  813 */     if (isContentAlwaysEmpty(message)) {
/*  814 */       HttpUtil.setTransferEncodingChunked(message, false);
/*  815 */       return State.SKIP_CONTROL_CHARS;
/*      */     } 
/*  817 */     if (HttpUtil.isTransferEncodingChunked(message)) {
/*  818 */       this.chunked = true;
/*  819 */       if (!contentLengthFields.isEmpty() && message.protocolVersion() == HttpVersion.HTTP_1_1) {
/*  820 */         handleTransferEncodingChunkedWithContentLength(message);
/*      */       }
/*  822 */       return State.READ_CHUNK_SIZE;
/*      */     } 
/*  824 */     if (this.contentLength >= 0L) {
/*  825 */       return State.READ_FIXED_LENGTH_CONTENT;
/*      */     }
/*  827 */     return State.READ_VARIABLE_LENGTH_CONTENT;
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
/*      */   
/*      */   protected void handleTransferEncodingChunkedWithContentLength(HttpMessage message) {
/*  852 */     message.headers().remove((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/*  853 */     this.contentLength = Long.MIN_VALUE;
/*      */   }
/*      */   
/*      */   private LastHttpContent readTrailingHeaders(ByteBuf buffer) {
/*  857 */     HeaderParser headerParser = this.headerParser;
/*  858 */     ByteBuf line = headerParser.parse(buffer, this.defaultStrictCRLFCheck);
/*  859 */     if (line == null) {
/*  860 */       return null;
/*      */     }
/*  862 */     LastHttpContent trailer = this.trailer;
/*  863 */     int lineLength = line.readableBytes();
/*  864 */     if (lineLength == 0 && trailer == null)
/*      */     {
/*      */       
/*  867 */       return LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     }
/*      */     
/*  870 */     CharSequence lastHeader = null;
/*  871 */     if (trailer == null) {
/*  872 */       trailer = this.trailer = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.trailersFactory);
/*      */     }
/*  874 */     while (lineLength > 0) {
/*  875 */       byte[] lineContent = line.array();
/*  876 */       int startLine = line.arrayOffset() + line.readerIndex();
/*  877 */       byte firstChar = lineContent[startLine];
/*  878 */       if (lastHeader != null && (firstChar == 32 || firstChar == 9)) {
/*  879 */         List<String> current = trailer.trailingHeaders().getAll(lastHeader);
/*  880 */         if (!current.isEmpty()) {
/*  881 */           int lastPos = current.size() - 1;
/*      */ 
/*      */           
/*  884 */           String lineTrimmed = langAsciiString(lineContent, startLine, line.readableBytes()).trim();
/*  885 */           String currentLastPos = current.get(lastPos);
/*  886 */           current.set(lastPos, currentLastPos + lineTrimmed);
/*      */         } 
/*      */       } else {
/*  889 */         splitHeader(lineContent, startLine, lineLength);
/*  890 */         AsciiString headerName = this.name;
/*  891 */         if (!HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase((CharSequence)headerName) && 
/*  892 */           !HttpHeaderNames.TRANSFER_ENCODING.contentEqualsIgnoreCase((CharSequence)headerName) && 
/*  893 */           !HttpHeaderNames.TRAILER.contentEqualsIgnoreCase((CharSequence)headerName)) {
/*  894 */           trailer.trailingHeaders().add((CharSequence)headerName, this.value);
/*      */         }
/*  896 */         AsciiString asciiString1 = this.name;
/*      */         
/*  898 */         this.name = null;
/*  899 */         this.value = null;
/*      */       } 
/*  901 */       line = headerParser.parse(buffer, this.defaultStrictCRLFCheck);
/*  902 */       if (line == null) {
/*  903 */         return null;
/*      */       }
/*  905 */       lineLength = line.readableBytes();
/*      */     } 
/*      */     
/*  908 */     this.trailer = null;
/*  909 */     return trailer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int skipWhiteSpaces(byte[] hex, int start, int length) {
/*  920 */     for (int i = 0; i < length; i++) {
/*  921 */       if (!isWhitespace(hex[start + i])) {
/*  922 */         return i;
/*      */       }
/*      */     } 
/*  925 */     return length;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getChunkSize(byte[] hex, int start, int length) {
/*  930 */     int skipped = skipWhiteSpaces(hex, start, length);
/*  931 */     if (skipped == length)
/*      */     {
/*  933 */       throw new NumberFormatException();
/*      */     }
/*  935 */     start += skipped;
/*  936 */     length -= skipped;
/*  937 */     int result = 0;
/*  938 */     for (int i = 0; i < length; i++) {
/*  939 */       int digit = StringUtil.decodeHexNibble(hex[start + i]);
/*  940 */       if (digit == -1) {
/*      */         
/*  942 */         byte b = hex[start + i];
/*  943 */         if (b == 59 || isControlOrWhitespaceAsciiChar(b)) {
/*  944 */           if (i == 0)
/*      */           {
/*  946 */             throw new NumberFormatException("Empty chunk size");
/*      */           }
/*  948 */           return result;
/*      */         } 
/*      */         
/*  951 */         throw new NumberFormatException("Invalid character in chunk size");
/*      */       } 
/*  953 */       result *= 16;
/*  954 */       result += digit;
/*  955 */       if (result < 0) {
/*  956 */         throw new NumberFormatException("Chunk size overflow: " + result);
/*      */       }
/*      */     } 
/*  959 */     return result;
/*      */   }
/*      */   
/*      */   private String[] splitInitialLine(ByteBuf asciiBuffer) {
/*  963 */     byte[] asciiBytes = asciiBuffer.array();
/*      */     
/*  965 */     int arrayOffset = asciiBuffer.arrayOffset();
/*      */     
/*  967 */     int startContent = arrayOffset + asciiBuffer.readerIndex();
/*      */     
/*  969 */     int end = startContent + asciiBuffer.readableBytes();
/*      */     
/*  971 */     byte lastByte = asciiBytes[end - 1];
/*  972 */     if (isControlOrWhitespaceAsciiChar(lastByte) && (
/*  973 */       isDecodingRequest() || !isOWS(lastByte)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  979 */       throw new IllegalArgumentException("Illegal character in request line: 0x" + 
/*  980 */           Integer.toHexString(lastByte));
/*      */     }
/*      */ 
/*      */     
/*  984 */     int aStart = findNonSPLenient(asciiBytes, startContent, end);
/*  985 */     int aEnd = findSPLenient(asciiBytes, aStart, end);
/*      */     
/*  987 */     int bStart = findNonSPLenient(asciiBytes, aEnd, end);
/*  988 */     int bEnd = findSPLenient(asciiBytes, bStart, end);
/*      */     
/*  990 */     int cStart = findNonSPLenient(asciiBytes, bEnd, end);
/*  991 */     int cEnd = findEndOfString(asciiBytes, Math.max(cStart - 1, startContent), end);
/*      */     
/*  993 */     return new String[] {
/*  994 */         splitFirstWordInitialLine(asciiBytes, aStart, aEnd - aStart), 
/*  995 */         splitSecondWordInitialLine(asciiBytes, bStart, bEnd - bStart), 
/*  996 */         (cStart < cEnd) ? splitThirdWordInitialLine(asciiBytes, cStart, cEnd - cStart) : "" };
/*      */   }
/*      */   
/*      */   protected String splitFirstWordInitialLine(byte[] asciiContent, int start, int length) {
/* 1000 */     return langAsciiString(asciiContent, start, length);
/*      */   }
/*      */   
/*      */   protected String splitSecondWordInitialLine(byte[] asciiContent, int start, int length) {
/* 1004 */     return langAsciiString(asciiContent, start, length);
/*      */   }
/*      */   
/*      */   protected String splitThirdWordInitialLine(byte[] asciiContent, int start, int length) {
/* 1008 */     return langAsciiString(asciiContent, start, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String langAsciiString(byte[] asciiContent, int start, int length) {
/* 1015 */     if (length == 0) {
/* 1016 */       return "";
/*      */     }
/*      */     
/* 1019 */     if (start == 0) {
/* 1020 */       if (length == asciiContent.length) {
/* 1021 */         return new String(asciiContent, 0, 0, asciiContent.length);
/*      */       }
/* 1023 */       return new String(asciiContent, 0, 0, length);
/*      */     } 
/* 1025 */     return new String(asciiContent, 0, start, length);
/*      */   }
/*      */   
/*      */   private void splitHeader(byte[] line, int start, int length) {
/* 1029 */     int end = start + length;
/*      */     
/* 1031 */     int nameStart = start;
/*      */     
/* 1033 */     boolean isDecodingRequest = isDecodingRequest(); int nameEnd;
/* 1034 */     for (nameEnd = nameStart; nameEnd < end; nameEnd++) {
/* 1035 */       byte ch = line[nameEnd];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1045 */       if (ch == 58 || (!isDecodingRequest && 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1050 */         isOWS(ch))) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1055 */     if (nameEnd == end)
/*      */     {
/* 1057 */       throw new IllegalArgumentException("No colon found");
/*      */     }
/*      */     int colonEnd;
/* 1060 */     for (colonEnd = nameEnd; colonEnd < end; colonEnd++) {
/* 1061 */       if (line[colonEnd] == 58) {
/* 1062 */         colonEnd++;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1066 */     this.name = splitHeaderName(line, nameStart, nameEnd - nameStart);
/* 1067 */     int valueStart = findNonWhitespace(line, colonEnd, end);
/* 1068 */     if (valueStart == end) {
/* 1069 */       this.value = "";
/*      */     } else {
/* 1071 */       int valueEnd = findEndOfString(line, start, end);
/*      */       
/* 1073 */       this.value = langAsciiString(line, valueStart, valueEnd - valueStart);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected AsciiString splitHeaderName(byte[] sb, int start, int length) {
/* 1078 */     return new AsciiString(sb, start, length, true);
/*      */   }
/*      */   
/*      */   private static int findNonSPLenient(byte[] sb, int offset, int end) {
/* 1082 */     for (int result = offset; result < end; ) {
/* 1083 */       byte c = sb[result];
/*      */       
/* 1085 */       if (isSPLenient(c)) {
/*      */         result++; continue;
/*      */       } 
/* 1088 */       if (isWhitespace(c))
/*      */       {
/* 1090 */         throw new IllegalArgumentException("Invalid separator");
/*      */       }
/* 1092 */       return result;
/*      */     } 
/* 1094 */     return end;
/*      */   }
/*      */   
/*      */   private static int findSPLenient(byte[] sb, int offset, int end) {
/* 1098 */     for (int result = offset; result < end; result++) {
/* 1099 */       if (isSPLenient(sb[result])) {
/* 1100 */         return result;
/*      */       }
/*      */     } 
/* 1103 */     return end;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1111 */   private static final boolean[] SP_LENIENT_BYTES = new boolean[256]; static {
/* 1112 */     SP_LENIENT_BYTES[160] = true;
/* 1113 */     SP_LENIENT_BYTES[137] = true;
/* 1114 */     SP_LENIENT_BYTES[139] = true;
/* 1115 */     SP_LENIENT_BYTES[140] = true;
/* 1116 */     SP_LENIENT_BYTES[141] = true;
/*      */   }
/* 1118 */   private static final boolean[] LATIN_WHITESPACE = new boolean[256]; static { byte b;
/* 1119 */     for (b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b = (byte)(b + 1)) {
/* 1120 */       LATIN_WHITESPACE[128 + b] = Character.isWhitespace(b);
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isSPLenient(byte c) {
/* 1126 */     return SP_LENIENT_BYTES[c + 128];
/*      */   }
/*      */   
/*      */   private static boolean isWhitespace(byte b) {
/* 1130 */     return LATIN_WHITESPACE[b + 128];
/*      */   }
/*      */   
/*      */   private static int findNonWhitespace(byte[] sb, int offset, int end) {
/* 1134 */     for (int result = offset; result < end; result++) {
/* 1135 */       byte c = sb[result];
/* 1136 */       if (!isWhitespace(c))
/* 1137 */         return result; 
/* 1138 */       if (!isOWS(c))
/*      */       {
/* 1140 */         throw new IllegalArgumentException("Invalid separator, only a single space or horizontal tab allowed, but received a '" + c + "' (0x" + 
/* 1141 */             Integer.toHexString(c) + ")");
/*      */       }
/*      */     } 
/* 1144 */     return end;
/*      */   }
/*      */   
/*      */   private static int findEndOfString(byte[] sb, int start, int end) {
/* 1148 */     for (int result = end - 1; result > start; result--) {
/* 1149 */       if (!isOWS(sb[result])) {
/* 1150 */         return result + 1;
/*      */       }
/*      */     } 
/* 1153 */     return 0;
/*      */   }
/*      */   
/*      */   private static boolean isOWS(byte ch) {
/* 1157 */     return (ch == 32 || ch == 9);
/*      */   }
/*      */   
/*      */   private static class HeaderParser {
/*      */     protected final ByteBuf seq;
/*      */     protected final int maxLength;
/*      */     int size;
/*      */     
/*      */     HeaderParser(ByteBuf seq, int maxLength) {
/* 1166 */       this.seq = seq;
/* 1167 */       this.maxLength = maxLength;
/*      */     }
/*      */     
/*      */     public ByteBuf parse(ByteBuf buffer, Runnable strictCRLFCheck) {
/* 1171 */       int endOfSeqIncluded, readableBytes = buffer.readableBytes();
/* 1172 */       int readerIndex = buffer.readerIndex();
/* 1173 */       int maxBodySize = this.maxLength - this.size;
/* 1174 */       assert maxBodySize >= 0;
/*      */ 
/*      */       
/* 1177 */       long maxBodySizeWithCRLF = maxBodySize + 2L;
/* 1178 */       int toProcess = (int)Math.min(maxBodySizeWithCRLF, readableBytes);
/* 1179 */       int toIndexExclusive = readerIndex + toProcess;
/* 1180 */       assert toIndexExclusive >= readerIndex;
/* 1181 */       int indexOfLf = buffer.indexOf(readerIndex, toIndexExclusive, (byte)10);
/* 1182 */       if (indexOfLf == -1) {
/* 1183 */         if (readableBytes > maxBodySize)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1188 */           throw newException(this.maxLength);
/*      */         }
/* 1190 */         return null;
/*      */       } 
/*      */       
/* 1193 */       if (indexOfLf > readerIndex && buffer.getByte(indexOfLf - 1) == 13) {
/*      */         
/* 1195 */         endOfSeqIncluded = indexOfLf - 1;
/*      */       } else {
/* 1197 */         if (strictCRLFCheck != null) {
/* 1198 */           strictCRLFCheck.run();
/*      */         }
/* 1200 */         endOfSeqIncluded = indexOfLf;
/*      */       } 
/* 1202 */       int newSize = endOfSeqIncluded - readerIndex;
/* 1203 */       if (newSize == 0) {
/* 1204 */         this.seq.clear();
/* 1205 */         buffer.readerIndex(indexOfLf + 1);
/* 1206 */         return this.seq;
/*      */       } 
/* 1208 */       int size = this.size + newSize;
/* 1209 */       if (size > this.maxLength) {
/* 1210 */         throw newException(this.maxLength);
/*      */       }
/* 1212 */       this.size = size;
/* 1213 */       this.seq.clear();
/* 1214 */       this.seq.writeBytes(buffer, readerIndex, newSize);
/* 1215 */       buffer.readerIndex(indexOfLf + 1);
/* 1216 */       return this.seq;
/*      */     }
/*      */     
/*      */     public void reset() {
/* 1220 */       this.size = 0;
/*      */     }
/*      */     
/*      */     protected TooLongFrameException newException(int maxLength) {
/* 1224 */       return new TooLongHttpHeaderException("HTTP header is larger than " + maxLength + " bytes.");
/*      */     }
/*      */   }
/*      */   
/*      */   private final class LineParser
/*      */     extends HeaderParser {
/*      */     LineParser(ByteBuf seq, int maxLength) {
/* 1231 */       super(seq, maxLength);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteBuf parse(ByteBuf buffer, Runnable strictCRLFCheck) {
/* 1237 */       reset();
/* 1238 */       int readableBytes = buffer.readableBytes();
/* 1239 */       if (readableBytes == 0) {
/* 1240 */         return null;
/*      */       }
/* 1242 */       if (HttpObjectDecoder.this.currentState == HttpObjectDecoder.State.SKIP_CONTROL_CHARS && 
/* 1243 */         skipControlChars(buffer, readableBytes, buffer.readerIndex())) {
/* 1244 */         return null;
/*      */       }
/* 1246 */       return super.parse(buffer, strictCRLFCheck);
/*      */     }
/*      */     
/*      */     private boolean skipControlChars(ByteBuf buffer, int readableBytes, int readerIndex) {
/* 1250 */       assert HttpObjectDecoder.this.currentState == HttpObjectDecoder.State.SKIP_CONTROL_CHARS;
/* 1251 */       int maxToSkip = Math.min(this.maxLength, readableBytes);
/* 1252 */       int firstNonControlIndex = buffer.forEachByte(readerIndex, maxToSkip, HttpObjectDecoder.SKIP_CONTROL_CHARS_BYTES);
/* 1253 */       if (firstNonControlIndex == -1) {
/* 1254 */         buffer.skipBytes(maxToSkip);
/* 1255 */         if (readableBytes > this.maxLength) {
/* 1256 */           throw newException(this.maxLength);
/*      */         }
/* 1258 */         return true;
/*      */       } 
/*      */       
/* 1261 */       buffer.readerIndex(firstNonControlIndex);
/* 1262 */       HttpObjectDecoder.this.currentState = HttpObjectDecoder.State.READ_INITIAL;
/* 1263 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     protected TooLongFrameException newException(int maxLength) {
/* 1268 */       return new TooLongHttpLineException("An HTTP line is larger than " + maxLength + " bytes.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1275 */   private static final boolean[] ISO_CONTROL_OR_WHITESPACE = new boolean[256]; static {
/* 1276 */     for (b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b = (byte)(b + 1)) {
/* 1277 */       ISO_CONTROL_OR_WHITESPACE[128 + b] = (Character.isISOControl(b) || isWhitespace(b));
/*      */     }
/*      */   }
/*      */   
/* 1281 */   private static final ByteProcessor SKIP_CONTROL_CHARS_BYTES = new ByteProcessor()
/*      */     {
/*      */       public boolean process(byte value)
/*      */       {
/* 1285 */         return HttpObjectDecoder.ISO_CONTROL_OR_WHITESPACE[128 + value];
/*      */       }
/*      */     };
/*      */   
/*      */   private static boolean isControlOrWhitespaceAsciiChar(byte b) {
/* 1290 */     return ISO_CONTROL_OR_WHITESPACE[128 + b];
/*      */   }
/*      */   
/*      */   protected abstract boolean isDecodingRequest();
/*      */   
/*      */   protected abstract HttpMessage createMessage(String[] paramArrayOfString) throws Exception;
/*      */   
/*      */   protected abstract HttpMessage createInvalidMessage();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpObjectDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */