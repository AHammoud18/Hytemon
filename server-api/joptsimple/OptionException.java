/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import joptsimple.internal.Messages;
/*     */ import joptsimple.internal.Strings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OptionException
/*     */   extends RuntimeException
/*     */ {
/*     */   private static final long serialVersionUID = -1L;
/*  49 */   private final List<String> options = new ArrayList<>();
/*     */   
/*     */   protected OptionException(List<String> options) {
/*  52 */     this.options.addAll(options);
/*     */   }
/*     */   
/*     */   protected OptionException(Collection<? extends OptionSpec<?>> options) {
/*  56 */     this.options.addAll(specsToStrings(options));
/*     */   }
/*     */   
/*     */   protected OptionException(Collection<? extends OptionSpec<?>> options, Throwable cause) {
/*  60 */     super(cause);
/*  61 */     this.options.addAll(specsToStrings(options));
/*     */   }
/*     */   
/*     */   private List<String> specsToStrings(Collection<? extends OptionSpec<?>> options) {
/*  65 */     List<String> strings = new ArrayList<>();
/*  66 */     for (OptionSpec<?> each : options)
/*  67 */       strings.add(specToString(each)); 
/*  68 */     return strings;
/*     */   }
/*     */   
/*     */   private String specToString(OptionSpec<?> option) {
/*  72 */     return Strings.join(new ArrayList<>(option.options()), "/");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> options() {
/*  81 */     return Collections.unmodifiableList(this.options);
/*     */   }
/*     */   
/*     */   protected final String singleOptionString() {
/*  85 */     return singleOptionString(this.options.get(0));
/*     */   }
/*     */   
/*     */   protected final String singleOptionString(String option) {
/*  89 */     return option;
/*     */   }
/*     */   
/*     */   protected final String multipleOptionString() {
/*  93 */     StringBuilder buffer = new StringBuilder("[");
/*     */     
/*  95 */     Set<String> asSet = new LinkedHashSet<>(this.options);
/*  96 */     for (Iterator<String> iter = asSet.iterator(); iter.hasNext(); ) {
/*  97 */       buffer.append(singleOptionString(iter.next()));
/*  98 */       if (iter.hasNext()) {
/*  99 */         buffer.append(", ");
/*     */       }
/*     */     } 
/* 102 */     buffer.append(']');
/*     */     
/* 104 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   static OptionException unrecognizedOption(String option) {
/* 108 */     return new UnrecognizedOptionException(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getMessage() {
/* 113 */     return localizedMessage(Locale.getDefault());
/*     */   }
/*     */   
/*     */   final String localizedMessage(Locale locale) {
/* 117 */     return formattedMessage(locale);
/*     */   }
/*     */   
/*     */   private String formattedMessage(Locale locale) {
/* 121 */     return Messages.message(locale, "joptsimple.ExceptionMessages", getClass(), "message", messageArguments());
/*     */   }
/*     */   
/*     */   abstract Object[] messageArguments();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */