package JIT;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

class Base {

}

class AA {
    @Inject
    AA(Base base) {

    }
}

class AAModule extends AbstractModule {
    @Override
    protected void configure() {
        binder().requireExplicitBindings();
    }
}

public class test {
    public static void main(String[] args) {
        // this statement will throw error
        // Explicit bindings are required and JIT.AA is not explicitly bound. while locating JIT.AA
        Injector injector = Guice.createInjector(new AAModule());
        AA client = injector.getInstance(AA.class);
    }
}
