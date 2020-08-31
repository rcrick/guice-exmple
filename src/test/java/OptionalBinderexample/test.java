package OptionalBinderexample;

import com.google.common.base.Optional;
import com.google.inject.*;
import com.google.inject.multibindings.OptionalBinder;

class TmpLogger {
    void info(String mesage) {
        System.out.println(mesage);
    }
}

class Request {
    Optional<TmpLogger> requestlogger;

    @Inject
    Request(Optional<TmpLogger> logger) {
        this.requestlogger = logger;
    }

    void request() {
        if (this.requestlogger.isPresent()) {
            this.requestlogger.get().info("start request");
        }
        System.out.println("request....");
        if (this.requestlogger.isPresent()) {
            this.requestlogger.get().info("finish request");
        }
    }
}

class RequestModule extends AbstractModule {
    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), TmpLogger.class);
    }

    /**
     * we can provide TmpLogger at there
     **/
    @Provides
    TmpLogger provideTmpLogger() {
        return new TmpLogger();
    }
}

public class test {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new RequestModule());
        Request req = injector.getInstance(Request.class);
        req.request();
    }
}