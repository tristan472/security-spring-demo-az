package springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig implements WebMvcConfigurer {
	
	// créer une variable les properties
	@Autowired
	private Environment env;
	
	// mise en place d'un attribut logger pour diagnostiquer
    private Logger logger = Logger.getLogger(getClass().getName());
    
 // bean pour la source des données de sécurité
    @Bean
    public DataSource securityDataSource() {
        // créer une connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
        
        // set la classe de pilote jdbc
        try {
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        
        // logger les propriétés de connexion
        // juste pour s'assurer qu'on lit les bonnes propriétés
        logger.info(">>jdbc.url = " + env.getProperty("jdbc.url"));
        logger.info(">>jdbc.user = " + env.getProperty("jdbc.user"));
        
        // set les propriétés de la connection à la BDD
        securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        securityDataSource.setUser(env.getProperty("jdbc.user"));
        securityDataSource.setPassword(env.getProperty("jdbc.password"));
        
        securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        // set les propriétés de la connection pool
        
        return securityDataSource;
    }
    
	 	// methode d'assistance
	    // lire une propriété d'environnement et la convertir en int
	    private int getIntProperty(String nomProp) {
	        String valeurProp = env.getProperty(nomProp);
	        return Integer.parseInt(valeurProp);
	    }

	// définir une bean pour le ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	        
	    }
	
}
