/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.HttpContent;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpObject;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.LastHttpContent;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyHttpEncoder
/*     */   extends MessageToMessageEncoder<HttpObject>
/*     */ {
/*     */   private int currentStreamId;
/*     */   private final boolean validateHeaders;
/*     */   private final boolean headersToLowerCase;
/*     */   
/*     */   public SpdyHttpEncoder(SpdyVersion version) {
/* 136 */     this(version, true, true);
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
/*     */   public SpdyHttpEncoder(SpdyVersion version, boolean headersToLowerCase, boolean validateHeaders) {
/* 148 */     super(HttpObject.class);
/* 149 */     ObjectUtil.checkNotNull(version, "version");
/* 150 */     this.headersToLowerCase = headersToLowerCase;
/* 151 */     this.validateHeaders = validateHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/* 157 */     boolean valid = false;
/* 158 */     boolean last = false;
/*     */     
/* 160 */     if (msg instanceof HttpRequest) {
/*     */       
/* 162 */       HttpRequest httpRequest = (HttpRequest)msg;
/* 163 */       SpdySynStreamFrame spdySynStreamFrame = createSynStreamFrame(httpRequest);
/* 164 */       out.add(spdySynStreamFrame);
/*     */       
/* 166 */       last = (spdySynStreamFrame.isLast() || spdySynStreamFrame.isUnidirectional());
/* 167 */       valid = true;
/*     */     } 
/* 169 */     if (msg instanceof HttpResponse) {
/*     */       
/* 171 */       HttpResponse httpResponse = (HttpResponse)msg;
/* 172 */       SpdyHeadersFrame spdyHeadersFrame = createHeadersFrame(httpResponse);
/* 173 */       out.add(spdyHeadersFrame);
/*     */       
/* 175 */       last = spdyHeadersFrame.isLast();
/* 176 */       valid = true;
/*     */     } 
/* 178 */     if (msg instanceof HttpContent && !last) {
/*     */       
/* 180 */       HttpContent chunk = (HttpContent)msg;
/*     */       
/* 182 */       chunk.content().retain();
/* 183 */       SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(this.currentStreamId, chunk.content());
/* 184 */       if (chunk instanceof LastHttpContent) {
/* 185 */         LastHttpContent trailer = (LastHttpContent)chunk;
/* 186 */         HttpHeaders trailers = trailer.trailingHeaders();
/* 187 */         if (trailers.isEmpty()) {
/* 188 */           spdyDataFrame.setLast(true);
/* 189 */           out.add(spdyDataFrame);
/*     */         } else {
/*     */           
/* 192 */           SpdyHeadersFrame spdyHeadersFrame = new DefaultSpdyHeadersFrame(this.currentStreamId, this.validateHeaders);
/* 193 */           spdyHeadersFrame.setLast(true);
/* 194 */           Iterator<Map.Entry<CharSequence, CharSequence>> itr = trailers.iteratorCharSequence();
/* 195 */           while (itr.hasNext()) {
/* 196 */             Map.Entry<CharSequence, CharSequence> entry = itr.next();
/*     */             
/* 198 */             CharSequence headerName = this.headersToLowerCase ? (CharSequence)AsciiString.of(entry.getKey()).toLowerCase() : entry.getKey();
/* 199 */             spdyHeadersFrame.headers().add(headerName, entry.getValue());
/*     */           } 
/*     */ 
/*     */           
/* 203 */           out.add(spdyDataFrame);
/* 204 */           out.add(spdyHeadersFrame);
/*     */         } 
/*     */       } else {
/* 207 */         out.add(spdyDataFrame);
/*     */       } 
/*     */       
/* 210 */       valid = true;
/*     */     } 
/*     */     
/* 213 */     if (!valid) {
/* 214 */       throw new UnsupportedMessageTypeException(msg, new Class[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SpdySynStreamFrame createSynStreamFrame(HttpRequest httpRequest) throws Exception {
/* 221 */     HttpHeaders httpHeaders = httpRequest.headers();
/* 222 */     int streamId = httpHeaders.getInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID).intValue();
/* 223 */     int associatedToStreamId = httpHeaders.getInt((CharSequence)SpdyHttpHeaders.Names.ASSOCIATED_TO_STREAM_ID, 0);
/* 224 */     byte priority = (byte)httpHeaders.getInt((CharSequence)SpdyHttpHeaders.Names.PRIORITY, 0);
/* 225 */     CharSequence scheme = httpHeaders.get((CharSequence)SpdyHttpHeaders.Names.SCHEME);
/* 226 */     httpHeaders.remove((CharSequence)SpdyHttpHeaders.Names.STREAM_ID);
/* 227 */     httpHeaders.remove((CharSequence)SpdyHttpHeaders.Names.ASSOCIATED_TO_STREAM_ID);
/* 228 */     httpHeaders.remove((CharSequence)SpdyHttpHeaders.Names.PRIORITY);
/* 229 */     httpHeaders.remove((CharSequence)SpdyHttpHeaders.Names.SCHEME);
/*     */ 
/*     */ 
/*     */     
/* 233 */     httpHeaders.remove((CharSequence)HttpHeaderNames.CONNECTION);
/* 234 */     httpHeaders.remove("Keep-Alive");
/* 235 */     httpHeaders.remove("Proxy-Connection");
/* 236 */     httpHeaders.remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/*     */     
/* 238 */     SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority, this.validateHeaders);
/*     */ 
/*     */ 
/*     */     
/* 242 */     SpdyHeaders frameHeaders = spdySynStreamFrame.headers();
/* 243 */     frameHeaders.set(SpdyHeaders.HttpNames.METHOD, httpRequest.method().name());
/* 244 */     frameHeaders.set(SpdyHeaders.HttpNames.PATH, httpRequest.uri());
/* 245 */     frameHeaders.set(SpdyHeaders.HttpNames.VERSION, httpRequest.protocolVersion().text());
/*     */ 
/*     */     
/* 248 */     CharSequence host = httpHeaders.get((CharSequence)HttpHeaderNames.HOST);
/* 249 */     httpHeaders.remove((CharSequence)HttpHeaderNames.HOST);
/* 250 */     frameHeaders.set(SpdyHeaders.HttpNames.HOST, host);
/*     */ 
/*     */     
/* 253 */     if (scheme == null) {
/* 254 */       scheme = "https";
/*     */     }
/* 256 */     frameHeaders.set(SpdyHeaders.HttpNames.SCHEME, scheme);
/*     */ 
/*     */     
/* 259 */     Iterator<Map.Entry<CharSequence, CharSequence>> itr = httpHeaders.iteratorCharSequence();
/* 260 */     while (itr.hasNext()) {
/* 261 */       Map.Entry<CharSequence, CharSequence> entry = itr.next();
/*     */       
/* 263 */       CharSequence headerName = this.headersToLowerCase ? (CharSequence)AsciiString.of(entry.getKey()).toLowerCase() : entry.getKey();
/* 264 */       frameHeaders.add(headerName, entry.getValue());
/*     */     } 
/* 266 */     this.currentStreamId = spdySynStreamFrame.streamId();
/* 267 */     if (associatedToStreamId == 0) {
/* 268 */       spdySynStreamFrame.setLast(isLast((HttpMessage)httpRequest));
/*     */     } else {
/* 270 */       spdySynStreamFrame.setUnidirectional(true);
/*     */     } 
/*     */     
/* 273 */     return spdySynStreamFrame;
/*     */   }
/*     */ 
/*     */   
/*     */   private SpdyHeadersFrame createHeadersFrame(HttpResponse httpResponse) throws Exception {
/*     */     SpdyHeadersFrame spdyHeadersFrame;
/* 279 */     HttpHeaders httpHeaders = httpResponse.headers();
/* 280 */     int streamId = httpHeaders.getInt((CharSequence)SpdyHttpHeaders.Names.STREAM_ID).intValue();
/* 281 */     httpHeaders.remove((CharSequence)SpdyHttpHeaders.Names.STREAM_ID);
/*     */ 
/*     */ 
/*     */     
/* 285 */     httpHeaders.remove((CharSequence)HttpHeaderNames.CONNECTION);
/* 286 */     httpHeaders.remove("Keep-Alive");
/* 287 */     httpHeaders.remove("Proxy-Connection");
/* 288 */     httpHeaders.remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/*     */ 
/*     */     
/* 291 */     if (SpdyCodecUtil.isServerId(streamId)) {
/* 292 */       spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamId, this.validateHeaders);
/*     */     } else {
/* 294 */       spdyHeadersFrame = new DefaultSpdySynReplyFrame(streamId, this.validateHeaders);
/*     */     } 
/* 296 */     SpdyHeaders frameHeaders = spdyHeadersFrame.headers();
/*     */     
/* 298 */     frameHeaders.set(SpdyHeaders.HttpNames.STATUS, httpResponse.status().codeAsText());
/* 299 */     frameHeaders.set(SpdyHeaders.HttpNames.VERSION, httpResponse.protocolVersion().text());
/*     */ 
/*     */     
/* 302 */     Iterator<Map.Entry<CharSequence, CharSequence>> itr = httpHeaders.iteratorCharSequence();
/* 303 */     while (itr.hasNext()) {
/* 304 */       Map.Entry<CharSequence, CharSequence> entry = itr.next();
/*     */       
/* 306 */       CharSequence headerName = this.headersToLowerCase ? (CharSequence)AsciiString.of(entry.getKey()).toLowerCase() : entry.getKey();
/* 307 */       spdyHeadersFrame.headers().add(headerName, entry.getValue());
/*     */     } 
/*     */     
/* 310 */     this.currentStreamId = streamId;
/* 311 */     spdyHeadersFrame.setLast(isLast((HttpMessage)httpResponse));
/*     */     
/* 313 */     return spdyHeadersFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLast(HttpMessage httpMessage) {
/* 323 */     if (httpMessage instanceof FullHttpMessage) {
/* 324 */       FullHttpMessage fullMessage = (FullHttpMessage)httpMessage;
/* 325 */       if (fullMessage.trailingHeaders().isEmpty() && !fullMessage.content().isReadable()) {
/* 326 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 330 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHttpEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */