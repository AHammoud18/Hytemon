package org.bson;

import org.bson.types.Decimal128;

public abstract class BsonNumber extends BsonValue {
  public abstract int intValue();
  
  public abstract long longValue();
  
  public abstract double doubleValue();
  
  public abstract Decimal128 decimal128Value();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */