package utils;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServicesLocator
{
	//-----------------------------------------------------------------------------
	private Context initialContext;
	private Map<String, Object> cache;
	// Singleton
	private static final ServicesLocator instance = new ServicesLocator();
	//-----------------------------------------------------------------------------
	private ServicesLocator()
	{
		try
		{
			initialContext = new InitialContext();
			cache = new HashMap<String, Object>();
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------
	public static ServicesLocator getInstance()
	{
		return instance;
	}
	//-----------------------------------------------------------------------------

	public Object getRemoteInterface(String nomEJB) throws ServicesLocatorException
	{
		// Le nom JNDI pour la récupération du service distant (stub du
		// composant EJB) est de la forme :
		//   java:global/<nom projet EAR>/<nom sous-projet EJB>/<nom bean session EJB>!<nom complet avec package de l'interface remote du bean>
		// Exemple :
		//   java:global/CabinetRecrutement_EAR/CabinetRecrutement_EJB/ServiceEntreprise!eu.telecom_bretagne.cabinet_recrutement.service.IServiceEntreprise

		String nomJNDI = null;
		if(nomEJB.equals("ServiceAsk"))
			nomJNDI = "java:global/EAR/EJB/ServiceAsk!service.IEmployee";
		else if(nomEJB.equals("ServiceValidate"))
			nomJNDI = "java:global/EAR/EJB/ServiceValidate!service.IValidator";

		// ATTENTION !!! La récupération d'un DAO n'existe ici que
		// pour les contrôles (utilisés dans la servlet ControleDAOServlet) :
		// ils ne sont normalement pas appelés par la couche IHM.

		else if(nomEJB.equals("CommentDAO"))
			nomJNDI = "java:global/EAR/EJB/CommentDAO!data.dao.CommentDAO";
		else if(nomEJB.equals("EmployeeDAO"))
			nomJNDI = "java:global/EAR/EJB/EmployeeDAO!data.dao.EmployeeDAO";
        else if(nomEJB.equals("ServiceDAO"))
            nomJNDI = "java:global/EAR/EJB/ServiceDAO!data.dao.ServiceDAO";
        else if(nomEJB.equals("EmployeeDAO"))
            nomJNDI = "java:global/EAR/EJB/ServiceDAO!data.dao.ServiceDAO";
		else
			throw new ServicesLocatorException("Il n'y a pas d'EJB avec ce nom... " + nomEJB);

		// La méthode recherche d'abord le stub dans le cache, s'il est absent,
		// il est récupéré via JNDI.
		Object remoteInterface = cache.get(nomJNDI);
		if (remoteInterface == null)
		{
			try
			{
				remoteInterface = initialContext.lookup(nomJNDI);
				cache.put(nomJNDI, remoteInterface);
			}
			catch (Exception e)
			{
				throw new ServicesLocatorException(e);
			}
		}
		return remoteInterface;
	}
	//-----------------------------------------------------------------------------
}
