package zm.gov.moh.core.model.submodule;

import java.util.List;


public interface ModuleGroup extends Module {

   List<Module> getModules();
   Module getSubmodule(String name);
}
