package org.bouncycastle.cms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.Iterable;

public class RecipientInformationStore implements Iterable<RecipientInformation> {
  private final List all;
  
  private final Map table = new HashMap<>();
  
  public RecipientInformationStore(RecipientInformation paramRecipientInformation) {
    this.all = new ArrayList(1);
    this.all.add(paramRecipientInformation);
    RecipientId recipientId = paramRecipientInformation.getRID();
    this.table.put(recipientId, this.all);
  }
  
  public RecipientInformationStore(Collection<RecipientInformation> paramCollection) {
    for (RecipientInformation recipientInformation : paramCollection) {
      RecipientId recipientId = recipientInformation.getRID();
      ArrayList<RecipientInformation> arrayList = (ArrayList)this.table.get(recipientId);
      if (arrayList == null) {
        arrayList = new ArrayList(1);
        this.table.put(recipientId, arrayList);
      } 
      arrayList.add(recipientInformation);
    } 
    this.all = new ArrayList<>(paramCollection);
  }
  
  public RecipientInformation get(RecipientId paramRecipientId) {
    Collection<RecipientInformation> collection = getRecipients(paramRecipientId);
    return (collection.size() == 0) ? null : collection.iterator().next();
  }
  
  public int size() {
    return this.all.size();
  }
  
  public Collection<RecipientInformation> getRecipients() {
    return new ArrayList<>(this.all);
  }
  
  public Collection<RecipientInformation> getRecipients(RecipientId paramRecipientId) {
    if (paramRecipientId instanceof PKIXRecipientId) {
      PKIXRecipientId pKIXRecipientId = (PKIXRecipientId)paramRecipientId;
      X500Name x500Name = pKIXRecipientId.getIssuer();
      byte[] arrayOfByte = pKIXRecipientId.getSubjectKeyIdentifier();
      if (x500Name != null && arrayOfByte != null) {
        ArrayList<RecipientInformation> arrayList1 = new ArrayList();
        ArrayList arrayList2 = (ArrayList)this.table.get(new PKIXRecipientId(pKIXRecipientId.getType(), x500Name, pKIXRecipientId.getSerialNumber(), null));
        if (arrayList2 != null)
          arrayList1.addAll(arrayList2); 
        ArrayList arrayList3 = (ArrayList)this.table.get(new PKIXRecipientId(pKIXRecipientId.getType(), null, null, arrayOfByte));
        if (arrayList3 != null)
          arrayList1.addAll(arrayList3); 
        return arrayList1;
      } 
    } 
    ArrayList<? extends RecipientInformation> arrayList = (ArrayList)this.table.get(paramRecipientId);
    return (arrayList == null) ? new ArrayList<>() : new ArrayList<>(arrayList);
  }
  
  public Iterator<RecipientInformation> iterator() {
    return getRecipients().iterator();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\RecipientInformationStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */