package com.explorer.ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
public class LdapClient{
	private DirContext dirContext = null;

	public LdapClient()
	{
    System.out.println("constructer to LDAP bind");
		try
        {
       String url = "ldap://localhost:1389";
	   String conntype = "simple";
  	   String AdminDn  = "uid=admin,ou=system";
	   String password = "secret";
        Hashtable<String, String> environment = new Hashtable<String, String>();

        environment.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL,url);         
        environment.put(Context.SECURITY_AUTHENTICATION,conntype);         
        environment.put(Context.SECURITY_PRINCIPAL,AdminDn);
        environment.put(Context.SECURITY_CREDENTIALS, password);
        dirContext = new InitialDirContext(environment);
         System.out.println("Bind successful");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
      }

	public static void main(String[] args) {
		String userIdToInsert = "125";
		LdapClient addEntryToLdap = new LdapClient();
		if(addEntryToLdap.addEntry(userIdToInsert))
		{
		System.out.println("entry creation completed");
		}
		else
		{
		System.out.println("entry creation failed");	
		}
	}
	//Attributes to be set for new entry creation
	public boolean addEntry(String userId)
	{
	boolean flag = false;
	Attribute userCn = new BasicAttribute("cn", "test5");
	Attribute userSn = new BasicAttribute("sn", "test5");
	Attribute userid = new BasicAttribute("userid", "test5");
	Attribute userPassword = new BasicAttribute("userPassword", "test5");
	//ObjectClass attributes
	Attribute oc = new BasicAttribute("objectClass");
	oc.add("inetOrgPerson");
	oc.add("organizationalPerson");
	oc.add("person");
	oc.add("top");

	Attributes entry = new BasicAttributes();
	entry.put(userCn);
	entry.put(userSn);
	entry.put(userid);
	entry.put(userPassword);
	entry.put(oc);
	//uid=142,ou=alzebra,dc=mathsdep,dc=college
	String entryDN = "uid=test5,ou=users,ou=system";
	System.out.println("entryDN :" + entryDN);
	try{
		dirContext.createSubcontext(entryDN, entry);
		flag = true;
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("error: " + e.getMessage());
		return flag;
		}
	return flag	;
	}
}
