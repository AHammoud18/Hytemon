/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.handler.codec.DefaultHeaders;
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
/*     */ public final class DefaultHttpHeadersFactory
/*     */   implements HttpHeadersFactory
/*     */ {
/*  30 */   private static final DefaultHeaders.NameValidator<CharSequence> DEFAULT_NAME_VALIDATOR = new DefaultHeaders.NameValidator<CharSequence>()
/*     */     {
/*     */       public void validateName(CharSequence name) {
/*  33 */         if (name == null || name.length() == 0) {
/*  34 */           throw new IllegalArgumentException("empty headers are not allowed [" + name + ']');
/*     */         }
/*  36 */         int index = HttpHeaderValidationUtil.validateToken(name);
/*  37 */         if (index != -1) {
/*  38 */           throw new IllegalArgumentException("a header name can only contain \"token\" characters, but found invalid character 0x" + 
/*  39 */               Integer.toHexString(name.charAt(index)) + " at index " + index + " of header '" + name + "'.");
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*  44 */   private static final DefaultHeaders.ValueValidator<CharSequence> DEFAULT_VALUE_VALIDATOR = new DefaultHeaders.ValueValidator<CharSequence>()
/*     */     {
/*     */       public void validate(CharSequence value) {
/*  47 */         int index = HttpHeaderValidationUtil.validateValidHeaderValue(value);
/*  48 */         if (index != -1)
/*  49 */           throw new IllegalArgumentException("a header value contains prohibited character 0x" + 
/*  50 */               Integer.toHexString(value.charAt(index)) + " at index " + index + '.'); 
/*     */       }
/*     */     };
/*     */   
/*  54 */   private static final DefaultHeaders.NameValidator<CharSequence> DEFAULT_TRAILER_NAME_VALIDATOR = new DefaultHeaders.NameValidator<CharSequence>()
/*     */     {
/*     */       public void validateName(CharSequence name)
/*     */       {
/*  58 */         DefaultHttpHeadersFactory.DEFAULT_NAME_VALIDATOR.validateName(name);
/*  59 */         if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(name) || HttpHeaderNames.TRANSFER_ENCODING
/*  60 */           .contentEqualsIgnoreCase(name) || HttpHeaderNames.TRAILER
/*  61 */           .contentEqualsIgnoreCase(name)) {
/*  62 */           throw new IllegalArgumentException("prohibited trailing header: " + name);
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  68 */   private static final DefaultHeaders.NameValidator<CharSequence> NO_NAME_VALIDATOR = DefaultHeaders.NameValidator.NOT_NULL;
/*     */   
/*  70 */   private static final DefaultHeaders.ValueValidator<CharSequence> NO_VALUE_VALIDATOR = DefaultHeaders.ValueValidator.NO_VALIDATION;
/*     */ 
/*     */   
/*  73 */   private static final DefaultHttpHeadersFactory DEFAULT = new DefaultHttpHeadersFactory(DEFAULT_NAME_VALIDATOR, DEFAULT_VALUE_VALIDATOR, false);
/*     */   
/*  75 */   private static final DefaultHttpHeadersFactory DEFAULT_TRAILER = new DefaultHttpHeadersFactory(DEFAULT_TRAILER_NAME_VALIDATOR, DEFAULT_VALUE_VALIDATOR, false);
/*     */   
/*  77 */   private static final DefaultHttpHeadersFactory DEFAULT_COMBINING = new DefaultHttpHeadersFactory(DEFAULT.nameValidator, DEFAULT.valueValidator, true);
/*     */   
/*  79 */   private static final DefaultHttpHeadersFactory DEFAULT_NO_VALIDATION = new DefaultHttpHeadersFactory(NO_NAME_VALIDATOR, NO_VALUE_VALIDATOR, false);
/*     */ 
/*     */ 
/*     */   
/*     */   private final DefaultHeaders.NameValidator<CharSequence> nameValidator;
/*     */ 
/*     */ 
/*     */   
/*     */   private final DefaultHeaders.ValueValidator<CharSequence> valueValidator;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean combiningHeaders;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DefaultHttpHeadersFactory(DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator, boolean combiningHeaders) {
/*  97 */     this.nameValidator = (DefaultHeaders.NameValidator<CharSequence>)ObjectUtil.checkNotNull(nameValidator, "nameValidator");
/*  98 */     this.valueValidator = (DefaultHeaders.ValueValidator<CharSequence>)ObjectUtil.checkNotNull(valueValidator, "valueValidator");
/*  99 */     this.combiningHeaders = combiningHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultHttpHeadersFactory headersFactory() {
/* 109 */     return DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultHttpHeadersFactory trailersFactory() {
/* 119 */     return DEFAULT_TRAILER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders newHeaders() {
/* 124 */     if (isCombiningHeaders()) {
/* 125 */       return new CombinedHttpHeaders(getNameValidator(), getValueValidator());
/*     */     }
/* 127 */     return new DefaultHttpHeaders(getNameValidator(), getValueValidator());
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders newEmptyHeaders() {
/* 132 */     if (isCombiningHeaders()) {
/* 133 */       return new CombinedHttpHeaders(getNameValidator(), getValueValidator(), 2);
/*     */     }
/* 135 */     return new DefaultHttpHeaders(getNameValidator(), getValueValidator(), 2);
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
/*     */   public DefaultHttpHeadersFactory withNameValidation(boolean validation) {
/* 154 */     return withNameValidator(validation ? DEFAULT_NAME_VALIDATOR : NO_NAME_VALIDATOR);
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
/*     */   public DefaultHttpHeadersFactory withNameValidator(DefaultHeaders.NameValidator<CharSequence> validator) {
/* 173 */     if (this.nameValidator == ObjectUtil.checkNotNull(validator, "validator")) {
/* 174 */       return this;
/*     */     }
/* 176 */     if (validator == DEFAULT_NAME_VALIDATOR && this.valueValidator == DEFAULT_VALUE_VALIDATOR) {
/* 177 */       return this.combiningHeaders ? DEFAULT_COMBINING : DEFAULT;
/*     */     }
/* 179 */     return new DefaultHttpHeadersFactory(validator, this.valueValidator, this.combiningHeaders);
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
/*     */   public DefaultHttpHeadersFactory withValueValidation(boolean validation) {
/* 198 */     return withValueValidator(validation ? DEFAULT_VALUE_VALIDATOR : NO_VALUE_VALIDATOR);
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
/*     */   public DefaultHttpHeadersFactory withValueValidator(DefaultHeaders.ValueValidator<CharSequence> validator) {
/* 217 */     if (this.valueValidator == ObjectUtil.checkNotNull(validator, "validator")) {
/* 218 */       return this;
/*     */     }
/* 220 */     if (this.nameValidator == DEFAULT_NAME_VALIDATOR && validator == DEFAULT_VALUE_VALIDATOR) {
/* 221 */       return this.combiningHeaders ? DEFAULT_COMBINING : DEFAULT;
/*     */     }
/* 223 */     return new DefaultHttpHeadersFactory(this.nameValidator, validator, this.combiningHeaders);
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
/*     */   public DefaultHttpHeadersFactory withValidation(boolean validation) {
/* 242 */     if (this == DEFAULT && !validation) {
/* 243 */       return DEFAULT_NO_VALIDATION;
/*     */     }
/* 245 */     if (this == DEFAULT_NO_VALIDATION && validation) {
/* 246 */       return DEFAULT;
/*     */     }
/* 248 */     return withNameValidation(validation).withValueValidation(validation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpHeadersFactory withCombiningHeaders(boolean combiningHeaders) {
/* 259 */     if (this.combiningHeaders == combiningHeaders) {
/* 260 */       return this;
/*     */     }
/* 262 */     return new DefaultHttpHeadersFactory(this.nameValidator, this.valueValidator, combiningHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHeaders.NameValidator<CharSequence> getNameValidator() {
/* 273 */     return this.nameValidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHeaders.ValueValidator<CharSequence> getValueValidator() {
/* 284 */     return this.valueValidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCombiningHeaders() {
/* 293 */     return this.combiningHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidatingHeaderNames() {
/* 302 */     return (this.nameValidator != NO_NAME_VALIDATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidatingHeaderValues() {
/* 311 */     return (this.valueValidator != NO_VALUE_VALIDATOR);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultHttpHeadersFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */