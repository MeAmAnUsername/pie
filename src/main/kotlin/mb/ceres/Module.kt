package mb.ceres

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import mb.ceres.internal.BuildManagerImpl
import mb.ceres.internal.BuilderStoreImpl

open class Module : AbstractModule() {
  override fun configure() {
    bindBuildManager()
  }


  open protected fun bindBuildManager() {
    install(FactoryModuleBuilder()
      .implement(BuildManager::class.java, BuildManagerImpl::class.java)
      .build(BuildManagerFactory::class.java))
    bind(BuilderStore::class.java).to(BuilderStoreImpl::class.java)
  }
}
