package org.goafabric.medicaldataservice.service.persistence.extensions;

import org.flywaydb.core.Flyway;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

// Source: https://spring.io/blog/2022/07/31/how-to-integrate-hibernates-multitenant-feature-with-spring-data-jpa-in-a-spring-boot-application

@Component
@ConditionalOnExpression("#{!('${spring.autoconfigure.exclude:}'.contains('DataSourceAutoConfiguration'))}")
@RegisterReflectionForBinding({org.flywaydb.database.postgresql.TransactionalModel.class, org.hibernate.binder.internal.TenantIdBinder.class, org.hibernate.generator.internal.TenantIdGeneration.class})
public class TenantResolver implements CurrentTenantIdentifierResolver<String>, MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

    private final DataSource dataSource;
    private final String schemaPrefix;
    private final String defaultSchema;

    public TenantResolver(DataSource dataSource,
                          @Value("${multi-tenancy.default-schema:PUBLIC}") String defaultSchema,
                          @Value("${multi-tenancy.schema-prefix:_}") String schemaPrefix) {
        this.dataSource = dataSource;
        this.defaultSchema = defaultSchema;
        this.schemaPrefix = schemaPrefix;
    }

    /** Resolver for optional CompanyId via @TenantId Discriminator **/

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContext.getOrganizationId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }

    /** Tenant Resolver for Schema **/

    @Override
    public Connection getConnection(String schema) throws SQLException {
        var connection = dataSource.getConnection();
        connection.setSchema(defaultSchema.equals(schema) ? defaultSchema : schemaPrefix + TenantContext.getTenantId());
        return connection;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection(defaultSchema);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }


    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        connection.setSchema(defaultSchema);
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        throw new IllegalStateException("unwrap not supported");
    }

    /** Flyway configuration to create database schemas **/

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {};
    }

    @Bean
    public Boolean schemaCreator(Flyway flyway,
                                           @Value("${database.provisioning.goals}") String goals,
                                           @Value("${multi-tenancy.tenants}") String tenants,
                                           @Value("${multi-tenancy.schema-prefix:_}") String schemaPrefix) {
        if (goals.contains("-migrate")) {
            Arrays.asList(tenants.split(",")).forEach(tenant -> {
                        Flyway.configure().configuration(flyway.getConfiguration())
                                .schemas(schemaPrefix + tenant).defaultSchema(schemaPrefix + tenant)
                                .placeholders(Map.of("tenantId", tenant))
                                .load().migrate();
                    }
            );
        }
        return true;
    }

}