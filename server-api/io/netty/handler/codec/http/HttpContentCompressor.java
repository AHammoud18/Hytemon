/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.handler.codec.compression.Brotli;
/*     */ import io.netty.handler.codec.compression.BrotliEncoder;
/*     */ import io.netty.handler.codec.compression.BrotliOptions;
/*     */ import io.netty.handler.codec.compression.CompressionOptions;
/*     */ import io.netty.handler.codec.compression.DeflateOptions;
/*     */ import io.netty.handler.codec.compression.GzipOptions;
/*     */ import io.netty.handler.codec.compression.SnappyFrameEncoder;
/*     */ import io.netty.handler.codec.compression.SnappyOptions;
/*     */ import io.netty.handler.codec.compression.StandardCompressionOptions;
/*     */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*     */ import io.netty.handler.codec.compression.ZlibWrapper;
/*     */ import io.netty.handler.codec.compression.Zstd;
/*     */ import io.netty.handler.codec.compression.ZstdEncoder;
/*     */ import io.netty.handler.codec.compression.ZstdOptions;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayList;
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
/*     */ public class HttpContentCompressor
/*     */   extends HttpContentEncoder
/*     */ {
/*     */   private final BrotliOptions brotliOptions;
/*     */   private final GzipOptions gzipOptions;
/*     */   private final DeflateOptions deflateOptions;
/*     */   private final ZstdOptions zstdOptions;
/*     */   private final SnappyOptions snappyOptions;
/*     */   private final int contentSizeThreshold;
/*     */   private ChannelHandlerContext ctx;
/*     */   private final Map<String, CompressionEncoderFactory> factories;
/*     */   
/*     */   public HttpContentCompressor() {
/*  72 */     this(0, (CompressionOptions[])null);
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
/*     */   @Deprecated
/*     */   public HttpContentCompressor(int compressionLevel) {
/*  86 */     this(compressionLevel, 15, 8, 0);
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
/*     */   @Deprecated
/*     */   public HttpContentCompressor(int compressionLevel, int windowBits, int memLevel) {
/* 110 */     this(compressionLevel, windowBits, memLevel, 0);
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
/*     */   @Deprecated
/*     */   public HttpContentCompressor(int compressionLevel, int windowBits, int memLevel, int contentSizeThreshold) {
/* 138 */     this(contentSizeThreshold, 
/* 139 */         defaultCompressionOptions(
/* 140 */           StandardCompressionOptions.gzip(
/* 141 */             ObjectUtil.checkInRange(compressionLevel, 0, 9, "compressionLevel"), 
/* 142 */             ObjectUtil.checkInRange(windowBits, 9, 15, "windowBits"), 
/* 143 */             ObjectUtil.checkInRange(memLevel, 1, 9, "memLevel")), 
/*     */           
/* 145 */           StandardCompressionOptions.deflate(
/* 146 */             ObjectUtil.checkInRange(compressionLevel, 0, 9, "compressionLevel"), 
/* 147 */             ObjectUtil.checkInRange(windowBits, 9, 15, "windowBits"), 
/* 148 */             ObjectUtil.checkInRange(memLevel, 1, 9, "memLevel"))));
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
/*     */   public HttpContentCompressor(CompressionOptions... compressionOptions) {
/* 162 */     this(0, compressionOptions);
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
/*     */   public HttpContentCompressor(int contentSizeThreshold, CompressionOptions... compressionOptions) {
/* 177 */     this.contentSizeThreshold = ObjectUtil.checkPositiveOrZero(contentSizeThreshold, "contentSizeThreshold");
/* 178 */     BrotliOptions brotliOptions = null;
/* 179 */     GzipOptions gzipOptions = null;
/* 180 */     DeflateOptions deflateOptions = null;
/* 181 */     ZstdOptions zstdOptions = null;
/* 182 */     SnappyOptions snappyOptions = null;
/* 183 */     if (compressionOptions == null || compressionOptions.length == 0) {
/* 184 */       compressionOptions = defaultCompressionOptions(
/* 185 */           StandardCompressionOptions.gzip(), StandardCompressionOptions.deflate());
/*     */     }
/*     */     
/* 188 */     ObjectUtil.deepCheckNotNull("compressionOptions", (Object[])compressionOptions);
/* 189 */     for (CompressionOptions compressionOption : compressionOptions) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 196 */       if (Brotli.isAvailable() && compressionOption instanceof BrotliOptions) {
/* 197 */         brotliOptions = (BrotliOptions)compressionOption;
/* 198 */       } else if (compressionOption instanceof GzipOptions) {
/* 199 */         gzipOptions = (GzipOptions)compressionOption;
/* 200 */       } else if (compressionOption instanceof DeflateOptions) {
/* 201 */         deflateOptions = (DeflateOptions)compressionOption;
/* 202 */       } else if (Zstd.isAvailable() && compressionOption instanceof ZstdOptions) {
/* 203 */         zstdOptions = (ZstdOptions)compressionOption;
/* 204 */       } else if (compressionOption instanceof SnappyOptions) {
/* 205 */         snappyOptions = (SnappyOptions)compressionOption;
/*     */       } else {
/* 207 */         throw new IllegalArgumentException("Unsupported " + CompressionOptions.class.getSimpleName() + ": " + compressionOption);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 212 */     this.gzipOptions = gzipOptions;
/* 213 */     this.deflateOptions = deflateOptions;
/* 214 */     this.brotliOptions = brotliOptions;
/* 215 */     this.zstdOptions = zstdOptions;
/* 216 */     this.snappyOptions = snappyOptions;
/*     */     
/* 218 */     this.factories = new HashMap<>();
/*     */     
/* 220 */     if (this.gzipOptions != null) {
/* 221 */       this.factories.put("gzip", new GzipEncoderFactory());
/*     */     }
/* 223 */     if (this.deflateOptions != null) {
/* 224 */       this.factories.put("deflate", new DeflateEncoderFactory());
/*     */     }
/* 226 */     if (Brotli.isAvailable() && this.brotliOptions != null) {
/* 227 */       this.factories.put("br", new BrEncoderFactory());
/*     */     }
/* 229 */     if (this.zstdOptions != null) {
/* 230 */       this.factories.put("zstd", new ZstdEncoderFactory());
/*     */     }
/* 232 */     if (this.snappyOptions != null) {
/* 233 */       this.factories.put("snappy", new SnappyEncoderFactory());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static CompressionOptions[] defaultCompressionOptions(GzipOptions gzipOptions, DeflateOptions deflateOptions) {
/* 239 */     List<CompressionOptions> options = new ArrayList<>(5);
/* 240 */     options.add(gzipOptions);
/* 241 */     options.add(deflateOptions);
/* 242 */     options.add(StandardCompressionOptions.snappy());
/*     */     
/* 244 */     if (Brotli.isAvailable()) {
/* 245 */       options.add(StandardCompressionOptions.brotli());
/*     */     }
/* 247 */     if (Zstd.isAvailable()) {
/* 248 */       options.add(StandardCompressionOptions.zstd());
/*     */     }
/* 250 */     return options.<CompressionOptions>toArray(new CompressionOptions[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 255 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpContentEncoder.Result beginEncode(HttpResponse httpResponse, String acceptEncoding) throws Exception {
/* 260 */     if (this.contentSizeThreshold > 0 && 
/* 261 */       httpResponse instanceof HttpContent && ((HttpContent)httpResponse)
/* 262 */       .content().readableBytes() < this.contentSizeThreshold) {
/* 263 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 267 */     String contentEncoding = httpResponse.headers().get((CharSequence)HttpHeaderNames.CONTENT_ENCODING);
/* 268 */     if (contentEncoding != null)
/*     */     {
/*     */       
/* 271 */       return null;
/*     */     }
/*     */     
/* 274 */     String targetContentEncoding = determineEncoding(acceptEncoding);
/* 275 */     if (targetContentEncoding == null) {
/* 276 */       return null;
/*     */     }
/*     */     
/* 279 */     CompressionEncoderFactory encoderFactory = this.factories.get(targetContentEncoding);
/*     */     
/* 281 */     if (encoderFactory == null) {
/* 282 */       throw new IllegalStateException("Couldn't find CompressionEncoderFactory: " + targetContentEncoding);
/*     */     }
/*     */     
/* 285 */     Channel channel = this.ctx.channel();
/* 286 */     return new HttpContentEncoder.Result(targetContentEncoding, 
/* 287 */         EmbeddedChannel.builder()
/* 288 */         .channelId(channel.id())
/* 289 */         .hasDisconnect(channel.metadata().hasDisconnect())
/* 290 */         .config(channel.config())
/* 291 */         .handlers((ChannelHandler)encoderFactory.createEncoder())
/* 292 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   protected String determineEncoding(String acceptEncoding) {
/* 297 */     float starQ = -1.0F;
/* 298 */     float brQ = -1.0F;
/* 299 */     float zstdQ = -1.0F;
/* 300 */     float snappyQ = -1.0F;
/* 301 */     float gzipQ = -1.0F;
/* 302 */     float deflateQ = -1.0F;
/*     */     
/* 304 */     int start = 0;
/* 305 */     int length = acceptEncoding.length();
/* 306 */     while (start < length) {
/* 307 */       int comma = acceptEncoding.indexOf(',', start);
/* 308 */       if (comma == -1) {
/* 309 */         comma = length;
/*     */       }
/* 311 */       String encoding = acceptEncoding.substring(start, comma);
/* 312 */       float q = 1.0F;
/* 313 */       int equalsPos = encoding.indexOf('=');
/* 314 */       if (equalsPos != -1) {
/*     */         try {
/* 316 */           q = Float.parseFloat(encoding.substring(equalsPos + 1));
/* 317 */         } catch (NumberFormatException e) {
/*     */           
/* 319 */           q = 0.0F;
/*     */         } 
/*     */       }
/* 322 */       if (encoding.contains("*")) {
/* 323 */         starQ = q;
/* 324 */       } else if (encoding.contains("br") && q > brQ) {
/* 325 */         brQ = q;
/* 326 */       } else if (encoding.contains("zstd") && q > zstdQ) {
/* 327 */         zstdQ = q;
/* 328 */       } else if (encoding.contains("snappy") && q > snappyQ) {
/* 329 */         snappyQ = q;
/* 330 */       } else if (encoding.contains("gzip") && q > gzipQ) {
/* 331 */         gzipQ = q;
/* 332 */       } else if (encoding.contains("deflate") && q > deflateQ) {
/* 333 */         deflateQ = q;
/*     */       } 
/* 335 */       start = comma + 1;
/*     */     } 
/* 337 */     if (brQ > 0.0F || zstdQ > 0.0F || snappyQ > 0.0F || gzipQ > 0.0F || deflateQ > 0.0F) {
/* 338 */       if (brQ != -1.0F && brQ >= zstdQ && this.brotliOptions != null)
/* 339 */         return "br"; 
/* 340 */       if (zstdQ != -1.0F && zstdQ >= snappyQ && this.zstdOptions != null)
/* 341 */         return "zstd"; 
/* 342 */       if (snappyQ != -1.0F && snappyQ >= gzipQ && this.snappyOptions != null)
/* 343 */         return "snappy"; 
/* 344 */       if (gzipQ != -1.0F && gzipQ >= deflateQ && this.gzipOptions != null)
/* 345 */         return "gzip"; 
/* 346 */       if (deflateQ != -1.0F && this.deflateOptions != null) {
/* 347 */         return "deflate";
/*     */       }
/*     */     } 
/* 350 */     if (starQ > 0.0F) {
/* 351 */       if (brQ == -1.0F && this.brotliOptions != null) {
/* 352 */         return "br";
/*     */       }
/* 354 */       if (zstdQ == -1.0F && this.zstdOptions != null) {
/* 355 */         return "zstd";
/*     */       }
/* 357 */       if (snappyQ == -1.0F && this.snappyOptions != null) {
/* 358 */         return "snappy";
/*     */       }
/* 360 */       if (gzipQ == -1.0F && this.gzipOptions != null) {
/* 361 */         return "gzip";
/*     */       }
/* 363 */       if (deflateQ == -1.0F && this.deflateOptions != null) {
/* 364 */         return "deflate";
/*     */       }
/*     */     } 
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected ZlibWrapper determineWrapper(String acceptEncoding) {
/* 373 */     float starQ = -1.0F;
/* 374 */     float gzipQ = -1.0F;
/* 375 */     float deflateQ = -1.0F;
/* 376 */     for (String encoding : acceptEncoding.split(",")) {
/* 377 */       float q = 1.0F;
/* 378 */       int equalsPos = encoding.indexOf('=');
/* 379 */       if (equalsPos != -1) {
/*     */         try {
/* 381 */           q = Float.parseFloat(encoding.substring(equalsPos + 1));
/* 382 */         } catch (NumberFormatException e) {
/*     */           
/* 384 */           q = 0.0F;
/*     */         } 
/*     */       }
/* 387 */       if (encoding.contains("*")) {
/* 388 */         starQ = q;
/* 389 */       } else if (encoding.contains("gzip") && q > gzipQ) {
/* 390 */         gzipQ = q;
/* 391 */       } else if (encoding.contains("deflate") && q > deflateQ) {
/* 392 */         deflateQ = q;
/*     */       } 
/*     */     } 
/* 395 */     if (gzipQ > 0.0F || deflateQ > 0.0F) {
/* 396 */       if (gzipQ >= deflateQ) {
/* 397 */         return ZlibWrapper.GZIP;
/*     */       }
/* 399 */       return ZlibWrapper.ZLIB;
/*     */     } 
/*     */     
/* 402 */     if (starQ > 0.0F) {
/* 403 */       if (gzipQ == -1.0F) {
/* 404 */         return ZlibWrapper.GZIP;
/*     */       }
/* 406 */       if (deflateQ == -1.0F) {
/* 407 */         return ZlibWrapper.ZLIB;
/*     */       }
/*     */     } 
/* 410 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private final class GzipEncoderFactory
/*     */     implements CompressionEncoderFactory
/*     */   {
/*     */     private GzipEncoderFactory() {}
/*     */ 
/*     */     
/*     */     public MessageToByteEncoder<ByteBuf> createEncoder() {
/* 421 */       return (MessageToByteEncoder<ByteBuf>)ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP, HttpContentCompressor.this
/* 422 */           .gzipOptions.compressionLevel(), HttpContentCompressor.this
/* 423 */           .gzipOptions.windowBits(), HttpContentCompressor.this.gzipOptions.memLevel());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class DeflateEncoderFactory
/*     */     implements CompressionEncoderFactory
/*     */   {
/*     */     private DeflateEncoderFactory() {}
/*     */ 
/*     */     
/*     */     public MessageToByteEncoder<ByteBuf> createEncoder() {
/* 435 */       return (MessageToByteEncoder<ByteBuf>)ZlibCodecFactory.newZlibEncoder(ZlibWrapper.ZLIB, HttpContentCompressor.this
/* 436 */           .deflateOptions.compressionLevel(), HttpContentCompressor.this
/* 437 */           .deflateOptions.windowBits(), HttpContentCompressor.this.deflateOptions.memLevel());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class BrEncoderFactory
/*     */     implements CompressionEncoderFactory
/*     */   {
/*     */     private BrEncoderFactory() {}
/*     */ 
/*     */     
/*     */     public MessageToByteEncoder<ByteBuf> createEncoder() {
/* 449 */       return (MessageToByteEncoder<ByteBuf>)new BrotliEncoder(HttpContentCompressor.this.brotliOptions.parameters());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class ZstdEncoderFactory
/*     */     implements CompressionEncoderFactory
/*     */   {
/*     */     private ZstdEncoderFactory() {}
/*     */ 
/*     */     
/*     */     public MessageToByteEncoder<ByteBuf> createEncoder() {
/* 461 */       return (MessageToByteEncoder<ByteBuf>)new ZstdEncoder(HttpContentCompressor.this.zstdOptions.compressionLevel(), HttpContentCompressor.this
/* 462 */           .zstdOptions.blockSize(), HttpContentCompressor.this.zstdOptions.maxEncodeSize());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SnappyEncoderFactory
/*     */     implements CompressionEncoderFactory
/*     */   {
/*     */     private SnappyEncoderFactory() {}
/*     */ 
/*     */     
/*     */     public MessageToByteEncoder<ByteBuf> createEncoder() {
/* 474 */       return (MessageToByteEncoder<ByteBuf>)new SnappyFrameEncoder();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpContentCompressor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */