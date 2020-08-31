package ToConstructorBindings;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

class Connection {
    String address = "127.0.0.1";
}

class DataBaseClient {
    public DataBaseClient(Connection conn) {
        System.out.println(conn.address);
    }
}

class DataBaseClientModule extends AbstractModule {
    @Override
    protected void configure() {
        try {
            // Occasionally @Inject can not be applied to the target constructor: either because it is a third party
            // class, or because multiple constructors that participate in dependency injection.

            // to use toConstructor, the DataBaseClient's constructor must be public
            bind(DataBaseClient.class).toConstructor(DataBaseClient.class.getConstructor(Connection.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

public class test {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DataBaseClientModule());
        DataBaseClient client = injector.getInstance(DataBaseClient.class);
    }
}
