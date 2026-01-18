/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RepeatedFieldBuilder<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder>
/*     */   implements AbstractMessage.BuilderParent
/*     */ {
/*     */   private AbstractMessage.BuilderParent parent;
/*     */   private Internal.ProtobufList<MType> messages;
/*     */   private List<SingleFieldBuilder<MType, BType, IType>> builders;
/*     */   private boolean isClean;
/*     */   private MessageExternalList<MType, BType, IType> externalMessageList;
/*     */   private BuilderExternalList<MType, BType, IType> externalBuilderList;
/*     */   private MessageOrBuilderExternalList<MType, BType, IType> externalMessageOrBuilderList;
/*     */   
/*     */   private static <MsgT extends GeneratedMessage> Internal.ProtobufList<MsgT> passthroughOrCopyToProtobufList(List<MsgT> messages) {
/*  93 */     if (messages instanceof Internal.ProtobufList) {
/*  94 */       return (Internal.ProtobufList<MsgT>)messages;
/*     */     }
/*     */     
/*  97 */     ProtobufArrayList<MsgT> copy = ProtobufArrayList.<MsgT>emptyList().mutableCopyWithCapacity(messages.size());
/*  98 */     copy.addAll(messages);
/*  99 */     return copy;
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
/*     */   public RepeatedFieldBuilder(List<MType> messages, boolean isMessagesListMutable, AbstractMessage.BuilderParent parent, boolean isClean) {
/* 115 */     this.messages = passthroughOrCopyToProtobufList(messages);
/* 116 */     this.parent = parent;
/* 117 */     this.isClean = isClean;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 122 */     this.parent = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureMutableMessageList() {
/* 130 */     if (!this.messages.isModifiable()) {
/* 131 */       this.messages = this.messages.mutableCopyWithCapacity(this.messages.size());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureBuilders() {
/* 140 */     if (this.builders == null) {
/* 141 */       this.builders = new ArrayList<>(this.messages.size());
/* 142 */       for (int i = 0; i < this.messages.size(); i++) {
/* 143 */         this.builders.add(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 154 */     return this.messages.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 163 */     return this.messages.isEmpty();
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
/*     */   public MType getMessage(int index) {
/* 175 */     return getMessage(index, false);
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
/*     */   private MType getMessage(int index, boolean forBuild) {
/* 189 */     if (this.builders == null)
/*     */     {
/*     */ 
/*     */       
/* 193 */       return this.messages.get(index);
/*     */     }
/*     */     
/* 196 */     SingleFieldBuilder<MType, BType, IType> builder = this.builders.get(index);
/* 197 */     if (builder == null)
/*     */     {
/*     */ 
/*     */       
/* 201 */       return this.messages.get(index);
/*     */     }
/*     */     
/* 204 */     return forBuild ? builder.build() : builder.getMessage();
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
/*     */   public BType getBuilder(int index) {
/* 216 */     ensureBuilders();
/* 217 */     SingleFieldBuilder<MType, BType, IType> builder = this.builders.get(index);
/* 218 */     if (builder == null) {
/* 219 */       GeneratedMessage generatedMessage = (GeneratedMessage)this.messages.get(index);
/* 220 */       builder = new SingleFieldBuilder<>((MType)generatedMessage, this, this.isClean);
/* 221 */       this.builders.set(index, builder);
/*     */     } 
/* 223 */     return builder.getBuilder();
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
/*     */   public IType getMessageOrBuilder(int index) {
/* 235 */     if (this.builders == null)
/*     */     {
/*     */ 
/*     */       
/* 239 */       return (IType)this.messages.get(index);
/*     */     }
/*     */     
/* 242 */     SingleFieldBuilder<MType, BType, IType> builder = this.builders.get(index);
/* 243 */     if (builder == null)
/*     */     {
/*     */ 
/*     */       
/* 247 */       return (IType)this.messages.get(index);
/*     */     }
/*     */     
/* 250 */     return builder.getMessageOrBuilder();
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
/*     */   @CanIgnoreReturnValue
/*     */   public RepeatedFieldBuilder<MType, BType, IType> setMessage(int index, MType message) {
/* 263 */     Internal.checkNotNull(message);
/* 264 */     ensureMutableMessageList();
/* 265 */     this.messages.set(index, message);
/* 266 */     if (this.builders != null) {
/* 267 */       SingleFieldBuilder<MType, BType, IType> entry = this.builders.set(index, null);
/* 268 */       if (entry != null) {
/* 269 */         entry.dispose();
/*     */       }
/*     */     } 
/* 272 */     onChanged();
/* 273 */     incrementModCounts();
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public RepeatedFieldBuilder<MType, BType, IType> addMessage(MType message) {
/* 285 */     Internal.checkNotNull(message);
/* 286 */     ensureMutableMessageList();
/* 287 */     this.messages.add(message);
/* 288 */     if (this.builders != null) {
/* 289 */       this.builders.add(null);
/*     */     }
/* 291 */     onChanged();
/* 292 */     incrementModCounts();
/* 293 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public RepeatedFieldBuilder<MType, BType, IType> addMessage(int index, MType message) {
/* 307 */     Internal.checkNotNull(message);
/* 308 */     ensureMutableMessageList();
/* 309 */     this.messages.add(index, message);
/* 310 */     if (this.builders != null) {
/* 311 */       this.builders.add(index, null);
/*     */     }
/* 313 */     onChanged();
/* 314 */     incrementModCounts();
/* 315 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public RepeatedFieldBuilder<MType, BType, IType> addAllMessages(Iterable<? extends MType> values) {
/* 328 */     for (GeneratedMessage generatedMessage : values) {
/* 329 */       Internal.checkNotNull(generatedMessage);
/*     */     }
/*     */ 
/*     */     
/* 333 */     int size = -1;
/* 334 */     if (values instanceof Collection) {
/* 335 */       Collection<?> collection = (Collection)values;
/* 336 */       if (collection.isEmpty()) {
/* 337 */         return this;
/*     */       }
/* 339 */       size = collection.size();
/*     */     } 
/* 341 */     ensureMutableMessageList();
/*     */     
/* 343 */     if (size >= 0 && this.messages instanceof ArrayList) {
/* 344 */       ((ArrayList)this.messages).ensureCapacity(this.messages.size() + size);
/*     */     }
/*     */     
/* 347 */     for (GeneratedMessage generatedMessage : values) {
/* 348 */       addMessage((MType)generatedMessage);
/*     */     }
/*     */     
/* 351 */     onChanged();
/* 352 */     incrementModCounts();
/* 353 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BType addBuilder(MType message) {
/* 363 */     ensureMutableMessageList();
/* 364 */     ensureBuilders();
/* 365 */     SingleFieldBuilder<MType, BType, IType> builder = new SingleFieldBuilder<>(message, this, this.isClean);
/*     */     
/* 367 */     this.messages.add(null);
/* 368 */     this.builders.add(builder);
/* 369 */     onChanged();
/* 370 */     incrementModCounts();
/* 371 */     return builder.getBuilder();
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
/*     */   public BType addBuilder(int index, MType message) {
/* 383 */     ensureMutableMessageList();
/* 384 */     ensureBuilders();
/* 385 */     SingleFieldBuilder<MType, BType, IType> builder = new SingleFieldBuilder<>(message, this, this.isClean);
/*     */     
/* 387 */     this.messages.add(index, null);
/* 388 */     this.builders.add(index, builder);
/* 389 */     onChanged();
/* 390 */     incrementModCounts();
/* 391 */     return builder.getBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int index) {
/* 401 */     ensureMutableMessageList();
/* 402 */     this.messages.remove(index);
/* 403 */     if (this.builders != null) {
/* 404 */       SingleFieldBuilder<MType, BType, IType> entry = this.builders.remove(index);
/* 405 */       if (entry != null) {
/* 406 */         entry.dispose();
/*     */       }
/*     */     } 
/* 409 */     onChanged();
/* 410 */     incrementModCounts();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 415 */     this.messages = ProtobufArrayList.emptyList();
/* 416 */     if (this.builders != null) {
/* 417 */       for (SingleFieldBuilder<MType, BType, IType> entry : this.builders) {
/* 418 */         if (entry != null) {
/* 419 */           entry.dispose();
/*     */         }
/*     */       } 
/* 422 */       this.builders = null;
/*     */     } 
/* 424 */     onChanged();
/* 425 */     incrementModCounts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MType> build() {
/* 436 */     this.isClean = true;
/*     */     
/* 438 */     if (!this.messages.isModifiable() && this.builders == null)
/*     */     {
/* 440 */       return this.messages;
/*     */     }
/*     */     
/* 443 */     boolean allMessagesInSync = true;
/* 444 */     if (!this.messages.isModifiable()) {
/*     */ 
/*     */       
/* 447 */       for (int j = 0; j < this.messages.size(); j++) {
/* 448 */         Message message = (Message)this.messages.get(j);
/* 449 */         SingleFieldBuilder<MType, BType, IType> builder = this.builders.get(j);
/* 450 */         if (builder != null && 
/* 451 */           builder.build() != message) {
/* 452 */           allMessagesInSync = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 457 */       if (allMessagesInSync)
/*     */       {
/* 459 */         return this.messages;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 464 */     ensureMutableMessageList();
/* 465 */     for (int i = 0; i < this.messages.size(); i++) {
/* 466 */       this.messages.set(i, getMessage(i, true));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 471 */     this.messages.makeImmutable();
/* 472 */     return this.messages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MType> getMessageList() {
/* 482 */     if (this.externalMessageList == null) {
/* 483 */       this.externalMessageList = new MessageExternalList<>(this);
/*     */     }
/* 485 */     return this.externalMessageList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BType> getBuilderList() {
/* 495 */     if (this.externalBuilderList == null) {
/* 496 */       this.externalBuilderList = new BuilderExternalList<>(this);
/*     */     }
/* 498 */     return this.externalBuilderList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IType> getMessageOrBuilderList() {
/* 508 */     if (this.externalMessageOrBuilderList == null) {
/* 509 */       this.externalMessageOrBuilderList = new MessageOrBuilderExternalList<>(this);
/*     */     }
/* 511 */     return this.externalMessageOrBuilderList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onChanged() {
/* 519 */     if (this.isClean && this.parent != null) {
/* 520 */       this.parent.markDirty();
/*     */ 
/*     */       
/* 523 */       this.isClean = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 529 */     onChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void incrementModCounts() {
/* 537 */     if (this.externalMessageList != null) {
/* 538 */       this.externalMessageList.incrementModCount();
/*     */     }
/* 540 */     if (this.externalBuilderList != null) {
/* 541 */       this.externalBuilderList.incrementModCount();
/*     */     }
/* 543 */     if (this.externalMessageOrBuilderList != null) {
/* 544 */       this.externalMessageOrBuilderList.incrementModCount();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MessageExternalList<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder>
/*     */     extends AbstractList<MType>
/*     */     implements List<MType>, RandomAccess
/*     */   {
/*     */     RepeatedFieldBuilder<MType, BType, IType> builder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MessageExternalList(RepeatedFieldBuilder<MType, BType, IType> builder) {
/* 564 */       this.builder = builder;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 569 */       return this.builder.getCount();
/*     */     }
/*     */ 
/*     */     
/*     */     public MType get(int index) {
/* 574 */       return this.builder.getMessage(index);
/*     */     }
/*     */     
/*     */     void incrementModCount() {
/* 578 */       this.modCount++;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class BuilderExternalList<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder>
/*     */     extends AbstractList<BType>
/*     */     implements List<BType>, RandomAccess
/*     */   {
/*     */     RepeatedFieldBuilder<MType, BType, IType> builder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BuilderExternalList(RepeatedFieldBuilder<MType, BType, IType> builder) {
/* 598 */       this.builder = builder;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 603 */       return this.builder.getCount();
/*     */     }
/*     */ 
/*     */     
/*     */     public BType get(int index) {
/* 608 */       return this.builder.getBuilder(index);
/*     */     }
/*     */     
/*     */     void incrementModCount() {
/* 612 */       this.modCount++;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MessageOrBuilderExternalList<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder>
/*     */     extends AbstractList<IType>
/*     */     implements List<IType>, RandomAccess
/*     */   {
/*     */     RepeatedFieldBuilder<MType, BType, IType> builder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MessageOrBuilderExternalList(RepeatedFieldBuilder<MType, BType, IType> builder) {
/* 632 */       this.builder = builder;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 637 */       return this.builder.getCount();
/*     */     }
/*     */ 
/*     */     
/*     */     public IType get(int index) {
/* 642 */       return this.builder.getMessageOrBuilder(index);
/*     */     }
/*     */     
/*     */     void incrementModCount() {
/* 646 */       this.modCount++;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\RepeatedFieldBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */