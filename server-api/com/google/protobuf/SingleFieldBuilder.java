/*     */ package com.google.protobuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SingleFieldBuilder<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder>
/*     */   implements AbstractMessage.BuilderParent
/*     */ {
/*     */   private AbstractMessage.BuilderParent parent;
/*     */   private BType builder;
/*     */   private MType message;
/*     */   private boolean isClean;
/*     */   
/*     */   public SingleFieldBuilder(MType message, AbstractMessage.BuilderParent parent, boolean isClean) {
/*  57 */     this.message = (MType)Internal.<GeneratedMessage>checkNotNull((GeneratedMessage)message);
/*  58 */     this.parent = parent;
/*  59 */     this.isClean = isClean;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  64 */     this.parent = null;
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
/*     */   public MType getMessage() {
/*  76 */     if (this.message == null)
/*     */     {
/*  78 */       this.message = (MType)this.builder.buildPartial();
/*     */     }
/*  80 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MType build() {
/*  91 */     this.isClean = true;
/*  92 */     return getMessage();
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
/*     */   public BType getBuilder() {
/*     */     GeneratedMessage.Builder builder1;
/* 107 */     BType builder = this.builder;
/*     */     
/* 109 */     if (builder == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       this.builder = (BType)(builder1 = (GeneratedMessage.Builder)this.message.newBuilderForType(this));
/* 115 */       builder1.mergeFrom((Message)this.message);
/* 116 */       builder1.markClean();
/*     */     } 
/* 118 */     return (BType)builder1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IType getMessageOrBuilder() {
/* 129 */     if (this.builder != null) {
/* 130 */       return (IType)this.builder;
/*     */     }
/* 132 */     return (IType)this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public SingleFieldBuilder<MType, BType, IType> setMessage(MType message) {
/* 144 */     this.message = (MType)Internal.<GeneratedMessage>checkNotNull((GeneratedMessage)message);
/* 145 */     if (this.builder != null) {
/* 146 */       this.builder.dispose();
/* 147 */       this.builder = null;
/*     */     } 
/* 149 */     onChanged();
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public SingleFieldBuilder<MType, BType, IType> mergeFrom(MType value) {
/* 161 */     if (this.builder == null && this.message == this.message.getDefaultInstanceForType()) {
/* 162 */       this.message = value;
/*     */     } else {
/* 164 */       getBuilder().mergeFrom((Message)value);
/*     */     } 
/* 166 */     onChanged();
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public SingleFieldBuilder<MType, BType, IType> clear() {
/* 178 */     this
/*     */ 
/*     */ 
/*     */       
/* 182 */       .message = (MType)((this.message != null) ? (GeneratedMessage)this.message.getDefaultInstanceForType() : (GeneratedMessage)this.builder.getDefaultInstanceForType());
/* 183 */     if (this.builder != null) {
/* 184 */       this.builder.dispose();
/* 185 */       this.builder = null;
/*     */     } 
/* 187 */     onChanged();
/*     */ 
/*     */     
/* 190 */     this.isClean = true;
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onChanged() {
/* 201 */     if (this.builder != null) {
/* 202 */       this.message = null;
/*     */     }
/* 204 */     if (this.isClean && this.parent != null) {
/* 205 */       this.parent.markDirty();
/*     */ 
/*     */       
/* 208 */       this.isClean = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 214 */     onChanged();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\SingleFieldBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */