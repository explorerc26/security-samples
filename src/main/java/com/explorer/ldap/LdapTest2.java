package com.explorer.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapTest2 {

	public static void main(String[] args) {
		
		Hashtable env = new Hashtable(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://ENT-SMUN6-J0K9.hq.nt.newyorklife.com:1389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		env.put(Context.SECURITY_CREDENTIALS, "secret");
		 try {
		        LdapContext ctx = new InitialLdapContext(env, null);
		        ctx.setRequestControls(null);
		        NamingEnumeration<?> namingEnum = ctx.search("ou=users,ou=system", "(objectClass=inetOrgPerson)", getSimpleSearchControls());
		        while (namingEnum.hasMore ()) {
		            SearchResult result = (SearchResult) namingEnum.next ();    
		            Attributes attrs = result.getAttributes ();
		            System.out.println(attrs.get("cn"));

		        } 
		        namingEnum.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

	}
	
	
	private static SearchControls getSimpleSearchControls() {
	    SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    searchControls.setTimeLimit(30000);
	    //String[] attrIDs = {"objectGUID"};
	    //searchControls.setReturningAttributes(attrIDs);
	    return searchControls;
	}

}
