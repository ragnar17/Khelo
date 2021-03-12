package org.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class KheloApplicationApplication extends Application<KheloApplicationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new KheloApplicationApplication().run(args);
    }

    @Override
    public String getName() {
        return "KheloApplication";
    }

    @Override
    public void initialize(final Bootstrap<KheloApplicationConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final KheloApplicationConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
