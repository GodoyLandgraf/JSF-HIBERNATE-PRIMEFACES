package posjavamavenhibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



/* LÊ O ARQUIVO PERSISTENCE - LÊ O ARQUIVO APENAS 1 X */
public class HibernateUtil {
	
	public static EntityManagerFactory factory = null; /* Fábrica de conexão do hibernate*/
	
	/*Chama o método init(), se for a primeira vez, lê o arquivo. Se nao só retorna o factory*/
	static {
		init();
	}
	
	private static void init() {
		/* verificação se vai ser esse arquivo apenas 1 única vez*/
		try {
			if(factory == null) {
				factory = Persistence.createEntityManagerFactory("pos-java-maven-hibernate");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();   /*Prove a parte de persistência */

}
	
	/*Retorna a primary key*/
	public static Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
}
