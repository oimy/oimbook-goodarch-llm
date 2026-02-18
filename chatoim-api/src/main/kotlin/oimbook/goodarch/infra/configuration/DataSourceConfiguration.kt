package oimbook.goodarch.infra.configuration

import oimbook.goodarch.infra.database.DataAccessor
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackageClasses = [DataAccessor::class])
@EntityScan(basePackageClasses = [DataAccessor::class])
class DataSourceConfiguration