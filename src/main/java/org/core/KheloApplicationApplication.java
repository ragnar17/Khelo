package org.core;



import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PolymorphicAuthDynamicFeature;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;

import org.core.auth.AuthFilterUtils;
import org.core.auth.jwt.ExampleUser;
import org.core.db.MongoDBFactoryConnection;
import org.core.db.MongoDBManaged;
import org.core.db.daos.*;
import org.core.health.DropwizardMongoDBHealthCheck;
import org.core.resources.*;
import org.core.response.ProtectedResourceOne;
import org.core.response.ProtectedResourceTwo;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtContext;
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
        final UserDAO userDAO = new UserDAO(mongoDBManagerConn.getClient()
                .getDatabase(configuration.getMongoDBConnection().getDatabase())
                .getCollection("user"));
        final SellerDAO sellerDAO = new SellerDAO(mongoDBManagerConn.getClient()
                .getDatabase(configuration.getMongoDBConnection().getDatabase())
                .getCollection("seller"));
        final StadiumDAO stadiumDAO = new StadiumDAO(mongoDBManagerConn.getClient()
                .getDatabase(configuration.getMongoDBConnection().getDatabase())
                .getCollection("stadium"));
        final CourtDAO courtDAO = new CourtDAO(mongoDBManagerConn.getClient()
                .getDatabase(configuration.getMongoDBConnection().getDatabase())
                .getCollection("court"));

        environment.lifecycle().manage(mongoDBManaged);
        environment.jersey().register(new DonutResource(donutDAO));
        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(new SellerResource(sellerDAO));
        environment.jersey().register(new StadiumResource(stadiumDAO));
        environment.jersey().register(new CourtResource(courtDAO));

        environment.jersey().register(new LoginResource());
        environment.jersey().register(new ProtectedResourceOne());
        environment.jersey().register(new ProtectedResourceTwo());
        registerAuthFilters(environment);

        environment.healthChecks().register("DropwizardMongoDBHealthCheck",
                new DropwizardMongoDBHealthCheck(mongoDBManagerConn.getClient()));
    }
    private void registerAuthFilters(Environment environment) {
        AuthFilterUtils authFilterUtils = new AuthFilterUtils();
        final AuthFilter<BasicCredentials, PrincipalImpl> basicFilter = authFilterUtils.buildBasicAuthFilter();
        final AuthFilter<JwtContext, ExampleUser> jwtFilter = authFilterUtils.buildJwtAuthFilter();

        final PolymorphicAuthDynamicFeature feature = new PolymorphicAuthDynamicFeature<>(
                ImmutableMap.of(PrincipalImpl.class, basicFilter, ExampleUser.class, jwtFilter));
        final AbstractBinder binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
                ImmutableSet.of(PrincipalImpl.class, ExampleUser.class));

        environment.jersey().register(feature);
        environment.jersey().register(binder);
        environment.jersey().register(RolesAllowedDynamicFeature.class);
    }
}

