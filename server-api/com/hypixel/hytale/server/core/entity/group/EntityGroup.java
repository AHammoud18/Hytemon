/*     */ package com.hypixel.hytale.server.core.entity.group;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.function.consumer.IntBiObjectConsumer;
/*     */ import com.hypixel.hytale.function.consumer.IntTriObjectConsumer;
/*     */ import com.hypixel.hytale.function.consumer.QuadConsumer;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class EntityGroup
/*     */   implements Component<EntityStore> {
/*     */   public static ComponentType<EntityStore, EntityGroup> getComponentType() {
/*  23 */     return EntityModule.get().getEntityGroupComponentType();
/*     */   }
/*     */   
/*     */   @Nonnull
/*  27 */   private final Set<Ref<EntityStore>> memberSet = new HashSet<>();
/*     */   
/*     */   @Nonnull
/*  30 */   private final List<Ref<EntityStore>> memberList = (List<Ref<EntityStore>>)new ObjectArrayList();
/*     */   
/*     */   @Nullable
/*     */   private Ref<EntityStore> leaderRef;
/*     */   
/*     */   private boolean dissolved;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getLeaderRef() {
/*  40 */     return this.leaderRef;
/*     */   }
/*     */   
/*     */   public void setLeaderRef(@Nonnull Ref<EntityStore> leaderRef) {
/*  44 */     this.leaderRef = leaderRef;
/*     */   }
/*     */   
/*     */   public void add(@Nonnull Ref<EntityStore> reference) {
/*  48 */     if (!this.memberSet.add(reference)) throw new IllegalStateException("Attempting to add entity " + String.valueOf(reference) + " that is already a member of the group!"); 
/*  49 */     this.memberList.add(reference);
/*     */   }
/*     */   
/*     */   public void remove(@Nonnull Ref<EntityStore> reference) {
/*  53 */     if (!this.memberSet.remove(reference)) throw new IllegalStateException("Attempting to remove entity " + String.valueOf(reference) + " that is not a member of the group!"); 
/*  54 */     this.memberList.remove(reference);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getFirst() {
/*  59 */     return !this.memberList.isEmpty() ? this.memberList.getFirst() : null;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<Ref<EntityStore>> getMemberList() {
/*  64 */     return this.memberList;
/*     */   }
/*     */   
/*     */   public int size() {
/*  68 */     return this.memberSet.size();
/*     */   }
/*     */   
/*     */   public boolean isDissolved() {
/*  72 */     return this.dissolved;
/*     */   }
/*     */   
/*     */   public void setDissolved(boolean dissolved) {
/*  76 */     this.dissolved = dissolved;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  80 */     this.memberSet.clear();
/*  81 */     this.memberList.clear();
/*  82 */     this.leaderRef = null;
/*  83 */     this.dissolved = true;
/*     */   }
/*     */   
/*     */   public boolean isMember(Ref<EntityStore> reference) {
/*  87 */     return (!this.dissolved && this.memberSet.contains(reference));
/*     */   }
/*     */   
/*     */   public <T> void forEachMemberExcludingLeader(@Nonnull TriConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, Ref<EntityStore> sender, T arg) {
/*  91 */     forEachMember(consumer, sender, arg, this.leaderRef);
/*     */   }
/*     */   
/*     */   public <T> void forEachMemberExcludingSelf(@Nonnull TriConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, Ref<EntityStore> sender, T arg) {
/*  95 */     forEachMember(consumer, sender, arg, sender);
/*     */   }
/*     */   
/*     */   public <T> void forEachMember(@Nonnull TriConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, Ref<EntityStore> sender, T arg) {
/*  99 */     forEachMember(consumer, sender, arg, (Ref<EntityStore>)null);
/*     */   }
/*     */   
/*     */   public <T> void forEachMember(@Nonnull TriConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, Ref<EntityStore> sender, T arg, Ref<EntityStore> excludeReference) {
/* 103 */     for (int i = 0; i < this.memberList.size(); i++) {
/* 104 */       Ref<EntityStore> member = this.memberList.get(i);
/* 105 */       if (member.isValid() && !member.equals(excludeReference))
/*     */       {
/* 107 */         consumer.accept(member, sender, arg); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T, V> void forEachMemberExcludingLeader(@Nonnull QuadConsumer<Ref<EntityStore>, Ref<EntityStore>, T, V> consumer, Ref<EntityStore> sender, T t, V v) {
/* 112 */     forEachMember(consumer, sender, t, v, this.leaderRef);
/*     */   }
/*     */   
/*     */   public <T, V> void forEachMemberExcludingSelf(@Nonnull QuadConsumer<Ref<EntityStore>, Ref<EntityStore>, T, V> consumer, Ref<EntityStore> sender, T t, V v) {
/* 116 */     forEachMember(consumer, sender, t, v, sender);
/*     */   }
/*     */   
/*     */   public <T, V> void forEachMember(@Nonnull QuadConsumer<Ref<EntityStore>, Ref<EntityStore>, T, V> consumer, Ref<EntityStore> sender, T t, V v) {
/* 120 */     forEachMember(consumer, sender, t, v, (Ref<EntityStore>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T, V> void forEachMember(@Nonnull QuadConsumer<Ref<EntityStore>, Ref<EntityStore>, T, V> consumer, Ref<EntityStore> sender, T t, V v, Ref<EntityStore> excludeReference) {
/* 125 */     for (int i = 0; i < this.memberList.size(); i++) {
/* 126 */       Ref<EntityStore> member = this.memberList.get(i);
/* 127 */       if (member.isValid() && !member.equals(excludeReference))
/*     */       {
/* 129 */         consumer.accept(member, sender, t, v); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T, V> void forEachMemberExcludingLeader(@Nonnull IntTriObjectConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, Ref<EntityStore> sender, T t, int value) {
/* 134 */     forEachMember(consumer, sender, t, value, this.leaderRef);
/*     */   }
/*     */   
/*     */   public <T, V> void forEachMemberExcludingSelf(@Nonnull IntTriObjectConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, @Nonnull Ref<EntityStore> sender, T t, int value) {
/* 138 */     forEachMember(consumer, sender, t, value, sender);
/*     */   }
/*     */   
/*     */   public <T> void forEachMember(@Nonnull IntTriObjectConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, Ref<EntityStore> sender, T t, int value) {
/* 142 */     forEachMember(consumer, sender, t, value, (Ref<EntityStore>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void forEachMember(@Nonnull IntTriObjectConsumer<Ref<EntityStore>, Ref<EntityStore>, T> consumer, Ref<EntityStore> sender, T t, int value, Ref<EntityStore> excludeReference) {
/* 147 */     for (int i = 0; i < this.memberList.size(); i++) {
/* 148 */       Ref<EntityStore> member = this.memberList.get(i);
/* 149 */       if (member.isValid() && !member.equals(excludeReference))
/*     */       {
/* 151 */         consumer.accept(value, member, sender, t); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> void forEachMember(@Nonnull IntBiObjectConsumer<Ref<EntityStore>, T> consumer, T t) {
/* 156 */     for (int i = 0; i < this.memberList.size(); i++) {
/* 157 */       Ref<EntityStore> member = this.memberList.get(i);
/* 158 */       if (member.isValid()) {
/* 159 */         consumer.accept(i, member, t);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> testMembers(@Nonnull Predicate<Ref<EntityStore>> predicate, boolean skipLeader) {
/* 166 */     for (int i = 0; i < this.memberList.size(); ) {
/* 167 */       Ref<EntityStore> member = this.memberList.get(i);
/* 168 */       if (!member.isValid() || (skipLeader && member.equals(this.leaderRef)) || !predicate.test(member)) {
/*     */         i++; continue;
/* 170 */       }  return member;
/*     */     } 
/* 172 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 179 */     EntityGroup group = new EntityGroup();
/* 180 */     group.memberSet.addAll(this.memberSet);
/* 181 */     group.memberList.addAll(this.memberList);
/* 182 */     group.leaderRef = this.leaderRef;
/* 183 */     group.dissolved = this.dissolved;
/* 184 */     return group;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 190 */     return "EntityGroup{leader=" + String.valueOf(this.leaderRef) + ", memberSet=" + String.valueOf(this.memberSet) + ", memberList=" + String.valueOf(this.memberList) + ", dissolved=" + this.dissolved + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\group\EntityGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */