package org.core;



import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.core.db.MongoDBFactoryConnection;
import org.core.db.MongoDBManaged;
import org.core.db.daos.DonutDAO;
import org.core.health.DropwizardMongoDBHealthCheck;
import org.core.resources.DonutResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KheloApplicationApplication extends Application<KheloApplicationConfiguration> {

    /**
     * Logger class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KheloApplicationApplication.class);

    /**
     * Entry point for start Application.
     *
     * @param args the args.
     * @throws Exception when the app can not start.
     */
    public static void main(final String[] args) throws Exception {
        LOGGER.info("Start application.");
        new KheloApplicationApplication().run(args);
    }

    @Override
    public String getName() {
        return "KheloApplication";
    }

//    @Override
//    public void initialize(final Bootstrap<KheloApplicationConfiguration> bootstrap) {
//        bootstrap.addBundle(new SwaggerBundle<KheloApplicationConfiguration>() {
//            @Override
//            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
//                    final KheloApplicationConfiguration kheloApplicationConfiguration) {
//                return kheloApplicationConfiguration.getSwaggerBundleConfiguration();
//            }
//        });
//    }
        @Override
        public void initialize(final Bootstrap<KheloApplicationConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final KheloApplicationConfiguration configuration,
                    final Environment environment) {

        final MongoDBFactoryConnection mongoDBManagerConn = new MongoDBFactoryConnection(configuration.getMongoDBConnection());

        final MongoDBManaged mongoDBManaged = new MongoDBManaged(mongoDBManagerConn.getClient());

        final DonutDAO donutDAO = new DonutDAO(mongoDBManagerConn.getClient()
                .getDatabase(configuration.getMongoDBConnection().getDatabase())
                .getCollection("donuts"));

        environment.lifecycle().manage(mongoDBManaged);
        environment.jersey().register(new DonutResource(donutDAO));
        environment.healthChecks().register("DropwizardMongoDBHealthCheck",
                new DropwizardMongoDBHealthCheck(mongoDBManagerConn.getClient()));
    }
}
//    public static void main(final String[] args) throws Exception {
//        new KheloApplicationApplication().run(args);
//    }
//
//    @Override
//    public String getName() {
//        return "KheloApplication";
//    }
//
//    @Override
//    public void initialize(final Bootstrap<KheloApplicationConfiguration> bootstrap) {
//        // TODO: application initialization
//    }
//
//    @Override
//    public void run(final KheloApplicationConfiguration configuration,
//                    final Environment environment) {
//        // TODO: implement application
//    }

//}
