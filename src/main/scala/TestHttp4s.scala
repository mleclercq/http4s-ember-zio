import com.comcast.ip4s.*
import org.http4s.*
import org.http4s.ember.server.EmberServerBuilder
import zio.*
import zio.interop.catz.*

object TestHttp4s extends ZIOAppDefault:

  val server = EmberServerBuilder
    .default[Task]
    .withPort(port"8080")
    .withHttpApp(HttpRoutes.of[Task] { case _ => ZIO.succeed(Response(Status.Ok)) }.orNotFound)
    .withShutdownTimeout(scala.concurrent.duration.FiniteDuration(0, "s"))
    .build

  def run =
    for
      _ <- Scope.addFinalizer(ZIO.logInfo("Finalizer 1"))
      _ <- server.toScopedZIO
      _ <- Scope.addFinalizer(ZIO.logInfo("Finalizer 2"))
      _ <- ZIO.logInfo("Server listening on port 8080")
      _ <- ZIO.never
    yield ()
