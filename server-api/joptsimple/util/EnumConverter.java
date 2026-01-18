/*     */ package joptsimple.util;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.ResourceBundle;
/*     */ import joptsimple.ValueConversionException;
/*     */ import joptsimple.ValueConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EnumConverter<E extends Enum<E>>
/*     */   implements ValueConverter<E>
/*     */ {
/*     */   private final Class<E> clazz;
/*  44 */   private String delimiters = "[,]";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumConverter(Class<E> clazz) {
/*  52 */     this.clazz = clazz;
/*     */   }
/*     */ 
/*     */   
/*     */   public E convert(String value) {
/*  57 */     for (Enum enum_ : (Enum[])valueType().getEnumConstants()) {
/*  58 */       if (enum_.name().equalsIgnoreCase(value)) {
/*  59 */         return (E)enum_;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     throw new ValueConversionException(message(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<E> valueType() {
/*  68 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDelimiters(String delimiters) {
/*  79 */     this.delimiters = delimiters;
/*     */   }
/*     */ 
/*     */   
/*     */   public String valuePattern() {
/*  84 */     EnumSet<E> values = EnumSet.allOf(valueType());
/*     */     
/*  86 */     StringBuilder builder = new StringBuilder();
/*  87 */     builder.append(this.delimiters.charAt(0));
/*  88 */     for (Iterator<E> i = values.iterator(); i.hasNext(); ) {
/*  89 */       builder.append(((Enum)i.next()).toString());
/*  90 */       if (i.hasNext())
/*  91 */         builder.append(this.delimiters.charAt(1)); 
/*     */     } 
/*  93 */     builder.append(this.delimiters.charAt(2));
/*     */     
/*  95 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private String message(String value) {
/*  99 */     ResourceBundle bundle = ResourceBundle.getBundle("joptsimple.ExceptionMessages");
/* 100 */     Object[] arguments = { value, valuePattern() };
/* 101 */     String template = bundle.getString(EnumConverter.class.getName() + ".message");
/* 102 */     return (new MessageFormat(template)).format(arguments);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimpl\\util\EnumConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */