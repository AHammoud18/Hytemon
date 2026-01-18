/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.DefaultHttpHeadersFactory;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeadersFactory;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpUtil;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyHttpDecoder
/*     */   extends MessageToMessageDecoder<SpdyFrame>
/*     */ {
/*     */   private final int spdyVersion;
/*     */   private final int maxContentLength;
/*     */   private final Map<Integer, FullHttpMessage> messageMap;
/*     */   private final HttpHeadersFactory headersFactory;
/*     */   private final HttpHeadersFactory trailersFactory;
/*     */   
/*     */   public SpdyHttpDecoder(SpdyVersion version, int maxContentLength) {
/*  67 */     this(version, maxContentLength, new HashMap<>(), 
/*  68 */         (HttpHeadersFactory)DefaultHttpHeadersFactory.headersFactory(), (HttpHeadersFactory)DefaultHttpHeadersFactory.trailersFactory());
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
/*     */   public SpdyHttpDecoder(SpdyVersion version, int maxContentLength, boolean validateHeaders) {
/*  84 */     this(version, maxContentLength, new HashMap<>(), validateHeaders);
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
/*     */   protected SpdyHttpDecoder(SpdyVersion version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap) {
/*  97 */     this(version, maxContentLength, messageMap, 
/*  98 */         (HttpHeadersFactory)DefaultHttpHeadersFactory.headersFactory(), (HttpHeadersFactory)DefaultHttpHeadersFactory.trailersFactory());
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
/*     */   @Deprecated
/*     */   protected SpdyHttpDecoder(SpdyVersion version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap, boolean validateHeaders) {
/* 116 */     this(version, maxContentLength, messageMap, 
/* 117 */         (HttpHeadersFactory)DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders), 
/* 118 */         (HttpHeadersFactory)DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders));
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
/*     */   protected SpdyHttpDecoder(SpdyVersion version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap, HttpHeadersFactory headersFactory, HttpHeadersFactory trailersFactory) {
/* 134 */     super(SpdyFrame.class);
/* 135 */     this.spdyVersion = ((SpdyVersion)ObjectUtil.checkNotNull(version, "version")).version();
/* 136 */     this.maxContentLength = ObjectUtil.checkPositive(maxContentLength, "maxContentLength");
/* 137 */     this.messageMap = messageMap;
/* 138 */     this.headersFactory = headersFactory;
/* 139 */     this.trailersFactory = trailersFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 145 */     for (Map.Entry<Integer, FullHttpMessage> entry : this.messageMap.entrySet()) {
/* 146 */       ReferenceCountUtil.safeRelease(entry.getValue());
/*     */     }
/* 148 */     this.messageMap.clear();
/* 149 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   protected FullHttpMessage putMessage(int streamId, FullHttpMessage message) {
/* 153 */     return this.messageMap.put(Integer.valueOf(streamId), message);
/*     */   }
/*     */   
/*     */   protected FullHttpMessage getMessage(int streamId) {
/* 157 */     return this.messageMap.get(Integer.valueOf(streamId));
/*     */   }
/*     */   
/*     */   protected FullHttpMessage removeMessage(int streamId) {
/* 161 */     return this.messageMap.remove(Integer.valueOf(streamId));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, SpdyFrame msg, List<Object> out) throws Exception {
/* 167 */     if (msg instanceof SpdySynStreamFrame) {
/*     */ 
/*     */       
/* 170 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 171 */       int streamId = spdySynStreamFrame.streamId();
/*     */       
/* 173 */       if (SpdyCodecUtil.isServerId(streamId)) {
/*     */         
/* 175 */         int associatedToStreamId = spdySynStreamFrame.associatedStreamId();
/*     */ 
/*     */ 
/*     */         
/* 179 */         if (associatedToStreamId == 0) {
/* 180 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */           
/* 182 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */         
/* 189 */         if (spdySynStreamFrame.isLast()) {
/* 190 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 192 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 198 */         if (spdySynStreamFrame.isTruncated()) {
/* 199 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */           
/* 201 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/* 206 */           FullHttpRequest httpRequestWithEntity = createHttpRequest(spdySynStreamFrame, ctx.alloc());
/*     */ 
/*     */           
/* 209 */           httpRequestWithEntity.headers().setInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID, streamId);
/* 210 */           httpRequestWithEntity.headers().setInt((CharSequence)SpdyHttpHeaders.Names.ASSOCIATED_TO_STREAM_ID, associatedToStreamId);
/* 211 */           httpRequestWithEntity.headers().setInt((CharSequence)SpdyHttpHeaders.Names.PRIORITY, spdySynStreamFrame.priority());
/*     */           
/* 213 */           out.add(httpRequestWithEntity);
/*     */         }
/* 215 */         catch (Throwable ignored) {
/* 216 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 218 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 225 */         if (spdySynStreamFrame.isTruncated()) {
/* 226 */           SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 227 */           spdySynReplyFrame.setLast(true);
/* 228 */           SpdyHeaders frameHeaders = spdySynReplyFrame.headers();
/* 229 */           frameHeaders.setInt(SpdyHeaders.HttpNames.STATUS, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.code());
/* 230 */           frameHeaders.setObject(SpdyHeaders.HttpNames.VERSION, HttpVersion.HTTP_1_0);
/* 231 */           ctx.writeAndFlush(spdySynReplyFrame);
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/* 236 */           FullHttpRequest httpRequestWithEntity = createHttpRequest(spdySynStreamFrame, ctx.alloc());
/*     */ 
/*     */           
/* 239 */           httpRequestWithEntity.headers().setInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID, streamId);
/*     */           
/* 241 */           if (spdySynStreamFrame.isLast()) {
/* 242 */             out.add(httpRequestWithEntity);
/*     */           } else {
/*     */             
/* 245 */             putMessage(streamId, (FullHttpMessage)httpRequestWithEntity);
/*     */           } 
/* 247 */         } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */           
/* 251 */           SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 252 */           spdySynReplyFrame.setLast(true);
/* 253 */           SpdyHeaders frameHeaders = spdySynReplyFrame.headers();
/* 254 */           frameHeaders.setInt(SpdyHeaders.HttpNames.STATUS, HttpResponseStatus.BAD_REQUEST.code());
/* 255 */           frameHeaders.setObject(SpdyHeaders.HttpNames.VERSION, HttpVersion.HTTP_1_0);
/* 256 */           ctx.writeAndFlush(spdySynReplyFrame);
/*     */         }
/*     */       
/*     */       } 
/* 260 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */       
/* 262 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 263 */       int streamId = spdySynReplyFrame.streamId();
/*     */ 
/*     */ 
/*     */       
/* 267 */       if (spdySynReplyFrame.isTruncated()) {
/* 268 */         SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */         
/* 270 */         ctx.writeAndFlush(spdyRstStreamFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 276 */         FullHttpResponse httpResponseWithEntity = createHttpResponse(spdySynReplyFrame, ctx.alloc());
/*     */ 
/*     */         
/* 279 */         httpResponseWithEntity.headers().setInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID, streamId);
/*     */         
/* 281 */         if (spdySynReplyFrame.isLast()) {
/* 282 */           HttpUtil.setContentLength((HttpMessage)httpResponseWithEntity, 0L);
/* 283 */           out.add(httpResponseWithEntity);
/*     */         } else {
/*     */           
/* 286 */           putMessage(streamId, (FullHttpMessage)httpResponseWithEntity);
/*     */         } 
/* 288 */       } catch (Throwable t) {
/*     */ 
/*     */         
/* 291 */         SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/* 293 */         ctx.writeAndFlush(spdyRstStreamFrame);
/*     */       }
/*     */     
/* 296 */     } else if (msg instanceof SpdyHeadersFrame) {
/*     */       FullHttpResponse fullHttpResponse;
/* 298 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 299 */       int streamId = spdyHeadersFrame.streamId();
/* 300 */       FullHttpMessage fullHttpMessage = getMessage(streamId);
/*     */       
/* 302 */       if (fullHttpMessage == null) {
/*     */         
/* 304 */         if (SpdyCodecUtil.isServerId(streamId)) {
/*     */ 
/*     */ 
/*     */           
/* 308 */           if (spdyHeadersFrame.isTruncated()) {
/* 309 */             SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */             
/* 311 */             ctx.writeAndFlush(spdyRstStreamFrame);
/*     */             
/*     */             return;
/*     */           } 
/*     */           try {
/* 316 */             fullHttpResponse = createHttpResponse(spdyHeadersFrame, ctx.alloc());
/*     */ 
/*     */             
/* 319 */             fullHttpResponse.headers().setInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID, streamId);
/*     */             
/* 321 */             if (spdyHeadersFrame.isLast()) {
/* 322 */               HttpUtil.setContentLength((HttpMessage)fullHttpResponse, 0L);
/* 323 */               out.add(fullHttpResponse);
/*     */             } else {
/*     */               
/* 326 */               putMessage(streamId, (FullHttpMessage)fullHttpResponse);
/*     */             } 
/* 328 */           } catch (Throwable t) {
/*     */ 
/*     */             
/* 331 */             SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */             
/* 333 */             ctx.writeAndFlush(spdyRstStreamFrame);
/*     */           } 
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 340 */       if (!spdyHeadersFrame.isTruncated()) {
/* 341 */         for (Map.Entry<CharSequence, CharSequence> e : (Iterable<Map.Entry<CharSequence, CharSequence>>)spdyHeadersFrame.headers()) {
/* 342 */           fullHttpResponse.headers().add(e.getKey(), e.getValue());
/*     */         }
/*     */       }
/*     */       
/* 346 */       if (spdyHeadersFrame.isLast()) {
/* 347 */         HttpUtil.setContentLength((HttpMessage)fullHttpResponse, fullHttpResponse.content().readableBytes());
/* 348 */         removeMessage(streamId);
/* 349 */         out.add(fullHttpResponse);
/*     */       }
/*     */     
/* 352 */     } else if (msg instanceof SpdyDataFrame) {
/*     */       
/* 354 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 355 */       int streamId = spdyDataFrame.streamId();
/* 356 */       FullHttpMessage fullHttpMessage = getMessage(streamId);
/*     */ 
/*     */       
/* 359 */       if (fullHttpMessage == null) {
/*     */         return;
/*     */       }
/*     */       
/* 363 */       ByteBuf content = fullHttpMessage.content();
/* 364 */       if (content.readableBytes() > this.maxContentLength - spdyDataFrame.content().readableBytes()) {
/* 365 */         removeMessage(streamId);
/* 366 */         throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes: " + spdyDataFrame
/*     */             
/* 368 */             .content().readableBytes());
/*     */       } 
/*     */       
/* 371 */       ByteBuf spdyDataFrameData = spdyDataFrame.content();
/* 372 */       int spdyDataFrameDataLen = spdyDataFrameData.readableBytes();
/* 373 */       content.writeBytes(spdyDataFrameData, spdyDataFrameData.readerIndex(), spdyDataFrameDataLen);
/*     */       
/* 375 */       if (spdyDataFrame.isLast()) {
/* 376 */         HttpUtil.setContentLength((HttpMessage)fullHttpMessage, content.readableBytes());
/* 377 */         removeMessage(streamId);
/* 378 */         out.add(fullHttpMessage);
/*     */       }
/*     */     
/* 381 */     } else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 383 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 384 */       int streamId = spdyRstStreamFrame.streamId();
/* 385 */       removeMessage(streamId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static FullHttpRequest createHttpRequest(SpdyHeadersFrame requestFrame, ByteBufAllocator alloc) throws Exception {
/* 392 */     SpdyHeaders headers = requestFrame.headers();
/* 393 */     HttpMethod method = HttpMethod.valueOf(headers.getAsString((CharSequence)SpdyHeaders.HttpNames.METHOD));
/* 394 */     String url = headers.getAsString((CharSequence)SpdyHeaders.HttpNames.PATH);
/* 395 */     HttpVersion httpVersion = HttpVersion.valueOf(headers.getAsString((CharSequence)SpdyHeaders.HttpNames.VERSION));
/* 396 */     headers.remove(SpdyHeaders.HttpNames.METHOD);
/* 397 */     headers.remove(SpdyHeaders.HttpNames.PATH);
/* 398 */     headers.remove(SpdyHeaders.HttpNames.VERSION);
/*     */     
/* 400 */     boolean release = true;
/* 401 */     ByteBuf buffer = alloc.buffer();
/*     */     try {
/* 403 */       DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(httpVersion, method, url, buffer);
/*     */ 
/*     */       
/* 406 */       headers.remove(SpdyHeaders.HttpNames.SCHEME);
/*     */ 
/*     */       
/* 409 */       CharSequence host = (CharSequence)headers.get(SpdyHeaders.HttpNames.HOST);
/* 410 */       headers.remove(SpdyHeaders.HttpNames.HOST);
/* 411 */       defaultFullHttpRequest.headers().set((CharSequence)HttpHeaderNames.HOST, host);
/*     */       
/* 413 */       for (Map.Entry<CharSequence, CharSequence> e : (Iterable<Map.Entry<CharSequence, CharSequence>>)requestFrame.headers()) {
/* 414 */         defaultFullHttpRequest.headers().add(e.getKey(), e.getValue());
/*     */       }
/*     */ 
/*     */       
/* 418 */       HttpUtil.setKeepAlive((HttpMessage)defaultFullHttpRequest, true);
/*     */ 
/*     */       
/* 421 */       defaultFullHttpRequest.headers().remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/* 422 */       release = false;
/* 423 */       return (FullHttpRequest)defaultFullHttpRequest;
/*     */     } finally {
/* 425 */       if (release) {
/* 426 */         buffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FullHttpResponse createHttpResponse(SpdyHeadersFrame responseFrame, ByteBufAllocator alloc) throws Exception {
/* 435 */     SpdyHeaders headers = responseFrame.headers();
/* 436 */     HttpResponseStatus status = HttpResponseStatus.parseLine((CharSequence)headers.get(SpdyHeaders.HttpNames.STATUS));
/* 437 */     HttpVersion version = HttpVersion.valueOf(headers.getAsString((CharSequence)SpdyHeaders.HttpNames.VERSION));
/* 438 */     headers.remove(SpdyHeaders.HttpNames.STATUS);
/* 439 */     headers.remove(SpdyHeaders.HttpNames.VERSION);
/*     */     
/* 441 */     boolean release = true;
/* 442 */     ByteBuf buffer = alloc.buffer();
/*     */     try {
/* 444 */       DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(version, status, buffer, this.headersFactory, this.trailersFactory);
/*     */       
/* 446 */       for (Map.Entry<CharSequence, CharSequence> e : (Iterable<Map.Entry<CharSequence, CharSequence>>)responseFrame.headers()) {
/* 447 */         defaultFullHttpResponse.headers().add(e.getKey(), e.getValue());
/*     */       }
/*     */ 
/*     */       
/* 451 */       HttpUtil.setKeepAlive((HttpMessage)defaultFullHttpResponse, true);
/*     */ 
/*     */       
/* 454 */       defaultFullHttpResponse.headers().remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/* 455 */       defaultFullHttpResponse.headers().remove((CharSequence)HttpHeaderNames.TRAILER);
/*     */       
/* 457 */       release = false;
/* 458 */       return (FullHttpResponse)defaultFullHttpResponse;
/*     */     } finally {
/* 460 */       if (release)
/* 461 */         buffer.release(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHttpDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */