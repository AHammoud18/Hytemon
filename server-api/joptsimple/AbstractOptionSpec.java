/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import joptsimple.internal.Reflection;
/*     */ import joptsimple.internal.ReflectionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractOptionSpec<V>
/*     */   implements OptionSpec<V>, OptionDescriptor
/*     */ {
/*  43 */   private final List<String> options = new ArrayList<>();
/*     */   private final String description;
/*     */   private boolean forHelp;
/*     */   
/*     */   AbstractOptionSpec(String option) {
/*  48 */     this(Collections.singletonList(option), "");
/*     */   }
/*     */   
/*     */   AbstractOptionSpec(List<String> options, String description) {
/*  52 */     arrangeOptions(options);
/*     */     
/*  54 */     this.description = description;
/*     */   }
/*     */   
/*     */   public final List<String> options() {
/*  58 */     return Collections.unmodifiableList(this.options);
/*     */   }
/*     */   
/*     */   public final List<V> values(OptionSet detectedOptions) {
/*  62 */     return detectedOptions.valuesOf(this);
/*     */   }
/*     */   
/*     */   public final V value(OptionSet detectedOptions) {
/*  66 */     return detectedOptions.valueOf(this);
/*     */   }
/*     */   
/*     */   public String description() {
/*  70 */     return this.description;
/*     */   }
/*     */   
/*     */   public final AbstractOptionSpec<V> forHelp() {
/*  74 */     this.forHelp = true;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public final boolean isForHelp() {
/*  79 */     return this.forHelp;
/*     */   }
/*     */   
/*     */   public boolean representsNonOptions() {
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract V convert(String paramString);
/*     */   
/*     */   protected V convertWith(ValueConverter<V> converter, String argument) {
/*     */     try {
/*  90 */       return (V)Reflection.convertWith(converter, argument);
/*  91 */     } catch (ReflectionException|ValueConversionException ex) {
/*  92 */       throw new OptionArgumentConversionException(this, argument, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String argumentTypeIndicatorFrom(ValueConverter<V> converter) {
/*  97 */     if (converter == null) {
/*  98 */       return null;
/*     */     }
/* 100 */     String pattern = converter.valuePattern();
/* 101 */     return (pattern == null) ? converter.valueType().getName() : pattern;
/*     */   }
/*     */ 
/*     */   
/*     */   abstract void handleOption(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet, String paramString);
/*     */   
/*     */   private void arrangeOptions(List<String> unarranged) {
/* 108 */     if (unarranged.size() == 1) {
/* 109 */       this.options.addAll(unarranged);
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     List<String> shortOptions = new ArrayList<>();
/* 114 */     List<String> longOptions = new ArrayList<>();
/*     */     
/* 116 */     for (String each : unarranged) {
/* 117 */       if (each.length() == 1) {
/* 118 */         shortOptions.add(each); continue;
/*     */       } 
/* 120 */       longOptions.add(each);
/*     */     } 
/*     */     
/* 123 */     Collections.sort(shortOptions);
/* 124 */     Collections.sort(longOptions);
/*     */     
/* 126 */     this.options.addAll(shortOptions);
/* 127 */     this.options.addAll(longOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 132 */     if (!(that instanceof AbstractOptionSpec)) {
/* 133 */       return false;
/*     */     }
/* 135 */     AbstractOptionSpec<?> other = (AbstractOptionSpec)that;
/* 136 */     return this.options.equals(other.options);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return this.options.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     return this.options.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\AbstractOptionSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */