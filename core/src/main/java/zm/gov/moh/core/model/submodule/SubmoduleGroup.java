package zm.gov.moh.core.model.submodule;

import java.io.Serializable;
import java.util.List;

import zm.gov.moh.core.model.submodule.Submodule;


public interface SubmoduleGroup extends Submodule{

   List<Submodule> getSubmodules();
   Submodule getSubmodule(String name);
}
