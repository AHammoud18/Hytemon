/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.StringTokenizer;
/*     */ import joptsimple.internal.Reflection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ArgumentAcceptingOptionSpec<V>
/*     */   extends AbstractOptionSpec<V>
/*     */ {
/*     */   private static final char NIL_VALUE_SEPARATOR = '\000';
/*     */   private final boolean argumentRequired;
/*  61 */   private final List<V> defaultValues = new ArrayList<>();
/*     */   
/*     */   private boolean optionRequired;
/*     */   private ValueConverter<V> converter;
/*  65 */   private String argumentDescription = "";
/*  66 */   private String valueSeparator = String.valueOf(false);
/*     */   
/*     */   ArgumentAcceptingOptionSpec(String option, boolean argumentRequired) {
/*  69 */     super(option);
/*     */     
/*  71 */     this.argumentRequired = argumentRequired;
/*     */   }
/*     */   
/*     */   ArgumentAcceptingOptionSpec(List<String> options, boolean argumentRequired, String description) {
/*  75 */     super(options, description);
/*     */     
/*  77 */     this.argumentRequired = argumentRequired;
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
/*     */ 
/*     */   
/*     */   public final <T> ArgumentAcceptingOptionSpec<T> ofType(Class<T> argumentType) {
/* 106 */     return withValuesConvertedBy(Reflection.findConverter(argumentType));
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
/*     */   public final <T> ArgumentAcceptingOptionSpec<T> withValuesConvertedBy(ValueConverter<T> aConverter) {
/* 123 */     if (aConverter == null) {
/* 124 */       throw new NullPointerException("illegal null converter");
/*     */     }
/* 126 */     this.converter = aConverter;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ArgumentAcceptingOptionSpec<V> describedAs(String description) {
/* 138 */     this.argumentDescription = description;
/* 139 */     return this;
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
/*     */   public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(char separator) {
/* 164 */     if (separator == '\000') {
/* 165 */       throw new IllegalArgumentException("cannot use U+0000 as separator");
/*     */     }
/* 167 */     this.valueSeparator = String.valueOf(separator);
/* 168 */     return this;
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
/*     */   public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(String separator) {
/* 193 */     if (separator.indexOf(false) != -1) {
/* 194 */       throw new IllegalArgumentException("cannot use U+0000 in separator");
/*     */     }
/* 196 */     this.valueSeparator = separator;
/* 197 */     return this;
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
/*     */   @SafeVarargs
/*     */   public final ArgumentAcceptingOptionSpec<V> defaultsTo(V value, V... values) {
/* 211 */     addDefaultValue(value);
/* 212 */     defaultsTo(values);
/*     */     
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentAcceptingOptionSpec<V> defaultsTo(V[] values) {
/* 225 */     for (V each : values) {
/* 226 */       addDefaultValue(each);
/*     */     }
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentAcceptingOptionSpec<V> required() {
/* 239 */     this.optionRequired = true;
/* 240 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 244 */     return this.optionRequired;
/*     */   }
/*     */   
/*     */   private void addDefaultValue(V value) {
/* 248 */     Objects.requireNonNull(value);
/* 249 */     this.defaultValues.add(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void handleOption(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions, String detectedArgument) {
/* 256 */     if (detectedArgument == null) {
/* 257 */       detectOptionArgument(parser, arguments, detectedOptions);
/*     */     } else {
/* 259 */       addArguments(detectedOptions, detectedArgument);
/*     */     } 
/*     */   }
/*     */   protected void addArguments(OptionSet detectedOptions, String detectedArgument) {
/* 263 */     StringTokenizer lexer = new StringTokenizer(detectedArgument, this.valueSeparator);
/* 264 */     if (!lexer.hasMoreTokens()) {
/* 265 */       detectedOptions.addWithArgument(this, detectedArgument);
/*     */     } else {
/* 267 */       while (lexer.hasMoreTokens()) {
/* 268 */         detectedOptions.addWithArgument(this, lexer.nextToken());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void detectOptionArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet);
/*     */   
/*     */   protected final V convert(String argument) {
/* 277 */     return convertWith(this.converter, argument);
/*     */   }
/*     */   
/*     */   protected boolean canConvertArgument(String argument) {
/* 281 */     StringTokenizer lexer = new StringTokenizer(argument, this.valueSeparator);
/*     */     
/*     */     try {
/* 284 */       while (lexer.hasMoreTokens())
/* 285 */         convert(lexer.nextToken()); 
/* 286 */       return true;
/* 287 */     } catch (OptionException ignored) {
/* 288 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isArgumentOfNumberType() {
/* 293 */     return (this.converter != null && Number.class.isAssignableFrom(this.converter.valueType()));
/*     */   }
/*     */   
/*     */   public boolean acceptsArguments() {
/* 297 */     return true;
/*     */   }
/*     */   
/*     */   public boolean requiresArgument() {
/* 301 */     return this.argumentRequired;
/*     */   }
/*     */   
/*     */   public String argumentDescription() {
/* 305 */     return this.argumentDescription;
/*     */   }
/*     */   
/*     */   public String argumentTypeIndicator() {
/* 309 */     return argumentTypeIndicatorFrom(this.converter);
/*     */   }
/*     */   
/*     */   public List<V> defaultValues() {
/* 313 */     return Collections.unmodifiableList(this.defaultValues);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 318 */     if (!super.equals(that)) {
/* 319 */       return false;
/*     */     }
/* 321 */     ArgumentAcceptingOptionSpec<?> other = (ArgumentAcceptingOptionSpec)that;
/* 322 */     return (requiresArgument() == other.requiresArgument());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 327 */     return super.hashCode() ^ (this.argumentRequired ? 0 : 1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\ArgumentAcceptingOptionSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */