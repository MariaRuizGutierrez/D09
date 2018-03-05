
package utilities;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import domain.Trip;

@SuppressWarnings("deprecation")
public class FullTextSearch {

	private static ServiceRegistry	sr	= null;
	private static SessionFactory	sf	= null;
	private static Session			ss	= null;


	@SuppressWarnings({
		"unchecked"
	})
	public static void main(final String[] args) throws InterruptedException {
		//inicio de session
		Configuration configuration = new Configuration();
		configuration.configure();
		Properties properties = configuration.getProperties();
		FullTextSearch.sr = new ServiceRegistryBuilder().applySettings(properties).build();
		FullTextSearch.sf = configuration.buildSessionFactory(FullTextSearch.sr);
		FullTextSearch.ss = FullTextSearch.sf.openSession();

		// Recogemos los datos indexados
		FullTextSession fts = Search.getFullTextSession(FullTextSearch.ss);
		fts.createIndexer().startAndWait();

		//mostramos los datos
		List<Trip> tripResult = FullTextSearch.ss.createQuery("from Trip").list();

		for (final Trip t : tripResult)
			System.out.println("Trip " + t.getTicker() + ", tittle: " + t.getTitle() + ", description: " + t.getDescription());

		Scanner scn = new Scanner(System.in);
		String tecladoString = null;

		System.out.println("\nEscriba la palabra clave por la que quiera buscar: ");

		//palabra que vamos a escribir por pantalla
		tecladoString = scn.nextLine();

		// lucene prepara la query
		QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Trip.class).get();

		org.apache.lucene.search.Query lq = qb.keyword().onFields("ticker", "title", "description").matching(tecladoString).createQuery();

		org.hibernate.Query ftq = fts.createFullTextQuery(lq, Trip.class);

		// Recogemos de la query y los mostramos en caso de que se encuentren
		List<Trip> ilr = ftq.list();

		if (ilr.isEmpty() == false)
			for (Trip t : ilr) {
				System.out.println("\n> Datos encontrados para '" + tecladoString + "'");
				System.out.println("Trip " + t.getTicker() + ", tittle: " + t.getTitle() + ", description: " + t.getDescription());
			}
		else
			System.out.println("No encontrado");
		fts.close();
	}
}
