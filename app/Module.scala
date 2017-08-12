import com.google.inject.AbstractModule
import repo.AirportRepo
import repo.impl.AirportRepoImpl

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[AirportRepo]).to(classOf[AirportRepoImpl])
  }
}
