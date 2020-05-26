// This file was generated from Pie source file definitionTestGen.
package mb.pie.lang.test.imports.subModule.a.b.c;

import java.io.Serializable;
import mb.pie.api.TaskDef;
import mb.pie.api.None;
import mb.pie.api.ExecException;
import mb.pie.api.ExecContext;
import javax.inject.Inject;

public class foreign_pie implements TaskDef<None, None> {
  private static final String _id = "foreign_pie";
  @Inject public foreign_pie( ) {
  }
  public String getId( ) {
    return foreign_pie._id;
  }
  @Override public Serializable key(None input) {
    return input;
  }
  @Override public None exec(ExecContext execContext, None input) throws Exception {
    return None.instance;
  }
}
// last cache update: 2020-01-09 13:52
