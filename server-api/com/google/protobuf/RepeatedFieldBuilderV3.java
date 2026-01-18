/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ @Deprecated
/*     */ public class RepeatedFieldBuilderV3<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder>
/*     */   implements AbstractMessage.BuilderParent
/*     */ {
/*     */   private AbstractMessage.BuilderParent parent;
/*     */   private List<MType> messages;
/*     */   private boolean isMessagesListMutable;
/*     */   private List<SingleFieldBuilderV3<MType, BType, IType>> builders;
/*     */   private boolean isClean;
/*     */   private MessageExternalList<MType, BType, IType> externalMessageList;
/*     */   private BuilderExternalList<MType, BType, IType> externalBuilderList;
/*     */   private MessageOrBuilderExternalList<MType, BType, IType> externalMessageOrBuilderList;
/*     */   
/*     */   @Deprecated
/*     */   public RepeatedFieldBuilderV3(List<MType> messages, boolean isMessagesListMutable, AbstractMessage.BuilderParent parent, boolean isClean) {
/*  57 */     this.messages = messages;
/*  58 */     this.isMessagesListMutable = isMessagesListMutable;
/*  59 */     this.parent = parent;
/*  60 */     this.isClean = isClean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void dispose() {
/*  70 */     this.parent = null;
/*     */   }
/*     */   
/*     */   private void ensureMutableMessageList() {
/*  74 */     if (!this.isMessagesListMutable) {
/*  75 */       this.messages = new ArrayList<>(this.messages);
/*  76 */       this.isMessagesListMutable = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void ensureBuilders() {
/*  81 */     if (this.builders == null) {
/*  82 */       this.builders = new ArrayList<>(this.messages.size());
/*  83 */       for (int i = 0; i < this.messages.size(); i++) {
/*  84 */         this.builders.add(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getCount() {
/*  96 */     return this.messages.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isEmpty() {
/* 106 */     return this.messages.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MType getMessage(int index) {
/* 116 */     return getMessage(index, false);
/*     */   }
/*     */   
/*     */   private MType getMessage(int index, boolean forBuild) {
/* 120 */     if (this.builders == null) {
/* 121 */       return this.messages.get(index);
/*     */     }
/*     */     
/* 124 */     SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(index);
/* 125 */     if (builder == null) {
/* 126 */       return this.messages.get(index);
/*     */     }
/*     */     
/* 129 */     return forBuild ? builder.build() : builder.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BType getBuilder(int index) {
/* 140 */     ensureBuilders();
/* 141 */     SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(index);
/* 142 */     if (builder == null) {
/* 143 */       AbstractMessage abstractMessage = (AbstractMessage)this.messages.get(index);
/* 144 */       builder = new SingleFieldBuilderV3<>((MType)abstractMessage, this, this.isClean);
/* 145 */       this.builders.set(index, builder);
/*     */     } 
/* 147 */     return builder.getBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public IType getMessageOrBuilder(int index) {
/* 158 */     if (this.builders == null) {
/* 159 */       return (IType)this.messages.get(index);
/*     */     }
/*     */     
/* 162 */     SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(index);
/* 163 */     if (builder == null) {
/* 164 */       return (IType)this.messages.get(index);
/*     */     }
/*     */     
/* 167 */     return builder.getMessageOrBuilder();
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
/*     */   public RepeatedFieldBuilderV3<MType, BType, IType> setMessage(int index, MType message) {
/* 179 */     Internal.checkNotNull(message);
/* 180 */     ensureMutableMessageList();
/* 181 */     this.messages.set(index, message);
/* 182 */     if (this.builders != null) {
/* 183 */       SingleFieldBuilderV3<MType, BType, IType> entry = this.builders.set(index, null);
/* 184 */       if (entry != null) {
/* 185 */         entry.dispose();
/*     */       }
/*     */     } 
/* 188 */     onChanged();
/* 189 */     incrementModCounts();
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @CanIgnoreReturnValue
/*     */   public RepeatedFieldBuilderV3<MType, BType, IType> addMessage(MType message) {
/* 201 */     Internal.checkNotNull(message);
/* 202 */     ensureMutableMessageList();
/* 203 */     this.messages.add(message);
/* 204 */     if (this.builders != null) {
/* 205 */       this.builders.add(null);
/*     */     }
/* 207 */     onChanged();
/* 208 */     incrementModCounts();
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @CanIgnoreReturnValue
/*     */   public RepeatedFieldBuilderV3<MType, BType, IType> addMessage(int index, MType message) {
/* 220 */     Internal.checkNotNull(message);
/* 221 */     ensureMutableMessageList();
/* 222 */     this.messages.add(index, message);
/* 223 */     if (this.builders != null) {
/* 224 */       this.builders.add(index, null);
/*     */     }
/* 226 */     onChanged();
/* 227 */     incrementModCounts();
/* 228 */     return this;
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
/*     */   public RepeatedFieldBuilderV3<MType, BType, IType> addAllMessages(Iterable<? extends MType> values) {
/* 240 */     for (AbstractMessage abstractMessage : values) {
/* 241 */       Internal.checkNotNull(abstractMessage);
/*     */     }
/*     */     
/* 244 */     int size = -1;
/* 245 */     if (values instanceof Collection) {
/* 246 */       Collection<?> collection = (Collection)values;
/* 247 */       if (collection.isEmpty()) {
/* 248 */         return this;
/*     */       }
/* 250 */       size = collection.size();
/*     */     } 
/* 252 */     ensureMutableMessageList();
/*     */     
/* 254 */     if (size >= 0 && this.messages instanceof ArrayList) {
/* 255 */       ((ArrayList)this.messages).ensureCapacity(this.messages.size() + size);
/*     */     }
/*     */     
/* 258 */     for (AbstractMessage abstractMessage : values) {
/* 259 */       addMessage((MType)abstractMessage);
/*     */     }
/*     */     
/* 262 */     onChanged();
/* 263 */     incrementModCounts();
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BType addBuilder(MType message) {
/* 274 */     ensureMutableMessageList();
/* 275 */     ensureBuilders();
/* 276 */     SingleFieldBuilderV3<MType, BType, IType> builder = new SingleFieldBuilderV3<>(message, this, this.isClean);
/*     */     
/* 278 */     this.messages.add(null);
/* 279 */     this.builders.add(builder);
/* 280 */     onChanged();
/* 281 */     incrementModCounts();
/* 282 */     return builder.getBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BType addBuilder(int index, MType message) {
/* 292 */     ensureMutableMessageList();
/* 293 */     ensureBuilders();
/* 294 */     SingleFieldBuilderV3<MType, BType, IType> builder = new SingleFieldBuilderV3<>(message, this, this.isClean);
/*     */     
/* 296 */     this.messages.add(index, null);
/* 297 */     this.builders.add(index, builder);
/* 298 */     onChanged();
/* 299 */     incrementModCounts();
/* 300 */     return builder.getBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void remove(int index) {
/* 310 */     ensureMutableMessageList();
/* 311 */     this.messages.remove(index);
/* 312 */     if (this.builders != null) {
/* 313 */       SingleFieldBuilderV3<MType, BType, IType> entry = this.builders.remove(index);
/* 314 */       if (entry != null) {
/* 315 */         entry.dispose();
/*     */       }
/*     */     } 
/* 318 */     onChanged();
/* 319 */     incrementModCounts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void clear() {
/* 329 */     this.messages = Collections.emptyList();
/* 330 */     this.isMessagesListMutable = false;
/* 331 */     if (this.builders != null) {
/* 332 */       for (SingleFieldBuilderV3<MType, BType, IType> entry : this.builders) {
/* 333 */         if (entry != null) {
/* 334 */           entry.dispose();
/*     */         }
/*     */       } 
/* 337 */       this.builders = null;
/*     */     } 
/* 339 */     onChanged();
/* 340 */     incrementModCounts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<MType> build() {
/* 350 */     this.isClean = true;
/*     */     
/* 352 */     if (!this.isMessagesListMutable && this.builders == null) {
/* 353 */       return this.messages;
/*     */     }
/*     */     
/* 356 */     boolean allMessagesInSync = true;
/* 357 */     if (!this.isMessagesListMutable) {
/* 358 */       for (int j = 0; j < this.messages.size(); j++) {
/* 359 */         Message message = (Message)this.messages.get(j);
/* 360 */         SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(j);
/* 361 */         if (builder != null && 
/* 362 */           builder.build() != message) {
/* 363 */           allMessagesInSync = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 368 */       if (allMessagesInSync) {
/* 369 */         return this.messages;
/*     */       }
/*     */     } 
/* 372 */     ensureMutableMessageList();
/* 373 */     for (int i = 0; i < this.messages.size(); i++) {
/* 374 */       this.messages.set(i, getMessage(i, true));
/*     */     }
/* 376 */     this.messages = Collections.unmodifiableList(this.messages);
/* 377 */     this.isMessagesListMutable = false;
/* 378 */     return this.messages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<MType> getMessageList() {
/* 388 */     if (this.externalMessageList == null) {
/* 389 */       this.externalMessageList = new MessageExternalList<>(this);
/*     */     }
/* 391 */     return this.externalMessageList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<BType> getBuilderList() {
/* 401 */     if (this.externalBuilderList == null) {
/* 402 */       this.externalBuilderList = new BuilderExternalList<>(this);
/*     */     }
/* 404 */     return this.externalBuilderList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<IType> getMessageOrBuilderList() {
/* 414 */     if (this.externalMessageOrBuilderList == null) {
/* 415 */       this.externalMessageOrBuilderList = new MessageOrBuilderExternalList<>(this);
/*     */     }
/* 417 */     return this.externalMessageOrBuilderList;
/*     */   }
/*     */   
/*     */   private void onChanged() {
/* 421 */     if (this.isClean && this.parent != null) {
/* 422 */       this.parent.markDirty();
/* 423 */       this.isClean = false;
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
/* 435 */     onChanged();
/*     */   }
/*     */   
/*     */   private void incrementModCounts() {
/* 439 */     if (this.externalMessageList != null) {
/* 440 */       this.externalMessageList.incrementModCount();
/*     */     }
/* 442 */     if (this.externalBuilderList != null) {
/* 443 */       this.externalBuilderList.incrementModCount();
/*     */     }
/* 445 */     if (this.externalMessageOrBuilderList != null) {
/* 446 */       this.externalMessageOrBuilderList.incrementModCount();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MessageExternalList<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder>
/*     */     extends AbstractList<MType>
/*     */     implements List<MType>, RandomAccess
/*     */   {
/*     */     RepeatedFieldBuilderV3<MType, BType, IType> builder;
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     MessageExternalList(RepeatedFieldBuilderV3<MType, BType, IType> builder) {
/* 460 */       this.builder = builder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public int size() {
/* 471 */       return this.builder.getCount();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public MType get(int index) {
/* 482 */       return this.builder.getMessage(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     void incrementModCount() {
/* 492 */       this.modCount++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BuilderExternalList<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder>
/*     */     extends AbstractList<BType>
/*     */     implements List<BType>, RandomAccess
/*     */   {
/*     */     RepeatedFieldBuilderV3<MType, BType, IType> builder;
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     BuilderExternalList(RepeatedFieldBuilderV3<MType, BType, IType> builder) {
/* 506 */       this.builder = builder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public int size() {
/* 517 */       return this.builder.getCount();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BType get(int index) {
/* 528 */       return this.builder.getBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     void incrementModCount() {
/* 538 */       this.modCount++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MessageOrBuilderExternalList<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder>
/*     */     extends AbstractList<IType>
/*     */     implements List<IType>, RandomAccess
/*     */   {
/*     */     RepeatedFieldBuilderV3<MType, BType, IType> builder;
/*     */ 
/*     */     
/*     */     MessageOrBuilderExternalList(RepeatedFieldBuilderV3<MType, BType, IType> builder) {
/* 551 */       this.builder = builder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public int size() {
/* 562 */       return this.builder.getCount();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IType get(int index) {
/* 573 */       return this.builder.getMessageOrBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     void incrementModCount() {
/* 583 */       this.modCount++;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\RepeatedFieldBuilderV3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */