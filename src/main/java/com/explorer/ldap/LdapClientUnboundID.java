package com.explorer.ldap;

import java.util.List;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

public class LdapClientUnboundID {

	public static void main(String[] args) throws Exception {

		// Get all Oragnization Units and Containers
		String baseDN = "ou=users,ou=system";
		// String filter =
		// "(&(|(objectClass=organizationalUnit)(objectClass=container)))";
		String filter = "(&(objectClass=inetOrgPerson))";
		LDAPConnection connection = getConnection();
		List<SearchResultEntry> results = getResults(connection, baseDN, filter);
		printResults(results);

		// Get a specific Organization Unit
		baseDN = "DC=com,DC=example,DC=local";
		String dn = "CN=Users,DC=com,DC=example,DC=local";
		String filterFormat = "(&(|(objectClass=organizationalUnit)(objectClass=container))(distinguishedName=%s))";
		filter = String.format(filterFormat, dn);
		connection = getConnection();
		results = getResults(connection, baseDN, filter);
		printResults(results);

		// Get all users under an Organizational Unit
		baseDN = "CN=Users,DC=com,DC=example,DC=local";
		filter = "(&(objectClass=user)(!(objectCategory=computer)))";
		connection = getConnection();
		results = getResults(connection, baseDN, filter);
		printResults(results);

		// Get a specific user under an Organization Unit
		baseDN = "CN=Users,DC=com,DC=example,DC=local";
		String userDN = "CN=abc,CN=Users,DC=com,DC=example,DC=local";

		filterFormat = "(&(objectClass=user)(distinguishedName=%s))";
		filter = String.format(filterFormat, userDN);
		connection = getConnection();
		results = getResults(connection, baseDN, filter);
		printResults(results);

	}

	public static void printResults(List<SearchResultEntry> results) {
		for (SearchResultEntry e : results) {
			System.out.println("name: " + e.getAttributeValue("name"));
		}
	}

	public static LDAPConnection getConnection() throws LDAPException {
		// host, port, username and password
		return new LDAPConnection("ENT-SMUN6-J0K9.hq.nt.newyorklife.com", 1389, "uid=admin,ou=system", "secret");
	}

	public static List<SearchResultEntry> getResults(LDAPConnection connection, String baseDN, String filter)
			throws LDAPSearchException {
		SearchResult searchResult;

		if (connection.isConnected()) {
			searchResult = connection.search(baseDN, SearchScope.ONE, filter);

			return searchResult.getSearchEntries();
		}

		return null;
	}

}
