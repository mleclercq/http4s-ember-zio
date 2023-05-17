## Example of http4s-ember-server with zio showing the graceful shutdown of the server hanging

Start the server: 

```
> sbt run
...
[info] running (fork) TestHttp4s 
[error] SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
[error] SLF4J: Defaulting to no-operation (NOP) logger implementation
[error] SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
[info] timestamp=2023-05-17T14:00:13.168916Z level=INFO thread=#zio-fiber-4 message="Server listening on port 8080" location=<empty>.TestHttp4s.run file=TestHttp4s.scala line=21
```

In an other terminal, send a TERM signal:

```
> jps -l
5106 TestHttp4s
...
> kill 5106
```

In the first terminal we see the following log:

```
[info] timestamp=2023-05-17T14:01:59.702141Z level=INFO thread=#zio-fiber-4 message="Finalizer 2" location=<empty>.TestHttp4s.run file=TestHttp4s.scala line=20
```

But the process doesn't exit

Get a fiber dump:

```
> kill -s INFO 5106
```

We get the following dump:

```
[error] "zio-fiber-2" (3m 197s 313ms) 
[error]         Status: Done
[error] "zio-fiber-18" (3m 196s 874ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-13" (3m 196s 900ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-17" (3m 196s 876ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-25" (3m 196s 878ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-40" (3m 196s 859ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.racePair(cats.scala:312)
[error] "zio-fiber-42" (1m 90s 335ms) waiting on #3
[error]         Status: Suspended((Interruption, CooperativeYielding, FiberRoots), <no trace>)
[error] "zio-fiber-16" (3m 196s 888ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-5" (3m 197s 109ms) waiting on #5
[error]         Status: Suspended((CooperativeYielding, FiberRoots), zio.interop.ZioDeferred.get(cats.scala:459))
[error]         at zio.interop.ZioDeferred.get(cats.scala:459)
[error]         at .map$$anonfun$1(Monad.scala:35:0)
[error]         at .attempt$$anonfun$2(ApplicativeError.scala:138:0)
[error]         at .map$$anonfun$1(Monad.scala:35:0)
[error]         at .goEval$1$$anonfun$1(Pull.scala:1090:0)
[error]         at zio.interop.ZioMonadErrorE.attempt.trace(cats.scala:741)
[error]         at .3$$anonfun(Pull.scala:1248:0)
[error]         at .loop$1$$anonfun$1(Resource.scala:174:0)
[error]         at .$greater$greater$extension$$anonfun$1(flatMap.scala:54:0)
[error]         at .map$$anonfun$1(Monad.scala:35:0)
[error]         at .attempt$$anonfun$2(ApplicativeError.scala:138:0)
[error]         at .map$$anonfun$1(Monad.scala:35:0)
[error]         at .2$$anonfun(Scope.scala:267:0)
[error]         at .map$$anonfun$1(Monad.scala:35:0)
[error]         at .2$$anonfun(Scope.scala:266:0)
[error]         at .rethrow$extension$$anonfun$1(monadError.scala:64:0)
[error]         at .loop$1$$anonfun$1(Resource.scala:174:0)
[error]         at zio.interop.package.signalOnNoExternalInterrupt(package.scala:214)
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-14" (3m 196s 913ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-1" (3m 197s 357ms) 
[error]         Status: Done
[error] "zio-fiber-8" (3m 196s 948ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-34" (3m 196s 893ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.racePair(cats.scala:312)
[error] "zio-fiber-0" (3m 197s 530ms) waiting on #3
[error]         Status: Suspended((Interruption, CooperativeYielding, FiberRoots, WindDown), <no trace>)
[error] "zio-fiber-26" (3m 196s 906ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-27" (3m 196s 909ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-4" (3m 197s 355ms) waiting on #5
[error]         Status: Suspended((CooperativeYielding, FiberRoots), zio.interop.ZioConcurrent.toFiber.$anon.cancel(cats.scala:225))
[error]         at zio.interop.ZioConcurrent.toFiber.$anon.cancel(cats.scala:225)
[error]         at <empty>.TestHttp4s.run(TestHttp4s.scala:19)
[error] "zio-fiber-41" (3m 196s 895ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.racePair(cats.scala:312)
[error] "zio-fiber-35" (3m 196s 910ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.racePair(cats.scala:312)
[error] "zio-fiber-19" (3m 196s 920ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-22" (3m 196s 924ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-15" (3m 196s 930ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-6" (3m 197s 34ms) 
[error]         Status: Suspended((CooperativeYielding, FiberRoots), zio.interop.ZioAsync.never(ZioAsync.scala:65))
[error]         at .1$$anonfun(InterruptContext.scala:99:0)
[error]         at .map$$anonfun$1(Monad.scala:35:0)
[error]         at .$anonfun$3(Pull.scala:1107:0)
[error]         at zio.interop.ZioMonadErrorE.attempt.trace(cats.scala:741)
[error]         at .3$$anonfun(Pull.scala:1248:0)
[error]         at zio.interop.ZioMonadErrorE.attempt.trace(cats.scala:741)
[error]         at .3$$anonfun(Pull.scala:1248:0)
[error]         at zio.interop.ZioMonadErrorE.attempt.trace(cats.scala:741)
[error]         at .3$$anonfun(Pull.scala:1248:0)
[error]         at .loop$1$$anonfun$1(Resource.scala:174:0)
[error]         at zio.interop.ZioConcurrent.guaranteeCase(cats.scala:328)
[error]         at .andThen(Function1.scala:87:0)
[error]         at zio.interop.package.signalOnNoExternalInterrupt(package.scala:214)
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
[error] "zio-fiber-7" (3m 197s 28ms) 
[error]         Status: Done
[error]         at zio.interop.ZioConcurrent.start.trace(cats.scala:249)
```