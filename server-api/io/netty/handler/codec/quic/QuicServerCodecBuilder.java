/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
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
/*     */ public final class QuicServerCodecBuilder
/*     */   extends QuicCodecBuilder<QuicServerCodecBuilder>
/*     */ {
/*  37 */   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap<>();
/*  38 */   private final Map<AttributeKey<?>, Object> attrs = new HashMap<>();
/*  39 */   private final Map<ChannelOption<?>, Object> streamOptions = new LinkedHashMap<>();
/*  40 */   private final Map<AttributeKey<?>, Object> streamAttrs = new HashMap<>();
/*     */   
/*     */   private ChannelHandler handler;
/*     */   
/*     */   private ChannelHandler streamHandler;
/*     */   
/*     */   private QuicConnectionIdGenerator connectionIdAddressGenerator;
/*     */   private QuicTokenHandler tokenHandler;
/*     */   private QuicResetTokenGenerator resetTokenGenerator;
/*     */   
/*     */   public QuicServerCodecBuilder() {
/*  51 */     super(true);
/*     */   }
/*     */   
/*     */   private QuicServerCodecBuilder(QuicServerCodecBuilder builder) {
/*  55 */     super(builder);
/*  56 */     this.options.putAll(builder.options);
/*  57 */     this.attrs.putAll(builder.attrs);
/*  58 */     this.streamOptions.putAll(builder.streamOptions);
/*  59 */     this.streamAttrs.putAll(builder.streamAttrs);
/*  60 */     this.handler = builder.handler;
/*  61 */     this.streamHandler = builder.streamHandler;
/*  62 */     this.connectionIdAddressGenerator = builder.connectionIdAddressGenerator;
/*  63 */     this.tokenHandler = builder.tokenHandler;
/*  64 */     this.resetTokenGenerator = builder.resetTokenGenerator;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicServerCodecBuilder clone() {
/*  69 */     return new QuicServerCodecBuilder(this);
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
/*     */   public <T> QuicServerCodecBuilder option(ChannelOption<T> option, @Nullable T value) {
/*  82 */     Quic.updateOptions(this.options, option, value);
/*  83 */     return self();
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
/*     */   public <T> QuicServerCodecBuilder attr(AttributeKey<T> key, @Nullable T value) {
/*  96 */     Quic.updateAttributes(this.attrs, key, value);
/*  97 */     return self();
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
/*     */   public QuicServerCodecBuilder handler(ChannelHandler handler) {
/* 109 */     this.handler = (ChannelHandler)ObjectUtil.checkNotNull(handler, "handler");
/* 110 */     return self();
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
/*     */   public <T> QuicServerCodecBuilder streamOption(ChannelOption<T> option, @Nullable T value) {
/* 123 */     Quic.updateOptions(this.streamOptions, option, value);
/* 124 */     return self();
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
/*     */   public <T> QuicServerCodecBuilder streamAttr(AttributeKey<T> key, @Nullable T value) {
/* 137 */     Quic.updateAttributes(this.streamAttrs, key, value);
/* 138 */     return self();
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
/*     */   public QuicServerCodecBuilder streamHandler(ChannelHandler streamHandler) {
/* 150 */     this.streamHandler = (ChannelHandler)ObjectUtil.checkNotNull(streamHandler, "streamHandler");
/* 151 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicServerCodecBuilder connectionIdAddressGenerator(QuicConnectionIdGenerator connectionIdAddressGenerator) {
/* 162 */     this.connectionIdAddressGenerator = connectionIdAddressGenerator;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicServerCodecBuilder tokenHandler(@Nullable QuicTokenHandler tokenHandler) {
/* 174 */     this.tokenHandler = tokenHandler;
/* 175 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicServerCodecBuilder resetTokenGenerator(@Nullable QuicResetTokenGenerator resetTokenGenerator) {
/* 186 */     this.resetTokenGenerator = resetTokenGenerator;
/* 187 */     return self();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validate() {
/* 192 */     super.validate();
/* 193 */     if (this.handler == null && this.streamHandler == null) {
/* 194 */       throw new IllegalStateException("handler and streamHandler not set");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelHandler build(QuicheConfig config, Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider, Executor sslTaskExecutor, int localConnIdLength, FlushStrategy flushStrategy) {
/* 203 */     validate();
/* 204 */     QuicTokenHandler tokenHandler = this.tokenHandler;
/* 205 */     if (tokenHandler == null) {
/* 206 */       tokenHandler = NoQuicTokenHandler.INSTANCE;
/*     */     }
/* 208 */     QuicConnectionIdGenerator generator = this.connectionIdAddressGenerator;
/* 209 */     if (generator == null) {
/* 210 */       generator = QuicConnectionIdGenerator.signGenerator();
/*     */     }
/* 212 */     QuicResetTokenGenerator resetTokenGenerator = this.resetTokenGenerator;
/* 213 */     if (resetTokenGenerator == null) {
/* 214 */       resetTokenGenerator = QuicResetTokenGenerator.signGenerator();
/*     */     }
/* 216 */     ChannelHandler handler = this.handler;
/* 217 */     ChannelHandler streamHandler = this.streamHandler;
/* 218 */     return (ChannelHandler)new QuicheQuicServerCodec(config, localConnIdLength, tokenHandler, generator, resetTokenGenerator, flushStrategy, sslEngineProvider, sslTaskExecutor, handler, 
/*     */         
/* 220 */         Quic.toOptionsArray(this.options), Quic.toAttributesArray(this.attrs), streamHandler, 
/* 221 */         Quic.toOptionsArray(this.streamOptions), Quic.toAttributesArray(this.streamAttrs));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicServerCodecBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */