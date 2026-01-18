/*     */ package joptsimple.util;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import joptsimple.ValueConversionException;
/*     */ import joptsimple.ValueConverter;
/*     */ import joptsimple.internal.Messages;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateConverter
/*     */   implements ValueConverter<Date>
/*     */ {
/*     */   private final DateFormat formatter;
/*     */   
/*     */   public DateConverter(DateFormat formatter) {
/*  53 */     if (formatter == null) {
/*  54 */       throw new NullPointerException("illegal null formatter");
/*     */     }
/*  56 */     this.formatter = formatter;
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
/*     */   public static DateConverter datePattern(String pattern) {
/*  69 */     SimpleDateFormat formatter = new SimpleDateFormat(pattern);
/*  70 */     formatter.setLenient(false);
/*     */     
/*  72 */     return new DateConverter(formatter);
/*     */   }
/*     */   
/*     */   public Date convert(String value) {
/*  76 */     ParsePosition position = new ParsePosition(0);
/*     */     
/*  78 */     Date date = this.formatter.parse(value, position);
/*  79 */     if (position.getIndex() != value.length()) {
/*  80 */       throw new ValueConversionException(message(value));
/*     */     }
/*  82 */     return date;
/*     */   }
/*     */   
/*     */   public Class<Date> valueType() {
/*  86 */     return Date.class;
/*     */   }
/*     */   
/*     */   public String valuePattern() {
/*  90 */     return (this.formatter instanceof SimpleDateFormat) ? ((SimpleDateFormat)this.formatter)
/*  91 */       .toPattern() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String message(String value) {
/*     */     String key;
/*     */     Object[] arguments;
/*  99 */     if (this.formatter instanceof SimpleDateFormat) {
/* 100 */       key = "with.pattern.message";
/* 101 */       arguments = new Object[] { value, ((SimpleDateFormat)this.formatter).toPattern() };
/*     */     } else {
/* 103 */       key = "without.pattern.message";
/* 104 */       arguments = new Object[] { value };
/*     */     } 
/*     */     
/* 107 */     return Messages.message(
/* 108 */         Locale.getDefault(), "joptsimple.ExceptionMessages", DateConverter.class, key, arguments);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimpl\\util\DateConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */