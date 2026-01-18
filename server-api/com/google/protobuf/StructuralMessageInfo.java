/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CheckReturnValue
/*     */ final class StructuralMessageInfo
/*     */   implements MessageInfo
/*     */ {
/*     */   private final ProtoSyntax syntax;
/*     */   private final boolean messageSetWireFormat;
/*     */   private final int[] checkInitialized;
/*     */   private final FieldInfo[] fields;
/*     */   private final MessageLite defaultInstance;
/*     */   
/*     */   StructuralMessageInfo(ProtoSyntax syntax, boolean messageSetWireFormat, int[] checkInitialized, FieldInfo[] fields, Object defaultInstance) {
/*  41 */     this.syntax = syntax;
/*  42 */     this.messageSetWireFormat = messageSetWireFormat;
/*  43 */     this.checkInitialized = checkInitialized;
/*  44 */     this.fields = fields;
/*  45 */     this.defaultInstance = (MessageLite)Internal.<Object>checkNotNull(defaultInstance, "defaultInstance");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtoSyntax getSyntax() {
/*  51 */     return this.syntax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMessageSetWireFormat() {
/*  57 */     return this.messageSetWireFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getCheckInitialized() {
/*  62 */     return this.checkInitialized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldInfo[] getFields() {
/*  70 */     return this.fields;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageLite getDefaultInstance() {
/*  75 */     return this.defaultInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Builder newBuilder() {
/*  80 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Builder newBuilder(int numFields) {
/*  85 */     return new Builder(numFields);
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private final List<FieldInfo> fields;
/*     */     private ProtoSyntax syntax;
/*     */     private boolean wasBuilt;
/*     */     private boolean messageSetWireFormat;
/*  94 */     private int[] checkInitialized = null;
/*     */     private Object defaultInstance;
/*     */     
/*     */     public Builder() {
/*  98 */       this.fields = new ArrayList<>();
/*     */     }
/*     */     
/*     */     public Builder(int numFields) {
/* 102 */       this.fields = new ArrayList<>(numFields);
/*     */     }
/*     */     
/*     */     public void withDefaultInstance(Object defaultInstance) {
/* 106 */       this.defaultInstance = defaultInstance;
/*     */     }
/*     */     
/*     */     public void withSyntax(ProtoSyntax syntax) {
/* 110 */       this.syntax = Internal.<ProtoSyntax>checkNotNull(syntax, "syntax");
/*     */     }
/*     */     
/*     */     public void withMessageSetWireFormat(boolean messageSetWireFormat) {
/* 114 */       this.messageSetWireFormat = messageSetWireFormat;
/*     */     }
/*     */     
/*     */     public void withCheckInitialized(int[] checkInitialized) {
/* 118 */       this.checkInitialized = checkInitialized;
/*     */     }
/*     */     
/*     */     public void withField(FieldInfo field) {
/* 122 */       if (this.wasBuilt) {
/* 123 */         throw new IllegalStateException("Builder can only build once");
/*     */       }
/* 125 */       this.fields.add(field);
/*     */     }
/*     */     
/*     */     public StructuralMessageInfo build() {
/* 129 */       if (this.wasBuilt) {
/* 130 */         throw new IllegalStateException("Builder can only build once");
/*     */       }
/* 132 */       if (this.syntax == null) {
/* 133 */         throw new IllegalStateException("Must specify a proto syntax");
/*     */       }
/* 135 */       this.wasBuilt = true;
/* 136 */       Collections.sort(this.fields);
/* 137 */       return new StructuralMessageInfo(this.syntax, this.messageSetWireFormat, this.checkInitialized, this.fields
/*     */ 
/*     */ 
/*     */           
/* 141 */           .<FieldInfo>toArray(new FieldInfo[0]), this.defaultInstance);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\StructuralMessageInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */