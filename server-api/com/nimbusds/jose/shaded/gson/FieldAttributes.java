/*     */ package com.nimbusds.jose.shaded.gson;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FieldAttributes
/*     */ {
/*     */   private final Field field;
/*     */   
/*     */   public FieldAttributes(Field f) {
/*  44 */     this.field = Objects.<Field>requireNonNull(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getDeclaringClass() {
/*  53 */     return this.field.getDeclaringClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  62 */     return this.field.getName();
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
/*     */   public Type getDeclaredType() {
/*  85 */     return this.field.getGenericType();
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
/*     */   public Class<?> getDeclaredClass() {
/* 106 */     return this.field.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> annotation) {
/* 117 */     return this.field.getAnnotation(annotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Annotation> getAnnotations() {
/* 127 */     return Arrays.asList(this.field.getAnnotations());
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
/*     */   public boolean hasModifier(int modifier) {
/* 142 */     return ((this.field.getModifiers() & modifier) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 147 */     return this.field.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\FieldAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */