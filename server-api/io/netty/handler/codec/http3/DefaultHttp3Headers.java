/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.handler.codec.CharSequenceValueConverter;
/*     */ import io.netty.handler.codec.DefaultHeaders;
/*     */ import io.netty.handler.codec.Headers;
/*     */ import io.netty.handler.codec.ValueConverter;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.ByteProcessor;
/*     */ import java.util.Iterator;
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
/*     */ public final class DefaultHttp3Headers
/*     */   extends DefaultHeaders<CharSequence, CharSequence, Http3Headers>
/*     */   implements Http3Headers
/*     */ {
/*  31 */   private static final ByteProcessor HTTP3_NAME_VALIDATOR_PROCESSOR = new ByteProcessor()
/*     */     {
/*     */       public boolean process(byte value) {
/*  34 */         return !AsciiString.isUpperCase(value);
/*     */       }
/*     */     };
/*  37 */   static final DefaultHeaders.NameValidator<CharSequence> HTTP3_NAME_VALIDATOR = new DefaultHeaders.NameValidator<CharSequence>()
/*     */     {
/*     */       public void validateName(@Nullable CharSequence name) {
/*  40 */         if (name == null || name.length() == 0) {
/*  41 */           throw new Http3HeadersValidationException(String.format("empty headers are not allowed [%s]", new Object[] { name }));
/*     */         }
/*  43 */         if (name instanceof AsciiString) {
/*     */           int index;
/*     */           try {
/*  46 */             index = ((AsciiString)name).forEachByte(DefaultHttp3Headers.HTTP3_NAME_VALIDATOR_PROCESSOR);
/*  47 */           } catch (Http3HeadersValidationException e) {
/*  48 */             throw e;
/*  49 */           } catch (Throwable t) {
/*  50 */             throw new Http3HeadersValidationException(
/*  51 */                 String.format("unexpected error. invalid header name [%s]", new Object[] { name }), t);
/*     */           } 
/*     */           
/*  54 */           if (index != -1) {
/*  55 */             throw new Http3HeadersValidationException(String.format("invalid header name [%s]", new Object[] { name }));
/*     */           }
/*     */         } else {
/*  58 */           for (int i = 0; i < name.length(); i++) {
/*  59 */             if (AsciiString.isUpperCase(name.charAt(i))) {
/*  60 */               throw new Http3HeadersValidationException(String.format("invalid header name [%s]", new Object[] { name }));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*  67 */   private DefaultHeaders.HeaderEntry<CharSequence, CharSequence> firstNonPseudo = this.head;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttp3Headers() {
/*  76 */     this(true);
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
/*     */   public DefaultHttp3Headers(boolean validate) {
/*  88 */     super(AsciiString.CASE_SENSITIVE_HASHER, (ValueConverter)CharSequenceValueConverter.INSTANCE, 
/*     */         
/*  90 */         validate ? HTTP3_NAME_VALIDATOR : DefaultHeaders.NameValidator.NOT_NULL);
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
/*     */   public DefaultHttp3Headers(boolean validate, int arraySizeHint) {
/* 104 */     super(AsciiString.CASE_SENSITIVE_HASHER, (ValueConverter)CharSequenceValueConverter.INSTANCE, 
/*     */         
/* 106 */         validate ? HTTP3_NAME_VALIDATOR : DefaultHeaders.NameValidator.NOT_NULL, arraySizeHint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3Headers clear() {
/* 112 */     this.firstNonPseudo = this.head;
/* 113 */     return (Http3Headers)super.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 118 */     return (o instanceof Http3Headers && equals((Http3Headers)o, AsciiString.CASE_SENSITIVE_HASHER));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     return hashCode(AsciiString.CASE_SENSITIVE_HASHER);
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3Headers method(CharSequence value) {
/* 128 */     set(Http3Headers.PseudoHeaderName.METHOD.value(), value);
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3Headers scheme(CharSequence value) {
/* 134 */     set(Http3Headers.PseudoHeaderName.SCHEME.value(), value);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3Headers authority(CharSequence value) {
/* 140 */     set(Http3Headers.PseudoHeaderName.AUTHORITY.value(), value);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3Headers path(CharSequence value) {
/* 146 */     set(Http3Headers.PseudoHeaderName.PATH.value(), value);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3Headers status(CharSequence value) {
/* 152 */     set(Http3Headers.PseudoHeaderName.STATUS.value(), value);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence method() {
/* 158 */     return (CharSequence)get(Http3Headers.PseudoHeaderName.METHOD.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence scheme() {
/* 163 */     return (CharSequence)get(Http3Headers.PseudoHeaderName.SCHEME.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence authority() {
/* 168 */     return (CharSequence)get(Http3Headers.PseudoHeaderName.AUTHORITY.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence path() {
/* 173 */     return (CharSequence)get(Http3Headers.PseudoHeaderName.PATH.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence status() {
/* 178 */     return (CharSequence)get(Http3Headers.PseudoHeaderName.STATUS.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(CharSequence name, CharSequence value) {
/* 183 */     return contains(name, value, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(CharSequence name, CharSequence value, boolean caseInsensitive) {
/* 188 */     return contains(name, value, caseInsensitive ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DefaultHeaders.HeaderEntry<CharSequence, CharSequence> newHeaderEntry(int h, CharSequence name, CharSequence value, DefaultHeaders.HeaderEntry<CharSequence, CharSequence> next) {
/* 194 */     return new Http3HeaderEntry(h, name, value, next);
/*     */   }
/*     */   
/*     */   private final class Http3HeaderEntry
/*     */     extends DefaultHeaders.HeaderEntry<CharSequence, CharSequence> {
/*     */     protected Http3HeaderEntry(int hash, CharSequence key, CharSequence value, DefaultHeaders.HeaderEntry<CharSequence, CharSequence> next) {
/* 200 */       super(hash, key);
/* 201 */       this.value = value;
/* 202 */       this.next = next;
/*     */ 
/*     */       
/* 205 */       if (Http3Headers.PseudoHeaderName.hasPseudoHeaderFormat(key)) {
/* 206 */         this.after = DefaultHttp3Headers.this.firstNonPseudo;
/* 207 */         this.before = DefaultHttp3Headers.this.firstNonPseudo.before();
/*     */       } else {
/* 209 */         this.after = DefaultHttp3Headers.this.head;
/* 210 */         this.before = DefaultHttp3Headers.this.head.before();
/* 211 */         if (DefaultHttp3Headers.this.firstNonPseudo == DefaultHttp3Headers.this.head) {
/* 212 */           DefaultHttp3Headers.this.firstNonPseudo = this;
/*     */         }
/*     */       } 
/* 215 */       pointNeighborsToThis();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void remove() {
/* 220 */       if (this == DefaultHttp3Headers.this.firstNonPseudo) {
/* 221 */         DefaultHttp3Headers.this.firstNonPseudo = DefaultHttp3Headers.this.firstNonPseudo.after();
/*     */       }
/* 223 */       super.remove();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\DefaultHttp3Headers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */