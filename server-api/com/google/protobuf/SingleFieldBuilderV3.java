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
/*     */ @Deprecated
/*     */ public class SingleFieldBuilderV3<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder>
/*     */   implements AbstractMessage.BuilderParent
/*     */ {
/*     */   private AbstractMessage.BuilderParent parent;
/*     */   private BType builder;
/*     */   private MType message;
/*     */   private boolean isClean;
/*     */   
/*     */   @Deprecated
/*     */   public SingleFieldBuilderV3(MType message, AbstractMessage.BuilderParent parent, boolean isClean) {
/*  39 */     this.message = (MType)Internal.<AbstractMessage>checkNotNull((AbstractMessage)message);
/*  40 */     this.parent = parent;
/*  41 */     this.isClean = isClean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void dispose() {
/*  51 */     this.parent = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MType getMessage() {
/*  62 */     if (this.message == null) {
/*  63 */       this.message = (MType)this.builder.buildPartial();
/*     */     }
/*  65 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MType build() {
/*  75 */     this.isClean = true;
/*  76 */     return getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BType getBuilder() {
/*  87 */     if (this.builder == null) {
/*  88 */       this.builder = (BType)this.message.newBuilderForType(this);
/*  89 */       this.builder.mergeFrom((Message)this.message);
/*  90 */       this.builder.markClean();
/*     */     } 
/*  92 */     return this.builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public IType getMessageOrBuilder() {
/* 103 */     if (this.builder != null) {
/* 104 */       return (IType)this.builder;
/*     */     }
/* 106 */     return (IType)this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @CanIgnoreReturnValue
/*     */   public SingleFieldBuilderV3<MType, BType, IType> setMessage(MType message) {
/* 118 */     this.message = (MType)Internal.<AbstractMessage>checkNotNull((AbstractMessage)message);
/* 119 */     if (this.builder != null) {
/* 120 */       this.builder.dispose();
/* 121 */       this.builder = null;
/*     */     } 
/* 123 */     onChanged();
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @CanIgnoreReturnValue
/*     */   public SingleFieldBuilderV3<MType, BType, IType> mergeFrom(MType value) {
/* 135 */     if (this.builder == null && this.message == this.message.getDefaultInstanceForType()) {
/* 136 */       this.message = value;
/*     */     } else {
/* 138 */       getBuilder().mergeFrom((Message)value);
/*     */     } 
/* 140 */     onChanged();
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @CanIgnoreReturnValue
/*     */   public SingleFieldBuilderV3<MType, BType, IType> clear() {
/* 153 */     this
/*     */ 
/*     */ 
/*     */       
/* 157 */       .message = (MType)((this.message != null) ? (AbstractMessage)this.message.getDefaultInstanceForType() : (AbstractMessage)this.builder.getDefaultInstanceForType());
/* 158 */     if (this.builder != null) {
/* 159 */       this.builder.dispose();
/* 160 */       this.builder = null;
/*     */     } 
/* 162 */     onChanged();
/* 163 */     this.isClean = true;
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private void onChanged() {
/* 174 */     if (this.builder != null) {
/* 175 */       this.message = null;
/*     */     }
/* 177 */     if (this.isClean && this.parent != null) {
/* 178 */       this.parent.markDirty();
/*     */       
/* 180 */       this.isClean = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void markDirty() {
/* 192 */     onChanged();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\SingleFieldBuilderV3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */