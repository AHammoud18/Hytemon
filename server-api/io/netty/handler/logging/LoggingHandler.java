/*     */ package io.netty.handler.logging;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogLevel;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.SocketAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class LoggingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  44 */   private static final LogLevel DEFAULT_LEVEL = LogLevel.DEBUG;
/*     */ 
/*     */   
/*     */   protected final InternalLogger logger;
/*     */   
/*     */   protected final InternalLogLevel internalLevel;
/*     */   
/*     */   private final LogLevel level;
/*     */   
/*     */   private final ByteBufFormat byteBufFormat;
/*     */ 
/*     */   
/*     */   public LoggingHandler() {
/*  57 */     this(DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(ByteBufFormat format) {
/*  66 */     this(DEFAULT_LEVEL, format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(LogLevel level) {
/*  76 */     this(level, ByteBufFormat.HEX_DUMP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(LogLevel level, ByteBufFormat byteBufFormat) {
/*  87 */     this.level = (LogLevel)ObjectUtil.checkNotNull(level, "level");
/*  88 */     this.byteBufFormat = (ByteBufFormat)ObjectUtil.checkNotNull(byteBufFormat, "byteBufFormat");
/*  89 */     this.logger = InternalLoggerFactory.getInstance(getClass());
/*  90 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(Class<?> clazz) {
/* 100 */     this(clazz, DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(Class<?> clazz, LogLevel level) {
/* 110 */     this(clazz, level, ByteBufFormat.HEX_DUMP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(Class<?> clazz, LogLevel level, ByteBufFormat byteBufFormat) {
/* 121 */     ObjectUtil.checkNotNull(clazz, "clazz");
/* 122 */     this.level = (LogLevel)ObjectUtil.checkNotNull(level, "level");
/* 123 */     this.byteBufFormat = (ByteBufFormat)ObjectUtil.checkNotNull(byteBufFormat, "byteBufFormat");
/* 124 */     this.logger = InternalLoggerFactory.getInstance(clazz);
/* 125 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(String name) {
/* 134 */     this(name, DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(String name, LogLevel level) {
/* 144 */     this(name, level, ByteBufFormat.HEX_DUMP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(String name, LogLevel level, ByteBufFormat byteBufFormat) {
/* 155 */     ObjectUtil.checkNotNull(name, "name");
/*     */     
/* 157 */     this.level = (LogLevel)ObjectUtil.checkNotNull(level, "level");
/* 158 */     this.byteBufFormat = (ByteBufFormat)ObjectUtil.checkNotNull(byteBufFormat, "byteBufFormat");
/* 159 */     this.logger = InternalLoggerFactory.getInstance(name);
/* 160 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogLevel level() {
/* 167 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufFormat byteBufFormat() {
/* 174 */     return this.byteBufFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 179 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 180 */       this.logger.log(this.internalLevel, format(ctx, "REGISTERED"));
/*     */     }
/* 182 */     ctx.fireChannelRegistered();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
/* 187 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 188 */       this.logger.log(this.internalLevel, format(ctx, "UNREGISTERED"));
/*     */     }
/* 190 */     ctx.fireChannelUnregistered();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 195 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 196 */       this.logger.log(this.internalLevel, format(ctx, "ACTIVE"));
/*     */     }
/* 198 */     ctx.fireChannelActive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 203 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 204 */       this.logger.log(this.internalLevel, format(ctx, "INACTIVE"));
/*     */     }
/* 206 */     ctx.fireChannelInactive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 211 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 212 */       this.logger.log(this.internalLevel, format(ctx, "EXCEPTION", cause), cause);
/*     */     }
/* 214 */     ctx.fireExceptionCaught(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 219 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 220 */       this.logger.log(this.internalLevel, format(ctx, "USER_EVENT", evt));
/*     */     }
/* 222 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 227 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 228 */       this.logger.log(this.internalLevel, format(ctx, "BIND", localAddress));
/*     */     }
/* 230 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 237 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 238 */       this.logger.log(this.internalLevel, format(ctx, "CONNECT", remoteAddress, localAddress));
/*     */     }
/* 240 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 245 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 246 */       this.logger.log(this.internalLevel, format(ctx, "DISCONNECT"));
/*     */     }
/* 248 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 253 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 254 */       this.logger.log(this.internalLevel, format(ctx, "CLOSE"));
/*     */     }
/* 256 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 261 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 262 */       this.logger.log(this.internalLevel, format(ctx, "DEREGISTER"));
/*     */     }
/* 264 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 269 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 270 */       this.logger.log(this.internalLevel, format(ctx, "READ COMPLETE"));
/*     */     }
/* 272 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 277 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 278 */       this.logger.log(this.internalLevel, format(ctx, "READ", msg));
/*     */     }
/* 280 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 285 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 286 */       this.logger.log(this.internalLevel, format(ctx, "WRITE", msg));
/*     */     }
/* 288 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
/* 293 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 294 */       this.logger.log(this.internalLevel, format(ctx, "WRITABILITY CHANGED"));
/*     */     }
/* 296 */     ctx.fireChannelWritabilityChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 301 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 302 */       this.logger.log(this.internalLevel, format(ctx, "FLUSH"));
/*     */     }
/* 304 */     ctx.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String format(ChannelHandlerContext ctx, String eventName) {
/* 313 */     String chStr = ctx.channel().toString();
/* 314 */     return (new StringBuilder(chStr.length() + 1 + eventName.length()))
/* 315 */       .append(chStr)
/* 316 */       .append(' ')
/* 317 */       .append(eventName)
/* 318 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
/* 328 */     if (arg instanceof ByteBuf)
/* 329 */       return formatByteBuf(ctx, eventName, (ByteBuf)arg); 
/* 330 */     if (arg instanceof ByteBufHolder) {
/* 331 */       return formatByteBufHolder(ctx, eventName, (ByteBufHolder)arg);
/*     */     }
/* 333 */     return formatSimple(ctx, eventName, arg);
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
/*     */   protected String format(ChannelHandlerContext ctx, String eventName, Object firstArg, Object secondArg) {
/* 346 */     if (secondArg == null) {
/* 347 */       return formatSimple(ctx, eventName, firstArg);
/*     */     }
/*     */     
/* 350 */     String chStr = ctx.channel().toString();
/* 351 */     String arg1Str = String.valueOf(firstArg);
/* 352 */     String arg2Str = secondArg.toString();
/*     */     
/* 354 */     StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + arg1Str.length() + 2 + arg2Str.length());
/* 355 */     buf.append(chStr).append(' ').append(eventName).append(": ").append(arg1Str).append(", ").append(arg2Str);
/* 356 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String formatByteBuf(ChannelHandlerContext ctx, String eventName, ByteBuf msg) {
/* 363 */     String chStr = ctx.channel().toString();
/* 364 */     int length = msg.readableBytes();
/* 365 */     if (length == 0) {
/* 366 */       StringBuilder stringBuilder = new StringBuilder(chStr.length() + 1 + eventName.length() + 4);
/* 367 */       stringBuilder.append(chStr).append(' ').append(eventName).append(": 0B");
/* 368 */       return stringBuilder.toString();
/*     */     } 
/* 370 */     int outputLength = chStr.length() + 1 + eventName.length() + 2 + 10 + 1;
/* 371 */     if (this.byteBufFormat == ByteBufFormat.HEX_DUMP) {
/* 372 */       int rows = length / 16 + ((length % 15 == 0) ? 0 : 1) + 4;
/* 373 */       int hexDumpLength = 2 + rows * 80;
/* 374 */       outputLength += hexDumpLength;
/*     */     } 
/* 376 */     StringBuilder buf = new StringBuilder(outputLength);
/* 377 */     buf.append(chStr).append(' ').append(eventName).append(": ").append(length).append('B');
/* 378 */     if (this.byteBufFormat == ByteBufFormat.HEX_DUMP) {
/* 379 */       buf.append(StringUtil.NEWLINE);
/* 380 */       ByteBufUtil.appendPrettyHexDump(buf, msg);
/*     */     } 
/*     */     
/* 383 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String formatByteBufHolder(ChannelHandlerContext ctx, String eventName, ByteBufHolder msg) {
/* 391 */     String chStr = ctx.channel().toString();
/* 392 */     String msgStr = msg.toString();
/* 393 */     ByteBuf content = msg.content();
/* 394 */     int length = content.readableBytes();
/* 395 */     if (length == 0) {
/* 396 */       StringBuilder stringBuilder = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 4);
/* 397 */       stringBuilder.append(chStr).append(' ').append(eventName).append(", ").append(msgStr).append(", 0B");
/* 398 */       return stringBuilder.toString();
/*     */     } 
/* 400 */     int outputLength = chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 2 + 10 + 1;
/* 401 */     if (this.byteBufFormat == ByteBufFormat.HEX_DUMP) {
/* 402 */       int rows = length / 16 + ((length % 15 == 0) ? 0 : 1) + 4;
/* 403 */       int hexDumpLength = 2 + rows * 80;
/* 404 */       outputLength += hexDumpLength;
/*     */     } 
/* 406 */     StringBuilder buf = new StringBuilder(outputLength);
/* 407 */     buf.append(chStr).append(' ').append(eventName).append(": ")
/* 408 */       .append(msgStr).append(", ").append(length).append('B');
/* 409 */     if (this.byteBufFormat == ByteBufFormat.HEX_DUMP) {
/* 410 */       buf.append(StringUtil.NEWLINE);
/* 411 */       ByteBufUtil.appendPrettyHexDump(buf, content);
/*     */     } 
/*     */     
/* 414 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String formatSimple(ChannelHandlerContext ctx, String eventName, Object msg) {
/* 422 */     String chStr = ctx.channel().toString();
/* 423 */     String msgStr = String.valueOf(msg);
/* 424 */     StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length());
/* 425 */     return buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\logging\LoggingHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */