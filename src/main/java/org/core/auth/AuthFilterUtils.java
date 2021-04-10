package org.core.auth;

import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import org.core.auth.basic.BasicAuthenticator;
import org.core.auth.jwt.ExampleUser;
import org.core.auth.jwt.JwtAuthenticator;
import org.core.auth.jwt.JwtAuthoriser;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;

public class AuthFilterUtils {
    public BasicCredentialAuthFilter<PrincipalImpl> buildBasicAuthFilter() {
        return new BasicCredentialAuthFilter.Builder<PrincipalImpl>().setAuthenticator(new BasicAuthenticator()).setPrefix("Basic")
                .buildAuthFilter();
    }

    public AuthFilter<JwtContext, ExampleUser> buildJwtAuthFilter() {
        // These requirements would be tightened up for production use
        final JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(300).setRequireSubject()
                .setVerificationKey(new HmacKey(Secrets.JWT_SECRET_KEY)).build();

        return new JwtAuthFilter.Builder<ExampleUser>().setJwtConsumer(consumer).setRealm("realm").setPrefix("Bearer")
                .setAuthenticator(new JwtAuthenticator()).setAuthorizer(new JwtAuthoriser()).buildAuthFilter();
    }
}
