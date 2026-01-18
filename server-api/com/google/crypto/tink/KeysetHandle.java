/*      */ package com.google.crypto.tink;
/*      */ 
/*      */ import com.google.crypto.tink.annotations.Alpha;
/*      */ import com.google.crypto.tink.config.GlobalTinkFlags;
/*      */ import com.google.crypto.tink.internal.InternalConfiguration;
/*      */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*      */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*      */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*      */ import com.google.crypto.tink.internal.MonitoringClient;
/*      */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*      */ import com.google.crypto.tink.internal.MutableMonitoringRegistry;
/*      */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*      */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*      */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*      */ import com.google.crypto.tink.internal.TinkBugException;
/*      */ import com.google.crypto.tink.internal.Util;
/*      */ import com.google.crypto.tink.proto.EncryptedKeyset;
/*      */ import com.google.crypto.tink.proto.KeyData;
/*      */ import com.google.crypto.tink.proto.KeyStatusType;
/*      */ import com.google.crypto.tink.proto.KeyTemplate;
/*      */ import com.google.crypto.tink.proto.Keyset;
/*      */ import com.google.crypto.tink.proto.KeysetInfo;
/*      */ import com.google.crypto.tink.proto.OutputPrefixType;
/*      */ import com.google.crypto.tink.tinkkey.KeyAccess;
/*      */ import com.google.crypto.tink.tinkkey.KeyHandle;
/*      */ import com.google.crypto.tink.tinkkey.TinkKey;
/*      */ import com.google.crypto.tink.tinkkey.internal.InternalKeyHandle;
/*      */ import com.google.crypto.tink.tinkkey.internal.ProtoKey;
/*      */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*      */ import com.google.errorprone.annotations.Immutable;
/*      */ import com.google.errorprone.annotations.InlineMe;
/*      */ import com.google.protobuf.ByteString;
/*      */ import com.google.protobuf.ExtensionRegistryLite;
/*      */ import com.google.protobuf.InvalidProtocolBufferException;
/*      */ import java.io.IOException;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class KeysetHandle
/*      */   implements KeysetHandleInterface
/*      */ {
/*      */   private final List<Entry> entries;
/*      */   private final MonitoringAnnotations annotations;
/*      */   @Nullable
/*      */   private final KeysetHandle unmonitoredHandle;
/*      */   
/*      */   public static final class Builder
/*      */   {
/*      */     private static class KeyIdStrategy
/*      */     {
/*   99 */       private static final KeyIdStrategy RANDOM_ID = new KeyIdStrategy();
/*      */       private final int fixedId;
/*      */       
/*      */       private KeyIdStrategy() {
/*  103 */         this.fixedId = 0;
/*      */       }
/*      */       
/*      */       private KeyIdStrategy(int id) {
/*  107 */         this.fixedId = id;
/*      */       }
/*      */       
/*      */       private static KeyIdStrategy randomId() {
/*  111 */         return RANDOM_ID;
/*      */       }
/*      */       
/*      */       private static KeyIdStrategy fixedId(int id) {
/*  115 */         return new KeyIdStrategy(id);
/*      */       }
/*      */       
/*      */       private int getFixedId() {
/*  119 */         return this.fixedId;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final class Entry
/*      */     {
/*      */       private boolean isPrimary;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  136 */       private KeyStatus keyStatus = KeyStatus.ENABLED;
/*      */       
/*      */       @Nullable
/*      */       private final Key key;
/*      */       @Nullable
/*      */       private final Parameters parameters;
/*  142 */       private KeysetHandle.Builder.KeyIdStrategy strategy = null;
/*      */ 
/*      */       
/*      */       @Nullable
/*  146 */       private KeysetHandle.Builder builder = null;
/*      */       
/*      */       private Entry(Key key) {
/*  149 */         this.key = key;
/*  150 */         this.parameters = null;
/*      */       }
/*      */       
/*      */       private Entry(Parameters parameters) {
/*  154 */         this.key = null;
/*  155 */         this.parameters = parameters;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       @CanIgnoreReturnValue
/*      */       public Entry makePrimary() {
/*  167 */         if (this.builder != null) {
/*  168 */           this.builder.clearPrimary();
/*      */         }
/*  170 */         this.isPrimary = true;
/*  171 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isPrimary() {
/*  176 */         return this.isPrimary;
/*      */       }
/*      */ 
/*      */       
/*      */       @CanIgnoreReturnValue
/*      */       public Entry setStatus(KeyStatus status) {
/*  182 */         this.keyStatus = status;
/*  183 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public KeyStatus getStatus() {
/*  188 */         return this.keyStatus;
/*      */       }
/*      */ 
/*      */       
/*      */       @CanIgnoreReturnValue
/*      */       public Entry withFixedId(int id) {
/*  194 */         this.strategy = KeysetHandle.Builder.KeyIdStrategy.fixedId(id);
/*  195 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       @CanIgnoreReturnValue
/*      */       public Entry withRandomId() {
/*  209 */         this.strategy = KeysetHandle.Builder.KeyIdStrategy.randomId();
/*  210 */         return this;
/*      */       }
/*      */     }
/*      */     
/*  214 */     private final List<Entry> entries = new ArrayList<>();
/*      */     @Nullable
/*  216 */     private GeneralSecurityException errorToThrow = null;
/*  217 */     private MonitoringAnnotations annotations = MonitoringAnnotations.EMPTY;
/*      */     private boolean buildCalled = false;
/*      */     
/*      */     private void clearPrimary() {
/*  221 */       for (Entry entry : this.entries) {
/*  222 */         entry.isPrimary = false;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     public Builder addEntry(Entry entry) {
/*  229 */       if (entry.builder != null) {
/*  230 */         throw new IllegalStateException("Entry has already been added to a KeysetHandle.Builder");
/*      */       }
/*  232 */       if (entry.isPrimary) {
/*  233 */         clearPrimary();
/*      */       }
/*  235 */       entry.builder = this;
/*  236 */       this.entries.add(entry);
/*  237 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     @Alpha
/*      */     public Builder setMonitoringAnnotations(MonitoringAnnotations annotations) {
/*  251 */       this.annotations = annotations;
/*  252 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  257 */       return this.entries.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry getAt(int i) {
/*  266 */       return this.entries.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     @CanIgnoreReturnValue
/*      */     public Entry removeAt(int i) {
/*  278 */       return this.entries.remove(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     public Builder deleteAt(int i) {
/*  287 */       this.entries.remove(i);
/*  288 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void checkIdAssignments(List<Entry> entries) throws GeneralSecurityException {
/*  297 */       for (int i = 0; i < entries.size() - 1; i++) {
/*  298 */         if ((entries.get(i)).strategy == KeyIdStrategy.RANDOM_ID && 
/*  299 */           (entries.get(i + 1)).strategy != KeyIdStrategy.RANDOM_ID) {
/*  300 */           throw new GeneralSecurityException("Entries with 'withRandomId()' may only be followed by other entries with 'withRandomId()'.");
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void setErrorToThrow(GeneralSecurityException errorToThrow) {
/*  308 */       this.errorToThrow = errorToThrow;
/*      */     }
/*      */     
/*      */     private static int randomIdNotInSet(Set<Integer> ids) {
/*  312 */       int id = 0;
/*  313 */       while (id == 0 || ids.contains(Integer.valueOf(id))) {
/*  314 */         id = Util.randKeyId();
/*      */       }
/*  316 */       return id;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static int getNextIdFromBuilderEntry(Entry builderEntry, Set<Integer> idsSoFar) throws GeneralSecurityException {
/*  322 */       int id = 0;
/*  323 */       if (builderEntry.strategy == null) {
/*  324 */         throw new GeneralSecurityException("No ID was set (with withFixedId or withRandomId)");
/*      */       }
/*  326 */       if (builderEntry.strategy == KeyIdStrategy.RANDOM_ID) {
/*  327 */         id = randomIdNotInSet(idsSoFar);
/*      */       } else {
/*  329 */         id = builderEntry.strategy.getFixedId();
/*      */       } 
/*  331 */       return id;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeysetHandle build() throws GeneralSecurityException {
/*  353 */       if (this.errorToThrow != null) {
/*  354 */         throw new GeneralSecurityException("Cannot build keyset due to error in original", this.errorToThrow);
/*      */       }
/*      */       
/*  357 */       if (this.buildCalled) {
/*  358 */         throw new GeneralSecurityException("KeysetHandle.Builder#build must only be called once");
/*      */       }
/*  360 */       this.buildCalled = true;
/*  361 */       List<KeysetHandle.Entry> handleEntries = new ArrayList<>(this.entries.size());
/*  362 */       Integer primaryId = null;
/*      */       
/*  364 */       checkIdAssignments(this.entries);
/*  365 */       Set<Integer> idsSoFar = new HashSet<>();
/*  366 */       for (Entry builderEntry : this.entries) {
/*  367 */         KeysetHandle.Entry handleEntry; if (builderEntry.keyStatus == null) {
/*  368 */           throw new GeneralSecurityException("Key Status not set.");
/*      */         }
/*  370 */         int id = getNextIdFromBuilderEntry(builderEntry, idsSoFar);
/*  371 */         if (idsSoFar.contains(Integer.valueOf(id))) {
/*  372 */           throw new GeneralSecurityException("Id " + id + " is used twice in the keyset");
/*      */         }
/*  374 */         idsSoFar.add(Integer.valueOf(id));
/*      */ 
/*      */ 
/*      */         
/*  378 */         if (builderEntry.key != null) {
/*  379 */           KeysetHandle.validateKeyId(builderEntry.key, id);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  387 */           handleEntry = new KeysetHandle.Entry(builderEntry.key, KeysetHandle.serializeStatus(builderEntry.keyStatus), id, builderEntry.isPrimary, false, KeysetHandle.Entry.NO_LOGGING);
/*      */         } else {
/*  389 */           Integer idRequirement = builderEntry.parameters.hasIdRequirement() ? Integer.valueOf(id) : null;
/*      */ 
/*      */           
/*  392 */           Key key = MutableKeyCreationRegistry.globalInstance().createKey(builderEntry.parameters, idRequirement);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  400 */           handleEntry = new KeysetHandle.Entry(key, KeysetHandle.serializeStatus(builderEntry.keyStatus), id, builderEntry.isPrimary, false, KeysetHandle.Entry.NO_LOGGING);
/*      */         } 
/*  402 */         if (builderEntry.isPrimary) {
/*  403 */           if (primaryId != null) {
/*  404 */             throw new GeneralSecurityException("Two primaries were set");
/*      */           }
/*  406 */           primaryId = Integer.valueOf(id);
/*  407 */           if (builderEntry.keyStatus != KeyStatus.ENABLED) {
/*  408 */             throw new GeneralSecurityException("Primary key is not enabled");
/*      */           }
/*      */         } 
/*  411 */         handleEntries.add(handleEntry);
/*      */       } 
/*  413 */       if (primaryId == null) {
/*  414 */         throw new GeneralSecurityException("No primary was set");
/*      */       }
/*  416 */       KeysetHandle unmonitoredKeyset = new KeysetHandle(handleEntries, this.annotations);
/*  417 */       return KeysetHandle.addMonitoringIfNeeded(unmonitoredKeyset);
/*      */     } } public static final class Entry {
/*      */     private boolean isPrimary; private KeyStatus keyStatus = KeyStatus.ENABLED; @Nullable
/*      */     private final Key key; @Nullable
/*      */     private final Parameters parameters; private KeysetHandle.Builder.KeyIdStrategy strategy = null; @Nullable
/*      */     private KeysetHandle.Builder builder = null; private Entry(Key key) { this.key = key;
/*      */       this.parameters = null; } private Entry(Parameters parameters) { this.key = null;
/*      */       this.parameters = parameters; } @CanIgnoreReturnValue
/*      */     public Entry makePrimary() {
/*      */       if (this.builder != null)
/*      */         this.builder.clearPrimary(); 
/*      */       this.isPrimary = true;
/*      */       return this;
/*      */     } public boolean isPrimary() {
/*      */       return this.isPrimary;
/*      */     } @CanIgnoreReturnValue
/*      */     public Entry setStatus(KeyStatus status) {
/*      */       this.keyStatus = status;
/*      */       return this;
/*      */     } public KeyStatus getStatus() {
/*      */       return this.keyStatus;
/*      */     } @CanIgnoreReturnValue
/*      */     public Entry withFixedId(int id) {
/*      */       this.strategy = KeysetHandle.Builder.KeyIdStrategy.fixedId(id);
/*      */       return this;
/*      */     } @CanIgnoreReturnValue
/*      */     public Entry withRandomId() {
/*      */       this.strategy = KeysetHandle.Builder.KeyIdStrategy.randomId();
/*      */       return this;
/*      */     }
/*      */   } @Immutable
/*      */   public static final class Entry implements KeysetHandleInterface.Entry { private Entry(Key key, KeyStatusType keyStatusType, int id, boolean isPrimary, boolean keyParsingFailed, EntryConsumer keyExportLogger) {
/*  449 */       this.key = key;
/*  450 */       this.keyStatusType = keyStatusType;
/*  451 */       this.keyStatus = KeysetHandle.parseStatusWithDisabledFallback(keyStatusType);
/*  452 */       this.id = id;
/*  453 */       this.isPrimary = isPrimary;
/*  454 */       this.keyParsingFailed = keyParsingFailed;
/*  455 */       this.keyExportLogger = keyExportLogger;
/*      */     }
/*      */ 
/*      */     
/*      */     private static final EntryConsumer NO_LOGGING = e -> {
/*      */       
/*      */       };
/*      */     
/*      */     private final Key key;
/*      */     
/*      */     private final KeyStatusType keyStatusType;
/*      */     
/*      */     private final KeyStatus keyStatus;
/*      */     
/*      */     private final int id;
/*      */     
/*      */     private final boolean isPrimary;
/*      */     private final boolean keyParsingFailed;
/*      */     private final EntryConsumer keyExportLogger;
/*      */     
/*      */     public Key getKey() {
/*  476 */       this.keyExportLogger.accept(this);
/*  477 */       return this.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyStatus getStatus() {
/*  482 */       return this.keyStatus;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getId() {
/*  487 */       return this.id;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isPrimary() {
/*  499 */       return this.isPrimary;
/*      */     }
/*      */     
/*      */     private boolean equalsEntry(Entry other) {
/*  503 */       if (other.isPrimary != this.isPrimary) {
/*  504 */         return false;
/*      */       }
/*  506 */       if (!other.keyStatusType.equals(this.keyStatusType)) {
/*  507 */         return false;
/*      */       }
/*  509 */       if (other.id != this.id) {
/*  510 */         return false;
/*      */       }
/*  512 */       if (!other.key.equalsKey(this.key)) {
/*  513 */         return false;
/*      */       }
/*  515 */       return true;
/*      */     }
/*      */     @Immutable
/*      */     private static interface EntryConsumer { void accept(KeysetHandle.Entry param2Entry); } }
/*      */   private static KeyStatus parseStatusWithDisabledFallback(KeyStatusType in) {
/*  520 */     switch (in) {
/*      */       case ENABLED:
/*  522 */         return KeyStatus.ENABLED;
/*      */       case DESTROYED:
/*  524 */         return KeyStatus.DESTROYED;
/*      */     } 
/*      */     
/*  527 */     return KeyStatus.DISABLED;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isValidKeyStatusType(KeyStatusType in) {
/*  532 */     switch (in) {
/*      */       case ENABLED:
/*      */       case DESTROYED:
/*      */       case DISABLED:
/*  536 */         return true;
/*      */     } 
/*  538 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static KeyStatusType serializeStatus(KeyStatus in) {
/*  543 */     if (KeyStatus.ENABLED.equals(in)) {
/*  544 */       return KeyStatusType.ENABLED;
/*      */     }
/*  546 */     if (KeyStatus.DISABLED.equals(in)) {
/*  547 */       return KeyStatusType.DISABLED;
/*      */     }
/*  549 */     if (KeyStatus.DESTROYED.equals(in)) {
/*  550 */       return KeyStatusType.DESTROYED;
/*      */     }
/*  552 */     throw new IllegalStateException("Unknown key status");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<Entry> getEntriesFromKeyset(Keyset keyset) throws GeneralSecurityException {
/*  562 */     List<Entry> result = new ArrayList<>(keyset.getKeyCount());
/*  563 */     for (Keyset.Key protoKey : keyset.getKeyList()) {
/*  564 */       LegacyProtoKey legacyProtoKey; boolean keyParsingFailed; int id = protoKey.getKeyId();
/*      */ 
/*      */       
/*      */       try {
/*  568 */         Key key = toKey(protoKey);
/*  569 */         keyParsingFailed = false;
/*  570 */       } catch (GeneralSecurityException e) {
/*  571 */         if (GlobalTinkFlags.validateKeysetsOnParsing.getValue()) {
/*  572 */           throw e;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  577 */         legacyProtoKey = new LegacyProtoKey(toProtoKeySerialization(protoKey), InsecureSecretKeyAccess.get());
/*  578 */         keyParsingFailed = true;
/*      */       } 
/*      */       
/*  581 */       if (GlobalTinkFlags.validateKeysetsOnParsing.getValue() && 
/*  582 */         !isValidKeyStatusType(protoKey.getStatus())) {
/*  583 */         throw new GeneralSecurityException("Parsing of a single key failed (wrong status) and Tink is configured via validateKeysetsOnParsing to reject such keysets.");
/*      */       }
/*      */ 
/*      */       
/*  587 */       result.add(new Entry((Key)legacyProtoKey, protoKey
/*      */ 
/*      */             
/*  590 */             .getStatus(), id, 
/*      */             
/*  592 */             (id == keyset.getPrimaryKeyId()), keyParsingFailed, Entry
/*      */             
/*  594 */             .NO_LOGGING));
/*      */     } 
/*  596 */     return Collections.unmodifiableList(result);
/*      */   }
/*      */   
/*      */   private Entry entryByIndex(int i) {
/*  600 */     Entry entry = this.entries.get(i);
/*  601 */     if (!isValidKeyStatusType(entry.keyStatusType)) {
/*  602 */       throw new IllegalStateException("Keyset-Entry at position " + i + " has wrong status");
/*      */     }
/*  604 */     if (entry.keyParsingFailed) {
/*  605 */       throw new IllegalStateException("Keyset-Entry at position " + i + " didn't parse correctly");
/*      */     }
/*  607 */     return this.entries.get(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Builder.Entry importKey(Key key) {
/*  618 */     Builder.Entry importedEntry = new Builder.Entry(key);
/*  619 */     Integer requirement = key.getIdRequirementOrNull();
/*  620 */     if (requirement != null) {
/*  621 */       importedEntry.withFixedId(requirement.intValue());
/*      */     }
/*  623 */     return importedEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Builder.Entry generateEntryFromParametersName(String parametersName) throws GeneralSecurityException {
/*  635 */     Parameters parameters = MutableParametersRegistry.globalInstance().get(parametersName);
/*  636 */     return new Builder.Entry(parameters);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Builder.Entry generateEntryFromParameters(Parameters parameters) {
/*  644 */     return new Builder.Entry(parameters);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private KeysetHandle getUnmonitoredHandle() {
/*  658 */     return (this.unmonitoredHandle == null) ? this : this.unmonitoredHandle;
/*      */   }
/*      */   
/*      */   private static void validateNoDuplicateIds(List<Entry> entries) throws GeneralSecurityException {
/*  662 */     Set<Integer> idsSoFar = new HashSet<>();
/*  663 */     boolean foundPrimary = false;
/*  664 */     for (Entry e : entries) {
/*  665 */       if (idsSoFar.contains(Integer.valueOf(e.getId()))) {
/*  666 */         throw new GeneralSecurityException("KeyID " + e
/*      */             
/*  668 */             .getId() + " is duplicated in the keyset, and Tink is configured to reject such keysets with the flag validateKeysetsOnParsing.");
/*      */       }
/*      */ 
/*      */       
/*  672 */       idsSoFar.add(Integer.valueOf(e.getId()));
/*  673 */       if (e.isPrimary()) {
/*  674 */         foundPrimary = true;
/*      */       }
/*      */     } 
/*  677 */     if (!foundPrimary) {
/*  678 */       throw new GeneralSecurityException("Primary key id not found in keyset, and Tink is configured to reject such keysets with the flag validateKeysetsOnParsing.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private KeysetHandle(List<Entry> entries, MonitoringAnnotations annotations) throws GeneralSecurityException {
/*  686 */     this.entries = entries;
/*  687 */     this.annotations = annotations;
/*  688 */     if (GlobalTinkFlags.validateKeysetsOnParsing.getValue()) {
/*  689 */       validateNoDuplicateIds(entries);
/*      */     }
/*  691 */     this.unmonitoredHandle = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private KeysetHandle(List<Entry> entries, MonitoringAnnotations annotations, KeysetHandle unmonitoredHandle) {
/*  696 */     this.entries = entries;
/*  697 */     this.annotations = annotations;
/*  698 */     this.unmonitoredHandle = unmonitoredHandle;
/*      */   }
/*      */   
/*      */   private static KeysetHandle addMonitoringIfNeeded(KeysetHandle unmonitoredHandle) {
/*  702 */     MonitoringAnnotations annotations = unmonitoredHandle.annotations;
/*  703 */     if (annotations.isEmpty()) {
/*  704 */       return unmonitoredHandle;
/*      */     }
/*      */     
/*  707 */     Entry.EntryConsumer keyExportLogger = entryToLog -> {
/*      */         MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/*      */ 
/*      */ 
/*      */         
/*      */         client.createLogger(unmonitoredHandle, annotations, "keyset_handle", "get_key").logKeyExport(entryToLog.getId());
/*      */       };
/*      */ 
/*      */     
/*  716 */     List<Entry> monitoredEntries = new ArrayList<>(unmonitoredHandle.entries.size());
/*  717 */     for (Entry e : unmonitoredHandle.entries) {
/*  718 */       monitoredEntries.add(new Entry(e
/*      */             
/*  720 */             .key, e.keyStatusType, e.id, e.isPrimary, e.keyParsingFailed, keyExportLogger));
/*      */     }
/*  722 */     return new KeysetHandle(monitoredEntries, annotations, unmonitoredHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final KeysetHandle fromKeyset(Keyset keyset) throws GeneralSecurityException {
/*  730 */     assertEnoughKeyMaterial(keyset);
/*  731 */     List<Entry> entries = getEntriesFromKeyset(keyset);
/*      */     
/*  733 */     return new KeysetHandle(entries, MonitoringAnnotations.EMPTY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final KeysetHandle fromKeysetAndAnnotations(Keyset keyset, MonitoringAnnotations annotations) throws GeneralSecurityException {
/*  742 */     assertEnoughKeyMaterial(keyset);
/*  743 */     List<Entry> entries = getEntriesFromKeyset(keyset);
/*  744 */     return addMonitoringIfNeeded(new KeysetHandle(entries, annotations));
/*      */   }
/*      */ 
/*      */   
/*      */   Keyset getKeyset() {
/*      */     try {
/*  750 */       Keyset.Builder builder = Keyset.newBuilder();
/*  751 */       for (Entry entry : this.entries) {
/*  752 */         Keyset.Key protoKey = createKeysetKey(entry.getKey(), entry.keyStatusType, entry.getId());
/*  753 */         builder.addKey(protoKey);
/*  754 */         if (entry.isPrimary()) {
/*  755 */           builder.setPrimaryKeyId(entry.getId());
/*      */         }
/*      */       } 
/*  758 */       return builder.build();
/*  759 */     } catch (GeneralSecurityException e) {
/*  760 */       throw new TinkBugException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Builder newBuilder() {
/*  766 */     return new Builder();
/*      */   }
/*      */ 
/*      */   
/*      */   public static Builder newBuilder(KeysetHandle handle) {
/*  771 */     Builder builder = new Builder();
/*  772 */     for (int i = 0; i < handle.size(); i++) {
/*      */       Entry entry;
/*      */       try {
/*  775 */         entry = handle.getAt(i);
/*  776 */       } catch (IllegalStateException e) {
/*  777 */         builder.setErrorToThrow(new GeneralSecurityException("Keyset-Entry in original keyset at position " + i + " has wrong status or key parsing failed", e));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  787 */       Builder.Entry builderEntry = importKey(entry.getKey()).withFixedId(entry.getId());
/*  788 */       builderEntry.setStatus(entry.getStatus());
/*  789 */       if (entry.isPrimary()) {
/*  790 */         builderEntry.makePrimary();
/*      */       }
/*  792 */       builder.addEntry(builderEntry);
/*      */     } 
/*  794 */     return builder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entry getPrimary() {
/*  806 */     for (Entry entry : this.entries) {
/*  807 */       if (entry != null && entry.isPrimary()) {
/*  808 */         if (entry.getStatus() != KeyStatus.ENABLED) {
/*  809 */           throw new IllegalStateException("Keyset has primary which isn't enabled");
/*      */         }
/*  811 */         return entry;
/*      */       } 
/*      */     } 
/*  814 */     throw new IllegalStateException("Keyset has no valid primary");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  820 */     return this.entries.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entry getAt(int i) {
/*  840 */     if (i < 0 || i >= size()) {
/*  841 */       throw new IndexOutOfBoundsException("Invalid index " + i + " for keyset of size " + size());
/*      */     }
/*  843 */     return entryByIndex(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public List<KeyHandle> getKeys() {
/*  855 */     ArrayList<KeyHandle> result = new ArrayList<>();
/*  856 */     Keyset keyset = getKeyset();
/*  857 */     for (Keyset.Key key : keyset.getKeyList()) {
/*  858 */       KeyData keyData = key.getKeyData();
/*  859 */       result.add(new InternalKeyHandle((TinkKey)new ProtoKey(keyData, 
/*      */               
/*  861 */               KeyTemplate.fromProto(key.getOutputPrefixType())), key
/*  862 */             .getStatus(), key
/*  863 */             .getKeyId()));
/*      */     } 
/*  865 */     return Collections.unmodifiableList(result);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public KeysetInfo getKeysetInfo() {
/*  878 */     Keyset keyset = getKeyset();
/*  879 */     return Util.getKeysetInfo(keyset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final KeysetHandle generateNew(Parameters parameters) throws GeneralSecurityException {
/*  891 */     return newBuilder()
/*  892 */       .addEntry(generateEntryFromParameters(parameters).withRandomId().makePrimary())
/*  893 */       .build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final KeysetHandle generateNew(KeyTemplate keyTemplate) throws GeneralSecurityException {
/*  915 */     return generateNew(TinkProtoParametersFormat.parse(keyTemplate.toByteArray()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final KeysetHandle generateNew(KeyTemplate keyTemplate) throws GeneralSecurityException {
/*  929 */     return generateNew(keyTemplate.toParameters());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final KeysetHandle createFromKey(KeyHandle keyHandle, KeyAccess access) throws GeneralSecurityException {
/*  940 */     KeysetManager km = KeysetManager.withEmptyKeyset().add(keyHandle);
/*  941 */     km.setPrimary(km.getKeysetHandle().getKeysetInfo().getKeyInfo(0).getKeyId());
/*  942 */     return km.getKeysetHandle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final KeysetHandle read(KeysetReader reader, Aead masterKey) throws GeneralSecurityException, IOException {
/*  961 */     return readWithAssociatedData(reader, masterKey, new byte[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final KeysetHandle readWithAssociatedData(KeysetReader reader, Aead masterKey, byte[] associatedData) throws GeneralSecurityException, IOException {
/*  982 */     EncryptedKeyset encryptedKeyset = reader.readEncrypted();
/*  983 */     assertEnoughEncryptedKeyMaterial(encryptedKeyset);
/*  984 */     return fromKeyset(decrypt(encryptedKeyset, masterKey, associatedData));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final KeysetHandle readNoSecret(KeysetReader reader) throws GeneralSecurityException, IOException {
/*      */     byte[] serializedKeyset;
/*      */     try {
/* 1005 */       serializedKeyset = reader.read().toByteArray();
/* 1006 */     } catch (InvalidProtocolBufferException e) {
/*      */       
/* 1008 */       throw new GeneralSecurityException("invalid keyset");
/*      */     } 
/* 1010 */     return readNoSecret(serializedKeyset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final KeysetHandle readNoSecret(byte[] serialized) throws GeneralSecurityException {
/*      */     try {
/* 1031 */       Keyset keyset = Keyset.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry());
/* 1032 */       assertNoSecretKeyMaterial(keyset);
/* 1033 */       return fromKeyset(keyset);
/* 1034 */     } catch (InvalidProtocolBufferException e) {
/*      */       
/* 1036 */       throw new GeneralSecurityException("invalid keyset");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void write(KeysetWriter keysetWriter, Aead masterKey) throws GeneralSecurityException, IOException {
/* 1050 */     writeWithAssociatedData(keysetWriter, masterKey, new byte[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void writeWithAssociatedData(KeysetWriter keysetWriter, Aead masterKey, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 1065 */     Keyset keyset = getKeyset();
/* 1066 */     EncryptedKeyset encryptedKeyset = encrypt(keyset, masterKey, associatedData);
/* 1067 */     keysetWriter.write(encryptedKeyset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void writeNoSecret(KeysetWriter writer) throws GeneralSecurityException, IOException {
/* 1082 */     Keyset keyset = getKeyset();
/* 1083 */     assertNoSecretKeyMaterial(keyset);
/* 1084 */     writer.write(keyset);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static EncryptedKeyset encrypt(Keyset keyset, Aead masterKey, byte[] associatedData) throws GeneralSecurityException {
/* 1090 */     byte[] encryptedKeyset = masterKey.encrypt(keyset.toByteArray(), associatedData);
/* 1091 */     return EncryptedKeyset.newBuilder()
/* 1092 */       .setEncryptedKeyset(ByteString.copyFrom(encryptedKeyset))
/* 1093 */       .setKeysetInfo(Util.getKeysetInfo(keyset))
/* 1094 */       .build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Keyset decrypt(EncryptedKeyset encryptedKeyset, Aead masterKey, byte[] associatedData) throws GeneralSecurityException {
/*      */     try {
/* 1104 */       Keyset keyset = Keyset.parseFrom(masterKey
/* 1105 */           .decrypt(encryptedKeyset.getEncryptedKeyset().toByteArray(), associatedData), 
/* 1106 */           ExtensionRegistryLite.getEmptyRegistry());
/*      */       
/* 1108 */       assertEnoughKeyMaterial(keyset);
/* 1109 */       return keyset;
/* 1110 */     } catch (InvalidProtocolBufferException e) {
/*      */       
/* 1112 */       throw new GeneralSecurityException("invalid keyset, corrupted key material");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeysetHandle getPublicKeysetHandle() throws GeneralSecurityException {
/* 1123 */     Keyset keyset = getKeyset();
/* 1124 */     List<Entry> publicEntries = new ArrayList<>(this.entries.size());
/*      */     
/* 1126 */     int i = 0;
/* 1127 */     for (Entry entry : this.entries) {
/*      */       Entry publicEntry;
/*      */       
/* 1130 */       if (entry.getKey() instanceof PrivateKey) {
/* 1131 */         Key publicKey = ((PrivateKey)entry.getKey()).getPublicKey();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1139 */         publicEntry = new Entry(publicKey, entry.keyStatusType, entry.getId(), entry.isPrimary(), false, Entry.NO_LOGGING);
/* 1140 */         validateKeyId(publicKey, entry.getId());
/*      */       } else {
/*      */         LegacyProtoKey legacyProtoKey; boolean keyParsingFailed;
/* 1143 */         Keyset.Key protoKey = keyset.getKey(i);
/* 1144 */         KeyData keyData = getPublicKeyDataFromRegistry(protoKey.getKeyData());
/* 1145 */         Keyset.Key publicProtoKey = protoKey.toBuilder().setKeyData(keyData).build();
/*      */ 
/*      */         
/*      */         try {
/* 1149 */           Key publicKey = toKey(publicProtoKey);
/* 1150 */           keyParsingFailed = false;
/* 1151 */         } catch (GeneralSecurityException e) {
/* 1152 */           if (GlobalTinkFlags.validateKeysetsOnParsing.getValue()) {
/* 1153 */             throw e;
/*      */           }
/*      */ 
/*      */           
/* 1157 */           legacyProtoKey = new LegacyProtoKey(toProtoKeySerialization(publicProtoKey), InsecureSecretKeyAccess.get());
/* 1158 */           keyParsingFailed = true;
/*      */         } 
/*      */         
/* 1161 */         int id = publicProtoKey.getKeyId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1169 */         publicEntry = new Entry((Key)legacyProtoKey, entry.keyStatusType, id, (id == keyset.getPrimaryKeyId()), keyParsingFailed, Entry.NO_LOGGING);
/*      */       } 
/*      */       
/* 1172 */       publicEntries.add(publicEntry);
/* 1173 */       i++;
/*      */     } 
/* 1175 */     return addMonitoringIfNeeded(new KeysetHandle(publicEntries, this.annotations));
/*      */   }
/*      */ 
/*      */   
/*      */   private static KeyData getPublicKeyDataFromRegistry(KeyData privateKeyData) throws GeneralSecurityException {
/* 1180 */     if (privateKeyData.getKeyMaterialType() != KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE) {
/* 1181 */       throw new GeneralSecurityException("The keyset contains a non-private key");
/*      */     }
/*      */     
/* 1184 */     KeyData publicKeyData = Registry.getPublicKeyData(privateKeyData.getTypeUrl(), privateKeyData.getValue());
/* 1185 */     return publicKeyData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1195 */     return getKeysetInfo().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void assertNoSecretKeyMaterial(Keyset keyset) throws GeneralSecurityException {
/* 1204 */     for (Keyset.Key key : keyset.getKeyList()) {
/* 1205 */       if (key.getKeyData().getKeyMaterialType() == KeyData.KeyMaterialType.UNKNOWN_KEYMATERIAL || key
/* 1206 */         .getKeyData().getKeyMaterialType() == KeyData.KeyMaterialType.SYMMETRIC || key
/* 1207 */         .getKeyData().getKeyMaterialType() == KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE) {
/* 1208 */         throw new GeneralSecurityException(
/* 1209 */             String.format("keyset contains key material of type %s for type url %s", new Object[] {
/*      */                 
/* 1211 */                 key.getKeyData().getKeyMaterialType().name(), key.getKeyData().getTypeUrl()
/*      */               }));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void assertEnoughKeyMaterial(Keyset keyset) throws GeneralSecurityException {
/* 1222 */     if (keyset == null || keyset.getKeyCount() <= 0) {
/* 1223 */       throw new GeneralSecurityException("empty keyset");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void assertEnoughEncryptedKeyMaterial(EncryptedKeyset keyset) throws GeneralSecurityException {
/* 1234 */     if (keyset == null || keyset.getEncryptedKeyset().size() == 0) {
/* 1235 */       throw new GeneralSecurityException("empty keyset");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private <P> P getPrimitiveInternal(InternalConfiguration config, Class<P> classObject) throws GeneralSecurityException {
/* 1241 */     Keyset keyset = getUnmonitoredHandle().getKeyset();
/* 1242 */     Util.validateKeyset(keyset);
/* 1243 */     for (int i = 0; i < size(); i++) {
/* 1244 */       if ((this.entries.get(i)).keyParsingFailed || !isValidKeyStatusType((this.entries.get(i)).keyStatusType)) {
/* 1245 */         Keyset.Key protoKey = keyset.getKey(i);
/* 1246 */         throw new GeneralSecurityException("Key parsing of key with index " + i + " and type_url " + protoKey
/*      */ 
/*      */ 
/*      */             
/* 1250 */             .getKeyData().getTypeUrl() + " failed, unable to get primitive");
/*      */       } 
/*      */     } 
/*      */     
/* 1254 */     return (P)config.wrap(getUnmonitoredHandle(), this.annotations, classObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <P> P getPrimitive(Configuration configuration, Class<P> targetClassObject) throws GeneralSecurityException {
/* 1263 */     if (!(configuration instanceof InternalConfiguration)) {
/* 1264 */       throw new GeneralSecurityException("Currently only subclasses of InternalConfiguration are accepted");
/*      */     }
/*      */     
/* 1267 */     InternalConfiguration internalConfig = (InternalConfiguration)configuration;
/* 1268 */     return getPrimitiveInternal(internalConfig, targetClassObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "this.getPrimitive(RegistryConfiguration.get(), targetClassObject)", imports = {"com.google.crypto.tink.RegistryConfiguration"})
/*      */   public <P> P getPrimitive(Class<P> targetClassObject) throws GeneralSecurityException {
/* 1285 */     return getPrimitive(RegistryConfiguration.get(), targetClassObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public KeyHandle primaryKey() throws GeneralSecurityException {
/* 1298 */     Keyset keyset = getKeyset();
/* 1299 */     int primaryKeyId = keyset.getPrimaryKeyId();
/* 1300 */     for (Keyset.Key key : keyset.getKeyList()) {
/* 1301 */       if (key.getKeyId() == primaryKeyId) {
/* 1302 */         return (KeyHandle)new InternalKeyHandle((TinkKey)new ProtoKey(key
/* 1303 */               .getKeyData(), KeyTemplate.fromProto(key.getOutputPrefixType())), key
/* 1304 */             .getStatus(), key
/* 1305 */             .getKeyId());
/*      */       }
/*      */     } 
/* 1308 */     throw new GeneralSecurityException("No primary key found in keyset.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equalsKeyset(KeysetHandle other) {
/* 1320 */     if (size() != other.size()) {
/* 1321 */       return false;
/*      */     }
/* 1323 */     boolean primaryFound = false;
/* 1324 */     for (int i = 0; i < size(); i++) {
/* 1325 */       Entry thisEntry = this.entries.get(i);
/* 1326 */       Entry otherEntry = other.entries.get(i);
/* 1327 */       if (thisEntry.keyParsingFailed) {
/* 1328 */         return false;
/*      */       }
/* 1330 */       if (otherEntry.keyParsingFailed) {
/* 1331 */         return false;
/*      */       }
/* 1333 */       if (!isValidKeyStatusType(thisEntry.keyStatusType)) {
/* 1334 */         return false;
/*      */       }
/* 1336 */       if (!isValidKeyStatusType(otherEntry.keyStatusType)) {
/* 1337 */         return false;
/*      */       }
/* 1339 */       if (!thisEntry.equalsEntry(otherEntry)) {
/* 1340 */         return false;
/*      */       }
/* 1342 */       primaryFound |= thisEntry.isPrimary;
/*      */     } 
/* 1344 */     if (!primaryFound) {
/* 1345 */       return false;
/*      */     }
/* 1347 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static ProtoKeySerialization toProtoKeySerialization(Keyset.Key protoKey) throws GeneralSecurityException {
/* 1352 */     int id = protoKey.getKeyId();
/*      */     
/* 1354 */     Integer idRequirement = (protoKey.getOutputPrefixType() == OutputPrefixType.RAW) ? null : Integer.valueOf(id);
/* 1355 */     return ProtoKeySerialization.create(protoKey
/* 1356 */         .getKeyData().getTypeUrl(), protoKey
/* 1357 */         .getKeyData().getValue(), protoKey
/* 1358 */         .getKeyData().getKeyMaterialType(), protoKey
/* 1359 */         .getOutputPrefixType(), idRequirement);
/*      */   }
/*      */ 
/*      */   
/*      */   private static Key toKey(Keyset.Key protoKey) throws GeneralSecurityException {
/* 1364 */     ProtoKeySerialization protoKeySerialization = toProtoKeySerialization(protoKey);
/* 1365 */     return MutableSerializationRegistry.globalInstance()
/* 1366 */       .parseKeyWithLegacyFallback(protoKeySerialization, InsecureSecretKeyAccess.get());
/*      */   }
/*      */ 
/*      */   
/*      */   private static Keyset.Key toKeysetKey(int id, KeyStatusType status, ProtoKeySerialization protoKeySerialization) {
/* 1371 */     return Keyset.Key.newBuilder()
/* 1372 */       .setKeyData(
/* 1373 */         KeyData.newBuilder()
/* 1374 */         .setTypeUrl(protoKeySerialization.getTypeUrl())
/* 1375 */         .setValue(protoKeySerialization.getValue())
/* 1376 */         .setKeyMaterialType(protoKeySerialization.getKeyMaterialType()))
/* 1377 */       .setStatus(status)
/* 1378 */       .setKeyId(id)
/* 1379 */       .setOutputPrefixType(protoKeySerialization.getOutputPrefixType())
/* 1380 */       .build();
/*      */   }
/*      */   
/*      */   private static void validateKeyId(Key key, int id) throws GeneralSecurityException {
/* 1384 */     Integer idRequirement = key.getIdRequirementOrNull();
/* 1385 */     if (idRequirement != null && idRequirement.intValue() != id) {
/* 1386 */       throw new GeneralSecurityException("Wrong ID set for key with ID requirement");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Keyset.Key createKeysetKey(Key key, KeyStatusType keyStatus, int id) throws GeneralSecurityException {
/* 1394 */     ProtoKeySerialization serializedKey = (ProtoKeySerialization)MutableSerializationRegistry.globalInstance().serializeKey(key, ProtoKeySerialization.class, InsecureSecretKeyAccess.get());
/* 1395 */     validateKeyId(key, id);
/* 1396 */     return toKeysetKey(id, keyStatus, serializedKey);
/*      */   }
/*      */   
/*      */   @Immutable
/*      */   private static interface EntryConsumer {
/*      */     void accept(KeysetHandle.Entry param1Entry);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KeysetHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */